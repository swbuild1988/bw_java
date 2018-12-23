import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
  namespaced: true,
  state: {
    autoplay: true, //视屏监控浏览模式，默认轮播
    entity: null, //缓存告警实体
    alarm: {
      alarmEntity: null,
      alarmObject: [],
    },
    planLinkageVideos: { //预案视屏联动集合
      videoIds: null,
      processInstanceId: null,
    },
  },
  getters: {},
  mutations: {
    // 视频轮询
    closeVideoLoop: state => state.autoplay = false,
    startVideoLoop: state => state.autoplay = true,
    //修改告警
    changeAlarm: (state, alarm) => {
      const stateAlarm = state.alarm;

      stateAlarm.alarmObject.splice(0);
      stateAlarm.alarmObject.push(alarm.object);

      stateAlarm.alarmEntity = alarm.entity;
    },
    //预案视屏联动
    addPlanLinkageVideos: ({
                             planLinkageVideos
                           }, {
                             id,
                             processInstanceId
                           }) => {
      planLinkageVideos.videoIds = id;
      planLinkageVideos.processInstanceId = processInstanceId;
    },
    removePlanLinkageVideos: ({
                                planLinkageVideos
                              }) => {
      planLinkageVideos.videoIds = null;
      planLinkageVideos.processInstanceId = null;
    }
  },
  actions: {}
}
