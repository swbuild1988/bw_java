<!--告警数据查询-->
<template>
  <div class="queryAlarm">
    <Row class="top">
      <Col span="6">
      <span class="planDec">班组名称:</span>
      <Select v-model="queryPrams.teamName" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">监测区域:</span>
      <treeselect :options="zoneList" placeholder="请选择" v-model="queryPrams.queryZone"
                  style="width: 64%;float: left;line-height: 16px;margin-top: 14px;"/>
      </Col>
      <Col span="6">
      <span class="planDec">告警级别:</span>
      <Select v-model="queryPrams.alarmLevel" multiple style="width:65%">
        <Option v-for="item in enumData.alarmLevel" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">监测对象:</span>
      <Input v-model="queryPrams.monitorObject" readonly style="width: 65%;margin-top:14px;">
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
      <span style="font-size: 16px;float: left; ">告警状态:</span>
      <CheckboxGroup v-model="queryPrams.alarmClear" style="position: relative;float: left;left: 10px;">
        <Checkbox label="已清除"></Checkbox>
        <Checkbox label="未清除"></Checkbox>
      </CheckboxGroup>
      </Col>
    </Row>
    <Row style="padding-top: 0px;padding-right: 9px;padding-left: 9px;">
      <Col span="24">
      <div style="position:relative; height: 40px;background-color: #e5eae99c;margin-bottom: 4px;">
        <Button type="primary" shape="circle" @click="queryAlarmData" title="查询"
                style="position:relative;float:right">
          <Icon type="ios-search"></Icon>
        </Button>
        <Button type="primary" shape="circle" @click="clearAlarms"
                style="position:relative;float:right;right: 20px;" title="清除告警数据">
          <Icon type="ios-trash-outline"></Icon>
        </Button>
        <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
      </div>
      <Col span="24">
      </Col>
      <div class="tablesize">
        <Table style="height: 100%" stripe border :columns="tableColumn" :data="tableData" ref="selection"
               @on-selection-change="selectionClick"></Table>
      </div>
      </Col>
    </Row>
    <div style="height: 50px;margin:9px;background-color: #98c1ab;position: absolute;width: 99%">
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
            show-total show-elevator
            :page-size="queryPrams.pageSize"></Page>
    </div>
  </div>
</template>

<script>

  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {EnumsService} from '../../../../services/enumsService.js'
  import ShowMonitorObjectSelect from '../../../../components/Common/Modal/ShowMonitorObjectSelect'

  export default {
    name: "query-alarm-data",
    data() {
      return {
        selectSelection: null,
        isReady: true,
        zoneList: [],
        dataObjectSelect: {
          show: {state: false},
          selectObjects: {},
          selectData: {idList: ""},
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
          teamName: "",
          monitorObject: "",
          alarmTimeType: 4,
          alarmLevel: [],
          monitorZone: "",
          endTime: "",
          startTime: "",
          total: 100,
          pageSize: 20,
          alarmClear: [],
        },
        enumData: {
          alarmLevel: [{key: 1, value: "提示"}, {key: 2, value: "一般"}, {key: 3, value: "严重"}, {key: 4, value: "危急"}],
          timeType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {key: 4, value: "自定义"}],
        },
        dataTypeEnum: [],
        cityList: [],
        alarmLevelCount: [9342, 554, 32, 10],
        tableColumn: [
          {type: 'selection', width: 70, align: 'center'},
          {title: '监测区域', key: 'monitorZone',},
          {title: '班组名称', key: 'teamName',},
          {title: '告警对象', key: 'alarmObject',},
          {title: '告警级别', key: 'alarmLevel',},
          {title: '告警类型', key: 'alarmType',},
          {title: '描述', key: 'description',},
          {title: '告警时间', key: 'alarmTime',},
          {title: '告警次数', key: 'alarmCount',},
          {title: '是否清除', key: 'isClear',}
        ],
        tableData: [
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmCount: 100,
            alarmTime: "2018-10-36 14:46",
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmCount: 100,
            alarmTime: "2018-10-36 14:46",
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime: "2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          }
        ],
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
        EnumsService.getMonitorZone().then((result) => {
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
                      child2.id = a.id + "_" + b.id + "_" + c.id;
                      child2.label = c.name;
                      child.children.push(child2);
                    })
                  }
                })
              }
            })
          }
        })
      },

      queryAlarmData() {
        var _this = this;
      },
      //勾选数据行
      selectionClick(arr) {
        this.selectSelection = arr;
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
          _this.historyPrams.startTime = "";
          _this.historyPrams.endTime = "";
        }
      },
      //切换页面
      changePage(index) {
        let _this = this;
        _this.queryPrams.pageNum = index;
        _this.queryAlarmData();
      }
      ,
      //切换页码数
      handlePageSize(value) {
        this.queryPrams.pageSize = value;
        this.queryAlarmData();
      },

      //清除勾选告警
      clearAlarms(alarmArray) {
      },
    },
    components: {
      ShowMonitorObjectSelect, Treeselect
    },
    watch: {
      "dataObjectSelect.selectData.idList": function () {
        this.queryPrams.monitorObject = this.dataObjectSelect.selectData.idList;
      }
    },
    mounted() {
      this.inItData();
    },
  }
</script>

<style scoped>
  .planDec {
    padding-right: 5px;
    font-size: 14px;
    float: left;
  }

  .tablesize {
    height: calc(94vh - 310px);
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
    height: 120px;
    line-height: 60px;
    background: #f1f1f1;
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
    color: #fff;
  }
</style>

