<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="4">
                    <span class="conditionTitle">任务类型：</span>
                    <Select style="width:60%" v-model="conditions.processType">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in processType" :value="item.val" :key="index">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime"></DatePicker>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime"></DatePicker>
                </Col>
                <Col span="4">
                    <Button type="primary" icon="ios-search" size="small" @click="queryMyTask()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <div v-for="(temp,index) in divName" :key="index">
                <span class="tabName">{{temp.name}}</span>
                <div class="nullData" v-show="isNullData">暂无数据</div>
                <Row>
                    <Col span="4" v-for="(item,index) in myTasks" :key='index' v-if="temp.id==item.processType">
                        <div class="unitBox" v-bind:class="{isFinished: item.isFinished,waitStart:item.status=='未开始'}" @click="goToMoudle(item)">
                            <div class="title">
                                <p>{{item.processTypeName}}</p>
                                <p class="workDetails">{{item.status}}</p>
                            </div>
                            <div class="crtTime">
                                <span>{{item.crtTime}} </span>
                            </div>    
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="position: absolute;bottom: 1vmin; right: 2vmin;"></Page>   
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
                pageSize: 24,
                pageTotal:0,
            },
            userId: null,
            divName:[],
            isNullData: false
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name:this.conditions.name,
                processType: this.conditions.processType,
                startTime: null,
                endTime: null,
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
        queryMyTask(){
            axios.post('users/activiti/allTask/datagrid',(this.params)).then(response=>{
                let { code, data } = response.data;
                if (code == 200) {
                    if(data.pagedList.length==0){
                        this.isNullData = true
                    }else{
                        this.isNullData = false
                    }
                    this.myTasks = data.pagedList
                    for(let index in this.myTasks){
                        this.myTasks[index].crtTime = new Date(this.myTasks[index].crtTime).format('yyyy-MM-dd')
                        var divNameId = this.myTasks[index].processType
                        var name = this.myTasks[index].processTypeName
                        var obj = {
                            id: divNameId,
                            name: name
                        }
                        if(JSON.stringify(this.divName).indexOf(JSON.stringify(obj))==-1){
                            this.divName.push(obj)
                        }
                    }   
                    this.page.pageTotal = data.total    
                }
            })
        },        
        goToMoudle: function (task) {
            console.log("task", task)
            let pathParams = {
                name:'',
                params:null
            }
            switch (task.processType){
                // 巡检计划审批
                case 1001:
                    pathParams.name = '我的巡检计划详情'
                    pathParams.params = {
                        processInstanceId: task.processInstanceId,
                        status: task.status,
                        processType: task.processType,
                        isFinished: task.isFinished
                    }
                    break;

                // 巡检任务提交
                case 1002:
                    pathParams.name = '提交巡检任务结果1'
                    pathParams.params = {
                        id: task.id,
                        isFinished: task.isFinished
                    }
                    break;

                // 维护工单
                case 1003:
                    // 指派任务
                    if (task.taskKey == 'allocation'&&task.isFinished == false) {
                        pathParams.name = '分配巡检任务'
                        pathParams.params = {
                            id: task.id,
                            isFinished: task.isFinished
                        }
                    }
                    //任务结束 查看
                    if(task.taskKey == 'allocation' && task.isFinished == true){
                        pathParams.name = '已完结工单详情'
                        pathParams.params = {
                            id: task.id,
                            isFinished: task.isFinished,
                            type: 1
                        }
                    }
                    // 结束任务，提交工单
                    if (task.taskKey == 'complete'&&task.isFinished==false) {
                        pathParams.name = '未完结工单详情'
                        pathParams.params = {
                            id: task.id,
                            isFinished: task.isFinished,
                            type: 4,
                            tip: 'tasks'
                        }
                    }
                break;

                case 2001:
                    // 查看入廊审批
                    if(task.taskKey=="approve"){                        
                        pathParams.name = '入廊申请审批'
                        pathParams.params = {
                            processInstanceId: task.processInstanceId,
                            processType: task.processType,
                            isFinished: task.isFinished,
                            id: task.id
                        }
                    }
                    // 离开管廊
                    if(task.taskKey=="leave"||task.isFinished==true){    
                        pathParams.name = '入廊申请详情'
                        pathParams.params = {
                            processInstanceId: task.processInstanceId,
                            processType: task.processType,
                            isFinished: task.isFinished,
                            id: task.id
                        }
                    }
                    //确认出廊
                    if(task.taskKey=='leave'&&task.isFinished==false){
                        pathParams.name = '确认出廊'
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
            this.queryMyTask()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryMyTask()()
        }
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
    .isFinished{
        background: #496dcc;
    }
    .waitStart{
        background: #d086a9;
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
        /* line-height: 85px; */
        line-height: 24px;
        padding-top: 20px;
    }
    .crtTime{
        position: absolute;
        bottom: 2px;
        right: 5px;
        color: #fff;
    }
    .tabName{
        font-size: 18px;
        padding: 5px;
        border-bottom: 2px solid #357aa1;
        color: #fff;
    }
    .workDetails{
        font-size: 18px;
        margin-top: 5px;
    }
    .ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
        color: #fff;
    }
    .list{
        overflow-y: auto;
    }
    .list::-webkit-scrollbar{
        width: 0.4vmin;
        height: 0.4vmin;
    }
    .list::-webkit-scrollbar-thumb{
        border-radius: 1vmin;
        box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
        background: #83a6ed;
    }
    .list::-webkit-scrollbar-track{
        box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
        border-radius: 1vmin;
        background: #ededed;
    }
    @media (min-width: 2200px){
        .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
        .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
        {
            height: 4vmin;
            line-height: 4vmin;
            font-size: 1.4vmin;
        }
        .queryCondition{
            font-size: 1.4vmin;
        }
        .tabName{
            font-size: 1.8vmin;
            padding: 0.5vmin;
            border-bottom: 0.2vmin solid #357aa1;
        }
        .unitBox{
            height: 10vmin;
            margin: 1vmin auto;
            border-radius: 0.4vmin;
        }
        .title{
            font-size: 2vmin;
            line-height: 2.4vmin;
            padding-top: 2vmin;
        }
        .workDetails{
            font-size: 1.8vmin;
            margin-top: 0.5vmin;
        }
        .crtTime{
            bottom: 0.2vmin;
            right: 0.5vmin;
            font-size: 1.4vmin;
        }
    }
</style>
