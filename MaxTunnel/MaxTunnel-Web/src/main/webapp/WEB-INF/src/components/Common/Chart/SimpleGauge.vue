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
      this.refreshData();
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
            bottom: 'left',
            left: 'center',
            fontSize: window.innerHeight * 0.02,
          },
          backgroundColor: null,
          series: [
            {
              name: '',
              type: 'gauge',
              min: 0,
              max: 100,
              splitNumber: 5,
              radius: '98%',
              title: {
                offsetCenter:[0,'-20%'],
                textStyle: {
                  color: '#ffffff',
                  fontSize:window.innerHeight * 0.016,
                }
              },
              axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                  width: 8,
                  shadowBlur: 0,
                  color: [
                    [0.2, '#7ff46a'],
                    [0.4, '#0066ff'],
                    [0.6, '#ffff00'],
                    [0.8, '#ffae00'],
                    [1, '#ff0000']
                  ]
                }
              },
              axisTick: { // 坐标轴小标记
                // length: 15, // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                  color: 'auto'
                }
              },
              axisLabel: {
                distance: 0
              },
              splitLine:
                { // 分隔线
                  // length: 20, // 属性length控制线长
                  lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto'
                  }
                },
              detail: {formatter: '{value}%'},
              pointer: {
                width:4,
              },
              itemStyle: {
                normal: {
                  shadowBlur: 5
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
          title: {
            textStyle: {
              fontSize: window.innerHeight * 0.012,
              color: "#fff"
            }
          },
          series: {
            axisLine: {
              lineStyle: { // 属性lineStyle控制线条样式
                width: window.innerHeight * 0.005,
              }
            },
            axisTick: { // 坐标轴小标记
              length: window.innerWidth * 0.007, // 属性length控制线长
            },
            axisLabel: {
              fontSize: window.innerHeight * 0.014,
            },
            splitLine: {
              length: window.innerWidth * 0.01,
            },
            detail: {
              fontSize: window.innerHeight * 0.015,
            },
            title: {
              textStyle: {

              }
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
