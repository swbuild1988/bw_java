<template>
    <div class="gasState" >
        <div class="mapgaugeLast"  v-for="item in gaugeChart">
            <simple-gauge style="height: 10vh;width: 6vw;" v-bind="item" ref="item.id">
            </simple-gauge>
        </div>
    </div>
</template>

<style scoped>
    .gasState{
        position: absolute;
        height: 90%;
        margin-top: 8.5%;
        z-index: 1003;
        right: 2%;
        width: 6vw;
    }
    .mapgaugeLast {
        height: 11vh;
        position: relative;
        background: url("../../../assets/VM/gauge_bg.png") no-repeat;
        background-size: 100% 100%;
    }
</style>

<script>
  import SimpleGauge from '../../Common/Chart/SimpleGauge.vue'
    import MapGanue from '../../Common/Chart/gauge'
    import config from '../../../../static/VM/js/VMGlobalConfig'

    export default {
        name:'gauges',
        data() {
            return {
              gaugeChart: [],
              tempData:[
                {
                  location:"古城大街1区污水仓",
                  value:21,
                  name:"温度",
                  unit:"℃"
                },
                {
                  location:"古城大街1区污水仓",
                  value:21,
                  name:"氧气浓度",
                  unit:"%"
                },
                {
                  location:"古城大街1区污水仓",
                  value:0.01,
                  name:"甲烷浓度",
                  unit:"ppm"
                },
                {
                  location:"古城大街1区污水仓",
                  value:10,
                  name:"湿度",
                  unit:"%"
                },
                ],
                top: '15.5%'
            }
        },
        components: {
            MapGanue,
          SimpleGauge
        },
        mounted(){
            this.init();
        },
        methods:{
            init() {
              var _this = this;
              _this.gaugeChart = [];
              if (_this.tempData) {
                _this.tempData.forEach((a, index) => {
                  let temp = {};
                  temp.requestUrl = "";
                  temp.id = index + "_Map_Chart";
                  temp.parameters = {
                    option: {
                      title: {
                        text: a.location
                      },
                      series: {
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
                let {networkAnomaly} = config;
                this.top = !networkAnomaly ? '15.5%' : '2%';
              }
            }
        }
    }

</script>
