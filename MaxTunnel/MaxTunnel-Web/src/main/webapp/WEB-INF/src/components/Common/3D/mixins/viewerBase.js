import Vue from 'vue'
import {
    flyManagerMinix
} from "./flyManager";
import {
    doSqlQuery,
    processFailed,
} from "../../../../scripts/commonFun.js";
import {
    TunnelService
} from '../../../../services/tunnelService'


const stateQuantity = '状态量输入';

export default (containerId, viewer, domId, route) => ({
    mixins: [flyManagerMinix],
    props: {
        infoBox: {
            type: Boolean,
            default: true
        },
        navigation: {
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
                    enable: true,
                    interval: 1000
                };
            }
        },
    },
    directives: {
        cancellation: {
            unbind(els) {

                let elementChild = [].slice.call(els.children);
                let [gis] = elementChild.filter(el => el.id === domId);

                gis.style.display = "none";

                els.removeChild(gis);
                document.body.appendChild(gis);
            }
        }
    },
    watch: {
        'prePosition': {
            handler({
                longitude,
                latitude,
                height
            }) {

                TunnelService.getStorePosition({
                        longitude,
                        latitude,
                        height
                    })
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
    computed: {
        viewer() {
            return Vue.prototype[viewer];
        },
        scene() {
            return this.viewer.scene;
        },
    },
    data() {
        return {
            prePosition: null,
            boxEntity: null,
            layers: null
        }
    },
    mounted() {
        this.createHtml()
            .then(() => {
                this.getLayer();
                this.init();
                this.initProps();
                this.loadCesiumMan();
                if (this.pointLight.isOpen) this.getPointLinght();
            })
            .catch(() => {
                this.initUpdate(Vue.prototype[viewer], Vue.prototype[viewer].scene)
            });
        this.flyManager(route);
        this.paramUpdate();

    },
    methods: {

        createHtml() {
            let _this = this;

            return new Promise((resolve, reject) => {

                if (!Vue.prototype[viewer]) {

                    $('#' + containerId)
                        .prepend("<div id='" + domId + "' style='position: relative;height: 100%;width: 100%'></div>")
                        .end();

                    Vue.prototype[viewer] = new Cesium.Viewer(domId, {
                        navigation: _this.navigation,
                        infoBox: _this.infoBox,
                        // terrainProvider:Cesium.createWorldTerrain()
                    });

                    resolve();
                } else {
                    _this.setGIS();
                    reject();
                }
            });
        },
        getLayer() {
            let _this = this;

            if (Cesium.defined(this.scene)) {

                try {
                    //打开所发布三维服务下的所有图层
                    let promise = this.scene.open(this.SuperMapConfig.BIM_SCP);

                    if (promise && viewer == '$viewer') _this.$store.commit("loadUnits"); // 俯视图加载单位图片

                    Cesium.when(
                        promise,
                        function (layer) {
                            //设置BIM图层不可选择
                            // Vue.prototype[viewer].scene.layers.find('Dataset_3Dmax@datasources').style3D.fillForeColor.alpha = 0;
                            // 112.488253735,37.714005145,-2.45,102,-10,0
                            _this.layers = layer;
                            layer.forEach(
                                curBIM => {
                                    curBIM._selectEnabled = false;
                                    curBIM._ignoreNormal = false;
                                    curBIM._clearMemoryImmediately = false;
                                }
                            );
                            //设置相机位置、视角，便于观察场景
                            _this.setViewAngle();

                            // let handler = new Cesium.ScreenSpaceEventHandler(
                            //     _this.scene.canvas
                            // );

                            // var boxEntity;
                            // handler.setInputAction(function(event) {

                            //     var cartesian = Cesium.Cartesian3.fromDegrees(112.48896541,37.71379887,-2.45);
                            // var cartesian = _this.scene.pickPosition(event.position);
                            // if(!_this.boxEntity){
                            //     _this.ClipBoxBIM(cartesian);
                            // }else {
                            //     _this.MoveClipBIM(cartesian)
                            // }

                            //     var boxOption = {
                            //         dimensions : new Cesium.Cartesian3(parseFloat(_this.ClipBIM.boxProp.length),parseFloat(_this.ClipBIM.boxProp.widht),parseFloat(_this.ClipBIM.boxProp.height)),
                            //         position : cartesian,
                            //         clipMode : 'clip_behind_all_plane',
                            //         heading : parseFloat(_this.ClipBIM.rotate)
                            //     };
                            //     var hpr = new Cesium.HeadingPitchRoll(parseFloat(_this.ClipBIM.rotate), 0, 0);
                            //     var orientation = Cesium.Transforms.headingPitchRollQuaternion(cartesian, hpr);
                            //     boxEntity = _this.viewer.entities.add({
                            //         box : {
                            //             dimensions : new Cesium.Cartesian3(parseFloat(_this.ClipBIM.boxProp.length),parseFloat(_this.ClipBIM.boxProp.widht),parseFloat(_this.ClipBIM.boxProp.height)),
                            //             material : Cesium.Color.fromRandom({alpha : 0.4})
                            //         },
                            //         position : cartesian,
                            //         orientation : orientation
                            //     });

                            //     for (let i=0;i<layer.length ; i++) {
                            //         layer[i].setCustomClipBox(boxOption);
                            //     }
                            // }, Cesium.ScreenSpaceEventType.LEFT_CLICK);


                            // handler.setInputAction(function(movement) {
                            //     if(!boxEntity)return;
                            //     var cartesian = _this.scene.pickPosition(movement.startPosition);
                            //     boxEntity.position = cartesian;
                            //     handler.setInputAction(function(evt) {

                            //         var newDim = boxEntity.box.dimensions.getValue();
                            //         var position = boxEntity.position.getValue(0);
                            //         var heading = parseFloat(_this.ClipBIM.rotate)
                            //         var boxOptions = {
                            //             dimensions : newDim,
                            //             position : position,
                            //             clipMo   e : 'clip_behind_all_plane',
                            //             heading : heading
                            //         }

                            //         for (let i=0;i<layer.length ; i++) {
                            //             layer[i].setCustomClipBox(boxOptions);
                            //         }

                            //         handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
                            //     }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

                            // }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);

                            // var handlerPolygon = new Cesium.DrawHandler(_this.viewer,Cesium.DrawMode.Polygon, 0);
                            // console.log('array')
                            // handlerPolygon.drawEvt.addEventListener(function(result){

                            //     var array = [].concat(result.object.positions);


                            //     var positions = [];
                            //     for(var i = 0, len = array.length; i < len; i ++){
                            //         var cartographic = Cesium.Cartographic.fromCartesian(array[i]);
                            //         var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                            //         var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                            //         var h=cartographic.height;
                            //         if(positions.indexOf(longitude)==-1&&positions.indexOf(latitude)==-1){
                            //             positions.push(longitude);
                            //             positions.push(latitude);
                            //             positions.push(h);
                            //         }
                            //     }
                            //     var dep = 500;
                            //     _this.viewer.scene.globe.removeAllExcavationRegion();
                            //     _this.viewer.scene.globe.addExcavationRegion({
                            //         name : 'ggg' ,
                            //         position : positions,
                            //         height : dep,
                            //         transparent : true
                            //     });
                            //     handlerPolygon.polygon.show = false;
                            //     handlerPolygon.polyline.show = false;
                            //     handlerPolygon.deactivate();
                            //     handlerPolygon.activate();
                            // });

                            // handlerPolygon.activate();  



                        },
                        function (e) {
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
        operateBoxBIM(cartesian) {
            !this.boxEntity ? this.ClipBoxBIM(cartesian) : this.MoveClipBIM(cartesian);
        },
        ClipBoxBIM({
            x,
            y,
            z
        }) {
            let _this = this;
            var cartesian = Cesium.Cartesian3.fromDegrees(parseFloat(x), parseFloat(y), parseFloat(z));

            var boxOption = {
                dimensions: new Cesium.Cartesian3(parseFloat(_this.ClipBIM.boxProp.length), parseFloat(_this.ClipBIM.boxProp.widht), parseFloat(_this.ClipBIM.boxProp.height)),
                position: cartesian,
                clipMode: 'clip_behind_all_plane',
                heading: parseFloat(_this.ClipBIM.rotate)
            };
            var hpr = new Cesium.HeadingPitchRoll(parseFloat(_this.ClipBIM.rotate), 0, 0);
            var orientation = Cesium.Transforms.headingPitchRollQuaternion(cartesian, hpr);
            _this.boxEntity = _this.viewer.entities.add({
                box: {
                    dimensions: new Cesium.Cartesian3(parseFloat(_this.ClipBIM.boxProp.length), parseFloat(_this.ClipBIM.boxProp.widht), parseFloat(_this.ClipBIM.boxProp.height)),
                    material: Cesium.Color.fromRandom({
                        alpha: 0.1
                    })
                },
                position: cartesian,
                orientation: orientation
            });

            this.setAllLayersClipOptions(boxOption);
        },
        MoveClipBIM({
            x,
            y,
            z
        }) {

            if (!this.boxEntity) return;

            var cartesian = Cesium.Cartesian3.fromDegrees(parseFloat(x), parseFloat(y), parseFloat(z));
            var newDim = this.boxEntity.box.dimensions.getValue();
            var heading = parseFloat(this.ClipBIM.rotate)
            var boxOptions = {
                dimensions: newDim,
                position: cartesian,
                clipMode: 'clip_behind_all_plane',
                heading: heading
            }
            this.setAllLayersClipOptions(boxOptions);
        },
        setAllLayersClipOptions(boxOption) {

            if (!this.layers) return;

            for (let i = 0; i < this.layers.length; i++) {
                this.layers[i].setCustomClipBox(boxOption);
            }
        },
        initProps() {
            let _this = this;
            // 去除login/版本信息
            _this.viewer._cesiumWidget._creditContainer.style.display = "none";

            if (_this.undergroundMode.enable) {
                // 设置是否开启地下场景
                _this.scene.undergroundMode = _this.undergroundMode.enable;
                // 设置相机最小缩放距离,距离地表-8米
                _this.scene.screenSpaceCameraController.minimumZoomDistance =
                    _this.undergroundMode.distance;
                var widget = _this.viewer.cesiumWidget;
            }

        },
        paramUpdate() {
            let _this = this;

            _this.viewer.selectedEntityChanged.addEventListener(this.operationEntity);

        },
        setViewAngle(cameraPosition = this.cameraPosition) {
            let {
                scene
            } = this;

            if (Cesium.defined(scene)) {
                scene.camera.setView({
                    destination: new Cesium.Cartesian3.fromDegrees(
                        parseFloat(cameraPosition.longitude), 
                        parseFloat(cameraPosition.latitude), 
                        parseFloat(cameraPosition.height)
                    ),
                    orientation: {
                        heading: parseFloat(cameraPosition.heading),
                        pitch: parseFloat(cameraPosition.pitch),
                        roll: parseFloat(cameraPosition.roll)
                    }
                });
            }
        },
        flyToAngle(cameraPosition = this.cameraPosition) {
            let {
                scene
            } = this;

            if (Cesium.defined(scene)) {
                scene.camera.flyTo({
                    destination: new Cesium.Cartesian3.fromDegrees(
                        parseFloat(cameraPosition.longitude), 
                        parseFloat(cameraPosition.latitude), 
                        parseFloat(cameraPosition.height)
                    ),
                    orientation: {
                        heading: parseFloat(cameraPosition.heading),
                        pitch: parseFloat(cameraPosition.pitch),
                        roll: parseFloat(cameraPosition.roll)
                    }
                });
            }
        },
        compare(id, parentNode) {
            return document.getElementById(id).parentNode.tagName.toLowerCase() === parentNode;
        },
        operate() {
            let startPt = Cesium.Cartesian3.fromDegrees(data_geo[startName][0], data_geo[startName][1], 0);
            let endPt = Cesium.Cartesian3.fromDegrees(data_geo[destinationName][0], data_geo[destinationName][1], 0);
            console.log(startPt, endPt)
            let curLinePointsArr = generateCurve(startPt, endPt);


            viewer.entities.add({ // 背景线
                polyline: {
                    width: 3,
                    positions: curLinePointsArr,
                    material: new Cesium.PolylineDashMaterialProperty({
                        color: new Cesium.Color(255 / 255, 249 / 255, 0, 0.5)
                    })
                }
            });

            viewer.entities.add({ // 尾迹线

                polyline: {
                    width: 5,
                    positions: curLinePointsArr,
                    material: new Cesium.PolylineTrailMaterialProperty({ // 尾迹线材质
                        color: Cesium.Color.fromCssColorString("rgba(118, 233, 241, 1.0)"),
                        trailLength: 0.2,
                        period: 5.0
                    })
                }
            });
        },
        generateCurve(startPoint, endPoint) {
            let addPointCartesian = new Cesium.Cartesian3();
            Cesium.Cartesian3.add(startPoint, endPoint, addPointCartesian);
            let midPointCartesian = new Cesium.Cartesian3();
            Cesium.Cartesian3.divideByScalar(addPointCartesian, 2, midPointCartesian);
            let midPointCartographic = Cesium.Cartographic.fromCartesian(midPointCartesian);
            midPointCartographic.height = Cesium.Cartesian3.distance(startPoint, endPoint) / 10;
            let midPoint = new Cesium.Cartesian3();
            Cesium.Ellipsoid.WGS84.cartographicToCartesian(midPointCartographic, midPoint);
            let spline = new Cesium.CatmullRomSpline({
                times: [0.0, 0.5, 1.0],
                points: [startPoint, midPoint, endPoint]
            });
            let curvePointsArr = [];
            for (let i = 0, len = 300; i < len; i++) {
                curvePointsArr.push(spline.evaluate(i / len));
            }
            return curvePointsArr;
        },
        operationEntity(feater) {
            let _this = this;
            let {
                viewer
            } = this;

            if (feater !== undefined) {
                if (feater._dataTypeName === stateQuantity) {

                    let [updateLabel] = viewer.entities._entities._array.filter(label => label._id == feater._id); //获取当前更新的实体
                    var image = !feater.cv ? 'open' : 'close';

                    updateLabel.billboard.image = require('../../../../assets/VM/' + image + '.png'); //修改告警图片
                    updateLabel._cv = !feater.cv; //修改cv值

                    return;
                }
                if (feater._messageType === 'videos' && _this.openVideoLinkage) {

                    _this.$store.commit('closeVideoLoop'); //关闭视屏监控轮播模式
                    _this.$emit('replaceVideoUrl', feater._moId);
                }
            }
        },
        setGIS() {
            let gis = document.getElementById(domId);
            setTimeout(() => {
                if (!gis) return;

                gis.style.display = "block";

                document.body.removeChild(gis);
                // document.getElementById( containerId ).appendChild(gis);
                let parentElement = document.getElementById(containerId);
                let firstChild = parentElement.firstChild;

                parentElement.insertBefore(gis, firstChild);
            }, 100)

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

                        _this.addLabel(_this.SuperMapConfig.IP + _this.SuperMapConfig.BIM_DATA, doSqlQuery, processFailed); //调用添加label
                        _this.showOrHideRelevantUnit(_this.prePosition);
                        _this.$emit("refreshCameraPosition", cameraPosition);
                    }
                } catch (error) {
                    console.warn('error' + error);
                }

                _this.cameraPositionRefresh();
            }, _this.refreshCameraPosition.interval);
        },
        getPointLinght() {
            this.scene.sun.show = false;
            this.scene.globe.enableLighting = false;

            TunnelService.getPointLinghtData().then(sources => {
                if (typeof sources != 'object' || !sources.length) return;

                sources.forEach(linght => this.addPointLinght(linght))
            })
        },
        addPointLinght({
            lat,
            lon,
            height
        }) {
            if (Cesium.defined(this.scene)) {
                let {
                    cutoffDistance,
                    decay,
                    intensity
                } = this.pointLinght;
                //经纬度转换世界坐标
                var ellipsoid = this.viewer.scene.globe.ellipsoid;
                var cartographic = Cesium.Cartographic.fromDegrees(lon, lat, height);
                var cartesian3 = ellipsoid.cartographicToCartesian(cartographic);

                this.scene.lightSource._ambientLightColor = new Cesium.Color(0.4, 0.4, 0.4, 0.4);

                var position = new Cesium.Cartesian3(cartesian3.x, cartesian3.y, cartesian3.z);
                var posDeg = Cesium.Cartographic.fromCartesian(position);
                var pointPosition = Cesium.Cartesian3.fromRadians(posDeg.longitude, posDeg.latitude, posDeg.height);

                this.viewer.entities.add(new Cesium.Entity({
                    point: new Cesium.PointGraphics({
                        color: new Cesium.Color(1, 1, 1),
                        pixelSize: 4,
                        outlineColor: new Cesium.Color(1, 1, 1),
                        scaleByDistance: new Cesium.NearFarScalar(0, 1, 40000, 0)
                    }),
                    position: pointPosition
                }));
                var options = {
                    color: new Cesium.Color(1, 1, 1, 1),
                    cutoffDistance,
                    decay,
                    intensity
                };

                let pointLight = new Cesium.PointLight(position, options);

                this.scene.addLightSource(pointLight);
            }

        },
        showOrHideRelevantUnit({
            height
        }) {

            let relevantUnits = this.viewer.entities._entities._array.filter(entity => entity.messageType == "relevantUnit");

            this.changeEntityUnitState(relevantUnits, height <= this.VMEntityConfig.criticalHeight, true) //隐藏实体
            this.changeEntityUnitState(relevantUnits, height > this.VMEntityConfig.criticalHeight, false) //显示实体
        },
        changeEntityUnitState(units, judge, bool) {
            if (judge && units.some(unit => unit._show == bool)) {
                units.forEach(unit => unit._show = !bool)
            }
        }
    },
    beforeDestroy() {
        this.viewer.selectedEntityChanged.removeEventListener(this.operationEntity);
        this.stopFly();
    },
})