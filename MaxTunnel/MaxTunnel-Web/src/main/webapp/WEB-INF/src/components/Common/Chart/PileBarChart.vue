<template>
    <div
        class='pileBar'
        :id=id
        ref="pileBar"
    ></div>
</template>


<script>
import { ChartService } from '../../../services/chartService.js'

export default {
    name: "pile-bar-chart",
    props: {
        id: {
            type: String,
            request: true,
            default: ''
        },
        requestUrl: {
            default: ''
        },
        parameters: {
            type: Object
        },
        type: {
            type: String,
            default: 'post'
        }
    },
    data() {
        return {
            myChart: {},
            legendData: [],
            xData: [],
            series: [],
            option: {
                title: {
                    text: '',
                    fontSize: 25,
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data: ["严重", "一般", "提示", "致命", "总数"],
                    // bottom: 10,
                    left: 'right',
                    right: 0,
                    orient: 'vertical',

                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: [],
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [],
            },
        }
    },
    mounted() {
        this.init();
        this.refreshData();
    },
    methods: {
        init() {
            this.drawPileBar();
            this.fetchData();
        },
        drawPileBar() {
            let _this = this;
            _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
            
            // 加载默认参数
            _this.myChart.setOption(_this.option);
            // 加载新的参数
            if (_this.parameters.option) {
                _this.myChart.setOption(_this.parameters.option);
            }
            _this.myChart.setOption({
                legend: { textStyle: {fontSize: _this.getFontSize('8%')} },
                yAxis: { axisLabel: {fontSize: _this.getFontSize('8%')} },
                xAxis: { axisLabel: {fontSize: _this.getFontSize('8%')} },
            })
            window.addEventListener('resize', this.myChart.resize);
            window.addEventListener('resize', this.init)
        },
        fetchData() {
            let _this = this;
            if(this.type == 'get'){
                ChartService.getPileBarDataByGet(_this.requestUrl).then((result) => {
                    if (result) {
                        _this.serises = [];
                        _this.xData = [];
                        _this.legendData = Object.keys(result[0]);
                        for (var i = 0; i < result.length; i++) {
                            let tempdata = result[i];
                            _this.xData.push(result[i].key);
                            for (var j = 0; j < _this.legendData.length; j++) {
                                if (!_this.serises[j] && _this.legendData[j] != "key") {
                                    _this.serises[j] = {};
                                    _this.serises[j].name = _this.legendData[j];
                                    _this.serises[j].type = 'bar';
                                    _this.serises[j].stack = 'count';
                                    _this.serises[j].data = [];
                                }
                                for (var key in tempdata) {
                                    if (_this.legendData[j] == key && _this.legendData[j] != "key") {
                                        _this.serises[j].data[i] = tempdata[key];
                                    }
                                }
                            }
                        }
                        _this.myChart.setOption({
                            xAxis: { data: _this.xData },
                            legend: { data: _this.legendData },
                            series: _this.serises
                        })
                    }
                })
            }
            
            let prams = _this.parameters.prams;
            ChartService.getPileBarDataByGet(prams).then((result) => {
                if (result) {
                    _this.serises = [];
                    _this.xData = [];
                    _this.legendData = Object.keys(result[0]);
                    for (var i = 0; i < result.length; i++) {
                        let tempdata = result[i];
                        _this.xData.push(result[i].key);
                        for (var j = 0; j < _this.legendData.length; j++) {
                            if (!_this.serises[j] && _this.legendData[j] != "key") {
                                _this.serises[j] = {};
                                _this.serises[j].name = _this.legendData[j];
                                _this.serises[j].type = 'bar';
                                _this.serises[j].stack = 'count';
                                _this.serises[j].data = [];
                            }
                            for (var key in tempdata) {
                                if (_this.legendData[j] == key && _this.legendData[j] != "key") {
                                    _this.serises[j].data[i] = tempdata[key];
                                }
                            }
                        }
                    }
                    _this.myChart.setOption({
                        xAxis: { data: _this.xData },
                        legend: { data: _this.legendData },
                        series: _this.serises
                    })
                }
            })
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
                    let height = this.$refs.pileBar.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        },
        //定时刷新数据
        refreshData() {
            let _this = this;
            // setInterval(() => {
            //   _this.fetchData(_this.requestUrl);
            // }, _this.intervalTime)
        },
    },
}
</script>

<style scoped>
.pileBar {
    width: 100%;
    height: 100%;
}
</style>

