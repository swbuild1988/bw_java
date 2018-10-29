// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import '@babel/polyfill'
import 'es6-promise/auto'
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import iView from 'iview'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'iview/dist/styles/iview.css'
import routes from "./router";
import Vuex from 'vuex'
import store from './store'
import echarts from 'echarts'
import 'animate.css/animate.min.css'
import Stomp from 'stompjs'
import './styles/common.css'
import VMConfig from '../static/VM/js/VMGlobalConfig'
import "./scripts/RibbitMQManage";

const ApiUrl = require('../static/serverconfig').ApiUrl;
const RouterBase = require('../static/serverconfig').RouterBase;
Vue.prototype.$echarts = echarts
Vue.use(VMConfig)
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(iView);
Vue.prototype.RouterBase = RouterBase;

var axios_instance = axios.create({
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
})
Vue.use(VueAxios, axios_instance)
if (process.env.NODE_ENV == "development") {
  axios.defaults.baseURL = '/MaxTunnel-Web/';
  Vue.prototype.ApiUrl = ApiUrl;
  Vue.prototype.ServerConfig = "/static";
}
//生产环境配置
else {
  Vue.prototype.ServerConfig = RouterBase + "dist/static";
  axios.get("../" + Vue.prototype.ServerConfig + "/serverconfig.json").then((result) => {
    //在main.js中定义一个全局函数
    Vue.prototype.ApiUrl = result.data.ApiUrl;
    axios.defaults.baseURL = Vue.prototype.ApiUrl;
  }).catch((error) => {
    // console.log(error)
  });
}
Vue.config.productionTip = false
// 定义一个全局的日志输出
Vue.prototype.Log = {
  info: function () {
    for (let i = 0; i < arguments.length; i++) {
      console.log(arguments[i]);
    }
  }
}

const router = new VueRouter({
  mode: 'history',
  base: RouterBase, //服务器地址，不设置时，默认为服务器根目录下
  routes
})
router.beforeEach((to, from, next) => {
  iView.LoadingBar.start();
  if (to.matched.some(m => m.meta.auth)) {
    let users = ['UMUser', 'VMUser', 'CMUser'];
    let user = users.filter(item => item.substr(0, 2) == to.path.substr(1, 2)).join('');
    users.forEach(function (value) {
      if (value != user) {
        sessionStorage.removeItem(value)
      }
    })
    if (!to.query.url && from.query.url) {
      to.query.url = from.query.url
    }
    if (JSON.parse(sessionStorage.getItem(user))) {
      next()
    } else {
      if (to.path.indexOf("UM") > 0) {
        next({
          path: '/UMlogin',
          query: {
            Rurl: to.fullPath
          }
        })
        return false;
      }
      if (to.path.indexOf("VM") > 0) {
        next({
          path: '/VMlogin',
          query: {
            Rurl: to.fullPath
          }
        })
        return false;
      }
      if (to.path.indexOf("CM") > 0) {
        next({
          path: '/CMlogin',
          query: {
            Rurl: to.fullPath
          }
        })
        return false;
      } else {
        next({
          path: '/UMlogin',
          query: {
            Rurl: to.fullPath
          }
        })
        return false;
      }
    }
  } else {
    next()
  }
})

router.afterEach(route => {
  iView.LoadingBar.finish();
});
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
