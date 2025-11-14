<template>
  <div
    v-if="isOpen"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm"
    @click.self="closeDialog"
  >
    <div class="relative w-full max-w-md mx-4 bg-white rounded-2xl shadow-2xl overflow-hidden">
      <!-- 关闭按钮 -->
      <button
        @click="closeDialog"
        class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 transition-colors z-10"
      >
        <X class="h-5 w-5" />
      </button>

      <!-- 背景装饰 -->
      <div class="absolute top-0 left-0 w-full h-32 bg-gradient-to-br from-red-500 to-pink-500 opacity-10"></div>

      <div class="relative p-8">
        <!-- 头部 -->
        <div class="text-center mb-8">
          <div class="inline-flex items-center justify-center w-16 h-16 mb-4 bg-gradient-to-br from-red-500 to-pink-500 rounded-2xl">
            <User class="h-8 w-8 text-white" />
          </div>
          <h2 class="text-2xl font-bold text-gray-900 mb-2">
            {{ isLoginMode ? '欢迎回来' : '创建账号' }}
          </h2>
          <p class="text-gray-600 text-sm">
            {{ isLoginMode ? '登录你的账号继续' : '注册新账号开始使用' }}
          </p>
        </div>

        <!-- 表单 -->
        <form @submit.prevent="handleSubmit" class="space-y-4">
          <!-- 用户名 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
            <div class="relative">
              <User class="absolute left-3 top-3 h-5 w-5 text-gray-400" />
              <Input
                v-model="formData.username"
                type="text"
                placeholder="请输入用户名"
                class="pl-10"
                required
              />
            </div>
          </div>

          <!-- 邮箱（仅注册） -->
          <div v-if="!isLoginMode">
            <label class="block text-sm font-medium text-gray-700 mb-2">邮箱（可选）</label>
            <div class="relative">
              <Mail class="absolute left-3 top-3 h-5 w-5 text-gray-400" />
              <Input
                v-model="formData.email"
                type="email"
                placeholder="请输入邮箱"
                class="pl-10"
              />
            </div>
          </div>

          <!-- 密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">密码</label>
            <div class="relative">
              <Lock class="absolute left-3 top-3 h-5 w-5 text-gray-400" />
              <Input
                v-model="formData.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="请输入密码"
                class="pl-10 pr-10"
                required
              />
              <button
                type="button"
                @click="showPassword = !showPassword"
                class="absolute right-3 top-3 text-gray-400 hover:text-gray-600"
              >
                <EyeOff v-if="showPassword" class="h-5 w-5" />
                <Eye v-else class="h-5 w-5" />
              </button>
            </div>
          </div>

          <!-- 确认密码（仅注册） -->
          <div v-if="!isLoginMode">
            <label class="block text-sm font-medium text-gray-700 mb-2">确认密码</label>
            <div class="relative">
              <Lock class="absolute left-3 top-3 h-5 w-5 text-gray-400" />
              <Input
                v-model="formData.confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                placeholder="请再次输入密码"
                class="pl-10 pr-10"
                required
              />
              <button
                type="button"
                @click="showConfirmPassword = !showConfirmPassword"
                class="absolute right-3 top-3 text-gray-400 hover:text-gray-600"
              >
                <EyeOff v-if="showConfirmPassword" class="h-5 w-5" />
                <Eye v-else class="h-5 w-5" />
              </button>
            </div>
          </div>

          <!-- 错误提示 -->
          <div v-if="errorMessage" class="flex items-center gap-2 p-3 bg-red-50 border border-red-200 rounded-lg">
            <AlertCircle class="h-4 w-4 text-red-500 flex-shrink-0" />
            <p class="text-sm text-red-600">{{ errorMessage }}</p>
          </div>

          <!-- 提交按钮 -->
          <Button
            type="submit"
            class="w-full bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white font-medium py-3"
            :disabled="loading"
          >
            <Loader2 v-if="loading" class="mr-2 h-4 w-4 animate-spin" />
            {{ loading ? '处理中...' : (isLoginMode ? '登录' : '注册') }}
          </Button>
        </form>

        <!-- 切换模式 -->
        <div class="mt-6 text-center">
          <p class="text-sm text-gray-600">
            {{ isLoginMode ? '还没有账号？' : '已有账号？' }}
            <button
              @click="toggleMode"
              class="text-red-500 hover:text-red-600 font-medium transition-colors"
            >
              {{ isLoginMode ? '立即注册' : '立即登录' }}
            </button>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { User, Lock, Mail, Eye, EyeOff, X, AlertCircle, Loader2 } from 'lucide-vue-next'
import Input from '@/components/ui/Input.vue'
import Button from '@/components/ui/Button.vue'
import { login as loginApi, register as registerApi } from '@/api/auth'
import { useUserStore } from '@/stores/user'
import { rsaEncrypt } from '@/utils/crypt'

const props = defineProps<{
  isOpen: boolean
}>()

const emit = defineEmits<{
  close: []
  success: []
}>()

const userStore = useUserStore()

const isLoginMode = ref(true)
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const loading = ref(false)
const errorMessage = ref('')

const formData = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: ''
})

// 切换登录/注册模式
const toggleMode = () => {
  isLoginMode.value = !isLoginMode.value
  errorMessage.value = ''
  formData.password = ''
  formData.confirmPassword = ''
}

