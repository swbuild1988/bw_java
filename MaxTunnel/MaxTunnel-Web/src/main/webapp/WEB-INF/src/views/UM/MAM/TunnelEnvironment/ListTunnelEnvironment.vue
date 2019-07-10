<template>
    <div>
        <div style="margin: 1vh;">
            <RadioGroup
                v-model="queryCondition.areaId"
                type="button"
                @on-change="updateArea"
                size="large"
                class="stores"
            >
                <Radio
                    v-for="(item) in areaList"
                    :key="item.id"
                    :label="item.id"
                    style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin;border: 1px solid #266fbd;color:white;"
                    :class="{select_radio:queryCondition.areaId==item.id}"
                >{{item.name}}</Radio>
            </RadioGroup>
        </div>
        <div style="margin: 1vh;">
            <check-select v-bind="storeProp" @toParent="updateStores"></check-select>
        </div>
        <Row>
            <Col span="18" style="padding-left: 10px;padding-right: 10px;margin-top:0.4vmin;">
                <div class="gis" style="height: 75.2vh">
                    <test-sm-viewer ref="smViewer" @sendSectionDetails="getSectionDetails"></test-sm-viewer>
                    <div
                        class="positionNote"
                        v-if="note.areaName && note.storeName"
                    >{{note.areaName + " " + note.storeName}}</div>
                    <ShowSectionDetailData
                        :dataDetails="sectionDetailsData"
                    ></ShowSectionDetailData>
                </div>
            </Col>
            <Col span="6" style="padding-left: 10px;padding-right: 10px;">
                <div class="overview-context">
                    <div class="monitor-tunnel-overview">
                        <div style="margin: 2vmin 10px;">
                            <span class="monitor-tunnel-title">{{curName}}</span>
                            <Row
                                :gutter="16"
                                style="margin-left: 0; margin-right:0;  padding: 0 2vmin;"
                            >
                                <Col span="24" class="descCol">
                                    <span>区段数：</span>
                                    <div style="float: right">{{curTunnel.areaNum}}</div>
                                </Col>
                                <Col span="24" class="descCol">
                                    <span>监测仓个数：</span>
                                    <div style="float: right">{{curTunnel.storeNum}}</div>
                                </Col>
                                <Col span="24" class="descCol">
                                    <span>建设单位：</span>
                                    <div style="float: right">{{curTunnel.constructionUnit}}</div>
                                </Col>
                                <Col span="24" class="descCol">
                                    <span>运营单位：</span>
                                    <div style="float: right">{{curTunnel.operationUnit}}</div>
                                </Col>
                            </Row>
                        </div>
                    </div>
                    <div
                        class="borde_rhadow monitor-tunnel-number"
                        style="height:28vh; overflow-y: auto;margin-top: 1vh;color: #f9f8f6"
                    >
                        <div style="margin: 2vmin 10px;">
                            <span class="monitor-tunnel-title">
                                极值数据统计
                                <Icon type="arrow-graph-up-right" :siez="iconSize*1.5"></Icon>
                            </span>
                            <Row style="margin-left: 2vmin">
                                <div v-for="(item,index) in tunnelProps" :key="index">
                                    <Col span="6" class="MaxValCol">
                                        <Icon type="clipboard" :size="iconSize"></Icon>
                                        {{item.key}}
                                    </Col>
                                    <Col span="9" class="MaxValCol">
                                        <div
                                            @click="goToDetails(item.key, item.areaId, item.storeId)"
                                        >
                                            <Icon type="ios-pulse" :size="iconSize"></Icon>
                                            <!-- <button> -->
                                            {{item.val}}{{item.unit}}
                                            <!-- </button> -->
                                        </div>
                                    </Col>
                                    <Col span="9" class="MaxValCol" color="#de8d1b">
                                        <Icon type="android-locate" :size="iconSize"></Icon>
                                        {{item.location}}
                                    </Col>
                                </div>
                            </Row>
                        </div>
                    </div>
                    <div
                        class="monitor-tunnel-number"
                        style="height: 23vh;overflow-y: auto;margin-top: 1vh;color: rgb(249, 248, 246);"
                    >
                        <div style="margin: 2vmin 10px">
                            <span class="monitor-tunnel-title">温湿度显示</span>
                            <Row style="margin-left: 0; margin-right:0;  padding: 0 2vmin;">
                                <Col
                                    span="24"
                                    v-for="item in tempHumData"
                                    :key="item.id"
                                    class="descCol"
                                >
                                    <span>{{item.name + '：'}}</span>
                                    <div style="float: right">{{item.curValue + ' ' + item.unit}}</div>
                                </Col>
                            </Row>
                        </div>
                    </div>
                </div>
            </Col>
        </Row>
    </div>
</template>

