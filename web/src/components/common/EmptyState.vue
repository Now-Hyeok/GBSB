<template>
  <div class="flex flex-col items-center justify-center py-16 px-4">
    <div class="text-6xl mb-4">{{ icon }}</div>
    <h3 class="text-xl font-semibold text-gray-900 dark:text-gray-100 mb-2">
      {{ title }}
    </h3>
    <p class="text-gray-600 dark:text-gray-400 text-center max-w-md">
      {{ description }}
    </p>
    <slot name="action"></slot>
  </div>
</template>

<script setup lang="ts">
interface Props {
  icon?: string
  title: string
  description: string
}

withDefaults(defineProps<Props>(), {
  icon: '📭'
})
</script>
