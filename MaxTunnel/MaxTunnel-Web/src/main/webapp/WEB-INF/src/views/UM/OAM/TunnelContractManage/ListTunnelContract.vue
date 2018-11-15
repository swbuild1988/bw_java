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
                    <Select v-model="conditions.payment" style="width: 60%" id="payment">
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
                       <Input v-model="customerName" placeholder="请选择客户" id="cusInput"></Input>
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
                        <!-- <div class="tags"></div> -->
                        <div class="contractName" @click="edit(index)">
                            <span>{{item.name}}</span>
                        </div>
                        <div class="contactInfo">
                            <div class="item">
                                <div class="title">公司：</div>
                                <div class="info">{{ item.companyName }}</div>
                            </div>
                            <div class="item">
                                <div class="title">联系人：</div>
                                <div class="info">{{ item.contact }}</div>
                            </div>
                            <div class="item">
                                <div class="title">合同状态：</div>
                                <div :class="['info',{'red': item.contractStatus === '过期'}]">{{ item.contractStatus }}</div>
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
                        <!-- <div class="option">
                            <Tooltip content="详情">
                                <div class="buttons">
                                    <Icon type="android-list" @click="read(index)" size="26" color="rgb(198,206,230)"></Icon>
                                </div>
                            </Tooltip>
                            <Tooltip content="编辑">
                                <div class="buttons">
                                    <Icon type="edit" @click="edit(index)" size="26" color="rgb(198,206,230)"></Icon>
                                </div>
                            </Tooltip>
                        </div> -->
                        <Tooltip content="删除" class="del">
                            <Icon type="trash-a" @click="del(index)" size="26" color="rgb(162, 77, 72)"></Icon>
                        </Tooltip>
                    </div>
                </Col>
            </Row>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
        </Page>
    </div>
</template>

<script>
    import { EnumsService } from '../../../../services/enumsService'
    import { ContractService } from '../../../../services/contractService'
    import types from '../../../../../static/Enum.json'
    import CustomerChoose from '../../../../components/UM/OAM/CustomerChoose'
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
                let width = document.getElementById('payment').offsetWidth
                document.getElementById('cusInput').style.width = width + 'px'
            },300)
            window.onresize = function(){
                let width = document.getElementById('payment').offsetWidth
                document.getElementById('cusInput').style.width = width + 'px'
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
.list{
    margin-top: 10px;
    background-color: white;
    padding: 20px 0 24px 0;
}
.pop{
    max-height:300px;
    overflow-y: auto;
}
.contracts{
   /* border: 1px solid #dddfe1;*/
    width: 90%;
    /*height: 30vh;*/
    margin: 10px auto; 
    border-radius: 4px;
    position: relative;
    /*background: -webkit-linear-gradient(left top, rgb(91,95,148) , rgb(31,37,69)); 
    background: -o-linear-gradient(bottom right, rgb(91,95,148) , rgb(31,37,69)); 
    background: -moz-linear-gradient(bottom right, rgb(91,95,148) , rgb(31,37,69));
    background: linear-gradient(to bottom right, rgb(91,95,148) , rgb(31,37,69)); */
    background-image: url('../../../../assets/UM/border2.png');
    background-size: 100% 100%;
}
.option{
    position: absolute;
    top: 14px;
    right: 14px;
}
.contractName{
    text-align: center;
    padding: 20px 0 10px 0;
    font-size: 20px;
    font-weight: bold;
    color: rgb(252,252,255);
    cursor: pointer;
}
.contactInfo{ 
    padding-bottom: 30px;
}
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
#cusInput{
    width: 60%;
}
.del{
    position: absolute;
    bottom: 18px;
    right: 22px;
    cursor: pointer;
}
.buttons{
   /* width: 26px;
    height: 26px;
    background-color: rgb(86,149,185);
    border-radius: 50%;
    text-align: center;
    padding: 4px;*/
    cursor: pointer;
}
.item{
    margin: 10px;
}
.title{
    color: rgb(223,223,241);
    display: inline-block;
    font-size: 16px;
    width: 38%;
    text-align: right;
}
.info{
    display: inline-block;
    color: rgb(234,234,243);
    font-size: 16px;
    width: 60%;
}
.tags{
    width: 4px;
    height: 80px;
    position: absolute;
    top: 6px;
    left: 0;
    background: -webkit-linear-gradient(rgb(40,203,245) , rgb(21,240,182)); 
    background: -o-linear-gradient(rgb(40,203,245) , rgb(21,240,182)); 
    background: -moz-linear-gradient(rgb(40,203,245) , rgb(21,240,182));
    background: linear-gradient(rgb(40,203,245) , rgb(21,240,182)); 
}

</style>
