import axios from 'axios'
import { resolve } from 'path';

const isRealData = require('../../static/serverconfig').isRealData

var InspectService = {
    //获取今年的巡检任务总数
    getPatrolTaskCount: function(){
        return new Promise((resolve, reject) => {
            if(isRealData){
                axios.get('inspection-tasks/count-year').then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg)
                    }
                })
            }
        })
    },
    //获取缺陷总数
    getDefectCount: function(){
        return new Promise((resolve,reject) => {
            if(isRealData){
                axios.get('defects/count-year').then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg)
                    }
                })
            }
        })
    },
    //获取维修数
    getMaintenanceCount: function(){
        return new Promise((resolve,reject) => {
            if(isRealData){
                axios.get('orders/count-year').then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg)
                    }
                })
            }
        })
    },
    //获取维修率
    getMaintenanceRate: function(){
        return new Promise((resolve,reject) => {
            if(isRealData){
                axios.get('orders/percentage-year').then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg)
                    }
                })
            }
        })
    },
    //获取今年与去年的每月巡检计划数
    getEveryMonthPlan: function(){
        if(isRealData){
            axios.get('maintenance-orders/months').then(res=>{
                console.log("res",res)
                let{ code,data,msg } = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg)
                }
            })
        }
    }
} 
export {
    InspectService
}