import axios from 'axios';
import { get,del,post } from "../utils/http";

let PatrolService = {
    // 获取责任班组
    getGroups: function() {
        return new Promise((resolve, reject) => {
            get('/inspection-groups').then((res) => {
                const { code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址/inspection-groups');
                }
            });
        });
    },
    // 添加巡检计划
    addPatrolPlan: function(params) {
        return new Promise((resolve, reject) => {
            post('/inspection-plans', params).then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址/inspection-plans');
                }
            });
        });
    },
    // 根据planId获取该巡检计划详情
    getPDetailByPlanId: function(planId) {
        return new Promise((resolve, reject) => {
            get('inspection-plans/' + planId).then((res) => {
                const {
                    code,
                    data,
                    msg,
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:inspection-plans/' + planId);
                }
            });
        });
  },
    // 获取年度计划数、本月计划、本月已完成巡检任务数、本月待巡检任务数、计划信息列表
    getPatrolCounts: function() {
        return new Promise((resolve, reject) => {
            get('/inspection-plans/count').then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:/inspection-plans/count');
                }
            });
        });
  },
    // 巡检计划分页查询
    patrolPlanDatagrid: function(params) {
        return new Promise((resolve, reject) => {
            post('inspection-plans/datagrid', params).then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:inspection-plans/datagrid');
                }
            });
        });
    },
    // 根据taskId获取该巡检任务详情
    getTDetailByPlanId: function(taskId) {
        return new Promise((resolve, reject) => {
            get('/inspection-tasks/' + taskId).then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:/inspection-tasks/' + taskId);
                }
            });
        });
    },
    // 获取所有员工
    getStaffs: function() {
        return new Promise((resolve, reject) => {
            get('/staffs').then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:/staffs');
                }
            });
        });
    },
    // 分配巡检任务
    distributeTask: function(taskId, staffId) {
        return new Promise((resolve, reject) => {
            get('maintenance-order/' + taskId + '/maintenance-person/' + staffId).then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:maintenance-order/' + taskId + '/maintenance-person/' + staffId);
                }
            });
        });
    },
    // 巡检任务分页查询
    patrolTasksDatagrid: function(params) {
        return new Promise((resolve, reject) => {
            post('/inspection-tasks/all', params).then((res) => {
                const {code,data,msg} = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + '地址:/inspection-tasks/all');
                }
            });
        });
    },
    //删除巡检任务
    patrolTaskDel(id){
        return new Promise((resolve, reject) => {
            del("inspection-plans/"+id).then(res => {
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：")
                }
            })
        })
    },
    //获取巡检方式
    getInspectWay(){
        return new Promise((resolve, reject) => {
            get('inspectionway-enum').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspectionway-enum")
                }
            })
        })
    },
    //获取巡检对象
    getInspectionObjects(){
        return new Promise((resolve, reject) => {
            get('inspectionobject-enum').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspectionobject-enum")
                }
            })
        })
    },
    //获取巡检路径
    getInspectionPath(){
        return new Promise((resolve, reject) => {
            get('inspectionpath-enum').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspectionpath-enum")
                }
            })
        })
    },
    // 获取审批人
    getApprover(){
        return new Promise((resolve, reject) => {
            get('roles/users').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：roles/users")
                }
            })
        })
    },
    //提交巡检计划
    submitPlan(params){
        return new Promise((resolve, reject) => {
            post('/inspection-plans', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/inspection-plans")
                }
            })
        })
    },
    //获取巡检计划路径
    getInspectionPlanPath(params){
        return new Promise((resolve, reject) =>{
            post('inspection-plans/condition', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-plans/condition")
                }
            })
        })
    },
    //获取img列表
    getImgList(id){
        return new Promise((resolve, reject) => {
            get('inspection-tasks/'+id+'/multifiles').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-tasks/"+id+"/multifiles")
                }
            })
        })
    },
    //生成模板
    submitTemp(params){
        return new Promise((resolve, reject) => {
            post('inspection-template',params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-template")
                }
            })
        })
    },
    //获取模板详情
    getTempInfo(id){
        return new Promise((resolve, reject) => {
            get('inspection-template/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-template/"+id)
                }
            })
        })
    },
    //查询模板
    importTemp(params){
        return new Promise((resolve, reject) => {
            post('inspection-template/condition', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-template/condition")
                }
            })
        })
    }
};

export {
  PatrolService,
};
