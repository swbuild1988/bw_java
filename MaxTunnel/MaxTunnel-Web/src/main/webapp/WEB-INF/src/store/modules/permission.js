import {PermissionService} from '../../services/permissionService.js'
import router, {DynamicRoutes, InitRoutes} from '../../router/index'
import {recursionRouter, setDefaultRoute} from '../../scripts/menuOpertion.js'

export default {
  namespaced: true,
  state: {
    permissionList: null /** 所有路由 */,
    sidebarMenu: [] /** 导航菜单 */,
    currentMenu: '' /** 当前active导航菜单 */
  },
  getters: {},
  mutations: {
    SET_PERMISSION(state, routes) {
      state.permissionList = routes
    },
    CLEAR_PERMISSION(state) {
      state.permissionList = null
    },
    SET_MENU(state, menu) {
      state.sidebarMenu = menu
    },
    CLEAR_MENU(state) {
      state.sidebarMenu = []
    },
    SET_CURRENT_MENU(state, currentMenu) {
      state.currentMenu = currentMenu
    }
  },
  actions: {
    FETCH_PERMISSION({commit, state}) {
      let permissionList = [];
      PermissionService.fetchPermission().then((result) => {
        result = [
          {
            path: '/VMMain',
            name: '可视化主页',
            component: 'VMMain',
            meta: {
              title: 'permission',
            },
          },
          {
            path: '/UMMain',
            component: 'UMMain',
            name: '综合管廊主页',
            meta: {
              title: 'permission',
            },
          },
          {
            path: '/404',
            component: 'NotFound',
            name: '丢失页面',
            hidden: true
          },
          {
            path: '*',
            hidden: true,
            redirect: {
              path: '/404'
            }
          },
          {
            path: '/',
            redirect: {
              path: '/UMMain'
            }
          }];
        if (result) {
          permissionList = result;
          /*  根据权限筛选出我们设置好的路由并加入到path=''的children */
          let routes = recursionRouter(permissionList, router);
          // console.log("router", router,"routes",routes);
          let MainContainer = DynamicRoutes.find(v => v.path === '/')
          let children = MainContainer.children
          children.push(routes)
          /* 生成导航菜单 */
          commit('SET_MENU', children)
          /*  为所有有children的菜单路由设置第一个children为默认路由
              主要是供面包屑用，防止点击面包屑后进入某个路由下的 '' 路由,比如/manage/
              而我们的路由是 [/manage/menu1, /manage/menu2]*/
          setDefaultRoute([MainContainer])
          /*  初始路由 */
          let initialRoutes = router.options.routes
          /*  动态添加路由 */
          router.addRoutes(DynamicRoutes)
          /* 拼接出完整的路由表 */
          commit('SET_PERMISSION', [initialRoutes, DynamicRoutes])
        }
      })
    }
  }
}
