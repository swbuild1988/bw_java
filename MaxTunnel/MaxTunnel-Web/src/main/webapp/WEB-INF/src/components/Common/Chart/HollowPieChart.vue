<template>
  <div>
    <div class="hollowPie" :id=id ref="element"></div>
  </div>
</template>

<script>
  import {TunnelService} from '../../../services/tunnelService';

  export default {
    name: "hollow-pie-chart",
    props: {
      id: {
        type: String
      },
      unit: {
        type: String,
        default:"",
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
      requestUrl: {
        type: String
      },
      parameters: {
        type: Object
      }
    },
    data() {
      return {
        myChart: "",
        legendData: [],
        serises: [],
        xData: [],
        option: {},
        total: 0,
        refresh: {
          time: 1000 * 60,
          timeoutId: null
        }
      }
    },
    components: {},
    mounted() {
      this.init();
      this.resizeChart();
    },
    methods: {
      init() {
        this.drawHollowPie();
        this.fetchData(this.requestUrl);
        // this.refreshData();
      },
      resizeChart() {
        let _this = this;
        window.addEventListener("resize", function () {
          _this.drawHollowPie();
          _this.myChart.resize();
          _this.fetchData(_this.requestUrl);
        });
      },
      drawHollowPie() {
        let _this = this;
        _this.option = {
          title: {
            text: '',
            left: 'center',
            top: '50%',
            textStyle: {
              color: '#fff',
              fontSize: _this.getFontSize('11%'),
              align: 'center'
            }
          },
          graphic: {
            type: 'text',
            left: 'center',
            top: '35%',
            style: {
              text: '0',
              fill: '#fff',
              fontSize: _this.getFontSize('14%'),
            }
          },
          series: [{
            name: '',
            type: 'pie',
            radius: ['50%', '70%'],
            hoverAnimation: false,
            color: ['#2af0ff', '#ffff00', '#3397ff', '#48f974', '#7b6df8'],
            label: {
              normal: {
                formatter: function (params) {
                  return params.value + _this.unit;
                },
                textStyle: {
                  fontSize: _this.getFontSize('12%'),
                },
              },
            },
            labelLine: {
              normal: {
                length: 10,
                length2: 4,
                // lineStyle: {color: '#0faecb',}
              }
            },
            data: []
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
          if (_this.Server.serverName == "TunnelService.getVmTunnelsMessage") {
            TunnelService.getVmTunnelsMessage().then(
              result => {
                _this.serises = [];
                _this.total=0;
                let tempData = result.slice(0, 3)
                tempData.forEach(a => {
                  let temp = {};
                  _this.total+=a.value
                  temp.name = a.name;
                  temp.value = a.value;
                  _this.serises.push(temp);
                })
                _this.myChart.setOption({
                  series:{data: _this.serises},
                  graphic:{style:{text:   _this.total}}
                })
              },
              error => {
                this.Log.info(error)
              })
              .finally(()=>{
                _this.refresh.timeoutId = setTimeout(()=>{
                  _this.fetchData(_this.requestUrl)
                },_parseFloat(_this.refreshTime))
              })
          }
        }
        else {
          _this.axios.get(requestUrl).then(result => {
            let {code, data} = result.data;
            if (code == 200) {
              _this.serises = [];
              data.forEach(a => {
                var temp = {};
                temp.name = a.key;
                temp.type = 'pie';
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
          .finally(()=>{
            _this.refresh.timeoutId = setTimeout(()=>{
              _this.fetchData(_this.requestUrl)
            },_this.refresh.time)
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
    },
    beforeDestroy(){
      clearTimeout(this.refresh.timeoutId)
    }
  }

</script>

<style scoped>
  .hollowPie {
    width: 100%;
    height: 100%;
  }
</style>
