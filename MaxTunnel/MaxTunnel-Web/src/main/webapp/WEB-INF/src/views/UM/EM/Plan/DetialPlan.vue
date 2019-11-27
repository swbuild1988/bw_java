<template>
    <div>
        <Tabs type="card" @on-click="changePane" class="tab">
            <TabPane label="预案详情" name="detial">
                <div>
                    <WorkModal v-if="showModal.val!=null" v-bind="showModal"></WorkModal>
                    <hr class="hr3">
                    <div style="width: 100%;height: 100%;position:relative;">
                        <Row>
                            <Col span="12">
                                <div class="nodesPic" v-if="nodeImgSrc">
                                    <image-from-url :url="nodeImgSrc"></image-from-url>
                                </div>
                            </Col>
                            <Col span="12" :offset="nodeImgSrc ? '0' : '12'">
                                <div>
                                    <img style="margin-top: 4vmin;height: 70vh;" v-bind:src="bgImg">
                                </div>
                            </Col>
                        </Row>
                    </div>
                </div>
            </TabPane>
            <TabPane label="执行记录" name="log">
                <Row class="top">
                    <Col span="6">
                        <span>开始时间:</span>
                        <DatePicker
                            style="width: 65%;"
                            v-model="queryPram.startTime"
                            format="yyyy年MM月dd日-hh:mm:ss"
                            type="datetime"
                            placeholder="选择时间"
                        ></DatePicker>
                    </Col>
                    <Col span="6">
                        <span>结束时间</span>
                        <DatePicker
                            style="width: 65%;"
                            v-model="queryPram.endTime"
                            format="yyyy年MM月dd日-hh:mm:ss"
                            type="datetime"
                            placeholder="选择时间"
                        ></DatePicker>
                    </Col>
                    <Col span="2" offset="10">
                        <div style="position: relative;float: right;right: 0px;">
                            <Button
                                type="primary"
                                icon="ios-search"
                                @click="queryTable"
                                class="search"
                            >查询</Button>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col span="24" style="margin-top: 10px;">
                        <hr class="hr1">
                    </Col>
                </Row>
                <Row>
                    <Col span="24">
                        <Table
                            :height="tableHeight"
                            stripe
                            :columns="tableColumns"
                            :data="tableData"
                            style="margin: 1vh;"
                        ></Table>
                        <Modal
                            width="900px;"
                            title="详细信息:"
                            v-model="showTableDetial"
                            :mask-closable="false"
                        >
                            <Table
                                stripe
                                :columns="detialTableColumns"
                                style="height: 540px;"
                                :data="detialTableData"
                            ></Table>
                        </Modal>
                    </Col>
                </Row>
                <div>
                    <Page
                        style="position: relative;float: right;right: 10px;color:#fff"
                        @on-page-size-change="handlePageSize"
                        :total="pageTotal"
                        :page-size="queryPram.pageSize"
                        :current="queryPram.pageNum"
                        show-total
                        show-elevator
                        show-sizer
                        placement="top"
                        @on-change="changePage"
                    ></Page>
                </div>
                <Modal v-model="planDetail.show" title="预案流程" :mask-closable="false">
                    <div class="nodesPic" v-if="planDetail.url">
                        <image-from-url :url="planDetail.url"></image-from-url>
                    </div>
                    <div slot="footer"></div>
                </Modal>
            </TabPane>
        </Tabs>
        <ShowStartPlan v-bind="showModal"></ShowStartPlan>
    </div>
</template>

<script>
import waterPlan from "../../../../assets/UM/waterPlan.png";
import windPlan from "../../../../assets/UM/wind.png";
import ShowStartPlan from "../../../../components/Common/Modal/ShowStartPlan";
import { PlanService } from "../../../../services/planService";
import { EnumsService } from "../../../../services/enumsService";
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";

