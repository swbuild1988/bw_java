<template>
    <div class="SimpleBar" :id="id" ref="element"></div>
</template>

<script>
export default {
    name: "SimpleBar",
    props: {
        id: {
            type: String
        },
        title: {
            type: String
        },
        xData: {
            type: Array,
            default: []
        },
        yData: {
            type: Array,
            required: true
        },
        parameters: {
            type: Object
        },
        // xAxisRotate: {
        //     default: 0,
        //     type: String
        // },
        titleColor: {
            default: "#fff",
            type: String
        },
        lineColor: {
            default: "#fff",
            type: String
        },
        splitLineColor: {
            default: "#fff",
            type: String
        },
        barColor: {
            default: "#fff",
            type: String
        }
    },
    data() {
        return {
            myChart: {},
            option: {}
        };
    },
    mounted() {
        this.init();
        this.refreshData();
    },
    watch: {
        yData() {
            this.init();
        }
    },
    methods: {
        init() {
            this.drawBar();
            // this.fetchData(this.requestUrl);
        },

        drawBar() {
            let _this = this;

            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );

            // 加载默认参数
            _this.option = {
                color: ["#61a0a8"],
                title: {
                    text: this.title,
                    textStyle: {
                        fontSize: this.getFontSize("5%"),
                        color: this.titleColor
                    },
                    top: "2%"
                },
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        // 坐标轴指示器，坐标轴触发有效
                        type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "5%",
                    top: "15%",
                    containLabel: true
                },
                xAxis: [
                    {
                        type: "category",
                        data: this.xData,
                        axisLabel: {
                            interval: 0,
                            boundaryGap: [0, 0.01],
                            // rotate: this.xAxisRotate,
                            show: true,
                            textStyle: {
                                fontSize: this.getFontSize("5%"), //更改坐标轴文字大小
                                color: this.lineColor
                            }
                        },
                        splitLine: {
                            show: false
                        },
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        }
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        axisLabel: {
                            interval: 0,
                            show: true,
                            textStyle: {
                                fontSize: this.getFontSize("5%"), //更改坐标轴文字大小
                                color: this.lineColor
                            }
                        },
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        splitLine: {
                            show: true,
                            lineStyle: {
                                type: "dashed",
                                color: this.splitLineColor
                            }
                        }
                    }
                ],
                series: [
                    {
                        type: "bar",
                        data: this.yData,
                        itemStyle: {
                            borderWidth: 2,
                            opacity: 1,
                            color: this.barColor
                        },
                        barWidth: this.getFontSize(10)
                    }
                ]
            };
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
                    let height = this.$refs.element.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        },
        //定时刷新数据
        refreshData() {
            let _this = this;

            if (_this.parameters.timer) {
                let { intervalId, intervalTime } = _this.parameters.timer;

                intervalId = setInterval(() => {
                    for (let i = 0, len = _this.yData.length; i < len; i++) {
                        _this.yData[i] = 0;
                    }
                    _this.myChart.setOption({
                        series: { data: _this.yData }
                    });
                    // _this.fetchData(_this.requestUrl);
                }, intervalTime);
            }
        }
    }
};
</script>

<style scoped>
.SimpleBar {
    width: 100%;
    height: 100%;
}
</style>
