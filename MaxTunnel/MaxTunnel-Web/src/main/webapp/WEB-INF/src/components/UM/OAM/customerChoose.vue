	<template>
    <div>
        <div class="conditions">
            <Row>
                <Col span="10">
                    <span class="conditionTitle">企业名称：</span>
                    <Input v-model="conditions.name" placeholder="请输入企业名称" style="width: 60%"/>
                </Col>
                <!-- <Col span="6">
						<span class="conditionTitle">开始时间：</span>
						<DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime"></DatePicker>
					</Col>
					<Col span="6">
						<span class="conditionTitle">结束时间：</span>
						<DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime"></DatePicker>
                </Col>-->
                <Col span="6" offset="8">
                    <Button type="primary" icon="ios-search" @click="queryList()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Row>
                <Col span="8" v-for="(item,index) in customers" :key="index">
                    <div class="infoList" @click="getCustomerId(item.id,item.name)">
                        <!-- <input type="radio" class="radio" name="customers" :checked="item.id == customerId" @change="getCustomerId(item.id,item.contact)"> -->
                        <div class="company">
                            <Icon type="android-bookmark"></Icon>
                            <span>{{item.name}}</span>
                        </div>
                        <div>
                            <Row>
                                <Col span="24" class="contactInfoItem">
                                    <Icon type="ios-home"></Icon>
                                    <span>{{item.address}}</span>
                                </Col>
                                <Col span="24" class="contactInfoItem">
                                    <Icon type="android-call"></Icon>
                                    <span>{{item.phone}}</span>
                                </Col>
                                <Col span="24" class="contactInfoItem">
                                    <Icon type="card"></Icon>
                                    <span>{{item.account}}</span>
                                </Col>
                                <Col span="24" class="contactInfoItem">
                                    <Icon type="android-mail"></Icon>
                                    <span>{{item.mail}}</span>
                                </Col>
                            </Row>
                        </div>
                        <!-- <div class="crtTime">
						<Icon type="android-time"></Icon>
						<span>{{item.crtTime}}</span>
                        </div>-->
                        <div class="checkBox">
                            <Checkbox :value="item.id == companyId"></Checkbox>
                        </div>
                    </div>
                </Col>
            </Row>
            <Page
                :total="page.pageTotal"
                :current="page.pageNum"
                :page-size="page.pageSize"
                show-total
                placement="top"
                @on-change="handlePage"
                show-elevator
                style="text-align: right;margin-top: 20px;color:#fff"
            ></Page>
        </div>
    </div>
</template>

	<script>
import { EnterGalleryService } from "../../../services/enterGalleryService";
import CompanyService from "../../../services/companyService";
export default {
    name: "customerChoose",
    props: {
        companyId: {
            type: Number,
            required: false
        }
    },
    data() {
        return {
            company: [],
            conditions: {
                name: null,
                startTime: null,
                endTime: null
            },
            page: {
                pageNum: 1,
                pageSize: 6,
                pageTotal: 0
            },
            customers: []
        };
    },
    mounted() {
        this.queryList();
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                bank: null,
                //   startTime: new Date(this.conditions.startTime).getTime(),
                //   endTime: new Date(this.conditions.endTime).getTime(),
                startTime: null,
                endTime: null
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        queryList() {
            let _this = this;
            CompanyService.companiesDatagrid(this.params).then(
                result => {
                    for (let index in result.list) {
                        result.list[index].crtTime = new Date(
                            result.list[index].crtTime
                        ).format("yyyy-MM-dd hh:mm:s");
                    }
                    _this.customers = result.list;
                    _this.page.pageTotal = result.total;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryList();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryList();
        },
        getCustomerId(id, name) {
            let company = {
                id: id,
                name: name
            };
            this.$emit("selectCustomer", company);
        }
    }
};
</script>

	<style scoped>
.radio {
    position: absolute;
    top: 4.8vmin;
    left: -2vmin;
}
.list {
    height: 30vmin;
}
.infoList {
    border: 1px solid #dddfe1;
    width: 24vmin;
    height: 14vmin;
    margin: 1vmin auto;
    border-radius: 4px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    position: relative;
    cursor: pointer;
    font-size: 1.66vmin;
    color: #fff;
}
.company {
    padding-left: 1vmin;
    font-size: 2vmin;
    margin: 0.8vmin;
}
.company span {
    font-size: 2vmin;
}
.contactInfoItem {
    padding-left: 10px;
    line-height: 1.4vmin;
    font-size: 1.2vmin;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: 0.4vmin 0;
}
.crtTime {
    padding: 0px 0px 0px 1vmin;
}
.conditions {
    padding: 1vmin;
}
.list {
    margin-top: 1vmin;
}
.ivu-icon {
    margin-right: 0.5vmin;
    color: #f4ea2a;
}
.checkBox {
    position: absolute;
    bottom: 0.2vmin;
    right: 0.2vmin;
}

.infoList >>> .ivu-checkbox-inner {
    width: 1.4vmin;
    height: 1.4vmin;
}
.infoList >>> .ivu-checkbox-inner:after {
    width: 0.4vmin;
    height: 0.8vmin;
    top: 0.1vmin;
    left: 0.4vmin;
}
</style>

