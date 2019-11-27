<template>
    <div class="formBG">
        <div class="formTitle" v-show="this.pageType==4">提交任务执行结果</div>
        <div class="formTitle" v-show="this.pageType==1">巡检任务详情</div>
        <div class="formHeight">
            <Form :model="task" :label-width="140">
                <FormItem label="所属计划：">
                    <Input type="text" v-model="task.planName" readonly></Input>
                </FormItem>
                <FormItem label="巡检人：">
                    <Input type="text" v-model="task.inspectManName" readonly></Input>
                </FormItem>
                <FormItem label="巡检进度：">
                    <Input type="text" v-model="task.processStatus" readonly></Input>
                </FormItem>
                <FormItem label="是否完成：">
                    <Input type="text" :value="task.isFinished?'完成':'未完成'" readonly></Input>
                </FormItem>
                <FormItem label="任务开始时间：">
                    <DatePicker type="datetime" v-model="task.startTime" placeholder="请输入巡检任务开始时间" style="width: 100%"
                        readonly></DatePicker>
                </FormItem>
                <FormItem label="任务结束时间：">
                    <DatePicker type="datetime" v-model="task.endTime" placeholder="请输入巡检任务结束时间" style="width: 100%"
                        readonly></DatePicker>
                </FormItem>
                <FormItem label="巡检记录：">
                    <Table :columns="columnsTask" :data="this.task.records"></Table>
                </FormItem>
                <FormItem label="巡检描述：">
                    <Input v-model="task.describe" type="textarea" :rows="4" placeholder="请输入巡检描述" readonly></Input>
                </FormItem>
                <div style="text-align: center" v-show="this.pageType==4">
                    <Button type="ghost" style="margin-left: 8px" @click="goBack()">返回</Button>
                    <Button type="primary">提交</Button>
                </div>
                <div style="margin-left: 140px;text-align: center" v-show="this.pageType==1">
                    <Button type="primary" @click="checkReport()">查看报表</Button>
                    <Button type="ghost" @click="goBack()">返回</Button>
                </div>
            </Form>
        </div>
        <Modal v-model="modalShow" title="报表查看下载" width="1000">
            <div class="showReport">
                <PDF ref="pdf"></PDF>
            </div>
            <div slot="footer">
                <Button type="error" long @click="downloadCycle()">下载</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
    import types from "../../../../../static/Enum.json";
    import showDefect from "../../../../components/UM/OMM/show-defect.vue";
    import PDF from "../../../../components/UM/MAM/pdfPerviewDownload.vue";
    import {
        TunnelService
    } from "../../../../services/tunnelService";
    import {
        PatrolService
    } from "../../../../services/patrolService";
    import {
        EnumsService
    } from "../../../../services/enumsService";
    export default {
        components: {
            showDefect,
            PDF
        },
        data() {
            return {
                // 页面类型 1：查看 2：编辑 3：新增
                pageType: 1,
                pageTypes: types.pageType,
                progress: 1,
                task: {
                    createTime: null,
                    id: null,
                    inspectManId: null,
                    inspectManName: null,
                    inspectTime: null,
                    isFinished: false,
                    planId: null,
                    planName: null,
                    processInstanceId: null,
                    processStatus: null,
                    processType: null,
                    processTypeName: null,
                    record: [],
                    taskTime: null,
                    startTime: null,
                    endTime: null
                },
                addRecords: [{
                    index: 1,
                    place: "",
                    recordTime: null,
                    content: "",
                    hasDefect: false,
                    status: 1,
                    taskId: 1,
                    defect: []
                }],
                tunnel: [],
                type: [],
                level: [],
                patrolResult: [{
                        id: 1,
                        val: 1,
                        key: "已完成"
                    },
                    {
                        id: 2,
                        val: 2,
                        key: "未完成"
                    },
                    {
                        id: 3,
                        val: 3,
                        key: "进行中"
                    }
                ],
                showAddDefect: false,
                modalShow: false,
                columnsTask: [{
                        type: "expand",
                        width: window.innerWidth * 0.01,
                        align: "center",
                        render: (h, params) => {
                            return h(showDefect, {
                                props: {
                                    row: params.row
                                }
                            });
                        }
                    },
                    {
                        title: "所属仓段",
                        align: "center",
                        render: (h, params) => {
                            return h("span", params.row.area.name);
                        }
                    },
                    {
                        title: "所属区段",
                        align: "center",
                        render: (h, params) => {
                            return h("span", params.row.store.name);
                        }
                    },
                    {
                        title: "巡检时间",
                        key: "recordTime",
                        align: "center",
                        width: window.innerWidth * 0.1,
                        render: (h, params) => {
                            if (params.row.recordTime != null) {
                                return h(
                                    "span",
                                    new Date(params.row.recordTime).format(
                                        "yyyy-MM-dd hh:mm:s"
                                    )
                                );
                            }
                        }
                    },
                    {
                        title: "巡检描述",
                        align: "center",
                        key: "content"
                    }
                ],
                areas: [],
                stores: []
            };
        },
        mounted() {
            this.task.id = this.$route.params.id ?
                this.$route.params.id :
                sessionStorage["detailId"];
            let _this = this;
            TunnelService.getTunnels().then(
                result => {
                    _this.tunnel = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );

            // 获取任务
            PatrolService.getTDetailByPlanId(this.task.id).then(
                result => {
                    _this.task = result;
                    if (result.startTime != null && result.endTime != null) {
                        _this.task.startTime = new Date(result.startTime).format(
                            "yyyy-MM-dd hh:mm:s"
                        );
                        _this.task.endTime = new Date(result.endTime).format(
                            "yyyy-MM-dd hh:mm:s"
                        );
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );

            //危险等级
            EnumsService.getDefectLevel().then(
                result => {
                    _this.level = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );

            //缺陷类型
            EnumsService.getDefectType().then(
                result => {
                    _this.type = result;
                },
                error => {
                    _this.Log.info = error;
                }
            );
        },
        methods: {
            //返回
            goBack() {
                this.$router.back(-1);
            },
            checkReport() {
                let _this = this;
                _this.modalShow = true
                PatrolService.createReport(this.task.id).then(
                    res => {
                        _this.$refs.pdf.previewPDF(_this.ApiUrl + "/inspection-tasks/" + _this.task.id +
                            "/report-view");
                        // this.$refs.pdf.previewPDF("http://localhost:8081/MaxTunnel-Web/inspection-tasks/" + _this.task.id +
                        //     "/report-view");
                    },
                    err => {

                    }
                )
            },
            downloadCycle() {
                let _this = this;
                this.$refs.pdf.downloadPDF(
                    _this.ApiUrl + "/inspection-tasks/" + _this.task.id + "/report-download",
                    "巡检任务" + _this.task.id + "报告"
                );
                // this.$refs.pdf.downloadPDF(
                //     "http://localhost:8081/MaxTunnel-Web/inspection-tasks/" + _this.task.id + "/report-download",
                //     "巡检任务" + _this.task.id + "报告"
                // );
                this.modalShow = false
            }
        }
    };
</script>
<style scoped>
    .ivu-form.ivu-form-label-right {
        width: 780px;
        margin: 10px auto;
        padding: 10px 20px;
    }

    .formTitle {
        color: #fff;
        width: auto;
        margin-top: -3.2vh;
        font-size: 2.2vmin;
    }

    .adddefect,
    .addRecords {
        width: 100%;
        border: 1px solid #ccc;
        border-collapse: collapse;
    }

    .addRecords tr,
    .addRecords th {
        border: 1px solid #ccc;
        border-radius: 4px;
        padding: 4px 7px;
    }

    .addRecords td {
        padding: 4px;
        text-align: center;
    }

    .addRecords th {
        padding: 9px 7px;
    }

    .addRecords {
        margin: 5px;
    }

    .formBG {
        background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
        background-size: 100% 100%;
        padding-top: 3vmin;
        padding-bottom: 3vmin;
    }

    .formBG>>>.ivu-form-item-label {
        color: #fff;
    }

    .formBG>>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before,
    .formBG .ivu-form>>>.ivu-form-item-label:before {
        color: #00fff6;
        content: "★";
        display: inline-block;
        margin-right: 0.4vmin;
        line-height: 1;
        font-family: SimSun;
        font-size: 1.2vmin;
    }

    .showReport {
        margin: 2% 2%;
        width: 96%;
        height: 80vh;
        color: #fff;
    }

    @media (min-width: 2200px) {
        .ivu-form.ivu-form-label-right {
            width: 50%;
        }

        .ivu-form-item>>>.ivu-form-item-label {
            width: 15vmin !important;
            line-height: 4.5vmin;
        }

        .ivu-form-item>>>.ivu-form-item-content {
            margin-left: 15vmin !important;
            line-height: 5.5vmin;
        }

        .ivu-select,
        .ivu-select>>>.ivu-select-selection,
        .ivu-input-wrapper>>>.ivu-input,
        .ivu-date-picker>>>.ivu-input,
        .ivu-select.ivu-select-single>>>.ivu-select-selected-value,
        .ivu-select.ivu-select-single>>>.ivu-select-placeholder {
            height: 4vmin;
            line-height: 4vmin;
            font-size: 1.4vmin;
        }

        .ivu-table-wrapper>>>.ivu-table-cell-with-expand {
            height: 4.7vmin;
            line-height: 4.7vmin;
        }

        .ivu-table-wrapper>>>.ivu-table-cell-with-expand .ivu-table-cell-expand i {
            font-size: 1.4vmin;
        }

    }
</style>