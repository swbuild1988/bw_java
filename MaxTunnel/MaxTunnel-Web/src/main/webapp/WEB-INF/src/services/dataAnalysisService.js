import axios from 'axios'

var DataAnalysisService = {
  //查询监测对象数据
  getMonitorData: function (prams) {
    prams.requestUrl = "data-analyse/measvalue/datagrid";
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl, prams).then((res) => {
        let {code, msg, data} = res.data;
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址:' + prams.requestUrl)
        }
      })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

  //获取告警TopN统计数据
  getAlarmTopNData: function (prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl,prams).then((res) => {
        let {code, msg, data} = res.data;
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址:' + prams.requestUrl)
        }
      })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

  //告警分页查询
  getAlarmData: (prams) => {
    return new Promise((resolve, reject) => {
      axios.post("alarms/datagrid", prams).then((res) => {
        let {code, msg, data} = res.data;
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址:' + prams.requestUrl)
        }
      }).catch(error => {
        reject(error.response.status + '  ' + error.response.data)
      })
    })
  },

  //获取告警不同级别的告警数
  getAlarmCountByAlarmLevel: (prams) => {
    return new Promise((resolve, reject) => {
      axios.get("alarms/count", prams).then((res) => {
        let {code, msg, data} = res.data;
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址:' + prams.requestUrl)
        }
      }).catch(error => {
        reject(error.response.status + '  ' + error.response.data)
      })
    })
  },

  clearAlarms:(prams) => {
    return new Promise((resolve, reject) => {
      axios.post("alarms/batch/clean", prams).then((res) => {
        let {code, msg, data} = res.data;
        console.log(data);
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址:' + prams.requestUrl)
        }
      }).catch(error => {
        reject(error.response.status + '  ' + error.response.data)
      })
    })
  },
  NonClearAlarms:function() {
    return new Promise((resolve, reject) => {
        axios.get('alarms/non-cleaned/list').then(res => {
            let {
                code,
                data,
                msg
            } = res.data
            if (code == 200) {
                resolve(data)
            } else {
                reject(msg + '地址：alarms/non-cleaned/list')
            }
        })
    })
}
}

export {
  DataAnalysisService
}
