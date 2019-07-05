import axios from "axios";
import { get,put,del,post } from "../utils/http";

var PowerSubstationService = {
    // 变电所分页查询
    substationDatagrid(params){
        return new Promise((resolve,reject)=>{
            post('substations/datagrid',params).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations/datagrid");
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
            // let data = [
            //     {
            //         id: 1,
            //         tunnelId: 1,
            //         invId: 2003,
            //         inaId: 3002,
            //         outvId: 2183,
            //         outaId: 2492,
            //         powerId: 47473,
            //         eleRadius: 2.5,
            //         powerFactor: 1.1,
            //         compensationFactor: 2.1,
            //         position: 'ufufkkb',
            //         time: 1553306212000,
            //         inv: {
            //             name: '进电压'
            //         },
            //         ina: {
            //             name: '进电流'
            //         },
            //         outv: {
            //             name: '出电压'
            //         },
            //         outa: {
            //             name: '出电流'
            //         },
            //         power: {
            //             name: '功率'
            //         },
            //         tunnle: {
            //             name: '管廊1'
            //         }
            //     },
            //     {
            //         id: 2,
            //         tunnelId: 1,
            //         invId: 2003,
            //         inaId: 3002,
            //         outvId: 2183,
            //         outaId: 2492,
            //         powerId: 47473,
            //         eleRadius: 2.5,
            //         powerFactor: 1.1,
            //         compensationFactor: 2.1,
            //         position: 'egfsetr',
            //         time: 1553306212000,
            //         inv: {
            //             name: '进电压1'
            //         },
            //         ina: {
            //             name: '进电流1'
            //         },
            //         outv: {
            //             name: '出电压1'
            //         },
            //         outa: {
            //             name: '出电流1'
            //         },
            //         power: {
            //             name: '功率1'
            //         },
            //         tunnle: {
            //             name: '管廊1'
            //         }
            //     }
            // ]
            // resolve(data)
        })
    },
    // 添加变电所信息
    addSubstation(params){
        return new Promise((resolve,reject)=>{
            post('substations',params).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations");
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
        })
    },
    // 修改变电所信息
    editSubstation(params){
        return new Promise((resolve,reject)=>{
            put('substations',params).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations");
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
        })
    },
    // 根据id查变电所详情
    substationDetailById(id){
        return new Promise((resolve,reject)=>{
            get('substations/'+id).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations"+id);
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
            // let data = {
            //     id: 1,
            //     tunnelId: 1,
            //     invId: 2003,
            //     inaId: 3002,
            //     outvId: 2183,
            //     outaId: 2492,
            //     powerId: 47473,
            //     eleRadius: 2.5,
            //     powerFactor: 1.1,
            //     compensationFactor: 2.1,
            //     position: 'ufufkkb',
            //     time: 1553306212000,
            //     inv: {
            //         name: '进电压'
            //     },
            //     ina: {
            //         name: '进电流'
            //     },
            //     outv: {
            //         name: '出电压'
            //     },
            //     outa: {
            //         name: '出电流'
            //     },
            //     power: {
            //         name: '功率'
            //     },
            //     tunnel: {
            //         name: '管廊1'
            //     }
            // }
            // resolve(data)
        })
    },
    // 删除变电所信息
    deleteSubstation(id){
        return new Promise((resolve,reject)=>{
            del('substations/'+id).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations"+id);
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
        })
    },
    // 条件获取变电所列表
    getSubstationList(params){
        return new Promise((resolve,reject)=>{
            post('substations/condition',params).then(
                res=>{
                    let {code,data,msg} = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/substations/condition");
                    }
                })
                .catch(error=>{
                    reject(error.response.status + "  " + error.response.data);
                })
        })
    }
}

export default PowerSubstationService