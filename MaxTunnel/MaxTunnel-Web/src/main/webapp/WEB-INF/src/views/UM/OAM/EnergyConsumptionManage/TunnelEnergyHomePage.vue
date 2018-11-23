<template>
  <div style="padding-left: 10px;padding-top: 10px;">
    <Row :gutter="16">
      <Col span="5">
        <data-box2 v-bind="historyData">
        </data-box2>
      </Col>

      <Col span="5">
        <data-box2 v-bind="curYearData">
        </data-box2>
      </Col>

      <Col span="5">
        <data-box2 v-bind="curMonthData">
        </data-box2>
      </Col>
    </Row>

    <Row style="margin-top: 10px;">
      <!-- 管廊能耗统计 饼图 -->
      <Col span="8">
        <div style="height:35vh;">
          <pie-chart v-bind="pieChart"></pie-chart>
        </div>
      </Col>
      <Col span="15" offset="1">

      <Row>
        <Col span="24">
        <div style="float:right;min-width: 485px">
          <label>起始时间：</label>
          <DatePicker v-model="startTime" type="date" placeholder="Select date" style="width:120px"></DatePicker>
          --
          <DatePicker v-model="endTime" type="date" placeholder="Select date" style="width: 120px"></DatePicker>
          <Button type="primary" icon="ios-search" size="large" @click="queryEnergies">查询</Button>
          <Button type="primary" icon="ios-download-outline" size="large" @click="exportData">导出</Button>
        </div>
        </Col>
        <Col span="23" offset="1">
        <Table size="small" stripe border :columns="tableColumn" :data="tableData" ref="table"></Table>
        <div style="float:right;margin-top: 4px">
          周期： <Select v-model="period" @on-change='changePeriod(period)' style=" width:70px">
          <Option v-for="item in periodList" :value="item.val" :key="item.val">{{ item.key }}</Option>
        </Select>
        </div>
        </Col>
      </Row>
      </Col>
    </Row>
    <Row>
      <Col span="8">
      <!-- 雷达图 -->
        <div style="height:35vh;">
          <radar-chart v-bind="radarChart"></radar-chart>
        </div>
      </Col>
      <Col span="14" offset="1">
      <row>
        <Col span="24">
          <LineChart :style="{height:'35vh',width:'100%'}" v-bind="lineChart" ref="childChart"></LineChart>
        </Col>
      </row>
      </Col>
    </Row>
  </div>
</template>
<style scoped>
h1 {
    margin: 2% 1% 0 0;
    width: 8%;
    float: left;
}
</style>
<script>
import DataBox2 from "../../../../components/Common/Box/DataBox2";
import LineChart from "../../../../components/Common/Chart/LineChart";
import PieChart from "../../../../components/Common/Chart/SimplePieChart";
import RadarChart from "../../../../components/Common/Chart/SimpleRadarChart";
import EnergyIcon from "../../../../assets/UM/TunnelEnergy.png";
import { EnumsService } from "../../../../services/enumsService";
import { energyConsumptionService } from "../../../../services/energyConsumptionService";

