import axios from 'axios'

var MonitorDataService = {
	// 根据tunnelId和monitorType获取该管廊内监测内容数据最大值
	getMaxMonitorData: function(tunnelId, monitorType) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/' + tunnelId + "/" + monitorType + '/max-cv').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/' + tunnelId + "/" + monitorType + '/max-cv')
				}
			})
		})
	},
	// 获取监测数据
	getMonitorData: function() {
		return new Promise((resolve, reject) => {
			axios.get('sections/ai/standard-cv').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：sections/ai/standard-cv')
				}
			})
		})
	},
	// 根据storeId获取该管舱区段的监测数据
	getMonitorDataByStoreId: function(storeId) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/stores/sections/' + storeId + '/ai/cv').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/stores/sections/' + storeId + '/ai/cv')
				}
			})
		})
	},
	// 详情面板数据分页查询
	objDetailDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('measobjs/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：measobjs/datagrid')
				}
			})
		})
	},
	// 根据ids和dataTypeId获取检测数据
	getDataByIdsAndDataType: function(ids, dataTypeId) {
		return new Promise((resolve, reject) => {
			axios.get('measobjs/' + ids + '/' + dataTypeId + '/cache-cv').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：measobjs/' + ids + '/' + dataTypeId + '/cache-cv')
				}
			})
		})
	}
}

export {
	MonitorDataService
}