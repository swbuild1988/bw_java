import axios from 'axios'

var MaxViewService = {
	// MaxView终端配置分页查询
	TerminalDatagird: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('maxview/datagrid', params)
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
							reject(msg + '地址：maxview/datagrid')
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
			axios.post('maxview', params)
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
							reject(msg + '地址：maxview')
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
			axios.get('maxview/' + terminalId)
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
							reject(msg + '地址：maxview/' + terminalId)
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
			axios.put('maxview', params)
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
							reject(msg + '地址：maxview')
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
			axios.delete('maxview/batch/' + ids)
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
							reject(msg + '地址：maxview/batch/')
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