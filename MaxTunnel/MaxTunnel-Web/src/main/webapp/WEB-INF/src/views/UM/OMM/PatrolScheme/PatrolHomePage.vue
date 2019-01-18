<template>
  <div class="flex-container">
    <h1 style="font-size: 2.6vmin">巡检计划信息总览</h1>
    <Row class="item-flex">
      <Col span="16">
      <Row>
        <Col span="24">
        <div class="GISbox">
          <TestSmViewer
            ref="TestSmViewer"
            :openImageryProvider="true"
          ></TestSmViewer>
          <div class="tunnelProfile">
            <div>巡检计划执行概况</div>
            <div>本月总计划：{{this.monthPlan.value}}</div>
            <div>发现缺陷数：{{this.defectCount}}</div>
          </div>
        </div>
        </Col>
        <Col span="11" style="margin-top: 1vh">
        <Card>
          <p slot="title">故障信息列表</p>
          <p v-for="(item,index) in faultInfoList" value="item" :key="index">
            <span class="goDetail" @click="goDefectDetails(item.id)">{{item.name}}</span>
            <span
              style="float: right;font-size: 12px;color: #ff9b00"
            >{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
          </p>
        </Card>
        </Col>
        <Col span="11" offset="2" style="margin-top: 1vh;">
        <Card>
          <p slot="title">计划信息列表</p>
          <p v-for="(item,index) in tunnelInfoList" value="item" :key="index">
            <span class="goDetail" @click="goPatrolPlan(item.planId)">{{item.name}}</span>
            <span
              style="float: right;font-size: 12px;color: #ff9b00"
            >{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
          </p>
        </Card>
        </Col>
      </Row>
      </Col>
      <Col span="8" class="rightBox">
      <Row>
        <Col span="12">
        <div
          @click="goToMoudle('/UM/planPatrol/queryAnnualPlan')"
          class="data-box"
          style="margin-right: 0"
        >
          <DataShow v-bind="yearPlan"></DataShow>
        </div>
        </Col>
        <Col span="12">
          <!-- <div @click="goToMoudle('/UM/planPatrol/queryMonthPlan')" class="data-box"> -->
          <div @click="goToMoudle('/UM/planPatrol/queryMonthPlan')" class="data-box">
            <DataShow v-bind="monthPlan"></DataShow>
          </div>
        </Col>
        <Col span="12">
          <div class="data-box" style="margin-right: 0">
            <DataShow v-bind="finishedTask"></DataShow>
          </div>
        </Col>
        <Col span="12">
          <div class="data-box">
            <DataShow v-bind="unfinishedTask"></DataShow>
          </div>
        </Col>
      </Row>
      </Col>
    </Row>
    <Row>
      <Col span="7">
      <div style="width: 24vw; height:22vh;" class="equipmentChartBox">
        <simple-bar v-bind="equipmentChart"></simple-bar>
      </div>
      </Col>
      <Col span="7">
      <div style="width:24vw; height:22vh;" class="equipmentChartDoubleColor">
        <simple-pie v-bind="equipmentChartDoubleColor"></simple-pie>
      </div>
      </Col>
      <Col span="5">
        <div class="circleBox" style="background: #2C6A69;" >
          <i-circle
            :size="radius"
            :trail-width="4"
            :stroke-width="4"
            :percent="finishedYearPlanRatio"
            stroke-linecap="square"
            stroke-color="#fff"
            style="margin-top: 5px;"
          >
            <p class="demo-Circle-inner" style="font-size:2.2vmin;color: #fff">年度完成任务占比</p>
            <p
              class="demo-Circle-inner"
              style="font-size:2.2vmin;color: #fff"
            >{{this.finishedYearPlanRatio}}%</p>
          </i-circle>
        </div>
      </Col>
      <Col span="5">
        <div class="circleBox" style="background: #D5C7E1">
          <i-circle
            :size="radius"
            :trail-width="4"
            :stroke-width="4"
            :percent="currMonthRatio"
            stroke-linecap="square"
            stroke-color="#E5B500"
            style="margin-top: 5px;"
          >
            <p class="demo-Circle-inner" style="font-size:2.2vmin">本月计划占比</p>
            <p class="demo-Circle-inner" style="font-size: 2.2vmin">{{this.currMonthRatio}}%</p>
          </i-circle>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
  import DataBox from "../../../../components/Common/Box/DataBox";
  import DataShow from "../../../../components/Common/DataShow";
  import SimpleBar from "../../../../components/Common/Chart/SimpleBarChart";
  import SimplePie from "../../../../components/Common/Chart/SimplePieChart";
  import Ring from "../../../../components/Common/Chart/RingChart";
  import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
  import ProcessRing from "../../../../components/Common/ProcessRing";
  import annualPlan from "../../../../assets/UM/annualPlan.png";
  import curMonthPlan from "../../../../assets/UM/curMonthPlan.png";
  import processedPlan from "../../../../assets/UM/processedPlan.png";
  import pendingPlan from "../../../../assets/UM/pendingPlan.png";
  import { PatrolService } from "../../../../services/patrolService";
  import { DefectService } from "../../../../services/defectService";
  import Enum from "../../../../../static/Enum.json";

  export default {
    name: "equipmentMain",

    data() {
      return {
        SMViewer: {
          id: "ViewId"
        },
        yearPlan: {
          label: "年度计划数",
          value: 365,
          imgSrc: annualPlan,
          imgBac: "#8C91A7"
        },
        monthPlan: {
          label: "本月计划",
          value: 87,
          imgSrc: curMonthPlan,
          imgBac: "#7ca9a9"
        },
        finishedTask: {
          label: "已完成巡检任务数",
          value: "5",
          imgSrc: processedPlan,
          imgBac: "#112f6b"
        },
        unfinishedTask: {
          label: "待巡检任务",
          value: "82",
          imgSrc: pendingPlan,
          imgBac: "#BD4932"
        },
        equipmentChart: {
          id: "equipmentChartId",
          requestUrl: "/inspection-plans/tunnelCount",
          parameters: {
            option: {
              title: {
                text: "各管廊巡检计划数"
              }
            }
          }
        },
        equipmentChartDoubleColor: {
          id: "equipmentCharDoubleColortId",
          requestUrl: "/tunnel/defect-count",
          parameters: {
            option: {
              title: {
                text: "各管廊缺陷占比"
              }
            }
          }
        },
        //本月计划占比
        currMonthRatio: 0,
        //年度计划占比
        finishedYearPlanRatio: 0,
        faultInfoList: [],
        tunnelInfoList: [],
        radius: 100,
        defectCount: 0,
        columns2: [
          {
            type: "index",
            width: 60,
            align: "center"
          },
          {
            title: "所属管廊",
            key: "tunnelName"
          },
          {
            title: "缺陷类型",
            key: "typeName"
          },
          {
            title: "对象名",
            key: "objName"
          },
          {
            title: "状态",
            key: "statusName"
          }
        ],
        listHeight: 0
      };
    },

    beforeRouteLeave(to, from, next) {
      if (
        to.name == "设备管理主页" ||
        to.name == "虚拟巡检" ||
        to.name == "人员定位详情" ||
        to.name == "管廊安防监控列表" ||
        to.name == "管廊环境监控列表" ||
        from.name == "虚拟巡检" ||
        from.name == "人员定位详情" ||
        from.name == "设备管理主页" ||
        from.name == "管廊安防监控列表" ||
        from.name == "管廊环境监控列表"
      ) {
        from.meta.keepAlive = true;
        to.meta.keepAlive = true;
        this.$destroy();
        next();
      } else {
        from.meta.keepAlive = false;
        to.meta.keepAlive = false;
        this.$destroy();
        next();
      }
    },

    components: {
      DataBox,
      SimpleBar,
      SimplePie,
      Ring,
      DataShow,
      TestSmViewer,
      ProcessRing
      // FeatureSample
    },

    mounted() {
      let _this = this;
      _this.radius=window.innerHeight*0.2;
      PatrolService.getPatrolCounts().then(
        result => {
          _this.yearPlan.value = result.yearPlanCount;
          _this.monthPlan.value = result.monthPlanCount;
          _this.finishedTask.value = result.finfinfinishTaskCount;
          _this.unfinishedTask.value = result.unfinishTaskCount;
          _this.tunnelInfoList = result.listPlan;
          _this.currMonthRatio = result.yearPlanCount != 0 ? parseFloat(result.monthPlanCount / result.yearPlanCount * 100).toFixed(2) : 0;
          _this.finishedYearPlanRatio = result.yearTaskCount != 0 ? parseFloat(result.finishTaskCount / result.yearTaskCount * 100).toFixed(2) : 0;
        },
        error => {
          _this.Log.info(error);
        }
      );
      DefectService.getDefectsCount().then(
        result => {
          result.forEach(a => {
            _this.defectCount = _this.defectCount + a.val;
          });
        },
        error => {
          _this.Log.info(error);
        }
      );
      DefectService.getDefectList().then(
        result => {
          _this.faultInfoList = result;
        },
        error => {
          _this.Log.info(error);
        }
      );

      _this.$refs.TestSmViewer.showCheckPointEntity();
    },

    methods: {
      goToMoudle: function(path) {
        this.$router.push(path);
      },
      goPatrolPlan(id) {
        this.$router.push({
          name: "UMPatrolDetails",
          params: {
            id: id,
            type: Enum.pageType.Read
          }
        });
      },
      goDefectDetails(id) {
        this.$router.push({
          name: "UMDetailDefect",
          params: {
            id: id,
            type: Enum.pageType.Read
          }
        });
      },
    },

  };
</script>
<style scoped>
  .ivu-col-span-12 {
    padding: 9px;
  }
  h1,
  .ivu-card >>> .ivu-card-head p,
  .GISTitle {
    color: #2e739b;
  }
  .ivu-card >>> .ivu-card-head p{
    font-size: 1.2vmin;
  }
  .GISbox {
    position: relative;
    width: 100%;
    height: 100%;
    min-height: 44vh;
    border: 1px solid #dddee1;
    display: inline-block;
    vertical-align: top;
    border-radius: 4px;
    margin-top: 10px;
  }
  .GISTitle {
    background-color: #f7f8f9;
    font-size: 18px;
    line-height: 35px;
    padding-left: 5px;
    z-index: 2;
    position: absolute;
    width: 100%;
  }
  .tunnelProfile {
    border-bottom: 1px solid #dddee1;
    position: absolute;
    bottom: 10px;
    right: 10px;
    background-color: rgb(255, 255, 255, 0.5);
    z-index: 999;
  }
  .tunnelProfile div {
    border: 1px solid #dddee1;
    border-bottom: none;
    line-height: 35px;
    text-align: center;
    padding-left: 10px;
    padding-right: 10px;
  }
  .circleBox {
    text-align: center;
    height: 22vh;
    width: 98%;
    display: inline-block;
    vertical-align: middle;
    border: 1px solid #dddee1;
    border-radius: 4px;
    background: #eeeeee;
  }
  .equipmentChartBox,
  .equipmentChartDoubleColor {
    border: 1px solid #dddee1;
    border-radius: 4px;
    height: 22vh;
    width: 99%;
  }
  .ivu-card >>> .ivu-card-body {
    height: 7vh;
    overflow-y: auto;
  }
  .cesium-viewer-bottom {
    display: none;
  }
  .goDetail {
    cursor: pointer;
  }
  @media (min-width: 2200px) {
    .ivu-card >>> .ivu-card-head p{
      height: 2vmin;
      line-height: 2vmin;
      font-size: 1.5vmin
    }
    .goDetail{
      font-size: 1.4vmin;
    }
  }
</style>


