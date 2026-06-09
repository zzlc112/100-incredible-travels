import { createRouter, createWebHistory } from 'vue-router'
import { checkAuth } from '@/api/auth'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/Home.vue') },
  { path: '/travel/:id', name: 'TravelDetail', component: () => import('@/views/TravelDetail.vue') },
  { path: '/admin/login', name: 'AdminLogin', component: () => import('@/views/admin/AdminLogin.vue'), meta: { guest: true } },
  { path: '/admin/travels', name: 'AdminList', component: () => import('@/views/admin/AdminList.vue'), meta: { auth: true } },
  { path: '/admin/travels/new', name: 'AdminCreate', component: () => import('@/views/admin/AdminEdit.vue'), meta: { auth: true } },
  { path: '/admin/travels/:id/edit', name: 'AdminEdit', component: () => import('@/views/admin/AdminEdit.vue'), meta: { auth: true } },
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  if (to.meta.auth) {
    try {
      await checkAuth()
      next()
    } catch {
      next('/admin/login')
    }
  } else if (to.meta.guest) {
    try {
      await checkAuth()
      next('/admin/travels')
    } catch {
      next()
    }
  } else {
    next()
  }
})

export default router
