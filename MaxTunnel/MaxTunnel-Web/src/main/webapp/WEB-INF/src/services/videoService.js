import axios from 'axios'
import {
	get,
	put,
	post,
	del
} from "../utils/http";
const isRealData = require('../../static/serverconfig').isRealData

var VideoService = {
	// 获取告警关联视频对象路由表
	getAlarmVideoRouter: function (id) {
		return new Promise((resolve, reject) => {
			get("object-bind/" + id + "/videos")
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:' + '/videos')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据tunnelId获取该管廊内的摄像头
	getCamerasByTunnelId: function (tunnelId) {
		return new Promise((resolve, reject) => {
			get('tunnels/' + tunnelId + '/videos')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:tunnels/' + tunnelId + '/videos')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据tunnelId / storeId / areaId 获取特定位置的摄像头
	getCamerasByConditions: function (condition) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				post('videos/condition', condition)
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ',地址:videos/condition')
						}
					})
					.catch(error => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				let data = [{
					id: 7001,
					name: "摄像头1",
					url: "192.168.6.156:8078",
					positionSupport: true,
					description: "A Camera for tunnel1",
					storeId: 1,
					areaId: 1
				}, {
					id: 7002,
					name: "摄像头2",
					url: "192.168.6.156:8078",
					positionSupport: false,
					description: "A Camera for tunnel2",
					storeId: 1,
					areaId: 1
				}, {
					id: 7003,
					name: "摄像头3",
					url: "192.168.6.156:8078",
					positionSupport: true,
					description: "A Camera for tunnel3",
					storeId: 1,
					areaId: 1
				}]
				resolve(data)
			}
		})
	},
	// 摄像头开始移动
	cameraMove: function (videoId, direction) {
		return new Promise((resolve, reject) => {
			get('videos/' + videoId + '/move/' + direction)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:videos/' + videoId + '/move/' + direction)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 摄像头停止移动
	cameraStop: function (videoId) {
		return new Promise((resolve, reject) => {
			get('videos/' + videoId + '/stop')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:videos/' + videoId + '/stop')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据cameraId获取该相机预置位
	getPresetsByCameraId: function (cameraId) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				get('videos/' + cameraId + '/presets')
					.then(res => {
						let {
							code,
							data,
							msg
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ',地址:videos/' + cameraId + '/presets')
						}
					})
					.catch(error => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				let data = ['预置位1', '预置位2', '预置位4',
					'预置位预置5预置位预置', '预置位预置位预置位6'
				]
				resolve(data)
			}
		})
	},
	// 摄像头到达预置位
	goToPreset: function (cameraId, presetName) {
		return new Promise((resolve, reject) => {
			get('videos/' + cameraId + '/presets/' + presetName + '/goto')
				.then(res => {
					resolve(res.data)
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 新增预置位
	addPreset: function (cameraId, params) {
		return new Promise((resolve, reject) => {
			post('videos/' + cameraId + '/presets', params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:videos/' + cameraId + '/presets')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 编辑预置位摄像头位置
	editPreset: function (cameraId, params) {
		return new Promise((resolve, reject) => {
			put('videos/' + cameraId + '/presets', params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:videos/' + cameraId + '/presets')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 删除预置位
	deletePreset: function (cameraId) {
		return new Promise((resolve, reject) => {
			del('videos/' + cameraId + '/presets', {
					data: param
				}).then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:videos/' + cameraId + '/presets')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据3d位置获取对应虚拟巡检中的摄像头
	getVICameras: function (position) {
		return new Promise((resolve, reject) => {
			post('virual-inspection/video', position)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:virual-inspection/video')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 根据相机获取section信息
	getSections: function (position) {
		return new Promise((resolve, reject) => {
			post('sections/gps', position)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:sections/gps')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 摄像机预置位和3d位置匹配
	matchPresetAnd3D: function (videoPreset) {
		return new Promise((resolve, reject) => {
			post('preset-3d', videoPreset)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:preset-3d')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 批量添加h5Stream配置
	batchAddH5StreamConfig: function (params) {
		return new Promise((resolve, reject) => {
			post('h5/api/addsrc', params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:h5/api/addsrc')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	},
	// 获取当前位置附近视频
	getNearbyVideos(params) {
		return new Promise((resolve, reject) => {
			post('videos/gps', params)
				.then(
					result => {
						let {
							code,
							data,
							msg
						} = result.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ' 地址：videos/gps')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
		})
	},
	// 历史照片分页
	historyPhotosDatagrid(params) {
		return new Promise((resolve, reject) => {
			post('snaps/datagrid', params)
				.then(
					result => {
						let {
							code,
							data,
							msg
						} = result.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ' 地址：snaps/datagrid')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
			// let data = [
			// 	{
			// 		strStartTime: "2019-03-26T13:52:43Z",
			// 		strPath: "/static/img/robotTop.2ef6c6f.png",
			// 		strToken: '22123010',
			// 		strDuration: '0'
			// 	},
			// 	{
			// 		strStartTime: "2019-03-26T14:52:43Z",
			// 		strPath: "/static/img/robotTop.2ef6c6f.png",
			// 		strToken: '22123011',
			// 		strDuration: '0'
			// 	},
			// 	{
			// 		strStartTime: "2019-03-26T15:52:43Z",
			// 		strPath: "/static/img/robotTop.2ef6c6f.png",
			// 		strToken: '22123012',
			// 		strDuration: '0'
			// 	}
			// ]
			// resolve(data)
		})
	},
	// 历史照片全部
	allHistoryPhotoes(params) {
		return new Promise((resolve, reject) => {
			post('snaps/condition', params)
				.then(
					result => {
						let {
							code,
							data,
							msg
						} = result.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + ' 地址：snaps/condition')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
		})
	},
	// 获取 预览和下载图片
	getPhoto(param) {
		return new Promise((resolve, reject) => {
			axios.post('snaps/tp', param, {
					responseType: "arraybuffer"
				})
				.then(
					result => {
						let {
							status,
							data
						} = result
						if (status == 200) {
							resolve(data)
						} else {
							reject('地址：snaps/tp')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
		})
	},
	// 根据storeId，areaId获取该区段及父类视频列表
	getVideoList(storeId, areaId) {
		return new Promise((resolve, reject) => {
			get('stores/' + storeId + '/areas/' + areaId + '/videos')
				.then(
					result => {
						let {
							code,
							data
						} = result.data
						if (code == 200) {
							resolve(data)
						} else {
							reject('地址：stores/' + storeId + '/areas/' + areaId + 'videos')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
		})
	},
	//插入上传H5配置文件
	insertH5StreamConfig(params){
		return new Promise((resolve, reject) => {
			post('h5/api/add', params).then(res=>{
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：h5/api/add")
				}
			})
		})
	},
	//获取下载数据不分页
	getDownloadData(){
		return new Promise((resolve, reject) => {
			get('h5/api/src').then(res=>{
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：h5/api/src")
				}
			})
		})
	},
	getSceneVideos(){
		return new Promise((resolve, reject) => {
			get('/video_scenes')
				.then(
					result => {
						let {
							code,
							data
						} = result.data
						if (code == 200) {
							resolve(data)
						} else {
							reject('地址：video_scenes')
						}
					}
				)
				.catch(error => {
					reject(error.response.status + ' ' + error.response.data)
				})
		})
	},
	//分页查询
	getH5Data(params){
		return new Promise((resolve, reject) => {
			post('h5/api/src/datagrid', params).then( res=> {
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：h5/api/src/datagrid")
				}
			})
		})
	},
	//获取详情
	getInfo(id){
		return new Promise((resolve, reject) => {
			get('videos/'+id).then(res=>{
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：videos/"+id)
				}
			})
		})
	},
	// 提交修改
	submitEdit(params){
		return new Promise((resolve, reject) => {
			put('h5/api/update', params).then(res=>{
				let{ code, data, msg } = res.data
				if( code == 200 ){
					resolve(data)
				}else{
					reject(msg+"地址：h5/api/update")
				}
			})
		})
	}
}

export {
	VideoService
}