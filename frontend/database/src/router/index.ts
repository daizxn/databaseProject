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
  const userInfo = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')

  // 如果要访问登录页面
  if (to.path === '/login') {
    // 如果已经登录，重定向到主页
    if (userInfo) {
      next('/')
    } else {
      next()
    }
  } else {
    // 访问其他页面需要登录
    if (userInfo) {
      next()
    } else {
      // 未登录，重定向到登录页面
      next('/login')
    }
  }
})

export default router
