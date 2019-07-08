import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var DataAnalysisService = {
    //查询监测对象数据
	getMonitorData: function (prams) {
		return new Promise((resolve, reject) => {
			post('data-analyse/measvalue/datagrid', prams).then((res) => {
				let {code, msg, data} = res.data;
				if (code == 200) {
					resolve(data);
				}
				else {
					reject(msg + '地址:' + prams.requestUrl)
				}
			})
			.catch(error => {
				reject(error.response.status + '  ' + error.response.data)
			})
		})
	},

	//获取告警TopN统计数据
	getAlarmTopNData: function (prams) {
		return new Promise((resolve, reject) => {
			post(prams.requestUrl,prams).then((res) => {
				let {code, msg, data} = res.data;
				if (code == 200) {
					resolve(data);
				}
				else {
					reject(msg + '地址:' + prams.requestUrl)
				}
			})
			.catch(error => {
				reject(error.response.status + '  ' + error.response.data)
			})
		})
	},

	//告警分页查询
	getAlarmData: (prams) => {
		return new Promise((resolve, reject) => {
			post("alarms/datagrid", prams).then((res) => {
				let {code, msg, data} = res.data;
				if (code == 200) {
					resolve(data);
				}
				else {
					reject(msg + '地址:' + prams.requestUrl)
				}
			}).catch(error => {
				reject(error.response.status + '  ' + error.response.data)
			})
		})
	},

	//获取告警不同级别的告警数
	getAlarmCountByAlarmLevel: (prams) => {
		return new Promise((resolve, reject) => {
			get("alarms/count", prams).then((res) => {
				let {code, msg, data} = res.data;
				if (code == 200) {
					resolve(data);
				}
				else {
					reject(msg + '地址:' + prams.requestUrl)
				}
			}).catch(error => {
				reject(error.response.status + '  ' + error.response.data)
			})
		})
	},

	clearAlarms:(prams) => {
		return new Promise((resolve, reject) => {
			post("alarms/batch/clean", prams).then((res) => {
				let {code, msg, data} = res.data;
				console.log(data);
				if (code == 200) {
					resolve(data);
				}
				else {
					reject(msg + '地址:' + prams.requestUrl)
				}
			}).catch(error => {
				reject(error.response.status + '  ' + error.response.data)
			})
		})
	},
	NonClearAlarms:function() {
		return new Promise((resolve, reject) => {
			get('alarms/non-cleaned/list').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：alarms/non-cleaned/list')
				}
			})
		})
	},
	//下载
	downLoadData(params){
		return new Promise((resolve, reject) => {
			post('data-analyse/measvalue/history/data', params).then(res=>{
				let { code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：data-analyse/measvalue/history/data")
				}
			})
		})
	}
}

export {
  	DataAnalysisService
}
