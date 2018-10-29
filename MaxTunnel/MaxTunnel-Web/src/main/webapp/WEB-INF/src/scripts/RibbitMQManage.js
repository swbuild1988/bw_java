import Stomp from 'stompjs'
import Vue from 'vue';

const MQServerAddress = require('../../static/serverconfig').MQServerAddress;

/**
 *
 * 初始化并连接MQ服务器
 * opreationId : 1=订阅消息，2=发送消息
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

Vue.prototype.MQ = new MQ();
