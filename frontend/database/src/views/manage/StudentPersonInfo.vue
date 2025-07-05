<template>
  <div class="student-info-container">
    <div class="page-header">
      <h1 class="page-title">
        <el-icon><User /></el-icon>
        学生个人信息
      </h1>
      <p class="page-subtitle">查看学生详细信息和成绩统计</p>
    </div>

    <!-- 查询条件 -->
    <div class="query-card">
      <el-form :inline="true" class="query-form">
        <el-form-item label="学年">
          <el-select v-model="academicYear" placeholder="请选择学年" style="width: 150px">
            <el-option label="2023-2024" value="2023-2024" />
            <el-option label="2022-2023" value="2022-2023" />
            <el-option label="2021-2022" value="2021-2022" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getStudentInfo" :loading="loading">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 学生信息展示 -->
    <div v-if="studentInfo" class="info-content">
      <!-- 基本信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <h3>
            <el-icon><UserFilled /></el-icon> 基本信息
          </h3>
        </div>
        <div class="card-content">
          <div class="info-grid">
            <div class="info-item">
              <span class="label">姓名</span>
              <span class="value">{{ studentInfo.studentName }}</span>
            </div>
            <div class="info-item">
              <span class="label">学号</span>
              <span class="value">{{ studentInfo.studentNumber }}</span>
            </div>
            <div class="info-item">
              <span class="label">班级</span>
              <span class="value">{{ studentInfo.className }}</span>
            </div>
            <div class="info-item">
              <span class="label">系部</span>
              <span class="value">{{ studentInfo.departmentName }}</span>
            </div>
            <div class="info-item">
              <span class="label">学年</span>
              <span class="value">{{ studentInfo.academicYear }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 成绩统计卡片 -->
      <div class="info-card">
        <div class="card-header">
          <h3>
            <el-icon><DataAnalysis /></el-icon> 成绩统计
          </h3>
        </div>
        <div class="card-content">
          <div class="stats-grid">
            <div class="stat-item highlight">
              <div class="stat-value">{{ studentInfo.averageScore?.toFixed(1) || 0 }}</div>
              <div class="stat-label">平均分</div>
            </div>
            <div class="stat-item highlight">
              <div class="stat-value">{{ studentInfo.averageGpa?.toFixed(2) || 0 }}</div>
              <div class="stat-label">平均GPA</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 课程信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <h3>
            <el-icon><Reading /></el-icon> 课程信息
          </h3>
        </div>
        <div class="card-content">
          <div class="course-grid">
            <div class="course-item">
              <div class="course-icon total">
                <el-icon><Document /></el-icon>
              </div>
              <div class="course-info">
                <div class="course-number">{{ studentInfo.totalCourses || 0 }}</div>
                <div class="course-label">总课程数</div>
              </div>
            </div>
            <div class="course-item">
              <div class="course-icon passed">
                <el-icon><CircleCheck /></el-icon>
              </div>
              <div class="course-info">
                <div class="course-number">{{ studentInfo.passedCourses || 0 }}</div>
                <div class="course-label">已通过</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 学分信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <h3>
            <el-icon><Medal /></el-icon> 学分信息
          </h3>
        </div>
        <div class="card-content">
          <div class="credit-progress">
            <div class="credit-item">
              <div class="credit-header">
                <span class="credit-label">已获学分</span>
                <span class="credit-value"
                  >{{ studentInfo.earnedCredits || 0 }} / {{ studentInfo.totalCredits || 0 }}</span
                >
              </div>
              <el-progress
                :percentage="calculateCreditPercentage()"
                :color="getCreditColor()"
                :stroke-width="12"
                class="credit-bar"
              />
            </div>
          </div>
        </div>
      </div>

      <!-- 排名信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <h3>
            <el-icon><TrendCharts /></el-icon> 排名信息
          </h3>
        </div>
        <div class="card-content">
          <div class="rank-grid">
            <div class="rank-item">
              <div class="rank-type">班级排名</div>
              <div class="rank-info">
                <span class="rank-position">{{ studentInfo.classRanking || 0 }}</span>
                <span class="rank-total">/ {{ studentInfo.classTotalStudents || 0 }}</span>
              </div>
              <div class="rank-percentage">
                {{ getRankPercentage(studentInfo.classRanking, studentInfo.classTotalStudents) }}%
              </div>
            </div>
            <div class="rank-item">
              <div class="rank-type">系部排名</div>
              <div class="rank-info">
                <span class="rank-position">{{ studentInfo.departmentRanking || 0 }}</span>
                <span class="rank-total">/ {{ studentInfo.departmentTotalStudents || 0 }}</span>
              </div>
              <div class="rank-percentage">
                {{
                  getRankPercentage(
                    studentInfo.departmentRanking,
                    studentInfo.departmentTotalStudents,
                  )
                }}%
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 暂无数据提示 -->
    <div v-else-if="!loading" class="no-data">
      <el-empty description="暂无学生信息">
        <el-button type="primary" @click="getStudentInfo">重新查询</el-button>
      </el-empty>
    </div>

    <!-- 加载状态 -->
    <div v-if="loading" class="loading-wrapper">
      <el-skeleton :rows="8" animated />
    </div>
  </div>
</template>

<script lang="ts" setup>
import {
  User,
  UserFilled,
  Search,
  DataAnalysis,
  Reading,
  Document,
  CircleCheck,
  Medal,
  TrendCharts,
} from '@element-plus/icons-vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

const studentInfo = ref<{
  studentName?: string
  studentNumber?: string
  className?: string
  departmentName?: string
  academicYear?: string
  averageGpa?: number
  averageScore?: number
  classRanking?: number
  classTotalStudents?: number
  departmentRanking?: number
  departmentTotalStudents?: number
  earnedCredits?: number
  passedCourses?: number
  totalCourses?: number
  totalCredits?: number
} | null>(null)
const academicYear = ref('2023-2024') // 默认学年
const loading = ref(false)

const getStudentInfo = async () => {
  loading.value = true
  try {
    const response = await request.get('/students/yearlyRanking', {
      params: {
        academicYear: academicYear.value,
      },
    })
    if (response.data) {
      studentInfo.value = response.data
      console.log('获取学生信息成功:', studentInfo.value)
    } else {
      ElMessage.error('获取学生信息失败: 数据为空')
      studentInfo.value = null
    }
  } catch (error) {
    console.error('获取学生信息失败:', error)
    ElMessage.error('获取学生信息失败')
    studentInfo.value = null
  } finally {
    loading.value = false
  }
}

// 计算学分完成百分比
const calculateCreditPercentage = () => {
  if (!studentInfo.value || !studentInfo.value.totalCredits) return 0
  return Math.round((studentInfo.value.earnedCredits / studentInfo.value.totalCredits) * 100)
}

// 获取学分进度条颜色
const getCreditColor = () => {
  const percentage = calculateCreditPercentage()
  if (percentage >= 80) return '#67c23a'
  if (percentage >= 60) return '#e6a23c'
  if (percentage >= 40) return '#f56c6c'
  return '#909399'
}

// 计算排名百分比
const getRankPercentage = (rank: number, total: number) => {
  if (!rank || !total) return 0
  return Math.round(((total - rank + 1) / total) * 100)
}

onMounted(() => {
  getStudentInfo()
})
</script>

<style scoped>
@import '@/styles/manage-common.css';

.student-info-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
  color: white;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 10px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
}

