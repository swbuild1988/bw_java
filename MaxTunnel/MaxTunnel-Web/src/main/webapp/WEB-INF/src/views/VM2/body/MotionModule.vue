<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="Chart">
            <line-chart v-bind="lineChart" ref="lineChart" style="height: 26vh;"></line-chart>
        </div>
    </div>
</template>

<script>
import { EnergyConsumptionService } from "../../../services/EnergyConsumptionService";
import LineChart from "../../../components/Common/Chart/LineChart.vue";
import ModuleTitle from "../../../components/VM2/ModuleTitle";

export default {
    data() {
        return {
            title: "能耗监测",
            lineChart: {
                id: "tunnelEnergyLineChart",
                series: [],
                legendData: [],
                xData: [],
                yMin: 0,
                parameters: {
                    option: {
                        title: {
                            text: "管廊最近一年耗电量",
                            textStyle: {
                                color: "#fff"
                            }
                        },
                        color: [
                            "#C23531",
                            "#2F4554",
                            "#61A0A8",
                            "#D48265",
                            "#91C7AE",
                            "#749F83"
                        ],
                        xAxis: {
                            axisLine: {
                                lineStyle: {
                                    color: "#fff"
                                }
                            }
                        },
                        yAxis: {
                            axisLine: {
                                lineStyle: {
                                    color: "#fff"
                                }
                            },
                            name: "单位( kW.h )"
                        }
                        // series: [
                        //     {
                        //         type: "line",
                        //         name: "1",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#00F6FC',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     },
                        //     {
                        //         type: "line",
                        //         name: "2",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#42F1F5',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     },
                        //     {
                        //         type: "line",
                        //         name: "3",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#11C0FF',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     },
                        //     {
                        //         type: "line",
                        //         name: "4",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#1190FA',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     },
                        //     {
                        //         type: "line",
                        //         name: "5",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#1255E8',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     },
                        //     {
                        //         type: "line",
                        //         name: "6",
                        //         itemStyle: {
                        //             normal: {
                        //                 // color: '#1C63FF',
                        //                 fontSize: window.innerHeight * 0.016
                        //             }
                        //         }
                        //     }
                        // ]
                    }
                }
            }
        };
    },
    components: {
        LineChart,
        ModuleTitle
    },
    mounted() {
        this.getLineChartData();
    },
    methods: {
        getLineChartData() {
            EnergyConsumptionService.getTotleEnergyEveryMonthAndTunnel().then(
                res => {
                    this.lineChart.series = [];
                    this.lineChart.xData = [];
                    res.forEach((tunnel, index) => {
                        var temp = {};
                        temp.name = tunnel.key;
                        temp.type = "line";
                        temp.smooth = true;

                        this.lineChart.legendData.push({
                            name: tunnel.key,
                            textStyle: {
                                color: this.lineChart.parameters.option.color[
                                    index
                                ]
                            }
                        });

                        let tempData = [];
                        tunnel.val.filter(b =>
                            tempData.push(parseFloat(b.val.toFixed(2)))
                        );
                        temp.data = tempData;
                        this.lineChart.yMin =
                            Math.min.apply(null, tempData) <
                                this.lineChart.yMin || this.lineChart.yMin == 0
                                ? Math.min.apply(null, tempData)
                                : this.lineChart.yMin;

                        this.lineChart.series.push(temp);
                    });

                    res[0].val.filter(time => {
                        this.lineChart.xData.push(time.key);
                    });
                }
            );
        }
    }
};
</script>

<style scoped>
.Main {
    position: relative;
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}

.Main .Title {
    width: 100%;
    height: 15%;
}

.Chart {
    margin: 0.5vh 1.5vmin 1vh;
    width: 100%;
    height: 26vh;
    position: relative;
}
</style>
