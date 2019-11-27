import axios from "axios";
import { get,put,del,post } from "../utils/http";

var AlarmService = {
	// 获取本年度,本月总告警数
	getYearAndMonthAlarmCount() {
		return new Promise((resolve, reject) => {
		// get('alarm-total-count')
		// 	.then(res => {
		// 		let {
		// 			code,
		// 			data,
		// 			msg
		// 		} = res.data
		// 		if (code == 200) {
		// 			resolve(data)
		// 		} else {
		// 			reject(msg + '地址：alarm-total-count')
		// 		}
		// 	})
		// 	.catch(error => {
		// 		reject(error.response.status + '  ' + error.response.data)
		// 	})
		let data = [{
			key: "year",
			value: 291,
			isRise: false
		}, {
			key: "month",
			value: 32,
			isRise: true
		}];
		resolve(data);
		});
	},
	// 获取最新告警列表
	getNewAlarmsList() {
		return new Promise((resolve, reject) => {
			get('alarms/part').then(res => {
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
		//   id: 101,
		//   location: "古城大街2区电力仓",
		//   description: "温度过高",
		//   alarmDate: 1547024941000,
		//   alarmLevel: "提示",
		//   name: "温度"
		// }, {
		//   alarmLevel: "一般",
		//   id: 102,
		//   location: "经三路1区电力仓",
		//   description: "湿度过高",
		//   alarmDate: 1546999996000,
		//   name: "湿度"
		// }, {
		//   id: 103,
		//   location: "实验路9区污水仓",
		//   description: "氧气浓度过低",
		//   alarmDate: 1547020534000,
		//   alarmLevel: "危急",
		//   name: "氧气浓度"
		// }, {
		//   id: 104,
		//   location: "纬三路1区燃气仓",
		//   description: "温度过高",
		//   alarmDate: 1546849736000,
		//   alarmLevel: "严重",
		//   name: "温度"
		// }, {
		//   id: 105,
		//   location: "纬三路1区燃气仓",
		//   description: "温度过高",
		//   alarmDate: 1546848394000,
		//   alarmLevel: "危急",
		//   name: "温度"
		// }];
		// resolve(data);
		});
	},
	// 获取告警级别及时间
	getAlarmTime(){
		return new Promise((resolve, reject) => {
			get('config/xml/alarm-time/json')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：config/xml/alarm-time/json')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 修改告警延迟时间
	updateAlarmTime(params){
		return new Promise((resolve, reject) => {
			post('config/xml/alarm-time',params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：config/xml/alarm-time')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	getCloseTime(){
		return new Promise((resolve, reject) => {
			get('config/xml/close-time')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：config/xml/close-time')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	updateCloseTime(params){
		return new Promise((resolve, reject) => {
			post('config/xml/close-time',params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：config/xml/close-time')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	manualAddAlarm(params){
		return new Promise((resolve, reject) => {
			post('test/add_alarm/'+ params.level ,params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：test/add_alarm'+params.level)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}
};
export {
  	AlarmService
};