import axios from "axios";
import { get,put,del,post } from "../utils/http";

var CompanyService = {
    // 企业管理分页查询
    companiesDatagrid(param){
        return new Promise((resolve, reject) => {
			post('companies/datagrid',param)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies/datagrid")
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
			// let data = {
			// 	total: 1,
			// 	list: [
			// 	{
			// 		account: '账户1',
			// 		address: '浦东新区上科路388号',
			// 		bank: 1,
			// 		bankName: '中国建设银行',
			// 		creditNo: 234245979245,
			// 		crtTime: 1529596800000,
			// 		customers: null,
			// 		id: 1,
			// 		imgUrl: null,
			// 		inspectionInfo: null,
			// 		inspectionNo: null,
			// 		inspectionTime: null,
			// 		mail: 'pub@bdw.com',
			// 		name: "波汇科技",
			// 		phone: '021-9997932'
			// 	}
			// ]}
			// resolve(data)
		})
    },
    // 根据Id查企业详情
    getcompanyDetail(id){
        return new Promise((resolve, reject) => {
			get('companies/'+ id)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies/" + id)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
    },
    // 添加企业信息
    addCompany(param){
        return new Promise((resolve, reject) => {
			post('companies',param)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies",param)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
    },
    // 编辑企业信息
    editCompany(param){
        return new Promise((resolve, reject) => {
			put('companies',param)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies",param)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
    },
    // 删除企业信息
    batchDeleteCompanys(ids){
        return new Promise((resolve, reject) => {
			del('companies/batch/'+ ids)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies/batch/" + ids)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 删除企业信息
	deleteCompany(id){
		return new Promise((resolve, reject) => {
			del('companies/'+ id)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies/" + id)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据公司Id上传资格证图片
	uploadQualification(id,param,config){
		return new Promise((resolve, reject) => {
			post('companies/'+ id +'/file',param,config)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ",地址:companies/" + id + '/file')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}
}

export default CompanyService