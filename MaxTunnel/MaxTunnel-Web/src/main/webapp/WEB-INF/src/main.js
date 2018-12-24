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
import store from './store/index'
import echarts from "echarts";
import "animate.css/animate.min.css";
import Stomp from "stompjs";
import "./styles/common.css";
import VMConfig from "../static/VM/js/VMGlobalConfig";
import './scripts/serviceClass'
// 加载字符格式转换
import './scripts/StringFormat'
import global_ from './components/Global'
import vueXlsxTable from 'vue-xlsx-table'

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
//Browser FileReader API have two methods to read local file readAsBinaryString and readAsArrayBuffer, default rABS false
Vue.use(vueXlsxTable, {rABS: false})
Vue.prototype.RouterBase = RouterBase;

var axios_instance = axios.create({headers: {"Content-Type": "application/json;charset=utf-8"}});
Vue.use(VueAxios, axios_instance);
if (process.env.NODE_ENV == "development") {
  axios.defaults.baseURL = "/MaxTunnel-Web/";
  Vue.prototype.ServerConfig = "/static";
  Vue.prototype.SuperMapConfig = SuperMapConfig;
  Vue.prototype.VMWebConfig = VMWebConfig;
  Vue.prototype.ApiUrl = require('../static/serverconfig').ApiUrl;
} else {
  Vue.prototype.ServerConfig = require('../static/serverconfig').ApiUrl + "/dist/static";
  axios.get(Vue.prototype.ServerConfig + "/serverconfig.json").then(result => {
    localStorage.setItem("ApiUrl", result.data.ApiUrl);
    Vue.prototype.ApiUrl = result.data.ApiUrl;
    Vue.prototype.SuperMapConfig = result.data.SuperMapConfig;
    Vue.prototype.VMWebConfig = VMWebConfig;
    axios.defaults.baseURL = Vue.prototype.ApiUrl;
  }).catch(error => {
  });
  // 获取VM的配置页
  axios.get("../" + Vue.prototype.ServerConfig + "/VM/js/VMWebConfig.json").then(result => {
    Vue.prototype.VMWebConfig = result.data.VMWebConfig;
  }).catch(error => {
    // console.log(error)
  });
}
Vue.config.productionTip = false;

// 定义一个全局的日志输出
Vue.prototype.Log = {
  info: function () {
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
  let CMUser = sessionStorage.CMUser;
  let UMUser = sessionStorage.UMUser;
  if (((to.path.substr(1, 2) == "UM" || to.path.substr(1, 2) == "VM") && UMUser) || (to.path.substr(1, 2) == "CM" && CMUser)) {
    if (!store.state.permission.permissionList) {
      store.dispatch('permission/FETCH_PERMISSION').then(() => {
        next({path: to.path})
      })
      next();
    }
    else {
      if (to.path.trim().toLowerCase().indexOf("login") < 0) {
        next()
      }
      else {
        next(from.fullPath)
      }
    }
  }
  else {
    if (to.path.indexOf("UM") > 0 && to.path.trim().toLowerCase() != "/umlogin") {
      next({
        path: "/UMlogin",
        query: {
          Rurl: to.fullPath
        }
      });
    }
    else if (to.path.indexOf("VM") > 0 && to.path.trim().toLowerCase() != "/vmlogin") {
      next({
        path: "/VMLogin",
        query: {
          Rurl: to.fullPath
        }
      });
    }
    else if (to.path.indexOf("CM") > 0 && to.path.trim().toLowerCase() != "/cmlogin") {
      next({
        path: "/CMlogin",
        query: {
          Rurl: to.fullPath
        }
      });
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
