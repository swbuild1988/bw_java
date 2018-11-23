<!--数据查询-->
<template>
  <div>
    <Row class="top" style="margin-bottom: 0px;">
      <Col span="6" class="col">
      <span class="planDec">对象类型:</span>
      <Select v-model="queryPrams.objectType" style="width:65%">
        <OptionGroup v-for="(group, index) in objectList" :label="group.key" :key="index" style="font-size: 18px;">
          <Option v-for="item in group.objectTypeList" :value="item.val" :key="item.val">{{ item.key }}</Option>
        </OptionGroup>
      </Select>
      </Col>
      <Col span="6" class="col">
      <span class="planDec">监测区域:</span>
      <treeselect :options="zoneList" placeholder="请选择" v-model="queryPrams.queryZone"
                  style="width: 60%;float: left;left: 8px"/>
      </Col>
      <Col span="6" class="col">
      <span class="planDec">数据类型:</span>
      <Select v-model="queryPrams.dataType" style="width:65%" @on-change="changeDataType">
        <Option v-for="item in dataTypeEnum" :value="item.val" :key="item.val">{{ item.key }}</Option>
      </Select>
      </Col>
    </Row>

    <Row class="top" style="margin-top: 0px;">
      <Col span="6" class=" col">
      <span class="planDec">监测对象:
      </span>
      <Input v-model="queryPrams.monitorObject" readonly style="width: 65%;">
      <Button slot="append" icon="ios-search" style="height: 35px;" @click="queryObject"></Button>
      </Input>
      </Col>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==1">
          <Col span="10" class="col">
          <span class="planDec">取整范围:</span>
          最小值
          <Input v-model="queryPrams.minVal" style="width: 100px"></Input>
          最大值
          <Input v-model="queryPrams.maxVal" style="width: 100px"></Input>
          </Col>
        </div>
      </transition>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==2">
          <Col span="10" class="col">
          <span class="planDec">开关状态:</span>
          <CheckboxGroup v-model="queryPrams.dataRangeGroup" style="position: relative;float: left;left:8px;top:6px;"
                         size="large">
            <Checkbox label="1">开</Checkbox>
            <Checkbox label="2">关</Checkbox>
          </CheckboxGroup>
          </Col>
        </div>
      </transition>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==4||queryPrams.dataType==5">
          <Col span="10" class="col">
          <span class="planDec">监测位置:</span>
          <Input v-model="queryPrams.pleace" style="width: 120px;margin-left: 8px;  "></Input>
          （米）
          </Col>
        </div>
      </transition>
      <Col span="1" style="position: relative;float: right;right: 10px;top:-20px;">
      <Button type="primary" shape="circle" icon="ios-search" @click="queryTableData" title="查询" size="large"></Button>
      </Col>
      <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
    </Row>
    <transition name="slide-fade" mode="in-out">
      <div v-if="!viewHistory">
        <Row style="padding-top: 0px;padding-right: 9px;padding-left: 9px;">
          <Col span="24">
          <Table class="tablesize" stripe border :columns="tableColumn" :data="tableData" ref="selection"
                 @on-selection-change="selectionClick"></Table>
          <div style="position:relative; line-height: 40px;background-color:#f1f1f1;">
            <Button type="primary" shape="circle" icon="forward" size="large" title="查看历史数据"
                    @click="queryHistoryData"></Button>
            <Button type="primary" shape="circle" icon="ios-cloud-download" size="large" title="导出"
                    @click="exportData"></Button>
            <Page class="nextPage" @on-change="changePage" @on-page-size-change="handlePageSize"
                  :total="queryPrams.total"
                  show-total show-elevator
                  :page-size="queryPrams.pageSize"></Page>
          </div>
          </Col>
        </Row>
      </div>
    </transition>
    <transition name="fade" mode="out-in">
      <div v-if="viewHistory">
        <Row style="padding: 9px;padding-top: 0px">
          <Col span="24">
          <div style="position:relative;" class="queryHis">
              <div class="chartSize">
              <SimplelineChart style="width: 100%;" v-bind="curlineChart"></SimplelineChart>
            </div>
            <div style="height: 50px;">
              <div style="position: relative;float: left;">
                <!--<span>图表类型:</span>-->
                <!--<Select v-model="historyPrams.chartType" style="width:250px;margin-right: 4px;margin-left: 4px;"-->
                        <!--@on-change="changeAlarmType" placement="top">-->
                  <!--<Option v-for="item in chartOptions" :value="item.key" :key="item.key">{{ item.value }}</Option>-->
                <!--</Select>-->
                <span>时间周期:</span>
                <Select v-model="historyPrams.dateType" style="width:250px;margin-right: 4px;margin-left: 4px;"
                        @on-change="changeAlarmType" placement="top">
                  <Option v-for="item in historyDateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
              </div>
              <div style="position: relative;float: left; ">
                <span>开始时间:</span>
                <DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"
                            style="width: 220px;margin-right: 4px;"></DatePicker>
                <span>结束时间:</span>
                <DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"
                            style="width:220px;margin-right: 14px;"></DatePicker>
              </div>
              <!--<div v-else style="position: relative;float: left;">-->
                <!--<span>时间周期1:</span>-->
                <!--<DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"-->
                            <!--style="width: 220px;margin-right: 4px;"></DatePicker>-->
                <!--<span>时间周期2:</span>-->
                <!--<DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"-->
                            <!--style="width:220px;margin-right: 14px;"></DatePicker>-->
              <!--</div>-->
              <div style="position:relative;float: right;right: 20px;">
                <Button type="primary" shape="circle" icon="ios-search" size="large" title="查询历史数据"></Button>
                <Button type="primary" shape="circle" @click="backToCurPage" icon="reply" size="large"
                        title="返回"></Button>
              </div>
            </div>
          </div>
          </Col>
        </Row>
      </div>
    </transition>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { EnumsService } from "../../../../services/enumsService.js";
