import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/',
      name: 'manage',
      component: () => import('../views/ManagerView.vue'),
      children: [
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
      ],
    },
  ],
})

export default router
