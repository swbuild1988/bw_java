import axios from 'axios'

var PermissionConfigService = {
    // 权限分页
    permissionDatagrid(params){
        return new Promise((resolve,reject)=>{
            axios.post('permissions/datagrid',params)
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
        })
    },
    // 添加权限
    addPermission(params){
        return new Promise((resolve,reject)=>{
            axios.post('permissions',params)
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
            axios.delete('permissions/batch/' + ids)
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
            axios.get('permissions/' + id)
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
        })
    },
    // 修改权限
    editPermission(params){
        return new Promise((resolve,reject)=>{
            axios.put('permissions', params)
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
            axios.post('roles/datagrid', params)
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
            axios.delete('roles/batch/' + ids)
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
            axios.post('roles',params)
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
            axios.put('roles', params)
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
            axios.get('roles/' + id)
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
            axios.get('permissions/list')
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
            axios.post('roles/associate',params)
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
            axios.get('roles/list')
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
            axios.post('users/associate',params)
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
    // 获取后端模块列表
    getModuleList(){
        return new Promise((resolve,reject)=>{
            // axios.get('permissions/modules')
            // .then(res=>{
            //     let { msg,data,code } = res.data
            //     if(code == 200){
            //         resolve(data)
            //     } else {
            //         reject(msg + '地址:permissions/modules')
            //     }
            // })
            // .catch(error=>{
            //     reject(error.response.status + ' ' + error.response.data)
            // })
            let data = [
                {
                    id: 1,
                    name: '用户管理'
                },
                {
                    id: 2,
                    name: '能耗管理'
                },
                {
                    id: 3,
                    name: '管廊管理'
                }
            ]
            resolve(data)
        })
    },
    // 获取操作列表
    getActionsList(){
        return new Promise((resolve,reject)=>{
            // axios.get('permissions/actions')
            // .then(res=>{
            //     let { msg,data,code } = res.data
            //     if(code == 200){
            //         resolve(data)
            //     } else {
            //         reject(msg + '地址:permissions/actions')
            //     }
            // })
            // .catch(error=>{
            //     reject(error.response.status + ' ' + error.response.data)
            // })
            let data = [
                {
                    val: '添加',
                    key: 'add'
                },
                {
                    val: '列表',
                    key: 'list'
                },
                {
                    val: '修改',
                    key: 'update'
                },
                {
                    val: '删除',
                    key: 'delete'
                }
            ]
            resolve(data)
        })
    },
    // 根据模块Id获取资源列表
    getObjectsByModuleId(moduleId){
        return new Promise((resolve,reject)=>{
            // axios.get('permissions/'+ moduleId + '/objects')
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