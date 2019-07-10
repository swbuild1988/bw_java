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
import Axios from 'axios';

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
            },
            sectionDetailsData: [],
            // showDetailsModel: false
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
            Axios.all([TunnelService.getStoresByTunnelId(_this.queryCondition.tunnelId),
                TunnelService.getTunnelArea(_this.queryCondition.tunnelId)
            ]).then(res => {
                this.Log.info("获取仓和区", res)
                let stores = res[0];
                let areas = res[1];

                // 更新仓列表
                _this.storeProp.dataList = [{
                    id: 0,
                    name: "全部"
                }];
                stores.forEach((a, index) => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    _this.storeProp.dataList.push(temp);
                });
                _this.storeProp.selectObj.selectId = _this.storeProp.dataList[0].id;

                // 更新区域列表
                _this.areaList = [{
                    id: 0,
                    name: "全部"
                }];
                areas.forEach(a => {
                    var temp = {};
                    temp.name = a.name;
                    temp.id = a.id;
                    _this.areaList.push(temp);
                });

                _this.queryCondition.areaId = _this.areaList[0].id;

                // 更新数据、位置等
                _this.changeAreaOrStore();
            })
        },
        //变更区段
        updateArea() {
            this.changeAreaOrStore();
        },
        //变更舱
        updateStores(storeId) {
            this.queryCondition.storeId = storeId;
            this.changeAreaOrStore();
        },
        changeAreaOrStore() {
            let {
                areaId,
                storeId
            } = this.queryCondition;
            
            this.change3DPosition(areaId, storeId);
            this.getMonitorData();
            this.changePositionNote(areaId, storeId);
        },
        // 改变3D的位置
        change3DPosition(areaId, storeId) {
            if (!areaId) areaId = this.areaList[1].id;
            if (!storeId) storeId = this.storeProp.dataList[1].id

            TunnelService.getSectionByAreaIdStoreId(storeId, areaId).then(result => this.intercept(result))
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
        changePositionNote(areaId, storeId) {
            if (!areaId) areaId = this.areaList[1].id;
            if (!storeId) storeId = this.storeProp.dataList[1].id

            let curArea = this.areaList.find(area => {
                return area.id === areaId
            })
            let curStore = this.storeProp.dataList.find(store => {
                return store.id === storeId
            })

            if (!curArea || !curStore) return

            this.note.storeName = curStore.name
            this.note.areaName = curArea.name

        },
        getSectionDetails(data) {
            // this.sectionDetailsData.splice(0);//清空数组
            // if (data) {
            //     this.showDetailsModel = true;
            //     this.sectionDetailsData = data.moInfo;
            // }else {
            //     this.showDetailsModel = false;
            // }
            this.sectionDetailsData = !data ? null : data.moInfo;
        }
    },
}