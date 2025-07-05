<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import request from '@/utils/request'
import 'element-plus/theme-chalk/el-message-box.css'
import 'element-plus/theme-chalk/el-message.css'

const router = useRouter()
const route = useRoute()

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

// 当前激活的导航项
const activeIndex = ref('/')

// 用户角色
const userRole = ref('')

// 修改密码相关状态
const changePasswordVisible = ref(false)
const changePasswordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})
const changePasswordLoading = ref(false)

// 根据角色过滤的导航菜单
const filteredMenuItems = computed(() => {
  const role = userRole.value

  // 基础菜单项
  const baseMenuItems: Array<{
    key: string
    icon: string
    iconColor?: string
    label: string
    type: 'item' | 'submenu'
    children?: Array<{ key: string; label: string }>
  }> = [
    {
      key: '/',
      icon: 'house',
      label: '首页',
      type: 'item',
    },
  ]

  // 根据角色添加菜单项
  if (role === 'student') {
    // 学生：只能看到 welcome, class-course, student-score
    baseMenuItems.push(
      {
        key: 'student-score',
        icon: 'document',
        iconColor: '#909399',
        label: '学生成绩查询',
        type: 'item',
      },
      {
        key: 'class-course',
        icon: 'reading',
        iconColor: '#f56c6c',
        label: '班级课程查看',
        type: 'item',
      },
      {
        key: 'student-person-info',
        icon: 'user',
        iconColor: '#67c23a',
        label: '个人信息查看',
        type: 'item',
      },
    )
  } else if (role === 'teacher') {
    // 教师：只能看到 welcome, course-info
    baseMenuItems.push({
      key: 'course-info',
      icon: 'reading',
      iconColor: '#f56c6c',
      label: '课程信息管理',
      type: 'item',
    })
  } else if (role === 'admin') {
    // 管理员：可以看到所有菜单
    baseMenuItems.push(
      {
        key: 'student',
        icon: 'user',
        iconColor: '#67c23a',
        label: '学生管理',
        type: 'submenu',
        children: [
          { key: 'student-info', label: '学生信息查看' },
          { key: 'regions', label: '学生地区统计' },
          { key: 'student-score', label: '学生成绩查询' },
        ],
      },
      {
        key: 'teacher',
        icon: 'avatar',
        iconColor: '#e6a23c',
        label: '教师管理',
        type: 'submenu',
        children: [{ key: 'teacher-info', label: '教师信息查看' }],
      },
      {
        key: 'course',
        icon: 'reading',
        iconColor: '#f56c6c',
        label: '课程管理',
        type: 'submenu',
        children: [
          { key: 'course-info', label: '课程信息管理' },
          { key: 'class-course', label: '班级课程查看' },
        ],
      },
    )
  }

  return baseMenuItems
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

  // 设置当前激活的导航项
  activeIndex.value = route.path === '/manage' ? '/' : (route.name as string) || '/'
})

// 监听路由变化
watch(
  () => route.path,
  (newPath: string) => {
    if (newPath === '/manage') {
      activeIndex.value = '/'
    } else {
      activeIndex.value = (route.name as string) || '/'
    }
  },
)

// 处理导航选择
const handleSelect = (key: string) => {
  activeIndex.value = key
  if (key === '/') {
    router.push('/')
  }
}

// 处理下拉菜单命令
const handleCommand = (command: string) => {
  if (command === 'logout') {
    handleLogout()
  } else if (command === 'changePassword') {
    openChangePasswordDialog()
  }
}

// 打开修改密码对话框
const openChangePasswordDialog = () => {
  changePasswordVisible.value = true
  // 重置表单
  changePasswordForm.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  }
}

// 关闭修改密码对话框
const closeChangePasswordDialog = () => {
  changePasswordVisible.value = false
  changePasswordForm.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  }
}

// 修改密码
const handleChangePassword = async () => {
  // 表单验证
  if (!changePasswordForm.value.oldPassword) {
    ElMessage.error('请输入原密码')
    return
  }

  if (!changePasswordForm.value.newPassword) {
    ElMessage.error('请输入新密码')
    return
  }

  if (changePasswordForm.value.newPassword.length < 6) {
    ElMessage.error('新密码长度不能少于6位')
    return
  }

  if (changePasswordForm.value.newPassword !== changePasswordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }

  if (changePasswordForm.value.oldPassword === changePasswordForm.value.newPassword) {
    ElMessage.error('新密码不能与原密码相同')
    return
  }

  changePasswordLoading.value = true

  try {
    const response = await request.post('/web/changePassword', {
      username: userInfo.value?.userInfo?.username,
      oldPassword: changePasswordForm.value.oldPassword,
      newPassword: changePasswordForm.value.newPassword,
    })

    if (response.data) {
      ElMessage.success('密码修改成功，请重新登录')
      closeChangePasswordDialog()

      // 清除用户信息并跳转到登录页
      setTimeout(() => {
        localStorage.removeItem('xm-user')
        sessionStorage.removeItem('xm-user')
        router.push('/login')
      }, 1500)
    } else {
      ElMessage.error('密码修改失败')
    }
  } catch (error: unknown) {
    console.error('修改密码失败:', error)
    const errorMessage = error instanceof Error ? error.message : '密码修改失败'
    ElMessage.error(errorMessage)
  } finally {
    changePasswordLoading.value = false
  }
}

