<template>
  <div class="welcome-container">
    <div class="welcome-header">
      <div class="welcome-icon">
        <el-icon size="64" color="#409eff">
          <i-ep-school />
        </el-icon>
      </div>
      <h1>欢迎使用学生成绩管理系统</h1>
      <p class="welcome-subtitle">Student Grade Management System</p>
    </div>

    <div class="feature-cards">
      <div class="feature-card" @click="navigateTo('student-info')">
        <el-icon size="32" color="#67c23a">
          <i-ep-user />
        </el-icon>
        <h3>学生管理</h3>
        <p>管理学生基本信息、查看学生统计数据</p>
      </div>

      <div class="feature-card" @click="navigateTo('teacher-info')">
        <el-icon size="32" color="#e6a23c">
          <i-ep-avatar />
        </el-icon>
        <h3>教师管理</h3>
        <p>管理教师信息和教学安排</p>
      </div>

      <div class="feature-card" @click="navigateTo('course-info')">
        <el-icon size="32" color="#f56c6c">
          <i-ep-reading />
        </el-icon>
        <h3>课程管理</h3>
        <p>管理课程信息、学分设置</p>
      </div>

      <div class="feature-card" @click="navigateTo('student-score')">
        <el-icon size="32" color="#909399">
          <i-ep-document />
        </el-icon>
        <h3>成绩管理</h3>
        <p>查看和管理学生成绩信息</p>
      </div>
    </div>

    <div class="system-info">
      <div class="info-item">
        <span class="label">当前用户：</span>
        <span class="value">{{ userInfo?.userInfo?.username || '未知用户' }}</span>
      </div>
      <div class="info-item">
        <span class="label">登录时间：</span>
        <span class="value">{{ loginTime }}</span>
      </div>
      <div class="info-item">
        <span class="label">系统版本：</span>
        <span class="value">v1.0.0</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

// 获取用户信息
const userInfo = ref<{
  username: string
  token: string
  userInfo: {
    id: number
    username: string
    refId: string
    roles: string
  }
} | null>(null)
const loginTime = ref('')

onMounted(() => {
  const userStr = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')
  if (userStr) {
    userInfo.value = JSON.parse(userStr)
  }

  // 设置登录时间
  loginTime.value = new Date().toLocaleString()
})

// 导航到指定页面
const navigateTo = (routeName: string) => {
  router.push({ name: routeName })
}
</script>

<style scoped>
.welcome-container {
  padding: 40px;
  max-width: 1200px;
  margin: 0 auto;
}

.welcome-header {
  text-align: center;
  margin-bottom: 60px;
}

.welcome-icon {
  margin-bottom: 20px;
}

.welcome-header h1 {
  font-size: 36px;
  color: #2c3e50;
  margin: 0 0 10px 0;
  font-weight: 600;
}

.welcome-subtitle {
  font-size: 16px;
  color: #7f8c8d;
  margin: 0;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  margin-bottom: 60px;
}

.feature-card {
  background: white;
  border-radius: 16px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  cursor: pointer;
  border: 1px solid #f0f0f0;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
  border-color: #409eff;
}

.feature-card h3 {
  font-size: 20px;
  color: #2c3e50;
  margin: 20px 0 10px 0;
  font-weight: 600;
}

.feature-card p {
  color: #7f8c8d;
  line-height: 1.6;
  margin: 0;
}

.system-info {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 30px;
  color: white;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.info-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.info-item .label {
  font-size: 14px;
  opacity: 0.8;
  margin-bottom: 8px;
}

.info-item .value {
  font-size: 16px;
  font-weight: 600;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .welcome-container {
    padding: 20px;
  }

  .welcome-header h1 {
    font-size: 28px;
  }

  .feature-cards {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .system-info {
    grid-template-columns: 1fr;
    gap: 15px;
  }
}
</style>
