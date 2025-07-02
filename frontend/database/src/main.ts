import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import request from '@/utils/request'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 全局配置
app.config.globalProperties.$request = request
app.config.globalProperties.$baseUrl = import.meta.env.VITE_APP_BASEURL

app.mount('#app')