import SimplelineChart from "../../../../components/Common/Chart/SimpleLineChart.vue";
import ShowMonitorObjectSelect from "../../../../components/Common/Modal/ShowMonitorObjectSelect";

export default {
    name: "query-data",
    data() {
        return {
            viewHistory: false,
            isReady: true,
            zoneList: [],
            curlineChart: {
                id: "historyDataChart",
                requestUrl: "lineChart",
                parameters: {
                    option: {
                        title: {
                            text: "历史数据",
                            textStyle: {
                                color: "#030303"
                            }
                        }
                    },
                    timer: {
                        interval: 10000
                    }
                }
            },
            queryPrams: {
                monitorObject: "",
                objectType: "",
                queryZone: null,
                dataType: 1,
                maxVal: 100,
                minVal: 0,
                dataRangeGroup: [],
                pleace: 0,
                total: 100,
                pageSize: 20
            },
            historyPrams: {
                startTime: "",
                endTime: "",
                dateType: 1
            },
            dataObjectSelect: {
                show: { state: false },
                selectObjects: {},
                selectData: { idList: "" }
            },
            historyDateType: [
                { key: 1, value: "最近一天" },
                { key: 2, value: "最近一周" },
                { key: 3, value: "最近一月" },
                {
                    key: 4,
                    value: "自定义"
                }
            ],
            queryZoneList: [
                { key: 1, value: "自定义" },
                { key: 2, value: "最近一天" },
                { key: 3, value: "最近一周" }
            ],
            dataTypeEnum: [],
            selectSelection: null,
            objectList: [],
            tableColumn: [
                {
                    type: "selection",
                    width: 70,
                    align: "center"
                },
                {
                    title: "对象编号",
                    key: "age"
                },
                {
                    title: "监测区域",
                    key: "name"
                },
                {
                    title: "对象名称",
                    key: "province"
                },
                {
                    title: "对象类型",
                    key: "city"
                },
                {
                    title: "监测值",
                    key: "address"
                },
                {
                    title: "采集时间",
                    key: "zip"
                }
            ],
            tableData: [
                {
                    name: "电力仓",
                    age: 1012,
                    address: "15",
                    province: "温度1",
                    city: "模拟量",
                    zip: "2018-6-7 12:23:00"
                },
                {
                    name: "电力仓",
                    age: 1012,
                    address: "15",
                    province: "温度1",
                    city: "模拟量",
                    zip: "2018-6-7 12:23:00"
                },
                {
                    name: "电力仓",
                    age: 1012,
                    address: "15",
                    province: "温度1",
                    city: "模拟量",
                    zip: "2018-6-7 12:23:00"
                },
                {
                    name: "电力仓",
                    age: 1012,
                    address: "15",
                    province: "温度1",
                    city: "模拟量",
                    zip: "2018-6-7 12:23:00"
                }
            ]
        };
    },
    methods: {
        //查询监测对象
        queryObject() {
            let _this = this;
            _this.dataObjectSelect.show.state = !_this.dataObjectSelect.show
                .state;
        },

        //初始化查询条件下拉列表数据
        inItData() {
            var _this = this;
            EnumsService.getMonitorType().then(result => {
                _this.objectList = result;
            });
            EnumsService.getDataType().then(result => {
                _this.dataTypeEnum = result;
            });
            EnumsService.getMonitorZone().then(result => {
                var _this = this;
                if (result) {
                    result.forEach(a => {
                        var temp = {};
                        temp.id = a.id;
                        temp.label = a.name;
                        temp.children = [];
                        _this.zoneList.push(temp);
                        if (a.list.length > 0) {
                            a.list.forEach(b => {
                                var child = {};
                                child.id = a.id + "_" + b.id;
                                child.label = b.name;
                                child.children = [];
                                temp.children.push(child);
                                if (b.list.length > 0) {
                                    b.list.forEach(c => {
                                        var child2 = {};
                                        child2.id =
                                            a.id + "_" + b.id + "_" + c.id;
                                        child2.label = c.name;
                                        child.children.push(child2);
                                    });
                                }
                            });
                        }
                    });
                }
            });
        },

        //导出数据
        exportData() {
            console.log(this.$refs.selection);
            this.$refs.selection.exportCsv({
                filename: new Date().format("yyyy-MM-dd hh:mm:ss") + "导出数据",
                original: false
            });
        },

        queryTableData() {
            var _this = this;
        },

        queryHistoryData() {
            var _this = this;
            _this.viewHistory = !_this.viewHistory;
            _this.dataObjectSelect.state = !_this.dataObjectSelect.state;
            _this.changeAlarmType(_this.historyPrams.dateType);
        },
        backToCurPage() {
            var _this = this;
            _this.viewHistory = !_this.viewHistory;
        },

        selectionClick(arr) {
            this.selectSelection = arr;
        },
        //切换页面
        changePage(index) {
            let _this = this;
            _this.queryPrams.pageNum = index;
            _this.queryTableData();
        },
        //切换页码数
        handlePageSize(value) {
            this.queryPrams.pageSize = value;
            this.queryTableData();
        },
        //切换数据类型
        changeDataType(index) {},
        //更改告警时间类型
        changeAlarmType(index) {
            var _this = this;
            var date = new Date();
            if (index == 1) {
                date.setTime(date.getTime() - 3600 * 1000 * 24);
                _this.historyPrams.startTime = date.format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.historyPrams.endTime = new Date().format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.isReady = true;
            } else if (index == 2) {
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                _this.historyPrams.startTime = date.format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.historyPrams.endTime = new Date().format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.isReady = true;
            } else if (index == 3) {
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
                _this.historyPrams.startTime = date.format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.historyPrams.endTime = new Date().format(
                    "yyyy-MM-dd hh:mm:ss"
                );
                _this.isReady = true;
            } else {
                _this.isReady = false;
                _this.historyPrams.startTime = "";
                _this.historyPrams.endTime = "";
            }
        }
    },
    components: {
        SimplelineChart,
        ShowMonitorObjectSelect,
        Treeselect
    },
    mounted() {
        this.inItData();
    },
    watch: {
        "dataObjectSelect.selectData.idList": function() {
            this.queryPrams.monitorObject = this.dataObjectSelect.selectData.idList;
        }
    }
};
</script>

<style scoped>
.col {
    height: 60px;
    padding-top: 10px;
}

.planDec {
    padding: 4px;
    font-size: 14px;
    float: left;
}

.queryHis {
    padding-right: 5px;
    background-color: #e5eae99c;
    line-height: 50px;
    font-size: 16px;
}

.queryEquipment {
    position: relative;
    min-height: 100%;
    padding-bottom: 50px;
}

.top {
    margin: 10px;
    background-color: #f1f1f1;
    padding-left: 10px;
}

.nextPage {
    position: relative;
    bottom: 0px;
    right: 7px;
    float: right;
}

.slide-fade-enter-active {
    transition: all 0.5s ease;
}

.slide-fade-leave-active {
    transition: all 0.5s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter,
.slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
}

.ivu-select-single > .ivu-select-selection > .ivu-select-selected-value {
    font-size: 16px;
}

.ivu-select-single > .ivu-select-selection > .ivu-select-placeholder {
    font-size: 16px;
}
.tablesize {
    height: calc(95vh - 255px);
    width: 100%;
  }

  .chartSize {
    height: calc(95vh - 265px);
    width: 100%;
  }
</style>
