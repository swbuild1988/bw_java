<template>
    <div class="mixedLineBar" :id=id ref="element">
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
            option: {},
        }
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.resize()
            window.addEventListener('resize', this.myChart.resize);
            window.addEventListener('resize', this.resize);
        },
        resize(){
            this.drawBar()
        },
        drawBar() {
            var _this = this
            this.myChart = this.$echarts.init(
                document.getElementById(this.id)
            );
            this.option = {
                tooltip: {
                    trigger: 'axis',
                },
                // toolbox: {
                //     feature: {
                //         dataView: {show: true, readOnly: false},
                //         magicType: {show: true, type: ['line', 'bar']},
                //         restore: {show: true}
                //     }
                // },
                legend: {
                    data: this.legendData,
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('7%')
                    },
                    padding: 10
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
                                color:'#ccc'
                            }
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize : _this.getFontSize('7%')      //更改坐标轴文字大小
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
                                color:'#ccc'
                            }
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize : _this.getFontSize('7%')      //更改坐标轴文字大小
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
                                color:'#ccc'
                            }
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize : _this.getFontSize('7%')      //更改坐标轴文字大小
                            }
                        },
                    }
                ],
                series: this.seriesData
            }
            this.myChart.setOption(_this.option);
            window.addEventListener("resize", this.myChart.resize);
        },
        getFontSize(val) {
            if (typeof (val) == 'number') return val;

            if (typeof (val) == 'string') {

                if (val.indexOf('%') > 0) {
                    var tmp = parseFloat(val.replace('%', '')) / 100;
                    let height = this.$refs.element.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
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
