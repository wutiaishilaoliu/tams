import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Main from '@/views/Main.vue'

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/teacher',
        component: () => import('@/views/teacher/TeacherHome.vue'),
        meta: { requiresAuth: true, userType: 'teacher' }
    },
    {
        path: '/student',
        component: () => import('@/views/student/StudentHome.vue'),
        meta: { requiresAuth: true, userType: 'student' }
    },
    {
        path: '/',
        redirect: '/course-scheduling',
        component: Main,
        children: [
            {
                path: '/course-scheduling',
                component: () => import('@/views/course-scheduling/CourseScheduling.vue')
            },
            {
                path: '/course-scheduling-list',
                component: () => import('@/views/course-scheduling-list/CourseSchedulingList.vue')
            },
            {
                path: '/classroom',
                component: () => import('@/views/classroom/Classroom.vue')
            },
            {
                path: '/course',
                component: () => import('@/views/course/Course.vue')
            },
            {
                path: '/teacher-manage',
                component: () => import('@/views/teacher/Teacher.vue')
            },
            {
                path: '/student-manage',
                component: () => import('@/views/student/Student.vue')
            },
            {
                path: '/report',
                component: () => import('@/views/report/Report.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const userInfoStr = localStorage.getItem('userInfo')
    
    if (to.meta.requiresAuth) {
        if (!token) {
            next('/login')
            return
        }
        
        if (to.meta.userType && userInfoStr) {
            const userInfo = JSON.parse(userInfoStr)
            if (userInfo.userType !== to.meta.userType) {
                // 用户类型不匹配，跳转到正确的页面
                next(userInfo.userType === 'teacher' ? '/teacher' : '/student')
                return
            }
        }
    }
    
    next()
})

export default router
