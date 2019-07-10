<template>
<div class="formBG">
    <div class="formTitle">添加入廊申请</div>
    <div class="formHeight">
    <Form ref="addEnterGalleryApplication" :model="addEnterGalleryApplication" :label-width="160" :rules="ruleValidate" @submit.native.prevent>
        <FormItem label="申请人所属公司：" prop="staffCompany">
            <Select v-model="addEnterGalleryApplication.staffCompany" @on-change="chooseDeptment()">
                <Option v-for="item in company" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </FormItem>
        <FormItem label="申请人所属部门：" prop="positionId" :disabled="this.addEnterGalleryApplication.staffCompany==null">
            <Select v-model="addEnterGalleryApplication.positionId" @on-change="chooseStaff()" >
                <Option v-for="item in positions" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
        </FormItem>
        <FormItem label="申请人：" prop="staffId"  :disabled="this.addEnterGalleryApplication.staffCompany==null||this.addEnterGalleryApplication.positionId==null">
            <Select v-model="addEnterGalleryApplication.staffId">
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
            <DatePicker type="datetime" placeholder="请选择计划入廊时间" v-model="addEnterGalleryApplication.preTime" style="width: 100%"></DatePicker>
        </FormItem>
        <FormItem label="访客总人数：" class="ivu-form-item-required">
            <InputNumber :min="1" v-model="addEnterGalleryApplication.visitorNumber" style="width: 100%;"></InputNumber>
        </FormItem>
        <FormItem label="访客所属公司" prop="visitorCompany">
            <Select v-model="addEnterGalleryApplication.visitorCompany">
                <Option v-for="item in company" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </FormItem>
        <FormItem label="访客信息：" class="required">
            <Poptip placement="top" style="width: 100%;">
                <Input v-model="addEnterGalleryApplication.visitorInfo" v-show="false"></Input>
                <Input v-model="addEnterGalleryApplication.visitorInfoNames" @on-blur="checkVisitorInfo"></Input>
                <div class="ivu-form-item-error-tip"  v-show="isVisitorNames">访客不能为空</div>
                <div class="pop" slot="content">
                    <outSiders v-on:childByValue="childByValue"></outSiders>
                </div>    
            </Poptip>    
        </FormItem>
        <div style="text-align: center">
            <Button type="ghost"  style="margin-right: 8px" @click="goBack()">返回</Button>
            <Button type="primary" @click="submitEnterGalleryApplication('addEnterGalleryApplication')" :disabled="isDisable">提交</Button>
        </div>
    </Form>
    </div>
</div>    
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import { EnumsService } from '../../../../services/enumsService'
import { EnterGalleryService } from '../../../../services/enterGalleryService'
import outSiders from '../../../../components/UM/OAM/outsiderChoose'

