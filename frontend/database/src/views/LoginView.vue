<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <div class="logo">
          <el-icon size="32" color="#409eff">
            <i-ep-school />
          </el-icon>
        </div>
        <h1 class="title">学生成绩管理系统</h1>
        <p class="subtitle">Student Grade Management System</p>
      </div>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
            clearable
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" class="remember-me"> 记住我 </el-checkbox>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="login-button"
            :loading="loading"
            @click="handleLogin"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <p>© 2025 学生成绩管理系统. All rights reserved.</p>
      </div>
    </div>

    <!-- 背景装饰 -->
    <div class="background-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import request from '@/utils/request'
import { ElMessage, type FormInstance } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

// 响应式数据
const loginFormRef = ref<FormInstance>()
const loading = ref(false)

const loginForm = ref({
  username: '',
  password: '',
  rememberMe: false,
})

// 表单验证规则
const loginRules = {
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: 'blur',
    },
    {
      min: 3,
      max: 20,
      message: '用户名长度为 3 到 20 个字符',
      trigger: 'blur',
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur',
    },
    {
      min: 2,
      max: 20,
      message: '密码长度为 6 到 20 个字符',
      trigger: 'blur',
    },
  ],
}

// 登录处理
const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    const valid = await loginFormRef.value.validate()
    if (!valid) return

    loading.value = true

    interface ApiResponse {
      code: string
      msg: string
      data: {
        message?: string
        token?: string
        userInfo?: {
          id: number
          username: string
          refId: string
          roles: string[]
        }
      }
    }

    // 调用登录接口
    const response: ApiResponse = await request.post('/web/login', {
      username: loginForm.value.username,
      password: loginForm.value.password,
    })

    if (response.code === '200') {
      ElMessage.success(response.data.message || '登录成功！')

      // 保存用户信息到本地存储
      const userInfo = {
        token: response.data.token,
        userInfo: response.data.userInfo,
        username: response.data.userInfo?.username,
        roles: response.data.userInfo?.roles,
        refId: response.data.userInfo?.refId,
        id: response.data.userInfo?.id,
      }

      if (loginForm.value.rememberMe) {
        localStorage.setItem('xm-user', JSON.stringify(userInfo))
      } else {
        sessionStorage.setItem('xm-user', JSON.stringify(userInfo))
      }

      // 跳转到主页
      router.push('/')
    } else {
      ElMessage.error(response.msg || '登录失败，请检查用户名和密码')
    }
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error('登录失败，请稍后再试')
  } finally {
    loading.value = false
  }
}

// 组件挂载时检查是否已登录
onMounted(() => {
  const userInfo = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')
  if (userInfo) {
    // 如果已经登录，直接跳转到主页
    router.push('/')
  }
})
</script>

<style scoped>
.login-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;
}

.login-card {
  position: relative;
  z-index: 10;
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.logo {
  margin-bottom: 16px;
}

.title {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.subtitle {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0;
  font-weight: 400;
}

.login-form {
  margin-bottom: 20px;
}

.login-form .el-form-item {
  margin-bottom: 20px;
}

.remember-me {
  color: #606266;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
}

.login-footer {
  text-align: center;
  margin-top: 20px;
}

.login-footer p {
  font-size: 12px;
  color: #95a5a6;
  margin: 0;
}

/* 背景装饰 */
.background-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 200px;
  height: 200px;
  top: 10%;
  left: 10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 300px;
  height: 300px;
  top: 60%;
  right: 10%;
  animation-delay: 2s;
}

.circle-3 {
  width: 150px;
  height: 150px;
  bottom: 20%;
  left: 60%;
  animation-delay: 4s;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
  }
}

/* 响应式设计 */
@media (max-width: 480px) {
  .login-card {
    width: 90%;
    padding: 30px 20px;
    margin: 20px;
  }

  .title {
    font-size: 24px;
  }

  .circle {
    display: none;
  }
}

/* 输入框样式优化 */
:deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}
</style>
