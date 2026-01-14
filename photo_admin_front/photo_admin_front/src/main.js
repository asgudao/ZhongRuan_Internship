//导入全局样式（一般全局样式文件放在src/assets目录下）
//import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

//导入pinia持久化插件
import persistedstate from 'pinia-plugin-persistedstate'
//导入ElementPlus组件
import ElementPlus from 'element-plus'
//导入ElementPlus样式
import 'element-plus/dist/index.css'
//导入element-plus/icon图标组件
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//返回vue对象
const app = createApp(App)
//创建pinia对象
const pinia = createPinia()
//pinia对象使用存储插件对象，这样的话 定义的store就可以持久化了
pinia.use(persistedstate)
//vue对象使用pinia对象
app.use(pinia)
//vue对象使用router 就是 router对象赋值给vue对象，这样router才能生效
app.use(router)
//使用ElementPlus组件
app.use(ElementPlus)
//遍历ElementPlusIconsVue对象所有属性（icon图标组件），注册成全局组件，页面直接可以使用，不用再单独import了
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//挂在DOM对象上
app.mount('#app')
