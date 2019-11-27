<template>
  <div
          class='SimpleBar'
          ref="element"
          :id=id></div>
</template>

<script>
    export default {
        name: 'SimpleBar',
        props: {
            id: {
                type: String
            },
            title: {
                type: String
            },
            legendData: {
                type: Array
            },
            xData: {
                type: Array
            },
            seriesData: {
                type: Array
            },
            yAxisUnit:''
        },
        watch:{
            seriesData: {
                handler(newValue, oldValue) {
                    if (newValue.length && newValue.length > 0) this.resize();
                },
                deep: true,
                // immediate: true
            },
        },
        data() {
            return {
                myChart: {},
                option: {},
                chartConfig:{},
                itemStyleColor: ['#00f6fc', '#ff2d2d', '#008bfe', '#53ff53', '#949449', '#b766ad', '#e1e8c8', '#7b7c68', '#e5b5b5', '#f0b489', '#928ea8', '#bda29a']
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            init() {
                this.resize();
                window.addEventListener("resize", this.myChart.resize);
                window.addEventListener("resize", this.resize)
            },
            resize() {
                this.templateConfig();
                this.drawBar();
                this.fetchData()
            },
            templateConfig(){
                let _this = this;
                if(this.id === 'stacChart'){
                    this.chartConfig ={
                        legend: {
                            textStyle: {
                                color: '#000',
                                fontSize: _this.getFontSize('4%')
                            },
                        },
                        title: {
                            textStyle: {
                                color: '#000',
                                fontSize: _this.getFontSize('6%')
                            },
                        },
                        xAxis:_this.getCoordinateStyle('#000','4%',0).xAxis,
                        yAxis:_this.getCoordinateStyle('#000','4%',100).yAxis,
                        grid:_this.getGrid('20%','3%','4%','15%')
                    }
                }else if(this.id === 'corssBarChartID'){
                    this.chartConfig ={
                        legend: {
                            x:'60%',
                            y:'15%',
                            textStyle: {
                                color: '#ccc',
                                fontSize: _this.getFontSize('7%')
                            },
                        },
                        title: {
                            textStyle: {
                                color: '#ccc',
                                fontSize: _this.getFontSize('10%')
                            },
                            left:'center'
                        },
                        xAxis:_this.getCoordinateStyle('#ccc','7%','auto','月').xAxis,
                        yAxis:_this.getCoordinateStyle('#ccc','7%',5).yAxis,
                        grid:_this.getGrid('30%','3%','4%','3%')
                    }
                }else {
                    this.chartConfig ={
                        legend: {
                            y:'bottom',      // 可选为：'top' | 'center'
                            textStyle: {
                                color: '#ccc',
                                fontSize: _this.getFontSize('7%')
                            },
                        },
                        title: {
                            textStyle: {
                                color: '#ccc',
                                fontSize: _this.getFontSize('9%')
                            },
                            left:'center'
                        },
                        xAxis:_this.getCoordinateStyle('#ccc','7%',0).xAxis,
                        yAxis:_this.getCoordinateStyle('#ccc','7%',100,' 个').yAxis,
                        grid:_this.getGrid('20%','3%','4%','15%')
                    }
                }

            },
            drawBar() {
                let _this = this;
                this.myChart = this.$echarts.init(document.getElementById(this.id));

                this.option = {
                    title: Object.assign({
                            text: this.title
                        },
                        _this.chartConfig.title
                    ),
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: Object.assign({
                            data:this.legendData
                        },
                        _this.chartConfig.legend
                    ),
                    grid: _this.chartConfig.grid,
                    xAxis : [
                        Object.assign(
                            {
                                type : 'category',
                                data : [],
                            },
                            _this.chartConfig.xAxis
                        )
                    ],
                    yAxis : [
                        Object.assign(
                            {
                                type : 'value',
                                min:0,
                            },
                            _this.chartConfig.yAxis
                        )

                    ],
                    series: []
                }
                this.myChart.setOption(_this.option);
            },
            fetchData() {
                let _this = this;
                while (this.itemStyleColor.length < this.seriesData.length) {
                    this.itemStyleColor = this.itemStyleColor.concat(this.itemStyleColor)
                }

                for (var i = 0; i < this.legendData.length; i++) {
                    let seriesData = {
                        name: this.legendData[i],
                        type: 'bar',
                        data: this.seriesData[i].data,
                        barWidth:'30%',
                        itemStyle: {
                            normal: {
                                color: this.itemStyleColor[i]
                            }
                        }
                    };

                    let seriesDataAll = this.id === 'corssBarChartID'
                        ? Object.assign({},seriesData)
                        : Object.assign({
                              stack: 'total'
                          },seriesData);

                    this.option.series.push(seriesDataAll);

                }
                this.option.xAxis[0].data = this.xData;

                this.myChart.setOption(this.option);
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
            },
            getCoordinateStyle(lineColor,labelFontSize,interval,unit){
                let _this = this;

                let Axis = {
                    axisLine: {
                        lineStyle: {
                            color: lineColor
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize: _this.getFontSize(labelFontSize)      //更改坐标轴文字大小
                        },
                        interval:interval,
                    },
                }

                return {
                    xAxis:Object.assign(
                        Axis,
                    ),
                    yAxis:Object.assign(
                        Axis,
                        {
                            interval:interval,
                            name: !unit ? '':'单位('+ unit +' )'
                        }
                    ),

                }
            },
            getGrid(){
                let [ top,left,right,bottom ] = [].slice.call(arguments);

                return {
                    top,
                    left,
                    right,
                    bottom,
                    containLabel: true
                }
            }
        },
    }
</script>

<style scoped>
  .SimpleBar {
    width: 100%;
    height: 100%;
  }
</style>
