<template>
    <div>
        <Row style="width: 80vw;margin: 1vmin auto;">
            <Col span="8">
                <div class="pieWrapper">
                    <value-pie-chart v-bind="valuePie"></value-pie-chart>
                </div>
            </Col>
            <Col span="15" offset="1">
                <div class="tableContent">
                    <div class="tableWrapper">
                        <Table :columns="allFaultColumns" :data="allFaultData" ref="allFaultTable"></Table>
                    </div>
                </div>
            </Col>
        </Row>
        <div class="barWrapper">
            <Button type="primary" class="export" @click="exportTable">导出</Button>
            <category-bar-chart v-bind="categoryBarChart"></category-bar-chart>
        </div>
    </div>
</template>
<script>
import CategoryBarChart from "../../../../components/Common/Chart/CategoryBarChart";
import ValuePieChart from "../../../../components/Common/Chart/ValuePieChart";
import { TunnelService } from "../../../../services/tunnelService";
import { MonitorDataService } from "../../../../services/monitorDataService";
export default {
    name: "fault-homepage",
    data() {
        return {
            categoryBarChart: {
                id: "faultsCategoryBar",
                title: "各管廊故障类型总计",
                series: [],
                xLable: []
            },
            valuePie: {
                id: "faultValuePie",
                title: "各管廊故障占比",
                series: []
            },
            allFaultData: [],
            allFaultColumns: [
                {
                    title: "名称",
                    key: "name",
                    align: "center",
                    render: (h, params) => {
                        if (params.row.id) {
                            return h(
                                "div",
                                {
                                    style: {
                                        fontSize: "1.4vmin"
                                    }
                                },
                                params.row.name
                            );
                        } else {
                            return h(
                                "div",
                                {
                                    style: {
                                        fontSize: "1.6vmin"
                                    }
                                },
                                params.row.name
                            );
                        }
                    }
                },
                {
                    title: "ID",
                    key: "id",
                    align: "center"
                },
                {
                    title: "管廊",
                    key: "tunnel",
                    align: "center"
                },
                {
                    title: "区域",
                    key: "area",
                    align: "center"
                },
                {
                    title: "管舱",
                    key: "store",
                    align: "center"
                }
            ]
        };
    },
    components: {
        CategoryBarChart,
        ValuePieChart
    },
    mounted() {
        this.getFaultData();
    },
    methods: {
        getFaultData() {
            Promise.all([
                TunnelService.getTunnels(),
                MonitorDataService.getAllFaultObjs()
            ]).then(res => {
                let tunnels = res[0],
                    faults = res[1],
                    barSeries = this.categoryBarChart.series,
                    pieSeries = this.valuePie.series;
                tunnels.map(tunnel => {
                    barSeries.push({
                        name: tunnel.name,
                        data: []
                    });
                    pieSeries.push({
                        name: tunnel.name,
                        value: 0
                    });
                });
                faults.forEach((type, index) => {
                    this.categoryBarChart.xLable.push(type.objType);
                    barSeries.map(tunnel => {
                        tunnel.data[index] = 0;
                    });
                    type.faults.map(fault => {
                        barSeries.find(bar => {
                            return bar.name == fault.tunnel;
                        }).data[index]++;
                        pieSeries.find(pie => {
                            return pie.name == fault.tunnel;
                        }).value++;
                    });
                });

                this.allFaultData = this.getAllFaultData(faults);
            });
        },
        getAllFaultData(originArr) {
            return originArr.reduce((prevArr, curItem) => {
                let arr = [];
                arr.push({
                    name:
                        curItem.objType + "损坏" + curItem.faults.length + "个",
                    id: null,
                    tunnel: null,
                    area: null,
                    store: null
                });
                curItem.faults.forEach(fault => {
                    arr.push(fault);
                });
                return prevArr.concat(arr);
            }, []);
        },
        exportTable() {
            this.$refs.allFaultTable.exportCsv({
                filename:
                    "故障总计" + new Date(Date.now()).format("yyyy-MM-dd"),
                original: false
            });
        }
    }
};
</script>
<style scoped>
.barWrapper {
    height: 50vh;
    width: 80vw;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
    padding: 1vmin;
    margin: 2vmin auto;
    position: relative;
}
.pieWrapper,
.tableContent {
    height: 30vh;
    background: url("../../../../assets/UM/energyBorder2.png") no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
}
.tableWrapper {
    padding: 1vmin;
    height: 28vh;
    overflow-y: auto;
    margin: 1vmin;
}
.tableWrapper::-webkit-scrollbar {
    width: 1vmin;
    height: 0.2vmin;
}
.tableWrapper::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.tableWrapper::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.tableWrapper >>> .ivu-table {
    font-size: 1.4vmin;
}
.tableWrapper .ivu-table-wrapper {
    border: none;
}
.tableWrapper .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.tableWrapper .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: 1px solid #7d7d7d;
}
.tableWrapper .ivu-table-wrapper >>> .ivu-table:before,
.tableWrapper .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.tableWrapper .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
.export {
    position: absolute;
    top: 2vmin;
    right: 2vmin;
    background: -webkit-linear-gradient(left, #66cd64, #1ece16);
    background: -o-linear-gradient(right, #66cd64, #1ece16);
    background: -moz-linear-gradient(right, #66cd64, #1ece16);
    background: linear-gradient(to right, #66cd64, #66cd64);
    z-index: 10;
}
</style>

