<template>
    <div class="SimpleLine" :id="id"></div>
</template>

<script>
export default {
    name: "SimpleLine",
    props: {
        id: {
            type: String
        },
        requestUrl: {
            type: String
        },
        parameters: {
            type: Object
        },
        title: {
            type: String
        }
    },
    watch: {
        title() {
            this.redraw();
        }
    },
    data() {
        return {
            myChart: {},
            xData: [],
            yData: [],
            series: [],
            option: {
                title: {
                    text: this.title
                },
                textStyle: {
                    color: "rgba(255, 255, 255, 0.3)"
                },
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow"
                    }
                },
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "3%",
                    containLabel: true
                },
                xAxis: [
                    {
                        type: "category",
                        data: [],
                        axisLabel: {
                            interval: 0
                        },
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        axisLabel: {
                            interval: 1
                        }
                    }
                ],
                series: [
                    {
                        name: "",
                        type: "line",
                        data: []
                    }
                ]
            }
        };
    },
    components: {},
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.drawSimpleLine();
            this.fetchData();
            window.addEventListener("resize", this.myChart.resize);
            window.addEventListener("resize", this.redraw);
        },
        redraw() {
            this.drawSimpleLine();
            this.fetchData();
        },
        drawSimpleLine() {
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
        },
        fetchData() {
            if (this.parameters.params) {
                this.axios
                    .post(this.requestUrl, this.parameters.params)
                    .then(result => {
                        let { code, data } = result.data;
                        if (code == 200) {
                            let newData = data.reduce((init, item) => {
                                return {
                                    xData:
                                        init.xData == undefined
                                            ? [].concat(
                                                  new Date(item.key).format(
                                                      "yyyy-MM-dd"
                                                  )
                                              )
                                            : [].concat(
                                                  init.xData,
                                                  new Date(item.key).format(
                                                      "yyyy-MM-dd"
                                                  )
                                              ),
                                    yData:
                                        init.yData == undefined
                                            ? [].concat(item.val)
                                            : [].concat(init.yData, item.val)
                                };
                            }, {});
                            if (
                                JSON.stringify(newData.xData) !=
                                    JSON.stringify(this.xData) ||
                                JSON.stringify(newData.yData) !=
                                    JSON.stringify(this.xData)
                            ) {
                                this.xData = newData.xData;
                                this.yData = newData.yData;
                                this.myChart.setOption({
                                    xAxis: { data: this.xData },
                                    series: { data: this.yData }
                                });
                            }
                        }
                    });
            } else {
                this.axios.get(requestUrl).then(result => {
                    let { code, data } = result.data;
                    if (code == 200) {
                        let newData = data.reduce((init, item) => {
                            return {
                                xData:
                                    init.xData == undefined
                                        ? [].concat(item.key)
                                        : [].concat(init.xData, item.key),
                                yData:
                                    init.yData == undefined
                                        ? [].concat(item.val)
                                        : [].concat(init.yData, item.val)
                            };
                        }, {});
                        if (
                            JSON.stringify(newData.xData) !=
                                JSON.stringify(this.xData) ||
                            JSON.stringify(newData.yData) !=
                                JSON.stringify(this.xData)
                        ) {
                            this.xData = newData.xData;
                            this.yData = newData.yData;
                            this.myChart.setOption({
                                xAxis: { data: this.xData },
                                series: { data: this.yData }
                            });
                        }
                    }
                });
            }
        }
    }
};
</script>

<style scoped>
.SimpleLine {
    width: 100%;
    height: 100%;
}
</style>
