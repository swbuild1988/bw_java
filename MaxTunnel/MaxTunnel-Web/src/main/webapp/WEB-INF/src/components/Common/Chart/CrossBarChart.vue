<template>
    <div class="crossBar" :id="id">
    </div>
</template>
<script>
    export default { 
        name: 'corssBar',  
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
            yAxisData: {
                type: Array
            },
            seriesData: {
                type: Array
            }, 
        },   
        data() {
            return {
                myChart: {},
                option: {
                    title: {
                        text: this.title,
                        textStyle: {
                            color: '#ccc'
                        }
                    },
                    tooltip: {  
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: this.legendData,
                        textStyle: {color: '#ccc'},
                        top: 30
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true  
                    },    
                    xAxis: {   
                        type: 'value',
                        boundaryGap: [0, 0.01],
                        axisLine:{
                            lineStyle:{
                                color:'#00FF00'
                            }
                        }
                    },
                    yAxis: {
                        type: 'category',   
                        data: this.yAxisData,
                        axisLine:{
                            lineStyle:{
                                color:'#00FF00'
                            }
                        }
                    },
                    series: [],
                },
                itemStyleColor: ['#d47c1d','#2b85e4'] 
            }
        },
        mounted(){
            this.drawBar()
            this.fetchData()
        },
        methods:{
            drawBar() {
                this.myChart = this.$echarts.init(
                    document.getElementById(this.id)
                );
                this.myChart.setOption(this.option);
                window.addEventListener("resize", this.myChart.resize);
            },
            fetchData(){
                for(var i=0; i<this.legendData.length; i++){
                    for(var j=0; j<this.seriesData.length; j++){
                        for(var k=0; k<this.itemStyleColor.length; k++){
                            if(i==0&&j==0&&k==0){
                                var seriesDataAll1 = {
                                    name: this.legendData[0],
                                    type: 'bar',
                                    data:  this.seriesData[0].data,
                                    itemStyle: {
                                        normal: {
                                            color: this.itemStyleColor[0]
                                        }
                                    }
                                }
                                this.option.series.push(seriesDataAll1)
                            }else if(i==1&&j==1&&k==1){
                                var seriesDataAll2 = {
                                    name: this.legendData[1],
                                    type: 'bar',
                                    data:  this.seriesData[1].data,
                                    itemStyle: {
                                        normal: {
                                            color: this.itemStyleColor[1]
                                        }
                                    }
                                }
                                this.option.series.push(seriesDataAll2)
                            }
                        }
                    }
                }
                this.myChart.setOption(this.option);
            }
        }   
    }
</script>
<style scoped>
.crossBar{
    width: 100%;
    height: 100%;
}
</style>
