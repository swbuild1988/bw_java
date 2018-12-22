<template>
  <div>
    <div class="dataContainer1">
      <Row class="chart">
        <Col span="12">
          <Row>
            <Col span="6">
              <h1 class="total">{{ total }} 千瓦时</h1>
            </Col>
            <Col span="7">
              <DatePicker type="datetime" placeholder="请选择开始日期和时间" format="yyyy-MM-dd HH:mm" class="searchTime" v-model="query.startTime"></DatePicker>
            </Col>
            <Col span="7">
              <DatePicker type="datetime" placeholder="请选择结束日期和时间" format="yyyy-MM-dd HH:mm" class="searchTime" v-model="query.endTime"></DatePicker>
            </Col>
            <Col span="4">
              <Button type="primary" @click="queryData" icon="ios-search">查询</Button>
            </Col>
          </Row>
          <Row class="catContent">
            <Col span="12" v-for="(cat,index) in catDatas" :key="index">
              <div class="cat">
                <data-box2 v-bind="cat"></data-box2>
              </div>
            </Col>
          </Row>
        </Col>
        <Col span="12">
          <div class="piechart" id="pie">
            <!-- <pie-chart v-bind="pieChart"></pie-chart> -->
          </div>
        </Col>
      </Row>
      <hr>
      <div class="linechart">
         <div style="float:right;margin-top: 4px">
          周期： <Select v-model="period" @on-change='changePeriod()' style=" width:70px">
          <Option v-for="item in periodList" :value="item.val" :key="item.val">{{ item.key }}</Option>
        </Select>
        </div>
        <div class="Line" id="line"></div>
      </div>
    </div>
  </div>
</template>

