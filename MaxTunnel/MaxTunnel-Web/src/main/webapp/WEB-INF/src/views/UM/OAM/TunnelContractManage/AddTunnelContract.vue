<template>
  <div :style="backStyle">
    <Form ref="addContractInfo" :model="addContractInfo"  :rules="ruleValidate" :label-width="140">
        <h2 v-if="pageType!=pageTypes.Edit && pageType!=pageTypes.Read">添加合同信息</h2>
        <h2 v-if="pageType==pageTypes.Edit">修改合同信息</h2>
        <h2 v-if="pageType==pageTypes.Read">合同信息详情</h2>
        <FormItem label="合同名称：" prop="name">
            <Input v-model="addContractInfo.name" placeholder="请输入合同名称" v-if="!read"></Input>
            <span v-if="read">{{ addContractInfo.name }}</span>
        </FormItem>
         <FormItem label="合同开始日期：" prop="contractStartTime">
            <DatePicker type="datetime" placeholder="请选择合同开始日期" v-model="addContractInfo.contractStartTime" style="width:460px" v-if="!read"></DatePicker>
            <span v-if="read">{{ new Date(addContractInfo.contractStartTime).format('yyyy-MM-dd hh-mm-s') }}</span>
        </FormItem>
         <FormItem label="合同结束日期：" :prop="read ? null : 'contractEndTime'"> 
            <DatePicker type="datetime" placeholder="请选择合同结束日期" v-model="addContractInfo.contractEndTime" style="width: 460px" v-if="!read"></DatePicker>
            <span v-if="read">{{ new Date(addContractInfo.contractEndTime).format('yyyy-MM-dd hh-mm-s') }}</span>
        </FormItem>
        <FormItem label="付款方式：" prop="payType">
            <Select v-model="addContractInfo.payType" v-if="!read">
                <Option v-for="(item,index) in payTypes" :value="item.val.toString()" :key="index">{{ item.key }}</Option>
            </Select>
            <span v-if="read && item.val == addContractInfo.payType" v-for="(item,index) in payTypes" :key="index">{{ item.key }}</span>
        </FormItem>
        <FormItem label="合同状态：" v-if="pageType==pageTypes.Edit || read">
            <Select v-model="addContractInfo.contractStatus" v-if="!read">
                <Option v-for="(item,index) in contractStatus" :value="item.val.toString()" :key="index">{{ item.key }}</Option>
            </Select>
            <span v-if="read && item.val == addContractInfo.contractStatus" v-for="(item,index) in contractStatus" :key="index">{{ item.key }}</span>
        </FormItem>
        <FormItem label="管线名称：" prop="cableName">
            <Input v-model="addContractInfo.cableName" placeholder="请输入管线名称" v-if="!read"></Input>
            <span v-if="read">{{ addContractInfo.cableName }}</span>
        </FormItem>
        <FormItem label="管线长度：" prop="cableLength">
            <Input v-model="addContractInfo.cableLength" placeholder="请输入管线长度" v-if="!read"></Input>
            <span v-if="read">{{ addContractInfo.cableLength }}</span>
        </FormItem>
        <FormItem label="管线状态：" v-if="pageType==pageTypes.Edit || read">
            <Select v-model="addContractInfo.cableStatus" v-if="!read">
                <Option v-for="(item,index) in cableStatus" :value="item.val.toString()" :key="index">{{ item.key }}</Option>
            </Select>
            <span v-if="read && item.val == addContractInfo.cableStatus" v-for="(item,index) in cableStatus" :key="index">{{ item.key }}</span>
        </FormItem>
        <FormItem label="客户：" :prop="read ? null : 'customerId'">
            <Poptip placement="top" width="1000">
                 <Input v-model="customerName" placeholder="请选择客户" style="width:460px" v-if="!read"></Input>
                <div class="pop" slot="content" v-show="pageType != pageTypes.Read">
                    <customer-choose @selectCustomer="getCustomerId" v-bind:customerId="addContractInfo.customerId"></customer-choose>
                </div>
            </Poptip>
            <span v-if="read">{{ customerName }}</span>
        </FormItem>
        <!-- <FormItem label="管线位置：" prop="sectionIds">
            <Poptip placement="top" width="600">
                 <Input v-model="sectionNames" placeholder="请选择管线位置" style="width:380px"></Input>
                 <div class="sections" slot="content">
                    <Tree :data="tunnels" ref="tree" show-checkbox multiple @on-check-change="getSelected"></Tree>
                 </div>
            </Poptip>
        </FormItem> -->
        <FormItem label="管线位置：">
            <Col span="8">
                <Select v-model="addContractInfo.tunnelId" v-show="!read" @on-change="tunnelChange">
                  <!--   <Option disabled value="0">管廊</Option> -->
                    <Option v-for="item in cableLocation.tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
                <span v-if="read && item.id == addContractInfo.tunnelId" v-for="(item,index) in cableLocation.tunnels" :key="index">{{ item.name }}</span>
                <div class="ivu-form-item-error-tip" v-if="checkCable.tunnel">请选择管廊</div>
            </Col>
            <Col span="8">
                <Select v-model="addContractInfo.storeId" @on-change="check('store')" v-if="!read">
                    <Option v-for="(item,index) in cableLocation.stores" :value="item.id" :key="index">{{ item.name }}</Option>
                </Select>
                <span v-if="read && item.id == addContractInfo.storeId" v-for="item in cableLocation.stores" :key="item.id">{{ item.name }}</span>
                <div class="ivu-form-item-error-tip" v-if="checkCable.store">请选择仓</div>
            </Col>
            <Col span="8">
               <Select multiple v-model="addContractInfo.areaIds" @on-change="check('area')" v-if="!read">
                    <Option v-for="(item,index) in cableLocation.areas" :value="item.id" :key="index">{{ item.name }}</Option>
                </Select>
                <p v-if="read && addContractInfo.areaIds.indexOf(item.id) != -1" v-for="(item,index) in cableLocation.areas" :key="index">{{ item.name }}</p>
                <div class="ivu-form-item-error-tip" v-if="checkCable.area">请选择区域</div>
            </Col>
        </FormItem>
        <div style="text-align: center">
        <Button type="primary" @click="submitAddContractInfo('addContractInfo')" v-if="pageType!=pageTypes.Edit && pageType != pageTypes.Read" :disabled="isDisable">提交</Button>
        <Button type="primary" @click="submitEditContractInfo('addContractInfo')" v-if="pageType==pageTypes.Edit" :disabled="isDisable">更新</Button>
        <Button type="ghost"  @click="handleReset('addContractInfo')" style="margin-left: 8px" v-if="pageType != pageTypes.Read">取消</Button>
        <Button type="ghost"  @click="handleReset('addContractInfo')" style="margin-left: 8px" v-if="read">返回</Button>
        </div>
    </Form>

  </div>
