<template>
      <div class="UntreatedContainer BackgroundImage lf BodyCenterPadding">
          <div class="Untreated" :id=basiColumnid></div>
          <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
    .Untreated{
      width: 100%;
      height: 100%;
    }
</style>

<script>
    import VmTitle from '../VMTitle'
    import { converArrayFun } from '../../../scripts/commonFun'
    export default {
        name:'Untreated',
        props:['basiColumnid','requestUrl'],
        data() {
            return {
              title: '未处理缺陷',
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
                this.drawLine();
                this.fetchData(this.requestUrl)
            },
            drawLine(){
                let _this=this;
                _this.myChart = _this.$echarts.init(document.getElementById(_this.basiColumnid));
                let option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data:[]
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : _this.yData,
                            axisLabel:{
                                textStyle:{
                                    color:'#F1F2F3'
                                }
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel:{
                                textStyle:{
                                    color:'#F1F2F3'
                                }
                            }
                        }
                    ],
                    series : _this.series
                };
                _this.myChart.setOption(option);
                window.addEventListener("resize", this.myChart.resize);
            },
            fetchData(requestUrl){
                let _this=this;

                _this.axios.get(requestUrl).then(result=>{

                    let { code, data } = result.data;
                    if(code==200){

                        let  filterData=converArrayFun(data);

                        if(JSON.stringify(filterData.yData)!=JSON.stringify(_this.yData)
                            ||JSON.stringify(filterData.initObj)!=JSON.stringify(_this.series)){
                            _this.yData=filterData.yData;
                            _this.series=filterData.series;
                            _this.myChart.setOption({
                                xAxis:{data:_this.yData},
                                series:_this.series
                            })
                        }
                    }

                })
            },
        }
    }

</script>
