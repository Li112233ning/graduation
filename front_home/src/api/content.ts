import request, { type ApiResponse } from '@/lib/request'
import type { ContentSubmission, ContentListParams, ContentListResponse } from '@/types/content'

/**
 * 创建/更新稿件
 */
export function saveContentSubmission(data: Partial<ContentSubmission>): Promise<ApiResponse<ContentSubmission>> {
  return request({
    url: '/api/content/submission',
    method: 'post',
    data
  })
}

/**
 * 提交稿件审核
 */
export function submitContentForReview(id: number): Promise<ApiResponse<ContentSubmission>> {
  return request({
    url: `/api/content/submission/${id}/submit`,
    method: 'put'
  })
}

/**
 * 查询我的稿件列表
 */
export function getMySubmissions(params?: ContentListParams): Promise<ApiResponse<ContentListResponse>> {
  return request({
    url: '/api/content/submission/my',
    method: 'get',
    params
  })
}

/**
 * 查询稿件详情
 */
export function getSubmissionDetail(id: number): Promise<ApiResponse<ContentSubmission>> {
  return request({
    url: `/api/content/submission/${id}`,
    method: 'get'
  })
}

/**
 * 删除稿件
 */
export function deleteSubmission(id: number): Promise<ApiResponse<void>> {
  return request({
    url: `/api/content/submission/${id}`,
    method: 'delete'
  })
}

/**
 * 上传封面图
 */
export function uploadCoverImage(formData: FormData): Promise<ApiResponse<{ url: string }>> {
  return request({
    url: '/api/content/upload/cover',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
