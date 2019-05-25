<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="4">
                    <span>企业名称：</span>
                    <Input v-model="conditions.name" placeholder="请输入企业名称" style="width: 60%"/>
                </Col>
                <Col span="4">
                    <span>开户行：</span>
                    <Input v-model="conditions.bank" placeholder="请输入开户行" style="width: 60%"/>
                </Col>
                <Col span="4">
                    <span>开始时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择开始时间"
                        style="width: 60%"
                        v-model="conditions.startTime"
                    ></DatePicker>
                </Col>
                <Col span="4">
                    <span>结束时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择结束时间"
                        style="width: 60%"
                        v-model="conditions.endTime"
                    ></DatePicker>
                </Col>
                <Col span="4">
                    <Button type="primary" icon="ios-search" @click="queryList()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list" style="margin-top: 10px;padding: 10px 0;">
            <!-- <Button class="add" type="info" @click="goToMoudle({ path:'/UM/tunnelcompany/add'})"><Icon type="plus-round" color="#fff"></Icon>添加</Button> -->
            <Row>
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
                bottom: "20px",
                right: "15px",
                color: "#fff"
            }
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
            this.$router.push({
                name: "添加管廊客户",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        edit(id) {
            this.goMoudle(id, types.pageType.Edit);
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
    background: url("../../../../assets/UM/companyBg.png") no-repeat;
    background-size: 100% 100%;
}
.company {
    text-align: center;
    font-size: 2.2vmin;
    line-height: 4.4vmin;
}
.columns {
    line-height: 3.2vmin;
    font-size: 1.4vmin;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    /* margin: 0.4vmin 0; */
}

.crtTime {
    font-size: 1.2vmin;
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
.edit {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
}
.del {
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
}

@media (min-width: 2200px) {
    .ivu-select,
    .ivu-select >>> .ivu-select-selection,
    .ivu-input-wrapper >>> .ivu-input,
    .ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,
    .ivu-select.ivu-select-single >>> .ivu-select-placeholder {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .infoList {
        width: 82%;
    }
}
</style>
