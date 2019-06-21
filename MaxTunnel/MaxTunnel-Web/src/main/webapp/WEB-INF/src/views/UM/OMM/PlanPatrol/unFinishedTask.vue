<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="6">
                <span>所属计划：</span>
                <Input type="text" style="width: 60%" v-model="conditions.planName"></Input>
            </Col>
            <Col span="6">
                <span>巡检人：</span>
                <Input type="text" style="width: 60%" v-model="conditions.name"></Input>
            </Col>
        </Row>
        <div class="list">
            <Table :columns='columns'  :data="finishedTaskData"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        </div>
    </div>
</template>
<script>
import Enum from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            conditions: {
                planName: null,
                name: null,
                isFinished: null
            },
            columns: [
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
                // {
                //     title: '任务进程',
                //     key: 'processStatus',
                //     align: 'center'
                // },
                {
                    title: '计划巡检时间',
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
                                        this.show(params.row.id)
                                    }
                                }
                            }, '查看')
                        ]);
                    }
                }
            ],
            finishedTaskData: [
                { id: 1, planName: '2018年年末大检查123', inspectManName: '张三', taskTime: 1529596800000, startTime: 1529596800000, endTime: 1529596800000, describe: '一切正常'}
            ],
            page: {
                pageTotal: 0,
                pageSize: 10,
                pageNum: 1
            },
            pageStyle: {
                position: 'absolute',
                bottom: '2vmin',
                right: '2.5vmin'
            },
        }
    },
    mounted(){
        this.conditions.isFinished = this.$route.params.isFinished
        this.queryConditions()
    },
    methods:{
        queryConditions(){
            let info = {
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum,
                planName: this.conditions.planName,
                name: this.conditions.name,
                finished: null,
            }
            this.axios.post('',info).then( response => {
                let { code,data } = response.data
                if(code==200){
                    this.finishedTaskData = data
                }
            })
        },
        handlePage(value) {
            this.page.pageNum = value;
        },
        handlePageSize(value) {
            this.page.pageSize = value;
        },
        //跳转至详情
        goToMoudle2: function(id, type) {
            this.$router.push({
                name: "添加巡检任务",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        show(index){
            this.goToMoudle2(index,Enum.pageType.Read)
        },
    }
}
</script>
<style scoped>
.list .ivu-table-wrapper>>>.ivu-table{
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.list .ivu-table-wrapper>>>.ivu-table:before,.list .ivu-table-wrapper>>>.ivu-table:after{
    background-color: #fffdfd00 !important;
}
.list .ivu-table-wrapper>>>.ivu-table th,.ivu-table-wrapper>>>.ivu-table td{
    background-color: #fffdfd00 !important;
    border-bottom: none;
}
.list .ivu-table-wrapper>>>.ivu-btn-primary,.ivu-table-wrapper>>>.ivu-btn-info{
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none
}
.ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
    color: #fff;
}
</style>


