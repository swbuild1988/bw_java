
export const flyManagerMinix = {
    data(){
        return{

            flyManagerAttr:{
                flyManager:null,
            },
            // 飞行文件
            flyFilePath: this.ServerConfig + "/VM/font/flytest.fpf"
        }
    },
    methods:{
        // 飞行管理
        flyManager() {
            if (!global.Cesium) return;

            let {scene, viewer, flyManagerAttr} = this;
            let routes = new Cesium.RouteCollection();
            //添加fpf飞行文件，fpf由SuperMap iDesktop生成
            routes.fromFile(this.flyFilePath);

            //初始化飞行管理
            flyManagerAttr.flyManager = new Cesium.FlyManager({
                scene: scene,
                routes: routes
            });

            //注册站点到达事件
            flyManagerAttr.flyManager.stopArrived.addEventListener(
                routeStop => {
                    let stopName = routeStop.stopName;
                    routeStop.waitTime = 1;
                }
            );
        },
        //开始飞行
        playFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.play();
            }
        },
        // 暂停飞行
        pauseFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.pause();
            }
        },
        //停止飞行
        stopFly() {
            let {flyManagerAttr} = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.stop();
            }
        },
        // 加速飞行
        speedUp() {
            let flyManager = this.flyManagerAttr.flyManager;
            if (flyManager.playRate < 1) {
                flyManager.playRate += 0.1;
            } else {
                if (flyManager.playRate < 10) flyManager.playRate += 1;
            }
        },
        // 减速飞行
        speedDown() {
            let flyManager = this.flyManagerAttr.flyManager;
            if (flyManager.playRate > 1) {
                flyManager.playRate -= 1;
            } else {
                if (flyManager.playRate > 0.5) flyManager.playRate -= 0.1;
            }
        },
    },
}