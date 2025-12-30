<template>
  <div :class="['flex justify-center items-center', containerClass]">
    <div :class="['loading-spinner', sizeClass]" role="status" aria-label="Loading">
      <span class="sr-only">로딩 중...</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  size?: 'sm' | 'md' | 'lg'
  containerClass?: string
}

const props = withDefaults(defineProps<Props>(), {
  size: 'md',
  containerClass: 'py-8'
})

const sizeClass = computed(() => {
  switch (props.size) {
    case 'sm':
      return 'w-6 h-6'
    case 'lg':
      return 'w-16 h-16'
    default:
      return 'w-10 h-10'
  }
})
</script>
