
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
const stateQuantity = "状态量";
let viewerComponent = null; //当前组件实例对象

import Cesium from "Cesium";
import zlib from "zlib";
import Vue from "vue";
import showModel from "../Modal/ShowMapDataModal";
import describeModel from "../../VM/AlarmManage/DescAlarmModel";
// import { URL_CONFIG } from "../../../../static/3DMap/js/3DMapConfig";
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
import { flyManagerMinix } from "./mixins/flyManager";
import tools from "./tools";

export default {
    mixins: [flyManagerMinix, tools],
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
        openImageryProvider: {
            type: Boolean,
            default: true
        },
        openVideoLinkage: {
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
        defectPosition: {
            type: Object,
            default: () => {
                return {
                    openPosition: false,
                    isShow: false
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
                    intervalId: null,
                    interval: 1000
                };
            }
        },
        refreshPersonnelPosition: {
            type: Object,
            default: () => {
                return {
                    enable: false,
                    intervalId: null,
                    interval: 1000
                };
            }
        },
        cameraPosition: {
            type: Object,
            default: () => {
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
            viewer: this.GLOBAL.Viewer,
            scene: this.GLOBAL.Viewer.scene,
            handler: null,
            prePosition: null,
            timer: {
                timeoutId: null,
                intervalId: null,
                // 保留上次section
                sectionId: null
            }
        };
    },
    watch: {},
    components: {
        showModel,
        describeModel
    },
    beforeMount() {
        viewerComponent = this;
    },
    mounted() {
        this.init();
    },
    methods: {
        // 初始化
        init() {
            var _this = this;

            // 初始化飞行管理
            _this.flyManager();

            // 滚轮滑动，获得当前窗口的经纬度，偏移角
            _this.handler = new Cesium.ScreenSpaceEventHandler(
                _this.viewer.scene.canvas
            );
            _this.handler.setInputAction(e => {
                addLabel.call(
                    _this,
                    _this.viewer.scene,
                    _this.viewer,
                    500,
                    doSqlQuery,
                    this.SuperMapConfig.BIM_DATA,
                    labelSqlCompleted,
                    processFailed,
                    getSection
                );
                this.Log.info("addLabel");
            }, Cesium.ScreenSpaceEventType.WHEEL);
            // 鼠标左键松开，获得当前窗口的经纬度，偏移角
            _this.handler.setInputAction(e => {
                addLabel.call(
                    _this,
                    _this.viewer.scene,
                    _this.viewer,
                    500,
                    doSqlQuery,
                    this.SuperMapConfig.BIM_DATA,
                    labelSqlCompleted,
                    processFailed,
                    getSection
                );
            }, Cesium.ScreenSpaceEventType.LEFT_UP);
            _this.setViewAngAngle()
            
        },
        //设置相机视角
        setViewAngAngle(camera) {
            let tmp_cameraPosition = camera ? camera : this.cameraPosition;
            this.Log.info("cameraPosition:", tmp_cameraPosition);

            if (Cesium.defined(this.viewer.scene)) {
                this.viewer.scene.camera.setView({
                    destination: new Cesium.Cartesian3.fromDegrees(
                        tmp_cameraPosition.longitude,
                        tmp_cameraPosition.latitude,
                        tmp_cameraPosition.height
                    ),
                    orientation: {
                        heading: tmp_cameraPosition.heading,
                        pitch: tmp_cameraPosition.pitch,
                        roll: tmp_cameraPosition.roll
                    }
                });
            }
        },
        //隐藏所有实体
        hideAllEntitys() {
            let { viewer } = this;
            viewer.entities._entities._array.forEach(
                entitie => (entitie._show = true)
            );
        },
        // 开始相机位置刷新
        startCameraPositionRefresh() {
            // 已经再刷新了，退出
            if (this.refreshCameraPosition.enable) return;

            this.refreshCameraPosition.enable = true;
            this.cameraPositionRefresh();
        },
        // 停止相机位置刷新
        stopCameraPositionRefresh() {
            if (!this.refreshCameraPosition.intervalId)
                clearInterval(this.refreshCameraPosition.intervalId);
            this.refreshCameraPosition.enable = false;
        },
        // 相机位置刷新
        cameraPositionRefresh() {
            var _this = this;

            _this.refreshCameraPosition.intervalId = setInterval(() => {
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
                        // console.log("now position", _this.prePosition);
                        _this.$emit("refreshCameraPosition", cameraPosition);
                    }
                } catch (error) {
                    console.log(error);
                }
            }, _this.refreshCameraPosition.interval);
        },
        // 开始人员定位刷新
        startPersonnelPosition() {
            if (this.refreshPersonnelPosition.enable) return;

            this.refreshPersonnelPosition.enable = true;
            this.personnelPositionRefresh();
        },
        // 停止人员定位刷新
        stopPersonnelPosition() {
            if (this.refreshPersonnelPosition.intervalId)
                clearInterval(this.refreshPersonnelPosition.intervalId);
            this.refreshPersonnelPosition.enable = false;
        },
        // 人员定位刷新
        personnelPositionRefresh() {
            if (Cesium.defined(this.viewer)) {
                this.refreshPersonnelPosition.intervalId = setInterval(() => {
                    getEntitySet.call(this, {
                        viewer: this.viewer,
                        url: "actived-locators",
                        show: this.refreshPersonnelPosition.enable,
                        typeMode: "personnelType",
                        messageType: "personnel"
                    });
                }, this.refreshPersonnelPosition.interval);
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
                messageType: "checkPoint"
            });
        },
    },
    beforeDestroy() {
        let { handler, refreshCameraPosition, timer } = this;

        // 停止位置刷新
        this.stopCameraPositionRefresh();

        if (handler) handler.destroy();
    }
};
</script>

<style scoped>
.content,
.threedContent {
    position: relative;
    width: 100%;
    height: 100%;
}
.cesium-viewer-bottom {
    display: none;
}
</style>