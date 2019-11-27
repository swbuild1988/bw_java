<template>
    <div>
        <div class="hollowPie" :id="id" ref="element"></div>
    </div>
</template>

<script>
    export default {
        name: "hollow-pie-chart",
        props: {
            id: {
                type: String
            },
            unit: {
                type: String,
                default: ""
            },
            series: {
                type: Array,
                required: true
            },
            parameters: {
                type: Object
            }
        },
        data() {
            return {
                myChart: "",
                legendData: [],
                xData: [],
                option: {},
                total: 0
            };
        },
        components: {},
        watch: {
            series() {
                this.drawHollowPie();
            }
        },
        mounted() {
            this.init();
            this.resizeChart();
        },
        methods: {
            init() {
                this.drawHollowPie();
            },
            resizeChart() {
                let _this = this;
                window.addEventListener("resize", function () {
                    _this.drawHollowPie();
                    _this.myChart.resize();
                });
            },
            drawHollowPie() {
                let _this = this;
                _this.option = {
                    title: {
                        text: "",
                        left: "center",
                        top: "55%",
                        textStyle: {
                            color: "#fff",
                            fontSize: _this.getFontSize("11%"),
                            align: "center"
                        }
                    },
                    graphic: {
                        type: "text",
                        left: "center",
                        top: "45%",
                        style: {
                            text: "10",
                            fill: "#fff",
                            fontSize: _this.getFontSize("14%")
                        }
                    },
                    series: [{
                        name: "",
                        type: "pie",
                        center: ["50%","60%"],
                        radius: ["50%", "70%"],
                        hoverAnimation: false,
                        color: [
                            "#2af0ff",
                            "#ffff00",
                            "#3397ff",
                            "#48f974",
                            "#7b6df8"
                        ],
                        label: {
                            normal: {
                                formatter: function (params) {
                                    return params.value + _this.unit;
                                },
                                textStyle: {
                                    fontSize: _this.getFontSize("12%")
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                length: 10,
                                length2: 4
                                // lineStyle: {color: '#0faecb',}
                            }
                        },
                        data: _this.series
                    }]
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
        }
    };
</script>

<style scoped>
    .hollowPie {
        width: 100%;
        height: 100%;
    }
</style>