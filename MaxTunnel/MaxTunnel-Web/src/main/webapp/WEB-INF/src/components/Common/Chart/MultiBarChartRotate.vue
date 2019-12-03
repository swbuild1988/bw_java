<template>
    <div class="MultiBarRotate" :id="id" ref="element"></div>
</template>

<style scoped>
    .MultiBarRotate {
        position: relative;
        height: 100%;
        width: 100%;
    }
</style>

<script>
    export default {
        name: "MultiBarRotate",
        props: {
            id: {
                type: String
            },
            data: {
                default: [{
                    key: "key",
                    val: 10
                }],
                type: Array
            },
            unit: {
                default: '',
                type: String
            },
            parameters: {
                type: Object
            }
        },
        data() {
            return {
                myChart: {},
                option: {},
            };
        },
        watch: {
            data() {
                this.init();
            }
        },
        mounted() {
            this.init();
            this.resizeChart();
        },
        methods: {
            init() {
                this.drawBar();
            },

            resizeChart() {
                let _this = this;
                window.addEventListener("resize", function () {
                    _this.drawBar();
                    _this.myChart.resize();
                });
            },
            drawBar() {
                let _this = this;
                _this.myChart = _this.$echarts.init(
                    document.getElementById(_this.id)
                );
                this.option = {
                    color: ['#3398DB'],
                    title: {
                        textStyle: {
                            color: '#eee',
                            fontSize: this.getFontSize('6.5%')
                        },
                        left: 'center'
                    },
                    tooltip: {
                        trigger: "axis",
                        axisPointer: {
                            type: "shadow"
                        }
                    },
                    legend: {
                        show: false
                    },
                    grid: {
                        left: "3%",
                        right: "15%",
                        bottom: "3%",
                        containLabel: true
                    },
                    calculable: true,
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
                                fontSize: _this.getFontSize('4%') //更改坐标轴文字大小
                            }
                        }
                    },
                    yAxis: {
                        type: 'category',
                        axisLine: {
                            lineStyle: {
                                color: '#ccc'
                            }
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize: _this.getFontSize('4%') //更改坐标轴文字大小
                            }
                        },
                        data: []
                    },
                    series: [{
                        type: 'bar',
                        label: {
                            show: true,
                            position: 'right',
                            fontSize: this.getFontSize('4%')
                        },
                        barMaxWidth: '50%',
                        data: []
                    }]
                }
                for (let index = 0; index < this.data.length; index++) {
                    const element = this.data[index];
                    this.option.yAxis.data.push(element.key)
                    this.option.series[0].data.push(element.val)
                }
                // 加载默认参数
                _this.myChart.setOption(_this.option);
                // 加载新的参数
                if (_this.parameters.option) {
                    _this.myChart.setOption(_this.parameters.option);
                }
                window.addEventListener("resize", _this.myChart.resize);
            },
            getFontSize(val) {
                if (typeof val == "number") return val;

                if (typeof val == "string") {
                    if (val.indexOf("%") > 0) {
                        var tmp = parseFloat(val.replace("%", "")) / 100;
                        console.log("rotate", this.$refs)
                        let height = this.$refs.element.offsetHeight;
                        return Math.round(height * tmp);
                    }
                }
                return 0;
            }
        }
    };
</script>