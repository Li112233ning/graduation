<template>
  <form @submit.prevent="handleSubmit" class="space-y-6">
    <div class="space-y-2">
      <Label for="title">标题 *</Label>
      <Input
        id="title"
        v-model="formData.title"
        placeholder="请输入稿件标题"
        required
      />
    </div>

    <div class="space-y-2">
      <Label for="content">正文 *</Label>
      <Textarea
        id="content"
        v-model="formData.content"
        placeholder="请输入稿件内容"
        rows="10"
        required
      />
    </div>

    <div class="space-y-2">
      <Label>封面图 *</Label>
      <ImageUpload v-model="formData.coverImage" />
    </div>

    <div class="space-y-2">
      <Label for="platformType">平台类型 *</Label>
      <Select id="platformType" v-model="formData.platformType" required>
        <option value="">请选择平台</option>
        <option value="douyin">抖音</option>
        <option value="xiaohongshu">小红书</option>
        <option value="weibo">微博</option>
        <option value="other">其他</option>
      </Select>
    </div>

    <div class="space-y-2">
      <Label for="scheduledPublishTime">预计发布时间 *</Label>
      <Input
        id="scheduledPublishTime"
        v-model="formData.scheduledPublishTime"
        type="datetime-local"
        required
      />
    </div>

    <div v-if="rejectReason" class="p-4 bg-red-50 dark:bg-red-900/20 rounded-md">
      <p class="text-sm font-medium text-red-600 dark:text-red-400 mb-1">驳回原因：</p>
      <p class="text-sm text-red-600 dark:text-red-400">{{ rejectReason }}</p>
    </div>

    <div class="flex gap-4">
      <Button type="button" variant="outline" @click="handleSaveDraft" :disabled="saving">
        {{ saving ? '保存中...' : '保存草稿' }}
      </Button>
      <Button type="submit" :disabled="submitting">
        {{ submitting ? '提交中...' : '提交审核' }}
      </Button>
      <Button type="button" variant="outline" @click="$emit('cancel')">
        取消
      </Button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, reactive, watch, onMounted } from 'vue'
import { useContentStore } from '@/stores/content'
import type { ContentSubmission } from '@/types/content'
import Button from '@/components/ui/Button.vue'
import Input from '@/components/ui/Input.vue'
import Label from '@/components/ui/Label.vue'
import Textarea from '@/components/ui/Textarea.vue'
import Select from '@/components/ui/Select.vue'
import ImageUpload from '@/components/ImageUpload.vue'

interface Props {
  submission?: ContentSubmission | null
}

const props = defineProps<Props>()

const emit = defineEmits<{
  cancel: []
  success: []
}>()

const contentStore = useContentStore()

const formData = reactive({
  title: '',
  content: '',
  coverImage: '',
  platformType: '',
  scheduledPublishTime: ''
})

const rejectReason = ref('')
const saving = ref(false)
const submitting = ref(false)

// 如果是编辑模式，加载现有数据
watch(() => props.submission, (newSubmission) => {
  if (newSubmission) {
    formData.title = newSubmission.title
    formData.content = newSubmission.content
    formData.coverImage = newSubmission.coverImage
    formData.platformType = newSubmission.platformType
    formData.scheduledPublishTime = newSubmission.scheduledPublishTime
    rejectReason.value = newSubmission.rejectReason || ''
  }
}, { immediate: true })

const handleSaveDraft = async () => {
  saving.value = true
  
  try {
    const data: Partial<ContentSubmission> = {
      ...formData,
      status: 'draft'
    }
    
    if (props.submission?.id) {
      await contentStore.updateSubmission(props.submission.id, data)
    } else {
      await contentStore.createSubmission(data)
    }
    
    alert('草稿保存成功')
    emit('success')
  } catch (error: any) {
    alert(error.response?.data?.msg || '保存失败，请重试')
  } finally {
    saving.value = false
  }
}

const handleSubmit = async () => {
  if (!formData.title || !formData.content || !formData.coverImage || !formData.platformType || !formData.scheduledPublishTime) {
    alert('请填写所有必填字段')
    return
  }
  
  submitting.value = true
  
  try {
    let submissionId = props.submission?.id
    
    // 如果是新建或草稿，先保存
    if (!submissionId || props.submission?.status === 'draft') {
      const data: Partial<ContentSubmission> = {
        ...formData,
        status: 'draft'
      }
      
      const result = submissionId 
        ? await contentStore.updateSubmission(submissionId, data)
        : await contentStore.createSubmission(data)
      
      submissionId = result?.id
    }
    
    // 提交审核
    if (submissionId) {
      await contentStore.submitForReview(submissionId)
      alert('提交成功')
      emit('success')
    }
  } catch (error: any) {
    alert(error.response?.data?.msg || '提交失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>
