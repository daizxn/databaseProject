import axios from 'axios'
import router from '@/router'

// 创建可一个新的axios对象
const request = axios.create({
  baseURL: import.meta.env.VITE_APP_BASEURL, // 后端的接口地址  ip:port
  timeout: 30000, // 30s请求超时
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(
  (config) => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8' // 设置请求头格式

    // 获取缓存的用户信息，优先从 localStorage，其次从 sessionStorage
    const userFromLocal = localStorage.getItem('xm-user')
    const userFromSession = sessionStorage.getItem('xm-user')
    const userStr = userFromLocal || userFromSession

    if (userStr) {
      try {
        const user = JSON.parse(userStr)
        if (user.token) {
          // 设置 Authorization 头部，这是标准的 Bearer token 格式
          config.headers['Authorization'] = `Bearer ${user.token}`
          // 同时保持原有的 token 头部，以兼容后端
          config.headers['token'] = user.token
        }
      } catch (error) {
        console.error('解析用户信息失败:', error)
        // 如果解析失败，清除无效的用户信息
        localStorage.removeItem('xm-user')
        sessionStorage.removeItem('xm-user')
      }
    }

    return config
  },
  (error) => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
  },
)

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  (response) => {
    let res = response.data

    // 兼容服务端返回的字符串数据
    if (typeof res === 'string') {
      res = res ? JSON.parse(res) : res
    }

    // 处理认证失败的情况
    if (res.code === '401' || res.code === 401) {
      console.warn('认证失败，跳转到登录页面')
      // 清除本地存储的用户信息
      localStorage.removeItem('xm-user')
      sessionStorage.removeItem('xm-user')
      // 跳转到登录页面
      router.push('/login')
      return Promise.reject(new Error('认证失败'))
    }

    return res
  },
  (error) => {
    console.error('response error: ' + error) // for debug

    // 处理网络错误或服务器错误
    if (error.response) {
      const status = error.response.status
      if (status === 401) {
        console.warn('HTTP 401 - 认证失败')
        localStorage.removeItem('xm-user')
        sessionStorage.removeItem('xm-user')
        router.push('/login')
      } else if (status === 403) {
        console.warn('HTTP 403 - 权限不足')
      } else if (status >= 500) {
        console.error('服务器错误:', status)
      }
    }

    return Promise.reject(error)
  },
)

interface ApiResponse {
  code: string
  msg: string
  [key: string]: unknown
}

export default request
export type { ApiResponse }
