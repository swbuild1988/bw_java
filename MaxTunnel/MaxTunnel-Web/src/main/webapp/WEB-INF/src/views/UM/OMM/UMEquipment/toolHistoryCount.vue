<template>
    <Row>
        <Col span="24">
            <Row class="queryCondition" style="line-height: 37px;padding-left: 5px;">
                <Col span="4">
                    <span class="conditionTitle">仪表工具名称</span><span class="conditionTitle">：</span>
                    <Input type="text" v-model="toolConditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">仪表工具类型：</span>
                    <Select v-model="toolConditions.typeId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">仪表工具型号</span><span class="conditionTitle">：</span>
                    <Select v-model="toolConditions.modelId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="4">
                    <span class="word63 conditionTitle">借用人</span><span class="conditionTitle">：</span>
                    <Input type="text" v-model="toolConditions.staffId" style="width: 60%"></Input>
                </Col>
                <Col span="4">
                    <span class="word63 conditionTitle">归还人</span><span class="conditionTitle">：</span>
                    <Input type="text" v-model="toolConditions.returnId" style="width: 60%"></Input>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">使用状态</span><span class="conditionTitle">：</span>
                    <Select v-model="toolConditions.useStatus" style="width: 60%">
                        <Option value=null key="2">所有</Option>
                        <Option v-for="item in usingStatus" :key="item.key" :value="item.key">{{item.val}}</Option>
                    </Select>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">借用开始时间</span><span class="conditionTitle">：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" v-model="toolConditions.startTime" style="width: 60%"></DatePicker>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">借用结束时间</span><span class="conditionTitle">：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" v-model="toolConditions.endTime" style="width: 60%"></DatePicker>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">归还开始时间</span><span class="conditionTitle">：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" v-model="toolConditions.retStartTime" style="width: 60%"></DatePicker>
                </Col>
                <Col span="4">
                    <span class="conditionTitle">归还结束时间</span><span class="conditionTitle">：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" v-model="toolConditions.retEndTime" style="width: 60%"></DatePicker>
                </Col>
                <Col span="4">
                    <Button type="primary" size="small" icon="ios-search" @click="showTable()">查询</Button>
                </Col>
            </Row>
            <div>
                <Table stripe border :height="tableHieght" :columns="toolColums"  :data="toolData"></Table>
                <div class="pageContainer" ref="pageContainer">
                    <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" 
                    show-elevatorn show-total show-sizer @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
                </div>
            </div>
        </Col>
        <Col span="12" class="chartBox">
            <MultiBarChart v-bind="toolStatic"></MultiBarChart>
        </Col>
        <Col span="12" class="chartBox" style="margin-left: 5px;">
            <MulitBarPosiNega v-bind="inventory"></MulitBarPosiNega>
        </Col>
    </Row>    