.page-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  margin: 0;
}

.query-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 25px;
  margin-bottom: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

.query-form {
  display: flex;
  justify-content: center;
  align-items: end;
  gap: 20px;
  flex-wrap: wrap;
}

.info-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  gap: 25px;
}

.info-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  transition:
    transform 0.3s ease,
    box-shadow 0.3s ease;
}

.info-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 45px rgba(0, 0, 0, 0.15);
}

.card-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px 25px;
  color: white;
}

.card-header h3 {
  margin: 0;
  font-size: 1.3rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}

.card-content {
  padding: 25px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-item .label {
  font-size: 0.9rem;
  color: #666;
  font-weight: 500;
}

.info-item .value {
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
  padding: 8px 12px;
  background: #f8f9ff;
  border-radius: 8px;
  border: 2px solid #e6e8ff;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
}

.stat-item {
  text-align: center;
  padding: 20px;
  border-radius: 12px;
  background: #f8f9ff;
  border: 2px solid #e6e8ff;
  transition: all 0.3s ease;
}

.stat-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.2);
}

.stat-item.highlight {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border-color: transparent;
}

.stat-value {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 0.9rem;
  opacity: 0.8;
}

.course-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  max-width: 600px;
  margin: 0 auto;
}

.course-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  border-radius: 12px;
  background: #f8f9ff;
  border: 2px solid #e6e8ff;
  transition: all 0.3s ease;
}

.course-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.2);
}

.course-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
}

.course-icon.total {
  background: linear-gradient(135deg, #667eea, #764ba2);
}

.course-icon.passed {
  background: linear-gradient(135deg, #67c23a, #85ce61);
}

.course-number {
  font-size: 1.8rem;
  font-weight: 700;
  color: #333;
}

.course-label {
  font-size: 0.9rem;
  color: #666;
}

.credit-progress {
  max-width: 500px;
  margin: 0 auto;
}

.credit-item {
  margin-bottom: 20px;
}

.credit-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.credit-label {
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
}

.credit-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #667eea;
}

.credit-bar {
  margin-top: 10px;
}

.rank-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 25px;
}

.rank-item {
  text-align: center;
  padding: 25px;
  border-radius: 16px;
  background: linear-gradient(135deg, #f8f9ff, #e6e8ff);
  border: 2px solid #d6d9ff;
  transition: all 0.3s ease;
}

.rank-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.2);
}

.rank-type {
  font-size: 1.1rem;
  font-weight: 600;
  color: #667eea;
  margin-bottom: 15px;
}

.rank-info {
  margin-bottom: 10px;
}

.rank-position {
  font-size: 2.5rem;
  font-weight: 700;
  color: #333;
}

.rank-total {
  font-size: 1.2rem;
  color: #666;
}

.rank-percentage {
  font-size: 1rem;
  color: #667eea;
  font-weight: 600;
}

.no-data {
  text-align: center;
  padding: 60px 20px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

.loading-wrapper {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .student-info-container {
    padding: 15px;
  }

  .page-title {
    font-size: 2rem;
  }

  .info-content {
    grid-template-columns: 1fr;
  }

  .query-form {
    flex-direction: column;
    align-items: stretch;
  }

  .query-form .el-form-item {
    margin-bottom: 15px;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }

  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .course-grid {
    grid-template-columns: 1fr;
    max-width: none;
  }

  .rank-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .course-item {
    flex-direction: column;
    text-align: center;
  }
}
</style>
