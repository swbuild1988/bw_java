<template>
<Tabs value="name1">
    <TabPane label="表格" name="name1">
        <div class="allDiv">
            <Row class="conditions">
                <Col span="6">
                    <span class="conditionTitle">任务进程：</span>
                    <Select v-model="conditions.finished" style="width: 60%" @on-change="queryConditions()">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in patrolResult" :key="index" :value="item.val">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">巡检人：</span>
                    <Input type="text" style="width: 60%" v-model="conditions.name" @on-keyup="queryConditions()"></Input>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入巡检开始时间" style="width: 60%" @on-change="queryConditions()"></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入巡检结束时间" style="width: 60%" @on-change="queryConditions()"></DatePicker>
                </Col>
            </Row>
            <div class="list">           
                <Table  :columns='columns'  :data="patrolTask"></Table>
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
            </div>
        </div>
    </TabPane>
    <TabPane label="超级日历" name="name2">
        <superCalender v-bind="SCalender" v-on:listenToChildEvent="queryOneMonth"></superCalender>
    </TabPane>
</Tabs>
</template>
<script>
import Enum from '../../../../../static/Enum.json'
import superCalender from '../../../Common/SuperCalender.vue'
export default {
    components: {
        superCalender
    },  
    data(){
        return{
            SCalender:{
                tasks:[]
            },
            conditions:{
                finished: null,
                name: null,
                startTime: null,
                endTime: null
            },
            patrolResult:[
                {id: 1, val: 1, key: '已完成'}, 
                {id: 2, val: 0, key: '进行中'}
            ],
            patrolTask:[
                {id:1,planName: '7月巡检计划',taskPerson: '张三', startTime: 1529596800000,endTime: null,describe: '中旬计划，大家认真执行',patrolStatus: '进行中',},
                {id:2,planName: '7月巡检计划',taskPerson: '张三', startTime: null,endTime: 1529596800000,describe: '中旬计划，大家认真执行',patrolStatus: '进行中',},
            ],
            page:{
                pageSize: 10,
                pageNum: 1,
                pageTotal: null
            },
            pageStyle: {
                position: 'absolute',
                bottom: '0px',
                right: '15px'
            },
            columns:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '所属计划',
                    key: 'planName',
                    align: 'center'
                },
                {
                    title: '巡检人',
                    key: 'inspectManName',
                    align: 'center'
                },
                {
                    title: '任务进程',
                    key: 'processStatus',
                    align: 'center'
                },
                {
                    title: '巡检计划时间',
                    key: 'taskTime',
                    align: 'center',
                    render: (h,params) => {
                        let temp = params.row.taskTime
                        if(temp == null){
                            temp = ''
                        }else{
                            temp = new Date(params.row.taskTime).format('yyyy-MM-dd')
                        }
                        return h('div',
                            temp
                        )
                    }
                },
                {
                    title: '巡检开始时间',
                    key: 'startTime',
                    align: 'center',
                    render:(h,params) =>{
                        let temp = params.row.startTime
                        if(temp == null){
                            temp = ''
                        }else{
                            temp = new Date(params.row.startTime).format('yyyy-MM-dd hh:mm:s')
                        }
                        return h('div',
                            temp
                        )
                    }
                },
                {
                    title: '巡检结束时间',
                    key: 'endTime',
                    align: 'center',
                    render:(h,params) =>{
                        let temp = params.row.endTime
                        if(temp == null){
                            temp = ''
                        }else{
                            temp = new Date(params.row.endTime).format('yyyy-MM-dd hh:mm:s')
                        }
                        return h('div',
                            temp
                        )
                    }
                },
                {
                    title: '巡检描述',
                    key: 'describe',
                    align: 'center',
                    width: 200,
                    render:(h,params)=>{
                        let temp = params.row.describe
                        if(temp!=null){
                            if(temp.length<=10){
                                temp = temp
                            }else{
                                temp = temp.substr(0,10)+'...'
                            }
                            return h('div',temp)
                        }
                    }
                },
                {
                    title:"操作",
                    key:"action",
                    align: 'center',
                    width:150,
                    align:'center',
                    render:(h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.show(params.index)
                                    }
                                }
                            }, '查看')
                        ]);
                    }
                }
            ],
            tunnelId: null,
            tunnels:[],
            firstDay: null,
            lastDay: null
        }
    },
    watch:{
        '$route': function () {
          //2. $route发生变化时再次赋值planId
          this.tunnelId = this.$route.params.id;
          this.tunnels.forEach(a => {
            if (a.id == this.tunnelId) {
              this.queryConditions();
            }
          });
        },
    },
    mounted() {
        this.tunnelId = this.$route.params.id;
        // 从数据库读取select的option选项
        this.axios.get("/tunnels ").then(response => {
            let { code, data } = response.data;
            if (code == 200) {
                this.tunnels = data;
            }
        });
        this.queryConditions()
        this.queryOneMonth(null)
    },
    methods:{
        queryConditions(){
                var info = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    startTime: this.conditions.startTime,
                    endTime: this.conditions.endTime,
                    finished: this.conditions.finished,
                    name: this.conditions.name,
                    tunnelId: this.$route.params.id,  
                }
            this.axios.post('/inspection-tasks/all',(info)).then(response=>{
                let{code,data} = response.data
                if(code==200){
                    this.patrolTask = data.list
                    this.page.pageTotal = data.total
                }
            })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryConditions()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryConditions()
        },
        goToMoudle2: function(index, type) {
            this.$router.push({
                name: "UMAddTask",
                params: {
                    id: this.patrolTask[index].id,
                    type: type
                }
            });
        },
        show(index){
            this.goToMoudle2(index,Enum.pageType.Read)
        },
        submit(index){
            this.goToMoudle2(index,Enum.pageType.Submit)
        },
        //传给子组件
        queryOneMonth(data){
            if(data){
                this.firstDay = data.firstDay
                this.lastDay = data.lastDay
            }else{
                var d = new Date()
                this.firstDay = new Date(d.getFullYear(),d.getMonth(),1)
                this.lastDay = new Date(d.getFullYear(),d.getMonth()+1,0)
            }
            var timeInfo = {
                startTime: this.firstDay,
                endTime: this.lastDay
            }
            this.axios.post('/inspection-tasks/one-month',(timeInfo)).then(response=>{
                let{code,data} = response.data
                if(code==200){
                    this.SCalender.tasks = []
                    data.forEach(a=>{
                        var temp = {
                            id : a.id,
                            taskTime : a.taskTime,
                            processStatus : a.processStatus,
                            inspectManName : a.inspectManName
                        }
                        this.SCalender.tasks.push(temp)
                    })
                }
            })
        }
    }
}
</script>
