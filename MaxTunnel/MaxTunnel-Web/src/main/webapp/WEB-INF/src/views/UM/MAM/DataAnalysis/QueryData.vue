<!--数据查询-->
<template>
  <div>
    <Row class="top">
      <Col span="6">
      <span class="planDec">对象类型:</span>
      <Select v-model="queryPrams.objectType" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">监测区域:</span>
      <Select v-model="queryPrams.queryZone" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">数据类型:</span>
      <Select v-model="queryPrams.dataType" style="width:65%">
        <Option v-for="item in dataTypeEnum" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="1" offset="5" style="position: relative;float: right;right: 20px;top:25px;">
      <span class="planDec"> <Button type="primary" shape="circle" icon="ios-search" @click="queryTableData">查询</Button></span>
      </Col>
      <Col span="6">
      <span class="planDec">监测对象:</span>
      <Select v-model="queryPrams.monitorObject" style="width:65%">
        <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      </Col>
      <Col span="10" class="planDec">
      <span>取整范围:</span>
      最小值
      <Input v-model="queryPrams.minVal" style="width: 100px"></Input>
      最大值
      <Input v-model="queryPrams.maxVal" style="width: 100px"></Input>
      </Col>
    </Row>
    <transition name="slide-fade" mode="in-out">
      <div v-if="!viewHistory">
        <Row style="padding-top: 0px;padding-right: 9px;padding-left: 9px;">
          <Col span="24">
          <div style="position:relative; line-height: 50px;background-color: #e5eae99c;">
            <Button type="primary" @click="queryTableData" style="margin-left: 4px;">查看历史数据</Button>
            <Page class="nextPage" @on-change="changePage" @on-page-size-change="handlePageSize"
                  :total="queryPrams.total"
                  show-total show-elevator
                  :page-size="queryPrams.pageSize"></Page>
          </div>
          <Table height="620" stripe border :columns="tableColumn" :data="tableData" ref="selection"
                 @on-selection-change="selectionClick"></Table>
          </Col>
        </Row>
      </div>
    </transition>
    <transition name="fade" mode="out-in">
      <div v-if="viewHistory">
        <Row style="padding: 9px;padding-top: 0px">
          <Col span="24">
          <div style="position:relative;" class="queryHis">
            <Select v-model="historyPrams.dateType" style="width:250px;margin-right: 4px;margin-left: 4px;">
              <Option v-for="item in historyDateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
            </Select>
            <span>开始时间:</span>
            <DatePicker v-model="historyPrams.startTime" type="datetime" placeholder="选择开始时间"
                        style="width: 180px;margin-right: 4px;"></DatePicker>
            <span>结束时间:</span>
            <DatePicker v-model="historyPrams.endTIme" type="datetime" placeholder="选择结束时间"
                        style="width: 180px;margin-right: 4px;"></DatePicker>
            <Button type="primary" shape="circle" icon="ios-search"></Button>
            <Button type="primary" shape="circle" icon="ios-cloud-download"></Button>
            <Button class="nextPage" type="primary" @click="queryTableData" style="margin-top: 7px;">返回</Button>
            <div style="">
              <SimplelineChart v-band="curlineChart" style="width: 100%;height: 620px;"></SimplelineChart>
            </div>
          </div>
          </Col>
        </Row>
      </div>
    </transition>
  </div>
</template>

<script>
  import SimplelineChart from '../../../../components/Common/Chart/SimpleLineChart.vue'

  export default {
    name: "query-data",
    data() {
      return {
        viewHistory: false,
        curlineChart: {
          id: "historyDataChart",
          requestUrl: 'lineChart',
          titleName: '23',
          title: "历史数据",
          titleColor: '#030303',
          intervalTime: 1000,
        },
        queryPrams: {
          monitorObject: "",
          objectType: '',
          queryZone: "",
          dataType: "",
          maxVal: 100,
          minVal: 0,
          total: 100,
          pageSize: 20,
        },
        historyPrams: {
          startTime: "",
          endTIme: "",
          dateType: 1,
        },
        historyDateType: [{key: 1, value: "自定义"}, {key: 2, value: "最近一天"}, {key: 3, value: "最近一周"}],
        dataTypeEnum: [{key: 1, value: "模拟量输入"}, {key: 2, value: "开关量输入"}, {key: 3, value: "状态量输入"}, {
          key: 4,
          value: "分布式数据"
        }, {key: 5, value: "频谱数据"}],
        selectSelection: null,
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
        tableColumn: [
          {
            type: 'selection',
            width: 70,
            align: 'center'
          },
          {
            title: '对象编号',
            key: 'age',
          },
          {
            title: '监测区域',
            key: 'name',
          },
          {
            title: '对象名称',
            key: 'province',
          },
          {
            title: '对象类型',
            key: 'city',
          },
          {
            title: '监测值',
            key: 'address',
          },
          {
            title: '采集时间',
            key: 'zip',
          }
        ],
        tableData: [
          {
            name: 'John Brown',
            age: 18,
            address: 'New York No. 1 Lake Park',
            province: 'America',
            city: 'New York',
            zip: 100000
          },
          {
            name: 'Jim Green',
            age: 24,
            address: 'Washington, D.C. No. 1 Lake Park',
            province: 'America',
            city: 'Washington, D.C.',
            zip: 100000
          },
          {
            name: 'Joe Black',
            age: 30,
            address: 'Sydney No. 1 Lake Park',
            province: 'Australian',
            city: 'Sydney',
            zip: 100000
          },
          {
            name: 'Jon Snow',
            age: 26,
            address: 'Ottawa No. 2 Lake Park',
            province: 'Canada',
            city: 'Ottawa',
            zip: 100000
          }
        ],
      }
    },
    methods: {
      queryTableData() {
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
      }
      ,
      //切换页码数
      handlePageSize(value) {
        this.queryPrams.pageSize = value;
        this.queryTableData();
      }
    },
    components: {SimplelineChart},
  }
</script>

<style scoped>
  .planDec {
    padding-right: 5px;
    font-size: 14px;
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

  .ivu-select-single > > > .ivu-select-selection > > > .ivu-select-selected-value {
    font-size: 16px;
  }

  .ivu-select-single > > > .ivu-select-selection > > > .ivu-select-placeholder {
    font-size: 16px;
  }
</style>
