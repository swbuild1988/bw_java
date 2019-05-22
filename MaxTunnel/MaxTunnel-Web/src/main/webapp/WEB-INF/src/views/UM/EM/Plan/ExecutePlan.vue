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
                    <Row style="line-height: 3vmin;font-size: 1.66vmin;overflow-y: auto;">
                        <div v-for="item in equipmentList" :key="item.name">
                            <Col span="12" style="padding: 10px;font-size: 1.8vmin;">
                                <span>{{item.name}}</span>
                                <div style="float: right;">
                                    <img :src="item.url" style="width: 40px;">
                                </div>
                                <i-Switch
                                    :value="item.cv"
                                    size="large"
                                    @on-change="changeStatus(item.id,item.cv)"
                                    style="float:right;margin-right: 50px;margin-top: 5px;"
                                >
                                    <span slot="open">开</span>
                                    <span slot="close">关</span>
                                </i-Switch>
                            </Col>
                        </div>
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
import fenClose from "../../../../assets/UM/fanClose.png";
import fenOpen from "../../../../assets/UM/fenOpen.gif";
import closeLamp from "../../../../assets/UM/照明-关.png";
import openLamp from "../../../../assets/UM/照明-开.png";
import openBlinds from "../../../../assets/UM/百叶-开.png";
import closeBlinds from "../../../../assets/UM/百叶-关.png";
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";

export default {
    name: "execute-plan",
    data() {
        return {
            curCarousel: {
                videolist: []
            },
            fenOpen: fenOpen,
            fenClose: fenClose,
            openLamp: openLamp,
            closeLamp: closeLamp,
            closeBlinds: closeBlinds,
            openBlinds: openBlinds,
            img: { open: "", close: "" },
            equipmentList: [],
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
            nodePicState: 0
        };
    },

    mounted() {
        this.init();
    },

    computed: {
        startParams() {
            let params = {
                storeId: this.condition.storeId,
                areaId: this.condition.areaId,
                processValue: this.condition.processValue
            };
        }
    },

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

        //获取设备状态图片
        getStatusUrl(dataTypeId, cv) {
            var _this = this;
            var imgUrl = "";
            switch (dataTypeId) {
                // 灯
                case 11: {
                    if (cv > 0) {
                        imgUrl = _this.openLamp;
                    } else {
                        imgUrl = _this.closeLamp;
                    }
                    return imgUrl;
                }
                //风机
                case 10: {
                    if (cv > 0) {
                        imgUrl = _this.fenOpen;
                    } else {
                        imgUrl = _this.fenClose;
                    }
                    return imgUrl;
                }
                //百叶
                case 58: {
                    if (cv > 0) {
                        imgUrl = _this.openBlinds;
                    } else {
                        imgUrl = _this.closeBlinds;
                    }
                    return imgUrl;
                }
            }
        },

        // 变更设备状态
        changeStatus(id, cv) {
            var _this = this;
            var params = { id: id, status: cv };
            _this.equipmentList.forEach(a => {
                if (a.id == id) {
                    a.status = !a.status;
                    a.cv = a.cv == 0 ? 1 : 0;
                    a.url = _this.getStatusUrl(a.objtypeId, a.cv);
                }
            });
            MeasObjServer.changeEquimentStatus(params).then();
        },

        //切换路由
        goToMoudle(path) {
            this.$router.push(path);
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
</style>
