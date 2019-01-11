<template>
    <div class="Main">
        <div class="topLeft">
            <div class="Title">
                <module-title :title="title"></module-title>
            </div>
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
            <pile-bar-chart v-bind="pileBarChart" ref="pileBar"></pile-bar-chart>
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
            title: '告警统计',
            yearTotal: {
                number: 271,
                isRise: false
            },
            monthTotal: {
                number: 32,
                isRise: true
            },
            pieChart: {
                requestUrl: "tunnels/items/energies",
                id: "tunnelEnergyPieChart",
                parameters: {
                    option: {
                        title: {
                            text: "管廊告警统计",
                            x: "center",
                            textStyle: {
                                fontWeight: "bold",
                                color: "white",
                                fontSize: "16"
                            }
                        }
                    }
                }
            },
            pileBarChart: {
                id: 'alarmReportBar',
                requestUrl: "alarms/level-count–everymonth",
                parameters: {option: {
                    legend: {
                        data: ["严重", "一般", "提示", "致命"],
                        textStyle: {
                            color: 'white'
                        },
                        orient: 'horizontal',
                        left: 20
                    },
                    xAxis: {
                        axisLabel: {
                           color: 'white'
                        }
                    },
                    yAxis: {
                        axisLabel: {
                            color: 'white'
                        }
                    }
                }, prams: {}},
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
        init(){
            let _this = this
            AlarmService.getYearAndMonthAlarmCount().then(
                result=>{
                    result.forEach(item=>{
                        if(item.key == 'year'){
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
    width: 43%;
    height: 50%;
    position: absolute;
    top: 0%;
    left: 0%;
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
    width: 100%;
    height: 30%;
}
.subTitle {
    font-size: 1.4vmin;
    color: white;
    margin: 1vh 1.4vw;
}
.number {
    font-size: 2vmin;
    margin-left: 0.6vw;
    color: #37bbcc;
}
.yearIcon {
    font-size: 3vmin;
    position: absolute;
    top: 5.4vh;
    right: 0;
}
.monthIcon {
    font-size: 3vmin;
    position: absolute;
    top: 9.2vh;
    right: 0;
}
</style>
