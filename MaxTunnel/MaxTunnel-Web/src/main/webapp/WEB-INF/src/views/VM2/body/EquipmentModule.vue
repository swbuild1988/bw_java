<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="simplePieChartBox" id="simplePieChartBox">
            <simplePieChart v-bind="simplePieData"></simplePieChart>
        </div>
        <div class="stackCrossBox">
            <stackCrossChart v-bind="stackCrossData" ref="stackCrossChart"></stackCrossChart>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle";
import stackCrossChart from "../../../components/Common/Chart/StackCrossChart"
import simplePieChart from '../../../components/Common/Chart/SimplePieChart'

export default {
    data() {
        return {
            title: "设备",
            stackCrossData: {
                id: 'stackCrossID',
                title: '管廊设备统计',
                legendData: [],
                yAxisData: [],
                seriesData: [],
                setIntervalTime: 1000*5*60
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
                    timer: 1000*5*60
                },
                titleSize: '7%',
                seriesSize: '4%'
            }
        };
    },
    components: {
        ModuleTitle, stackCrossChart, simplePieChart
    },
    mounted() {
        this.getSerious()
    },
    methods: {
        getSerious(){
            this.axios.get('types/equipments/tunnels').then(res=>{
                let { code,data } = res.data
                if(code==200){
                    this.stackCrossData.legendData = data.types
                    this.stackCrossData.yAxisData = data.tunnels
                    var series = data.list
                    for( var i=0; i<this.stackCrossData.legendData.length; i++ ){
                        var temp = {
                            name: this.stackCrossData.legendData[i],
                            type: 'bar',
                            stack: '总量',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            data: series[i].data
                        }
                        this.stackCrossData.seriesData.push(temp)
                    }
                }
            })
        },
        refreshData(){
            setInterval(()=>{
                this.$refs.stackCrossChart.drawBar()
            },1000*5)
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

.simplePieChartBox {
    width: 44%;
    height: 85%;
    display: inline-block;
}
.stackCrossBox {
    width: 55%;
    height: 85%;
    padding-right:3%;
    display: inline-block;
}
</style>
