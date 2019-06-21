<template>
    <div class="posiNega" :id="id" ref="element"></div>
</template>
<style scoped>
.posiNega {
    height: 100%;
    width: 100%;
}
</style>
<script>
export default {
    name: "posiNegaBar",
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
        legendData: {
            type: Array
        },
        parameters: {
            type: Object
        },
        leftBarColor: {
            default: '#25cef3',
            type: String
        },
        rightBarColor: {
            default: '#d01864',
            type: String
        },
        lineColor: {
            default: '#25cef3',
            type: String
        },
        textColor:{
            default: '#fff',
            type: String
        },
        titlePosition: {
            default: 'left',
            type: String
        },
        chartType: {
            default: 'bar',
            type: String
        },
        xSplitLine: {
            default: {
                show: false
            },
            type: Object
        },
    },
    data() {
        return{
            myChart: {},
            option: {
            }
        }
    },
    mounted() {
        this.init();
        this.refreshData();
    },
    methods:{
        init() {
            this.drawRadar();
            this.fetchData(this.requestUrl);
        },

        drawRadar() {
            let _this = this;
            window.addEventListener("resize", _this.myChart.resize);
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            this.option = {
                title: {
                    text: this.title,
                    textStyle: {
                        fontSize: this.getFontSize('5%'),
                        color: this.textColor
                    },
                    x: this.titlePosition,
                    top: "2%"
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow',
                        label: {
                            show: true
                        }
                    }
                },
                grid: {
                    left: "3%",
                    right: "3%",
                    bottom: "4%",
                    top: '15%',
                    containLabel: true
                },
                calculable: true,
                legend: {
                    data: this.legendData,
                    x:'right',
                    textStyle: { 
                        fontSize: _this.getFontSize('5%') ,
                        color: this.textColor
                    },
                    padding: _this.getFontSize('6%')
                },
                xAxis: [
                    {
                        type: 'category',
                        data: [],
                        splitLine: this.xSplitLine,
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize : this.getFontSize('5%'),      //更改坐标轴文字大小
                                color: this.textColor
                            },                            
                        },
                    }
                ],
                yAxis: [{
                    type: 'value',
                    axisLine:{
                        show:false
                    },
                    axisLabel:{   
                        show: true, 
                        textStyle: {
                            fontSize : _this.getFontSize('4%'),      //更改坐标轴文字大小
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
                }],
                
                series: []
            }
            // 加载默认参数
            _this.myChart.setOption(_this.option);
            if(_this.parameters.option){
                _this.myChart.setOption(_this.parameters.option)
            }
        },

        fetchData(requestUrl) {
            this.axios.get(requestUrl).then(res=>{
                let { code, data } = res.data
                if(code==200){
                    var xData = this.getDataX(data)
                    var yData1 = this.getDataY1(data)
                    var yData2 = this.getDataY2(data)
                    let series = [
                        {
                            name: this.option.legend.data[0],
                            type: this.chartType,
                            barWidth: this.getFontSize('5%'),
                            itemStyle: {
                                normal: {
                                    color: this.leftBarColor
                                },
                            },
                            data: yData1,
                        },{
                            name: this.option.legend.data[1],
                            type: this.chartType,
                            barWidth: this.getFontSize('5%'),
                            itemStyle: {
                                normal: {
                                    color: this.rightBarColor
                                }
                            },
                            data: yData2
                    }];
                    this.option.xAxis[0].data = xData;
                    let tmp_series = series
                    tmp_series.forEach(element => {
                        this.option.series.push(element);
                    });
                    this.myChart.setOption(this.option);
                }
            })
        },
        getDataX(data) {
            let xData = [];
            data.forEach(element => {
                xData.push(element.key);
            });
            return xData;
        },
        getDataY1(data){
            let dataY1 = [];
            for (let i = 0; i < data.length; i++) {
                for (let j = 0; j < data[i].val.length; j++) {
                    if(data[i].val[j].key==this.option.legend.data[0]){
                        dataY1.push(data[i].val[j].val)
                    }
                }
            }
            return dataY1
        },
        getDataY2(data){
            let dataY2 = [];
            for (let i = 0; i < data.length; i++) {
                for (let j = 0; j < data[i].val.length; j++) {
                    if(data[i].val[j].key==this.option.legend.data[1]){
                        dataY2.push(data[i].val[j].val)
                    }
                }
            }
            return dataY2
        },
        getSeries(data) {
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
}
</script>

