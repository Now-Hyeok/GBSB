<template>
  <div class="min-h-screen">
    <LoadingSpinner v-if="postStore.loading && !post" />

    <article v-else-if="post" class="py-12">
      <div class="container-custom max-w-4xl">
        <!-- Header -->
        <header class="mb-8">
          <!-- Company Badge -->
          <div class="mb-4">
            <CompanyBadge v-if="post.company" :company="post.company" />
          </div>

          <!-- Title -->
          <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4 leading-tight">
            {{ post.title }}
          </h1>

          <!-- Meta Info -->
          <div class="flex flex-wrap items-center gap-4 text-sm text-gray-600 dark:text-gray-400">
            <time :datetime="post.publishedAt" class="flex items-center gap-2">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              {{ formatDate(post.publishedAt) }}
            </time>

            <span v-if="post.viewCount" class="flex items-center gap-2">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
              </svg>
              {{ post.viewCount.toLocaleString() }}
            </span>
          </div>

          <!-- Tags -->
          <div v-if="post.tags && post.tags.length > 0" class="flex flex-wrap gap-2 mt-4">
            <TagChip v-for="tag in post.tags" :key="tag.id" :tag="tag" />
          </div>

          <!-- Share Buttons -->
          <div class="mt-6 pt-6 border-t border-gray-200 dark:border-gray-700">
            <ShareButtons :title="post.title" :url="currentUrl" />
          </div>
        </header>

        <!-- Summary -->
        <div v-if="post.summary" class="mb-8 p-6 bg-gray-50 dark:bg-dark-card rounded-lg border border-gray-200 dark:border-dark-border">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">요약</h2>
          <p class="text-gray-700 dark:text-gray-300 leading-relaxed">
            {{ post.summary }}
          </p>
        </div>

        <!-- Ad Banner -->
        <div class="mb-8">
          <AdBanner size="horizontal" />
        </div>

        <!-- Original Post Link -->
        <div class="mb-12 p-6 bg-primary-50 dark:bg-primary-900/20 rounded-lg border border-primary-200 dark:border-primary-800">
          <div class="flex items-start justify-between gap-4">
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white mb-2">
                원문 보러가기
              </h3>
              <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">
                전체 내용은 {{ post.company?.nameKo }} 공식 블로그에서 확인하실 수 있습니다.
              </p>
              <a
                :href="post.url"
                target="_blank"
                rel="noopener noreferrer"
                class="btn btn-primary"
              >
                <span>{{ post.company?.nameKo }} 블로그에서 읽기</span>
                <svg class="w-5 h-5 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
                </svg>
              </a>
            </div>
          </div>
        </div>

        <!-- Related Posts -->
        <section v-if="relatedPosts.length > 0" class="mt-16">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">
            관련 포스트
          </h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <PostCard v-for="relatedPost in relatedPosts" :key="relatedPost.id" :post="relatedPost" />
          </div>
        </section>
      </div>
    </article>

    <!-- Error State -->
    <EmptyState
      v-else
      icon="❌"
      title="포스트를 찾을 수 없습니다"
      description="존재하지 않는 포스트입니다."
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
import { useHistoryStore } from '@/store/useHistoryStore'
import type { Post } from '@/types'
import PostCard from '@/components/common/PostCard.vue'
import CompanyBadge from '@/components/common/CompanyBadge.vue'
import TagChip from '@/components/common/TagChip.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import EmptyState from '@/components/common/EmptyState.vue'
import AdBanner from '@/components/common/AdBanner.vue'
import ShareButtons from '@/components/common/ShareButtons.vue'

const route = useRoute()
const postStore = usePostStore()
const historyStore = useHistoryStore()

const relatedPosts = ref<Post[]>([])

const postId = computed(() => Number(route.params.postId))
const post = computed(() => postStore.currentPost)
const currentUrl = computed(() => window.location.href)

const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const fetchRelatedPosts = async () => {
  if (!post.value) return

  // Fetch posts from the same company
  if (post.value.companyId) {
    await postStore.fetchPostsByCompany(post.value.companyId, { page: 0, size: 4 })
    relatedPosts.value = postStore.posts
      .filter(p => p.id !== post.value?.id)
      .slice(0, 2)
  }
}

onMounted(async () => {
  await postStore.fetchPostById(postId.value)

  // Add to history
  if (post.value) {
    historyStore.addToHistory(post.value)
  }

  await fetchRelatedPosts()
})
</script>
