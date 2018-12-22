import axios from 'axios'

var ContractService = {
	// 添加合同
	addContract: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('contracts', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：contracts')
				}
			})
		})
	},
	// 根据contractId获取该合同详细信息
	getDetailsByContractId: function(contractId) {
		return new Promise((resolve, reject) => {
			axios.get('contracts/' + contractId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：contracts/' + contractId)
				}
			})
		})
	},
	// 修改合同信息
	updateContract: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('contracts', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：contracts')
				}
			})
		})
	},
	// 合同信息分页查询
	contractDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('contracts/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：contracts/datagrid')
				}
			})
		})
	},
	// 删除合同信息
	delelteContract: function(contractId) {
		return new Promise((resolve, reject) => {
			axios.delete('contracts/' + contractId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：contracts/' + contractId)
				}
			})
		})
	}
}

export {
	ContractService
}