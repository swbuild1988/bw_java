import Vue from 'vue'
import Stomp from "stompjs";
import {
    infromationManagDetails
} from "../components/Common/3D/infromationManagChart";
import {
    changStrLength
} from "./commonFun";
import createProxy from './createProxy'

/**
 * 信息统一管理类
 *
 * 用于视屏信息，单位信息，人员信息，缺陷信息，告警信息的增删改查
 *
 *  videos : 视屏信息队列
 *  units : 单位信息队列
 *  personnel : 人员信息队列
 *  flaw : 缺陷信息队列
 *  alarm : 告警信息队列
 *  events : 重大时间队列
 *  unitPlace : 单位队列
 */
class InformationManagement {

    constructor() {
        this.videos = [];
        this.units = [];
        this.personnel = [];
        this.flaw = [];
        this.alarm = [];
        this.events = [];
        this.unitPlace = [];

    }
    getInformation(informationType) {
        if (typeof informationType != 'string') return

        return this[informationType];
    };
    addInformation(informationType, information) {

        if (Array.isArray(this.getInformation(informationType))) {
            this.getInformation(informationType).push(information);
        }
        return this;
    }
    deleteInformation(newInformations, informationType, id) {
        if (!Array.isArray(newInformations)) return false;

        newInformations.forEach((details) => {
            this.getInformation(informationType).forEach((information, index, messageType) => {

                if (typeof information === 'object' &&
                    typeof details === 'object' &&
                    information[id] === details[id]) {

                    if (informationType === "personnel") {

                        let [entity] = Vue.prototype.$viewer.entities._entities._array.filter(entitie => (entitie._moId == information[id] && entitie.messageType == 'personnel'));
                        Vue.prototype.$viewer.entities.remove(entity)

                    }
                    messageType.splice(index, 1);
                }
            })
        });

        return this;
    }
    searchInformation(entity, modelProp) {

        if (!entity._messageType || ['videos', 'linear'].indexOf(entity._messageType) !== -1) { //当为视屏、直线实体时，隐藏弹框
            modelProp.show.state = false;
            return;
        }

        modelProp.showModelFooter = ['alarm'].indexOf(entity._messageType) !== -1 ? true : false; //用于切换footer插槽

        let informations = this.getInformation(entity._messageType);

        if (informations !== undefined && informations.length !== 0 && Array.isArray(informations)) {

            informations.forEach(information => {

                if (this._getEntityMoId(information, entity._messageType) == entity._moId) {

                    modelProp.data.splice(0);
                    modelProp.show.state = true;
                    modelProp.showModelFooter && Vue.prototype.$viewerComponent.$store.commit('changeAlarm', {
                        'entity': entity,
                        'object': information
                    }); //缓存鼠标获取最后一个告警

                    this._getInformation(information, entity, modelProp);

                }
            })
        }

        return this;
    }
    _getEntityMoId(information, messageType) {
        return (messageType == 'flaw' && information.type == 2) || (messageType == 'alarm') ? information.objectId : information.id;
    };
    _processInformation(messageFields, details, infromation) {
        if (!messageFields) return false;

        let managInfromation = null,
            obj = {};

        for (let i = 0; i < messageFields.length; i++) {

            if (messageFields[i].key == details) {

                managInfromation = this._isString(messageFields[i].val) ? messageFields[i].val : messageFields[i];

                let dealInfromation = this._isString(managInfromation.val) ? {
                    key: managInfromation.key,
                    val: managInfromation.val(infromation[details])
                } : {
                    key: managInfromation.val,
                    val: infromation[details]
                };

                return Object.assign(obj, dealInfromation);

            }
        }
    };
    _isString(param) {
        return Object.prototype.toString.call(param) === '[object Object]' || Object.prototype.toString.call(param) === '[object Function]';
    };
    _getInformation(information, entity, modelProp) {
        if (!this._isString(information)) return;

        for (let details in information) {
            if (information.hasOwnProperty(details)) {
                if (typeof information[details] === "object") {
                    this._getInformation(information[details], entity, modelProp)
                }
                let processObj = this._processInformation(infromationManagDetails[entity._messageType + 'Infromations'], details, information);

                processObj && modelProp.data.push({
                    key: processObj.key,
                    val: processObj.val
                });
            }
        }
    }
}

Vue.prototype.IM = new InformationManagement(); //实例化InformationManagement , 挂载到Vue原型上

/**
 * 告警管理类
 *
 * alarm : 用于监控接收的告警队列
 *
 * addAlarm : 向告警队列中添加告警
 * searchEntity : 监听到新告警时调用，动态替换图片
 */
class alarmManage {

    constructor() {
        this.data = {
            alarm: [],
        };
        this.init();
    }
    init() {
        createProxy(this.data, this.searchEntity); //监听data对象
    };
    addAlarm(alarmInformation) {

        this.data.alarm.push(alarmInformation);

        return this;
    };
    searchEntity(newEntity) {
        let timer = setInterval(() => {
            let [entity] = Vue.prototype.$viewer.entities._entities._array.filter(entitie => (entitie._moId == changStrLength(newEntity.objectId, 10) && entitie.messageType == 'alarm'));

            if (global.Cesium.defined(entity)) {
                clearInterval(timer);

                let isOpen = 1;
                let animationId = null;
                //定时器刷新,刷新频率 1000 / 60 = 16.7
                // let intervalTimer = setInterval(()=>{
                //     if( !viewer.entities.contains(entity) ) clearInterval(intervalTimer); //实体集中不存在当前实体清除定时器
                //
                //     let image =  isOpen ? 'alarm-open' : 'alarm-close';
                //     entity.billboard.image = require('../../../assets/VM/'+ image +'.png'); //修改告警图片
                //     isOpen = !isOpen;
                //
                // },1000/60)
                let changeImage = () => {

                    if (!Vue.prototype.$viewer.entities.contains(entity)) {
                        global.cancelAnimationFrame(animationId); //实体集中不存在当前实体清除动画
                        return this;
                    }

                    let image = isOpen ? 'alarm-open' : 'alarm-close';
                    entity.billboard.image = require('../assets/VM/' + image + '.png'); //修改告警图片
                    isOpen = !isOpen;

                    animationId = global.requestAnimationFrame(changeImage)
                };
                animationId = global.requestAnimationFrame(changeImage); // 调用requestAnimationFrame ，刷新频率随着浏览器刷新频率
            }
        }, 1000);

        return this;
    }
}

