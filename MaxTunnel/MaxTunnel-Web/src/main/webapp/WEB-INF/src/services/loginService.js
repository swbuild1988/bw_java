import axios from 'axios';
import { get,post } from "../utils/http";
import Cookies from 'js-cookie';

const isRealData = require('../../static/serverconfig').isRealData;
const LoginService = {
    // Login
    UmLogin: function (params) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
            post('/login', params)
                    .then((res) => {
                        const {
                            msg,
                            code,
                            data,
                        } = res.data;
                        if (code == 200 || code == 20007 || code == 20006) {
                            resolve(data);
                        } else {
                            reject(msg);
                        }
                    })
                    .catch((error) => {
                        reject(new Error(error));
                    });
            } else {
                const data = {
                    name: 'admin',
                    id: 1,
                };
                resolve(data);
            }
        });
    },
    // 获取登录用户权限
    getInfo: function () {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                get('/login/' + localStorage.getItem('userName')).then((res) => {
                    const {
                        msg,
                        code,
                        data,
                    } = res.data;
                    if (code == 200) {
                        resolve(data);
                    } else {
                        reject(new Error(msg + '地址:/login/' + Cookies.get('userName')));
                    }
                });
            }
        });
    },
    // Logout
    UMLogout: function (params) {
        return new Promise((resolve, reject) => {
            if (isRealData) {
                get('/logout')
                    .then((res) => {
                        const {
                            msg,
                            code,
                            data,
                        } = res.data;
                        if (code == 200) {
                            resolve(data);
                        } else {
                            reject(new Error(msg + '地址：/logout'));
                        }
                    })
                    .catch((error) => {
                        reject(new Error(error.response.status + '  ' + error.response.data));
                    });
            } else {
                const data = {};
                resolve(data);
            }
        });
    },
};

export {
    LoginService,
};