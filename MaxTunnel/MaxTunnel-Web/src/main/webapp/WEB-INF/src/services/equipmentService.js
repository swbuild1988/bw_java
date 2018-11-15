import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var EquipmentService = {
	// 获取所有的供应商
	getVenders: function() {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get("/venders").then(res => {
						let {
							code,
							data,
							msg
						} = res.data;
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ",地址:/venders")
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 获取所有设备型号
	getEquipmentModels: function() {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get("/equipment-models")
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipment-models')
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 添加设备
	addEquipment: function(params) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.post('/equipments', params)
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipments')
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 设备信息分页查询
	equipmentDatagird: function(params) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.post('/equipments/datagrid', params)
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipments/datagrid')
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 根据equipmentId获取该设备详细信息
	getEquDetailByEquId: function(equId) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get('/equipments/' + equId)
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipments/' + equId)
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 获取各设备类别个数
	getEquTypeAndCount: function() {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get('/equipments/type/count')
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipments/type/count')
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	},
	// 删除设备
	deleteEquipment: function(equId){
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.delete('/equipments/' + equId)
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/equipments/' + equId)
						}
					})
					.catch((error) => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				// 假数据
			}
		})
	}
}

export {
	EquipmentService
}