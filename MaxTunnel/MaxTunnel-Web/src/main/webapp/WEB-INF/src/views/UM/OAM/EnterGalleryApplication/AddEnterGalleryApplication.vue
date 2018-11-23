<template>
<div :style="backStyle">
    <Form ref="addEnterGalleryApplication" :model="addEnterGalleryApplication" :label-width="160" :rules="ruleValidate" @submit.native.prevent>
        <h2 class="formTitle">添加入廊申请</h2>
        <FormItem label="申请人所属公司：" prop="staffCompany">
            <Select v-model="addEnterGalleryApplication.staffCompany" @on-change="chooseDeptment()">
                <Option v-for="item in company" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </FormItem>
        <FormItem label="申请人所属部门：" prop="positionId">
            <Select v-model="addEnterGalleryApplication.positionId" @on-change="chooseStaff()"  :disabled="this.addEnterGalleryApplication.staffCompany==null">
                <Option v-for="item in positions" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
        </FormItem>
        <FormItem label="申请人：" prop="staffId">
            <Select v-model="addEnterGalleryApplication.staffId"  :disabled="this.addEnterGalleryApplication.staffCompany==null||this.addEnterGalleryApplication.positionId==null">
                <Option v-for="(item) in staffs" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
            <!-- <Input type="text" v-model="addEnterGalleryApplication.staffId" readonly></Input> -->
        </FormItem>
        <FormItem label="审批人：" prop="approverId" class="ivu-form-item-required">
            <Input v-model="addEnterGalleryApplication.approverId" readonly style="display: none"></Input>
            <Input v-model="approver.name" readonly></Input>
        </FormItem>
        <FormItem label="管廊：" prop="tunnelId">
            <Select v-model="addEnterGalleryApplication.tunnelId">
                <Option v-for="(item,index) in tunnels" :key="index" :value="item.id">{{item.name}}</Option>
            </Select>
        </FormItem>
        <FormItem label="入廊目的：" prop="action">
            <Select v-model="addEnterGalleryApplication.action">
                <Option v-for="(item,index) in action" :key="index" :value="item.val">{{item.key}}</Option>
            </Select>
        </FormItem>
        <FormItem label="计划入廊时间：" prop="preTime">
            <DatePicker type="datetime" placeholder="请选择计划入廊时间" v-model="addEnterGalleryApplication.preTime" style="width: 540px"></DatePicker>
        </FormItem>
        <FormItem label="访客总人数：" class="ivu-form-item-required">
            <InputNumber  :min="1" v-model="addEnterGalleryApplication.visitorNumber" style="width: 540px;"></InputNumber>
        </FormItem>
        <FormItem label="访客所属公司" prop="visitorCompany">
            <Select v-model="addEnterGalleryApplication.visitorCompany">
                <Option v-for="item in company" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </FormItem>
        <FormItem label="访客详细信息："
            v-for="(item, index) in items"
                v-if="item.status"
                :key="index"
                :prop="item.name+','+item.idCard+','+item.tel"
                class="ivu-form-item-required">
            <Row>
                <Col span="6">
                    <Input type="text" v-model="item.name" placeholder="请输入访客姓名" @on-blur="validateName(index)"></Input>
                    <div class="ivu-form-item-error-tip"  v-show="check[index].checkName">访客姓名不能为空</div>
                </Col>
                <Col span="6">
                    <Input type="text" v-model="item.idCard" placeholder="请输入身份证号" @on-blur="validateIdCard(index)"></Input>
                    <div class="ivu-form-item-error-tip" v-show="check[index].checkidCard">身份证号不能为空</div>
                    <div class="ivu-form-item-error-tip" v-show="check[index].checkRightIdCard">请输入正确的身份证号</div>
                </Col>
                <Col span="6">
                    <Input type="text" v-model="item.tel" placeholder="请输入联系方式" @on-blur="validateTel(index)"></Input>
                    <div class="ivu-form-item-error-tip" v-show="check[index].checkTel">联系方式不能为空</div>
                    <div class="ivu-form-item-error-tip" v-show="check[index].checkRightTel">请输入正确的联系方式</div>
                </Col>
                <Col span="4" offset="1">
                    <Button type="ghost" @click="handleRemove(index)">删除</Button>
                </Col>
            </Row>
        </FormItem>
        <FormItem>
            <Row>
                <Col span="12">
                    <Button type="dashed" long @click="handleAdd" icon="plus-round">添加访客信息</Button>
                </Col>
            </Row>
        </FormItem>
        <div style="text-align: center">
            <Button type="primary" @click="submitEnterGalleryApplication('addEnterGalleryApplication')" :disabled="isDisable">提交</Button>
            <Button type="ghost"  style="margin-left: 8px" @click="handleReset('addEnterGalleryApplication')">取消</Button>
        </div>
    </Form>
