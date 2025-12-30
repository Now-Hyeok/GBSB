<template>
  <div v-if="recentPosts.length > 0" class="card p-6">
    <div class="flex items-center justify-between mb-4">
      <h3 class="text-lg font-bold text-gray-900 dark:text-white">
        최근 본 포스트
      </h3>
      <button
        @click="clearHistory"
        class="text-xs text-gray-500 hover:text-red-500 dark:text-gray-400 dark:hover:text-red-400"
      >
        전체 삭제
      </button>
    </div>

    <div class="space-y-3">
      <router-link
        v-for="post in recentPosts.slice(0, limit)"
        :key="post.id"
        :to="`/post/${post.id}`"
        class="block group"
      >
        <div class="flex items-start gap-3 p-3 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
          <img
            v-if="post.company?.logoUrl"
            :src="post.company.logoUrl"
            :alt="post.company.nameKo"
            class="w-8 h-8 rounded object-contain flex-shrink-0 mt-1"
          />
          <div class="flex-1 min-w-0">
            <h4 class="text-sm font-medium text-gray-900 dark:text-white line-clamp-2 group-hover:text-primary-600 dark:group-hover:text-primary-400 mb-1">
              {{ post.title }}
            </h4>
            <div class="flex items-center gap-2 text-xs text-gray-500 dark:text-gray-400">
              <span>{{ post.company?.nameKo }}</span>
              <span>•</span>
              <time>{{ formatDate(post.publishedAt) }}</time>
            </div>
          </div>
        </div>
      </router-link>
    </div>

    <router-link
      v-if="recentPosts.length > limit"
      to="/history"
      class="block mt-4 text-center text-sm text-primary-600 dark:text-primary-400 hover:underline"
    >
      전체 보기 ({{ recentPosts.length }})
    </router-link>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useHistoryStore } from '@/store/useHistoryStore'

interface Props {
  limit?: number
}

const props = withDefaults(defineProps<Props>(), {
  limit: 5
})

const historyStore = useHistoryStore()

const recentPosts = computed(() => historyStore.recentPosts)

const clearHistory = () => {
  if (confirm('최근 본 포스트 기록을 모두 삭제하시겠습니까?')) {
    historyStore.clearHistory()
  }
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  const now = new Date()
  const diffInMs = now.getTime() - date.getTime()
  const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24))

  if (diffInDays === 0) return '오늘'
  if (diffInDays === 1) return '어제'
  if (diffInDays < 7) return `${diffInDays}일 전`

  return date.toLocaleDateString('ko-KR', {
    month: 'short',
    day: 'numeric'
  })
}
</script>
