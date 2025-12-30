<template>
  <router-link
    :to="`/category/${category.id}`"
    class="card card-hover p-6 flex flex-col items-center text-center group"
  >
    <!-- Icon -->
    <div
      class="w-16 h-16 rounded-full flex items-center justify-center text-3xl mb-4 transition-transform group-hover:scale-110"
      :style="{ backgroundColor: category.color + '20' }"
    >
      {{ category.icon }}
    </div>

    <!-- Name -->
    <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-2">
      {{ category.name }}
    </h3>

    <!-- Description -->
    <p v-if="category.description" class="text-sm text-gray-600 dark:text-gray-400 mb-3">
      {{ category.description }}
    </p>

    <!-- Count -->
    <div class="mt-auto">
      <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300">
        {{ category.count }}개의 포스트
      </span>
    </div>
  </router-link>
</template>

<script setup lang="ts">
import type { Category } from '@/types'

interface Props {
  category: Category
}

defineProps<Props>()
</script>
