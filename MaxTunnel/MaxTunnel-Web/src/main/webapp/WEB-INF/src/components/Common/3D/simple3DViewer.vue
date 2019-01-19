
<template>
    <div class="content" id="simpleGISbox">
    </div>
</template>

<script>

    const stateQuantity = '状态量输入';

    import Cesium from "Cesium";
    import Vue from 'vue'
    import {
        doSqlQuery,
        processFailed,
        getEntitySet,
        getLayer,
        setViewAngle
    } from "../../../scripts/commonFun.js";
    import { flyManagerMinix } from './mixins/flyManager'
    import { addBarnLabel } from "./mixins/addBarnLabel";
    import { TunnelService } from '../../../services/tunnelService'

    export default {
        mixins: [flyManagerMinix, addBarnLabel],
        props: {
            infoBox: {
                type: Boolean,
                default: true
            },
            navigation: {
                type: Boolean,
                default: true
            },
            openVideoLinkage: {
                type: Boolean,
                default: false
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
            };
        },
        watch: {
            'prePosition': {
                handler({ longitude, latitude, height }) {

                    TunnelService.getStorePosition({ longitude, latitude, height })
                        .then( storePosition => {
                            if ( !storePosition ) return;

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
        mounted() {
            this.createHtml()
                .then( ()=> this.init() )
                .catch( () => this.initUpdate( Vue.prototype.$simpleViewer,Vue.prototype.$simpleViewer.scene ) )
        },
        methods: {
            createHtml(){
                let _this = this;

                return new Promise(( resolve, reject ) => {

                    if( !Vue.prototype.$simpleViewer ){

                        $('#simpleGISbox')
                            .prepend("<div id='simple3DBox' style='position: relative;height: 100%;width: 100%'></div>")
                            .end();

                        Vue.prototype.$simpleViewer = new Cesium.Viewer("simple3DBox", {
                            navigation: _this.navigation,
                            infoBox: _this.infoBox
                        });

                        resolve();
                    }else {
                        _this.setGIS();

                        reject();
                    }
                });
            },
            // 初始化
            init() {
                let _this = this;

                _this.viewer = Vue.prototype.$simpleViewer;
                _this.scene = _this.viewer.scene;

                if ( _this.undergroundMode.enable ) {
                    // 设置是否开启地下场景
                    _this.scene.undergroundMode = _this.undergroundMode.enable;
                    // 设置相机最小缩放距离,距离地表-8米
                    _this.scene.screenSpaceCameraController.minimumZoomDistance =
                        _this.undergroundMode.distance;
                    var widget = _this.viewer.cesiumWidget;
                }
                //设置鼠标左键单击回调事件
                _this.viewer.selectedEntityChanged.addEventListener( _this.operationEntity );

                _this.initUpdate( _this.viewer,_this.scene );

                getLayer(_this.scene,_this.cameraPosition,this.SuperMapConfig.BIM_SCP);
                // _this.flyManager(2);

                //滚轮滑动，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.WHEEL);
                //鼠标左键松开，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.LEFT_UP)

            },
            initUpdate( viewer,scene ){
                let _this = this;

                if ( _this.refreshCameraPosition.enable ) {
                    //开启相机定位
                    this.cameraPositionRefresh();
                }

                _this.handler = new Cesium.ScreenSpaceEventHandler(
                    scene.canvas
                );
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
                console.log('2gis',gis)
                if( !gis ) return;

                gis.style.display = "block";

                document.body.removeChild(gis);
                document.getElementById("simpleGISbox").appendChild(gis);
                // 加载视角
                setViewAngle( Vue.prototype.$simpleViewer.scene, this.cameraPosition );
            },
            destory3D() {
                let gis = document.getElementById("simple3DBox");
                if( !gis ) return;

                gis.style.display = "none";

                document.getElementById("simpleGISbox").removeChild(gis);
                document.body.appendChild(gis);

            }
        },
        beforeDestroy() {
            let { handler, refreshCameraPosition, timer } = this;

            refreshCameraPosition.enable = false;
            clearInterval(this.personnelPositionTimerId);
            clearInterval(timer.timeoutId);
            clearInterval(timer.intervalId);

            if (handler != null && handler.isDestroyed()) {
                handler.destroy();
            }

            this.stopCameraPositionRefresh();
            this.destory3D();
        },
    };

</script>

<style scoped>
    .content{
        position: relative;
        width: 100%;
        height: 100%;
    }
    .cesium-viewer-bottom {
        display: none;
    }
</style>