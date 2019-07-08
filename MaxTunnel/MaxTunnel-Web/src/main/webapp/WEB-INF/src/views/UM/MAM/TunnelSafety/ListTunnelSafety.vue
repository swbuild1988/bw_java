<template>
    <div style="position:relative">
        <div class="delay">{{ '入侵延时关闭时间：' + delayTime + 's' }}</div>
        <div style="margin: 1vh;">
            <RadioGroup
                v-model="queryCondition.areaId"
                type="button"
                @on-change="updateArea"
                size="large"
            >
                <Radio
                    v-for="(item,key) in areaList"
                    :key="key"
                    :label="item.id"
                    style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin;margin-top: 0.4vmin;"
                    :class="{select_radio:queryCondition.areaId==item.id}"
                >{{item.name}}</Radio>
            </RadioGroup>
        </div>
        <div style="margin: 1vh;">
            <check-select v-bind="storeProp" @toParent="updateStores"></check-select>
        </div>
        <Row>
            <Col span="18" style="padding-left: 10px;padding-right: 10px">
                <div class="gis" :style="{height:curHeight+'px'}">
                    <!-- <sm-viewer :id="mapId" ref="smViewer">
                    </sm-viewer>-->
                    <TestSmViewer
                        ref="smViewer"
                        :openImageryProvider="false"
                        @sendSectionDetails="getSectionDetails"
                    ></TestSmViewer>
                    <div
                        class="positionNote"
                        v-if="note.areaName && note.storeName"
                    >{{note.areaName + " " + note.storeName}}</div>
                    <ShowSectionDetailData
                        :showDetailsModel="showDetailsModel"
                        :dataDetails="sectionDetailsData"
                    ></ShowSectionDetailData>
                </div>
            </Col>
            <Col span="6" style="padding-left: 10px;padding-right: 10px;">
                <div :style="{height:curHeight+'px'}">
                    <div class="monitor-tunnel-overview">
                        <div style="margin: 3vmin 10px;">
                            <span class="monitor-tunnel-title">{{curName}}</span>
                            <Row :gutter="16" style="padding: 0px 2vmin;">
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
                    <div class="monitor-tunnel-overview" style="height:43vh;">
                        <div style="margin:  3vmin 10px;">
                            <div class="monitor-data">
                                <h2 class="monitor-tunnel-title">安防统计</h2>
                                <div>
                                    <Row style="color: #fff">
                                        <Col
                                            span="22"
                                            style="margin-top: 1vh;margin-bottom: 1vh; margin-left: 1vw;font-size:1.5vmin"
                                            v-for="(item,index) in tunnelProps"
                                            :key="index"
                                        >
                                            <span
                                                style="width:100%;display:inline-block;text-align:center"
                                            >{{item.name}}</span>
                                            <div style>
                                                <Row>
                                                    <Col
                                                        span="10"
                                                        style="margin-top: 14px;"
                                                        offset="2"
                                                        v-for="(item2,index) in item.data"
                                                        :key="index"
                                                    >
                                                        <img
                                                            src="../../../../assets/UM/status-open.png"
                                                            class="open-status-img"
                                                            v-if="item2.key ==('开' || '正常')"
                                                        >
                                                        <img
                                                            src="../../../../assets/UM/status-close.png"
                                                            class="open-status-img"
                                                            v-if="item2.key ==('关' || '入侵')"
                                                        >
                                                        <img
                                                            src="../../../../assets/UM/status-alarm.png"
                                                            class="open-status-img"
                                                            v-if="item2.key =='告警'"
                                                        >
                                                        {{item2.key}}：{{item2.val}}
                                                    </Col>
                                                </Row>
                                            </div>
                                        </Col>
                                    </Row>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="options">
                        <Button
                            type="primary"
                            size="small"
                            class="defence"
                            @click="handleDefence"
                        >布防</Button>
                        <Button
                            type="primary"
                            size="small"
                            class="linkage"
                            @click="handleLinkage"
                        >联动</Button>
                    </div>
                </div>
            </Col>
        </Row>
        <show-defence v-bind="defence" @saveDefence="saveDefence"></show-defence>
        <show-linkage v-bind="linkage" @saveLinkage="saveLinkage"></show-linkage>
    </div>
</template>

<script>
import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
import ShowDefence from "../../../../components/Common/Modal/ShowDefence";
import ShowLinkage from "../../../../components/Common/Modal/ShowLinkage";
import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
import { EnumsService } from "../../../../services/enumsService";
import { MonitorDataService } from "../../../../services/monitorDataService";
import { SuperMapSqlQuery, lookAt } from "../../../../scripts/three.js";
import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
import checkSelect from "../../../../components/Common/CheckSelect.vue";
import ShowSectionDetailData from "../../../../components/Common/Modal/ShowSectionDetailData.vue";
import { commonFlyFn } from "../Minxis/unit";

