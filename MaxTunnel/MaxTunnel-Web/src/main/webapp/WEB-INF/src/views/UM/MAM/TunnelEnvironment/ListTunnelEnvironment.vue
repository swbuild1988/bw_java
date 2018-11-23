<template>
  <div>
    <div class=" top">
      <Row style="font-size:16">
        <Col span="6">
        监测仓:
        <Select v-model="queryCondition.storeId" @on-change='changeStore(queryCondition.storeId)' style="width:12vw;">
          <Option value="0" key="0">全部
          </Option>
          <Option v-for="item in sotres" :value="item.id" :key="item.id">{{ item.name }}
          </Option>
        </Select>
        </Col>
        <Col span="6">
        区段:
        <Select v-model="queryCondition.sectionId" @on-change='changeSection(queryCondition.sectionId)'
                style="width:12vw;">
          <Option value="0" key="0">全部
          </Option>
          <Option v-for="item in sections" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
        </Col>
      </Row>
    </div>
    <diV class="gis" id="GISbox">
      <!-- <sm-viewer :id="mapId" ref="smViewer">
      </sm-viewer> -->
      <TestSmViewer ref="smViewer"></TestSmViewer>
      <modal v-bind="modelProp"></modal>
    </diV>
    <!--切换区段时显示的信息-->
    <div class="details" :style="{display:showSection}">
      <div class="tunnelsInfo">
        <div class="title">
          <h3>{{curName+curSotre.name+"概况"}}</h3>
        </div>
      </div>
      <div class="environmentalMonitoring">
        <div class="title">环境监测</div>
        <div class="inline-box" v-for=" item in curEnvData" :key="item.name">
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
      <div style="padding:4% 0% 4% 13%;height:51vh; overflow-y: auto;">
        <EnvironmentShow :tunnelProps="tunnelProps" :circleSize="circleSize"></EnvironmentShow>
      </div>
    </div>
  </div>
