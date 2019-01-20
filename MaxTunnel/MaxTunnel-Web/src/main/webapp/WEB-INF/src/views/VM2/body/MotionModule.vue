<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="Chart">
            <line-chart
                v-bind="lineChart"
                ref="lineChart"
                style="height: 26vh;"
            ></line-chart>
        </div>
    </div>
</template>

<script>
import { TunnelService } from '../../../services/tunnelService'
import { EnterGalleryService } from '../../../services/enterGalleryService'
import { EnergyConsumptionService } from '../../../services/EnergyConsumptionService'
import LineChart from '../../../components/Common/Chart/LineChart.vue'
import ModuleTitle from "../../../components/VM2/ModuleTitle";

export default {
    data() {
        return {
            title: "能耗监测",
            lineChart: {
                id: "tunnelEnergyLineChart",
                requestUrl: "energy/totle-everymonth",
                parameters: {
                    option: {
                        title: {
                            text: "管廊最近一年耗电量",
                        },
                        series: [
                            {
                                type: 'line',
                                name: '1',
                                itemStyle: {
                                    normal: {
                                        color: '#a173ed',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            },
                            {
                                type: 'line',
                                name: '2',
                                itemStyle: {
                                    normal: {
                                        color: '#ed9a20',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            },
                            {
                                type: 'line',
                                name: '3',
                                itemStyle: {
                                    normal: {
                                        color: '#509bed',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            },
                            {
                                type: 'line',
                                name: '4',
                                itemStyle: {
                                    normal: {
                                        color: '#90ed27',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            },
                            {
                                type: 'line',
                                name: '5',
                                itemStyle: {
                                    normal: {
                                        color: '#ed405d',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            },
                            {
                                type: 'line',
                                name: '6',
                                itemStyle: {
                                    normal: {
                                        color: '#adedc3',
                                        fontSize: window.innerHeight * 0.016,
                                    },
                                },
                                markPoint: {
                                    data: [
                                        {
                                            type: 'max', name: '最大值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#d90051',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        },
                                        {
                                            type: 'min', name: '最小值',
                                            itemStyle: {
                                                normal: {
                                                    color: '#01bc8f',
                                                    fontSize: window.innerHeight * 0.016,
                                                },
                                            },
                                        }
                                    ]
                                },
                            }
                        ]
                    }
                }
            },
        };
    },
    components: {
        LineChart,
        ModuleTitle
    },
    mounted() {
    },
    methods: {
        getData() {
            let p1 = TunnelService.getTunnels();
            let p2 = EnterGalleryService.getCountOfEnterEveryMonthAndTunnel();
            let p3 = EnergyConsumptionService.getTotleEnergyEveryMonthAndTunnel();
            let p4 = EnergyConsumptionService.getOneKmEneryEveryMonthAndTunnel();

            Promise.all([p1, p2, p3, p4]).then(result => {
                // console.log("result", result);
                this.data.group = []
                this.data.timeline = []
                this.data.series = []

                this.data.group = result[0].map(function (o) {
                    return o.name;
                })

                this.data.timeline = result[1].map(function (o) {
                    if (o.length && o.length > 0)
                        return new Date(o[0].time).format('yy.MM');
                    else
                        return "";
                })

                for (let i = 0; i < this.data.timeline.length; i++) {
                    let tmp = [];
                    for (let j = 0; j < this.data.group.length; j++) {
                        // 每公里能耗
                        let x = result[3].find(a => {
                            if (a.length && a.length > 0)
                                return new Date(a[0].time).format('yy.MM') == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).energy;
                        // 入廊次数
                        let y = result[1].find(a => {
                            if (a.length && a.length > 0)
                                return new Date(a[0].time).format('yy.MM') == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).count;
                        // 总能耗
                        let z = result[2].find(a => {
                            if (a.length && a.length > 0)
                                return new Date(a[0].time).format('yy.MM') == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).energy;
                        tmp.push([x, y, z, this.data.group[j], new Date(this.data.timeline[i]).format('yy.MM')])
                    }
                    this.data.series.push(tmp);
                }

            }).then(error => {
                if (error) console.log("error", error)
            })
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
    margin: 1vh 1.5vmin 1vh;
    width: 100%;
    height: 26vh;
    position: relative;
}
</style>
