import axios from 'axios'
import { get,post } from "../utils/http";

var MonitorDataService = {
    // 根据tunnelId获取该管廊内监测内容数据最大数据
// {tunnelId:"",areaId:"",storeId:""}
    getMaxMonitorData: function(parms) {
        return new Promise((resolve, reject) => {
            post('measobjs/max-cv',parms).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址:measobjs/' +'max-cv')
                }
            })
        })
    },
    getMeasStatusCounts(parms){
        return new Promise((resolve, reject) => {
            
            post('meas-status-counts',parms).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + 'meas-status-counts')
                }
            })
        })
    },
    getMeasFireCounts(parms){
        return new Promise((resolve, reject) => {
        
            post('meas-fire-counts',parms).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + 'meas-fire-counts')
                }
            })
        })
    },
    // 获取监测数据
    getMonitorMaxData: function() {
        return new Promise((resolve, reject) => {
            get('sections/ai/standard-cv').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：sections/ai/standard-cv')
                }
            })
        })
    },
    // 根据storeId获取该管舱区段的监测数据
    getMonitorDataByStoreId: function(storeId) {
        return new Promise((resolve, reject) => {
            get('tunnels/stores/sections/' + storeId + '/ai/cv').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：tunnels/stores/sections/' + storeId + '/ai/cv')
                }
            })
        })
    },
    // 详情面板数据分页查询
    objDetailDatagrid: function(prams) {
        return new Promise((resolve, reject) => {
            post('measobjs/list', prams).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：measobjs/datas')
                }
            })
        })
    },

    // 详情面板数据
    objDetail(params) {
        return new Promise((resolve, reject) => {
            post('measobjs/list', params)
                .then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：measobjs/datas')
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },

    getdataVideos: function(params) {
        return new Promise((resolve, reject) => {
            post('/videos/condition', params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：/videos/condition')
                }
            })
        })
    },
    // 根据ids和dataTypeId获取检测数据
    getDataByIdsAndDataType: function(ids, dataTypeId) {
        return new Promise((resolve, reject) => {
            get('measobjs/' + ids + '/' + dataTypeId + '/cache-cv').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：measobjs/' + ids + '/' + dataTypeId + '/cache-cv')
                }
            })
        })
    },
    // 获取管廊安防撤防设置
    getDefenceSetting(tunnelId){
         return new Promise((resolve, reject) => {
            get('tunnels/'+tunnelId+'/deploy').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：tunnels/'+tunnelId+'/deploy')
                }
            })
            // let data = [
            //     {
            //         areaId: 1,
            //         areaName: "污水舱",
            //         stores: [
            //             {
            //                 storeId: 1,
            //                 storeName: "1区",
            //                 id: 1,
            //                 value: false
            //             },
            //             {
            //                 storeId: 2,
            //                 storeName: "2区",
            //                 id: 2,
            //                 value: false
            //             },
            //             {
            //                 storeId: 3,
            //                 storeName: "3区",
            //                 id: 3,
            //                 value: false
            //             },
            //             {
            //                 storeId: 4,
            //                 storeName: "4区",
            //                 id: 4,
            //                 value: true
            //             }
            //         ]
            //     },
            //     {
            //         areaId: 2,
            //         areaName: "综合舱",
            //         stores: [
            //             {
            //                 storeId: 1,
            //                 storeName: "1区",
            //                 id: 5,
            //                 value: false
            //             },
            //             {
            //                 storeId: 2,
            //                 storeName: "2区",
            //                 id: 6,
            //                 value: true
            //             },
            //             {
            //                 storeId: 3,
            //                 storeName: "3区",
            //                 id: 7,
            //                 value: false
            //             },
            //             {
            //                 storeId: 4,
            //                 storeName: "4区",
            //                 id: 8,
            //                 value: false
            //             }
            //         ]
            //     },
            //     {
            //         areaId: 3,
            //         areaName: "燃气舱",
            //         stores: [
            //             {
            //                 storeId: 1,
            //                 storeName: "1区",
            //                 id: 9,
            //                 value: false
            //             },
            //             {
            //                 storeId: 2,
            //                 storeName: "2区",
            //                 id: 10,
            //                 value: false
            //             },
            //             {
            //                 storeId: 3,
            //                 storeName: "3区",
            //                 id: 11,
            //                 value: true
            //             },
            //             {
            //                 storeId: 4,
            //                 storeName: "4区",
            //                 id: 12,
            //                 value: false
            //             }
            //         ]
            //     },
            //     {
            //         areaId: 4,
            //         areaName: "电力舱",
            //         stores: [
            //             {
            //                 storeId: 1,
            //                 storeName: "1区",
            //                 id: 13,
            //                 value: false
            //             },
            //             {
            //                 storeId: 2,
            //                 storeName: "2区",
            //                 id: 14,
            //                 value: false
            //             },
            //             {
            //                 storeId: 3,
            //                 storeName: "3区",
            //                 id: 15,
            //                 value: false
            //             },
            //             {
            //                 storeId: 4,
            //                 storeName: "4区",
            //                 id: 16,
            //                 value: false
            //             }
            //         ]
            //     }
            // ]
            // resolve(data)
        })
    },
    // 批量控制开关
    batchControl(data){
        return new Promise((resolve, reject) => {
            post('sub-sys/action',data).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：sub-sys/action')
                }
            })
            // let data = {}
            // resolve(data)
        })
    },
    // 获取管廊联动设置
    getLinkageSetting(tunnelId){
         return new Promise((resolve, reject) => {
            get('tunnels/'+tunnelId+'/linkage').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：tunnels/'+tunnelId+'/linkage')
                }
            })
            // let data = [
            //     {
            //         areaId: 1,
            //         areaName: "污水舱",
            //         id: 1,
            //         value: false
            //     },
            //     {
            //         areaId: 2,
            //         areaName: "综合舱",
            //         id: 2,
            //         value: true
            //     },
            //     {
            //         areaId: 3,
            //         areaName: "燃气舱",
            //         id: 3,
            //         value: false
            //     },
            //     {
            //         areaId: 4,
            //         areaName: "电力舱",
            //         id: 4,
            //         value: true
            //     }
            // ]
            // resolve(data)
        })
    },
    // 获取外部、气象台温湿度
    getTempHum(){
        return new Promise((resolve, reject) => {
            get('measobjs/outside').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：measobjs/outside')
                }
            })
        })
    },
    // 获取入侵延时关闭时间（s）
    getDelayCloseTime(){
        return new Promise((resolve, reject) => {
            get('config/xml/close-time').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：config/xml/close-time')
                }
            })
        })
    },
    // 根据tunnelId获取故障列表
    getObjFaultList(tunnelId) {
        return new Promise((resolve, reject) => {
            get('tunnels/'+tunnelId+'/fault_measobjs')
                .then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：tunnels/'+tunnelId+'/fault_measobjs')
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
            // let data = [
            //     {
            //         objType: '温度',
            //         id: 1,
            //         faults: [
            //             {
            //                 area: "22区",
            //                 areaLeath: 165,
            //                 control: false,
            //                 curValue: 0,
            //                 datatypeId: 1,
            //                 id: 222022402,
            //                 maxNormal: 40,
            //                 maxValue: 120,
            //                 minNormal: -15,
            //                 minValue: -40,
            //                 name: "温度检测仪",
            //                 reset: false,
            //                 store: "电力舱",
            //                 time: 1563177600000,
            //                 unit: "℃"
            //             }
            //         ]
            //     },
            //     {
            //         objType: '电子井盖',
            //         id: 56,
            //         faults: [
            //             {
            //                 area: "22区",
            //                 areaLeath: 165,
            //                 control: true,
            //                 curValue: {
            //                     close: {index: 0, descript: "关足", value: false},
            //                     fault1: {index: 2, descript: "故障", value: false},
            //                     fault2: {index: 3, descript: "运行故障", value: false},
            //                     open: {index: 1, descript: "开足", value: false},
            //                 },
            //                 datatypeId: 200,
            //                 id: 222113300,
            //                 maxNormal: null,
            //                 maxValue: null,
            //                 minNormal: null,
            //                 minValue: null,
            //                 name: "智能井盖",
            //                 reset: true,
            //                 store: "污水舱进风口",
            //                 time: 1563435165844
            //             },
            //             {
            //                 area: "22区",
            //                 areaLeath: 165,
            //                 control: true,
            //                 curValue: {
            //                     close: {index: 0, descript: "关足", value: false},
            //                     fault1: {index: 2, descript: "故障", value: false},
            //                     fault2: {index: 3, descript: "运行故障", value: false},
            //                     open: {index: 1, descript: "开足", value: false}
            //                 },
            //                 datatypeId: 200,
            //                 id: 222243300,
            //                 maxNormal: null,
            //                 maxValue: null,
            //                 minNormal: null,
            //                 minValue: null,
            //                 name: "智能井盖",
            //                 reset: true,
            //                 store: "燃气舱排风口",
            //                 time: 1563435590664,
            //                 unit: ""
            //             }
            //         ]
            //     }
            // ]
            // resolve(data)
        })
    },
    // 获取故障列表
    getAllFaultObjs(){
        return new Promise((resolve, reject) => {
            get('fault_measobjs').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：fault_measobjs')
                }
            })
        })
    },
}

export {
    MonitorDataService
}