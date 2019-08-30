<template>
    <div class="SimpleRadar" :id="id" ref="element"></div>
</template>
<style scoped>
.SimpleRadar {
    height: 100%;
    width: 100%;
}
</style>
<script>
export default {
    name: "SimpleRadar",
    props: {
        id: {
            type: String
        },
        parameters: {
            type: Object
        },
        titleSize: {
            default: "7%",
            type: String
        },
        seriesSize: {
            default: "5%",
            type: String
        },
        radarIndicator: {
            required: true,
            type: Array
        },
        seriesData:{
            required: true,
            type: Array
        },
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
    watch:{
        seriesData(){
            this.drawRadar()
        },
        radarIndicator(){
            this.drawRadar()
        }
    },
    methods: {
        init() {
            this.drawRadar();
        },

        drawRadar() {
            let _this = this;
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );

            _this.option = {
                title: {
                    x: "left",
                    textStyle: {
                        fontSize: _this.getFontSize(_this.titleSize)
                        // color: "#000"
                    }
                },
                tooltip: {
                    trigger: "item"
                },
                legend: {
                    x: "center",
                    data: ["legend"]
                },
                radar: {
                    name: {
                        textStyle: {
                            color: "#00b6e4",
                            fontSize: _this.getFontSize(this.seriesSize),
                            borderRadius: 3,
                            padding: [3, 5]
                        }
                    },
                    indicator: this.radarIndicator,
                    axisLine: {
                        lineStyle: {
                            color: "#449cff"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            color: "#449cff"
                        }
                    }
                },
                series: [
                    {
                        type: "radar",
                        areaStyle: {
                            normal: {
                                color: {
                                    type: "linear",
                                    x: 0,
                                    y: 0,
                                    x2: 0,
                                    y2: 1,
                                    colorStops: [
                                        {
                                            offset: 0,
                                            color: "#44ff86"
                                        },
                                        {
                                            offset: 1,
                                            color: "#0060ff"
                                        }
                                    ],
                                    globalCoord: false
                                }
                            }
                        },
                        lineStyle: {
                            normal: {
                                color: "#00ffff",
                                width: 2
                            }
                        },
                        data: this.seriesData
                    }
                ]
            };
            // 加载默认参数
            _this.myChart.setOption(_this.option);

            // 加载新的参数
            if (_this.parameters.option) {
                _this.myChart.setOption(_this.parameters.option);
            }
            window.addEventListener("resize", _this.myChart.resize);
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
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
