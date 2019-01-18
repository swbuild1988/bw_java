
<script src="tools.js"></script>
<template>
    <div class="content" id="simpleGISbox">
    </div>
</template>

<script>

    const stateQuantity = '状态量输入';

    import Cesium from "Cesium";
    import zlib from "zlib";
    import Vue from 'vue'
    import showModel from '../Modal/ShowMapDataModal'
    import describeModel from '../../VM/AlarmManage/DescAlarmModel'
    import {
        addEntity,
        doSqlQuery,
        processFailed,
        getEntitySet,
        addBillboard,
        getEntityProperty,
        switchShowEntity,
        changStrLength
    } from "../../../scripts/commonFun.js";
    import { flyManagerMinix } from './mixins/flyManager'
    import { addBarnLabel } from "./mixins/addBarnLabel";
    import tools from './tools'
    import { TunnelService } from '../../../services/tunnelService'

    let { progressTime } = require('../../../../static/VM/js/VMWebConfig')

    export default {
        mixins: [flyManagerMinix, tools, addBarnLabel],
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
            openImageryProvider: {
                type: Boolean,
                default: true
            },
            openVideoLinkage: {
                type: Boolean,
                default: false
            },
            openDoubleClickView: {
                type: Boolean,
                default: true
            },
            openFlyLoop: {
                type: Boolean,
                default: false
            },
            openSpinShow: {
                type: Boolean,
                default: false
            },
            unitsPosition: {
                default: () => {
                    return {
                        openPosition: false,
                        isShow: false,
                    }
                }
            },
            searchCamera: {
                type: Object,
                default: () => {
                    return {
                        openSearch: false,
                        isShow: false,
                    }
                }
            },
            personnelPosition: {
                type: Object,
                default: () => {
                    return {
                        openPosition: false,
                        isShow: false,
                        refreshTime: 1000
                    }
                }
            },
            defectPosition: {
                type: Object,
                default: () => {
                    return {
                        openPosition: false,
                        isShow: false,
                    }
                }
            },
            openPlanPosition: {
                type: Object,
                default: () => {
                    return {
                        openPosition: false,
                    }
                }
            },
            eventsPosition: {
                type: Object,
                default: () => {
                    return {
                        openPosition: false,
                    }
                }
            },
            undergroundMode: {
                type: Object,
                default: function () {
                    return {
                        enable: true,
                        distance: -8
                    };
                }
            },
            refreshCameraPosition: {
                type: Object,
                default: function () {
                    return {
                        enable: false,
                        interval: 1000
                    };
                }
            },
            cameraPosition: {
                type: Object,
                default: () => {
                    return {
                        longitude: 112.49360922053003,
                        latitude: 37.71252325043172,
                        height: -1.0311432831720453,
                        roll: 2.582822844487964e-12,
                        pitch: -0.30235173267000404,
                        heading: 1.716482618088178
                    };
                }
            }
        },
        data() {
            return {
                viewer: null,
                scene: null,
                handler: null,
                prePosition: null,
                personnelPositionTimerId: null,
                spin: {
                    spinShow: this.openSpinShow,
                    spinTimer: null
                }
            };
        },
        watch: {
            'unitsPosition.isShow'() {
                switchShowEntity.call(this, {
                    messageType: 'units'
                })
            },
            'searchCamera.openSearch'() {
                switchShowEntity.call(this, {
                    messageType: 'videos'
                })
            },
            'personnelPosition.isShow'() {
                switchShowEntity.call(this, {
                    messageType: 'personnel'
                })
                this.personnelPosition.isShow ?
                    this.refreshPersonnelPosition() :
                    clearInterval(this.personnelPositionTimerId);
            },
            'defectPosition.isShow'() {
                switchShowEntity.call(this, {
                    messageType: 'flaw'
                })
            },
            'eventsPosition.openPosition'() {
                let { viewer } = this;

                let events = Vue.prototype.IM.getInformation('events');

                if (this.eventsPosition.openPosition) {
                    this.eventNotie()
                } else {
                    if (!events.length) return;

                    events.forEach(currEvent => viewer.entities.removeById(currEvent.id));
                    events.splice(0);
                    this.modelProp.show.state = false;
                }

            },
            'prePosition': {
                handler({ longitude, latitude, height }) {

                    TunnelService.getStorePosition({ longitude, latitude, height })
                        .then(storePosition => {
                            if (!storePosition) return;

                            this.$emit("showStorePosition", {
                                areaName: storePosition.area.name,
                                storeName: storePosition.name,
                                tunnelName: storePosition.store.tunnel.name
                            });
                        })
                },
                deep: true
            }
        },
        components: {
            showModel,
            describeModel
        },
        computed: {
            IMG_MAP_LIST() {
                return this.SuperMapConfig.IMG_MAP_LIST;
            }
        },
        beforeMount() {
            Vue.prototype.$viewerComponent = this; // 把当前组件挂载到Vue原型$viewerComponent上
        },
        mounted() {
            this.createHtml().then( ()=> this.init() ).catch( warn => console.log(warn))
        },
        methods: {
            createHtml(){
                let _this = this;
                return new Promise((resolve, reject) => {

                    if( !Vue.prototype.$simpleViewer ){
                        const _div = document.createElement("div");
                        _div.id = "simple3DBox";
                        _div.style.position = "relative";
                        _div.style.height = "100%";
                        _div.style.width = "100%";

                        document.getElementById("simpleGISbox").appendChild(_div);

                        Vue.prototype.$simpleViewer = new Cesium.Viewer("simple3DBox", {
                            navigation: _this.navigation,
                            infoBox: _this.infoBox
                        });
                        resolve();
                    }else {
                        console.log('setGIS')
                        _this.setGIS();
                        reject('setGis');
                    }
                });
            },
            // 初始化
            init() {
                var _this = this;
                // 初始化viewer部件
                // _this.viewer = new Cesium.Viewer(_this.id,{
                //     navigation:_this.navigation,
                //     infoBox:_this.infoBox,

                // });
                // if( this.id == 'newID' ) Vue.prototype.$viewer = _this.viewer;
                // Vue.prototype.$viewer = _this.viewer;// 把当前viewer实例挂载到Vue原型$viewer上
                _this.viewer = Vue.prototype.$simpleViewer;
                _this.scene = Vue.prototype.$simpleViewer.scene;

                if (_this.openDoubleClickView) {
                    //设置是否开始双击视角
                    _this.viewer.screenSpaceEventHandler.setInputAction(function () { }, Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
                }

                if (_this.undergroundMode.enable) {
                    // 设置是否开启地下场景
                    _this.scene.undergroundMode = _this.undergroundMode.enable;
                    // 设置相机最小缩放距离,距离地表-8米
                    _this.scene.screenSpaceCameraController.minimumZoomDistance =
                        _this.undergroundMode.distance;
                    var widget = _this.viewer.cesiumWidget;
                }

                if (_this.searchCamera.openSearch) {
                    //查询全部相机
                    doSqlQuery.call(_this, _this.viewer, 'MOTYPEID=7', this.SuperMapConfig.BIM_DATA, addBillboard, processFailed, 'videoType', 'videos', _this.searchCamera.isShow)
                }

                if (_this.unitsPosition.openPosition) {
                    //开启单位定位
                    getEntitySet.call(this, { viewer: _this.viewer, url: 'relatedunits', show: _this.unitsPosition.isShow, typeMode: 'unitType', messageType: 'units' })
                }

                if (_this.personnelPosition.openPosition) {
                    //开启人员定位
                    _this.refreshPersonnelPosition();

                }

                if (_this.defectPosition.openPosition) {
                    //开启缺陷定位
                    getEntitySet.call(this, {
                        viewer: _this.viewer,
                        url: 'defects/list',
                        typeMode: 'flawType',
                        messageType: 'flaw',
                        show: _this.defectPosition.isShow,
                        dataUrl: this.SuperMapConfig.BIM_DATA,
                        onQueryComplete: addBillboard,
                        processFailed: processFailed
                    })
                }

                if (_this.eventsPosition.openPosition) {
                    //开启事件定位
                    this.eventNotie();
                }

                if (_this.openSpinShow) {
                    //开启加载进度条
                    _this.showSpin();
                }
                if (_this.refreshCameraPosition.enable) {
                    //开启相机定位
                    this.cameraPositionRefresh();
                }


                if (_this.searchCamera.openSearch || _this.unitsPosition.openPosition || _this.personnelPosition.openPosition || _this.defectPosition.openPosition || _this.eventsPosition.openPosition) {
                    //鼠标经过实体时,触发气泡
                    getEntityProperty.call(_this, _this.scene, Cesium, _this.modelProp, 'model-content')
                }
                //设置鼠标左键单击回调事件
                _this.viewer.selectedEntityChanged.addEventListener(this.operationEntity);

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
                            // //设置相机位置、视角，便于观察场景
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
                _this.flyManager(2);
                _this.addIdentifierViewer();

                //滚轮滑动，获得当前窗口的经纬度，偏移角
                _this.handler = new Cesium.ScreenSpaceEventHandler(
                    _this.scene.canvas
                );

                // setInterval(()=>{
                //     var camera=_this.viewer.scene.camera;
                //     var position=camera.position;
                //     //将笛卡尔坐标化为经纬度坐标
                //     var cartographic = Cesium.Cartographic.fromCartesian(position);
                //     var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                //     var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                //     var height = cartographic.height;
                //     console.log(longitude+"/"+latitude+"/"+height);
                //     console.log('pitch'+camera.pitch)
                //     console.log('roll'+camera.roll)
                //     console.log('heading'+camera.heading)
                // },10000)

                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.WHEEL);
                //鼠标左键松开，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.LEFT_UP)
                //  _this.handler.setInputAction(e=>{
                //     var position=_this.scene.pickPosition(e.position)
                //     var camera=_this.viewer.scene.camera;
                //     var cartographic = Cesium.Cartographic.fromCartesian(position)
                //     var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                //     var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                //     var height = cartographic.height;
                //
                //     console.log(longitude+"/"+latitude+"/"+height);
                //     console.log('pitch'+camera.pitch)
                //     console.log('roll'+camera.roll)
                //     console.log('heading'+camera.heading)
                // },Cesium.ScreenSpaceEventType.LEFT_CLICK)

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
                            equals: function (o) {
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

                            _this.$emit("refreshCameraPosition", cameraPosition);
                        }
                    } catch (error) {
                        console.log(error);
                    }

                    _this.cameraPositionRefresh();
                }, _this.refreshCameraPosition.interval);
            },
            //设置相机视角
            setViewAngle() {
                let { scene, cameraPosition } = this;

                if (Cesium.defined(scene)) {
                    scene.camera.setView({
                        destination: new Cesium.Cartesian3.fromDegrees(cameraPosition.longitude, cameraPosition.latitude, cameraPosition.height),
                        orientation: {
                            heading: cameraPosition.heading,
                            pitch: cameraPosition.pitch,
                            roll: cameraPosition.roll
                        }
                    });
                }
            },
            //添加告警实体
            addAlarmEntity(obj) {
                let { viewer } = this;

                if (obj.isDistribute) {  //isDistribute 为true时为分布式,false为非分布式
                    addEntity({
                        viewer: viewer,
                        X: obj.longitude,
                        Y: obj.latitude,
                        Z: Vue.prototype.VMConfig.entityHeight,
                        moId: obj.objectId,
                        show: true,
                        messageType: 'alarm',
                        billboard: {
                            image: 'alarm-close',
                            height: 30,
                            scaleByDistance: new Cesium.NearFarScalar(0, 1, 3500, 0.8),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
                        },
                    })
                } else {

                    doSqlQuery.call(this,
                        viewer,
                        'MOLD in ("' + changStrLength(obj.objectId, 10) + '")',
                        this.SuperMapConfig.BIM_DATA,
                        addBillboard,
                        processFailed,
                        'alarmType',
                        'alarm',
                        true);
                }
            },
            //人员定位
            refreshPersonnelPosition() {
                let { personnelPosition, viewer } = this;
                if (Cesium.defined(viewer)) {
                    this.personnelPositionTimerId = setInterval(() => {
                        getEntitySet.call(
                            this,
                            {
                                viewer: viewer,
                                url: 'actived-locators',
                                show: personnelPosition.isShow,
                                typeMode: 'personnelType',
                                messageType: 'personnel'
                            })
                    }, personnelPosition.refreshTime)
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
            showCheckPointEntity() {
                let { viewer } = this;
                getEntitySet.call(this, {
                    viewer: viewer,
                    url: "actived-locators",
                    show: true,
                    typeMode: "checkPointType",
                    messageType: 'checkPoint'
                })
            },
            //操作实体集
            operationEntity(feater) {
                let _this = this;
                let { viewer } = this;

                if (feater != undefined) {
                    if (feater._dataTypeName == stateQuantity) {

                        let [updateLabel] = viewer.entities._entities._array.filter(label => label._id == feater._id); //获取当前更新的实体
                        var image = !feater.cv ? 'open' : 'close';

                        updateLabel.billboard.image = require('../../../assets/VM/' + image + '.png'); //修改告警图片
                        updateLabel._cv = !feater.cv; //修改cv值

                        return;
                    }
                    if (feater._messageType == 'videos' && _this.openVideoLinkage) {

                        _this.$store.commit('closeVideoLoop');   //关闭视屏监控轮播模式
                        _this.$emit('replaceVideoUrl', feater._moId);
                    }
                }
            },
            showSpin() {
                let { spin } = this;

                spin.spinTimer = setTimeout(() => spin.spinShow = false, progressTime * 1000)
            },
            flyToMyLocation(flyParam) {
                if (typeof flyParam !== 'object') return;

                let { scene } = this;
                let { longitude, latitude, height, roll, pitch, heading } = flyParam.position;
                let duration, maximumHeight;

                duration = flyParam.duration || 5;
                maximumHeight = flyParam.maximumHeight || 6;

                scene.camera.flyTo({
                    destination: new Cesium.Cartesian3.fromDegrees(parseFloat(longitude), parseFloat(latitude), parseFloat(height)),
                    orientation: {
                        heading: parseFloat(1.716482618088178),
                        pitch: parseFloat(-0.30235173267000404),
                        roll: parseFloat(2.582822844487964e-12)
                    },
                    duration: duration,// 设置飞行持续时间，默认会根据距离来计算
                    maximumHeight: maximumHeight,// 相机最大飞行高度
                })
            },
            setGIS() {
                let gis = document.getElementById("simple3DBox");
                gis.style.display = "block";

                document.body.removeChild(gis);
                document.getElementById("simpleGISbox").appendChild(gis);
                // 加载视角
                this.setViewAngle();
            },
            destory3D() {
                let gis = document.getElementById("simple3DBox");
                gis.style.display = "none";

                document.getElementById("simpleGISbox").removeChild(gis);
                document.body.appendChild(gis);

            }
        },
        beforeDestroy() {
            let { handler, refreshCameraPosition, viewer, timer } = this;

            refreshCameraPosition.enable = false;
            clearInterval(this.personnelPositionTimerId);
            clearInterval(timer.timeoutId);
            clearInterval(timer.intervalId);
            clearTimeout(this.spin.spinTimer);

            if (handler != null && handler.isDestroyed()) {
                handler.destroy();
            }

            this.stopCameraPositionRefresh();
            this.destory3D();
        },
    };

