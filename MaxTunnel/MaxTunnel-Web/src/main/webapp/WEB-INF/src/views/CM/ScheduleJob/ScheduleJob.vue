<template>
    <!-- 定时任务调度管理  -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px;">定时任务管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>任务筛选：</span>
                <Select v-model="researchInfo.status" class="inputWidth">
                    <Option v-for="item in statusSelect" :value="item.val" :key="item.key">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="10">
                <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch()">筛选</Button>
                <Button type="error" size="small" @click="addNewTask()">新增任务</Button>  
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="schedulejobColumns" :data="schedulejobData" @on-selection-change="startdelete"></Table>
        </div>
        <div>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
                  placement="top" @on-change="handlePage" @on-page-size-change="handlePageSize" show-elevator
                  :style="pageStyle"></Page>
        </div>
        <div>
            <schedule-module ref="scheduleModule" v-bind="ScheduleJobInfo" v-on:addScheduleJob="saveSchedule" v-on:editScheduleJob="saveChangeSchedule"></schedule-module>
        </div>
    </div>
</template>

<script>
import ScheduleModule from '../../CM/ScheduleJob/ScheduleModule'
import {SchedulejobService} from '@/services/schedulejobService'
import CycleTime from '../../../components/Common/CycleTime'
export default {
    name: "schedule-job",
    components:{
        ScheduleModule,
        CycleTime
    },
    data(){
        return {
            page:{
                pageNum: 1,
                pageTotal: 0,
                pageSize: 6
            },
            pageStyle:{
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            },
            researchInfo:{
                status:null
            },
            statusSelect:[
                { val:"null",key:"不限"},
                { val:0, key:"禁用"},
                { val:1, key:"启用"}
            ],
            formValidate:{
                jobId:-1,
                jobName:'',
                jobGroup:'',
                cronExpression:'',
                jobClass:'',
                jobMethod:'',
                description:''
            },
            schedulejobColumns:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '任务名称',
                    key: 'jobName',
                    align: 'center'
                },
                // {
                //     title: '任务类型',
                //     key: 'description',
                //     align: 'center'
                // },
                {
                    title: '调度表达式',
                    key: 'cronExpression',
                    align: 'center',
                    render: (h,params)=>{
                        return h(CycleTime,{
                            props:{
                                displayData: params.row.cronExpression
                            }
                        })
                    }
                },
                {
                    title: '是否启用',
                    key: 'jobStatusName',
                    align: 'center',
                    width: 120,
                    render:(h,params) =>{
                        return h('i-switch',{
                           props: {
                               type: 'primary',
                               size: 'large',
                               value: this.schedulejobData[params.index].jobStatusName === '启用'     //1则启用，0则禁用
                           },
                           on:{
                               'on-change':(value) => {
                                   this.switch(params.row)
                               }
                           }
                        })
                    }
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 80,
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.changeSchedule(params.row.jobId)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            schedulejobData:[],
            ScheduleJobInfo:{
                show:{state: false},
                type: 1,
                id: null
            },
            // changeScheduleInfo:{
            //     show:{state:false},
            //     changeInfo:{}
            // },
            deleteShow: false,
            deleteSelect:[]
        }
    },
    mounted(){
        this.resetPageSearch();
    },
    computed:{
        choices(){
            let choice = {
                jobStatus: this.researchInfo.status,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return Object.assign({}, choice);
        },
        modifications(){
            let modification = {
                jobId: this.formValidate.jobId,
                jobName: this.formValidate.jobName,
                jobGroup: this.formValidate.jobGroup,
                jobClass: this.formValidate.jobClass,
                jobMethod: this.formValidate.jobMethod,
                cronExpression: this.formValidate.cronExpression,
                description: this.formValidate.description
            };
            return Object.assign({},modification);
        }
    },
    methods:{
        switch(job){
            this.Log.info(job)
            let jobStatus = job.jobStatusName == '禁用' ? 0 : 1
            this.axios.get('/schedulejobs/'+ job.jobId +'/jobstatus/'+ (1-jobStatus)).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.resetPageSearch();
                }
            })
        },
        showTable(){
            SchedulejobService.querySchedule(this.choices).then(
                result => {
                    this.schedulejobData = result.list
                    this.page.pageTotal = result.total;
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value){
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value){
            this.page.pageSize = value;
            this.resetPageSearch();
        },
        addNewTask(){
            this.ScheduleJobInfo.show.state = true;
            this.ScheduleJobInfo.type = 1
        },
        saveSchedule(){
            this.$Message.success('添加成功！');
            this.resetPageSearch();
            this.ScheduleJobInfo.show.state = false;
        },
        changeSchedule(id){
            this.$refs.scheduleModule.getScheduleJob(id)
            this.ScheduleJobInfo.show.state = true;
            this.ScheduleJobInfo.type = 2
        },
        saveChangeSchedule(data){
            this.ScheduleJobInfo.show.state = false;
            this.resetPageSearch();
            this.$Message.success('修改成功!');
        },
        startdelete(selection){
            if(selection.length != 0){
                this.deleteShow = true;
                this.deleteSelect = selection;
            }else{
                this.deleteShow = false;
            }
        },
        alldelete(){
            this.$Modal.confirm({
                title: '删除确认',
                content: '<p>确认要删除选中的信息吗？</p>',
                onOk: () => {
                    let ids=this.deleteSelect[0].jobId;
                    for(let i=1;i<this.deleteSelect.length;i++){
                        ids += "," + this.deleteSelect[i].jobId;
                    }
                    this.axios.delete('/schedulejobs/batch/' + ids).then(res =>{
                        let {code,data} = res.data;
                        if (code == 200){
                            this.$Message.info('已删除');
                            this.resetPageSearch();
                        }
                    })  
                    console.log(ids);  
                },
                onCancel: () => {
                    this.$Message.info('已取消操作');
                    this.resetPageSearch();
                }
            });
        }, 
        resetPageSearch(){
            this.page.pageNum = 1;
            this.showTable()
        }
    }
}
</script>

<style scoped>
.inputWidth{
    width: 60%;
}
</style>


