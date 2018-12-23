import axios from 'axios';
import {
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed,
    getEntitySet,
    addBillboard,
    getEntityProperty,
    switchShowEntity
} from "../scripts/commonFun.js";

var SuperMap = {
    _div : null,
    init() {
        this._div = document.createElement("div");
        document.body.appendChild(this._div);
        this._div.id = '3d_viewer'
        var _this = this;
        var scene;
        // 初始化viewer部件
        _this.viewer = new Cesium.Viewer(this._div.id,{
            navigation:_this.navigation,
            infoBox:_this.infoBox

        });

        _this.scene = _this.viewer.scene;

        if (_this.undergroundMode.enable) {
            // 设置是否开启地下场景
            _this.scene.undergroundMode = _this.undergroundMode.enable;
            // 设置相机最小缩放距离,距离地表-8米
            _this.scene.screenSpaceCameraController.minimumZoomDistance =
                _this.undergroundMode.distance;
            var widget = _this.viewer.cesiumWidget;
        }

        if(_this.openImageryProvider){
            //开启地图服务
            let provider_mec = new Cesium.SuperMapImageryProvider({
                url : this.SuperMapConfig.IMG_MAP//墨卡托投影地图服务
            });
            _this.viewer.imageryLayers.addImageryProvider(provider_mec);
        }

        if(_this.searchCamera.openSearch){
            //查询全部相机
            doSqlQuery.call(_this,_this.viewer,'MOTYPEID=7',this.SuperMapConfig.BIM_DATA,addBillboard,processFailed,'videoType','videos',_this.searchCamera.isShow)
        }

        if(_this.unitsPosition.openPosition){
            //开启单位定位
            getEntitySet.call(this,{viewer:_this.viewer,url:'relatedunits',show:_this.unitsPosition.isShow,typeMode:'unitType',messageType:'units'})
        }

        if(_this.personnelPosition.openPosition){
            //开启人员定位
            _this.refreshPersonnelPosition();

        }

        if(_this.defectPosition.openPosition){
            //开启缺陷定位
            getEntitySet.call(this,{
                viewer:_this.viewer,
                url:'defects/list',
                typeMode:'flawType',
                messageType:'flaw',
                show:_this.defectPosition.isShow,
                dataUrl:this.SuperMapConfig.BIM_DATA,
                onQueryComplete:addBillboard,
                processFailed:processFailed
                })
        }

        if(_this.searchCamera.openSearch ||　_this.unitsPosition.openPosition ||　_this.personnelPosition.openPosition ||　_this.defectPosition.openPosition){
            //鼠标经过实体时,触发气泡
            getEntityProperty.call(_this,_this.scene,Cesium,_this.modelProp,'model-content',_this.viewer)
        }
        //设置鼠标左键单击回调事件
        _this.viewer.selectedEntityChanged.addEventListener(feater=>{
            if(feater != undefined){

                if(feater._dataTypeName == stateQuantity){

                    let [ updateLabel ]=_this.viewer.entities._entities._array.filter(label=>label._id == feater._id); //获取当前更新的实体
                    var image = !feater.cv ? 'open' :'close';

                    updateLabel.billboard.image =require('../assets/VM/'+ image +'.png'); //修改告警图片
                    updateLabel._cv = !feater.cv; //修改cv值
                    return;
                }
                if(feater._messageType == 'videos' && _this.openVideoLinkage){

                    _this.$store.commit('closeVideoLoop');   //开启视屏监控轮播模式
                    _this.$emit('replaceVideoUrl',feater._moId);
                }
            }
        })

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
        _this.flyManager();

        //滚轮滑动，获得当前窗口的经纬度，偏移角
        var handler = new Cesium.ScreenSpaceEventHandler(
            _this.scene.canvas
        );
        handler.setInputAction(e => {
            addLabel.call(
                _this,
                _this.scene,
                _this.viewer,
                500,
                doSqlQuery,
                this.SuperMapConfig.BIM_DATA,
                labelSqlCompleted,
                processFailed,
                getSection
            );
        }, Cesium.ScreenSpaceEventType.WHEEL);
        //鼠标左键松开，获得当前窗口的经纬度，偏移角
        handler.setInputAction(e=>{
            addLabel.call(
                _this,
                _this.scene,
                _this.viewer,
                500,
                doSqlQuery,
                this.SuperMapConfig.BIM_DATA,
                labelSqlCompleted,
                processFailed,
                getSection
            );
        },Cesium.ScreenSpaceEventType.LEFT_UP)
    },
    // 开始相机位置刷新
    startCameraPositionRefresh() {
        this.refreshCameraPosition.enable = true;
        this.cameraPositionRefresh();
    },
};
export {
    SuperMap
};