<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="6">
                    流程类型：
                    <Select style="width:60%" v-model="conditions.processType" @on-change="queryMyApplication()">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in processType" :value="item.val" :key="index">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime" @on-change="queryMyApplication()"></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime" @on-change="queryMyApplication()"></DatePicker>
                </Col>
            </Row>
        </div>
        <Table stripe border :columns="columns1" :data="myApplication"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="position: absolute;bottom: 20px;right: 15px"></Page>   
    </div>    
</template>
<script>
import Enum from "../../../../../static/Enum.json";
export default {
    data(){
        return{
            id: null,
            columns1:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '申请类型',
                    key: 'processTypeName',
                    width: 150,
                    align: 'center'
                },
                {
                    title: '审批人',
                    key: 'approver',
                    align: 'center',
                    render: (h,params) => {
                        return h('div',params.row.approver)
                    }
                },
                {
                    title: '申请时间',
                    key: 'crtTime',
                    align: 'center',
                    render: (h,params)=>{
                        return h('div',new Date(params.row.crtTime).format('yyyy-MM-dd hh:mm:s'))
                    }
                },
                {
                    title: "申请状态",
                    key: "statusName",
                    align: "center",
                    render: (h, params) => {
                        let tmpStr = "";
                        if (params.row.status == 3) {
                        tmpStr = "拒绝";
                        } else if (params.row.status == 2) {
                        tmpStr = "通过";
                        } else if(params.row.status == 1){
                        tmpStr = "审核中";
                        }else{
                        tmpStr = "结束";
                         }
                        return h("span",
                        {
                            style: {
                            color:
                                params.row.status == 3 ? "#ff6600"
                                : params.row.status == 2 ? "#19be6b" 
                                : params.row.status == 1 ? "#2d8cf0" : "#a005fdb3"
                            }
                        },
                        tmpStr
                        );
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        let processDefinitionId = null
                        let type 
                        let processType = ''
                        if(params.row.processType==2){
                            if(params.row.status==1){
                                type=Enum.examineStatus.examining
                            }else if(params.row.status==2){
                                type=Enum.examineStatus.agree
                            }else if(params.row.status==3){
                                type=Enum.examineStatus.disagree
                            }else{
                                type=Enum.examineStatus.end
                            }
                            processDefinitionId = params.row.processDefinitionId
                            processType = params.row.processType
                            return h('Button', {
                                props: {
                                    type: "primary",
                                    // size: "small"
                                },
                                on: {
                                    click: () => {
                                            this.goToMoudle2(processDefinitionId,processType,type)
                                        }
                                    }
                                }, '查看入廊申请');
                        }else if(params.row.processType==1){
                            let processDefinitionId = null
                            let type 
                            let processType = ''
                            if(params.row.status==1){
                                type=Enum.examineStatus.examining
                            }else if(params.row.status==2){
                                type=Enum.examineStatus.agree
                            }else if(params.row.status==3){
                                type=Enum.examineStatus.disagree
                            }else{
                                type=Enum.examineStatus.end
                            }
                            processDefinitionId = params.row.processDefinitionId
                            processType = params.row.processType
                            return h('Button', {
                                props: {
                                    type: "primary",
                                    // size: "small"
                                },
                                on: {
                                    click: () => {
                                            this.goToMoudle1(processDefinitionId,processType,type)
                                        }
                                    }
                                }, '查看巡检信息');
                        }
                    }
                }
            ],
            myApplication:[
                {id:1,processTypeName: '入廊申请',approver: 'admin',crtTime: 1529596800000,statusName:'拒绝' }
            ],
            conditions:{
                processType: null,
                startTime: null,
                endTime: null
            },
            page:{
                pageNum: 1,
                pageSize: 10
            },
            processType:[],
            requestStatus: []
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                processType: this.conditions.processType,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime()
            };
            return Object.assign({}, param);
        }
    },
    mounted() {
        this.axios.get('processtype-enums').then(response=>{
            let{code,data} = response.data
            if(code==200){
                this.processType = data
            }
        })
        this.axios.get('reqstatus-enums').then(response=>{
            let{code,data} = response.data
            if(code==200){
                this.requestStatus = data
            }
        })
        this.queryMyApplication()
    },
    methods: {
        queryMyApplication(){
            users/activiti/task/datagrid
            this.axios.post('/users/activiti/apply/datagrid',(this.params)).then(response=>{
                let{ code,data } = response.data
                if(code==200){
                    this.myApplication = data.list
                }
            })
        },
        goToMoudle2: function(processDefinitionId, processType,type) {
            this.$router.push({
                name: 'UMApplicationExamineApprove',
                params: {
                    type: type,
                    processDefinitionId: processDefinitionId,
                    processType: processType
                },
            });
        },
        goToMoudle1: function(processDefinitionId, processType,type) {
            this.$router.push({
                name: 'UMApplicationExaminePlans',
                params: {
                    type: type,
                    processDefinitionId: processDefinitionId,
                    processType: processType
                }
            });
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryMyApplication();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryMyApplication();
        },
    }
}
</script>

