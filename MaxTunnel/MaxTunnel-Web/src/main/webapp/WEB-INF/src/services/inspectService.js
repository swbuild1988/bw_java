import axios from "axios";


const isRealData = require("../../static/serverconfig").isRealData;

var InspectService = {
  //获取今年的巡检任务总数
  getPatrolTaskCount: function() {
    return new Promise((resolve, reject) => {
      if (isRealData) {
        axios.get("inspection-tasks/count-year").then(res => {
          let { code, data, msg } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
            reject(msg);
          }
        });
      }
    });
  },
  //获取缺陷总数
  getDefectCount: function() {
    return new Promise((resolve, reject) => {
      if (isRealData) {
        axios.get("defects/count-year").then(res => {
          let { code, data, msg } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
            reject(msg);
          }
        });
      }
    });
  },
  //获取维修数
  getMaintenanceCount: function() {
    return new Promise((resolve, reject) => {
      if (isRealData) {
        axios.get("orders/count-year").then(res => {
          let { code, data, msg } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
            reject(msg);
          }
        });
      }
    });
  },
  //获取维修率
  getMaintenanceRate: function() {
    return new Promise((resolve, reject) => {
      if (isRealData) {
        axios.get("orders/percentage-year").then(res => {
          let { code, data, msg } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
            reject(msg);
          }
        });
      }
    });
  },
  // 获取每月维修情况（已维修和未维修）
  getEveryMonthMaintenance: function() {
    return new Promise((resolve, reject) => {
        axios.get("maintenance-orders/months").then(res => {
          console.log("res", res);
          let { code, data, msg } = res.data;
          if (code == 200) {
            resolve(data);
          } else {
            reject(msg);
          }
        });
      // let res = [];
      // for (let i = 13; i > 1; i--) {
      //   let tmp = {
      //     key:
      //       (i == 13 ? "19." : "18.") +
      //       (i < 10 || i == 13 ? "0" : "") +
      //       (i == 13 ? 1 : i),
      //     val: [
      //       {
      //         key: "已维修",
      //         val: Math.floor(10 + Math.random() * (20 - 10))
      //       },
      //       {
      //         key: "未维修",
      //         val: Math.floor(1 + Math.random() * (6 - 1))
      //       }
      //     ]
      //   };
      //   res.push(tmp);
      // }
      // resolve(res);
    });
  }
};
export { InspectService };
