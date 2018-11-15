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
  // 查询监测对象数据
  measObjDataGrid: function(prams) {
    return new Promise((resolve, reject) => {
      axios.post('measobjs/datagrid' ,prams).then(res => {
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
}

export {
  MeasObjServer
}
