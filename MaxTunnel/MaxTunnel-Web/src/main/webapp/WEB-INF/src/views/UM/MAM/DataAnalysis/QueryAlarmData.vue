<!--告警数据查询-->
<template>
  <div class="queryAlarm">
    <Row class="top">
      <Col span="6">
      <span class="planDec">告警状态:</span>
      <Select v-model="queryPrams.cleaned" style="width:65%">
        <Option v-for="item in alarmStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">所属管廊:</span>
      <Select v-model="queryPrams.tunnelId" style="width:64%">
        <Option v-for="item in tunnelList" :value="item.id" :key="item.id">{{ item.name }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">告警级别:</span>
      <Select v-model="queryPrams.alarmLevel" style="width:65%">
        <Option v-for="item in enumData.alarmLevel" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec" style="padding-top: 3px;">监测对象:</span>
      <Input v-model="queryPrams.objectId"  style="width: 65%;margin-top:14px;">
      <Button slot="append" icon="ios-search" style="height: 35px;" @click="queryObject"></Button>
      </Input>
      </Col>
      <Col span="6">
      <span class="planDec">时间类型:</span>
      <Select v-model="queryPrams.alarmTimeType" style="width:65%" @on-change="changeAlarmType">
        <Option v-for="item in enumData.timeType" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6" class="planDec">
      <span class="planDec">开始时间:</span>
      <DatePicker :readonly="isReady" v-model="queryPrams.startTime" type="datetime" placeholder="选择结束时间"
                  style="width: 65%;"></DatePicker>
      </Col>
      <Col span="6">
      <span class="planDec">结束时间:</span>
      <DatePicker :readonly="isReady" v-model="queryPrams.endTime" type="datetime" placeholder="选择结束时间"
                  style="width: 65%;"></DatePicker>
      </Col>
      <Col span="6">
      </Col>
    </Row>
    <Row style="padding-top: 0px;padding-right: 9px;padding-left: 9px;">
      <Col span="24">
      <div style="position:absolute; height: 40px;right: 5px;top:-55px;z-index: 10001">
        <Button type="primary" shape="circle" @click="queryAlarmData" title="查询" icon="ios-search" size="large"
                style="position:relative;float:right">
        </Button>
        <Button type="primary" shape="circle" @click="clearAlarms"
                style="position:relative;float:right;right: 20px;" title="清除告警数据" icon="ios-trash-outline" size="large">
        </Button>
        <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
        <ClearAlarm v-bind="alarmsClear"></ClearAlarm>
      </div>
      <Col span="24">
      </Col>
        <Table :height="tableHeight" stripe border :columns="tableColumn" :data="tableData" ref="selection"
               :loading="tableLoad"
               @on-selection-change="selectionClick"></Table>
      </Col>
    </Row>
    <div style="height: 50px;margin:9px;background-color: #fff;position: absolute;width: 99%">
      <div style="position:absolute;height: 100%;">
        <div style="margin-left:8px;float: left;height: 100%">
          <div class="alarmCircle" style="background-color: #0066ff;">提示</div>
          <span class="alarmCircleText">{{alarmLevelCount[0]}}</span>
        </div>
        <div style="margin-left:80px;    float: left;">
          <div class="alarmCircle" style="background-color: #ffff00; ">一般</div>
          <span class="alarmCircleText">{{alarmLevelCount[1]}}</span>
        </div>
        <div style="margin-left:80px;    float: left;">
          <div class="alarmCircle" style="background-color: #ffae00; ">严重</div>
          <span class="alarmCircleText">{{alarmLevelCount[2]}}</span>
        </div>
        <div style="margin-left:80px; float: left;">
          <div class="alarmCircle" style="background-color: #ff0000;">危急</div>
          <span class="alarmCircleText">{{alarmLevelCount[3]}}</span>
        </div>
      </div>
      <Page class="nextPage" @on-change="changePage" @on-page-size-change="handlePageSize"
            :total="queryPrams.total"
            show-total show-elevator show-sizer placement="top"
            :page-size="queryPrams.pageSize"></Page>
    </div>
  </div>
</template>

<script>

  import {EnumsService} from '../../../../services/enumsService.js'
  import {TunnelService} from '../../../../services/tunnelService.js'
  import {DataAnalysisService} from '../../../../services/dataAnalysisService.js'
  import ClearAlarm from '../../../../components/Common/Modal/ClearAlarm'
  import ShowMonitorObjectSelect from '../../../../components/Common/Modal/ShowMonitorObjectSelect'

  export default {
    name: "query-alarm-data",
    data() {
      return {
        tableHeight:450,
        selectSelection: null,
        tableLoad: true,
        isReady: true,
        tunnelList: [],
        dataObjectSelect: {
          show: {state: false},
          selectObjects: {},
          selectData: {idList: ""},
        },
        alarmsClear: {
          modalPrams: {state: false, ids: [],},
        },
        curlineChart: {
          id: "historyDataChart",
          requestUrl: 'lineChart',
          titleName: '23',
          title: "历史数据",
          titleColor: '#030303',
          intervalTime: 1000,
        },
        queryPrams: {
          cleaned: -1,
          tunnelId: -1,
          alarmLevel: -1,
          objectId: "",
          alarmTimeType: 1,
          monitorZone: "",
          endTime: "",
          startTime: "",
          total: 0,
          pageSize: 12,
          pageNum: 10,
        },
        enumData: {
          alarmLevel: [{key: -1, value: "全部"}, {key: 1, value: "提示"}, {key: 2, value: "一般"}, {
            key: 3,
            value: "严重"
          }, {key: 4, value: "危急"}],
          timeType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {key: 4, value: "自定义"}],
        },
        dataTypeEnum: [],
        alarmStatusList: [{value: -1, label: "全部"}, {value: 1, label: "已清除"}, {value: 0, label: "未清除"}],
        alarmLevelCount: [0, 0, 0, 0],
        tableColumn: [
          {type: 'selection', width: 60, align: 'center'},
          {title: 'Id', key: 'id', width: 80,},
          {title: '所属管廊', key: 'tuunelName',},
          {title: '监测对象名', key: 'objectName',},
          {title: '告警级别', key: 'alarmLevel',width: 110,},
          // {title: '告警类型', key: 'alarmType',},
          {title: '描述', key: 'description',},
          {title: '告警时间', key: 'alarmTime',width: 200,},
          {title: '告警次数', key: 'alarmCount',width: 110,},
          {title: '是否清除', key: 'isClear',},
          {title: '清除时间', key: 'clearTime',width: 200,},
          {
            title: '操作',
            key: 'action',
            width: 100,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'success',
                    size: 'default'
                  },
                  style: {
                    marginRight: '5px',
                    display: (params.row.isClear == "已清除") ? "none" : "inline-block",
                  },
                  on: {
                    click: () => {
                      this.clearAlarms(params.index)
                    }
                  }
                }, '清除')
              ]);
            }
          },
        ],
        tableData: [],
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
        EnumsService.getMonitorType().then((result) => {
          _this.objectList = result;
        });
        EnumsService.getDataType().then((result) => {
          _this.dataTypeEnum = result;
        });
        TunnelService.getTunnels().then((result) => {
          _this.tunnelList = [{id: -1, name: "全部"}];
          result.reduce((a,b)=>{
            _this.tunnelList.push(b);
          }, _this.tunnelList)
        });
        DataAnalysisService.getAlarmCountByAlarmLevel().then((result) => {
          if (result) {
            _this.alarmLevelCount = [];
            result.forEach(a => {
              _this.alarmLevelCount.push(a.val);
            })

          }
        });
      },

      queryAlarmData() {
        var _this = this;
        var prams = {};
        _this.tableLoad = true;
        prams.startTime = _this.queryPrams.startTime != "" ? new Date(_this.queryPrams.startTime).getTime() : 0
        prams.endTime = _this.queryPrams.endTime != "" ? new Date(_this.queryPrams.endTime).getTime() : 0
        prams.tunnelId = _this.queryPrams.tunnelId !=-1 ? _this.queryPrams.tunnelId : ""
        prams.cleaned = _this.queryPrams.cleaned !=-1 ? _this.queryPrams.cleaned : ""
        prams.alarmLevel = _this.queryPrams.alarmLevel !=-1 ? _this.queryPrams.alarmLevel : ""
        prams.pageNum = _this.queryPrams.pageNum
        prams.pageSize = _this.queryPrams.pageSize
        DataAnalysisService.getAlarmData(prams).then((result) => {
          if (result) {
            _this.tableData = [];
            _this.queryPrams.total = result.total;
            result.list.forEach(a => {
              let temp = {};
              temp.id = a.id;
              temp.tuunelName = a.tunnel.name;
              temp.alarmLevel = a.alarmLevelName;
              temp.objectName = a.objectName;
              temp.description = a.description;
              temp.alarmCount = 1;
              temp.isClear = a.cleaned ? "已清除" : "未清除";
              temp.alarmTime = new Date(a.alarmDate).format("yyyy-MM-dd hh:mm:ss");
              temp.clearTime = a.cleanedDate != null ? new Date(a.cleanedDate).format("yyyy-MM-dd hh:mm:ss") : "";
              _this.tableData.push(temp);
            })
          }
        }).then(() => {
          _this.tableLoad = false;
        })
      },
      //勾选数据行
      selectionClick(arr) {
        var _this = this;
        _this.selectSelection = arr;
        if (arr.length > 0) {
          _this.alarmsClear.modalPrams.ids = [];
          arr.forEach(a => {
            _this.alarmsClear.modalPrams.ids.push(a.id);
          })
        }
      },
      //更改告警时间类型
      changeAlarmType(index) {
        var _this = this;
        var date = new Date();
        if (index == 1) {
          date.setTime(date.getTime() - 3600 * 1000 * 24);
          _this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else if (index == 2) {
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
          _this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else if (index == 3) {
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
          _this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
          _this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          _this.isReady = true;
        }
        else {
          _this.isReady = false;
          _this.queryPrams.startTime = "";
          _this.queryPrams.endTime = "";
        }
      },
      //切换页面
      changePage(index) {
        let _this = this;
        _this.queryPrams.pageNum = index;
        _this.queryAlarmData();
      },
      //切换页码数
      handlePageSize(value) {
        this.queryPrams.pageSize = value;
        this.queryAlarmData();
      },

      //清除勾选告警
      clearAlarms(index) {
        var _this = this;
        if (typeof (index) === "number") {
          _this.alarmsClear.modalPrams.ids = [_this.tableData[index].id];
        }
        _this.alarmsClear.modalPrams.state = !_this.alarmsClear.modalPrams.state;
      },
    },
    components: {
      ShowMonitorObjectSelect,
      ClearAlarm
    },
    watch: {
      "dataObjectSelect.selectData.idList": function () {
        this.queryPrams.objectId = this.dataObjectSelect.selectData.idList;
      },
      "alarmsClear.modalPrams.state": function () {
        if (!this.alarmsClear.modalPrams.state && this.alarmsClear.modalPrams.ids.length > 0) {
          this.queryAlarmData();
        }

      }
    },
    mounted() {
      this.tableHeight=window.innerHeight*0.85-180
      this.inItData();
      this.changeAlarmType(this.queryPrams.alarmTimeType);
      this.queryAlarmData();
    },
  }
</script>

<style scoped>
  .planDec {
    padding-right: 5px;
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
    height: 100px;
    line-height: 50px;
    background: #fff;
    padding-left: 10px;
  }

  .nextPage {
    float: right;
    position: relative;
    top: 8px;
    right: 8px;
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

  .ivu-select-single > > > .ivu-select-selection > > > .ivu-select-selected-value {
    font-size: 16px;
  }

  .ivu-select-single > > > .ivu-select-selection > > > .ivu-select-placeholder {
    font-size: 16px;
  }

  .queryAlarm {
    position: relative;
    min-height: 96%;
    padding-bottom: 40px;
  }

  .alarmCircle {
    width: 35px;
    height: 35px;
    color: #a79d9d;
    border-radius: 100%;
    display: flex;
    -moz-box-shadow: 2px 3px 12px #5DA0AD;
    -webkit-box-shadow: 2px 3px 12px #5DA0AD;
    box-shadow: 2px 3px 12px #5DA0AD;
    justify-content: center;
    align-items: Center;
    margin-top: 10px;
    float: left;
    font-size: 15px;
  }

  .alarmCircleText {
    line-height: 53px;
    margin-left: 6px;
    font-size: 16px;
    color: #a79d9d;
  }
</style>

