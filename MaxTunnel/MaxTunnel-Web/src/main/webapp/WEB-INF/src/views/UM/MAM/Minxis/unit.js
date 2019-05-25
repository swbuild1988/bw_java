import {
    AreaService
} from '@/services/areaService'
import {
    flyToMyLocation
} from '../../../../scripts/commonFun'
import {
    TunnelService
} from "@/services/tunnelService";
import Vue from 'vue'

export const commonFlyFn = {
    data() {
        return {
            storeProp: {
                itemLen: 12,
                dataList: [],
                selectObj: {
                    selectId: ""
                }
            },
            areaList: [], //管廊对应区段数据
            queryCondition: {
                tunnelId: null, //监测仓ID
                storeId: null, //监测区段ID
                areaId: null,
            },
            tunnels: [],
            curTunnel: {
                storeNum: 3,
                constructionUnit: "中建一局",
                operationUnit: "波汇科技",
                areaNum: 10
            },
            curName: '',
            note: {
                areaName: null,
                storeName: null
            }
        }
    },
    methods: {
        //获取数据
        fentchData() {
            //获取管廊列表
            let _this = this;
            _this.queryCondition.tunnelId = parseInt(_this.$route.params.id);
            TunnelService.getTunnels().then(
                result => {
                    _this.tunnels = result;
                    _this.tunnels.forEach(a => {
                        if (a.id == _this.queryCondition.tunnelId) {
                            _this.curName = a.name;
                        }
                    });
                },
                error => {
                    console.log(error);
                }
            );
            //获取管廊详细信息
            TunnelService.getTunnelDetailByTunnelId(
                _this.queryCondition.tunnelId
            ).then(
                result => {
                    _this.curTunnel.areaNum = result.areaCount;
                    _this.curTunnel.storeNum = result.storeCount;
                    _this.curTunnel.constructionUnit = result.construct;
                    _this.curTunnel.operationUnit = result.operation;
                },
                error => {
                    console.log(error);
                }
            );
            //获取管仓列表
            TunnelService.getStoresByTunnelId(
                _this.queryCondition.tunnelId
            ).then(
                result => {
                    _this.storeProp.dataList = [{
                        id: 0,
                        name: "全部"
                    }];
                    result.forEach((a, index) => {
                        let temp = {};
                        temp.id = a.id;
                        temp.name = a.name;
                        _this.storeProp.dataList.push(temp);
                    });
                    _this.storeProp.selectObj.selectId =
                        _this.storeProp.dataList[0].id;
                },
                error => {
                    console.log(error);
                }
            );
            //获取区域
            TunnelService.getTunnelArea(_this.queryCondition.tunnelId).then(
                result => {
                    if (result) {
                        _this.areaList = [{
                            id: 0,
                            name: "全部"
                        }];
                        result.forEach(a => {
                            var temp = {};
                            temp.name = a.name;
                            temp.id = a.id;
                            _this.areaList.push(temp);
                        });
                        _this.queryCondition.areaId = _this.areaList[0].id;
                    }
                }
            );
        },
        //变更区段
        updateArea() {

            let {
                areaId,
                storeId
            } = this.queryCondition;
            //storeId =0 || null 
            storeId ? TunnelService.getSectionByAreaIdStoreId(storeId, areaId).then(result => this.intercept(result)) :
                AreaService.getAreaInfo(areaId).then(areaInfo => this.intercept(areaInfo))

            this.getMonitorData();
            this.changePositionNote();
        },
        //变更舱
        updateStores(storeId) {
            let {
                queryCondition,
                storeProp,
                areaList
            } = this;

            if (!areaList.length) return;
            queryCondition.areaId = queryCondition.areaId || areaList[1].id;

            queryCondition.storeId = storeId || storeProp.dataList[1].id;

            TunnelService.getSectionByAreaIdStoreId(storeId, queryCondition.areaId).then(result => this.intercept(result))
            this.getMonitorData();
            this.changePositionNote();
        },
        //用于判断返回结果不为undefined
        intercept(info) {
            if (!info || !info.startPoint || !info.endPoint) return
            this.perspective(info)
        },
        perspective({
            startPoint,
            endPoint
        }) {
            if (!startPoint || !endPoint) return

            let [startLon, startLan, startHeight] = startPoint.split(',');
            let [endLon, endLan] = endPoint.split(',');

            let position = {
                longitude: (Number(startLon) + Number(endLon)) / 2,
                latitude: (Number(startLan) + Number(endLan)) / 2,
                height: Number(startHeight)
            }

            flyToMyLocation({
                scene: Vue.prototype['$simpleViewer'],
                position
            })

        },
        changePositionNote() {
            let curArea = this.areaList.find(area => {
                return area.id === this.queryCondition.areaId
            })
            let curStore = this.storeProp.dataList.find(store => {
                return store.id === this.queryCondition.storeId
            })
            this.note.storeName = curStore.name
            this.note.areaName = curArea.name
            console.log(this.note)
        }
    },
}