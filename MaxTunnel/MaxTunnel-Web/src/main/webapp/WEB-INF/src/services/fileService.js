import axios from 'axios'
import { get,del,post } from "../utils/http";

var FileService = {
	// 文件信息分页查询
	filesDataGrid: function(params) {
		return new Promise((resolve, reject) => {
			post('files/datagrid', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:files/datagrid')
				}
			})
		})
	},
	// 删除文件
	deleteFiles: function(ids) {
		return new Promise((resolve, reject) => {
			del('files/' + ids).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:files/')
				}
			})
		})
	},
	// 下载文件
	downloadFile: function(id) {
		return new Promise((resolve, reject) => {
			get('files/download/' + id, {
				responseType: "arraybuffer"
			}).then(res => {
				if (res.code && res.code != 200) {
					reject('Download Failed')
				} else {
					resolve(res.data)
				}
			})
		})
	},
	// 获取文件类型树结构
	getDocTypeTree(params){
		return new Promise((resolve, reject) => {
			post('doc-types/condition',params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:doc-types/condition')
				}
			})
		})
	},
	// 添加文件类型节点
	addDocType(params){
		return new Promise((resolve, reject) => {
			post('doc-types',params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:doc-types')
				}
			})
		})
	},
	// 删除文件类型节点
	deleteDocType(id){
		return new Promise((resolve, reject) => {
			del('doc-types/'+id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + ',地址:doc-types/'+id)
				}
			})
		})
	}
}

export {
	FileService
}