<template>
    <div class="flex-container">
        <h1 style="font-size: 1.6vmin; color: #fff">巡检计划信息总览</h1>
        <Row class="item-flex">
            <Col span="16">
            <Row>
                <Col span="24" class="GISBG">
                <div class="GISbox">
                    <TestSmViewer ref="TestSmViewer" :openImageryProvider="true"></TestSmViewer>
                </div>
                </Col>
                <Col span="11" style="margin-top: 1vh">
                    <div class="card">
                        <p class="title">故障信息列表</p>
                        <div class="details">
                            <p v-for="(item,index) in faultInfoList" value="item" :key="index">
                                <span class="goDetail" @click="goDefectDetails(item.id)">{{item.name}}</span>
                                <span class="defaultListTime">{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
                            </p>
                        </div>
                    </div>
                </Col>
                <Col span="11" offset="2" style="margin-top: 1vh;">
                    <div class="card">
                        <p class="title">计划信息列表</p>
                        <div class="details">
                            <p v-for="(item,index) in tunnelInfoList" value="item" :key="index">
                                <span class="goDetail" @click="goPatrolPlan(item.planId)">{{item.name}}</span>
                                <span class="defaultListTime">{{new Date(item.createTime).format('yyyy-MM-dd')}}</span>
                            </p>
                        </div>
                    </div>
                </Col>
            </Row>
            </Col>
            <Col span="8" class="rightBox">
            <Row>
                <Col span="12">
                    <div class="data-box" style="margin-right: 0">
                        <DataShow v-bind="yearPlan"></DataShow>
                    </div>
                </Col>
                <Col span="12">
                    <div class="data-box">
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
        <Row style="margin-top: 1vh;">
            <Col span="7">
                <div style="width: 24vw; height:22vh;" class="equipmentChartBox boxBG">
                    <simple-bar v-bind="equipmentChart"></simple-bar>
                </div>
            </Col>
            <Col span="7">
                <div style="width:24vw; height:22vh;" class="equipmentChartDoubleColor boxBG">
                    <simple-pie v-bind="equipmentChartDoubleColor"></simple-pie>
                </div>
            </Col>
            <Col span="5">
                <div class="circleBox boxBG">
                    <i-circle :size="radius" :trail-width="4" :stroke-width="4" :percent="finishedYearPlanRatio"
                        stroke-linecap="square" stroke-color="#fc9694">
                        <p class="demo-Circle-inner">年度完成任务占比</p>
                        <p class="demo-Circle-inner">{{this.finishedYearPlanRatio}}%</p>
                    </i-circle>
                </div>
            </Col>
            <Col span="5">
                <div class="circleBox boxBG">
                    <i-circle :size="radius" :trail-width="4" :stroke-width="4" :percent="currMonthRatio" 
                        stroke-linecap="square" stroke-color="#b195ed">
                        <p class="demo-Circle-inner">本月任务占比</p>
                        <p class="demo-Circle-inner">{{this.currMonthRatio}}%</p>
                    </i-circle>
                </div>
            </Col>
        </Row>
    </div>
</template>

<script>
    import DataBox from "@/components/Common/Box/DataBox";
    import DataShow from "@/components/Common/DataShow";
    import SimpleBar from "@/components/Common/Chart/SimpleBarChart";
    import SimplePie from "@/components/Common/Chart/SimplePieChart";
    import Ring from "@/components/Common/Chart/RingChart";
    import TestSmViewer from "@/components/Common/3D/overLook3DViewer";
    import ProcessRing from "@/components/Common/ProcessRing";
    import annualPlan from "@/assets/UM/annualPlan.png";
    import curMonthPlan from "@/assets/UM/curMonthPlan.png";
    import processedPlan from "@/assets/UM/processedPlan.png";
    import pendingPlan from "@/assets/UM/pendingPlan.png";
    import { PatrolService } from "../../../../services/patrolService";
    import { DefectService } from "../../../../services/defectService";
    import Enum from "../../../../../static/Enum.json";
    import Vue from 'vue'
    export default {
        name: "equipmentMain",

        data() {
            return {
                SMViewer: {
                    id: "ViewId"
                },
                yearPlan: {
                    label: "年度任务",
                    value: 365,
                    imgSrc: annualPlan,
                    imgBac: "#8C91A7",
                    showDataColor: '#249cf9'
                },
                monthPlan: {
                    label: "本月任务",
                    value: 87,
                    imgSrc: curMonthPlan,
                    imgBac: "#7ca9a9",
                    showDataColor: '#fdb628'
                },
                finishedTask: {
                    label: "已完成巡检任务",
                    value: "5",
                    imgSrc: processedPlan,
                    imgBac: "#112f6b",
                    showDataColor: '#12f9ff'
                },
                unfinishedTask: {
                    label: "待巡检任务",
                    value: "82",
                    imgSrc: pendingPlan,
                    imgBac: "#BD4932",
                    showDataColor: '#eb6f49'
                },

                equipmentChart: {
                    id: "equipmentChartId",
                    requestUrl: "/inspection-plans/tunnelCount",
                    parameters: {
                        option: {
                            title: {
                                text: "各管廊巡检计划数",
                                textStyle: {
                                    color: "#fff"
                                }
                            }
                        }
                    },
                    titleColor: '#708acc',
                    lineColor: '#708acc',
                    seriesColorList: [
                        [ '#fed701', '#fc9501' ],
                        [ '#f3cb7c', '#fed601' ],
                        [ '#3ce94c', '#0ee622' ],
                        [ '#ed4a49', '#eb1818' ],
                        [ '#61eba5', '#00fc7c' ],
                        [ '#fe9b1a', '#fe411b' ]
                    ]
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
                    },
                    seriesColor: ['#c23531', '#e5c52f', '#6bade1', '#6fe46c', '#e06ce4', '#e48e6c'],
                    legendColor: '#ffffff'
                },
                //本月计划占比
                currMonthRatio: null,
                //年度计划占比
                finishedYearPlanRatio: null,
                faultInfoList: [],
                tunnelInfoList: [],
                radius: 100,
                defectCount: 0,
                listHeight: 0,
                polylineAttr:{
					viewer:Vue.prototype.$viewer,
					id:null,
					type:'patrolShemeRoute',
				}
            };
        },
        components: {
            DataBox,
            SimpleBar,
            SimplePie,
            Ring,
            DataShow,
            TestSmViewer,
            ProcessRing
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
        mounted() {
            let _this = this;
            _this.radius = window.innerHeight * 0.19;
            PatrolService.getPatrolCounts().then(
                result => {
                    //本年任务数
                    _this.yearPlan.value = result.yearTaskCount;
                    //本月任务数
                    _this.monthPlan.value = result.monthTaskCount;
                    // 本月已完成任务数
                    _this.finishedTask.value = result.finishTaskCount;
                    // 本月未完成任务数
                    _this.unfinishedTask.value = result.unfinishTaskCount;
                    _this.tunnelInfoList = result.listPlan;
                    //本月任务占比
                    _this.currMonthRatio = result.yearPlanCount != 0 ? parseFloat(result.monthTaskCount / result.yearTaskCount *
                        100).toFixed(2) : 0;

                    _this.finishedYearPlanRatio = result.finYearTaskCount != 0 ? parseFloat(result.finYearTaskCount /
                        result.yearTaskCount * 100).toFixed(2) : 0;
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
            _this.getRouteList()
        },

        methods: {
            goToMoudle: function (path) {
                this.$router.push(path);
            },
            goToPlan: function(name, timeType){
                this.$router.push({
                    name: name,
                    params: {
                        timeType: timeType
                    }
                })
            },
            goToTask: function(name, taskStatus){
                this.$router.push({
                    name: name,
                    params: {
                        taskStatus: taskStatus
                    }
                })
            },
            goPatrolPlan(id) {
                this.$router.push({
                    name: "查看巡检计划详情",
                    params: {
                        id: id,
                        type: Enum.pageType.Read
                    }
                });
            },
            goDefectDetails(id) {
                this.$router.push({
                    name: "缺陷详情",
                    params: {
                        id: id,
                        type: Enum.pageType.Read
                    }
                });
            },
            removePolyline(){
				let { polylineAttr } = this;
				
				if(!!polylineAttr.id){
					let entitys  = polylineAttr.viewer.entities.getById(polylineAttr.id);
				
					if( Array.isArray(entitys) && entitys.length ){
						entitys.forEach(entity => this.removeCommonEntity)
					}
                    else{
						this.removeCommonEntity(entitys)
					}
					
				}
			},
			removeCommonEntity(entitys){
				let { polylineAttr } = this;

				return entitys.messageType == polylineAttr.type && polylineAttr.viewer.entities.removeById(polylineAttr.id)
            },
            getRouteList(){
                let params = {}
                PatrolService.getInspectionPlanPath(params).then(
                    result => {
                        result.forEach(item=>{
                            let temp = {}
                            temp.id = item.id
                            temp.name = item. name
                            temp.positions = []
                            let arr = item.others
                            if(arr.length>0){
                                for(let i = 0; i<arr.length; i++ ){
                                    let startPointPosition = {}
                                    let startPointArr = arr[0].startPoint.split(',')
                                    startPointPosition.Lon = startPointArr[0]
                                    startPointPosition.Lat = startPointArr[1]
                                    temp.positions.push(startPointPosition)
                                    let endPointPosition = {}
                                    if(arr.length==1){
                                        let endPointArr = arr[0].endPoint.split(',')
                                        endPointPosition.Lon = endPointArr[0]
                                        endPointPosition.Lat = endPointArr[1]
                                        temp.positions.push(endPointPosition)
                                    }else{
                                        let endPointArr = arr[item.others.length-1].endPoint.split(',')
                                        endPointPosition.Lon = endPointArr[0]
                                        endPointPosition.Lat = endPointArr[1]
                                        temp.positions.push(endPointPosition)
                                    }
                                }
                                this.getCurRoute(temp)
                            }
                        })
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            },
            getCurRoute(routeInfo) {

				let params = {}
				params = {
                    polyline:{
                        position: routeInfo.positions,
                        width: 5,
                        color: 'blue'
                    }
                }

				this.removePolyline();

				this.$refs.TestSmViewer.addPolylineEntity({id:routeInfo.id,messageType:this.polylineAttr.type},params)
				
            }
        }
    };
</script>
<style scoped>
    .ivu-col-span-12 {
        padding: 9px;
    }

    .GISbox {
        position: relative;
        width: 100%;
        height: 44vh;
        display: inline-block;
        vertical-align: top;
        border-radius: 4px;
        margin-top: 10px;
    }

    .GISBG{
        background: url("../../../../assets/UM/boxBGBig.png") no-repeat;
        background-size: 100% 100%;
        padding: 0.2vmin 1.5vmin 1vmin 1.5vmin;
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
        align-items:center;/*垂直居中*/
        justify-content: center;/*水平居中*/
        padding: 2vmin 0px;
    }

    .boxBG{
        background: url("../../../../assets/UM/chartBG.png") no-repeat;
        background-size: 100% 100%;
    }

    .equipmentChartBox,
    .equipmentChartDoubleColor {
        border-radius: 4px;
        height: 22vh;
        width: 99%;
    }

    .cesium-viewer-bottom {
        display: none;
    }

    .goDetail {
        cursor: pointer;
    }

    .goDetail:hover {
        color: #9300ff;
        font-size: 1.6vmin;
    }

    .defaultListTime {
        float: right;
        font-size: 12px;
        color: #9300ff
    }

    .card{
        background: url("../../../../assets/UM/cardBG.png") no-repeat;
        background-size: 100% 100%;
    }

    .card .title{
        color: #0efcff;
        line-height: 5.5vh;
        padding-left: 1vw;
    }

    .card .details{
        border-top: 2px solid #1b6ca3;
        padding: 0 1vw;
        line-height: 3.5vh;
        overflow-y: auto;
        height: 6vh;
        color: #fff;
    }

    .demo-Circle-inner{
        font-size: 2.2vmin;
        color: #fff;
    }

    @media (min-width: 2200px) {

        .goDetail {
            font-size: 1.6vmin;
        }

        .defaultListTime {
            font-size: 1.4vmin;
        }

        .card .title{
            font-size: 1.8vmin;
        }
    }
</style>