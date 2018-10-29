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
                    <Input v-model="conditions.contractName" placeholder="请输入合同名称" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <span>付款方式：</span>
                    <Select v-model="conditions.payment" style="width: 60%" class="payment">
                        <Option value="null">所有</Option>
                        <Option v-for="(item,index) in selectList.payType" :key="index" :value="item.val">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>合同状态：</span>
                    <Select v-model="conditions.contractStatus" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option v-for="(item,index) in selectList.contractStatus" :key="index" :value="item.val">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime">
                    </DatePicker>
                </Col>
                <Col span="6">
                    <span>结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime">
                    </DatePicker>
                </Col>
                 <Col span="6">
                    <Poptip placement="bottom" width="1000">
                       <span>客户名称：</span>
                       <Input v-model="customerName" placeholder="请选择客户" class="cusInput"></Input>
                       <div class="pop" slot="content">
                        <customer-choose @selectCustomer="getCustomerId" v-bind:customerId="conditions.customerId"></customer-choose>
                        </div>
                    </Poptip>
                </Col>
                <Col span="6">
                     <Button type="primary" @click="search" icon="ios-search">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Row>
                <Col span="6" v-for="(item,index) in contractList" :key="index">
                    <div class="contracts">
                        <div class="contractName">
                            <span>{{item.name}}</span>
                        </div>
                        <div class="second">
                        <div class="crtTime">
                            <Icon type="android-time"></Icon>
                            <span>{{item.crtTime}}</span>
                        </div>
                        <span class="companyName">{{ item.companyName }}</span>
                        </div>
                        <div class="contactInfo">
                            <div class="person">
                                <Icon type="android-person"></Icon>
                                <span>{{item.contact}}</span>
                            </div>
                            <div class="payAndStatus">
                               <!--  <Icon type="android-call"></Icon>
                                <span>{{item.tel}}</span> -->
                                <Icon type="ios-calendar"></Icon>
                                <span>{{ item.payType }}</span>
                                <span :class="[{'red': item.contractStatus === '过期'},'status']">{{ item.contractStatus }}</span>
                            </div>
                        </div>
                        <!-- <div class="cable">
                            <span class="cableInfo">{{ item.cable.cableName }}</span>
                            <span class="cableInfo">{{ item.cable.length }}</span>
                            <span class="cableInfo">{{ item.cable.cableStatus }}</span>
                        </div> -->
                        <div class="times">
                            <span>{{ item.contractStartTime + ' 至 ' + item.contractEndTime}}</span>
                        </div>
                        <div class="option">
                            <Tooltip content="详情">
                                <Button type="primary" size="small" @click="read(index)" icon="android-list"></Button>
                            </Tooltip>
                            <Tooltip content="编辑">
                                <Button type="primary" size="small" icon="edit" @click="edit(index)"></Button>
                            </Tooltip>
                            <Tooltip content="删除" class="del">
                                <Button type="error" size="small" @click="del(index)" icon="trash-a"></Button>
                            </Tooltip>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
        </Page>
    </div>
</template>

