import axios from 'axios'
import { get,del,post,put } from "../utils/http";

var RelatedUnitService = {
	// 根据ID获取相关单位信息
	getRelatedUnitInfoById: function(id) {
		return new Promise((resolve, reject) => {
			get('relatedunits/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/")
				}
			})
		})
	},
	// 添加相关单位
	addRelatedUnit: function(params) {
		return new Promise((resolve, reject) => {
			post('relatedunits', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits")
				}
			})
		})
	},
	// 修改相关单位
	updateRelatedUnit: function(params) {
		return new Promise((resolve, reject) => {
			put('relatedunits', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits")
				}
			})
		})
	},
	// 相关单位分页查询
	relatedUnitDataGrid: function(params) {
		return new Promise((resolve, reject) => {
			post('relatedunits/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/datagrid")
				}
			})
		})
	},
	// 删除相关单位
	deleteRelatedUnit: function(id) {
		return new Promise((resolve, reject) => {
			del('relatedunits/' + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:relatedunits/" + id)
				}
			})
		})
	},
	// 获取大屏各重要点位置
	getVMUnits(){
		return new Promise((resolve, reject) => {
			get('config/xml/units').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:config/xml/units")
				}
			})
		})
	},
	// 修改大屏各重要点位置
	updateVMUnits(params){
		return new Promise((resolve, reject) => {
			post('config/xml/units',params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ",地址:config/xml/units")
				}
			})
		})
	}
}

export {
	RelatedUnitService
}