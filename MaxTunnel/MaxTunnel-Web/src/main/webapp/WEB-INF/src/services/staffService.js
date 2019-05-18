import axios from 'axios'
import { get,del,post } from "../utils/http";
const isRealData = require('../../static/serverconfig').isRealData

var StaffService = {
    //获取所有职位列表
    getPositionList(){ 
        return new Promise((resolve,reject) => {
            if(isRealData){
                get('/positions').then(res=>{
                    let { code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg + ',地址:positions')
                    }
                })
            }
        })    
    },
    //获取所有的部门
    getDepartmentList(){
        return new Promise((resolve,reject) => {
            if(isRealData){
                get('/departments').then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg+ '地址：departments')
                    }
                })
            }
        })
    },
    //分页查询
    getStaffInfo(params){
        return new Promise((resolve,reject) => {
            if(isRealData){
                post('/staffs/datagrid',params).then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg + '地址：/staffs/datagrid')
                    }
                })
            }
        })
    },
    //新增员工
    addStaffInfo(params){
        return new Promise((resolve,reject) => {
            if(isRealData){
                post('staffs',params).then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject( msg + '地址: staffs' )
                    }
                })
            }
        })
    },
    //批量删除
    delStaff(ids){
        return new Promise((resolve,reject) => {
            if(isRealData){
                del('staffs/batch/'+ids).then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg + '地址：staffs/batch/'+ids)
                    }
                })
            }
        })

    },
    //获取员工信息
    getStaffInfoById(id){
        return new Promise((resolve,reject) => {
            if(isRealData){
                get('staffs/'+id).then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg + '地址：staffs/' + id)
                    }
                })
            }
        })
    },
    // 修改员工信息
    // updateStaffInfo(params){
    //     return new Promise((resolve,reject) => {
    //         if(isRealData){
    //             axios.put('staffs',params).then(res=>{
    //                 let{ code,data,msg } = res.data
    //                 if(code==200){
    //                     resolve(data)
    //                 }
    //                 else if(code==20009){
    //                     resolve(msg)
    //                 }
    //                 else{
    //                     reject(msg+'地址：staffs')
    //                 }
    //             })
    //         }
    //     })
    // }
    //重置密码
    resetPassword(id){
        return new Promise((resolve,reject) => {
            if(isRealData){
                get('users/init-pw/'+id).then(res=>{
                    let{ code,data,msg } = res.data
                    if(code==200){
                        resolve(data)
                    }else{
                        reject(msg+'地址：users/init-pw/'+id)
                    }
                })
            }
        })
    }
}
export {
    StaffService
} 