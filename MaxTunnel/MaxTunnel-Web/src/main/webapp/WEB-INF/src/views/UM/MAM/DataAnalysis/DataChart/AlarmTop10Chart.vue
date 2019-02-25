<!--告警top10统计报表-->
<template>
  <div>
    <Row class="conditions" style="margin-bottom: 0px;">
      <Col span="4">
        <span>时间周期：</span>
        <Select v-model="chartPrams.dateType" style="width:65%;" @on-change="changeAlarmType">
          <Option v-for="item in dateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
        </Select>
      </Col>
      <Col span="6">
        <span>开始时间：</span>
        <DatePicker v-model="chartPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"
                    style="width: 65%"></DatePicker>
      </Col>
      <Col span="6">
        <span>结束时间：</span>
        <DatePicker v-model="chartPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"
                    style="width:65%"></DatePicker>
      </Col>
      <Col span="4">
        <span class="explain">监测对象：</span>
        <Input v-model="chartPrams.objectId" readonly style="width: 65%;margin-top: 1.2vh;">
          <Button slot="append" icon="ios-search" style="height: 4vmin" @click="queryObject"></Button>
        </Input>
      </Col>
      <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
      <Col span="2" offset="2">
        <Button type="primary"  icon="ios-search" size="small" @click="createChart">生成报表</Button>
      </Col>
    </Row>
    <Row style="margin-top: 20px;">
      <Col span="12">
      <pile-bar-chart v-bind="pileBarChart" style="height: 76vh;" ref="pileBar"></pile-bar-chart>
      </Col>
      <Col span="12" style="padding: 10px;">
      <Table border :columns="columns" :data="tableData" stripe></Table>
      <div style="margin: 10px;font-size: 1.66vmin;line-height: 4vmin;">
        <span>报表时间:</span>
        {{reportTime}}
        <div style="float: right" v-if="startTime!=''">
          <span>时间范围:</span>
          {{startTime}}----{{endTime}}
        </div>
      </div>

      </Col>
    </Row>
  </div>
</template>

<script>
  import ShowMonitorObjectSelect from '../../../../../components/Common/Modal/ShowMonitorObjectSelect'
  import pileBarChart from '../../../../../components/Common/Chart/PileBarChart'
  import {ChartService} from '../../../../../services/chartService.js'

  export default {
    name: "alarm-top10-chart",
    data() {
      return {
        isReady: true,
        dateType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {
          key: 4,
          value: "自定义"
        }],
        chartPrams: {
          dateType: 1,
          startTime: "",
          endTime: "",
          objectId: "",
        },
        dataObjectSelect: {
          show: {state: false},
          selectObjects: {},
          selectData: {idList: ""},
        },
        pileBarChart: {
          id: 'alarmReportBar',
          requestUrl: "data-analyse/alarms/topN",
          parameters: {option: {}, prams: {}},
          xData: []
        },
        reportTime: "",
        startTime: "",
        endTime: "",
        columns: [
          {
            title: '对象名称',
            key: 'key',
            width: 200
          },
          {
            title: '提示告警',
            key: '提示'
          },
          {
            title: '一般告警',
            key: '一般'
          },
          {
            title: '严重告警',
            key: '严重'
          },
          {
            title: '致命告警',
            key: '致命'
          },
          {
            title: '总数',
            key: 'alarmCount'
          }
        ],
        tableData: []
      }
    },
    mounted() {
      this.changeAlarmType(this.chartPrams.dateType);
    },
    methods: {
      //查询监测对象.
      queryObject() {
        let _this = this;
        _this.dataObjectSelect.show.state = !_this.dataObjectSelect.show.state;
      },

      //更改告警时间类型
      changeAlarmType(index) {
        var _this = this;
        var date = new Date();
        if (index == 1) {
          date.setTime(date.getTime() - 3600 * 1000 * 24);
          _this.chartPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.chartPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else if (index == 2) {
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
          _this.chartPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.chartPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else if (index == 3) {
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
          _this.chartPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.chartPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else {
          _this.isReady = false;
          _this.chartPrams.startTime = "";
          _this.chartPrams.endTime = "";
        }
      },

      //生成告警报表
      createChart() {
        let _this = this;
        let prams = {};
        prams.requestUrl = _this.pileBarChart.requestUrl;
        prams.startTime = new Date(_this.chartPrams.startTime).getTime();
        prams.endTime = new Date(_this.chartPrams.endTime).getTime();
        prams.ids = _this.chartPrams.objectId.trim().slice(0, _this.chartPrams.objectId.trim().length - 1).split(",");
        _this.pileBarChart.parameters.prams = prams;
        ChartService.getSimpleBarDataWithClick(prams).then((result) => {
          if (result) {
            _this.tableData = result;
            var tempKey = Object.keys(result[0]);
            _this.tableData.forEach(a => {
              var alarmCount = 0;
              for (var key in a) {
                if (typeof (a[key]) == "number") {
                  alarmCount += a[key];
                }
                a.alarmCount = alarmCount;
              }
            })
          }
        })
        _this.$refs.pileBar.fetchData();
        _this.startTime = _this.chartPrams.startTime.format("yyyy-MM-dd hh:mm:ss");
        _this.endTime = _this.chartPrams.endTime.format("yyyy-MM-dd hh:mm:ss");
        _this.reportTime = _this.reportTime.format("yyyy-MM-dd hh:mm:ss");
      },
    },
    watch: {
      "dataObjectSelect.selectData.idList": function () {
        this.chartPrams.objectId = this.dataObjectSelect.selectData.idList;
      },
    },
    components: {
      ShowMonitorObjectSelect, pileBarChart
    },
  }
</script>

<style scoped>
  .top {
    margin: 10px;
    background-color: #fff;
    padding-left: 10px;
  }

  .explain {
    float: left;
  }

  .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
  .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
  .ivu-select-multiple.ivu-select-selection >>> .ivu-select-placeholder
  {
    height: 4vmin;
    line-height: 4vmin;
    font-size: 1.4vmin;
  }
</style>
