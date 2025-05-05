import {createRouter, createWebHistory} from 'vue-router'
import AuthPage from '@/views/AuthPage.vue'
import Home from '@/views/home.vue'
import BusinessDetail from "@/views/BusinessDetail.vue";
import UserInfo from "@/views/UserInfo.vue";

const routes = [
    {
        path: '/',
        redirect: '/login' // 默认跳转到登录页
    },
    {
        path: '/login',
        name: 'AuthPage',
        component: AuthPage
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        meta: {requiresAuth: true} // 需要登录才能访问
    },
    {
        path: '/user-info',
        name: 'UserInfo',
        component: UserInfo,
    },
    {
        path: '/businessDetail/:id',
        name: 'BusinessDetail',
        component: BusinessDetail,
        meta: {requiresAuth: true}
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫：拦截未登录访问
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('userInfo')
    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/login') // 未登录则跳转登录页
    } else {
        next()
    }
})

export default router