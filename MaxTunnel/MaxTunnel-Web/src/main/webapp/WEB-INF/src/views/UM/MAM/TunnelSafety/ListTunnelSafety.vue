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
          style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin;margin-top: 0.4vmin;"
          :class="{select_radio:queryCondition.areaId==item.id}"
        >{{item.name}}</Radio>
      </RadioGroup>
    </div>
        <div style="margin: 1vh;">
            <check-select v-bind="storeProp" @toParent="updateStores"></check-select>
        </div>
    <Row>
      <Col span="18" style="padding-left: 10px;padding-right: 10px">
        <div class="gis" :style="{height:curHeight+'px'}">
          <!-- <sm-viewer :id="mapId" ref="smViewer">
          </sm-viewer> -->
          <TestSmViewer ref="smViewer" :openImageryProvider="false"></TestSmViewer>
        </div>
      </Col>
      <Col span="6" style="padding-left: 10px;padding-right: 10px;">
      <div :style="{height:curHeight+'px'}">
        <div class="monitor-tunnel-overview">
          <div style="margin: 3vmin 10px;">
            <span class="monitor-tunnel-title" >{{curName}}</span>
            <Row :gutter="16" style="padding: 0px 2vmin;">
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
        <div class="monitor-tunnel-overview" style="height:calc(48vh );">
          <div style="margin: 10px;">
            <div>
              <p>
              <h2 class="monitor-tunnel-title">安防统计:</h2>
              </p>
              <div>
                <Row style="color: #fff">
                  <Col span="22" style="margin-top: 1vh;margin-bottom: 1vh; margin-left: 1vw;font-size:1.5vmin"
                       v-for="(item,index) in tunnelProps" :key="index" v-updateStatusImg>
                  <span style="width:100%;display:inline-block;text-align:center"> {{item.name}}</span>
                  <div style="">
                    <Row>
                      <Col span="10" style="margin-top: 14px;" offset="2" v-for="(item2,index) in item.data"
                           :key="index">
                          <img class="open-status-img" :src="ImgName" ><span>{{item2.key}}：{{item2.val}}</span>
                      </Col>
                    </Row>
                  </div>
                  </Col>
                </Row>
              </div>
            </div>
          </div>
        </div>
      </div>
      </Col>
    </Row>
  </div>
</template>

<script>
  import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
  import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
  import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
  import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
  import { EnumsService } from '../../../../services/enumsService'
  import { MonitorDataService } from '../../../../services/monitorDataService'
  import {SuperMapSqlQuery, lookAt} from "../../../../scripts/three.js";
  import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
  import checkSelect from "../../../../components/Common/CheckSelect.vue";
  import { commonFlyFn } from '../Minxis/unit'

  export default {
    name:"list-tunnel-safety",
    mixins:[commonFlyFn],
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
        tunnelProps: [], //管廊统计数据
        updateImg:''
      };
    },
    directives:{
      updateStatusImg:{
        update(el,status){
          console.log('status',status)
        }
      }
    },
    mounted() {
      this.fentchData();
      this.getMonitorData();
      // 设置表格高度
      this.curHeight = window.innerHeight * 0.76; //将85vh转为数值
      this.iconSize=window.innerHeight*0.02;
      //3D加载
    },
    components: {
      SimulatedData,
      showSwitchData,
      Modal,
      EnvironmentShow,
      // SmViewer
      TestSmViewer,
      checkSelect
    },
    methods: {

      //根据监测类型获取数据
      getMonitorData() {
          this.tunnelProps = [];
          // this.axios.get(" ").then(result => {
          //   let {code, data} = result.data;
            // if (code == 200) {
            // let tempData = data;
            let tempData = [
              {
                name: "门禁",
                data: [
                  {key: "开", val: "120"},
                  {key: "关", val: "10"},
                  {key: "告警", val: "2"}
                ]
              },
              {
                name: "电子井盖",
                data: [
                  {key: "开", val: "4"},
                  {key: "关", val: "81"},
                  {key: "告警", val: "3"}
                ]
              },
              {
                name: "红外",
                data: [
                  {key: "正常", val: "70"},
                  {key: "入侵", val: "7"},
                  {key: "告警", val: "3"}
                ]
              }
            ];
            tempData.forEach(a => {
              let temp = {};
              temp.name = a.name;
              temp.data = a.data;
              this.tunnelProps.push(temp);
            });
            // }
          // });
      },
      changStatusImg(status){
        let ImgName  = status == '开'|| '正常' ? 'status-open' :(status == '关'|| '入侵' ? 'status-open' : 'status-alarm');
        this.ImgName = require(`../../../../assets/UM/${ ImgName }.png`)
      }

    },
    watch:{
        $route() {
            // $route发生变化时再次赋值
            this.queryCondition.tunnelId = this.tunnelId;
            this.fentchData();
            this.getMonitorData();
        },
        storeProp: {
            handler: function (newVal, oldVal) {
                this.updateStores(newVal.selectObj.selectId)
            },
            deep: true
        }
    }
  };
</script>

<style scoped>
  @import '../CommonCss/ComStyle.css';

  /* .Security {
    border: 1px solid #b3b0b0;
    border-radius: 8px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    border-color: #eee;
  } */
  .ivu-modal-wrap > .ivu-modal {
    left: 500px;
    top: 500px;
  }
  .ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
    background: #adb3e2;
    color: #fff;
  }

  .details {
    display: inline-block;
    border: 1px solid #b3b0b0;
    vertical-align: top;
    width: 100%;
  }


  .Section #pageBox {
    border: 1px solid #b3b0b0;
    bottom: 0px;
    width: 83.5vw;
    left: 0px;
    text-align: center;
    overflow-x: hidden;
  }
  .showSection ul li {
    display: inline-block;
    border-right: 1px solid #b3b0b0;
    line-height: 35px;
    text-align: center;
  }
  .showSection ul li {
    width: 100px;
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

  .details {
    display: inline-block;
    border: 1px solid #b3b0b0;
    vertical-align: top;
    width: 100%;
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
  .monitor-tunnel-number {
    background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
    background-size: 100% 100%;
  }
  .monitor-tunnel-overview {
      height: calc(30vh - 10px);
      overflow-y: auto;
      color: #f9f8f6;
      background: url("../../../../assets/UM/monitor-tunnel-bg.png") no-repeat;
      background-size: 100% 100%;
  }
  .ivu-col-span-10 {
      display: block;
      width: 33%;
      text-align: center;
  }
  .open-status-img,
  .close-status-img {
      width: 2.1vmin; 
      float: left;
      margin-right: .5vmin;
  }
</style>
