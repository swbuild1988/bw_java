import axios from 'axios'

var StoreService = {
    //分页查询
    queryStore(params){
        return new Promise((resolve, reject) => {
            axios.post("/tunnels/stores/datagrid",params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/tunnels/stores/datagrid' )
                }
            })
        })
    },
    // 获取tunnels
    getTunnels(){
        return new Promise((resolve, reject) => {
            axios.get("/tunnels").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/tunnels' )
                }
            })
        })
    },
    //获取storeType
    getStoreType(){
        return new Promise((resolve, reject) => {
            axios.get("/store-type/list").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/store-type/list' )
                }
            })
        })
    },
    //批量删除
    bulkDelete(ids){
        return new Promise((resolve, reject) => {
            axios.delete("/stores/batch/"+ids).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/stores/batch/'+ids )
                }
            })
        })
    },
    //保存修改后的store
    saveEditStore(params){
        return new Promise((resolve, reject) => {
            axios.put("/stores", params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/stores' )
                }
            })
        })
    },
    //保存新增的store
    saveAddStore(params){
        return new Promise((resolve, reject) => {
            axios.post("/stores", params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg+'地址：/stores' )
                }
            })
        })
    }
}

export {
    StoreService
}