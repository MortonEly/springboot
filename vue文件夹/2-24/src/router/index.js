import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import store from '../store/store.js';
import { Notification, MessageBox, Message, Loading } from 'element-ui'
import ElementUI from 'element-ui'
Vue.use(VueRouter)

const routes = [

  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: '登录',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
    meta: { requiresAuth: false } // 设置元数据，表示该页面不需要登录权限
  },
  {
    path: '/register',
    name: '注册',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
    meta: { requiresAuth: false } // 设置元数据，表示该页面不需要登录权限
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//重置路由器里面的路由集合
export const resetRoutes = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}
router.addRoute(
  {
    mode: 'history',
    path: '/',
    name: '管理',
    component: Manage,
    // redirect: "/home",
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: () => import(/* webpackChunkName: "about" */ '../views/User.vue'),
        meta: { title: '用户菜单' },
      },
      {
        path: 'role',
        name: '角色管理',
        component: () => import(/* webpackChunkName: "about" */ '../views/Role.vue'),
        meta: { title: '角色菜单' },

      },
      {
        path: 'menu',
        name: '菜单管理',
        component: () => import(/* webpackChunkName: "about" */ '../views/Menu.vue'),
        meta: { title: '菜单信息' },

      },

      {
        path: '', // 将根路径设置为空字符串
        name: '主页信息', // 名称
        component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue'), // 对应的组件
        meta: { title: '主页菜单' }, // 元数据，用于设置页面标题
      },
      {
        path: 'person',
        name: '个人信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue'),
        meta: { title: '个人信息菜单' },
      },
      {
        path: 'file',
        name: '文件',
        component: () => import(/* webpackChunkName: "about" */ '../views/File.vue'),
        meta: { title: '文件管理菜单' },
      },
      {
        path: 'dict',
        name: '字典信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Dict.vue'),
        meta: { title: '字典菜单' },
      },
      {
        path: 'article',
        name: '文章信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Article.vue'),
        meta: { title: '文章信息菜单' },
      },

      {
        path: 'noticle',
        name: '公告信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Noticle.vue'),
        meta: { title: '公告菜单' },
      },
      {
        path: 'carousel',
        name: 'carousel信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Carousel.vue'),
        meta: { title: 'carousel菜单' },
      },
      // 所有未定义路由，全部重定向到404页
      {
        path: '*',
        redirect: '/404'
      }
    ]
  }


)
export const setRoutes = () => {
  //获取浏览器缓存的菜单数据
  const localMenus = localStorage.getItem("menus");
  if (localMenus) {
    const currentRoutes = router.getRoutes().map(v => v.name);
    if (!currentRoutes.includes('manage')) {
      //当前Router不包含manage，在拼装
      const manageRoute = {
        path: '/',
        name: 'manage',
        redirect: '/home',
        component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),
        children: [
          {
            path: 'person',
            name: 'person',
            component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue'),
          },
          {
            path: 'password',
            name: 'password',
            component: () => import(/* webpackChunkName: "about" */ '../views/Password.vue'),
          },
          {
            path: 'home',
            name: 'home',
            component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue'),
          }
        ]
      };
      const menus = JSON.parse(localMenus);
      menus.forEach(item => {
        if (item.path) {
          const itemMenu = {
            path: item.path.replace("/", ""),
            name: item.name,
            component: () => import(/* webpackChunkName: "about" */ '../views/' + item.pagePath + '.vue'),
          };
          manageRoute.children.push(itemMenu);
        } else if (item.children.length) {
          item.children.forEach(item => {
            const itemMenu = {
              path: item.path.replace("/", ""),
              name: item.name,
              component: () => import(/* webpackChunkName: "about" */ '../views/' + item.pagePath + '.vue'),
            };
            manageRoute.children.push(itemMenu);
          })
        }
      })
      router.addRoute(manageRoute);
      console.log('1---重新加入manage路由')
      console.log('1---重新加入manage路由')
      console.log('1---重新加入manage路由')
      console.log(router.getRoutes())
    } else {
      console.log('未重新加入manage路由')
      console.log('未重新加入manage路由')
      console.log('未重新加入manage路由')
      console.log(router.getRoutes())
    }
  }
}
//localStorage.setItem('currentPathName', to.name); // 将当前页面名称存储到本地存储中
// store.commit('setPath')// 提交状态更新
//const localMenus = localStorage.getItem("menu")//localStorage.getItem("menu")获取名为 "menu" 的本地存储数据，并将其赋值给变量 localMenus
// 如果访问的是登录界面并且已经登录，则直接跳转到主页
// router.beforeEach((to, from, next) => {



