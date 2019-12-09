<template>
    <div>
        <div class="conditions">
            <Row>
                <Col span="5">
                    <span class="queryTitle">姓名：</span>
                    <Input v-model="conditions.name" placeholder="请输入姓名" style="width: 70%"></Input>
                </Col>
                <Col span="5">
                    <span class="queryTitle">手机号：</span>
                    <Input v-model="conditions.phone" placeholder="请输入手机号" style="width: 70%"></Input>
                </Col>
                <Col span="5">
                    <span class="queryTitle">设备：</span>
                    <Select v-model="conditions.equId" style="width: 70%">
                        <Option value="null">所有</Option>
                        <Option
                            v-for="item in lists.equs"
                            :key="item.id"
                            :value="item.id"
                        >{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="queryTitle">时间范围：</span>
                    <DatePicker
                        v-model="conditions.time"
                        type="datetimerange"
                        placeholder="请选择时间"
                        style="width: 70%"
                    ></DatePicker>
                </Col>
                <Col span="2" offset="1">
                    <Button type="primary" @click="resetPageAndSearch" icon="ios-search">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Row>
                <Col span="4" v-for="item in visitorList" :key="item.id">
                    <div class="infoList">
                        <div class="name">
                            <Icon type="android-person"></Icon>
                            <span>{{item.name}}</span>
                        </div>
                        <div class="tel">
                            <Icon type="android-call"></Icon>
                            <span>{{item.telphone}}</span>
                        </div>
                        <div class="idCard">
                            <Icon type="card"></Icon>
                            <span>{{item.identityNO}}</span>
                        </div>
                        <div class="option">
                            <Button type="primary" size="small" @click="detail(item.id)">详情</Button>
                        </div>
                    </div>
                </Col>
            </Row>
            <!-- <div class="page"> -->
            <Page
                :total="page.pageTotal"
                :current="page.pageNum"
                :page-size-opts="[12,24,36]"
                :page-size="page.pageSize"
                show-sizer
                show-total
                placement="top"
                @on-change="handlePage"
                @on-page-size-change="handlePageSize"
                show-elevator
                :style="pageStyle"
            ></Page>
            <!-- </div> -->
        </div>
        <div class="viewer">
            <over-look-sm-viewer
                :cameraPosition="VMConfig.CAMERA"
                :personnelPosition="personnelPosition"
                ref="smViewer"
            ></over-look-sm-viewer>
        </div>
    </div>
</template>
<script>
import { personnelPositionService } from "../../../../services/personnelPositionService";
import overLookSmViewer from "../../../../components/Common/3D/overLook3DViewer";

export default {
    data() {
        return {
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px",
                color: "#fff"
            },
            conditions: {
                name: null,
                phone: null,
                equId: null,
                time: null
            },
            lists: {
                equs: []
            },
            visitorList: [],
            personnelPosition: {
                openPosition: true,
                isShow: true,
                refreshTime: 10000
            }
        };
    },
    components: {
        overLookSmViewer
    },
    mounted() {
        this.init();
        this.resetPageAndSearch();
    },
    methods: {
        init() {
            let _this = this;
            personnelPositionService.getAllLocators().then(
                result => {
                    _this.lists.equs = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.resetPageAndSearch();
        },
        resetPageAndSearch() {
            let params = {
                staffName: this.conditions.name,
                telphone: this.conditions.phone,
                equipmentId: this.conditions.equId,
                startTime: this.conditions.time
                    ? new Date(this.conditions.time[0])
                    : null,
                endTime: this.conditions.time
                    ? new Date(this.conditions.time[1])
                    : null,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            let _this = this;
            personnelPositionService.visitorsDatagrid(params).then(
                result => {
                    _this.visitorList = result.pagedList;
                    _this.page.pageTotal = result.total;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        detail(userId) {
            this.$router.push("/UM/PersonnelPosition/detail/" + userId);
        }
    }
};
</script>
<style scoped>
.conditions >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
}
.queryTitle,
.conditions >>> .ivu-select-selected-value,
.list >>> .ivu-select,
.list >>> .ivu-page-options-elevator input,
.list >>> .ivu-page-next a,
.list >>> .ivu-page-prev a {
    color: #fff;
}
.conditions >>> .ivu-select-selection {
    background: transparent;
    border-radius: 1vmin;
}
.queryTitle {
    font-size: 1.4vmin;
}
.list >>> .ivu-page-next,
.list >>> .ivu-page-prev,
.list >>> .ivu-select-selection,
.list >>> .ivu-page-options-elevator input {
    background: transparent;
}
.list {
    height: 34vmin;
    width: 100%;
    overflow-y: auto;
}
.infoList {
    width: 90%;
    margin: 10px auto;
    padding: 5px 0px;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
    position: relative;
    height: 14vmin;
    color: #fff;
}
.name {
    padding-left: 1vmin;
    font-size: 2vmin;
    margin-bottom: 0.8vmin;
}
.ivu-icon {
    margin-right: 5px;
    color: #1296db;
}
.tel,
.idCard {
    margin: 0.4vmin 1vmin;
    font-size: 1.6vmin;
}
.option {
    position: absolute;
    bottom: 1vmin;
    right: 1vmin;
}
.option .ivu-btn {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.2vmin !important;
}
.viewer {
    margin: 1.2vmin;
    height: 42vmin;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
    padding: 1vmin;
}
</style>
