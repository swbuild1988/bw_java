import axios from 'axios'
import router from './../router'
import serverconfig from '../../static/serverconfig'
import Vue from "vue";

axios.defaults.timeout = 60000;
axios.defaults.headers.post['Content-Type'] = '{"Content-Type": "application/json;charset=utf-8"}'
// 开发环境配置
if (process.env.NODE_ENV == "development") {
  axios.defaults.baseURL = serverconfig.RouterBase;
  Vue.prototype.ServerConfig = "/static";
  Vue.prototype.SuperMapConfig = serverconfig.SuperMapConfig;
  Vue.prototype.VMWebConfig = require("../../static/VM/js/VMWebConfig").VMWebConfig;
  Vue.prototype.ApiUrl = serverconfig.ApiUrl;
}
//生产环境配置
else {
  axios.get("dist/static/serverconfig.json").then(result => {
    console.log("defaultAxios result", result);
    Vue.prototype.ApiUrl = result.data.ApiUrl;
    Vue.prototype.ServerConfig = result.data.ApiUrl + "/dist/static";
    Vue.prototype.SuperMapConfig = result.data.SuperMapConfig;
    router.base = result.data.RouterBase;
    Vue.prototype.RouterBase = result.data.RouterBase;
    axios.defaults.baseURL = Vue.prototype.ApiUrl;
    sessionStorage.setItem("ServerConfig", Vue.prototype.ServerConfig);
  }).catch(error => {
    console.log("defaultAxios result error", error);
  });
  // 获取VM的配置页
  axios.get("dist/static/VM/js/VMWebConfig.json").then(result => {
    Vue.prototype.VMWebConfig = result.data.VMWebConfig;
  }).catch(error => {
    // console.log(error)
  });
}

//http request 拦截器
axios.interceptors.request.use(
  config => {
    // if (localStorage.getItem('Authorization')) {
    //   config.headers.Authorization = localStorage.getItem('Authorization');
    // }
    if(config.url==='/UMLogin'){  //如果是登录和注册操作，则不需要携带header里面的token
    }else{
      if (localStorage.getItem('Authorization')) {
        config.headers.Authorizatior = localStorage.getItem('Authorization');
        console.log("2222")
      }
    }
    return config;
  },
  error => {
    return Promise.reject(error);
});


//http response 拦截器
axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response) { 
      switch (error.response.status) {
        case 401:
          //返回 401 清除token信息并跳转到登录页面
          // localStorage.removeItem('Authorization');
          // router.replace({
          //   path: '/UMLogin',
          //   //query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
          // })
        case 400:
          error.message = '请求错误'
          break
        case 403:
          error.message = '拒绝访问'
          break
        case 404:
          error.message = `请求地址出错: ${error.response.config.url}`
          break
        case 408:
          error.message = '请求超时'
          break
        case 500:
          error.message = '服务器内部错误'
          break
        case 501:
          error.message = '服务未实现'
          break
        case 502:
          error.message = '网关错误'
          break
        case 503:
          error.message = '服务不可用'
          break
        case 504:
          error.message = '网关超时'
          break
        case 505:
          error.message = 'HTTP版本不受支持'
          break
        default:
          error.message = "连接服务器异常";
      }
    } else {
      error.message = "连接服务器失败";
      setTimeout(() => {
        router.replace({
          path: '/umlogin',
          //query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
        })
      }, 500)
    }
    return Promise.reject(error.message) // 返回接口返回的错误信息
  }
);

//异步请求前判断请求的连接是否需要token
router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next();
  } else {
    let token = localStorage.getItem('Authorization');
    console.log("我是浏览器本地缓存的token: "+token);
    if (token === 'null' || token === '') {
      next('/UMLogin');
    } else {
      next();
    }
  }
});

export default axios;