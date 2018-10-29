import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var TunnelService = {
  // 获取所有管廊（名字和id）
  getTunnels: function() {
    return new Promise((resolve, reject) => {
      if (isRealData) {
        axios.get("/tunnels").then(res => {
            let {
              code,
              data,
              msg
            } = res.data;
            if (code == 200) {
              resolve(data)
            } else {
              reject(msg + ",地址:/tunnels")
            }
          })
          .catch(error => {
            reject(error.response.status + '  ' + error.response.data)
          })
      } else {
        let data = [{
          id: 1,
          name: '管廊1'
        }, {
          id: 2,
          name: '管廊2'
        }, {
          id: 3,
          name: '管廊3'
        }, {
          id: 4,
          name: '管廊4'
        }]
        resolve(data)
      }
    });
  },
  // 根据tunnelId获取该管廊内的管舱信息
  getStoresByTunnelId: function(tunnelId) {
    return new Promise((resolve, reject) => {
      axios.get('tunnels/' + tunnelId + '/stores').then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:/tunnels" + tunnelId + '/stores')
        }
      })
    });
  },
  // 根据tunnelId获取该管廊内的区域信息
  getAreasByTunnelId: function(tunnelId) {
    return new Promise((resolve, reject) => {
      axios.get('tunnels/' + tunnelId + '/areas').then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:/tunnels" + tunnelId + '/areas')
        }
      })
    });
  },
  // 获取所有管廊（树形结构）
  getTunnelsTree: function() {
    return new Promise((resolve, reject) => {
      axios.get("tunnels/tree").then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:tunnels/tree")
        }
      })
    });
  },
  // 根据管舱IDs返回管舱详细信息
  getSectionsByIds: function(ids) {
    return new Promise((resolve, reject) => {
      axios.get("sections/batch/" + ids).then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:sections/batch/" + id)
        }
      })
    });
  },
  // 根据storeId获取管舱区段信息
  getSectionsByStoreId: function(storeId) {
    return new Promise((resolve, reject) => {
      axios.get("tunnels/stores/" + storeId + '/sections').then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:tunnels/stores/" + storeId + '/sections')
        }
      })
    });
  },
  // 根据tunnelId获取管廊详细信息
  getTunnelDetailByTunnelId: function(tunnelId) {
    return new Promise((resolve, reject) => {
      axios.get("tunnels/" + tunnelId).then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:tunnels/" + tunnelId)
        }
      })
    });
  },
  // 根据tunnelID获取该管廊内管舱数
  getStoresCountByTunnelId: function(tunnelId) {
    return new Promise((resolve, reject) => {
      axios.get("tunnels/" + tunnelId + "/stores/count").then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:tunnels/" + tunnelId + "/stores/count")
        }
      })
    });
  },
  // 根据tunnelID获取该管廊内管舱区段数
  getSectionsCountByTunnelId: function(tunnelId) {
    return new Promise((resolve, reject) => {
      axios.get("tunnels/" + tunnelId + "/sections/count").then(res => {
        let {
          code,
          data,
          msg
        } = res.data;
        if (code == 200) {
          resolve(data)
        } else {
          reject(msg + ",地址:tunnels/" + tunnelId + "/sections/count")
        }
      })
    });
  }
};
export {
    TunnelService
};