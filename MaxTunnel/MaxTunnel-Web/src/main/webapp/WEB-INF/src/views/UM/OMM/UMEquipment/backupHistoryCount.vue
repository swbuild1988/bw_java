<!--备品历史统计-->
<template>
    <div style="padding: 5px;">
        <Row >
        <Col span="12">
        <div>
            <Row >
            <Col span="12" >
            <div class="datapanle">
                <span slot="title" >
                取用备品备件排行
                </span>
                <div class="listUpdate">
                <a href="#" slot="extra" @click="getBackUpType">
                    <Icon type="ios-loop-strong"></Icon>
                    刷新
                </a>
                </div>
                <hr>
                <div>
                <ul v-for="(item,index) in backupTakeData" :key="index">
                    <li class="takeBackUpList">
                        <Icon type="star" color="#ff3c1d"></Icon>
                        {{item.name}}
                        <span style="position: relative;float: right;">
                            {{item.count}}
                            <span class="orderDetails" @click="showTakingDetails(item.id)">&nbsp;&nbsp;详情</span>
                        </span>
                    </li>
                </ul>
                </div>
            </div>
            </Col>
            <Col span="12" >
            <div class="datapanle">
                <span slot="title" >
                备品取用人排行
                </span>
                <div class="listUpdate">
                    <a href="#" slot="extra" @click="getBackUpBorrower">
                        <Icon type="ios-loop-strong"></Icon>
                        刷新
                    </a>
                </div>
                <hr>
                <div>
                    <ul v-for="(item,index) in takeUserData" :key="index">
                        <li class="takeBackUpList">
                            <Icon type="reply-all" size="20" color="#15ffe4"></Icon>
                            {{item.name}}
                            <span style="position: relative;float: right;">
                                {{item.count}}
                                <span class="orderDetails" @click="showTakingPersonDetails(item.id)">&nbsp;&nbsp;详情</span>
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
            </Col>
            </Row>
        </div>
        </Col>
        <Col span="6" >
        <div style="width:21vw;height: 30vh;">
            <SimplePieChart v-bind="countTakeData"></SimplePieChart>
        </div>
        </Col>
        <Col span="6">
        <div style="width:21vw;height: 30vh;">
            <SimplePieChart v-bind="countStoreBackupData"></SimplePieChart>
        </div>
        </Col>
        <Col span="12" style="margin-top: 1.5vh">
            <div style="width:43vw;height: 52vh;" ref="MultiBarChartBox">
                <MultiBarChart v-bind="backupDetailData"></MultiBarChart>
            </div>
        </Col>
        <Col span="12" style="margin-top: 1.5vh"  >
            <Row class="queryCondition">
                <Col span="8">
                    备品名称：
                    <Input type="text" v-model="outStorageConditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="8">
                    备品类别：
                    <Select v-model="outStorageConditions.typeId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    备品型号：
                    <Select v-model="outStorageConditions.modelId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    开始时间：
                    <DatePicker type="datetime" v-model="outStorageConditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="8">
                    结束时间：
                    <DatePicker type="datetime" v-model="outStorageConditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="8">
                    <Button type="primary" @click="showTable()" icon="ios-search" size="small">查询</Button>
                </Col>
            </Row>
            <Table stripe border :columns="toolColumns"  :height="tableHeight"  :data="toolData"></Table>
            <div class="pageBox">
                <Page ref="pageBox" :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                    :style='pageStyle'></Page>
            </div>
        </Col>
    </Row>
    <Modal
        v-model="isTaking"
        title="取用备品详情"
        :width="modalWidth"
        @on-cancel="resetVal"
    >
        <Row style="margin-bottom: 10px;">
            <div style="display: none;">
                typeId:<Input v-model="typeId"></Input>
            </div> 
            <Col span="14">
                <span>出库时间：</span>
                <DatePicker type="datetime" ref="takingStartTime" v-model="takingSpareModalConditions.startTime" placeholder="请选择开始时间" class="timeWidth"></DatePicker> -
                <DatePicker type="datetime" ref="takingEndTime" v-model="takingSpareModalConditions.endTime" placeholder="请选择结束时间" class="timeWidth"></DatePicker>
            </Col>
            <Col span="7">
                取用人：
                <Select clearable ref="staffId" style="width: 60%;" v-model="takingSpareModalConditions.staffId">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="3" style="text-align: center">
                <Button type="primary" size="small" @click="showTakingDetails(typeId)">查询</Button>
            </Col>
        </Row>
        <Table stripe border :columns="takingColums"  :data="takingDate"></Table>   
        <div class="pageContainer">
            <Page :total="takingPage.pageTotal" :current="takingPage.pageNum" :page-size="takingPage.pageSize" 
            show-elevator show-total @on-change="takingSpareHandlePage" @on-page-size-change='takingSpareHandlePageSize'></Page>
        </div>
        <div slot="footer">
            <Button type="text" size="large" @click="resetVal">取消</Button>
            <Button type="primary" size="large" @click="resetVal">确定</Button>
        </div>
    </Modal>
    <Modal
        v-model="isTakingPerson"
        title="取用备品人详情"
        :width="modalWidth"
        @on-cancel = "takingPersonReset"
    >
        <Row style="margin-bottom: 10px">
            <div style="display: none">
                staffId: <input v-model="staffId">
            </div>
            <Col span="14">
                <span>出库时间：</span>
                <DatePicker type="datetime" ref="takingPersonStartTime" v-model="takingPersonConditions.startTime" placeholder="请选择开始时间" class="timeWidth"></DatePicker> -
                <DatePicker type="datetime" ref="takingPersonEndTime" v-model="takingPersonConditions.endTime" placeholder="请选择结束时间" class="timeWidth"></DatePicker>
            </Col>
            <Col span="7">
                <span>备品名称：</span>
                <Input ref="borrowName" type="text" class="borrowName" v-model="takingPersonConditions.name"></Input>
            </Col>
            <Col span="3" style="text-align: center">
                <Button type="primary" size="small" @click="showTakingPersonDetails(staffId)">查询</Button>
            </Col>
        </Row>
        <Table stripe border :columns="takingPersonColums"  :data="takingPersonDate"></Table>
        <div class="pageContainer">
            <Page :total="takingPersonPage.pageTotal" :current="takingPersonPage.pageNum" :page-size="takingPersonPage.pageSize" 
            show-elevator show-total @on-change="takingPersonHandlePage" @on-page-size-change='takingPersonHandlePageSize'></Page>
        </div>
        <div slot="footer">
            <Button type="text" size="large" @click="takingPersonReset">取消</Button>
            <Button type="primary" size="large" @click="takingPersonReset">确定</Button>
        </div>
    </Modal>
  </div>
