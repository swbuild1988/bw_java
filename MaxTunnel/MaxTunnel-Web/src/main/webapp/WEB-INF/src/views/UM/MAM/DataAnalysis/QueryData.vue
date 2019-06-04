<!--数据查询-->
<template>
    <div>
        <Row class="queryCondition">
            <Col span="6">
                <span class="conditionTitle">监测对象：</span>
                <Input v-model="queryPrams.id " @on-blur="queryObject" style="width: 65%;"></Input>
            </Col>
            <Col span="6">
                <span class="conditionTitle">所属管廊：</span>
                <Select v-model="queryPrams.tunnelId" style="font-size: 18px;width:64%" >
                    <Option v-for="item in tunnelList" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">数据类型：</span>
                <Select v-model="queryPrams.datatypeId" style="width:65%" @on-change="changeDataType">
                    <Option v-for="item in dataTypeEnum" :value="item.val" :key="item.val">{{ item.key }}</Option>
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
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==1">
                    <Col span="10">
                        <span class="conditionTitle">取整范围：</span>
                        <span style="color: #fff;font-size: 1.6vmin">最小值</span>
                        <Input v-model="queryPrams.minVal" style="width: 100px"></Input>
                        <span style="color: #fff; font-size: 1.6vmin">最大值</span>
                        <Input v-model="queryPrams.maxVal" style="width: 100px"></Input>
                    </Col>
                </div>
            </transition>
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==2">
                    <Col span="10">
                        <span class="conditionTitle">开关状态:</span>
                        <CheckboxGroup v-model="queryPrams.dataRangeGroup" style="position: relative;float: left;left:8px;top:6px;"
                                        size="large">
                            <Checkbox label="1">开</Checkbox>
                            <Checkbox label="2">关</Checkbox>
                        </CheckboxGroup>
                    </Col>
                </div>
            </transition>
            <transition name="slide-fade" mode="in-out">
                <div v-if="queryPrams.datatypeId==4||queryPrams.datatypeId==5">
                    <Col span="10">
                        <span class="conditionTitle">监测位置:</span>
                        <Input v-model="queryPrams.pleace" style="width: 120px;margin-left: 8px;  "></Input>
                        （米）
                    </Col>
                </div>
            </transition>
            <Col span="2">
                <Button type="primary" size="samll" icon="ios-search" @click="queryTableData"  v-if="!viewHistory">查询</Button>
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
                            <Button type="primary" shape="circle" icon="forward" size="large" title="历史数据"
                                    @click="viewHistoryData">历史数据</Button>
                            <Button type="info" shape="circle" icon="ios-cloud-download" size="large" title="导出"
                                    @click="exportData">导出</Button>
                            <Page class="nextPage" :total="queryPrams.total" :current="queryPrams.pageNum" :page-size="queryPrams.pageSize" show-sizer show-total
                                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator></Page>
                        </div>
                    </Col>
                </Row>
            </div>
        </transition>
        <transition name="fade" mode="out-in">
            <div v-if="viewHistory">
                <Row style="padding: 9px;padding-top: 0px">
                    <Col span="24">
                        <div class="chartSize">
                            <MultiLineChart style="width: 100%;" v-bind="curlineChart" ref="multiLine"></MultiLineChart>
                        </div>
                        <div class="chooseBox">
                            <div style="position: relative;float: left;font-size: 1.66vmin;line-height: 4vmin;">
                                <span class="timeTitle">时间周期:</span>
                                <Select v-model="historyPrams.dateType" style="width:12vw;margin-right: 4px;margin-left: 4px;"
                                        @on-change="changeAlarmType" placement="top">
                                    <Option v-for="item in historyDateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
                                </Select>
                            </div>
                            <div style="  position: relative;float: left; font-size: 1.66vmin;line-height: 4vmin;">
                                <span class="timeTitle">开始时间:</span>
                                <DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"
                                            placement="top"
                                            style="width: 12vw;margin-right: 4px;"></DatePicker>
                                <span class="timeTitle">结束时间:</span>
                                <DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"
                                            placement="top"
                                            style="width:12vw;margin-right: 14px;"></DatePicker>
                            </div>
                            <div class="btnBox">
                                <Button type="primary" shape="circle" icon="forward" size="large"
                                   @click="queryHistoryData" v-if="viewHistory">历史数据</Button>
                                <Button type="info" shape="circle" icon="ios-cloud-download" size="large" title="导出"
                                    @click="backToCurPage">导出</Button>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </transition>
    </div>
</template>