export default {
    components: {outSiders},
    data(){ 
        return{
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
                visitorInfoNames: null,
                approverId: null
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
                visitorInfoNames: [
                    {required: true, message: '请选择访客', trigger: 'blur'}
                ]
            },
            staffs:[],
            company:[],
            tunnels: [],
            action:[],
            positions:[],
            // index: 1,
            // items:[
            //     {
            //         index: 1,
            //         status: 1,
            //         name: null,
            //         idCard: null,
            //         tel: null
            //     }
            // ],
            // check:[
            //     {
            //         index: 1,
            //         status: 1,
            //         checkName: false,
            //         checkidCard: false,
            //         checkRightIdCard: false,
            //         checkTel: false,
            //         checkRightTel: false
            //     }
            // ],
            isDisable:false,
            isVisitorNames: false
        }
    },
    watch: {
        'addEnterGalleryApplication.visitorInfoNames': function(newVal, oldVal){
            if(newVal==''||newVal==null||newVal==undefined){
                this.isVisitorNames = true
            }else{
                this.isVisitorNames = false
            }
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
        submitEnterGalleryApplication(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                    this.checkVisitorInfo()
                    this.$refs[name].validate((valid) => {
                        if(valid){
                            let arr = []
                            let formInfo = {
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
                                }
                            )
                        }
                    })
            },2000)
        },
        chooseDeptment(){                                                                             
            if(this.addEnterGalleryApplication.staffCompany!=null){
                this.addEnterGalleryApplication.positionId = null
                let _this = this
                EnterGalleryService.getDepsByCompanyId(_this.addEnterGalleryApplication.staffCompany).then(
                    (result)=>{
                        _this.positions = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    }
                )
            }
        },
        chooseStaff(){
            if(this.addEnterGalleryApplication.positionId!=null||this.addEnterGalleryApplication.staffId!=null){
                this.addEnterGalleryApplication.staffId = null
                let _this = this
                EnterGalleryService.getStaffsByComIdAndDepId(_this.addEnterGalleryApplication.staffCompany,_this.addEnterGalleryApplication.positionId).then(
                    (result)=>{
                        _this.staffs = result
                    },
                    (error)=>{
                        _this.Log.info(error)
                    }
                )           
            }
        }, 
        //返回
        goBack(){
            this.$router.back(-1);
        },
        childByValue(childByValue){
            var arr = []
            var str = ''
            var names = ''
            arr = childByValue
            arr.forEach(temp => {
                str += temp.split(":")[0]+','
                names += temp.split(":")[1]+','
            })
            this.addEnterGalleryApplication.visitorInfoNames = names.substring(0, names.length-1)
            this.addEnterGalleryApplication.visitorInfo = str.substring(0,str.length-1)
        },
        //验证访客信息不能为空
        checkVisitorInfo(){
            if(this.addEnterGalleryApplication.visitorInfoNames==null||this.addEnterGalleryApplication.visitorInfoNames==''){
                this.isVisitorNames = true
            }else{
                this.isVisitorNames = false
            }
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 740px;
    margin: 20px auto;
    padding: 10px 20px;
}
ul li{
    list-style: none;
}
.ivu-poptip >>> .ivu-poptip-rel{
    width: 100%
}
.required >>> .ivu-form-item-label:before{
    content: '*';
    display: inline-block;
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 12px;
    color: #ed3f14;
}
.formBG{
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label,.formTitle{
    color: #fff;
}
.formTitle{
    font-size: 2.2vmin;
    margin-top: -3vh;
}
.formBG >>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before, .formBG .ivu-form>>>.ivu-form-item-label:before {
    color: #00fff6;
    content: '★';
    display: inline-block;
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}

@media (min-width: 2200px){
    .ivu-form.ivu-form-label-right{
        width: 50%;
    }
    h2{
        font-size: 2.4vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 16vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 16vmin !important;
        line-height: 5.5vmin;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .ivu-form-item-content{
        line-height: 6.5vmin;
    }
    .btn{
        margin-left: 20% !important;
    }
    .ivu-form-item-required .ivu-form-item-label:before{
        font-size: 1.6vmin !important;
    }
    .ivu-input-number,.ivu-input-number >>> .ivu-input-number-input{
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .ivu-input-number >>> .ivu-input-number-input-wrap{
        height: 3.2vmin;
    }
    .ivu-input-number >>> .ivu-input-number-handler-wrap{
        width: 2.2vmin;
    }
    .ivu-input-number >>> .ivu-input-number-handler{
        height: 1.6vmin;
    }
    .ivu-input-number >>> .ivu-input-number-handler-down-inner, .ivu-input-number >>> .ivu-input-number-handler-up-inner{
        width: 1.2vmin;
        height: 1.2vmin;
        line-height: 1.2vmin;
        font-size: 1.4vmin;
        right: 0.4vmin;
        transition: all .2s linear;
    }
    .outSiderBox{
        font-size: 1.4vmin;
    }
    .ivu-input {
        border: 0.1vmin solid #dddee1;
        border-radius: 0.4vmin;
        height: 3.2vmin;
        line-height: 3.2vmin;
        padding: 0.4vmin 0.7vmin;
    }
}
</style>
