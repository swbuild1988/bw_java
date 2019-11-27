import router from './router';
import store from './store';
import Vue from "vue";
import {
    Message
} from 'iview';
import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css'; // Progress 进度条样式
import {
    getToken,
} from '@/utils/auth'; // 验权
import session from '@/utils/auth';
import { LoginService } from "@/services/loginService.js";



const permission = async () => {

    console.log("permission.js 开始运行")

    const whiteList = ['/404', '/umlogin', '/vmlogin', '/cmlogin']; // 白名单,不需要登录的路由
    router.beforeEach((to, from, next) => {

        NProgress.start();

        console.log("localStorage", localStorage)
        if (localStorage.getItem('Authorization')) {
            if(from.path.trim()=='/'){
                var loginParams = {
                    name: localStorage.getItem('userName'),
                    password: localStorage.getItem('password'),
                    Authorization: localStorage.getItem('Authorization')
                };
				store.dispatch("Login", loginParams).then(
                    result => {
                        next()
                        NProgress.done(); // 结束Progress
                    }
                )

            }

            if(localStorage.getItem('queueName')){
                // 如果已登录且MQ尚未连接，连接MQ
                let mq = Vue.prototype.MQ;
                if (mq.getQueueName() == "") {
                    // let tmp = JSON.parse(localStorage.getItem('queueName'))
                    mq.setQueueName(localStorage.getItem('queueName'));
                    mq.openMQ();
                }
            }

            if (to.path.trim().toLowerCase().indexOf('login') > 0) {
                if (to.path.trim().toLowerCase().indexOf('um') > 0) {
                    next({
                        path: 'UMMain',
                    });
                    NProgress.done(); // 结束Progress
                } 
                else if (to.path.trim().toLowerCase().indexOf('vm') > 0) {
                    next({
                        path: 'VMMain',
                    });
                    NProgress.done(); // 结束Progress
                }
                else if (to.path.trim().toLowerCase().indexOf('cm') > 0) {
                    next({
                        path: 'CMMain',
                    });
                    NProgress.done(); // 结束Progress
                }
            } else if (!store.getters.role || store.getters.role.length == 0) { 
                store.dispatch('GetInfo').then(() => {
                        next({
                            ...to,
                            replace: true
                        });
                    },
                    error => {
                        Message.error({
                            content: error,
                            duration: 5,
                        });
                        NProgress.done();
                    });
            } else{
                next()
            }

        } else if (whiteList.indexOf(to.path.toLowerCase()) !== -1) {
            // 如果前往的路径是白名单内的,就可以直接前往
            next();
        } else {
            // 如果路径不是白名单内的,而且又没有登录,就跳转登录页面
            next('/UMLogin');
            NProgress.done(); // 结束Progress
        }
    });

    router.afterEach(() => {
        NProgress.done(); // 结束Progress
    });
}

export {
    permission
}