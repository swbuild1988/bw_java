<template>
    <div class="allDiv">
        <div class="queryCondition">
            <Row>
                <Col span="6">
                    <span>合同编号：</span>
                    <Input v-model="conditions.contractId" placeholder="请输入合同编号" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <span>合同名称：</span>
                    <Input
                        v-model="conditions.contractName"
                        placeholder="请输入合同名称"
                        style="width: 60%"
                    ></Input>
                </Col>
                <Col span="6">
                    <span>付款方式：</span>
                    <Select v-model="conditions.payment" style="width: 60%" id="payment">
                        <Option value="null">所有</Option>
                        <Option
                            v-for="(item,index) in selectList.payType"
                            :key="index"
                            :value="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>合同状态：</span>
                    <Select v-model="conditions.contractStatus" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option
                            v-for="(item,index) in selectList.contractStatus"
                            :key="index"
                            :value="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>开始时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择开始时间"
                        style="width: 60%"
                        v-model="conditions.startTime"
                    ></DatePicker>
                </Col>
                <Col span="6">
                    <span>结束时间：</span>
                    <DatePicker
                        type="datetime"
                        placeholder="请选择结束时间"
                        style="width: 60%"
                        v-model="conditions.endTime"
                    ></DatePicker>
                </Col>
                <Col span="6">
                    <Poptip placement="bottom">
                        <span>企业客户：</span>
                        <Input v-model="customerName" placeholder="请选择客户" id="cusInput"/>
                        <div class="pop" slot="content">
                            <customer-choose
                                @selectCustomer="getcompanyId"
                                v-bind:companyId="conditions.companyId"
                            ></customer-choose>
                        </div>
                    </Poptip>
                </Col>
                <Col span="6">
                    <Button type="primary" @click="resetPageAndSearch" icon="ios-search">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <div class="nullData" v-show="isNullData">暂无数据</div>
            <Row>
                <Col span="6" v-for="(item,index) in contractList" :key="index">
                    <div class="contracts">
                        <div style="display: table-cell;vertical-align: middle;text-align: center;">
                            <div class="contractName" @click="edit(index)">
                                <span>{{item.name}}</span>
                            </div>
                            <div class="contactInfo">
                                <div class="item">
                                    <div class="title">公司：</div>
                                    <div class="info">{{ item.companyName }}</div>
                                </div>
                                <div class="item">
                                    <div class="title">联系方式：</div>
                                    <div class="info">{{ item.tel }}</div>
                                </div>
                                <div class="item">
                                    <div class="title">合同状态：</div>
                                    <div
                                        :class="['info',{'red': item.contractStatus === '过期'}]"
                                    >{{ item.contractStatus }}</div>
                                </div>
                                <div class="item">
                                    <div class="title">创建时间：</div>
                                    <div class="info">{{ item.crtTime }}</div>
                                </div>
                                <div class="item">
                                    <div class="title">付款方式：</div>
                                    <div class="info">{{ item.payType }}</div>
                                </div>
                            </div>
                            <div class="option">
                                <Tooltip content="详情">
                                    <div class="buttons">
                                        <Icon
                                            type="android-list"
                                            @click.native="read(index)"
                                            color="rgb(198,206,230)"
                                            class="icons"
                                        ></Icon>
                                    </div>
                                </Tooltip>
                                <Tooltip content="编辑">
                                    <div class="buttons">
                                        <Icon
                                            type="edit"
                                            @click.native="edit(index)"
                                            class="icons"
                                            color="rgb(198,206,230)"
                                        ></Icon>
                                    </div>
                                </Tooltip>
                            </div>
                            <Tooltip content="删除" class="del">
                                <Icon
                                    type="trash-a"
                                    @click.native="del(item.id)"
                                    class="icons"
                                    color="rgb(162, 77, 72)"
                                ></Icon>
                            </Tooltip>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
        <div class="page">
            <Page
                :total="page.pageTotal"
                :current="page.pageNum"
                :page-size-opts="[8,16,24]"
                :page-size="page.pageSize"
                show-sizer
                show-total
                placement="top"
                @on-change="handlePage"
                @on-page-size-change="handlePageSize"
                show-elevator
                :style="pageStyle"
            ></Page>
        </div>
    </div>
