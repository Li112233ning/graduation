<template>
  <div class="min-h-screen bg-gradient-to-b from-red-50 via-pink-50 to-orange-50 py-12 px-4">
    <div class="max-w-3xl mx-auto">
      <!-- 页面头部 -->
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center w-20 h-20 rounded-full bg-gradient-to-r from-red-500 to-pink-500 mb-4 shadow-lg">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
          </svg>
        </div>
        <h1 class="text-4xl font-bold text-gray-900 mb-3">达人入驻申请</h1>
        <p class="text-lg text-gray-600 max-w-2xl mx-auto">
          成为平台认证达人，获得内容投稿权限，展示您的专业才华
        </p>
      </div>

      <!-- 申请表单卡片 -->
      <Card class="p-8 bg-white/80 backdrop-blur-sm shadow-xl rounded-lg border border-gray-200/50">
        <InfluencerApplicationForm @success="handleSuccess" @cancel="handleCancel" />
      </Card>

      <!-- 优势说明 -->
      <div class="mt-12 grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="bg-white/60 backdrop-blur-sm rounded-lg p-6 border border-gray-200/50 shadow-md hover:shadow-lg transition-shadow">
          <div class="w-12 h-12 rounded-full bg-gradient-to-r from-red-500 to-pink-500 flex items-center justify-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">内容投稿</h3>
          <p class="text-gray-600 text-sm">发布您的优质内容，展示专业能力</p>
        </div>

        <div class="bg-white/60 backdrop-blur-sm rounded-lg p-6 border border-gray-200/50 shadow-md hover:shadow-lg transition-shadow">
          <div class="w-12 h-12 rounded-full bg-gradient-to-r from-pink-500 to-orange-500 flex items-center justify-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">粉丝增长</h3>
          <p class="text-gray-600 text-sm">获得更多曝光，吸引精准粉丝关注</p>
        </div>

        <div class="bg-white/60 backdrop-blur-sm rounded-lg p-6 border border-gray-200/50 shadow-md hover:shadow-lg transition-shadow">
          <div class="w-12 h-12 rounded-full bg-gradient-to-r from-orange-500 to-red-500 flex items-center justify-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">官方认证</h3>
          <p class="text-gray-600 text-sm">获得平台认证标识，提升专业形象</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import Card from '@/components/ui/Card.vue'
import InfluencerApplicationForm from '@/components/InfluencerApplicationForm.vue'

const router = useRouter()

const handleSuccess = () => {
  // 表单组件已经处理了跳转
}

const handleCancel = () => {
  router.push('/')
}
</script>
