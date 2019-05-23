<template>
    <div>
        <Row style="padding: 1vmin 3vmin">
            <Col span="7">
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
                <Select v-model="condition.areaId" style="width:60%;">
                    <Option
                        v-for="item in selectList.areas"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="7">
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
                <Card :bordered="false" style="height: 77vmin" class="card">
                    <p slot="title" style="font-size: 1.66vmin;">执行日志</p>
                    <div v-if="nodePicState === 2" style="height:69vmin;max-width:54vmin">
                        <image-from-url :url="processPicSrc"></image-from-url>
                    </div>
                    <span
                        v-else
                        style="font-size: 2.8vmin;"
                    >{{nodePicState ? '已开启，图片加载中' : '未开启预案' }}</span>
                </Card>
            </Col>
            <Col span="12" style="padding: 10px;">
                <div style="height: 33vmin" class="coolBox">
                    <Carousel v-bind="curCarousel"></Carousel>
                </div>
            </Col>
            <Col span="12" style="padding: 10px;">
                <Card :bordered="false" style="height: 41.8vmin" class="card">
                    <p slot="title" style="font-size: 1.66vmin;">监测对象列表</p>
                    <Row style="overflow-y: auto">
                        <Col span="8" v-for="obj in objList" :key="obj.id">
                            <div class="objWrapper">
                                <p>{{obj.name + ' ' + obj.id}}</p>
                                <p
                                    v-if="obj.type === 1"
                                    style="font-size: 3vmin;padding: 1.6vmin 0;"
                                >{{obj.cv}}</p>
                                <div style="margin:0 auto;" v-else>
                                    <img
                                        style="width: 7vmin;height:7vmin"
                                        :src="obj.image"
                                        :title="obj.cv == 0 ? '关' : (obj.cv == 1 ? '开' : '故障')"
                                    >
                                </div>
                                <span>时间：{{obj.time}}</span>
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
            timer: null,
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
            this.getMeasObjs();
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
            PlanService.startPlan(this.condition).then(res => {
                this.$Message.success("预案已开启！");
            });
            this.acceptPlanData();
        },
        // 连接成功回调函数
        callback(respond) {
            let result = JSON.parse(respond.body);
            this.processPicSrc = null;
            let _this = this;
            this.timer = setTimeout(() => {
                _this.processPicSrc =
                    "/emplans/png/" + result.processInstanceId;
                _this.nodePicState = 2;
            }, 400);
        },
        //获取MQ推送的预案消息
        acceptPlanData() {
            var _this = this;
            _this.MQ._InitMQ(1, "/queue/QUEUE_PLAN_UM", "", _this.callback);
            this.nodePicState = 1;
        },
        getMeasObjs() {
            MeasObjServer.getMeasObjects(this.condition).then(
                res => {
                    this.objList = res;
                    this.objList.forEach(obj => {
                        if (obj.type === 2) {
                            let image_url =
                                obj.cv == 0
                                    ? "close-state"
                                    : obj.cv == 1
                                    ? "open-state"
                                    : "fault-state";
                            obj.image = require("../../../../assets/VM/" +
                                image_url +
                                ".png");
                        }
                    });
                    console.log(this.objList);
                },
                err => {
                    this.Log.info(err);
                }
            );
        }
    },
    beforeDestroy() {
        clearTimeout(this.timer);
    },
    components: { Carousel, ImageFromUrl }
};
</script>

<style scoped>
.begin {
    padding-left: 1vmin;
    width: 11vmin;
}

.border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
}

.hr3 {
    height: 7px;
    border: none;
    border-top: 5px ridge #45d6d8;
}

.hr1 {
    margin: 0 auto;
    border: 0;
    height: 5px;
    background: #333;
    background-image: linear-gradient(to right, #ccc, #333, #ccc);
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

.card >>> .ivu-card-head p {
    height: 2vmin;
    line-height: 2vmin;
}
.objWrapper {
    margin: 1vmin;
    border: 1px solid #cecfd2;
    padding: 1vmin;
    text-align: center;
    border-radius: 10px;
    font-size: 1.58vmin;
    height: 14vmin;
}
</style>
