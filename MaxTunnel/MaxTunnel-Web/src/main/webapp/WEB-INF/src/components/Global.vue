<script>
import Cesium from "Cesium";
import Vue from 'vue';

const _div = document.createElement("div");
_div.id = "newID";
_div.style.display = "none";
document.body.appendChild(_div);
const Viewer = new Cesium.Viewer("newID", {
    navigation: false,
    infoBox: false
});
console.log("create newID", _div);


const simpleDiv = document.createElement("div");
simpleDiv.id = "simple3DBox";
simpleDiv.style.display = "none";
document.body.appendChild(simpleDiv);
const simpleViewer = new Cesium.Viewer("simple3DBox", {
    navigation: false,
    infoBox: false
});
console.log("create simple3DBox", simpleDiv);

// 设置是否开启地下场景
Viewer.scene.undergroundMode = true;
simpleViewer.scene.undergroundMode = true;
// 设置相机最小缩放距离,距离地表-8米
Viewer.scene.screenSpaceCameraController.minimumZoomDistance = -8;
simpleViewer.scene.screenSpaceCameraController.minimumZoomDistance = -8;
console.log("加载地下场景完毕");

Vue.prototype.GLOBAL = {
    simpleViewer: null,
    Viewer: null
}
Vue.prototype.GLOBAL.simpleViewer = simpleViewer
Vue.prototype.GLOBAL.Viewer = Viewer

setTimeout(function () {
    var widget = Viewer.cesiumWidget;
    var widget1 = simpleViewer.cesiumWidget;
    try {

        //开启地图服务
        let provider_mec = new Cesium.SuperMapImageryProvider({
            url: Vue.prototype.SuperMapConfig.IMG_MAP//墨卡托投影地图服务
        });
        Viewer.imageryLayers.addImageryProvider(provider_mec);

        console.log("Viewer 加载地图完毕")

        //打开所发布三维服务下的所有图层
        var promise = Viewer.scene.open(Vue.prototype.SuperMapConfig.BIM_SCP);

        console.log("打开promise");

        Cesium.when(
            promise,
            function (layer) {
                //设置BIM图层不可选择
                layer.forEach(curBIM => {
                    curBIM._selectEnabled = false;
                    curBIM._ignoreNormal = false;
                    curBIM._clearMemoryImmediately = true;
                });
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
        console.log("supermap global init error:", e)
    }

    try {
        //打开所发布三维服务下的所有图层
        var promise1 = simpleViewer.scene.open(Vue.prototype.SuperMapConfig.BIM_SCP);

        console.log("打开simpleviewer promise");

        Cesium.when(
            promise1,
            function (layer) {
                //设置BIM图层不可选择
                layer.forEach(curBIM => {
                    curBIM._selectEnabled = false;
                    curBIM._ignoreNormal = false;
                    curBIM._clearMemoryImmediately = true;
                });
            },
            function (e) {
                if (widget1._showRenderLoopErrors) {
                    var title =
                        "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
                    widget1.showErrorPanel(title, undefined, e);
                }
            }
        );
    } catch (e) {
        if (widget1._groundPrimitives) {
            var title = "渲染时发生错误，已停止渲染。";
            widget1.showErrorPanel(title, undefined, e);
        }
    }
    console.log("加载所有图层完毕");

}, 2000)
export default {
    // Viewer,
    // simpleViewer
};

</script>

