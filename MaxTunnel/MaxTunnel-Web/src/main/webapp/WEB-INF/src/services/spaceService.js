import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var SpaceService = {
	// 根据tunnelId获取该管廊内各个管舱的管线数
	getCableCount: function(tunnelId) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get('tunnels/' + tunnelId + '/stores/cables-count')
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ',地址:tunnels/' + tunnelId + '/stores/cables-count')
						}
					})
					.catch(error => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				let data = [{
					"可用管线数统计": 104,
					"已用管线数统计": 6,
					"设计管线数统计": 110,
					"key": "燃气仓"
				}, {
					"可用管线数统计": 110,
					"已用管线数统计": 0,
					"设计管线数统计": 110,
					"key": "污水仓"
				}, {
					"可用管线数统计": 440,
					"已用管线数统计": 0,
					"设计管线数统计": 440,
					"key": "电力仓"
				}, {
					"可用管线数统计": 110,
					"已用管线数统计": 0,
					"设计管线数统计": 110,
					"key": "综合仓"
				}]
				resolve(data)
			}
		})
	},
	// 管舱区段分页查询
	sectionsDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('sections/datagrid', params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:sections/datagrid')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据sectionIds获取管线数统计
	getCableCountBysectionIds: function(sectionIds) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/areas/sections/batch/' + sectionIds + '/cables-count')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:tunnels/areas/sections/batch/' + sectionIds + '/cables-count')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据sectionId获取管线信息
	getCableInfo: function(sectionId) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/areas/sections/' + sectionId + '/cables')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:tunnels/areas/sections/' + sectionIds + '/cables')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}
}

export {
	SpaceService
}