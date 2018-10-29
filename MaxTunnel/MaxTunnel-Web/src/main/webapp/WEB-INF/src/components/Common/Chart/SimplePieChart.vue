<template>
  <div class="SimplePie" :id="id"></div>
</template>
<style scoped>
  .SimplePie {
    position: relative;
    height: 100%;
    width: 100%;
  }
</style>
<script>
  export default {
    name: "SimplePie",
    props: {
      id: {
        default: ""
      },
      params: {
        default: ""
      },
      requestUrl: {
        default: ""
      },
      intervalTime: {
        default: 60000
      },
      title: {
        default: {
          text: "SimplePie",
          x: 'left',
        }
      },
      TitleName: {
        default: '各类型设备数'
      }
    },
    data() {
      return {
        myChart: {},
        option: {
          backgroundColor: 'rgba(225,225,225,0.8)',
          title: {
            text: "Customized Pie",
            left: "left",
            textStyle: {
              color: '#000'
            },
            // fontSize: 13 * gain,
          },

          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c}"
          },

          // visualMap: {
          //   show: false,
          //   min: '',
          //   max: '',
          //   inRange: {
          //     colorLightness: [0, 1]
          //   }
          // },
          series: [
            {
              name: this.TitleName,
              type: "pie",
              radius: "70%",
              center: ["50%", "50%"],
              data: [].sort(function (a, b) {
                return a.value - b.value;
              }),
              // roseType: "radius",
              label: {
                normal: {
                  show: true
                },
                emphasis: {
                  show: true
                }
              },
              lableLine: {
                normal: {
                  show: true
                },
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                  return Math.random() * 200;
                },
                emphasis: {
                  show: true
                }
              },
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    // formatter: '{b}{c}%'
                  },
                  lableLine: {
                    show: true
                  }
                },
                shadowColor: 'rgba(128, 128, 128, 0.5)',
                shadowBlur: 10,
                shadowOffsetX: 7,
                shadowOffsetY: 7,
              }
            }
          ]
        }
      };
    },
    mounted() {
      this.init();
      this.refreshData();
    },
    methods: {
      init() {
        this.drawBar();
        this.fetchData(this.requestUrl, this.params);
      },
      drawBar() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.option.title = _this.title;
        _this.myChart.setOption(_this.option);
        window.addEventListener("resize", _this.myChart.resize);
      },
      fetchData(requestUrl, params) {
        let _this = this;
        if (params) {
          _this.axios.post(requestUrl, params).then(result => {
            let {code, data} = result.data;
            var tempCount = 0
            data.filter(function (item) {
              tempCount += item.val
            })
            let newData = data.map(curObj => {
              return {
                value: curObj.val,
                name: curObj.key + " " + (100 * curObj.val / tempCount).toFixed(2) + "%"
              }
            })
            _this.option.series[0].data = newData
            _this.myChart.setOption(_this.option)
          })
        } else {
          _this.axios.get(requestUrl).then(result => {
            let {code, data} = result.data;
            if (code == 200) {
              _this.option.series[0].data = [];
              var tempCount = 0;
              data.filter(function (item) {
                tempCount += item.val;
              })
              _this.option.series[0].data = data.map(curObj => {
                return {
                  value: curObj.val,
                  name: curObj.key + " " + (100 * curObj.val / tempCount).toFixed(2) + "%"
                }
              });
              // console.log(_this.option.series[0].data);
              _this.myChart.setOption(_this.option)
            }
            else {
              if ("getCountLendData" == requestUrl) {
                _this.option = {
                  series: {
                    data: [
                      {value: 31, name: "电压表"},
                      {value: 20, name: "有害气体探测器"},
                      {value: 21, name: "扳手"},
                      {value: 9, name: "温度计"},
                      {value: 11, name: "螺丝刀"},
                      {value: 8, name: "其他"}
                    ]
                  }
                };
                _this.myChart.setOption(_this.option);
              }
              if ("getCountBreakData" == requestUrl) {
                _this.option = {
                  series: {
                    data: [
                      {value: 21, name: "万能表"},
                      {value: 11, name: "螺丝刀"},
                      {value: 31, name: "电压表"},
                      {value: 9, name: "温度计"},
                      {value: 20, name: "应力计"},
                      {value: 8, name: "其他"}
                    ]
                  }
                };
                _this.myChart.setOption(_this.option);
              }
            }
          });
        }
      },
      //定时刷新数据
      refreshData() {
        let _this = this;
        setInterval(() => {
          _this.fetchData(_this.requestUrl, _this.params);
        }, _this.intervalTime)
      },
    }
  };
</script>
