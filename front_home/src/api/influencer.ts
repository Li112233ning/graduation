import request, { type ApiResponse } from '@/lib/request'
import type { InfluencerApplication, InfluencerRoleInfo } from '@/types/influencer'

/**
 * 提交达人申请
 */
export function submitInfluencerApplication(data: Omit<InfluencerApplication, 'id' | 'userId' | 'status' | 'createdAt' | 'updatedAt' | 'approvedAt'>): Promise<ApiResponse<InfluencerApplication>> {
  return request({
    url: '/api/influencer/application',
    method: 'post',
    data
  })
}

/**
 * 查询当前用户的申请状态
 */
export function getMyApplication(): Promise<ApiResponse<InfluencerApplication | null>> {
  return request({
    url: '/api/influencer/application/my',
    method: 'get'
  })
}

/**
 * 查询用户角色信息
 */
export function getInfluencerRole(): Promise<ApiResponse<InfluencerRoleInfo>> {
  return request({
    url: '/api/influencer/role',
    method: 'get'
  })
}
