import Vue from 'vue'

var Audio = {
    _audioCtx: null,
    // 初始化
    init() {
        window.AudioContext = window.AudioContext || window.webkitAudioContext;
        this._audioCtx = new AudioContext();
    },
    // 发出告警声
    createAlarmVoice() {
        console.log("发出告警声")

        let audioCtx = this._audioCtx
        // 创建波形（音调）
        let oscillator = this._audioCtx.createOscillator()
        // 创建音量
        let gainNode = this._audioCtx.createGain()
        // 音调和音量关联
        oscillator.connect(gainNode);
        // 和音频设备关联
        gainNode.connect(audioCtx.destination);
        /**
         * 设置波形
         * sine：正弦波
         * square：方波
         * triangle：三角波
         * sawtooth：锯齿波
         */
        oscillator.type = 'square';
        // 波形的评率，可以理解为音调（多瑞米发嗦啦西），数值越小，声音越低沉，越大提琴；数值越大，声音越清脆，越小提琴。
        oscillator.frequency.value = 800.00;
        // 第一个参数表示音量，范围0~1
        gainNode.gain.setValueAtTime(0, audioCtx.currentTime);
        // 表示音量在某时间线性变化到某值，这里表示0.01s内，音量从0~1
        gainNode.gain.linearRampToValueAtTime(1, audioCtx.currentTime + 0.5);
        // 开始播放，出声了
        oscillator.start(audioCtx.currentTime);
        // 音量在磨时间指数变化到某值，下面表示1s内从1以指数曲线的速度降到0.01音量
        // gainNode.gain.exponentialRampToValueAtTime(0.001, audioCtx.currentTime + 2);
        gainNode.gain.linearRampToValueAtTime(0.001, audioCtx.currentTime + 15);
        // n秒后，声音关闭
        oscillator.stop(audioCtx.currentTime + 15);
    }
}

export {
    Audio
}

// 启动给MQ加一个监听，来告警了就叫唤
(function () {
    let TransferStation = Vue.prototype.TransferStation

    Audio.init()
    TransferStation.addListener("MakeSound", callback)
}())

// 收到告警的回调
function callback(data) {
    console.log("Audio.js 收到MQ消息")

    Audio.createAlarmVoice();
}