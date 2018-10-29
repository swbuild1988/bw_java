<template>
  <div class='ComplexBar' :id=id></div>
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
          backgroundColor: 'rgba(225,225,225,0.8)',
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
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
            fontSize: 16,
          },
          xAxis: [{
            type: 'category',
            axisLabel: {
              fontSize: 16,
            },
            data: ['新虹桥', '中山公园', '虹桥', '镇宁路', '天山古北']
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
          series: [{
            name: '包租费',
            type: 'bar',
            data: [20, 12, 31, 34, 31]
          }, {
            name: '装修费',
            type: 'bar',
            data: [10, 20, 5, 9, 3]
          }, {
            name: '保洁费',
            type: 'bar',
            data: [1, 1, 2, 3, 1]
          }]
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
      },

      drawComplexBar() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.option.title.text = _this.title;
        _this.myChart.setOption(_this.option);
        window.addEventListener('resize', this.myChart.resize);
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
        if ("barChart" == requestUrl) {
          let xData = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
          let serise = [
            {
              name: '检查企业数',
              data: [2031, 1793, 3640, 2593, 4377, 3201, 2275, 3289, 3356, 2859, 4244, 3945],
              type: 'bar'
            },
            {
              name: '完成整改企业数',
              data: [1043, 1456, 1900, 1200, 2100, 1870, 980, 1569, 1130, 1490, 2300, 2210],
              type: 'bar'
            },
            {name: '违法违规企业数', data: [787, 571, 999, 341, 231, 812, 735, 231, 322, 712, 1230, 870], type: 'bar'}
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
    },
  }
</script>

<style scoped>
  .ComplexBar {
    width: 100%;
    height: 100%;
  }
</style>

