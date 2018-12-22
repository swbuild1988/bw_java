import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var CommunicationService = {
	// 登录
	login(params) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/login', params)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve(res)
			// 			} else {
			// 				reject('地址：/v1.0.0/login')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				token: "48a7d7481a5355aa4fb5dc285edeb40e"
			}
			resolve(data)
			// }
		})
	},
	// 退出登录
	logout(token) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/logout?token=' + token)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve('logout')
			// 			} else {
			// 				reject('地址：/v1.0.0/logout')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				status: "Success"
			}
			resolve(data)
			// }
		})
	},
	// 查询分机列表
	getExtensionList(token) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/extensionlist/query?token=' + token)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve(res)
			// 			} else {
			// 				reject('地址：/v1.0.0/extensionlist/query')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				status: "Success",
				extlist: [{
					extnumber: 1000,
					status: 'Idle',
					type: 'FXS',
					port: 'Span2-Port1',
					username: '1000'
				}, {
					extnumber: 1001,
					status: 'Idle',
					type: 'FXS',
					port: 'Span2-Port2',
					username: '1001'
				}, {
					extnumber: 1002,
					status: 'Registered',
					type: 'SIP',
					username: 'Amy'
				}, {
					extnumber: 1003,
					status: 'Registered',
					type: 'SIP',
					username: '1003'
				}, {
					extnumber: 1004,
					status: 'Registered',
					type: 'IAX',
					username: 'Lisa'
				}]
			}
			resolve(data)
			// }
		})
	},
	// 查询来电
	getInbound(token, inboundId) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/inbound/query?token=' + token, inboundId)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve(res)
			// 			} else {
			// 				reject('地址：/v1.0.0/inbound/query')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				status: "Success",
				inbound: [{
					inboundid: 1495698433.203,
					from: '1000',
					to: '1002',
					trunk: 'SIP-142',
					status: 'Talking',
					callee: [{
						extid: 1002
					}]
				}]
			}
			resolve(data)
			// }
		})
	},
	// 查询去电
	getOutbound(token, outboundId) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/outbound/query?token=' + token, outboundId)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve(res)
			// 			} else {
			// 				reject('地址：/v1.0.0/outbound/query')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				status: "Success",
				outbound: [{
					outboundid: 1495705009.316,
					from: '1002',
					to: '41000',
					trunk: 'SIP-142',
					status: 'Talking'
				}]
			}
			resolve(data)
			// }
		})
	},
	// 心跳    params : {ipaddr:'192.168.11.144',port:'8260',url:'1112121212'}
	heartBeat(token, params) {
		return new Promise((resolve, reject) => {
			// if (isRealData) {
			// 	axios.post('/v1.0.0/heartbeat?token=' + token, params)
			// 		.then(res => {
			// 			if (res.status == 'Success') {
			// 				resolve(res)
			// 			} else {
			// 				reject('地址：/v1.0.0/heartbeat')
			// 			}
			// 		})
			// 		.catch(error => {
			// 			reject(error.response.status + '  ' + error.response.data)
			// 		})
			// } else {
			let data = {
				status: "Success"
			}
			resolve(data)
			// }
		})
	}
}

export {
	CommunicationService
}