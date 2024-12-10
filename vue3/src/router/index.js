import { createRouter, createWebHistory } from 'vue-router'
import Manager from '@/views/BackManager.vue'
import Home from '@/views/manager/ManagerHome.vue'
import Admin from '@/views/manager/admin/ManagerAdmin.vue'
import InvalidPage from '@/views/global/InvalidPage404.vue'
import Shower from '@/views/FrontShower.vue'
import Login from '@/views/login/AccountLogin.vue'
import Register from '@/views/login/AccountRegister.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/manager/home' },
    {
      path: '/manager',
      component: Manager,
      children: [
        { path: '/manager/home', meta: { name: '系统首页' }, component: Home },
        { path: '/manager/admin', meta: { name: '管理员管理' }, component: Admin }
      ]
    },
    { path: '/404', component: InvalidPage },
    { path: '/:pathMatch(.*)', redirect: '/404' },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/front', component: Shower }
  ]
})

router.beforeEach((to, from, next) => {
  const token = JSON.parse(localStorage.getItem('pinia-userInfo'))?.AccountInfo?.username
  if (token === undefined && to.path.includes('/manager')) next('/login')
  else next()
})

export default router
