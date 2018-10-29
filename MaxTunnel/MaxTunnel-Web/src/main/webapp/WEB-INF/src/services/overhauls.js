import axios from 'axios'

var OverhaulService = {
	// 根据overhaulId获取缺陷详情
	getDefectByOverhaulId: function(overhaulId) {
		return new Promise((resolve, reject) => {
			axios.get('/orders/' + overhaulId + '/defect').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/orders/' + overhaulId + '/defect')
				}
			})
		})
	},
	// 根据overhaulId获取维修工单详情
	getOrderByOverhaulId: function(overhaulId) {
		return new Promise((resolve, reject) => {
			axios.get('/orders/' + overhaulId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/orders/' + overhaulId)
				}
			})
		})
	},
	// 添加维修工单
	addOverhaul: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('/orders', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/orders')
				}
			})
		})
	},
	// 维修工单分页查询
	ordersDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/orders/datagird', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/orders/datagird')
				}
			})
		})
	}
}

export {
	OverhaulService
}