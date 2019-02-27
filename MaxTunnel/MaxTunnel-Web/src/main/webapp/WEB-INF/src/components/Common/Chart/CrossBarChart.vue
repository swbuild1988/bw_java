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
            itemStyleColor: ['#75af99', '#c1a048', '#bcd3bb', '#e88f70', '#edc1a5', '#9dc5c8', '#e1e8c8', '#7b7c68', '#e5b5b5', '#f0b489', '#928ea8', '#bda29a']
        }
    },
    watch: {
        seriesData(newVal, oldVal) {
            this.drawBar()
            this.fetchData()
        }
    },
    mounted() {
        this.init()
        // this.drawBar()
    },
    methods: {
        init() {
            this.resize()
            window.addEventListener("resize", this.myChart.resize);
            window.addEventListener("resize", this.resize)
        },
        resize() {
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
                        fontSize: _this.getFontSize('6.5%')
                    }
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
                        fontSize: _this.getFontSize('4%')
                    },
                    top: _this.getFontSize('6.5%'),
                    right: _this.getFontSize('1%')
                },
                grid: {
                    top: this.title.length > 0 ? '12%' : '1%',
                    left: '3%',
                    right: '3%',
                    bottom: '1%',
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
                            fontSize: _this.getFontSize('4%')      //更改坐标轴文字大小
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
                            fontSize: _this.getFontSize('4%')      //更改坐标轴文字大小
                        }
                    },
                },
                series: []
            }
            this.myChart.setOption(_this.option);
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
                    stack: 'total',
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