</template>
<script>
import { EnumsService } from '../../../../services/enumsService'
import { TunnelService } from '../../../../services/tunnelService'
import { ContractService } from '../../../../services/contractService'
import types from '../../../../../static/Enum.json'
import CustomerChoose from '../../../../components/UM/OAM/CustomerChoose'
export default {
    data(){
        return{
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                height: '100%'
            },
            pageType: 3,
            pageTypes: types.pageType,
            addContractInfo:{
                name: null,
                contractStartTime: null,
                contractEndTime: null,
                payType: null,
                customerId: null,
                cableName: null,
                cableLength: null,
                areaIds:[],
                storeId: null,
                id: null,
                cableId: null,
                contractStatus: null,
                cableStatus: null,
                tunnelId: null
            },
            payTypes:[],
            customers:[],
            ruleValidate: {
                name: [
                    { required: true, message: '请填写合同名称', trigger: 'blur' }
                ],
                contractStartTime: [
                    { required: true, type: 'date', message: '请选择合同开始日期', trigger: 'change' }
                ],
                contractEndTime: [
                    { required: true, type: 'date', message: '请选择合同结束日期', trigger: 'change' }
                ],
                payType: [
                    { required: true, message: '请选择付款方式', trigger: 'change' }
                ],
                customerId: [
                    { required: true, message: '请选择客户'}
                     // { validator: validateCusIdCheck, trigger: 'blur' }
                ],
                cableName: [
                    { required: true, message: '请填写管线名称', trigger: 'blur' }
                ],
                cableLength: [
                    { required: true, message: '请填写管线长度', trigger: 'blur' }
                ]
            },
            validateTelRules: true,
            isDisable:false,
            customerName: '',
            sectionNames: '',
            tunnels:[],
            areas:[],
            contractStatus:[],
            cableLocation:{
                tunnels:[],
                areas:[],
                stores:[]
            },
            cableStatus: [],
            checkCable:{
                tunnel: false,
                area: false,
                store: false
            },
            read: false
        }
    },
    components: { CustomerChoose },
    mounted(){
        this.addContractInfo.id = this.$route.params.id
        this.pageType = this.$route.params.type
        if(this.pageType == this.pageTypes.Read){
            this.read = true
        }
        this.getInitData()
        this.getParams()
    },
    watch: {
      '$route': function () {
            this.pageType = 3;
            this.addContractInfo = {
                name: null,
                contractStartTime: null,
                contractEndTime: null,
                payType: ' ',
                customerId: null,
                cableName: null,
                cableLength: null,
                areaIds:[],
                storeId: null,
                tunnelId: null
            }
            this.customerName = null;
            this.checkCable.store = false;
            this.checkCable.area = false;
            this.read = false;
        },
        // 'addContractInfo.tunnelId': function(){
            
        // }
    },
    methods:{
        getInitData() {
            let _this = this
            EnumsService.getPayType().then(
                (result)=>{
                    _this.payTypes = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            TunnelService.getTunnels().then(
                (result)=>{
                    _this.cableLocation.tunnels = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
        },
        submitAddContractInfo(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid)=>{
                  if(valid){
                    if(this.validateTelRules==true){
                        let _this = this
                        ContractService.addContract(_this.addContractInfo).then(
                            (result)=>{
                                _this.Log.info('success')
                            },
                            (error)=>{
                                _this.Log.info(error)
                            })
                      this.$router.push("/UM/tunnelContract/list");
                    }
                  }else{
                    this.$Message.error("请填写正确的客户信息")
                  }
                })
            },2000)

        },
        getParams(){
            if(this.pageType == this.pageTypes.Edit || this.pageType == this.pageTypes.Read){
                let _this = this
                EnumsService.getContractStatus().then(
                    (result)=>{
                        _this.contractStatus = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
                EnumsService.getCableStatus().then(
                    (result)=>{
                        _this.cableStatus = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
                ContractService.getDetailsByContractId(_this.addContractInfo.id).then((result)=>{
                        _this.addContractInfo.name = result.cableDto.contract.name;
                        _this.addContractInfo.contractStartTime = new Date(result.cableDto.contract.contractStartTime);
                        _this.addContractInfo.contractEndTime = new Date(result.cableDto.contract.contractEndTime);
                        _this.addContractInfo.payType = result.cableDto.contract.payType.toString();
                        _this.addContractInfo.customerId = result.cableDto.contract.customer.id;
                        _this.addContractInfo.cableName = result.cableDto.cableName;
                        _this.addContractInfo.cableLength = result.cableDto.cableLength.toString();
                        // _this.addContractInfo.sectionIds = result.sectionIds;
                        _this.customerName = result.cableDto.contract.customer.contact.toString();
                        _this.addContractInfo.cableStatus = result.cableDto.cableStatus.toString();
                        _this.addContractInfo.contractStatus = result.cableDto.contract.contractStatus.toString();
                        _this.addContractInfo.cableId = result.cableDto.id;
                        _this.addContractInfo.tunnelId = result.tunnelId;
                        _this.getStoreAndAreas()
                        _this.addContractInfo.areaIds = result.areaIds;
                        _this.addContractInfo.storeId = result.storeId;
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
            }
        },
        submitEditContractInfo(name){
            this.isDisable = true
            setTimeout(()=>{
                this.$refs[name].validate((valid)=>{
                if(valid){
                  if(this.validateTelRules==true){
                    let _this = this
                    ContractService.updateContract(_this.addContractInfo).then(
                        (result)=>{
                            _this.Log.info('success')
                        },
                        (error)=>{
                            _this.Log.info(error)
                        })
                    this.$router.push("/UM/tunnelContract/list");
                  }
                }else{
                  this.$Message.error("请填写正确的客户信息")
                }
              })
            },2000)
        },
        handleReset(name){
            if(this.read ||　this.pageType == this.pageTypes.Edit){
                this.$router.push("/UM/tunnelContract/list")
            }else{
                this.$refs[name].resetFields()
                this.customerName = '';
                this.sectionNames = '';
            }
        },
        getCustomerId(data){
            this.addContractInfo.customerId = data.id;
            this.customerName = data.name.toString();
        },
        check(type){
            if(type == 'store'){
                if(!this.addContractInfo.storeId){
                    this.checkCable.store = true
                }else{
                    this.checkCable.store = false
                }
            }else if(type == 'area'){
                if(!this.addContractInfo.storeId){
                    this.checkCable.store = true
                }else{
                    this.checkCable.store = false
                }
                if(!this.addContractInfo.areaIds[0]){
                    this.checkCable.area = true
                }else{
                    this.checkCable.area = false
                }
            }
        },
        tunnelChange(){
            if(!this.addContractInfo.tunnelId){
                this.checkCable.tunnel = false;
            }else{
                this.addContractInfo.storeId = null
                this.addContractInfo.areaIds = null
                this.getStoreAndAreas()
            }   
        },
        getStoreAndAreas(){
            let _this = this
            TunnelService.getStoresByTunnelId(_this.addContractInfo.tunnelId).then(
                (result)=>{
                    _this.cableLocation.stores = result
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
            TunnelService.getAreasByTunnelId(_this.addContractInfo.tunnelId).then(
                (result)=>{
                    _this.cableLocation.areas = result
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 640px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
}
h2{
    text-align: center;
    margin-bottom: 20px;
}
.pop{
    max-height:300px;
    overflow-y: auto;
}
</style>
