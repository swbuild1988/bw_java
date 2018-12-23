// 权限管理服务
import axios from 'axios'

var PermissionService = {
  // 获取用户权限路由
  fetchPermission: function () {
    return new Promise((resolve, reject) => {
      axios.get('/users/msg/count')
        .then(res => {
          let {
            msg,
            data,
            code
          } = res.data
          if (code == 200) {
            resolve(data)
          } else {
            reject(msg + '地址：/users/msg/count')
          }
        })
        .catch(error => {
          reject(error.response.status + '  ' + error.response.data)
        })
    })
  },

}

export {
  PermissionService
}
