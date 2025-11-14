import axios from 'axios'
import type { AxiosInstance, AxiosResponse } from 'axios'

// API响应类型
export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
}

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 可以在这里添加token等认证信息
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    
    console.log('API响应:', res)
    
    // 后端ResponseDTO的结构: { code: number, msg: string, data: any }
    // code为0表示成功（SUCCESS(0, "操作成功")）
    if (res.code !== undefined && res.code !== 0 && res.code !== 200) {
      console.error('响应错误:', res.msg || '未知错误')
      // 创建一个包含完整错误信息的错误对象
      const error: any = new Error(res.msg || '未知错误')
      error.response = {
        data: res,
        status: response.status
      }
      return Promise.reject(error)
    }
    
    // 返回整个响应对象，包含data字段
    return res
  },
  (error) => {
    console.error('网络错误:', error)
    // 确保错误对象包含response信息
    if (!error.response && error.message) {
      const customError: any = new Error(error.message)
      customError.response = {
        data: {
          msg: error.message,
          code: error.response?.status || 500
        }
      }
      return Promise.reject(customError)
    }
    return Promise.reject(error)
  }
)

export default service

