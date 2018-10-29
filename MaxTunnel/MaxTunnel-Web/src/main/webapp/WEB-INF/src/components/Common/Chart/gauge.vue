<template>
  <div>
    <div class="nameTop">{{titleName}}</div>
    <div class="pie" :id=gaugeId></div>
  </div>
</template>

<style scoped>
  .pie {
    position: relative;
    margin-top: 1%;
    width: 100%;
    height: 100%;

  }

  .nameTop {
    position: absolute;
    fontFamily: "'Microsoft YaHei";
    fontWeight: "normal";
    z-index: 1003;
    color: #fff;
    top: .3em;
    left: .7em;
  }
</style>

<script>
  import VmTitle from '../../VM/VMTitle'
  import $ from 'jquery'

  export default {
    name: 'gauge',
    props: ['gaugeId', 'requestUrl', 'titleName', 'startColor', 'endColor','startDynamicStyle','titleFontSize'],
    data() {
      return {
        title: '仪表盘',
        myChart: "",
        data: [],
        startC: "",
        endC: "",
        DOMClass:{
            $pie:"",
            $Map:"",
            height:""
        }
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
        let titleFontSize = this.titleFontSize || 12;

        if(this.startDynamicStyle){ this.dynamicSetStyle(); }
        this.drawGauge(this.titleName, this.startColor, this.endColor,titleFontSize);
        this.startC = this.startColor;
        this.endC = this.endColor;
        this.fetchData(this.requestUrl, this.titleName, this.startC, this.endC);
        this.refreshData();
      },
      drawGauge(titleName, startColor, endColor,titleFontSize) {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.gaugeId));
        _this.myChart.showLoading();

        let option = {
          title: {
            text: "",
            x: 'center',
            y: 'center',
            top: '45%',
            textStyle: {
              color: startColor,
              fontSize: titleFontSize,
            }
          },
          color: ['rgba(176, 212, 251, 1)'],
          // backgroundColor: 'rgba(24, 72, 107, .9)',
          backgroundColor: 'rgba(51, 85, 153, 0)',
          series: [{
            name: titleName,
            type: 'pie',
            radius: ['50%', '60%'],
            center: ['50%', '60%'], // 默认全局居中
            hoverAnimation: false,
            label: {
              normal: {
                show: false,
              }
            },
              title:{
                  textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                      fontWeight: 'bolder',
                      fontSize: 16,
                      fontStyle: 'italic',
                      color: 'red',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
            data: [{
              value: 20,
              itemStyle: {
                normal: {
                  color: { // 完成的圆环的颜色
                    colorStops: [{
                      offset: 0,
                      color: startColor // 0% 处的颜色
                    }, {
                      offset: 1,
                      color: endColor // 100% 处的颜色
                    }]
                  },
                  label: {
                    show: false
                  },
                  labelLine: {
                    show: false
                  }
                }
              }
            }, {
              value: 80,
            }],
          }]
        };
        _this.myChart.hideLoading();
        _this.myChart.setOption(option);
        window.addEventListener("resize", this.myChart.resize);
      },
      fetchData(requestUrl, titleName, startColor, endColor) {
        let _this = this;
        _this.axios.get(requestUrl).then(result => {
          let {code, data} = result.data;
          // if (code == 200) {
          //
          //   let newData = data.map(curObj => {
          //     return {
          //       value: curObj.val,
          //       name: curObj.key
          //     }
          //   })
          //
          //   if (JSON.stringify(newData) != JSON.stringify(data)) {
          //     _this.data = newData
          //     _this.myChart.setOption({
          //       series: {data: _this.data}
          //     })
          //   }
          // }
          // else
          {
            let newData = {
              value: (Math.random() * 100).toFixed(2) - 0,
              name: titleName
            };
            _this.myChart.setOption({
              title: {
                text: newData.value
              },
              series: [{
                name: titleName,
                data: [{
                  value: newData.value,
                  itemStyle: {
                    normal: {
                      color: { // 完成的圆环的颜色
                        colorStops: [{
                          offset: 0,
                          color: startColor // 0% 处的颜色
                        }, {
                          offset: 1,
                          color: endColor // 100% 处的颜色
                        }]
                      },
                      label: {
                        show: false
                      },
                      labelLine: {
                        show: false
                      }
                    }
                  }
                }, {
                  value: 100 - newData.value,
                }]
              }]
            });
          }

        })
      },
      refreshData() {
        let _this = this;
        setInterval(() => _this.fetchData(_this.requestUrl, _this.titleName, _this.startColor, _this.endColor), 5000)
      },
      dynamicSetStyle(){ //动态修改样式
          let _this=this;

          _this.DOMClass.$pie = _this.DOMClass.$pie ? _this.DOMClass.$pie : $('.pie');
          _this.DOMClass.$Map = _this.DOMClass.$Map ?  _this.DOMClass.$Map : $('.Map');
          _this.DOMClass.height = _this.DOMClass.height ? _this.DOMClass.height : window.innerHeight;

          _this.DOMClass.$pie.css({
              width:_this.DOMClass.$Map.width()*0.085,
              height:_this.DOMClass.height*0.085
          })
      }

    }
  }

</script>
