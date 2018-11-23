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
      switch (processKey) {
        case 'FirePlanProcess':
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
          // let data2 = {
          //   planName: '消防预案',
          //   planStatus: [{
          //     stepName: '开启声光报警',
          //     actionKey: 1,
          //     target: '开启所有管廊的声光报警'
          //   }, {
          //     stepName: '调用摄像头',
          //     actionKey: 2,
          //     target: '调用相应管舱的摄像头'
          //   }, {
          //     stepName: '打开风机',
          //     actionKey: 1,
          //     target: '打开相应管舱的风机'
          //   }, {
          //     stepName: '打开风阀',
          //     actionKey: 1,
          //     target: '打开相应管舱的风阀'
          //   }, {
          //     stepName: '打开百叶',
          //     actionKey: 2,
          //     target: '打开相应管舱的百叶'
          //   }, {
          //     stepName: '启动干粉灭火',
          //     actionKey: 1,
          //     target: '启动整个管廊的干粉灭火'
          //   }, {
          //     stepName: '通知相关单位',
          //     actionKey: 2,
          //     target: '通知整个管廊的相关单位'
          //   }, {
          //     stepName: '打开风阀',
          //     actionKey: 1,
          //     target: '打开相应管舱的风阀'
          //   }, {
          //     stepName: '打开百叶',
          //     actionKey: 2,
          //     target: '打开相应管舱的百叶'
          //   }, {
          //     stepName: '启动干粉灭火',
          //     actionKey: 1,
          //     target: '启动整个管廊的干粉灭火'
          //   }, {
          //     stepName: '通知相关单位',
          //     actionKey: 1,
          //     target: '通知整个管廊的相关单位'
          //   }]
          // }
          // resolve(data2)
          break
        case 'WindPlanProcess':
          let data = {
            planName: '通风预案',
            planStatus: [{
              stepName: '开启声光报警',
              actionKey: 1,
              target: '开启所有管廊的声光报警'
            }, {
              stepName: '调用摄像头',
              actionKey: 2,
              target: '调用相应管舱的摄像头'
            }, {
              stepName: '打开风机',
              actionKey: 1,
              target: '打开相应管舱的风机'
            }, {
              stepName: '打开百叶',
              actionKey: 1,
              target: '打开相应管舱的百叶'
            }, {
              stepName: '通知相关单位',
              actionKey: 1,
              target: '通知整个管廊的相关单位'
            }]
          }
          resolve(data)
          break
        case 'WaterPlanProcess':
          let data1 = {
            planName: '排水预案',
            planStatus: [{
              stepName: '开启水泵',
              actionKey: 1,
              target: '开启所有管廊的水泵'
            }, {
              stepName: '调用摄像头',
              actionKey: 2,
              target: '调用相应管舱的摄像头'
            }, {
              stepName: '打开照明',
              actionKey: 1,
              target: '打开相应管舱的照明'
            }, {
              stepName: '通知相关单位',
              actionKey: 1,
              target: '通知整个管廊的相关单位'
            }]
          }
          resolve(data1)
          break
      }

    })
  },
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
    }
}

export {
  PlanService
}