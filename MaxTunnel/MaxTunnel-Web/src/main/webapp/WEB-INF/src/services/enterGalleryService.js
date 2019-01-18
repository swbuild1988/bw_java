import axios from "axios";

var EnterGalleryService = {
    // 获取公司列表
    getCompanys: function() {
        return new Promise((resolve, reject) => {
            axios.get("/companies").then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/companies");
                }
            });
        });
    },
    // 添加入廊申请
    addEnterGalleryApplication: function(params) {
        return new Promise((resolve, reject) => {
            axios.post("/req-historys", params).then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/req-historys");
                }
            });
        });
    },
    // 根据companyId获取部门
    getDepsByCompanyId: function(companyId) {
        return new Promise((resolve, reject) => {
            axios.get("/companies/" + companyId + "/departments").then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(
                        msg + "地址：/companies/" + companyId + "/departments"
                    );
                }
            });
        });
    },
    // 根据companyId和departmentId获取工作人员
    getStaffsByComIdAndDepId: function(companyId, depId) {
        return new Promise((resolve, reject) => {
            axios
                .get(
                    "/companies/" +
                        companyId +
                        "/departments/" +
                        depId +
                        "/staffs"
                )
                .then(res => {
                    let { code, data, msg } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(
                            msg +
                                "地址：/companies/" +
                                companyId +
                                "/departments/" +
                                depId +
                                "/staffs"
                        );
                    }
                });
        });
    },
    // 入廊申请信息分页查询
    enterGalleryDatagrid: function(params) {
        return new Promise((resolve, reject) => {
            axios.post("req-historys/datagrid", params).then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：req-historys/datagrid");
                }
            });
        });
    },
    // 删除入廊申请信息
    deleteEnterGallery: function(id) {
        return new Promise((resolve, reject) => {
            axios.delete("/req-historys/" + id).then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/req-historys/" + id);
                }
            });
        });
    },
    // 根据入廊信息ID查询详细信息
    getDetailsById: function(id) {
        return new Promise((resolve, reject) => {
            axios.get("/req-historys/" + id).then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/req-historys/" + id);
                }
            });
        });
    },
    // 提交入廊人员出廊时间
    putExitTime: function(params) {
        return new Promise((resolve, reject) => {
            axios.put("users/out/req-historys", params).then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：users/out/req-historys");
                }
            });
        });
    },
    //获取关于我们自己公司的信息
    getAboutUsData: function() {
        return new Promise((resolve, reject) => {
            axios.get("/version").then(res => {
                let { code, data, msg } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/version");
                }
            });
        });
    },
    // 获得每条管廊每个月的入廊次数
    getCountOfEnterEveryMonthAndTunnel: function() {
        // 地址
        // let tunnels = [
        //     "古城大街",
        //     "实验路",
        //     "纬三路",
        //     "经二路",
        //     "经三路",
        //     "监控中心"
        // ];
        // let times = ["18.09", "18.10", "18.11", "18.12", "19.01"];
        // let res = [];
        // for (let i = 0; i < times.length; i++) {
        //     let tmp = [];
        //     for (let j = 0; j < tunnels.length; j++) {
        //         tmp.push({
        //             time: times[i],
        //             tunnel: tunnels[j],
        //             count: Math.floor(Math.random() * 20 + 5)
        //         });
        //     }
        //     res.push(tmp);
        // }
        return new Promise((resolve, reject) => {
            // resolve(res)
            axios
                .get("tunnels/enter-count-everymonth")
                .then(res => {
                    let { code, data, msg } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + "地址：tunnels/enter-count-everymonth");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
        });
    }
};

export { EnterGalleryService };
