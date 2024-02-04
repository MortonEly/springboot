import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import store from '../store/store';

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
const setRoute = () => {
  const storeMenus = localStorage.getItem("menus");
}
router.addRoute(
  {
    mode: 'history',
    path: '/',
    name: '管理',
    component: Manage,
    redirect: "/home",
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
        path: 'home',
        name: '主页信息',
        component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue'),
        meta: { title: '主页菜单' },
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
      // 所有未定义路由，全部重定向到404页
      {
        path: '*',
        redirect: '/404'
      }
    ]
  }
)
router.beforeEach((to, from, next) => {
  localStorage.setItem('currentPathName', to.name);
  store.commit('setPath')
  const isAuthenticated = checkAuth()
  store.commit("setPath")
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 检查路由是否需要登录权限
    if (!isAuthenticated) {
      // 如果用户未登录并且要访问需要登录权限的页面，则重定向到登录页
      next({ name: '登录' })
    } else {
      // 用户已登录或要访问不需要登录权限的页面，正常跳转
      next()
    }
  } else if (to.name === '登录' && isAuthenticated) {
    // 如果用户已登录但访问登录页面，则重定向到主页
    next({ name: 'Home' })
  } else {
    // 其他情况下正常跳转
    next()
  }
})

function checkAuth() {
  return localStorage.getItem('token') !== null; // 这里可以根据你的实际登录逻辑来判断用户是否已经登录
}

export default router