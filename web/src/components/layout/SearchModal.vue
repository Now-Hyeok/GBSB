<template>
  <teleport to="body">
    <div
      class="fixed inset-0 z-50 flex items-start justify-center pt-20 px-4"
      @click.self="close"
    >
      <!-- Backdrop -->
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm"></div>

      <!-- Modal -->
      <div class="relative w-full max-w-2xl bg-white dark:bg-dark-card rounded-lg shadow-2xl">
        <!-- Search Input -->
        <div class="p-4 border-b border-gray-200 dark:border-dark-border">
          <div class="relative">
            <svg
              class="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
            <input
              ref="searchInputRef"
              v-model="searchQuery"
              type="text"
              placeholder="검색어를 입력하세요..."
              class="w-full pl-10 pr-4 py-3 bg-gray-50 dark:bg-dark-bg border-0 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
              @keyup.enter="performSearch"
              @keyup.esc="close"
            />
          </div>
        </div>

        <!-- Results -->
        <div class="max-h-96 overflow-y-auto p-4">
          <LoadingSpinner v-if="loading" size="md" container-class="py-8" />

          <div v-else-if="searchQuery && results.length === 0" class="text-center py-8 text-gray-500 dark:text-gray-400">
            검색 결과가 없습니다
          </div>

          <div v-else-if="results.length > 0" class="space-y-2">
            <router-link
              v-for="post in results"
              :key="post.id"
              :to="`/post/${post.id}`"
              class="block p-4 rounded-lg hover:bg-gray-50 dark:hover:bg-dark-bg transition-colors"
              @click="close"
            >
              <div class="flex items-start gap-3">
                <img
                  v-if="post.company?.logoUrl"
                  :src="post.company.logoUrl"
                  :alt="post.company.nameKo"
                  class="w-8 h-8 rounded object-contain flex-shrink-0"
                />
                <div class="flex-1 min-w-0">
                  <h4 class="font-medium text-gray-900 dark:text-white mb-1 line-clamp-1">
                    {{ post.title }}
                  </h4>
                  <p class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2">
                    {{ post.summary }}
                  </p>
                  <div class="flex items-center gap-2 mt-2">
                    <span class="text-xs text-gray-500">{{ post.company?.nameKo }}</span>
                    <span class="text-xs text-gray-400">•</span>
                    <span class="text-xs text-gray-500">{{ formatDate(post.publishedAt) }}</span>
                  </div>
                </div>
              </div>
            </router-link>
          </div>

          <div v-else class="text-center py-12 text-gray-500 dark:text-gray-400">
            <svg class="w-16 h-16 mx-auto mb-4 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
            <p>검색어를 입력해주세요</p>
          </div>
        </div>

        <!-- Footer -->
        <div class="p-3 border-t border-gray-200 dark:border-dark-border flex items-center justify-between text-xs text-gray-500 dark:text-gray-400">
          <div class="flex gap-4">
            <kbd class="px-2 py-1 bg-gray-100 dark:bg-dark-bg rounded">Enter</kbd>
            <span>검색</span>
          </div>
          <div class="flex gap-4">
            <kbd class="px-2 py-1 bg-gray-100 dark:bg-dark-bg rounded">ESC</kbd>
            <span>닫기</span>
          </div>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { usePostStore } from '@/store/usePostStore'
import type { Post } from '@/types'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const emit = defineEmits<{
  close: []
}>()

const router = useRouter()
const postStore = usePostStore()

const searchInputRef = ref<HTMLInputElement | null>(null)
const searchQuery = ref('')
const results = ref<Post[]>([])
const loading = ref(false)

const performSearch = async () => {
  if (!searchQuery.value.trim()) return

  loading.value = true
  try {
    await postStore.searchPosts(searchQuery.value)
    results.value = postStore.posts
  } catch (error) {
    console.error('Search error:', error)
  } finally {
    loading.value = false
  }
}

const close = () => {
  emit('close')
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

// Debounced search
let searchTimeout: NodeJS.Timeout
watch(searchQuery, () => {
  clearTimeout(searchTimeout)
  searchTimeout = setTimeout(() => {
    if (searchQuery.value.trim()) {
      performSearch()
    } else {
      results.value = []
    }
  }, 300)
})

onMounted(() => {
  searchInputRef.value?.focus()
})
</script>
