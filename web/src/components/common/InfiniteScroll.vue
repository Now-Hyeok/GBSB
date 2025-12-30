<template>
  <div ref="containerRef">
    <slot></slot>

    <div v-if="loading" class="py-8">
      <LoadingSpinner size="md" container-class="" />
    </div>

    <div v-if="!loading && !hasMore" class="text-center py-8 text-gray-500 dark:text-gray-400">
      모든 게시글을 불러왔습니다
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import LoadingSpinner from './LoadingSpinner.vue'

interface Props {
  loading: boolean
  hasMore: boolean
  distance?: number
}

const props = withDefaults(defineProps<Props>(), {
  distance: 200
})

const emit = defineEmits<{
  'load-more': []
}>()

const containerRef = ref<HTMLElement | null>(null)

const handleScroll = () => {
  if (props.loading || !props.hasMore) return

  const scrollHeight = document.documentElement.scrollHeight
  const scrollTop = document.documentElement.scrollTop
  const clientHeight = document.documentElement.clientHeight

  if (scrollHeight - scrollTop - clientHeight < props.distance) {
    emit('load-more')
  }
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>