// 退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })

    // 清除用户信息
    localStorage.removeItem('xm-user')
    sessionStorage.removeItem('xm-user')

    // 跳转到登录页面
    router.push('/login')
  } catch {
    // 用户取消退出
  }
}
</script>

<template>
  <el-container class="layout-container" style="height: 100vh">
    <!-- 头部区域 -->
    <el-header class="header-section">
      <div class="toolbar">
        <span class="system-title">学生成绩管理系统</span>
        <div class="user-info">
          <!-- 角色显示 -->
          <div class="role-indicator">
            <el-tag
              :type="
                userRole === 'admin' ? 'danger' : userRole === 'teacher' ? 'warning' : 'success'
              "
              size="small"
              style="margin-right: 12px"
            >
              {{ userRole === 'admin' ? '管理员' : userRole === 'teacher' ? '教师' : '学生' }}
            </el-tag>
          </div>

          <el-dropdown @command="handleCommand" trigger="click">
            <div class="user-avatar">
              <el-icon style="margin-right: 8px"><i-ep-user /></el-icon>
              <span>{{ userInfo?.userInfo?.username || '用户' }}</span>
              <el-icon style="margin-left: 8px"><i-ep-arrow-down /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="changePassword">
                  <el-icon><i-ep-key /></el-icon>
                  修改密码
                </el-dropdown-item>
                <el-dropdown-item command="logout">
                  <el-icon><i-ep-switch-button /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-header>

    <!-- 导航栏区域 -->
    <el-header class="nav-section" height="60px">
      <el-menu
        :default-active="activeIndex"
        class="nav-menu"
        mode="horizontal"
        :router="true"
        @select="handleSelect"
      >
        <!-- 动态渲染菜单项 -->
        <template v-for="item in filteredMenuItems" :key="item.key">
          <!-- 普通菜单项 -->
          <el-menu-item v-if="item.type === 'item'" :index="item.key" class="nav-item">
            <el-icon :color="item.iconColor">
              <component :is="`i-ep-${item.icon}`" />
            </el-icon>
            <span>{{ item.label }}</span>
          </el-menu-item>

          <!-- 子菜单项 -->
          <el-sub-menu v-else-if="item.type === 'submenu'" :index="item.key" class="nav-item">
            <template #title>
              <el-icon :color="item.iconColor">
                <component :is="`i-ep-${item.icon}`" />
              </el-icon>
              <span>{{ item.label }}</span>
            </template>
            <el-menu-item v-for="child in item.children" :key="child.key" :index="child.key">
              {{ child.label }}
            </el-menu-item>
          </el-sub-menu>
        </template>
      </el-menu>
    </el-header>

    <!-- 主内容区域 -->
    <el-main class="main-content">
      <el-scrollbar>
        <router-view />
      </el-scrollbar>
    </el-main>

    <!-- 修改密码对话框 -->
    <el-dialog
      v-model="changePasswordVisible"
      title="修改密码"
      width="450px"
      :before-close="closeChangePasswordDialog"
      class="change-password-dialog"
    >
      <el-form :model="changePasswordForm" label-width="100px" class="change-password-form">
        <el-form-item label="原密码" required>
          <el-input
            v-model="changePasswordForm.oldPassword"
            type="password"
            placeholder="请输入原密码"
            show-password
            clearable
          />
        </el-form-item>

        <el-form-item label="新密码" required>
          <el-input
            v-model="changePasswordForm.newPassword"
            type="password"
            placeholder="请输入新密码（至少6位）"
            show-password
            clearable
          />
        </el-form-item>

        <el-form-item label="确认密码" required>
          <el-input
            v-model="changePasswordForm.confirmPassword"
            type="password"
            placeholder="请再次输入新密码"
            show-password
            clearable
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeChangePasswordDialog">取消</el-button>
          <el-button type="primary" @click="handleChangePassword" :loading="changePasswordLoading">
            确认修改
          </el-button>
        </span>
      </template>
    </el-dialog>
  </el-container>
