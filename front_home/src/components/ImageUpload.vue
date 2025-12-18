<template>
  <div class="space-y-2">
    <div v-if="previewUrl" class="relative w-full h-48 border rounded-md overflow-hidden">
      <img :src="previewUrl" alt="预览" class="w-full h-full object-cover" />
      <button
        type="button"
        @click="removeImage"
        class="absolute top-2 right-2 bg-red-500 text-white rounded-full p-1 hover:bg-red-600"
      >
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd" />
        </svg>
      </button>
    </div>
    
    <div v-else class="border-2 border-dashed rounded-md p-6 text-center">
      <input
        ref="fileInput"
        type="file"
        accept="image/*"
        class="hidden"
        @change="handleFileChange"
      />
      <button
        type="button"
        @click="fileInput?.click()"
        class="inline-flex items-center justify-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-primary hover:bg-primary/90"
      >
        选择图片
      </button>
      <p class="mt-2 text-sm text-muted-foreground">支持 JPG、PNG 格式，最大 5MB</p>
    </div>
    
    <div v-if="uploading" class="text-sm text-muted-foreground">上传中...</div>
    <div v-if="error" class="text-sm text-red-500">{{ error }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { uploadCoverImage } from '@/api/content'

interface Props {
  modelValue?: string
}

const props = defineProps<Props>()

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const fileInput = ref<HTMLInputElement>()
const previewUrl = ref(props.modelValue || '')
const uploading = ref(false)
const error = ref('')

watch(() => props.modelValue, (newValue) => {
  previewUrl.value = newValue || ''
})

const handleFileChange = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  
  if (!file) return
  
  // 验证文件类型
  if (!file.type.startsWith('image/')) {
    error.value = '请选择图片文件'
    return
  }
  
  // 验证文件大小 (5MB)
  if (file.size > 5 * 1024 * 1024) {
    error.value = '图片大小不能超过 5MB'
    return
  }
  
  error.value = ''
  uploading.value = true
  
  try {
    const formData = new FormData()
    formData.append('file', file)
    
    const response = await uploadCoverImage(formData)
    if (response.code === 0 && response.data?.url) {
      previewUrl.value = response.data.url
      emit('update:modelValue', response.data.url)
    } else {
      error.value = '上传失败'
    }
  } catch (err: any) {
    error.value = err.response?.data?.msg || '上传失败'
  } finally {
    uploading.value = false
  }
}

const removeImage = () => {
  previewUrl.value = ''
  emit('update:modelValue', '')
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}
</script>
