import {
	asyncRouterMap,
	routes,
} from '@/router/index';
/**
 * 判断用户是否拥有此菜单
 * @param menus
 * @param route
 */

function deepCopy(obj) { // 只拷贝对象 
	if (typeof obj !== 'object') return;
	// 根据obj的类型判断是新建一个数组还是一个对象
	let newObj = obj instanceof Array ? [] : {};
	for (let key in obj) {
		// 遍历obj,并且判断是obj的属性才拷贝 
		if (obj.hasOwnProperty(key)) {
			// 判断属性值的类型，如果是对象递归调用深拷贝 
			newObj[key] = typeof obj[key] === 'object' ? deepCopy(obj[key]) : obj[key];
		}
	}
	return newObj;
}
/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表
 * @param asyncRouterMap
 * @param menus
 */
function filterAsyncRouter(asyncRouters, routeNames) {
	return asyncRouters.reduce((prevArr, curVal) => {
		let obj = {}
		let findFlag = routeNames.find(name => {
			return name === curVal.name || curVal.path == '*'
		})
		if (!!findFlag) {
			obj = curVal
			if (obj.children) {
				obj.children = filterAsyncRouter(curVal.children, routeNames)
			}
		}

		return findFlag ? prevArr.concat(obj) : prevArr
	}, [])
}

const permission = {
	state: {
		routers: routes, // 本用户所有的路由,包括了固定的路由和下面的addRouters
		addRouters: [], // 本用户的角色赋予的新增的动态路由
	},
	mutations: {
		SET_ROUTERS: (state, permissionRoute) => {
			state.addRouters = permissionRoute;
			state.routers = routes.concat(state.addRouters); // 将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
		},
	},
	actions: {
		GenerateRoutes({
			commit,
		}, userPermission) {
			// 生成路由
			return new Promise((resolve) => {
				let accessedRouters, isAdmin, menus = [];
				userPermission.forEach(role => {
					if (role.roleName === 'admin') {
						isAdmin = true
					} else {
						isAdmin = false
					}
				})
				if (!!isAdmin) {
					accessedRouters = asyncRouterMap;
				} else {
					userPermission.map(item => {
						menus = menus.concat(item.routList.split(','))
					})
					const tempMap = deepCopy(asyncRouterMap);
					accessedRouters = filterAsyncRouter(tempMap, Array.from(new Set(menus)));
				}
				// 执行设置路由的方法
				commit('SET_ROUTERS', accessedRouters);
				resolve();
			});
		},
	},
};
export default permission;