</template>
<script>
  import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
  // import SmViewer from "../../../../components/Common/3D/3DViewer";
  import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
  import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
  import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
  import {TunnelService} from '../../../../services/tunnelService'
  import {EnumsService} from '../../../../services/enumsService'
  import {MonitorDataService} from '../../../../services/monitorDataService'
  // import {
  //   setViewAngle,
  //   bubble,
  //   addLabel,
  //   getSection,
  //   doSqlQuery,
  //   labelSqlCompleted,
  //   processFailed
  // } from "../../../../scripts/commonFun.js";
  import {SuperMapSqlQuery} from "../../../../scripts/three.js";
  import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";

  export default {
    name: "tunnel-list-tunnel-environment",
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
    beforeRouteLeave (to, from, next) {
      if(to.name == 'UMPatrolHomePage' || to.name == '设备管理主页' || to.name == '虚拟巡检' || to.name == '人员定位详情' || to.name == '管廊安防监控列表' || to.name == '管廊环境监控详情'
        || from.name == 'UMPatrolHomePage' || from.name == '设备管理主页' || from.name == 'UMDetailEquipment' || from.name == '虚拟巡检' || from.name == '人员定位详情' || from.name == '管廊安防监控列表' || from.name == '管廊环境监控详情' || from.name == '管廊安防监控详情'){
          from.meta.keepAlive = true
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
        this.setCircleSize();
      }
    },
    components: {
      // v_3DViewer,
      SimulatedData,
      showSwitchData,
      Modal,
      EnvironmentShow,
      // SmViewer
      TestSmViewer
    },
    mounted() {
      this.fentchData();
      this.setCircleSize();
      this.setGIS()
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
        this.getObjDetialData();
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
        this.getObjDetialData();
      },
      //加载超图模型
      // onload(parent) {
      //   let _this = this;
      //   let Cesium = parent.Cesium;

      //   // 初始化viewer部件
      //   var viewer = new Cesium.Viewer(this.mapId, {
      //     navigation: false, //关闭导航控件
      //     infoBox: false
      //   });
      //   var scene = viewer.scene,
      //     widget = viewer.cesiumWidget,
      //     imageryLayers = viewer.imageryLayers,
      //     imagery_mec;
      //   this.scene = scene;
      //   var provider_mec = new Cesium.SuperMapImageryProvider({
      //     url: this.SuperMapConfig.IMG_MAP //墨卡托投影地图服务
      //   });
      //   imagery_mec = imageryLayers.addImageryProvider(provider_mec);

      //   try {
      //     //打开所发布三维服务下的所有图层
      //     var promise = scene.open(this.SuperMapConfig.BIM_SCP);

      //     promise.then(function (layer) {
      //       // 将东西设置成不可选择
      //       layer.forEach(element => {
      //         // 结构性框架的要查数据，非结构性框架的不用管了，也不能选择
      //         if (element.name.indexOf("结构框架") < 0) {
      //           element.selectEnabled = false;
      //         } else {
      //           // 设置查找参数
      //           element.setQueryParameter({
      //             url: this.SuperMapConfig.BIM_DATA,
      //             dataSourceName: "tunnel",
      //             dataSetName: "结构框架",
      //             keyWord: "SmID"
      //           });
      //         }
      //       });
      //     });
      //     //注册鼠标点击事件
      //     viewer.pickEvent.addEventListener(function (feater) {
      //     });
      //     // lay是所有的数据集
      //     Cesium.when(
      //       promise,
      //       function (layer) {
      //         //设置相机位置、视角，便于观察场景
      //         setViewAngle(scene, Cesium, _this.camera);
      //         viewer.pickEvent.addEventListener(function (feature) {
      //         });
      //       },
      //       function (e) {
      //         if (widget._showRenderLoopErrors) {
      //           var title =
      //             "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
      //           widget.showErrorPanel(title, undefined, e);
      //         }
      //       }
      //     );
      //   } catch (e) {
      //     if (widget._showRenderLoopErrors) {
      //       var title = "渲染时发生错误，已停止渲染。";
      //       widget.showErrorPanel(title, undefined, e);
      //     }
      //   }

      //   //滚轮滑动，获得当前窗口的经纬度，偏移角
      //   var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
      //   handler.setInputAction(e => {
      //     addLabel.call(
      //       _this,
      //       scene,
      //       viewer,
      //       500,
      //       doSqlQuery,
      //       this.SuperMapConfig.BIM_DATA,
      //       labelSqlCompleted,
      //       processFailed,
      //       getSection
      //     );
      //   }, Cesium.ScreenSpaceEventType.WHEEL);

      //   bubble.call(_this, Cesium, scene, viewer, "model-content"); //调用气泡
      // },
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
      // },
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
          this.btnStasus = false;
          //获取区段列表
          let _this = this
          TunnelService.getSectionsByStoreId(_this.queryCondition.storeId).then(
            (result) => {
              _this.sections = result.sort((a, b) => a.id - b.id);
              _this.curSotre.id = _this.sections[0].id;
              _this.queryCondition.sectionId = _this.sections[0].id;
              _this.curSotre.name = _this.sections[0].name;
              _this.getSectionsMonitorData();
            },
            (error) => {
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
      },
      //变更区段
      changeSection() {
          this.curSotre.id = this.queryCondition.sectionId;
          this.curSotre.name = this.sections.filter(
            a => a.id == this.curSotre.id
          )[0].name;
          this.queryCondition.sectionId = this.sections[0].id;
          this.getSectionsMonitorData();
          if (this.IsDetial) {
            this.getObjDetialData();
          }
      },
      // //变更数据类型
      // changeDataType() {
      //   if (this.queryCondition.objDataType.length > 0) {
      //     this.getObjDetialData();
      //   }
      // },
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
          (result) => {
            _this.tunnels = result;
            _this.tunnels.forEach(a => {
              if (a.id == _this.tunnelId) {
                _this.curName = a.name;
              }
            });
          },
          (error) => {
            console.log(error)
          })
        //获取管廊详细信息
        TunnelService.getTunnelDetailByTunnelId(_this.tunnelId).then(
          (result) => {
            _this.curTunnel.user = result.responsibility.name;
            _this.curTunnel.constructionUnit = result.construct.name;
            _this.curTunnel.operationUnit = result.operation.name;
          },
          (error) => {
            console.log(error)
          })
        //获取管仓列表     url：tunnels/{id}/stores
        TunnelService.getStoresByTunnelId(_this.tunnelId).then(
          (result) => {
            _this.sotres = result;
          },
          (error) => {
            console.log(error)
          })
        this.getMonitorData();
        //获取指定管廊下共有多少管仓
        TunnelService.getStoresCountByTunnelId(_this.tunnelId).then(
          (result) => {
            _this.curTunnel.storeNum = result.val
          },
          (error) => {
            console.log(error)
          })
        //获取区段数
        TunnelService.getSectionsCountByTunnelId(_this.tunnelId).then(
          (result) => {
            _this.curTunnel.sectionNum = result.val
          },
          (error) => {
            console.log(error)
          })
        //获取监测内容
        EnumsService.getMonitorType().then(
          (result) => {
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
          (error) => {
            console.log(error)
          })
      },

      //根据监测类型获取数据
      getMonitorData() {
        if (this.queryCondition.monitorType == 1) {
          let _this = this;
          Promise.all([MonitorDataService.getMaxMonitorData(_this.tunnelId, _this.queryCondition.monitorType),
            MonitorDataService.getMonitorData()]).then(
            (result) => {
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
            (error) => {
              console.log(error)
            }
          )
        }
      },

      //获取监测仓具体区段的监测数据
      getSectionsMonitorData() {
        if (this.queryCondition.monitorType == 1) {
          let _this = this
          MonitorDataService.getMonitorDataByStoreId(_this.curSotre.id).then(
            (result) => {
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
            (error) => {
              console.log(error)
            })
        }
      },
      //总开关控制
      // allControl(value) {
      //   for (var i = 0; i < this.Obj.length; i++) {
      //     this.Obj[i].ObjVal = value;
      //   }
      // },

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
                this, SuperMapConfig.BIM_DATA,
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
          (result) => {
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
              MonitorDataService.getDataByIdsAndDataType(ids, datatypeId).then(
                (result1) => {
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
                (error) => {
                  console.log(error)
                })
            }
          },
          (error) => {
            console.log(error)
          })
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
      },
      destory3D(){
          var gis = document.getElementById("newID");
          gis.style.display = "none";
          document.getElementById("GISbox").removeChild(gis)
          document.body.appendChild(gis)
      }
    },
    beforeDestroy(){
      this.destory3D()
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
