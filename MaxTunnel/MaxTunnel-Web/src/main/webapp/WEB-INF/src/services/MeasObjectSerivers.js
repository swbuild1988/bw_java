import axios from "axios";
import {
	get,
	put,
	del,
	post
} from "../utils/http";
import store from "../store";

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
	//批量修改监测对象
	batchUpdateMeasObjs(params) {
		return new Promise((resolve, reject) => {
			put("measobjs/batch", params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + "地址：measobjs/batch")
				}
			})
		})
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

	getMeasObjCurValue(id) {
		return new Promise((resolve, reject) => {
			get("measobjs/" + id + "/curValues")
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:measobjs/" + id + "/curValues");
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
			get("sub-sys/output/obj/" + params.id + "/code/" + params.code)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:sub-sys/output/obj/" + params.id + "/code/" + params.code);
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
	// 获取当前复杂对象的值
	getComplexobjDatas() {
		return new Promise((resolve, reject) => {
			get("complexobj-datas").then(res => {
					let {
						code,
						data,
						msg
					} = res.data;
					if (code == 200) {
						resolve(data);
					} else {
						reject(msg + ",地址:complexobj-datas");
					}
				})
				.catch(error => {
					reject(error.response.status + "  " + error.response.data);
				});
		})
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
		})
	},
	//查询所有监测对象 不分页
	getAllMeasObjs() {
		return new Promise((resolve, reject) => {
			get('measobjs').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + '地址：measobjs')
				}
			})
		})
	},
	// 获取批量导入监测对象所需参数
	getBatchAddObjParams() {
		return new Promise((resolve, reject) => {
			get('config/xml/tunnel-param/equipment-info').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/tunnel-param/equipment-info')
				}
			})
		})
	},
	// 修改批量导入监测对象所需管廊参数
	updateTunnelParams(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/tunnel-param', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/tunnel-param')
				}
			})
		})
	},
	// 修改批量导入监测对象所需设备参数
	updateEqiuParams(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/equipment-info', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/equipment-info')
				}
			})
		})
	},
	// 获取监测对象复杂类型
	getObjComplexType() {
		return new Promise((resolve, reject) => {
			get('config/xml/complex-object-converts').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/complex-object-converts')
				}
			})
		})
	},
	// 修改监测对象复杂类型
	updateObjComplexType(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/complex-object-converts', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/complex-object-converts')
				}
			})
		})
	},
	// 获取AI数据小数点位数
	getAIDataDecimal() {
		return new Promise((resolve, reject) => {
			get('config/xml/decimal').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/decimal')
				}
			})
		})
	},
	// 修改AI数据小数点位数
	updateAIDataDecimal(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/decimal', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/decimal')
				}
			})
		})
	},
	getMeasObjExtremun() {
		return new Promise((resolve, reject) => {
			get('config/xml/objTypeParams').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/objTypeParams')
				}
			})
		})
	},
	updateMeasObjExtremun(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/objTypeParams', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/objTypeParams')
				}
			})
		})
	},
	getMeasObjTypes() {
		return new Promise((resolve, reject) => {
			get('config/xml/objTypes').then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/objTypes')
				}
			})
		})
	},
	updateMeasObjTypes(params) {
		return new Promise((resolve, reject) => {
			post('config/xml/objTypes', params).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'config/xml/objTypes')
				}
			})
		})
	},
	updateMeasObjsActived(tunnelId, areaId, storeId, actived) {
		let param = {
			tunnelId: tunnelId == 0 ? null : tunnelId,
			areaId: areaId == 0 ? null : areaId,
			storeId: storeId == 0 ? null : storeId,
			actived: actived
		}
		return new Promise((resolve, reject) => {
			put('measobjs/update_actived', param).then(res => {
				let {
					code,
					data,
					msg
				} = res.data
				if (code == 200) {
					resolve(data)
				} else {
					reject(msg + 'measobjs/update_actived')
				}
			})
		})
	}
};

export {
	MeasObjServer
};