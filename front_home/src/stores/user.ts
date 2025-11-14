import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getUserInfo as getUserInfoApi } from '@/api/auth'

export interface UserInfo {
  userId: number
  username: string
  nickname: string
  avatar: string
  email?: string
}

export const useUserStore = defineStore('user', () => {
  // 状态
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)
  
  // 计算属性
  const isLoggedIn = computed(() => !!token.value && !!userInfo.value)
  // 使用相对于src目录的路径，Vite会自动处理
  const userAvatar = computed(() => {
    if (userInfo.value?.avatar) {
      return userInfo.value.avatar
    }
    // 使用动态import来引用本地图片
    return new URL('../img/361084_2.gif', import.meta.url).href
  })
  const displayName = computed(() => {
    if (!userInfo.value) return '游客'
    return userInfo.value.nickname || userInfo.value.username || '用户'
  })
  
  // 登录
  const login = (userToken: string, user: UserInfo) => {
    token.value = userToken
    userInfo.value = user
    localStorage.setItem('token', userToken)
    localStorage.setItem('userInfo', JSON.stringify(user))
  }
  
  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }
  
  // 初始化用户信息（从localStorage恢复）
  const initUserInfo = () => {
    const savedUserInfo = localStorage.getItem('userInfo')
    if (savedUserInfo && token.value) {
      try {
        userInfo.value = JSON.parse(savedUserInfo)
      } catch (e) {
        console.error('Failed to parse user info:', e)
        logout()
      }
    }
  }
  
  // 更新用户信息
  const updateUserInfo = (info: Partial<UserInfo>) => {
    if (userInfo.value) {
      userInfo.value = { ...userInfo.value, ...info }
      localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
    }
  }

  // 刷新用户信息（从服务器获取最新的用户信息）
  const refreshUserInfo = async () => {
    if (!token.value) {
      return false
    }

    try {
      const response = await getUserInfoApi()
      if (response.code === 0 && response.data) {
        const userData = response.data
        userInfo.value = {
          userId: userData.userId || userData.id,
          username: userData.username || userData.userName,
          nickname: userData.nickName || userData.nickname || userData.username || userData.userName,
          avatar: userData.avatar || '',
          email: userData.email
        }
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
        return true
      }
    } catch (error) {
      console.error('Failed to refresh user info:', error)
      // 如果 token 无效，清除登录状态
      logout()
    }
    return false
  }
  
  return {
    token,
    userInfo,
    isLoggedIn,
    userAvatar,
    displayName,
    login,
    logout,
    initUserInfo,
    updateUserInfo,
    refreshUserInfo
  }
})

