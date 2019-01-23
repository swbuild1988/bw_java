let {
    flyFilePathes
} = require('../../../../../static/VM/js/VMWebConfig')

export const flyManagerMinix = {
    data() {
        return {

            flyManagerAttr: {
                flyManager: null,
            },
            // 飞行文件
            // flyFilePath: this.ServerConfig + "/VM/font/flytest.fpf",

            // flyFilePathes: [{
            //     id: 1,
            //     name: '飞行路线_1',
            //     path: '/VM/font/flytest.fpf'
            // }, {
            //     id: 2,
            //     name: '飞行路线_2',
            //     path: '/VM/font/flytest2.fpf'
            // }]
        }
    },
    computed: {
        flyFilePathes() {
            return flyFilePathes;
        }
    },
    methods: {
        // 飞行管理
        flyManager(id) {

            if (!global.Cesium || id == null) return;

            let {
                scene,
                viewer,
                flyManagerAttr
            } = this;
            let routes = new Cesium.RouteCollection();
            //添加fpf飞行文件，fpf由SuperMap iDesktop生成
            // routes.fromFile(this.flyFilePath);
            let curRoute = this.flyFilePathes.find(route => {
                return route.id == id
            })
            // console.log('curRoute',curRoute)
            routes.fromFile(this.ServerConfig + curRoute.path);

            //初始化飞行管理
            flyManagerAttr.flyManager = new Cesium.FlyManager({
                scene: scene,
                routes: routes
            });

            if( !!curRoute.playRate ){
                console.log(typeof parseFloat(curRoute.playRate),parseFloat(curRoute.playRate))
                flyManagerAttr.flyManager.playRate = parseFloat( curRoute.playRate );
            }
            flyManagerAttr.flyManager.readyPromise.then(function() {
               
                if ( curRoute.isFlyLoop ) {
                    let currentRoute = flyManagerAttr.flyManager.currentRoute
                    currentRoute.isFlyLoop = JSON.parse( curRoute.isFlyLoop );

                }
            })

            //注册站点到达事件
            flyManagerAttr.flyManager.stopArrived.addEventListener(
                routeStop => {
                    let stopName = routeStop.stopName;
                    routeStop.waitTime = curRoute.waitTime;
                    if ( !curRoute.isFlyLoop ) {
                        flyManagerAttr.flyManager.currentStopIndex = routeStop.index
                    }
                }
            );
        },
        //开始飞行
        playFly() {
            let {
                flyManagerAttr
            } = this;
            
            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.play();
            }
        },
        // 暂停飞行
        pauseFly() {
            let {
                flyManagerAttr
            } = this;

            if (flyManagerAttr.flyManager) {
                flyManagerAttr.flyManager.pause();
            }
        },
        //停止飞行
        stopFly() {
            let {
                flyManagerAttr
            } = this;

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
        // 获取飞行路径
        getRoutes() {
            return this.flyFilePathes
        },
        getStopsList(id) {
            let stops = []
            if (id == 0) {
                // 获取所有飞行路径的站点
                for (let i = 1; i <= this.flyFilePathes.length; i++) {
                    this.flyManager(i)
                    let flyManager = this.flyManagerAttr.flyManager
                    flyManager.readyPromise.then(() => {
                        let route = flyManager.routes.routes
                        route[0].stopCollection.forEach((stop, index) => {
                            if (stop.index != 0 || i != 1) {
                                let temp1 = {}
                                temp1.stopIndex = i + ',' + stop.index
                                temp1.stopName = '飞行路线' + i + ' - ' +
                                    stop.stopName.replace('stop', '站点')
                                stops.push(temp1)
                            }
                        })
                    })
                }
                this.flyManager(1)
            } else {
                // 切换飞行路径
                this.flyManager(id)
                let flyManager = this.flyManagerAttr.flyManager
                // 获取新的站点
                flyManager.readyPromise.then(() => {
                    let route = flyManager.routes.routes
                    route[0].stopCollection.forEach(stop => {
                        if (stop.index != 0) {
                            let temp = {}
                            temp.stopIndex = stop.index
                            temp.stopName = stop.stopName.replace('stop', '站点')
                            stops.push(temp)
                        }
                    })
                })
            }
            return stops
        },
        // 切换站点
        stopChanged(stopInfo) {
            let flyManager = this.flyManagerAttr.flyManager
            flyManager.readyPromise.then(() => {
                // 飞行全部路径
                if (stopInfo.indexOf(',') > -1) {
                    let curRoute = this.flyFilePathes.find(route => {
                        return route.name == flyManager.currentRoute.routeName
                    })
                    let routeId = stopInfo.split(',')[0]
                    let stopIndex = stopInfo.split(',')[1]
                    // 判断选择的站点是否属于当前飞行路径
                    if (curRoute.id == routeId) {
                        let route = flyManager.currentRoute
                        let stop = route.get(stopIndex)
                        flyManager.currentStopIndex = stopIndex
                        flyManager.viewToStop(stop)
                    } else {
                        this.flyManager(routeId)
                        let flyManager = this.flyManagerAttr.flyManager
                        flyManager.readyPromise.then(() => {
                            let stop = flyManager.currentRoute.get(stopIndex)
                            flyManager.currentStopIndex = stopIndex
                            flyManager.viewToStop(stop)
                        })
                    }
                } else {
                    // 飞行单一路径
                    let route = flyManager.currentRoute
                    let stop = route.get(stopInfo)
                    flyManager.currentStopIndex = stopInfo
                    flyManager.viewToStop(stop)
                }
            })
        },
        // 获取飞行状态
        getFlyStatus(routeId) {
            let flyManager = this.flyManagerAttr.flyManager
            let curStop = flyManager.currentStopIndex
            // 飞行全部路径时结束上一条直接飞行下一条
            if (routeId == 0) {
                let curRoute = this.flyFilePathes.find(route => {
                    return route.name == flyManager.currentRoute.routeName
                })
                if (curStop == flyManager.currentRoute.stopCollection.length - 1) {
                    if (curRoute.id < this.flyFilePathes.length) {
                        this.stopFly()
                        this.flyManager(curRoute.id + 1)
                        let flyManager = this.flyManagerAttr.flyManager
                        let _this = this
                        flyManager.readyPromise.then(() => {
                            _this.playFly()
                        })
                    } else {
                        return true
                    }
                }
            } else {
                if (curStop == flyManager.currentRoute.stopCollection.length - 1) {
                    return true
                }
            }

        }
    },
}