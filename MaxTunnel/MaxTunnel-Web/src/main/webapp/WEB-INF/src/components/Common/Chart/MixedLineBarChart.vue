<template>
    <div class="mixedLineBar" :id=id>
    </div>
</template>
<script>
export default {
    name: 'mixedLineBar',
    props: {
        id: {
            type: String
        },
        legendData: {
            type: Array
        },
        xAxisData: {
            type: Array
        },
        yAxisData1: {
            type: String
        },
        yAxisData2: {
            type: String
        },
        seriesData: {
            type: Array
        }  
    }, 
    data(){
        return {
            myChart: {},
            option: {
                tooltip: {
                    trigger: 'axis',
                },
                toolbox: {
                    feature: {
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true}
                    }
                },
                legend: {
                    data: this.legendData,
                    textStyle: {color: '#ccc'},
                },
                xAxis: [
                    {
                        type: 'category',
                        data: this.xAxisData,
                        axisPointer: {
                            type: 'shadow'
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#00FF00'
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '缺陷与备品',
                        axisLabel: {
                            formatter: "{value}" + this.yAxisData1+""
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#00FF00'
                            }
                        }
                    },
                    {
                        type: 'value',
                        name: '工具',
                        axisLabel: {
                            formatter: "'{value}" + this.yAxisData2+"'"
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#00FF00'
                            }
                        }
                    }
                ],
                series: this.seriesData
            },
        }
    },
     mounted(){
        this.drawBar()
    },
    methods: {
        drawBar() {
            this.myChart = this.$echarts.init(
                document.getElementById(this.id)
            );
            this.myChart.setOption(this.option);
            window.addEventListener("resize", this.myChart.resize);
        }
    }
}
</script>
<style scoped>
    .mixedLineBar{
        width: 100%;
        height: 100%;
    }
</style>