</template>

<script>
import { EnumsService } from "../../../../services/enumsService";
import { ContractService } from "../../../../services/contractService";
import types from "../../../../../static/Enum.json";
import CustomerChoose from "../../../../components/UM/OAM/CustomerChoose";
export default {
    data() {
        return {
            page: {
                pageNum: 1,
                pageSize: 8,
                pageTotal: 0
            },
            contractList: [],
            conditions: {
                payment: null,
                contractId: null,
                contractName: null,
                startTime: null,
                endTime: null,
                companyId: null,
                contractStatus: null
            },
            selectList: {
                payType: [],
                contractStatus: []
            },
            customer: [],
            customerName: "",
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px",
                color: "#fff"
            },
            contractIds: [],
            isNullData: false
        };
    },
    components: { CustomerChoose },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                id: this.conditions.contractId
                    ? this.conditions.contractId
                    : null,
                name: this.conditions.contractName,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                companyId: this.conditions.companyId,
                payType: this.conditions.payment,
                contractStatus: this.conditions.contractStatus
            };
            return Object.assign({}, param);
        }
    },
    mounted() {
        this.initData();
        this.search();
        this.$nextTick(() => {
            let width = document.getElementById("payment").offsetWidth;
            document.getElementById("cusInput").style.width = width + "px";
        });
        window.onresize = function() {
            let width = document.getElementById("payment").offsetWidth;
            document.getElementById("cusInput").style.width = width + "px";
        };
    },
    methods: {
        initData() {
            let _this = this;
            EnumsService.getPayType().then(
                result => {
                    _this.selectList.payType = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            EnumsService.getContractStatus().then(
                result => {
                    _this.selectList.contractStatus = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        resetPageAndSearch() {
            this.page.pageNum = 1;
            this.search();
        },
        search() {
            if (!this.customerName && this.conditions.companyId) {
                this.conditions.companyId = null;
            }
            let _this = this;
            if (
                new Date(_this.conditions.startTime) >
                new Date(_this.conditions.endTime)
            ) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            ContractService.contractDatagrid(_this.params).then(result => {
                _this.contractList = [];
                if (!result.list.length) {
                    _this.isNullData = true;
                } else {
                    _this.isNullData = false;
                }
                result.list.forEach(a => {
                    _this.contractList.push({
                        id: a.id,
                        companyName: a.company.name,
                        name: a.name,
                        contact: a.company.mail,
                        tel: a.company.phone,
                        payType: a.payTypeName,
                        contractStatus: a.contractStatusName,
                        contractStartTime: new Date(a.contractStartTime).format(
                            "yyyy-MM-dd"
                        ),
                        contractEndTime: new Date(a.contractEndTime).format(
                            "yyyy-MM-dd"
                        ),
                        crtTime: new Date(a.crtTime).format(
                            "yyyy-MM-dd hh:mm:ss"
                        )
                    });
                    _this.contractIds.push(a.id);
                    _this.page.pageTotal = result.total;
                });
            });
        },
        goToMoudle(path) {
            this.$router.push(path);
        },
        goMoudle(index, type) {
            if (type == 2) {
                this.$router.push({
                    name: "编辑合同",
                    params: {
                        contractIds: this.contractIds,
                        curIndex: index,
                        type: type
                    }
                });
            } else if (type == 1) {
                this.$router.push({
                    name: "合同详情",
                    params: {
                        contractIds: this.contractIds,
                        curIndex: index,
                        type: type
                    }
                });
            }
        },
        read(index) {
            this.goMoudle(index, types.pageType.Read);
        },
        edit(index) {
            this.goMoudle(index, types.pageType.Edit);
        },
        del(id) {
            this.$Modal.confirm({
                title: "合同信息",
                content: "<p>确定删除吗？</p>",
                onOk: () => {
                    let _this = this;
                    ContractService.delelteContract(id).then(
                        result => {
                            _this.resetPageAndSearch();
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                }
            });
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.resetPageAndSearch();
        },
        getcompanyId(data) {
            this.conditions.companyId = data.id;
            this.customerName = data.name.toString();
        }
    }
};
</script>

<style scoped>
.allDiv {
    padding-bottom: 0;
}
.list {
    margin-top: 10px;
    padding: 20px 0 24px 0;
}
.pop {
    max-height: 300px;
    overflow-y: auto;
}
.contracts {
    /* border: 1px solid #dddfe1;*/
    width: 90%;
    height: 30vh;
    margin: 10px auto;
    border-radius: 4px;
    position: relative;
    background-image: url("../../../../assets/UM/border2.png");
    background-size: 100% 100%;
    display: table;
}
.option {
    position: absolute;
    top: 1.66vmin;
    right: 1.66vmin;
}
.contractName {
    text-align: center;
    padding: 20px 0 10px 0;
    font-size: 2vmin;
    font-weight: bold;
    color: rgb(252, 252, 255);
    cursor: pointer;
}
.contactInfo {
    padding-bottom: 30px;
}
.red {
    color: rgb(255, 102, 0);
}
.cable {
    text-align: center;
    background-color: rgb(245, 245, 245);
    padding: 4px 0 4px 0;
}
.cableInfo {
    padding: 0 8px;
}
.ivu-poptip {
    width: 100%;
}
#cusInput {
    width: 60%;
}
.del {
    position: absolute;
    bottom: 8%;
    right: 6%;
    cursor: pointer;
}
.buttons {
    /* width: 26px;
    height: 26px;
    background-color: rgb(86,149,185);
    border-radius: 50%;
    text-align: center;
    padding: 4px;*/
    cursor: pointer;
}
.item {
    margin: 10px;
}
.title {
    color: rgb(223, 223, 241);
    display: inline-block;
    font-size: 1.66vmin;
    width: 38%;
    text-align: right;
}
.info {
    display: inline-block;
    color: rgb(234, 234, 243);
    font-size: 1.66vmin;
    width: 60%;
}

.queryCondition >>> .ivu-poptip-popper {
    width: 100vmin;
}

.queryCondition span {
    font-size: 1.66vmin;
}

.queryCondition >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
}
/*日期选择*/
.queryCondition >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.queryCondition >>> .ivu-picker-panel-icon-btn {
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.queryCondition >>> .ivu-date-picker-header-label {
    font-size: 1.66vmin;
}

@media (min-width: 1921px) {
    .queryCondition >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .queryCondition >>> .ivu-date-picker-cells-cell {
        width: 2vmin;
    }
    .queryCondition >>> .ivu-date-picker-cells-header span {
        padding-right: 2.5rem;
    }
}

/*下拉框*/
.queryCondition >>> .ivu-select-selection {
    height: 3.2vmin !important;
}

.queryCondition >>> .ivu-select-placeholder,
.ivu-select-selected-value {
    font-size: 1.28vmin !important;
    padding-top: 0.4vmin !important;
    height: 2.6vmin !important;
    line-height: 2vmin !important;
}
.queryCondition >>> .ivu-select-dropdown {
    max-height: 30vmin;
}
.queryCondition .ivu-poptip-inner {
    background-color: 485463;
}
.icons {
    font-size: 2.4vmin;
}

.page >>> .ivu-select-selection {
    height: 3.2vmin !important;
}
.page >>> .ivu-select-selected-value,
.ivu-select-placeholder {
    font-size: 1.2vmin !important;
    height: 3vmin !important;
    line-height: 3vmin !important;
}
.page >>> .ivu-page-options-elevator input {
    font-size: 1.2vmin;
    height: 3vmin;
}
.page >>> .ivu-page-options-elevator {
    display: inline-block;
    height: 3.2vmin;
    line-height: 3.2vmin;
}
.page >>> .ivu-page-next {
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-next .ivu-icon {
    font-size: 1.6vmin;
}
.page >>> .ivu-page-prev {
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-prev .ivu-icon {
    font-size: 1.6vmin;
}
</style>

