import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var EquipmentService = {
    // 获取所有的供应商
    getVenders: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get("/equipment-venders").then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data;
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + ",地址:/venders")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + ' ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    //获取设备状态
    getStatus() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get("/equipmentstatus-enums")
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipmentstatus-enums')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    // 获取所有设备型号
    getEquipmentModels: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get("/equipment-models")
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipment-models')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    //获取所有的设备类型
    getEquipmentTypes: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get("/equipment-types").then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：/equipment-types')
                    }
                })
            } else {
                resolve({ id: 1, name: '消防设备' }, { id: 2, name: '通讯设备' }, { id: 3, name: '万能表' }, { id: 4, name: '螺丝刀' }, { id: 5, name: '电脑' }, { id: 6, name: '有害气体检测器' }, )
            }
        })
    },
    // 添加设备
    addEquipment: function(params) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('/equipments', params)
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                resolve({ id: 1, name: '001' }, { id: 2, name: '002' }, { id: 3, name: '003' }, { id: 4, name: '004' })
            }
        })
    },
    // 设备信息分页查询
    equipmentDatagird: function(params) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('/equipments/datagrid', params)
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments/datagrid')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    // 根据equipmentId获取该设备详细信息
    getEquDetailByEquId: function(equId) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('/equipments/' + equId)
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments/' + equId)
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    //修改设备信息
    updateEquipmentInfo: function(param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.put('/equipments', param).then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments/')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                //假数据
            }
        })
    },
    // 获取各设备类别个数
    getEquTypeAndCount: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('/equipments/type/count')
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments/type/count')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    // 删除设备
    deleteEquipment: function(equId) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.delete('/equipments/' + equId)
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/equipments/' + equId)
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    //备品备件分页
    backUpDatagrid: function(params) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('/spares/datagrid', params)
                    .then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：/spares/datagrid')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            } else {
                // 假数据
            }
        })
    },
    batchOutBound: function(ids, tunnelId, param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('/spare-outs/' + ids + '/tunnels/' + tunnelId, param).then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/spare-outs/ids")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品历史统计--出库备品
    backUpHistory: function(param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('/spare-outs/datagrid', param).then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/spare-outs/datagrid")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品历史统计--设备类型出库排行榜
    getBackUpTypeHisList: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('spare-outs/type').then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/spare-outs/type")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品历史统计--设备类型出库排行榜详情
    getBackUpTypeHisDetails: function(id) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('types/' + id + '/spare-outs').then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/types/ + id + /spare-outs")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品历史统计--取用设备人排行
    getBackUpBorrowerHisList: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('spare-outs/staff').then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/spare-outs/staff")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品历史统计--取用设备人排行详情
    getBackUpBorrowerHisDetails: function(id) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('staffs/' + id + 'spare-outs').then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + "地址：/staffs/+ id + spare-outs")
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //备品查询--去向
    getWhither: function() {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.get('sparewhither-enums').then(res => {
                        let { code, data, msg } = res.data
                        if (code == 200) {
                            resolve(data)
                        } else {
                            reject(msg + '地址：sparewhithere-enums')
                        }
                    })
                    .catch((error) => {
                        reject(error.response.status + '  ' + error.response.data)
                    })
            }
        })
    },
    //仪表工具--添加
    addTools: function(count, param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('instruments/' + count, param).then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + "地址：/instruments/'+count")
                    }
                })
            }
        })
    },
    //仪表工具--查询
    queryTools: function(param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('instruments/datagride', param).then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：instruments/datagride')
                    }
                })
            } else {
                let data = {
                    pagedList: [
                        { id: 1, typeId: 1, typeName: '安全设备', modelId: 1, modelName: 'model-1', useStatus: 1, useStatusName: '在库', status: 1, venderId: 1, venderName: '张三', inTime: '' }
                    ],
                    total: 1
                }
                resolve(data)
            }
        })
    },
    //仪表工具--历史记录
    queryHisRecord: function(param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('instrument-records/datagride', param).then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：instrument-records/datagrid')
                    }
                })
            }
        })
    },
    //仪表工具--批量借出提交
    batchLend: function(ids, param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('instrument-records/' + ids, param).then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：instrument-records/+ids')
                    }
                })
            }
        })
    },
    //仪表工具--批量归还提交
    batchReturn: function(ids, usestatus, param) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                axios.post('instrument-records/' + ids + '/usestatus/' + usestatus, param).then(res => {
                    let { code, data, msg } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：instrument-records/+ids+time')
                    }
                })
            }
        })
    },
}

export {
    EquipmentService
}