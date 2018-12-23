<!--带点击事件的柱状图-->
<template>
  <div class='SimpleBarWithClick' :id=id></div>
</template>

<script>
  import {ChartService} from '../../../services/chartService.js'

  export default {
    name: 'SimpleBarWithClick',
    props: {
      id: {
        default: ''
      },
      requestUrl: {
        default: ''
      },
      title: {
        default: ''
      },
      color: {
        default: '#336666'
      },
      intervalTime: {
        default: 60000
      },
      clickPrams: {
        type: Object,
        default: function () {
          return {
            areaType:1,
            tunnelId: "",
            storeId: "",
            energyType: "",
            startTime: "",
            endTime: "",
            arerId: "",
          }
        }
      }
    },
    data() {
      return {
        myChart: {},
        xData: [],
        yData: [],
        series: [],
        option: {
          backgroundColor:'rgba(1, 36, 65, 0.8)',
          title: {
            text: "title",
            textStyle: {
              color: '#2e739b'
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'value',
              axisLine: {
                lineStyle: {
                  color: '#fff'
                }
              },
              splitLine: {show: false},
            }
          ],
          yAxis: [
            {
              type: 'category',
              axisLine: {
                lineStyle: {
                  color: '#fff'
                }
              },
              axisTick: {
                alignWithLabel: true
              },
              splitLine: {show: false},
            }
          ],
          series: [
            {
              name: '耗电量',
              type: 'bar',
              barWidth: '34',
              data: [],
              itemStyle: {
                normal: {
                  barBorderRadius: 36,
                  color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#fff'
                  }, {
                    offset: 0.4,
                    color: '#d3beff'
                  }, {
                    offset: 0.9,
                    color: '#6862ff'
                  }])
                }
              }
            }
          ]
        }
      }
    },
    components: {},
    mounted() {
      this.init();
      this.refreshData();
    },
    methods: {
      init() {
        this.drawBar();
        this.fetchData();
      },

      drawBar() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.option.title.text = _this.title;
        _this.myChart.setOption(_this.option);
        window.addEventListener('resize', this.myChart.resize);
        _this.myChart.on('click', function (params) {
          _this.clickPrams.storeId = _this.yData[params.dataIndex];
          _this.goToMoudle(_this.clickPrams.url + _this.clickPrams.tunnelId, _this.clickPrams);
        });
      },

      fetchData() {
        let _this = this;
        let prams = {};
        prams = _this.clickPrams;
        prams.requestUrl = _this.requestUrl;
        ChartService.getSimpleBarDataWithClick(prams).then((result) => {
          if (result) {
            _this.series = [];
            _this.xData = [];
            _this.yData = [];
            result.forEach(a => {
              _this.yData.push(a.id);
              _this.series.push(a.val);
              _this.xData.push(a.name);
            })
            _this.myChart.setOption({
              yAxis: {data: _this.xData},
              series: {data: _this.series},
            })
          }
        })
      },

      //定时刷新数据
      refreshData() {
        let _this = this;
        // setInterval(() => {
        //   _this.fetchData(_this.requestUrl);
        // }, _this.intervalTime)
      },

      goToMoudle(path, parms) {
        this.$router.push({path: path, query: parms});
      },
    },
  }
</script>

<style scoped>
  .SimpleBarWithClick {
    width: 100%;
    height: 100%;
  }
</style>
