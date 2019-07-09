<!--数据查询-->
<template>
    <div>
        <Row class="queryCondition">
            <Col span="6">
                <span class="conditionTitle" style="display: inline-block">监测对象：</span>
                <Input class="objBox" v-model="queryPrams.id" style="width: 65%;">
                    <Button slot="append" icon="ios-search" @click="queryObject()"></Button>
                </Input>
            </Col>
            <Col span="6">
                <span class="conditionTitle">所属管廊：</span>
                <Select v-model="queryPrams.tunnelId" style="width:65%" @on-change="changeTunnelId(queryPrams.tunnelId)">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in tunnelList" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">所属区域：</span>
                <Select v-model="queryPrams.areaId" style="width:65%" >
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in areas" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">所属管舱：</span>
                <Select v-model="queryPrams.storeId" style="width:65%" >
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">对象类型：</span>
                <Select v-model="queryPrams.objtypeIds" style="width:65%" multiple>
                    <OptionGroup v-for="(group, index) in objectList" :label="group.key" :key="index" style="font-size: 1.4vmin;">
                    <Option v-for="item in group.objectTypeList" :value="item.val" :key="item.val" style="font-size: 1.2vmin;line-height: 5vmin;height: 5vmin">{{ item.key }}</Option>
                    </OptionGroup>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">数据类型：</span>
                <Select v-model="queryPrams.datatypeId" style="width:65%" @on-change="changeDataType">
                    <Option v-for="item in dataTypeEnum" :value="item.val" :key="item.val">{{ item.key }}</Option>
                </Select>
            </Col>
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==1">
                    <Col span="6">
                        <span class="conditionTitle">取整范围：</span>
                        <Input v-model="queryPrams.minVal" style="width: 13vmin"></Input>
                        <span style="color: #fff">~</span>
                        <Input v-model="queryPrams.maxVal" style="width: 13vmin"></Input>
                    </Col>
                </div>
            </transition>
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==2">
                    <Col span="6">
                        <span class="conditionTitle">开关状态：</span>
                        <RadioGroup v-model="queryPrams.cv">
                            <Radio label=1><span style="color: #fff">开</span></Radio>
                            <Radio label=0><span style="color: #fff">关</span></Radio>
                        </RadioGroup>
                    </Col>
                </div>
            </transition>
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==4||queryPrams.datatypeId==5">
                    <Col span="6">
                        <span class="conditionTitle">监测位置：</span>
                        <Input v-model="queryPrams.pleace" style="width: 24vmin;"></Input>
                        <span style="color: #fff">（米）</span>
                    </Col>
                </div>
            </transition>
            <Col span="2">
                <Button type="primary" icon="ios-search" @click="queryTableData"  v-if="!viewHistory">查询</Button>
            </Col>
            <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
        </Row>
        <transition name="slide-fade" mode="in-out">
            <div v-if="!viewHistory">
                <Row style="margin: 0 9px 0 9px">
                    <Col span="24">
                        <div class="boxBG">
                            <Table :height="tableHeight" :columns="tableColumn" :data="tableData" ref="selection" :loading="tableload" @on-selection-change="selectionClick"></Table>
                        </div>
                        <div class="historyDiv">
                            <Button type="primary" shape="circle" icon="forward" title="历史数据"
                                    @click="viewHistoryData">历史数据</Button>
                            <Button type="info" shape="circle" icon="ios-cloud-download"
                                    @click="exportData">导出</Button>
                            <Page class="nextPage" :total="page.total" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
                                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator></Page>
                        </div>
                    </Col>
                </Row>
            </div>
        </transition>
        <transition name="fade" mode="out-in">
            <div  class="boxBG" v-if="viewHistory">
                <Row>
                    <Button @click="downLoad" class="downloadBtn">下载历史数据</Button>
                    <Table :columns="downTableColumn" :data="downTableDate" v-show="false" ref="downTable"></Table>
                    <Col span="24">
                        <div class="chartSize">
                            <MultiLineChart style="width: 100%;" v-bind="curlineChart" ref="multiLine"></MultiLineChart>
                        </div>
                    </Col>
                    <Col span="6">
                        <span class="timeTitle">时间周期：</span>
                        <Select v-model="historyPrams.dateType" style="width:12vw;margin-right: 4px;margin-left: 4px;"
                                @on-change="changeAlarmType" placement="top">
                            <Option v-for="item in historyDateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
                        </Select>
                    </Col>
                    <Col span="6">
                        <span class="timeTitle">开始时间：</span>
                        <DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间" placement="top" style="width: 65%"></DatePicker>
                    </Col>
                    <Col span="6">
                        <span class="timeTitle">结束时间：</span>
                        <DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间" placement="top" style="width:65%;"></DatePicker>
                    </Col>
                    <Col span="6" class="btnBox">
                        <Button type="primary" shape="circle" icon="forward" @click="queryHistoryData" v-if="viewHistory">查询</Button>
                        <Button type="info" shape="circle" icon="ios-cloud-download" @click="backToCurPage">返回</Button>
                        <PDF ref="pdf" v-show="false"></PDF>
                    </Col>
                </Row>
            </div>
        </transition>
    </div>
