import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var VideoService = {
	// 根据tunnelId获取该管廊内的摄像头
	getCamerasByTunnelId: function(tunnelId) {
		return new Promise((resolve, reject) => {
			axios.get('tunnels/' + tunnelId + '/videos').then(res => {
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
		})
	},
	// 根据tunnelId / storeId / areaId 获取特定位置的摄像头
	getCamerasByConditions: function(condition) {
		return new Promise((resolve, reject) => {
			axios.post('videos/condition', condition).then(res => {
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
		})
	},
	// 摄像头开始移动
	cameraMove: function(videoId, direction) {
		return new Promise((resolve, reject) => {
			axios.get('videos/' + videoId + '/move/' + direction).then(res => {
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
		})
	},
	// 摄像头停止移动
	cameraStop: function(videoId) {
		return new Promise((resolve, reject) => {
			axios.get('videos/' + videoId + '/stop').then(res => {
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
		})
	},
	// 根据cameraId获取该相机预置位
	getPresetsByCameraId: function(cameraId) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get('videos/' + cameraId + '/presets').then(res => {
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
			} else {
				let data = ['预置位1', '预置位2', '预置位4',
					'预置位预置5预置位预置', '预置位预置位预置位6'
				]
				resolve(data)
			}
		})
	},
	// 摄像头到达预置位
	goToPreset: function(cameraId, presetName) {
		return new Promise((resolve, reject) => {
			axios.get('videos/' + cameraId + '/presets/' + presetName + '/goto').then(res => {
				resolve(res.data)
			})
		})
	},
	// 新增预置位
	addPreset: function(cameraId, params) {
		return new Promise((resolve, reject) => {
			axios.post('videos/' + cameraId + '/presets', params).then(res => {
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
		})
	},
	// 编辑预置位摄像头位置
	editPreset: function(cameraId, params) {
		return new Promise((resolve, reject) => {
			axios.put('videos/' + cameraId + '/presets', params).then(res => {
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
		})
	},
	// 删除预置位
	deletePreset: function(cameraId) {
		return new Promise((resolve, reject) => {
			axios.delete('videos/' + cameraId + '/presets', {
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
		})
	},
	// 根据3d位置获取对应虚拟巡检中的摄像头
	getVICameras: function(position) {
		return new Promise((resolve, reject) => {
			axios.post('virual-inspection/video', position).then(res => {
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
		})
	},
	// 根据相机获取section信息
	getSections: function(position) {
		return new Promise((resolve, reject) => {
			axios.post('sections/gps', position).then(res => {
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
		})
	},
	// 摄像机预置位和3d位置匹配
	matchPresetAnd3D: function(videoPreset) {
		return new Promise((resolve, reject) => {
			axios.post('preset-3d', videoPreset).then(res => {
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
		})
	}
}

export {
	VideoService
}