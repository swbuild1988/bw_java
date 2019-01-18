<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="topLeft">
            <div class="subTitle">
                <span>本年度告警：</span>
                <span class="number">{{ yearTotal.number }}</span>
                <span class="yearIcon">
                    <Icon
                        :type="yearTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="yearTotal.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </span>
            </div>
            <div class="subTitle">
                <span style="letter-spacing: 0.25em;margin-right: -0.25em;">本月告警：</span>
                <span class="number">{{ monthTotal.number }}</span>
                <span class="monthIcon">
                    <Icon
                        :type="monthTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="monthTotal.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </span>
            </div>
        </div>
        <div class="topRight">
            <simple-pie-chart v-bind="pieChart"></simple-pie-chart>
        </div>
        <div class="bottom">
            <pile-bar-chart
                v-bind="pileBarChart"
                ref="pileBar"
            ></pile-bar-chart>
        </div>
    </div>
</template>

<script>
import SimplePieChart from '../../../components/Common/Chart/SimplePieChart'
import pileBarChart from "../../../components/Common/Chart/PileBarChart"
import ModuleTitle from "../../../components/VM2/ModuleTitle"
import { AlarmService } from '../../../services/alarmService'

export default {
    data() {
        return {
            title: '警报统计',
            yearTotal: {
                number: 271,
                isRise: false
            },
            monthTotal: {
                number: 32,
                isRise: true
            },
            pieChart: {
                requestUrl: "tunnels/alarm-count",
                id: "tunnelAlarmPieChart",
                parameters: {
                    option: {
                        title: {
                            text: "管廊告警统计",
                            x: "center",
                            textStyle: {
                                color: "white",
                                fontSize: "10%"
                            }
                        },
                        series: {
                            label: {
                                normal: {
                                    textStyle: {
                                        fontSize: '8%'
                                    }
                                }
                            }
                        },
                        grid: {
                            top: '6%',
                            left: 0,
                            right: 0
                        },
                    }
                }
            },
            pileBarChart: {
                id: 'alarmsReportBar',
                requestUrl: "alarms/level-count–everymonth",
                parameters: {                    option: {
                        legend: {
                            data: ["严重", "一般", "提示", "致命"],
                            textStyle: {
                                color: 'white',
                                fontSize: '10%'
                            },
                            orient: 'horizontal',
                            left: 20
                        },
                        xAxis: {
                            axisLabel: {
                                color: 'white',
                                fontSize: '8%'
                            }
                        },
                        yAxis: {
                            axisLabel: {
                                color: 'white',
                                fontSize: '8%'
                            }
                        }
                    }, prams: {}                },
                type: 'get'
            }
        };
    },
    components: {
        SimplePieChart,
        pileBarChart,
        ModuleTitle
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            let _this = this
            AlarmService.getYearAndMonthAlarmCount().then(
                result => {
                    result.forEach(item => {
                        if (item.key == 'year') {
                            _this.yearTotal.number = item.value
                            _this.yearTotal.isRise = item.isRise
                        } else {
                            _this.monthTotal.number = item.value
                            _this.monthTotal.isRise = item.isRise
                        }
                    })
                })
        }
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.topLeft {
    width: 42%;
    height: 28%;
    position: absolute;
    top: 14%;
    left: 4%;
    background: url("../../../assets/UM/videoBody.png") no-repeat;
    background-size: 100% 100%;
}
.topRight {
    width: 44%;
    height: 50%;
    position: absolute;
    top: 5%;
    right: 6%;
}
.bottom {
    position: absolute;
    width: 90%;
    height: 42%;
    top: 50%;
    right: 6%;
}
.Title {
    width: 42vmin;
    height: 5vmin;
}
.subTitle {
    font-size: 1.4vmin;
    color: white;
    margin: 1vh 0.8vw;
}
.number {
    font-size: 2vmin;
    margin-left: 0.6vw;
    color: #37bbcc;
}
.yearIcon {
    font-size: 3vmin;
    position: absolute;
    top: 0.4vh;
    right: 0.8vw;
}
.monthIcon {
    font-size: 3vmin;
    position: absolute;
    top: 4.4vh;
    right: 0.8vw;
}
</style>
