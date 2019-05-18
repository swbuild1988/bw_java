<template>
    <div class="main">
        <div class="commonTitle">
            <module-title :title="title"></module-title>
        </div>

        <Row style="padding: 0.1vh;">
            <Col span="8" offset="1">
            <div>
                <hollow-pie-chart v-bind="pieChart" style="height: 12vh;"></hollow-pie-chart>
            </div>
            </Col>
            <Col span="15">
            <Row>
                <Col span="20" offset="2">
                <div class="safeRunText">
                    <span style="font-weight: bold">管廊安全运营</span>
                    <span class="safe">{{runMessage.safe}}</span>
                    <span style="font-weight: bold">天</span>

                </div>
                </Col>
                <Col span="7" offset="1" :key="index" v-for="(item,index) in statusList">
                <div class="statusText">
                    <span style>{{item.name}}</span>
                    <p style="margin-left: 5px;" :class="index>0 ? (index>1?'style_3':'style_2'): 'style_1'  ">{{item.value}}</p>
                </div>
                </Col>
            </Row>
            </Col>
        </Row>

        <div>
            <progress-bar-chart v-bind="barChart" style="height: 12vh;"></progress-bar-chart>
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
    import ProgressBarChart from "../../../components/Common/chart/ProgressBarChart.vue";

    export default {
        data() {
            return {
                title: "",
                statusList: [],
                pieChart: {
                    id: "hollow-pie-chart",
                    requestUrl: "",
                    unit: "条",
                    Server: {
                        status: true,
                        serverName: "TunnelService.getVmTunnelsMessage"
                    },
                    parameters: {
                        option: {
                            title: {
                                text: "管廊总数"
                            }
                        }
                    }
                },
                barChart: {
                    id: "progress-bar-chart",
                    requestUrl: "",
                    unit: "km",
                    Server: {
                        status: true,
                        serverName: "TunnelService.getVmLineMessage"
                    },
                    parameters: {
                        option: {}
                    }
                },
                runMessage: {
                    total: 50,
                    safe: 20
                },
                refresh: {
                    intervalId: null,
                    tunnelMessage: null,
                    runMessage: null
                }
            };
        },
        components: {
            Grid,
            ModuleTitle,
            HollowPieChart,
            ProgressBarChart
        },
        mounted() {
            
            this.getTunnelMessage();
            let _this = this;
            // this.refresh.intervalId = setInterval(() => {
            //     _this.init();
            // }, this.refresh.time);
        },
        beforeDestroy() {
            clearTimeout(this.refresh.tunnelMessage);
            clearTimeout(this.refresh.runMessage);
        },
        methods: {
            getTunnelMessage() {
                this.title = "基本信息";
                TunnelService.getVmTunnelsMessage().then(
                    result => {
                        this.statusList = [];
                        let tempData = result.slice(0, 3);
                        tempData.forEach(tunnel => {
                            let temp = [];
                            temp.name = tunnel.name;
                            temp.value =
                                Math.round(tunnel.percent.replace("%", "")) + "%";
                            this.statusList.push(temp);
                        });
                    },
                    error => {
                        this.Log.info(error);
                    }
                )
                .finally(()=>{
                    let _this = this
                   
                    this.refresh.tunnelMessage = setTimeout(()=>{
                        _this.getTunnelMessage()
                    },parseFloat(this.refreshTime))
                });
            },
            getRunMessage(){
                 TunnelService.getVmRunMessage().then(
                    result => {
                        this.runMessage.total = result.total;
                        this.runMessage.safe = result.safe;
                    },
                    error => {
                        this.Log.info(error);
                    }
                )
                .finally(()=>{
                    let _this = this
                    this.refresh.runMessage = setTimeout(()=>{
                        _this.getRunMessage()
                    },parseFloat(this.refreshTime))
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

    .safe {
        font-family: UnidreamLED;
        font-size: 3.4vmin;
        color: red;
        margin: 0 0.5vmin;
    }

    .safeRunText {
        color: #fff;
        font-size: 1.8vmin;
        line-height: 4vmin;
        margin-left: 1vw;
        margin-top: -5%;
        margin-bottom: 5%;
    }

    .statusText {
        color: #fff;
        font-size: 1.66vmin;
        line-height: 3vmin;
        margin-left: 1vw;
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