import axios from "axios";
import {
    get,
    put,
    post
} from "../utils/http";

const isRealData = require("../../static/serverconfig").isRealData;

var TunnelService = {
    // 获取所有管廊（名字和id）
    getTunnels: function () {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                get("/tunnels").then(res => {
                        let {
                            code,
                            data,
                            msg
                        } = res.data;
                        if (code == 200) {
                            resolve(data);
                        } else {
                            reject(msg + ",地址:/tunnels");
                        }
                    })
                    .catch(error => {
                        reject(error.response.status + "  " + error.response.data);
                    });
            } else {
                let data = [{
                        id: 1,
                        name: "管廊1"
                    },
                    {
                        id: 2,
                        name: "管廊2"
                    },
                    {
                        id: 3,
                        name: "管廊3"
                    },
                    {
                        id: 4,
                        name: "管廊4"
                    }
                ];
                resolve(data);
            }
        });
    },
    // 根据tunnelId获取该管廊内的管舱信息
    getStoresByTunnelId: function (tunnelId) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                get("tunnels/" + tunnelId + "/stores").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/tunnels" + tunnelId + "/stores");
                    }
                });
            } else {
                let data = [{
                        camera: null,
                        crtTime: 1535611490000,
                        id: 1,
                        name: "燃气仓",
                        storeType: {
                            crtTime: 1535611490000,
                            id: 1,
                            name: "燃气仓"
                        },
                        tunnel: {
                            id: 1,
                            name: "管廊1"
                        }
                    },
                    {
                        camera: null,
                        crtTime: 1535611490000,
                        id: 2,
                        name: "污水仓",
                        storeType: {
                            crtTime: 1535611490000,
                            id: 2,
                            name: "污水仓"
                        },
                        tunnel: {
                            id: 1,
                            name: "管廊1"
                        }
                    },
                    {
                        camera: null,
                        crtTime: 1535611490000,
                        id: 3,
                        name: "电力仓",
                        storeType: {
                            crtTime: 1535611490000,
                            id: 3,
                            name: "电力仓"
                        },
                        tunnel: {
                            id: 1,
                            name: "管廊1"
                        }
                    },
                    {
                        camera: null,
                        crtTime: 1535611490000,
                        id: 4,
                        name: "综合仓",
                        storeType: {
                            crtTime: 1535611490000,
                            id: 4,
                            name: "综合仓"
                        },
                        tunnel: {
                            id: 1,
                            name: "管廊1"
                        }
                    }
                ];
                resolve(data);
            }
        });
    },
    // 根据tunnelId获取该管廊内的区域信息
    getAreasByTunnelId: function (tunnelId) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                get("tunnels/" + tunnelId + "/areas").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/tunnels" + tunnelId + "/areas");
                    }
                });
            } else {
                let data = [{
                    camera: null,
                    crtTime: 1535611490000,
                    id: 1,
                    location: "1",
                    name: "1区",
                    tunnel: {
                        id: 1,
                        name: "管廊1"
                    }
                }];
                resolve(data);
            }
        });
    },
    // 获取所有管廊（树形结构）
    getTunnelsTree: function () {
        return new Promise((resolve, reject) => {
            get("tunnels/tree").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/tree");
                }
            });
        });
    },
    // 获取所有管廊
    getTunnelsAll: function () {
        return new Promise((resolve, reject) => {
            get("tunnels/all").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/all");
                }
            });
        });
    },
    // 根据管舱IDs返回管舱详细信息
    getSectionsByIds: function (ids) {
        return new Promise((resolve, reject) => {
            get("sections/batch/" + ids).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:sections/batch/" + id);
                }
            });
        });
    },
    // 根据storeId获取管舱区段信息
    getSectionsByStoreId: function (storeId) {
        return new Promise((resolve, reject) => {
            get("tunnels/stores/" + storeId + "/sections").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/stores/" + storeId + "/sections");
                }
            });
        });
    },
    // 根据tunnelId获取管廊详细信息
    getTunnelDetailByTunnelId: function (tunnelId) {
        return new Promise((resolve, reject) => {
            get("tunnels/" + tunnelId + "/detail").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/" + tunnelId + "/detail");
                }
            });
        });
    },
    // 根据tunnelID获取该管廊内管舱数
    getStoresCountByTunnelId: function (tunnelId) {
        return new Promise((resolve, reject) => {
            get("tunnels/" + tunnelId + "/stores/count").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/" + tunnelId + "/stores/count");
                }
            });
        });
    },
    // 根据tunnelID获取该管廊内管舱区段数
    getSectionsCountByTunnelId: function (tunnelId) {
        return new Promise((resolve, reject) => {
            get("tunnels/" + tunnelId + "/sections/count").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + ",地址:tunnels/" + tunnelId + "/sections/count");
                }
            });
        });
    },
    // 获取管廊告警数量
    getTunnelAlarmCount() {
        return new Promise((resolve, reject) => {
            get("tunnel/alarmcount").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject("msg" + msg);
                }
            });
        });
    },
    // 区域分页查询
    areaDatagird: function (params) {
        return new Promise((resolve, reject) => {
            post("/areas/datagrid", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/areas/datagrid");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 管舱分页查询
    storeDatagrid: function (params) {
        return new Promise((resolve, reject) => {
            post("/tunnels/stores/datagrid", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/tunnels/stores/datagrid");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 获取区域下所有的仓
    getStoresByAreaId: function (params) {
        return new Promise((resolve, reject) => {
            post("/tunnels/stores/sections/condition", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;

                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/tunnels/stores/sections/condition");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 根据相机位置获取所处的管仓
    getStorePosition: function (params) {
        return new Promise((resolve, reject) => {
            post("/sections/xyz", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/sections/xyz");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 获取管舱类型列表
    getStoreTypeList: function () {
        return new Promise((resolve, reject) => {
            get("/store-type/list").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/store-type/list");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    //获取管廊内区域
    getTunnelArea: function (tunnelId) {
        return new Promise((resolve, reject) => {
            get("tunnels/" + tunnelId + "/areas").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:/tunnels/" + tunnelId + "/areas");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 批量添加区段
    batchCreateSections(params) {
        return new Promise((resolve, reject) => {
            post("sections/batch", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:sections/batch");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // 批量添加区段经纬度
    batchAddPositions(params) {
        return new Promise((resolve, reject) => {
            put("sections/batch", params).then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:sections/batch");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    },
    // VM中获得管廊的总览信息
    getVmTunnelsMessage() {
        // 地址 tunnels/message
        // let res = [{
        //     name: "运行中",
        //     value: 2,
        //     unit: "条",
        //     percent: '40%'
        // }, {
        //     name: "建设中",
        //     value: 2,
        //     unit: "条",
        //     percent: '50%'
        // }, {
        //     name: "规划中",
        //     value: 1,
        //     unit: "条",
        //     percent: '50%'
        // }];
        return new Promise((resolve, reject) => {
            get("tunnels/message").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:tunnels/message");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
            // resolve(res)
        });
    },
    // VM中获得管线的总览信息
    getVmLineMessage() {
        // 地址 lines/message
        let res = [{
                name: "电力管线",
                value: 15,
                unit: "km",
                percent: "72%"
            },
            {
                name: "通信管线",
                value: 13,
                unit: "km",
                percent: "67.5%"
            },
            {
                name: "燃气管线",
                value: 12,
                unit: "km",
                percent: "64%"
            }
        ];
        return new Promise((resolve, reject) => {
            //     get("lines/message")
            //     .then(res => {
            //         let {
            //             code,
            //             data,
            //             msg
            //         } = res.data;
            //         if (code == 200) {
            //             resolve(data);
            //         } else {
            //             reject(msg + ",地址:lines/message");
            //         }
            //     })
            //     .catch(error => {
            //         reject(error.response.status + "  " + error.response.data);
            //     });
            resolve(res);
        });
    },
    // VM中或的管廊运行时间数据
    getVmRunMessage() {
        // 地址 tunnels/run-message
        // let res = {
        //     total: 50,
        //     safe: 14
        // };
        return new Promise((resolve, reject) => {
            get("tunnels/run-message").then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + ",地址:run/message");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
            // resolve(res)
        });
    },
    //根据areaId和storeId获取section
    getSectionByAreaIdStoreId(storeId, areaId) {
        return new Promise((resolve, reject) => {
            get("stores/" + storeId + "/areas/" + areaId + "/sections").then(res => {
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
    //根据
    getAreaInfo(id) {
        return new Promise((resolve, reject) => {
            get('areas/' + id).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data
                if (code == 200) {
                    resolve(data)
                }else{
                    reject(msg+"地址：areas/"+id)
                }
            })
        })
    },
    getStoreInfo(id){
        return new Promise((resolve, rejresolve, reject) => {
            get('tunnels/stores/'+id).then(res=>{
                let{ code, data, msg } = res.data
                if( code == 200 ){
                    resolve(data)
                }else{
                    reject(msg+"地址：tunnels/stores/"+id)
                }
            })
        })
    },
    getRelatedUnits() {
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
                    reject(msg + "getRelatedUnits")
                }
            })
        })
    },
    getPointLinghtData() {
        return new Promise((resolve, reject) => {
            // tunnel_lights
            get('tunnel_lights').then(res => {
                    let {
                        code,
                        data,
                        msg
                    } = res.data
                    if (code == 200) {
                        resolve(data)
                    } else {
                        reject(msg + "getRelatedUnits")
                    }
                })
                .catch(() => {
                    resolve([{
                            lon: 112.524040,
                            lat: 37.703632,
                            height: -1.35,
                        },
                        {
                            lon: 112.524040,
                            lat: 37.703632,
                            height: -4.35,
                        }
                    ])
                })
        })
    },
};
export {
    TunnelService
};