
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
let viewerComponent = null; //当前组件实例对象
/**
 * 信息统一管理类
 *
 * 用于视屏信息，单位信息，人员信息，缺陷信息，告警信息的增删改查
 *
 *  videos : 视屏信息队列
 *  units : 单位信息队列
 *  personnel : 人员信息队列
 *  flaw : 缺陷信息队列
 *  alarm : 告警信息队列
 *  events : 重大时间队列
 */
class InformationManagement {

    constructor(){
        this.videos = [];
        this.units = [];
        this.personnel = [];
        this.flaw = [];
        this.alarm = [];
        this.events = [];
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

        if(['videos'].indexOf(entity._messageType) != -1){ //当为视屏实体时，隐藏弹框
            modelProp.show.state = false;
            return;
        }
        modelProp.showModelFooter = ['alarm'].indexOf(entity._messageType) != -1 ? true : false; //用于切换footer插槽

        let informations=this.getInformation(entity._messageType);

        if(informations.length != 0 &&　Array.isArray(informations)){

            informations.forEach(information=>{

                if( this._getEntityMoId(information,entity._messageType) == entity._moId ){

                    modelProp.data.splice(0);
                    modelProp.show.state = true;
                    modelProp.showModelFooter &&　viewerComponent.$store.commit('changeAlarm',{'entity':entity,'object':information});//缓存鼠标获取最后一个告警

                    for(let details in information){
                        if(information.hasOwnProperty(details)){

                            let processObj = this._processInformation(infromationManagDetails[entity._messageType + 'Infromations'],details,information);

                            processObj && modelProp.data.push({key:processObj.key,val:processObj.val});
                        }
                    }
                }
            })
        }

        return this;
    }
    _getEntityMoId(information,messageType){
        return (messageType == 'flaw' && information.type == 2) || (messageType == 'alarm')  ? information.objectId : information.id;
    };
    _processInformation(messageFields,details,infromation){
        if(!messageFields) return false;

        let managInfromation = null,
            obj = {};

        for( let i = 0; i < messageFields.length ; i++ ){

            if( messageFields[i].key == details ) {

                managInfromation  = this._isString(messageFields[i].val) ? messageFields[i].val : messageFields[i];

                let dealInfromation = this._isString(managInfromation.val) ? {
                    key : managInfromation.key,
                    val : managInfromation.val(infromation[details])
                }:{
                    key : managInfromation.val,
                    val : infromation[details]
                };

                return Object.assign(obj, dealInfromation);

            }
        }
    };
    _isString(param){
        return Object.prototype.toString.call( param ) == '[object Object]' || Object.prototype.toString.call( param ) == '[object Function]';
    }

}

Vue.prototype.IM = new InformationManagement();

import Cesium from "Cesium";
import zlib from "zlib";
import Vue from 'vue'
import  showModel from '../Modal/ShowMapDataModal'
import  describeModel  from '../../VM/AlarmManage/DescAlarmModel'
import { URL_CONFIG } from "../../../../static/3DMap/js/3DMapConfig";
import { infromationManagDetails } from "./infromationManagChart";
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
} from "../../../scripts/commonFun.js";
import { flyManagerMinix } from './mixins/flyManager'
import tools from './tools'

export default {
    mixins:[ flyManagerMinix,tools ],
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
            let { viewer } =this;
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
        viewerComponent = this;
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

                        updateLabel.billboard.image =require('../../../assets/VM/'+ image +'.png'); //修改告警图片
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
            _this.handler = new Cesium.ScreenSpaceEventHandler(
                _this.scene.canvas
            );
            _this.handler.setInputAction(e => {
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
            _this.handler.setInputAction(e=>{
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
            viewer.entities._entities._array.forEach(entitie=>entitie._show = true);
        },
        //添加告警实体
        addAlarmEntity(obj){
            let {　viewer　}　= this;

            // doSqlQuery.call(this,viewer,'MOID in ('+ obj.objectId.toString() +')',URL_CONFIG.BIM_DATA,addBillboard,processFailed,'alarmType','alarm',true);

            addEntity({
                viewer:viewer,
                X:obj.longitude,
                Y:obj.latitude,
                moId:obj.objectId,
                show:true,
                messageType:'alarm',
                billboard:{
                    image:'alarm2',
                    height:30,
                    scaleByDistance:new Cesium.NearFarScalar(0,1,3500,0.8),
                    verticalOrigin : Cesium.VerticalOrigin.BOTTOM,
                }
            })
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

    },
    beforeDestroy() {
        let { handler,refreshCameraPosition,timer } = this;

        refreshCameraPosition.enable = false;
        clearInterval(timer.personnelPositionTimerId);
        clearInterval(timer.timeoutId);
        clearInterval(timer.intervalId);
        handler.destroy();
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