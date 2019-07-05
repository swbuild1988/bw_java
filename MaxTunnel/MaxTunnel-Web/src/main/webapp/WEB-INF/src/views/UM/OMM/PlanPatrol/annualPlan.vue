<template>
<div class="allDiv">
    <Row class="conditions">
        <Col span="6">
            <span class="conditionTitle">计划名称：</span>
            <Input v-model="conditions.name" style="width: 60%"></Input>
        </Col>
        <Col span="6">
            <span class="conditionTitle">所属管廊：</span>
            <Select v-model='conditions.tunnelId' style="width: 60%">
                <Option key="0" value=null>所有</Option>
                <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </Col>
        <Col span="6">
            <span class="conditionTitle">巡检开始时间：</span>
            <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入巡检开始时间" style="width: 60%"></DatePicker>
        </Col>
        <Col span="6">
            <span class="conditionTitle">巡检结束时间：</span>
            <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入巡检开始时间" style="width: 60%"></DatePicker>            
        </Col>
    </Row>
    <div class="list">
        <Table :columns='columns'  :data="plansData"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
        placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
    </div>
</div>    
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService';
export default {
    data(){
        return{
            conditions:{
                name: null,
                tunnelId: null,
                startTime: null,
                endTime: null
            },
            columns: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '计划名称',
                    key: 'planName',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    key: 'tunnelName',
                    align: 'center'
                },
                {
                    title: '计划状态',
                    key: 'processStatus',
                    align: 'center'
                },
                {
                    title: '责任人',
                    key: 'leaderName',
                    align: 'center'
                },
                {
                    title: '巡检日期',
                    key: 'inspectTime',
                    align: 'center',
                    render: (h,param) => {
                        let temp = new Date(params.row.inspectTime).format('yyyy-MM')
                        return h('div',temp)
                    }
                },
                {
                    title: '操作',
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
            plansData: [
                { id: 1, planName: '年末巡检计划', tunnelName: '古城大街', processStatus: '未完成', leaderName: '张三', inspectTime: '2018-06-17' }
            ],
            page:{
                pageTotal: 0,
                pageNum: 1,
                pageSize: 10
            },
            pageStyle:{
                position: 'absolute',
                bottom: '2vmin',
                right: '2.5vmin'
            },
            tunnels: []
        }
    },
    mounted(){
        // 获取所有的管廊
        TunnelService.getTunnels().then(
            (result)=>{
                this.tunnels = result;
            },
            (error)=>{
                this.Log.info(error)
            }
        )
    },
    methods:{
        queryConditions(){
            let info = {
                name: this.conditions.name,
                tunnelId: this.conditions.tunnelId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            }
            this.axios.post('',info).then( res => {
                let { code,data } = res.data
                if( code==200 ){
                    this.plansData = data
                }
            } )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryConditions()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryConditions()
        },
        //跳转至详情
        goToMoudle: function(id) {
            this.$router.push({
                name: "计划详情",
                params: {
                    id: id,
                }
            });
        },
        show(id){
            this.goToMoudle(id)
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
