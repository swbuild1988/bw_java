<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="4">
                    <span>模块类型：</span>
<<<<<<< HEAD
                    <Input type="text" v-model="conditions.moduleType" placeholder="请输入模块类型" style="width: 56%"></Input>
                </Col>
                <Col span="4">
                    <span>操作人姓名：</span>
                    <Input type="text" v-model="conditions.reqUser" placeholder="请输入操作人姓名" style="width: 56%"></Input>
                </Col>
                <Col span="4">
                    <span>请求结果：</span>
                    <Select v-model="conditions.result"  style="width: 56%">
=======
                    <Input type="text" v-model="conditions.moduleType" placeholder="请输入模块类型" style="width: 60%"></Input>
                </Col>
                <Col span="4">
                    <span>操作人姓名：</span>
                    <Input type="text" v-model="conditions.reqUser" placeholder="请输入操作人姓名" style="width: 60%"></Input>
                </Col>
                <Col span="4">
                    <span>请求结果：</span>
                    <Select v-model="conditions.result"  style="width: 60%">
>>>>>>> origin/dev
                        <Option value="null">所有</Option>
                        <Option v-for="item in result" :key="item.id" :value="item.name">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="4">
                    <span>开始时间：</span>
<<<<<<< HEAD
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 56%" v-model="conditions.beginTime"></DatePicker>
                </Col>
                <Col span="4">
                    <span>结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 56%" v-model="conditions.endTime"></DatePicker>
=======
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.beginTime"></DatePicker>
                </Col>
                <Col span="4">
                    <span>结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime"></DatePicker>
>>>>>>> origin/dev
                </Col>
                <Col span="4">
                    <Button type="primary" size="small" icon="ios-search" @click="queryOperationLog()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Table :columns="columns1" :data="operationLog"></Table>
            <div class="page">
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="position: absolute;bottom: 20px; right: 15px;"></Page>
            </div>
        </div>
    </div>
</template>
<script>
import { OperationLogService } from '../../../../services/operationLogService'

export default {
    data(){
        return{
            conditions:{
                moduleType: null,
                reqUser: null,
                result: null,
                beginTime: null,
                endTime: null
            },
            page:{
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0,
            },
            result:[
                {
                    id: 0,
                    name: '成功'
                },
                {
                    id: 1,
                    name: '失败'
                }
            ],
            operationLog:[],
            columns1: [
                {
                    type: 'index',
                    align: 'center',
                    width: 60
                },
                {
                    title: '模块类型',
                    key: 'moduleType',
                    align: 'center'
                },
                {
                    title: '请求IP',
                    key: 'reqIp',
                    align: 'center'
                },
                {
                    title: '请求人',
                    key: 'reqUser',
                    align: 'center'
                },
                {
                    title: '请求结果',
                    key: 'result',
                    align: 'center'
                },
                {
                    title: '操作描述',
                    key: 'desc',
                    align: 'center',
                    width: 400
                },
                {
                    title: '操作时间',
                    key: 'crtTime',
                    align: 'center'
                },
                {
                    title: '',
                    width: 150,
                    align: "center",
                    render: (h,params)=>{
                        return('div',[
                            h('Button',{
                                props: {
                                    type: "error",
                                    size: "small"
                                },
                                on: {
                                    click: () => {
                                        this.del(params.row.id);
                                    }
                                },
                            },'删除')
                        ])
                    }
                }
            ]    
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                moduleType: this.conditions.moduleType,
                reqUser: this.conditions.reqUser,
                beginTime: new Date(this.conditions.beginTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                result: this.conditions.result
            };
            return Object.assign({}, param);
        }
    },
    mounted(){
        this.queryOperationLog()
    },
    methods:{
        queryOperationLog(){
            let _this = this
            OperationLogService.logDatagrid(_this.params).then(
                (result)=>{
                    if(result.list!=null){
                        for(let index in result.list){
                            result.list[index].crtTime =  new Date(result.list[index].crtTime).format('yyyy-MM-dd hh:mm:s')                      
                        }            
                        _this.operationLog = result.list
                        _this.page.pageTotal = result.total  
                    }
                },
                (error)=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {  
            this.page.pageNum = value 
            this.queryOperationLog()
        },  
        handlePageSize(value) {  
            this.page.pageSize = value  
            this.queryOperationLog()
        },
        del(id) {
            this.$Modal.confirm({
                title: '操作日志',
                content: '<p>是否删除这条操作记录</p>',
                onOk: () => {
                    let _this = this
                    OperationLogService.deleteLog(id).then(
                        (result)=>{
                            _this.operationLog.splice(id, 1);
                        },
                        (error)=>{
                            _this.Log.info(error)
                        })
                    this.queryOperationLog()
                },
                onCancel: () => {}
            })
        },
    }
}
</script>
<style scoped>
    /*.allDiv{
        position: relative;
        min-height: 100%;
        padding-bottom: 60px;
    }*/
    .word25{
        letter-spacing: 0.25em;
        margin-right: -0.25em;
    }
   /* .list{
        background: #ffffff;
        margin-top: 10px;
    }*/
    @media (min-width: 2200px){
        .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
        .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
        {
            height: 4vmin;
            line-height: 4vmin;
            font-size: 1.4vmin;
        }
    }

    .conditions >>> .ivu-input {
        height: 3.2vmin;
        font-size: 1.28vmin;
    }

    .page >>> .ivu-select-selection{
        height: 3.2vmin;
    }
    .page >>> .ivu-select-selected-value{
        font-size: 1.2vmin;
        height: 3vmin;
        line-height: 3vmin;
    }
    .page >>> .ivu-select-placeholder{
        font-size: 1.2vmin;
        height: 3vmin;
        line-height: 3vmin;
    }
    .page >>> .ivu-page-options-elevator input{
        font-size: 1.2vmin;
        height: 3vmin;
    }
    .page >>> .ivu-page-options-elevator{
        display: inline-block;
        height: 3.2vmin;
        line-height: 3.2vmin;
    }
    .page >>> .ivu-page-next{
        height: 3.2vmin;
        line-height: 3vmin;
    }
    .page >>> .ivu-page-next .ivu-icon{
        font-size: 1.6vmin;
    }
    .page >>> .ivu-page-prev{
        height: 3.2vmin;
        line-height: 3vmin;
    }
    .page >>> .ivu-page-prev .ivu-icon{
        font-size: 1.6vmin;
    }

    .conditions >>> .ivu-date-picker-header {
        height: 3.2vmin;
        line-height: 3.2vmin;
    }

    .conditions >>> .ivu-picker-panel-icon-btn{
        font-size: 1.66vmin;
        width: 1.28vmin;
        height: 2.5vmin;
    }

    .conditions >>> .ivu-date-picker-header-label{
        font-size: 1.66vmin;
    }

    @media (min-width: 1921px){
        .conditions >>> .ivu-date-picker-cells {
            width: 15vmin;
            font-size: 1.66vmin;
        }

        .conditions >>> .ivu-date-picker-cells-cell{
            width: 2vmin;
        }
        .conditions >>> .ivu-date-picker-cells-header span{
            padding-right: 2.5rem;
        }
    }
</style>
