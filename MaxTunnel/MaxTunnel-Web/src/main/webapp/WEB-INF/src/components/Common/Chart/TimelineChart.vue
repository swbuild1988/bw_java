<template>
    <div
        class='TimelineChart'
        :id=id
        ref="element"
    ></div>
</template>

<script>
export default {
    name: 'TimelineChart',
    props: {
        id: {
            default: '',
            type: String
        },
        title: {
            default: 'TimelineChart',
            type: String
        },
        // 有几个球
        group: {
            default: [],
            type: Array
        },
        // 时间线
        timeline: {
            default: [],
            type: Array
        },
        // 显示的数据
        series: {
            default: [],
            type: Array
        },
        // x,y,z的单位
        units: {
            default: {
                x: "",
                y: "",
                z: ""
            },
            type: Object
        },
        // x，y，z，以及group的标签显示
        labels: {
            default: {
                x: "",
                y: "",
                z: "",
                group: ""
            },
            type: Object
        }
    },
    data() {
        return {
            option: {
                title: {
                    text: ""
                }
            },
            myChart: null,
        }
    },
    watch: {
        group: {
            handler(newValue, oldValue) {

            },
            immediate: true
        },
        timeline: {
            handler(newValue, oldValue) {

            },
            immediate: true
        },
        series: {
            handler(newValue, oldValue) {
                if (newValue.length && newValue.length > 0) this.draw();
            },
            deep: true,
            immediate: true
        },
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let _this = this;
            _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
            _this.option.title.text = _this.title;
            _this.myChart.setOption(_this.option);
            window.addEventListener('resize', this.myChart.resize);
            window.addEventListener('resize', this.resize);
        },
        resize() {
            this.draw();
        },
        draw() {
            let _this = this;

            // Schema:
            var schema = [
                { name: 'x', index: 0, text: this.labels.x, unit: this.units.x },
                { name: 'y', index: 1, text: this.labels.y, unit: this.units.y },
                { name: 'z', index: 2, text: this.labels.z, unit: this.units.z },
                { name: 'group', index: 3, text: this.labels.group, unit: '' },
            ];

            var itemStyle = {
                normal: {
                    opacity: 0.8,
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            };

            this.option = {
                baseOption: {
                    timeline: {
                        axisType: 'category',
                        orient: 'vertical',
                        autoPlay: true,
                        inverse: true,
                        playInterval: 5000,
                        left: null,
                        right: 0,
                        top: '5%',
                        bottom: '2%',
                        width: '7%',
                        height: null,
                        label: {
                            normal: {
                                textStyle: {
                                    color: '#999'
                                }
                            },
                            emphasis: {
                                textStyle: {
                                    color: '#fff'
                                }
                            }
                        },
                        symbol: 'none',
                        lineStyle: {
                            color: '#555'
                        },
                        checkpointStyle: {
                            color: '#bbb',
                            borderColor: '#777',
                            borderWidth: 2
                        },
                        controlStyle: {
                            showNextBtn: false,
                            showPrevBtn: false,
                            normal: {
                                color: '#666',
                                borderColor: '#666'
                            },
                            emphasis: {
                                color: '#aaa',
                                borderColor: '#aaa'
                            }
                        },
                        data: []
                    },
                    backgroundColor: 'rgba(0, 0, 0, 0)',
                    title: [{
                        text: this.timeline[0],
                        textAlign: 'center',
                        left: '63%',
                        top: '55%',
                        textStyle: {
                            fontSize: _this.getFontSize('12%'),
                            color: 'rgba(255, 255, 255, 0.7)'
                        }
                    }, {
                        text: this.title,
                        left: 'center',
                        top: '2%',
                        textStyle: {
                            color: '#aaa',
                            fontWeight: 'normal',
                            fontSize: _this.getFontSize('10%')
                        }
                    }],
                    tooltip: {
                        padding: 5,
                        backgroundColor: '#222',
                        borderColor: '#777',
                        borderWidth: 1,
                        formatter: function (obj) {
                            var value = obj.value;
                            var res = schema[schema.length - 1].text + '：' + value[schema.length - 1] + '<br>';
                            for (let i = 0; i < schema.length - 1; i++) {
                                res += schema[i].text + '：' + value[i] + schema[i].unit + '<br>'
                            }
                            _this.getDpr();
                            return res;
                        }
                    },
                    grid: {
                        top: '15%',
                        containLabel: true,
                        left: '3%',
                        right: '10%',
                        bottom: '8%'
                    },
                    xAxis: {
                        type: 'value',
                        name: this.labels.x,
                        nameGap: 25,
                        nameLocation: 'middle',
                        nameTextStyle: {
                            fontSize: _this.getFontSize('5%')
                        },
                        splitLine: {
                            show: false
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#ccc'
                            }
                        },
                        axisLabel: {
                            formatter: '{value} ' + this.units.x
                        }
                    },
                    yAxis: {
                        type: 'value',
                        name: this.labels.y,
                        nameTextStyle: {
                            color: '#ccc',
                            fontSize: _this.getFontSize('5%')
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#ccc'
                            }
                        },
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value} ' + this.units.y
                        }
                    },
                    visualMap: [
                        {
                            show: false,
                            dimension: 3,
                            categories: this.group,
                            calculable: true,
                            precision: 0.1,
                            textGap: 30,
                            textStyle: {
                                color: '#ccc'
                            },
                            inRange: {
                                color: (function () {
                                    var colors = ['#bcd3bb', '#e88f70', '#edc1a5', '#9dc5c8', '#e1e8c8', '#7b7c68', '#e5b5b5', '#f0b489', '#928ea8', '#bda29a'];
                                    return colors.concat(colors);
                                })()
                            }
                        }
                    ],
                    series: [
                        {
                            type: 'scatter',
                            itemStyle: itemStyle,
                            data: _this.series[0],
                            symbolSize: function (val) {
                                return _this.sizeFunction(val[2]);
                            }
                        }
                    ],
                    animationDurationUpdate: 500,
                    animationEasingUpdate: 'quinticInOut'
                },
                options: []
            };

            for (var n = 0; n < this.timeline.length; n++) {
                this.option.baseOption.timeline.data.push(this.timeline[n]);
                this.option.options.push({
                    title: {
                        show: true,
                        text: this.timeline[n]
                    },
                    series: {
                        name: this.timeline[n],
                        type: 'scatter',
                        itemStyle: itemStyle,
                        data: this.series[n],
                        symbolSize: function (val) {
                            return _this.sizeFunction(val[2]);
                        }
                    }
                });
            }

            this.myChart.setOption(this.option);
        },
        sizeFunction(x) {
            var min = Math.min.apply(null, this.series.map(o1 => {
                return Math.min.apply(null, o1.map(o2 => {
                    return parseFloat(o2[2]);
                }));
            }));
            var max = Math.max.apply(null, this.series.map(o1 => {
                return Math.max.apply(null, o1.map(o2 => {
                    return parseFloat(o2[2]);
                }));
            }));

            // 最小的5%，最大的15%
            var y = 5 + (x - min) / (max - min) * (15 - 5);
            var size = this.getFontSize(y + '%');
            return size;
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
    },
}
</script>

<style scoped>
.TimelineChart {
    width: 100%;
    height: 100%;
}
</style>
