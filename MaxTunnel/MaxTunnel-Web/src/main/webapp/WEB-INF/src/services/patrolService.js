import axios from 'axios'

var PatrolService = {
	// 获取责任班组
	getGroups: function() {
		return new Promise((resolve, reject) => {
			axios.get('/inspection-groups').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址/inspection-groups')
				}
			})
		})
	},
	// 添加巡检计划
	addPatrolPlan: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/inspection-plans', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址/inspection-plans')
				}
			})
		})
	},
	// 根据planId获取该巡检计划详情
	getPDetailByPlanId: function(planId) {
		return new Promise((resolve, reject) => {
			axios.get('inspection-plans/' + planId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:inspection-plans/' + planId)
				}
			})
		})
	},
	// 获取年度计划数、本月计划、本月已完成巡检任务数、本月待巡检任务数、计划信息列表
	getPatrolCounts: function() {
		return new Promise((resolve, reject) => {
			axios.get('/inspection-plans/count').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:/inspection-plans/count')
				}
			})
		})
	},
	// 巡检计划分页查询
	patrolPlanDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('inspection-plans/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:inspection-plans/datagrid')
				}
			})
		})
	},
	// 根据taskId获取该巡检任务详情
	getTDetailByPlanId: function(taskId) {
		return new Promise((resolve, reject) => {
			axios.get('/inspection-tasks/' + taskId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:/inspection-tasks/' + taskId)
				}
			})
		})
	},
	// 获取所有员工
	getStaffs: function() {
		return new Promise((resolve, reject) => {
			axios.get('/staffs').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:/staffs')
				}
			})
		})
	},
	// 分配巡检任务
	distributeTask: function(taskId, staffId) {
		return new Promise((resolve, reject) => {
			axios.get('maintenance-order/' + taskId + '/maintenance-person/' + staffId).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:maintenance-order/' + taskId + '/maintenance-person/' + staffId)
				}
			})
		})
	},
	// 巡检任务分页查询
	patrolTasksDatagrid: function(params) {
		return new Promise((resolve, reject) => {
			axios.post('/inspection-tasks/all', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址:/inspection-tasks/all')
				}
			})
		})
	}
}

export {
	PatrolService
}