Vue.prototype.AM = new alarmManage(); //实例化alarmManage , 挂载到Vue原型上

/**
 * 时间管理类
 *
 * transTime : 时间转换为时间戳
 * timetrans : 时间戳转为时间
 * getSomeDayDate : 获取n天后的时间
 * comparisonDate : 比较时间，精确到秒
 */
class DateManage {
    constructor() {

    }
    transTime(time) {
        let date = new Date();

        date.setFullYear(time.substring(0, 4));
        date.setMonth(time.substring(5, 7) - 1);
        date.setDate(time.substring(8, 10));
        date.setHours(time.substring(11, 13));
        date.setMinutes(time.substring(14, 16));
        date.setSeconds(time.substring(17, 19));

        return Date.parse(date);
    };
    timetrans(date) {
        var date = new Date(date / 1000); //如果date为10位需要乘1000

        let Y = date.getFullYear() + '-';
        let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        let D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        let h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        let m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        let s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());

        return Y + M + D + h + m + s;
    };
    getSomeDayDate(addDay) {
        var dd = new Date();

        dd.setDate(dd.getDate() + addDay); //获取AddDayCount天后的日期
        let Y = dd.getFullYear();
        let M = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1); //获取当前月份的日期，不足10补0
        let D = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate(); //获取当前几号，不足10补0
        let h = dd.getHours() < 10 ? "0" + dd.getHours() : dd.getHours();
        let m = dd.getMinutes() < 10 ? "0" + dd.getMinutes() : dd.getMinutes();
        let s = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();

        return Y + "-" + M + "-" + D + ' ' + h + ":" + m + ":" + s;
    };
    comparisonDate(addDay, endDate) {
        return parseFloat(this.transTime(this.getSomeDayDate(addDay))) > parseFloat(endDate);
    }
}

Vue.prototype.DM = new DateManage(); //实例化DateManage , 挂载到Vue原型上

// MQ消息队列中转站
var TransferStation = {
    listeners: new Map(),

    updateData: function (data) {
        console.log("中转站更新数据", data)
        console.log("listeners", this.listeners)

        for (let [key, listener] of this.listeners) {
            listener(data);
        }
    },

    addListener: function (key, listener) {
        console.log("中转站添加新的监听器", key)
        this.listeners.set(key, listener)
    },

    deleteListener: function (key) {
        this.listeners.delete(key)
    },

    getListener: function (key) {
        this.listeners.get(key);
    }
}

/**
 *
 * 初始化并连接MQ服务器
 *
 */
class MQ {
    constructor() {
        this.client = null;
        this.ws = null;
        this.listeners = null;
        this.queueName = "";

    };

    setQueueName(_queueName) {
        console.log("设定队列名", _queueName)
        if (_queueName && _queueName != null && _queueName != 'null') {
            this.queueName = "/queue/" + _queueName;
        }
    }

    getQueueName() {
        return this.queueName;
    }


    addListeners(listener) {
        this.listener.push(listener)
    }

    addListener(_listener) {
        if (this.listener) {
            this.deleteListener();
        }
        this.listener = _listener;
    }

    deleteListener() {
        delete this.listener;
        this.listener = null;
    }

    openMQ() {
        let _this = this;
        if (_this.queueName == "") return;
        console.log("打开MQ，队列名：", _this.queueName);
        _this._InitMQ(_this._MQCallback);
    };

    //断开与MQ的连接
    closeMQ() {
        this.client.disconnect(() => {
            console.log("See you next time!");
        });
    }

    _MQCallback() {
        let _this = this;
        return function (data) {
            console.log("this", _this)
            TransferStation.updateData(data.body)
        }

        // this.listener.forEach( i )
    }

    _onConnect(args) {
        let [callback] = args;
        let _this = this;

        return function (conn) {
            _this.client.subscribe(_this.queueName, callback.apply(_this));
        }
    }

    //断连后重连
    _onError() {
        console.info("rabbitmq error")
        console.error(arguments)
    };

    //初始化MQ
    _InitMQ() {
        let _this = this;
        let MQServerAddress = Vue.prototype.MQServerAddress;
        let args = [].slice.call(arguments);

        if ('WebSocket' in window) {
            _this.ws = new WebSocket(MQServerAddress);
        } else {
            // ws = new SockJS('http://192.168.0.41:15670/stomp');
            alert("浏览器不支持WebSocket");
        }
        // 获得Stomp client对象
        _this.client = Stomp.over(_this.ws);
        // SockJS does not support heart-beat: disable heart-beats
        _this.client.heartbeat.outgoing = 2000;
        _this.client.heartbeat.incoming = 2000;

        _this.client.connect('admin', 'admin', _this._onConnect(args), _this._onError, '/');
    };
}

Vue.prototype.MQ = new MQ(); //实例化MQ , 挂载到Vue原型上
Vue.prototype.TransferStation = TransferStation;