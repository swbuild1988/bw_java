<template>
    <div  class="formBG">
        <h2 class="formTitle">审批巡检计划</h2> 
        <div class="formHeight">
        <Form :model="plans" :label-width="140" @submit.native.prevent>
            <FormItem label="计划编号：">
                <Input v-model="plans.id" readonly></Input>
            </FormItem>
            <FormItem label="计划名称：">
                <Input v-model="plans.name" readonly></Input>
            </FormItem>
            <FormItem label="申请人：">
                <Input v-model="plans.requestStaff.name" readonly></Input>
            </FormItem>
            <FormItem label="审批人：">
                <Input v-model="plans.approver.name" readonly></Input>
            </FormItem>
            <FormItem label="所属管廊：">
                <Input v-model="plans.tunnelName" readonly></Input>
            </FormItem>
            <FormItem label="责任班组：">
                <Input v-model="plans.groupName" readonly></Input>
            </FormItem>
            <FormItem label="巡检方式：">
                <Input v-model="plans.inspectionWayName" readonly></Input>
            </FormItem>
            <FormItem label="巡检路径：">
                <Input v-model="plans.inspectionPathName" readonly></Input>
            </FormItem>
            <FormItem label="巡检对象：">
                <Input v-model="plans.inspectionObjectName" readonly></Input>
            </FormItem>
            <FormItem label="创建时间：">
                <Input v-model="plans.createTime" readonly></Input>
            </FormItem>
            <FormItem label="巡检步骤：">
                <ul>
                    <li v-for="(item, index) in plans.steps" :key="index" class="todoLi">
                        <span style="color: #fff">{{index+1}}、</span>
                        <input class="todoEidt" :value="item.name" placeholder="请输入要执行的计划步骤" readonly/>
                    </li>
                </ul>
            </FormItem>
            <FormItem label="巡检计划描述：">
                <Input type="textarea" readonly v-model="plans.remark" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批备注"></Input>
            </FormItem>
            <!-- <FormItem label="巡检时间：">
                <Input v-model="plans.inspectTime" readonly></Input>
            </FormItem> -->
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
                <Button type="success" class="cancelPoint" v-show="this.plans.result=='agree'">同意</Button>
                <Button type="error" class="cancelPoint" style="margin-left: 8px" v-show="this.plans.result!='agree'">不同意</Button>
            </FormItem>
            <FormItem style="text-align: center;margin-left: -140px">
                <Button @click="goBack()" type="gost">返回</Button>
            </FormItem>
        </Form>
        </div> 
        <!-- <Icon class="goBack" type="chevron-left" size="30"  title="" color="#fff"></Icon> -->
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
                // inspectTime: null,
                processInstanceId: null,
                remark: '1',
                tasks:[],
                comment: null,
                result: null,
                inspectionWay: null,
                inspectionPath: null,
                inspectObject: null,
                steps: [
                    { name: null }
                ]
            },
            columns1: [
                {
                    type: 'index',
                    align: 'center',
                    width: window.innerWidth/100*3
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
        axios.get('/users/activiti/task/detail/'+this.$route.params.processInstanceId).then(response=>{
            let{ code,data } = response.data
            if(code=200){
                this.plans = data
                this.plans.createTime = new Date(data.createTime).format('yyyy-MM-dd hh:mm:s')
                // this.plans.inspectTime = new Date(data.inspectTime).format('yyyy-MM-dd hh:mm:s')
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
            axios.post('inspection-plans/audit',(param)).then(response=>{
                let{ code,data } = response.data
                if(code==200){
                    if(this.$route.params.isFinished==null){
                        this.$router.push("/UM/myNews/queryMyTask");
                    }else{
                        this.$router.push("/UM/myTasks/query");
                    }
                }
            })  
        },
        //返回
        goBack(){
            this.$router.back(-1);
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 700px;
    padding: 10px 20px;
    margin: 0 auto;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
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
    padding-left: 0.5vw;
}
.cancelPoint{
    cursor: text;
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
    margin-top: -3.2vmin;
}
.formHeight{
    height: 75vh;
    overflow-y: auto;
}
.formHeight::-webkit-scrollbar{
    width: 4px;
    height: 4px;
}
.formHeight::-webkit-scrollbar-thumb{
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2)
}
.formHeight::-webkit-scrollbar-track{
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1)
}
.formBG >>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before{
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
        padding: 1vmin 2vmin;
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
        font-size: 2.2vmin;
    }
    textarea.ivu-input{
        height: 5.5vmin !important;
        min-height: 5.5vmin !important;
    }
}
</style>
