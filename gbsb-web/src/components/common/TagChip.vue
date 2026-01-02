<template>
  <router-link
    :to="`/tag/${tag.slug}`"
    :class="[
      'tag-chip',
      sizeClasses,
      clickable && 'hover:bg-primary-100 dark:hover:bg-primary-900/30'
    ]"
  >
    <span>#{{ tag.name }}</span>
    <span v-if="showCount && tag.count" class="text-xs opacity-70 ml-1">
      {{ tag.count }}
    </span>
  </router-link>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Tag } from '@/types'

interface Props {
  tag: Tag
  size?: 'sm' | 'md' | 'lg'
  showCount?: boolean
  clickable?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  size: 'md',
  showCount: false,
  clickable: true
})

const sizeClasses = computed(() => {
  switch (props.size) {
    case 'sm':
      return 'text-xs px-2 py-0.5'
    case 'lg':
      return 'text-base px-4 py-2'
    default:
      return 'text-sm px-3 py-1'
  }
})
</script>
