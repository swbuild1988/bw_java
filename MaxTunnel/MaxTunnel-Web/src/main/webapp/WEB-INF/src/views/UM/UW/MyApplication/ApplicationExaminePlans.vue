<template>
    <div>
        <Form :model="plans" :label-width="120" @submit.native.prevent>
            <FormItem label="计划编号：">
                <Input v-model="plans.id" readonly></Input>
            </FormItem>
            <FormItem label="计划名称：">
                <Input v-model="plans.name" readonly></Input>
            </FormItem>
            <FormItem label="申请人：">
                <Input v-model="plans.requestStaffId" readonly></Input>
            </FormItem>
            <FormItem label="审批人：">
                <Input v-model="plans.approverId" readonly></Input>
            </FormItem>
            <FormItem label="所属管廊：">
                <Input v-model="plans.tunnelName" readonly></Input>
            </FormItem>
            <FormItem label="责任班组：">
                <Input v-model="plans.groupName" readonly></Input>
            </FormItem>
            <FormItem label="巡检方式：">
                <Input v-model="plans.inspectionWay" readonly></Input>
            </FormItem>
            <FormItem label="巡检路径：">
                <Input v-model="plans.inspectionPath" readonly></Input>
            </FormItem>
            <FormItem label="巡检对象：">
                <Input v-model="plans.inspectObject" readonly></Input>
            </FormItem>
            <FormItem label="创建时间：">
                <Input v-model="plans.createTime" readonly></Input>
            </FormItem>
            <FormItem label="巡检时间：">
                <Input v-model="plans.inspectTime" readonly></Input>
            </FormItem>
            <FormItem label="巡检步骤：">
                <ul>
                    <li v-for="(item, index) in plans.todolist" :key="index" class="todoLi">
                        <span>
                            <Checkbox></Checkbox>
                        </span>
                        <span>{{index+1}}、</span>
                        <input class="todoEidt" :value="item.todoContent" placeholder="请输入要执行的计划步骤" />
                    </li>
                </ul>
            </FormItem>
            <FormItem label="巡检计划：">
                <Table border stripe :columns="columns1" :data="plans.tasks" style="margin: 20px auto;"></Table>
            </FormItem>
            <FormItem label="备注：" v-show="examineStatus==1||examineStatus==2">
                <Input type="textarea" v-model="plans.remark" :autosize="{minRows: 2,maxRows: 5}" readonly placeholder="请输入审批备注"></Input>
            </FormItem>
            <FormItem label="申请状态：">
                <Button type="success" class="btn" v-show="examineStatus==1">同意</Button>
                <Button type="error" class="btn"  v-show="examineStatus==2">不同意</Button>
                <Button type="info" class="btn" v-show="examineStatus==3">审核中</Button>
            </FormItem>
        </Form>
        <Icon class="goBack" type="chevron-left" size="30" @click="goBack()" title="返回" color="#fff"></Icon>
    </div>
</template>
<script>
import axios from "axios";
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            plans:{
                id: this.$route.params.id,
                name: null,
                requestStaffId: null,
                approverId: null,
                requestStatus: null,
                tunnelName: null,
                groupName: null,
                createTime: null,
                inspectTime: null,
                processDefinitionId: null,
                remark: null,
                tasks:[],
                inspectionWay: null,
                inspectionPath: null,
                inspectObject: null,
                todolist: [
                    { id: 1, todoContent: '111' },
                    { id: 2, todoContent: '222' }
                ]
            },
            columns1: [
                {
                    type: 'index',
                    align: 'center',
                    width: 40
                },
                {
                    title: '计划编号',
                    key: 'planId',
                    align: 'center',
                    width: 90
                },
                {
                    title: '计划名称',
                    key: 'planName',
                    align: 'center'
                },
                {
                    title: '计划时间',
                    key: 'taskTime',
                    align: 'center',
                    render: (h,params) =>{
                        return h('div',new Date(params.row.taskTime).format('yyyy-MM-dd hh:mm:s'))
                    }
                },
                {
                    title: '巡检时间',
                    key: 'inspectTime',
                    align: 'center',
                    render: (h,params) => {
                        return h('div',new Date(params.row.inspectTime).format('yyyy-MM-dd hh:mm:s'))
                    }
                },
                {
                    title: '巡检人',
                    key: 'inspectManName',
                    align: 'center',
                    width: 80
                },
                {
                    title: '结果',
                    key: 'status',
                    align: 'center',
                    width: 80
                }    
            ],
            examineStatus: 1,
            examine: types.examineStatus,
        }
    },
    mounted(){
        this.examineStatus = this.$route.params.type
        axios.get('/users/activiti/task/detail/'+this.$route.params.processDefinitionId+'/'+this.$route.params.processType).then(response=>{
            let{ code,data } = response.data
            if(code=200){
                this.plans = data
                this.plans.createTime = new Date(this.plans.createTime).format('yyyy-MM-dd hh:mm:s')
                this.plans.inspectTime = new Date(this.plans.inspectTime).format('yyyy-MM-dd hh:mm:s')
            }
        })
    },
    methods:{
        //返回
        goBack(){
            this.$router.back(-1);
        }
    }
}
</script>
<style scoped>
.todoLi{
    line-height: 3.5vh;
    height: 3.5vh;
    margin-right: 0.5vw;
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 0.5vh;
}
.todoLi .todoEidt{
    border: none;
    box-shadow: 2px 2px 10px 0px #ccc;
    flex: 1;
    margin-right: 0.5vw;
    padding-left: 0.5vw;
}
.ivu-form.ivu-form-label-right{
    width: 780px;
    margin: 20px auto;
}
.btn{
    cursor: text;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
</style>
