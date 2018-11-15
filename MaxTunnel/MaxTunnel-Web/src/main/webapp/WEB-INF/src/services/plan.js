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
  }
}

export {
  PlanService
}