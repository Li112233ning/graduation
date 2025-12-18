<template>
  <div class="min-h-screen bg-gradient-to-b from-red-50 via-pink-50 to-orange-50">
    <div class="container mx-auto px-4 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">个人中心</h1>
        <p class="mt-2 text-gray-600">管理您的个人信息和内容</p>
      </div>
      
      <Tabs default-value="info" class="w-full">
        <TabsList class="grid w-full grid-cols-5 bg-white/80 backdrop-blur-sm p-1 rounded-lg shadow-sm border border-gray-200/50">
          <TabsTrigger value="info" class="data-[state=active]:bg-gradient-to-r data-[state=active]:from-red-500 data-[state=active]:to-pink-500 data-[state=active]:text-white transition-all duration-300">
            个人信息
          </TabsTrigger>
          <TabsTrigger value="likes" class="data-[state=active]:bg-gradient-to-r data-[state=active]:from-red-500 data-[state=active]:to-pink-500 data-[state=active]:text-white transition-all duration-300">
            我的点赞
          </TabsTrigger>
          <TabsTrigger value="favorites" class="data-[state=active]:bg-gradient-to-r data-[state=active]:from-red-500 data-[state=active]:to-pink-500 data-[state=active]:text-white transition-all duration-300">
            我的收藏
          </TabsTrigger>
          <TabsTrigger value="application" class="data-[state=active]:bg-gradient-to-r data-[state=active]:from-red-500 data-[state=active]:to-pink-500 data-[state=active]:text-white transition-all duration-300">
            达人申请
          </TabsTrigger>
          <TabsTrigger value="content" v-if="isInfluencer" class="data-[state=active]:bg-gradient-to-r data-[state=active]:from-red-500 data-[state=active]:to-pink-500 data-[state=active]:text-white transition-all duration-300">
            我的稿件
          </TabsTrigger>
        </TabsList>
        
        <TabsContent value="info" class="mt-6">
          <Card class="p-8 bg-white/80 backdrop-blur-sm shadow-lg rounded-lg border border-gray-200/50">
            <h2 class="text-2xl font-semibold mb-6 text-gray-900">个人信息</h2>
            <!-- 消息提示 -->
            <div v-if="message" 
                 :class="[
                   'mb-4 p-3 rounded-lg border flex items-center gap-2',
                   messageType === 'success' 
                     ? 'bg-green-50 border-green-200 text-green-700' 
                     : 'bg-red-50 border-red-200 text-red-700'
                 ]">
              <svg v-if="messageType === 'success'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
              </svg>
              <p class="text-sm">{{ message }}</p>
            </div>
            <div class="space-y-6">
              <!-- 用户头像和基本信息 -->
              <div class="flex items-center gap-6 pb-6 border-b border-gray-200">
                <!-- 头像 - 可点击上传 -->
                <div class="relative group cursor-pointer" @click="handleAvatarClick">
                  <img 
                    :src="userAvatar" 
                    alt="头像" 
                    class="w-24 h-24 rounded-full object-cover ring-4 ring-red-100 shadow-lg transition-all duration-300 group-hover:ring-red-300" 
                  />
                  <div class="absolute inset-0 bg-black/50 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" />
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                  </div>
                  <div v-if="isInfluencer" class="absolute -bottom-2 -right-2 bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-full p-2 shadow-lg">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                    </svg>
                  </div>
                  <input 
                    ref="avatarInput" 
                    type="file" 
                    accept="image/*" 
                    class="hidden" 
                    @change="handleAvatarChange"
                  />
                </div>
                
                <div class="flex-1 space-y-3">
                  <!-- 昵称 - 可编辑 -->
                  <div class="group">
                    <div v-if="!editingField.nickname" 
                         class="flex items-center gap-2 cursor-pointer hover:bg-gray-50 rounded px-2 py-1 -mx-2 transition-colors"
                         @click="startEdit('nickname')">
                      <h3 class="text-xl font-semibold text-gray-900">{{ displayName }}</h3>
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-gray-400 opacity-0 group-hover:opacity-100 transition-opacity" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                      </svg>
                    </div>
                    <div v-else class="max-w-xs">
                      <Input 
                        v-model="editForm.nickname" 
                        class="text-xl font-semibold"
                        @blur="saveField('nickname')"
                        @keyup.enter="saveField('nickname')"
                        @keyup.esc="cancelEdit('nickname')"
                        ref="nicknameInput"
                      />
                    </div>
                  </div>
                  
                  <!-- 邮箱 - 可编辑 -->
                  <div class="group">
                    <div v-if="!editingField.email" 
                         class="flex items-center gap-2 cursor-pointer hover:bg-gray-50 rounded px-2 py-1 -mx-2 transition-colors"
                         @click="startEdit('email')">
                      <p class="text-gray-600">{{ userInfo?.email || '点击设置邮箱' }}</p>
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-gray-400 opacity-0 group-hover:opacity-100 transition-opacity" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                      </svg>
                    </div>
                    <div v-else class="max-w-sm">
                      <Input 
                        v-model="editForm.email" 
                        type="email"
                        placeholder="请输入邮箱"
                        @blur="saveField('email')"
                        @keyup.enter="saveField('email')"
                        @keyup.esc="cancelEdit('email')"
                        ref="emailInput"
                      />
                    </div>
                  </div>
                  
                  <div v-if="isInfluencer" class="pt-2">
                    <Badge class="bg-gradient-to-r from-red-500 to-pink-500 text-white px-3 py-1 text-sm font-medium shadow-md">
                      ⭐ 达人用户
                    </Badge>
                  </div>
                </div>
              </div>
              
              <!-- 账户统计信息 -->
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4 pt-4">
                <div class="bg-gradient-to-br from-red-50 to-pink-50 rounded-lg p-4 border border-red-100 hover:shadow-md transition-shadow duration-300">
                  <div class="flex items-center gap-3">
                    <div class="bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-full p-2 shadow-md">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path d="M9 6a3 3 0 11-6 0 3 3 0 016 0zM17 6a3 3 0 11-6 0 3 3 0 016 0zM12.93 17c.046-.327.07-.66.07-1a6.97 6.97 0 00-1.5-4.33A5 5 0 0119 16v1h-6.07zM6 11a5 5 0 015 5v1H1v-1a5 5 0 015-5z" />
                      </svg>
                    </div>
                    <div>
                      <p class="text-sm text-gray-600">账户类型</p>
                      <p class="text-lg font-semibold text-gray-900">
                        {{ isInfluencer ? '达人' : '普通用户' }}
                      </p>
                    </div>
                  </div>
                </div>
                
                <div class="bg-gradient-to-br from-green-50 to-emerald-50 rounded-lg p-4 border border-green-100 hover:shadow-md transition-shadow duration-300">
                  <div class="flex items-center gap-3">
                    <div class="bg-gradient-to-r from-green-500 to-emerald-500 text-white rounded-full p-2 shadow-md">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                      </svg>
                    </div>
                    <div>
                      <p class="text-sm text-gray-600">账户状态</p>
                      <p class="text-lg font-semibold text-gray-900">正常</p>
                    </div>
                  </div>
                </div>
                
                <div class="bg-gradient-to-br from-orange-50 to-amber-50 rounded-lg p-4 border border-orange-100 hover:shadow-md transition-shadow duration-300">
                  <div class="flex items-center gap-3">
                    <div class="bg-gradient-to-r from-orange-500 to-amber-500 text-white rounded-full p-2 shadow-md">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path d="M2 11a1 1 0 011-1h2a1 1 0 011 1v5a1 1 0 01-1 1H3a1 1 0 01-1-1v-5zM8 7a1 1 0 011-1h2a1 1 0 011 1v9a1 1 0 01-1 1H9a1 1 0 01-1-1V7zM14 4a1 1 0 011-1h2a1 1 0 011 1v12a1 1 0 01-1 1h-2a1 1 0 01-1-1V4z" />
                      </svg>
                    </div>
                    <div>
                      <p class="text-sm text-gray-600">注册时间</p>
                      <p class="text-lg font-semibold text-gray-900">
                        {{ userInfo?.createTime ? new Date(userInfo.createTime).toLocaleDateString('zh-CN') : '-' }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Card>
        </TabsContent>
        
        <TabsContent value="application" class="mt-6">
          <ApplicationStatusCard :application="application" />
        </TabsContent>
        
        <TabsContent value="likes" class="mt-6">
          <Card class="p-6 bg-white/80 backdrop-blur-sm shadow-lg rounded-lg border border-gray-200/50">
            <h2 class="text-xl font-semibold mb-4 text-gray-900">我的点赞</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              <div v-if="likedContent.length === 0" class="col-span-full text-center py-12 text-gray-500">
                暂无点赞内容
              </div>
              <GameCard v-for="item in likedContent" :key="item.id" :game="item" @click="handleContentClick" />
            </div>
          </Card>
        </TabsContent>

        <TabsContent value="favorites" class="mt-6">
          <Card class="p-6 bg-white/80 backdrop-blur-sm shadow-lg rounded-lg border border-gray-200/50">
            <h2 class="text-xl font-semibold mb-4 text-gray-900">我的收藏</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              <div v-if="favoriteContent.length === 0" class="col-span-full text-center py-12 text-gray-500">
                暂无收藏内容
              </div>
              <GameCard v-for="item in favoriteContent" :key="item.id" :game="item" @click="handleContentClick" />
            </div>
          </Card>
        </TabsContent>
        
        <TabsContent value="application" class="mt-6">
          <ApplicationStatusCard :application="application" />
        </TabsContent>
        
        <TabsContent value="content" class="mt-6" v-if="isInfluencer">
          <ContentListTable />
        </TabsContent>
      </Tabs>
    </div>

  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, reactive, nextTick } from 'vue'
