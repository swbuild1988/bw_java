<template>
    <div>
        <!-- 应急管理之新增流程节点 -->
        <Modal v-model="show.state" title="新增流程节点">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="140">
                <FormItem label="预案名" prop="processKey">
                    <Select v-model="formValidate.processKey" placeholder="请选择预案名" class="InputWidth">
                        <Option v-for="item in planEnums" :value="item.processKey" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="BPMN节点ID" prop="taskKey">
                    <Input v-model="formValidate.taskKey" placeholder="请输入BPMN节点ID" class="InputWidth"/>
                </FormItem>
                <FormItem label="BPMN节点Name" prop="taskName">
                    <Input v-model="formValidate.taskName" placeholder="请输入新BPMN节点Name" class="InputWidth"/>
                </FormItem>
                <FormItem label="目标类型" prop="targetKey">
                    <Select v-model="formValidate.targetKey" placeholder="请选择目标类型" class="InputWidth">
                        <Option v-for="item in targetEnums" :value="item.val + ''" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="目标对象" prop="targetValue">
                    <Input placeholder="请输入目标对象" class="InputWidth" v-if="!formValidate.targetKey"/>
                    <Select v-model="formValidate.targetValue" placeholder="请选择目标对象" class="InputWidth" v-if="formValidate.targetKey == 2">
                        <Option v-for="item in targetValEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                    <Poptip placement="bottom" width="1000" v-show="formValidate.targetKey == 1">
                        <Input v-model="formValidate.targetValueName" placeholder="请选择目标对象" style="width:294px"/>
                        <div class="pop" slot="content">
                            <meas-obj-choose v-on:objsChoose="getObjs" v-bind="measObj"></meas-obj-choose>
                        </div>
                    </Poptip>
                </FormItem>
                <FormItem label="应急行为类型" prop="actionKey">
                    <Select v-model="formValidate.actionKey" placeholder="请选择应急行为类型" class="InputWidth">
                        <Option v-for="item in actionEnums" :value="item.val+''" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="应急行为" prop="actionValue">
                    <Input placeholder="请输入新应急行为" class="InputWidth" v-if="!formValidate.actionKey"/>
                    <Input v-model="formValidate.actionValue" placeholder="请输入新应急行为" class="InputWidth" v-if="formValidate.actionKey == 1"/>
                    <Select v-model="formValidate.actionValue" placeholder="请选择单位类型" class="InputWidth" v-if="formValidate.actionKey == 2"> 
                        <Option v-for="item in unitTypeEnums" :value="item.val+''" :key="item.key">{{item.key}}</Option>
                    </Select>
                    <Select v-model="formValidate.actionValue" placeholder="请选择开关类型" class="InputWidth" v-if="formValidate.actionKey == 3"> 
                        <Option v-for="item in switchEnums" :value="item.val+''" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="应急结束类型" prop="finishKey">
                    <Select v-model="formValidate.finishKey" placeholder="请选择应急结束类型" class="InputWidth">
                        <Option v-for="item in finishEnums" :value="item.val+''" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="应急结束" prop="finishValue">
                    <Input v-model="formValidate.finishValue" placeholder="请输入新应急结束" class="InputWidth"/>
                </FormItem>
                <FormItem label="应急结束状态" prop="isFinished">
                    <Select v-model="formValidate.isFinished" placeholder="请选择是否结束" class="InputWidth">
                        <Option v-for="item in isFinishEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { EnumsService } from '../../../services/enumsService'
import measObjChoose from '../../../components/CM/MAM/measObjChoose'

export default {
    name: 'em-plan-add',
    data(){
        return {
            formValidate:{
                processKey: null,
                taskKey: null,
                taskName: null,
                targetKey: null,
                targetValue: null,
                actionKey: null,
                actionValue: null,
                finishKey: null,
                finishValue: null,
                isFinished: null,
                targetValueName: null
            },
            ruleValidate:{
                processKey: [
                    { required: true, message: '预案名不能为空', trigger: 'blur' }
                ],
                taskKey: [
                    { required: true, message: 'BPMN节点ID不能为空', trigger: 'blur' }
                ],
                taskName: [
                    { required: true, message: 'BPMN节点Name不能为空', trigger: 'blur' }
                ],
                targetKey: [
                    { required: true, message: '目标类型不能为空', trigger: 'change' }
                ],
                targetValue: [
                    { required: true, message: '目标对象不能为空'}
                ],
                actionKey: [
                    { required: true, message: '应急行为类型不能为空', trigger:'change'}
                ],
                actionValue: [
                    { required: true, message: '应急行为不能为空'}
                ],
                finishKey: [
                    { required: true, message: '应急结束类型不能为空', trigger:'change'}
                ],
                finishValue: [
                    { required: true, message: '应急结束不能为空', trigger:'blur'}
                ],
                isFinished: [
                     { required: true, message: '应急结束状态不能为空'}
                ]
            },
            targetEnums:[],
            actionEnums:[],  
            finishEnums:[],
            planEnums:[], 
            targetValEnums:[], 
            unitTypeEnums: [],
            switchEnums: [],
            isFinishEnums:[
                {   val: 0,key: "否" },
                {   val: 1,key: "是" }
            ],
            measObj: {
                targetValue: null,
                isShow: false
            }
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        editInfo: {
            required: true
        },
        type: {
            required: true
        }
    },
    watch:{
        'show.state':function(newValue,oldValue){
            if(newValue){
                if(this.type == 'add'){
                    this.cleanFormValidate()
                } else {
                    this.formValidate = this.editInfo
                    for(let item in this.formValidate){
                        if(item == 'targetKey' || 'actionKey' || 'finishKey'){
                            this.formValidate[item] = this.formValidate[item] + ''
                        }
                    }
                    this.formValidate.isFinished = this.formValidate.isFinished == 'true' ? 1 : 0

                    if(this.formValidate.targetKey == 2){
                        this.formValidate.targetValue = parseInt(this.formValidate.targetValue)
                    } else {
                        this.measObj.targetValue = this.formValidate.targetValue.split(',')
                        this.measObj.isShow = true
                    }  
                }
            } 
        }
    },
    mounted(){
        this.init();
    },
    components: {
        measObjChoose       
    },
    methods:{
        init(){      
            let _this = this
            //获取应急目标对象列表
            EnumsService.getEmTargetObjs().then(
                result=>{
                    _this.targetEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            //获取应急行为
            EnumsService.getEmAction().then(
                result=>{
                    _this.actionEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            //获取应急结束类型
            EnumsService.getEmFinishType().then(
                result=>{
                    _this.finishEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            EnumsService.getPlanType().then(
                result=>{
                    _this.planEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            EnumsService.getObjType().then(
                result=>{
                    _this.targetValEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            EnumsService.getUnitType().then(
                result=>{
                    _this.unitTypeEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            EnumsService.getSwitch().then(
                result=>{
                    _this.switchEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$emit("addOrEdit",this.formValidate);
                } else {
                    this.$Message.error('添加失败');
                }
            });
        },
        cleanFormValidate(){
            for(let item in this.formValidate){
                this.formValidate[item] = null
            }
        },
        getObjs(data) {
            this.formValidate.targetValue = data.ids.join(',')
            this.formValidate.targetValueName = data.names.join(',')
            this.measObj.isShow = false
        }
    }
}
</script>

<style scoped>
.InputWidth{
    width: 85%;
}
.errorStyle{
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 120px;
}
</style>
