<template>
  <div>
    <div class="Line" :id=id></div>
  </div>
</template>

<style scoped>
  .Line {
    width: 100%;
    height: 100%;
  }
</style>

<script>
  export default {
    name: 'Line-Chart',
    props: ['id', 'requestUrl', 'titleName', 'titleColor', 'titleFontSize', 'period'],
    data() {
      return {
        title: '折线',
        myChart: "",
        data: [],
        legendData: [],
        startC: "",
        endC: "",
        XCategory: [],
      }
    },
    components: {},
    mounted() {
      this.init();

    },
    methods: {
      init() {
        this.drawLine(this.titleName, this.titleColor, this.titleFontSize, this.XCategory);
        this.fetchData(this.requestUrl + this.period, this.titleColor);
        this.refreshData();
      },
      drawLine(titleName, titleColor, titleFontSize, XCategory) {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.myChart.showLoading();

        let option = {
          title: {
            text: titleName,
            x: 'center',
            textStyle: {
              color: titleColor,
              fontSize: titleFontSize,
            }
          },
          tooltip: {
            trigger: 'axis'
          },
          visualMap: [{
            show: false,
            seriesIndex: 0,
            inRange: {
              // color: ['#1ff7ff', 'rgba(58,255,90,0.4)', '#c307ff'],
              color: ['#cb514d'],
            },
            min: 0,
            max: 10000,
          },
          {
            show: false,
            type: 'continuous',
            seriesIndex: 1,
            inRange: {
              color: ['#5a6c77'],
            },
            min: 0,
            max: 10000,
          },
          {
            show: false,
            seriesIndex: 2,
            inRange: {
              color: ['#81b3ba'],
            },
            min: 0,
            max: 10000,
          },
          {
            show: false,
            seriesIndex: 3,
            inRange: {
              color: ['#dc9b85'],
            },
            min: 0,
            max: 10000,
          },
          {
            show: false,
            seriesIndex: 4,
            inRange: {
              color: ['#a7d3be'],
            },
            min: 0,
            max: 10000,
          },
          ],
          legend: {
            data: []
          },
          xAxis: {
            type: 'category',
            nameTextStyle: {
              fontSize: 16 ,    //单位：件的字号大小
            },
            data: XCategory
          },
          yAxis: {
            type: 'value',
            nameTextStyle: {
              fontSize: 16 ,    //单位：件的字号大小
            },
          },
          series: [{
            data: [],
            type: 'line',
            smooth: true
          }]
        };

        _this.myChart.hideLoading();
        _this.myChart.setOption(option);
        window.addEventListener("resize", this.myChart.resize);
      },
      fetchData(requestUrl) {
        let _this = this;
        _this.axios.get(requestUrl).then(result => {
          let {code, data} = result.data;
          let serises = [];
          _this.legendData = [];
          let XCategory = [];
          data.filter(item => {
            var temp = {};
            temp.name = item.key;
            _this.legendData.push(item.key);
            temp.type = 'line';
            temp.smooth = true;
            let tempData = [];
            item.val.filter(a => tempData.push(a.val.toFixed(2)));
            temp.data = tempData;
            serises.push(temp);
          });
          data[0].val.filter(item => {
            XCategory.push(item.key)
          });
          if (code == 200) {
            let newData = [];
            if (XCategory != null && serises != null) {
              _this.data = newData;
              _this.myChart.setOption({
                series: serises,
                xAxis: {
                  type: 'category',
                  data: XCategory
                },
                legend: {
                  data: _this.legendData,
                  bottom : '0',
                  // orient: 'vertical',
                  // right: '4%',
                },
              })
            }
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
