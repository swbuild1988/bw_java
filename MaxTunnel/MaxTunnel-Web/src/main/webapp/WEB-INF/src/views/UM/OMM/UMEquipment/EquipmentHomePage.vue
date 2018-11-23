<template>
  <div style="padding-left: 10px;">
    <h1>设备信息总览</h1>
    <Row>
      <Col span="18">
      <Col span="6">
      <data-box v-bind="equimentTotalNum" ref="dataBox"></data-box>
      </Col>
      <Col span="6">
      <data-box v-bind="equimentRunNum"></data-box>
      </Col>
      <Col span="6">
      <data-box v-bind="spareAFew"></data-box>
      </Col>
      <Col span="6">
      <data-box v-bind="duration"></data-box>
      </Col>
      <Col span="24">
      <div class="GISbox" id="GISbox" ref="gisBox">
        <!-- <sm-viewer
          :id="id"
          ref="smViewer"
        >
        </sm-viewer> -->
        <TestSmViewer ref="TestSmViewer"></TestSmViewer>
        <div class="tunnelProfile">
          <div>管廊设备概况</div>
          <div>统计:185</div>
          <div>风岭北路:40</div>
          <div>长虹路:30</div>
          <div>高坡岭路:50</div>
          <div>凤凰岭路:45</div>
          <div>佛子岭路:20</div>
        </div>
      </div>
      </Col>
      </Col>
      <Col span="6" class="chartBox">
      <Col span="24" style="height: 49%;">
      <!-- <Button class="equimentChartBtn" @click.native="goToMoudle({ path:'/UM/equipment/query'})">更多</Button> -->
      <div style="width: 21.4vw; height:100%;" class="equipmentChartBox">
        <simple-bar v-bind="equipmentChart"></simple-bar>
      </div>
      </Col>
      <Col span="24" style="height: 49%;">
      <!-- <Button class="equimentChartBtn" @click.native="goToMoudle({ path:'/UM/equipment/query'})">更多</Button> -->
      <div style="width: 21.4vw; height:100%;" class="equipmentChartDoubleColor">
        <simple-pie v-bind="equipmentChartDoubleColor"></simple-pie>
      </div>
      </Col>
      </Col>
    </Row>
    <Row style="margin-top:2vh;">
      <Col span="5">
      <div class="circleBox" style="background: #b189a357">
        <process-ring v-bind="spare">
          <p>{{spare.explain}}</p>
          <p>{{spare.percent}}%</p>
        </process-ring>
      </div>
      </Col>
      <Col span="5">
      <div class="circleBox" style="background: #6e6ec761">
        <process-ring v-bind="breakDown">
          <p>{{breakDown.explain}}</p>
          <p>{{breakDown.percent}}%</p>
        </process-ring>
      </div>
      </Col>
      <Col span="7" style="margin-top: 5px">
      <Card style="width: 98%">
        <p slot="title">管廊设备明细</p>
        <div style="height: 80px;">
          <Row :gutter="16">
            <Col span="10" offset="2" v-for="(item,index) in tunnelInfoList" :key="index">
            {{item.key}}:{{item.val}}
            </Col>
          </Row>
        </div>
      </Card>
      </Col>
      <Col span="7" style="margin-top: 5px">
      <Card style="width: 98%">
        <p slot="title">最近一条故障信息</p>
        <Table stripe border :columns="breakColumns"  height="80" :data="breakData"></Table>
      </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
import DataBox from "../../../../components/Common/Box/DataBox";
import SimpleBar from "../../../../components/Common/Chart/SimpleBarChart";
import SimplePie from "../../../../components/Common/Chart/SimplePieChart";
// import SmViewer from "../../../../components/Common/3D/3DViewer";
import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import ProcessRing from "../../../../components/Common/ProcessRing";
import { URL_CONFIG } from "../../../../../static/3DMap/js/3DMapConfig";
import {
    setViewAngle,
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed
} from "../../../../scripts/commonFun.js";
import equimentTotalNum from "../../../../assets/UM/equimentTotalNum.png";
import spareAFew from "../../../../assets/UM/spareAFew.png";
import duration from "../../../../assets/UM/duration.png";
import equimentRunNum from "../../../../assets/UM/equimentRunNum.png";
import { EquipmentService } from "../../../../services/equipmentService";

