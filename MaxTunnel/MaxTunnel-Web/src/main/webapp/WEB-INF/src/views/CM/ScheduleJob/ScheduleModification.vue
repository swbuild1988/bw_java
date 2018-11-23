<template>
    <div>
        <!--修改定时调度任务的弹出框组件-->
        <Modal v-model="show.state" title="修改定时任务">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="定时任务名" prop="jobName">
                    <Input v-model="formValidate.jobName" disabled class="inputWidth"></Input>
                </FormItem>
                <!-- <FormItem label="定时任务组" prop="jobGroup">
                    <Input v-model="formValidate.jobGroup" disabled class="inputWidth"></Input>
                </FormItem> -->
                <FormItem label="定时任务周期" prop="cronExpression">
                    <Input v-model="formValidate.cronExpression" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务类" prop="jobClass">
                    <Input v-model="formValidate.jobClass" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务方法" prop="jobMethod">
                    <Input v-model="formValidate.jobMethod" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="定时任务描述" prop="description">
                    <Input v-model="formValidate.description" type="textarea" class="inputWidth"></Input>
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
    name: 'schedule-modification',
    data(){
        return {
            formValidate:{
                cronExpression:'',
                jobClass:'',
                jobMethod:'',
                description:''
            },
            ruleValidate:{
            },
        }
    },
    watch:{
        changeInfo:function(value,oldvalue){
            this.formValidate = JSON.parse(JSON.stringify(value));
        }
    },
    props:{
        show:{
            state:{
                default: false
            },
        },
        changeInfo:{}
    },
    mounted(){
    },
    methods:{
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$emit("listenToChange",this.formValidate);
                }else{
                    this.$Message.error("修改失败!");
                }
            })
        }
    }
}
</script>

<style scoped>
.inputWidth{
    width:85%;
}
</style>


