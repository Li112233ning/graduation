<template>
  <div class="min-h-screen bg-background py-12 px-4">
    <div class="max-w-2xl mx-auto">
      <Card class="p-6">
        <h1 class="text-2xl font-bold mb-2">达人入驻申请</h1>
        <p class="text-muted-foreground mb-6">填写以下信息，成为平台达人，获得内容投稿权限</p>
        
        <InfluencerApplicationForm @success="handleSuccess" @cancel="handleCancel" />
      </Card>
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
