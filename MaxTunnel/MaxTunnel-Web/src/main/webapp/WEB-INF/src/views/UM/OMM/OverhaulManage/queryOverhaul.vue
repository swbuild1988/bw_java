<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="6">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime"></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime"></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" icon="ios-search" @click="getList()">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="columns1" :data="overhaulData"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        </div>    
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
                bottom: '20px',
                right: '15px'
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
            tunnels:[]
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
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get('/tunnels').then(response=>{
        //     let{code,data} = response.data
        //     if(code==200){
        //         this.tunnels = data
        //     }
        // })
        this.getList()
    },
    methods:{
        //获取list
        getList(){
            let _this = this
            OverhaulService.ordersDatagrid(this.params).then(
                (result)=>{
                    _this.overhaulData = result.list
                    _this.page.pageTotal = result.total
                },
                (error)=>{
                    _this.Log.info(error)
                })
            // this.axios.post('/orders/datagird',(this.params)).then(response=>{
            //     let{ code,data } = response.data
            //     if(code==200){
            //         this.overhaulData = data.list
            //         this.page.pageTotal = data.total
            //     }
            // })
            // .catch(function(error){
            //     console.log(error)
            // })
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
                name: 'UMDetailsOverhaul',
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
