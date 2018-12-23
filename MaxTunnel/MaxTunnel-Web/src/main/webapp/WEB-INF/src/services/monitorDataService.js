import axios from 'axios'

var MonitorDataService = {
    // 根据tunnelId和monitorType获取该管廊内监测内容数据最大值
    getMaxMonitorData: function(tunnelId, monitorType) {
        return new Promise((resolve, reject) => {
            axios.get('tunnels/' + tunnelId + '/max-cv').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：tunnels/' + tunnelId + "/" + monitorType + '/max-cv')
                }
            })
        })
    },
    // 获取监测数据
    getMonitorData: function() {
        return new Promise((resolve, reject) => {
            axios.get('sections/ai/standard-cv').then(res => {
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
            axios.get('tunnels/stores/sections/' + storeId + '/ai/cv').then(res => {
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
            axios.post('measobjs/list', prams).then(res => {
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
            axios.post('measobjs/list', params)
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
            axios.post('/videos/condition', params).then(res => {
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
            axios.get('measobjs/' + ids + '/' + dataTypeId + '/cache-cv').then(res => {
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