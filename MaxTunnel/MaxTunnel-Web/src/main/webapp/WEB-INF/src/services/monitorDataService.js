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
    }
}

export {
    MonitorDataService
}