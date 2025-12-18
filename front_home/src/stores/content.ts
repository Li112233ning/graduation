import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { ContentSubmission, ContentListParams } from '@/types/content'
import {
  saveContentSubmission,
  submitContentForReview,
  getMySubmissions,
  getSubmissionDetail,
  deleteSubmission
} from '@/api/content'

export const useContentStore = defineStore('content', () => {
  // 状态
  const submissions = ref<ContentSubmission[]>([])
  const currentSubmission = ref<ContentSubmission | null>(null)
  const total = ref(0)
  const loading = ref(false)

  // 创建稿件
  const createSubmission = async (data: Partial<ContentSubmission>) => {
    loading.value = true
    try {
      const response = await saveContentSubmission(data)
      if (response.code === 0 && response.data) {
        currentSubmission.value = response.data
        return response.data
      }
      return null
    } catch (error) {
      console.error('创建稿件失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 更新稿件
  const updateSubmission = async (id: number, data: Partial<ContentSubmission>) => {
    loading.value = true
    try {
      const response = await saveContentSubmission({ ...data, id })
      if (response.code === 0 && response.data) {
        currentSubmission.value = response.data
        return response.data
      }
      return null
    } catch (error) {
      console.error('更新稿件失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 提交审核
  const submitForReview = async (id: number) => {
    loading.value = true
    try {
      const response = await submitContentForReview(id)
      if (response.code === 0 && response.data) {
        currentSubmission.value = response.data
        return true
      }
      return false
    } catch (error) {
      console.error('提交审核失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 获取我的稿件列表
  const fetchMySubmissions = async (params?: ContentListParams) => {
    loading.value = true
    try {
      const response = await getMySubmissions(params)
      if (response.code === 0 && response.data) {
        submissions.value = response.data.list || []
        total.value = response.data.total || 0
        return true
      }
      return false
    } catch (error) {
      console.error('获取稿件列表失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  // 获取稿件详情
  const fetchSubmissionDetail = async (id: number) => {
    loading.value = true
    try {
      const response = await getSubmissionDetail(id)
      if (response.code === 0 && response.data) {
        currentSubmission.value = response.data
        return response.data
      }
      return null
    } catch (error) {
      console.error('获取稿件详情失败:', error)
      return null
    } finally {
      loading.value = false
    }
  }

  // 删除稿件
  const removeSubmission = async (id: number) => {
    loading.value = true
    try {
      const response = await deleteSubmission(id)
      if (response.code === 0) {
        // 从列表中移除
        submissions.value = submissions.value.filter(s => s.id !== id)
        return true
      }
      return false
    } catch (error) {
      console.error('删除稿件失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  return {
    submissions,
    currentSubmission,
    total,
    loading,
    createSubmission,
    updateSubmission,
    submitForReview,
    fetchMySubmissions,
    fetchSubmissionDetail,
    removeSubmission
  }
})