</div>    
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import { EnumsService } from '../../../../services/enumsService'
import { EnterGalleryService } from '../../../../services/enterGalleryService'

export default {
    data(){ 
        return{
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            },
            approver:{},
            addEnterGalleryApplication:{
                staffCompany: null,
                positionId: null,
                staffId: null,
                tunnelId: null,
                action: null,
                preTime: null,
                visitorNumber: 1,
                visitorCompany: null,
                visitorInfo: null,
                approverId: null,  
                remark: ''
            },
            ruleValidate:{
                staffCompany:[
                    {type: 'number', required: true, message: '请选择所属公司', trigger: 'change'}
                ],
                positionId:[
                    {type: 'number',required: true, message: '请选择所属部门', trigger: 'change'}
                ],
                staffId:[
                    {type: 'number',required: true, message: '请选择申请人', trigger: 'change'}
                ],
                tunnelId:[
                    {type: 'number',required: true, message: '请选择管廊', trigger: 'change'}
                ],
                action:[
                    {type: 'number',required: true, message: '请选择入廊目的', trigger: 'change'},
                ],
                preTime:[
                    {required: true, type: 'date', message: '请选择计划入廊时间', trigger: 'change'}
                ],
                visitorCompany:[
                    {type: 'number',required: true, message: '请选择访客所属公司', trigger: 'change'}
                ],
            },
            staffs:[{id:1,name:'张三'}],
            company:[{id:1, name: '波汇科技'}],
            tunnels: [{id: 1,name: '凤岭北路'}],
            action:[{id: 1,name: '维修'}],
            positions:[{id:1,partName:'技术部'}],
            index: 1,
            items:[
                {
                    index: 1,
                    status: 1,
                    name: null,
                    idCard: null,
                    tel: null
                }
            ],
            check:[
                {
                    index: 1,
                    status: 1,
                    checkName: false,
                    checkidCard: false,
                    checkRightIdCard: false,
                    checkTel: false,
                    checkRightTel: false
                }
            ],
            checkStatus: false,
            isDisable:false
        }
    },
    mounted(){
        let _this = this
        EnterGalleryService.getCompanys().then(
            (result)=>{
                _this.company = result
            },
            (error)=>{
                 _this.Log.info(error)
            })
        
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                _this.Log.info(error)
            })

        EnumsService.getEnterActionType().then(
            (result)=>{
                _this.action = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        this.axios.get('roles/users').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.approver = data[0]
                this.addEnterGalleryApplication.approverId = data[0].id
            }
        })
    },
    methods:{
        handleAdd () {
            this.index++;
            this.items.push({
                index: this.index,
                status: 1,
                name:'',
                idCard:'',
                tel:''
            });
            this.check.push({
                index: this.index,
                status: 1,
                checkName: false,
                checkidCard: false,
                checkRightIdCard: false,
                checkTel: false,
                checkRightTel: false
            })
        },
        handleRemove (index) {
            this.items[index].status = 0;
            this.check[index].status = 0;
        },
        submitEnterGalleryApplication(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
            for(let a in this.items){
                this.validateName(a);
                this.validateIdCard(a);
                this.validateTel(a); 
                if(this.check[a].checkName&&this.check[a].checkidCard&&this.check[a].checkTel&&this.check[a].checkRightIdCard&&this.check[a].checkRightTel){
                    this.checkStatus = false;
                }else{
                    this.checkStatus = true;
                }
            }
            this.$refs[name].validate((valid) => {
                if(valid){
                    if(this.checkStatus == true){
                        var arr = new Array()
                        for(let index in this.items){
                            if(this.items[index].status==1){
                                arr.push(this.items[index].name+"-"+this.items[index].idCard+"-"+this.items[index].tel)
                            }
                        }
                        this.addEnterGalleryApplication.visitorInfo = arr.toString()
                        var formInfo = {
                            staffId: parseInt(this.addEnterGalleryApplication.staffId),
                            approverId: parseInt(this.addEnterGalleryApplication.approverId),
                            tunnelId: parseInt(this.addEnterGalleryApplication.tunnelId),
                            action: parseInt(this.addEnterGalleryApplication.action),
                            preTime: this.addEnterGalleryApplication.preTime,
                            visitorNumber: this.addEnterGalleryApplication.visitorNumber,
                            visitorCompany: parseInt(this.addEnterGalleryApplication.visitorCompany),
                            visitorInfo: this.addEnterGalleryApplication.visitorInfo
                        }
                        let _this = this
                        EnterGalleryService.addEnterGalleryApplication(formInfo).then((result)=>{
                                _this.$router.push("/UM/enterGalleryApplication/query");
                            },
                            (error)=>{
                                _this.Log.info(error)
                            })
                    }
                }else{
                    this.$Message.error('请输入正确的申请信息');
                }
            })
            },2000)
        },
        handleReset(name){
            this.$refs[name].resetFields()
        },
        chooseDeptment(){                                                                                 
            if(this.addEnterGalleryApplication.staffCompany!=null){
                let _this = this
                EnterGalleryService.getDepsByCompanyId(_this.addEnterGalleryApplication.staffCompany).then(
                    (result)=>{
                        _this.positions = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
                // this.axios.get('/companies/'+this.addEnterGalleryApplication.staffCompany+'/departments').then(response=>{
                //     let{ code,data } = response.data
                //     if(code==200){
                //         this.positions = data;
                //     }
                // })
            }
        },
        chooseStaff(){
            if(this.addEnterGalleryApplication.positionId!=null||this.addEnterGalleryApplication.staffId!=null){
                let _this = this
                EnterGalleryService.getStaffsByComIdAndDepId(_this.addEnterGalleryApplication.staffCompany,_this.addEnterGalleryApplication.positionId).then(
                    (result)=>{
                        _this.staffs = data
                    },
                    (error)=>{
                        _this.Log.info(error)
                    })
                // this.axios.get('/companies/'+this.addEnterGalleryApplication.staffCompany+'/departments/'+this.addEnterGalleryApplication.positionId+'/staffs').then(response=>{
                //     let{ code,data } =response.data
                //     if(code==200){
                //         this.staffs = data
                //     }
                // })
            }
        }, 
        validateName(index){
            if(this.items[index].name==null||this.items[index].name==undefined||this.items[index].name==""){
                this.check[index].checkName = true;
            }else{
                this.check[index].checkName = false;
            }
        },
        validateIdCard(index){
            if(this.items[index].idCard==null||this.items[index].idCard==undefined||this.items[index].idCard==""){
                this.check[index].checkidCard = true;
            }else{
                this.check[index].checkidCard = false;
                var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
                if(reg.test(this.items[index].idCard) === false) {
                    this.check[index].checkRightIdCard = true
                }else{
                    this.check[index].checkRightIdCard = false
                }
            }
        },
        validateTel(index){
            if(this.items[index].tel==null||this.items[index].tel==undefined||this.items[index].tel==""){
                this.check[index].checkTel = true;
            }else{
                this.check[index].checkTel = false;
                var reg = /^1[0-9]{10}$/;
                if(reg.test(this.items[index].tel)===false){
                   this.check[index].checkRightTel = true; 
                }else{
                    this.check[index].checkRightTel = false;
                }
            }
        },
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 740px;
    margin: 20px auto;
    background: #fff;
    padding: 10px 20px;
}
ul li{
    list-style: none;
}
</style>
