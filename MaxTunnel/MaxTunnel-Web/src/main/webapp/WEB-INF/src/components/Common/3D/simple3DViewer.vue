
<template>
    <div class="content"
         id="simpleGISbox"
         v-cancellation>
    </div>
</template>

<script>

    import Cesium from "Cesium";
    import Vue from 'vue'
    import {
        doSqlQuery,
        processFailed,
        getEntitySet,
        _getFieldValues
    } from "../../../scripts/commonFun.js";
    // import { lookAt } from "../../../scripts/three";
    import { addBarnLabel } from "./mixins/addBarnLabel";
    import { TunnelService } from '../../../services/tunnelService'
    import  viewerBaseConfig  from "./mixins/viewerBase";

    export default {
        mixins: [addBarnLabel,viewerBaseConfig('simpleGISbox','$simpleViewer','simple3DBox',3)],
        props: {
            detectionObjInfor:{
                type:Object,
                default:function(){
                    return {
                        id:null,
                        dataTypeId:null,
                    }
                }
            },
            openVideoLinkage: {
                type: Boolean,
                default: false
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
            },
        },
        data() {
            return {
                handler: null,
                prePosition: null,
            };
        },
        watch: {
            'detectionObjInfor':{

                handler(newVal,oldVal) {

                    if( !newVal.id || !newVal.moTypeId ) return
                    if( !!oldVal ) this.viewer.entities.remove(oldVal.id)

                    this.switchCameraAngle(newVal.id,newVal.moTypeId);
                },
                deep: true
            },
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
        },
        methods: {
            // 初始化
            init() {
                let _this = this;

                _this.initUpdate( _this.viewer,_this.scene );

                //滚轮滑动，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.WHEEL);
                //鼠标左键松开，获得当前窗口的经纬度，偏移角
                _this.handler.setInputAction(e => {
                    this.addLabel(this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed, 1000 / 60);
                }, Cesium.ScreenSpaceEventType.LEFT_UP)

                // _this.handler.setInputAction(e=>{
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
                let _this = this;

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
                        console.warn('error'+ error);
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
            // 展示巡检点
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
            switchCameraAngle(id){
                let _this = this;

                doSqlQuery.call(_this, _this.viewer, 'MOID in ("'+ id +'")', _this.SuperMapConfig.BIM_DATA, _this.onQueryComplete,_this.processFailed)
            },
            onQueryComplete(){
                let _this = this;
                return function (queryEventArgs) {

                    let [ selectedFeatures ] = queryEventArgs.originResult.features;

                    let entity = _this.viewer.entities.add({
                        id: _this.detectionObj.id,
                        position: Cesium.Cartesian3.fromDegrees(parseFloat(_getFieldValues(selectedFeatures,'X')), parseFloat(_getFieldValues(selectedFeatures,'Y')), parseFloat(_getFieldValues(selectedFeatures,'Z'))),
                        label:{
                            text:''
                        }
                    });

                    let offset = _this.detectionObjInfor.dataTypeId != 56 ? new Cesium.HeadingPitchRange(0, 0, 1.5) : new Cesium.HeadingPitchRange(0, -90, 2);

                    _this.viewer.flyTo(entity,{
                        offset:offset,
                    })
                }
            },
            processFailed(queryEventArgs) {
                console.log('查询失败！');
            }
        },
        beforeDestroy() {
            let { handler, refreshCameraPosition, timer } = this;

            refreshCameraPosition.enable = false;
            clearInterval(timer.timeoutId);
            clearInterval(timer.intervalId);

            if (handler != null && handler.isDestroyed()) {
                handler.destroy();
            }

            this.stopCameraPositionRefresh();
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