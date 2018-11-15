<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="6">
                    流程类型：
                    <Select style="width:60%" v-model="conditions.processType" @on-change="queryMyTask()">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in processType" :value="item.val" :key="index">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime" @on-change="queryMyTask()"></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime" @on-change="queryMyTask()"></DatePicker>
                </Col>
            </Row>
        </div>
        <!-- <Table stripe border :columns="columns1" :data="myTasks"></Table> -->
        <div class="list">
            <Row>
                <Col span="4" v-for="(item,index) in myTasks" :key='index'>
                    <div class="unitBox" @click="goToMoudle(item)">
                        <div class="title">
                            <p>{{item.processTypeName}}</p>
                            <p style="font-size: 18px;margin-top: 5px;">{{item.status}}</p>
                        </div>
                        <div class="crtTime">
                            <span>{{item.crtTime}} </span>
                        </div>    
                    </div>
                </Col>
            </Row>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="position: absolute;bottom: 20px; right: 15px;"></Page>   
    </div>    
</template>
<script>
import axios from "axios";
export default {
    data(){
        return{
            id: null,
            myTasks:[],
            processType:[],
            conditions:{
                processType: null,
                startTime: null,
                endTime: null
            },
            page:{
                pageNum: 1,
                pageSize: 30,
                pageTotal:null,
            },
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name:this.conditions.name,
                processType: this.conditions.processType,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
            };
            return Object.assign({}, param);
        }
    },
    mounted() {
        axios.get('processtype-enums').then(response=>{
            let{code,data} = response.data
            if(code==200){
                this.processType = data
            }
        })
        this.queryMyTask()
    },
    methods: {
        //获取我的任务
        queryMyTask(){
            axios.post('users/activiti/task/datagrid',(this.params)).then(response=>{
                let { code, data } = response.data;
                if (code == 200) {
                    this.myTasks = data.pagedList
                    this.page.pageTotal = data.length
                    for(let index in this.myTasks){
                        this.myTasks[index].crtTime = new Date(this.myTasks[index].crtTime).format('yyyy-MM-dd')
                    }
                }
            })
        },
        goToMoudle: function (task) {
            let pathParams = {
                name:'',
                params:null
            }
            switch (task.processType){
                // 巡检计划审批
                case 1001:
                    pathParams.name = 'examinPlans'
                    pathParams.params = {
                        processInstanceId: task.processInstanceId,
                        status: task.status,
                        processType: task.processType
                    }
                    break;

                // 巡检任务提交
                case 1002:
                    pathParams.name = 'submitPatralTask'
                    pathParams.params = {
                        id: task.id
                    }
                    break;

                // 维护工单
                case 1003:
                    // 指派任务
                    if (task.taskKey == 'allocation') {
                        pathParams.name = 'newsDistributeDefect'
                        pathParams.params = {
                            id: task.id
                        }
                    }
                    // 结束任务，提交工单 完结工单
                    if (task.taskKey == 'complete') {
                        pathParams.name = 'UWDetailsOverhaul'
                        pathParams.params = {
                            id: task.id,
                            type: 4,
                            tip: 'news'
                        }
                    }
                break;

                case 2001:
                    // 入廊审批
                    if(task.taskKey=="approve"){                        
                        pathParams.name = 'UMExamineApprove'
                        pathParams.params = {
                            processInstanceId: task.processInstanceId,
                            processType: task.processType,
                            id: task.id
                        }
                    }
                    // 离开管廊
                    if(task.taskKey=="leave"){         
                        pathParams.name = 'newsExamineApprove'
                        pathParams.params = {
                            processInstanceId: task.processInstanceId,
                            processType: task.processType,
                            id: task.id
                        }
                    }
                break;

                default:

                break;

            }
            this.$router.push(pathParams);
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryMyTask()();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryMyTask()();
        },
    }
}
</script>
<style scoped>
    .unitBox{
        background: linear-gradient(to right, #d086a9 , #496dcc);
        height: 100px;
        width: 80%;
        margin: 10px auto;
        position: relative;
        border-radius: 4px;
        cursor: pointer;
    }
    .unitBox:hover{
        box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
        width: 81%;
        /* height: 101px; */
    }
    .title{
        font-size: 20px;
        font-weight: 800;
        color: #fff;
        cursor: pointer;
        margin: 0 auto;
        text-align: center;
        line-height: 30px;
        padding-top: 10px;
    }
    .crtTime{
        position: absolute;
        bottom: 2px;
        right: 5px;
        color: #fff;
    }
</style>
