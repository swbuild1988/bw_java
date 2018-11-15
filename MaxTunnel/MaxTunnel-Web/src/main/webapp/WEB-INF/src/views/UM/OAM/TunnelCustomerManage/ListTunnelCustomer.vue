<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="6">
                    <span class="conditionTitle">所属公司：</span>
                    <Select v-model="conditions.companyId" @on-change="queryList()" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option v-for="item in company" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">联系人：</span>
                    <Input v-model="conditions.contact" placeholder="请输入联系人姓名" style="width: 60%" @on-keyup="queryList()"></Input>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime" @on-change="queryList()"></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime" @on-change="queryList()"></DatePicker>
                </Col>
            </Row>
        </div>
        <div class="list" style="background: #fff;margin-top: 10px;padding: 10px 0;">
            <!-- <Button class="add" type="info" @click="goToMoudle({ path:'/UM/tunnelCustomer/add'})"><Icon type="plus-round" color="#fff"></Icon>添加</Button> -->
            <Row>
                <Col span="6" v-for="(item,index) in customerList" :key="index">
                    <div class="infoList">
                        <div class="company">
                            <Icon type="star"></Icon>
                            <span>{{item.company.name}}</span>
                        </div>
                        <div class="conta-ctInfo">
                            <div class="contact">
                                <Icon type="android-person"></Icon>
                                <span>{{item.contact}}</span>
                            </div>
                            <div class="tel">
                                <Icon type="android-call"></Icon>
                                <span>{{item.tel}}</span>
                            </div>
                        </div>
                        <div class="crtTime">
                            <Icon type="android-time"></Icon>
                            <span>{{item.crtTime}}</span>
                        </div>
                        <div class="option">
                            <Button type="primary" size="small" @click="edit(index)">编辑</Button>
                            <Button type="error" size="small" @click="instance(index)">删除</Button>
                        </div>
                    </div>
                </Col>
            </Row>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle"></Page>
        </div>
    </div>
</template>
<script>
import { EnterGalleryService } from '../../../../services/enterGalleryService' 
import { CustomerService } from '../../../../services/customerService' 
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            customerList:[
                {
                    id: 1,
                    company:{name: '波汇科技'},
                    contact: '占三',
                    tel: '17664567786',
                    crtTime: '2018-08-23 12:23:46'
                }
            ],
            conditions:{
                payment: null,
                companyId:null,
                contact: null,
                startTime: null,
                endTime: null
            },
            payType:[],
            company: [
            ],
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                companyId:this.conditions.companyId,
                contact: this.conditions.contact,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime()
            };
            return Object.assign({}, param);
        }
    },
    mounted(){
        this.queryList()
        //获取所有的公司
        let _this = this
        EnterGalleryService.getCompanys().then(
            (result)=>{
                _this.company = result
            },
            (error)=>{
                _this.Log.info(error)
            })
    },
    methods:{
        queryList: function(){
            let _this = this
            CustomerService.customerDatagrid(_this.params).then(
                (result)=>{
                    for( let index in result.list ){
                        result.list[index].crtTime = new Date( result.list[index].crtTime ).format( "yyyy-MM-dd hh:mm:s" )
                    }
                    _this.customerList = result.list;
                    _this.page.pageTotal = result.total
                },
                (error)=>{
                    _this.Log.info(error)
                })
        },
        goToMoudle: function (path) {
            this.$router.push(path);
        },
        goMoudle: function (index,type) {
            this.$router.push({
                name: '添加管廊客户',
                params: {
                    id: this.customerList[index].id,
                    type: type
                }
            });
        },
        edit(index,type){
            this.goMoudle(index,types.pageType.Edit);
        },
        ok () {
            this.delIt = true;
        },
        instance(index) {
            this.$Modal.confirm({
                title: '客户信息',
                content: '<p>是否删除这条客户信息</p>',
                onOk: () => {
                    let _this = this
                    CustomerService.deleteCustomer(_this.customerList[index].id).then(
                        (result)=>{
                            _this.customerList.splice(_this.customerList[index].id, 1);
                            _this.queryList()
                        },
                        (error)=>{
                            _this.Log.info(error)
                    })
                },
                onCancel: () => {
                }
            });
        },
        handlePage(value) {
            this.queryList()
        },
        handlePageSize(value) {
            this.page.pageSize = value
            this.queryList()
        }
    }
}
</script>
<style scoped>
    .infoList{
        border: 1px solid#dddfe1;
        width: 260px;
        margin: 10px auto;
        padding: 5px 0px;
        border-radius: 4px;
        box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    }
    .company{
        padding-left: 10px;
        font-size: 22px;
    }
    .contact,.tel{
        padding-left: 10px;
        display: inline-block;
        width: 48%;
        line-height: 45px;
    }
    .crtTime,.option{
        display: inline-block;
    }
    .crtTime{
        float: right;
        padding: 0px 5px 0px 0px;
        font-size: 12px;
        line-height: 35px;
    }
    .option{
        padding: 0px 0px 0px 10px;
    }
    .conditions{
        height: 60px;
        background: #fff;
        line-height: 60px;
        padding-left: 10px;
    }
    .add{
        width: 100px;
        float: right;
    }
    .ivu-icon {
        margin-right: 5px;
        color: #ff9b00;
    }
    .allDiv{
        position:relative;  
        min-height: 100%;
        padding-bottom: 60px;
    }
</style>
