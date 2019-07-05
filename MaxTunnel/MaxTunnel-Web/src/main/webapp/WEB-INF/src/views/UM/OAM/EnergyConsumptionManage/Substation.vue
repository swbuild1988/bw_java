<template>
    <div class="substationWrapper">
        <div class="tableWrapper">
            <div class="title">高压进线</div>
            <Table :columns="incomingLineColumns" :data="incomingLineData"></Table>
        </div>
        <div class="tableWrapper">
            <div class="title">低压出线</div>
            <Table :columns="outletColumns" :data="outletData"></Table>
        </div>
        <line-chart
            style="width:90vmin;height:30vmin;margin:0 auto;"
            v-bind="lineChart"
            v-if="curObj.id"
        ></line-chart>
        <Modal
            v-model="editModal.showFlag"
            title="编辑变电所信息"
            :mask-closable="false"
            @on-ok="save"
            @on-cancel="cancel"
        >
            <Form ref="substationForm" :model="editModal.form" :rules="ruleValidate">
                <FormItem label="回路名称：" prop="name">
                    <Input v-model="editModal.form.name" placeholder="请输入回路名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="电压等级：" prop="voltageLevel">
                    <Input
                        v-model="editModal.form.voltageLevel"
                        placeholder="请输入电压等级"
                        class="InputWidth"
                    />
                </FormItem>
                <FormItem label="倍率：" prop="magnification">
                    <Input
                        v-model="editModal.form.magnification"
                        placeholder="请输入倍率"
                        class="InputWidth"
                    />
                </FormItem>
            </Form>
        </Modal>
    </div>
