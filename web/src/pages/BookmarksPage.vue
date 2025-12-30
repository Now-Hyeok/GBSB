<template>
  <div class="min-h-screen py-12">
    <div class="container-custom max-w-6xl">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4">
          내 북마크
        </h1>
        <div class="flex items-center justify-between">
          <p class="text-gray-600 dark:text-gray-400">
            저장한 포스트 {{ bookmarkStore.bookmarkCount }}개
          </p>
          <button
            v-if="bookmarkStore.bookmarkCount > 0"
            @click="clearAllBookmarks"
            class="text-sm text-red-600 dark:text-red-400 hover:underline"
          >
            전체 삭제
          </button>
        </div>
      </div>

      <!-- Empty State -->
      <EmptyState
        v-if="bookmarkStore.bookmarkCount === 0"
        icon="📚"
        title="저장된 북마크가 없습니다"
        description="마음에 드는 포스트를 북마크하여 나중에 다시 읽어보세요."
      >
        <template #action>
          <router-link to="/" class="btn btn-primary mt-4">
            포스트 둘러보기
          </router-link>
        </template>
      </EmptyState>

      <!-- Bookmarked Posts Grid -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <PostCard
          v-for="post in bookmarkedPosts"
          :key="post.id"
          :post="post"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useBookmarkStore } from '@/store/useBookmarkStore'
import PostCard from '@/components/common/PostCard.vue'
import EmptyState from '@/components/common/EmptyState.vue'

const bookmarkStore = useBookmarkStore()

const bookmarkedPosts = computed(() => bookmarkStore.bookmarkedPosts)

const clearAllBookmarks = () => {
  if (confirm('모든 북마크를 삭제하시겠습니까?')) {
    bookmarkStore.clearBookmarks()
  }
}
</script>
