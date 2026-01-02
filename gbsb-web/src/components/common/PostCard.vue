<template>
  <article class="card card-hover p-6 h-full flex flex-col relative group">
    <!-- Bookmark Button -->
    <button
      @click.prevent="toggleBookmark"
      class="absolute top-4 right-4 p-2 rounded-lg opacity-0 group-hover:opacity-100 transition-opacity bg-white dark:bg-dark-card shadow-md hover:shadow-lg z-10"
      :class="isBookmarked ? 'text-yellow-500' : 'text-gray-400 hover:text-yellow-500'"
      :title="isBookmarked ? '북마크 제거' : '북마크 추가'"
    >
      <svg
        class="w-5 h-5"
        :fill="isBookmarked ? 'currentColor' : 'none'"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 5a2 2 0 012-2h10a2 2 0 012 2v16l-7-3.5L5 21V5z" />
      </svg>
    </button>

    <!-- Company Badge -->
    <div class="mb-3">
      <CompanyBadge v-if="post.company" :company="post.company" />
    </div>

    <!-- Title -->
    <h3 class="text-xl font-bold mb-3 line-clamp-2 text-balance">
      <router-link
        :to="`/post/${post.id}`"
        class="hover:text-primary-600 dark:hover:text-primary-400"
      >
        {{ post.title }}
      </router-link>
    </h3>

    <!-- Summary -->
    <p
      v-if="post.summary"
      class="text-gray-600 dark:text-gray-400 text-sm mb-4 line-clamp-3 flex-grow"
    >
      {{ post.summary }}
    </p>

    <!-- Tags -->
    <div v-if="post.tags && post.tags.length > 0" class="flex flex-wrap gap-2 mb-4">
      <TagChip v-for="tag in post.tags.slice(0, 3)" :key="tag.id" :tag="tag" size="sm" />
    </div>

    <!-- Footer -->
    <div class="flex items-center justify-between text-sm text-gray-500 dark:text-gray-400 mt-auto pt-4 border-t border-gray-100 dark:border-gray-800">
      <time :datetime="post.publishedAt" class="flex items-center gap-1">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
        {{ formatDate(post.publishedAt) }}
      </time>

      <a
        :href="post.url"
        target="_blank"
        rel="noopener noreferrer"
        class="flex items-center gap-1 text-primary-600 dark:text-primary-400 hover:underline"
        @click.stop
      >
        원문 보기
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
        </svg>
      </a>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Post } from '@/types'
import { useBookmarkStore } from '@/store/useBookmarkStore'
import CompanyBadge from './CompanyBadge.vue'
import TagChip from './TagChip.vue'

interface Props {
  post: Post
}

const props = defineProps<Props>()
const bookmarkStore = useBookmarkStore()

const isBookmarked = computed(() => bookmarkStore.isBookmarked(props.post.id))

const toggleBookmark = () => {
  bookmarkStore.toggleBookmark(props.post)
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  const now = new Date()
  const diffInMs = now.getTime() - date.getTime()
  const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24))

  if (diffInDays === 0) return '오늘'
  if (diffInDays === 1) return '어제'
  if (diffInDays < 7) return `${diffInDays}일 전`
  if (diffInDays < 30) return `${Math.floor(diffInDays / 7)}주 전`
  if (diffInDays < 365) return `${Math.floor(diffInDays / 30)}개월 전`

  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}
</script>
