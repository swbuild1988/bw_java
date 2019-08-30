import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var DataAnalysisService = {
    chooseDown: function(params){
        return new Promise((resolve,reject) => {
            post('reports/list',params).then(res=>{
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
            get('reports/preview/'+id).then(res=>{
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
            get('reports/download/'+id).then(res=>{
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
            get('time-enums').then(res=>{
                let{code,data,msg} = res.data
                if(code==200){
                    resolve(data)
                }else{
                    reject(msg+"错误：time-enums")
                }
            })
        })
    },
    downloadPDF: function(url, arraybuffer){
        return new Promise((resolve, reject) => {
            get(url, arraybuffer).then(res=>{
                let{ status, data } = res
                if(status==200){
                    resolve(data)
                }else{
                    reject("下载出错")
                }
            })
        })
    }
}

export {
	DataAnalysisService
}
