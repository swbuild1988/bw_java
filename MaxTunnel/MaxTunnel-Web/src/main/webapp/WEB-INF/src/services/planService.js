import axios from 'axios'

var PlanService = {
  // 手动确认预案流程
  ConfirmStep: function(instanceId, objectId, isConfirm) {
    return new Promise((resolve, reject) => {
      axios.get('emplans/confirm/processInstanceId/' + instanceId + '/result/' + isConfirm + '/objectId/' + objectId).then(res => {
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
  // 获取预案步骤
  getAllSteps: function(processKey) {
    return new Promise((resolve, reject) => {
      axios.get('emplans/process-key/' + processKey)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：emplans/process-key/' + processKey)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },
  // 预案分页查询
  emPlansDatagrid: function(params) {
    return new Promise((resolve, reject) => {
      axios.post('/emplans/datagrid', params)
        .then(res => {
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
  addEMPlanNode: function(params) {
    return new Promise((resolve, reject) => {
      axios.post('/emplans', params)
        .then(res => {
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
  batchDeletePlans: function(ids) {
    return new Promise((resolve, reject) => {
      axios.delete('/emplans/batch/' + ids)
        .then(res => {
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
  getNodeDetailById: function(id) {
    return new Promise((resolve, reject) => {
      axios.get('emplans/' + id)
        .then(res => {
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
      axios.put('emplans', params)
        .then(res => {
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
  publishPlans: function(vals) {
    return new Promise((resolve, reject) => {
      axios.get('emplans/deploy/batch/' + vals)
        .then(res => {
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
      axios.get('nodestatus-enums').then(res => {
        let {code, data, msg} = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject('msg', msg)
        }
      })
    })
  },
}

export {
  PlanService
}
