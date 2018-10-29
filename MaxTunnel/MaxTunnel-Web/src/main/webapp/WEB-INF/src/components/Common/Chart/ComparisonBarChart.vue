<template>
  <div class='comparisonBar' :id=id></div>
</template>


<script>
  import  axios from 'axios'
  export default {
    name: "comparison-bar-chart",
    props: {
      id: {
        default: ''
      },
      yAxisName: {
        default: '单位：件',
      },
      requestTotalUrl: {
        default: ''
      },
      requestPartUrl: {
        default: ''
      },
      title: {
        default: 'comparisonBar'
      },
      color: {
        default: '#336666'
      },
      intervalTime: {
        default: 60000
      },
    },
    data() {
      var gap = 0;
      var myColor = ['#e63810', '#ff6b00', '#e3b61f', '#13b5b1', '#20ff26'];   //五根柱子的颜色
      var myBgColor = ['rgba(230,56,16,0.2)', 'rgba(255,107,0,0.2)', 'rgba(227,182,31,0.2)', 'rgba(19,181,177,0.2)', 'rgba(70,249,86,0.2)'];
      //柱子数据
      var data = [8, 15, 10, 6];
      return {
        myChart: {},
        inlineData: [],//对比柱状图内柱数据
        outData: [],//对比柱状图外柱数据
        xData: [],
        yData: [],
        series:[],
        option: {
          backgroundColor:'rgba(225,225,225,0.8)',
          grid: {
            left: '3%',
            top: '20%',
            right: '2%',
            bottom: '5%',
            containLabel: true
          },
          title: {
            text: "title",
            textStyle: {
              color: '#3980ff',
            }
          },
          xAxis: [{
            type: 'category',
            fontSize: 16 ,
            axisTick: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(160,160,160,0.3)',
              }
            },
            axisLabel: {
              textStyle: {
                color: function (param, index) {
                  return myColor[index]
                },
                fontSize: 16 ,
              }
            },
            data: ['01', '02', '03', '04']  //xData
          }, {
            type: 'category',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              show: false
            },
            splitArea: {
              show: false
            },
            splitLine: {
              show: false
            },
            data: []
          },
          ],
          yAxis: {
            type: 'value',
            name: null,
            nameGap: 35 + gap,
            nameTextStyle: {
              color: '#5fb6ff',
              fontSize: 16 ,    //单位：件的字号大小
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: 'rgba(160,160,160,0.3)',
              }
            },
            axisLabel: {
              textStyle: {
                color: 'rgba(95,233,255,0.8)',
                fontSize: 16 ,           //y坐标轴字号大小
              }
            }
          },
          series: [{
            type: 'bar',
            xAxisIndex: 1,
            itemStyle: {
              normal: {
                show: true,
                color: function (params) {
                  var num = myBgColor.length;
                  return myBgColor[params.dataIndex % num]
                },
                barBorderRadius: 50,
                borderWidth: 0,
                borderColor: '#333',
              }
            },
            label: {
              normal: {
                show: true,
                // formatter: function (params) {
                //   var stuNum = 0;
                //   data.forEach(function (value, index, array) {
                //     if (params.dataIndex == index) {
                //       stuNum = value;
                //     }
                //   })
                //   return stuNum;
                // },
                position: 'top',
                textStyle: {
                  color: function (params) {
                    var num = myBgColor.length;
                    return myBgColor[params.dataIndex % num]
                  },
                  fontSize: 25,         //柱子上的数字字号大小
                }
              }
            },
            barWidth: '25%',
            data: data //outdata
          },
            {
              type: 'bar',
              itemStyle: {
                normal: {
                  show: true,
                  color: function (params) {
                    var num = myColor.length;
                    return myColor[params.dataIndex % num]
                  },
                  barBorderRadius: 50,
                  borderWidth: 0,
                  borderColor: '#333',
                }
              },
              label: {
                normal: {
                  show: true,
                  position: 'inside',
                  formatter: '{c}%'
                }
              },
              barWidth: '25%',
              data: [] //inlineData
            }
          ]
        },
      }
    },
    components: {},
    mounted() {
      this.option.yAxis.name = this.yAxisName;
      this.init();
      this.refreshData();
    },
    methods: {
      init() {
        this.comparisonBar();
        this.fetchData();
      },
      comparisonBar() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
        _this.option.title.text = _this.title;
        _this.myChart.setOption(_this.option);
        window.addEventListener('resize', this.myChart.resize);
      },

      fetchData() {
        let _this = this;
        axios.all([
          _this.axios.get(_this.requestTotalUrl),
          _this.axios.get(_this.requestPartUrl),
        ]).then(axios.spread(function (totalData, partData) {
          // 上面两个请求都完成后，才执行这个回调方法
          if (JSON.stringify(totalData.data) !=null &&JSON.stringify(partData.data) != null ){
            _this.series=[];
            _this.xData =[];
              totalData.data.data.forEach(a=>{
                _this.xData.push(a.key);
            })
            let tempTotal=[];
            totalData.data.data.forEach(a=>{
              tempTotal.push(a.val);
            })
            _this.series.push({data:tempTotal});
            let tempPart=[];
            partData.data.data.forEach(a=>{
              tempPart.push(a.val);
            })
            _this.series.push({data:tempPart});
            // _this.xData = ["古城大街", "试验路", "经三路", "经二路", "纬三路"];
            // _this.series=[{data:[100,90,102,98,94]},{data:[21,34,22,31,27]}];
            _this.myChart.setOption({
              xAxis: {data: _this.xData},
              series:  _this.series
            })
          }
        }));
        //
        // _this.axios.get(_this.requestTotalUrl).then(result => {
        //   let {code, data} = result.data;
        //   code=200;
        //   if (code == 200) {
        //     let newData={};
        //     if (JSON.stringify(newData.xData) != JSON.stringify(_this.xData) || JSON.stringify(newData.yData) != JSON.stringify(_this.xData)) {
        //       _this.xData = newData.xData;
        //       _this.yData = newData.yData;
        //       //测试使用模拟静态数据
        //        if("getCompareData"==requestTotalUrl){
        //         _this.series=[];
        //         _this.xData = ["古城大街", "试验路", "经三路", "经二路", "纬三路"];
        //         _this.series=[{data:[100,90,102,98,94]},{data:[21,34,22,31,27]}];
        //       }
        //     }
        //   }
        // })
      },
      //定时刷新数据
      refreshData() {
        let _this = this;
        setInterval(() => {
          _this.fetchData();
        }, _this.intervalTime)
      },
    },
  }
</script>

<style scoped>
  .comparisonBar {
    width: 100%;
    height: 100%;
  }
</style>
