import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var PipeService = {
    getTunnelInfo(id){
        return new Promise((resolve, reject) => {
            get("tunnels/"+id).then(res=>{
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
            put("/tunnels",params).then(res=>{
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
            post("/tunnels/datagrid",params).then(res=>{
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
            get("/companies").then(res=>{
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
            get("sub-sys/list").then(res=>{
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
            post("/staffs/conditions",params).then(res=>{
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
            del("/tunnels/batch/"+ids).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/staffs/conditions")
                }
            })
        })
    },
    autoPoint(id){
        return new Promise((resolve, reject) => {
            get("tunnels/"+id+"/auto-cal-area-section").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：tunnels/"+id+"/auto-cal-area-section")
                }
            })
        })
    }
}

export {
    PipeService
}