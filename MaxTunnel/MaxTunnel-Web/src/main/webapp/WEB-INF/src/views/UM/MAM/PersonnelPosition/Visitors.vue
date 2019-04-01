<template>
    <div>
         <div class="conditions">
            <Row>
                <Col span="5">
                    <span>姓名：</span>
                    <Input v-model="conditions.name" placeholder="请输入姓名" style="width: 70%"></Input>
                </Col>
                <Col span="5">
                    <span>手机号：</span>
                    <Input v-model="conditions.phone" placeholder="请输入手机号" style="width: 70%"></Input>
                </Col>
                <Col span="5">
                    <span>设备：</span>
                    <Select v-model="conditions.equId" style="width: 70%">
                        <Option value="null">所有</Option>
                        <Option v-for="item in lists.equs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>时间范围：</span>
                    <DatePicker v-model="conditions.time" type="datetimerange" placeholder="请选择时间" style="width: 70%">
                    </DatePicker>
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
            <div class="page">
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size-opts=[12,24,36]
                :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
                </Page>
            </div>
        </div>
        <div class="viewer">
            <over-look-sm-viewer :cameraPosition="VMConfig.CAMERA" :personnelPosition="personnelPosition">
            </over-look-sm-viewer>
        </div>
    </div>
</template>
<script>
import { personnelPositionService } from '../../../../services/personnelPositionService'
import overLookSmViewer from "../../../../components/Common/3D/overLook3DViewer"

export default {
    data() {
        return {
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
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
        this.init()
        this.resetPageAndSearch()
    },
    methods: {
        init() {
            let _this = this
            personnelPositionService.getAllLocators().then(
                result=>{
                    _this.lists.equs = result
                },
                error=>{
                    _this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value
            this.search()
        },
        handlePageSize(value) {
            this.page.pageSize = value
            this.resetPageAndSearch()
        },
        resetPageAndSearch() {
            let params = {
                staffName: this.conditions.name,
                telphone: this.conditions.phone,
                equipmentId: this.conditions.equId,
                startTime: this.conditions.time ? new Date(this.conditions.time[0]) : null,
                endTime: this.conditions.time ? new Date(this.conditions.time[1]) : null,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            }
            let _this = this
            personnelPositionService.visitorsDatagrid(params).then(
                result=>{
                    _this.visitorList = result.pagedList
                    _this.page.pageTotal = result.total
                },
                error=>{
                    _this.Log.info(error)
                }
            )
        },
        detail(userId) {
            this.$router.push('/UM/PersonnelPosition/detail/' + userId)
        }
    }
};
</script>
<style scoped>
    .conditions >>> .ivu-input{
        height: 3.2vmin;
        font-size: 1.28vmin;
    }
    .list{
        height: 34vmin;
        width: 100%;
        overflow-y: auto; 
    }
    .infoList{
        border: 1px solid#dddfe1;
        width: 75%;
        margin: 10px auto;
        padding: 5px 0px;
        border-radius: 4px;
        box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
        position: relative;
        height: 11.2vmin;
    }
    .name{
        padding-left: 1vmin;
        font-size: 2vmin;
        margin-bottom: 0.8vmin;
    }
    .ivu-icon{
        margin-right: 5px;
        color: #ff9b00;
    }
    .tel, .idCard{
        margin: 0.2vmin 0.8vmin;
    }
    .option{
        position: absolute;
        bottom: 0.2vmin;
        right: 0.2vmin;
    }
    .viewer{
        margin: 1.2vmin;
        height: 42vmin;
    }
</style>
