<template>
    <div>
    <!-- <Button type="primary" @click="goBack"><Icon type="arrow-left-a" style="margin-right: 10px"></Icon>返回</Button>     -->
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
        <FormItem label="创建时间：">
            <Input v-model="plans.createTime" readonly></Input>
        </FormItem>
        <FormItem label="巡检时间：">
            <Input v-model="plans.inspectTime" readonly></Input>
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
                tasks:[]
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
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 780px;
    margin: 20px auto;
}
 .btn{
    cursor: text;
  }
</style>
