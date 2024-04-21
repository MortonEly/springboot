import Vue from 'vue'
import App from './App.vue'
import Aside from './components/Aside.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import axios from 'axios'
import request from './utils/request'
import './css/home.css'
import './css/homeview.css'
import './css/login.css'
import './css/register.css'

import echarts from 'echarts'
Vue.component('Aside', Aside)
Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios
Vue.use(Element)
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