import { useUserStore } from '@/stores/user'
import { useInfluencerStore } from '@/stores/influencer'
import { updateUserProfile, uploadAvatar } from '@/api/user'
import Card from '@/components/ui/Card.vue'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import Input from '@/components/ui/Input.vue'
import Tabs from '@/components/ui/Tabs.vue'
import TabsList from '@/components/ui/TabsList.vue'
import TabsTrigger from '@/components/ui/TabsTrigger.vue'
import TabsContent from '@/components/ui/TabsContent.vue'
import ApplicationStatusCard from '@/components/ApplicationStatusCard.vue'
import ContentListTable from '@/components/ContentListTable.vue'
import GameCard from '@/components/GameCard.vue'

const userStore = useUserStore()
const influencerStore = useInfluencerStore()

const userInfo = computed(() => userStore.userInfo)
const userAvatar = computed(() => userStore.userAvatar)
const displayName = computed(() => userStore.displayName)
const isInfluencer = computed(() => influencerStore.isInfluencer)
const application = computed(() => influencerStore.application)

// 内联编辑状态
const editingField = reactive({
  nickname: false,
  email: false
})

const editForm = reactive({
  nickname: '',
  email: ''
})

// 防止刚进入编辑模式时立即触发失焦保存
const isJustStartedEditing = ref(false)