<script>
    import { EnumsService } from '../../../../services/enums'
    import { ContractService } from '../../../../services/contracts'
    import types from '../../../../../static/Enum.json'
    import CustomerChoose from '../../../../components/UM/OAM/CustomerChoose'
    import $ from 'jquery'
    export default {
        data(){
            return{
                page: {
                    pageNum: 1,
                    pageSize: 10,
                    pageTotal: 0
                },
                contractList: [
                    {
                        id: 0,
                        name: '测试合同',
                        companyName: '测试公司',
                        contact: '测试联系人',
                        tel: 13942184630,
                        payType: '月付',
                        contractStatus: '正常',
                        contractStartTime: '2018-08-10',
                        contractEndTime: '2018-10-02',
                        crtTime: '2018-08-03 10:00:00' 
                    }
                ],
                conditions:{
                    payment: null,
                    contractId:null,
                    contractName: null,
                    startTime: null,
                    endTime: null,
                    customerId: null,
                    contractStatus: null
                },
                selectList:{
                    payType:[],
                    contractStatus:[]
                }, 
                customer: [],
                customerName: '',  
                pageStyle: {
                    position: 'absolute',
                    bottom: '20px',
                    right: '15px'
                }
            }
        },
        components: { CustomerChoose },
        computed:{
            params() {
                let param = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    id: this.conditions.contractId ? this.conditions.contractId : null,
                    name: this.conditions.contractName,
                    startTime: new Date(this.conditions.startTime).getTime(),
                    endTime: new Date(this.conditions.endTime).getTime(),
                    customerId: this.conditions.customerId,
                    payType: this.conditions.payment,
                    contractStatus: this.conditions.contractStatus
                };
                return Object.assign({}, param);
            }
        },
        mounted(){
            this.initData()
            this.search()
            setTimeout(function(){
                let width = $('.payment').width()
                $('.cusInput').css('width',width+'px')
            },300)
            window.onresize = function(){
                let width = $('.payment').width()
                $('.cusInput').css('width',width+'px')
            }
        },
        methods:{
            initData() {
                let _this = this
                EnumsService.getPayType().then(
                    (result)=>{
                        _this.selectList.payType = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
                EnumsService.getContractStatus().then(
                    (result)=>{
                        _this.selectList.contractStatus = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
            },
            search: function(){
                if(!this.customerName && this.conditions.customerId){
                    this.conditions.customerId = null;
                }
                // console.log(this.conditions.contractId)
                let _this = this
                ContractService.contractDatagrid(_this.params).then(
                    (result)=>{
                        _this.contractList = [];
                        result.list.forEach(a=>{
                            _this.contractList.push({
                                id: a.id,
                                companyName: a.customer.company.name,
                                name: a.name,
                                contact: a.customer.contact,
                                tel: a.customer.tel,
                                payType: a.payTypeName,
                                contractStatus: a.contractStatusName,
                                contractStartTime: new Date(a.contractStartTime).format('yyyy-MM-dd'),
                                contractEndTime: new Date(a.contractEndTime).format('yyyy-MM-dd'),
                                crtTime: new Date(a.crtTime).format('yyyy-MM-dd hh:mm:s'),
                                // cable: {
                                //     cableName: null,
                                //     cableLength: null,
                                //     cableStatus: null
                                // }
                            })
                            // this.contractList.forEach(contract=>{
                            //     this.axios.get('contracts/' + contract.id).then(res =>{
                            //         let { code,data } = res.data;
                            //         contract.cable.cableName = data.cableDto.cableName;
                            //         contract.cable.length = data.cableDto.cableLength;
                            //         contract.cable.cableStatus = data.cableDto.cableStatusName;
                            //     })  
                            // })     
                        })
                    })
            },
            goToMoudle: function (path) {
                this.$router.push(path);
            },
            goMoudle: function (index,type) {
                if(type == 2){
                    this.$router.push({
                        name: 'UMEditContract',
                        params: {
                            id: this.contractList[index].id,
                            type: type
                        }
                    })
                }else if(type == 1){
                    this.$router.push({
                        name: 'UMDetailContract',
                        params: {
                            id: this.contractList[index].id,
                            type: type
                        }
                    })
                }

            },
            read(index){
                this.goMoudle(index,types.pageType.Read);
            },
            edit(index){
                this.goMoudle(index,types.pageType.Edit);
            },
            del(index) {
                 this.$Modal.confirm({
                    title: '确认',
                    content: '<p>确定删除吗？</p>',
                    onOk: () => {
                        let _this = this
                        ContractService.delelteContract(_this.contractList[index].id).then(
                            (result)=>{
                                _this.contractList.splice(_this.contractList[index].id, 1);
                                _this.search()
                            },
                            (error)=>{
                                _this.Log.info(error)
                            })
                    }
                });
            },
            handlePage(value) {
                this.page.pageNum = value
                this.search()
            },
            handlePageSize(value) {
                this.page.pageSize = value
                this.search()
            },
            getCustomerId(data) {
                this.conditions.customerId = data.id;
                this.customerName = data.name.toString();
            }
        }
    }
</script>

<style scoped>
.contact,.tel{
    padding-left: 10px;
    display: inline-block;
    line-height: 45px;
}
.crtTime{
    padding: 0px 0px 0px 6px;
    display: inline-block;
    font-size: 14px;
}
.list{
    margin-top: 10px;
    background-color: white;
    padding: 20px 0 24px 0;
}
.ivu-icon {
    margin-right: 2px;
    color: #ff9b00;
}
.pop{
    max-height:300px;
    overflow-y: auto;
}
.contracts{
    border: 1px solid #dddfe1;
    width: 80%;
    /* height: 120px;*/
    margin: 10px auto; 
    border-radius: 4px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    position: relative;
    /*background-color: rgba(94,147,165,0.6);*/
    background-image: url('../../../../assets/UM/contract2.jpg');
    background-size: 100% 100%;
}
.option{
    padding: 10px 0px 0px 4px;
}
.contractName{
    text-align: center;
    padding: 10px;
    font-size: 20px;
    font-weight: bold;
    /*color: #fff;*/
}
.second{
	height: 24px;
}
/*@media screen and (max-width:1592px){
    .second{
   		height: 40px;
    }
}*/
.companyName{
    font-size: 16px;
    float: right;
    margin-right: 10px;
    font-weight: bold;
}
.contactInfo{ 
	width: 100%;
	height: 30px;
	margin-top: 10px;
}
.person{
	padding-left: 6px;
    display: inline-block;
    vertical-align: middle;
}
.payAndStatus{
    float: right;
    margin-right: 10px;
    vertical-align: middle;
}
.times{
    text-align: center;
    font-size: 18px;
    padding: 4px;
   /* background-color: rgb(245,245,245);*/
}
/*.status{
    margin-left: 10px;
}*/
.red{
    color:rgb(255, 102, 0);;
}
.cable{
    text-align: center;
    background-color: rgb(245,245,245);
    padding: 4px 0 4px 0;
}
.cableInfo{
    padding:0 8px;
}
.ivu-poptip{
    width: 100%;
}
.cusInput{
    width: 60%;
}
.del{
    float: right;
}
</style>
