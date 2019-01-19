<template>
  <div class="SimplePie" :id="id" ref="pieChart"></div>
</template>
<style scoped>
.SimplePie {
    position: relative;
    height: 100%;
    width: 100%;
}
</style>
<script>
export default {
    name: "SimplePie",
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
        titleSize: {
            default: '10%',
            type: String
        },
        seriesSize: {
            default: '6%',
            type: String
        }
    },
    data() {
        return {
            myChart: {},
            option: {
                series: [
                    {
                        type: "pie",
                        radius: '50%',
                        center: ['50%','50%'],
                        data: []
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
            _this.myChart.setOption({
                title: {
                    textStyle: {
                        fontSize: _this.getFontSize(this.titleSize)
                    }
                },
                series: {
                    label: {
                        normal: {
                            textStyle: {
                                fontSize: _this.getFontSize(this.seriesSize)
                            }
                        }
                    }
                }
            })
            window.addEventListener("resize", _this.myChart.resize);
            window.addEventListener('resize', _this.init)
        },
        fetchData(requestUrl) {
            let _this = this;
            if (this.parameters.params) {
                _this.axios
                    .post(requestUrl, this.parameters.params)
                    .then(result => {
                        let { code, data } = result.data;
                        var tempCount = 0;
                        data.filter(function(item) {
                            tempCount += item.val;
                        });
                        let newData = data.map(curObj => {
                            return {
                                value: curObj.val,
                                name:
                                    curObj.key +
                                    " " +
                                    ((100 * curObj.val) / tempCount).toFixed(
                                        2
                                    ) +
                                    "%"
                            };
                        });
                        _this.option.series[0].data = newData;
                        _this.myChart.setOption(_this.option);
                    });
            } else {
                _this.axios.get(requestUrl).then(result => {
                    let { code, data } = result.data;
                    if (code == 200) {
                        _this.option.series[0].data = [];
                        var tempCount = 0;
                        data.filter(function(item) {
                            tempCount += item.val;
                        });
                        _this.option.series[0].data = data.map(curObj => {
                            return {
                                value: curObj.val,
                                name:
                                    curObj.key +
                                    " " +
                                    ((100 * curObj.val) / tempCount).toFixed(
                                        2
                                    ) +
                                    "%"
                            };
                        });
                        _this.myChart.setOption(_this.option);
                    } else {
                        if ("getCountLendData" == requestUrl) {
                            _this.option = {
                                series: {
                                    data: [
                                        { value: 31, name: "电压表" },
                                        { value: 20, name: "有害气体探测器" },
                                        { value: 21, name: "扳手" },
                                        { value: 9, name: "温度计" },
                                        { value: 11, name: "螺丝刀" },
                                        { value: 8, name: "其他" }
                                    ]
                                }
                            };
                            _this.myChart.setOption(_this.option);
                        }
                        if ("getCountBreakData" == requestUrl) {
                            _this.option = {
                                series: {
                                    data: [
                                        { value: 21, name: "万能表" },
                                        { value: 11, name: "螺丝刀" },
                                        { value: 31, name: "电压表" },
                                        { value: 9, name: "温度计" },
                                        { value: 20, name: "应力计" },
                                        { value: 8, name: "其他" }
                                    ]
                                }
                            };
                            _this.myChart.setOption(_this.option);
                        }
                    }
                });
            }
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
            if (_this.parameters.timer) {
                // let { intervalId, intervalTime } = _this.parameters.timer;
                // console.log("intervalTime",intervalTime)
                // intervalId = setInterval(() => {
                // _this.option.series[0].data.forEach(a => (a.val = 1));
                // _this.myChart.setOption(_this.option);
                // _this.fetchData(_this.requestUrl);
                // }, intervalTime);
                setInterval(() => {
                    _this.option.series[0].data.forEach(a => (a.val = 1));
                    _this.myChart.setOption(_this.option);
                    _this.fetchData(_this.requestUrl);
                }, _this.parameters.timer);
            }
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
                    let height = this.$refs.pieChart.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        }
    }
};
</script>
