<<<<<<< .mine
<template>
    <div class="content">
        <div class="threedContent" :id="id">
            <slot></slot>
        </div>
        <show-model v-bind="modelProp"></show-model>
    </div>
</template>

<script>
import Cesium from "Cesium";
import zlib from "zlib";
import Vue from 'vue'
import  showModel from './Modal/ShowMapDataModal'
import { URL_CONFIG } from "../../../static/3DMap/js/3DMapConfig";
import {
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed,
    getEntitySet,
    addBillboard,
    getEntityProperty,
    switchShowEntity
} from "../../scripts/commonFun.js";

const stateQuantity = '状态量';

class InformationManagement {

    constructor(){
        this.videos = []; //视屏信息
        this.units = [];//单位信息
        this.personnel = []; //人员信息
        this.flaw = []; //缺陷信息
        this.entitysInformation  = []; //实体信息
    }
    getInformation(informationType){
        if(typeof informationType != 'string') return

        return this[informationType];
    };
    addInformation(informationType,information){

        if(Array.isArray(this.getInformation(informationType))){
            this.getInformation(informationType).push(information);
        }
        return this;
    }
    deleteInformation(newInformations,informationType,id){
        if(!Array.isArray(newInformations))return

        newInformations.forEach((details)=>{
            this.getInformation(informationType).forEach((information,index,messageType)=>{

                if(typeof information=='object' &&　typeof details=='object'){
                    information[id] == details[id] &&　messageType.splice(index,1);
                }
            })

        });
        return this;
    }
    searchInformation(entity,modelProp){
        if(['videos'].indexOf(entity._messageType) != -1){
            modelProp.show.state = false;
            return;
        }

        let informations=this.getInformation(entity._messageType);

        if(informations.length != 0 &&　Array.isArray(informations)){
            informations.forEach(information=>{
                if( this._getEntityMoId(information,entity._messageType) == entity._moId ){

                    modelProp.data.splice(0);
                    modelProp.show.state=true;

                    for(let details in information){
                        if(information.hasOwnProperty(details)){
                            modelProp.data.push({key:details,val:information[details]});
                        }
                    }
                }
            })
        }

        return this;
    }
    _getEntityMoId(information,messageType){
        return messageType == 'flaw' && information.type == 2 ? information.objId : information.id;
    }
}

Vue.prototype.IM = new InformationManagement();

