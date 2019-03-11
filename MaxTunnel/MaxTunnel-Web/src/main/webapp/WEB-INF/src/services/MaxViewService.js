import axios from 'axios'

var MaxViewService = {
	// MaxView终端配置分页查询
	TerminalDatagird: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('sub-sys/datagrid', params)
				.then(
					res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：sub-sys/datagrid')
						}
					})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 添加终端
	addTerminal: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('sub-sys', params)
				.then(
					res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(res.data)
						} else {
							reject(msg + '地址：sub-sys')
						}
					})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据终端Id获取详情
	getDetailById: function(terminalId) {
		return new Promise((resolve, reject) => {
			axios.get('sub-sys/' + terminalId)
				.then(
					res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：sub-sys/' + terminalId)
						}
					})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 更新终端信息
	updateTerminal: function(params) {
		return new Promise((resolve, reject) => {
			axios.put('sub-sys', params)
				.then(
					res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + 'sub-sys')
						}
					})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 批量删除终端
	batchDeleteTerminals: function(ids) {
		return new Promise((resolve, reject) => {
			axios.delete('sub-sys/batch/' + ids)
				.then(
					res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：sub-sys/batch/')
						}
					})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}
}

export {
	MaxViewService
}