</template>

<script>
import SimplePieChart from "../../../../components/Common/Chart/SimplePieChart.vue";
import MultiBarChart from "../../../../components/Common/Chart/MultiBarChart.vue";
import { EquipmentService } from "../../../../services/equipmentService";
export default {
    name: "backup-history-count",
    data() {
        return {
            backupTakeData: [
            ],
            isTaking: false,
            typeId: null,
            takingSpareModalConditions:{
                startTime: null,
                endTime: null,
                staffId: null
            },
            takingColums:[
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '备品类别',
                        key: 'typeName',
                        align: 'center'
                    },
                    {
                        title: '备品型号',
                        key: 'modelName',
                        align: 'center'
                    },
                    {
                        title: '备品名称',
                        key: 'name',
                        align: 'center'
                    },
                    {
                        title: '入库时间',
                        key: 'inTime',
                        align: 'center'
                    },
                    {
                        title: '出库时间',
                        key: 'outTime',
                        align: 'center'
                    },
                    {
                        title: '取用人',
                        key: 'staffName',
                        align: 'center'
                    },
                    {
                        title: '备注',
                        key: 'describe',
                        align: 'center'
                    }
            ],
            takingDate:[
                { id: 1, backUpModal: '温度计', backUpName: '温度计1', takingTime: '', takingPersonName: '张三', operation: '管理员' },
                { id: 2, backUpModal: '温度计', backUpName: '温度计2', takingTime: '', takingPersonName: '张三', operation: '管理员' },
                { id: 3, backUpModal: '温度计', backUpName: '温度计3', takingTime: '', takingPersonName: '张三', operation: '管理员' }
            ],
            takingPage:{
                pageSize: 3,
                pageTotal: 100,
                pageNum: 1
            },
            staffs: [],
            isTakingPerson: false,
            staffId: null,
            takingPersonConditions: {
                startTime: null,
                endTime: null,
                name: null
            },
            takingPersonColums:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '取用人',
                    key: 'staffName',
                    align: 'center'
                },
                {
                    title: '备品名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '备品类别',
                    key: 'typeName',
                    align: 'center'
                },
                {
                    title: '备品型号',
                    key: 'modelName',
                    align: 'center'
                },
                {
                    title: '入库时间',
                    key: 'inTime',
                    align: 'center',
                    width: 190
                },
                {
                    title: '出库时间',
                    key: 'outTime',
                    align: 'center',
                    width: 190
                }
            ],
            takingPersonDate:[],
            takingPersonPage:{
                pageSize: 3,
                pageTotal: 100,
                pageNum: 1
            },
            takeUserData: [],
            countTakeData: {
                id: "loanDetail",
                requestUrl: "spare-outs/type/key",
                parameters: {
                option: {
                    title: {
                    text: "取用备品设备比例"
                    }
                },
                // timer: {
                //     interval: 50000
                // }
                }
            },
            countStoreBackupData: {
                id: "BreakDetail",
                requestUrl: "spares/type/key",
                parameters: {
                    option: {
                        title: {
                        text: "库存备品比例"
                        }
                    },
                    // timer: {
                    //     interval: 5000
                    // }
                }
            },
            backupDetailData: {
                id: " toolDetail",
                requestUrl: "spares/outs/type",
                title: '备品备件明细',
                parameters: {
                    option: {
                        backgroundColor: '#FCF2EA',
                        color: ['#CE98AF','#016A9A'],
                        title: {
                            text: "备品备件明细",
                            textStyle: {
                                color: "#8080C0"
                            }
                        }
                    },
                    // timer: {
                    //     interval: 5000
                    // }
                }
            },
            toolColumns: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: "备品名称",
                    key: "name",
                    align: 'center'
                },
                {
                    title: "备品类别",
                    key: "typeName",
                    align: 'center'
                },
                {
                    title: '备品型号',
                    key: 'modelName',
                    align: 'center'
                },
                {
                    title: '出库至',
                    key: 'whitherName',
                    align: 'center'
                },
                {
                    title: '取用人',
                    key: 'staffName',
                    align: 'center'
                },
                {
                    title: '出库时间',
                    key: 'outTime',
                    align: 'center',
                },
                {
                    title: '备注',
                    key: 'describe',
                    align: 'center',
                    render: (h,params) => {
                        let temp = params.row.describe
                        if(temp!=null){
                            if(temp.length<7){
                                temp=temp
                            }else{
                                temp=temp.substring(0,7)+'......'
                            }
                        }
                        return h('div',temp)
                    }
                },
            ],
            toolData: [],
            equipmentTypes: [],
            equipmentModels: [],
            page: {
                pageNum: 1,
                pageSize: 7,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "-46px",
                right: "15px",
            },
            outStorageConditions:{
                name: null,
                typeId: null,
                modelId: null,
                startTime: null,
                endTime: null,
                //批量出库查询是否在库，否
                status: 0,
            },
            modalWidth: null,
            tableHeight: null
        };
    },
    components: {
        SimplePieChart,
        MultiBarChart
    },
    watch:{
        typeId: function(curVal,oldVal){
            this.showTakingDetails(curVal)
        },
    },
    computed:{
            outStorageParams() {
                let param = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    name: this.page.name,
                    typeId: this.outStorageConditions.typeId,
                    modelId: this.outStorageConditions.modelId,
                    startTime: this.outStorageConditions.startTime,
                    endTime: this.outStorageConditions.endTime,
                    status: true,
                };
                return Object.assign({},param)
            },
    },
    mounted(){
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.equipmentTypes = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.equipmentModels = res
            },
            error => {
                this.Log.info(error)
            }
        ),
        //获取取用人列表
        this.axios.get('staffs').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.staffs = data
            }
        })
        
        this.showTable()
        this.getBackUpType()
        this.getBackUpBorrower()
        this.getModalWidth()
    },
    methods: {
        //用户AutoComplete
        filterMethod(value, option) {
        return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
        },
        showTable() {
            //批量出库查询出库备件
            EquipmentService.backUpHistory(this.outStorageParams).then(
                result => {
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        result.pagedList[index].outTime = new Date(result.pagedList[index].outTime).format("yyyy-MM-dd hh:mm:ss");
                    }
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
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        //获取备品排行
        getBackUpType(){
            EquipmentService.getBackUpTypeHisList().then(
                res => {
                    this.backupTakeData = res
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //取出备品排行详情
        showTakingDetails(id){
            this.isTaking = true
            this.typeId = id
            let param1 = {
                typeId: id,
                pageNum: this.takingPage.pageNum,
                pageSize: this.takingPage.pageSize,
                startTime: this.takingSpareModalConditions.startTime,
                endTime: this.takingSpareModalConditions.endTime,
                staffId: this.takingSpareModalConditions.staffId
            }
            EquipmentService.backUpHistory(param1).then(
                result => {
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        result.pagedList[index].outTime = new Date(result.pagedList[index].outTime).format("yyyy-MM-dd hh:mm:ss");
                    }
                    this.takingDate = result.pagedList
                    this.takingPage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },

        resetVal(){
            this.isTaking = false 
            this.takingPage.pageNum = 1
            this.$refs.takingStartTime.handleClear()
            this.$refs.takingEndTime.handleClear()
            this.$refs.staffId.clearSingleSelect()
        },

        takingSpareHandlePage(value){
            this.takingPage.pageNum = value;
            this.showTakingDetails(this.typeId)
        },

        takingSpareHandlePageSize(value){
            this.takingPage.pageSize = value
            this.showTakingDetails(this.typeId)
        },

        //取用备品人排行
        getBackUpBorrower(){
            EquipmentService.getBackUpBorrowerHisList().then(
                res => {
                    this.takeUserData = res
                },
                error => {
                    this.Log.info(error)
                }
            )
        },

        //取用备品人排行详情
        showTakingPersonDetails(id){
            this.isTakingPerson = true
            this.staffId = id
            let param = {
                staffId: id,
                pageNum: this.takingPersonPage.pageNum,
                pageSize: this.takingPersonPage.pageSize,
                startTime: this.takingPersonConditions.startTime,
                endTime: this.takingPersonConditions.endTime,
                name: this.takingPersonConditions.name
            }
            EquipmentService.backUpHistory(param).then(
                result => {
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        result.pagedList[index].outTime = new Date(result.pagedList[index].outTime).format("yyyy-MM-dd hh:mm:ss");
                    }
                    this.takingPersonDate = result.pagedList
                    this.takingPersonPage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },

        takingPersonReset(){
            this.isTakingPerson = false
            this.takingPersonPage.pageNum = 1
            this.$refs.takingPersonStartTime.handleClear()
            this.$refs.takingPersonEndTime.handleClear()
            this.$refs.borrowName.handleClear()
        },
        takingPersonHandlePageSize(value){
            this.takingPersonPage.pageSize = value
            this.showTakingPersonDetails(this.staffId)
        },
        takingPersonHandlePage(value){
            this.takingPersonPage.pageNum = value
            this.showTakingPersonDetails(this.staffId)
        },
        getModalWidth(){
            this.modalWidth = document.body.offsetWidth *0.55
            this.tableHeight = this.$refs.MultiBarChartBox.offsetHeight-document.body.offsetHeight/10-document.body.offsetHeight/100*3.2
        }
    }
};
</script>

<style scoped>
.datapanle {
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.2);
  border-color: #eee;
  width: 98%;
  height: 30vh;
  margin-right: 10px;
  padding: 18px;
  background: #fff;
}
.orderDetails{
    color: #2d8cf0;
    cursor: pointer;
}
.pageContainer{
    margin-top: 10px;
    text-align: right;
}
.takeBackUpList{
    margin-top: 3px;
    line-height: 24px;
    list-style-type:none;
}
.listUpdate{
    position: relative;
    float: right;
}
.timeWidth{
    width: 153px;
}
.borrowName{
    width: 130px;
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
    .takeBackUpList{
        font-size: 1.4vmin;
        line-height: 3.5vmin;
    }
    .datapanle,.listUpdate{
        font-size: 1.5vmin;
        line-height: 4vmin;
    }
    .timeWidth,.borrowName{
        width: 17vmin;
    }
    .pageBox{
        margin-top: 1.3vh;
    }
}
</style>


