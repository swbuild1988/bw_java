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
	}
}

export {
	AlarmService
}