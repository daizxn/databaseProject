<template>
  <div class="welcome-container">
    <div class="welcome-header">
      <div class="welcome-icon">
        <el-icon size="64" color="#409eff">
          <School />
        </el-icon>
      </div>
      <h1>欢迎使用学生成绩管理系统</h1>
      <p class="welcome-subtitle">Student Grade Management System</p>
    </div>
    <div class="feature-cards">
      <!-- 动态渲染功能卡片 -->
      <div
        v-for="card in filteredFeatureCards"
        :key="card.route"
        class="feature-card"
        @click="navigateTo(card.route)"
      >
        <el-icon size="64" :color="card.color">
          <component :is="iconMap[card.icon]" />
        </el-icon>
        <h3>{{ card.title }}</h3>
        <p>{{ card.description }}</p>
      </div>

      <!-- 如果没有可用功能，显示提示 -->
      <div v-if="filteredFeatureCards.length === 0" class="no-features">
        <el-icon size="64" color="#909399">
          <Warning />
        </el-icon>
        <h3>暂无可用功能</h3>
        <p>请联系管理员分配相应权限</p>
      </div>
    </div>

    <div class="system-info">
      <div class="info-item">
        <span class="label">当前用户：</span>
        <span class="value">{{ userInfo?.userInfo?.username || '未知用户' }}</span>
      </div>
      <div class="info-item">
        <span class="label">用户角色：</span>
        <span class="value">
          <el-tag
            :type="userRole === 'admin' ? 'danger' : userRole === 'teacher' ? 'warning' : 'success'"
            size="small"
          >
            {{ userRole === 'admin' ? '管理员' : userRole === 'teacher' ? '教师' : '学生' }}
          </el-tag>
        </span>
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
import {
  UserFilled,
  OfficeBuilding,
  Reading,
  DataLine,
  Calendar,
  LocationFilled,
  Warning,
  School,
} from '@element-plus/icons-vue'

const router = useRouter()

// 图标映射对象
const iconMap: Record<string, unknown> = {
  'user-filled': UserFilled,
  'office-building': OfficeBuilding,
  reading: Reading,
  'data-line': DataLine,
  calendar: Calendar,
  'location-filled': LocationFilled,
  warning: Warning,
  school: School,
}

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
const userRole = ref('')

// 根据角色过滤的功能卡片
const filteredFeatureCards = computed(() => {
  const role = userRole.value

  const allCards = [
    {
      route: 'student-info',
      icon: 'user-filled',
      color: '#67c23a',
      title: '学生管理',
      description: '管理学生基本信息、查看学生统计数据',
      roles: ['admin'],
    },
    {
      route: 'teacher-info',
      icon: 'office-building',
      color: '#e6a23c',
      title: '教师管理',
      description: '管理教师信息和教学安排',
      roles: ['admin'],
    },
    {
      route: 'course-info',
      icon: 'reading',
      color: '#f56c6c',
      title: '课程管理',
      description: '管理课程信息、学分设置',
      roles: ['admin', 'teacher'],
    },
    {
      route: 'student-score',
      icon: 'data-line',
      color: '#909399',
      title: '成绩查询',
      description: '查看和管理学生成绩信息',
      roles: ['admin', 'student'],
    },
    {
      route: 'class-course',
      icon: 'calendar',
      color: '#409eff',
      title: '课程查看',
      description: '查看班级课程安排信息',
      roles: ['admin', 'student'],
    },
    {
      route: 'regions',
      icon: 'location-filled',
      color: '#67c23a',
      title: '地区统计',
      description: '查看学生地区分布统计',
      roles: ['admin'],
    },
  ]

  // 根据用户角色过滤卡片
  return allCards.filter((card) => card.roles.includes(role))
})

onMounted(() => {
  const userStr = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')
  if (userStr) {
    try {
      userInfo.value = JSON.parse(userStr)
      userRole.value = userInfo.value?.userInfo?.roles || ''
    } catch (error) {
      console.error('解析用户信息失败:', error)
      localStorage.removeItem('xm-user')
      sessionStorage.removeItem('xm-user')
      router.push('/login')
    }
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

/* 功能卡片图标增强样式 */
.feature-card .el-icon {
  margin-bottom: 16px;
  padding: 16px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.feature-card:hover .el-icon {
  transform: scale(1.1) translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
  background: rgba(255, 255, 255, 0.2);
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

/* 暂无功能提示样式 */
.no-features {
  grid-column: 1 / -1;
  text-align: center;
  padding: 60px 20px;
  color: #909399;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 16px;
  border: 2px dashed #e4e7ed;
}

.no-features h3 {
  font-size: 18px;
  margin: 20px 0 10px 0;
  color: #606266;
}

.no-features p {
  margin: 0;
  font-size: 14px;
  color: #909399;
}

/* 角色标签样式优化 */
.system-info .el-tag {
  font-weight: 500;
  border-radius: 12px;
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
