import axios from 'axios'
import { get } from "../utils/http";
const isRealData = require('../../static/serverconfig').isRealData

var EnumsService = {
	// 获取文件类型
	getFileType: function() {
		return new Promise((resolve, reject) => {
			get('filetype-enums')
				.then(res => {
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
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取资料类型
	getDocType: function() {
		return new Promise((resolve, reject) => {
			get('doctype-enums').then(res => {
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
			get('unittype-enums').then(res => {
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
			get('monitortype-enums').then(res => {
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
			get('datatype-enums').then(res => {
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
			get('objecttype-enums').then(res => {
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
			get('tunnels/tree ').then(res => {
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
			get('time-enums').then(res => {
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
			get('action-enums').then(res => {
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
			get('paytype-enums').then(res => {
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
			get('contractstatus-enums').then(res => {
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
			get('cablestatus-enums').then(res => {
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
			get('/defectType/enum').then(res => {
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
			get('/defectStatus/enum').then(res => {
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
			get('/defectLevel/enum').then(res => {
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
			get('/reqstatus-enums').then(res => {
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
			get('/plan-enums').then(res => {
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
	},
	// 获取开关枚举
	getSwitch: function() {
		return new Promise((resolve, reject) => {
			get('switch-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:switch-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取应急目标对象列表
	getEmTargetObjs: function() {
		return new Promise((resolve, reject) => {
			get('/em/target-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:/em/target-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取应急行为
	getEmAction: function() {
		return new Promise((resolve, reject) => {
			get('/em/action-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:/em/action-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取应急结束类型
	getEmFinishType: function() {
		return new Promise((resolve, reject) => {
			get('/em/finish-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:/em/finish-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	//获取告警级别
	getAlarmLevelList: function() {
		return new Promise((resolve, reject) => {
			get('alarmlevel-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:alarmlevel-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取状态量监测对象
	getSwitchObj() {
		return new Promise((resolve, reject) => {
			get('datatypeSI-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:datatypeSI-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取银行
	getBanks(){
		return new Promise((resolve, reject) => {
			get('bank-enums')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:bank-enums")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	  // 获取权限模块列表
	  getModuleList(){
        return new Promise((resolve,reject)=>{
            get('shiro/resource-type/enums')
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:shiro/resource-type/enums')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
            // let data = [
            //     {
            //         id: 'user',
            //         name: '用户管理'
            //     },
            //     {
            //         id: 'energy',
            //         name: '能耗管理'
            //     },
            //     {
            //         id: 'tunnel',
            //         name: '管廊管理'
            //     }
            // ]
            // resolve(data)
        })
    },
    // 获取操作列表
    getActionsList(){
        return new Promise((resolve,reject)=>{
            get('shiro/operate-type/enums')
            .then(res=>{
                let { msg,data,code } = res.data
                if(code == 200){
                    resolve(data)
                } else {
                    reject(msg + '地址:shiro/operate-type/enums')
                }
            })
            .catch(error=>{
                reject(error.response.status + ' ' + error.response.data)
            })
            // let data = [
            //     {
            //         val: '添加',
            //         key: 'add'
            //     },
            //     {
            //         val: '列表',
            //         key: 'list'
            //     },
            //     {
            //         val: '修改',
            //         key: 'update'
            //     },
            //     {
            //         val: '删除',
            //         key: 'delete'
            //     }
            // ]
            // resolve(data)
        })
	},
	//获取告警级别
	getAlarmLevel(){
		return new Promise((resolve, reject) => {
			get('alarmlevel-enums').then(res=>{
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：alarmlevel-enums")
				}
			})
		})
	}
}
export {
	EnumsService
}