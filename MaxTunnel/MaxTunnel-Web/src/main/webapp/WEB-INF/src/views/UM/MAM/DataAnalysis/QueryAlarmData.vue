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
      <Select v-model="queryPrams.monitorZone" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">告警级别:</span>
      <Select v-model="queryPrams.alarmLevel" multiple  style="width:65%">
        <Option v-for="item in enumData.alarmLevel" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">监测对象:</span>
      <Select v-model="queryPrams.monitorObject" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">时间类型:</span>
      <Select v-model="queryPrams.alarmTimeType" style="width:65%">
        <Option v-for="item in enumData.timeType" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6" class="planDec">
      <span class="planDec">开始时间:</span>
      <DatePicker v-model="queryPrams.startTime" type="datetime" placeholder="选择结束时间"
                  style="width: 65%;"></DatePicker>
      </Col>
      <Col span="6">
      <span class="planDec">结束时间:</span>
      <DatePicker v-model="queryPrams.endTime" type="datetime" placeholder="选择结束时间"
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
        <Button type="primary" shape="circle" icon="ios-search" @click="queryAlarmData"
                style="position:relative;float:right">查询
        </Button>
        <Button type="primary" shape="circle" @click="clearAlarms"
                style="position:relative;float:right;;right: 20px; ">清除告警数据
        </Button>
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
          <div class="alarmCircle" style="background-color: #2fa7ee;">提示</div>
          <span class="alarmCircleText">{{alarmLevelCount[0]}}</span>
        </div>
        <div style="margin-left:80px;    float: left;">
          <div class="alarmCircle" style="background-color: #eed00f; ">一般</div>
          <span class="alarmCircleText">{{alarmLevelCount[1]}}</span>
        </div>
        <div style="margin-left:80px;    float: left;">
          <div class="alarmCircle" style="background-color: #ee6907; ">严重</div>
          <span class="alarmCircleText">{{alarmLevelCount[2]}}</span>
        </div>
        <div style="margin-left:80px; float: left;">
          <div class="alarmCircle" style="background-color: #ee082d;">危急</div>
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

  export default {
    name: "query-alarm-data",
    data() {
      return {
        viewHistory: false,
        selectSelection: null,
        curlineChart: {
          id: "historyDataChart",
          requestUrl: 'lineChart',
          titleName: '23',
          title: "历史数据",
          titleColor: '#030303',
          intervalTime: 1000,
        },
        queryPrams: {
          teamName:"",
          monitorObject: "",
          alarmTimeType:1,
          alarmLevel: [],
          monitorZone: "",
          dataType: "",
          endTime:"",
          startTime: "",
          total: 100,
          pageSize: 20,
          alarmClear: [],
        },
        enumData:{
          alarmLevel:[{key: 1, value: "提示"},{key: 2, value: "一般"},{key: 3, value: "严重"},{key: 4, value: "危急"}],
          timeType:[{key:1,value:"最近一天"},{key:2,value:"最近一周"},{key:3,value:"自定义"}],
        },
        dataTypeEnum: [{key: 1, value: "模拟量输入"}, {key: 2, value: "开关量输入"}, {key: 3, value: "状态量输入"}, {
          key: 4,
          value: "分布式数据"
        }, {key: 5, value: "频谱数据"}],
        cityList: [
          {
            value: 'New York',
            label: 'New York'
          },
          {
            value: 'London',
            label: 'London'
          },
          {
            value: 'Sydney',
            label: 'Sydney'
          },
          {
            value: 'Ottawa',
            label: 'Ottawa'
          },
          {
            value: 'Paris',
            label: 'Paris'
          },
          {
            value: 'Canberra',
            label: 'Canberra'
          }
        ],
        alarmLevelCount:[9342,554,32,10],
        tableColumn: [
          {
            type: 'selection',
            width: 70,
            align: 'center'
          },
          {
            title: '监测区域',
            key: 'monitorZone',
          },
          {
            title: '班组名称',
            key: 'teamName',
          },
          {
            title: '告警对象',
            key: 'alarmObject',
          },
          {
            title: '告警级别',
            key: 'alarmLevel',
          },
          {
            title: '告警类型',
            key: 'alarmType',
          },
          {
            title: '描述',
            key: 'description',
          },
          {
            title: '告警时间',
            key: 'alarmTime',
          },

          {
            title: '告警次数',
            key: 'alarmCount',
          },
          {
            title: '是否清除',
            key: 'isClear',
          }
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
            isClear: false
          },
          {
            teamName: "管仓1",
            monitorZone: '试验路管廊',
            alarmObject: 18,
            alarmLevel: 1,
            alarmType: 1,
            description: '测试数据',
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
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
            alarmTime:"2018-10-36 14:46",
            alarmCount: 100,
            isClear: false
          }
        ],
      }
    },
    methods: {
      queryAlarmData() {
        var _this = this;
        _this.viewHistory = !_this.viewHistory;
      },
      //勾选数据行
      selectionClick(arr) {
        this.selectSelection = arr;
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
      clearAlarms(alarmArray){

      },
    },
    components: {},
  }
</script>

<style scoped>
  .planDec {
    padding-right: 5px;
    font-size: 14px;
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
    color: #fff;
    border-radius: 100%;
    display: flex;
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

