import axios from 'axios'

var reqUrls = {
  simplePieChart: {
    energyComsumption: 'tunnels/items/energies'
  }
}

var ChartService = {
  getChartDatabyGet: function (reqUrl) {
    return new Promise((resolve, reject) => {
      axios.get(reqUrl)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：' + reqUrl)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },
  getChartDatabyPost: function (reqUrl) {
    return new Promise((resolve, reject) => {
      axios.post(reqUrl)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：' + reqUrl)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

  getPileBarChartData: function (prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl,prams)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：' + prams.requestUrl)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

  getSimpleBarDataWithClick: function (prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl, prams)
        .then(res => {
          let {code, data, msg} = res.data;
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：' + prams.requestUrl)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

  getMulitLineData(prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl, prams).then(res => {
        let {code, msg, data} = res.data;
        if (code == 200) {
          resolve(data);
        }
        else {
          reject(msg + '地址：' + prams.requestUrl)
        }
      })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

}

export {
  ChartService,
  reqUrls
}
