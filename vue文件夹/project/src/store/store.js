import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
/**
 * 自定义插件
 * 
 */
// const logger = (store) => {
//     console.log(store),
//         store.subscribe()
// }
const store = new Vuex.Store({
  state: {
    currentPathName: ''
  },
  getters: {

  },
  mutations: {
    setPath(state) {
      state.currentPathName = localStorage.getItem('currentPathName');
    }
  },
  actions: {

  },
  modules: {

  },
});

export default store;