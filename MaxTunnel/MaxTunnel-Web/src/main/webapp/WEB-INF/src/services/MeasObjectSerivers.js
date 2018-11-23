import axios from 'axios'

//监测对象数据服务
var MeasObjServer = {
  // 根据Ids查询监测对象
  getObjByIds: function(ids) {
    return new Promise((resolve, reject) => {
      axios.post('/measobjs/ids', ids).then(res => {
        let {
          code,
          data,
          msg
        } = res.data
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ',地址:/measobjs/ids')
        }
      })
    })
  },
  // 根据objId查询监测对象详情
  getObjById: function(id) {
    return new Promise((resolve, reject) => {
      axios.get('measobjs/' + id).then(res => {
        let {
          code,
          data,
          msg
        } = res.data
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ',地址:measobjs/' + id)
        }
      })
    })
  },
  // 查询监测对象数据
  measObjDataGrid: function(prams) {
    return new Promise((resolve, reject) => {
      axios.post('measobjs/datagrid', prams).then(res => {
        let {
          code,
          data,
          msg
        } = res.data
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ',地址:measobjs/datagrid')
        }
      })
    })
  },
  // 添加监测对象
  addMeasObj: function(params) {
    return new Promise((resolve, reject) => {
      axios.post('/measobjs', params)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + ',地址:/measobjs')
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },
  // 更新监测对象
  updateMeasObj: function(params) {
    return new Promise((resolve, reject) => {
      axios.put('measobjs', params)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + ',地址:measobjs')
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },
  // 批量删除监测对象
  batchDeleteMeasObjs: function(ids) {
    return new Promise((resolve, reject) => {
      axios.delete('measobjs/batch/' + ids)
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + ',地址:measobjs/batch/' + ids)
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },
  // 监测对象ID查重
  checkObjId: function(objId) {
    return new Promise((resolve, reject) => {
      axios.get('measobjs/' + this.formValidate.id + '/ajax')
        .then(res => {
          let {
            code,
            data,
            msg
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + ',地址:measobjs/' + this.formValidate.id + '/ajax')
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  }
}

export {
  MeasObjServer
}