export default {
    name: "detial-plan",
    data() {
        return {
            tableHeight: 450,
            waterPlan: waterPlan,
            windPlan: windPlan,
            pageTotal: 1,
            showTableDetial: false,
            queryPram: {
                startTime: null,
                endTime: null,
                pageNum: 1,
                pageSize: 12,
                processKey: null
            },
            showModal: {
                modalPrams: { state: false }
            },
            tableColumns: [
                {
                    title: "Id",
                    key: "processInstanceId"
                },
                {
                    title: "开始时间",
                    key: "startTime"
                },
                {
                    title: "结束时间",
                    key: "endTime"
                },
                {
                    title: "操作",
                    key: "action",
                    width: 150,
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "default"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.show(
                                                params.row.processInstanceId
                                            );
                                        }
                                    }
                                },
                                "查看详情"
                            )
                        ]);
                    }
                }
            ],
            detialTableColumns: [
                {
                    title: "Id",
                    key: "id"
                },
                {
                    title: "开始时间",
                    key: "startTime"
                },
                {
                    title: "结束时间",
                    key: "endTime"
                },
                {
                    title: "流程",
                    key: "activityName"
                },
                {
                    title: "流程类型",
                    key: "activityType"
                }
            ],
            detialTableData: [],
            tableData: [],
            allSteps: [],
            bgImg: null,
            nodeImgSrc: null,
            planDetail: {
                show: false,
                url: null
            }
        };
    },
    mounted() {
        // 设置表格高度
        this.tableHeight = window.innerHeight * 0.69;
        this.queryPram.processKey = this.$route.params.processKey;
        this.setBgImg();
        this.getNodes();
    },
    methods: {
        getNodes() {
            EnumsService.getPlanType().then(res => {
                let curPlan = res.find(plan => {
                    return plan.processKey == this.$route.params.processKey;
                });
                this.nodeImgSrc = "/emplans/png/static/" + curPlan.val;
            });
        },
        setBgImg() {
            switch (this.queryPram.processKey) {
                case "VentilationPlanSOP_KEY":
                    this.bgImg = windPlan;
                    break;
                case "WaterPlanProcess":
                    this.bgImg = waterPlan;
                    break;
                default:
                    this.bgImg = waterPlan;
            }
        },
        //切换路由
        goToMoudle(path) {
            this.$router.push(path);
        },
        //切换页面
        changePage(index) {
            let _this = this;
            _this.queryPram.pageNum = index;
            _this.queryTable();
        },
        //切换页码数
        handlePageSize(value) {
            this.queryPram.pageSize = value;
            this.queryTable();
        },

        //点击切换面板事件
        changePane(name) {
            if (name == "log") {
                this.queryTable();
            } else {
            }
        },
        // 查询表数据
        queryTable() {
            this.queryPram.processKey = this.$route.params.processKey;
            let prams = {};
            prams = this.queryPram;
            let _this = this;
            if (
                new Date(_this.queryPram.startTime) >
                new Date(_this.queryPram.endTime)
            ) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            PlanService.submitPlanDetails(prams).then(
                result => {
                    _this.tableData = [];
                    result.list.forEach(a => {
                        let temp = {};
                        temp.startTime = new Date(a.startTime).format(
                            "YYYY-MM-dd hh:mm:ss"
                        );
                        temp.endTime = new Date(a.endTime).format(
                            "YYYY-MM-dd hh:mm:ss"
                        );
                        temp.processInstanceId = a.processInstanceId;
                        _this.tableData.push(temp);
                    });
                    _this.pageTotal = data.totalCount;
                },
                error => {
                    this.Log.info(err)
                }
            )
        },
        show(processInstanceId) {
            this.planDetail.show = true;
            this.planDetail.url = "emplans/png/" + processInstanceId;
        }
    },
    components: { ShowStartPlan, ImageFromUrl },
    watch: {
        $route: function() {
            // $route发生变化时再次赋值
            this.queryPram.processKey = this.$route.params.processKey;
            this.setBgImg();
            this.getNodes();
        }
    }
};
</script>

<style scoped>
.top {
    margin-top: 1vh;
    color: #fff;
    font-size: 1.66vmin;
    height: 5.5vh;
    padding: 1vh;
    margin-left: 10px;
    margin-right: 10px;
}
.ivu-tabs-card >>> .ivu-tabs-bar {
    margin-bottom: 0px !important;
}
.ivu-tabs >>> .ivu-card-body {
    padding: 10px !important;
}
.ivu-tooltip >>> .ivu-tooltip-inner {
    background-color: rgba(110, 110, 110, 0.9);
    text-align: center;
    color: rgb(174, 205, 237);
    height: 100px;
    font-size: 1.6vmin;
    display: table-cell;
    vertical-align: middle;
    font-weight: bold;
    min-width: 180px;
}
.ivu-tooltip >>> .ivu-tooltip-arrow {
    border-bottom-color: rgb(112, 112, 112);
}
.border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
    width: 7vw;
    height: 6vh;
    background-color: #22a5f5;
    padding: 7px;
    cursor: pointer;
    font-size: 1.66vmin;
}
.hr3 {
    height: 7px;
    border: none;
    border-top: 5px ridge #45d6d8;
}
.hr1 {
    margin: 0 auto;
    border: 0;
    height: 5px;
    background: #333;
    background-image: linear-gradient(to right, #ccc, #333, #ccc);
}
.begin {
    display: table-cell;
    text-align: right;
    width: 7vw;
    height: 6vh;
    vertical-align: middle;
}
.toolTip >>> .ivu-tooltip-inner {
    max-width: 2000px;
    height: 4vmin;
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
    color: #00b6e4;
}
.tab >>> .ivu-tabs-nav-container {
    height: 3.2vmin !important;
}

.ivu-select,
.ivu-select >>> .ivu-select-selection,
.ivu-input-wrapper >>> .ivu-input,
.ivu-date-picker >>> .ivu-input,
.ivu-select.ivu-select-single >>> .ivu-select-selected-value,
.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
.ivu-select-multiple.ivu-select-selection >>> .ivu-select-placeholder {
    height: 4vmin;
    line-height: 4vmin;
    font-size: 1.4vmin;
}
.tab >>> .ivu-input {
    background: transparent;
    color: #fff;
    border-radius: 1vmin;
}
.tab >>> .ivu-input::-webkit-input-placeholder,
.tab >>> .ivu-input-icon {
    color: #fff;
}

.tab >>> .ivu-tabs-nav-container {
    font-size: 1.66vmin;
}
.nodesPic {
    max-width: 34vw;
    margin: 8vmin auto;
}
.tab >>> .ivu-page-next,
.tab >>> .ivu-page-prev,
.tab >>> .ivu-select-selection,
.tab >>> .ivu-page-options-elevator input {
    background: transparent;
}

.tab >>> .ivu-select,
.tab >>> .ivu-page-options-elevator input,
.tab >>> .ivu-page-next a,
.tab >>> .ivu-page-prev a {
    color: #fff;
}
.tab .ivu-table-wrapper {
    border: none;
}
.tab .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.tab .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: 1px solid #7d7d7d;
}
.tab .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
.tab .ivu-table-wrapper >>> .ivu-table:before,
.tab .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.search {
    background-color: -webkit-linear-gradient(left, #8e5de0, #6527cd);
    background: -o-linear-gradient(right, #8e5de0, #6527cd);
    background: -moz-linear-gradient(right, #8e5de0, #6527cd);
    background: linear-gradient(to right, #8e5de0, #6527cd);
    border-color: #3e4f61;
    border-radius: 1vmin;
}
</style>
