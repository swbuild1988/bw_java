<template>
    <Row style="padding: 5px;">
        <Col span="12" class="chartBox">
            <MultiBarChart v-bind="toolStatic"></MultiBarChart>
        </Col>
        <Col span="12" class="chartBox" style="margin-left: 5px;">
            <MulitBarPosiNega v-bind="inventory"></MulitBarPosiNega>
        </Col>
        <Col span="24">
            <Row class="queryCondition">
                <Col span="8">
                    <span>仪表工具名称</span><span>：</span>
                    <Input type="text" v-model="toolConditions.toolName" style="width: 45%"></Input>
                </Col>
                <Col span="8">
                    <span class="word43">借用人</span><span>：</span>
                    <Input type="text" v-model="toolConditions.takingPerson" style="width: 45%"></Input>
                </Col>
                <Col span="8">
                    <span>操作员：</span>
                    <Input type="text" v-model="toolConditions.operation" style="width: 45%"></Input>
                </Col>
                <Col span="8">
                    <span class="word64">开始时间</span><span>：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" v-model="toolConditions.startTime" style="width: 45%"></DatePicker>
                </Col>
                <Col span="8">
                    <span>结束时间</span><span>：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" v-model="toolConditions.endTime" style="width: 45%"></DatePicker>
                </Col>
                <Col span="8" style="text-align: right;width: 18%">
                    <Button type="primary" size="small">确定</Button>
                </Col>
            </Row>
            <div class="list">
                <Table stripe border  :columns="toolColums"  :data="toolData"></Table>
            </div>
            <div class="pageContainer">
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" 
                show-elevator @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
            </div>
        </Col>
    </Row>    
</template>
<script>
import MultiBarChart from '../../../../components/Common/Chart/MultiBarChart.vue'
import MulitBarPosiNega from '../../../../components/Common/Chart/MulitBarPosiNega.vue'
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
                    key: 'toolName',
                    align: 'center'
                },
                {
                    title: '借出时间',
                    key: 'takingTime',
                    align: 'center'
                },
                {
                    title: '借用人',
                    key: 'takingPerson',
                    align: 'center'
                },
                {
                    title: '借用数量',
                    key: 'takingNum',
                    align: 'center'
                },
                {
                    title: '预计归还时间',
                    key: 'expectReturnTime',
                    align: 'center'
                },
                {
                    title: '操作员',
                    key: 'operation',
                    align: 'center'
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
                pageSize: 3,
                pageTotal: 100,
                pageNum: 1
            },
            toolConditions:{
                toolName: null,
                takingPerson: null,
                operation: null,
                startTime: null,
                endTime: null
            },
            toolStatic: {
                id: 'toolStaticId',
                requestUrl: 'getToolStatic',
                parameters: {
                    option: {
                        title: {
                            text: "仪表工具状态",
                            textStyle: {
                                color: "#1affc9"
                            }
                        }
                    },
                }
            },
            inventory: {
                id: 'inventoryId',
                requestUrl: 'getInventory',
                titleText: '仪表工具库存状态',
                legendData: ['借出','在库']
            }
        }
    },
    components: {
        MultiBarChart,
        MulitBarPosiNega
    },
    methods:{
        handlePage(value) {
            this.page.pageNum = value;
        },
        handlePageSize(value) {
            this.page.pageSize = value;
        },
    }
}
</script>
<style scoped>
.pageContainer{
    /* margin-top: 10px;
    text-align: right; */
    position: fixed;
    bottom: 55px;
    right: 10px;
}
.word64{
    letter-spacing: 0.667em;
    margin-right: -0.667em;
}
.word43{
    letter-spacing: 0.5em;
    margin-right: -0.5em;
}
.chartBox{
    width: 49.5%;
    height: 30vh;
    background: #fff;
    margin: 5px 5px 5px 0px;
}
</style>
