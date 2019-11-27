<template>
    <div :id="id" class="simpleGauge" ref="gaugeChart"></div>
</template>

<script>
export default {
    name: "simple-gauge",
    props: {
        id: {
            type: String
        },
        parameters: {
            type: Object
        },
        gaugeData: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            myChart: {},
            xData: [],
            yData: [],
            series: [],
            defaultOption: {},
            option: {}
        };
    },
    components: {},
    mounted() {
        this.init();
        this.resizeChart();
    },
    watch:{
        gaugeData: {
            handler(newValue, oldValue) {
                if (newValue.length && newValue.length > 0) this.updateSimpGauge(newValue);
            },
            deep: true,
            immediate: true
        }
    },
    methods: {
        init() {
            this.drawSimpleGauge();
        },
        resizeChart() {
            let _this = this;
            window.addEventListener("resize", function() {
                _this.drawSimpleGauge();

                _this.myChart.resize();
            });
        },
        drawSimpleGauge() {
            let _this = this;
            _this.option = {
                title: {
                    text: "",
                    subtext: "",
                    textStyle: {
                        fontSize: window.innerHeight * 0.012,
                        color: "#fff"
                    },
                    subtextStyle: {
                        color: "#fff",
                        fontSize: window.innerHeight * 0.013
                    },
                    bottom: "-8",
                    left: "center",
                },
                backgroundColor: null,
                series: [
                    {
                        name: "",
                        type: "gauge",
                        min: 0,
                        max: 100,
                        splitNumber: 5,
                        radius: "90%",
                        title : {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder',
                                fontSize: this.getFontSize('10%'),
                                color: '#FFFF09',
                                shadowColor : '#fff', //默认透明
                                shadowBlur: 3
                            },
                            
                            offsetCenter:[0,'6%']
                        },
                        axisLine: {
                            // 坐标轴线
                            lineStyle: {
                                // 属性lineStyle控制线条样式
                                // width: 8,
                                width: window.innerHeight * 0.007,
                                shadowBlur: 0,
                                color: [
                                    [0.2, "#0066FF"],
                                    [0.8, "#FFFF00"],
                                    [1, "#FF0000"]
                                ]
                            }
                        },
                        axisTick: {
                            // 坐标轴小标记
                            lineStyle: {
                                // 属性lineStyle控制线条样式
                                color: "auto"
                            }
                        },
                        axisLabel: {
                            // distance: 0,
                            fontSize: window.innerHeight * 0.012,
                            formatter: function(value, index) {
                                value = parseFloat(value.toFixed(1));
                                return value;
                            }
                        },
                        splitLine: {
                            // 分隔线
                            length: window.innerWidth * 0.0065,
                            lineStyle: {
                                // 属性lineStyle控制线条样式
                                color: "auto"
                            }
                        },
                        detail: {
                            offsetCenter: [0, "25%"],
                            fontSize: this.getFontSize('10%')
                        },
                        //指针
                        // pointer: {
                        //   width:4,
                        // },
                        itemStyle: {
                            color: "#fff",
                            normal: {
                                shadowBlur: 6
                            }
                        },
                        // data: _this.gaugeData
                        data: [{ value: 12, name: "最高温度" }]
                    }
                ]
            };
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            // 加载默认参数
            _this.myChart.setOption(_this.option);

            // 加载新的参数
            // if (_this.parameters.option) {
            //     _this.myChart.setOption(_this.parameters.option);
            // }
            this.updateSimpGauge(this.gaugeData);
        },
        updateSimpGauge(newValue){
            if (newValue) {
                this.myChart.setOption(this.parameters.option);
                this.myChart.setOption({
                    series: {
                        data: newValue,
                        min: this.parameters.option.series.min,
                        max: this.parameters.option.series.max
                    }
                });
            }
        },
         getFontSize(val) {
            if (typeof val == "number") return val;

            if (typeof val == "string") {
                if (val.indexOf("%") > 0) {
                    var tmp = parseFloat(val.replace("%", "")) / 100;
                    let height = this.$refs.gaugeChart.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        }
    }
};
</script>

<style scoped>
.simpleGauge {
    width: 100%;
    height: 100%;
}
</style>
