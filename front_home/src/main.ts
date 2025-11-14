import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './assets/styles/globals.css'
import { useUserStore } from './stores/user'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

// 在应用挂载后初始化用户信息
app.mount('#app').$nextTick(async () => {
  const userStore = useUserStore()

  // 初始化用户信息（从localStorage恢复）
  userStore.initUserInfo()

  // 如果有token，尝试刷新用户信息
  if (userStore.token) {
    await userStore.refreshUserInfo()
  }
})

