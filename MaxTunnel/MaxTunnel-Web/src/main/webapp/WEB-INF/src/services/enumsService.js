import axios from 'axios'

var EnumsService = {
	// 获取文件类型
	getFileType: function() {
		return new Promise((resolve, reject) => {
			axios.get('filetype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/filetype-enums")
				}
			})
		})
	},
	// 获取资料类型
	getDocType: function() {
		return new Promise((resolve, reject) => {
			axios.get('doctype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/doctype-enums")
				}
			})
		})
	},
	// 获取单位类型
	getUnitType: function() {
		return new Promise((resolve, reject) => {
			axios.get('unittype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/unittype-enums")
				}
			})
		})
	},
	// 获取监测内容
	getMonitorType: function() {
		return new Promise((resolve, reject) => {
			axios.get('monitortype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/monitortype-enums")
				}
			})
		})
	},
	//获取数据类型
	getDataType: function() {
		return new Promise((resolve, reject) => {
			axios.get('datatype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ":地址:/datatype-enums");
				}
			})
		})
	},
	// 获取监测对象类型
	getObjType: function() {
		return new Promise((resolve, reject) => {
			axios.get('objecttype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ":地址:objecttype-enums");
				}
			})
		})
	},
	//获取监测区域（树型数据）
	getMonitorZone: function() {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/tree ').then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ":地址:/tunnels/tree ");
				}
			})
		})
	},
	// 获取时间周期
	getTimeCycle: function() {
		return new Promise((resolve, reject) => {
			axios.get('time-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/time-enums")
				}
			})
		})
	},
	// 获取入廊行为
	getEnterActionType: function() {
		return new Promise((resolve, reject) => {
			axios.get('action-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/action-enums")
				}
			})
		})
	},
	// 获取支付方式
	getPayType: function() {
		return new Promise((resolve, reject) => {
			axios.get('paytype-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/paytype-enums")
				}
			})
		})
	},
	// 获取合同状态
	getContractStatus: function() {
		return new Promise((resolve, reject) => {
			axios.get('contractstatus-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/contractstatus-enums")
				}
			})
		})
	},
	// 获取管线状态
	getCableStatus: function() {
		return new Promise((resolve, reject) => {
			axios.get('cablestatus-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/cablestatus-enums")
				}
			})
		})
	},
	// 获取缺陷类型
	getDefectType: function() {
		return new Promise((resolve, reject) => {
			axios.get('/defectType/enum').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/defectType/enum")
				}
			})
		})
	},
	// 获取缺陷状态
	getDefectStatus: function() {
		return new Promise((resolve, reject) => {
			axios.get('/defectStatus/enum').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/defectStatus/enum")
				}
			})
		})
	},
	// 获取危险等级
	getDefectLevel: function() {
		return new Promise((resolve, reject) => {
			axios.get('/defectLevel/enum').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/defectLevel/enum")
				}
			})
		})
	},
	// 获取申请状态
	getReqStatus: function() {
		return new Promise((resolve, reject) => {
			axios.get('/reqstatus-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/reqstatus-enums")
				}
			})
		})
	},
	// 预案枚举
	getPlanType: function() {
		return new Promise((resolve, reject) => {
			axios.get('/plan-enums').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:/plan-enums")
				}
			})
			// let data = [{
			// 	key: "消防预案",
			// 	processKey: "FirePlanProcess",
			// 	val: 4001
			// }, {
			// 	key: "通风预案",
			// 	processKey: "WindPlanProcess",
			// 	val: 4003
			// }, {
			// 	key: "排水预案",
			// 	processKey: "WaterPlanProcess",
			// 	val: 4002
			// }]
			// resolve(data)
		})
	}
}

export {
	EnumsService
}