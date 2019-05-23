<template>
    <div>
        <div style="margin: 1vh;">
            <RadioGroup
                v-model="queryCondition.areaId"
                type="button"
                @on-change="updateArea"
                size="large"
            >
                <Radio
                    v-for="(item) in areaList"
                    :key="item.id"
                    :label="item.id"
                    style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
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
                <test-sm-viewer ref="smViewer"></test-sm-viewer>
            </div>
            </Col>
            <Col span="6" style="padding-left: 10px;padding-right: 10px;">
            <div :style="{height:curHeight+'px'}">
                <div class="borde_rhadow" style="height: calc(30vh - 10px);overflow-y: auto;background-color: #5c7b8e;color: #f9f8f6">
                    <div style="margin: 10px;">
                        <span style="font-size: 2.5vmin">{{curName}}</span>
                        <Row :gutter="16" style="padding: 2px;">
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
                <div class="borde_rhadow" style="height:46vh; overflow-y: auto;background-color: #5c7b8e;margin-top: 10px;color: #f9f8f6">
                    <div style="margin: 10px;">
                        <span style="font-size: 2.5vmin;">极值数据统计
                            <Icon type="arrow-graph-up-right" :siez="iconSize*1.5"></Icon>
                        </span>
                        <Row :gutter="16">
                            <div v-for="(item,index) in tunnelProps" :key="index">
                                <Col span="6" class="MaxValCol">
                                    <Icon type="clipboard" :size="iconSize"></Icon>
                                    {{item.key}}
                                </Col>
                                <Col span="9" class="MaxValCol" v-if="item.key != '手报'">
                                    <div  @click="goToDetails(item.key, item.areaId, item.storeId)">
                                    <Icon type="ios-pulse" :size="iconSize"></Icon>
                                    <!-- <button> -->
                                    {{item.val}}{{item.unit}}
                                    <!-- </button> -->
                                    </div>
                                </Col>
                                <Col span="9" class="MaxValCol" v-else>
                                开 ：{{item.open}}
                                </Col>
                                <Col span="9" class="MaxValCol" color="#de8d1b" v-if="item.key != '手报'">
                                    <Icon type="android-locate" :size="iconSize"></Icon>
                                    {{item.location}}
                                </Col>
                                 <Col span="9" class="MaxValCol" v-else>
                                 关 : {{item.close}}
                                </Col>
                            </div>
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
    import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
    import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
    import {MonitorDataService} from "../../../../services/monitorDataService";
    import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
    import checkSelect from "../../../../components/Common/CheckSelect.vue";
    import { commonFlyFn } from '../Minxis/unit'
    import { flyToMyLocation } from '../../../../scripts/commonFun'
    import Vue from 'vue'

    export default {
        name: "tunnel-list-tunnel-environment",
        mixins:[commonFlyFn],
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
                    data: [{
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
            };
        },
        mounted() {
            this.fentchData();
            this.getMonitorData();
            // 设置表格高度
            this.curHeight = window.innerHeight * 0.76; //将85vh转为数值
            this.iconSize = window.innerHeight * 0.02;
            //3D加载
            // this.setGIS();
        },
        methods: {
            //根据监测类型获取数据
            getMonitorData() {
                
            let { queryCondition } = this;
            !queryCondition.areaId && (queryCondition.areaId = queryCondition.storeId = null)
            let parms = {
                tunnelId: queryCondition.tunnelId,
                storeId: queryCondition.storeId,
                areaId:  queryCondition.areaId
            };

            MonitorDataService.getMeasFireCounts(parms).then(
                result => {
                    this.tunnelProps = result
            });
            },
            goToDetails(key, areaId, storeId){
                this.$router.push({
                    path:"/UM/TunnelEnvironment/details/"+this.$route.params.id,
                    query: {
                        objtypeKey: key,
                        areaId: areaId,
                        storeId: storeId,
                        tunnelId: this.$route.params.id,
                    }
                });
            }
        },
        components: {
            SimulatedData,
            showSwitchData,
            Modal,
            EnvironmentShow,
            TestSmViewer,
            checkSelect
        },
        watch: {
            $route: function () {
                // $route发生变化时再次赋值
                this.queryCondition.tunnelId = this.tunnelId;
                this.fentchData();
                this.getMonitorData();
            },
            storeProp: {
                handler: function (newVal, oldVal) {
                    this.updateStores(newVal.selectObj.selectId)
                },
                deep: true
            }
        }
    };
</script>

<style scoped>
    .ivu-select-single .ivu-select-selection {
        height: 100px;
    }

    .MaxValCol {
        margin-top: 4.5vh;
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
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
        border-color: #eee;
    }

    .select_radio {
        color: #fff;
        background-color: #869bcb;
        background-position: 0 -15px;
    }

    .ivu-modal-wrap>.ivu-modal {
        left: 500px;
        top: 500px;
    }

    .ivu-tabs.ivu-tabs-card>.ivu-tabs-bar .ivu-tabs-tab {
        background: #adb3e2;
        color: #fff;
    }

    .gis,
    .details {
        display: inline-block;
        border: 1px solid #b3b0b0;
        vertical-align: top;
        width: 100%;
    }

    .gis {
        position: relative;
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
</style>