import axios from 'axios'

var AlarmService = {
	// 获取本年度,本月总告警数
	getYearAndMonthAlarmCount() {
		return new Promise((resolve, reject) => {
			axios.get('alarm-total-count')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：alarm-total-count')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
			// let data = [{
			// 	key: "year",
			// 	value: 291,
			// 	isRise: false
			// }, {
			// 	key: "month",
			// 	value: 32,
			// 	isRise: true
			// }]
			// resolve(data)
		})
	},
	// 获取最新告警列表
	getNewAlarmsList() {
		return new Promise((resolve, reject) => {
			axios.get('alarms/part')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：alarms/part')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
			// let data = [{
			// 	id: 101,
			// 	location: "古城大街2区电力仓",
			// 	description: "温度过高",
			// 	alarmDate: "01.10.19",
			// 	alarmLevel: "提示",
			// 	name: '温度'
			// }, {
			// 	alarmLevel: "一般",
			// 	id: 102,
			// 	location: "经三路1区电力仓",
			// 	description: "湿度过高",
			// 	alarmDate: "12.11.18",
			// 	name: '湿度'
			// }, {
			// 	id: 103,
			// 	location: "实验路9区污水仓",
			// 	description: "氧气浓度过低",
			// 	alarmDate: "01.02.19",
			// 	alarmLevel: "危急",
			// 	name: '氧气浓度'
			// }, {
			// 	id: 104,
			// 	location: "纬三路1区燃气仓",
			// 	description: "温度过高",
			// 	alarmDate: "12.30.18",
			// 	alarmLevel: "严重",
			// 	name: '温度'
			// }, {
			// 	id: 105,
			// 	location: "纬三路1区燃气仓",
			// 	description: "温度过高",
			// 	alarmDate: "12.30.18",
			// 	alarmLevel: "危急",
			// 	name: '温度'
			// }]
			// resolve(data)
		})
	}
}
export {
	AlarmService
}