export default {
    name: "tunnel-energy-consumption",
    data() {
        return {
            // 上面databox的参数
            historyData: {
                value: 0,
                unit: "千瓦时",
                label: "历史总能耗",
                backGroundColor: "#758aff",
                imgSrc: EnergyIcon,
                tagColor: "yellow"
            },
            curYearData: {
                value: 0,
                unit: "千瓦时",
                label: "本年度能耗",
                backGroundColor: "#ffce2d",
                imgSrc: EnergyIcon,
                tagColor: "#22b0c8"
            },
            curMonthData: {
                value: 0,
                unit: "千瓦时",
                label: "本月度能耗",
                backGroundColor: "#1dc8b2",
                imgSrc: EnergyIcon,
                tagColor: "#6da1ff"
            },
            screenWidth: 1200,
            startTime: "",
            endTime: "",
            period: 3,
            periodList: [],
            pieChart: {
                requestUrl: "tunnels/items/energies ",
                id: "tunnelEnergyPieChart",
                parameters: {
                    option: {
                        title: {
                            text: "管廊能耗统计",
                            x: "center",
                            textStyle: {
                                fontWeight: "normal",
                                color: "#030303",
                                fontSize: "20"
                            }
                        }
                    }
                }
            },
            radarChart: {
                requestUrl: "tunnels/avg-energies",
                id: "tunnelEnergyRadarChart",
                parameters: {
                    option: {
                        title: {
                            text: "能耗(KWh/km)",
                            x: "right",
                            color: "#030303"
                        }
                    }
                }
            },
            tableColumn: [
                {
                    title: "管廊名称",
                    key: "tunnelName"
                },
                {
                    title: "耗电单价（元/千瓦时）",
                    key: "unitPrice"
                },
                {
                    title: "耗电量（千瓦时）",
                    key: "powerConsumption"
                },
                {
                    title: "耗电成本（元）",
                    key: "costing"
                }
            ],
            tableData: [],
            tableColumnAdd: {
                tunnelName: "统计",
                unitPrice: "",
                powerConsumption: 0,
                costing: 0
            },
            isCollapsed: false
        };
    },
    components: {
        LineChart,
        DataBox2,
        PieChart,
        RadarChart
    },
    mounted() {
        this.screenWidth = document.body.clientWidth;
        this.queryEnergies();
        this.initConsumption();
        this.initTime();
        this.changePeriod();
    },
    computed: {
        menuitemClasses: function() {
            return ["menu-item", this.isCollapsed ? "collapsed-menu" : ""];
        },
        lineChart: function() {
            return {
                id: "tunnelEnergyLineChart",
                requestUrl: "tunnels/energies/interval/" + this.period,
                parameters: {
                    titleName: "综合管廊耗电量",
                    titleColor: "#030303",
                    titleFontSize: 25
                }
            };
        }
    },
    methods: {
        //加载能耗数据
        initConsumption() {
            let _this = this;
            Promise.all([
                energyConsumptionService.getECHistoryTotal(),
                energyConsumptionService.getECYearTotal(),
                energyConsumptionService.getECMonthTotal()
            ]).then(result => {
                _this.historyData.value = result[0].val;
                _this.curYearData.value = result[1].val;
                _this.curMonthData.value = result[2].val;
            });
            // axios.all([
            //   _this.axios.get('/tunnels/energies/timetype/1'),
            //   _this.axios.get('/tunnels/energies/timetype/2'),
            //   _this.axios.get('/tunnels/energies/timetype/3'),
            // ]).then(axios.spread(function (history, curYear, curMonth) {
            //   // 上面两个请求都完成后，才执行这个回调方法
            //   _this.historyData.value = history.data.data.val;
            //   _this.curYearData.value = curYear.data.data.val;
            //   _this.curMonthData.value = curMonth.data.data.val;
            // }));
        },
        initTime() {
            let _this = this;
            EnumsService.getTimeCycle().then(
                result => {
                    _this.periodList = result.slice(2, 4);
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        //修改周期
        changePeriod() {
            this.$refs.childChart.fetchData(this.lineChart.requestUrl);
        },
        //导出表数据
        exportData() {
            this.$refs.table.exportCsv({
                filename: "Sorting and filtering data",
                original: false
            });
        },
        //查询表数据
        queryEnergies() {
            let _this = this;
            var queryParams = { startTime: null, endTime: null };
            if (_this.startTime && _this.endTime) {
                queryParams = {
                    startTime: _this.startTime.getTime(),
                    endTime: _this.endTime.getTime()
                };
            }
            energyConsumptionService.getECDetail(queryParams).then(
                result => {
                    _this.tableData = [];
                    _this.tableColumnAdd.powerConsumption = 0;
                    _this.tableColumnAdd.costing = 0;
                    result.filter(a => {
                        let tempData = {};
                        tempData.tunnelId = a.tunnelId;
                        tempData.tunnelName = a.tunnelName;
                        tempData.unitPrice = a.avgUnitPrice.toFixed(2);
                        tempData.costing = a.totalPrice.toFixed(2);
                        tempData.powerConsumption = a.totalValue.toFixed(2);
                        _this.tableColumnAdd.powerConsumption += Number(
                            tempData.powerConsumption,
                            2
                        );
                        _this.tableColumnAdd.costing += Number(
                            tempData.costing,
                            2
                        );
                        _this.tableData.push(tempData);
                    });
                    _this.tableColumnAdd.powerConsumption = Number(
                        _this.tableColumnAdd.powerConsumption
                    ).toFixed(2);
                    _this.tableColumnAdd.costing = Number(
                        _this.tableColumnAdd.costing
                    ).toFixed(2);
                    _this.tableData.push(_this.tableColumnAdd);
                },
                error => {
                    _this.$Message.error(error);
                }
            );
        },

        //跳转模块
        goToMoudle(path) {
            this.$router.push(path);
        }
    }
};
</script>


