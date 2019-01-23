import Vue from 'vue'

export default ( containerId,viewer,domId ) => ({
    props:{
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
        undergroundMode: {
            type: Object,
            default: function () {
                return {
                    enable: true,
                    distance: -8
                };
            }
        },
    },
    computed: {
    },
    mounted() {
        this.createHtml()
            .then( ()=> {
                this.init();
                this.getLayer();
                this.initProps();
            })
            .catch( () => this.initUpdate( Vue.prototype[ viewer ],Vue.prototype[ viewer ].scene  ) )

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
                            this.setViewAngle(this.scene,this.cameraPosition);
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

            if ( _this.undergroundMode.enable ) {
                // 设置是否开启地下场景
                _this.scene.undergroundMode = _this.undergroundMode.enable;
                // 设置相机最小缩放距离,距离地表-8米
                _this.scene.screenSpaceCameraController.minimumZoomDistance =
                    _this.undergroundMode.distance;
                var widget = _this.viewer.cesiumWidget;
            }
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
    }
})
