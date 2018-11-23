<template>
    <!-- 定时任务调度管理  -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px;">定时任务管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>任务筛选：</span>
                <!-- <Input v-model="researchInfo.id" placeholder="输入要查询的任务编号" class="inputWidth"/> -->
                <Select v-model="researchInfo.status" class="inputWidth">
                    <Option v-for="item in statusSelect" :value="item.val" :key="item.key">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="10">
                <Button type="primary" size="small"  icon="ios-search" @click="filterTask(researchInfo.status)">筛选</Button>
                <Button type="error" size="small" @click="addNewTask()">新增任务</Button>  
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
        </div>
        <div>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
                  placement="top" @on-change="handlePage" @on-page-size-change="handlePageSize" show-elevator
                  :style="pageStyle"></Page>
        </div>
        <div>
            <schedule-module v-bind="addScheduleJob" v-on:listenToAdd="saveSchedule"></schedule-module>
        </div>
        <div>
            <schedule-modification v-bind="changeScheduleInfo" v-on:listenToChange="saveChangeSchedule"></schedule-modification>
        </div>
    </div>
</template>

<script>
import ScheduleModule from '../../CM/ScheduleJob/ScheduleModule'
import ScheduleModification from '../../CM/ScheduleJob/ScheduleModification'
export default {
    name: "schedule-job",
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
            columns7:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'index'
                },
                {
                    title: '任务名称',
                    key: 'jobName'
                },
                // {
                //     title: '任务组',
                //     key: 'jobGroup'
                // },
                {
                    title: '任务描述',
                    key: 'description'
                },
                {
                    title: '任务周期',
                    key: 'cronExpression',
                },
                {
                    title: '任务类',
                    key: 'jobClass'
                },
                {
                    title: '任务方法',
                    key: 'jobMethod'
                },
                {
                    title: '是否启用',
                    key: 'jobStatusName',
                    render:(h,params) =>{
                        return h('i-switch',{
                           props: {
                               type: 'primary',
                               size: 'large',
                               value: this.data6[params.index].jobStatusName === '启用'     //1则启用，0则禁用
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
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.changeSchedule(params.index)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            data6:[],
            addScheduleJob:{
                show:{state: false}
            },
            changeScheduleInfo:{
                show:{state:false},
                changeInfo:{}
            },
            deleteShow: false,
            deleteSelect:[]
        }
    },
    mounted(){
        this.showTable();
    },
    computed:{
        pages(){
            let _page = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return Object.assign({}, _page);
        },
        choices(){
            let choice = {
                jobStatus: this.researchInfo.status,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return Object.assign({}, choice);
        },
        params() {
            let param = {
                // pageNum: this.page.pageNum,
                // pageSize: this.page.pageSize,
                jobName: this.formValidate.jobName,
                jobGroup: this.formValidate.jobGroup,
                cronExpression: this.formValidate.cronExpression,
                jobClass: this.formValidate.jobClass,
                jobStatus:this.formValidate.jobStatus,
                jobMethod: this.formValidate.jobMethod,
                description: this.formValidate.description
            };
            return Object.assign({},param);
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
                    this.showTable();
                }
            })
        },
        showTable(){
            this.axios.post('/schedulejobs/datagrid',(this.pages)).then(res =>{
                let {code,data} = res.data;
                if (code == 200){
                    let allinfo = [];
                    for (let index in data.list){
                        let info = {};
                        info.jobId = data.list[index].jobId;
                        info.jobName = data.list[index].jobName;
                        info.jobGroup = data.list[index].jobGroup;
                        info.jobClass = data.list[index].jobClass;
                        info.jobMethod = data.list[index].jobMethod;
                        info.cronExpression = data.list[index].cronExpression;
                        info.description = data.list[index].description;
                        info.jobStatusName = data.list[index].jobStatusName;
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            })
        },
        addNewTask(){
            this.addScheduleJob.show.state = !this.addScheduleJob.show.state;
        },
        saveSchedule(_data){
            this.formValidate = _data;
            this.axios.post('/schedulejobs',(this.params)).then(res => {
                let {code,data} = res.data;
                if(code == 200){
                    this.showTable();
                    this.page.pageTotal = data.total;
                    this.$Message.success('添加成功！');
                    this.addScheduleJob.show.state = !this.addScheduleJob.show.state;
                }
            })
        },
        handlePage(value){
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value){
            this.page.pageSize = value;
            this.showTable();
        },
        changeSchedule(index){
            this.changeScheduleInfo.changeInfo = this.data6[index];
            this.formValidate.jobId = this.data6[index].jobId;
            this.changeScheduleInfo.show.state = !this.changeScheduleInfo.show.state;
        },
        saveChangeSchedule(data){
            this.formValidate = data;
            this.axios.put('/schedulejobs',(this.modifications)).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.changeScheduleInfo.show.state = !this.changeScheduleInfo.show.state;
                    this.$Message.success('修改成功!');
                }
            })
        },
        // deleteSchedule(index){
        //     this.$Modal.confirm({
        //         title: '删除确认',
        //         content: '<p>确认要删除此条信息吗？</p>',
        //         onOk: () => {
        //             this.axios.delete('/schedulejobs/' + this.data6[index].jobId).then(res =>{
        //                 let {code,data} = res.data;
        //                 if(code == 200){
        //                     this.$Message.info('已删除');
        //                     this.showTable();
        //                 }
        //             })
        //         },
        //         onCancel: () => {
        //             this.$Message.info('已取消操作');
        //         }
        //     });
        // },
        filterTask(val){
            this.axios.post('/schedulejobs/datagrid',(this.choices)).then(res =>{
                let {code,data} = res.data;
                if (code == 200){
                    let allinfo = [];
                    for (let index in data.list){
                        let info = {};
                        info.jobId = data.list[index].jobId;
                        info.jobName = data.list[index].jobName;
                        info.jobGroup = data.list[index].jobGroup;
                        info.jobClass = data.list[index].jobClass;
                        info.jobMethod = data.list[index].jobMethod;
                        info.cronExpression = data.list[index].cronExpression;
                        info.description = data.list[index].description;
                        info.jobStatusName = data.list[index].jobStatusName;
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            })
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
                            this.showTable();
                        }
                    })  
                    console.log(ids);  
                },
                onCancel: () => {
                    this.$Message.info('已取消操作');
                    this.showTable();
                }
            });
        }, 
    },
    components:{
        ScheduleModule,ScheduleModification
    }
}
</script>

<style scoped>
.inputWidth{
    width: 60%;
}
</style>


