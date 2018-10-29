import axios from 'axios'

var RelatedUnitService = {
	// 根据ID获取相关单位信息
	getRelatedUnitInfoById: function(id) {
		return new Promise((resolve, reject) => {
			axios.get('relatedunits/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/")
				}
			})
		})
	},
	// 添加相关单位
	addRelatedUnit: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('relatedunits', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits")
				}
			})
		})
	},
	// 修改相关单位
	updateRelatedUnit: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('relatedunits', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits")
				}
			})
		})
	},
	// 相关单位分页查询
	relatedUnitDataGrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('relatedunits/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/datagrid")
				}
			})
		})
	},
	// 删除相关单位
	deleteRelatedUnit: function(id) {
		return new Promise((resolve, reject) => {
			axios.delete('relatedunits/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/" + id)
				}
			})
		})
	}
}

export {
	RelatedUnitService
}