// 输入框引用
const avatarInput = ref<HTMLInputElement>()
const nicknameInput = ref<HTMLInputElement>()
const emailInput = ref<HTMLInputElement>()

// 消息提示
const message = ref('')
const messageType = ref<'success' | 'error'>('success')
const showMessage = (text: string, type: 'success' | 'error' = 'success') => {
  message.value = text
  messageType.value = type
  setTimeout(() => {
    message.value = ''
  }, 3000)
}

// 点赞和收藏的内容（示例数据，实际应该从API获取）
const likedContent = ref<any[]>([])
const favoriteContent = ref<any[]>([])

const handleContentClick = (content: any) => {
  // 处理内容点击事件
  console.log('Content clicked:', content)
}

// 开始编辑字段
const startEdit = async (field: 'nickname' | 'email') => {
  // 初始化编辑表单的值
  if (field === 'nickname') {
    editForm.nickname = userInfo.value?.nickName || userInfo.value?.nickname || ''
  } else if (field === 'email') {
    editForm.email = userInfo.value?.email || ''
  }
  
  editingField[field] = true
  isJustStartedEditing.value = true
  
  // 使用nextTick确保输入框已渲染
  await nextTick()
  
  // 获取输入框元素并聚焦
  if (field === 'nickname' && nicknameInput.value) {
    nicknameInput.value.focus()
    nicknameInput.value.select()
  } else if (field === 'email' && emailInput.value) {
    emailInput.value.focus()
    emailInput.value.select()
  }
  
  // 延迟重置标志，防止立即触发blur（增加延迟时间）
  setTimeout(() => {
    isJustStartedEditing.value = false
  }, 200)
}

