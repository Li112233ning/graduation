import request from '@/lib/request'

export interface LoginParams {
  username: string
  password: string
}

export interface RegisterParams {
  username: string
  password: string
  confirmPassword: string
  email?: string
}

/**
 * 登录
 */
export function login(data: LoginParams) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

/**
 * 注册
 */
export function register(data: RegisterParams) {
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

/**
 * 登出
 */
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
  return request({
    url: '/getLoginUserInfo',
    method: 'get'
  })
}

