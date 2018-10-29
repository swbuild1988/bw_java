<template>
      <div class="MonitoringContainer BackgroundImage lf BodyCenterImg" :style="{padding:'0.5%'}">
          <div class="Monitoring" :id=stackedBarid></div>
          <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
    .Monitoring{
      width: 100%;
      height: 100%;
    }
</style>

<script>
    import VmTitle from '../VMTitle'
    import { converArrayFun } from '../../../scripts/commonFun'
    export default {
        name:'Monitoring',
        props:['stackedBarid','requestUrl'],
        data() {
            return {
                title:'报警监控统计',
                series:[],
                yData:[]
            }
        },
        components:{
          VmTitle
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                this.drawBar();
                this.fetchData(this.requestUrl);
                this.refreshData();
            },
            drawBar(){
                let _this=this;
                _this.myChart = _this.$echarts.init(document.getElementById(_this.stackedBarid));
                let option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data: []
                    },
                    grid: {
                        left: '1%',
                        right: '6%',
                        bottom: '1%',
                        containLabel: true
                    },
                    xAxis:  {
                        type: 'value',
                        axisLabel:{
                            textStyle:{
                                color:'#F1F2F3'
                            }
                        }
                    },
                    yAxis: {
                        type: 'category',
                        data: _this.yData,
                        axisLabel:{
                            textStyle:{
                                color:'#F1F2F3'
                            }
                        }
                    },
                    series: _this.series
                };
                _this.myChart.setOption(option);
                window.addEventListener("resize", this.myChart.resize);
            },
            fetchData(requestUrl){
                let _this=this;

                _this.axios.get(requestUrl).then(result=>{
                    let { code, data } = result.data;

                    if(code==200){

                        let  filterData=converArrayFun(data,[],[],'总量');

                        if(JSON.stringify(filterData.yData)!=JSON.stringify(_this.yData)
                            ||JSON.stringify(filterData.initObj)!=JSON.stringify(_this.series)){
                            _this.yData=filterData.yData;
                            _this.series=filterData.series;
                            _this.myChart.setOption({
                                yAxis:{data:_this.yData},
                                series:_this.series
                            })
                        }
                    }

                })
            },
            refreshData(){
                let _this=this;
                setInterval(()=>_this.fetchData(_this.requestUrl),50000)

            },
        }
    }

</script>