export default {
    name: "equipmentMain",
    data() {
        return {
            id: "GIS_ID",
            breakColumns: [
                {
                    title: "设备名称",
                    key: "name"
                },
                {
                    title: "设备类型",
                    key: "type"
                },
                {
                    title: "故障时间",
                    key: "time"
                },
                {
                    title: "报告人",
                    key: "user"
                }
            ],
            breakData: [
                {
                    name: "风机",
                    type: "管廊设备",
                    time: "2018-1-12",
                    user: "张三"
                }
            ],
            camera: {
                longitude: 116.43709465365579,
                latitude: 39.91793569836651,
                height: 1245.5482069457155,
                roll: 6.281841926729911,
                pitch: -0.9120242487858476,
                heading: 1.5735504792701676
            },
            equimentTotalNum: {
                label: "故障设备",
                value: 10,
                imgSrc: equimentTotalNum,
                imgBac: "#CCCCFF",
                valBackground: "#e4bdeb",
                labelBackground: "#CC99CC"
            },
            equimentRunNum: {
                label: "运行中的设备",
                value: 87,
                imgSrc: equimentRunNum,
                imgBac: "#999999",
                valBackground: "#cccccc",
                labelBackground: "#336666"
            },
            spareAFew: {
                label: "设备总计",
                value: "500",
                imgSrc: spareAFew,
                imgBac: "#466b91",
                valBackground: "#98ccc2",
                labelBackground: "#b6a8db"
            },
            duration: {
                label: "设备运行总时长",
                value: "158h",
                imgSrc: duration,
                imgBac: "#aaccd2",
                valBackground: "#eaba0a",
                labelBackground: "#554d6b"
            },
            equipmentChart: {
                id: "equipmentChartId",
                requestUrl: "/equipments/type/count",
                parameters: {
                    option: {
                        title: {
                            text: "设备类别"
                        }
                    }
                }
            },
            equipmentChartDoubleColor: {
                id: "equipmentCharDoubleColortId",
                requestUrl: "/equipments/type/count",
                parameters: {
                    option: {
                        title: {
                            text: "管廊故障设备统计",
                            x: "left",
                            textStyle: {
                                color: "#2e739b"
                            }
                        }
                    }
                }
            },
            equimentPie: {
                id: "equipmentPieId",
                requestUrl: "/equipments/type/count",
                parameters: {
                    option: {
                        title: {
                            text: "各类型故障占比"
                        }
                    }
                }
            },
            spare: {
                id: "spare",
                explain: "备品占比",
                percent: 85,
                strokeWidth: 10,
                radius: 60
            },
            breakDown: {
                id: "breakDown",
                explain: "故障占比",
                percent: 45,
                strokeWidth: 10,
                radius: 60
            },
            tunnelInfoList: [],
            timer: {
                timeoutId: null,
                intervalId: null,
                sectionId: null //保留上次section
            },
            // isDestory: false,
            // path: this.$router.currentRoute.path
        };
    },
    components: {
        DataBox,
        SimpleBar,
        SimplePie,
        ProcessRing,
        // SmViewer
        TestSmViewer
    },
    beforeRouteLeave(to,from,next){
        if(to.name == 'UMPatrolHomePage' || to.name == 'UMDetailEquipment' || to.name == '虚拟巡检' || to.name == '人员定位详情' 
            || to.name == '管廊安防监控详情' || to.name == '管廊安防监控列表' || to.name == '管廊环境监控列表'
            || from.name == '人员定位详情' || from.name == '虚拟巡检' || from.name == 'UMDetailEquipment' || from.name == 'UMPatrolHomePage' 
            || from.name == '管廊安防监控详情' || from.name == '管廊安防监控列表' || from.name == '管廊环境监控列表' || from.name == '管廊环境监控详情'){
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
    mounted() {
        this.getEquipmentType();
        this.setGIS();
    },
    methods: {
        //获取管廊设备类别信息
        getEquipmentType() {
            let _this = this;
            EquipmentService.getEquTypeAndCount().then(
                result => {
                    _this.tunnelInfoList = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },

        goToMoudle: function(path) {
            this.$router.push(path);
        },
        onload(parent) {
            var _this = this;
        },
        setGIS() {
            var gis = document.getElementById("newID");
            gis.style.display = "block";
            gis.style.position = 'absolute';
            gis.style.top = '20px';
            gis.style.height = '100%';
            gis.style.width = '99%'    
            document.body.removeChild(gis)
            document.getElementById("GISbox").appendChild(gis)
            // 加载视角
            this.$refs.TestSmViewer.setViewAngAngle();
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
h1,
.ivu-card > .ivu-card-head p {
    color: #2e739b;
}

.tunnelProfile {
    border: 1px solid #dddee1;
    border-radius: 4px;
    position: absolute;
    bottom: 2px;
    right: 13px;
    text-align: left;
    z-index: 2;
    color: #fff;
}

.tunnelProfile div {
    border: 1px solid #dddee1;
    border-bottom: none;
    line-height: 35px;
    text-align: center;
    padding-left: 10px;
    padding-right: 10px;
}

.chartBox {
    height: 100%;
    position: absolute;
    right: 0px;
    margin-right: 10px;
}

.chartBox .ivu-col-span-24 {
    margin-bottom: 10px;
}

.equipmentChartBox,
.equipmentChartDoubleColor {
    border: 1px solid #dddee1;
    border-radius: 4px;
    margin: 10px;
    margin-top: 0px;
}

.GISbox {
    width: 99%;
    height: 45vh;
    border: 1px solid #dddee1;
    border-radius: 4px;
    margin-top: 20px;
}

ul {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding-left: 5px;
    height: 80px;
    overflow: auto;
}

ul li {
    list-style: none;
    line-height: 20px;
}

.ivu-col-span-12 {
    padding: 10px;
}

.circleBox {
    text-align: center;
    height: 18vh;
    width: 98%;
    display: inline-block;
    vertical-align: middle;
    border: 1px solid #dddee1;
    border-radius: 4px;
    margin-top: 5px;
    padding-top: 10px;
}

.ivu-card > .ivu-card-body {
    height: 12.5vh;
    overflow-y: auto;
}
</style>



