<template>
  <router-link
    :to="`/company/${company.id}`"
    class="inline-flex items-center gap-2 hover:opacity-80 transition-opacity"
  >
    <img
      v-if="company.logoUrl"
      :src="company.logoUrl"
      :alt="company.nameKo"
      class="w-5 h-5 rounded object-contain"
      @error="onImageError"
    />
    <span class="text-sm font-medium text-gray-700 dark:text-gray-300">
      {{ company.nameKo || company.name }}
    </span>
  </router-link>
</template>

<script setup lang="ts">
import type { Company } from '@/types'

interface Props {
  company: Company
}

defineProps<Props>()

const onImageError = (event: Event) => {
  const img = event.target as HTMLImageElement
  img.style.display = 'none'
}
</script>
