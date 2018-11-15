<template>
    <div class="MultiRadar" :id="id"></div>
</template>
<style scoped>
.MultiRadar {
    position: relative;
    height: 100%;
    width: 100%;
}
</style>
<script>
export default {
    name: "MultiRadar",
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
                radar: {
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
                                value: [10, 20, 30, 40, 50, 60]
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

            // 因为是多数据的格式，所以得到的结果样式为
            // [{
            //     key: "安全",
            //     val: [
            //         {
            //             key: "总数",
            //             val: 10
            //         },
            //         {
            //             key: "运行中",
            //             val: 10
            //         },
            //         {
            //             key: "故障",
            //             val: 10
            //         }
            //     ]
            // },
            // {
            //     key: "视频",
            //     val: [
            //         {
            //             key: "总数",
            //             val: 20
            //         },
            //         {
            //             key: "运行中",
            //             val: 30
            //         },
            //         {
            //             key: "故障",
            //             val: 40
            //         }
            //     ]
            // }，
            // ...]
            // 对上述数据进行转换
            // 使数据对雷达图有效

            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    _this.option.radar.indicator = this.getIndicator(data);
                    _this.option.series[0].data = [];
                    let tmp_series = this.getSeriesData(data);
                    tmp_series.forEach(element => {
                        _this.option.series[0].data.push(element);
                    });

                    _this.myChart.setOption(_this.option);
                }
            });
        },
        getIndicator(data) {
            let indicator = [];
            data.forEach(element => {
                indicator.push({ name: element.key });
            });
            return indicator;
        },
        getSeriesData(data) {
            let seriesData = [];
            for (let i = 0; i < data.length; i++) {
                for (let j = 0; j < data[i].val.length; j++) {
                    if (i == 0) {
                        let tmp_seriesData = {
                            name: data[i].val[j].key,
                            value: []
                        };
                        seriesData.push(tmp_seriesData);
                    }
                    seriesData[j].value.push(data[i].val[j].val);
                }
            }
            return seriesData;
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
