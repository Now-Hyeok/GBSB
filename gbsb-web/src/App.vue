<template>
  <div id="app" class="min-h-screen flex flex-col bg-white dark:bg-dark-bg">
    <TheHeader />
    <main class="flex-1">
      <router-view v-slot="{ Component }">
        <transition name="page" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    <TheFooter />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import TheHeader from '@/components/layout/TheHeader.vue'
import TheFooter from '@/components/layout/TheFooter.vue'
import { useHead } from '@vueuse/head'

// SEO
useHead({
  title: '개발새발 - Tech Blog Hub',
  meta: [
    {
      name: 'description',
      content: '국내외 테크 기업들의 기술 블로그를 한 곳에서 - 배달의민족, 카카오, 토스, 네이버, 라인, 쿠팡, 당근마켓 등'
    },
    {
      name: 'keywords',
      content: '기술블로그, 개발블로그, 테크블로그, 배달의민족, 카카오, 토스, 네이버, 라인, 쿠팡, 당근마켓'
    },
    {
      property: 'og:title',
      content: '개발새발 - Tech Blog Hub'
    },
    {
      property: 'og:description',
      content: '국내외 테크 기업들의 기술 블로그를 한 곳에서'
    },
    {
      property: 'og:type',
      content: 'website'
    }
  ]
})

onMounted(() => {
  // Initialize any global app logic here
  console.log('개발새발 앱이 시작되었습니다.')
})
</script>

<style>
/* Page transition animations */
.page-enter-active,
.page-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.page-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