export default {
    props: {
        id: {
            type: String
        },
        infoBox: {
            type: Boolean,
            default: true
        },
        navigation: {
            type: Boolean,
            default: true
        },
        openImageryProvider:{
            type: Boolean,
            default: true
        },
        openVideoLinkage:{
            type: Boolean,
            default: false
        },
        unitsPosition:{
            default: ()=>{
                return {
                    openPosition:false,
                    isShow:false,
                }
            }
        },
        searchCamera:{
            type: Object,
            default: ()=>{
                return {
                    openSearch:false,
                    isShow:false,
                }
            }
        },
        personnelPosition:{
            type: Object,
            default: ()=>{
                return {
                    openPosition:false,
                    isShow:false,
                    refreshTime:1000
                }
            }
        },
        defectPosition:{
            type: Object,
            default: ()=>{
                return {
                    openPosition:false,
                    isShow:false,
                }
            }
        },
        undergroundMode: {
            type: Object,
            default: function() {
                return {
                    enable: true,
                    distance: -8
                };
            }
        },
        refreshCameraPosition: {
            type: Object,
            default: function() {
                return {
                    enable: false,
                    interval: 1000
                };
            }
        },
        cameraPosition: {
            type: Object,
            default: ()=> {
                return {
                    longitude: 112.49402798396824,
                    latitude: 37.70621237244105,
                    height: 6.85193571485006,
                    roll: 6.283185307178147,
                    pitch: -0.2724024021381044,
                    heading: 6.271857201776858
                };
            }
        }
    },
    data() {
        return {
            viewer: null,
            scene: null,
            prePosition: null,
            flyManagerAttr:{
                flyManager:null,
                flyTimeoutId:null,
            },
            timer:{
                timeoutId:null,
                intervalId:null,
                sectionId:null, //保留上次section
                personnelPositionTimerId:null,
            },
            modelProp:{
                show:{ //默认隐藏
                    state:false
                },
                showButton:false, //关闭底部按钮
                tilte:'详情', //对话框标题
                data:[],//属性集

            },
            // 飞行文件
            flyFilePath: this.ServerConfig + "/VM/font/flytest.fpf"
        };
    },
    watch:{
        'unitsPosition.isShow'(){
            switchShowEntity.call(this,{
                messageType:'units'
            })
        },
        'searchCamera.openSearch'(){
            switchShowEntity.call(this,{
                messageType:'videos'
            })
        },
        'personnelPosition.isShow'(){
            switchShowEntity.call(this,{
                messageType:'personnel'
            })
            this.personnelPosition.isShow ? this.refreshPersonnelPosition() : clearInterval(timer.personnelPositionTimerId);
        },
        'defectPosition.isShow'(){
            switchShowEntity.call(this,{
                messageType:'flaw'
            })
        }
    },
    components: {
        showModel
    },
    mounted() {
        this.init();
    },
    methods: {
        // 初始化
        init() {
            var _this = this;

            // 初始化viewer部件
            _this.viewer = new Cesium.Viewer(_this.id,{
                navigation:_this.navigation,
                infoBox:_this.infoBox

            });

            _this.scene = _this.viewer.scene;

            if (_this.undergroundMode.enable) {
                // 设置是否开启地下场景
                _this.scene.undergroundMode = _this.undergroundMode.enable;
                // 设置相机最小缩放距离,距离地表-8米
                _this.scene.screenSpaceCameraController.minimumZoomDistance =
                    _this.undergroundMode.distance;
                var widget = _this.viewer.cesiumWidget;
            }

            if(_this.openImageryProvider){
                //开启地图服务
                let provider_mec = new Cesium.SuperMapImageryProvider({
                    url : URL_CONFIG.IMG_MAP//墨卡托投影地图服务
                });
                _this.viewer.imageryLayers.addImageryProvider(provider_mec);
            }

            if(_this.searchCamera.openSearch){
                //查询全部相机
                doSqlQuery.call(_this,_this.viewer,'MOTYPEID=7',URL_CONFIG.BIM_DATA,addBillboard,processFailed,'videoType','videos',_this.searchCamera.isShow)
            }

            if(_this.unitsPosition.openPosition){
                //开启单位定位
                getEntitySet.call(this,{viewer:_this.viewer,url:'relatedunits',show:_this.unitsPosition.isShow,typeMode:'unitType',messageType:'units'})
            }

            if(_this.personnelPosition.openPosition){
                //开启人员定位
                _this.refreshPersonnelPosition();

            }

            if(_this.defectPosition.openPosition){
                //开启缺陷定位
                getEntitySet.call(this,{
                    viewer:_this.viewer,
                    url:'defects/list',
                    typeMode:'flawType',
                    messageType:'flaw',
                    show:_this.defectPosition.isShow,
                    dataUrl:URL_CONFIG.BIM_DATA,
                    onQueryComplete:addBillboard,
                    processFailed:processFailed
                    })
            }

            if(_this.searchCamera.openSearch ||　_this.unitsPosition.openPosition ||　_this.personnelPosition.openPosition ||　_this.defectPosition.openPosition){
                //鼠标经过实体时,触发气泡
                getEntityProperty.call(_this,_this.scene,Cesium,_this.modelProp,'model-content',_this.viewer)
            }
            //设置鼠标左键单击回调事件
            _this.viewer.selectedEntityChanged.addEventListener(feater=>{
                if(feater != undefined){

                    if(feater._dataTypeName == stateQuantity){

                        let [ updateLabel ]=_this.viewer.entities._entities._array.filter(label=>label._id == feater._id); //获取当前更新的实体
                        var image = !feater.cv ? 'open' :'close';

                        updateLabel.billboard.image =require('../../assets/VM/'+ image +'.png'); //修改告警图片
                        updateLabel._cv = !feater.cv; //修改cv值
                        return;
                    }
                    if(feater._messageType == 'videos' && _this.openVideoLinkage){

                        _this.$store.commit('closeVideoLoop');   //开启视屏监控轮播模式
                        _this.$emit('replaceVideoUrl',feater._moId);
                    }
                }
            })

            try {
                //打开所发布三维服务下的所有图层
                var promise = _this.scene.open(URL_CONFIG.BIM_SCP);

                Cesium.when(
                    promise,
                    function(layer) {
                        //设置BIM图层不可选择
                        layer.forEach(
                            curBIM => (curBIM._selectEnabled = false)
                        );
                        //设置相机位置、视角，便于观察场景
                        _this.setViewAngle();
                    },
                    function(e) {
                        if (widget._showRenderLoopErrors) {
                            var title =
                                "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
                            widget.showErrorPanel(title, undefined, e);
                        }
                    }
                );
            } catch (e) {
                if (widget._groundPrimitives) {
                    var title = "渲染时发生错误，已停止渲染。";
                    widget.showErrorPanel(title, undefined, e);
                }
            }
            _this.flyManager();

            //滚轮滑动，获得当前窗口的经纬度，偏移角
            var handler = new Cesium.ScreenSpaceEventHandler(
                _this.scene.canvas
            );
            handler.setInputAction(e => {
                addLabel.call(
                    _this,
                    _this.scene,
                    _this.viewer,
                    500,
                    doSqlQuery,
                    URL_CONFIG.BIM_DATA,
                    labelSqlCompleted,
                    processFailed,
                    getSection
                );
            }, Cesium.ScreenSpaceEventType.WHEEL);
            //鼠标左键松开，获得当前窗口的经纬度，偏移角
            handler.setInputAction(e=>{
                addLabel.call(
                    _this,
                    _this.scene,
                    _this.viewer,
                    500,
                    doSqlQuery,
                    URL_CONFIG.BIM_DATA,
                    labelSqlCompleted,
                    processFailed,
                    getSection
                );
            },Cesium.ScreenSpaceEventType.LEFT_UP)
        },
        // 开始相机位置刷新
        startCameraPositionRefresh() {
            console.log("开始刷新");
            this.refreshCameraPosition.enable = true;
            this.cameraPositionRefresh();
        },
        // 停止相机位置刷新
        stopCameraPositionRefresh() {
            this.refreshCameraPosition.enable = false;
        },
        // 相机位置刷新
        cameraPositionRefresh() {
            var _this = this;

            setTimeout(() => {
                try {
                    // 如果刷新相机位置不可用，则退出
                    if (!_this.refreshCameraPosition.enable) return;

                    var camera = _this.scene.camera;
                    var position = camera.position;
                    //将笛卡尔坐标化为经纬度坐标
                    var cartographic = Cesium.Cartographic.fromCartesian(
                        position
                    );
                    var longitude = Cesium.Math.toDegrees(
                        cartographic.longitude
                    );
                    var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                    var height = cartographic.height;

                    var cameraPosition = {
                        longitude: longitude,
                        latitude: latitude,
                        height: height,
                        pitch: camera.pitch,
                        roll: camera.roll,
                        heading: camera.heading,
                        equals: function(o) {
                            if (o == null) return false;
                            return (
                                Math.abs(o.longitude - this.longitude) <
                                    0.000001 &&
                                Math.abs(o.latitude - this.latitude) <
                                    0.000001 &&
                                Math.abs(o.height - this.height) < 0.000001 &&
                                Math.abs(o.pitch - this.pitch) < 0.000001 &&
                                Math.abs(o.roll - this.roll) < 0.000001 &&
                                Math.abs(o.heading - this.heading) < 0.000001
                            );
                        }
                    };
                    if (!cameraPosition.equals(_this.prePosition)) {
                        _this.prePosition = cameraPosition;
                        console.log("now position", _this.prePosition);
                        _this.$emit("refreshCameraPosition", cameraPosition);
                    }
                } catch (error) {
                    console.log(error);
                }

                _this.cameraPositionRefresh();
            }, _this.refreshCameraPosition.interval);
        },
        // 飞行管理
        flyManager() {
            if (!global.Cesium) return;

            let {scene, viewer, flyManagerAttr} = this;
            let routes = new Cesium.RouteCollection();
            //添加fpf飞行文件，fpf由SuperMap iDesktop生成
            routes.fromFile(this.flyFilePath);
            this.Log.info("routes 加载完毕");

            //初始化飞行管理
            flyManagerAttr.flyManager = new Cesium.FlyManager({
                scene: scene,
                routes: routes
            });

            //注册站点到达事件
            flyManagerAttr.flyManager.stopArrived.addEventListener(
                routeStop => {
                    let stopName = routeStop.stopName;
                    this.Log.info("stop Name", stopName);
                    routeStop.waitTime = 1;
                }
            );
        },
        //开始飞行
        playFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.play();
            }
        },
        // 暂停飞行
        pauseFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.pause();
            }
        },
        //停止飞行
        stopFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.stop();
            }
        },
        // 加速飞行
        speedUp() {
            let flyManager = this.flyManagerAttr.flyManager;
            this.Log.info("当前速率：" + flyManager.playRate);
            if (flyManager.playRate < 1) {
                flyManager.playRate += 0.1;
            } else {
                if (flyManager.playRate < 10) flyManager.playRate += 1;
            }
            this.Log.info("加速后速率：" + flyManager.playRate);
        },
        // 减速飞行
        speedDown() {
            let flyManager = this.flyManagerAttr.flyManager;
            if (flyManager.playRate > 1) {
                flyManager.playRate -= 1;
            } else {
                if (flyManager.playRate > 0.5) flyManager.playRate -= 0.1;
            }
        },
        //飞行过程刷新数据
        _flightRefreshData(){
            let { scene,viewer,flyManagerAttr } = this;
            if(!flyManagerAttr.flyTimeoutId){

                flyManagerAttr.flyTimeoutId = setTimeout(()=>{
                    addLabel.call(this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
                    flyManagerAttr.flyTimeoutId  = null;
                },this.VMConfig.flyManagerProp.flyUpdateTime);
            }
        },
        //设置相机视角
        setViewAngle(){
            let { scene,cameraPosition } = this;

            if(Cesium.defined(scene)){
                scene.camera.setView({
                    destination : new Cesium.Cartesian3.fromDegrees(cameraPosition.longitude,cameraPosition.latitude,cameraPosition.height),
                    orientation : {
                        heading : cameraPosition.heading,
                        pitch : cameraPosition.pitch,
                        roll : cameraPosition.roll
                    }
                });
            }
        },
        //隐藏所有实体
        hideAllEntitys(){
            let {　viewer　} = this;
            viewer.entities._entities._array.forEach(entitie=>entitie._show = false);
        },
        //人员定位
        refreshPersonnelPosition(){
            let { timer,personnelPosition,viewer } = this;

            if(Cesium.defined(viewer)){
                timer.personnelPositionTimerId = setInterval(()=>{
                    getEntitySet.call(
                        this,
                        {
                            viewer:viewer,
                            url:'actived-locators',
                            show:personnelPosition.isShow,
                            typeMode:'personnelType',
                            messageType:'personnel'
                        })
                },personnelPosition.refreshTime)
            }

        },
        LookAt1(obj, heading, pitch, range) {
            let target = Cesium.Cartesian3.fromDegrees(
                obj.longitude,
                obj.latitude,
                // obj.height
            );
            lookAt(
                this.viewer,
                target,
                Cesium.Math.toRadians(heading),
                Cesium.Math.toRadians(pitch),
                range
            );
        },

    },
    beforeDestroy() {
        this.refreshCameraPosition.enable = false;
    },
};
</script>