<script>
import DataBox2 from "../../../../components/Common/Box/DataBox2";
import EnergyIcon from "../../../../assets/UM/TunnelEnergy.png";
import { EnumsService } from "../../../../services/enumsService";
import { energyConsumptionService } from "../../../../services/energyConsumptionService";
export default {
    name: "eneryCategory",
    data() {
        return {
            query: {
                id: null,
                startTime: null,
                endTime: null
            },
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            period: 3,
            periodList: [],
            colors: ["#8C91A7", "#7CA9A9", "#ffce2d", "#BD4932"],
            catDatas: [],
            total: 100,
            option: {
                backgroundColor: "#eeeeee",
                title: {
                    text: "Customized Pie",
                    x: "center",
                    textStyle: {
                        fontWeight: "normal",
                        color: "#030303",
                        fontSize: "20"
                    }
                },

                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },

                visualMap: {
                    show: false,
                    min: "",
                    max: "",
                    inRange: {
                        colorLightness: [0, 1]
                    }
                },
                series: [
                    {
                        name: "各类型设备数",
                        type: "pie",
                        // radius: "55%",
                        center: ["50%", "50%"],
                        data: [].sort(function(a, b) {
                            return a.value - b.value;
                        }),
                        // roseType: "radius",
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        lableLine: {
                            normal: {
                                show: true
                            },
                            animationType: "scale",
                            animationEasing: "elasticOut",
                            animationDelay: function(idx) {
                                return Math.random() * 200;
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            shadowColor: "rgba(128, 128, 128, 0.5)",
                            shadowBlur: 10,
                            shadowOffsetX: 7,
                            shadowOffsetY: 7
                        }
                    }
                ]
            },
            pie: {},
            line: "",
            data: [],
            legendData: [],
            XCategory: [],
            EnergyIcon: EnergyIcon
        };
    },
    components: {
        DataBox2
    },
    mounted() {
        this.query.id = parseInt(this.$route.params.id);
        this.initTime();
        this.pie = this.$echarts.init(document.getElementById("pie"));
        this.option.title.text = "能耗类别统计";
        this.queryData();
        this.line = this.$echarts.init(document.getElementById("line"));
        this.line.showLoading();
        this.fetchData();
    },
    watch: {
        $route: function() {
            this.query.id = parseInt(this.$route.params.id);
            this.initTime();
            this.queryData();
            this.fetchData();
        }
    },
    methods: {
        initTime() {
            let _this = this;
            EnumsService.getTimeCycle().then(
                result => {
                    _this.periodList = result.slice(2, 4);
                },
                error => {
                    console.log(error);
                }
            );
        },
        queryData() {
            let params = {};
            params.id = this.query.id;
            if (this.query.startTime && this.query.endTime) {
                params.startTime = new Date(this.query.startTime).getTime();
                params.endTime = new Date(this.query.endTime).getTime();
            } else {
                if (this.query.startTime) {
                    params.startTime = new Date(this.query.startTime).getTime();
                } else {
                    if (this.query.endTime) {
                        params.endTime = new Date(this.query.endTime).getTime();
                    }
                }
            }

            let _this = this;
            energyConsumptionService.getECTotal(params).then(
                result => {
                    _this.total = result.val;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            energyConsumptionService.getECCategory(params).then(result => {
                var tempCount = 0;
                result.filter(function(item) {
                    tempCount += item.val;
                });
                let newData = null;
                if (tempCount == 0) {
                    newData = result.map(curObj => {
                        return {
                            value: 0,
                            name: curObj.key + " 0%"
                        };
                    });
                } else {
                    newData = result.map(curObj => {
                        return {
                            value: curObj.val,
                            name:
                                curObj.key +
                                " " +
                                ((100 * curObj.val) / tempCount).toFixed(2) +
                                "%"
                        };
                    });
                }
                _this.option.series[0].data = newData;
                _this.pie.setOption(_this.option);
                // dataBox
                _this.catDatas = [];
                result.forEach((cat, index) => {
                    let colorIndex =
                        (index + _this.colors.length) % _this.colors.length;
                    let temp = {};
                    temp.value = cat.val.toFixed(2);
                    temp.unit = "千瓦时";
                    temp.label = cat.key;
                    temp.backGroundColor = _this.colors[colorIndex];
                    temp.imgSrc = _this.EnergyIcon;
                    temp.tagColor = "#f7f7f7";
                    _this.catDatas.push(temp);
                });
            });
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryData();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryData();
        },
        changePeriod() {
            this.lineChart.period = this.period;
            this.fetchData();
        },
        fetchData() {
            let option = {
                title: {
                    text: "管廊耗电量分析",
                    x: "center",
                    textStyle: {
                        color: "#030303",
                        fontSize: 25
                    }
                },
                tooltip: {
                    trigger: "axis"
                },
                visualMap: [
                    {
                        show: false,
                        seriesIndex: 0,
                        inRange: {
                            color: ["#1ff7ff", "rgba(58,255,90,0.4)", "#c307ff"]
                        },
                        min: 0,
                        max: 10000
                    },
                    {
                        show: false,
                        type: "continuous",
                        seriesIndex: 1,
                        inRange: {
                            color: ["#1ff7ff", "rgba(58,255,90,0.4)", "#c307ff"]
                        },
                        min: 0,
                        max: 10000
                    },
                    {
                        show: false,
                        seriesIndex: 2,
                        inRange: {
                            color: ["#1ff7ff", "rgba(58,255,90,0.4)", "#c307ff"]
                        },
                        min: 0,
                        max: 10000
                    },
                    {
                        show: false,
                        seriesIndex: 3,
                        inRange: {
                            color: ["#1ff7ff", "rgba(58,255,90,0.4)", "#c307ff"]
                        },
                        min: 0,
                        max: 10000
                    },
                    {
                        show: false,
                        seriesIndex: 4,
                        inRange: {
                            color: ["#1ff7ff", "rgba(58,255,90,0.4)", "#c307ff"]
                        },
                        min: 0,
                        max: 10000
                    }
                ],
                legend: {
                    data: []
                },
                xAxis: {
                    type: "category",
                    data: this.XCategory
                    // axisLabel :{
                    //   rotate: 90
                    // }
                },
                yAxis: {
                    type: "value"
                },
                series: [
                    {
                        data: [],
                        type: "line",
                        smooth: true
                    }
                ]
            };

            this.line.hideLoading();
            this.line.setOption(option);
            let _this = this;
            energyConsumptionService
                .getECCategoryDetail(_this.query.id, _this.period)
                .then(
                    result => {
                        let serises = [];
                        _this.legendData = [];
                        let XCategory = [];
                        result.filter(item => {
                            var temp = {};
                            temp.name = item.key;
                            _this.legendData.push(item.key);
                            temp.type = "line";
                            temp.smooth = true;
                            let tempData = [];
                            item.val.filter(a =>
                                tempData.push(a.val.toFixed(2))
                            );
                            temp.data = tempData;
                            serises.push(temp);
                        });
                        result[0].val.filter(item => {
                            XCategory.push(item.key);
                        });
                        let newData = [];
                        if (XCategory != null && serises != null) {
                            _this.data = newData;
                            _this.line.setOption({
                                series: serises,
                                xAxis: {
                                    type: "category",
                                    data: XCategory
                                },
                                legend: {
                                    data: _this.legendData,
                                    bottom: "0"
                                    // orient: 'vertical',
                                    // right: '4%',
                                }
                            });
                        }
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
        }
    }
};
</script>
<style scoped>
/*.query{
    background-color: white;
    padding: 10px;
  }*/
.chart {
    margin: 20px;
}
.piechart {
    height: 380px;
    margin-right: 6px;
}
.searchTime {
    width: 90%;
}
.dataContainer1 {
    /* background-color: white;*/
    padding: 0 10px 20px 10px;
    margin-top: 10px;
    overflow: hidden;
}
.linechart {
    margin-top: 10px;
    /*  background-color: white;*/
}
.catContent {
    margin-top: 20px;
    margin-right: 20px;
    height: 320px;
    overflow: auto;
}
.cat {
    margin: 10px;
}
.total {
    font-size: 30px;
}
.Line {
    height: 35vh;
    width: 90%;
}
</style>
