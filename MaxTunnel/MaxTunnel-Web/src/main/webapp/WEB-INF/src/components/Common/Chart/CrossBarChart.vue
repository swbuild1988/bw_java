<template>
    <div
        class="crossBar"
        :id="id"
        ref="element"
    >
    </div>
</template>
<script>
export default {
    name: 'corssBar',
    props: {
        id: {
            type: String
        },
        title: {
            type: String
        },
        legendData: {
            type: Array
        },
        yAxisData: {
            type: Array
        },
        seriesData: {
            type: Array
        },
    },
    data() {
        return {
            myChart: {},
            option: {},
            itemStyleColor: ['#C23531', '#61A0A8']
        }
    },
    mounted() {
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
            this.fetchData()
        },
        drawBar() {
            var _this = this;
            this.myChart = this.$echarts.init(document.getElementById(this.id));

            this.option = {
                title: {
                    text: this.title,
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('5%')
                    },
                    padding: [5,10]
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: this.legendData,
                    textStyle: { 
                        color: '#ccc',
                        fontSize: _this.getFontSize('5%') 
                    },
                    top: 30,
                    left: 10,
                    right: 10
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01],
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize : _this.getFontSize('5%')      //更改坐标轴文字大小
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
                            fontSize : _this.getFontSize('5%')      //更改坐标轴文字大小
                        }
                    },
                },
                series: []
            }
            this.myChart.setOption(_this.option);
            // window.addEventListener("resize", this.myChart.resize);
        },
        fetchData() {
            var _this = this;
            while (this.itemStyleColor.length < this.seriesData.length) {
                this.itemStyleColor = this.itemStyleColor.concat(this.itemStyleColor)
            }
            for (var i = 0; i < this.legendData.length; i++) {
                let seriesDataAll = {
                    name: this.legendData[i],
                    type: 'bar',
                    data: this.seriesData[i].data,
                    itemStyle: {
                        normal: {
                            color: this.itemStyleColor[i]
                        }
                    }
                };
                this.option.series.push(seriesDataAll);
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
        }
    }
}
</script>
<style scoped>
.crossBar {
    width: 100%;
    height: 100%;
}
</style>
