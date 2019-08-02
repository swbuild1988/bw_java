<template>
    <div>
        <Tabs type="card" class="tab">
            <TabPane label="卡片" name="card">
                <Row style="margin-top: 0.8vmin;" v-if="isOverview">
                    <Col span="4" v-for="type in overviewList" :key="type.id">
                        <article class="overviewWrapper" @click="checkDetails(type.id)">
                            <div style="font-size: 2.4vmin;margin-bottom: 1vmin;">
                                <img
                                    :src="type.icon"
                                    alt
                                    style="vertical-align: middle;width:2.8vmin;height:2.8vmin;"
                                >
                                <span style="vertical-align: middle;">{{type.objType}}</span>
                            </div>
                            <div>
                                <img :src="type.count ? faultIcon : intactIcon" alt class="icons">
                                <span
                                    :class="{'warning': type.count }"
                                    style="vertical-align: middle;"
                                >
                                    损坏
                                    <span class="totalNumber">{{type.count}}</span>
                                    个
                                </span>
                            </div>
                            <div>
                                <img src="../../../../assets/UM/totalIcon.png" alt class="icons">
                                <span style="vertical-align: middle;">{{ "总共" + type.total + "个"}}</span>
                            </div>
                        </article>
                    </Col>
                </Row>
                <div style="position: relative;margin: 2vmin;overflow:hidden;" v-else>
                    <Button type="primary" class="back" @click="back">返回</Button>
                    <Button type="primary" class="confirmFault" @click="confirmFault">故障确认</Button>
                    <div v-if="!detailList.length" class="noData">暂无数据</div>
                    <Row style="margin-top:4vmin" :gutter="16" v-else>
                        <CheckboxGroup v-model="choosedObjData">
                            <Col
                                v-for="item in detailList"
                                :span="item.objtypeId == 30 ? '6' : '4'"
                                :key="item.id"
                            >
                                <Checkbox
                                    class="checkStyle"
                                    :label="item.id"
                                    :disabled="item.isBroken"
                                >
                                    <span></span>
                                </Checkbox>
                                <show-obj-data v-bind:Obj="item"></show-obj-data>
                            </Col>
                        </CheckboxGroup>
                    </Row>
                </div>
            </TabPane>
            <TabPane label="表格" name="table">
                <Button type="primary" class="export" @click="exportTable">导出</Button>
                <div class="tableWrapper">
                    <Table :columns="faultColumns" :data="tableData" ref="faultTable"></Table>
                </div>
            </TabPane>
        </Tabs>
    </div>
