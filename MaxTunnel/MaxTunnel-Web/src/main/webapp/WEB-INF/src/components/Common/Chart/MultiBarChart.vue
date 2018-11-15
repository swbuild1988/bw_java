<template>
    <div class="MultiBar" :id="id"></div>
</template>
<style scoped>
.MultiBar {
    position: relative;
    height: 100%;
    width: 100%;
}
</style>
<script>
export default {
    name: "MultiBar",
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
                calculable: true,
                xAxis: [
                    {
                        type: "category",
                        axisTick: { show: false },
                        data: []
                    }
                ],
                yAxis: [
                    {
                        type: "value"
                    }
                ],
                series: []
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

            // 因为是多数据的格式，所以得到的结果样式为
            // [{
            //     key: "古城大街",
            //     val: [
            //         {
            //             key: "结构缺陷"",
            //             val: 10
            //         },
            //         {
            //             key: "设备缺陷",
            //             val: 10
            //         }
            //     ]
            // },
            // {
            //     key: "试验路",
            //     val: [
            //         {
            //             key: "结构缺陷",
            //             val: 20
            //         },
            //         {
            //             key: "设备缺陷",
            //             val: 30
            //         }
            //     ]
            // }，
            // ...]
            // 对上述数据进行转换
            // 使数据对柱状图有效

            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    _this.option.xAxis[0].data = this.getDataX(data);
                    _this.option.series = [];
                    let tmp_series = this.getSeries(data);
                    tmp_series.forEach(element => {
                        _this.option.series.push(element);
                    });
                    _this.myChart.setOption(_this.option);
                }
            });
        },
        getDataX(data) {
            let xData = [];
            data.forEach(element => {
                xData.push(element.key);
            });
            return xData;
        },
        getSeries(data) {
            let series = [];
            for (let i = 0; i < data.length; i++) {
                for (let j = 0; j < data[i].val.length; j++) {
                    if (i == 0) {
                        let tmp_series = {
                            name: data[i].val[j].key,
                            type: "bar",
                            data: []
                        };
                        series.push(tmp_series);
                    }
                    series[j].data.push(data[i].val[j].val);
                }
            }
            return series;
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
            // setInterval(() => {
            //     _this.fetchData(_this.requestUrl);
            // }, _this.intervalTime);
        }
    }
};
</script>
