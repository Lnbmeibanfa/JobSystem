import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/manager/home' },
    {
      path: '/manager',
      redirect: '/manager/home',
      component: () => import('@/views/BackManager.vue'),
      children: [
        {
          path: 'home',
          meta: { name: '系统首页' },
          component: () => import('@/views/manager/ManagerHome.vue')
        },
        {
          path: 'admin',
          meta: { name: '管理员管理' },
          component: () => import('@/views/manager/ManagerAdmin.vue')
        },
        {
          path: 'self',
          meta: { name: '个人中心' },
          component: () => import('@/views/manager/ManagerSelf.vue')
        },
        {
          path: 'password',
          meta: { name: '修改密码' },
          component: () => import('@/views/manager/ManagerPassword.vue')
        },
        {
          path: 'notice',
          meta: { name: '系统公告' },
          component: () => import('@/views/manager/ManagerNotice.vue')
        },
        {
          path: 'industry',
          meta: { name: '行业信息' },
          component: () => import('@/views/manager/ManagerIndustry.vue')
        },
        {
          path: 'employ',
          meta: { name: '企业信息' },
          component: () => import('@/views/manager/ManagerEmploy.vue')
        },
        {
          path: 'user',
          meta: { name: '用户信息' },
          component: () => import('@/views/manager/ManagerUser.vue')
        },
        {
          path: 'position',
          meta: { name: '职位信息' },
          component: () => import('@/views/manager/ManagerPosition.vue')
        },
        {
          path: 'eSelf',
          meta: { name: '企业信息' },
          component: () => import('@/views/employ/EmploySelf.vue')
        },
        {
          path: 'advertise',
          meta: { name: '广告信息' },
          component: () => import('@/views/manager/ManagerAdvertise.vue')
        },
        {
          path: 'submit',
          meta: { name: '投递信息' },
          component: () => import('@/views/manager/ManagerSubmit.vue')
        }
      ]
    },
    { path: '/404', component: () => import('@/views/global/InvalidPage404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' },
    { path: '/login', component: () => import('@/views/login/AccountLogin.vue') },
    { path: '/register', component: () => import('@/views/login/AccountRegister.vue') },
    { path: '/resumePreview', component: () => import('@/views/global/ResumePreview.vue') },
    {
      path: '/front',
      redirect: '/front/home',
      component: () => import('@/views/FrontShower.vue'),
      children: [
        { path: 'user', component: () => import('@/views/front/FrontUser.vue') },
        { path: 'home', component: () => import('@/views/front/FrontHome.vue') },
        {
          path: 'position/:id',
          component: () => import('@/views/front/FrontPosition.vue'),
          name: 'FrontPosition'
        },
        { path: 'collect', component: () => import('@/views/front/FrontCollect.vue') },
        { path: 'resume', component: () => import('@/views/front/FrontResume.vue') },
        { path: 'resumeEdit', component: () => import('@/views/front/FrontResumeEdit.vue') },
        { path: 'submit', component: () => import('@/views/front/FrontSubmit.vue') },
        { path: 'employAll', component: () => import('@/views/front/FrontEmploy.vue') }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const token = JSON.parse(localStorage.getItem('pinia-userInfo'))?.AccountInfo?.username
  const role = JSON.parse(localStorage.getItem('pinia-userInfo'))?.AccountInfo?.role
  if (token === undefined && to.path.includes('/manager')) next('/login')
  if (role === 'USER' && to.path.includes('/manager')) next('/front')
  else next()
})

export default router
