import request from '@/lib/request'

/**
 * 更新用户资料
 */
export function updateUserProfile(data: { nickname: string; email?: string }) {
  return request({
    url: '/user/profile',
    method: 'put',
    data
  })
}

/**
 * 上传头像
 */
export function uploadAvatar(formData: FormData) {
  return request({
    url: '/user/avatar',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}