// 关闭对话框
const closeDialog = () => {
  emit('close')
  // 重置表单
  setTimeout(() => {
    formData.username = ''
    formData.password = ''
    formData.confirmPassword = ''
    formData.email = ''
    errorMessage.value = ''
    isLoginMode.value = true
  }, 300)
}

// 表单验证
const validateForm = (): boolean => {
  // 检查用户名
  if (!formData.username || formData.username.trim() === '') {
    errorMessage.value = '请填写用户名'
    return false
  }

  // 检查密码
  if (!formData.password || formData.password.trim() === '') {
    errorMessage.value = '请填写密码'
    return false
  }

  // 注册模式下检查确认密码
  if (!isLoginMode.value) {
    if (!formData.confirmPassword || formData.confirmPassword.trim() === '') {
      errorMessage.value = '请填写确认密码'
      return false
    }
  }

  // 检查用户名长度
  if (formData.username.trim().length < 4) {
    errorMessage.value = '用户名至少4个字符'
    return false
  }

  // 检查密码长度
  if (formData.password.length < 6) {
    errorMessage.value = '密码至少6个字符'
    return false
  }

  // 注册模式下检查密码是否一致
  if (!isLoginMode.value && formData.password !== formData.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致'
    return false
  }

  return true
}

// 提交表单
const handleSubmit = async () => {
  errorMessage.value = ''
  
  console.log('开始提交表单, isLoginMode:', isLoginMode.value)
  console.log('表单数据:', {
    username: formData.username,
    password: formData.password ? '***' : '(空)',
    confirmPassword: formData.confirmPassword ? '***' : '(空)',
    email: formData.email,
    usernameLength: formData.username?.length || 0,
    passwordLength: formData.password?.length || 0,
    confirmPasswordLength: formData.confirmPassword?.length || 0
  })
  
  if (!validateForm()) {
    console.log('表单验证失败, errorMessage:', errorMessage.value)
    return
  }
  
  console.log('表单验证通过，准备发送请求')

  loading.value = true
  console.log('开始发送请求...')

  try {
    if (isLoginMode.value) {
      // 登录
      console.log('发送登录请求...')
      // 对密码进行RSA加密
      const encryptedPassword = rsaEncrypt(formData.password)
      console.log('密码已加密')
      
      const response = await loginApi({
        username: formData.username,
        password: encryptedPassword
      })

      console.log('登录响应:', response)
      console.log('响应数据结构:', {
        code: response.code,
        hasData: !!response.data,
        dataKeys: response.data ? Object.keys(response.data) : [],
        currentUser: response.data?.currentUser,
        token: response.data?.token
      })

      // 后端成功码是0，兼容200
      if ((response.code === 0 || response.code === 200) && response.data) {
        // 登录成功
        const token = response.data.token
        const currentUser = response.data.currentUser
        const userData = currentUser?.userInfo
        
        console.log('用户数据:', userData)
        
        if (!token) {
          errorMessage.value = '登录失败：未获取到token'
          return
        }
        
        if (!userData) {
          errorMessage.value = '登录失败：未获取到用户信息'
          return
        }
        
        // 登录成功，保存用户信息
        userStore.login(token, {
          userId: userData.userId,
          username: userData.username || userData.userName,
          nickname: userData.nickName || userData.nickname || userData.username || userData.userName,
          avatar: userData.avatar || new URL('../img/361084_2.gif', import.meta.url).href,
          email: userData.email
        })

        console.log('登录成功，用户信息已保存')
        console.log('当前登录状态:', userStore.isLoggedIn)

        // 刷新用户信息以确保数据是最新的
        await userStore.refreshUserInfo()
        
        // 重置loading状态
        loading.value = false
        
        // 触发成功事件并关闭对话框
        emit('success')
        // 延迟关闭以确保状态更新
        setTimeout(() => {
          closeDialog()
        }, 100)
      } else {
        errorMessage.value = response.msg || '登录失败，请检查用户名和密码'
      }
    } else {
      // 注册
      console.log('发送注册请求...', {
        username: formData.username,
        password: formData.password,
        confirmPassword: formData.confirmPassword,
        email: formData.email
      })
      
      const response = await registerApi({
        username: formData.username,
        password: formData.password,
        confirmPassword: formData.confirmPassword,
        email: formData.email
      })

      console.log('注册响应:', response)

      // 后端成功码是0，兼容200
      if (response.code === 0 || response.code === 200) {
        // 注册成功，自动切换到登录模式
        errorMessage.value = ''
        isLoginMode.value = true
        formData.password = ''
        formData.confirmPassword = ''
        // 可以显示成功提示
        alert('注册成功！请登录')
      } else {
        errorMessage.value = response.msg || '注册失败，请稍后重试'
      }
    }
  } catch (error: any) {
    console.error('Auth error:', error)
    console.error('Error details:', {
      message: error.message,
      response: error.response,
      data: error.response?.data
    })
    // 处理HTTP错误响应
    if (error.response?.data?.msg) {
      errorMessage.value = error.response.data.msg
    } else if (error.message) {
      errorMessage.value = error.message
    } else {
      errorMessage.value = '操作失败，请稍后重试'
    }
  } finally {
    loading.value = false
  }
}

// 监听对话框打开/关闭
watch(() => props.isOpen, (newVal) => {
  if (newVal) {
    errorMessage.value = ''
  }
})
</script>