<style scoped>
.content, .threedContent{
    position: relative;
    width: 100%;
    height: 100%;
}
.cesium-viewer-bottom{
    display:none
}
</style>
=======
<template>
  <div class="content">
    <div class="threedContent" :id="id">
      <slot></slot>
    </div>
    <show-model v-bind="modelProp"></show-model>
  </div>
</template>

<script>
  import Cesium from "Cesium";
  import zlib from "zlib";
  import Vue from 'vue'
  import  showModel from './Modal/ShowMapDataModal'
  import { URL_CONFIG } from "../../../static/3DMap/js/3DMapConfig";
  import {
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed,
    getEntitySet,
    addBillboard,
    getEntityProperty,
    switchShowEntity
  } from "../../scripts/commonFun.js";

  const stateQuantity = '状态量';

  class InformationManagement {

    constructor(){
      this.videos = []; //视屏信息
      this.units = [];//单位信息
      this.personnel = []; //人员信息
      this.flaw = []; //缺陷信息
      this.checkPoint = [] //巡检点信息
    }
    getInformation(informationType){
      if(typeof informationType != 'string') return

      return this[informationType];
    };
    addInformation(informationType,information){

      if(Array.isArray(this.getInformation(informationType))){
        this.getInformation(informationType).push(information);
      }
      return this;
    }
    deleteInformation(newInformations,informationType,id){
      if(!Array.isArray(newInformations))return

      newInformations.forEach((details)=>{
        this.getInformation(informationType).forEach((information,index,messageType)=>{

          if(typeof information=='object' &&　typeof details=='object'){
            information[id] == details[id] &&　messageType.splice(index,1);
          }
        })

      });
      return this;
    }
    searchInformation(entity,modelProp){
      let informations=this.getInformation(entity._messageType);

      if(informations.length != 0 &&　Array.isArray(informations)){
        informations.forEach(information=>{
          if(this._getEntityMoId(information,entity._messageType) == entity._moId){

            modelProp.data.splice(0);
            modelProp.show.state=true;

            for(let details in information){
              if(information.hasOwnProperty(details)){
                modelProp.data.push({key:details,val:information[details]});
              }
            }
          }
        })
      }
      return this;
    }
    _getEntityMoId(information,messageType){
      return messageType == 'flaw' && information.type == 2 ? information.objId : information.id;
    }
  }

  Vue.prototype.IM = new InformationManagement();

  export default {
    props: {
      id: {
        type: String
      },
      infoBox: {
        type: Boolean,
        default: true
      },
      navigation: {
        type: Boolean,
        default: true
      },
      openImageryProvider:{
        type: Boolean,
        default: true
      },
      openVideoLinkage:{
        type: Boolean,
        default: false
      },
      unitsPosition:{
        default: ()=>{
          return {
            openPosition:false,
            isShow:false,
          }
        }
      },
      searchCamera:{
        type: Object,
        default: ()=>{
          return {
            openSearch:false,
            isShow:false,
          }
        }
      },
      personnelPosition:{
        type: Object,
        default: ()=>{
          return {
            openPosition:false,
            isShow:false,
          }
        }
      },
      defectPosition:{
        type: Object,
        default: ()=>{
          return {
            openPosition:false,
            isShow:false,
          }
        }
      },
      undergroundMode: {
        type: Object,
        default: function() {
          return {
            enable: true,
            distance: -8
          };
        }
      },
      refreshCameraPosition: {
        type: Object,
        default: function() {
          return {
            enable: false,
            interval: 1000
          };
        }
      },
      cameraPosition: {
        type: Object,
        default: ()=> {
          return {
            longitude: 112.49402798396824,
            latitude: 37.70621237244105,
            height: 6.85193571485006,
            roll: 6.283185307178147,
            pitch: -0.2724024021381044,
            heading: 6.271857201776858
          };
        }
      }
    },
    data() {
      return {
        viewer: null,
        scene: null,
        prePosition: null,
        flyManagerAttr:{
          flyManager:null,
          flyTimeoutId:null,
        },
        timer:{
          timeoutId:null,
          intervalId:null,
          sectionId:null //保留上次section
        },
        modelProp:{
          show:{ //默认隐藏
            state:false
          },
          showButton:false, //关闭底部按钮
          tilte:'详情', //对话框标题
          data:[],//属性集

        },
      };
    },
    watch:{
      'unitsPosition.isShow'(){
        switchShowEntity.call(this,{
          messageType:'units'
        })
      },
      'searchCamera.openSearch'(){
        switchShowEntity.call(this,{
          messageType:'videos'
        })
      },
      'personnelPosition.isShow'(){
        switchShowEntity.call(this,{
          messageType:'personnel'
        })
      },
      'defectPosition.isShow'(){
        switchShowEntity.call(this,{
          messageType:'flaw'
        })
      }
    },
    components: {
      showModel
    },
    mounted() {
      this.init();
    },
    methods: {
      // 初始化
      init() {
        var _this = this;

        // 初始化viewer部件
        _this.viewer = new Cesium.Viewer(_this.id,{
          navigation:_this.navigation,
          infoBox:_this.infoBox

        });
        _this.scene = _this.viewer.scene;

        if (_this.undergroundMode.enable) {
          // 设置是否开启地下场景
          _this.scene.undergroundMode = _this.undergroundMode.enable;
          // 设置相机最小缩放距离,距离地表-8米
          _this.scene.screenSpaceCameraController.minimumZoomDistance =
            _this.undergroundMode.distance;
          var widget = _this.viewer.cesiumWidget;
        }

        if(_this.openImageryProvider){
          //开启地图服务
          let provider_mec = new Cesium.SuperMapImageryProvider({
            url : URL_CONFIG.IMG_MAP//墨卡托投影地图服务
          });
          _this.viewer.imageryLayers.addImageryProvider(provider_mec);
        }

        if(_this.searchCamera.openSearch){
          //查询全部相机
          doSqlQuery.call(_this,_this.viewer,'MOTYPEID=7',URL_CONFIG.BIM_DATA,addBillboard,processFailed,'videoType','videos',_this.searchCamera.isShow)
        }

        if(_this.unitsPosition.openPosition){
          //开启单位定位
          getEntitySet.call(this,{viewer:_this.viewer,url:'relatedunits',show:_this.unitsPosition.isShow,typeMode:'unitType',messageType:'units'})
        }

        if(_this.personnelPosition.openPosition){
          //开启人员定位
          getEntitySet.call(this,{viewer:_this.viewer,url:'actived-locators',show:_this.personnelPosition.isShow,typeMode:'personnelType',messageType:'personnel'})
        }

        if(_this.defectPosition.openPosition){
          //开启缺陷定位
          getEntitySet.call(this,{
            viewer:_this.viewer,
            url:'defects/list',
            typeMode:'flawType',
            messageType:'flaw',
            show:_this.defectPosition.isShow,
            dataUrl:URL_CONFIG.BIM_DATA,
            onQueryComplete:addBillboard,
            processFailed:processFailed
          })
        }

        if(_this.searchCamera.openSearch ||　_this.unitsPosition.openPosition ||　_this.personnelPosition.openPosition ||　_this.defectPosition.openPosition){
          //鼠标经过实体时,触发气泡
          getEntityProperty.call(_this,_this.scene,Cesium,_this.modelProp,'model-content')
        }
        //设置鼠标左键单击回调事件
        _this.viewer.selectedEntityChanged.addEventListener(feater=>{
          if(feater != undefined){

            if(feater._dataTypeName == stateQuantity){

              let [ updateLabel ]=_this.viewer.entities._entities._array.filter(label=>label._id == feater._id); //获取当前更新的实体
              var image = !feater.cv ? 'open' :'close';

              updateLabel.billboard.image =require('../../assets/VM/'+ image +'.png'); //修改告警图片
              updateLabel._cv = !feater.cv; //修改cv值
              return;
            }
            if(feater._messageType == 'videos' && _this.openVideoLinkage){

              _this.$store.commit('closeVideoLoop');   //开启视屏监控轮播模式
              _this.$emit('replaceVideoUrl',feater._moId);
            }
          }
        })

        try {
          //打开所发布三维服务下的所有图层
          var promise = _this.scene.open(URL_CONFIG.BIM_SCP);

          Cesium.when(
            promise,
            function(layer) {
              //设置BIM图层不可选择
              layer.forEach(
                curBIM => (curBIM._selectEnabled = false)
              );
              //设置相机位置、视角，便于观察场景
              _this.setViewAngle();
            },
            function(e) {
              if (widget._showRenderLoopErrors) {
                var title =
                  "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
                widget.showErrorPanel(title, undefined, e);
              }
            }
          );
        } catch (e) {
          if (widget._groundPrimitives) {
            var title = "渲染时发生错误，已停止渲染。";
            widget.showErrorPanel(title, undefined, e);
          }
        }
        _this.flyManager();

        //滚轮滑动，获得当前窗口的经纬度，偏移角
        var handler = new Cesium.ScreenSpaceEventHandler(
          _this.scene.canvas
        );
        handler.setInputAction(e => {
          addLabel.call(
            _this,
            _this.scene,
            _this.viewer,
            500,
            doSqlQuery,
            URL_CONFIG.BIM_DATA,
            labelSqlCompleted,
            processFailed,
            getSection
          );
        }, Cesium.ScreenSpaceEventType.WHEEL);
        //鼠标左键松开，获得当前窗口的经纬度，偏移角
        handler.setInputAction(e=>{
          addLabel.call(
            _this,
            _this.scene,
            _this.viewer,
            500,
            doSqlQuery,
            URL_CONFIG.BIM_DATA,
            labelSqlCompleted,
            processFailed,
            getSection
          );
        },Cesium.ScreenSpaceEventType.LEFT_UP)
      },
      // 开始相机位置刷新
      startCameraPositionRefresh() {
        console.log("开始刷新");
        this.refreshCameraPosition.enable = true;
        this.cameraPositionRefresh();
      },
      // 停止相机位置刷新
      stopCameraPositionRefresh() {
        this.refreshCameraPosition.enable = false;
      },
      // 相机位置刷新
      cameraPositionRefresh() {
        var _this = this;

        setTimeout(() => {
          try {
            // 如果刷新相机位置不可用，则退出
            if (!_this.refreshCameraPosition.enable) return;

            var camera = _this.scene.camera;
            var position = camera.position;
            //将笛卡尔坐标化为经纬度坐标
            var cartographic = Cesium.Cartographic.fromCartesian(
              position
            );
            var longitude = Cesium.Math.toDegrees(
              cartographic.longitude
            );
            var latitude = Cesium.Math.toDegrees(cartographic.latitude);
            var height = cartographic.height;

            var cameraPosition = {
              longitude: longitude,
              latitude: latitude,
              height: height,
              pitch: camera.pitch,
              roll: camera.roll,
              heading: camera.heading,
              equals: function(o) {
                if (o == null) return false;
                return (
                  Math.abs(o.longitude - this.longitude) <
                  0.000001 &&
                  Math.abs(o.latitude - this.latitude) <
                  0.000001 &&
                  Math.abs(o.height - this.height) < 0.000001 &&
                  Math.abs(o.pitch - this.pitch) < 0.000001 &&
                  Math.abs(o.roll - this.roll) < 0.000001 &&
                  Math.abs(o.heading - this.heading) < 0.000001
                );
              }
            };
            if (!cameraPosition.equals(_this.prePosition)) {
              _this.prePosition = cameraPosition;
              console.log("now position", _this.prePosition);
              _this.$emit("refreshCameraPosition", cameraPosition);
            }
          } catch (error) {
            console.log(error);
          }

          _this.cameraPositionRefresh();
        }, _this.refreshCameraPosition.interval);
      },
      // 飞行管理
      flyManager(){
        if(!global.Cesium) return

        let { scene,viewer,flyManagerAttr } = this;
        let routes = new Cesium.RouteCollection();
        //添加fpf飞行文件，fpf由SuperMap iDesktop生成
        routes.fromFile(this.VMConfig.flyManagerProp.flyUrl);
        new Cesium.Route().speed = this.VMConfig.flyManagerProp.flySpeed; //飞行速度
        //初始化飞行管理
        flyManagerAttr.flyManager = new Cesium.FlyManager({
          scene : scene,
          routes : routes
        });
        //注册站点到达事件
        flyManagerAttr.flyManager.stopArrived.addEventListener((routeStop)=>{
          let stopName = routeStop.stopName;
          flyManagerAttr.flyTimeoutId  = null;
          viewer.clock.onTick.removeEventListener(this._flightRefreshData);
          routeStop.waitTime = 1;
        });
      },
      //开始飞行
      playFly(){
        let { flyManagerAttr,viewer }=this;

        if(flyManagerAttr.flyManager){
          flyManagerAttr.flyManager.play();
          viewer.clock.onTick.addEventListener(this._flightRefreshData)
        }
      },
      //停止飞行
      stopFly(){
        let { flyManagerAttr,viewer }=this;

        if(flyManagerAttr.flyManager){
          flyManagerAttr.flyManager.stop();
          flyManagerAttr.flyTimeoutId  = null;
          viewer.clock.onTick.removeEventListener(this._flightRefreshData);
        }
      },
      //飞行过程刷新数据
      _flightRefreshData(){
        let { scene,viewer,flyManagerAttr } = this;
        if(!flyManagerAttr.flyTimeoutId){

          flyManagerAttr.flyTimeoutId = setTimeout(()=>{
            addLabel.call(this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
            flyManagerAttr.flyTimeoutId  = null;
          },this.VMConfig.flyManagerProp.flyUpdateTime);
        }
      },
      //设置相机视角
      setViewAngle(){
        let { scene,cameraPosition } = this;

        if(Cesium.defined(scene)){
          scene.camera.setView({
            destination : new Cesium.Cartesian3.fromDegrees(cameraPosition.longitude,cameraPosition.latitude,cameraPosition.height),
            orientation : {
              heading : cameraPosition.heading,
              pitch : cameraPosition.pitch,
              roll : cameraPosition.roll
            }
          });
        }
      },
      //隐藏所有实体
      hideAllEntitys(){
        let {　viewer　} = this;
        viewer.entities._entities._array.forEach(entitie=>entitie._show = false);
      }

    }
  };
</script>

<style scoped>
  .content, .threedContent{
    position: relative;
    width: 100%;
    height: 100%;
  }
  .cesium-viewer-bottom{
    display:none
  }
</style>
>>>>>>> .r943
