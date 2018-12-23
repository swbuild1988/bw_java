<template>
  <div>
    <div class="Line" :id=id></div>
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
        option: {
          title: {
            text: "",
            x: 'center',
            textStyle: {}
          },
          tooltip: {
            trigger: 'axis'
          },
          // visualMap: [{
          //   show: false,
          //   seriesIndex: 0,
          //   inRange: {
          //     color: ['#cb514d'],
          //   },
          //   min: 0,
          //   max: 10000,
          // },
          //   {
          //     show: false,
          //     type: 'continuous',
          //     seriesIndex: 1,
          //     inRange: {
          //       color: ['#5a6c77'],
          //     },
          //     min: 0,
          //     max: 10000,
          //   },
          //   {
          //     show: false,
          //     seriesIndex: 2,
          //     inRange: {
          //       color: ['#81b3ba'],
          //     },
          //     min: 0,
          //     max: 10000,
          //   },
          //   {
          //     show: false,
          //     seriesIndex: 3,
          //     inRange: {
          //       color: ['#dc9b85'],
          //     },
          //     min: 0,
          //     max: 10000,
          //   },
          //   {
          //     show: false,
          //     seriesIndex: 4,
          //     inRange: {
          //       color: ['#a7d3be'],
          //     },
          //     min: 0,
          //     max: 10000,
          //   },
          // ],
          legend: {
            data: []
          },
          xAxis: {
            type: 'category',
            nameTextStyle: {
              fontSize: 16,    //单位：件的字号大小
            },
            data: []
          },
          yAxis: {
            type: 'value',
            nameTextStyle: {
              fontSize: 16,    //单位：件的字号大小
            },
          },
          series: [{
            data: [],
            type: 'line',
            smooth: true
          }]
        },
      }
    },
    components: {},
    mounted() {
      this.init();
    },
    methods: {
      init() {
        this.drawLine();
        this.fetchData(this.requestUrl + this.period, this.titleColor);
        this.refreshData();
      },
      drawLine() {
        let _this = this;
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
              a.val.filter(b => tempData.push(b.val.toFixed(2)));
              temp.data = tempData;
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
              legend: {
                data: _this.legendData,
                bottom: '0',
              },
            })
          }
        })
      },
      refreshData() {
        let _this = this;
        // setInterval(() => _this.fetchData(_this.requestUrl + _this.period), 5000)
      },

    }
  }

</script>

<style scoped>
  .Line {
    width: 100%;
    height: 100%;
    position: relative;
  }
</style>
