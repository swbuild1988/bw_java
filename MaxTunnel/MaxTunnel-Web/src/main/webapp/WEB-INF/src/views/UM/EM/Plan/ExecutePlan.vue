<template>
    <div>
        <Row style="padding: 1vmin 3vmin">
            <Col span="7">
            <Select v-model="condition.tunnelId" style="width:60%;" @on-change="handleTunnelChange">
                <Option v-for="item in selectList.tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
            </Col>
            <Col span="7">
            <Select v-model="condition.areaId" style="width:60%;">
                <Option v-for="item in selectList.areas" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
            </Col>
            <Col span="7">
            <Select v-model="condition.storeId" style="width:60%;">
                <Option v-for="item in selectList.stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
            </Col>
            <Col span="3">
            <div class="begin">
                <Button type="success" @click="startPlan">启动预案</Button>
            </div>
            </Col>
        </Row>
        <hr class="hr3">
        <Row>
            <Col span="12" style="position: relative;padding: 10px;">
            <Card :bordered="false" style="height: 77vmin" class="processCard">
                <p slot="title" style="font-size: 1.66vmin;">执行日志</p>
                <div v-if="nodePicState === 2" style="height:69vmin;max-width:54vmin">
                    <image-from-url :url="processPicSrc"></image-from-url>
                </div>
                <span v-else style="font-size: 2.8vmin;">{{nodePicState ? '已开启，图片加载中' : '未开启预案' }}</span>
            </Card>
            </Col>
            <Col span="12" style="padding: 10px;">
            <div style="height: 33vmin" class="coolBox">
                <Carousel v-bind="curCarousel"></Carousel>
            </div>
            </Col>
            <Col span="12" style="padding: 10px;">
            <Card :bordered="false" style="height: 41.8vmin" class="objCard">
                <p slot="title" style="font-size: 1.66vmin;">监测对象列表</p>
                <span v-if="!nodePicState" style="font-size: 2.8vmin;">未开启预案</span>
                <Row v-else>
                    <Col span="8" v-for="obj in objList" :key="obj.id">
                    <div class="objWrapper">
                        <p class="objName">{{obj.name + ' ' + obj.id}}</p>
                        <p v-if="obj.datatypeId === 1" style="font-size: 3vmin;padding: 1.6vmin 0;">
                            {{obj.curValue + obj.unit}}</p>
                        <div style="margin:0 auto;" v-else>
                            <img style="width: 7vmin;height:7vmin" :src="obj.image"
                                :title="obj.curValue == 0 ? '关' : (obj.curValue == 1 ? '开' : '故障')">
                        </div>
                        <span>{{ new Date(obj.time).format("yyyy-MM-dd hh:mm:ss")}}</span>
                    </div>
                    </Col>
                </Row>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import {
        PlanService
    } from "../../../../services/planService";
    import {
        VideoService
    } from "../../../../services/videoService";
    import {
        TunnelService
    } from "../../../../services/tunnelService";
    import {
        MeasObjServer
    } from "../../../../services/MeasObjectSerivers.js";
    import {
        EnumsService
    } from "../../../../services/enumsService";
    import Carousel from "../../../../components/Common/Carousel.vue";
    import ImageFromUrl from "../../../../components/Common/ImageFromUrl";

    export default {
        name: "execute-plan",
        data() {
            return {
                curCarousel: {
                    videolist: []
                },
                processInstanceId: null,
                processPicSrc: null,
                condition: {
                    areaId: null,
                    tunnelId: null,
                    storeId: null,
                    processValue: null
                },
                selectList: {
                    tunnels: [],
                    areas: [],
                    stores: []
                },
                nodePicState: 0,
                objList: []
            };
        },

        mounted() {
            this.init();
        },

        computed: {},

        watch: {
            $route: function () {
                this.init();
            }
        },

        methods: {
            init() {
                TunnelService.getTunnels().then(
                    res => {
                        this.selectList.tunnels = res;
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
                EnumsService.getPlanType().then(
                    res => {
                        let curPlan = res.find(plan => {
                            return plan.processKey == this.$route.params.processKey;
                        });
                        this.condition.processValue = curPlan.val;
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
            },
            handleTunnelChange() {
                TunnelService.getStoresByTunnelId(this.condition.tunnelId).then(
                    res => {
                        this.selectList.stores = res;
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
                TunnelService.getAreasByTunnelId(this.condition.tunnelId).then(
                    res => {
                        this.selectList.areas = res;
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
            },
            startPlan() {
                let _this = this;
                PlanService.startPlan(_this.condition).then(res => {
                    _this.$Message.success("预案已启动！");
                    _this.acceptPlanData();
                    _this.getVideoList();
                    // _this.getMeasObjs();
                    _this.processInstanceId = res;
                })
            },
            // 连接成功回调函数
            callback(data) {
                this.Log.info("executPlan收到回调", data)
                let result = JSON.parse(data);
                if (result.type && result.type == "Plan") {
                    let content = JSON.parse(result.content);
                    this.Log.info("executPlan收到回调", content)

                    // 如果返回的预案实例不是这个页面调用的预案实例
                    if (content.processInstanceId != this.processInstanceId) return;
                    this.processPicSrc = null;
                    let _this = this;
                    this.$nextTick(() => {
                        _this.processPicSrc =
                            "/emplans/png/" + content.processInstanceId;
                        _this.nodePicState = 2;
                    });
                    
                    // 如果预案结束
                    if (content.status == "finished") {
                        this.stopPlan();
                        this.$Message.success("预案执行结束")
                    }
                }
            },
            //获取MQ推送的预案消息
            acceptPlanData() {
                this.Log.info("添加监听器到MQ")
                this.TransferStation.addListener("ExecutePlan", this.callback);
                this.nodePicState = 1;
            },
            // 预案结束，停止监听
            stopPlan() {
                this.Log.info("移除监听器")
                this.TransferStation.deleteListener("ExecutePlan");
            },
            getMeasObjs() {
                MeasObjServer.getMeasObjects(
                        this.condition.storeId,
                        this.condition.areaId
                    )
                    .then(
                        res => {
                            this.objList = res;
                            this.objList.forEach(obj => {
                                if (obj.datatypeId !== 1) {
                                    let image_url =
                                        obj.curValue == 0 ?
                                        "close-state" :
                                        obj.curValue == 1 ?
                                        "open-state" :
                                        "fault-state";
                                    obj.image = require("../../../../assets/VM/" +
                                        image_url +
                                        ".png");
                                }
                            });
                        },
                        err => {
                            this.Log.info(err);
                        }
                    )
                    .finally(() => {
                        let _this = this;
                        this.getObjsTimer = setTimeout(() => {
                            _this.getMeasObjs();
                        }, 5000);
                    });
            },
            getVideoList() {
                VideoService.getVideoList(
                    this.condition.storeId,
                    this.condition.areaId
                ).then(
                    res => {
                        this.curCarousel.videolist = res;
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
            }
        },
        beforeDestroy() {
            clearTimeout(this.getObjsTimer);
        },
        components: {
            Carousel,
            ImageFromUrl
        }
    };
</script>

<style scoped>
    .begin {
        padding-left: 1vmin;
        width: 11vmin;
    }

    .hr3 {
        height: 7px;
        border: none;
        border-top: 5px ridge #45d6d8;
    }

    .ivu-steps>>>.ivu-steps-title {
        font-size: 18px;
    }

    .ivu-steps>>>.ivu-steps-content {
        font-size: 18px;
    }

    .coolBox {
        border: 1px solid rgba(0, 0, 0, 0.2);
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
        border-radius: 5px;
        background-clip: padding-box;
        background: rgba(36, 40, 42, 0.5);
        box-shadow: 0 0 13px 3px rgba(0, 0, 0, 0.5);
    }

    .processCard>>>.ivu-card-head p,
    .objCard>>>.ivu-card-head p {
        height: 2vmin;
        line-height: 2vmin;
    }

    .objCard>>>.ivu-card-body {
        height: 36vmin;
        overflow-y: auto;
    }

    .objWrapper {
        margin: 1vmin;
        border: 1px solid #cecfd2;
        padding: 1vmin;
        text-align: center;
        border-radius: 10px;
        font-size: 1.58vmin;
        height: 16vmin;
    }

    .objName {
        /* overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis; */
        height: 4.4vmin;
    }
</style>