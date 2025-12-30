<template>
  <div class="min-h-screen">
    <!-- Tag Header -->
    <section class="bg-gradient-to-br from-primary-50 to-blue-50 dark:from-gray-900 dark:to-dark-bg py-12 md:py-16">
      <div class="container-custom">
        <div class="max-w-3xl">
          <div class="inline-flex items-center gap-2 px-4 py-2 bg-white dark:bg-dark-card rounded-full mb-4">
            <span class="text-2xl">#</span>
            <span class="text-xl font-bold">{{ tagSlug }}</span>
          </div>
          <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4">
            {{ tagSlug }} 포스트
          </h1>
          <p class="text-gray-600 dark:text-gray-300">
            {{ postStore.pagination.totalElements }}개의 포스트
          </p>
        </div>
      </div>
    </section>

    <!-- Posts Section -->
    <section class="py-8">
      <div class="container-custom">
        <!-- Sort Controls -->
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            포스트
          </h2>
          <div class="flex gap-2">
            <button
              @click="sortBy('latest')"
              :class="[
                'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                currentSort === 'latest'
                  ? 'bg-primary-600 text-white'
                  : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
              ]"
            >
              최신순
            </button>
            <button
              @click="sortBy('popular')"
              :class="[
                'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                currentSort === 'popular'
                  ? 'bg-primary-600 text-white'
                  : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
              ]"
            >
              인기순
            </button>
          </div>
        </div>

        <!-- Loading State -->
        <LoadingSpinner v-if="postStore.loading && posts.length === 0" />

        <!-- Empty State -->
        <EmptyState
          v-else-if="posts.length === 0"
          title="포스트가 없습니다"
          description="이 태그와 관련된 포스트가 없습니다."
        >
          <template #action>
            <router-link to="/" class="btn btn-primary mt-4">
              홈으로 돌아가기
            </router-link>
          </template>
        </EmptyState>

        <!-- Posts Grid -->
        <InfiniteScroll
          v-else
          :loading="postStore.loading"
          :has-more="postStore.hasMore"
          @load-more="loadMore"
        >
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <PostCard v-for="post in posts" :key="post.id" :post="post" />
          </div>
        </InfiniteScroll>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { usePostStore } from '@/store/usePostStore'
import PostCard from '@/components/common/PostCard.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import EmptyState from '@/components/common/EmptyState.vue'
import InfiniteScroll from '@/components/common/InfiniteScroll.vue'

const route = useRoute()
const postStore = usePostStore()

const currentSort = ref<'latest' | 'popular'>('latest')

const tagSlug = computed(() => String(route.params.tagSlug))
const posts = computed(() => postStore.posts)

const sortBy = async (sort: 'latest' | 'popular') => {
  if (currentSort.value === sort) return

  currentSort.value = sort
  postStore.reset()

  await postStore.fetchPostsByTag(tagSlug.value, {
    page: 0,
    size: 12,
    sort
  })
}

const loadMore = async () => {
  await postStore.loadMore()
}

onMounted(async () => {
  postStore.reset()
  await postStore.fetchPostsByTag(tagSlug.value, {
    page: 0,
    size: 12,
    sort: 'latest'
  })
})
</script>
