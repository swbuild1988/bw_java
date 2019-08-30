<template>
    <div style="padding-left: 10px;">
        <h1 style="line-height: 4vh; color: #fff; font-size: 1.6vmin">设备管理总览</h1>
        <Row>
            <Col span="18">
                <Col span="6">
                    <data-box v-bind="equimentTotalNum" ref="dataBox"></data-box>
                </Col>
                <Col span="6">
                    <data-box v-bind="equimentRunNum"></data-box>
                </Col>
                <Col span="6">
                    <data-box v-bind="spareAFew"></data-box>
                </Col>
                <Col span="6">
                    <data-box v-bind="duration"></data-box>
                </Col>
                <Col span="24" style="margin-top:1vh">
                    <div class="GISbox" ref="gisBox">
                        <simple3-d-viewer ref="smViewer" :openImageryProvider="false"></simple3-d-viewer>
                    </div>
                </Col>
            </Col>
            <Col span="6" class="chartBox">
                <Col span="24" style="height: 49%;">
                    <div style="width: 21.4vw; height:100%;" class="boxBG">
                        <mono-bar v-bind="equipmentChart"></mono-bar>
                    </div>
                </Col>
                <Col span="24" style="height: 49%;">
                    <div style="width: 21.4vw; height:100%;" class="boxBG">
                        <simple-pie v-bind="equipmentChartDoubleColor"></simple-pie>
                    </div>
                </Col>
            </Col>
        </Row>
        <Row style="margin-top:1vh;" :gutter="16">
            <Col span="5">
                <div class="circleBox boxBG">
                    <process-ring v-bind="spare">
                        <p>{{spare.explain}}</p>
                        <p>{{spare.percent}}%</p>
                    </process-ring>
                </div>
            </Col>
            <Col span="5">
                <div class="circleBox boxBG">
                    <process-ring v-bind="breakDown">
                        <p>{{breakDown.explain}}</p>
                        <p>{{breakDown.percent}}%</p>
                    </process-ring>
                </div>
            </Col>
            <Col span="7">
                <div class="card">
                    <p class="title">备品备件明细</p>
                    <div class="details">
                        <Row :gutter="16">
                            <Col span="12" style="line-height: 4vh;">
                                出库至管廊设备数：
                                <span>{{spareDetails.equipment}}</span>
                            </Col>
                            <Col span="12" style="line-height: 4vh;">
                                出库至仪表工具数：
                                <span>{{spareDetails.instrument}}</span>
                            </Col>
                            <Col span="24" style="line-height: 4vh;">
                                备品备件当前在库数：
                                <span>{{spareDetails.in}}</span>
                            </Col>
                        </Row>
                    </div>
                </div>
            </Col>
            <Col span="7">
                <div class="card">
                    <p class="title">最新一条故障信息</p>
                    <div class="details">
                        <p @click="enterOrder(breakData.id)">
                            缺陷名称：
                            <span style="cursor: pointer;">{{breakData.name}}</span>
                        </p>
                        <p>发现时间：{{breakData.createTime}}</p>
                        <p>危险等级：{{breakData.levelName}}</p>
                    </div>
                </div>
            </Col>
        </Row>
    </div>
</template>

<script>
import DataBox from "../../../../components/Common/Box/DataBox";
import MonoBar from "../../../../components/Common/Chart/MonoBarChart";
import SimplePie from "../../../../components/Common/Chart/SimplePieChart";
import Simple3DViewer from "../../../../components/Common/3D/simple3DViewer";
import ProcessRing from "../../../../components/Common/ProcessRing";
import equimentTotalNum from "../../../../assets/UM/equimentTotalNum.png";
import instrumentTool from "../../../../assets/UM/instrumentTool.png";
import faultyEquipment from "../../../../assets/UM/faultyEquipment.png";
import spareEquipment from "../../../../assets/UM/spareEquipment.png";
import { EquipmentService } from "../../../../services/equipmentService";
import Enum from "../../../../../static/Enum.json";

