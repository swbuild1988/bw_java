<template>
    <div>
        <div class="boxBG smallBox">
            <MulitBarPosiNega v-bind="chartData"></MulitBarPosiNega>
        </div>
        <div class="boxBG smallBox" style="padding: 1vmin;width: 43vw;">
            <div class="queryCondition">
                <div class="conWidth">
                    <span class="conditionTitle">设备名称：</span>
                    <Input v-model="conditions.name" style="width: 60%"></Input>
                </div>
                <div class="conWidth">
                    <span class="conditionTitle">所属管廊：</span>
                    <Select v-model="conditions.tunnelId" style="width: 60%">
                        <Option value=null>所有</Option>                    
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </div>
                <div class="conWidth">
                    <span class="conditionTitle">设备类型：</span>
                    <Select v-model="conditions.typeId" style="width: 60%">
                        <Option value=null>所有</Option>
                        <Option v-for="item in equipmentType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </div>
                <div class="conWidth">
                    <span class="conditionTitle">设备型号：</span>
                    <Select v-model="conditions.modelId" style="width: 60%">
                        <Option value=null>所有</Option>
                        <Option v-for="item in equipmentModel" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </div>
                <div class="conWidth">
                    <Button type="primary" size="small" icon="ios-search" @click="showTable()">查询</Button>
                </div>
            </div>
            
            <Table  stripe border :columns="breakColumns" :height="tableHeight" :data="breakData"></Table>
            <div class="pageBox">
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize"  :style="pageStyle"
                    show-elevatorn show-total @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
            </div>
        </div>
        <div class="boxBG" style="height: 40vh;width: 85.5vw;margin-top: 1vh;">
            <ComplexBarChart v-bind="ComplexBar"></ComplexBarChart>
        </div>
    </div>
</template>
<script>
// import ComparisonBarChart from "../../../../components/Common/Chart/ComparisonBarChart";
import MulitBarPosiNega from '../../../../components/Common/Chart/MulitBarPosiNega.vue'
import ComplexBarChart from "../../../../components/Common/Chart/ComplexBarChart.vue";
import types from "../../../../../static/Enum.json";
import { TunnelService } from '../../../../services/tunnelService'
import { EquipmentService } from "../../../../services/equipmentService";
export default {
    data(){
        return{           
            chartData: {
                id: 'chartDataId',
                requestUrl: 'tunnels/equipments/status',
                title: '管廊设备状态统计',
                legendData: ['正常','故障'],
                parameters: {
                    timer: {
                        interval: 5000
                    }
                },
                lineColor: '#25cef3',
                textColor: '#fff',
                titlePosition: 'center'
            },
            ComplexBar: {
                id: "ComplexBarChart",
                yAxisName: "单位：个",
                requestUrl: "tunnels/equipments/types",
                title: "管廊设备明细",
                color: "#21d6ff",
                gridTop: '15%',
                // itemStyleList: ['#b945ef', '#f8a42b', '#38db2c', '#45ef8d', '#2734e1', '#ef45d9']
            },
            conditions: {
                name: null,
                typeId: null,
                modelId: null,
                status: 2,
                tunnelId: null
            },
            equipmentType: [],
            equipmentModel: [],
            tunnels: [],
            status: [],
            breakColumns: [
                {
                    title: "设备名称",
                    key: "name",
                    align: 'center'
                },
                {
                    title: "所属管廊",
                    align: 'center',
                    key: 'tunnel',
                    render: (h, params) => {
                        return h('div',params.row.tunnel.name)
                    }
                },
                {
                    title: "设备类型",
                    key: "typeName",
                    align: 'center'
                },
                {
                    title: "设备型号",
                    align: 'center',
                    key: 'model',
                    render: (h, params) => {
                        return h("div", params.row.model.name);
                    }
                },
                {
                    title: "设备状态",
                    key: "statusName",
                    align: 'center'
                },
                // {
                //     title: "操作",
                //     key: "action",
                //     width: 150,
                //     align: "center",
                //     render: (h, params) => {
                //         return h("div", [
                //         h(
                //             "Button",
                //             {
                //                 props: {
                //                     type: "info",
                //                     size: "small"
                //                 },
                //                 style: {
                //                     marginRight: "5px"
                //                 },
                //                 on: {
                //                     click: () => {
                //                         this.show(params.row.id);
                //                     }
                //                 }
                //             },
                //             "查看详情"
                //         )
                //         ]);
                //     }
                // }
            ],
            breakData: [
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' }
            ],
            page: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 5
            },
            pageStyle: {
                position: 'absolute',
                right: '0.7vmin',
                bottom: '0.7vmin'
            },
            tableHeight: null
        }
    },
    components: { MulitBarPosiNega, ComplexBarChart },
    computed: {
        params() {
            let param = {
                name: this.conditions.name,
                type: this.conditions.typeId,
                status: this.conditions.status,
                tunnelId: this.conditions.tunnelId,
                modelId: this.conditions.modelId,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return Object.assign({}, param);
        }
    },
    mounted(){
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.equipmentType = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.equipmentModel = res
            },
            error => {
                this.Log.info(error)
            }
        )
        // 获取所有的管廊
        TunnelService.getTunnels().then(
            (result)=>{
                this.tunnels = result;
            },
            (error)=>{
                this.Log.info(error)
            }
        )
        this.showTable()
        this.getTableHieght()
    },
    
    methods:{
        showTable(){
            EquipmentService.equipmentDatagird(this.params).then(
                result=>{
                    this.breakData = result.pagedList
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error);
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        goToMoudle1: function(id, type) {
            this.$router.push({
                name: "设备详情",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        show(id) {
            this.goToMoudle1(id, types.pageType.Read);
        },
        getTableHieght(){
            this.tableHeight = document.body.offsetHeight/100*26
        }
    }
}
</script>
<style scoped>
    .conWidth{
        width: 32%;
        display: inline-block;
    }
    .ivu-table-wrapper{
        border: none;
    }
    .ivu-table-wrapper>>>.ivu-table{
        color: #ffffff !important;
        background-color: #fffdfd00 !important;
    }
    .ivu-table-wrapper>>>.ivu-table:before,.ivu-table-wrapper>>>.ivu-table:after{
        background-color: #fffdfd00 !important;
    }
    .ivu-table-wrapper>>>.ivu-table th,.ivu-table-wrapper>>>.ivu-table td{
        background-color: #fffdfd00 !important;
        border-bottom: 1px solid #7d7d7d;
    }
    .pageBox .ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
        color: #fff;
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
    .boxBG{
        background: url("../../../../assets/UM/chartBG.png") no-repeat;
        background-size: 100% 100%;
    }
    .smallBox{
        height: 42vh;
        width: 42vw;
        display: inline-block;
        vertical-align: top;
    }
    .ivu-table-wrapper>>>.ivu-table-border td, .ivu-table-wrapper>>>.ivu-table-border th{
        border-right: none;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection {
        background-color: #fffdfd00 !important;
    }
    .queryCondition .ivu-select{
        color: #fff;
    }
    @media (min-width: 2200px){
        .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
        .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
        {
            height: 4vmin;
            line-height: 4vmin;
            font-size: 1.4vmin;
        }
        .queryCondition{
            font-size: 1.4vmin;
        }
    }
</style>
