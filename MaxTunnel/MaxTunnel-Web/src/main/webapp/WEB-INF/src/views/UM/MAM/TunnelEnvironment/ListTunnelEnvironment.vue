<template>
  <div>
    <div style="margin: 1vh;">
      <RadioGroup
        v-model="queryCondition.areaId"
        type="button"
        @on-change="updateArea"
        size="large"
      >
        <Radio
          v-for="(item,key) in areaList"
          :key="key"
          :label="item.id"
          style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
          :class="{select_radio:queryCondition.areaId==item.id}"
        >{{item.name}}</Radio>
      </RadioGroup>
    </div>
    <div style="margin: 1vh;height:3vh;overflow-y: auto">
      <RadioGroup
        v-model="queryCondition.storeId"
        type="button"
        @on-change="changeStore"
        size="large"
      >
        <Radio
          v-for="(item,key) in storeList"
          :key="key"
          :label="item.id"
          style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
          :class="{select_radio:queryCondition.storeId==item.id}"
        >{{item.name}}</Radio>
      </RadioGroup>
    </div>
    <Row>
      <Col span="18" style="padding-left: 10px;padding-right: 10px">
        <div class="gis" :style="{height:curHeight+'px'}" >
            <test-sm-viewer ref="smViewer"></test-sm-viewer>
        </div>
      </Col>
      <Col span="6" style="padding-left: 10px;padding-right: 10px;">
        <div :style="{height:curHeight+'px'}">
          <div class="borde_rhadow" style="height: calc(30vh - 10px);overflow-y: auto;background-color: #5c7b8e;color: #f9f8f6">
            <div style="margin: 10px;">
              <span style="font-size: 2.5vmin">{{curName}}</span>
                <Row :gutter="16" style="padding: 2px;">
                        <Col span="24" class="descCol">
                        <span>区段数：</span>
                        <div style="float: right">  {{curTunnel.areaNum}}</div>
                        </Col>
                    <Col span="24" class="descCol">
                    <span>监测仓个数：</span>
                    <div style="float: right">  {{curTunnel.storeNum}}</div>
                    </Col>
                    <Col span="24"class="descCol">
                    <span>建设单位：</span>
                    <div style="float: right">  {{curTunnel.constructionUnit}}</div>
                    </Col>
                    <Col span="24" class="descCol">
                    <span>运营单位：</span>
                    <div style="float: right">  {{curTunnel.operationUnit}}</div>
                    </Col>
                </Row>
            </div>
          </div>
          <div class="borde_rhadow" style="height:calc(46vh ); overflow-y: auto;background-color: #5c7b8e;margin-top: 10px;color: #f9f8f6">
              <div style="margin: 10px;">
                   <span style="font-size: 2.5vmin">极值数据统计<Icon type="arrow-graph-up-right" :siez="iconSize*1.5"></Icon></span>
                  <Row :gutter="16">
                      <div v-for="item in tunnelProps">
                          <Col span="7"  class="MaxValCol">
                          <Icon type="clipboard" :size="iconSize"></Icon>
                         {{item.key}}
                          </Col>
                          <Col span="7" class="MaxValCol">
                          <Icon type="ios-pulse" :size="iconSize"></Icon>
                        {{item.val}}
                          </Col>
                          <Col span="10" class="MaxValCol" color="#de8d1b">
                          <Icon type="android-locate" :size="iconSize"></Icon>
                          {{item.location}}
                          </Col>
                      </div>
                  </Row>
              </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
import { TunnelService } from "../../../../services/tunnelService";
import { MonitorDataService } from "../../../../services/monitorDataService";
import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";

