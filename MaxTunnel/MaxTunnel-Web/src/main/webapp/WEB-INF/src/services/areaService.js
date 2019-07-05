import axios from 'axios';
import { get,put,del,post } from "../utils/http";

var AreaService = {
    //获取管廊
    getTunnel: function(){
        return new Promise((resolve, reject) => {
            get('/tunnels').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + '地址：/tunnels')
                }
            })
        })
    },
    //批量删除
    bulkDelete(ids){
        return new Promise((resolve, reject) => {
            del("/areas/batch/"+ids).then( res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + "地址：/areas/batch/"+ ids)
                }
            } )
        })
    },
    //分页条件查询
    queryAreas(params){
        return new Promise((resolve, reject) => {
            post('/areas/datagrid', params).then( res => {
                let { code, data, msg } = res.data
                if( code == 200 ){
                    resolve( data )
                }else{
                    reject( msg + '地址：/areas/datagrid' )
                }
            } )
        })
    },
    // 新增area
    addArea(params){
        return new Promise((resolve, reject) => {
            post('/areas', params).then( res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + '地址：/areas')
                }
            } )
        })
    },
    //修改area
    editArea(params){
        return new Promise((resolve, reject) => {
            put('/areas', params).then( res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + '地址：/areas')
                }
            } )
        })
    },
    //获取单个area的详情信息
    getAreaInfo(id){
        return new Promise((resolve, reject) => {
            get('areas/'+ id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + '地址：/areas'+id)
                }
            })
        })
    }
}

export {
    AreaService
}