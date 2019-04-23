import axios from "axios";

var CompanyService = {
    // 企业管理分页查询
    companiesDatagrid(param){
        return new Promise((resolve, reject) => {
			axios.post('companies/datagrid',param)
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
		})
    },
    // 根据Id查企业详情
    getcompanyDetail(id){
        return new Promise((resolve, reject) => {
			axios.get('companies/'+ id)
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
			axios.post('companies',param)
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
			axios.put('companies',param)
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
			axios.delete('companies/batch/'+ ids)
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
			axios.delete('companies/'+ id)
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
			axios.post('companies/'+ id +'/file',param,config)
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