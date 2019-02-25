import Vue from 'vue'
import { flyManagerMinix } from "./flyManager";

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
    directives:{
        cancellation:{
            unbind(els){

                let elementChild = [].slice.call( els.children );
                let [ gis ] = elementChild.filter(el => el.id === domId);
                // console.log('elementChild',elementChild)
                // console.log('gis',gis)
                gis.style.display = "none";

                els.removeChild(gis);
                document.body.appendChild(gis);
            }
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
                            this.setViewAngle();
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
                document.getElementById( containerId ).appendChild(gis);
            },100)
                // console.log('getGis',gis)
                //
                // console.log('document.getElementById( containerId )',document.getElementById( containerId ))
            // 加载视角
            this.setViewAngle();
        },
    },
    beforeDestroy() {
        this.viewer.selectedEntityChanged.removeEventListener( this.operationEntity );
    },
})