</script>

<style scoped>
    .content,
    .content .threedContent {
        position: relative;
        width: 100%;
        height: 100%;
    }
    .cesium-viewer-bottom {
        display: none;
    }
    .pace.pace-inactive {
        display: none;
    }

    .pace {
        -webkit-pointer-events: none;
        pointer-events: none;

        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;

        z-index: 2000;
        position: fixed;
        height: 60px;
        width: 100px;
        margin: auto;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .pace .pace-progress {
        z-index: 2000;
        position: absolute;
        height: 60px;
        width: 100px;

        -webkit-transform: translate3d(0, 0, 0) !important;
        -ms-transform: translate3d(0, 0, 0) !important;
        transform: translate3d(0, 0, 0) !important;
    }

    .pace .pace-progress:before {
        content: attr(data-progress-text);
        text-align: center;
        color: #fff;
        background: #29d;
        border-radius: 50%;
        font-family: "Helvetica Neue", sans-serif;
        font-size: 14px;
        font-weight: 100;
        line-height: 1;
        padding: 20% 0 7px;
        width: 50%;
        height: 40%;
        margin: 10px 0 0 30px;
        display: block;
        z-index: 999;
        position: absolute;
    }

    .pace .pace-activity {
        font-size: 15px;
        line-height: 1;
        z-index: 2000;
        position: absolute;
        height: 60px;
        width: 100px;

        display: block;
        -webkit-animation: pace-theme-center-atom-spin 2s linear infinite;
        -moz-animation: pace-theme-center-atom-spin 2s linear infinite;
        -o-animation: pace-theme-center-atom-spin 2s linear infinite;
        animation: pace-theme-center-atom-spin 2s linear infinite;
    }

    .pace .pace-activity {
        border-radius: 50%;
        border: 5px solid #29d;
        content: " ";
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        height: 60px;
        width: 100px;
    }

    .pace .pace-activity:after {
        border-radius: 50%;
        border: 5px solid #29d;
        content: " ";
        display: block;
        position: absolute;
        top: -5px;
        left: -5px;
        height: 60px;
        width: 100px;

        -webkit-transform: rotate(60deg);
        -moz-transform: rotate(60deg);
        -o-transform: rotate(60deg);
        transform: rotate(60deg);
    }
    .spin-icon-load {
        width: 400px;
        height: 400px;
        animation: ani-demo-spin 1s linear infinite;
    }
    @keyframes ani-demo-spin {
        from {
            transform: rotate(0deg);
        }
        50% {
            transform: rotate(180deg);
        }
        to {
            transform: rotate(360deg);
        }
    }
    .loading {
        font-size: 34px;
    }
    .content >>> .ivu-spin-main {
        width: 15%;
        height: 15%;
    }
    .content >>> .ivu-spin-large .ivu-spin-dot {
        width: 100%;
        height: 100%;
    }
    .pace .pace-activity:before {
        border-radius: 50%;
        border: 5px solid #29d;
        content: " ";
        display: block;
        position: absolute;
        top: -5px;
        left: -5px;
        height: 60px;
        width: 100px;

        -webkit-transform: rotate(120deg);
        -moz-transform: rotate(120deg);
        -o-transform: rotate(120deg);
        transform: rotate(120deg);
    }

    @-webkit-keyframes pace-theme-center-atom-spin {
        0% {
            -webkit-transform: rotate(0deg);
        }
        100% {
            -webkit-transform: rotate(359deg);
        }
    }
    @-moz-keyframes pace-theme-center-atom-spin {
        0% {
            -moz-transform: rotate(0deg);
        }
        100% {
            -moz-transform: rotate(359deg);
        }
    }
    @-o-keyframes pace-theme-center-atom-spin {
        0% {
            -o-transform: rotate(0deg);
        }
        100% {
            -o-transform: rotate(359deg);
        }
    }
    @keyframes pace-theme-center-atom-spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(359deg);
        }
    }
</style>