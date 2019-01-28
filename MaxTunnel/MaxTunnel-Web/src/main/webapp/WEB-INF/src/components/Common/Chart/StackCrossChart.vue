<template>
    <div
        class='stackCrossChart'
        :id=id
        ref="element"
    ></div>
</template>
<script>
export default {
    name: 'stackCrossChart',
    props: {
        id: {
            type: String
        },
        title: {
            type: String
        },
        legendData:{
            type: Array
        },
        yAxisData: {
            type: Array
        },
        seriesData: {
            type: Array
        },
        setIntervalTime: {
            default: 1000*5*60
        }
    },
    data(){
        return {
            myChart: {},
            option: {},
        }
    },
    watch: {
        seriesData(newVla,oldVal){
            this.drawBar()
        }
    },
    mounted(){
        this.init()
        this.drawBar()
    },
    methods: {
        init(){
            this.drawBar()
            window.addEventListener('resize', this.myChart.resize);
            window.addEventListener('resize',this.drawBar)
        },
        drawBar(){
            this.myChart = this.$echarts.init(document.getElementById(this.id));
            this.option = {
                title: {
                    text: this.title,
                    textStyle: {
                        color: '#ccc',
                        fontSize: this.getFontSize('5%')
                    },
                    padding: [0,10,2,10]
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data: this.legendData,
                    textStyle: { 
                        color: '#ccc',
                        fontSize: this.getFontSize('5%') 
                    },
                    top: 15
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis:  {
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize : this.getFontSize('5%')      //更改坐标轴文字大小
                        }
                    },
                },
                yAxis: {
                    type: 'category',
                    data: this.yAxisData,
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize : this.getFontSize('5%')      //更改坐标轴文字大小
                        }
                    },
                },
                series: this.seriesData
            }
            this.myChart.setOption(this.option);
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
        },
        refreshData(){
            setInterval(()=>{
                this.drawBar()
            },this.setIntervalTime)
        }
    }
}
</script>
<style scope>
.stackCrossChart {
    width: 100%;
    height: 100%;
}
</style>
