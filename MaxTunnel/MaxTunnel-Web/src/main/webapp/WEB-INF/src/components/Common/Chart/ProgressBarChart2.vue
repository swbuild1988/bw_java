<template>
    <div class="progressBar" :id=id ref="element"></div>
</template>

<script>
    import {
        TunnelService
    } from '../../../services/tunnelService';

    export default {
        name: 'progress-bar-chart',
        props: {
            id: {
                type: String
            },
            unit: {
                type: String,
                default: "",
            },
            parameters: {
                type: Object
            },
            finished: {
                type: Array,
                default: []
            },
            total: {
                type: Array,
                default: []
            },
            labels: {
                type: Array,
                default: []
            }
        },
        data() {
            return {
                myChart: "",
                legendData: [],
                serises: [],
                serises_border: [],
                xData: [],
                yAxis_name: [],
                yAxis_value: [],
                option: {},
                yMin: 0,
            }
        },
        components: {},
        mounted() {
            this.init();
            this.resizeChart();
        },
        watch: {
            finished() {
                this.drawProgressBar();
            }
        },
        methods: {
            init() {
                this.drawProgressBar();
            },
            resizeChart() {
                let _this = this;
                window.addEventListener("resize", function () {
                    _this.drawProgressBar();
                    _this.myChart.resize();
                });
            },
            drawProgressBar() {
                this.Log.info("drawProgressBar")
                let _this = this;
                _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
                _this.option = {
                        title: {
                            text: "",
                            left: "center",
                            textStyle: {
                                color: "#fff",
                                fontSize: _this.getFontSize("8%"),
                                align: "center"
                            }
                        },
                        grid: {
                            height: "80%",
                            top: "15%",
                            bottom: "5%",
                            width: "60%",
                            left: "5%"
                        },
                        xAxis: {
                            show: false
                        },
                        yAxis: [{
                                show: false,
                                data: [],
                                inverse: true,
                                axisLine: {
                                    show: false
                                },
                                splitLine: {
                                    show: false
                                },
                                axisTick: {
                                    show: false
                                },
                                axisLabel: {
                                    color: '#fff',
                                    fontSize: this.getFontSize('6%'),
                                },
                            },
                            {
                                show: true,
                                inverse: true,
                                data: [],
                                axisLabel: {
                                    textStyle: {
                                        fontSize: this.getFontSize('5%'),
                                        color: '#fff',

                                    },
                                },
                                axisLine: {
                                    show: false
                                },
                                splitLine: {
                                    show: false
                                },
                                axisTick: {
                                    show: false
                                },

                            }
                        ],
                        series: [{
                                name: '条',
                                type: 'bar',
                                yAxisIndex: 0,
                                data: [],
                                barWidth: "50%",
                                itemStyle: {
                                    normal: {
                                        barBorderRadius: 10,
                                        color: new _this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                                offset: 0,
                                                color: '#11c0ff'
                                            },
                                            {
                                                offset: 1,
                                                color: '#9bedff'
                                            }
                                        ]),
                                    }
                                },
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'inside',
                                        formatter: '{c}%',
                                        fontSize: this.getFontSize('5%'),
                                    }
                                },
                            },
                            {
                                name: '框',
                                type: 'bar',
                                yAxisIndex: 1,
                                barGap: '-100%',
                                data: [],
                                barWidth: "50%",
                                itemStyle: {
                                    normal: {
                                        color: 'none',
                                        borderColor: '#00c1de',
                                        borderWidth: 2,
                                        barBorderRadius: 10,
                                    }
                                }
                            }
                        ]
                    },
                    _this.Log.info("option", _this.option)
                _this.myChart.setOption(_this.option);
                // 加载新的参数
                if (_this.parameters.option) {
                    _this.Log.info("parameters option", _this.parameters.option)
                    _this.myChart.setOption(_this.parameters.option);
                }
                window.addEventListener("resize", this.myChart.resize);

                // 加载数据
                _this.serises = [];
                _this.yAxis_name = [];
                _this.yAxis_value = [];
                _this.serises_border = [];

                for (let i = 0; i < _this.labels.length; i++) {
                    _this.yAxis_name.push(_this.labels[i])
                    _this.serises_border.push(100)
                    _this.yAxis_value.push(_this.total[i] + _this.unit)
                    let percent = _this.total[i] == 0 ? 0 : _this.finished[i] / _this.total[i] * 100
                    _this.serises.push(Math.round(percent))
                }
                _this.myChart.setOption({
                    series: [{
                        data: _this.serises
                    }, {
                        data: _this.serises_border
                    }],
                    yAxis: [{
                        data: []
                    }, {
                        data: _this.yAxis_name
                    }],
                })
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
    .progressBar {
        width: 100%;
        height: 100%;
    }
</style>