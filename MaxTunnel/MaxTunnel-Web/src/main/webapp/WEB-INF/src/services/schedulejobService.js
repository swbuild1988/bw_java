import axios from 'axios'
import { get,put,post } from "../utils/http";

var SchedulejobService = {
    querySchedule(params){
        return new Promise((resolve, reject) => {
            post('/schedulejobs/datagrid',params).then(res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + "地址：/schedulejobs/datagrid")
                }
            })
        })
    },
    getObjtype(){
        return new Promise((resolve, reject) => {
            get('jobtype-enums').then(res=> {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject( msg + '地址：jobtype-enums' )
                }
            })
        })
    },
    checkJobName(jobName){
        return new Promise((resolve, reject) => {
            get('/schedulejobs/ajax/'+jobName).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg + "地址：/schedulejobs/ajax/" + jobName)
                }
            })
        })
    },
    saveSchedule(params){
        return new Promise((resolve, reject) => {
            post('/schedulejobs', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/schedulejobs")
                }
            })
        })
    },
    editSchedule(params){
        return new Promise((resolve, reject) => {
            put('/schedulejobs', params).then(res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve( data )
                }else{
                    reject( msg )
                }
            })
        })
    },
    getSchedule(id){
        return new Promise((resolve, reject) => {
            get("schedulejobs/"+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve( data )
                }else{
                    reject( msg )
                }
            })
        })
    }
}

export {
    SchedulejobService
}