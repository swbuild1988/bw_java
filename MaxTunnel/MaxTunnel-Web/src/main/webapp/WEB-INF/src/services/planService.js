import axios from 'axios'
import {
    get,
    put,
    del,
    post
} from "../utils/http";

var PlanService = {
    // 手动确认预案流程
    ConfirmStep: function (instanceId, objectId, isConfirm) {
        return new Promise((resolve, reject) => {
            get('emplans/confirm/processInstanceId/' + instanceId + '/result/' + isConfirm + '/objectId/' + objectId).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + '地址：emplans/confirm/processInstanceId/' + instanceId + "/result/" + isConfirm + '/objectId/' + objectId)
                }
            })
        })
    },
    // 预案分页查询
    emPlansDatagrid: function (params) {
        return new Promise((resolve, reject) => {
            post('/emplans/datagrid', params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：/emplans/datagrid')
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    // 添加应急预案
    addEMPlanNode: function (params) {
        return new Promise((resolve, reject) => {
            post('/emplans', params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：/emplans')
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    // 批量删除应急预案
    batchDeletePlans: function (ids) {
        return new Promise((resolve, reject) => {
            del('/emplans/batch/' + ids).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：/emplans/batch/' + ids)
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    // 根据planId获取节点详情
    getNodeDetailById: function (id) {
        return new Promise((resolve, reject) => {
            get('emplans/' + id).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：emplan/' + id)
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    // 更新预案节点
    updateEMPlanNode(params) {
        return new Promise((resolve, reject) => {
            put('emplans', params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：emplans')
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    // 部署预案
    publishPlans: function (vals) {
        return new Promise((resolve, reject) => {
            get('emplans/deploy/batch/' + vals).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：emplans/deploy/batch/' + vals)
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    //获取预案状态类型
    getNodeListStatus() {
        return new Promise((resolve, reject) => {
            get('nodestatus-enums').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data)
                } else {
                    reject('msg', msg)
                }
            })
        })
    },
    //根据key获取预案流程
    getAllSteps(processKey) {
        return new Promise((resolve, reject) => {
            get('emplans/process-key/' + processKey).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data)
                } else {
                    reject('msg', msg)
                }
            })
        })
    },
    //启动预案
    startPlan(params) {
        return new Promise((resolve, reject) => {
            post('emplans/start', params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data)
                } else {
                    reject('msg', msg)
                }
            })
        })
    },
    completeTask(processInstanceId, value) {
        return new Promise((resolve, reject) => {
            let params = {
                processInstanceId: processInstanceId,
                value: value
            }
            post('emplan/complete', params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data)
                } else {
                    reject('msg', msg)
                }
            })
        })
    },
    // 根据预案获取节点列表
    getNodeList(processKey) {
        return new Promise((resolve, reject) => {
            get('activiti/process-key/' + processKey)
                .then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + ',地址:activiti/process-key/' + processKey)
                    }
                })
                .catch(error => {
                    reject(error.response.status + '  ' + error.response.data)
                })
        })
    },
    submitPlanDetails(params){
        return new Promise((resolve, reject) => {
            post('plans/his-proc-inst/datagrid', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plans/his-proc-inst/datagrid")
                }
            })
        })
    },
    startMQ(){
        return new Promise((resolve, reject) => {
            get('/emplans/start/sections/10/process-type/4001').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/emplans/start/sections/10/process-type/4001")
                }
            })
        })
    },
    endMQ(){
        return new Promise((resolve, reject) => {
            get('emplans/deploy/4001').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：emplans/deploy/4001")
                }
            })
        })
    },
    objBindPlan(params){
        return new Promise((resolve, reject) => {
            post('measobjs/conf/plans',params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：measobjs/conf/plans")
                }
            })
        })
    }
}

export {
    PlanService
}