</template>

<style scoped>
.layout-container {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

/* 头部区域样式 */
.header-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  height: 60px !important;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 30px;
}

.system-title {
  font-size: 20px;
  font-weight: 600;
  color: white;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  display: flex;
  align-items: center;
  padding: 8px 16px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.1);
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
}

.user-avatar:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-1px);
}

/* 导航栏区域样式 */
.nav-section {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  height: 60px !important;
  padding: 0 !important;
}

.nav-menu {
  height: 100%;
  border-bottom: none;
  background: transparent;
  padding: 0 30px;
}

:deep(.nav-menu .el-menu-item) {
  height: 60px;
  line-height: 60px;
  color: #2c3e50;
  font-weight: 500;
  border-bottom: 3px solid transparent;
  transition: all 0.3s ease;
}

:deep(.nav-menu .el-sub-menu) {
  height: 60px;
  line-height: 60px;
}

:deep(.nav-menu .el-sub-menu > .el-sub-menu__title) {
  height: 60px;
  line-height: 60px;
  color: #2c3e50;
  font-weight: 500;
  border-bottom: 3px solid transparent;
  transition: all 0.3s ease;
}

:deep(.nav-menu .el-menu-item:hover) {
  background: rgba(102, 126, 234, 0.05);
  color: #667eea;
  border-bottom-color: #667eea;
}

:deep(.nav-menu .el-sub-menu:hover > .el-sub-menu__title) {
  background: rgba(102, 126, 234, 0.05);
  color: #667eea;
  border-bottom-color: #667eea;
}

:deep(.nav-menu .el-menu-item.is-active) {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
  border-bottom-color: #667eea;
}

:deep(.nav-menu .el-sub-menu.is-active > .el-sub-menu__title) {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
  border-bottom-color: #667eea;
}

/* 下拉菜单样式 */
:deep(.el-sub-menu__dropdown) {
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
}

:deep(.el-sub-menu__dropdown .el-menu-item) {
  height: 40px;
  line-height: 40px;
  border-bottom: none;
  margin: 4px 8px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

:deep(.el-sub-menu__dropdown .el-menu-item:hover) {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

/* 主内容区域样式 */
.main-content {
  padding: 0;
  background: transparent;
  height: calc(100vh - 120px);
}

/* 图标样式 */
.nav-item .el-icon {
  margin-right: 8px;
  font-size: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .toolbar {
    padding: 0 15px;
  }

  .system-title {
    font-size: 16px;
  }

  .nav-menu {
    padding: 0 15px;
  }

  :deep(.nav-menu .el-menu-item),
  :deep(.nav-menu .el-sub-menu > .el-sub-menu__title) {
    font-size: 14px;
  }
}

/* 角色指示器样式 */
.role-indicator {
  display: flex;
  align-items: center;
}

.role-indicator .el-tag {
  font-weight: 500;
  border-radius: 12px;
}

/* 修改密码对话框样式 */
:deep(.change-password-dialog) {
  border-radius: 16px;
  overflow: hidden;
}

:deep(.change-password-dialog .el-dialog__header) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 20px 25px;
  margin: 0;
  border-radius: 14px;
}

:deep(.change-password-dialog .el-dialog__title) {
  color: white;
  font-weight: 600;
  font-size: 18px;
}

:deep(.change-password-dialog .el-dialog__headerbtn) {
  top: 20px;
  right: 20px;
}

:deep(.change-password-dialog .el-dialog__close) {
  color: white;
  font-size: 18px;
}

:deep(.change-password-dialog .el-dialog__body) {
  padding: 25px;
}

:deep(.change-password-dialog .el-dialog__footer) {
  padding: 20px 25px 25px;
  border-top: 1px solid #f0f0f0;
}

.change-password-form {
  margin-top: 10px;
}

.change-password-form .el-form-item {
  margin-bottom: 20px;
}

:deep(.change-password-form .el-form-item__label) {
  font-weight: 500;
  color: #2c3e50;
}

:deep(.change-password-form .el-input__wrapper) {
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s ease;
}

:deep(.change-password-form .el-input__wrapper:hover) {
  border-color: #667eea;
}

:deep(.change-password-form .el-input__wrapper.is-focus) {
  border-color: #667eea;
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
}

.dialog-footer .el-button {
  border-radius: 8px;
  padding: 10px 20px;
  font-weight: 500;
}

.dialog-footer .el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.dialog-footer .el-button--primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
}
</style>
