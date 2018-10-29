import axios from 'axios'

var DefectService = {
	// 根据defectId获取缺陷详情
	getDetailByDefectId: function(defectId) {
		return new Promise((resolve, reject) => {
			axios.get('/defects/' + defectId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/defects/' + defectId)
				}
			})
		})
	},
	// 根据storeId和areaID获取对象名
	getObj: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/section/measobjs', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/section/measobjs')
				}
			})
		})
	},
	// 添加缺陷
	addDefect: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('defects', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：defects')
				}
			})
		})
	},
	// 缺陷信息分页查询
	defectsDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/defects/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/defects/datagrid')
				}
			})
		})
	},
	// 获取员工列表
	getStaffs: function() {
		return new Promise((resolve, reject) => {
			axios.get('/staffs').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/staffs')
				}
			})
		})
	},
	// 根据工单Id获取工单详情
	getDetailsBySheetId: function(sheetId) {
		return new Promise((resolve, reject) => {
			axios.get('orders/' + sheetId + '/defect').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：orders/' + sheetId + '/defect')
				}
			})
		})
	},
	// 分配缺陷
	distributeDefect: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('/maintenance-orders', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/maintenance-orders')
				}
			})
		})
	},
	// 获取总缺陷数
	getDefectsCount: function() {
		return new Promise((resolve, reject) => {
			axios.get('/tunnel/defect-count').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnel/defect-count')
				}
			})
		})
	},
	// 获取故障信息列表
	getDefectList: function() {
		return new Promise((resolve, reject) => {
			axios.get('/defects/list').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/defects/list')
				}
			})
		})
	}
}

export {
	DefectService
}