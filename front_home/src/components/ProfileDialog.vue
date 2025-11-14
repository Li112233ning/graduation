<template>
  <Teleport to="body">
    <!-- 背景遮罩 -->
    <Transition name="fade">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm"
        @click="handleClose"
      ></div>
    </Transition>

    <!-- 个人中心弹窗 -->
    <Transition name="modal">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
        @click.self="handleClose"
      >
        <div
          class="relative w-full max-w-md bg-white rounded-2xl shadow-2xl overflow-hidden"
          @click.stop
        >
          <!-- 关闭按钮 -->
          <button
            @click="handleClose"
            class="absolute top-4 right-4 z-10 w-10 h-10 flex items-center justify-center rounded-full bg-gray-100 hover:bg-gray-200 text-gray-600 hover:text-gray-900 transition-all duration-300"
          >
            <X class="h-5 w-5" />
          </button>

          <!-- 头部背景 -->
          <div class="h-32 bg-gradient-to-br from-red-500 to-pink-500"></div>

          <!-- 内容区域 -->
          <div class="relative px-6 pb-6">
            <!-- 头像 -->
            <div class="flex justify-center -mt-16 mb-4">
              <div class="relative group">
                <Avatar class="h-32 w-32 border-4 border-white shadow-lg">
                  <AvatarImage :src="userStore.userAvatar" :alt="userStore.displayName" />
                  <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-white text-3xl">
                    {{ userStore.displayName.substring(0, 2) }}
                  </AvatarFallback>
                </Avatar>
                <!-- 上传头像按钮 -->
                <label
                  class="absolute bottom-0 right-0 w-10 h-10 flex items-center justify-center rounded-full bg-red-500 text-white cursor-pointer hover:bg-red-600 transition-colors shadow-lg"
                >
                  <Camera class="h-5 w-5" />
                  <input
                    type="file"
                    accept="image/*"
                    class="hidden"
                    @change="handleAvatarUpload"
                  />
                </label>
              </div>
            </div>

            <!-- 用户信息 -->
            <div class="text-center mb-6">
              <h2 class="text-2xl font-bold text-gray-900 mb-1">{{ userStore.displayName }}</h2>
              <p class="text-sm text-gray-500">@{{ userStore.userInfo?.username }}</p>
            </div>

            <!-- 统计信息 -->
            <div class="grid grid-cols-3 gap-4 mb-6 p-4 bg-gray-50 rounded-lg">
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900">0</div>
                <div class="text-xs text-gray-500">稿件</div>
              </div>
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900">0</div>
                <div class="text-xs text-gray-500">粉丝</div>
              </div>
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900">0</div>
                <div class="text-xs text-gray-500">关注</div>
              </div>
            </div>

            <!-- 编辑表单 -->
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">昵称</label>
                <Input
                  v-model="formData.nickname"
                  placeholder="请输入昵称"
                  class="w-full"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">邮箱</label>
                <Input
                  v-model="formData.email"
                  type="email"
                  placeholder="请输入邮箱"
                  class="w-full"
                />
              </div>

              <!-- 错误提示 -->
              <div v-if="errorMessage" class="flex items-center gap-2 p-3 bg-red-50 border border-red-200 rounded-lg">
                <AlertCircle class="h-4 w-4 text-red-500 flex-shrink-0" />
                <p class="text-sm text-red-600">{{ errorMessage }}</p>
              </div>

              <!-- 成功提示 -->
              <div v-if="successMessage" class="flex items-center gap-2 p-3 bg-green-50 border border-green-200 rounded-lg">
                <CheckCircle class="h-4 w-4 text-green-500 flex-shrink-0" />
                <p class="text-sm text-green-600">{{ successMessage }}</p>
              </div>

              <!-- 保存按钮 -->
              <Button
                @click="handleSave"
                class="w-full bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white font-medium"
                :disabled="loading"
              >
                <Loader2 v-if="loading" class="mr-2 h-4 w-4 animate-spin" />
                {{ loading ? '保存中...' : '保存修改' }}
              </Button>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { X, Camera, AlertCircle, CheckCircle, Loader2 } from 'lucide-vue-next'
import Avatar from '@/components/ui/Avatar.vue'
import AvatarImage from '@/components/ui/AvatarImage.vue'
import AvatarFallback from '@/components/ui/AvatarFallback.vue'
import Input from '@/components/ui/Input.vue'
import Button from '@/components/ui/Button.vue'
import { useUserStore } from '@/stores/user'
import { updateUserProfile, uploadAvatar } from '@/api/user'

const props = defineProps<{
  isOpen: boolean
}>()

const emit = defineEmits<{
  close: []
}>()

const userStore = useUserStore()
const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const formData = reactive({
  nickname: '',
  email: ''
})

// 初始化表单数据
watch(() => props.isOpen, (newVal) => {
  if (newVal && userStore.userInfo) {
    formData.nickname = userStore.userInfo.nickname || ''
    formData.email = userStore.userInfo.email || ''
    errorMessage.value = ''
    successMessage.value = ''
  }
})

// 关闭弹窗
const handleClose = () => {
  emit('close')
}

// 保存修改
const handleSave = async () => {
  errorMessage.value = ''
  successMessage.value = ''

  if (!formData.nickname.trim()) {
    errorMessage.value = '昵称不能为空'
    return
  }

  loading.value = true

  try {
    const response = await updateUserProfile({
      nickname: formData.nickname.trim(),
      email: formData.email.trim()
    })

    if (response.code === 0) {
      // 更新本地用户信息
      userStore.updateUserInfo({
        nickname: formData.nickname.trim(),
        email: formData.email.trim()
      })

      successMessage.value = '保存成功！'

      // 2秒后关闭弹窗
      setTimeout(() => {
        handleClose()
      }, 2000)
    } else {
      errorMessage.value = response.msg || '保存失败，请稍后重试'
    }
  } catch (error: any) {
    console.error('保存失败:', error)
    errorMessage.value = error.response?.data?.msg || '保存失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

// 上传头像
const handleAvatarUpload = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]

  if (!file) return

  // 验证文件类型
  if (!file.type.startsWith('image/')) {
    errorMessage.value = '请选择图片文件'
    return
  }

  // 验证文件大小（限制为2MB）
  if (file.size > 2 * 1024 * 1024) {
    errorMessage.value = '图片大小不能超过2MB'
    return
  }

  loading.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    const formData = new FormData()
    formData.append('file', file)

    const response = await uploadAvatar(formData)

    if (response.code === 0 && response.data) {
      // 更新本地用户头像
      userStore.updateUserInfo({
        avatar: response.data.url
      })

      successMessage.value = '头像上传成功！'
    } else {
      errorMessage.value = response.msg || '头像上传失败'
    }
  } catch (error: any) {
    console.error('头像上传失败:', error)
    errorMessage.value = error.response?.data?.msg || '头像上传失败，请稍后重试'
  } finally {
    loading.value = false
    // 清空input，允许重复上传同一文件
    target.value = ''
  }
}
</script>

<style scoped>
/* 背景遮罩动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 弹窗动画 */
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
  transform: scale(0.95) translateY(10px);
}
</style>