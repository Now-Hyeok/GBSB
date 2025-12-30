<template>
  <div class="min-h-screen flex flex-col">
    <!-- Hero Section -->
    <section class="bg-gradient-to-br from-primary-50 to-blue-50 dark:from-gray-900 dark:to-dark-bg py-16 md:py-24">
      <div class="container-custom">
        <div class="max-w-3xl mx-auto text-center">
          <h1 class="text-4xl md:text-5xl font-bold text-gray-900 dark:text-white mb-6">
            국내외 테크 기업들의<br />
            기술 블로그를 한 곳에서
          </h1>
          <p class="text-lg text-gray-600 dark:text-gray-300 mb-8">
            배달의민족, 카카오, 토스, 네이버 등 주요 IT 기업의<br />
            최신 기술 블로그 포스트를 큐레이션합니다
          </p>
        </div>
      </div>
    </section>

    <!-- Categories Section -->
    <section class="py-12 bg-white dark:bg-dark-bg">
      <div class="container-custom">
        <h2 class="text-2xl md:text-3xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          분야별 탐색
        </h2>
        <div class="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-8 gap-4 mb-8">
          <CategoryCard v-for="category in categories" :key="category.id" :category="category" />
        </div>
      </div>
    </section>

    <!-- Ad Banner -->
    <div class="container-custom py-4">
      <AdBanner size="large" />
    </div>

    <!-- Company Filter Tabs -->
    <section class="bg-white dark:bg-dark-card border-b border-gray-200 dark:border-dark-border sticky top-16 z-40">
      <div class="container-custom py-4">
        <div class="flex items-center gap-2 overflow-x-auto scrollbar-hide pb-2">
          <button
            @click="filterByCompany(null)"
            :class="[
              'px-4 py-2 rounded-lg font-medium whitespace-nowrap transition-all flex-shrink-0',
              selectedCompanyId === null
                ? 'bg-primary-600 text-white'
                : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
            ]"
          >
            전체
          </button>
          <button
            v-for="company in companies"
            :key="company.id"
            @click="filterByCompany(company.id)"
            :class="[
              'px-4 py-2 rounded-lg font-medium whitespace-nowrap transition-all flex items-center gap-2 flex-shrink-0',
              selectedCompanyId === company.id
                ? 'bg-primary-600 text-white'
                : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
            ]"
          >
            <img
              v-if="company.logoUrl"
              :src="company.logoUrl"
              :alt="company.nameKo"
              class="w-4 h-4 rounded object-contain"
            />
            {{ company.nameKo }}
          </button>
        </div>
      </div>
    </section>

    <!-- Posts Section -->
    <section class="flex-1 py-8">
      <div class="container-custom">
        <!-- Recent Posts Sidebar -->
        <div class="mb-8">
          <RecentPosts :limit="5" />
        </div>
        <!-- Sort Controls -->
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            {{ selectedCompanyId ? companies.find(c => c.id === selectedCompanyId)?.nameKo + ' 포스트' : '최신 포스트' }}
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
          v-else-if="postStore.isEmpty"
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
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { usePostStore } from '@/store/usePostStore'
import { useCompanyStore } from '@/store/useCompanyStore'
import { useCategoryStore } from '@/store/useCategoryStore'
import PostCard from '@/components/common/PostCard.vue'
import CategoryCard from '@/components/common/CategoryCard.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import EmptyState from '@/components/common/EmptyState.vue'
import InfiniteScroll from '@/components/common/InfiniteScroll.vue'
import AdBanner from '@/components/common/AdBanner.vue'
import RecentPosts from '@/components/common/RecentPosts.vue'

const postStore = usePostStore()
const companyStore = useCompanyStore()
const categoryStore = useCategoryStore()

const selectedCompanyId = ref<number | null>(null)
const currentSort = ref<'latest' | 'popular'>('latest')

const posts = computed(() => postStore.posts)
const companies = computed(() => companyStore.activeCompanies)
const categories = computed(() => categoryStore.categories)

const filterByCompany = async (companyId: number | null) => {
  selectedCompanyId.value = companyId
  postStore.reset()

  if (companyId) {
    await postStore.fetchPostsByCompany(companyId, {
      page: 0,
      size: 12,
      sort: currentSort.value
    })
  } else {
    await postStore.fetchPosts({
      page: 0,
      size: 12,
      sort: currentSort.value
    })
  }
}

const sortBy = async (sort: 'latest' | 'popular') => {
  if (currentSort.value === sort) return

  currentSort.value = sort
  postStore.reset()

  if (selectedCompanyId.value) {
    await postStore.fetchPostsByCompany(selectedCompanyId.value, {
      page: 0,
      size: 12,
      sort
    })
  } else {
    await postStore.fetchPosts({
      page: 0,
      size: 12,
      sort
    })
  }
}

const loadMore = async () => {
  await postStore.loadMore()
}

onMounted(async () => {
  if (companyStore.companies.length === 0) {
    await companyStore.fetchCompanies({ isActive: true })
  }

  if (categoryStore.categories.length === 0) {
    await categoryStore.fetchCategories()
  }

  postStore.reset()
  await postStore.fetchPosts({
    page: 0,
    size: 12,
    sort: 'latest'
  })
})
</script>
