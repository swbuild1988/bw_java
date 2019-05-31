<template>
    <div>
        <Row class="conditions">
            <Col span="7">
                <span>所属管廊：</span>
                <Select
                    v-model="condition.tunnelId"
                    style="width:60%;"
                    @on-change="handleTunnelChange"
                >
                    <Option
                        v-for="item in selectList.tunnels"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="7">
                <span>所属区域：</span>
                <Select v-model="condition.areaId" style="width:60%;">
                    <Option
                        v-for="item in selectList.areas"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="7">
                <span>所属管舱：</span>
                <Select v-model="condition.storeId" style="width:60%;">
                    <Option
                        v-for="item in selectList.stores"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
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
                    <p slot="title" style="font-size: 1.66vmin;color:#fff;">执行日志</p>
                    <div v-if="nodePicState === 2" style="height:69vmin;max-width:54vmin">
                        <image-from-url :url="processPicSrc"></image-from-url>
                    </div>
                    <span
                        v-else
                        style="font-size: 2.8vmin;color:#fff;"
                    >{{nodePicState ? '已开启，图片加载中' : '未开启预案' }}</span>
                </Card>
            </Col>
            <Col span="12" style="padding: 10px;">
                <div style="height: 33vmin" class="coolBox">
                    <Carousel v-bind="curCarousel"></Carousel>
                </div>
            </Col>
            <Col span="12" style="padding: 10px;">
                <Card :bordered="false" style="height: 41.8vmin" class="objCard">
                    <p slot="title" style="font-size: 1.66vmin;color:#fff;">监测对象列表</p>
                    <span v-if="!nodePicState" style="font-size: 2.8vmin;color:#fff;">未开启预案</span>
                    <Row v-else>
                        <Col span="8" v-for="obj in objList" :key="obj.id">
                            <div class="objWrapper">
                                <p class="objName">{{obj.name + ' ' + obj.id}}</p>
                                <p
                                    v-if="obj.datatypeId === 1"
                                    style="font-size: 3vmin;padding: 1.6vmin 0;"
                                >{{obj.curValue + obj.unit}}</p>
                                <div style="margin:0 auto;" v-else>
                                    <img
                                        style="width: 7vmin;height:7vmin"
                                        :src="obj.image"
                                        :title="obj.curValue == 0 ? '关' : (obj.curValue == 1 ? '开' : '故障')"
                                    >
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
import { PlanService } from "../../../../services/planService";
import { VideoService } from "../../../../services/videoService";
import { TunnelService } from "../../../../services/tunnelService";
import { MeasObjServer } from "../../../../services/MeasObjectSerivers.js";
import { EnumsService } from "../../../../services/enumsService";
import Carousel from "../../../../components/Common/Carousel.vue";
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";

export default {
    name: "execute-plan",
    data() {
        return {
            curCarousel: {
                videolist: []
            },
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
        $route: function() {
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
            PlanService.startPlan(this.condition)
                .then(res => {
                    this.$Message.success("预案已完成！");
                })
                .finally(() => {
                    this.stopPlan();
                });
            this.acceptPlanData();
            this.getVideoList();
            this.getMeasObjs();
        },
        // 连接成功回调函数
        callback(data) {
            this.Log.info("executPlan收到回调", data);
            let result = JSON.parse(data);
            if (result.type && result.type == "Plan") {
                let content = JSON.parse(result.content);
                this.Log.info("executPlan收到回调", content);
                this.processPicSrc = null;
                let _this = this;
                this.$nextTick(() => {
                    _this.processPicSrc =
                        "/emplans/png/" + content.processInstanceId;
                    _this.nodePicState = 2;
                });
            }
        },
        //获取MQ推送的预案消息
        acceptPlanData() {
            this.Log.info("添加监听器到MQ");
            this.TransferStation.addListener("ExecutePlan", this.callback);
            this.nodePicState = 1;
        },
        // 预案结束，停止监听
        stopPlan() {
            this.Log.info("移除监听器");
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
                                    obj.curValue == 0
                                        ? "close-state"
                                        : obj.curValue == 1
                                        ? "open-state"
                                        : "fault-state";
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
.conditions {
    padding: 1vmin 3vmin;
    color: #fff;
    font-size: 1.4vmin;
    height: 5vmin;
    line-height: 3vmin;
}
.conditions >>> .ivu-select-selection {
    height: 3.2vmin;
    background: transparent;
    border-radius: 1vmin;
    color: #fff;
}
.conditions >>> .ivu-select-placeholder {
    color: #fff;
}
.begin {
    padding-left: 1vmin;
    width: 11vmin;
}

.hr3 {
    height: 7px;
    border: none;
    border-top: 5px ridge #45d6d8;
}

.ivu-steps >>> .ivu-steps-title {
    font-size: 18px;
}

.ivu-steps >>> .ivu-steps-content {
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
.processCard,
.objCard {
    background: transparent;
    border: 1px solid #fff;
    border-radius: 1vmin;
}

.processCard >>> .ivu-card-head p,
.objCard >>> .ivu-card-head p {
    height: 2vmin;
    line-height: 2vmin;
}

.objCard >>> .ivu-card-body {
    height: 36vmin;
    overflow-y: auto;
}
.objCard >>> .ivu-card-body::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 1vmin; /*高宽分别对应横竖滚动条的尺寸*/
    height: 0.2vmin;
}
.objCard >>> .ivu-card-body::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.objCard >>> .ivu-card-body::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.objWrapper {
    margin: 1vmin;
    padding: 1vmin;
    text-align: center;
    border-radius: 10px;
    font-size: 1.58vmin;
    height: 16vmin;
    color: #fff;
    background: url("../../../../assets/UM/companyBg.png") no-repeat;
    background-size: 100% 100%;
}

.objName {
    height: 4.4vmin;
}
</style>