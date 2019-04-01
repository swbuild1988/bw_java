import Vue from 'vue';
import axios from 'axios';
import {
    Message,
    Modal,
} from 'iview';
import store from '../store';
import serverconfig from '../../static/serverconfig';
const Axios = axios.create({
    timeout: 15000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json;charset=utf-8',
    },
})
async function initDefaultSystem() {
    if (process.env.NODE_ENV == 'development') {
        // 开发环境配置
        let baseURL = serverconfig.RouterBase;
        return baseURL;
    } else {
        // 生产环境配置

        Vue.prototype.ServerConfig = '/dist/static';
        let baseURL = await axios.get('dist/static/serverconfig.json').then(result => {
            let temp = result.data.ApiUrl;
            return temp;
        }).catch((error) => {
            console.log("配置生产环境配错误");
        })
        return baseURL;
    }
}
initDefaultSystem().then(res => {
    Axios.defaults.baseURL = res;
});
// request拦截器
Axios.interceptors.request.use(
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
Axios.interceptors.response.use(
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
        console.log('err' + error); // for debug
        Message.error({
            content: error.message,
            duration: 5,
        });
        return Promise.reject(error);
    }
);


export default {
    //get请求
    get(url, param) {
        return new Promise((resolve, reject) => {
            if (!Axios.defaults.baseURL) {
                initDefaultSystem().then(res => {
                    Axios.defaults.baseURL = res;
                }).then(() => {
                    Axios({
                        method: 'get',
                        url,
                        params: param,
                    }).then(res => { //axios返回的是一个promise对象
                        resolve(res) //resolve在promise执行器内部 
                    }).catch(err => {
                        console.log(err, '异常')
                    })
                });
            } else {
                Axios({
                    method: 'get',
                    url,
                    params: param,
                }).then(res => { //axios返回的是一个promise对象
                    resolve(res) //resolve在promise执行器内部 
                }).catch(err => {
                    console.log(err, '异常')
                })
            }
        })
    },
    //post请求
    post(url, param) {
        return new Promise((resolve, reject) => {
            if (!Axios.defaults.baseURL) {
                initDefaultSystem().then(res => {
                    Axios.defaults.baseURL = res;
                    console.log(res);
                }).then(() => {
                    Axios({
                        method: 'post',
                        url,
                        data: param,
                    }).then(res => {
                        resolve(res)
                    }).catch(err => {
                        console.log(err, '异常')
                    })
                })
            } else {
                Axios({
                    method: 'post',
                    url,
                    data: param,
                }).then(res => {
                    resolve(res)
                }).catch(err => {
                    console.log(err, '异常')
                })
            }
        })
    }
}