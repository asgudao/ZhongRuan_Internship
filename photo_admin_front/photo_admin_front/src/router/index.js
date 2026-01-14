import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue')
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('@/views/HomeView.vue')
    },
    {
      path: '/admin/page',
      name: 'adminPage',
      component: () => import('@/views/admin/AdminPageView.vue')
    },
    {
      path: '/admin/add',
      name: 'adminAdd',
      component: () => import('@/views/admin/AdminAdd.vue')
    },
    {
      path: '/admin/edit',
      name: 'adminEdit',
      component: () => import('@/views/admin/AdminEdit.vue')
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('@/views/AccountView.vue')
    },
    {
      path: '/user/page',
      name: 'userPage',
      component: () => import('@/views/user/UserPageView.vue')
    },
    {
      path: '/user/add',
      name: 'userAdd',
      component: () => import('@/views/user/UserAdd.vue')
    },
    {
      path: '/user/edit',
      name: 'userEdit',
      component: () => import('@/views/user/UserEdit.vue')
    },
    {
      path: '/question/page',
      name: 'questionPage',
      component: () => import('@/views/question/QuestionPageView.vue')
    },
        {
      path: '/community/page',
      name: 'communityPage',
      component: () => import('@/views/community/CommunityPageView.vue')
    },
  ]
})

export default router
