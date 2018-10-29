import axios from 'axios'

var PlanService = {
  // 手动确认预案流程
  ConfirmStep: function (instanceId, isConfirm) {
    return new Promise((resolve, reject) => {
      axios.get('emplans/confirm/processInstanceId/' + instanceId + '/result/' + isConfirm).then(res => {
        let {code, data, msg} = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + '地址：emplans/confirm/processInstanceId/' + instanceId + "result/" + isConfirm)
        }
      })
    })
  }
}

export {
  PlanService
}
