import axios from 'axios'
import { get,put,del,post } from "../utils/http";
import {
    resolve
} from 'path';

var CMVideoService = {
    //分页获取视频服务
    getVideoService: function(conditions) {
        return new Promise((resolve, reject) => {
            post('video_servers/datagrid', conditions).then(res => {
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
            get('video/vendor-enums').then(res => {
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
            get('video/vendor/version-enums').then(res => {
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
            post('video_servers', addData).then(res => {
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
            put('video_servers', editDate).then(res => {
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
            get("video_servers/" + id).then(res => {
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
            del("video_servers/" + id).then(res => {
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
            get("videos").then(res => {
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
            get('video_scenes')
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
            get('video_servers')
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
    },
    // 视频分页查询
    videoDatagrid(params){
        return new Promise((resolve,reject)=>{
            post('videos/datagrid',params)
            .then(res=>{
                let { code, data, msg } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + ' 地址:videos/datagrid')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 根据Id获取视频详情
    getVideoDetailById(id){
        return new Promise((resolve,reject)=>{
            get('videos/'+ id)
            .then(res=>{
                let { code, data, msg } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + ' 地址:videos/'+id)
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
            // let data = {
            //     id:1,
            //     tunnelId: 1,
            //     areaId: 1012, 
            //     storeId:1022,
            //     name:'十二段',
            //     name:'电力仓视频',
            //     objtypeId:'视频',
            //     serverId:1,
            //     channelNo:2,
            //     videoSceneId:1,
            //     description:'可以正常投入使用',
            //     actived:0,
            //     password: 12345,
            //     username: 'admin',
            //     ip: '192.168.0.12',
            //     port: 8078,
            //     vendor: 1
            // }
            // resolve(data)
        })
    },
    // 批量删除视频
    batchDeleteVideos(ids){
        return new Promise((resolve,reject)=>{
            del('videos/'+ ids)
            .then(res=>{
                let { code, data, msg } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + ' 地址:videos/'+ids)
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 新增视频
    addVideo(params){
        return new Promise((resolve,reject)=>{
            post('videos',params)
            .then(res=>{
                let { code, data, msg } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + ' 地址:videos')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 更新视频
    updateVideo(params){
        return new Promise((resolve,reject)=>{
            put('videos',params)
            .then(res=>{
                let { code, data, msg } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + ' 地址:videos')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    }
}
export {
    CMVideoService
}