<script>
import Cesium from "Cesium";
import aa from '../../static/serverconfig.json'
import Vue from 'vue';
const _div = document.createElement("div");
_div.id = "newID";
_div.style.display = "none";
document.body.appendChild(_div);
const Viewer = new Cesium.Viewer("newID", {
    navigation: this.navigation,
    infoBox: this.infoBox
});



// 设置是否开启地下场景
Viewer.scene.undergroundMode = true;
// 设置相机最小缩放距离,距离地表-8米
Viewer.scene.screenSpaceCameraController.minimumZoomDistance = -8;
// console.log("加载地下场景完毕");

setTimeout(function(){
// 加载地图
let provider_mec = new Cesium.SuperMapImageryProvider({
    url: Vue.prototype.SuperMapConfig.IMG_MAP //墨卡托投影地图服务
    // url:
    //     "http://192.168.0.41:8090/iserver/services/map-ugcv5-taiyuanmap/rest/maps/taiyuan_map"
});
Viewer.imageryLayers.addImageryProvider(provider_mec);
console.log("加载地图完毕");

var widget = Viewer.cesiumWidget;
try {
    //打开所发布三维服务下的所有图层
    var promise = Viewer.scene.open(Vue.prototype.SuperMapConfig.BIM_SCP);
    // var promise = Viewer.scene.open(
    //     "http://192.168.0.41:8090/iserver/services/3D-taiyuan_tunnel1/rest/realspace"
    // );
    console.log("打开promise");

    Cesium.when(
        promise,
        function(layer) {
            //设置BIM图层不可选择
            layer.forEach(curBIM => (curBIM._selectEnabled = false));
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
console.log("加载所有图层完毕");
},2000)
const ID = "newID";
export default {
    Viewer,
    ID
};
</script>