// 取消编辑
const cancelEdit = (field: 'nickname' | 'email') => {
  editingField[field] = false
  // 恢复原值
  if (field === 'nickname') {
    editForm.nickname = userInfo.value?.nickName || userInfo.value?.nickname || ''
  } else if (field === 'email') {
    editForm.email = userInfo.value?.email || ''
  }
}

// 保存字段（失焦时自动保存）
const saveField = async (field: 'nickname' | 'email') => {
  // 如果刚开始编辑，忽略这次blur事件
  if (isJustStartedEditing.value) {
    return
  }
  
  // 如果值没有变化，直接退出编辑模式
  const originalValue = field === 'nickname' 
    ? userInfo.value?.nickName || userInfo.value?.nickname || '' 
    : userInfo.value?.email || ''
  
  const newValue = editForm[field]?.trim() || ''
  
  if (newValue === originalValue) {
    editingField[field] = false
    return
  }
  
  // 验证邮箱格式
  if (field === 'email' && newValue) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(newValue)) {
      showMessage('请输入正确的邮箱格式', 'error')
      return
    }
  }
  
  // 验证昵称不能为空
  if (field === 'nickname' && !newValue) {
    showMessage('昵称不能为空', 'error')
    return
  }
  
  try {
    // 调用API保存
    const updateData: { nickname: string; email?: string } = {
      nickname: field === 'nickname' ? newValue : (userInfo.value?.nickname || userInfo.value?.nickName || ''),
    }
    
    if (field === 'email') {
      updateData.email = newValue
    }
    
    const response = await updateUserProfile(updateData)
    
    if (response.code === 0) {
      // 更新本地用户信息
      if (field === 'nickname') {
        userStore.updateUserInfo({ nickname: newValue })
      } else if (field === 'email') {
        userStore.updateUserInfo({ email: newValue })
      }
      
      showMessage(`${field === 'nickname' ? '昵称' : '邮箱'}更新成功`, 'success')
      editingField[field] = false
    } else {
      showMessage(response.msg || '更新失败，请重试', 'error')
    }
  } catch (error: any) {
    console.error('更新失败:', error)
    showMessage(error.response?.data?.msg || error.message || '更新失败，请重试', 'error')
  }
}

// 处理头像点击
const handleAvatarClick = () => {
  avatarInput.value?.click()
}

// 处理头像上传
const handleAvatarChange = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (!file) return
  
  // 验证文件类型
  if (!file.type.startsWith('image/')) {
    showMessage('请选择图片文件', 'error')
    return
  }
  
  // 验证文件大小（限制为2MB）
  if (file.size > 2 * 1024 * 1024) {
    showMessage('图片大小不能超过2MB', 'error')
    return
  }
  
  try {
    const formData = new FormData()
    formData.append('file', file)
    
    const response = await uploadAvatar(formData)
    
    if (response.code === 0 && response.data) {
      // 更新本地用户头像
      const avatarUrl = response.data.url
      userStore.updateUserInfo({ avatar: avatarUrl })
      
      showMessage('头像上传成功', 'success')
    } else {
      showMessage(response.msg || '头像上传失败', 'error')
    }
  } catch (error: any) {
    console.error('头像上传失败:', error)
    showMessage(error.response?.data?.msg || error.message || '头像上传失败，请重试', 'error')
  } finally {
    // 清空文件输入，允许重复选择同一文件
    if (target) {
      target.value = ''
    }
  }
}

onMounted(async () => {
  await influencerStore.checkInfluencerRole()
  await influencerStore.fetchMyApplication()
  
  // 初始化编辑表单
  editForm.nickname = userInfo.value?.nickName || userInfo.value?.nickname || ''
  editForm.email = userInfo.value?.email || ''
  
  // TODO: 加载点赞和收藏的内容
  // likedContent.value = await fetchLikedContent()
  // favoriteContent.value = await fetchFavoriteContent()
})
</script>
