<template>
  <div :class="containerClasses">
    <!-- Ad container for Google AdSense -->
    <div
      v-if="!isProduction"
      class="bg-gray-100 dark:bg-gray-800 border-2 border-dashed border-gray-300 dark:border-gray-600 rounded-lg flex items-center justify-center"
      :style="{ height: heightPx }"
    >
      <div class="text-center px-4">
        <p class="text-sm font-medium text-gray-500 dark:text-gray-400 mb-1">
          광고 영역 ({{ size }})
        </p>
        <p class="text-xs text-gray-400 dark:text-gray-500">
          프로덕션에서 Google AdSense 표시
        </p>
      </div>
    </div>

    <!-- Google AdSense script will be injected here in production -->
    <ins
      v-else
      class="adsbygoogle"
      :style="{ display: 'block', height: heightPx }"
      :data-ad-client="adClient"
      :data-ad-slot="adSlot"
      :data-ad-format="adFormat"
      :data-full-width-responsive="fullWidthResponsive"
    ></ins>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'

interface Props {
  size?: 'horizontal' | 'vertical' | 'square' | 'large'
  adSlot?: string
  adClient?: string
  className?: string
}

const props = withDefaults(defineProps<Props>(), {
  size: 'horizontal',
  adClient: 'ca-pub-XXXXXXXXXXXXXXXX', // Replace with actual AdSense client ID
  className: ''
})

const isProduction = computed(() => import.meta.env.PROD)

const heightPx = computed(() => {
  switch (props.size) {
    case 'horizontal':
      return '90px'
    case 'vertical':
      return '600px'
    case 'square':
      return '250px'
    case 'large':
      return '280px'
    default:
      return '90px'
  }
})

const adFormat = computed(() => {
  switch (props.size) {
    case 'horizontal':
      return 'horizontal'
    case 'vertical':
      return 'vertical'
    case 'square':
      return 'rectangle'
    case 'large':
      return 'auto'
    default:
      return 'auto'
  }
})

const fullWidthResponsive = computed(() => {
  return props.size === 'horizontal' || props.size === 'large' ? 'true' : 'false'
})

const containerClasses = computed(() => {
  return ['w-full', props.className].filter(Boolean).join(' ')
})

onMounted(() => {
  // Initialize AdSense in production
  if (isProduction.value) {
    try {
      // @ts-ignore
      (window.adsbygoogle = window.adsbygoogle || []).push({})
    } catch (e) {
      console.error('AdSense error:', e)
    }
  }
})
</script>

<style scoped>
.adsbygoogle {
  display: block;
}
</style>
