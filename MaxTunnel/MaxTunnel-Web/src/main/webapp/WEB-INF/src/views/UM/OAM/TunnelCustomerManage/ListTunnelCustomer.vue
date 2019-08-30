<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="6">
                    <span class="conditionTitle">企业名称：</span>
                    <Input v-model="conditions.name" placeholder="请输入企业名称" style="width: 60%"/>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择开始时间"
                        style="width: 60%"
                        v-model="conditions.startTime"
                    ></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择结束时间"
                        style="width: 60%"
                        v-model="conditions.endTime"
                    ></DatePicker>
                </Col>
                <Col span="6">
                    <Button type="primary" icon="ios-search" @click="queryList()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Tabs value="card">
                <TabPane label="卡片" name="card">
                    <Row class="cardStyle">
                        <Col span="6" v-for="(item,index) in companyList" :key="index">
                            <div class="infoList">
                                <div class="company">
                                    <Icon type="android-bookmark"></Icon>
                                    <span>{{item.name}}</span>
                                </div>
                                <div class="conta-ctInfo">
                                    <Row>
                                        <Col span="10" offset="1" class="columns">
                                            <Icon type="ios-home"></Icon>
                                            <span>{{item.address}}</span>
                                        </Col>
                                        <Col span="10" offset="2" class="columns">
                                            <Icon type="android-call"></Icon>
                                            <span>{{item.phone}}</span>
                                        </Col>
                                        <Col span="10" offset="1" class="columns">
                                            <Icon type="card"></Icon>
                                            <span>{{item.account}}</span>
                                        </Col>
                                        <Col span="10" offset="2" class="columns">
                                            <Icon type="android-mail"></Icon>
                                            <span>{{item.mail}}</span>
                                        </Col>
                                        <Col span="10" offset="1" class="columns">
                                            <Icon type="ios-barcode"></Icon>
                                            <span>{{item.creditNo}}</span>
                                        </Col>
                                        <Col span="10" offset="2" class="columns">
                                            <Icon type="cash"></Icon>
                                            <span>{{item.bank}}</span>
                                        </Col>
                                        <Col span="10" offset="1">
                                            <div class="option">
                                                <Button
                                                    type="primary"
                                                    size="small"
                                                    @click="edit(item.id)"
                                                    class="edit"
                                                >编辑</Button>
                                                <Button
                                                    type="primary"
                                                    size="small"
                                                    @click="read(item.id)"
                                                    class="read"
                                                >详情</Button>
                                                <Button
                                                    type="error"
                                                    size="small"
                                                    @click="instance(item.id)"
                                                    class="del"
                                                >删除</Button>
                                            </div>
                                        </Col>
                                        <Col span="10" offset="2">
                                            <div class="crtTime">
                                                <Icon type="android-time"></Icon>
                                                <span>{{item.crtTime}}</span>
                                            </div>
                                        </Col>
                                    </Row>
                                </div>
                            </div>
                        </Col>
                    </Row>
                </TabPane>
                <TabPane label="表格" name="table" class="table">
                    <Table :columns="companyColumn" :data="companyData" :height="tableHeight"></Table>
                </TabPane>
            </Tabs>
        </div>
        <Page
            :total="page.pageTotal"
            :current="page.pageNum"
            :page-size="page.pageSize"
            show-sizer
            show-total
            :page-size-opts="[12,24,36]"
            placement="top"
            @on-change="handlePage"
            @on-page-size-change="handlePageSize"
            show-elevator
            :style="pageStyle"
        ></Page>
    </div>
