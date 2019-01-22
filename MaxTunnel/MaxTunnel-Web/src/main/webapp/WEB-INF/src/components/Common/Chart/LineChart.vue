<template>
    <div>
        <div
            class="Line"
            :id=id
        ></div>
    </div>
</template>

<script>
export default {
    name: 'Line-Chart',
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
            myChart: "",
            legendData: [],
            serises: [],
            xData: [],
            option: {},
        }
    },
    components: {},
    mounted() {
        this.init();
        this.resizeChart();
    },
    methods: {
        init() {
            this.drawLine();
            this.fetchData(this.requestUrl);
            this.refreshData();
        },
        resizeChart() {
            let _this = this;
            window.addEventListener("resize", function () {
                _this.drawLine();
                _this.myChart.resize();
                _this.fetchData(_this.requestUrl);
            });
        },
        drawLine() {
            let _this = this;
            _this.option = {
                title: {
                    text: "",
                    top: "left",
                    textStyle: {
                        color: "#fff",
                        fontSize: window.innerHeight * 0.018,
                    },
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    textStyle: {
                        color: '#ccc',
                        fontSize: window.innerHeight * 0.012,
                    },
                    top: "center",
                    left: "center"
                },
                grid: {
                    top: '30%',
                    left: '10%',
                    right: '10%',
                    bottom: '10%'
                },
                xAxis: {
                    type: 'category',
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        },
                    },
                    axisLabel: { fontSize: window.innerHeight * 0.010 },
                    data: []
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        margin: 2,
                        lineStyle: {
                            color: '#fff'
                        },
                    },
                    axisLabel: {
                        fontSize: window.innerHeight * 0.010,
                        formatter: function (value, index) {
                            if (value >= 10000 && value < 100000) {
                                value = value / 10000 + "万";
                            }
                            else if (value >= 100000 && value < 1000000) {
                                value = value / 1000000 + "十万";
                            }
                            else if (value >= 10000000) {
                                value = value / 10000000 + "千万";
                            }
                            return value;
                        },                    },
                },
                series: [{
                    data: [],
                    type: 'line',
                    smooth: true
                }]
            };
            _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
            _this.myChart.setOption(_this.option);
            // 加载新的参数
            if (_this.parameters.option) {
                _this.myChart.setOption(_this.parameters.option);
            }
            window.addEventListener("resize", this.myChart.resize);
        },
        fetchData(requestUrl) {
            let _this = this;
            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    _this.serises = [];
                    _this.legendData = [];
                    _this.xData = [];
                    let yMin = 0;
                    let yMax = 0;
                    data.forEach(a => {
                        var temp = {};
                        temp.name = a.key;
                        temp.type = 'line';
                        temp.smooth = true;
                        _this.legendData.push(a.key);
                        let tempData = [];
                        a.val.filter(b => tempData.push(parseFloat(b.val.toFixed(2))));
                        temp.data = tempData;
                        yMin = (Math.min.apply(null, _this.xData) < yMin || yMin == 0) ? Math.min.apply(null, temp.data) : yMin;
                        // yMax=  (Math.max.apply(null, _this.xData) > yMax || yMax == 0) ? Math.max.apply(null,  temp.data) : yMax;
                        _this.serises.push(temp);
                    })
                    data[0].val.filter(a => {
                        _this.xData.push(a.key)
                    });
                    _this.myChart.setOption({
                        series: _this.serises,
                        xAxis: {
                            data: _this.xData,
                        },
                        yAxis: {
                            min: yMin,
                        },
                        legend: {
                            data: _this.legendData,
                            top: "10%",
                            left: 'center'
                        },
                    })
                }
            })
        },
        refreshData() {
            let _this = this;
            // setInterval(() => _this.fetchData(_this.requestUrl + _this.period), 5000)
        },
    }
}

</script>

<style scoped>
.Line {
    width: 100%;
    height: 100%;
}
</style>
