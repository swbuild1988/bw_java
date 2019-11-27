import {
	LoginService,
} from '../../services/loginService';
// import {
// 	removeToken,
// 	setToken,
// } from '@/utils/auth';
import session from '@/utils/auth'
import store from '../../store';
import router from '../../router';
import Cookies from 'js-cookie';
const user = {
	state: {
		name: '',
		userId: '',
		avatar: '',
		role: [],
		menus: [],
		permissions: [],
	},
	mutations: {
		SET_USER: (state, userInfo) => {
			state.name = userInfo.name;
			state.userId = userInfo.userId;
			state.role = []
			state.menus = []
			let menus = []
			userInfo.forEach(user=>{
				state.role.push(user.roleName)
				if(user.roleName !== 'admin'){
				menus = menus.concat(user.routList.split(','))
				}
			})
			state.menus = Array.from(new Set(menus))
			state.permissions = userInfo.pList;
		},
		RESET_USER: (state) => {
			state.name = '';
			state.userId = '';
			state.role = [];
			state.menus = [];
			state.permissions = [];
		},
  },
  actions: {
    // 登录
		Login({
		commit,
		}, loginForm) {
		return new Promise((resolve, reject) => {
			LoginService.UmLogin(loginForm).then((data) => {
				resolve(data);
			}).catch((err) => {
				reject(err);
			});
		});
    },

    // 获取用户信息
    GetInfo({
      commit,
      state,
    }) {
      	return new Promise((resolve, reject) => {
			LoginService.getInfo().then((data) => {
				// 储存用户信息
				let roleFlag = data.filter(role=>{
					return role.roleName.length > 0
				})
				if(data.length && roleFlag){
					commit('SET_USER', data);
					// cookie保存登录状态,仅靠vuex保存的话,页面刷新就会丢失登录状态
					// setToken();
					// 生成路由
					store.dispatch('GenerateRoutes', data).then(() => {
						// 生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
						router.addRoutes(store.getters.addRouters);
					});
					resolve(data);
				} else {
					reject('您没有权限！！！')
				}
				
			}).catch((error) => {
				reject(error);
			});
      	});
    },
    // 登出
    LogOut({
      commit,
    }) {
      	return new Promise((resolve) => {
			LoginService.UMLogout().then((data) => {
				commit('RESET_USER');
                session.removeSession();
				resolve(data);
			}).catch(() => {
				commit('RESET_USER');
                session.removeSession();
			});
		}).catch(() => {
			console.log("注销时出错了！");
		});
    },
    // 前端 登出
    FedLogOut({
      commit,
    }) {
		return new Promise((resolve) => {
			commit('RESET_USER');
            session.removeSession();
			// removeToken();
			localStorage.removeItem('Authorization')
            localStorage.removeItem('userName')
			resolve();
		});
    	},
  	},
};
export default user;
