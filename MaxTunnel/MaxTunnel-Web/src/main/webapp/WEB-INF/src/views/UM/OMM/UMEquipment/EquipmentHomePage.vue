<template>
  <div>
    <h1>设备信息总览</h1>
    <Row>
      <Col span="18">
      <Col span="6">
      <data-box v-bind="equimentTotalNum"></data-box>
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
      <div class="GISbox">
        <sm-viewer
          :id="id"
          ref="smViewer"
        >
          <!--@onload="onload"-->
        </sm-viewer>
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
  import SmViewer from "../../../../components/Common/3D/3DViewer";
  // import SmViewer from "../../../../components/Common/3DViewers";
  import ProcessRing from "../../../../components/Common/ProcessRing";
  import {URL_CONFIG} from "../../../../../static/3DMap/js/3DMapConfig";
  import {
    setViewAngle,
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed
  } from "../../../../scripts/commonFun.js"
  import equimentTotalNum from "../../../../assets/UM/equimentTotalNum.png";
  import spareAFew from "../../../../assets/UM/spareAFew.png";
  import duration from "../../../../assets/UM/duration.png";
  import equimentRunNum from "../../../../assets/UM/equimentRunNum.png";
  import { EquipmentService } from '../../../../services/equipments'

  export default {
    name: "equipmentMain",
    data() {
      return {
        id: "GIS_ID",
        breakColumns: [
          {
            title: '设备名称',
            key: 'name'
          },
          {
            title: '设备类型',
            key: 'type'
          },
          {
            title: '故障时间',
            key: 'time'
          },
          {
            title: '报告人',
            key: 'user',
          }
        ],
        breakData:[{name:"风机",type:"管廊设备",time:"2018-1-12",user:"张三"}],
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
          title: "设备类别"
        },
        equipmentChartDoubleColor: {
          id: "equipmentCharDoubleColortId",
          requestUrl: "/equipments/type/count",
          title: {
            text: "管廊故障设备统计",
            x: "left",
            textStyle: {
              color: "#2e739b"
            }
          }
        },
        equimentPie: {
          id: "equipmentPieId",
          requestUrl: "/equipments/type/count",
          title: "各类型故障占比"
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
        timer:{
          timeoutId:null,
          intervalId:null,
          sectionId:null //保留上次section
        },
        camera:{
          longitude:112.49402798396824,
          latitude:37.70621237244105,
          height:6.85193571485006,
          roll:6.283185307178147,
          pitch:-0.2724024021381044,
          heading:6.271857201776858
        },
      };
    },
    components: {
      DataBox,
      SimpleBar,
      SimplePie,
      ProcessRing,
      SmViewer
    },
    mounted() {
      this.getEquipmentType();
    },
    methods: {
      //获取管廊设备类别信息
      getEquipmentType() {
        let _this = this
        EquipmentService.getEquTypeAndCount().then(
          (result)=>{
            _this.tunnelInfoList = result;
          },
          (error)=>{
            _this.Log.info(error)
          })
        
      },

      goToMoudle: function (path) {
        this.$router.push(path);
      },
      onload(parent){
        var _this=this;

        var  Cesium=parent.Cesium;
        // 初始化viewer部件
        var viewer = new Cesium.Viewer(this.id);

        var scene = viewer.scene;
        _this.scene=scene;

        scene.undergroundMode = this.VMConfig.UNDERGROUND_MODE //设置是否开启地下场景
        scene.screenSpaceCameraController.minimumZoomDistance = scene.undergroundMode ? -8 : 0;//设置相机最小缩放距离,距离地表-8米
        var widget = viewer.cesiumWidget;

        try{
          //打开所发布三维服务下的所有图层
          var promise = scene.open(URL_CONFIG.BIM_SCP);
          //获取相机
          var camera=viewer.scene.camera;

          Cesium.when(promise,function(layer){
            //设置BIM图层不可选择
            layer.forEach(curBIM=>curBIM._selectEnabled=false);
            //设置相机位置、视角，便于观察场景
            setViewAngle(scene,Cesium,_this.camera)

          },function(e){
            if (widget._showRenderLoopErrors) {
              var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
              widget.showErrorPanel(title, undefined, e);
            }
          });
        }
        catch(e){
          if (widget._groundPrimitives) {
            var title = '渲染时发生错误，已停止渲染。';
            widget.showErrorPanel(title, undefined, e);
          }
        }
        //滚轮滑动，获得当前窗口的经纬度，偏移角
        var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
        handler.setInputAction(e=>{
          addLabel.call(_this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
        },Cesium.ScreenSpaceEventType.WHEEL)

      },
    }
  };
</script>
<style scoped>
  h1,
  .ivu-card > > > .ivu-card-head p {
    color: #2e739b;
  }

  .tunnelProfile {
    border: 1px solid #dddee1;
    border-radius: 4px;
    position: absolute;
    bottom: 2px;
    right: 13px;
    text-align: left;
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

  .ivu-card > > > .ivu-card-body {
    height: 12.5vh;
    overflow-y: auto;
  }
</style>



