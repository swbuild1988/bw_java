<template>
  <div>
    <div class="progressBar" :id=id ref="element"></div>
  </div>
</template>

<script>
  import {TunnelService} from '../../../services/tunnelService';

  export default {
    name: 'progress-bar-chart',
    props: {
      id: {
        type: String
      },
      unit: {
        type: String,
        default: "",
      },
      requestUrl: {
        type: String
      },
      parameters: {
        type: Object
      },
      Server: {
        type: Object,
        default: function () {
          return {
            status: false,
            serverName: "",
          }
        }
      },
    },
    data() {
      return {
        myChart: "",
        legendData: [],
        serises: [],
        serises_border: [],
        xData: [],
        yAxis_name: [],
        yAxis_value: [],
        option: {},
        yMin: 0,
      }
    },
    components: {},
    mounted() {
      this.init();
      this.resizeChart();
    },
    methods: {
      init() {
        this.drawProgressBar();
        this.fetchData(this.requestUrl);
        this.refreshData();
      },
      resizeChart() {
        let _this = this;
        window.addEventListener("resize", function () {
          _this.drawProgressBar();
          _this.myChart.resize();
          _this.fetchData(_this.requestUrl);
        });
      },
      drawProgressBar() {
        let _this = this;
        _this.option = {
          grid: {height: "80%", top: "2%", bottom: "2%", width: "60%", left: "20%"},
          xAxis: {
            show: false
          },
          yAxis: [
            {
              show: true,
              data: [],
              inverse: true,
              axisLine: {
                show: false
              },
              splitLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                color: '#fff',
                fontSize: _this.getFontSize('12%'),
              },
            },
            {
              show: true,
              inverse: true,
              data: [],
              axisLabel: {
                // formatter: '{value}'+_this.unit,
                textStyle: {
                  fontSize: _this.getFontSize('12%'),
                  color: '#fff',
                  
                },
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: false
              },
              axisTick: {
                show: false
              },

            }],
          series: [
            {
              name: '条',
              type: 'bar',
              yAxisIndex: 0,
              data: [],
              barWidth: 20,
              itemStyle: {
                normal: {
                  barBorderRadius: 15,
                  color: new _this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#11c0ff'
                  },
                    {
                    offset: 1,
                    color: '#9bedff'
                  }]),
                }
              },
              label: {
                normal: {
                  show: true,
                  position: 'inside',
                  formatter: '{c}%' ,
                  fontSize:_this.getFontSize('12%'),
                }
              },
            },
            {
              name: '框',
              type: 'bar',
              yAxisIndex: 1,
              barGap: '-100%',
              data: [],
              barWidth: 20,
              itemStyle: {
                normal: {
                  color: 'none',
                  borderColor: '#00c1de',
                  borderWidth: 2,
                  barBorderRadius: 10,
                }
              }
            }]
        };
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.myChart.setOption(_this.option);
        // 加载新的参数
        if (_this.parameters.option) {
          _this.myChart.setOption(_this.parameters.option);
        }
        window.addEventListener("resize", this.myChart.resize);
      },
      fetchData(requestUrl) {
        let _this = this;
        if (_this.Server.status) {
          if (_this.Server.serverName == "TunnelService.getVmLineMessage") {
            TunnelService.getVmLineMessage().then(
              result => {
                _this.serises = [];
                _this.yAxis_name = [];
                _this.yAxis_value = [];
                _this.serises_border = [];
                let tempData = result.slice(0, 3);
                tempData.forEach(line => {
                  _this.yAxis_name.push(line.name);
                  _this.serises_border.push(100);
                  _this.yAxis_value.push(line.value+_this.unit);
                  _this.serises.push(parseInt(line.percent.slice(0, line.percent.length - 1)));
                })
                _this.myChart.setOption({
                  series: [{data: _this.serises}, {data: _this.serises_border}],
                  yAxis: [{data: _this.yAxis_name}, {data: _this.yAxis_value}],
                })
              },
              error => {
                this.Log.info(error)
              })
          }
        }
        else {
          _this.axios.get(requestUrl).then(result => {
            let {code, data} = result.data;
            if (code == 200) {
              _this.serises = [];
              _this.legendData = [];
              _this.xData = [];
              data.forEach(a => {
                var temp = {};
                temp.name = a.key;
                temp.type = 'line';
                temp.smooth = true;
                _this.legendData.push(a.key);
                let tempData = [];
                a.val.filter(b => tempData.push(parseFloat(b.val.toFixed(2))));
                temp.data = tempData;
                _this.yMin = (Math.min.apply(null, tempData) < _this.yMin || _this.yMin == 0) ? Math.min.apply(null, tempData) : _this.yMin;
                _this.serises.push(temp);
              })
              data[0].val.filter(a => {
                _this.xData.push(a.key)
              });
              _this.myChart.setOption({
                series: _this.serises,
                xAxis: {
                  data: _this.xData,
                },
                yAxis: {
                  min: _this.yMin,
                },
                legend: {
                  data: _this.legendData,
                },
              })
            }
          })
        }
      },
      refreshData() {
        let _this = this;
        setInterval(() => _this.fetchData(_this.requestUrl ), 5000)
      },
      sizeFunction(x) {
        var min = Math.min.apply(null, this.series.map(o1 => {
          return Math.min.apply(null, o1.map(o2 => {
            return parseFloat(o2[2]);
          }));
        }));
        var max = Math.max.apply(null, this.series.map(o1 => {
          return Math.max.apply(null, o1.map(o2 => {
            return parseFloat(o2[2]);
          }));
        }));

        // 最小的5%，最大的15%
        var y = 5 + (x - min) / (max - min) * (15 - 5);
        var size = this.getFontSize(y + '%');
        return size;
      },
      getFontSize(val) {
        if (typeof (val) == 'number') return val;

        if (typeof (val) == 'string') {

          if (val.indexOf('%') > 0) {
            var tmp = parseFloat(val.replace('%', '')) / 100;
            let height = this.$refs.element.offsetHeight;
            return Math.round(height * tmp);
          }
        }

        return 0;
      }
    }
  }

</script>

<style scoped>
  .progressBar {
    width: 100%;
    height: 100%;
  }
</style>
