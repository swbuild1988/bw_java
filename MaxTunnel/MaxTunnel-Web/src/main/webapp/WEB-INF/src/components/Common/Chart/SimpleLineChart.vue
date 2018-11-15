<template>
  <div class='SimpleLine' :id=id></div>
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
        }
    },
    data() {
        return {
            myChart: {},
            xData: [],
            yData: [],
            series: [],
            option: {
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
        this.refreshData();
    },
    methods: {
        init() {
            this.drawComplexBar();
            this.fetchData(this.requestUrl);
        },
        drawComplexBar() {
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
            });
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
            // setInterval(() => {
            //   _this.fetchData(_this.requestUrl);
            // }, _this.intervalTime)
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
