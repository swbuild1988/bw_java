import Vue from 'vue'
import { flyManagerMinix } from "./flyManager";
import {
    doSqlQuery,
    processFailed,
} from "../../../../scripts/commonFun.js";
import { TunnelService } from '../../../../services/tunnelService'


const stateQuantity = '状态量输入';

export default ( containerId,viewer,domId,route ) => ({
    mixins:[flyManagerMinix],
    props:{
        infoBox: {
            type: Boolean,
            default: true
        },
        navigation: {
            type: Boolean,
            default: true
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
                    enable: true,
                    interval: 1000
                };
            }
        },
    },
    directives:{
        cancellation:{
            unbind(els){

                let elementChild = [].slice.call( els.children );
                let [ gis ] = elementChild.filter(el => el.id === domId);

                gis.style.display = "none";

                els.removeChild(gis);
                document.body.appendChild(gis);
            }
        }
    },
    watch:{
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
    computed:{
        viewer(){
            return Vue.prototype[ viewer ];
        },
        scene(){
            return this.viewer.scene;
        }
    },
    data(){
        return {
            prePosition: null,
        }
    },
    mounted() {
        this.createHtml()
            .then( ()=> {
                this.getLayer();
                this.init();
                this.initProps();
                this.flyManager(route);
            })
            .catch( () => {
                this.initUpdate( Vue.prototype[ viewer ],Vue.prototype[ viewer ].scene  )
            } );
        this.paramUpdate();

    },
    methods:{
        createHtml(){
            let _this = this;

            return new Promise(( resolve, reject ) => {

                if( !Vue.prototype[ viewer ] ){

                    $( '#'+ containerId )
                        .prepend("<div id='"+ domId +"' style='position: relative;height: 100%;width: 100%'></div>")
                        .end();

                    Vue.prototype[ viewer ] = new Cesium.Viewer(domId, {
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
        getLayer(){
            let _this = this;
            if ( Cesium.defined( this.scene ) ){

                try {
                    //打开所发布三维服务下的所有图层
                    let promise = this.scene.open(this.SuperMapConfig.BIM_SCP);

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
            }
        },
        initProps(){
            let _this = this;
            // 去除login/版本信息
            _this.viewer._cesiumWidget._creditContainer.style.display = "none";

            if ( _this.undergroundMode.enable ) {
                // 设置是否开启地下场景
                _this.scene.undergroundMode = _this.undergroundMode.enable;
                // 设置相机最小缩放距离,距离地表-8米
                _this.scene.screenSpaceCameraController.minimumZoomDistance =
                    _this.undergroundMode.distance;
                var widget = _this.viewer.cesiumWidget;
            }

        },
        paramUpdate(){
            let _this = this;

            _this.viewer.selectedEntityChanged.addEventListener( this.operationEntity );

        },
        setViewAngle() {
            let { scene, cameraPosition } = this;
            
            if ( Cesium.defined( scene ) ) {
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
        compare(id,parentNode){
            return document.getElementById( id ).parentNode.tagName.toLowerCase() === parentNode;
        },
        operationEntity(feater) {
            let _this = this;
            let { viewer } = this;

            if ( feater !== undefined ) {
                if (feater._dataTypeName === stateQuantity) {

                    let [ updateLabel ] = viewer.entities._entities._array.filter(label => label._id == feater._id); //获取当前更新的实体
                    var image = !feater.cv ? 'open' : 'close';

                    updateLabel.billboard.image = require('../../../../assets/VM/' + image + '.png'); //修改告警图片
                    updateLabel._cv = !feater.cv; //修改cv值

                    return;
                }
                if (feater._messageType === 'videos' && _this.openVideoLinkage) {

                    _this.$store.commit('closeVideoLoop');   //关闭视屏监控轮播模式
                    _this.$emit('replaceVideoUrl', feater._moId);
                }
            }
        },
        setGIS() {
            let gis = document.getElementById( domId );
            setTimeout(()=>{
                if( !gis ) return;

                gis.style.display = "block";

                document.body.removeChild(gis);
                // document.getElementById( containerId ).appendChild(gis);
                let parentElement = document.getElementById( containerId );
                let firstChild = parentElement.firstChild;

                parentElement.insertBefore( gis,firstChild );
            },100)

            // 加载视角
            this.setViewAngle();
        },
        // 开始相机位置刷新
        startCameraPositionRefresh() {
            this.refreshCameraPosition.enable = true;
            this.cameraPositionRefresh();
        },
        // 停止相机位置刷新
        stopCameraPositionRefresh() {
            this.refreshCameraPosition.enable = false;
            console.log('停止相机位置刷新',this.refreshCameraPosition.enable)
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

                        _this.addLabel(_this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed); //调用添加label

                        _this.$emit("refreshCameraPosition", cameraPosition);
                    }
                } catch (error) {
                    console.warn('error'+ error);
                }

                _this.cameraPositionRefresh();
            }, _this.refreshCameraPosition.interval);
        },
    },
    beforeDestroy() {
        this.viewer.selectedEntityChanged.removeEventListener( this.operationEntity );
    },
})
