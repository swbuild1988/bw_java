import axios from 'axios'

var CustomerService = {
	// 添加客户信息
	addCustomer: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/customers', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/customers')
				}
			})
		})
	},
	// 根据customerID获取该客户详细信息
	getDetailsByCustomerId: function(customerId) {
		return new Promise((resolve, reject) => {
			axios.get('/customers/' + customerId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/customers/' + customerId)
				}
			})
		})
	},
	// 修改客户信息
	updateCustomer: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('customers', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：customers')
				}
			})
		})
	},
	// 客户信息分页查询
	customerDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('customers/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：customers/datagrid')
				}
			})
		})
	},
	// 删除客户信息
	deleteCustomer: function(customerId) {
		return new Promise((resolve, reject) => {
			axios.delete('/customers/' + customerId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/customers/' + customerId)
				}
			})
		})
	}
}

export {
	CustomerService
}