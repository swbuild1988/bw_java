<template>
    <div style="padding-left: 8vmin;padding-top: 1vmin;padding-right: 2vmin">
        <Row>
            <Col span="6">
                <data-box2 v-bind="historyData"></data-box2>
            </Col>
            <Col span="6" offset="2">
                <data-box2 v-bind="curYearData"></data-box2>
            </Col>
            <Col span="6" offset="2">
                <data-box2 v-bind="curMonthData"></data-box2>
            </Col>
        </Row>

        <Row style="margin: 2vmin 0;">
            <!-- 管廊能耗统计 饼图 -->
            <Col span="9">
                <div class="pie">
                    <pie-chart v-bind="pieChart" style="width:58vmin;height: 34vh"></pie-chart>
                </div>
            </Col>
            <Col span="14" offset="1">
                <Row class="table">
                    <Col span="24">
                        <Row style="margin-bottom: 10px;">
                            <Col span="9" offset="1">
                                <span class="defaultFont_size">起始时间：</span>
                                <DatePicker
                                    v-model="startTime"
                                    type="date"
                                    placeholder="选择时间"
                                    style="width:61%"
                                ></DatePicker>
                            </Col>
                            <Col span="9">
                                <span style="font-size: 1.66vmin;">结束时间：</span>
                                <DatePicker
                                    v-model="endTime"
                                    type="date"
                                    placeholder="选择时间"
                                    style="width: 61%"
                                ></DatePicker>
                            </Col>
                            <Col span="5">
                                <div style="position: relative;float: right;right: 0px;">
                                    <Button
                                        type="primary"
                                        icon="ios-search"
                                        class="search"
                                        @click="queryEnergies"
                                        size="small"
                                    >查询</Button>
                                    <Button
                                        type="primary"
                                        icon="ios-download-outline"
                                        class="export"
                                        @click="exportData"
                                        size="small"
                                    >导出</Button>
                                </div>
                            </Col>
                        </Row>
                    </Col>
                    <Col span="23" offset="1">
                        <Table
                            size="small"
                            stripe
                            :columns="tableColumn"
                            :data="tableData"
                            ref="table"
                            :height="tableHeight"
                        ></Table>
                    </Col>
                </Row>
            </Col>
        </Row>
        <Row>
            <Col span="9">
                <!-- 雷达图 -->
                <div class="radar">
                    <radar-chart v-bind="radarChart"></radar-chart>
                </div>
            </Col>
            <Col span="14" offset="1">
                <div class="lineChartWrapper">
                    <div style="position: absolute;top: 1vmin;right: 2vmin;">
                        <span style="font-size: 1.22vmin;">周期：</span>
                        <Select
                            v-model="period"
                            @on-change="changePeriod(period)"
                            style=" width:4vw"
                        >
                            <Option
                                v-for="item in periodList"
                                :value="item.val"
                                :key="item.val"
                            >{{ item.key }}</Option>
                        </Select>
                    </div>
                    <line-chart
                        style="width:90vmin;height:100%"
                        v-bind="lineChart"
                        ref="childChart"
                    ></line-chart>
                </div>
            </Col>
        </Row>
    </div>
</template>
<style scoped>
.defaultFont_size {
    font-size: 1.66vmin;
}

h1 {
    margin: 2% 1% 0 0;
    width: 8%;
    float: left;
}

.radar {
    height: 34vh;
    width: 100%;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
}

.pie {
    height: 34vh;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
}
.table {
    background: url("../../../../assets/UM/energyBorder2.png") no-repeat;
    background-size: 100% 100%;
}
.table .ivu-table-wrapper {
    border: none;
}
.table .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.table .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: none;
}
.table .ivu-table-wrapper >>> .ivu-table:before,
.table .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.table .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}

.table span {
    font-size: 1.66vmin;
    color: #fff;
}

.table >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
    background: transparent;
    color: #fff;
    border-radius: 1vmin;
}
.table >>> .ivu-input-icon {
    color: #fff;
}
.table >>> .ivu-input::-webkit-input-placeholder {
    color: #fff;
}

