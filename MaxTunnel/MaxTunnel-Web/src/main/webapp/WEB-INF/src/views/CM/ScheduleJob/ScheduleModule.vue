<template>
    <div>
        <!--新增定时调度任务的弹出框组件-->
        <Modal v-model="show.state" title="新增定时任务">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="定时任务名" prop="jobName">
                    <Input v-model="formValidate.jobName" placeholder="请输入定时任务名" class="InputWidth"></Input>
                </FormItem>
                <!-- <FormItem label="定时任务组" prop="jobGroup">
                    <Input v-model="formValidate.jobGroup" placeholder="请输入定时任务组" class="InputWidth"></Input>
                </FormItem> -->
                <FormItem label="定时任务周期" prop="cronExpression">
                    <Input v-model="formValidate.cronExpression" placeholder="请输入定时任务周期" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务类" prop="jobClass">
                    <Input v-model="formValidate.jobClass" placeholder="请输入定时任务类" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务方法" prop="jobMethod">
                    <Input v-model="formValidate.jobMethod" placeholder="请输入定时任务方法" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务描述" prop="description">
                    <Input v-model="formValidate.description" type="textarea" placeholder="请输入定时任务描述" class="InputWidth"></Input>
                </FormItem>
                <span v-show="!flag" class="errorStyle">任务名需唯一</span>
            </Form>
            <div slot="footer" v-show="flag">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
            <div slot="footer" v-show="!flag">
                <Button disabled type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>    
</template>

<script>
export default {
    name: 'schedule-add',
    data(){
        return {
            flag:true,
            formValidate:{
                jobName:null,
                jobGroup:null,
                cronExpression:'',
                jobClass:'',
                jobMethod:'',
                description:''
            },
            ruleValidate:{
                jobName: [
                    { required: true, message: '定时任务名不能为空', trigger: 'blur' }
                ],
                // jobGroup: [
                //     { required: true, message: '定时任务组不能为空', trigger: 'blur' }
                // ],
                cronExpression: [
                    { required: true, message: '定时任务周期不能为空', trigger: 'blur' }
                ],
                jobClass: [
                    { required: true, message: '定时任务类不能为空', trigger: 'blur' }
                ],
                jobMethod: [
                    { required: true, message: '定时任务方法不能为空', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '定时任务描述不能为空', trigger: 'blur' }
                ]
            },
            allstatus:[]
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
        'formValidate.jobName':function(value,oldvalue){
            if(value != null){
                this.axios.get('/schedulejobs/ajax/' + value).then(res =>{
                    let {code,data} = res.data;
                    if(code == 200){
                        this.flag = data;
                    }
                })
            }
        },
        // 'formValidate.jobGroup':function(value,oldvalue){
        //     if(value != null && this.formValidate.jobName != null){
        //         this.axios.get('/schedulejobs/ajax/' + this.formValidate.jobName + '/' + value).then(res =>{
        //             let {code,data} = res.data;
        //             if(code == 200){
        //                 this.flag = data;
        //             }
        //         })
        //     }
        // }
    },
    methods:{
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    if(this.flag){
                        this.$emit("listenToAdd",this.formValidate);
                    }else{
                        this.$Message.error("任务名需唯一");
                    }
                }else{
                    this.$Message.error("添加失败!");
                }
            })
        }
    },
    
}
</script>

<style scoped>
.errorStyle{
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 136px;
}
.InputWidth{
    width: 85%;
}
</style>


