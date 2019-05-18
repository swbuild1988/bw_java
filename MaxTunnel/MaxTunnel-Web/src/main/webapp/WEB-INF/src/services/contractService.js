import axios from 'axios'
import { get,put,del,post } from "../utils/http";

var ContractService = {
	// 添加合同
	addContract: function(params) {
		return new Promise((resolve, reject) => {
			post('contracts', params).then(res => {
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
			get('contracts/' + contractId).then(res => {
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
			put('contracts', params).then(res => {
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
			post('contracts/datagrid', params).then(res => {
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
			del('contracts/' + contractId).then(res => {
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
	// 合同上传文件
	uploadFile(param,config){
		return new Promise((resolve, reject) => {
			post('/contracts/upload',param,config)
			.then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/contracts/upload')
				}
			})
			.catch(error=>{
				reject(error.response.stauts + ' ' + error.response.data)
			})
		})
	},
	// 文件预览
	preViewFile(contractId){
		return new Promise((resolve, reject) => {
			get('contracts/'+contractId+'/view')
			.then(res => {
				// let {
				// 	code,
				// 	data,
				// 	msg
				// } = res.data
				if (res) {
					resolve(res)
				} else {
					reject(msg + '地址：/contracts/'+contractId+'/view')
				}
			})
			.catch(error=>{
				reject(error.response.stauts + ' ' + error.response.data)
			})
		})
	}
}

export {
	ContractService
}