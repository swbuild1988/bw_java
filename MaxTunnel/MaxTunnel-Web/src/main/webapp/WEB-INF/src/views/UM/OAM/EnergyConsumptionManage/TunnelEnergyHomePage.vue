<template>
    <div style="padding-left: 10px;padding-top: 10px;">
        <Row>
            <Col span="5">
                <data-box2 v-bind="historyData"></data-box2>
            </Col>

            <Col span="5" offset="1">
                <data-box2 v-bind="curYearData"></data-box2>
            </Col>

            <Col span="5" offset="1">
                <data-box2 v-bind="curMonthData"></data-box2>
            </Col>
        </Row>

        <Row style="margin-top: 10px;">
            <!-- 管廊能耗统计 饼图 -->
            <Col span="9">
                <div class="pie">
                    <pie-chart v-bind="pieChart"></pie-chart>
                </div>
            </Col>
            <Col span="14" offset="1" style="padding-right: 10px;">
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
                                        style="font-size: 1.66vmin;"
                                        @click="queryEnergies"
                                        size="small"
                                    >查询</Button>
                                    <Button
                                        type="primary"
                                        icon="ios-download-outline"
                                        style="font-size: 1.66vmin;"
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
                            border
                            :columns="tableColumn"
                            :data="tableData"
                            ref="table"
                            :height="tableHeight"
                        ></Table>
                        <div style="float:right;margin-top:4px;">
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
                <line-chart
                    style="width:50vw;height:calc(30vh - 20px);"
                    v-bind="lineChart"
                    ref="childChart"
                ></line-chart>
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
    height: calc(30vh - 20px);
    width: 100%;
}

.pie {
    margin-top: 10px;
    height: 37vh;
}

.table span {
    font-size: 1.66vmin;
}

.table >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
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
import EnergyIcon from "../../../../assets/UM/TunnelEnergy.png";
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
            startTime: "",
            endTime: "",
            period: 3,
            periodList: [],
            pieChart: {
                requestUrl: "tunnels/total-avg/1/consume-datas",
                id: "tunnelEnergyPieChart",
                parameters: {
                    option: {
                        backgroundColor: "#15252f1a",
                        title: {
                            text: "管廊能耗统计",
                            left: "left",
                            textStyle: {
                                fontWeight: "normal",
                                color: "#030303"
                            }
                        }
                    }
                }
            },
            radarChart: {
                requestUrl: "tunnels/total-avg/2/consume-datas",
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
        this.tableHeight = window.innerHeight * 0.34;
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
                            color: "#030303",
                            fontSize: 25
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