//   if (to.path === '/login' && window.sessionStorage.getItem('token')) {
//     return next('/user')
//   }
//   // 判断路由是否需要登录权限
//   if (to.matched.some(record => record.meta.requiresAuth)) {
//     // 获取用户页面token信息
//     let token = window.sessionStorage.getItem('token')
//     // 如果token数据为null则跳转到指定路径
//     if (!token) {
//       // 没有登录信息，跳转到登录页面，并显示提示信息
//       return next({
//         path: '/login',
//         query: { message: '请先登录' }
//       })
//     } else {
//       // 验证通过，调用next()将控制权交给下一个钩子函数或路由处理逻辑
//       next()
//     }
//   } else {
//     // 不需要登录权限，直接放行
//     next()
//   }

//   // 如果访问的是不存在的页面，则跳转到404页
//   if (to.matched.length === 0) {
//     return next('/404')
//   }
//   // 如果没有用户信息，则跳转到注册页面，并显示提示信息
//   if (!window.sessionStorage.getItem('userInfo')) {
//     return next({
//       path: '/register',
//       query: { message: '请先注册' }
//     })
//   }

//   localStorage.setItem('currentPathName', to.name);
//   store.commit('setPath')
//   const isAuthenticated = checkAuth()
//   store.commit("setPath")
//   if (to.matched.some(record => record.meta.requiresAuth)) {
//     // 检查路由是否需要登录权限
//     if (!isAuthenticated) {
//       // 如果用户未登录并且要访问需要登录权限的页面，则重定向到登录页
//       next({ name: '登录' })
//     } else {
//       // 用户已登录或要访问不需要登录权限的页面，正常跳转
//       next()
//     }
//   } else if (to.name === '登录' && isAuthenticated) {
//     // 如果用户已登录但访问登录页面，则重定向到主页
//     next({ name: 'Home' })
//   } else {
//     // 其他情况下正常跳转
//     next()
//   }


// function checkAuth() {
//   return localStorage.getItem('token') !== null; // 这里可以根据你的实际登录逻辑来判断用户是否已经登录
// }
router.beforeEach((to, from, next) => {

  localStorage.setItem('currentPathName', to.name);
  store.commit('setPath')
  const localMenus = localStorage.getItem("menus");
  if (to.path === '/login' && window.sessionStorage.getItem('token')) {
    return next('/user')
  }
  // 判断路由是否需要登录权限
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 获取用户页面token信息
    let token = window.sessionStorage.getItem('token')
    // 如果token数据为null则跳转到指定路径
    if (!token) {
      // 没有登录信息，跳转到登录页面，并显示提示信息
      return next({
        path: '/login',
        query: { message: '请先登录' }
      })
    } else {
      // 验证通过，调用next()将控制权交给下一个钩子函数或路由处理逻辑
      next()
    }
  } else {
    // 不需要登录权限，直接放行
    next()
  }
  // 如果访问的是不存在的页面，则跳转到404页
  if (to.matched.length === 0) {
    return next('/404')
  }
  // 如果没有用户信息，则跳转到注册页面，并显示提示信息
  // if (!window.sessionStorage.getItem('userInfo')) {
  //   if (to.path === '/register') {
  //     // 当前已经是注册页面，直接放行
  //     next()
  //   } else {
  //     next({
  //       path: '/register',
  //       query: { message: '请先注册' }
  //     })
  //   }
  // } else {
  //   next()
  // }
})
export default router