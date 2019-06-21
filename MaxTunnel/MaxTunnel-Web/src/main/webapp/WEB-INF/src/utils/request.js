import Vue from 'vue';
import axios from 'axios/index';
import VueAxios from 'vue-axios';
import {
    Message,
    Modal,
} from 'iview';
import store from '../store';
import serverconfig from '../../static/serverconfig';

export default async () => {

    console.log("request.js 开始运行")

    try {
        if (process.env.NODE_ENV == 'development') {
            // 开发环境配置
            // axios.defaults.baseURL = serverconfig.ApiUrl;
            // Vue.prototype.ServerConfig = '/dist/static';
            // let result_config = await axios.get('/dist/static/serverconfig.json');
            // console.log('result_config', result_config)
            axios.defaults.baseURL = "/MaxTunnel-Web";
            Vue.prototype.ServerConfig = '/static';
            Vue.prototype.SuperMapConfig = serverconfig.SuperMapConfig;
            Vue.prototype.VMEntityConfig = serverconfig.VMEntityConfig;
            Vue.prototype.flyFilePathes = serverconfig.flyFilePathes;
            Vue.prototype.inspectionFlyFiles = serverconfig.inspectionFlyFiles;
            Vue.prototype.refreshTime = serverconfig.refreshTime;
            Vue.prototype.pointLinght = serverconfig.pointLinght;
            Vue.prototype.VMWebConfig = require('../../static/VM/js/VMWebConfig').VMWebConfig;
            Vue.prototype.ApiUrl = serverconfig.ApiUrl;
            Vue.prototype.RouterBase = serverconfig.RouterBase;
            Vue.prototype.MQServerAddress = serverconfig.MQServerAddress;
            sessionStorage.setItem('ServerConfig', Vue.prototype.ServerConfig);
        } else {
            // 生产环境配置
            console.log("进入生产环境配置")
            console.log('axios', axios)
            Vue.prototype.ServerConfig = '/dist/static';
            let result_config = await axios.get('/dist/static/serverconfig.json');
            console.log('result_config', result_config)
            Vue.prototype.ApiUrl = result_config.data.ApiUrl;
            // Vue.prototype.ServerConfig = result_config.data.ApiUrl + '/dist/static';
            Vue.prototype.SuperMapConfig = result_config.data.SuperMapConfig;
            Vue.prototype.flyFilePathes = result_config.data.flyFilePathes;
            Vue.prototype.inspectionFlyFiles = result_config.data.inspectionFlyFiles;
            Vue.prototype.refreshTime = result_config.data.refreshTime;
            Vue.prototype.VMEntityConfig = result_config.data.VMEntityConfig;
            Vue.prototype.RouterBase = result_config.data.RouterBase;
            Vue.prototype.MQServerAddress = result_config.data.MQServerAddress;
            Vue.prototype.pointLinght = result_config.data.pointLinght;
            sessionStorage.setItem('ServerConfig', '/dist/static');

            // 获取VM的配置页
            // axios.get('dist/static/VM/js/VMWebConfig.json').then((result) => {
            //     Vue.prototype.VMWebConfig = result.data.VMWebConfig;
            // }).catch((error) => {
            //     // console.log(error)
            // });
            let result_webConfig = await axios.get('/dist/static/VM/js/VMWebConfig.json');
            Vue.prototype.VMWebConfig = result_webConfig.data.VMWebConfig;

            // 待配置文件都加载好之后，后续的访问都是访问后端接口了，修改地址
            axios.defaults.baseURL = result_config.data.ApiUrl;
        }

        console.log("request.js中的RouterBase", axios.defaults.baseURL)

        // 创建axios实例
        const axios_instance = axios.create({
            // timeout: 15000, // 请求超时时间
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
            },
        });
        // request拦截器
        axios_instance.interceptors.request.use(
            (config) => {
                if (store.getters.token) {
                    config.headers['X-Token'] = store.getters.token; // 让每个请求携带自定义token 请根据实际情况自行修改
                }
                return config;
            },
            (error) => {
                // Do something with request error
                console.log(error); // for debug
                Promise.reject(error);
            });

        // respone拦截器
        axios_instance.interceptors.response.use(
            (response) => {
                /**
                 * code为非20000是抛错 可结合自己业务进行修改
                 */
                const res = response.data;
                if (response.status !== 200) {
                    Message.error({
                        content: res.message,
                        duration: 5,
                    });
                    // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
                    if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                        Modal.confirm({
                            title: 'warning',
                            okText: '重新登录',
                            cancelText: '取消',
                            content: '<p>你已被登出，可以取消继续留在该页面，或者重新登录确定登出</p>',
                            onOk: () => {
                                store.dispatch('FedLogOut').then(() => {
                                    location.reload(); // 为了重新实例化vue-router对象 避免bug
                                });
                            },
                            onCancel: () => {
                                Message.info('Clicked cancel');
                            },
                        });
                    }
                    console.log("调用接口后台返回错误");
                    // return Promise.reject(new Error('error'));
                } else {
                    return response;
                }
            },
            (error) => {
                // console.log('err' + error); // for debug
                // Message.error({
                //     content: error.message,
                //     duration: 5,
                // });
                return Promise.reject(error);
            }
        );
        Vue.use(VueAxios, axios_instance);

    } catch (err) {
        throw new Error(err);
    }
}