<template>
    <div class="main">
        <div class="commonTitle">
            <module-title :title="title"></module-title>
        </div>

        <div class="leftBox">
            <div class="tunnelMessage">
                <div class="startTimeText">
                    <span style="font-weight:bold">自{{runMessage.startTime}}起</span>
                </div>
                <div class="safeRunText">
                    <span style="font-weight: bold">管廊已安全运营</span>
                    <span class="safe">{{runMessage.safe}}</span>
                    <span style="font-weight: bold">天</span>
                </div>
            </div>

            <div class="pieChartClass">
                <hollow-pie-chart v-bind="pieChart" style="height: 12vh;"></hollow-pie-chart>
            </div>

            <div class="tongjiClass">
                <div class="statusText" :key="index" v-for="(item,index) in statusList">
                    <span style>{{item.name}}</span>
                    <span style="margin-left: 5px;" :class="index>0 ? (index>1?'style_3':'style_2'): 'style_1'  ">
                        {{item.value}}</span>
                </div>
            </div>
        </div>

        <div class="rightBox">
            <MultiBarChartRotate v-bind="barChart"></MultiBarChartRotate>
        </div>
    </div>
</template>

<script>
    import Grid from "../../../components/VM2/Grid1";
    import ModuleTitle from "../../../components/VM2/ModuleTitle";
    import {
        TunnelService
    } from "../../../services/tunnelService";
    import HollowPieChart from "../../../components/Common/Chart/HollowPieChart.vue";
    import MultiBarChartRotate from "../../../components/Common/chart/MultiBarChartRotate";

    export default {
        data() {
            return {
                title: "",
                statusList: [],
                pieChart: {
                    id: "hollow-pie-chart",
                    unit: "条",
                    series: [],
                    parameters: {
                        option: {
                            title: {
                                text: "管廊总数"
                            },
                            graphic: {
                                style: {
                                    text: "10"
                                }
                            }
                        }
                    }
                },
                barChart: {
                    id: "progress-bar-chart",
                    unit: "km",
                    parameters: {
                        option: {
                            title: {
                                text: "入廊管线信息"
                            }
                        }
                    },
                    data: []
                },
                runMessage: {
                    total: 50,
                    safe: 20
                },
                refresh: {
                    refreshTime: 60000,
                    tunnelMessageFlag: true,
                    runMessageFlag: true,
                    cableMessageFlag: true
                }
            };
        },
        components: {
            Grid,
            ModuleTitle,
            HollowPieChart,
            MultiBarChartRotate
        },
        mounted() {
            this.getTunnelMessage();
            this.getRunMessage();
            this.getCableMessage();
        },
        beforeDestroy() {
            this.refresh.tunnelMessage = false;
            this.refresh.runMessage = false;
            this.refresh.cableMessageFlag = false;
        },
        methods: {
            getTunnelMessage() {
                this.title = "基本信息";
                TunnelService.getVmTunnelsMessage()
                    .then(
                        result => {
                            this.statusList = [];
                            let tempData = result.slice(0, 3);
                            this.pieChart.series = []
                            let total = 0
                            tempData.forEach(tunnel => {
                                let temp = [];
                                temp.name = tunnel.name;
                                temp.value =
                                    Math.round(tunnel.percent.replace("%", "")) +
                                    "%";
                                this.statusList.push(temp);
                                this.pieChart.series.push({
                                    value: tunnel.value,
                                    name: tunnel.name
                                });
                                total += tunnel.value
                            })
                            this.pieChart.parameters.option.graphic.style.text = total + ""
                            this.Log.info("statusList", this.statusList, total)
                        },
                        error => {
                            this.Log.info(error);
                        }
                    )
                    .finally(() => {
                        if (this.refresh.tunnelMessageFlag) {
                            let _this = this;
                            setTimeout(() => {
                                _this.getTunnelMessage();
                            }, parseFloat(this.refresh.refreshTime));
                        }
                    });
            },
            getRunMessage() {
                TunnelService.getVmRunMessage()
                    .then(
                        result => {
                            this.runMessage.startTime = new Date(result.startTime).format("yyyy-MM-dd")
                            this.runMessage.total = result.total
                            this.runMessage.safe = result.safe
                        },
                        error => {
                            this.Log.info(error);
                        }
                    )
                    .finally(() => {
                        if (this.refresh.runMessageFlag) {
                            let _this = this;
                            setTimeout(() => {
                                _this.getRunMessage();
                            }, parseFloat(this.refresh.refreshTime));
                        }
                    });
            },
            getCableMessage() {
                TunnelService.getVmLineMessage().then(
                    res => {
                        this.barChart.data = res
                    },
                    err => {
                        this.Log.info(err)
                    }
                ).finally(() => {
                    if (this.refresh.cableMessageFlag) {
                        let _this = this;
                        setTimeout(() => {
                            _this.getCableMessage();
                        }, parseFloat(this.refresh.refreshTime));
                    }
                });
            }
        }
    };
</script>

<style scoped>
    .main {
        width: 100%;
        height: 100%;
        background: url("../../../assets/VM/module_bg.png") no-repeat;
        background-size: 100% 100%;
    }

    .main .commonTitle {
        width: 100%;
        height: 15%;
    }

    .leftBox {
        width: 50%;
        height: 85%;
        float: left;
    }

    .rightBox {
        width: 50%;
        height: 85%;
        float: left;
    }

    .leftBox .tunnelMessage {
        width: 100%;
        height: 35%;
    }

    .leftBox .pieChartClass {
        width: 60%;
        height: 65%;
        float: left;
    }

    .leftBox .tongjiClass {
        width: 40%;
        height: 45%;
        float: left;
    }

    .safe {
        font-family: UnidreamLED;
        font-size: 1.3vw;
        color: red;
        margin: 0 0.5vmin;
    }

    .startTimeText {
        color: #fff;
        font-size: 0.5vw;
        line-height: 4vmin;
        margin-left: 1vw;
    }

    .safeRunText {
        color: #fff;
        font-size: 0.8vw;
        line-height: 4vmin;
        margin-left: 1vw;
        margin-top: -5%;
        margin-bottom: 5%;
    }

    .statusText {
        color: #fff;
        font-size: 0.7vw;
        line-height: 3vmin;
        margin: 10% 5%;
    }

    .style_1 {
        color: #2af0ff;
    }

    .style_2 {
        color: #ffff00;
    }

    .style_3 {
        color: #3397ff;
    }
</style>