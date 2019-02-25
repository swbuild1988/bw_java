import axios from 'axios'
import {
    resolve
} from 'path';

var CMVideoService = {
    //分页获取视频服务
    getVideoService: function(conditions) {
        return new Promise((resolve, reject) => {
            axios.post('video_servers/datagrid', conditions).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址:video_servers/condition')
                }
            })
        })
    },
    //获取视频供应商枚举
    getVendor: function() {
        return new Promise((resolve, reject) => {
            axios.get('video/vendor-enums').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址:video/vendor-enums')
                }
            })
        })
    },
    //获取视频版本枚举
    getVersion: function() {
        return new Promise((resolve, reject) => {
            axios.get('video/vendor/version-enums').then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(mes + ',地址:video/vendor/version-enums')
                }
            })
        })
    },
    //添加视频服务
    addVideoService: function(addData) {
        return new Promise((resolve, reject) => {
            axios.post('video_servers', addData).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址:video_servers')
                }
            })
        })
    },
    //编辑视频服务
    editVideoService: function(editDate) {
        return new Promise((resolve, reject) => {
            axios.put('video_servers', editDate).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址： video_servers')
                }
            })
        })
    },
    //通过id获取视频服务
    getVideoServiceById: function(id) {
        return new Promise((resolve, reject) => {
            axios.get("video_servers/" + id).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址:video_servers/"+id')
                }
            })
        })
    },
    //删除
    delVideoService: function(id) {
        return new Promise((resolve, reject) => {
            axios.delete("video_servers/" + id).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg + ',地址：video_servers/id ')
                }
            })
        })
    },
    getAllVideoService() {
        return new Promise((resolve, reject) => {
            axios.get("videos").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                } else {
                    reject(msg)
                }
            })
        })
    },
    // 获取视频场景
    getVideoScenes() {
        return new Promise((resolve, reject) => {
            axios.get('video_scenes')
                .then(res => {
                    let {
                        data,
                        msg,
                        code
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：video_scenes')
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data)
                })
        })
    },
    // 获取视频服务
    getVideoServices() {
        return new Promise((resolve, reject) => {
            axios.get('video_servers')
                .then(res => {
                    let {
                        data,
                        msg,
                        code
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + '地址：video_servers')
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data)
                })
        })
    }

}
export {
    CMVideoService
}