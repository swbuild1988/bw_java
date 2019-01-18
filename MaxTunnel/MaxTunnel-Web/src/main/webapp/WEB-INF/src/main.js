// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import "@babel/polyfill";
import "es6-promise/auto";
import Vue from "vue";
import App from "./App";
import VueRouter from "vue-router";
import iView from "iview";
import VueAxios from "vue-axios";
import "iview/dist/styles/iview.css";
import routes from "./router";
import Vuex from "vuex";
import store from "./store.js";
import echarts from "echarts";
import "animate.css/animate.min.css";
import Stomp from "stompjs";
// import global_ from "./components/Global";
import "./styles/common.css";
import VMConfig from "../static/VM/js/VMGlobalConfig";
import "./scripts/serviceClass";
import "./scripts/StringFormat";
import vueXlsxTable from "vue-xlsx-table";
import axios from "axios/index";
import serverconfig from '../static/serverconfig'

// console.log("document.body.offsetWidth1",document.body.offsetWidth)
// if(document.body.offsetWidth>3000){
//     console.log("document.body.offsetWidth2",document.body.offsetWidth)
//     require('lib-flexible/flexible.js')
// }

Vue.prototype.$echarts = echarts;
Vue.use(VMConfig);
Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(iView);
Vue.use(vueXlsxTable, { rABS: false });
//设置一个默认值
Vue.prototype.RouterBase = serverconfig.RouterBase;
const router = new VueRouter({
  mode: "history",
  base:  Vue.prototype.RouterBase, //服务器地址，不设置时，默认为服务器根目录下
  routes
});

axios.defaults.timeout = 3000;
// 开发环境配置
if (process.env.NODE_ENV == "development") {
  axios.defaults.baseURL = serverconfig.RouterBase;
  Vue.prototype.ServerConfig = "/static";
  Vue.prototype.SuperMapConfig = serverconfig.SuperMapConfig;
  Vue.prototype.VMWebConfig = require("../static/VM/js/VMWebConfig").VMWebConfig;
  Vue.prototype.ApiUrl = serverconfig.ApiUrl;
  sessionStorage.setItem("ServerConfig", Vue.prototype.ServerConfig);
}
//生产环境配置
else {
  axios.get("dist/static/serverconfig.json").then(result => {
    Vue.prototype.ApiUrl = result.data.ApiUrl;
    Vue.prototype.ServerConfig = result.data.ApiUrl + "/dist/static";
    Vue.prototype.SuperMapConfig = result.data.SuperMapConfig;
    router.base = result.data.RouterBase;
    Vue.prototype.RouterBase = result.data.RouterBase;
    axios.defaults.baseURL = Vue.prototype.ApiUrl;
    sessionStorage.setItem("ServerConfig", Vue.prototype.ServerConfig);
  }).catch(error => {
  });
  // 获取VM的配置页
  axios.get("dist/static/VM/js/VMWebConfig.json").then(result => {
    Vue.prototype.VMWebConfig = result.data.VMWebConfig;
  }).catch(error => {
    // console.log(error)
  });
}

var axios_instance = axios.create({timeout:'3000',headers:{"Content-Type": "application/json;charset=utf-8"}});
Vue.use(VueAxios, axios_instance);
 Vue.config.productionTip = false;

// 定义一个全局的日志输出
Vue.prototype.Log = {
    info: function() {
        console.log(arguments);
    }
};

router.beforeEach((to, from, next) => {
    let CMUser = sessionStorage.CMUser;
    let UMUser = sessionStorage.UMUser;
    if (
        ((to.path.substr(1, 2).toLowerCase() == "um" || to.path.substr(1, 2).toLowerCase() == "vm") &&
            UMUser) ||
        (to.path.substr(1, 2).toLowerCase() == "cm" && CMUser)
    ) {
        // if (!store.state.permission.permissionList) {
        //   store.dispatch('permission/FETCH_PERMISSION').then(() => {
        //     next({path: to.path})
        //   })
        //   next();
        // }
        {
            if (
                to.path
                    .trim()
                    .toLowerCase()
                    .indexOf("login") < 0
            ) {
                next();
            } else {
                next();
            }
        }
    }
    else {
        if (to.path.toLowerCase().indexOf("um") > 0 && to.path.trim().toLowerCase().indexOf("umlogin") <0 ) {
            next({path:"UMlogin"});
        }
        else if (to.path.toLowerCase().indexOf("vm") > 0 && to.path.trim().toLowerCase().indexOf("vmlogin") <0 ){
            next({path: "VMLogin"});
        }
        else if (to.path.toLowerCase().indexOf("cm") > 0 && to.path.trim().toLowerCase().indexOf("cmlogin") <0 ) {
            next({path: "CMlogin"});
        }
        else {
            next();
        }
    }
});

new Vue({
    el: "#app",
    router,
    store,
    render: h => h(App)
});
