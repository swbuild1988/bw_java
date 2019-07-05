import axios from 'axios'
import { get,post } from "../utils/http";

var personnelPositionService = {
	// 获取入廊人员信息
	getPersonsInfo: function() {
		return new Promise((resolve, reject) => {
			get('req-historys/visitors').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:req-historys/visitors')
				}
			})
			// let data = [
			// 	{	
			// 		"number":100,
			// 		"action":"参观",
			// 		"processStatus": "管理员审批",
			// 		"preTime":1551088800000,
			// 		"company": {
			// 			"id":1,
			// 			"name":"波汇科技",
			// 			"crtTime":1529596800000
			// 		},
			// 		"list":[
			// 			{
			// 				"id":1001,
			// 				"name":"test",
			// 				"account":"test1001",
			// 				"sex":"男",
			// 				"telphone":"15664102252",
			// 				"hireDate":1551283200000,
			// 				"dept":{
			// 					"id":1,
			// 					"name":"产品部",
			// 					"crtTime":1529942400000
			// 				},
			// 				"position":{
			// 					"id":1,
			// 					"name":"总经理",
			// 					"crtTime":1529596800000
			// 				},
			// 				"acctInfo":null,
			// 				"crtTime":1551339628000,
			// 				"identityNO":null,
			// 				"outside":1,
			// 				"outsideName":"参观"
			// 			},
			// 			{
			// 				"id":1002,
			// 				"name":"test2",
			// 				"account":"test1002",
			// 				"sex":"男",
			// 				"telphone":"15664102251",
			// 				"hireDate":1551283200000,
			// 				"dept":{
			// 					"id":1,
			// 					"name":"产品部",
			// 					"crtTime":1529942400000
			// 				},
			// 				"position":{
			// 					"id":1,
			// 					"name":"总经理",
			// 					"crtTime":1529596800000
			// 				},
			// 				"acctInfo":null,
			// 				"crtTime":1551339628000,
			// 				"identityNO":null,
			// 				"outside":1,
			// 				"outsideName":"参观"
			// 			}
			// 		],
			// 		"tunnel":{
			// 			"id":1,
			// 			"name":"古城大街",
			// 			"status":1,
			// 			"length":null
			// 		}
			// 	}
			// ]
			// resolve(data)
		})
	},
	// 获取在用设备
	getActiveLocators: function() {
		return new Promise((resolve, reject) => {
			get('actived-locators').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:actived-locators')
				}
			})
			// let data = [
			// 		{
			// 			"owner": {
			// 				"id":1001,
			// 				"name":"test",
			// 				"account":"test1001",
			// 				"sex":"男",
			// 				"telphone":"15664102252",
			// 				"hireDate":1551283200000,
			// 				"dept":{
			// 					"id":1,
			// 					"name":"产品部",
			// 					"crtTime":1529942400000
			// 				},
			// 				"position":{
			// 					"id":1,
			// 					"name":"总经理",
			// 					"crtTime":1529596800000
			// 				},
			// 				"acctInfo":null,
			// 				"crtTime":1551339628000,
			// 				"identityNO":null,
			// 				"outside":1,
			// 				"outsideName":"参观"
			// 			},
			// 			"latitude":37.7123123,
			// 			"name":"定位设备1",
			// 			"id":20001,
			// 			"time":1551432826382,
			// 			"type":0,
			// 			"longitude":112.5001279,
			// 			"height":0.0
			// 		}
			// ]
			// resolve(data)
		})
	},
	// 获取所有设备
	getAllLocators: function() {
		return new Promise((resolve, reject) => {
			get('locators').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:locators')
				}
			})
			// let data = [
			// 	{
			// 		"owner": null,
			// 		"latitude":0.0,
			// 		"name":"定位设备1",
			// 		"id":20001,
			// 		"time":1551681896079,
			// 		"type":0,
			// 		"longitude":0.0,
			// 		"height":0.0
			// 	},
			// 	{
			// 		"owner": null,
			// 		"latitude":37.7067123,
			// 		"name":"定位设备2",
			// 		"id":20002,
			// 		"time":1551252103000,
			// 		"type":0,
			// 		"longitude":112.4945279,
			// 		"height":0.0
			// 	}
			// ]
			// resolve(data)
		})
	},
	// 绑定设备
	bindLocator: function(equipmentId, userParam) {
		return new Promise((resolve, reject) => {
			post('locators/' + equipmentId + '/start', userParam).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:locators/' + equipmentId + '/start')
				}
			})
		})
	},
	// 解绑设备
	unbindLocator: function(equipmentId) {
		return new Promise((resolve, reject) => {
			get('locators/' + equipmentId + '/stop').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:locators/' + equipmentId + '/stop')
				}
			})
		})
	},
	// 入廊人员分页查询
	visitorsDatagrid(params) {
		return new Promise((resolve,reject)=>{
			post('req-record/datagrid',params)
			.then(
				res=> {
					let { code,data,msg } = res.data
					if( code == 200){
						resolve(data)
					} else {
						reject(msg + '地址：visitors-datagrid')
					}
				}
			)
			.catch(error=>{
				reject(error.response.status + ' ' + error.response.data)
			})
			// let data = [
			// 	{
			// 		"id":1001,
			// 		"name":"test",
			// 		"account":"test1001",
			// 		"sex":"男",
			// 		"telphone":"15664102252",
			// 		"hireDate":1551283200000,
			// 		"dept":{
			// 			"id":1,
			// 			"name":"产品部",
			// 			"crtTime":1529942400000
			// 		},
			// 		"position":{
			// 			"id":1,
			// 			"name":"总经理",
			// 			"crtTime":1529596800000
			// 		},
			// 		"acctInfo":null,
			// 		"crtTime":1551339628000,
			// 		"identityNO": 220111133875083321,
			// 		"outside":1,
			// 		"outsideName":"参观"
			// 	}
			// ]
			// resolve(data)
		})
	},
	// 根据绑定设备的staffId获取详情
	getBindDetails(staffId){
		return new Promise((resolve,reject)=>{
			get('req-historys/' + staffId + '/staff')
			.then(
				result=>{
					let { code,data,msg } = result.data
					if(code == 200){
						resolve(data)
					} else {
						reject(msg + ' 地址：req-historys/' + staffId + '/staff')
					}
				}
			)
			.catch(error=>{
				reject(error.response.status + ' ' + error.response.data)
			})
		})
	},
	// 获取历史轨迹
	getRoutes(params){
		return new Promise((resolve,reject)=>{
			post('req-record/condition',params)
			.then(
				result=>{
					let { code,data,msg } = result.data
					if(code == 200){
						resolve(data)
					} else {
						reject(msg + ' 地址：req-record/condition')
					}
				}
			)
			.catch(error=>{
				reject(error.response.status + ' ' + error.response.data)
			})
		})
	}
}

export {
	personnelPositionService
}