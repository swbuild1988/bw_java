<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="4">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime"></DatePicker>
            </Col>
            <Col span="4">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime"></DatePicker>
            </Col>
            <Col span="4">
                <Button type="primary" icon="ios-search"  @click="getList()">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="columns1" :data="overhaulData" :height="tableHeight"></Table>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
        placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
    </div>
</template>
<script>
import Enum from '../../../../../static/Enum.json'
import { TunnelService } from '../../../../services/tunnelService'
import { OverhaulService } from '../../../../services/overhaulService'
export default {
    data(){
        return{
            conditions:{
                startTime: null,
                endTime: null,
            },
            pageStyle: {
                position: 'absolute',
                bottom: '1vmin',
                right: '2.5vmin'
            },
            page:{
                pageTotal: null,
                pageSize: 10,
                pageNum: 1
            },
            overhaulData:[
                {id:0, defectName: '一区燃气仓漏气', overhaulPerson: '张三', defectId: 1, defectName: '凤岭北路电力故障', startTime: 1529596800000, endTime:1529596800000,  overhaulResultId: 1 },
                {id:1, defectName: '四区污水仓管道腐蚀渗水', overhaulPerson: '张三', defectId: 1, defectName: '凤岭北路电力故障', startTime: 1529596800000, endTime:1529596800000, overhaulResultId: 1 }
            ],
            columns1:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                // {
                //     title: '所属区段',
                //     key: 'defectName',
                //     align: 'center'
                // },
                // {
                //     title: '所属管仓',
                //     key: 'defectName',
                //     align: 'center'
                // },
                {
                    title: '所属缺陷',
                    key: 'defectName',
                    align: 'center'
                },
                {
                    title: '维修人',
                    key: 'workerName',
                    align: 'center'
                },
                {
                    title: '工单进程',
                    key: 'processStatus',
                    align: 'center'
                },
                {
                    title: '维修开始时间',
                    key: 'overhaulTime',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.startTime
                        if(temp==null){
                            temp = ''
                        }else{
                            temp = new Date(params.row.startTime).format("yyyy-MM-dd hh:mm:s")
                        }
                        return h(
                        "div",temp
                        );
                    }
                },
                {
                    title: '维修结束时间',
                    key: 'overhaulTime',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.endTime
                        if(temp==null){
                            temp = ''
                        }else{
                            temp = new Date(params.row.endTime).format("yyyy-MM-dd hh:mm:s")
                        }
                        return h(
                        "div",temp
                        );
                    }
                },
                {
                    title: '操作',
                    width: 300,
                    align: 'center',
                    render: (h, params) => {
                        return h("div", [
                        h(
                            "Button",
                            {
                            props: {
                                type: "primary",
                                size: "small"
                            },
                            style: {
                                marginRight: "5px"
                            },
                            on: {
                                click: () => {
                                    this.show(params.row.id);
                                }
                            }
                            },
                            "详情"
                        ),
                        ]);
                    }
                },
            ],
            tunnelId: null,
            tunnels:[],
            tableHeight: 600
        }
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                tunnelId: this.tunnelId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime
            };
        return Object.assign({}, param);
        }
    },
    watch:{
        '$route': function () {
            //2. $route发生变化时再次赋值planId
            this.tunnelId = this.$route.params.id;
            this.tunnels.forEach(a => {
                if (a.id == this.tunnelId) {
                    this.getList();
                }
            });
        },
    },
    mounted(){
        this.tunnelId = this.$route.params.id
        this.tableHeight = window.innerHeight/100*67
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        this.getList()
    },
    methods:{
        //获取list
        getList(){
            let _this = this
          if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
            _this.$Message.error('开始时间必须小于结束时间！');
            return;
          }
            OverhaulService.ordersDatagrid(this.params).then(
                (result)=>{
                    _this.overhaulData = result.list
                    _this.page.pageTotal = result.total
                },
                (error)=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.getList()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.getList()
        },
        goToModule(id,type){
            this.$router.push({
                name: '检修详情',
                params: {
                    id: id,
                    type: type
                }
            })
        },
        //查看工单详情
        show(id){
            this.goToModule(id,Enum.pageType.Read)
        }
    }
}
</script>
<style scoped>
/* table style & table-buttom style(.ivu-btn-primary .ivu-btn-info) */
.list{
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    padding: 1%;
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
    border-bottom: 1px solid #7d7d7d;
}
.list .ivu-table-wrapper>>>.ivu-btn-primary,.ivu-table-wrapper>>>.ivu-btn-info{
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none
}
.ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
    color: #fff;
}
.list .ivu-table-wrapper>>>.ivu-table-tip table{
    width: auto;
}
.ivu-table-wrapper>>>.ivu-table-overflowY{
    overflow-x: hidden;
}
.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar{
    width: 0.4vmin;
    height: 0.4vmin;
}
.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar-thumb{
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar-track{
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
}
</style>

