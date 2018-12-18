
<template>
    <div class="content">
        <div class="threedContent" :id="id">
            <slot></slot>
        </div>
        <show-model v-bind="modelProp"
                    @showDesModel="showDesModel">
        </show-model>
        <describe-model v-bind="model"
                        @removeByEntityId="removeByEntityId">
        </describe-model>
    </div>
</template>

<script>

    const stateQuantity = '状态量';

    import Cesium from "Cesium";
    import zlib from "zlib";
    import Vue from 'vue'
    import  showModel from './Modal/ShowMapDataModal'
    import  describeModel  from '../VM/AlarmManage/DescAlarmModel'
    import {
        addEntity,
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
    import { flyManagerMinix } from './3D/mixins/flyManager'
    import tools from './3D/tools'

    export default {
        mixins:[ flyManagerMinix,tools ],
        props: {
            id: {
                type: String,
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
            eventsPosition:{
                type: Object,
                default: ()=>{
                    return {
                        openPosition:false,
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
                handler:null,
                prePosition: null,
                timer:{
                    timeoutId:null,
                    intervalId:null,
                    sectionId:null, //保留上次section
                    personnelPositionTimerId:null,
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
                this.personnelPosition.isShow ?
                    this.refreshPersonnelPosition() :
                    clearInterval(this.timer.personnelPositionTimerId);
            },
            'defectPosition.isShow'(){
                switchShowEntity.call(this,{
                    messageType:'flaw'
                })
            },
            'eventsPosition.openPosition'(){
                let { viewer } = this;

                let events = Vue.prototype.IM.getInformation('events');

                if( this.eventsPosition.openPosition ){
                    this.eventNotie()
                }else {
                    if( !events.length ) return;

                    events.forEach(currEvent => viewer.entities.removeById( currEvent.id ));
                    events.splice(0);
                    this.modelProp.show.state = false;
                }

            }
        },
        components: {
            showModel,
            describeModel
        },
        beforeMount(){

            Vue.prototype.$viewerComponent = this; // 把当前组件挂载到Vue原型$viewerComponent上
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
                    infoBox:_this.infoBox,

                });
                if( this.id == 'mapViewer' ) Vue.prototype.$viewer = _this.viewer; // 把当前viewer实例挂载到Vue原型$viewer上
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
                        url : this.SuperMapConfig.IMG_MAP//墨卡托投影地图服务
                    });
                    _this.viewer.imageryLayers.addImageryProvider(provider_mec);
                }

                if(_this.searchCamera.openSearch){
                    //查询全部相机
                    doSqlQuery.call(_this,_this.viewer,'MOTYPEID=7',this.SuperMapConfig.BIM_DATA,addBillboard,processFailed,'videoType','videos',_this.searchCamera.isShow)
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
                        dataUrl:this.SuperMapConfig.BIM_DATA,
                        onQueryComplete:addBillboard,
                        processFailed:processFailed
                    })
                }

                if(_this.eventsPosition.openPosition){
                    //开启事件定位
                    this.eventNotie();
                }

                if(_this.searchCamera.openSearch ||　_this.unitsPosition.openPosition ||　_this.personnelPosition.openPosition ||　_this.defectPosition.openPosition ||　_this.eventsPosition.openPosition){
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
                    var promise = _this.scene.open(this.SuperMapConfig.BIM_SCP);

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
                // _this.flyManager();

                //滚轮滑动，获得当前窗口的经纬度，偏移角
                _this.handler = new Cesium.ScreenSpaceEventHandler(
                    _this.scene.canvas
                );
                _this.handler.setInputAction(e => {
                    // addLabel.call(
                    //     _this,
                    //     _this.scene,
                    //     _this.viewer,
                    //     500,
                    //     doSqlQuery,
                    //     SuperMapConfig.BIM_DATA,
                    //     labelSqlCompleted,
                    //     processFailed,
                    //     getSection
                    // );
                }, Cesium.ScreenSpaceEventType.WHEEL);
                //鼠标左键松开，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e=>{
                    // addLabel.call(
                    //     _this,
                    //     _this.scene,
                    //     _this.viewer,
                    //     500,
                    //     doSqlQuery,
                    //     SuperMapConfig.BIM_DATA,
                    //     labelSqlCompleted,
                    //     processFailed,
                    //     getSection
                    // );
                },Cesium.ScreenSpaceEventType.LEFT_UP)
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

                viewer.entities._entities._array.forEach(entitie => entitie._show = true);
            },
            //添加告警实体
            addAlarmEntity(obj){
                let {　viewer　}　= this;

                if( obj.isDistribute ){  //isDistribute 为true时为分布式,false为非分布式

                    addEntity({
                        viewer:viewer,
                        X:obj.longitude,
                        Y:obj.latitude,
                        Z:Vue.prototype.VMConfig.entityHeight,
                        moId:obj.objectId,
                        show:true,
                        messageType:'alarm',
                        billboard:{
                            image:'alarm-close',
                            height:30,
                            scaleByDistance:new Cesium.NearFarScalar(0,1,3500,0.8),
                            verticalOrigin : Cesium.VerticalOrigin.BOTTOM,
                        },
                    })
                }else {
                    doSqlQuery.call(this,viewer,'MOID in ('+ obj.objectId.toString() +')',SuperMapConfig.BIM_DATA,addBillboard,processFailed,'alarmType','alarm',true);
                }
            },
            //人员定位
            refreshPersonnelPosition(){
                let { personnelPosition,viewer,timer } = this;
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
            //展示巡检点
            showCheckPointEntity(){
                let { viewer } = this;
                getEntitySet.call(this,{
                    viewer: viewer,
                    url: "actived-locators",
                    show: true,
                    typeMode: "checkPointType",
                    messageType: 'checkPoint'
                })
            },
            //销毁viewer
            destoryViewer(){
                var layers = this.viewer.scene.layers;
                for(var i=0; i<layers._layers.length;i++){
                    layers.findByIndex(i).destroy()
                    layers.findByIndex(i).ignoreNormal = true
                    layers.findByIndex(i).clearMemoryImmediately = true
                }
                this.viewer.destroy()
            }

        },
        beforeDestroy() {
            let { handler,refreshCameraPosition,timer } = this;

            refreshCameraPosition.enable = false;
            clearInterval(timer.personnelPositionTimerId);
            clearInterval(timer.timeoutId);
            clearInterval(timer.intervalId);
            if(handler != null &&　handler.isDestroyed()){
                handler.destroy();
            }


            this.destoryViewer()
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