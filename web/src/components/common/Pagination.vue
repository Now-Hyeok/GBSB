<template>
  <nav class="flex items-center justify-center gap-2 mt-8" aria-label="Pagination">
    <!-- Previous button -->
    <button
      :disabled="currentPage === 0"
      @click="goToPage(currentPage - 1)"
      class="btn btn-secondary disabled:opacity-50 disabled:cursor-not-allowed"
    >
      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
      </svg>
      이전
    </button>

    <!-- Page numbers -->
    <div class="flex gap-1">
      <button
        v-for="page in visiblePages"
        :key="page"
        @click="goToPage(page)"
        :class="[
          'min-w-[40px] h-10 rounded-lg font-medium transition-colors',
          page === currentPage
            ? 'bg-primary-600 text-white'
            : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
        ]"
      >
        {{ page + 1 }}
      </button>
    </div>

    <!-- Next button -->
    <button
      :disabled="currentPage >= totalPages - 1"
      @click="goToPage(currentPage + 1)"
      class="btn btn-secondary disabled:opacity-50 disabled:cursor-not-allowed"
    >
      다음
      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
      </svg>
    </button>
  </nav>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  currentPage: number
  totalPages: number
  maxVisible?: number
}

const props = withDefaults(defineProps<Props>(), {
  maxVisible: 5
})

const emit = defineEmits<{
  'page-change': [page: number]
}>()

const visiblePages = computed(() => {
  const pages: number[] = []
  const half = Math.floor(props.maxVisible / 2)

  let start = Math.max(0, props.currentPage - half)
  let end = Math.min(props.totalPages - 1, start + props.maxVisible - 1)

  if (end - start < props.maxVisible - 1) {
    start = Math.max(0, end - props.maxVisible + 1)
  }

  for (let i = start; i <= end; i++) {
    pages.push(i)
  }

  return pages
})

const goToPage = (page: number) => {
  if (page >= 0 && page < props.totalPages && page !== props.currentPage) {
    emit('page-change', page)
  }
}
</script>
