<template>
      <div class="BuddhaRidgeContainer BackgroundImage lf BodyCenterPadding">
          <div class="BuddhaRidge" :id=timeSeriesid></div>
          <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
    .BuddhaRidge{
      position: relative;
      width: 100%;
      height: 100%;
      top: 5%;
    }
</style>

<script>
    import VmTitle from '../VMTitle'
    export default {
        name:'BuddhaRidge',
        props:['timeSeriesid','requestUrl'],
        data() {
            return {
              title:'佛子岭路耗电量',
              myChart:"",
              date:[],
              data:[]
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
            _this.myChart = _this.$echarts.init(document.getElementById(_this.timeSeriesid));
            let option = {
                tooltip: {
                  trigger: 'axis',
                  position: function (pt) {
                    return [pt[0], '30%'];
                  }
                },
                grid: {
                    left: '3%',
                    right: '8%',
                    bottom: '8%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: _this.date,
                    axisLabel:{
                      textStyle:{
                        color:'#F1F2F3'
                      }
                    }
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    axisLabel:{
                      textStyle:{
                        color:'#F1F2F3'
                      }
                    }
                },
                dataZoom: [{
                  type: 'inside',
                  start: 0,
                  end: 50,
                }, {
                  show:false,
                  start: 0,
                  end: 10,
                  handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                  handleSize: '70%',
                  handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                  }
                }],
                series: [
                    {
                        name:'用电量',
                        type:'line',
                        smooth:false,//是否平滑曲线显示
                        symbol: 'none', //标记图形
                        sampling: 'average',//取过滤点的平均值
                        itemStyle: {
                          normal: {
                            color: '#699DCE'
                          }
                      },
                        areaStyle: {
                            normal: {
                            color: new _this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#699DCE'
                            }, {
                                offset: 1,
                                color: '#05202E'
                            }])
                          }
                        },
                        data: _this.data
                    }
                ]
            };
            _this.myChart.setOption(option);
            window.addEventListener("resize", this.myChart.resize);
          },
            fetchData(requestUrl){
            let _this=this;

            _this.axios.get(requestUrl).then(result=>{
              let { code, data } = result.data;

              if(code==200){

                let newData=data.reduce((init,item)=>{
                  return{
                    date:init.date==undefined?[].concat(new Date(item.crtTime).format('yyyy/MM/dd')):[].concat(init.date,new Date(item.crtTime).format('yyyy/MM/dd')),
                    data:init.data==undefined?[].concat(item.value):[].concat(init.data,item.value)
                  }
                },{})

                if(JSON.stringify(newData.date)!=JSON.stringify(_this.date)
                  ||JSON.stringify(newData.data)!=JSON.stringify(_this.data)){
                  _this.date=newData.date;
                  _this.data=newData.data;
                  _this.myChart.setOption({
                    xAxis:{data:_this.date},
                    series:{data:_this.data}
                  })
                }
              }

            })
          },
        }
    }

</script>
