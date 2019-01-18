import axios from 'axios'

var reqUrls = {
  simplePieChart: {
    energyComsumption: 'tunnels/items/energies'
  }
}

var ChartService = {
  getChartDatabyGet: function(reqUrl) {
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
  getChartDatabyPost: function(reqUrl) {
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

  getPileBarChartData: function(prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl, prams)
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

  getPileBarDataByGet: function(requestUrl) {
    return new Promise((resolve, reject) => {
      axios.get(requestUrl)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：' + requestUrl)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
      // let data = [{
      //   'key': '1月',
      //   '一般告警': 10,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '2月',
      //   '一般告警': 10,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '3月',
      //   '一般告警': 10,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '4月',
      //   '一般告警': 1,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '5月',
      //   '一般告警': 10,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '6月',
      //   '一般告警': 7,
      //   '严重告警': 2,
      //   '提示告警': 2,
      //   '致命告警': 4
      // }, {
      //   'key': '7月',
      //   '一般告警': 3,
      //   '严重告警': 2,
      //   '提示告警': 6,
      //   '致命告警': 4
      // }, {
      //   'key': '8月',
      //   '一般告警': 4,
      //   '严重告警': 2,
      //   '提示告警': 4,
      //   '致命告警': 4
      // }, {
      //   'key': '9月',
      //   '一般告警': 3,
      //   '严重告警': 1,
      //   '提示告警': 5,
      //   '致命告警': 4
      // }, {
      //   'key': '10月',
      //   '一般告警': 8,
      //   '严重告警': 1,
      //   '提示告警': 2,
      //   '致命告警': 6
      // }, {
      //   'key': '11月',
      //   '一般告警': 6,
      //   '严重告警': 3,
      //   '提示告警': 0,
      //   '致命告警': 4
      // }, {
      //   'key': '12月',
      //   '一般告警': 1,
      //   '严重告警': 5,
      //   '提示告警': 1,
      //   '致命告警': 4
      // }]
      // resolve(data)
    })
  },

  getSimpleBarDataWithClick: function(prams) {
    return new Promise((resolve, reject) => {
      axios.post(prams.requestUrl, prams)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data;
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
          let {
            code,
            msg,
            data
          } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
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