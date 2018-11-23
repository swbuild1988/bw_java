<template>
    <div style="padding-left: 10px;">
      <h1>巡检计划信息总览</h1>
      <Row>
        <Col span="16">
          <Row>
            <Col span="24">
              <div class="GISbox" id="GISbox" ref="gisBox">
                <!--<div class="GISTitle">地图显示所有的管廊位置GIS</div>-->
                <!-- <sm-viewer v-bind="SMViewer" ref="smviewer" v-show="true">
                </sm-viewer> -->
                <TestSmViewer ref="TestSmViewer"></TestSmViewer>
                <div class="tunnelProfile">
                  <div>巡检计划执行概况</div>
                  <div>本月总计划：{{this.monthPlan.value}}</div>
                  <div>发现缺陷数：{{this.defectCount}}</div>
                </div>
              </div>
            </Col>
            <Col span="11" style="margin-top: 5px">
              <!-- <p>故障信息列表</p> -->
              <!-- <Table height="100" :data="faultInfoList" border :columns="columns2"></Table> -->
              <Card>
                <p slot="title">故障信息列表</p>
                <p v-for="(item,index) in faultInfoList" value="item" :key="index">
                  <span>{{item.name}}</span>
                  <span style="float: right;font-size: 12px;color: #ff9b00">{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
                </p>
              </Card>
            </Col>
            <Col span="11" offset="2" style="margin-top: 5px;">
              <Card>
                <p slot="title">计划信息列表</p>
                <p v-for="(item,index) in tunnelInfoList" value="item" :key="index">
                  <span>{{item.name}}</span>
                  <span style="float: right;font-size: 12px;color: #ff9b00">{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
                </p>
              </Card>
            </Col>
          </Row>
        </Col>
        <Col span="8" class="rightBox">
        <Row>
          <Col span="12"><div class="data-box" style="margin-right: 0"><DataShow v-bind="yearPlan"></DataShow></div></Col>
          <Col span="12"><div class="data-box"><DataShow v-bind="monthPlan"></DataShow></div></Col>
          <Col span="12"><div class="data-box" style="margin-right: 0"><DataShow v-bind="finishedTask"></DataShow></div></Col>
          <Col span="12"><div class="data-box"><DataShow v-bind="unfinishedTask"></DataShow></div></Col>
        </Row>
        </Col>
      </Row>
      <Row style="padding-top: 5px;">
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
            <div class="circleBox" style="background: #2C6A69;" ref="circleBox1">
                <i-circle :size=this.leftRadius
                          :trail-width="4"
                          :stroke-width="8"
                          :percent=this.finishedYearPlanRatio
                          stroke-linecap="square"
                          stroke-color="#fff" style="margin-top: 5px;">
                    <p class="demo-Circle-inner" style="font-size:22px;color: #fff">年度完成任务占比</p>
                    <p class="demo-Circle-inner" style="font-size: 22px;color: #fff">{{this.finishedYearPlanRatio}}%</p>
                </i-circle>
            </div>
        </Col>
        <Col span="5">
            <div class="circleBox" style="background: #D5C7E1" ref="circleBox2">
                <i-circle :size=this.rightRadius
                          :trail-width="4"
                          :stroke-width="8"
                          :percent=this.currMonthRatio
                          stroke-linecap="square"
                          stroke-color="#E5B500" style="margin-top: 5px;">
                    <p class="demo-Circle-inner" style="font-size:22px">本月计划占比</p>
                    <p class="demo-Circle-inner" style="font-size: 22px">{{this.currMonthRatio}}%</p>
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

// import SmViewer from "../../../../components/Common/3D/3DViewer";
import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";

import ProcessRing from "../../../../components/Common/ProcessRing";
import { URL_CONFIG } from "../../../../../static/3DMap/js/3DMapConfig";
import annualPlan from "../../../../assets/UM/annualPlan.png";
import curMonthPlan from "../../../../assets/UM/curMonthPlan.png";
import processedPlan from "../../../../assets/UM/processedPlan.png";
import pendingPlan from "../../../../assets/UM/pendingPlan.png";
import { PatrolService } from "../../../../services/patrolService";
import { DefectService } from "../../../../services/defectService";
// import FeatureSample from '../../../../components/Common/Chart/FeatureSample'
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
            currMonthRatio: 10,
            //年度计划占比
            finishedYearPlanRatio: 10,
            faultInfoList: [],
            tunnelInfoList: [],
            leftRadius: 100,
            rightRadius: 100,
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
    beforeRouteLeave(to,from,next){
        if(to.name == '设备管理主页' || to.name == '虚拟巡检' || to.name == '人员定位详情' || to.name == '管廊安防监控列表' || to.name == '管廊环境监测列表'
            || from.name == '虚拟巡检' || from.name == '人员定位详情' || from.name == '设备管理主页' || from.name == '管廊安防监控详情' || from.name == '管廊安防监控列表'
            || from.name == '管廊环境监控列表' || from.name == '管廊环境监控详情'
        ){
            from.meta.keepAlive = true;
            to.meta.keepAlive = true
            this.$destroy()
            next()
        }else{
            from.meta.keepAlive = false
            to.meta.keepAlive = false
            this.$destroy()
            next()
        }
    },
    components: {
        DataBox,
        SimpleBar,
        SimplePie,
        Ring,
        DataShow,
        // SmViewer,
        TestSmViewer,
        ProcessRing
        // FeatureSample
    },
    mounted() {
        this.setGIS();
        this.getRadius1();
        this.getRadius2();
        let _this = this;
        PatrolService.getPatrolCounts().then(
            result => {
                _this.yearPlan.value = result.yearPlanCount;
                _this.monthPlan.value = result.monthPlanCount;
                _this.finishedTask.value = result.finfinfinishTaskCount;
                _this.unfinishedTask.value = result.unfinishTaskCount;
                _this.tunnelInfoList = result.listPlan;
                _this.currMonthRatio = Number(
                    (
                        (result.monthPlanCount / result.yearPlanCount) *
                        100
                    ).toFixed(2)
                );
                _this.finishedYearPlanRatio = Number(
                    (
                        (result.finishTaskCount / result.yearTaskCount) *
                        100
                    ).toFixed(2)
                );
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

        this.$refs.TestSmViewer.showCheckPointEntity();
    },
    methods: {
        goToMoudle: function(path) {
            this.$router.push(path);
        },
        getRadius1() {
            if (
                this.$refs.circleBox1.offsetWidth >=
                this.$refs.circleBox1.offsetHeight
            ) {
                this.leftRadius = this.$refs.circleBox1.offsetHeight - 20;
            } else {
                this.leftRadius = this.$refs.circleBox1.offsetWidth - 20;
            }
        },
        getRadius2() {
            if (
                this.$refs.circleBox2.offsetWidth >=
                this.$refs.circleBox2.offsetHeight
            ) {
                this.rightRadius = this.$refs.circleBox2.offsetHeight - 20;
            } else {
                this.rightRadius = this.$refs.circleBox2.offsetWidth - 20;
            }
        },
        setGIS() {
            var gis = document.getElementById("newID");
            gis.style.display = "block";
            gis.style.position = 'absolute';
            gis.style.top = '0px';
            gis.style.height = '100%';
            gis.style.width = '100%'    
            document.body.removeChild(gis)
            document.getElementById("GISbox").appendChild(gis)
            // 加载视角
            this.$refs.TestSmViewer.setViewAngAngle({
                longitude: 112.49446991184571,
                latitude: 37.70536834041335,
                height: 121.73914318253694,
                roll: 6.28318530714758,
                pitch: -0.7220718086739968,
                heading: 5.868990772801154
            });
        },
        destory3D(){
            var gis = document.getElementById("newID");
            gis.style.display = "none";
            document.getElementById("GISbox").removeChild(gis)
            document.body.appendChild(gis)
        }
    },
    beforeDestroy() {
        this.destory3D()
    }
};
</script>
<style scoped>
.ivu-col-span-12 {
    padding: 10px;
}
h1,
.ivu-card >>> .ivu-card-head p,
.GISTitle {
    color: #2e739b;
}
.GISbox {
    width: 100%;
    height: 43vh;
    border: 1px solid #dddee1;
    display: inline-block;
    vertical-align: top;
    margin-top: 10px;
    border-radius: 4px;
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
    height: 9vh;
    overflow-y: auto;
}
.cesium-viewer-bottom {
    display: none;
}
</style>