<script>
  import {EnumsService} from '../../../../services/enumsService.js'
  import {TunnelService} from '../../../../services/tunnelService.js'
  import {DataAnalysisService} from '../../../../services/dataAnalysisService.js'
  import MultiLineChart from '../../../../components/Common/Chart/MultiLineChart'
  import ShowMonitorObjectSelect from '../../../../components/Common/Modal/ShowMonitorObjectSelect'


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
                id: null,
                objtypeIds: [],
                datatypeId: null,
                maxVal: 100,
                minVal: 0,
                dataRangeGroup: [],
                pleace: 0,
                total: 0,
                pageNum: 1,
                pageSize: 10,
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
            selectData: {idList: ""},
            },
            historyDateType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {key: 4,value: "自定义"}],
            queryZoneList: [{key: 1, value: "自定义"}, {key: 2, value: "最近一天"}, {key: 3, value: "最近一周"}],
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
                            temp = params.row.obj.section.name
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
            tableData: []
        }
    },
    methods: {
        //查询监测对象
        queryObject() {
            let _this = this;
            _this.dataObjectSelect.show.state = !_this.dataObjectSelect.show.state;
        },

        //初始化查询条件下拉列表数据
        inItData() {
            var _this = this;
            const p1= EnumsService.getMonitorType().then((result) => {
                _this.objectList = result;
            });
            const p2 = EnumsService.getDataType().then((result) => {
                _this.dataTypeEnum = result;
                this.queryPrams.datatypeId = result[0].val;
            });
            const p3 = TunnelService.getTunnels().then((result) => {
                _this.tunnelList = [{id: -1, name: "全部"}];
                result.reduce((a,b)=>{
                    _this.tunnelList.push(b);
                }, _this.tunnelList)
            });
            Promise.all([p1,p2,p3]).then(([a, b,c]) => _this.queryTableData());
        },

        //导出数据
        exportData() {
            this.$refs.selection.exportCsv({
            filename: new Date().format("yyyy-MM-dd hh:mm:ss") + "导出数据",
            original: false
            });
        },

        queryTableData() {
            var _this = this;
            _this.tableload=true;
            var params= _this.queryPrams;
            DataAnalysisService.getMonitorData(params).then((result) => {
                _this.tableData = result.list
                _this.queryPrams.total = result.total;
                _this.tableload=false;
            })
        },

        viewHistoryData() {
            var _this = this;
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
            var _this = this;
            _this.curlineChart.parameters.queryPram.startTime = _this.historyPrams.startTime.getTime();
            _this.curlineChart.parameters.queryPram.endTime = _this.historyPrams.endTime.getTime();
            _this.curlineChart.parameters.queryPram.ids = _this.historyPrams.ids;
            _this.$refs.multiLine.fetchData();
        },

        backToCurPage() {
            var _this = this;
            _this.viewHistory = !_this.viewHistory;
        },

        selectionClick(arr) {
            this.selectSelection = arr;
        },
        handlePage(value) {
            this.queryPrams.pageNum = value;
            this.inItData()
        },
        handlePageSize(value) {
            this.queryPrams.pageSize = value;
            this.inItData()
        },

        //切换数据类型
        changeDataType(index) {
        },

        //更改告警时间类型
        changeAlarmType(index) {
            var _this = this;
            var date = new Date();
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
            else {
            _this.isReady = false;
            _this.historyPrams.startTime = "";
            _this.historyPrams.endTime = "";
            }
        },
    },
    components: {
        MultiLineChart, ShowMonitorObjectSelect
    },
    mounted() {
        this.inItData();
        // 设置表格高度
        this.tableHeight = window.innerHeight * 0.65;
    },
    watch: {
        "dataObjectSelect.selectData.idList": function () {
            this.queryPrams.id = this.dataObjectSelect.selectData.idList;
        }
    },
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

    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
    .ivu-select-multiple >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
        color: #fff;
        background-color: #fffdfd00 !important;
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
        border-bottom: none;
    }
    .nextPage .ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator,.nextPage.ivu-page>>>.ivu-page-total{
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
    .queryCondition .ivu-select{
        color: #fff;
    }
    .btnBox{
        position:relative;
        float: right;
        right: 0px;
    }
    .btnBox .ivu-btn-primary,.historyDiv .ivu-btn-primary{
        background: linear-gradient(to left, #2734e1, #b195ed)
    }
    .btnBox .ive-btn-info,.historyDiv .ive-btn-info{
        background: linear-gradient(to left, #1af6b0, #a7ecd7)
    }
    .chooseBox{
        height: 5.8vh;
        padding: 0.8vh;
        margin-top: 1vh;
        /* color: #fff; */
    }
    .timeTitle{
        color: #fff;
    }
</style>
