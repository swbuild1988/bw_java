<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="leftBox">
            <div class="simplePieChartBox">
                <!-- <simplePieChart v-bind="simplePieData"></simplePieChart> -->
                <pie-chart v-bind="pieData"></pie-chart>
            </div>
            <div class="letBottomBox">
                <ComplexBarChart v-bind="ComplexBar"></ComplexBarChart>
            </div>
        </div>
        <div class="stackCrossBox">
            <cross-bar-chart
                v-bind="stackCrossData"
                ref="stackCrossChart"
            ></cross-bar-chart>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle";
import stackCrossChart from "../../../components/Common/Chart/StackCrossChart"
import simplePieChart from '../../../components/Common/Chart/SimplePieChart'
import CrossBarChart from "../../../components/Common/Chart/CrossBarChart"
import PieChart from "../../../components/Common/Chart/PieChart"
import ComplexBarChart from "../../../components/Common/Chart/ComplexBarChart.vue"
import { EquipmentService } from '../../../services/equipmentService'

export default {
    data() {
        return {
            title: "设备台账",
            stackCrossData: {
                id: 'stackCrossID',
                title: '设备类型状态统计',
                legendData: [],
                yAxisData: [],
                seriesData: []
            },
            pieData: {
                id: 'pieID',
                title: '设备状态',
                legendData: [],
                seriesData: []
            },
            simplePieData: {
                id: 'simplePieID',
                requestUrl: '/equipments/instruments/status',
                parameters: {
                    option: {
                        title: {
                            text: "设备统计",
                            x: "center",
                            textStyle: {
                                color: "white"
                            }
                        },
                        series: {
                            label: {
                                normal: {
                                    textStyle: {
                                        fontSize: '15%'
                                    }
                                }
                            }
                        },
                        grid: {
                            top: '8%',
                            left: 0,
                            right: 0
                        },
                    },
                    timer: 1000 * 5 * 60
                },
                titleSize: '7%',
                seriesSize: '4%'
            },
            ComplexBar: {
                id: "ComplexBarChart",
                // yAxisName: "单位：个",
                requestUrl: "tunnels/equipments/types/test",
                title: "各管廊设备明细",
                color: "#21d6ff",
                bgColor: '#0E2644',
                titleColor: '#ccc',
                fontSizeNum: '10%'
            },
        };
    },
    components: {
        ModuleTitle, stackCrossChart, simplePieChart, CrossBarChart, PieChart, ComplexBarChart
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            // 获取数据
            EquipmentService.getEquipmentTypeAndStatusCount().then(res => {
                // 柱状图数据
                var arr = []
                for (var i = 0; i < res.length; i++) {
                    this.stackCrossData.yAxisData.push(res[i].key)
                    var tmp = [];
                    for (var j = 0; j < res[i].val.length; j++) {
                        if (i == 0) {
                            this.stackCrossData.legendData.push(res[i].val[j].key)
                            arr.push([res[i].val[j].val])
                        } else {
                            arr[j].push(res[i].val[j].val)
                        }
                    }
                }
                for (var i = 0; i < arr.length; i++) {
                    this.stackCrossData.seriesData.push({ data: arr[i] })
                }

                // 饼图
                this.pieData.legendData = [];
                for (var i = 0; i < this.stackCrossData.legendData.length; i++) {
                    this.pieData.legendData.push(this.stackCrossData.legendData[i]);
                }
                for (var i = 0; i < arr.length; i++) {
                    this.pieData.seriesData.push(0);
                    for (var j = 0; j < arr[i].length; j++) this.pieData.seriesData[i] += arr[i][j];
                }
                // this.pieData.legendData = this.pieData.legendData.reverse();
                // this.pieData.seriesData = this.pieData.seriesData.reverse();
            })
        },
        refreshData() {
            setInterval(() => {
                this.$refs.stackCrossChart.drawBar()
            }, 1000 * 60 * 5)
        }
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}

.Main .Title {
    width: 100%;
    height: 15%;
}

.leftBox{
    width: 45%;
    height: 85%;
    display: inline-block;
    vertical-align: top;
}

.simplePieChartBox {
    width: 100%;
    height: 45%;
}

.letBottomBox{
    width: 100%;
    height: 55%;
} 

.stackCrossBox {
    width: 52%;
    height: 85%;
    display: inline-block;
}
</style>
