import Vue from 'vue'
import Stomp from "stompjs";
import { infromationManagDetails } from "../components/Common/3D/infromationManagChart";
import createProxy from './createProxy'

const MQServerAddress = require('../../static/serverconfig').MQServerAddress;

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
 */
class InformationManagement {

    constructor(){
        this.videos = [];
        this.units = [];
        this.personnel = [];
        this.flaw = [];
        this.alarm = [];
        this.events = [];
    }
    getInformation(informationType){
        if(typeof informationType != 'string') return

        return this[informationType];
    };
    addInformation(informationType,information){

        if(Array.isArray(this.getInformation(informationType))){
            this.getInformation(informationType).push(information);
        }
        return this;
    }
    deleteInformation(newInformations,informationType,id){
        if(!Array.isArray(newInformations)) return false;

        newInformations.forEach((details)=>{
            this.getInformation(informationType).forEach((information,index,messageType)=>{

                if(typeof information=='object' &&　typeof details=='object'){
                    information[id] == details[id] &&　messageType.splice(index,1);
                }
            })

        });
        return this;
    }
    searchInformation(entity,modelProp){

        if(['videos'].indexOf(entity._messageType) !== -1){ //当为视屏实体时，隐藏弹框
            modelProp.show.state = false;
            return;
        }
        modelProp.showModelFooter = ['alarm'].indexOf(entity._messageType) !== -1 ? true : false; //用于切换footer插槽

        let informations=this.getInformation(entity._messageType);

        if(informations !== undefined && informations.length !== 0 &&　Array.isArray(informations)){

            informations.forEach(information=>{

                if( this._getEntityMoId(information,entity._messageType) == entity._moId ){

                    modelProp.data.splice(0);
                    modelProp.show.state = true;
                    modelProp.showModelFooter &&　Vue.prototype.$viewerComponent.$store.commit('changeAlarm',{'entity':entity,'object':information});//缓存鼠标获取最后一个告警

                    for(let details in information){
                        if(information.hasOwnProperty(details)){

                            let processObj = this._processInformation(infromationManagDetails[entity._messageType + 'Infromations'],details,information);

                            processObj && modelProp.data.push({key:processObj.key,val:processObj.val});
                        }
                    }
                }
            })
        }

        return this;
    }
    _getEntityMoId(information,messageType){
        return (messageType == 'flaw' && information.type == 2) || (messageType == 'alarm')  ? information.objectId : information.id;
    };
    _processInformation(messageFields,details,infromation){
        if(!messageFields) return false;

        let managInfromation = null,
            obj = {};

        for( let i = 0; i < messageFields.length ; i++ ){

            if( messageFields[i].key == details ) {

                managInfromation  = this._isString(messageFields[i].val) ? messageFields[i].val : messageFields[i];

                let dealInfromation = this._isString(managInfromation.val) ? {
                    key : managInfromation.key,
                    val : managInfromation.val(infromation[details])
                }:{
                    key : managInfromation.val,
                    val : infromation[details]
                };

                return Object.assign(obj, dealInfromation);

            }
        }
    };
    _isString(param){
        return Object.prototype.toString.call( param ) == '[object Object]' || Object.prototype.toString.call( param ) == '[object Function]';
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
class alarmManage{

    constructor(){
        this.data = {
            alarm : [],
        };
        this.init();
    }
    init(){
        createProxy(this.data,this.searchEntity); //监听data对象
    };
    addAlarm(alarmInformation){

        this.data.alarm.push(alarmInformation);

        return this;
    };
    searchEntity(newEntity){
        let timer = setInterval(()=>{
            let [ entity ] = Vue.prototype.$viewer.entities._entities._array.filter( entitie => ( entitie._moId == newEntity.objectId && entitie.messageType == 'alarm' ) );

            if( global.Cesium.defined( entity ) ){
                clearInterval( timer );

                let isOpen = 1;
                let animationId = null ;
                //定时器刷新,刷新频率 1000 / 60 = 16.7
                // let intervalTimer = setInterval(()=>{
                //     if( !viewer.entities.contains(entity) ) clearInterval(intervalTimer); //实体集中不存在当前实体清除定时器
                //
                //     let image =  isOpen ? 'alarm-open' : 'alarm-close';
                //     entity.billboard.image = require('../../../assets/VM/'+ image +'.png'); //修改告警图片
                //     isOpen = !isOpen;
                //
                // },1000/60)
                let changeImage = ()=>{

                    if( !Vue.prototype.$viewer.entities.contains(entity) ) {
                        global.cancelAnimationFrame(animationId); //实体集中不存在当前实体清除动画
                        return this;
                    }

                    let image =  isOpen ? 'alarm-open' : 'alarm-close';
                    entity.billboard.image = require('../assets/VM/'+ image +'.png'); //修改告警图片
                    isOpen = !isOpen;

                    animationId = global.requestAnimationFrame(changeImage)
                };
                animationId = global.requestAnimationFrame(changeImage); // 调用requestAnimationFrame ，刷新频率随着浏览器刷新频率
            }
        },1000);

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
    constructor(){

    }
    transTime(time){
        let date=new Date();

        date.setFullYear(time.substring(0,4));
        date.setMonth(time.substring(5,7)-1);
        date.setDate(time.substring(8,10));
        date.setHours(time.substring(11,13));
        date.setMinutes(time.substring(14,16));
        date.setSeconds(time.substring(17,19));

        return Date.parse(date);
    };
    timetrans(date){
        var date = new Date( date / 1000 );//如果date为10位需要乘1000

        let Y = date.getFullYear() + '-';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        let D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        let h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        let m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        let s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());

        return Y + M + D + h + m + s;
    };
    getSomeDayDate(addDay){
        var dd = new Date();

        dd.setDate(dd.getDate() + addDay);//获取AddDayCount天后的日期
        let Y = dd.getFullYear();
        let M = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
        let D = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
        let h = dd.getHours()<10?"0"+dd.getHours():dd.getHours();
        let m = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
        let s = dd.getDate()<10?"0"+dd.getDate():dd.getDate();

        return Y+"-"+M+"-"+D+' '+h+":"+m+":"+s;
    };
    comparisonDate(addDay,endDate){
        return parseFloat(this.transTime(this.getSomeDayDate(addDay))) > parseFloat(endDate);
    }
}

Vue.prototype.DM = new DateManage(); //实例化DateManage , 挂载到Vue原型上

/**
 *
 * 初始化并连接MQ服务器
 *
 * opreationId : 1 = 订阅消息，2 = 发送消息
 * targetUrl : 订阅或者发送消息的地址
 * data : 发送消息的内容
 */

class MQ {
    constructor(){
        this.client = null;
        this.ws = null;

    };
    openMQ(MQCallback){

        this._InitMQ(1, "/queue/queueAlarm", "",MQCallback);

    };
    sendMQ(){
        Vue.axios.post('/alarms',
            {
                alarmDate:+new Date(),
                alarmLevel:1,
                tunnelId:1002,
                objectId:1
            })
            .then()
    };
    //初始化MQ
    _InitMQ(){
        let args=[].slice.call(arguments); //类数组转换成数组
console.log('MQServerAddress',MQServerAddress)
        if ('WebSocket' in window) {
            this.ws = new WebSocket(MQServerAddress);
            // ws = new SockJS('http://192.168.0.41:15670/stomp');
        }
        else {
            alert("浏览器不支持WebSocket");
        }
        // 获得Stomp client对象
        this.client = Stomp.over(this.ws);
        // SockJS does not support heart-beat: disable heart-beats
        this.client.heartbeat.outgoing = 2000;
        this.client.heartbeat.incoming = 2000;

        this.client.connect('admin', 'admin', this._onConnect(args), this._reconnectWs(args), '/');
    };
    _onConnect(args){
        let [ opreationId, targetUrl, data,callbacks]=args; //解析数组内容
        let _this = this;

        return function (conn) {

            if ( opreationId == 1 ) {
                return _this._getMessage(targetUrl,callbacks);

            }
            else if ( opreationId == 2 ) {
                return _this._sendMessage(targetUrl, data);
            }
            else {
                return true;
            }
        }
    }
    //接收消息
    _getMessage(url,callbacks){
        this.client.subscribe(url,callbacks);
    };
    //发送信息
    _sendMessage(url, data){
        // start the transaction
        var tx = this.client.begin();
        // send the message in a transaction
        // 最关键的在于要在 headers 对象中加入事务 id，若没有添加，则会直接发送消息，不会以事务进行处理
        let result = this.client.send(url, {transaction: tx.id}, data);
        // commit the transaction to effectively send the message
        tx.commit();
        return result;
    }
    //断连后重连
    _reconnectWs(args){
        let [ opreationId, targetUrl, data,callbacks]=args; //解析数组内容
        let _this = this;

        return function () {
            setTimeout(() => {
                console.log('try to reconnect');
                _this._InitMQ(opreationId, targetUrl, data,callbacks);
            }, 5000);
        }

    };
    //断开与MQ的连接
    closeMQ(){
        this.client.disconnect(()=>{
            console.log("See you next time!");
        });
    }


}

Vue.prototype.MQ = new MQ(); //实例化MQ , 挂载到Vue原型上