<script>
import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
import { MonitorDataService } from "../../../../services/monitorDataService";
import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
import checkSelect from "../../../../components/Common/CheckSelect.vue";
import ShowSectionDetailData from "../../../../components/Common/Modal/ShowSectionDetailData.vue";
import { commonFlyFn } from "../Minxis/unit";

export default {
    name: "tunnel-list-tunnel-environment",
    mixins: [commonFlyFn],
    data() {
        return {
            curHeight: 450,
            iconSize: 12,
            scene: null,
            modelProp: {
                show: {
                    //默认隐藏
                    state: false
                },
                showButton: false, //关闭底部按钮
                tilte: "详情", //对话框标题
                data: [
                    {
                        key: "氧气浓度",
                        val: "12.45%"
                    },
                    {
                        key: "硫化氢",
                        val: "45%"
                    },
                    {
                        key: "一氧化碳",
                        val: "34.2%"
                    },
                    {
                        key: "湿度",
                        val: "30%"
                    }
                ] //属性集
            },
            tunnelProps: [], //管廊统计数据
            tempHumData: []
        };
    },
    mounted() {
        this.fentchData();
        this.getTempHum();
        // this.getMonitorData();
        // 设置表格高度
        this.curHeight = window.innerHeight * 0.75; //将85vh转为数值
        this.iconSize = window.innerHeight * 0.02;
        //3D加载
        // this.setGIS();
    },
    methods: {
        //根据监测类型获取数据
        getMonitorData() {
            let { queryCondition } = this;
            let areaId = !queryCondition.areaId ? null : queryCondition.areaId;
            !queryCondition.storeId && (queryCondition.storeId = null);
            let parms = {
                tunnelId: queryCondition.tunnelId,
                storeId: queryCondition.storeId,
                areaId: areaId
            };

            MonitorDataService.getMaxMonitorData(parms).then(result => {
                this.tunnelProps = result;
            });
        },
        getTempHum() {
            MonitorDataService.getTempHum().then(
                res => {
                    this.tempHumData = res;
                },
                err => {
                    this.Log.info(err);
                }
            );
        },
        goToDetails(key, areaId, storeId) {
            this.$router.push({
                path: "/UM/TunnelEnvironment/details/" + this.$route.params.id,
                query: {
                    objtypeKey: key,
                    areaId: areaId,
                    storeId: storeId,
                    tunnelId: this.$route.params.id
                }
            });
        }
        // getSectionDetails(data) {
        //     // this.sectionDetailsData.splice(0);//清空数组
        //     if (data) {
        //         this.showDetailsModel = true;
        //         this.sectionDetailsData = data.moInfo;
        //     }else {
        //         this.showDetailsModel = false;
        //     }
        // }
    },
    components: {
        Modal,
        EnvironmentShow,
        TestSmViewer,
        checkSelect,
        ShowSectionDetailData
    },
    watch: {
        $route: function() {
            // $route发生变化时再次赋值
            this.queryCondition.tunnelId = this.tunnelId;
            this.fentchData();
            this.getTempHum();
            this.getMonitorData();
        },
        storeProp: {
            handler: function(newVal, oldVal) {
                this.updateStores(newVal.selectObj.selectId);
            },
            deep: true
        }
    }
};
</script>

<style scoped>
@import "../CommonCss/ComStyle.css";

.ivu-select-single .ivu-select-selection {
    height: 100px;
}

.MaxValCol {
    margin-top: 1.5vh;
    font-size: 1.5vmin;
}

.descCol {
    margin-top: 1vh;
    font-size: 1.66vmin;
}

.MaxValCol:hover,
.descCol:hover {
    background-color: #44c3ee;
    cursor: pointer;
}

.borde_rhadow {
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    border-color: #eee;
}

.ivu-modal-wrap > .ivu-modal {
    left: 500px;
    top: 500px;
}

.ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
    background: #adb3e2;
    color: #fff;
}

.details {
    display: inline-block;
    border: 1px solid #b3b0b0;
    vertical-align: top;
    width: 100%;
}

.Section #pageBox {
    border: 1px solid #b3b0b0;
    bottom: 0px;
    width: 83.5vw;
    left: 0px;
    text-align: center;
    overflow-x: hidden;
}

.tunnelsInfo,
.environmentalMonitoring,
.theFireWarning,
.monitoringSituation {
    margin: 0.5vw;
    border: 1px solid #b3b0b0;
    border-radius: 8px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    border-color: #828991;
}

.tunnelsInfo div,
.environmentalMonitoring div,
.theFireWarning div,
.monitoringSituation div {
    line-height: 5vh;
    font-size: 14px;
}

.overview-context {
    /* margin-top: -1vmin; */
}

.monitor-tunnel-number {
    background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
    background-size: 100% 100%;
}

.monitor-tunnel-overview {
    height: 23vh;
    overflow-y: auto;
    color: #f9f8f6;
    background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
    background-size: 100% 100%;
}

.inline-box div {
    display: inline-block;
}
</style>