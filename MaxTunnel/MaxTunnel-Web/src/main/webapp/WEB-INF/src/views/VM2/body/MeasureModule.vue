<template>
  <div class="Main">
    <div class="Title">
      <module-title :title="title"></module-title>
    </div>

    <div v-for="item in gaugeChart" class="myChart">
      <div class="chartInfo">{{item.name}}</div>
      <Simple-gauge v-bind="item" :ref="item.id"></Simple-gauge>
      <div class="chartInfo">{{item.time}}</div>
    </div>

    <div class="info" v-for="item in countData">
      <div class="infoTitle" ><p>{{item.name}}</p></div>
      <div class="infoTitle">
        <div v-if="item.type==1">
          <span><Icon type="ios-football-outline" :size="iconSize" color="#00a1ff"></Icon></span>
          <span style="color: #ffd50a;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;">{{item.value}}</span>个
        </div>
        <div v-else>
          <span><Icon type="ios-people" :size="iconSize" color="#00a1ff"></Icon></span>
          <span style="color: #ffd50a;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;">{{item.value}}</span>人
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import ModuleTitle from "../../../components/VM2/ModuleTitle"
  import SimpleGauge from "../../../components/Common/Chart/SimpleGauge"
  import {MeasObjServer} from '../../../services/MeasObjectSerivers'

  export default {
    data() {
      return {
        title: '管廊监测',
        iconSize:window.innerWidth*0.012,
        fetchTime:5000,
        countData: [],
        gaugeChart:[
          {
            name: "最高温度",
            type: 1,//数据类型为温度的枚举值
            time: "",
            id: "temperatureGaugeChart",
            requestUrl: "",
            parameters: {
              option: {
                title: {
                  subtext: ""
                },
                series: [{
                  data: [{value: 0, name: "最高温度"}],
                  detail:{
                    offsetCenter:[0,'25%'],
                    fontSize: window.innerHeight * 0.015,
                  },
                }]
              }
            }
          },
          {
            name:"最高甲烷",
            type:5,//数据类型为甲烷的枚举值
            time:"",
            id:"methaneGaugeChart",
            requestUrl:"",
            parameters:{
              option: {
                title: {
                  subtext: ""
                },
                series: [{
                  data: [{value: 0, name: "最高甲烷"}],
                  detail:{
                    offsetCenter:[0,'25%'],
                    fontSize: window.innerHeight * 0.015,
                  },
                }]
              }
            }
          },
          {
            name:"最低含氧量",
            type:3,//数据类型为氧气的枚举值
            time:"",
            id:"oxygenGaugeChart",
            requestUrl:"",
            parameters:{
              option: {
                title: {
                  subtext: ""
                },
                series:[ {
                  data: [{value: 0, name: "最低含氧量"}],
                  detail:{
                    offsetCenter:[0,'25%'],
                    fontSize: window.innerHeight * 0.015,
                  },
                }]
              }
            }
          }
        ] ,
        dataInterval:null,
      };
    },
    components: {
      ModuleTitle,
      SimpleGauge
    },
    mounted() {
      this.init();
      this.fetchData();
    },
    methods: {
      fetchData(){
        let _this=this;
        _this.dataInterval = setInterval(() => {
          _this.init();
        }, _this.fetchTime)
      },
      init() {
        let _this = this;
        MeasObjServer.getToDayExtreDatas().then(
          result => {
            if (result) {
              result.forEach(a=>{
                _this.gaugeChart.forEach(b=>{
                  if(b.type==a.type){
                    b.name=a.name;
                    b.time=new Date(a.time).format("yyyy-MM-dd hh:mm:ss");
                    b.parameters.option.title.subtext=a.location;
                    let min= parseFloat((a.min-(a.max-a.min)/3).toFixed(1)) ;
                    let max=parseFloat((a.max+(a.max-a.min)/3).toFixed(1));
                    b.parameters.option.series.min=min>0?min:0;
                    b.parameters.option.series.max=max;
                    if(b.type==5){
                      b.parameters.option.series.max=1;
                    }
                    b.parameters.option.series.data=[{value: a.value, name: a.name}];
                    b.parameters.option.series[0].detail.formatter=  "{value}" + a.unit;
                  }
                })
              })
              // 刷新数据
              _this.$refs.temperatureGaugeChart[0].refreshData();
              _this.$refs.methaneGaugeChart[0].refreshData();
              _this.$refs.oxygenGaugeChart[0].refreshData();
            }
          },
          error => {
            _this.Log.info(error)
          })
        MeasObjServer.getMeasTriggerCounts().then(
          result => {
            _this.countData = result
          },
          error => {
            _this.Log.info(error)
          })
      },
    },
    beforeDestroy(){
      clearInterval(this.dataInterval);
      this.dataInterval = null;
    },
  };
</script>

<style scoped>
  .Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
  }
  .Main .Title {
    width: 100%;
    height: 15%;
  }
  .myChart {
    margin-left: 0.1vw;
    width: 32.8%;
    height: 16vh;
    float: left;
  }
.chartInfo{
  text-align: center;
  font-size: 1.16vmin;
  color: #fff;
  background-color: #1c81ff;
  margin-left: 0.2vw;
  margin-right: 0.2vw;
  border-radius:25px;
  box-shadow: 0.5vh 0.5vh 1vh #000000;
}
  .info {
    margin: 5vmin 2.5% 1vmin 2.5%;
    width: 45%;
    height: 6.5vh;
    float: left;
    background: url("../../../assets/VM/doubt.png") no-repeat;
    background-size: 100% 100%;
    border-radius: 1.2vh 0;
  }
  .infoTitle {
    text-align: center;
    font-size: 1.4vmin;
    color: #4dfaff;
    line-height: 2.8vmin;
    font-family: UnidreamLED;
  }
</style>