</template>
<script>
import { EnumsService } from "../../../../services/enumsService";
import CompanyService from "../../../../services/companyService";
import types from "../../../../../static/Enum.json";
export default {
    data() {
        return {
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            companyList: [],
            conditions: {
                bank: null,
                name: null,
                startTime: null,
                endTime: null
            },
            payType: [],
            pageStyle: {
                position: "absolute",
                bottom: "1vmin",
                right: "2.5vmin",
                color: "#fff"
            },
            companyColumn: [
                {
                    title: "企业名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "信用代码",
                    key: "creditNo",
                    align: "center"
                },
                {
                    title: "开户行",
                    key: "bank",
                    align: "center"
                },
                {
                    title: "账号",
                    key: "account",
                    align: "center"
                },
                {
                    title: "注册地址",
                    key: "address",
                    align: "center"
                },
                {
                    title: "注册电话",
                    key: "phone",
                    align: "center"
                },
                {
                    title: "邮箱",
                    key: "mail",
                    align: "center"
                },
                {
                    title: "操作",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    class: "edit",
                                    props: {
                                        type: "error",
                                        size: "small"
                                    },
                                    style: {
                                        marginRight: "0.4vmin"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row.id);
                                        }
                                    }
                                },
                                "编辑"
                            ),
                            h(
                                "Button",
                                {
                                    class: "read",
                                    props: {
                                        type: "error",
                                        size: "small"
                                    },
                                    style: {
                                        marginRight: "0.4vmin"
                                    },
                                    on: {
                                        click: () => {
                                            this.read(params.row.id);
                                        }
                                    }
                                },
                                "详情"
                            ),
                            h(
                                "Button",
                                {
                                    class: "del",
                                    props: {
                                        type: "error",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.del(params.row.id);
                                        }
                                    }
                                },
                                "删除"
                            )
                        ]);
                    }
                }
            ],
            companyData: [],
            tableHeight: 600
        };
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                bank: this.conditions.bank,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime()
            };
            return Object.assign({}, param);
        }
    },
    mounted() {
        this.tableHeight = (window.innerHeight / 100) * 60;
        this.queryList();
    },
    methods: {
        queryList: function() {
            let _this = this;
            if (
                new Date(_this.conditions.startTime) >
                new Date(_this.conditions.endTime)
            ) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            CompanyService.companiesDatagrid(_this.params).then(
                result => {
                    for (let index in result.list) {
                        result.list[index].crtTime = new Date(
                            result.list[index].crtTime
                        ).format("yyyy-MM-dd");
                    }
                    _this.companyList = result.list;
                    _this.companyData = result.list;
                    _this.page.pageTotal = result.total;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        goToMoudle: function(path) {
            this.$router.push(path);
        },
        goMoudle: function(id, type) {
            sessionStorage.setItem(
                "refreshAddress",
                "/UM/tunnelCustomer/detail"
            );
            sessionStorage.setItem("detailId", id);
            sessionStorage.setItem("pageType", type);
            this.$router.push({
                name: "管廊客户详情",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        edit(id) {
            this.goMoudle(id, types.pageType.Edit);
        },
        read(id) {
            this.goMoudle(id, types.pageType.Read);
        },
        ok() {
            this.delIt = true;
        },
        instance(id) {
            this.$Modal.confirm({
                title: "客户信息",
                width: "24vw",
                content: "<p>是否删除这条客户信息</p>",
                onOk: () => {
                    let _this = this;
                    CompanyService.deleteCompany(id).then(
                        result => {
                            _this.queryList();
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                },
                onCancel: () => {}
            });
        },
        handlePage(value) {
            this.queryList();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryList();
        }
    }
};
</script>
<style scoped>
.infoList {
    width: 80%;
    margin: 10px auto;
    padding: 5px 0px;
    color: #fff;
    background: url("../../../../assets/UM/border3.png") no-repeat;
    background-size: 100% 100%;
}
.company {
    text-align: center;
    font-size: 2.2vmin;
    line-height: 4.4vmin;
}
.columns {
    line-height: 3.2vmin;
    font-size: 1.5vmin;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    /* margin: 0.4vmin 0; */
}

.crtTime {
    font-size: 1.4vmin;
    line-height: 4.4vmin;
}
.option {
    line-height: 4.4vmin;
}
.add {
    width: 100px;
    float: right;
}
.ivu-icon {
    margin-right: 5px;
    color: #f4ea2a;
}
.conditions .ivu-input-icon {
    line-height: 3vmin;
}
.edit,
.table >>> .ivu-btn:first-child {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1vmin !important;
}
.del,
.table >>> .ivu-btn:last-child {
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1vmin !important;
}
.read,
.table >>> .ivu-btn:nth-child(2) {
    background-color: -webkit-linear-gradient(left, #dcd77c, #cabf11);
    background: -o-linear-gradient(right, #dcd77c, #cabf11);
    background: -moz-linear-gradient(right, #dcd77c, #cabf11);
    background: linear-gradient(to right, #dcd77c, #cabf11);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1vmin !important;
}

.ivu-select,
.ivu-select >>> .ivu-select-selection,
.ivu-input-wrapper >>> .ivu-input,
.ivu-date-picker >>> .ivu-input,
.ivu-select.ivu-select-single >>> .ivu-select-selected-value,
.ivu-select.ivu-select-single >>> .ivu-select-placeholder {
    height: 3.2vmin;
    line-height: 3.2vmin;
    font-size: 1.24vmin;
}
.ivu-page >>> .ivu-page-total,
.ivu-page >>> .ivu-page-options-elevator {
    color: #fff;
}
/* .table {
    padding-right: 4vmin;
} */
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
    border-bottom: 1px solid #7d7d7d;
}
.table .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
.table .ivu-table-wrapper >>> .ivu-table:before,
.table .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.ivu-tabs {
    color: #fff;
}
.ivu-table-cell:last-child {
    width: 20vmin;
}
.list {
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    padding: 1%;
}
.ivu-table-wrapper >>> .ivu-table-overflowY {
    overflow-x: hidden;
}
.cardStyle {
    overflow-x: hidden;
    overflow-y: auto;
    height: 60vh;
}
.ivu-table-wrapper >>> .ivu-table-overflowY::-webkit-scrollbar,
.cardStyle::-webkit-scrollbar {
    width: 0.4vmin;
    height: 0.4vmin;
}
.ivu-table-wrapper >>> .ivu-table-overflowY::-webkit-scrollbar-thumb,
.cardStyle::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.ivu-table-wrapper >>> .ivu-table-overflowY::-webkit-scrollbar-track,
.cardStyle::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
@media (min-width: 2200px) {
    .infoList {
        width: 95%;
    }
}
</style>
