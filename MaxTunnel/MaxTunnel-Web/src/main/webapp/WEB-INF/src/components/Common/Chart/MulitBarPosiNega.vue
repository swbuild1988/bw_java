<template>
    <div class="posiNega" :id="id"></div>
</template>
<style scoped>
.posiNega {
    position: relative;
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
        titleText: {
            type: String
        },
        legendData: {
            type: Array
        }
    },
    data() {
        return{
            myChart: {},
            option: {
                backgroundColor: '#161139',
                title: {
                    text: this.titleText,
                    textStyle: {
                        color: '#ccc'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow',
                        label: {
                            show: true,
                            backgroundColor: '#333'
                        }
                    }
                },
                legend: {
                    data: this.legendData,
                    textStyle: {
                        color: '#ccc'
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
                        type: 'category',
                        data: [],
                        axisTick: {
                            alignWithLabel: true
                        },                    
                        splitLine: {
                            show: false,  
                        },
                        axisLabel:{
                            fontsize:2,
                            align:'center',
                            color:'rgba(255,255,255,0.3)'
                        }
                    }
                ],
                yAxis: [{
                    type: 'value',
                    splitLine: {
                        show: true,
                        lineStyle:{
                            color:'rgba(255,255,255,0.2)'
                        }
                    },
                    axisLine:{
                        show:false
                    },
                    axisLabel:{                    
                        fontWeight:10,
                        fontsize:5,
                        color:'rgba(255,255,255,0.3)'
                    }
                        
                }],
                series: []
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
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            // 加载默认参数
            _this.myChart.setOption(_this.option);
            window.addEventListener("resize", _this.myChart.resize);
        },

        fetchData(requestUrl) {
            if(requestUrl=='getInventory'){
                var xData = ['温度计','万能表','门禁卡','信号发射器']
                var yData1 = ['-10','-20','-8','-5','-1']
                var yData2 = ['20','21','2','5','9']
                let series = [
                    {
                        name: this.option.legend.data[0],
                        type: 'bar',
                        stack: '总量',
                        barWidth: 10,
                        itemStyle: {
                            normal: {
                                barBorderRadius: 50,
                                color: new this.$echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        {offset: 0, color: '#BC34BC'},
                                        {offset: 1, color: '#7F3594'}
                                    ]
                                )
                            },
                        },
                        data: yData1
                    },{
                        name: this.option.legend.data[1],
                        type: 'bar',
                        stack: '总量',
                        barWidth: 10,
                        itemStyle: {
                            normal: {
                                barBorderRadius: 50,
                                color:new this.$echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        {offset: 0, color: '#4740C8'},
                                        {offset: 1, color: '#EF71FF'}
                                    ]
                                )
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
        },
        getDataX(data) {
            let xData = [];
            data.forEach(element => {
                xData.push(element);
            });
            return xData;
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
        }
    }
}
</script>