</template>
<script>
import MultiBarChart from '../../../../components/Common/Chart/MultiBarChart.vue'
import MulitBarPosiNega from '../../../../components/Common/Chart/MulitBarPosiNega.vue'
import { EquipmentService } from "../../../../services/equipmentService"
export default {
    data(){
        return{
            toolColums:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '仪表工具名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '仪表工具类型',
                    key: 'typeName',
                    align: 'center'
                },
                {
                    title: '仪表工具型号',
                    key: 'modelName',
                    align: 'center'
                },{
                    title: '仪表工具使用状态',
                    key: 'useStatusName',
                    align: 'center'
                },
                {
                    title: '入库时间',
                    key: 'inTime',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.inTime!=null){
                            temp = new Date(param.row.inTime).format("yyyy-MM-dd hh:mm:s")
                        }
                        return h('div',temp)
                    }
                },
                {
                    title: '供应商',
                    key: 'venderName',
                    align: 'center'
                },
                {
                    title: '借用人',
                    key: 'staffName',
                    align: 'center',
                },
                {
                    title: '借出时间',
                    key: 'borrowTime',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.borrowTime!=null){
                            temp = new Date(param.row.borrowTime).format("yyyy-MM-dd hh:mm:s")
                        }
                        return h('div',temp)
                    }
                },
                { 
                    title: '借用时备注',
                    key: 'describe',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.describe!=null){
                            if(param.row.describe.length>10){
                                temp = param.row.describe.substr(0,7)+'...'
                            }else{
                                temp = param.row.describe
                            }
                        }
                        return h(
                            'div',
                            {
                                domProps: {
                                    title: param.row.describe
                                },
                            },
                            temp
                        )
                    }
                },
                {
                    title: '归还人',
                    key: 'returnName',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.returnName==null){
                            temp = '暂未归还'
                        }else{
                            temp = param.row.returnName
                        }
                        return h('div',temp)
                    }
                },
                {
                    title: '归还时间',
                    key: 'returnTime',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.retStartTime==null){
                            temp = '暂未归还'
                        }else{
                            temp = new Date(param.row.borrowTime).format("yyyy-MM-dd hh:mm:s")
                        }
                        return h('div',temp)
                    }
                },
                {
                    title: '归还时备注',
                    key: 'remark',
                    align: 'center',
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.remark!=null){
                            if(param.row.remark.length>10){
                                temp = param.row.remark.substr(0,7)+'...'
                            }else{
                                temp = param.row.remark
                            }
                        }
                        return h('div',temp)
                    }
                }
            ],
            toolData: [
                { id: 1, toolName: '门禁卡', takingTime: '' , takingPerson: '张三', takingNum: '10', expectReturnTime: '', operation: 'admin' },
                { id: 2, toolName: '门禁卡', takingTime: '' , takingPerson: '张三', takingNum: '10', expectReturnTime: '', operation: 'admin' },
                { id: 3, toolName: '门禁卡', takingTime: '' , takingPerson: '张三', takingNum: '10', expectReturnTime: '', operation: 'admin' },
                { id: 4, toolName: '门禁卡', takingTime: '' , takingPerson: '张三', takingNum: '10', expectReturnTime: '', operation: 'admin' },
                { id: 5, toolName: '门禁卡', takingTime: '' , takingPerson: '张三', takingNum: '10', expectReturnTime: '', operation: 'admin' }
            ],
            page:{
                pageSize: 6,
                pageTotal: 0,
                pageNum: 1
            },
            //仪表工具使用状态
            usingStatus: [
                { key: 0, val: '损坏' },
                { key: 1, val: '正常' }
            ],
            toolConditions:{
                name: null,
                typeId: null,
                modelId: null,
                staffId: null,
                startTime: null,
                endTime: null,
                returnId: null,
                useStatus: null,
                retStartTime: null,
                retEndTime: null
            },
            toolStatic: {
                id: 'toolStaticId',
                requestUrl: 'instruments/usestatus/types',
                title: '仪表工具状态',
                parameters: {
                    option: {
                        backgroundColor: '#E8E8FF',
                        color: ['#006699', '#e5323e'],
                        title: {
                            text: "仪表工具状态",
                            textStyle: {
                                color: "#161139"
                            }
                        }
                    },
                }
            },
            inventory: {
                id: 'inventoryId',
                requestUrl: 'instruments/status/types',
                title: '仪表工具库存状态',
                legendData: ['在库','出库'],
                parameters: {
                    option: {
                        backgroundColor: '#FBFBEA',
                        title: {
                            text: '仪表工具库存状态',
                            textStyle: {
                                color: '#161139'
                            }
                        },
                        legend: {
                            data: ['在库','出库'],
                            textStyle: {
                                color: '#161139'
                            }
                        },
                    },
                    timer: {
                        interval: 5000
                    }
                }
            },
            toolsType: [],
            toolsModel: [],
            venders: [],
            staffs: [],
            tableHieght: null
        }
    },
    components: {
        MultiBarChart,
        MulitBarPosiNega
    },
    computed:{
        hisParams(){
            let param = {
                name: this.toolConditions.name,
                typeId: this.toolConditions.typeId,
                modelId: this.toolConditions.modelId,
                venderId: this.toolConditions.venderId,
                useStatus: this.toolConditions.usingStatusId,
                startTime: this.toolConditions.startTime,
                endTime: this.toolConditions.endTime,
                returnId: this.toolConditions.returnId,
                retStartTime: this.toolConditions.retStartTime,
                retEndTime: this.toolConditions.retEndTime,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            }
            return Object.assign({}, param);
        },
    },
    mounted(){
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.toolsType = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.toolsModel = res
            },
            error => {
                this.Log.info(error)
            }
        )
        //获取供应商
        EquipmentService.getVenders().then(
            res=>{
                this.venders = res
            },
            error=>{
                this.Log.info(error)
            }
        )
        //获取借用人列表
        this.axios.get('staffs').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.staffs = data
            }
        })
        this.showTable()
        this.getTableHeight()
    },
    methods:{
        showTable(){
            EquipmentService.queryHisRecord(this.hisParams).then(
                result => {
                    this.toolData = result.pagedList
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable()
        },
        getTableHeight(){
            this.tableHieght = document.body.offsetHeight-((document.body.offsetHeight/100)*64)-this.$refs.pageContainer.offsetHeight-30
        }
    }
}
</script>
<style scoped>
.pageContainer{
    margin-top: 5px;
    text-align: right;
}
.word64{
    letter-spacing: 0.667em;
    margin-right: -0.667em;
}
.word63{
    letter-spacing: 1.5em;
    margin-right: -1.5em;
}
.chartBox{
    width: 49.5%;
    height: 40vh;
    margin: 5px 5px 5px 0px;
}
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
@media (min-width: 2200px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 3.5vmin;
        line-height: 3.5vmin;
        font-size: 1.4vmin;
    }
    .queryConditions{
        font-size: 1.3vmin;
        height: 8.2vh;
        line-height: 4.1vh; 
    }
}
</style>
