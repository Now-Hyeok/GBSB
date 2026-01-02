<template>
  <div class="flex items-center gap-2">
    <span v-if="showLabel" class="text-sm font-medium text-gray-700 dark:text-gray-300 mr-2">
      공유하기:
    </span>

    <!-- Twitter -->
    <a
      :href="twitterUrl"
      target="_blank"
      rel="noopener noreferrer"
      class="p-2 rounded-lg bg-gray-100 dark:bg-gray-800 hover:bg-blue-400 hover:text-white transition-colors"
      title="Twitter에 공유"
    >
      <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
        <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2c9 5 20 0 20-11.5a4.5 4.5 0 00-.08-.83A7.72 7.72 0 0023 3z"/>
      </svg>
    </a>

    <!-- Facebook -->
    <a
      :href="facebookUrl"
      target="_blank"
      rel="noopener noreferrer"
      class="p-2 rounded-lg bg-gray-100 dark:bg-gray-800 hover:bg-blue-600 hover:text-white transition-colors"
      title="Facebook에 공유"
    >
      <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
        <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
      </svg>
    </a>

    <!-- KakaoTalk -->
    <button
      @click="shareKakao"
      class="p-2 rounded-lg bg-gray-100 dark:bg-gray-800 hover:bg-yellow-400 hover:text-black transition-colors"
      title="카카오톡 공유"
    >
      <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
        <path d="M12 3c5.799 0 10.5 3.664 10.5 8.185 0 4.52-4.701 8.184-10.5 8.184a13.5 13.5 0 01-1.727-.11l-4.408 2.883c-.501.265-.678.236-.472-.413l.892-3.678c-2.88-1.46-4.785-3.99-4.785-6.866C1.5 6.665 6.201 3 12 3zm5.907 8.06l1.47-1.424a.472.472 0 00-.656-.678l-1.928 1.866V9.282a.472.472 0 00-.944 0v2.557a.471.471 0 000 .222V13.5a.472.472 0 00.944 0v-1.363l.427-.413 1.428 2.033a.472.472 0 10.773-.543l-1.514-2.155zm-2.958 1.924h-1.46V9.297a.472.472 0 00-.943 0v4.159c0 .26.21.472.471.472h1.932a.472.472 0 000-.944zm-5.857-1.092l.696-1.707.638 1.707H9.092zm2.523.488l.002-.016a.469.469 0 00-.127-.32l-1.046-2.8a.69.69 0 00-.627-.474.696.696 0 00-.653.447l-1.661 4.075a.472.472 0 00.874.357l.33-.813h2.07l.299.8a.472.472 0 10.884-.330l-.345-.926zM8.293 9.302a.472.472 0 00-.471-.472H4.577a.472.472 0 100 .944h1.16v3.736a.472.472 0 00.944 0V9.774h1.141a.472.472 0 00.47-.472z"/>
      </svg>
    </button>

    <!-- URL Copy -->
    <button
      @click="copyUrl"
      class="p-2 rounded-lg bg-gray-100 dark:bg-gray-800 hover:bg-gray-300 dark:hover:bg-gray-700 transition-colors"
      :class="{ 'bg-green-500 text-white': copied }"
      :title="copied ? '복사됨!' : 'URL 복사'"
    >
      <svg v-if="!copied" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z"/>
      </svg>
      <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
      </svg>
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Props {
  title: string
  url: string
  showLabel?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  showLabel: true
})

const copied = ref(false)

const twitterUrl = computed(() => {
  const text = encodeURIComponent(props.title)
  const url = encodeURIComponent(props.url)
  return `https://twitter.com/intent/tweet?text=${text}&url=${url}`
})

const facebookUrl = computed(() => {
  const url = encodeURIComponent(props.url)
  return `https://www.facebook.com/sharer/sharer.php?u=${url}`
})

const shareKakao = () => {
  // Kakao SDK를 사용하려면 앱 키가 필요합니다
  // 여기서는 간단히 URL 복사로 대체
  alert('카카오톡 공유는 카카오 개발자 등록 후 사용 가능합니다.\n지금은 URL을 복사합니다.')
  copyUrl()
}

const copyUrl = async () => {
  try {
    await navigator.clipboard.writeText(props.url)
    copied.value = true
    setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch (err) {
    console.error('Failed to copy:', err)
    // Fallback for older browsers
    const textArea = document.createElement('textarea')
    textArea.value = props.url
    document.body.appendChild(textArea)
    textArea.select()
    document.execCommand('copy')
    document.body.removeChild(textArea)
    copied.value = true
    setTimeout(() => {
      copied.value = false
    }, 2000)
  }
}
</script>
