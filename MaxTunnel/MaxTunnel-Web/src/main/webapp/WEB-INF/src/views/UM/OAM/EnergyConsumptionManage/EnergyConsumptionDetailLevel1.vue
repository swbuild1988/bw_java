<!--管廊能耗总览层级1-->
<template>
  <div>
    <Row class="top">
      <Col span="4" class="col">
      <span>时间周期：</span>
      <Select v-model="chartPrams.dateType" style="width: 60%" @on-change="changeDateType">
        <Option v-for="item in dateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="4" class="col">
        <span>开始时间：</span>
        <DatePicker v-model="chartPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间" style="width: 60%"></DatePicker>
      </Col>
      <Col span="4" class="col">
        <span>结束时间：</span>
        <DatePicker v-model="chartPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间" style="width: 60%"></DatePicker>
      </Col>
      <Col span="4" class=" col">
        <span>电表类型：</span>
        <i-Switch size="large" v-model="chartPrams.ammeterType">
          <span slot="open">普通</span>
          <span slot="close">危急</span>
        </i-Switch>
      </Col>
      <Col span="4" class="col">
        <Button type="primary" icon="ios-search"   @click="queryDetail">查询</Button>
      </Col>
    </Row>
    <Row>
      <Col span="8">
      <div class="chart">
        <SimpleBarWithClickChart ref="storeBar" v-bind="clickBarChartByStore"
          style="height:calc(85vh - 80px);"></SimpleBarWithClickChart>
      </div>
      </Col>
      <Col span="8">
      <div class="chart">
        <SimpleBarWithClickChart ref="sessionBar" v-bind="clickBarChartBySession"
          style="height:calc(85vh - 80px);"></SimpleBarWithClickChart>
      </div>
      </Col>
      <Col span="8">
      <div class="chart">
        <SimpleBarWithClickChart ref="typeBar" v-bind="clickBarChartByType"
         style="height:calc(85vh - 80px);"></SimpleBarWithClickChart>
      </div>
      </Col>
    </Row>
  </div>
</template>

<script>
  import SimpleBarWithClickChart from '../../../../components/Common/Chart/SimpleBarWithClickChart'

  export default {
    name: "energy-consumption-detail-level1",
    data() {
      return {
        isReady: true,
        tunnelId: "",
        dateType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {
          key: 4,
          value: "自定义"
        }],
        chartPrams: {
          dateType: 1,
          startTime: "",
          endTime: "",
          ammeterType: true,
        },
        clickBarChartByStore: {
          id: "storeBarChart",
          requestUrl: "stores/consume-datas",
          clickPrams: {
            areaType: 1,//1为仓，2为区段，3为电表类型
            tunnelId: "",
            storeId: "",
            curIndexId: "",
            energyType: "",
            startTime: "",
            endTime: "",
            arerId: "",
            url: "/UM/TunnelEnergy/EnergyConsumptionDetailLevel2/",
          }
        },
        clickBarChartBySession: {
          id: "sessionBarChart",
          requestUrl: "areas/consume-datas",
          clickPrams: {
            areaType: 2,
            tunnelId: "",
            storeId: "",
            curIndexId: "",
            energyType: "",
            startTime: "",
            endTime: "",
            arerId: "",
            url: "/UM/TunnelEnergy/EnergyConsumptionDetailLevel2/",
          }
        },
        clickBarChartByType: {
          id: "typeBarChart",
          requestUrl: "objectType/consume-datas",
          clickPrams: {
            areaType: 3,
            tunnelId: "",
            storeId: "",
            curIndexId: "",
            energyType: "",
            startTime: "",
            endTime: "",
            arerId: "",
            url: "/UM/TunnelEnergy/EnergyConsumptionDetailLevel2/",
          }
        },
      }
    },
    watch: {
      '$route': function () {
        this.tunnelId = parseInt(this.$route.query.id);
        // 设置默认值
        this.chartPrams.dateType=1;
        this.chartPrams.ammeterType=true;
        this.clickBarChartByStore.clickPrams.areaType=1;

      }
    },
    mounted() {
      this.changeDateType(this.chartPrams.dateType);
      this.queryDetail();
    },
    methods: {
      //更改时间类型
      changeDateType(index) {
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
      queryDetail() {
        var _this = this;
        if(new Date(_this.chartPrams.startTime)>new Date(_this.chartPrams.endTime)){
          _this.$Message.error('开始时间必须小于结束时间！');
          return;
        }
        _this.clickBarChartByStore.clickPrams.tunnelId = parseInt(_this.$route.params.id);
        _this.clickBarChartByStore.clickPrams.energyType = _this.chartPrams.ammeterType ? 1 : 2;
        _this.clickBarChartByStore.clickPrams.startTime = new Date(_this.chartPrams.startTime).getTime();
        _this.clickBarChartByStore.clickPrams.endTime = new Date(_this.chartPrams.endTime).getTime();
        _this.$refs.storeBar.fetchData();
      },
    },
    components: {
      SimpleBarWithClickChart
    },
  }
</script>

<style scoped>
  .chart {
    padding: 10px;
  }

  .top {
    background-color: #fff;
    line-height: 5vh;
    padding-left: 5px;
    font-size: 1.6vmin;
  }

  .planDec {
    padding: 4px;
    font-size: 14px;
    float: left;
  }
  .top >>> .ivu-input{
    height: 3.2vmin;
    font-size: 1.2vmin;
  }
  .top >>> .ivu-switch-large{
    width: 8vmin;
  }
  .top >>> .ivu-switch{
    height: 2.4vmin;
    line-height: 2.2vmin;
  }
  .top >>> .ivu-switch-inner{
    font-size: 1.2vmin;
    left: 2.5vmin;
  }
  .top >>> .ivu-switch:after{
    width: 2vmin;
    height: 2vmin;
  }
  .top >>>.ivu-switch-large.ivu-switch-checked:after{
    left: 5.6vmin;
  }
  .top >>> .ivu-switch-large:active:after{
    width: 3.2vmin;
  }
</style>
