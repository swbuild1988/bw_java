// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import "@babel/polyfill";
import "es6-promise/auto";
import Vue from "vue";
import App from "./App";
import VueRouter from "vue-router";
import iView from "iview";
import axios from "axios";
import VueAxios from "vue-axios";
import "iview/dist/styles/iview.css";
import routes from "./router";
import Vuex from "vuex";
import store from "./store";
import echarts from "echarts";
import "animate.css/animate.min.css";
import Stomp from "stompjs";
import "./styles/common.css";
import VMConfig from "../static/VM/js/VMGlobalConfig";
import './scripts/serviceClass'
// 加载字符格式转换
import './scripts/StringFormat'
import global_ from './components/Global'

// const ApiUrl = require('../static/serverconfig').ApiUrl;
const RouterBase = require("../static/serverconfig").RouterBase;
const SuperMapConfig = require("../static/serverconfig").SuperMapConfig;
const VMWebConfig = require("../static/VM/js/VMWebConfig").VMWebConfig;

Vue.prototype.$echarts = echarts;

Vue.prototype.GLOBAL = global_

Vue.use(VMConfig);
Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(iView);
Vue.prototype.RouterBase = RouterBase;

var axios_instance = axios.create({
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
});
Vue.use(VueAxios, axios_instance);
if (process.env.NODE_ENV == "development") {
    axios.defaults.baseURL = "/MaxTunnel-Web/";
    Vue.prototype.ServerConfig = "/static";
    Vue.prototype.SuperMapConfig = SuperMapConfig;
    Vue.prototype.VMWebConfig = VMWebConfig;
    console.log("VMWebConfig", VMWebConfig);
    Vue.prototype.ApiUrl = require('../static/serverconfig').ApiUrl;
} else {
    Vue.prototype.ServerConfig = RouterBase + "dist/static";
    axios
        .get("../" + Vue.prototype.ServerConfig + "/serverconfig.json")
        .then(result => {
            localStorage.setItem("ApiUrl", result.data.ApiUrl);
            //在main.js中定义一个全局函数
            Vue.prototype.ApiUrl = result.data.ApiUrl;
            Vue.prototype.SuperMapConfig = result.data.SuperMapConfig;
            axios.defaults.baseURL = Vue.prototype.ApiUrl;
        })
        .catch(error => {
            // console.log(error)
        });
    // 获取VM的配置页
    axios
        .get("../" + Vue.prototype.ServerConfig + "/VM/js/VMWebConfig.json")
        .then(result => {
            Vue.prototype.VMWebConfig = result.data.VMWebConfig;
        })
        .catch(error => {
            // console.log(error)
        });
}
Vue.config.productionTip = false;

// 定义一个全局的日志输出
Vue.prototype.Log = {
    info: function() {
        console.log(arguments);
    }
};

Vue.prototype.GLOBAL = global_

const router = new VueRouter({
    mode: "history",
    base: RouterBase, //服务器地址，不设置时，默认为服务器根目录下
    routes
});
router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    if (to.matched.some(m => m.meta.auth)) {
        let users = ["UMUser", "VMUser", "CMUser"];
        let user = users
            .filter(item => item.substr(0, 2) == to.path.substr(1, 2))
            .join("");
        users.forEach(function(value) {
            if (value != user) {
                sessionStorage.removeItem(value);
            }
        });
        if (!to.query.url && from.query.url) {
            to.query.url = from.query.url;
        }
        if (JSON.parse(sessionStorage.getItem(user))) {
            next();
        } else {
            if (to.path.indexOf("UM") > 0) {
                next({
                    path: "/UMlogin",
                    query: {
                        Rurl: to.fullPath
                    }
                });
                return false;
            }
            if (to.path.indexOf("VM") > 0) {
                next({
                    path: "/VMlogin",
                    query: {
                        Rurl: to.fullPath
                    }
                });
                return false;
            }
            if (to.path.indexOf("CM") > 0) {
                next({
                    path: "/CMlogin",
                    query: {
                        Rurl: to.fullPath
                    }
                });
                return false;
            } else {
                next({
                    path: "/UMlogin",
                    query: {
                        Rurl: to.fullPath
                    }
                });
                return false;
            }
        }
    } else {
        next();
    }
});

router.afterEach(route => {
    iView.LoadingBar.finish();
});
new Vue({
    el: "#app",
    router,
    store,
    render: h => h(App)
});