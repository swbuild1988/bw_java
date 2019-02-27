<template>
  <div class='ComplexBar' :id=id ref="element"></div>
</template>

<script>
  export default {
    name: "complex-bar-chart",
    props: {
      id: {
        default: ''
      },
      requestUrl: {
        default: ''
      },
      yAxisName: {
        default: ''
      },
      title: {
        default: 'ComplexBar'
      },
      color: {
        default: '#336666'
      },
      intervalTime: {
        default: 60000
      },
      bgColor: {
        default: '#fff',
        type: String
      },
      titleColor: {
        default: '#535353',
        type: String
      },
      fontSizeNum: {
        default: '5%',
        type: String
      },
      gridTop: {
        default: '10%',
        type: String
      }
    },
    data() {
      return {
        myChart: {},
        xData: [],
        yData: [],
        series: [],
        option: {
          title: {
            text: 'title',
            fontSize: 25,
          },
          backgroundColor: '#fff',
          tooltip: {
            trigger: 'axis',
            fontSize: 16,
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            align: 'right',
            right: 10,
            textStyle: {
              fontSize: 16,
            },
          },
          xAxis: [{
            type: 'category',
            axisLabel: {
              fontSize: 16,
            },
            data: []
          }],
          yAxis: [{
            type: 'value',
            fontSize: 16,
            name: '单位(个)',
            nameTextStyle: {
              color: '#5fb6ff',
              fontSize: 14,    //单位：件的字号大小
            },
          }],
          series: []
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
        this.drawComplexBar();
        this.fetchData(this.requestUrl);
        window.addEventListener('resize', this.drawComplexBar);
      },

      drawComplexBar() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.option.title.text = _this.title;
        this.option = {
          title: {
            text: this.title,
            textStyle: {
                fontSize: this.getFontSize(this.fontSizeNum),
                color: this.titleColor
            },
          },
          backgroundColor: this.bgColor,
          tooltip: {
            trigger: 'axis',
            fontSize: this.getFontSize(this.fontSizeNum),
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            align: 'right',
            // right: 1,
            // left: 30,
            // x: 'right',
            top: _this.getFontSize(this.fontSizeNum),
            textStyle: {
              fontSize: this.getFontSize('5%'),
              color: this.titleColor
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            top: this.gridTop,
            bottom: '3%',
            containLabel: true,
            fontSize: this.getFontSize('5%'),
          },
          xAxis: [{
            type: 'category',
            axisLabel: {
              fontSize: this.getFontSize(this.fontSizeNum),
              color: this.titleColor
            },
            axisLine: {
                lineStyle: {
                    color: this.titleColor
                }
            },
            data: ['新虹桥', '中山公园', '虹桥', '镇宁路', '天山古北']
          }],
          yAxis: [{
            type: 'value',
            // name: '单位  (个)',
            nameTextStyle: {
              color: '#5fb6ff',
              fontSize: 14,    //单位：件的字号大小
            },
            axisLine: {
                lineStyle: {
                    color: this.titleColor
                }
            },
            axisLabel: {
              fontSize: this.getFontSize(this.fontSizeNum),
              color: this.titleColor
            }
          }],
          series: []
        }
        _this.myChart.setOption(_this.option);
        window.addEventListener('resize', _this.myChart.resize);
      },

      fetchData(requestUrl) {
        let _this = this;
        _this.axios.get(requestUrl).then(result => {
          let {code, data} = result.data;
          let serises = [], xData = []
          if (code == 200) {
            if (JSON.stringify(data) != null) {
              for (var item in data) {
                let temp = {};
                temp.name = item;
                let tempdata = data[item];
                xData = [];
                let yData = [];
                tempdata.forEach(a => {
                  xData.push(a.key);
                  yData.push(a.val);
                });
                temp.data = yData;
                temp.type = 'bar';
                serises.push(temp);
              }
            }
            _this.series = serises;
            _this.xData = xData
            //测试使用模拟静态数据
            _this.myChart.setOption({
              xAxis: {data: _this.xData},
              series: _this.series
            })
          }
        })
        //测试使用模拟静态数据
        if ("tunnels/equipments/types/test" == requestUrl) {
          let xData = ['古城大街', '实验路', '经三路', '经二路', '纬三路', '监控中心'];
          let serise = [
            {
              name: '安防',
              data: [203, 179, 364, 259, 437, 320],
              type: 'bar'
            },
            {
              name: '消防',
              data: [104, 145, 190, 120, 210, 187],
              type: 'bar'
            },
            {
              name: '视频', 
              data: [387, 271, 159, 341, 231, 812], 
              type: 'bar'
            },
            {
              name: '环境',
              data: [227, 328, 335, 285, 424, 394],
              type: 'bar'
            },
            {
              name: '监控',
              data: [735, 231, 322, 712, 120, 270],
              type: 'bar'
            },
            {
              name: '机电',
              data: [250, 156, 113, 149, 230, 221],
              type: 'bar'
            }
          ];
          _this.myChart.setOption({
            xAxis: {data: xData},
            series: serise
          })
        }
      },
      //定时刷新数据
      refreshData() {
        let _this = this;
        setInterval(() => {
          _this.fetchData(_this.requestUrl);
        }, _this.intervalTime)
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
  }
</script>

<style scoped>
  .ComplexBar {
    width: 100%;
    height: 100%;
  }
</style>

