<template>
  <div>
    <div class=" top">
      <Row style="font-size: 16">
        <Col span="6">
        监测内容:
        <Select v-model="queryCondition.monitorType" style="width:12vw;"
                @on-change='changeQueryTypeList(queryCondition.monitorType)'>
          <Option v-for="item in queryTypeList" :value="item.val" :key="item.val">{{ item.key }}</Option>
        </Select>
        </Col>
        <Col span="6">
        监测仓:
        <Select v-model="queryCondition.storeId" @on-change='changeStore(queryCondition.storeId)' style="width:12vw;">
          <Option v-for="item in sotres" :value="item.id" :key="item.id">{{ item.name }}
          </Option>
        </Select>
        </Col>
        <Col span="6" v-show="showBtn">
        区段:
        <Select v-model="queryCondition.sectionId" @on-change='changeSection(queryCondition.sectionId)'
                style="width:12vw;">
          <Option v-for="item in sections" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
        </Col>
      </Row>
    </div>

    <Tabs type="card" @on-click="changePane">
      <TabPane label="总览" name="total" icon="android-map">
        <diV class="gis">
          <!--<v_3DViewer :id="mapId" @onload="onload">-->
          <!--</v_3DViewer>-->
          <sm-viewer
                  :id="mapId"
                  ref="smViewer"
          >
          </sm-viewer>
          <modal v-bind="modelProp"></modal>
        </diV>
        <!--切换区段时显示的信息-->
        <div class="details" :style="{display:showSection}">
          <div class="tunnelsInfo">
            <div class="title">
              <h3>{{curName+curSotre.name+"概况"}}</h3>
            </div>
          </div>
          <div class="environmentalMonitoring" v-if="queryCondition.monitorType == 1">
            <div class="title">环境监测</div>
            <div class="inline-box" v-for=" item in curEnvData" :key="item.name">
              <div class="numerical">{{item.name}}</div>
              <div class="status"> {{item.value}}</div>
              <div :class="{'normal': item.status<50, 'abnormal': item.status >=50}"></div>
            </div>
          </div>
          <div class="theFireWarning" v-if="queryCondition.monitorType == 2">
            <div class="title">结构监测</div>
            <div class="inline-box" v-for=" item in curConstructionData" :key="item.name">
              <div class="numerical">{{item.name}}</div>
              <div class="status"> {{item.value}}</div>
              <div :class="{'normal': item.status<50, 'abnormal': item.status >=50}"></div>
            </div>
          </div>
          <div class="monitoringSituation" v-if="queryCondition.monitorType == 3">
            <div class="title">安防监测</div>
            <div class="inline-box" v-for=" item in curSecurityData" :key="item.name">
              <div class="numerical">{{item.name}}</div>
              <div class="status"> {{item.value}}</div>
              <div :class="{'normal': item.status<50, 'abnormal': item.status >=50}"></div>
            </div>
          </div>
        </div>
        <div class="details" v-if="showSection==='none'">
          <div class="tunnelsInfo">
            <div>
              <h2>
                {{curName}}
              </h2>
              <div style="padding-left: 3%">
                <div style="width: 60%;float: left">
                  负责人：{{curTunnel.user}}
                </div>
                <div style="width: 40%;float: left">
                  监测仓个数: {{curTunnel.storeNum}}
                </div>
              </div>
              <div style="padding-left: 3%">
                <div style="width: 60%;float: left">
                  建设单位：{{curTunnel.constructionUnit}}
                </div>
                <div style="width: 40%;float: left">
                  区段数：{{curTunnel.sectionNum}}
                </div>
              </div>
              <div style="padding-left: 3%">
                <div>
                  运营单位：{{curTunnel.operationUnit}}
                </div>
              </div>
            </div>
          </div>
          <!-- 根据要监测类型选项切换的模块 -->
          <div style="padding:4% 0% 4% 13%;height:51vh; overflow-y: auto;" v-if="queryCondition.monitorType == 1">
            <EnvironmentShow :tunnelProps="tunnelProps" :circleSize="circleSize"></EnvironmentShow>
          </div>
          <div style="padding: 1%; height:51vh; overflow-y: auto;" v-if="queryCondition.monitorType == 2">
            <!--结构-->
            <div class="constract">
              <p>
              <h2 style="margin-left: 10px;">结构最大值统计:</h2>
              </p>
              <div>
                <Row>
                  <Col v-for="(item,index) in tunnelProps" span="23" :key="index" offset="1" style="padding:12px ">
                  <span style="font-size: 14px;">{{item.key}}：{{item.val}}/{{item.unit}}
                                        <div style="position: relative;float: right;">
                                            <span><div
                                              :class="{'normal': item.status=='正常', 'abnormal': item.status =='不正常'}"
                                              style="position: relative;float: right;margin-top:4px; "></div></span>
                                        </div>
                                        <div style="position: relative; float: right;  margin-right: 30%;">
                                            <span><Icon size="20" type="location" color="#fa9126"></Icon>{{item.location}}</span>
                                        </div>
                  </span>
                  </Col>
                </Row>
              </div>
            </div>
          </div>
          <div style="padding: 1%; height:51vh; overflow-y: auto;" v-if="queryCondition.monitorType == 3">
            <div>
              <!--安防-->
              <div class="Security">
                <p>
                <h2 style="margin-left: 10px;">安防统计:</h2>
                </p>
                <div>
                  <Row style="color: #fff">
                    <Col span="22" style="margin-top: 1vh;margin-bottom: 1vh; margin-left: 1vw;    background: #a8d5de;"
                         v-for="(item,index) in tunnelProps" :key="index">
                    <span style="padding-left: 8px; font-size: 18px;"> {{item.name}}</span>
                    <div style="">
                      <Row>
                        <Col span="10" style="margin-top: 14px;" offset="2" v-for="(item2,index) in item.data"
                             :key="index">
                        {{item2.key}}：{{item2.val}}
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
      </TabPane>
      <TabPane label="详情" name="detial" icon="ios-keypad">
        <div>
          <Col Col span="20" v-show="queryCondition.showDataMenu">
          <div style="position: relative;float: left;width: 70px;">
            <Checkbox :indeterminate="indeterminate" :value="checkAll" size="large"
                      @click.prevent.native="handleCheckAll">全部
            </Checkbox>
          </div>
          <checkbox-group v-model="queryCondition.objDataType" @on-change="checkAllGroupChange">
            <Checkbox v-for="(item,key) in curDataTypeList" :key="key" :label="item.val">
              <span>{{item.key}}</span>
            </Checkbox>
          </checkbox-group>
          </Col>
          <div style="position: relative;float: right;z-index: 1001" v-if="queryCondition.showSwitch">
            <span>总开关:</span>
            <i-switch size="large" @on-change="allControl">
              <span slot="open">ON</span>
              <span slot="close">OFF</span>
            </i-switch>
          </div>
        </div>
        <br>
        <br>
        <Row :gutter="16">
          <Col span="12">
          <div style="width:42vw; height: 72vh;">
            <sm-viewer :id="detialMapId" ref="smViewer1">
            </sm-viewer>
          </div>
          </Col>
          <Col span="12">
          <Row :gutter="16">
            <Col span="12" v-for="item in Obj" :value="item.ObjName" :key="item.id" style="margin-top:10px;">
            <SimulatedData v-bind:Obj="item" v-if="item.datatypeId==1" @changeStatus="changeStatus"></SimulatedData>
            <showSwitchData v-bind:Obj="item" v-else @changeStatus="changeStatus"></showSwitchData>
            </Col>
          </Row>
          </Col>
        </Row>

        <div style="position:absolute;width:100%;bottom: 0px;" v-show="queryCondition.showDataMenu">
          <Page class="nextPage" @on-change="changePage" :total="queryCondition.total" show-total show-elevator
                :page-size="queryCondition.pageSize"></Page>
        </div>
      </TabPane>
    </Tabs>
  </div>