export default {
    name: "equipmentMain",
    data() {
        return {
            id: "GIS_ID",
            breakData: {
                name: null,
                levelName: null,
                createTime: null,
                id: null
            },
            equimentTotalNum: {
                label: "管廊设备总计",
                value: 10,
                imgSrc: equimentTotalNum,
                boxBGLeft: "#b195ed",
                boxBGRight: "#2734e1"
            },
            equimentRunNum: {
                label: "备品设备总计",
                value: 87,
                imgSrc: spareEquipment,
                imgWidth: "90",
                boxBGLeft: "#f68380",
                boxBGRight: "#f61a1a"
            },
            spareAFew: {
                label: "仪表工具总计",
                value: "500",
                imgSrc: instrumentTool,
                boxBGLeft: "#a7ecd7",
                boxBGRight: "#1af6b0"
            },
            duration: {
                label: "故障设备总计",
                value: "158",
                imgSrc: faultyEquipment,
                imgWidth: "90",
                boxBGLeft: "#e4e884",
                boxBGRight: "#eef61a"
            },
            equipmentChart: {
                id: "equipmentChartId",
                xData: [],
                yData: [],
                title: "设备类别",
                parameters: {
                    option: {}
                },
                barColor: "#d8246e",
                splitLineColor: "#2734e1",
                barColor: "#d8246e"
            },
            equipmentChartDoubleColor: {
                id: "equipmentCharDoubleColortId",
                requestUrl: "/equipments/instruments/broken-counts/types",
                parameters: {
                    option: {
                        title: {
                            text: "设备故障统计",
                            x: "left",
                            textStyle: {
                                color: "#fff"
                            }
                        }
                    }
                },
                seriesColor: [
                    "#c23531",
                    "#e5c52f",
                    "#6bade1",
                    "#6fe46c",
                    "#e06ce4",
                    "#e48e6c"
                ],
                legendColor: "#fff"
            },
            // equimentPie: {
            //     id: "equipmentPieId",
            //     requestUrl: "/equipments/type/count",
            //     parameters: {
            //         option: {
            //             title: {
            //                 text: "各类型故障占比",
            //                 textStyle: {
            //                     color: "#2e739b"
            //                 }
            //             }
            //         }
            //     }
            // },
            spare: {
                id: "spare",
                explain: "管廊设备占比",
                percent: 85,
                strokeWidth: 8,
                radius: window.innerHeight * 0.07,
                startColor: "#9ab8ff",
                middleColor: "#6b95f5",
                endColor: "#2561eb"
            },
            breakDown: {
                id: "breakDown",
                explain: "仪表工具占比",
                percent: 40,
                strokeWidth: 8,
                radius: window.innerHeight * 0.07,
                startColor: "#f8d78d",
                middleColor: "#f3cd4b",
                endColor: "#fed608"
            },
            tunnelInfoList: [],
            timer: {
                timeoutId: null,
                intervalId: null,
                sectionId: null //保留上次section
            },
            //备品备件明细
            spareDetails: {
                in: 123,
                equipment: 56,
                instrument: 85
            }
        };
    },
    components: {
        DataBox,
        MonoBar,
        SimplePie,
        ProcessRing,
        Simple3DViewer
    },
    mounted() {
        this.getEquipmentType();
        this.getEquipmentCount();
        this.getOutCount();
        this.getEquTypeCounts();
        this.getToolsPrecent();
        this.getSparePrecent();
        this.getBroken();
        this.getDefectEquipment();
    },
    methods: {
        //获取管廊设备类别信息
        getEquipmentType() {
            let _this = this;
            EquipmentService.getEquTypeAndCount().then(
                result => {
                    _this.tunnelInfoList = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        //跳转缺陷详情
        goToMoudle2: function(id, type) {
            this.$router.push({
                name: "缺陷详情",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        //三个出库统计
        getEquipmentCount() {
            EquipmentService.getEquipmentCount().then(
                result => {
                    this.spareDetails.in = result.in;
                    this.spareDetails.equipment = result.equipment;
                    this.spareDetails.instrument = result.instrument;
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //三个总计
        getOutCount() {
            EquipmentService.getOutCount().then(
                result => {
                    this.equimentTotalNum.value = result.equipment;
                    this.equimentRunNum.value = result.spares;
                    this.spareAFew.value = result.instrument;
                },
                error => {
                    this.Log.info(error)
                }
            )

        },
        getEquTypeCounts() {
            EquipmentService.getEquTypeCounts().then(res => {
                let newData = res.reduce((init, item) => {
                    return {
                        xData:
                            init.xData == undefined
                                ? [].concat(item.key)
                                : [].concat(init.xData, item.key),
                        yData:
                            init.yData == undefined
                                ? [].concat(item.val)
                                : [].concat(init.yData, item.val)
                    };
                }, {});
                this.equipmentChart.xData = newData.xData;
                this.equipmentChart.yData = newData.yData;
            });
        },
        //仪表工具书占比
        getToolsPrecent() {
            EquipmentService.getToolsPrecent().then(
                result => {
                    this.breakDown.percent = (result * 100).toFixed(2);
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //管廊设备数占比
        getSparePrecent() {
            EquipmentService.getSparePrecent().then(
                result => {
                    this.spare.percent = (result * 100).toFixed(2);
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //设备故障总数
        getBroken() {
            EquipmentService.getBroken().then(
                result => {
                    this.duration.value = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //最新一条设备故障信息
        getDefectEquipment() {
            EquipmentService.getDefectEquipment().then(
                result => {
                    for (let i = 0; i < result.length; i++) {
                        this.breakData.name = result[0].name;
                        this.breakData.levelName = result[0].levelName;
                        this.breakData.createTime = new Date( result[0].createTime ).format("yyyy-MM-dd hh:mm:s");
                        this.breakData.id = result[0].id;
                    }
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        enterOrder(index) {
            this.goToMoudle2(index, 1);
        }
    }
};
</script>
<style scoped>
.ivu-card > .ivu-card-head p {
    color: #2e739b;
}

.tunnelProfile {
    border: 1px solid #dddee1;
    border-radius: 4px;
    position: absolute;
    bottom: 2px;
    right: 13px;
    text-align: left;
    z-index: 2;
    color: #fff;
}

.tunnelProfile div {
    border: 1px solid #dddee1;
    border-bottom: none;
    line-height: 35px;
    text-align: center;
    padding-left: 10px;
    padding-right: 10px;
}

.chartBox {
    height: 100%;
    position: absolute;
    right: 0px;
    margin-right: 10px;
}

.chartBox .ivu-col-span-24 {
    margin-bottom: 10px;
}

.GISbox {
    width: 99%;
    height: 44vh;
    margin-top: 1vh;
    background: url("../../../../assets/UM/boxBGBig.png") no-repeat;
    background-size: 100% 100%;
    padding: 0.9vmin 1.6vmin;
}

ul {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding-left: 5px;
    height: 80px;
    overflow: auto;
}

ul li {
    list-style: none;
    line-height: 20px;
}

.ivu-col-span-12 {
    padding: 10px;
}

.circleBox {
    text-align: center;
    height: 21vh;
    width: 100%;
    display: inline-block;
    vertical-align: middle;
}

.ivu-card >>> .ivu-card-body {
    height: 16vh;
    overflow-y: auto;
}

.boxBG {
    background: url("../../../../assets/UM/chartBG.png") no-repeat;
    background-size: 100% 100%;
}

.card {
    background: url("../../../../assets/UM/cardBG.png") no-repeat;
    background-size: 100% 100%;
}

.card .title {
    color: #0efcff;
    line-height: 5.5vh;
    padding-left: 1vw;
}

.card .details {
    border-top: 2px solid #1b6ca3;
    padding: 0 1vw;
    overflow-y: auto;
    height: 15.5vh;
    color: #fff;
}

.card .details p {
    line-height: 4.5vh;
}

@media (min-width: 2200px) {
    .ivu-col-span-7 >>> .ivu-card,
    .ivu-card >>> .ivu-card-head p {
        font-size: 1.4vmin !important;
    }

    .ivu-card >>> .ivu-card-head p {
        height: 2vmin;
        line-height: 2vmin;
    }

    h1 {
        font-size: 2.8vmin;
    }

    .card .title {
        font-size: 1.8vmin;
    }

    .card .details {
        font-size: 1.6vmin;
    }
}
</style>