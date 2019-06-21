<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="6">
                <span class="conditionTitle">流程状态：</span>
                <Select v-model="conditions.finished" style="width:60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in workOrderStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%" @on-change="conditions.startTime=$event"></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%" @on-change="conditions.startTime=$event"></DatePicker>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="planColumus" :data="planData"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        </div>
    </div>
</template>
<script>
import Enum from "../../../../../static/Enum.json";
import { PatrolService } from '../../../../services/patrolService';
export default {
    data(){
        return{
            conditions: {
                finished: null,
                startTime: null,
                endTime: null,
            },
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            pageStyle: {
                position: 'absolute',
                bottom: '2vmin',
                right: '2.5vmin'
            },
            timeType: null,
            planColumus: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '计划名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    key: 'tunnelName',
                    align: 'center'
                },
                {
                    title: '流程状态',
                    key: 'processStatus',
                    align: 'center'
                },
                {
                    title: '责任班组',
                    key: 'groupName',
                    align: 'center'
                },
                {
                    title: '计划描述',
                    align: 'center',
                    key: 'remark'
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        return h('Button', {
                            props: {
                                type: 'primary',
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                    this.enterDetails(params.row.planId)
                                }
                            }
                        }, '详情')
                    }
                }
            ],
            planData: [],
            workOrderStatus: Enum.patrolPlanOrderStatus
        }
    },
    mounted(){
        if(this.$route.params.timeType==1){
            this.conditions.startTime = new Date().getFullYear()+"-01-01T00:00:00.000Z"
            this.conditions.endTime = new Date().getFullYear()+"-12-31T23:59:59.000Z"
        }else if(this.$route.params.timeType==2){
            this.conditions.startTime = this.getCurrentMonthFirst()
            this.conditions.endTime = this.getCurrentMonthLast()
        }
        this.getPlanData()
    },
    methods: {
        getPlanData(){
            var params = {
                finished: this.conditions.finished,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            }
            PatrolService.patrolPlanDatagrid(params).then(
                result => {
                    this.planData = result.list
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.getPlanData();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.getPlanData();
        },
        //获取当前月份的第一天
        getCurrentMonthFirst(){
            var date = new Date();
            date.setDate(1);
            var month = parseInt(date.getMonth()+1);
            var day = date.getDate();
            if (month < 10) {
                month = '0' + month
            }
            if (day < 10) {
                day = '0' + day
            }
            return date.getFullYear() + '-' + month + '-' + day + "T00:00:00.000Z";
        },
        // 获取当前月份的最后一天
        getCurrentMonthLast(){
            var date=new Date();
            var currentMonth=date.getMonth();
            var nextMonth=++currentMonth;
            var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
            var oneDay=1000*60*60*24;
            var lastTime = new Date(nextMonthFirstDay-oneDay);
            var month = parseInt(lastTime.getMonth()+1);
            var day = lastTime.getDate();
            if (month < 10) {
                month = '0' + month
            }
            if (day < 10) {
                day = '0' + day
            }
            return date.getFullYear() + '-' + month + '-' + day +"T16:59:59.000Z";
        },
        //查看详情
        enterDetails(id){
            this.$router.push({
                name: "查看巡检计划详情",
                params: {
                    id: id,
                    type: Enum.pageType.Read
                }
            });
        }
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
