import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import ViewAllPage from '@/views/ViewAllPage.vue'
import InfluencerApplicationPage from '@/views/InfluencerApplicationPage.vue'
import PersonalCenterPage from '@/views/PersonalCenterPage.vue'
import ContentManagementPage from '@/views/ContentManagementPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path: '/view-all',
      name: 'view-all',
      component: ViewAllPage,
    },
    {
      path: '/influencer-application',
      name: 'influencer-application',
      component: InfluencerApplicationPage,
    },
    {
      path: '/personal-center',
      name: 'personal-center',
      component: PersonalCenterPage,
    },
    {
      path: '/content-management',
      name: 'content-management',
      component: ContentManagementPage,
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
})

export default router

