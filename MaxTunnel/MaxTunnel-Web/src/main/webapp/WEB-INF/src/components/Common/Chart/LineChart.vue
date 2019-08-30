<template>
    <div>
        <div class="Line" :id="id" ref="element"></div>
    </div>
</template>

<script>
export default {
    name: "Line-Chart",
    props: {
        id: {
            type: String
        },
        legendData: {
            type: Array,
            default: () => {
                return [
                    "古城大街",
                    "实验路",
                    "经三路",
                    "经二路",
                    "纬二路",
                    "监控中心"
                ];
            }
        },
        series: {
            type: Array,
            required: true
        },
        xData: {
            type: Array,
            default: []
        },
        parameters: {
            type: Object
        },
        yMin: {
            default: 0
        }
    },
    data() {
        return {
            myChart: "",
            option: {},
            timerId: null,
            color: [
                "#C23531",
                "#2F4554",
                "#61A0A8",
                "#D48265",
                "#91C7AE",
                "#749F83"
            ],
            refreshFlag: true
        };
    },
    components: {},
    mounted() {
        this.init();
        this.resizeChart();
    },
    watch: {
        series() {
            this.init();
        },
        yMin() {
            this.init();
        }
    },
    methods: {
        init() {
            this.drawLine();
            // this.refreshData();
        },
        resizeChart() {
            let _this = this;
            window.addEventListener("resize", function() {
                _this.drawLine();
                _this.myChart.resize();
            });
        },
        drawLine() {
            let _this = this;

            _this.option = {
                title: {
                    text: "",
                    top: "left",
                    left: "center",
                    textStyle: {
                        //   color: "#fff",
                        fontSize: window.innerHeight * 0.018
                    }
                },
                tooltip: {
                    trigger: "axis"
                },
                color: _this.color,
                legend: {
                    itemHeight: 4,
                    orient: "vertical",
                    left: "center",
                    bottom: "bottom",
                    margin: 10,
                    // itemGap: 1,
                    itemWidth: window.innerHeight * 0.01,
                    textStyle: {
                        // color: '#fff',
                        fontSize: window.innerHeight * 0.014
                    },
                    data: this.legendData
                },
                grid: {
                    top: "15%",
                    left: "14%",
                    right: "10%",
                    bottom: "20%"
                },
                xAxis: {
                    type: "category",
                    axisLine: {
                        lineStyle: {
                            // color: '#fff'
                        }
                    },
                    axisLabel: {
                        fontSize: window.innerHeight * 0.01
                    },
                    data: this.xData
                },
                yAxis: {
                    type: "value",
                    min: this.yMin,
                    axisLine: {
                        margin: 2,
                        lineStyle: {
                            // color: '#fff'
                        }
                    },
                    axisLabel: {
                        fontSize: window.innerHeight * 0.01,
                        formatter: function(value, index) {
                            if (value >= 10000 && value < 100000) {
                                value = value / 10000 + "万";
                            } else if (value >= 100000 && value < 1000000) {
                                value = value / 1000000 + "十万";
                            } else if (value >= 10000000) {
                                value = value / 10000000 + "千万";
                            }
                            return value;
                        }
                    }
                },
                series: this.series
            };
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            _this.myChart.setOption(_this.option);
            // 加载新的参数
            if (_this.parameters.option) {
                _this.myChart.setOption(_this.parameters.option);
            }
            window.addEventListener("resize", this.myChart.resize);
        },
        // refreshData() {
        //   let _this = this;
        // },
        sizeFunction(x) {
            var min = Math.min.apply(
                null,
                this.series.map(o1 => {
                    return Math.min.apply(
                        null,
                        o1.map(o2 => {
                            return parseFloat(o2[2]);
                        })
                    );
                })
            );
            var max = Math.max.apply(
                null,
                this.series.map(o1 => {
                    return Math.max.apply(
                        null,
                        o1.map(o2 => {
                            return parseFloat(o2[2]);
                        })
                    );
                })
            );

            // 最小的5%，最大的15%
            var y = 5 + ((x - min) / (max - min)) * (15 - 5);
            var size = this.getFontSize(y + "%");
            return size;
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
        }
    },
    beforeDestroy() {
        clearTimeout(this.timerId);
        this.timerId = null;
        this.refreshFlag = false;
    }
};
</script>

<style scoped>
.Line {
    width: 100%;
    height: 100%;
}
</style>
