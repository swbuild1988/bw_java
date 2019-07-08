<template>
    <div class="content" id="simpleGISbox" v-cancellation>
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
    import {
        addBarnLabel
    } from "./mixins/addBarnLabel";
    // import { TunnelService } from '../../../services/tunnelService'
    import viewerBaseConfig from "./mixins/viewerBase";

    export default {
        mixins: [addBarnLabel, viewerBaseConfig('simpleGISbox', '$simpleViewer', 'simple3DBox', 1, this
            .listenPosition)],
        props: {
            detectionObjInfor: {
                type: Object,
                default: function () {
                    return {
                        id: null,
                        dataTypeId: null,
                    }
                }
            },
            openVideoLinkage: {
                type: Boolean,
                default: false
            },
            cameraPosition: {
                type: Object,
                default: () => {
                    return {
                        longitude: 112.52407930589152,
                        latitude: 37.703675232475895,
                        height: -2.4658494805836857,
                        roll: 2.5845992013273644e-12,
                        pitch: -0.3023515684457794,
                        heading: 1.7164828118850437
                    };
                }
            },
        },
        data() {
            return {
                handler: null,
                // prePosition: null,
            };
        },
        watch: {
            'detectionObjInfor': {

                handler(newVal, oldVal) {

                    if (!newVal.id || !newVal.moTypeId) return
                    if (!!oldVal) this.viewer.entities.remove(oldVal.id)

                    this.switchCameraAngle(newVal.id, newVal.moTypeId);
                },
                deep: true
            },
        },
        mounted() {},
        methods: {
            // 初始化
            init() {
                let _this = this;

                _this.initUpdate(_this.viewer, _this.scene);

            },
            initUpdate(viewer, scene) {
                let _this = this;

                if (_this.refreshCameraPosition.enable) {
                    //开启相机定位
                    this.cameraPositionRefresh();
                }

                _this.flyManager(1);

                _this.handler = new Cesium.ScreenSpaceEventHandler(
                    scene.canvas
                );
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
                let {
                    viewer
                } = this;
                getEntitySet.call(this, {
                    viewer: viewer,
                    url: "actived-locators",
                    show: true,
                    typeMode: "checkPointType",
                    messageType: 'checkPoint'
                })
            },
            switchCameraAngle(id) {
                let _this = this;

                doSqlQuery.call(_this, _this.viewer, 'MOID in ("'+ id +'")', _this.SuperMapConfig.BIM_DATA, _this.onQueryComplete,_this.processFailed)
            },
            onQueryComplete() {
                let _this = this;
                return function (queryEventArgs) {
                    let [selectedFeatures] = queryEventArgs.originResult.features;

                    // if(!selectedFeatures) return; //
                    let entity = _this.viewer.entities.add({
                        id: _this.detectionObjInfor.id,
                        position: Cesium.Cartesian3.fromDegrees(parseFloat(_getFieldValues(selectedFeatures,
                            'X')), parseFloat(_getFieldValues(selectedFeatures, 'Y')), parseFloat(
                            _getFieldValues(selectedFeatures, 'Z'))),
                        label: {
                            text: ''
                        }
                    });

                    let offset = _this.detectionObjInfor.dataTypeId != 56 ? new Cesium.HeadingPitchRange(0, 0,
                        1.5) : new Cesium.HeadingPitchRange(0, -90, 2);

                    _this.viewer.flyTo(entity, {
                        offset: offset,
                    })
                }
            },
            processFailed(queryEventArgs) {
                console.log('查询失败！');
            },
        },
        beforeDestroy() {
            let {
                handler,
                timer
            } = this;

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
    .content {
        position: relative;
        width: 100%;
        height: 100%;
    }

    .cesium-viewer-bottom {
        display: none;
    }
</style>