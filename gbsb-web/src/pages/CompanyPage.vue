<template>
  <div class="min-h-screen">
    <LoadingSpinner v-if="companyStore.loading && !company" />

    <div v-else-if="company" class="pb-12">
      <!-- Company Header -->
      <section class="bg-gradient-to-br from-primary-50 to-blue-50 dark:from-gray-900 dark:to-dark-bg py-12 md:py-16">
        <div class="container-custom">
          <div class="flex items-center gap-6">
            <img
              v-if="company.logoUrl"
              :src="company.logoUrl"
              :alt="company.nameKo"
              class="w-20 h-20 md:w-24 md:h-24 rounded-lg object-contain bg-white p-4 shadow-lg"
            />
            <div>
              <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-2">
                {{ company.nameKo }}
              </h1>
              <p v-if="company.description" class="text-gray-600 dark:text-gray-300 mb-4">
                {{ company.description }}
              </p>
              <a
                :href="company.blogUrl"
                target="_blank"
                rel="noopener noreferrer"
                class="inline-flex items-center gap-2 text-primary-600 dark:text-primary-400 hover:underline"
              >
                공식 블로그 방문하기
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
                </svg>
              </a>
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
              포스트 ({{ postStore.pagination.totalElements }})
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
      title="기업을 찾을 수 없습니다"
      description="존재하지 않는 기업입니다."
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
import { useCompanyStore } from '@/store/useCompanyStore'
import PostCard from '@/components/common/PostCard.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import EmptyState from '@/components/common/EmptyState.vue'
import InfiniteScroll from '@/components/common/InfiniteScroll.vue'

const route = useRoute()
const postStore = usePostStore()
const companyStore = useCompanyStore()

const currentSort = ref<'latest' | 'popular'>('latest')

const companyId = computed(() => Number(route.params.companyId))
const company = computed(() => companyStore.currentCompany)
const posts = computed(() => postStore.posts)

const sortBy = async (sort: 'latest' | 'popular') => {
  if (currentSort.value === sort) return

  currentSort.value = sort
  postStore.reset()

  await postStore.fetchPostsByCompany(companyId.value, {
    page: 0,
    size: 12,
    sort
  })
}

const loadMore = async () => {
  await postStore.loadMore()
}

onMounted(async () => {
  await companyStore.fetchCompanyById(companyId.value)

  postStore.reset()
  await postStore.fetchPostsByCompany(companyId.value, {
    page: 0,
    size: 12,
    sort: 'latest'
  })
})
</script>
