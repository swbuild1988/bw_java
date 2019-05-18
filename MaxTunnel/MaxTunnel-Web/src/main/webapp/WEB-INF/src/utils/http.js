import axios from 'axios';
import {
    Message,
    Modal,
} from 'iview';
import store from '../store';
import session from './auth'
import router from '../router'


// 创建axios实例
const axios_instance = axios.create({
    headers: {
        'Content-Type': 'application/json;charset=utf-8',
    },
});
// request拦截器
axios_instance.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        // Do something with request error
        Promise.reject(error);
    });

// respone拦截器
axios_instance.interceptors.response.use(
    (response) => {
        /**
         * code为非20000是抛错 可结合自己业务进行修改
         */
        const res = response.data;
        // console.log(response)
        if (response.status !== 200) {
            console.log('status')
            // Message.error({
            //     content: res.message,
            //     duration: 5,
            // });
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
             // 503:用户未登录
            if( res.code == 503 ){
                store.dispatch('FedLogOut').then(() => {
                    location.reload(); 
                });
                router.push({
                    path:'/UMLogin'
                })
            } else {
                return response;
            }
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

export default axios_instance;

/**
 * get : 请求
 * @param url
 */

// export function get( url ) {
//     return new Promise((resolve,reject) => {
//         axios_instance.get(url)
//             .then( response => {
//                 let{ code,data,msg } = response.data;
//
//                 if(code === 200){
//                     resolve(data)
//                 }else{
//                     reject( msg + "地址: "+ url )
//                 }
//             } )
//             .catch(err => {
//                 reject( err )
//             })
//     });
// }
export function get( url ) {
    return new Promise((resolve,reject) => {
        axios_instance.get(url)
            .then( response => resolve(response) )
            .catch(err => reject(err))
    });
}

/**
 * post 请求方法
 *
 * @param url
 * @param data
 */

// export function post( url,data = {} ) {
//     return new Promise((resolve,reject) => {
//         axios_instance.post(url,data)
//             .then( response => {
//                 let{ code,data,msg } = response.data;
//
//                 if(code === 200){
//                     resolve(data)
//                 }else{
//                     reject( msg + "地址: "+ url )
//                 }
//             } )
//             .catch(err => {
//                 reject( err )
//             })
//     });
// }

export function post( url,data = {} ) {
    return new Promise((resolve,reject) => {
        axios_instance.post(url,data)
            .then( response => {
                    resolve(response)
            } )
            .catch(err => {
                reject( err )
            })
    });
}

export function del( url ) {
    return new Promise((resolve,reject) => {
        axios_instance.delete(url)
            .then( response => {
                resolve(response)
            } )
            .catch(err => {
                reject( err )
            })
    });
}

export function put( url,data = {} ) {
    return new Promise((resolve,reject) => {
        axios_instance.put(url,data)
            .then( response => {
                resolve(response)
            } )
            .catch(err => {
                reject( err )
            })
    });
}