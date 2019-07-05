<template>
    <div class="MultiBar" :id="id" ref="element"></div>
</template>
<style scoped>
.MultiBar {
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
        title: {
            type: String
        },
        parameters: {
            type: Object
        },
        textColor: {
            default:  '#fff',
            type: String
        },
        lineColor: {
            default: '#fff',
            type: String
        },
        titlePosition: {
            default: 'center',
            type: String
        }
    },
    data() {
        return {
            myChart: {},
            option: {}
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
            this.option = {
                title: {
                    text: this.title,
                    textStyle: {
                        fontSize: this.getFontSize('4%')
                    },
                    x: this.titlePosition,
                    padding: 15
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
                calculable: true,
                legend: {
                    data: this.legendData,
                    x:'right',
                    textStyle: { 
                        fontSize: _this.getFontSize('3%') ,
                        color: this.textColor
                    },
                    padding: _this.getFontSize('6%')
                },
                xAxis: [
                    {
                        type: "category",
                        data: [],
                        splitLine: {
                            show: false
                        },
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize : this.getFontSize('3%'),      //更改坐标轴文字大小
                                color: this.textColor
                            },                            
                        },
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        axisLine:{
                            show:false
                        },
                        axisLabel:{   
                            show: true, 
                            textStyle: {
                                fontSize : _this.getFontSize('3%'),      //更改坐标轴文字大小
                                fontWeight:  this.getFontSize('10%'),
                                color: this.textColor
                            },                
                        },
                        axisTick: {
                            show: false
                        },
                        splitLine: {
                            show: true,
                            lineStyle:{
                                type:'dashed',
                                color: this.lineColor
                            }
                        }
                    }
                ],
                series: []
            }
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

            // if(requestUrl=='getToolStatic'){
            //     var data = [
            //         { key: '温度计', val: [{ key: '正常', val: 152 },{ key: '损坏', val: 25 }] },
            //         { key: '万能表', val: [{ key: '正常', val: 96 },{ key: '损坏', val: 52 }] },
            //         { key: '门禁卡', val: [{ key: '正常', val: 45 },{ key: '损坏', val: 5 }] },
            //         { key: '信号发射器', val: [{ key: '正常', val: 25 },{ key: '损坏', val: 85 }]}
            //     ]
            //      _this.option.xAxis[0].data = this.getDataX(data);
            //         _this.option.series = [];
            //         let tmp_series = this.getSeries(data);
            //         tmp_series.forEach(element => {
            //             _this.option.series.push(element);
            //         });
            //         _this.myChart.setOption(_this.option);
            // }

            // else if(requestUrl=='spares/outs/type'){
            //     var data = [
            //         { key: '温度计', val: [{ key: '正常', val: 152 },{ key: '损坏', val: 25 }] },
            //         { key: '万能表', val: [{ key: '正常', val: 96 },{ key: '损坏', val: 52 }] },
            //         { key: '门禁卡', val: [{ key: '正常', val: 45 },{ key: '损坏', val: 5 }] },
            //         { key: '信号发射器', val: [{ key: '正常', val: 25 },{ key: '损坏', val: 85 }]}
            //     ]
            //      _this.option.xAxis[0].data = this.getDataX(data);
            //         _this.option.series = [];
            //         let tmp_series = this.getSeries(data);
            //         tmp_series.forEach(element => {
            //             _this.option.series.push(element);
            //         });
            //         _this.myChart.setOption(_this.option);
            // }

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
                            barWidth: 20,
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
    }
};
</script>
