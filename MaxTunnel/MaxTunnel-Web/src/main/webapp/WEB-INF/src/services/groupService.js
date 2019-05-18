import axios from 'axios'

var GroupServices = {
    //添加责任班组
    addGroup(params){
        return new Promise((resolve, reject) => {
            axios.post('inspection-groups', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups")
                }
            })
        })
    },
    //获取所有巡检小组
    getGroups(){
        return new Promise((resolve, reject) => {
            axios.get('inspection-groups').then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups")
                }
            })
        })
    },
    // 分页
    getGroupDatagrid(params){
        return new Promise((resolve, reject) => {
            axios.post('inspection-groups/datagride', params).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups/datagride")
                }
            })
        })
    },
    //获取所有不在群组的成员
    getNumbersOut(id){
        return new Promise((resolve, reject) => {
            axios.get("inspection-groups/"+id+"/staffs").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups/"+id+"/staffs")
                }
            })
        })
    },
    //获取不在群组的员工，add是用
    getStaffs(){
        return new Promise((resolve, reject) => {
            axios.get("/inspection-groups/staffs").then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：/inspection-group/staffs")
                }
            })
        })
    },
    // 获取可以成为组长的员工，edit时使用
    getLeaderInEidt(id){
        return new Promise((resolve, reject) => {
            axios.get('inspection-groups/'+id+'/staffs').then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups/"+id+"/staffs")
                }
            })
        })
    },
    //修改责任班组
    editGroup(params){
        return new Promise((resolve, reject) => {
            axios.put('inspection-groups',params).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups")
                }
            })
        })
    },
    //删除责任班组
    delGroup(id){
        return new Promise((resolve, reject) => {
            axios.delete('inspection-groups/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups"+id)
                }
            })
        })
    },
    //批量添加成员
    addNumbers(id, ids){
        return new Promise((resolve, reject) => {
            axios.get('inspection-groups/'+id+"/members/"+ids).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups/"+id+"/members/"+ids)
                }
            })
        })
    },
    //获取某个具体的巡检小组
    getInspectionGroup(id){
        return new Promise((resolve, reject) => {
            axios.get('inspection-groups/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code ==200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：inspection-groups/"+id)
                }
            })
        })
    }
}

export{
    GroupServices
}  