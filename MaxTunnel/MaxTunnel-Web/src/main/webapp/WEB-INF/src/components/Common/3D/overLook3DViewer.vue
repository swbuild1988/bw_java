<template>
    <div class="content" id="GISbox" v-cancellation>
        <Spin fix size="large" v-if="spin.spinShow"></Spin>
        <show-model v-bind="modelProp" @showDesModel="showDesModel"></show-model>
        <describe-model v-bind="model" @removeByEntityId="removeByEntityId"></describe-model>
    </div>
</template>

<script>
import Cesium from "Cesium";
import Vue from "vue";
import $ from "jquery";
import showModel from "../Modal/ShowMapDataModal";
import describeModel from "../../VM/AlarmManage/DescAlarmModel";
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
import { addBarnLabel } from "./mixins/addBarnLabel";
import tools from "./tools";
import { TunnelService } from "../../../services/tunnelService";
import viewerBaseConfig from "./mixins/viewerBase";

let { progressTime } = require("../../../../static/VM/js/VMWebConfig");

export default {
    mixins: [
        tools,
        addBarnLabel,
        viewerBaseConfig("GISbox", "$viewer", "newID", 2)
    ],
    props: {
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
                    isShow: false
                };
            }
        },
        searchCamera: {
            type: Object,
            default: () => {
                return {
                    openSearch: false,
                    isShow: false
                };
            }
        },
        personnelPosition: {
            type: Object,
            default: () => {
                return {
                    openPosition: false,
                    isShow: false,
                    refreshTime: 1000
                };
            }
        },
        defectPosition: {
            type: Object,
            default: () => {
                return {
                    openPosition: false,
                    isShow: false
                };
            }
        },
        openPlanPosition: {
            type: Object,
            default: () => {
                return {
                    openPosition: false
                };
            }
        },
        eventsPosition: {
            type: Object,
            default: () => {
                return {
                    openPosition: false
                };
            }
        },
        cameraPosition: {
            type: Object,
            default: () => {
                return {
                    longitude: 112.49802,
                    latitude: 37.707683,
                    height: 4999.577866,
                    roll: 0,
                    pitch: -1.538392,
                    heading: 0.373214
                };
            }
        }
    },
    data() {
        return {
            handler: null,
            personnelPositionTimerId: null,
            spin: {
                spinShow: this.openSpinShow,
                spinTimer: null
            }
        };
    },
    watch: {
        "unitsPosition.isShow"() {
            switchShowEntity.call(this, {
                messageType: "units"
            });
        },
        "searchCamera.openSearch"() {
            switchShowEntity.call(this, {
                messageType: "videos"
            });
        },
        "personnelPosition.isShow"() {
            switchShowEntity.call(this, {
                messageType: "personnel"
            });
            this.personnelPosition.isShow
                ? this.refreshPersonnelPosition()
                : clearInterval(this.personnelPositionTimerId);
        },
        "defectPosition.isShow"() {
            switchShowEntity.call(this, {
                messageType: "flaw"
            });
        },
        "eventsPosition.openPosition"() {
            let { viewer } = this;

            let events = Vue.prototype.IM.getInformation("events");

            if (this.eventsPosition.openPosition) {
                this.eventNotie();
            } else {
                if (!events.length) return;

                events.forEach(currEvent =>
                    viewer.entities.removeById(currEvent.id)
                );
                events.splice(0);
                this.modelProp.show.state = false;
            }
        }
    },
    components: {
        showModel,
        describeModel
    },
    computed: {},
    beforeMount() {
        Vue.prototype.$viewerComponent = this; // 把当前组件挂载到Vue原型$viewerComponent上
    },
    mounted() {},
    methods: {
        // 初始化
        init() {
            let _this = this;

            _this.viewer.screenSpaceEventHandler.setInputAction(function() {},
            Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);

            if (_this.openSpinShow) {
                //开启加载进度条
                _this.showSpin();
            }
            this.initUpdate(_this.viewer, _this.scene);
            // _this.addRelatedUnits();
            _this.switchCameraAngle();
            _this.addIdentifierViewer(this.VMEntityConfig.unitEntityParam);
            // 滚轮滑动，获得当前窗口的经纬度，偏移角
            _this.handler = new Cesium.ScreenSpaceEventHandler(
                _this.scene.canvas
            );
            setInterval(()=>{
                var camera=_this.viewer.scene.camera;
                var position=camera.position;
                //将笛卡尔坐标化为经纬度坐标
                var cartographic = Cesium.Cartographic.fromCartesian(position);
                var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                var height = cartographic.height;
                console.log(longitude+"/"+latitude+"/"+height);
                console.log('pitch'+camera.pitch)
                console.log('roll'+camera.roll)
                console.log('heading'+camera.heading)
            },10000)
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
        initUpdate(viewer, scene) {
            let _this = this;

            if (_this.openImageryProvider) {
                //开启地图服务

                // let provider_mec = new Cesium.SuperMapImageryProvider({
                //     url: this.SuperMapConfig.IMG_MAP //墨卡托投影地图服务
                // });
                // viewer.imageryLayers.addImageryProvider(provider_mec);
                
                for (
                    let i = 0;
                    i < this.SuperMapConfig.IMG_MAP_LIST.length;
                    i++
                ) {
                    viewer.imageryLayers.addImageryProvider(
                        new Cesium.SuperMapImageryProvider({
                            url: this.SuperMapConfig.IMG_MAP_LIST[i] //墨卡托投影地图服务
                        })
                    );
                }
            }

            if (_this.searchCamera.openSearch) {
                //查询全部相机
                doSqlQuery.call(
                    _this,
                    viewer,
                    "MOTYPEID=7",
                    this.SuperMapConfig.BIM_DATA,
                    addBillboard,
                    processFailed,
                    "videoType",
                    "videos",
                    _this.searchCamera.isShow
                );
            }

            if (_this.unitsPosition.openPosition) {
                //开启单位定位
                getEntitySet.call(this, {
                    viewer: viewer,
                    url: "relatedunits",
                    show: _this.unitsPosition.isShow,
                    typeMode: "unitType",
                    messageType: "units"
                });
            }

            if (_this.personnelPosition.openPosition) {
                //开启人员定位
                _this.refreshPersonnelPosition();
            }

            if (_this.defectPosition.openPosition) {
                //开启缺陷定位
                getEntitySet.call(this, {
                    viewer: viewer,
                    url: "defects/list",
                    typeMode: "flawType",
                    messageType: "flaw",
                    show: _this.defectPosition.isShow,
                    dataUrl: this.SuperMapConfig.BIM_DATA,
                    onQueryComplete: addBillboard,
                    processFailed: processFailed
                });
            }

            if (_this.eventsPosition.openPosition) {
                //开启事件定位
                this.eventNotie();
            }

            if (_this.refreshCameraPosition.enable) {
                //开启相机定位
                this.cameraPositionRefresh();
            }

            getEntityProperty.call(
                _this,
                scene,
                Cesium,
                _this.modelProp,
                "model-content"
            );
        },
        addAlarmEntity(obj) {
            let { viewer } = this;

            if (obj.isDistribute) {
                //isDistribute 为true时为分布式,false为非分布式
                addEntity({
                    viewer: viewer,
                    X: obj.longitude,
                    Y: obj.latitude,
                    Z: Vue.prototype.VMConfig.entityHeight,
                    moId: obj.objectId,
                    show: true,
                    messageType: "alarm",
                    billboard: {
                        image: "alarm-close",
                        height: 30,
                        scaleByDistance: new Cesium.NearFarScalar(
                            0,
                            1,
                            3500,
                            0.8
                        ),
                        verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                    }
                });
            } else {
                doSqlQuery.call(
                    this,
                    viewer,
                    'MOID in ("' + changStrLength(obj.objectId, 10) + '")',
                    this.SuperMapConfig.BIM_DATA,
                    addBillboard,
                    processFailed,
                    "alarmType",
                    "alarm",
                    true
                );
            }
        },
        //人员定位
        refreshPersonnelPosition() {
            let { personnelPosition, viewer } = this;
            if (Cesium.defined(viewer)) {
                this.personnelPositionTimerId = setInterval(() => {
                    getEntitySet.call(this, {
                        viewer: viewer,
                        url: "req-records/new",
                        show: personnelPosition.isShow,
                        typeMode: "personnelType",
                        messageType: "personnel"
                    });
                }, personnelPosition.refreshTime);
            }
        },
        switchCameraAngle() {
            let _this = this;
            let handler = new Cesium.ScreenSpaceEventHandler(
                _this.scene.canvas
            );
            handler.setInputAction(function(event) {
                if (!_this.scene.pickPositionSupported) {
                    return;
                }

                // 获取屏幕坐标
                let scenePosition = _this.scene.pickPosition(event.position);
                // 通过屏幕坐标获取当前位置的实体信息
                let pickedObject = _this.scene.pick(event.position);
                // 如果实体信息存在则说明该位置存在实体
                if (Cesium.defined(pickedObject)) {
                    _this.viewer.flyTo(pickedObject.id);
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        },
        deleteEntity() {
            let entitie = this.viewer.entities._entities._array.filter(
                entitie =>
                    entitie.messageType === "linear" ||
                    entitie.messageType === "events" ||
                    entitie.messageType === "videos" ||
                    entitie.messageType === "units" ||
                    entitie.messageType === "personnel" ||
                    entitie.messageType === "flaw"
            );

            if (entitie.length > 0) {
                entitie.forEach(item => this.viewer.entities.remove(item));
            }
        },
        
    },
    beforeDestroy() {
        this.deleteEntity();

        clearInterval(this.personnelPositionTimerId);
        clearTimeout(this.spin.spinTimer);

        this.stopCameraPositionRefresh();
    }
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