<template>
  <div class='SimpleBar' :id=id></div>
</template>

<script>
  export default {
    name: 'SimpleBar',
    props: {
      id: {
        default: ''
      },
      requestUrl: {
        default: ''
      },
      title: {
        default: 'SimpleBar'
      },
      color: {
        default: '#336666'
      },
      intervalTime: {
        default: 60000
      },
      tunnelId:{
        default: null
      },
      timeCycle:{
        default: 4
      },
      xData:{
        type: Array,
        default:['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      },
      legendData:{
        type: Array
      }
    },
    data() {
      return {
        myChart: {},
        // xData: [],
        yData: [],
        series:[],
        option: {
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
          legend: {
              data:this.legendData
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis : [
              {
                  type : 'category',
                  data : []
              }
          ],
          yAxis : [
              {
                  type : 'value'
              }
          ],
          series: []
        }
      }
    },
    mounted() {
      this.init();
      this.refreshData();
    },
    methods: {
      init() {
        this.drawBar();
        // this.changexAxis();
        this.fetchData(this.requestUrl);  
      },

      drawBar() {
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
          if (code == 200) {
            // console.log("data",data)
            // let newData = data.reduce((init, item) => {
            //   return {
            //     xData: init.xData == undefined ? [].concat(item.key) : [].concat(init.xData, item.key),
            //     yData: init.yData == undefined ? [].concat(item.val) : [].concat(init.yData, item.val)
            //   }
            // }, {})
            // if (JSON.stringify(newData.xData) != JSON.stringify(_this.xData) || JSON.stringify(newData.yData) != JSON.stringify(_this.xData)) {
            //   _this.xData = newData.xData;
            //   _this.yData = newData.yData;
            //   _this.myChart.setOption({
            //     xAxis: {data: _this.xData},
            //     series: {data: _this.yData}
            //   })
            // }
          }
          if(requestUrl=='stacChart'){
            this.yData = []
            for(var i=0; i<this.option.legend.data.length; i++ ){
              var obj = {
                name: this.option.legend.data[i],
                type: 'bar',
                stack: this.option.legend.data[i].substring(this.option.legend.data[i].length-2,this.option.legend.data[i].length),
                data: ['12','2','3','4','5','6','7','8','9','10','7','2'],
              }
              this.yData.push(obj)
            }
            if(this.xData.length==0){
              this.xData=['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            }
            this.myChart.setOption({
              series:this.yData,
              xAxis:{
                type : 'category',
                data: this.xData
              }
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
    },
  }
</script>

<style scoped>
  .SimpleBar {
    width: 100%;
    height: 100%;
  }
</style>
