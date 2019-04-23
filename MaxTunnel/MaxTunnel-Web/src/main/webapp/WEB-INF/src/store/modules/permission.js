import {
  asyncRouterMap,
  routes,
} from '@/router/index';
/**
 * 判断用户是否拥有此菜单
 * @param menus
 * @param route
 */
function hasPermission(menus, route) {
  const result = {
    status: false,
    codelist: []
  }
  //先判断模块是否需要选项，不需要权限的模块直接添加路由
  if (route.meta && route.meta.roles) {
    if (menus.length) {
      result.status = menus.some((a) => {
        //目前路由配置限制为2层如：/UM/plans,第一次判断属于哪个系统，第二层判断属于系统的那个模块
        let temp = a.menuCode.toLocaleLowerCase().split('/').filter(a => a != "");
        if (route.path.replace('/', "").toLocaleLowerCase() == temp[0] &&
          route.children.some(b => b.path.toLocaleLowerCase() == temp[1])) {
          result.codelist = a.pCodeList;
          return a;
        }
      });
    }
  } else {
    result.status = true;
  }
  return result;
}

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
function filterAsyncRouter(asyncRouterMap, menus) {

  const accessedRouters = asyncRouterMap.filter((route) => {
    // 判断模块是否需要验证权限
    const permission = hasPermission(menus, route);
    if (permission.status) {
      // 过滤有权限的具体功能菜单
      if (route.children && route.children.length) {
        route.children = route.children.filter(child => {
          const result = menus.some((a) => {
            let temp = a.menuCode.toLocaleLowerCase().split('/').filter(a => a != "");
            if (child.path.toLocaleLowerCase() == temp[1]) {
              // console.log(child, child.meta.roles);
              if (child.meta && child.meta.roles) {
                child.meta.roles = permission.codelist;
              } else {
                child.meta = {
                  roles: permission.codelist
                };
              }

              return a;
            }
          });
          return result;
        })
        // console.log(route.children);
        // 如果过滤一圈后,没有子元素了,这个父级菜单就也不显示了
        return (route.children && route.children.length);
      }
      return true;
    }
    return false;
  });
  // console.log(accessedRouters);
  return accessedRouters;
}

const permission = {
  state: {
    routers: routes, // 本用户所有的路由,包括了固定的路由和下面的addRouters
    addRouters: [], // 本用户的角色赋予的新增的动态路由
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers;
      state.routers = routes.concat(routers); // 将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
    },
  },
  actions: {
    GenerateRoutes({
      commit,
    }, userPermission) {
      // 生成路由
      return new Promise((resolve) => {
        // roles是后台传过来的角色数组,比如['管理员','文章']
        const role = userPermission.rList;
        const menus = userPermission.pList;
        // 声明 该角色可用的路由
        let accessedRouters;
        if (role.indexOf('admin') > -1) {
          // 如果角色里包含'管理员',那么所有的路由都可以用
          // 其实管理员也拥有全部菜单,这里主要是利用角色判断,节省加载时间
          accessedRouters = asyncRouterMap;
        } else {
          // 否则需要通过以下方法来筛选出本角色可用的路由
          const tempMap = deepCopy(asyncRouterMap);
          accessedRouters = filterAsyncRouter(tempMap, menus);
        }
        // 执行设置路由的方法
        commit('SET_ROUTERS', accessedRouters);
        resolve();
      });
    },
  },
};
export default permission;
