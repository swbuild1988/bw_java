import axios from 'axios'

var OperationLogService = {
	// 操作日志信息分页查询
	logDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/operationlogs/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/operationlogs/datagrid')
				}
			})
		})
	},
	// 删除操作日志信息
	deleteLog: function(logId) {
		return new Promise((resolve, reject) => {
			axios.delete('/operationlogs/' + logId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/operationlogs/' + logId)
				}
			})
		})
	}
}

export {
	OperationLogService
}