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
        <span v-if="item.type==1" ><Icon type="ios-football-outline" :size="iconSize" color="#00a1ff"></Icon></span>
        <span  v-else style=""><Icon type="ios-people" :size="iconSize" color="#00a1ff"></Icon></span>
        <span style="color: #ffd50a;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;">{{item.value}}</span>个
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
        extreData: [],
        countData: [],
        gaugeChart: {},
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
            _this.gaugeChart = [];
            if (result) {
              var tempStemp=[];
              let min=0;
              let max=100;
              result.forEach((a, index) => {
                let temp = {};
                  temp.requestUrl = "";
                  temp.name=a.name;
                  temp.time= new Date(a.time).format("yyyy-MM-dd hh:mm:ss");
                  temp.id = index + "_Chart";
                  if(a.type==1){
                    min=0;
                    max=50;
                    tempStemp=[
                      [0.2, '#c23531'],
                      [0.8, '#329bff'],
                      [1, '#c23531'],
                    ];
                  }
               else if(a.type==5){
                  min=0.25;
                  max=1.5;
                  tempStemp=[
                    [0.2, '#c23531'],
                    [0.8, '#329bff'],
                    [1, '#c23531'],
                  ];
                }
                else if(a.type==3){
                  min=18.3;
                  max=24.2;
                  tempStemp=[
                    [0.2, '#c23531'],
                    [0.8, '#329bff'],
                    [1, '#c23531'],
                  ];
                    console.log(tempStemp);
                }
                  temp.parameters = {
                    option: {
                      title: {
                        // text: a.name,
                        subtext:a.location
                      },
                      series: {
                        min:min,
                        max:max,
                        axisLine:{
                          lineStyle:{
                            color:tempStemp
                          }
                        },
                        data: [{
                          value: a.value,
                          name: a.name
                        }],
                        detail: {formatter: "{value}" + a.unit},
                      }
                    }
                  };
                  _this.gaugeChart.push(temp);
              })
            }
            _this.extreData = result;
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
      }
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
