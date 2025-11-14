// 内容投稿相关类型定义

export type PlatformType = 'douyin' | 'xiaohongshu' | 'weibo' | 'other'
export type ContentStatus = 'draft' | 'pending' | 'approved' | 'rejected' | 'published'

export interface ContentSubmission {
  id?: number
  userId: number
  title: string               // 标题
  content: string            // 正文
  coverImage: string         // 封面图URL
  platformType: PlatformType  // 平台类型
  scheduledPublishTime: string  // 预计发布时间
  status: ContentStatus       // 状态
  rejectReason?: string      // 驳回原因
  createdAt?: string
  updatedAt?: string
  publishedAt?: string
}

export interface ContentListParams {
  status?: ContentStatus
  page?: number
  size?: number
}

export interface ContentListResponse {
  list: ContentSubmission[]
  total: number
}
