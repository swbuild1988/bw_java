<template>
    <div>
        <!--新增定时调度任务的弹出框组件-->
        <Modal v-model="show.state" :title="title">
            <Form ref="scheduleForm" :model="scheduleForm" :rules="ruleValidate" :label-width="120">
                <FormItem label="定时任务名：" prop="jobName">
                    <Input v-model="scheduleForm.jobName" placeholder="请输入定时任务名" class="InputWidth" :disabled="type==2"></Input>
                </FormItem>
                <FormItem label="定时任务类型：" prop="jobType">
                    <Select v-model="scheduleForm.jobType" placeholder="请选择定时任务类型" class="InputWidth">
                        <Option v-for="item in objTypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="调度表达式：" prop="cronExpression">
                    <Input v-model="scheduleForm.cronExpression" placeholder="请输入调度表达式" class="InputWidth"></Input>
                </FormItem>
            </Form>
            <div slot="footer" v-show="type==1">
                <Button type="primary" size="large" v-on:click="addScheduleJob('scheduleForm')">保存</Button>
            </div>
            <div slot="footer" v-show="type==2">
                <Button type="primary" size="large" v-on:click="editScheduleJob('scheduleForm')">保存</Button>
            </div>
        </Modal>
    </div>    
</template>

<script>
import {SchedulejobService} from '@/services/schedulejobService'
export default {
    name: 'schedule-add',
    data(){
        const validateJobName = (rule,value,callback) => {
            if (value === '') {
                callback(new Error('不能为空'))
            } else {
                MeasObjServer.checkJobName(value).then(
                    result=>{
                        if(!result){
                            callback(new Error('任务名需唯一'))
                        } else {
                            callback()
                        }
                    }
                ) 
            }
        }
        return {
            flag:true,
            scheduleForm:{
                jobId: null,
                jobName: null,
                jobType: null,
                cronExpression: null,
                jobGroup: null
            },
            ruleValidate:{
                jobName: [
                    { required: true, message: '定时任务名不能为空', trigger: 'blur' },
                    // { validator: this.validateJobName, trigger: 'blur' }
                ],
                jobType: [
                    { type: 'number', required: true, message: '定时任务类型不能为空', trigger: 'change' }
                ],
                cronExpression: [
                    { required: true, message: '调度表达式不能为空', trigger: 'change' }
                ]
            },
            allstatus:[],
            objTypes: [],
            title: null
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        type: null,
        id: null
    },
    computed:{
        params() {
            let param = {
                jobName: this.scheduleForm.jobName,
                jobType: this.scheduleForm.jobType,
                cronExpression: this.scheduleForm.cronExpression
            };
            return Object.assign({},param);
        },
        editParams() {
            let param = {
                jobId: this.scheduleForm.jobId, 
                jobName: this.scheduleForm.jobName,
                jobType: this.scheduleForm.jobType,
                cronExpression: this.scheduleForm.cronExpression,
                jobGroup: this.scheduleForm.jobGroup
            }
            return Object.assign({},param)
        }
    },
    watch:{
        'type': function(newVal){
            this.changeTitle(newVal)
        }
    },
    mounted(){
        this.changeTitle(this.type)
        SchedulejobService.getObjtype().then(
            result => {
                this.objTypes = result
            },
            error => {
                this.Log.info(error)
            }   
        )
    },
    methods:{
        //add 保存
        addScheduleJob: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    SchedulejobService.saveSchedule(this.params).then(
                        result => {
                            this.$emit("addScheduleJob")
                            this.handleReset(name)
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("添加失败！");
                }
            })
        },
        //获取详情
        getScheduleJob: function(id){
            SchedulejobService.getSchedule(id).then(
                result => {
                    this.scheduleForm = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        // edit 保存
        editScheduleJob: function(name){
            this.$refs[name].validate((valid) => {
                if(valid) {
                    SchedulejobService.editSchedule(this.editParams).then(
                        result => {
                            this.$emit("editScheduleJob")
                            this.handleReset(name)
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("修改失败！")
                }
            })
        },
        changeTitle(type){
            if(type==1){
                this.title = "新增定时任务"
            }
            if(type==2){
                this.title = "修改定时任务"
            }
        },
        handleReset(name){
            this.$refs[name].resetFields()
        }
    },
    
}
</script>

<style scoped>
.InputWidth{
    width: 85%;
}
</style>


