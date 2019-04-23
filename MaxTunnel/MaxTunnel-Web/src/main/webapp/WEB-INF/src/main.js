/* eslint-disable camelcase */
/* eslint-disable prefer-rest-params */
/* eslint-disable brace-style */
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import "@babel/polyfill";
import "es6-promise/auto";
import Vue from "vue";
import App from "./App";
import iView from "iview";
import "iview/dist/styles/iview.css";
import router from "./router";
import store from "./store";
import echarts from "echarts";
import "animate.css/animate.min.css";
import "./styles/common.css";
import "./scripts/serviceClass";
import "./scripts/StringFormat";
import "./scripts/DateFormat";
import vueXlsxTable from "vue-xlsx-table";
import VMConfig from "../static/VM/js/VMGlobalConfig";
import serverconfig from "../static/serverconfig";

Vue.prototype.$echarts = echarts;
Vue.use(VMConfig);
Vue.use(iView);
Vue.use(vueXlsxTable, {
    rABS: false
});
// 设置一个默认值s
Vue.prototype.RouterBase = serverconfig.RouterBase;

router.base = Vue.prototype.RouterBase;
sessionStorage.setItem("refreshAddress", "");
sessionStorage.setItem("selectedName", "");

Vue.config.productionTip = false;

// 定义一个全局的日志输出
Vue.prototype.Log = {
    info: function () {
        // console.log("开启全局日志输出！");
        console.info.apply(console, arguments);
    },
    error: function () {
        console.error.apply(console, arguments);
    }
};

// main 初始化
async function main_init() {
    console.log("开始初始化")
    try {
        console.log("引入request")
        let request = (await import("@/utils/request.js")).default;
        console.log("request", request)
        await request();

        console.log("引入permission")
        let permission = (await import("@/permission")).permission;
        console.log("permission", permission)
        await permission();

        return "success"
    } catch (err) {
        throw new Error(err);
    }
}

main_init().then(res => {
    console.log("初始化结束", res)

    // 待初始化成功后，创建新的Vue实例
    new Vue({
        el: "#app",
        router,
        store,
        render: h => h(App)
    });

}).catch(err => {
    console.log("初始化异常", err)
})

console.log("main.js运行结束")