</template>

<script>
    import {EnumsService} from '../../../../services/enumsService.js'
    import { TunnelService } from "../../../../services/tunnelService";
    import {DataAnalysisService} from '../../../../services/dataAnalysisService.js'
    import MultiLineChart from '../../../../components/Common/Chart/MultiLineChart'
    import ShowMonitorObjectSelect from '../../../../components/Common/Modal/ShowMonitorObjectSelect'
    import PDF from "@/components/UM/MAM/pdfPerviewDownload";

  export default {
    name: "query-data",
    data() {
        return {
            tableload:true,
            viewHistory: false,
            isReady: true,
            tunnelList: [],
            tableHeight: 450,
            curlineChart: {
                id: "historyDataChart",
                requestUrl: 'data-analyse/measvalue/history/diagram',
                parameters: {
                    option: {},
                    queryPram: {startTime: "", endTime: "", ids: "",},
                },
                title: '历史数据'
            },
            queryPrams: {
                tunnelId: null,
                areaId: null,
                storeId: null,
                id: null,
                objtypeIds: [],
                datatypeId: 1,
                // 最大值与最小值
                maxVal: 100,
                minVal: 0,
                // 开关状态
                cv: 1,
                // 监测位置
                pleace: 0,
            },
            page: {
                total: 0,
                pageNum: 1,
                pageSize: 10
            },
            historyPrams: {
                startTime: null,
                endTime: null,
                dateType: 1,
                ids: [],
            },
            dataObjectSelect: {
                show: {state: false},
                selectObjects: {},
                selectData: {idList: ""}
            },
            historyDateType: [
                {key: 1, value: "最近一天"}, 
                {key: 2, value: "最近一周"}, 
                {key: 3, value: "最近一月"},
                {key: 4, value: '最近一年'},
                {key: 5,value: "自定义"}
            ], 
            dataTypeEnum: [],
            selectSelection: null,
            objectList: [],
            tableColumn: [
                {
                    type: 'selection',
                    width: 70,
                    align: 'center'
                },
                {
                    title: 'Id',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: '监测区域',
                    key: 'tunnelName',
                    align: 'center',
                    render: (h, params) => {
                        let temp = null
                        if(params.row.obj.section!=null){
                            temp = params.row.obj.section.store.tunnel.name+"-"+params.row.obj.section.name
                        }else{
                            temp = null
                        }
                        return h('span', temp)
                    }
                },
                {
                    title: '对象名称',
                    key: 'name',
                    align: 'center',
                    render: (h, params) => {
                        let temp = null
                        if(params.row.obj.section!=null){
                            temp = params.row.obj.name
                        }
                        return h('span', temp)
                    }
                },
                {
                    title: '对象类型',
                    key: 'datatypeName',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.obj.datatypeName
                        return h('span', temp)
                    }
                },
                {
                    title: '监测值',
                    key: 'cv',
                    align: 'center'
                },
                {
                    title: '采集时间',
                    key: 'refreshTime',
                    align: 'center',
                    render: (h, params) => {
                        let temp = new Date(params.row.refreshTime).format("yyyy-MM-dd hh:mm:ss")
                        return h('span', temp)

                    }
                }
            ],
            tableData: [],
            areas: [],
            stores: [],
            downTableDate: [],
            downTableColumn: [
                {
                    title: '对象名称',
                    key: 'name'
                },
                {
                    title: '数值',
                    key: 'cv'
                },
                {
                    title: '监测时间',
                    key: 'time',
                    render: (h, params) => {
                        return h('span', new Date(params.row.time).format('yyyy-MM-dd hh:mm:s'))
                    }
                }
            ]
        }
    },
    components: {
        MultiLineChart, ShowMonitorObjectSelect, PDF
    },
    watch: {
        "dataObjectSelect.selectData.idList": function () {
            this.queryPrams.id = this.dataObjectSelect.selectData.idList;
        }
    },
    mounted() {
        // 设置表格高度
        this.tableHeight = window.innerHeight * 0.65;
        //获取数据类型
        EnumsService.getMonitorType().then(result => {
            this.objectList = result;
        });
        //获取对象类型
        EnumsService.getDataType().then(result => {
            result.forEach(item=>{
                if(item.val==1||item.val==2||item.val==3||item.val==97){
                    this.dataTypeEnum.push(item)
                }
            })
            this.queryPrams.datatypeId = result[0].val;
        });
        // 获取管廊名称
        TunnelService.getTunnels().then(result => {
            this.tunnelList = result
        });
        if(this.$route.params.id){
            this.queryPrams.id = this.$route.params.id
            this.historyPrams.ids.push(this.$route.params.id)
            this.selectSelection = [
                { id: this.$route.params.id }
            ]
            this.historyPrams.dateType = 3
            this.viewHistoryData()
            this.downLoadData()
        }else{
            this.queryTableData()
        }
    },
    methods: {
        //查询监测对象
        queryObject() {
            let _this = this;
            _this.dataObjectSelect.show.state = !_this.dataObjectSelect.show.state;
        },

        //导出数据
        exportData() {
            this.$refs.selection.exportCsv({
                filename: new Date().format("yyyy-MM-dd hh:mm:ss") + "导出数据",
                original: false
            });
        },

        //根据TunnelId,获取area和store
        changeTunnelId(id){
            if(id!=null){
                //获取store
                TunnelService.getStoresByTunnelId(id).then(
                    result => {
                        this.stores = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
                //获取area
                TunnelService.getAreasByTunnelId(id).then(
                    result => {
                        this.areas = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            }
        },
        queryTableData() {
            let _this = this;
            _this.tableload=true;
            let params = {}
            if(_this.queryPrams.datatypeId==1){
                params = {
                    pageNum: _this.page.pageNum,
                    pageSize: _this.page.pageSize,
                    datatypeId: _this.queryPrams.datatypeId,
                    minVal: _this.queryPrams.minVal,
                    maxVal: _this.queryPrams.maxVal,
                    id: _this.queryPrams.id,
                    tunnelId: _this.queryPrams.tunnelId,
                    areaId: _this.queryPrams.areaId,
                    storeId: _this.queryPrams.storeId,
                    objtypeIds: _this.queryPrams.objtypeIds
                }
            }else if(_this.queryPrams.datatypeId==2){ //数据类型--开关量输入
                params = {
                    pageNum: _this.page.pageNum,
                    pageSize: _this.page.pageSize,
                    datatypeId: _this.queryPrams.datatypeId,
                    cv: _this.queryPrams.cv,
                    id: _this.queryPrams.id,
                    tunnelId: _this.queryPrams.tunnelId,
                    areaId: _this.queryPrams.areaId,
                    storeId: _this.queryPrams.storeId,
                    objtypeIds: _this.queryPrams.objtypeIds
                }
            }else if( _this.queryPrams.datatypeId==4|| _this.queryPrams.datatypeId==5){ //数据类型--***||分布式数据
                params = {
                    pageNum: _this.page.pageNum,
                    pageSize: _this.page.pageSize,
                    datatypeId: _this.queryPrams.datatypeId,
                    pleace: _this.queryPrams.pleace,
                    id: _this.queryPrams.id,
                    tunnelId: _this.queryPrams.tunnelId,
                    areaId: _this.queryPrams.areaId,
                    storeId: _this.queryPrams.storeId,
                    objtypeIds: _this.queryPrams.objtypeIds
                }
            }else{
                params = {
                    pageNum: _this.page.pageNum,
                    pageSize: _this.page.pageSize,
                    datatypeId: _this.queryPrams.datatypeId,
                    id: _this.queryPrams.id,
                    tunnelId: _this.queryPrams.tunnelId,
                    areaId: _this.queryPrams.areaId,
                    storeId: _this.queryPrams.storeId,
                    objtypeIds: _this.queryPrams.objtypeIds
                }
            }
            DataAnalysisService.getMonitorData(params).then(result => {
                if(result!=null){
                    _this.tableData = result.list
                    _this.page.total = result.total;
                    _this.tableload=false;
                }
            })
        },

        viewHistoryData() {
            let _this = this;
            if(!this.selectSelection){
                this.$Message.warning('请勾选需要查询历史数据的对象');
                return;
            }
            _this.viewHistory = !_this.viewHistory;
            _this.dataObjectSelect.state = !_this.dataObjectSelect.state;
            _this.changeAlarmType(_this.historyPrams.dateType);
            _this.historyPrams.ids = [];
            this.selectSelection.reduce(function (a, b) {
                _this.historyPrams.ids.push(b.id);
            }, _this.historyPrams.ids);
            _this.curlineChart.parameters.queryPram.startTime = new Date(_this.historyPrams.startTime).getTime();
            _this.curlineChart.parameters.queryPram.endTime =new Date(_this.historyPrams.endTime) .getTime();
            _this.curlineChart.parameters.queryPram.ids = _this.historyPrams.ids;
        },

        queryHistoryData() {
            let _this = this;
            _this.curlineChart.parameters.queryPram.startTime = _this.historyPrams.startTime.getTime();
            _this.curlineChart.parameters.queryPram.endTime = _this.historyPrams.endTime.getTime();
            _this.curlineChart.parameters.queryPram.ids = _this.historyPrams.ids;
            _this.$refs.multiLine.fetchData();
            // _this.downLoadData()
        },

        backToCurPage() {
            let _this = this;
            _this.viewHistory = !_this.viewHistory;
        },

        selectionClick(arr) {
            this.selectSelection = arr;
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryTableData()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryTableData()
        },

        //切换数据类型
        changeDataType(index) {
        },

        //更改告警时间类型
        changeAlarmType(index) {
            let _this = this;
            let date = new Date();
            if (index == 1) {
                date.setTime(date.getTime() - 3600 * 1000 * 24);
                _this.historyPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
                _this.historyPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
                _this.isReady = true;
            }
            else if (index == 2) {
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                _this.historyPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
                _this.historyPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
                _this.isReady = true;
            }
            else if (index == 3) {
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
                _this.historyPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
                _this.historyPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
                _this.isReady = true;
            }
            else if (index == 4) {
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 365);
                _this.historyPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
                _this.historyPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
                _this.isReady = true;
            }
            else {
                _this.isReady = false;
                _this.historyPrams.startTime = "";
                _this.historyPrams.endTime = "";
            }
        },

        downLoadData(){
            let params = {
                ids: this.historyPrams.ids,
                startTime: this.historyPrams.startTime,
                endTime: this.historyPrams.endTime
            }
            DataAnalysisService.downLoadData(params).then(
                result => {
                    this.downTableDate = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },

        downLoad(){
            this.$refs.downTable.exportCsv({
                filename: new Date().format("yyyy-MM-dd hh:mm:ss") + "设备历史数据",
                original: false
            });
        }
    }
  }

</script>

<style scoped>
    .nextPage {
        position: relative;
        bottom: 0px;
        right: 7px;
        float: right;
    }
    .slide-fade-enter-active {
        transition: all .5s ease;
    }
    .slide-fade-leave-active {
        transition: all .5s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-enter, .slide-fade-leave-to {
        transform: translateX(10px);
        opacity: 0;
    }
    .ivu-select-single >>> .ivu-select-selection >>> .ivu-select-selected-value {
        font-size: 1.66vmin;
    }
    .ivu-select-single >>> .ivu-select-selection >>> .ivu-select-placeholder {
        font-size: 1.66vmin;
    }
    .chartSize {
        height: 64vh;
        margin-bottom: 1vh;
        width: 100%;
    }
    .historyDiv {
        position: relative;
        line-height: 40px;
        margin-top: 0.5vh;
    }
    .downloadBtn{
        position: absolute;
        right: 0;
        z-index: 999;
    }

    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
    .ivu-select-multiple >>> .ivu-select-placeholder,.objBox >>> .ivu-input-group-append .ivu-btn
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
        color: #fff;
        background-color: #fffdfd00 !important;
    }
    .objBox{
        display: inline-block;
    }
    .objBox.ivu-input-wrapper >>> .ivu-input{
        width: 80%;
        border-radius: 1vmin 0px 0px 1vmin;
    }

    .objBox.ivu-input-wrapper>>>.ivu-input-group-append{
        border: 0.1vmin solid #fff;
        border-left: 0;
        border-radius: 0px 1vmin 1vmin 0px;
        line-height: 2.4;
        background: #121a28;
    }

    .ivu-select-multiple >>> .ivu-tag,.ivu-tag-checked{
        height: 3.2vmin;
        line-height: 3.2vmin;
        font-size: 1.2vmin;
    }
    .ivu-select-group-wrap >>> .ivu-select-group-title{
        height: 3.8vmin;
        line-height: 3.2vmin;
        font-size: 1.6vmin;
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
    .nextPage .ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator,.nextPage.ivu-page>>>.ivu-page-total,.queryCondition .ivu-select,.timeTitle{
        color: #fff;
    }
    .boxBG{
        background: url("../../../../assets/UM/infoBox.png") no-repeat;
        background-size: 100% 100%;
        padding: 2vmin 4vmin;
    }
    .ivu-table-wrapper>>>.ivu-table-border td, .ivu-table-wrapper>>>.ivu-table-border th{
        border-right: none;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection {
        background-color: #fffdfd00 !important;
    }
    .btnBox .ivu-btn-primary,.historyDiv .ivu-btn-primary{
        background: linear-gradient(to left, #2734e1, #b195ed)
    }
    .btnBox .ive-btn-info,.historyDiv .ive-btn-info{
        background: linear-gradient(to left, #1af6b0, #a7ecd7)
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
		.timeTitle{
			font-size: 1.6vmin;
		}
	}
</style>
