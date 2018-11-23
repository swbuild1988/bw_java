<template>
    <div :style="backStyle">
        <Form :model="plans" :label-width="140" @submit.native.prevent>
            <h2 class="formTitle">审批巡检计划</h2>    
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
            <FormItem label="巡检计划描述：">
                <Input type="textarea" v-model="plans.remark" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批备注"></Input>
            </FormItem>
            <FormItem label="巡检时间：">
                <Input v-model="plans.inspectTime" readonly></Input>
            </FormItem>
            <FormItem label="巡检计划：">
                <Table border stripe :columns="columns1" :data="plans.tasks" style="margin: 20px auto;"></Table>
            </FormItem>
            <FormItem label="备注：">
                <Input type="textarea" v-model="plans.comment" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批备注" :readonly="this.plans.result!=null"></Input>
            </FormItem>
            <FormItem label="申请状态：" v-show="this.plans.result==null">
                <Button type="success" @click="agree(1)">同意</Button>
                <Button type="error" style="margin-left: 8px" @click="agree(0)">不同意</Button>
            </FormItem>
            <FormItem label="申请状态：" v-show="this.plans.result!=null">
                <Button type="success" v-show="this.plans.result=='agree'">同意</Button>
                <Button type="error" style="margin-left: 8px" v-show="this.plans.result!='agree'">不同意</Button>
            </FormItem>
        </Form>
    </div>
</template>
<script>
import axios from "axios";
export default {
    data(){
        return{
            plans:{
                id: 0,
                name: null,
                requestStaffId: null,
                approverId: null,
                requestStatus: null,
                tunnelName: null,
                groupName: null,
                createTime: null,
                inspectTime: null,
                processInstanceId: null,
                remark: '1',
                tasks:[],
                comment: null,
                result: null
            },
            columns1: [
                {
                    type: 'index',
                    align: 'center',
                    width: 60
                },
                {
                    title: '计划巡检时间',
                    key: 'taskTime',
                    align: 'center',
                    render: (h,params) =>{
                        return h('div',new Date(params.row.taskTime).format('yyyy-MM-dd'))
                    }
                },
                {
                    title: '巡检人',
                    key: 'inspectManName',
                    align: 'center'
                },   
            ],
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            }
        }
    },
    computed:{
      params() {
        let param = {
          remark: this.plans.comment,
          id: this.plans.id,
          processInstanceId: this.plans.processInstanceId,
          value: 1
        }
        return Object.assign({}, param);
      },
      paramsDis(){
        let param = {
          remark: this.plans.comment,
          id: this.plans.id,
          processInstanceId: this.plans.processInstanceId,
          value: 0
        }
        return Object.assign({}, param);
      } 
    },
    mounted(){
        console.log("this.$route.params",this.$route.params)
        axios.get('/users/activiti/task/detail/'+this.$route.params.processInstanceId).then(response=>{
            let{ code,data } = response.data
            if(code=200){
                this.plans = data
                this.plans.createTime = new Date(data.createTime).format('yyyy-MM-dd hh:mm:s')
                this.plans.inspectTime = new Date(data.inspectTime).format('yyyy-MM-dd hh:mm:s')
            }
        })
    },
    methods:{
        agree(num){
            let param = {
                remark: this.plans.comment,
                id: this.plans.id,
                processInstanceId: this.plans.processInstanceId,
                value: num
            }
            axios.post('inspection-plans/audit',(this.params)).then(response=>{
                let{ code,data } = response.data
                if(code==200){
                    if(this.$route.params.isFinished==null){
                        this.$router.push("/UM/myNews/queryMyTask");
                    }else{
                        this.$router.push("/UM/myTasks/query");
                    }
                }
            })  
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 700px;
    background: #fff;
    padding: 10px 20px;
    margin: 0 auto;
}
</style>
