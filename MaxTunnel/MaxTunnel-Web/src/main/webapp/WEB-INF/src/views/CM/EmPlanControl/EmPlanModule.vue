<template>
    <div>
        <!-- 应急管理之新增流程节点 -->
        <Modal v-model="show.state" title="新增流程节点">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="预案名" prop="processKey">
                    <Select v-model="formValidate.processKey" placeholder="请选择预案名" class="InputWidth">
                        <Option v-for="item in planEnums" :value="item.processKey" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="流程节点值" prop="taskKey">
                    <Input v-model="formValidate.taskKey" placeholder="请输入新流程节点值" class="InputWidth"/>
                </FormItem>
                <FormItem label="流程节点名称" prop="taskName">
                    <Input v-model="formValidate.taskName" placeholder="请输入新流程节点名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="应急目标类型" prop="targetKey">
                    <Select v-model="formValidate.targetKey" placeholder="请选择应急目标类型" class="InputWidth">
                        <Option v-for="item in targetEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="应急目标" prop="targetValue">
                    <Input v-model="formValidate.targetValue" placeholder="请输入新应急目标" class="InputWidth"/>
                </FormItem>
                <FormItem label="应急行为类型" prop="actionKey">
                    <Select v-model="formValidate.actionKey" placeholder="请选择应急行为类型" class="InputWidth">
                        <Option v-for="item in actionEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="应急行为" prop="actionValue">
                    <Input v-model="formValidate.actionValue" placeholder="请输入新应急行为" class="InputWidth"/>
                </FormItem>
                <FormItem label="应急结束类型" prop="finishKey">
                    <Select v-model="formValidate.finishKey" placeholder="请选择应急结束类型" class="InputWidth">
                        <Option v-for="item in finishEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
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
export default {
    name: 'em-plan-add',
    data(){
        return {
            formValidate:{
                processKey:null,
                taskKey:null,
                taskName:null,
                targetKey:null,
                targetValue:null,
                actionKey:null,
                actionValue:null,
                finishKey:null,
                finishValue:null,
                isFinished:null
            },
            ruleValidate:{
                processKey: [
                    { required: true, message: '流程节点名不能为空', trigger: 'blur' }
                ]
            },
            targetEnums:[],
            actionEnums:[],  
            finishEnums:[],
            planEnums:[],  
            isFinishEnums:[
                {   val: 0,key: "否" },
                {   val: 1,key: "是" }
            ]
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        }
    },
    watch:{
        'show.state':function(newValue,oldValue){
            this.cleanFormValidate();
        }
    },
    mounted(){
        this.getTargetEnumList();
        this.getActionEnumList();
        this.getFinishEnumList();
        this.getPlanEnumList();
    },
    methods:{
        getTargetEnumList(){      //获取应急目标对象列表
            this.axios.get('/em/target-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.targetEnums = data;
                }
            })
        },
        getActionEnumList(){       //获取应急行为
            this.axios.get('/em/action-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.actionEnums = data;
                }
            })
        },
        getFinishEnumList(){        //获取应急结束类型
            this.axios.get('/em/finish-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.finishEnums = data;
                }
            })
        },
        getPlanEnumList(){
            this.axios.get('/plan-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.planEnums = data;
                }
            })
        },
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$emit("listenToAdd",this.formValidate);
                } else {
                    this.$Message.error('添加失败');
                }
            });
        },
        cleanFormValidate(){
            this.formValidate.processKey = null,
            this.formValidate.taskKey = null,
            this.formValidate.taskName = null,
            this.formValidate.targetKey = null,
            this.formValidate.targetValue = null,
            this.formValidate.actionKey = null,
            this.formValidate.actionValue = null,
            this.formValidate.finishKey = null,
            this.formValidate.finishValue = null,
            this.formValidate.isFinished = null
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