export default {
    name: "list-tunnel-safety",
    mixins: [commonFlyFn],
    data() {
        return {
            curHeight: 450,
            iconSize: 16,
            scene: null,
            modelProp: {
                show: {
                    //默认隐藏
                    state: false
                },
                showButton: false, //关闭底部按钮
                tilte: "详情", //对话框标题
                data: [
                    { key: "氧气浓度", val: "12.45%" },
                    { key: "硫化氢", val: "45%" },
                    { key: "一氧化碳", val: "34.2%" },
                    { key: "湿度", val: "30%" }
                ] //属性集
            },
            tunnelProps: [], //管廊统计数据
            defence: {
                show: { state: false },
                tunnelName: null,
                title: "布防/撤防",
                data: []
            },
            linkage: {
                show: { state: false },
                tunnelName: null,
                data: []
            },
            delayTime: 4
        };
    },
    mounted() {
        this.fentchData();
        this.getDelayCloseTime();
        // this.getMonitorData();
        // 设置表格高度
        this.curHeight = window.innerHeight * 0.76; //将85vh转为数值
        this.iconSize = window.innerHeight * 0.02;
        //3D加载
    },
    components: {
        SimulatedData,
        showSwitchData,
        ShowLinkage,
        Modal,
        ShowDefence,
        EnvironmentShow,
        // SmViewer
        TestSmViewer,
        checkSelect,
        ShowSectionDetailData
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
                areaId: areaId,
                monitorType: 3
            };

            MonitorDataService.getMeasStatusCounts(parms).then(result => {
                this.tunnelProps = result;
            });
        },
        getDelayCloseTime() {
            MonitorDataService.getDelayCloseTime().then(
                res => (this.delayTime = res),
                err => this.Log.info(err)
            );
        },
        handleDefence() {
            MonitorDataService.getDefenceSetting(
                this.queryCondition.tunnelId
            ).then(
                res => {
                    this.defence.data = res.area;
                    this.defence.tunnelName = res.tunnelName;
                    this.defence.show.state = true;
                },
                err => {
                    this.$Message.error("获取信息失败，请稍后再试");
                }
            );
        },
        handleLinkage() {
            MonitorDataService.getLinkageSetting(
                this.queryCondition.tunnelId
            ).then(
                res => {
                    this.linkage.data = res.area;
                    this.linkage.tunnelName = res.tunnelName;
                    this.linkage.show.state = true;
                },
                err => {
                    this.$Message.error("获取信息失败，请稍后再试");
                }
            );
        },
        saveDefence(data) {
            MonitorDataService.batchControl(data).then(res => {
                this.$Message.success("布防设置成功");
                this.defence.show.state = false;
            });
        },
        saveLinkage(data) {
            MonitorDataService.batchControl(data).then(res => {
                this.$Message.success("联动设置成功");
                this.linkage.show.state = false;
            });
        }
    },
    watch: {
        $route() {
            // $route发生变化时再次赋值
            this.queryCondition.tunnelId = this.tunnelId;
            this.fentchData();
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
.delay {
    position: absolute;
    top: 2vmin;
    right: 8%;
    color: #fff;
    font-size: 1.6vmin;
}
.showSection ul li {
    display: inline-block;
    border-right: 1px solid #b3b0b0;
    line-height: 35px;
    text-align: center;
}
.showSection ul li {
    width: 100px;
}
.ivu-select-single .ivu-select-selection {
    height: 100px;
}
.MaxValCol {
    margin-top: 4.5vh;
    font-size: 1.66vmin;
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
.select_radio {
    color: #fff;
    background-color: #869bcb;
    background-position: 0 -15px;
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

.inline-box div {
    display: inline-block;
}
.monitor-tunnel-number {
    background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
    background-size: 100% 100%;
}
.monitor-tunnel-overview {
    height: calc(30vh - 10px);
    overflow-y: auto;
    color: #f9f8f6;
    background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
    background-size: 100% 100%;
}
.monitor-data {
    height: 37vmin;
    overflow-y: auto;
}
.monitor-data::-webkit-scrollbar {
    width: 1vmin;
    height: 0.2vmin;
}
.monitor-data::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.monitor-data::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.options {
    position: relative;
    height: 4vmin;
}
.defence {
    position: absolute;
    bottom: 0vmin;
    left: 2vmin;
}
.linkage {
    position: absolute;
    bottom: 0vmin;
    right: 2vmin;
}
.defence,
.linkage {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.6vmin;
}
.ivu-col-span-10 {
    display: block;
    width: 33%;
    text-align: center;
}
.open-status-img,
.close-status-img {
    width: 2.1vmin;
    float: left;
}
.ivu-col-offset-2 {
    margin-left: 0;
}
</style>
