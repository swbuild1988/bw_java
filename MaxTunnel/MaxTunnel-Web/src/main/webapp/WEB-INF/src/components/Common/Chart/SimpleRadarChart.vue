<template>
    <div class="SimpleRadar" :id="id"></div>
</template>
<style scoped>
.SimpleRadar {
    position: relative;
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
        requestUrl: {
            type: String
        },
        parameters: {
            type: Object
        }
    },
    data() {
        return {
            myChart: {},
            option: {
                backgroundColor: "#eeeeee",
                title: {
                    text: "Radar",
                    x: "right",
                    textStyle: {
                        color: "#000"
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
                            color: "#fff",
                            backgroundColor: "#999",
                            borderRadius: 3,
                            padding: [3, 5]
                        }
                    },
                    indicator: [
                        { name: "indicator1", max: 100 },
                        { name: "indicator2", max: 100 },
                        { name: "indicator3", max: 100 },
                        { name: "indicator4", max: 100 },
                        { name: "indicator5", max: 100 },
                        { name: "indicator6", max: 100 }
                    ]
                },
                series: [
                    {
                        type: "radar",
                        data: [
                            {
                                value: [],
                                name: this.title.text,
                                label: {
                                    normal: {
                                        show: true
                                    },
                                    emphasis: {
                                        show: true
                                    }
                                },
                                areaStyle: {
                                    normal: {
                                        opacity: 0.9,
                                        color: {
                                            // 完成的圆环的颜色
                                            colorStops: [
                                                {
                                                    offset: 0,
                                                    color:
                                                        "rgba(160,255,238,0.3)" // 0% 处的颜色
                                                },
                                                {
                                                    offset: 1,
                                                    color: "rgba(44,255,204,1)" // 100% 处的颜色
                                                }
                                            ]
                                        }
                                    }
                                }
                            }
                        ]
                    }
                ]
            }
        };
    },
    mounted() {
        this.init();
        this.refreshData();
    },
    methods: {
        init() {
            this.drawRadar();
            this.fetchData(this.requestUrl);
        },

        drawRadar() {
            let _this = this;
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            // 加载默认参数
            _this.myChart.setOption(_this.option);
            // 加载新的参数
            if (_this.parameters.option) {
                _this.myChart.setOption(_this.parameters.option);
            }
            window.addEventListener("resize", _this.myChart.resize);
        },

        fetchData(requestUrl) {
            let _this = this;

            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    // var tmpMax = 0;
                    // let newData = [
                    //     {
                    //         name: this.title.text,
                    //         value: []
                    //     }
                    // ];
                    // let tmpIndicator = [];

                    // for (const iterator of data) {
                    //     newData[0].value.push(iterator.val);
                    //     if (iterator.val > tmpMax) tmpMax = iterator.val;
                    // }
                    // for (const iterator of data) {
                    //     tmpIndicator.push({
                    //         name: iterator.key,
                    //         // 最大值大个10%
                    //         max: tmpMax * 1.1
                    //     });
                    // }

                    // _this.option.title.text = _this.title.text;
                    // _this.option.title.textStyle = {
                    //     fontWeight: "normal",
                    //     color: _this.title.color,
                    //     fontSize: "20"
                    // };
                    // _this.option.legend = {
                    //     show: false,
                    //     data: [this.title.text]
                    // };
                    _this.option.radar.indicator = tmpIndicator;
                    _this.option.series[0].data = newData;

                    _this.myChart.setOption(_this.option);
                }
            });
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
            setInterval(() => {
                _this.fetchData(_this.requestUrl);
            }, _this.intervalTime);
        }
    }
};
</script>
