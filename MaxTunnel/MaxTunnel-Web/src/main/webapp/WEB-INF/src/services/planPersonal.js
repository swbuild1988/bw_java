import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var PlanPersonService = {
    // 小组!!!!!!!!!!!!!!!
    //添加小组
    addPosition(params){
        return new Promise((resolve, reject) =>{
            post("plan-groups", params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups")
                }
            })
        })
    },
    //分页查询
    queryPositions(params){
        return new Promise((resolve, reject) => {
            post('plan-groups/datagrid', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups/datagrid")
                }
            })
        })
    },
    //不分页
    queryPositionList(params){
        return new Promise((resolve, reject) => {
            post('plan-groups/condition', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups/condition")
                }
            })
        })
    },
    //通过id获取详情
    getPositionById(id){
        return new Promise((resolve, reject) => {
            get('plan-groups/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups/"+id)
                }
            })
        })
    },
    //修改
    editPositionInfo(params){
        return new Promise((resolve, reject) => {
            put('plan-groups', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups")
                }
            })
        })
    },
    //删除
    delPosition(id){
        return new Promise((resolve, reject) => {
            del('plan-groups/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-groups")
                }
            })
        })
    },
    //成员！！！！！！！！！！！！！！
    //分页查询
    queryGroups(params){
        return new Promise((resolve, reject) => {
            post("plan-members/datagrid",params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/datagrid")
                }
            })
        })
    },
    //添加成员
    addGroupMember(params,role){
        return new Promise((resolve, reject) => {
            post("plan-members/"+role, params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/"+role)
                }
            })
        })
    },
    //判断成员是否已经添加进组
    checkInGroup(params){
        return new Promise((resolve, reject) => {
            post("plan-members/checked", params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/checked")
                }
            })
        })
    },
    //通过ID获取详情
    getDetailsById(id){
        return new Promise((resolve, reject) => {
            get('plan-members/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/"+id)
                }
            })
        })
    },
    //修改
    updateMemberInfo(params, role){
        return new Promise((resolve, reject) => {
            put('plan-members/'+role, params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/"+role)
                }
            })
        })
    },
    //获取staff
    getStaffs(params){
        return new Promise((resolve, reject) => {
            post('staffs/conditions', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：staffs/conditions")
                }
            })
        })
    },
    //del
    delMember(id){
        return new Promise((resolve, reject) => {
            del("plan-members/"+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/"+id)
                }
            })
        })
    },
    //echarts！！！！！！！！！！！！！！！！
    getEchartsData(){
        return new Promise((resolve, reject) => {
            get("plan-members/tree").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：plan-members/tree")
                }
            })
        })
    }
}

export {
    PlanPersonService
}