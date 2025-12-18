<template>
  <form @submit.prevent="handleSubmit" class="space-y-6">
    <!-- 平台账号 -->
    <div class="space-y-2">
      <Label for="platformAccount" class="flex items-center gap-2 text-gray-700 font-medium">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
        </svg>
        平台账号 <span class="text-red-500">*</span>
      </Label>
      <Input
        id="platformAccount"
        v-model="formData.platformAccount"
        placeholder="请输入您的平台账号（如：抖音、小红书、B站等）"
        class="w-full"
        required
      />
      <p v-if="errors.platformAccount" class="text-sm text-red-500 flex items-center gap-1">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        {{ errors.platformAccount }}
      </p>
    </div>

    <!-- 粉丝量 -->
    <div class="space-y-2">
      <Label for="fansCount" class="flex items-center gap-2 text-gray-700 font-medium">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
        </svg>
        粉丝量 <span class="text-red-500">*</span>
      </Label>
      <Input
        id="fansCount"
        v-model.number="formData.fansCount"
        type="number"
        placeholder="请输入粉丝数量（单位：人）"
        class="w-full"
        required
      />
      <p v-if="errors.fansCount" class="text-sm text-red-500 flex items-center gap-1">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        {{ errors.fansCount }}
      </p>
    </div>

    <!-- 内容领域 -->
    <div class="space-y-2">
      <Label for="contentField" class="flex items-center gap-2 text-gray-700 font-medium">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z" />
        </svg>
        内容领域 <span class="text-red-500">*</span>
      </Label>
      <Input
        id="contentField"
        v-model="formData.contentField"
        placeholder="例如：美食、旅游、科技、时尚、美妆等"
        class="w-full"
        required
      />
      <p v-if="errors.contentField" class="text-sm text-red-500 flex items-center gap-1">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        {{ errors.contentField }}
      </p>
    </div>

    <!-- 提示信息 -->
    <div class="bg-blue-50 border border-blue-200 rounded-lg p-4">
      <div class="flex gap-3">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-500 flex-shrink-0 mt-0.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <div class="text-sm text-blue-700">
          <p class="font-medium mb-1">温馨提示</p>
          <p class="text-blue-600">请确保填写的信息真实有效，我们会在1-3个工作日内完成审核，审核结果将通过站内消息通知您。</p>
        </div>
      </div>
    </div>

    <!-- 按钮组 -->
    <div class="flex gap-4 pt-4">
      <Button 
        type="submit" 
        :disabled="submitting"
        class="flex-1 bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white shadow-lg hover:shadow-xl transition-all duration-300"
      >
        <span v-if="!submitting" class="flex items-center justify-center gap-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          提交申请
        </span>
        <span v-else class="flex items-center justify-center gap-2">
          <svg class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
          </svg>
          提交中...
        </span>
      </Button>
      <Button 
        type="button" 
        variant="outline" 
        @click="$emit('cancel')"
        class="px-6 border-gray-300 hover:bg-gray-50"
      >
        取消
      </Button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useInfluencerStore } from '@/stores/influencer'
import Button from '@/components/ui/Button.vue'
import Input from '@/components/ui/Input.vue'
import Label from '@/components/ui/Label.vue'

const emit = defineEmits<{
  cancel: []
  success: []
}>()

const router = useRouter()
const influencerStore = useInfluencerStore()

const formData = reactive({
  platformAccount: '',
  fansCount: 0,
  contentField: ''
})

const errors = reactive({
  platformAccount: '',
  fansCount: '',
  contentField: ''
})

const submitting = ref(false)

const validateForm = () => {
  let isValid = true
  
  // 重置错误
  errors.platformAccount = ''
  errors.fansCount = ''
  errors.contentField = ''
  
  if (!formData.platformAccount.trim()) {
    errors.platformAccount = '请输入平台账号'
    isValid = false
  }
  
  if (!formData.fansCount || formData.fansCount < 0) {
    errors.fansCount = '请输入有效的粉丝数量'
    isValid = false
  }
  
  if (!formData.contentField.trim()) {
    errors.contentField = '请输入内容领域'
    isValid = false
  }
  
  return isValid
}

const handleSubmit = async () => {
  if (!validateForm()) {
    return
  }
  
  submitting.value = true
  
  try {
    await influencerStore.submitApplication({
      platformAccount: formData.platformAccount,
      fansCount: formData.fansCount,
      contentField: formData.contentField,
      portfolioLinks: []
    })
    
    emit('success')
    router.push('/personal-center')
  } catch (error: any) {
    alert(error.response?.data?.msg || '提交失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>