.table >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.table >>> .ivu-picker-panel-icon-btn {
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.table >>> .ivu-date-picker-header-label {
    font-size: 1.66vmin;
}
.search {
    background: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #33525a;
    border-radius: 1vmin;
    font-size: 1.4vmin;
}
.export {
    background: -webkit-linear-gradient(left, #66cd64, #1ece16);
    background: -o-linear-gradient(right, #66cd64, #1ece16);
    background: -moz-linear-gradient(right, #66cd64, #1ece16);
    background: linear-gradient(to right, #66cd64, #66cd64);
    border-color: #33525a;
    border-radius: 1vmin;
    font-size: 1.4vmin;
}
.lineChartWrapper {
    width: 100%;
    height: 34vh;
    color: #fff;
    background: url("../../../../assets/UM/energyBorder2.png") no-repeat;
    background-size: 100% 100%;
}
.lineChartWrapper >>> .ivu-select-selection {
    background: transparent;
    border-radius: 1vmin;
    color: #fff;
}

@media (min-width: 1921px) {
    .table >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .table >>> .ivu-date-picker-cells-cell {
        width: 2vmin;
    }

    .table >>> .ivu-date-picker-cells-header span {
        padding-right: 2.5rem;
    }
}
</style>
<script>
import DataBox2 from "../../../../components/Common/Box/DataBox2";
import LineChart from "../../../../components/Common/Chart/LineChart";
import PieChart from "../../../../components/Common/Chart/SimplePieChart";
import RadarChart from "../../../../components/Common/Chart/SimpleRadarChart";
import EnergyIcon from "../../../../assets/UM/energy.png";
import { EnumsService } from "../../../../services/enumsService";
import { EnergyConsumptionService } from "../../../../services/EnergyConsumptionService";

export default {
    name: "tunnel-energy-consumption",
    data() {
        return {
            // 上面databox的参数
            tableHeight: 250,
            historyData: {
                value: 0,
                unit: "千瓦时",
                label: "历史总能耗",
                backGroundColor1: "#7369e2",
                backGroundColor2: "#230fe3",
                imgSrc: EnergyIcon
            },
            curYearData: {
                value: 0,
                unit: "千瓦时",
                label: "本年度能耗",
                backGroundColor1: "#dbe369",
                backGroundColor2: "#c2ce12",
                imgSrc: EnergyIcon
            },
            curMonthData: {
                value: 0,
                unit: "千瓦时",
                label: "本月度能耗",
                backGroundColor1: "#66cd64",
                backGroundColor2: "#1ece16",
                imgSrc: EnergyIcon
            },
            startTime: "",
            endTime: "",
            period: 3,
            periodList: [],
            pieChart: {
                requestUrl: "tunnels/total-avg/1/consume-datas",
                id: "tunnelEnergyPieChart",
                titleSize: "6%",
                legendColor: "#fff",
                parameters: {
                    option: {
                        backgroundColor: "#15252f1a",
                        title: {
                            text: "管廊能耗统计",
                            left: "left",
                            textStyle: {
                                fontWeight: "normal",
                                color: "#fff"
                            },
                            top: "6%"
                        }
                    }
                },
                seriesColor: [
                    "#e5c52f",
                    "#6bade1",
                    "#6fe46c",
                    "#e06ce4",
                    "#e48e6c",
                    "#c23531"
                ]
            },
            radarChart: {
                requestUrl: "tunnels/total-avg/2/consume-datas",
                id: "tunnelEnergyRadarChart",
                parameters: {
                    option: {}
                }
            },
            tableColumn: [
                {
                    title: "管廊名称",
                    key: "tunnelName",
                    align: "center"
                },
                {
                    title: "耗电量（千瓦时）",
                    key: "powerConsumption",
                    align: "center"
                }
            ],
            tableData: [],
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
        this.tableHeight = window.innerHeight * 0.3;
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
                requestUrl: "tunnels/consumes/interval/" + this.period,
                parameters: {
                    option: {
                        title: {
                            text: "综合管廊耗电量",
                            textStyle: {
                                color: "#fff"
                            }
                        },
                        color: [
                            "#e5c52f",
                            "#6bade1",
                            "#6fe46c",
                            "#e06ce4",
                            "#e48e6c",
                            "#c23531"
                        ],
                        xAxis: {
                            axisLabel: {
                                color: "#fff"
                            }
                        },
                        yAxis: {
                            axisLabel: {
                                color: "#fff"
                            }
                        },
                        legend: {
                            textStyle: {
                                color: ["#fff"]
                            }
                        }
                    }
                }
            };
        }
    },
    methods: {
        //加载能耗数据
        initConsumption() {
            let _this = this;
            Promise.all([
                EnergyConsumptionService.getECHistoryTotal(),
                EnergyConsumptionService.getECYearTotal(),
                EnergyConsumptionService.getECMonthTotal()
            ]).then(result => {
                _this.historyData.value = result[0].val;
                _this.curYearData.value = result[1].val;
                _this.curMonthData.value = result[2].val;
            });
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
            if (new Date(_this.startTime) > new Date(_this.endTime)) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            var queryParams = {
                startTime: null,
                endTime: null
            };
            if (_this.startTime && _this.endTime) {
                queryParams = {
                    startTime: _this.startTime.getTime(),
                    endTime: _this.endTime.getTime()
                };
            }
            EnergyConsumptionService.getECDetail(queryParams).then(
                result => {
                    _this.tableData = [];
                    result.filter(item => {
                        _this.tableData.push({
                            tunnelName: item.key.name,
                            powerConsumption: item.val
                        });
                    });
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