</template>
<script>
import PowerSubstationService from "../../../../services/powerSubstationService";
import { TunnelService } from "../../../../services/tunnelService";
import LineChart from "../../../../components/Common/Chart/SimpleLineChart";
export default {
    data() {
        return {
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "请填写回路名称",
                        trigger: "blur"
                    }
                ],
                voltageLevel: [
                    {
                        required: true,
                        message: "请填写电压等级",
                        trigger: "blur"
                    }
                ],
                magnification: [
                    {
                        required: true,
                        message: "请填写倍率",
                        trigger: "blur"
                    }
                ]
            },
            incomingLineColumns: [
                {
                    title: "回路名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "电压等级",
                    key: "voltageLevel",
                    align: "center"
                },
                {
                    title: "电压",
                    key: "voltage",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.voltageId,
                                            "进线电压"
                                        );
                                    }
                                }
                            },
                            params.row.voltage.cv
                        );
                    }
                },
                {
                    title: "电流",
                    key: "current",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.currentId,
                                            "进线电流"
                                        );
                                    }
                                }
                            },
                            params.row.current.cv
                        );
                    }
                },
                {
                    title: "倍率",
                    key: "magnification",
                    align: "center"
                },
                {
                    title: "有功功率",
                    key: "power",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerId,
                                            "进线有功功率"
                                        );
                                    }
                                }
                            },
                            params.row.power.cv
                        );
                    }
                },
                {
                    title: "无功功率",
                    key: "unpower",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerId,
                                            "进线无功功率"
                                        );
                                    }
                                }
                            },
                            params.row.unpower.cv
                        );
                    }
                },
                {
                    title: "功率因数",
                    key: "powerFactor",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerFactorId,
                                            "进线功率因数"
                                        );
                                    }
                                }
                            },
                            params.row.powerFactor.cv
                        );
                    }
                },
                {
                    title: "有功日电量",
                    key: "powerDay",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerDayId,
                                            "进线有功日电量"
                                        );
                                    }
                                }
                            },
                            params.row.powerDay.cv
                        );
                    }
                },
                {
                    title: "无功日电量",
                    key: "unpowerDay",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerDayId,
                                            "进线无功日电量"
                                        );
                                    }
                                }
                            },
                            params.row.unpowerDay.cv
                        );
                    }
                },
                {
                    title: "有功电量示值",
                    key: "powerEle",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerEleId,
                                            "进线有功电量示值"
                                        );
                                    }
                                }
                            },
                            params.row.powerEle.cv
                        );
                    }
                },
                {
                    title: "无功电量示值",
                    key: "unpowerEle",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerEleId,
                                            "进线无功电量示值"
                                        );
                                    }
                                }
                            },
                            params.row.unpowerEle.cv
                        );
                    }
                },
                {
                    title: "操作",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "Button",
                            {
                                props: {
                                    type: "primary"
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id);
                                    }
                                }
                            },
                            "编辑"
                        );
                    }
                }
            ],
            incomingLineData: [],
            outletColumns: [
                {
                    title: "回路名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "电压等级",
                    key: "voltageLevel",
                    align: "center"
                },
                {
                    title: "电压",
                    key: "voltage",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.voltageId,
                                            "出线电压"
                                        );
                                    }
                                }
                            },
                            params.row.voltage.cv
                        );
                    }
                },
                {
                    title: "电流",
                    key: "current",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.currentId,
                                            "出线电流"
                                        );
                                    }
                                }
                            },
                            params.row.current.cv
                        );
                    }
                },
                {
                    title: "倍率",
                    key: "magnification",
                    align: "center"
                },
                {
                    title: "有功功率",
                    key: "power",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerId,
                                            "出线有功功率"
                                        );
                                    }
                                }
                            },
                            params.row.power.cv
                        );
                    }
                },
                {
                    title: "无功功率",
                    key: "unpower",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerId,
                                            "出线无功功率"
                                        );
                                    }
                                }
                            },
                            params.row.unpower.cv
                        );
                    }
                },
                {
                    title: "功率因数",
                    key: "powerFactor",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerFactorId,
                                            "出线功率因数"
                                        );
                                    }
                                }
                            },
                            params.row.powerFactor.cv
                        );
                    }
                },
                {
                    title: "有功日电量",
                    key: "powerDay",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerDayId,
                                            "出线有功日电量"
                                        );
                                    }
                                }
                            },
                            params.row.powerDay.cv
                        );
                    }
                },
                {
                    title: "无功日电量",
                    key: "unpowerDay",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerDayId,
                                            "出线无功日电量"
                                        );
                                    }
                                }
                            },
                            params.row.unpowerDay.cv
                        );
                    }
                },
                {
                    title: "有功电量示值",
                    key: "powerEle",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.powerEleId,
                                            "出线有功电量示值"
                                        );
                                    }
                                }
                            },
                            params.row.powerEle.cv
                        );
                    }
                },
                {
                    title: "无功电量示值",
                    key: "unpowerEle",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "div",
                            {
                                style: {
                                    cursor: "pointer"
                                },
                                on: {
                                    click: () => {
                                        this.showDetail(
                                            params.row.unpowerEleId,
                                            "出线无功电量示值"
                                        );
                                    }
                                }
                            },
                            params.row.unpowerEle.cv
                        );
                    }
                },
                {
                    title: "操作",
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "Button",
                            {
                                props: {
                                    type: "primary"
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id);
                                    }
                                }
                            },
                            "编辑"
                        );
                    }
                }
            ],
            outletData: [],
            editModal: {
                showFlag: false,
                form: {
                    id: null,
                    name: null,
                    voltageLevel: null,
                    magnification: null
                }
            },
            curObj: {
                id: null,
                name: null
            }
        };
    },
    mounted() {
        this.init();
    },
    computed: {
        lineChartParams() {
            let data = {
                id: this.curObj.id,
                startTime: new Date().getTime() - 1000 * 60 * 60 * 24 * 30,
                endTime: Date.now()
            };
            return data;
        },
        lineChart: function() {
            return {
                id: "substationLineChart",
                title: this.curObj.name,
                requestUrl: "data-analyse/measvalue/history/object",
                parameters: {
                    option: {
                        title: {
                            text: this.curObj.name,
                            textStyle: {
                                color: "#fff"
                            }
                        },
                        xAxis: {
                            axisLabel: {
                                color: "#fff",
                                rotate: "40"
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
                    },
                    params: this.lineChartParams
                }
            };
        }
    },
    components: { LineChart },
    methods: {
        init() {
            PowerSubstationService.getSubstationList({}).then(res => {
                this.incomingLineData = [];
                this.outletData = [];
                res.map(
                    item => {
                        if (item.type) {
                            this.incomingLineData.push(item);
                        } else {
                            this.outletData.push(item);
                        }
                    },
                    err => {
                        this.Log.info(err);
                    }
                );
            });
        },
        save() {
            this.$refs["substationForm"].validate(valid => {
                if (valid) {
                    PowerSubstationService.editSubstation(
                        this.editModal.form
                    ).then(
                        res => {
                            this.$Message.success("修改成功");
                            this.cancel();
                            this.init();
                        },
                        err => {
                            this.$Message.error(err);
                        }
                    );
                } else {
                    this.$Message.error("请正确填写客户信息");
                }
            });
        },
        cancel() {
            for (let item in this.editModal.form) {
                this.editModal.form[item] = null;
            }
            this.editModal.showFlag = false;
        },
        edit(id) {
            this.editModal.showFlag = true;
            PowerSubstationService.substationDetailById(id).then(
                res => {
                    this.editModal.form = {
                        id,
                        name: res.name,
                        voltageLevel: res.voltageLevel,
                        magnification: res.magnification + ""
                    };
                },
                err => {
                    this.Log.info(err);
                }
            );
        },
        showDetail(id, name) {
            console.log(id, name);
            this.curObj = {
                id,
                name
            };
        }
    }
};
</script>
<style scoped>
.substationWrapper {
    height: 100%;
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
    position: relative;
}
.tableWrapper {
    margin: 6vmin 4vmin;
}
.title {
    font-size: 2.4vmin;
    margin: 2vmin 0 4vmin 0;
    font-weight: bold;
    color: #fff;
    text-align: center;
}

.substationWrapper .ivu-table-wrapper {
    border: none;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table:before,
.substationWrapper .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: 1px solid #7d7d7d;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border-color: #3e4f61;
    border-radius: 1vmin;
}
.InputWidth {
    width: 70%;
}
.ivu-form >>> .ivu-form-item-label {
    width: 10vmin;
}
</style>


