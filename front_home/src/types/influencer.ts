// 达人申请相关类型定义

export interface InfluencerApplication {
  id?: number
  userId: number
  platformAccount: string      // 平台账号
  fansCount: number           // 粉丝量
  contentField: string        // 内容领域
  portfolioLinks: string[]    // 代表作品链接数组
  status: 'pending' | 'approved' | 'rejected'  // 审核状态
  rejectReason?: string       // 驳回原因
  createdAt?: string
  updatedAt?: string
  approvedAt?: string
}

export interface InfluencerRoleInfo {
  isInfluencer: boolean
}
