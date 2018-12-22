import axios from 'axios'

var energyConsumptionService = {
	// 根据管廊ID，起始时间获取能耗总值
	getECTotal: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('tunnels/energies-det/total', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/energies-det/total')
				}
			})
		})
	},
	// 根据管廊ID，起始时间获取各能耗类别值
	getECCategory: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('tunnels/energies-det/ratio', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/energies-det/ratio')
				}
			})
		})
	},
	// 根据管廊ID和时间周期获取能耗分类详情（曲线表）
	getECCategoryDetail: function(tunnelId, timeCycle) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/' + tunnelId + '/energies-det/' + timeCycle).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/' + tunnelId + '/energies-det/' + timeCycle)
				}
			})
		})
	},
	// 管廊能耗分页查询(表格)
	getECDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/tunnels/energies/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnels/energies/datagrid')
				}
			})
		})
	},
	// 根据管廊ID，起始时间获取能耗信息(曲线表)
	getECInfo: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('tunnels/energies/time/condition', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：tunnels/energies/time/condition')
				}
			})
		})
	},
	// 获取历史总能耗
	getECHistoryTotal: function() {
		return new Promise((resolve, reject) => {
			axios.get('/tunnels/energies/timetype/1').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnels/energies/timetype/1')
				}
			})
		})
	},
	// 获取本年总能耗
	getECYearTotal: function() {
		return new Promise((resolve, reject) => {
			axios.get('/tunnels/energies/timetype/2').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnels/energies/timetype/2')
				}
			})
		})
	},
	// 获取本月总能耗
	getECMonthTotal: function() {
		return new Promise((resolve, reject) => {
			axios.get('/tunnels/energies/timetype/3').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnels/energies/timetype/3')
				}
			})
		})
	},
	// 根据起始时间查询表数据
	getECDetail: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/tunnels/energies/time', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：/tunnels/energies/time')
				}
			})
		})
	}
}

export {
	energyConsumptionService
}