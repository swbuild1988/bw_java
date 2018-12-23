import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
  namespaced: true,
  // 1. state
  state: {
    planData: null,
    communication: {
      isLogin: false,
      token: null,
      intervalId: null
    }
  },
  // 2. getters
  getters: {
    // 参数列表state指的是state数据
    getPlanData(state) {
      return state.planData;
    }
  },
  // 3. actions
  // 通常跟api接口打交道
  actions: {
    // 参数列表：{commit, state}
    // state指的是state数据
    // commit调用mutations的方法
    // plan就是调用此方法时要传的参数
    setPlanData({commit, state}, plan) {
      // 跟后台打交道
      // 调用mutaions里面的方法
      commit("setPlanData", plan);
    },
  },
  // 4. mutations
  mutations: {
    // state指的是state的数据
    // plan传递过来的数据
    setPlanData(state, plan) {
      state.planData = plan;
    },

    comLogin(state, data) {
      state.communication.isLogin = data.isLogin
      state.communication.token = data.token
    },
    comLogout(state) {
      state.communication.isLogin = false
      state.communication.token = null
    },
    startInterval(state, id) {
      state.communication.intervalId = id
    },
    stopInterval(state) {
      state.communication.intervalId = null
    }
  }
}
