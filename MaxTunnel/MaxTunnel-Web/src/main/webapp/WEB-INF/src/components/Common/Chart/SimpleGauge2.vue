<template>
    <div
        :id="id"
        class="simpleGauge"
    ></div>
</template>

<script>
export default {
    name: "simple-gauge",
    props: {
        id: {
            type: String
        },
        title: {
            type: String
        },
        min: {
            type: Number,
            default: 0
        },
        max: {
            type: Number,
            default: 100
        },
        value: {
            type: Number,
            default: 0
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
    mounted() {
        this.init();
    },
    watch: {
        value(newVal, oldVal) {
            if (newVal && newVal.length > 0) {
                this.drawBar()
            }
        }
    },
    methods: {
        init() {
            this.drawBar()
            window.addEventListener("resize", this.myChart.resize);
            window.addEventListener("resize", this.drawBar)
        },
        drawBar() {
            this.option = {
                series: [
                    {
                        name: '',
                        type: 'gauge',
                        min: this.min,
                        max: this.max,
                        splitNumber: 5,
                        radius: '90%',
                        axisLine: { // 坐标轴线
                            lineStyle: {
                                color: [[0.2, '#c23531'], [0.8, '#63869e'], [1, '#c23531']]
                            }
                        },
                        detail: {
                            formatter: '{value}%',
                            rich: {}
                        },
                        data: [{
                            value: (this.max + this.min) / 2,
                            name: this.title
                        }]
                    }
                ]
            };
            var defaultOption = {
                axisTick: {
                    length: window.innerWidth * 0.5
                },
                title: {
                    textStyle: {
                        fontSize: window.innerHeight * 0.012,
                        color: "#fff"
                    }
                },
                series: {
                    axisLine: {
                        lineStyle: { // 属性lineStyle控制线条样式
                            width: window.innerHeight * 0.005,
                        }
                    },
                    axisTick: { // 坐标轴小标记
                        length: window.innerWidth * 0.007, // 属性length控制线长
                    },
                    axisLabel: {
                        fontSize: window.innerHeight * 0.014,
                    },
                    splitLine: {
                        length: window.innerWidth * 0.01,
                    },
                    detail: {
                        fontSize: window.innerHeight * 0.015,
                    },
                    title: {
                        textStyle: {

                        }
                    },
                },
            };
            this.myChart = this.$echarts.init(
                document.getElementById(this.id)
            );
            // 加载默认参数
            this.myChart.setOption(this.option);
            // 加载默认参数
            this.myChart.setOption(defaultOption);
            // 加载新的参数
            if (this.parameters.option) {
                this.myChart.setOption(this.parameters.option);
            }
        }
    }
}
</script>

<style scoped>
.simpleGauge {
    width: 100%;
    height: 100%;
}
</style>
