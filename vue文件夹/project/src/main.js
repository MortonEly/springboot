import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from "@/store/store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import request from '@/utils/request.js';
import * as echarts from 'echarts';
//样式
import './css/global.css';
import './css/Login.css';
import './css/register.css';
import './css/file.css';
import './css/person.css';
import './css/vue.css';
import './css/404.css';
import './css/Menu.css'
import './css/role.css';
// 引入 Message 组件
import { Message } from 'element-ui'
import $ from 'jquery'
import { Button } from 'vant';
import { Icon } from 'vant';
import 'vant/lib/index.css';
//滑动验证码
import dragVerify from 'vue-drag-verify2'
Vue.use(dragVerify)
// Tag使用
import Tag from "vue2-tag"
Vue.use(Tag)

Vue.use(Icon);
Vue.use(Button);
Vue.prototype.$ = $
Vue.config.productionTip = false;
//vue全局注入echarts
Vue.prototype.$echarts = echarts;
Vue.use(ElementUI, { size: "medium" });
Vue.prototype.$http = axios;
Vue.prototype.request = request;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');