</template>
<script>
  import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
  // import v_3DViewer from "../../../../components/Common/3DViewers";
  import SmViewer from "../../../../components/Common/3D/3DViewer";
  import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
  import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
  import { TunnelService } from '../../../../services/tunnelService'
  import { EnumsService } from '../../../../services/enumsService'
  import { MonitorDataService } from '../../../../services/monitorDataService'
  import {
    setViewAngle,
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed
  } from "../../../../scripts/commonFun.js";
  import {SuperMapSqlQuery, lookAt} from "../../../../scripts/three.js";
  // import {URL_CONFIG} from "../../../../../static/3DMap/js/3DMapConfig";
  import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
  import axios from "axios"

  export default {
    data() {
      return {
        checkAll: true,
        indeterminate: false,
        Obj: [],
        scene: null,
        modelProp: {
          show: {
            //默认隐藏
            state: false
          },
          showButton: false, //关闭底部按钮
          tilte: "详情", //对话框标题
          data: [
            {key: "氧气浓度", val: "12.45%"},
            {key: "硫化氢", val: "45%"},
            {key: "一氧化碳", val: "34.2%"},
            {key: "湿度", val: "30%"}
          ] //属性集
        },
        mapId: "tunnnelMap",
        detialMapId: "detialMap",
        queryCondition: {
          tunnelId: null, //监测仓ID
          storeId: null, //监测区段ID
          sectionId: null,
          objDataType: [], //监测数据类型
          showDataMenu: false,
          monitorType: 1, //监测内容类型
          pageNum: 1,
          pageSize: 6,
          total: 100,
          IsDetial: false,
          showSwitch: false
        },
        circleSize: 120,
        curDataTypeList: [],
        tunnelId: 0,
        curName: "",
        showSection: "none",
        showBtn: false,
        btnStasus: true,
        tunnels: [],
        sotres: [],
        tunnelProps: [], //管廊统计数据
        tunnelPropsMax: [], //监测数据对应最大值
        sections: [], //管廊对应区段数据
        queryTypeList: [], //监测内容
        curTunnel: {
          user: "jisin",
          storeNum: 3,
          constructionUnit: "中建一局",
          operationUnit: "波汇科技",
          sectionNum: 10
        }, //当前管廊数据
        curSotre: {
          id: "",
          name: ""
        }, //当前监测仓数据
        curEnvData: [], //环境监测数据
        curConstructionData: [], //结构监测数据
        curSecurityData: [], //安防监测数据
        timer: null,
        timer: {
          timeoutId: null,
          intervalId: null,
          sectionId: null //保留上次section
        }
      };
    },
    watch: {
      $route: function () {
        // $route发生变化时再次赋值
        this.tunnelId = this.$route.params.id;
        this.queryCondition.tunnelId = this.tunnelId;
        this.sotres = [];
        this.fentchData();
        this.getObjDetialData();
        this.showSection = "none";
        this.btnStasus = true;
        this.showBtn = false;
        this.setCircleSize();
      }
    },
    components: {
      // v_3DViewer,
      SimulatedData,
      showSwitchData,
      Modal,
      EnvironmentShow,
      SmViewer
    },
    mounted() {
      this.fentchData();
      this.setCircleSize();
      // this.getDataType();
      // this.addARrea();
    },
    methods: {
      //勾选全选
      handleCheckAll() {
        if (this.indeterminate) {
          this.checkAll = false;
        } else {
          this.checkAll = !this.checkAll;
        }
        this.indeterminate = false;
        if (this.checkAll) {
          let temp = [];
          this.curDataTypeList.forEach(a => {
            temp.push(a.val);
          });
          this.queryCondition.objDataType = temp;
        } else {
          this.queryCondition.objDataType = [];
        }
        this.changeDataType();
      },
      //勾选数据类型
      checkAllGroupChange(data) {
        if (data.length === this.curDataTypeList.length) {
          this.indeterminate = false;
          this.checkAll = true;
        } else if (data.length > 0) {
          this.indeterminate = true;
          this.checkAll = false;
        } else {
          this.indeterminate = false;
          this.checkAll = false;
        }
        this.changeDataType();
      },
      //加载超图模型
      onload(parent) {
        let _this = this;
        let Cesium = parent.Cesium;

        // 初始化viewer部件
        var viewer = new Cesium.Viewer(this.mapId, {
          navigation: false, //关闭导航控件
          infoBox: false
        });
        var scene = viewer.scene,
          widget = viewer.cesiumWidget,
          imageryLayers = viewer.imageryLayers,
          imagery_mec;
        this.scene = scene;
        var provider_mec = new Cesium.SuperMapImageryProvider({
          url: this.SuperMapConfig.IMG_MAP //墨卡托投影地图服务
        });
        imagery_mec = imageryLayers.addImageryProvider(provider_mec);

        try {
          //打开所发布三维服务下的所有图层
          var promise = scene.open(this.SuperMapConfig.BIM_SCP);

          promise.then(function (layer) {
            // 将东西设置成不可选择
            layer.forEach(element => {
              // 结构性框架的要查数据，非结构性框架的不用管了，也不能选择
              if (element.name.indexOf("结构框架") < 0) {
                element.selectEnabled = false;
              } else {
                // 设置查找参数
                element.setQueryParameter({
                  url: this.SuperMapConfig.BIM_DATA,
                  dataSourceName: "tunnel",
                  dataSetName: "结构框架",
                  keyWord: "SmID"
                });
              }
            });
          });
          //注册鼠标点击事件
          viewer.pickEvent.addEventListener(function (feater) {
          });
          // lay是所有的数据集
          Cesium.when(
            promise,
            function (layer) {
              //设置相机位置、视角，便于观察场景
              setViewAngle(scene, Cesium, _this.camera);
              viewer.pickEvent.addEventListener(function (feature) {
              });
            },
            function (e) {
              if (widget._showRenderLoopErrors) {
                var title =
                  "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
                widget.showErrorPanel(title, undefined, e);
              }
            }
          );
        } catch (e) {
          if (widget._showRenderLoopErrors) {
            var title = "渲染时发生错误，已停止渲染。";
            widget.showErrorPanel(title, undefined, e);
          }
        }

        //滚轮滑动，获得当前窗口的经纬度，偏移角
        var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
        handler.setInputAction(e => {
          addLabel.call(
            _this,
            scene,
            viewer,
            500,
            doSqlQuery,
            this.SuperMapConfig.BIM_DATA,
            labelSqlCompleted,
            processFailed,
            getSection
          );
        }, Cesium.ScreenSpaceEventType.WHEEL);

        bubble.call(_this, Cesium, scene, viewer, "model-content"); //调用气泡
      },
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
        } catch (e) {
        }
      },
      //添加监测区段的视角位置
      addARrea() {
        // let _this = this;
        // _this.axios
        //   .post("/tunnels/stores", {
        //     name: "区域-1",
        //     tunnelId: 2,
        //     camera:
        //       "116.4452364361197/39.91946138913281/1.2353791933507945/4.798691472818731/-0.142284992117377/6.281842456965784",
        //     storeTypeId: 1,
        //     crtTime: +new Date()
        //   })
        //   .then();
      },
      //切换路由
      goToMoudle(path) {
        this.$router.push(path);
      },

      //设置统计图表大小
      setCircleSize() {
        if (window.innerWidth < 1400) {
          this.circleSize = 100;
          if (this.tunnelProps.length > 6) {
            this.circleSize = 95;
          }
        } else if (window.innerWidth > 1400 && window.innerWidth < 1800) {
          this.circleSize = 120;
          if (this.tunnelProps.length > 6) {
            this.circleSize = 105;
          }
        } else if (window.innerWidth > 1800) {
          this.circleSize = 140;
          if (this.tunnelProps.length > 6) {
            this.circleSize = 115;
          }
        }
      },
      //变更监测仓
      changeStore() {
        if (this.queryCondition.storeId) {
          this.showSection = "";
          this.btnStasus = false;
          this.showBtn = true;
          //获取区段列表
          let _this = this
          TunnelService.getSectionsByStoreId(_this.queryCondition.storeId).then(
            (result)=>{
              _this.sections = result.sort((a, b) => a.id - b.id);
              _this.curSotre.id = _this.sections[0].id;
              _this.queryCondition.sectionId = _this.sections[0].id;
              _this.curSotre.name = _this.sections[0].name;
              _this.getSectionsMonitorData();
            },
            (error)=>{
              console.log(error)
            })
          //获取位置信息
          let curView = this.sotres.filter(
            a => a.id == this.queryCondition.storeId
          )[0].camera;
          this.changeArea(curView);
          if (this.IsDetial) {
            this.getObjDetialData();
          }
        }
      },
      //变更区段
      changeSection() {
        if (this.queryCondition.sectionId) {
          this.curSotre.id = this.queryCondition.sectionId;
          this.curSotre.name = this.sections.filter(
            a => a.id == this.curSotre.id
          )[0].name;
          this.queryCondition.sectionId = this.sections[0].id;
          this.getSectionsMonitorData();
          if (this.IsDetial) {
            this.getObjDetialData();
          }
        }
      },
      //变更数据类型
      changeDataType() {
        if (this.queryCondition.objDataType.length > 0) {
          this.getObjDetialData();
        }
      },
      //变更监测内容
      changeQueryTypeList(index) {
        if (this.queryCondition.monitorType) {
          this.queryCondition.monitorType = index;
          this.queryTypeList.forEach(a => {
            if (a.val == index) {
              this.curDataTypeList = a.objectTypeList;
              let temp = [];
              this.curDataTypeList.forEach(a => {
                temp.push(a.val);
              });
              this.queryCondition.objDataType = temp;
            }
          });
          this.fentchData();
          this.getObjDetialData();
        }
      },
      //获取数据
      fentchData() {
        this.tunnelId = this.$route.params.id;
        this.queryCondition.tunnelId = this.tunnelId;
        //获取管廊列表
        let _this = this
        TunnelService.getTunnels().then(
          (result)=>{
            _this.tunnels = result;
            _this.tunnels.forEach(a => {
              if (a.id == _this.tunnelId) {
                _this.curName = a.name;
              }
            });
          },
          (error)=>{
            console.log(error)
          })
          //获取管廊详细信息
          TunnelService.getTunnelDetailByTunnelId(_this.tunnelId).then(
            (result)=>{
              _this.curTunnel.user = result.responsibility.name;
              _this.curTunnel.constructionUnit = result.construct.name;
              _this.curTunnel.operationUnit = result.operation.name;
            },
            (error)=>{
              console.log(error)
            })
        //获取管仓列表     url：tunnels/{id}/stores
        TunnelService.getStoresByTunnelId(_this.tunnelId).then(
          (result)=>{
            _this.sotres = result;
          },
          (error)=>{
            console.log(error)
          })
        this.getMonitorData();
        //获取指定管廊下共有多少管仓    url:tunnels/{id}/stores/count
        TunnelService.getStoresCountByTunnelId(_this.tunnelId).then(
          (result)=>{
            _this.curTunnel.storeNum = result.val
          },
          (error)=>{
            console.log(error)
          })
        //获取区段数
        TunnelService.getSectionsCountByTunnelId(_this.tunnelId).then(
          (result)=>{
            _this.curTunnel.sectionNum = result.val
          },
          (error)=>{
            console.log(error)
          })
        //获取监测内容
        EnumsService.getMonitorType().then(
          (result)=>{
            console.log(result)
            _this.queryTypeList = result;
            _this.queryTypeList.forEach(a => {
              if (a.val == _this.queryCondition.monitorType) {
                _this.curDataTypeList = a.objectTypeList;
                let temp = [];
                _this.curDataTypeList.forEach(a => {
                  temp.push(a.val);
                });
                _this.queryCondition.objDataType = temp;
              }
            });
          },
          (error)=>{
            console.log(error)
          })
      },

      //根据监测类型获取数据
      getMonitorData() {
        if (this.queryCondition.monitorType == 1) {
          let _this = this
          Promise.all([MonitorDataService.getMaxMonitorData(_this.tunnelId,_this.queryCondition.monitorType),
            MonitorDataService.getMonitorData()]).then(
              (result)=>{
                _this.tunnelProps = [];
                _this.tunnelPropsMax = [];
                _this.tunnelPropsMax = result[1];
                result[0].forEach(a => {
                  let temp = {};
                  temp.location = a.location;
                  temp.key = a.key;
                  temp.val = new Number(a.val.toFixed(2));
                  temp.percent =
                    temp.val /
                    _this.tunnelPropsMax.filter(
                      a => a.key == temp.key
                    )[0].val *
                    100;
                  _this.tunnelProps.push(temp);
                });
              },
              (error)=>{
                console.log(error)
              }
            )
        } else if (this.queryCondition.monitorType == 2) {
          this.tunnelProps = [];
          this.axios.get(" ").then(result => {
            let {code, data} = result.data;
            // if (code == 200) {
            // let tempData = data;
            let tempData = [
              {
                key: "沉降",
                val: 2,
                unit: "mm",
                status: "正常",
                location: "燃气仓区段1"
              },
              {
                key: "伸缩缝",
                val: 3,
                unit: "mm",
                status: "正常",
                location: "电力仓区段1"
              },
              {
                key: "应力",
                val: 2000,
                unit: "N",
                status: "正常",
                location: "燃气仓区段2"
              },
              {
                key: "外水压力",
                val: 400,
                unit: "N",
                status: "不正常",
                location: "燃气仓区段1"
              }
            ];
            tempData.forEach(a => {
              let temp = {};
              temp.key = a.key;
              temp.location = a.location;
              temp.val = new Number(a.val.toFixed(2));
              temp.status = a.status;
              temp.unit = a.unit;
              this.tunnelProps.push(temp);
            });
            // }
          });
        } else {
          this.tunnelProps = [];
          this.axios.get(" ").then(result => {
            let {code, data} = result.data;
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
          });
        }
      },

      //点击切换面板事件
      changePane(name) {
        if (name == "detial") {
          this.IsDetial = true;
          this.queryCondition.showDataMenu = true;
          this.getObjDetialData();
        } else {
          this.IsDetial = false;
          this.queryCondition.showDataMenu = false;
        }
      },
      //获取监测仓具体区段的监测数据
      getSectionsMonitorData() {
        if (this.queryCondition.monitorType == 1) {
          let _this = this
          MonitorDataService.getMonitorDataByStoreId(_this.curSotre.id).then(
            (result)=>{
                _this.curEnvData = [];
                result.forEach(a => {
                  let temp = {};
                  temp.name = a.objTypeName;
                  temp.value = new Number(a.cv).toFixed(2);
                  temp.status =
                    new Number(temp.value) /
                    _this.tunnelPropsMax.filter(
                      a => a.key == temp.name
                    )[0].val *
                    100;
                  _this.curEnvData.push(temp);
                });
            },
            (error)=>{
              console.log(error)
            })
        } else if (this.queryCondition.monitorType == 2) {
          let _this = this
          MonitorDataService.getMonitorDataByStoreId(_this.curSotre.id).then(
            (result)=>{
                _this.curEnvData = [];
                result.forEach(a => {
                  let temp = {};
                  temp.name = a.objTypeName;
                  temp.value = new Number(a.cv).toFixed(2);
                  temp.status =
                    new Number(temp.value) /
                    _this.tunnelPropsMax.filter(
                      a => a.key == temp.name
                    )[0].val *
                    100;
                  _this.curConstructionData.push(temp);
                });
            },
            (error)=>{
              console.log(error)
            })
        } else {
          let _this = this
          MonitorDataService.getMonitorDataByStoreId(_this.curSotre.id).then(
            (result)=>{
                _this.curEnvData = [];
                result.forEach(a => {
                  let temp = {};
                  temp.name = a.objTypeName;
                  temp.value = new Number(a.cv).toFixed(2);
                  temp.status =
                    new Number(temp.value) /
                    _this.tunnelPropsMax.filter(
                      a => a.key == temp.name
                    )[0].val *
                    100;
                  _this.curSecurityData.push(temp);
                });
            },
            (error)=>{
              console.log(error)
            })
          // this.axios
          //   .get(
          //     "tunnels/stores/sections/" + this.curSotre.id + "/ai/cv"
          //   )
          //   .then(response => {
          //     let {code, data} = response.data;
          //     if (code == 200) {
          //       this.curEnvData = [];
          //       data.forEach(a => {
          //         let temp = {};
          //         temp.name = a.objTypeName;
          //         temp.value = new Number(a.cv).toFixed(2);
          //         temp.status =
          //           new Number(temp.value) /
          //           this.tunnelPropsMax.filter(
          //             a => a.key == temp.name
          //           )[0].val *
          //           100;
          //         this.curSecurityData.push(temp);
          //       });
          //     }
          //   });
        }
      },
      //总开关控制
      allControl(value) {
        for (var i = 0; i < this.Obj.length; i++) {
          this.Obj[i].ObjVal = value;
        }
      },

      //总开关控制
      allControl(value) {
        for (var i = 0; i < this.Obj.length; i++) {
          this.Obj[i].ObjVal = value;
        }
      },
      //切换开关量控制和设备是否点击
      changeStatus(id, ObjVal, datatypeId, clickStatus) {
        if (datatypeId != 1) {
          this.Obj.filter(a => a.id == id)[0].ObjVal = ObjVal;
        }
        if (clickStatus) {
          this.Obj.forEach(b => {
            if (b.id == id) {
              b.clickStatus = clickStatus;
              this.Log.info("click " + b.id);
              SuperMapSqlQuery(
                this,SuperMapConfig.BIM_DATA,
                this.VMConfig.queryParam,
                "moid = " + b.id
              )
                .then(res => {
                  this.Log.info("查找成功", res);
                  if (res.length > 0) {
                    this.$refs.smViewer1.LookAt1(res[0], 50, -10, 5);
                  }
                })
                .then(res => {
                  this.Log.info("查找失败", res);
                });
            } else {
              b.clickStatus = !clickStatus;
            }
          });
        }
      },
      //获取详情面板的数据
      getObjDetialData() {
        let _this = this;
        var Params = {
          tunnelId: _this.queryCondition.tunnelId,
          storeId: _this.queryCondition.storeId,
          objtypeIds: _this.queryCondition.objDataType,
          sectionId: _this.queryCondition.sectionId,
          pageNum: _this.queryCondition.pageNum,
          pageSize: _this.queryCondition.pageSize
        };
        MonitorDataService.objDetailDatagrid(Params).then(
          (result)=>{
            let ids = "";
            let datatypeId = null;
            _this.Obj = [];
            result.list.forEach(a => {
              let temp = {};
              temp.ObjName = a.name;
              temp.id = a.id;
              temp.clickStatus = false;
              temp.objtypeId = a.objtypeId;
              temp.ObjVal = false;
              temp.objtypeIds = _this.queryCondition.objDataType;
              temp.datatypeId = a.datatypeId;
              ids += a.id + ",";
              datatypeId = a.datatypeId;
              temp.objtypeName =
                _this.curName +
                _this.curSotre.name +
                "---" +
                a.objtypeName;
              _this.Obj.push(temp);
            });
            _this.queryCondition.total = result.total;
            _this.queryCondition.pageNum = result.pageNum;
            ids = ids.slice(0, ids.length - 1);
             if (result.list != null && result.list.length > 0) {
              MonitorDataService.getDataByIdsAndDataType(ids,datatypeId).then(
                (result1)=>{
                    _this.Obj.forEach(a => {
                      result1.filter(b => {
                        if (a.id == b.id) {
                          if (datatypeId == 1) {
                            a.ObjVal = b.cv.toFixed(2);
                            this.queryCondition.showSwitch = false;
                          } else {
                            a.ObjVal = b.cv;
                            this.queryCondition.showSwitch = true;
                          }
                        }
                      });
                    });
                },
                (error)=>{
                  console.log(error)
                })
             }
          },
          (error)=>{
            console.log(error)
          })
        // _this.axios.post("measobjs/datagrid", Params).then(result => {
        //   let {code, data} = result.data;
        //   let ids = "";
        //   let datatypeId = null;
        //   if (code == "200") {
        //     _this.Obj = [];
        //     data.list.forEach(a => {
        //       let temp = {};
        //       temp.ObjName = a.name;
        //       temp.id = a.id;
        //       temp.clickStatus = false;
        //       temp.objtypeId = a.objtypeId;
        //       temp.ObjVal = false;
        //       temp.objtypeIds = _this.queryCondition.objDataType;
        //       temp.datatypeId = a.datatypeId;
        //       ids += a.id + ",";
        //       datatypeId = a.datatypeId;
        //       temp.objtypeName =
        //         _this.curName +
        //         _this.curSotre.name +
        //         "---" +
        //         a.objtypeName;
        //       _this.Obj.push(temp);
        //     });
        //     _this.queryCondition.total = data.total;
        //     _this.queryCondition.pageNum = data.pageNum;
        //     ids = ids.slice(0, ids.length - 1);
        //     if (data.list != null && data.list.length > 0) {
        //       _this.axios
        //         .get(
        //           "measobjs/" +
        //           ids +
        //           "/" +
        //           datatypeId +
        //           "/cache-cv"
        //         )
        //         .then(result => {
        //           let {code, data} = result.data;
        //           if (code == "200") {
        //             _this.Obj.forEach(a => {
        //               data.filter(b => {
        //                 if (a.id == b.id) {
        //                   if (datatypeId == 1) {
        //                     a.ObjVal = b.cv.toFixed(2);
        //                     this.queryCondition.showSwitch = false;
        //                   } else {
        //                     a.ObjVal = b.cv;
        //                     this.queryCondition.showSwitch = true;
        //                   }
        //                 }
        //               });
        //             });
        //           }
        //         });
        //     }
        //   }
        // });
      }
      ,
      //切换页面
      changePage(index) {
        let _this = this;
        _this.queryCondition.pageNum = index;
        _this.getObjDetialData();
      }
      ,
      //切换页码数
      handlePageSize(value) {
        this.queryCondition.pageSize = value;
        this.getObjDetialData();
      }
    }
  };
