<template>
  <div class="PipeRackContainer BackgroundImage lf BodyCenterPadding">
    <div class="PipeRack" :id=pieChartid></div>
    <vm-title :title-text="title"></vm-title>
  </div>
</template>

<style scoped>
  .PipeRack {
    position: relative;
    top: 23%;
    left: 0%;
    width: 100%;
    height: 77%;
  }
</style>

<script>
  import VmTitle from '../VMTitle'

  export default {
    name: 'PipeRack',
    props: ['pieChartid', 'requestUrl'],
    data() {
      return {
        title: '管廊年度总耗电量',
        myChart: '',
        data: []
      }
    },
    components: {
      VmTitle
    },
    mounted() {
      this.init();
    },
    methods: {
      init() {
        this.drawPie();
        this.fetchData(this.requestUrl);
        // this.refreshData();
      },
      drawPie() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.pieChartid));
        let option = {
          title: {},
          tooltip: {
            trigger: 'item',
            formatter: params => `${params.name} : <span style="font-weight: bold;color:${params.color} ;">${params.value}</span> kwh`
          },
          series: [
            {
              type: 'pie',
              radius: [40, 100],
              center: ['50%', '50%'],
              data: _this.data,
              // roseType: 'radius',
              label: {
                normal: {
                  show: false,
                  position: 'center',
                  textStyle: {}
                },
                emphasis: {
                  show: true,
                  textStyle: {
                    fontSize: '10',
                    fontWeight: 'bold'
                  }
                }
              },
              labelLine: {
                normal: {
                  show: false,
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.3)'
                  },
                  smooth: 0.2,
                  length: 10,
                  length2: 20
                }
              },
              animationType: 'scale',
              animationEasing: 'elasticOut',
            }
          ]
        };
        _this.myChart.setOption(option);
        window.addEventListener("resize", this.myChart.resize);
      },
      fetchData(requestUrl) {
        let _this = this;

        _this.axios.get(requestUrl).then(result => {
          let {code, data} = result.data;
          if (code == 200) {

            let newData = data.map(curObj => {
              return {
                value: curObj.val,
                name: curObj.key
              }
            })

            if (JSON.stringify(newData) != JSON.stringify(data)) {
              _this.data = newData
              _this.myChart.setOption({
                series: {data: _this.data}
              })
            }
          }

        })
      },
      refreshData() {
        let _this = this;
        setInterval(() => _this.fetchData(_this.requestUrl), 500000)
      },
    }
  }

</script>
