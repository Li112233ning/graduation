<template>
  <div class="space-y-4">
    <div class="flex justify-between items-center">
      <div class="flex gap-2">
        <Select v-model="filterStatus" class="w-40">
          <option value="">全部状态</option>
          <option value="draft">草稿</option>
          <option value="pending">待审核</option>
          <option value="approved">已通过</option>
          <option value="rejected">已驳回</option>
          <option value="published">已发布</option>
        </Select>
      </div>
      <Button @click="$emit('create')">
        + 创建稿件
      </Button>
    </div>

    <Card>
      <div v-if="loading" class="p-8 text-center text-muted-foreground">
        加载中...
      </div>
      
      <div v-else-if="submissions.length === 0" class="p-8 text-center text-muted-foreground">
        暂无稿件
      </div>
      
      <div v-else class="overflow-x-auto">
        <table class="w-full">
          <thead class="border-b">
            <tr class="text-left">
              <th class="p-4 font-medium">标题</th>
              <th class="p-4 font-medium">平台类型</th>
              <th class="p-4 font-medium">状态</th>
              <th class="p-4 font-medium">提交时间</th>
              <th class="p-4 font-medium">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="submission in submissions" :key="submission.id" class="border-b hover:bg-muted/50">
              <td class="p-4">{{ submission.title }}</td>
              <td class="p-4">{{ getPlatformText(submission.platformType) }}</td>
              <td class="p-4">
                <Badge :class="getStatusClass(submission.status)">
                  {{ getStatusText(submission.status) }}
                </Badge>
              </td>
              <td class="p-4 text-sm text-muted-foreground">
                {{ formatDate(submission.createdAt) }}
              </td>
              <td class="p-4">
                <div class="flex gap-2">
                  <Button
                    size="sm"
                    variant="outline"
                    @click="$emit('edit', submission)"
                    v-if="submission.status === 'draft' || submission.status === 'rejected'"
                  >
                    编辑
                  </Button>
                  <Button
                    size="sm"
                    variant="outline"
                    @click="handleDelete(submission.id!)"
                    v-if="submission.status === 'draft'"
                  >
                    删除
                  </Button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'
import { useContentStore } from '@/stores/content'
import type { ContentSubmission, ContentStatus } from '@/types/content'
import Card from '@/components/ui/Card.vue'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import Select from '@/components/ui/Select.vue'

const emit = defineEmits<{
  create: []
  edit: [submission: ContentSubmission]
}>()

const contentStore = useContentStore()

const filterStatus = ref<ContentStatus | ''>('')
const loading = computed(() => contentStore.loading)
const submissions = computed(() => contentStore.submissions)

const fetchSubmissions = async () => {
  await contentStore.fetchMySubmissions({
    status: filterStatus.value || undefined
  })
}

watch(filterStatus, () => {
  fetchSubmissions()
})

onMounted(() => {
  fetchSubmissions()
})

const getPlatformText = (platform: string) => {
  const platformMap: Record<string, string> = {
    douyin: '抖音',
    xiaohongshu: '小红书',
    weibo: '微博',
    other: '其他'
  }
  return platformMap[platform] || platform
}

const getStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    draft: '草稿',
    pending: '待审核',
    approved: '已通过',
    rejected: '已驳回',
    published: '已发布'
  }
  return statusMap[status] || status
}

const getStatusClass = (status: string) => {
  const classMap: Record<string, string> = {
    draft: 'bg-gray-100 text-gray-800 dark:bg-gray-900/20 dark:text-gray-400',
    pending: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900/20 dark:text-yellow-400',
    approved: 'bg-green-100 text-green-800 dark:bg-green-900/20 dark:text-green-400',
    rejected: 'bg-red-100 text-red-800 dark:bg-red-900/20 dark:text-red-400',
    published: 'bg-blue-100 text-blue-800 dark:bg-blue-900/20 dark:text-blue-400'
  }
  return classMap[status] || ''
}

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleString('zh-CN')
}

const handleDelete = async (id: number) => {
  if (!confirm('确定要删除这个稿件吗？')) {
    return
  }
  
  try {
    await contentStore.removeSubmission(id)
    alert('删除成功')
  } catch (error: any) {
    alert(error.response?.data?.msg || '删除失败，请重试')
  }
}
</script>
