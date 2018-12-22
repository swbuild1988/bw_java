import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

// 应用初始状态
const VMstate = {
    state: {
        autoplay: true, //视屏监控浏览模式，默认轮播
        entity: null, //缓存告警实体
        alarm: {
            alarmEntity: null,
            alarmObject: [],
        },
        planLinkageVideos:{ //预案视屏联动集合
            videoIds : null,
            processInstanceId : null,
        },
    },
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
        addPlanLinkageVideos:({ planLinkageVideos }, { id,processInstanceId }) => {
            planLinkageVideos.videoIds = id;
            planLinkageVideos.processInstanceId = processInstanceId;
        },
        removePlanLinkageVideos: ({ planLinkageVideos }) => {
            planLinkageVideos.videoIds = null ;
            planLinkageVideos.processInstanceId = null;
        }
    }
}

const UMstate = {
  // 1. state
  state: {
    planData:null,
  },
   // 2. getters
  getters:{
    // 参数列表state指的是state数据
    getPlanData(state){
      return state.planData;
    }
  },
  // 3. actions
  // 通常跟api接口打交道
  actions:{
    // 参数列表：{commit, state}
    // state指的是state数据
    // commit调用mutations的方法
    // plan就是调用此方法时要传的参数
    setPlanData({commit,state}, plan){
      // 跟后台打交道
      // 调用mutaions里面的方法
      commit("setPlanData", plan);
    }
  },
  // 4. mutations
  mutations:{
    // state指的是state的数据
    // plan传递过来的数据
    setPlanData(state, plan){
      state.planData = plan;
    }
  }
}

// 创建 store 实例
export default new Vuex.Store({
    modules: {
      VMstate,
      UMstate
    }

})
