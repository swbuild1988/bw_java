import axios from 'axios'

var EnterGalleryService = {
	// 获取公司列表
	getCompanys: function() {
		return new Promise((resolve, reject) => {
			axios.get('/companies').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/companies')
				}
			})
		})
	},
	// 添加入廊申请
	addEnterGalleryApplication: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/req-historys', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/req-historys')
				}
			})
		})
	},
	// 根据companyId获取部门
	getDepsByCompanyId: function(companyId) {
		return new Promise((resolve, reject) => {
			axios.get('/companies/' + companyId + '/departments').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/companies/' + companyId + '/departments')
				}
			})
		})
	},
	// 根据companyId和departmentId获取工作人员
	getStaffsByComIdAndDepId: function(companyId, depId) {
		return new Promise((resolve, reject) => {
			axios.get('/companies/' + companyId + '/departments/' + depId + '/staffs').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/companies/' + companyId + '/departments/' + depId + '/staffs')
				}
			})
		})
	},
	// 入廊申请信息分页查询
	enterGalleryDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('req-historys/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：req-historys/datagrid')
				}
			})
		})
	},
	// 删除入廊申请信息
	deleteEnterGallery: function(id) {
		return new Promise((resolve, reject) => {
			axios.delete('/req-historys/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/req-historys/' + id)
				}
			})
		})
	},
	// 根据入廊信息ID查询详细信息
	getDetailsById: function(id) {
		return new Promise((resolve, reject) => {
			axios.get('/req-historys/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/req-historys/' + id)
				}
			})
		})
	},
	// 提交入廊人员出廊时间
	putExitTime: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('users/out/req-historys', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：users/out/req-historys')
				}
			})
		})
	}
}

export {
	EnterGalleryService
}