</script>

<style scoped>
  .constract,
  .Security {
    border: 1px solid #b3b0b0;
    border-radius: 8px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    border-color: #eee;
  }

  .top {
    margin: 0 0 12px 0;
    line-height: 50px;
    background: #fff;
    padding-left: 10px;
  }

  .bottom {
    background: #fff;
    margin-top: 10px;
    padding: 10px;
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
    height: 74vh;
    vertical-align: top;
  }

  .gis {
    position: relative;
    width: 59vw;
  }

  .details {
    border: 1px solid #b3b0b0;
    overflow-y: auto;
    width: 24.3vw;
  }

  .Section #pageBox {
    border: 1px solid #b3b0b0;
    bottom: 0px;
    width: 83.5vw;
    left: 0px;
    text-align: center;
    overflow-x: hidden;
  }

  .showSection {
    display: inline-block;
    line-height: 35px;
  }

  .showSection {
    overflow: hidden;
    padding-left: 20%;
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

  .tunnelsInfo,
  .environmentalMonitoring,
  .theFireWarning,
  .monitoringSituation {
    margin: 0.5vw;
    border: 1px solid #b3b0b0;
    border-radius: 8px;
    padding-left: 20px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    border-color: #eee;
  }

  .tunnelsInfo div,
  .environmentalMonitoring div,
  .theFireWarning div,
  .monitoringSituation div {
    line-height: 5vh;
    font-size: 14px;
  }

  .title {
    font-size: 20px !important;
    text-align: center;
    font-weight: 700;
    margin-top: 5px;
  }

  .inline-box div {
    display: inline-block;
  }

  .normal {
    width: 15px !important;
    height: 15px !important;
    border-radius: 100%;
    background: green;
  }

  .abnormal {
    width: 15px !important;
    height: 15px !important;
    border-radius: 100%;
    background: red;
  }

  .numerical {
    width: 60%;
  }

  .status {
    width: 20%;
  }

  .nextPage {
    position: relative;
    /*width: 470px;*/
    bottom: 0px;
    float: right;
  }
</style>
