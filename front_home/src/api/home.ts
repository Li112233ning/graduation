import request from '@/lib/request'

export interface BannerItem {
  id: number
  title: string
  description: string
  image: string
  linkUrl?: string
  tags: string[]
  sortOrder: number
  status: number
}

export interface CategoryItem {
  id: number
  name: string
  icon: string
  color: string
  sortOrder: number
  status: number
}

export interface ContentItem {
  id: number
  title: string
  categoryId: number
  image: string
  images?: string[] // 多图片列表
  tags: string[]
  likes: string
  comments: string
  players: string
  platform: string
  status: number
  update?: string
  description?: string
  publishTime?: string
  shares?: string
  isLiked?: boolean // 当前用户是否已点赞
  isCollected?: boolean // 当前用户是否已收藏
  author?: {
    name: string
    avatar?: string
    followers?: string
  }
}

export interface CommentItem {
  id: number
  contentId: number
  userId: number
  parentId?: number
  content: string
  likesCount: number
  isLiked?: boolean
  createTime: string
  userName: string
  userAvatar?: string
  replies?: CommentItem[]
}

export interface InfluencerItem {
  id: number
  name: string
  platform: string
  followers: string
  field: string
  image: string
  sortOrder: number
  status: number
}

export interface PlatformItem {
  id: number
  name: string
  sortOrder: number
  status: number
}

/**
 * 获取轮播图列表
 */
export function getBannerList() {
  return request({
    url: '/home/banners',
    method: 'get'
  })
}

/**
 * 获取分类列表
 */
export function getCategoryList() {
  return request({
    url: '/home/categories',
    method: 'get'
  })
}

/**
 * 获取精选内容列表
 */
export function getFeaturedContentList() {
  return request({
    url: '/home/featured-contents',
    method: 'get'
  })
}

/**
 * 获取最新内容列表
 */
export function getLatestContentList() {
  return request({
    url: '/home/latest-contents',
    method: 'get'
  })
}

/**
 * 获取优质达人列表
 */
export function getInfluencerList() {
  return request({
    url: '/home/influencers',
    method: 'get'
  })
}

/**
 * 获取平台列表
 */
export function getPlatformList() {
  return request({
    url: '/home/platforms',
    method: 'get'
  })
}

/**
 * 获取内容列表（支持分页和筛选）
 */
export function getHomeContentList(
  type?: string,
  category?: string,
  platform?: string,
  tag?: string
) {
  const params: Record<string, string> = {}
  if (type) {
    params.type = type
  }
  if (category) {
    params.category = category
  }
  if (platform) {
    params.platform = platform
  }
  if (tag) {
    params.tag = tag
  }
  return request({
    url: '/home/contents',
    method: 'get',
    params
  })
}

/**
 * 获取内容详情
 */
export function getContentDetail(id: number) {
  return request({
    url: `/home/content/${id}`,
    method: 'get'
  })
}

/**
 * 获取评论列表
 */
export function getCommentList(contentId: number) {
  return request({
    url: `/home/content/${contentId}/comments`,
    method: 'get'
  })
}

/**
 * 添加评论
 */
export function addComment(contentId: number, content: string, parentId?: number) {
  return request({
    url: `/home/content/${contentId}/comments`,
    method: 'post',
    data: {
      content,
      parentId
    }
  })
}

/**
 * 点赞/取消点赞内容
 */
export function toggleLike(contentId: number) {
  return request({
    url: `/home/content/${contentId}/like`,
    method: 'post'
  })
}

/**
 * 收藏/取消收藏内容
 */
export function toggleCollect(contentId: number) {
  return request({
    url: `/home/content/${contentId}/collect`,
    method: 'post'
  })
}

/**
 * 点赞/取消点赞评论
 */
export function toggleCommentLike(commentId: number) {
  return request({
    url: `/home/comment/${commentId}/like`,
    method: 'post'
  })
}

/**
 * 关注/取消关注用户
 */
export function toggleFollow(userId: number) {
  return request({
    url: `/home/user/${userId}/follow`,
    method: 'post'
  })
}

/**
 * 检查是否已关注某用户
 */
export function checkFollowStatus(userId: number) {
  return request({
    url: `/home/user/${userId}/follow/status`,
    method: 'get'
  })
}

/**
 * 根据达人ID获取稿件列表
 */
export function getContentListByInfluencerId(influencerId: number) {
  return request({
    url: `/home/influencer/${influencerId}/contents`,
    method: 'get'
  })
}

