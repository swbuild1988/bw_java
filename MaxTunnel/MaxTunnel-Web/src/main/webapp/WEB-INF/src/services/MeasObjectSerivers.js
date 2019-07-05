import axios from "axios";
import {
	get,
	put,
	del,
	post
} from "../utils/http";

//监测对象数据服务
var MeasObjServer = {
	// 根据Ids查询监测对象
	getObjByIds: function (ids) {
		return new Promise((resolve, reject) => {
			post("/measobjs/ids", ids).then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data);
				} else {
					reject(msg + ",地址:/measobjs/ids");
				}
			});
		});
	},
	// 根据objId查询监测对象详情
	getObjById: function (id) {
		return new Promise((resolve, reject) => {
			get("measobjs/" + id).then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data);
				} else {
					reject(msg + ",地址:measobjs/" + id);
				}
			});
		});
	},
	// 查询监测对象数据
	measObjDataGrid: function (prams) {
		return new Promise((resolve, reject) => {
			post("measobjs/datagrid", prams).then(res => {
				let {
					code,
					data,
					msg
				} = res.data;
				if (code == 200) {
					resolve(data);
				} else {
					reject(msg + ",地址:measobjs/datagrid");
				}
			});
		});
	},
	// 添加监测对象
	addMeasObj: function (params) {
		return new Promise((resolve, reject) => {
			post("/measobjs", params).then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:/measobjs");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 更新监测对象
	updateMeasObj: function (params) {
		return new Promise((resolve, reject) => {
			put("measobjs", params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	batchPostMeasObjs: function (objs) {
		return new Promise((resolve, reject) => {
			post("measobjs/batch", objs)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 批量删除监测对象
	batchDeleteMeasObjs: function (ids) {
		return new Promise((resolve, reject) => {
			del("measobjs/batch/" + ids)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs/batch/" + ids);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 监测对象ID查重
	checkObjId: function (objId) {
		return new Promise((resolve, reject) => {
			get("measobjs/" + objId + "/ajax")
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs/" + this.formValidate.id + "/ajax");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 监测对象映射分页查询
	measObjMapDatagird: function (params) {
		return new Promise((resolve, reject) => {
			post("measobj-map/datagrid", params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobj-map/datagrid");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 添加监测对象映射
	addMeasObjMap: function (params) {
		return new Promise((resolve, reject) => {
			post("measobjs-map", params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs-map");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 更新监测对象映射
	updateMeasObjMap: function (params) {
		return new Promise((resolve, reject) => {
			put("measobjs-map", params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs-map");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 根据Id获取监测对象映射详情
	getObjMapById: function (id) {
		return new Promise((resolve, reject) => {
			get("measobjs-map/" + id)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs-map/" + id);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 根据objectId和inputValue获取映射对象
	getMapObj: function (objId, inputValue) {
		return new Promise((resolve, reject) => {
			get("measobjs-map/" + objId + "/inputvalue/" + inputValue)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(
							msg + ",地址:measobjs-map/" + objId + "/inputvalue/" + inputValue
						);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 批量删除监测对象映射
	batchDeleteObjMap: function (ids) {
		return new Promise((resolve, reject) => {
			del("measobjs-map/batch/" + ids)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs-map/batch/" + ids);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	//根基Id查询视频流对象
	getVideoByIds: function (ids) {
		return new Promise((resolve, reject) => {
			get("text" + ids)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:" + ids);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	// 批量添加监测对象映射
	batchAddMap(params) {
		return new Promise((resolve, reject) => {
			post("measobj-map/batch", params)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobj-map/batch", params);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},

	//修改设备状态
	changeEquimentStatus(params) {
		return new Promise((resolve, reject) => {
			get("sub-sys/action/obj/" + params.id + "/inputval/" + params.status)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:sub-sys/action/obj/" + params.id + "/inputval/" + params.status);
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		});
	},
	//获取今日温度，湿度，含氧量最值
	getToDayExtreDatas() {
		return new Promise((resolve, reject) => {
			get('extreme-datas').then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + ',地址:extreme-datas')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		});
	},
	//获取今日监测对象触发次数及与昨日比是否增长
	getMeasTriggerCounts() {
		return new Promise((resolve, reject) => {
			get("meas-switched-counts").then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:meas-switched-counts");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
			// let data = [{
			//     name: "电子井盖打开",
			//     value: 12,
			//     unit: "次",
			//     isRise: false
			// }, {
			//     name: "门禁触发",
			//     value: 12,
			//     unit: "次",
			//     isRise: false
			// }, {
			//     name: "红外探测器触发",
			//     value: 12,
			//     unit: "次",
			//     isRise: true
			// }]
			// resolve(data)
		});
	},
	//更areaId, storeId获取section内所有的视频
	getSectionVideos(storeId, areaId) {
		return new Promise((resolve, reject) => {
			get('measobjs/' + storeId + '/' + areaId + '/videos').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + "地址：")
				}
			})
		})
	},
	// 根据storeId，areaId获取所有监测对象
	getMeasObjects(storeId, areaId) {
		return new Promise((resolve, reject) => {
			get('/stores/' + storeId + '/areas/' + areaId + '/measobjs')
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：/stores/' + storeId + '/areas/' + areaId + '/measobjs')
					}
				})
				.catch(err => {
					reject(err.response.status + ' ' + err.response.data)
				})
			// let data = [{
			// 		curValue: 0,
			// 		datatypeId: 3,
			// 		id: 222113201,
			// 		maxValue: null,
			// 		minValue: null,
			// 		name: "声光报警器",
			// 		store: "污水舱进风口",
			// 		time: 1558602699682,
			// 		unit: ""
			// 	},
			// 	{
			// 		id: 2,
			// 		name: '监测对象2',
			// 		cv: 2,
			// 		time: '2019-09-12',
			// 		type: 2
			// 	},
			// 	{
			// 		id: 3,
			// 		name: '监测对象3',
			// 		cv: 1,
			// 		time: '2019-09-12',
			// 		type: 2
			// 	},
			// 	{
			// 		id: 4,
			// 		name: '监测对象4',
			// 		cv: 0,
			// 		time: '2019-09-12',
			// 		type: 2
			// 	},
			// ]
			// resolve(data)
		})
	}
};

export {
	MeasObjServer
};