export default {
  name: "tunnel-list-tunnel-environment",

  data() {
    return {
      curHeight: 450,
      iconSize:16,
      scene: null,
      modelProp: {
        show: {
          //默认隐藏
          state: false
        },
        showButton: false, //关闭底部按钮
        tilte: "详情", //对话框标题
        data: [
          { key: "氧气浓度", val: "12.45%" },
          { key: "硫化氢", val: "45%" },
          { key: "一氧化碳", val: "34.2%" },
          { key: "湿度", val: "30%" }
        ] //属性集
      },
      queryCondition: {
        tunnelId: null, //监测仓ID
        storeId: null, //监测区段ID
        areaId: null,
      },
      curName: "",
      tunnels: [],
      storeList: [],
        areaList: [], //管廊对应区段数据
      tunnelProps: [], //管廊统计数据
      curTunnel: {
        storeNum: 3,
        constructionUnit: "中建一局",
        operationUnit: "波汇科技",
          areaNum: 10
      },
      curSotre: {
        id: "",
        name: ""
      }, //当前监测仓数据
    };
  },

  beforeRouteLeave(to, from, next) {
      console.log("to",to)
      console.log("from",from)
    if (
      to.name == "UMPatrolHomePage" ||
      to.name == "设备管理主页" ||
      to.name == "人员定位详情" ||
      to.name == "虚拟巡检" ||
      to.name == "管廊安防监控列表" ||
      to.name == "管廊环境监控详情" ||
      to.name== "管廊环境监控"  ||
      from.name == "管廊环境监控列表"||
      from.name == "UMPatrolHomePage" ||
      from.name == "设备管理主页" ||
      from.name == "人员定位详情" ||
      from.name == "虚拟巡检" ||
      from.name == "管廊安防监控列表" ||
      from.nmae == "管廊环境监控详情"
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
    this.fentchData();
    this.getMonitorData();
    // 设置表格高度
    this.curHeight = window.innerHeight * 0.76; //将85vh转为数值
    this.iconSize=window.innerHeight*0.02;
    //3D加载
    // this.setGIS();
  },

  methods: {
    //变更模型视角
    changeArea(area) {
      let _this = this;
      try {
        let location = area.split("/");
        _this.scene.camera.flyTo({
          destination: new Cesium.Cartesian3.fromDegrees(
            parseFloat(location[0]),
            parseFloat(location[1]),
            parseFloat(location[2])
          ),
          orientation: {
            heading: parseFloat(location[3]),
            pitch: parseFloat(location[4]),
            roll: parseFloat(location[5])
          }
        });
      } catch (e) {}
    },
    //变更监测仓
    changeStore() {
      //获取区段列表
      let _this = this;
      //获取位置信息
      let curView = this.storeList.filter(
        a => a.id == _this.queryCondition.storeId
      )[0].camera;
      this.changeArea(curView);
        this.getMonitorData();
    },
    //变更区段
    updateArea() {
        this.getMonitorData();
    },

    //获取数据
    fentchData() {
        //获取管廊列表
        let _this = this;
        _this.queryCondition.tunnelId =parseInt( _this.$route.params.id);
      TunnelService.getTunnels().then(
        result => {
          _this.tunnels = result;
          _this.tunnels.forEach(a => {
            if (a.id == _this.queryCondition.tunnelId) {
              _this.curName = a.name;
            }
          });
        },
        error => {
          console.log(error);
        }
      );
      //获取管廊详细信息
      TunnelService.getTunnelDetailByTunnelId(_this.queryCondition.tunnelId).then(
        result => {
          _this.curTunnel.areaNum = result.areaCount;
          _this.curTunnel.storeNum = result.storeCount;
            _this.curTunnel.constructionUnit = result.construct;
            _this.curTunnel.operationUnit = result.operation;
        },
        error => {
          console.log(error);
        }
      );
      //获取管仓列表
      TunnelService.getStoresByTunnelId(_this.queryCondition.tunnelId).then(
        result => {
            _this.storeList=[{id:0,name:"全部"}];
            result.forEach(a=>{
                _this.storeList.push(a)
            })
            _this.queryCondition.storeId=_this.storeList[0].id;
        },
        error => {
          console.log(error);
        }
      );

      //获取区域
      TunnelService.getTunnelArea(_this.queryCondition.tunnelId).then(result => {
        if (result) {
          _this.areaList = [{id:0,name:"全部"}];
          result.forEach(a => {
            var temp = {};
            temp.name = a.name;
            temp.id = a.id;
            _this.areaList.push(temp);
          });
            _this.queryCondition.areaId=_this.areaList[0].id;
        }
      });
    },

    //根据监测类型获取数据
      getMonitorData() {
          let _this = this;
          let parms={tunnelId:_this.queryCondition.tunnelId,
              storeId:_this.queryCondition.storeId==0?null:_this.queryCondition.storeId,
              areaId:_this.queryCondition.areaId==0?null:_this.queryCondition.areaId}
          MonitorDataService.getMaxMonitorData(parms).then((result)=>{
              _this.tunnelProps=[];
              if(result){
                  result.forEach(a => {
                      let temp = {};
                      temp.location = a.location;
                      temp.key = a.key;
                      temp.val = parseFloat(a.val);
                      _this.tunnelProps.push(temp);
                  });
              }
          });
      },
  },

  components: {
    SimulatedData,
    showSwitchData,
    Modal,
    EnvironmentShow,
    TestSmViewer
  },

  watch: {
    $route: function() {
      // $route发生变化时再次赋值
      this.queryCondition.tunnelId = this.tunnelId;
      this.fentchData();
      this.getMonitorData();
    }
  },

  beforeDestroy() {
  }
};
</script>

<style scoped>
  .ivu-select-single .ivu-select-selection{
    height: 100px;
  }
  .MaxValCol {
    margin-top: 4.5vh;
    font-size: 1.66vmin;
  }

  .descCol {
    margin-top: 1vh;
    font-size: 1.66vmin;
  }
    .MaxValCol:hover, .descCol:hover{
        background-color: #44c3ee;
        cursor:pointer
    }

    .borde_rhadow {
  border: 1px solid #b3b0b0;
  border-radius: 8px;
  box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
  border-color: #eee;
}

  .select_radio {
    color: #fff;
    background-color: #869bcb;
    background-position: 0 -15px;
  }

.ivu-modal-wrap > .ivu-modal {
  left: 500px;
  top: 500px;
}

.ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
  background: #adb3e2;
  color: #fff;
}

.gis,
.details {
  display: inline-block;
  border: 1px solid #b3b0b0;
  vertical-align: top;
  width: 100%;
}

.gis {
  position: relative;
}
.Section #pageBox {
  border: 1px solid #b3b0b0;
  bottom: 0px;
  width: 83.5vw;
  left: 0px;
  text-align: center;
  overflow-x: hidden;
}

.tunnelsInfo,
.environmentalMonitoring,
.theFireWarning,
.monitoringSituation {
  margin: 0.5vw;
  border: 1px solid #b3b0b0;
  border-radius: 8px;
  box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
  border-color: #828991;
}

.tunnelsInfo div,
.environmentalMonitoring div,
.theFireWarning div,
.monitoringSituation div {
  line-height: 5vh;
  font-size: 14px;
}

.inline-box div {
  display: inline-block;
}
</style>
