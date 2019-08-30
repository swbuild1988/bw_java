import axios from "axios";
import {
    get,
    put,
    del,
    post
} from "../utils/http";

var EnergyConsumptionService = {
    // 根据管廊ID，起始时间获取能耗总值
    getECTotal: function (params) {
        return new Promise((resolve, reject) => {
            post("tunnels/energies-det/total", params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：tunnels/energies-det/total");
                }
            });
        });
    },
    // 根据管廊ID，起始时间获取各能耗类别值
    getECCategory: function (params) {
        return new Promise((resolve, reject) => {
            post("tunnels/energies-det/ratio", params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：tunnels/energies-det/ratio");
                }
            });
        });
    },
    // 根据管廊ID和时间周期获取能耗分类详情（曲线表）
    getECCategoryDetail: function (tunnelId, timeCycle) {
        return new Promise((resolve, reject) => {
            get("tunnels/" + tunnelId + "/energies-det/" + timeCycle)
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
                            msg +
                            "地址：tunnels/" +
                            tunnelId +
                            "/energies-det/" +
                            timeCycle
                        );
                    }
                });
        });
    },
    // 管廊能耗分页查询(表格)
    getECDatagrid: function (params) {
        return new Promise((resolve, reject) => {
            post("/tunnels/energies/datagrid", params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/tunnels/energies/datagrid");
                }
            });
        });
    },
    // 根据管廊ID，起始时间获取能耗信息(曲线表)
    getECInfo: function (params) {
        return new Promise((resolve, reject) => {
            post("tunnels/energies/time/condition", params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：tunnels/energies/time/condition");
                }
            });
        });
    },
    // 获取历史总能耗
    getECHistoryTotal: function () {
        return new Promise((resolve, reject) => {
            get("tunnels/consumes/timetype/1").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/tunnels/consumes/timetype/1");
                }
            });
        });
    },
    // 获取本年总能耗
    getECYearTotal: function () {
        return new Promise((resolve, reject) => {
            get("/tunnels/consumes/timetype/2").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/tunnels/consumes/timetype/2");
                }
            });
        });
    },
    // 获取本月总能耗
    getECMonthTotal: function () {
        return new Promise((resolve, reject) => {
            get("/tunnels/consumes/timetype/3").then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：/tunnels/consumes/timetype/3");
                }
            });
        });
    },
    // 按照周期获取各管廊耗电量
    getIntervalConsumes(period){
        return new Promise((resolve, reject) => {
            get("tunnels/consumes/interval/"+period).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：tunnels/consumes/interval/" + period);
                }
            });
        });
    },
    // 根据起始时间查询表数据
    getECDetail: function (params) {
        return new Promise((resolve, reject) => {
            post("tunnels/consume-datas", params).then(res => {
                let {
                    code,
                    data,
                    msg
                } = res.data;
                if (code == 200) {
                    resolve(data);
                } else {
                    reject(msg + "地址：tunnels/consume-datas");
                }
            });
        });
    },
    // 获得每条管廊每个月的总能耗
    getTotleEnergyEveryMonthAndTunnel: function () {
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
        //             energy: (Math.random() * 1000 + 1000).toFixed(2)
        //         });
        //     }
        //     res.push(tmp);
        // }
        return new Promise((resolve, reject) => {
            get("energy/totle-everymonth")
                .then(res => {
                    let { code, data, msg } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + "地址：energy/totle-everymonth");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
            // resolve(res);
        });
    },
    // 获得每条管廊每个月的每公里能耗
    getOneKmEneryEveryMonthAndTunnel: function () {
        // 地址
        let tunnels = [
            "古城大街",
            "实验路",
            "纬三路",
            "经二路",
            "经三路",
            "监控中心"
        ];
        let times = ["18.09", "18.10", "18.11", "18.12", "19.01"];
        let res = [];
        for (let i = 0; i < times.length; i++) {
            let tmp = [];
            for (let j = 0; j < tunnels.length; j++) {
                tmp.push({
                    time: times[i],
                    tunnel: tunnels[j],
                    energy: (Math.random() * 100 + 200).toFixed(2)
                });
            }
            res.push(tmp);
        }
        return new Promise((resolve, reject) => {
            resolve(res);
            // get("energy/average-everymonth")
            //     .then(res => {
            //         let { code, data, msg } = res.data;
            //         if (code == 200) {
            //             resolve(data);
            //         } else {
            //             reject(msg + "地址：energy/average-everymonth");
            //         }
            //     })
            //     .catch(error => {
            //         reject(error.response.status + "  " + error.response.data);
            //     });
        });
    },
    // 各管廊平均能耗
    getTunnelAveEC(){
        return new Promise((resolve, reject) => {
            get("tunnels/total-avg/2/consume-datas")
                .then(res => {
                    let { code, data, msg } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(msg + "地址：tunnels/total-avg/2/consume-datas");
                    }
                })
                .catch(error => {
                    reject(error.response.status + "  " + error.response.data);
                });
            // resolve(res);
        });
    }
};

export {
    EnergyConsumptionService
};