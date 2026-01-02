import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/pages/HomePage.vue'),
    meta: {
      title: '개발새발 - Tech Blog Hub'
    }
  },
  {
    path: '/company/:companyId',
    name: 'Company',
    component: () => import('@/pages/CompanyPage.vue'),
    meta: {
      title: 'Company Posts'
    }
  },
  {
    path: '/post/:postId',
    name: 'Post',
    component: () => import('@/pages/PostDetailPage.vue'),
    meta: {
      title: 'Post Detail'
    }
  },
  {
    path: '/tag/:tagSlug',
    name: 'Tag',
    component: () => import('@/pages/TagPage.vue'),
    meta: {
      title: 'Posts by Tag'
    }
  },
  {
    path: '/category/:categoryId',
    name: 'Category',
    component: () => import('@/pages/CategoryPage.vue'),
    meta: {
      title: 'Posts by Category'
    }
  },
  {
    path: '/bookmarks',
    name: 'Bookmarks',
    component: () => import('@/pages/BookmarksPage.vue'),
    meta: {
      title: '내 북마크'
    }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/pages/AboutPage.vue'),
    meta: {
      title: 'About 개발새발'
    }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/pages/NotFoundPage.vue'),
    meta: {
      title: '404 - Page Not Found'
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// Navigation guards
router.beforeEach((to, from, next) => {
  // Update document title
  const baseTitle = '개발새발 - Tech Blog Hub'
  document.title = to.meta.title ? `${to.meta.title} | ${baseTitle}` : baseTitle

  next()
})

export default router
