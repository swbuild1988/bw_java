<template>
    <div class="formBG">
        <Form :model="defectDetails" ref="defectDetails" :label-width="100" :rules="ruleValidate" @submit.native.prevent>
            <h2 class="formTitle">指派维修工单</h2>
            <FormItem label="所属管廊：">
                <Select v-model="defectDetails.tunnelId" disabled>
                    <Option v-for="(item,index) in tunnel" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属区段：">
                <Select v-model="defectDetails.area.id" disabled>
                    <Option v-for="(item,index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属管仓：">
                <Select v-model="defectDetails.store.id" disabled>
                    <Option v-for="(item,index) in stores" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷名称：">
                <Input v-model="defectDetails.name" type="text" placeholder="请输入缺陷名称" readonly></Input>
            </FormItem>
            <FormItem label="发现时间：">
                <DatePicker type="datetime" v-model="defectDetails.createTime" placeholder="请输入发现时间" style="width: 100%" readonly></DatePicker>
            </FormItem>
            <FormItem label="缺陷类型：">
                <Select v-model="defectDetails.type" disabled>
                    <Option v-for="(item,index) in type" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="对象名：" v-show="defectDetails.type==2">
                <Select v-model="defectDetails.objectId" disabled>
                    <Option v-for="(item,index) in objs" :key="index" :value="item.key">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="危险等级：">
                <Select v-model="defectDetails.level" disabled>
                    <Option v-for="(item,index) in level" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷状态：">
                <Select v-model="defectDetails.status" disabled>
                    <Option v-for="(item,index) in status" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷描述：">
                <Input v-model="defectDetails.description" type="textarea" :rows="4" placeholder="请输入缺陷描述" readonly></Input>
            </FormItem>
            <FormItem label="责任人：" prop="manId">
                <Select v-model="defectDetails.manId">
                    <Option v-for="(item,index) in liable" :value="item.id" :key="index">{{item.name}}{{item.accountId}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备注：">
                <Input v-model="defectDetails.remark" type="textarea" :rows="4" placeholder="请输入备注"></Input>
            </FormItem>
            <FormItem style="text-align: center;margin-left: -100px;">
                <Button type="ghost" @click="goBack()">返回</Button>
                <Button type="primary" style="margin-left: 8px" @click="submitForm('defectDetails')" :disabled="isSubmit">提交</Button>
            </FormItem>
        </Form> 
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import { DefectService } from '../../../../services/defectService'
import { EnumsService } from '../../../../services/enumsService'    
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            defectDetails:{
                id: null,
                tunnelId: null,
                area:{
                    id: null,
                    name: null
                },
                store:{
                    id: null,
                    name: null
                },
                name: '',
                createTime: null,
                type: null,
                objectId: null,
                level: null,
                status: null,
                description: null,
                manId: null,
                remark: null,
                tunnelName: null
            },
            ruleValidate:{
                manId:[
                    {type: 'number', required: true, message: '责任人不能为空', trigger: 'change'}
                ],
            },
            tunnel: [],
            type: [],
            level: [],
            status: [],
            liable:[],
            areas:[],
            stores:[],
            objs:[],
            processInstanceId: null,
            typeKey: null,
            isSubmit: false
        }    
    },
    watch:{
        '$route':function(){
            this.processInstanceId=this.$route.params.id;
        }
    },

    mounted(){
        this.processInstanceId =  this.$route.params.id;
        this.pageType = this.$route.params.type;
        this.typeKey = this.$route.params.typeKey;
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnel = result
            },
            (error)=>{
                _this.Log.info(error)
            })

        
        //缺陷类型
        EnumsService.getDefectType().then(
            (result)=>{
                _this.type = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //缺陷状态
        EnumsService.getDefectStatus().then(
            (result)=>{
                _this.status = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //危险等级
        EnumsService.getDefectLevel().then(
            (result)=>{
                _this.level = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //获取工单详情
        DefectService.getDetailsBySheetId(this.processInstanceId).then(
            (result)=>{
                _this.defectDetails = result
                _this.defectDetails.createTime = new Date(result.createTime).format('yyyy-MM-dd hh:mm:s')
                _this.getAreas()
                _this.getStores()
            }
        )
        this.getStaff()
    },
    methods: {
        //获取所属区域
        getAreas(){
            let _this = this
            TunnelService.getAreasByTunnelId(this.defectDetails.tunnelId).then(
                (result)=>{
                    _this.areas = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            this.getObj()
        },

        //获取所属仓段
        getStores(){
            let _this = this
            TunnelService.getStoresByTunnelId(this.defectDetails.tunnelId).then(
                (result)=>{
                    _this.stores = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            this.getObj()
        },

        //获取对象名    
        getObj(){
            this.objs = []
            let mes={
                    areaId : this.defectDetails.area.id,
                    storeId : this.defectDetails.store.id
                }
            let _this = this
            DefectService.getObj(mes).then(
                (result)=>{
                    _this.objs = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
        },
        //提交事件
        submitForm(name){
            this.isSubmit = true
            setTimeout(()=>{
                this.isSubmit = false
                this.$refs[name].validate(valid=>{
                if(valid){
                    var formInfo = {
                        id: this.defectDetails.id,
                        manId: this.defectDetails.manId,
                        remark: this.defectDetails.remark
                    }
                    let _this = this
                    DefectService.distributeDefect(formInfo).then(
                        (result)=>{
                            if(_this.$route.params.isFinished==undefined){
                                _this.$router.push('/UM/myNews/queryMyTask')
                            }else{
                                _this.$router.push('/UM/myTasks/query')
                            }
                        },
                        (error)=>{
                            _this.Log.info(error)
                        })
                }
            })
            },2000)
        },
        //返回
        goBack(){
            this.$router.back(-1);
        },
        //获取员工列表
        getStaff(){
            var params = {
                outside: 1
            }
            DefectService.getStaffs(params).then(
                (result)=>{
                    this.liable = result
                },
                (error)=>{
                    this.Log.info(error)
                }
            )
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 680px;
    margin: 10px auto;
    padding: 10px 20px;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
.formBG{
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label,.formTitle{
    color: #fff;
}
.formBG >>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before, .formBG .ivu-form>>>.ivu-form-item-label:before {
    color: #00fff6;
    content: '★';
    display: inline-block;
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 12px;
}
@media (min-width: 2200px){
    .ivu-form.ivu-form-label-right{
        width: 50%;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 15vmin !important;
        line-height: 2.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 15vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .formTitle{
        font-size: 2.5vmin;
    }
    textarea.ivu-input{
        height: 5.5vmin !important;
        min-height: 5.5vmin !important;
    }
}   
</style>


