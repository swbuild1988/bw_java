import axios from 'axios'
import {
	get
} from "../utils/http";

var UserService = {
	// 获取用户消息数
	getMessageCount: function () {
		return new Promise((resolve, reject) => {
			get('/users/msg/count')
				.then(res => {
					let {
						msg,
						data,
						code
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：/users/msg/count')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	//获取告警消息数
	getAlarmCount() {
		return new Promise((resolve, reject) => {
			get('alarms/non-cleaned/alarm-levels').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + "地址：alarms/non-cleaned/alarm-levels")
				}
			})
		})
	},
	//请求入廊信息
	reqHistorysInfo(requestUrl) {
		return new Promise((resolve, reject) => {
			get(requestUrl)
				.then(res => {
					let {
						msg,
						data,
						code
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + requestUrl)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	//获取告警级别对应的时间
	getAlarmLevelTime() {
		return new Promise((resolve, reject) => {
			get('config/xml/alarm-time').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + "地址：config/xml/alarm-time")
				}
			})
		})
	},
	// 获取用户具体信息
	getUserInfo(id) {
		return new Promise((resolve, reject) => {
			get('users/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + "地址：config/xml/alarm-time")
				}
			})
		})
	}
}

export {
	UserService
}