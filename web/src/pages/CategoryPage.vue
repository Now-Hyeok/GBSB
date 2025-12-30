<template>
  <div class="min-h-screen">
    <LoadingSpinner v-if="categoryStore.loading && !category" />

    <div v-else-if="category" class="pb-12">
      <!-- Category Header -->
      <section class="bg-gradient-to-br from-primary-50 to-blue-50 dark:from-gray-900 dark:to-dark-bg py-12 md:py-16">
        <div class="container-custom">
          <div class="flex items-center gap-6">
            <div
              class="w-20 h-20 md:w-24 md:h-24 rounded-2xl flex items-center justify-center text-5xl shadow-lg"
              :style="{ backgroundColor: category.color + '20' }"
            >
              {{ category.icon }}
            </div>
            <div>
              <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-2">
                {{ category.name }}
              </h1>
              <p v-if="category.description" class="text-gray-600 dark:text-gray-300 mb-2">
                {{ category.description }}
              </p>
              <p class="text-sm text-gray-500 dark:text-gray-400">
                {{ postStore.pagination.totalElements }}개의 포스트
              </p>
            </div>
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

          <!-- Ad Banner -->
          <AdBanner size="horizontal" class="mb-8" />

          <!-- Loading State -->
          <LoadingSpinner v-if="postStore.loading && posts.length === 0" />

          <!-- Empty State -->
          <EmptyState
            v-else-if="posts.length === 0"
            title="포스트가 없습니다"
            description="아직 등록된 포스트가 없습니다."
          />

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

    <!-- Error State -->
    <EmptyState
      v-else
      icon="❌"
      title="카테고리를 찾을 수 없습니다"
      description="존재하지 않는 카테고리입니다."
    >
      <template #action>
        <router-link to="/" class="btn btn-primary mt-4">
          홈으로 돌아가기
        </router-link>
      </template>
    </EmptyState>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { usePostStore } from '@/store/usePostStore'
import { useCategoryStore } from '@/store/useCategoryStore'
import PostCard from '@/components/common/PostCard.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import EmptyState from '@/components/common/EmptyState.vue'
import InfiniteScroll from '@/components/common/InfiniteScroll.vue'
import AdBanner from '@/components/common/AdBanner.vue'
import { api } from '@/services/api'

const route = useRoute()
const postStore = usePostStore()
const categoryStore = useCategoryStore()

const currentSort = ref<'latest' | 'popular'>('latest')

const categoryId = computed(() => Number(route.params.categoryId))
const category = computed(() => categoryStore.currentCategory)
const posts = computed(() => postStore.posts)

const sortBy = async (sort: 'latest' | 'popular') => {
  if (currentSort.value === sort) return

  currentSort.value = sort
  postStore.reset()

  const response = await api.getPostsByCategory(categoryId.value, {
    page: 0,
    size: 12,
    sort
  })
  postStore.posts = response.content
  postStore.pagination = {
    page: response.page,
    size: response.size,
    totalElements: response.totalElements,
    totalPages: response.totalPages,
    last: response.last,
    first: response.first
  }
}

const loadMore = async () => {
  await postStore.loadMore()
}

onMounted(async () => {
  await categoryStore.fetchCategoryById(categoryId.value)

  postStore.reset()
  const response = await api.getPostsByCategory(categoryId.value, {
    page: 0,
    size: 12,
    sort: 'latest'
  })
  postStore.posts = response.content
  postStore.pagination = {
    page: response.page,
    size: response.size,
    totalElements: response.totalElements,
    totalPages: response.totalPages,
    last: response.last,
    first: response.first
  }
})
</script>
