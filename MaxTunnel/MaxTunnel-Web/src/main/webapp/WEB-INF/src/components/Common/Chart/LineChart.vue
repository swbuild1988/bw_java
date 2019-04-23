<template>
  <div>
    <div class="Line" :id="id" ref="element"></div>
  </div>
</template>

<script>
  export default {
    name: 'Line-Chart',
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
        myChart: "",
        legendData: [],
        serises: [],
        xData: [],
        option: {},
        yMin: 0,
        timerId:null,
        color:['#C23531','#2F4554','#61A0A8','#D48265','#91C7AE','#749F83'],
      }
    },
    components: {},
    mounted() {
      this.init();
      this.resizeChart();
    },
    methods: {
      init() {
        this.drawLine();
        this.fetchData(this.requestUrl);
        this.refreshData();
      },
      resizeChart() {
        let _this = this;
        window.addEventListener("resize", function () {
          _this.drawLine();
          _this.myChart.resize();
          _this.fetchData(_this.requestUrl);
        });
      },
      drawLine() {
        let _this = this;
        _this.option = {
          title: {
            text: "",
            top: "left",
            left: "center",
            textStyle: {
              // color: "#fff",
              fontSize: window.innerHeight * 0.018,
            },
          },
          tooltip: {
            trigger: 'axis'
          },
          color:_this.color,
          legend: {
            itemHeight: 4,
            orient: "vertical",
            left: "center",
            bottom: "bottom",
            margin: 10,
            // itemGap: 1,
            itemWidth: window.innerHeight * 0.01,
            textStyle: {
              // color: '#fff',
              fontSize: window.innerHeight * 0.014,
            },
          },
          grid: {
            top: '15%',
            left: '14%',
            right: '10%',
            bottom: '20%'
          },
          xAxis: {
            type: 'category',
            axisLine: {
              lineStyle: {
                // color: '#fff'
              },
            },
            axisLabel: {
              fontSize: window.innerHeight * 0.010
            },
            data: []
          },
          yAxis: {
            type: 'value',
            axisLine: {
              margin: 2,
              lineStyle: {
                // color: '#fff'
              },
            },
            axisLabel: {
              fontSize: window.innerHeight * 0.010,
              formatter:

                function (value, index) {
                  if (value >= 10000 && value < 100000) {
                    value = value / 10000 + "万";
                  } else if (value >= 100000 && value < 1000000) {
                    value = value / 1000000 + "十万";
                  } else if (value >= 10000000) {
                    value = value / 10000000 + "千万";
                  }
                  return value ;
                }

                ,
            },
          },
          series: [{
            data: [],
            type: 'line',
            smooth: true
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
        _this.axios.get(requestUrl).then(result => {
          let {
            code,
            data
          } = result.data;
          if (code == 200) {
            _this.serises = [];
            _this.legendData = [];
            _this.xData = [];

            data.forEach((a,index) => {
              var temp = {};
              temp.name = a.key;
              temp.type = 'line';
              temp.smooth = true;

              _this.legendData.push({
                  name:a.key,
                  textStyle:{
                      color:_this.color[index]
                  }
              });
              let tempData = [];
              a.val.filter(b => tempData.push(parseFloat(b.val.toFixed(2))));
              temp.data = tempData;
              _this.yMin = (Math.min.apply(null, tempData) < _this.yMin || _this.yMin == 0) ? Math.min.apply(
                null, tempData) : _this.yMin;
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
      },
      refreshData() {
        let _this = this;
        this.timerId = setInterval(() => _this.fetchData(_this.requestUrl), 5000)
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
          let _this =this;

          clearInterval( _this.timerId )
      }

  }
</script>

<style scoped>
  .Line {
    width: 100%;
    height: 100%;
  }

</style>
