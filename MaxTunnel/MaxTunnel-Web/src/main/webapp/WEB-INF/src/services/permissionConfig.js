import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var PermissionConfigService = {
    // 权限分页
    permissionDatagrid(params){
        return new Promise((resolve,reject)=>{
            post('permissions/datagrid',params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions/datagrid')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
            // let data = {
            //     total: 4,
            //     list: [
            //         {
            //             "id":1,
            //             "permissionCode":"tunnel:*:1,2",
            //             "etc":{
            //                 "c3":["古城大街","实验路"],
            //                 "c1":"管廊",
            //                 "c2":["添加","删除","更新","列表","导出"]
            //             }
            //         },
            //         {
            //             "id":2,
            //             "permissionCode":"tunnel:*:1,2",
            //             "etc":{
            //                 "c3":[],
            //                 "c1":"管廊",
            //                 "c2":["添加","删除","更新","列表","导出"]
            //             }
            //         }
            //     ]
            // }
            // resolve(data)
        })
    },
    // 添加权限
    addPermission(params){
        return new Promise((resolve,reject)=>{
            post('permissions',params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 批量删除权限
    batchDeletePer(ids){
        return new Promise((resolve,reject)=>{
            del('permissions/batch/' + ids)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions/batch/')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 根据id获取权限详情
    getPermissionDetail(id){
        return new Promise((resolve,reject)=>{
            get('permissions/' + id)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions/')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
            // let data = {
            //     "id":1,
            //     "permissionCode":"tunnel:*:1,2",
            //     "etc":{
            //         "c3":["古城大街","实验路"],
            //         "c1":"管廊",
            //         "c2":["添加","删除","更新","列表","导出"]
            //     }
            // }
            // resolve(data)
        })
    },
    // 修改权限
    editPermission(params){
        return new Promise((resolve,reject)=>{
            put('permissions', params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 角色分页
    rolesDatagrid(params){
        return new Promise((resolve,reject)=>{
            post('roles/datagrid', params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 删除角色
    batchDeleteRoles(ids){
        return new Promise((resolve,reject)=>{
            del('roles/batch/' + ids)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles/batch/')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 添加角色
    addRole(params){
        return new Promise((resolve,reject)=>{
            post('roles',params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    //  修改角色
    editRole(params){
        return new Promise((resolve,reject)=>{
            put('roles', params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 根据id获取角色详情
    getRoleDetail(id){
        return new Promise((resolve,reject)=>{
            get('roles/' + id)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles/')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 获取所有权限
    getAllPermissions(){
        return new Promise((resolve,reject)=>{
            get('permissions/list')
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:permissions/list')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 给角色赋权
    roleAuthorize(params){
        return new Promise((resolve,reject)=>{
            post('roles/associate',params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles/associate',params)
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 获取所有角色(账号分配角色时)
    getAllRoles(){
        return new Promise((resolve,reject)=>{
            get('roles/list')
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:roles/list')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 账号分配角色
    staffAuthorize(params){
        return new Promise((resolve,reject)=>{
            post('users/associate',params)
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:users/associate')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
        })
    },
    // 根据模块Id获取资源列表
    getObjectsByModuleId(moduleId){
        return new Promise((resolve,reject)=>{
            // get('permissions/'+ moduleId + '/objects')
            // .then(res=>{
            //     let { msg,data,code } = res.data
            //     if(code == 200){
            //         resolve(data)
            //     } else {
            //         reject(msg + '地址:permissions/'+ moduleId +'/objects')
            //     }
            // })
            // .catch(error=>{
            //     reject(error.response.status + ' ' + error.response.data)
            // })
            let data = [
                {
                    id: 1,
                    name: '管廊1'
                },
                {
                    id: 2,
                    name: '管廊2'
                },
                {
                    id: 3,
                    name: '管廊3'
                },
            ]
            resolve(data)
        })
    }
}

export default PermissionConfigService