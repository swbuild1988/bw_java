<template>
  <div :id="id" class="simpleGauge"></div>
</template>

<script>
  export default {
    name: "simple-gauge",
    props: {
      id: {
        type: String
      },
      requestUrl: {
        type: String
      },
      parameters: {
        type: Object
      }
    },
    data() {
      return {
        myChart: {},
        xData: [],
        yData: [],
        series: [],
        defaultOption: {},
        option: {},
      };
    },
    components: {},
    mounted() {
      this.init();
      this.resizeChart();
    },
    methods: {
      init() {
        this.drawSimpleGauge();
        this.fetchData();
      },
      resizeChart(){
        let _this=this;
        window.addEventListener("resize", function () {
          _this.drawSimpleGauge();

          _this.myChart.resize();
        });
      },
      drawSimpleGauge() {
        let _this = this;
        _this.option={
          title: {
            text: '',
            subtext:"",
            textStyle:{
              fontSize: window.innerHeight * 0.012,
              color: "#fff",
            },
            subtextStyle:{color:"#fff",fontSize:window.innerHeight * 0.013},
            bottom: '0',
            left: 'center',
          },
          backgroundColor: null,
          series: [
            {
              name: '',
              type: 'gauge',
              min: 0,
              max: 100,
              splitNumber: 5,
              radius: '90 %',
              title: {
                show: false,
                textStyle: {
                  color: '#fff',
                  fontSize:window.innerHeight * 0.016,
                }
              },
              axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                  width: 8,
                  shadowBlur: 0,
                    color:[
                      [0.2, '#c23531'],
                      [0.8, '#329bff'],
                      [1, '#c23531'],
                    ]
                }
              },
              axisTick: { // 坐标轴小标记
                lineStyle: { // 属性lineStyle控制线条样式
                  color: 'auto'
                }
              },
              axisLabel: {
                distance: 0
              },
              splitLine: { // 分隔线
                  lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                  }
                },
              detail: {
                offsetCenter:[0,'25%'],
                fontSize: window.innerHeight * 0.015,
              },
              //指针
              // pointer: {
              //   width:4,
              // },
              itemStyle: {
                color:"#fff",
                normal: {
                  shadowBlur: 6
                }
              },
              data: [
                {
                  value: 0,
                  name: ''
                }]
            },
          ]
        };
        _this.defaultOption={
          axisTick: {
            length: window.innerWidth * 0.5
          },
          series: {
            axisLine: {
              lineStyle: { // 属性lineStyle控制线条样式
                width: window.innerHeight * 0.007,
              }
            },
            axisTick: { // 坐标轴小标记
              length: window.innerWidth * 0.006, // 属性length控制线长
            },
            axisLabel: {
              fontSize: window.innerHeight * 0.012,
              formatter: function (value, index) {
               value=parseFloat(value.toFixed(1));
                return value;
              },
            },
            splitLine: {
              length: window.innerWidth * 0.0065,
            },
          },
        };
        _this.myChart = _this.$echarts.init(
          document.getElementById(_this.id)
        );
        // 加载默认参数
        _this.myChart.setOption(_this.option);
        // 加载基础配置
        _this.myChart.setOption(_this.defaultOption);
        // 加载新的参数
        if (_this.parameters.option) {
          _this.myChart.setOption(_this.parameters.option);
        }
      },
      fetchData() {
        let _this = this;
        if (_this.requestUrl && requestUrl != "") {
          _this.axios.get(requestUrl).then(result => {
            let {code, data} = result.data;
            if (code == 200) {
              {
                // _this.xData = newData.xData;
                // _this.yData = newData.yData;
                _this.myChart.setOption({
                  xAxis: {data: _this.xData},
                  series: {data: _this.yData}
                });
              }
            }
          });
        }
      },
      //定时刷新数据
      refreshData() {
        let _this = this;
        // 数据以参数的形式传递过来时，用此刷新方法
        if (_this.parameters.option) {
          _this.myChart.setOption(_this.parameters.option);
          let tempOption=_this.parameters.option;
          _this.myChart.setOption({
            series: {
              data: tempOption.series.data,
              min: tempOption.series.min,
              max: tempOption.series.max,
            }
          });
        }
        // setInterval(() => {
        //   _this.fetchData(_this.requestUrl);
        // }, _this.intervalTime)
      }
    }
  }
</script>

<style scoped>
  .simpleGauge {
    width: 100%;
    height: 100%;
  }
</style>
