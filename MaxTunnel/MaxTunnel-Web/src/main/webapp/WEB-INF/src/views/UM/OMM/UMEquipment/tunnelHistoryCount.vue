<template>
    <Row>
        <Col span="12" style="height: 42vh;width: 42vw;">
            <ComparisonBarChart v-bind="chartData"></ComparisonBarChart>
        </Col>
        <Col span="12" style="height: 42vh;padding-left: 0.5vw">
            <Row class="queryCondition">
                <Col span="8">
                    设备名称：
                    <Input v-model="conditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="8">
                    设备类型：
                    <Select v-model="conditions.typeId" style="width: 60%">
                        <Option value=null key=0>所有</Option>
                        <Option v-for="item in equipmentType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    设备型号：
                    <Select v-model="conditions.modelId" style="width: 60%">
                        <Option value=null key=0>所有</Option>
                        <Option v-for="item in equipmentModel" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    设备状态：
                    <Select v-model="conditions.statusId" style="width: 60%">
                        <Option value=null key=0>所有</Option>                    
                        <Option v-for="item in status" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    所属管廊：
                    <Select v-model="conditions.tunnelId" style="width: 60%">
                        <Option value=null key=0>所有</Option>                    
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col>
                    <Button type="primary" size="small" icon="ios-search">查询</Button>
                </Col>
            </Row>
            <Table  stripe border :columns="breakColumns"  :style="{height:'29vh',zIndex:101}" :data="breakData"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize"  :style="pageStyle"
                show-elevatorn show-total show-sizer @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
        </Col>
        <Col span="24" style="height: 40vh;width: 86vw;margin-top: 1vh;">
            <ComplexBarChart v-bind="ComplexBar"></ComplexBarChart>
        </Col>
    </Row>
</template>
<script>
import ComparisonBarChart from "../../../../components/Common/Chart/ComparisonBarChart";
import ComplexBarChart from "../../../../components/Common/Chart/ComplexBarChart.vue";
import types from "../../../../../static/Enum.json";
import { TunnelService } from '../../../../services/tunnelService'
import { EquipmentService } from "../../../../services/equipmentService";
export default {
    data(){
        return{
            chartData: {
                id: "comparisonBarChart",
                yAxisName: "单位：个",
                requestTotalUrl: "tunnels/equipments/count",
                requestPartUrl: "tunnels/equipments/broken/count",
                title: "管廊设备故障统计",
                color: "#46dbff"
            },
            ComplexBar: {
                id: "ComplexBarChart",
                yAxisName: "单位：个",
                requestUrl: "equipments/tunnels/count",
                title: "管廊设备明细",
                color: "#21d6ff"
            },
            conditions: {
                name: null,
                typeId: null,
                modelId: null,
                statusId: null,
                tunnelId: null
            },
            equipmentType: [],
            equipmentModel: [],
            tunnels: [],
            status: [
                { id: 1, name: '损坏' },
                { id: 2, name: '正常' }
            ],
            breakColumns: [
                {
                    title: "设备名称",
                    key: "name",
                    align: 'center'
                },
                {
                    title: "设备类型",
                    key: "typeName",
                    align: 'center'
                },
                {
                    title: "设备型号",
                    key: "modelName",
                    align: 'center'
                },
                {
                    title: "所属管廊",
                    key: "tunnelName",
                    align: 'center'
                },
                {
                    title: "设备状态",
                    key: "statusName",
                    align: 'center'
                },
                {
                    title: "操作",
                    key: "action",
                    width: 150,
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                        h(
                            "Button",
                            {
                            props: {
                                type: "info",
                                size: "small"
                            },
                            style: {
                                marginRight: "5px"
                            },
                            on: {
                                click: () => {
                                this.show(params.index);
                                }
                            }
                            },
                            "查看详情"
                        )
                        ]);
                    }
                }
            ],
            breakData: [
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },                
                { name: "风2机", typeName: "管廊设备", modelName: "2018-1-12", tunnelName: "古城大街", statusName: '正常' },

            ],
            page: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 5
            },
            pageStyle: {
                background: '#fff',
                textAlign: 'right',
                lineHeight: '4vh'
            }
        }
    },
    components: { ComparisonBarChart, ComplexBarChart },
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
    },
    computed:{
        params(){
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                typeId: this.conditions.typeId,
                modelId: this.conditions.modelId,
                statusId: this.conditions.statusId,
                tunnelId: this.conditions.tunnelId
            }
            return Object.assign({}, param);
        }
    },
    methods:{
        showTable(){
            console.log("111")
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
    }
}
</script>
<style scoped>

</style>
