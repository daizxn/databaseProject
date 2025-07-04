import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/',
      name: 'manage',
      component: () => import('../views/ManagerView.vue'),
      redirect: '/welcome',
      children: [
        {
          path: 'welcome',
          name: 'welcome',
          component: () => import('../views/WelcomeView.vue'),
        },
        {
          path: 'student-info',
          name: 'student-info',
          component: () => import('../views/manage/StudentInfo.vue'),
        },
        {
          path: 'about',
          name: 'about',
          component: () => import('../views/AboutView.vue'),
        },
        {
          path: 'teacher-info',
          name: 'teacher-info',
          component: () => import('../views/manage/TeacherInfo.vue'),
        },
        {
          path: 'course-info',
          name: 'course-info',
          component: () => import('../views/manage/CourseInfo.vue'),
        },
        {
          path: 'student-score',
          name: 'student-score',
          component: () => import('../views/manage/StudentScore.vue'),
        },
        {
          path: 'class-course',
          name: 'class-course',
          component: () => import('../views/manage/ClassCourse.vue'),
        },
        {
          path: 'regions',
          name: 'regions',
          component: () => import('../views/manage/StudentRegion.vue'),
        },
      ],
    },
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 获取用户信息
  const userStr = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')

  // 如果要访问登录页面
  if (to.path === '/login') {
    // 如果已经登录，重定向到主页
    if (userStr) {
      next('/')
    } else {
      next()
    }
  } else {
    // 访问其他页面需要登录
    if (userStr) {
      try {
        const userInfo = JSON.parse(userStr)
        const userRole = userInfo?.userInfo?.roles || ''

        // 检查权限
        if (checkPermission(to.name as string, userRole)) {
          next()
        } else {
          // 没有权限，重定向到欢迎页面
          next('/welcome')
        }
      } catch (error) {
        console.error('解析用户信息失败:', error)
        // 解析失败，清除无效数据并重定向到登录页
        localStorage.removeItem('xm-user')
        sessionStorage.removeItem('xm-user')
        next('/login')
      }
    } else {
      // 未登录，重定向到登录页面
      next('/login')
    }
  }
})

// 权限检查函数
function checkPermission(routeName: string, userRole: string): boolean {
  // 所有用户都可以访问的页面
  const publicRoutes = ['welcome', 'manage', 'login']
  if (publicRoutes.includes(routeName)) {
    return true
  }

  // 学生权限：只能访问 welcome, class-course, student-score
  if (userRole === 'student') {
    const studentRoutes = ['welcome', 'class-course', 'student-score']
    return studentRoutes.includes(routeName)
  }

  // 教师权限：只能访问 welcome, course-info
  if (userRole === 'teacher') {
    const teacherRoutes = ['welcome', 'course-info']
    return teacherRoutes.includes(routeName)
  }

  // 管理员权限：可以访问所有页面
  if (userRole === 'admin') {
    return true
  }

  // 默认情况下拒绝访问
  return false
}

export default router
