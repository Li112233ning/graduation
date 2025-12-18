import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { InfluencerApplication } from '@/types/influencer'
import { submitInfluencerApplication, getMyApplication, getInfluencerRole } from '@/api/influencer'

export const useInfluencerStore = defineStore('influencer', () => {
  // 状态
  const application = ref<InfluencerApplication | null>(null)
  const isInfluencer = ref(false)
  const loading = ref(false)

  // 提交申请
  const submitApplication = async (data: Omit<InfluencerApplication, 'id' | 'userId' | 'status' | 'createdAt' | 'updatedAt' | 'approvedAt'>) => {
    loading.value = true
    try {
      const response = await submitInfluencerApplication(data)
      if (response.code === 0 && response.data) {
        application.value = response.data
        return true
      }
      return false
    } catch (error) {
      console.error('提交申请失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 获取我的申请
  const fetchMyApplication = async () => {
    loading.value = true
    try {
      const response = await getMyApplication()
      if (response.code === 0) {
        application.value = response.data || null
        return true
      }
      return false
    } catch (error) {
      console.error('获取申请失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  // 检查达人角色
  const checkInfluencerRole = async () => {
    try {
      const response = await getInfluencerRole()
      if (response.code === 0 && response.data) {
        isInfluencer.value = response.data.isInfluencer
        return response.data.isInfluencer
      }
      return false
    } catch (error) {
      console.error('检查达人角色失败:', error)
      return false
    }
  }

  return {
    application,
    isInfluencer,
    loading,
    submitApplication,
    fetchMyApplication,
    checkInfluencerRole
  }
})
