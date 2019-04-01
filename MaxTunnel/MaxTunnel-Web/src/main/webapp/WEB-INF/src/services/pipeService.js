import axios from 'axios'

var PipeService = {
    getTunnelInfo(id){
        return new Promise((resolve, reject) => {
            axios.get("tunnels/"+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg)
                }
            })
        })
    },
    saveEditTunnelInfo(params){
        return new Promise((resolve, reject) => {
            axios.put("/tunnels",params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(mgs+"地址：/tunnels")
                }
            })
        })
    },
    queryTunnels(params){
        return new Promise((resolve, reject) => {
            axios.post("/tunnels/datagrid",params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/tunnels/datagrid")
                }
            })
        })
    },
    getCompanies() {
        return new Promise((resolve, reject) => {
            axios.get("/companies").then(res=>{
                let { code, data, msg } = res.data;
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"地址：/companies")
                }
            })
        })
    },
    getMaxView(){
        return new Promise((resolve, reject) => {
            axios.get("sub-sys/list").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(error+"地址：/sub-sys/list")
                }
            })
        })
    },
    getStaffs(params){
        return new Promise((resolve, reject) => {
            axios.post("/staffs/conditions",params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/staffs/conditions")
                }
            })
        })
    },
    delTunnels(ids){
        return new Promise((resolve, reject) => {
            axios.delete("/tunnels/batch/"+ids).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/staffs/conditions")
                }
            })
        })
    }
}

export {
    PipeService
}