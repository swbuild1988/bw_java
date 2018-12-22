import axios from 'axios'

var personnelPositionService = {
	// 获取入廊人员信息
	getPersonsInfo: function() {
		return new Promise((resolve, reject) => {
			axios.get('req-historys/visitors').then(res => {
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
		})
	},
	// 获取在用设备
	getActiveLocators: function() {
		return new Promise((resolve, reject) => {
			axios.get('actived-locators').then(res => {
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
		})
	},
	// 获取所有设备
	getAllLocators: function() {
		return new Promise((resolve, reject) => {
			axios.get('locators').then(res => {
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
		})
	},
	// 绑定设备
	bindLocator: function(equipmentId, userParam) {
		return new Promise((resolve, reject) => {
			axios.post('locators/' + equipmentId + '/start', userParam).then(res => {
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
			axios.get('locators/' + equipmentId + '/stop').then(res => {
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
	}
}

export {
	personnelPositionService
}