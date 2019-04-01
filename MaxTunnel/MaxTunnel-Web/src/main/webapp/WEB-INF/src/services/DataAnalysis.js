import axios from 'axios'

var DataAnalysisService = {
    chooseDown: function(params){
        return new Promise((resolve,reject) => {
            axios.post('reports/list',params).then(res=>{
                let{ code,data,msg } = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"地址：reports/list")
                }
            })
        })
    },
    preview: function(id){
        return new Promise((resolve,reject) => {
            axios.get('reports/preview/'+id).then(res=>{
                let{ code,data,msg } = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"地址：reports/preview/"+id)
                }
            })
        })
    },
    download: function(id){
        return new Promise((resolve, reject) => {
            axios.get('reports/download/'+id).then(res=>{
                let{ code,data,msg } = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"地址：reports/download/"+id)
                }
            })
        })
    },
    //时间枚举
    timeEnums: function(){
        return new Promise((resolve, reject) => {
            axios.get('time-enums').then(res=>{
                let{code,data,msg} = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"错误：time-enums")
                }
            })
        })
    }
}

export {
	DataAnalysisService
}
