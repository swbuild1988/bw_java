<template>
  <div class='SimpleBar' :id="id"></div>
</template>

<script>
export default {
    name: "SimpleBar",
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
            xData: [],
            yData: [],
            series: [],
            option: {
                color: ["#61a0a8"],
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
                            interval: 0
                        }
                    }
                ],
                series: [
                    {
                        type: "bar",
                        itemStyle: {
                            normal: {
                                color: new this.$echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        { offset: 0, color: "#346699" },
                                        { offset: 1, color: "#70ABE8" }
                                    ]
                                )
                            },
                            emphasis: {
                                color: new this.$echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        { offset: 0, color: "#70ABE8" },
                                        { offset: 1, color: "#346699" }
                                    ]
                                )
                            }
                        }
                    }
                ]
            }
        };
    },
    components: {},
    mounted() {
        this.init();
        this.refreshData();
    },
    methods: {
        init() {
            this.drawBar();
            this.fetchData(this.requestUrl);
        },

        drawBar() {
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
                    console.log("simple bar data", newData);
                    if (
                        JSON.stringify(newData.xData) !=
                            JSON.stringify(_this.xData) ||
                        JSON.stringify(newData.yData) !=
                            JSON.stringify(_this.xData)
                    ) {
                        _this.xData = newData.xData;
                        _this.yData = newData.yData;
                        _this.myChart.setOption({
                            xAxis: { data: _this.xData },
                            series: { data: _this.yData }
                        });
                    }
                }
                // if ("getToolDetail" == requestUrl) {
                //     _this.xData = ["借出工具", "损坏工具", "库存工具"];
                //     _this.series = [
                //         { name: "仪表盘", type: "bar", data: [22, 3, 10] },
                //         { name: "扳手", type: "bar", data: [23, 4, 14] },
                //         { name: "温度计", type: "bar", data: [0, 2, 3] },
                //         { name: "湿度计", type: "bar", data: [2, 0, 3] },
                //         { name: "螺丝刀", type: "bar", data: [6, 2, 0] }
                //     ];
                //     _this.yData = [145, 40, 334];
                //     _this.myChart.series = [];
                //     _this.myChart.setOption({
                //         series: []
                //     });
                //     _this.myChart.setOption({
                //         xAxis: { data: _this.xData },
                //         series: _this.series
                //     });
                // }
            });
        },
        //定时刷新数据
        refreshData() {
            // let _this = this;
            // setInterval(() => {
            //     _this.fetchData(_this.requestUrl);
            // }, _this.intervalTime);
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