</template>
<script>
import { MonitorDataService } from "../../../../services/monitorDataService";
import { TunnelService } from "../../../../services/tunnelService";
import ShowObjData from "../../../../components/UM/MAM/ShowObjData/ShowObjData";
import { EquipmentService } from "../../../../services/equipmentService";
import { DefectService } from "../../../../services/defectService";
import faultIcon from "../../../../assets/UM/faultIcon.png";
import intactIcon from "../../../../assets/UM/intactIcon.png";
export default {
    name: "list-fault-statistics",
    data() {
        return {
            faultIcon: faultIcon,
            intactIcon: intactIcon,
            originList: [],
            isOverview: true,
            detailList: [],
            curTunnelName: "",
            faultColumns: [
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
                                        fontSize: "1.66vmin"
                                    }
                                },
                                params.row.name
                            );
                        } else {
                            return h(
                                "div",
                                {
                                    style: {
                                        fontSize: "2vmin",
                                        textAlign: "left"
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
            ],
            choosedObjData: []
        };
    },
    mounted() {
        this.init();
    },
    computed: {
        overviewList() {
            return this.originList.reduce((arr, curItem) => {
                return arr.concat({
                    objType: curItem.objType,
                    id: curItem.objTypeId,
                    total: curItem.total,
                    count: curItem.faults.length,
                    icon: require("../../../../assets/UM/obj" +
                        curItem.objTypeId +
                        ".png")
                });
            }, []);
        },
        tableData() {
            return this.originList.reduce((prevArr, curItem) => {
                let arr = [];
                arr.push({
                    name:
                        curItem.objType +
                        "损坏" +
                        curItem.faults.length +
                        "个 , 共" +
                        curItem.total +
                        "个",
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
        }
    },
    watch: {
        $route: function() {
            this.isOverview = true;
            this.init();
        }
    },
    methods: {
        init() {
            MonitorDataService.getObjFaultList(this.$route.params.id).then(
                res => {
                    this.originList = res;
                },
                err => this.Log.info(err)
            );
            TunnelService.getTunnels().then(
                result => {
                    result.forEach(a => {
                        if (a.id == this.$route.params.id) {
                            this.curTunnelName = a.name;
                        }
                    });
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        checkDetails(id) {
            this.isOverview = false;
            this.detailList = [];
            this.originList.forEach(obj => {
                if (obj.objTypeId == id) {
                    obj.faults.map(a => {
                        let temp = a;
                        temp.ObjName = a.name;
                        temp.ObjVal = a.curValue;
                        temp.clickStatus = false;
                        temp.objtypeId = id;
                        temp.time =
                            a.time == undefined || a.time == ""
                                ? ""
                                : new Date(a.time).format(
                                      "yyyy-MM-dd hh:mm:ss"
                                  );
                        temp.objtypeName = a.tunnel + a.area + a.store;
                        this.detailList.push(temp);
                        if (temp.isBroken) {
                            this.choosedObjData.push(temp.id);
                        }
                    });
                }
            });
        },
        back() {
            this.isOverview = true;
        },
        exportTable() {
            this.$refs.faultTable.exportCsv({
                filename:
                    this.curTunnelName +
                    "故障统计" +
                    new Date(Date.now()).format("yyyy-MM-dd"),
                original: false
            });
        },
        confirmFault() {
            this.$Modal.confirm({
                title: "故障确认",
                content: "<p>确定所选故障已全部确认？</p>",
                onOk: () => {
                    if (this.choosedObjData.length == 0) {
                        this.$Message.error("请选择已经确认的故障");
                    } else {
                        let arr = [];
                        this.choosedObjData.forEach(item => {
                            let params = {
                                objId: item,
                                status: 2
                            };
                            // 修改管廊设备的运行状态
                            EquipmentService.updateEquipmentStatus(params).then(
                                result => {
                                    //
                                },
                                error => {
                                    this.Log.info(error);
                                }
                            );
                            // 生成维修工单
                            this.tableData.forEach(ele => {
                                if (ele.id == item) {
                                    arr.push(ele);
                                }
                            });
                        });
                        arr.forEach(item => {
                            let defectDetails = {
                                tunnelId: item.tunnelId,
                                createTime: new Date(item.time),
                                type: 2,
                                typeName: "设备缺陷",
                                objectId: item.id,
                                objName: item.name,
                                level: 1,
                                levelName: "隐患",
                                status: 1,
                                statusName: "未处理",
                                description: null,
                                area: {
                                    id: item.areaId,
                                    name: item.area
                                },
                                store: {
                                    id: item.storeId,
                                    name: item.store
                                },
                                name: item.name + "：" + item.id + "故障"
                            };
                            DefectService.addDefect(defectDetails).then(
                                result => {},
                                error => {
                                    this.Log.info(error);
                                }
                            );
                        });
                        this.$Message.info(
                            "故障已确认，并已生成相应的维修工单。"
                        );
                    }
                },
                onCancel: () => {
                    this.$Message.info("已取消");
                }
            });
        }
    },
    components: {
        ShowObjData
    }
};
</script>
<style scoped>
.tab {
    border-bottom: 1px solid #dddee1;
}
.tab >>> .ivu-tabs-bar {
    height: 3.2vmin;
}
.tab >>> .ivu-tabs-bar .ivu-tabs-tab {
    height: 3.2vmin;
    background: transparent;
    color: #fff;
}
.tab >>> .ivu-tabs-bar .ivu-tabs-tab-active {
    color: #05b1a7;
}
.ivu-tabs-card >>> .ivu-tabs-bar {
    margin-bottom: 0px !important;
}
.tab >>> .ivu-tabs-nav-container {
    height: 3.2vmin !important;
}
.overviewWrapper {
    width: 80%;
    color: #fff;
    font-size: 1.8vmin;
    text-align: center;
    margin: 1vmin auto;
    height: 17vmin;
    padding-top: 2.6vmin;
    background: url("../../../../assets/UM/cardBorder.png") no-repeat;
    background-size: 100% 100%;
    cursor: pointer;
}
.totalNumber {
    font-size: 2.8vmin;
}
.warning {
    color: #df0505;
}
.back {
    position: absolute;
    top: 0;
    right: 2vmin;
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
}
.back,
.export,
.confirmFault {
    border-color: #33525a;
    border-radius: 1vmin;
    font-size: 1.4vmin;
}
.confirmFault {
    position: absolute;
    top: 0;
    right: 9vmin;
    background-color: -webkit-linear-gradient(left, #6952dd, #2d0dd3);
    background: -o-linear-gradient(right, #6952dd, #2d0dd3);
    background: -moz-linear-gradient(right, #6952dd, #2d0dd3);
    background: linear-gradient(to right, #6952dd, #2d0dd3);
}
.noData {
    height: 60vmin;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 3vmin;
    color: #fff;
}
.tableWrapper {
    margin: 2vmin;
    height: 74vmin;
    padding: 2vmin;
    overflow-x: auto;
}
.tableWrapper >>> .ivu-table {
    font-size: 1.66vmin;
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
.export {
    margin-top: -3.4vmin;
    margin-right: 1vmin;
    float: right;
    background: -webkit-linear-gradient(left, #66cd64, #1ece16);
    background: -o-linear-gradient(right, #66cd64, #1ece16);
    background: -moz-linear-gradient(right, #66cd64, #1ece16);
    background: linear-gradient(to right, #66cd64, #66cd64);
    font-size: 1vmin;
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
.checkStyle {
    position: absolute;
    right: 1vmin;
    top: 2.4vmin;
}
.icons {
    vertical-align: middle;
    margin-right: 0.4vmin;
    width: 2.2vmin;
    height: 2.2vmin;
}
.checkStyle
    >>> .ivu-checkbox-disabled.ivu-checkbox-checked
    .ivu-checkbox-inner {
    border-color: #2d8cf0;
    background-color: #2d8cf0;
}
</style>

