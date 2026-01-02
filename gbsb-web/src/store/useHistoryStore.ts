import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useStorage } from '@vueuse/core'
import type { Post } from '@/types'

interface HistoryItem {
  postId: number
  viewedAt: string
}

export const useHistoryStore = defineStore('history', () => {
  // State (persisted in localStorage)
  const historyItems = useStorage<HistoryItem[]>('post-history', [])
  const recentPosts = ref<Post[]>([])
  const maxHistorySize = 20

  // Getters
  const hasViewed = computed(() => (postId: number) => {
    return historyItems.value.some(item => item.postId === postId)
  })

  const recentPostIds = computed(() => {
    return historyItems.value.slice(0, 10).map(item => item.postId)
  })

  // Actions
  const addToHistory = (post: Post) => {
    // Remove if already exists
    const index = historyItems.value.findIndex(item => item.postId === post.id)
    if (index > -1) {
      historyItems.value.splice(index, 1)
    }

    // Add to beginning
    historyItems.value.unshift({
      postId: post.id,
      viewedAt: new Date().toISOString()
    })

    // Update recentPosts
    const postIndex = recentPosts.value.findIndex(p => p.id === post.id)
    if (postIndex > -1) {
      recentPosts.value.splice(postIndex, 1)
    }
    recentPosts.value.unshift(post)

    // Limit size
    if (historyItems.value.length > maxHistorySize) {
      historyItems.value = historyItems.value.slice(0, maxHistorySize)
    }
    if (recentPosts.value.length > maxHistorySize) {
      recentPosts.value = recentPosts.value.slice(0, maxHistorySize)
    }
  }

  const clearHistory = () => {
    historyItems.value = []
    recentPosts.value = []
  }

  const removeFromHistory = (postId: number) => {
    const index = historyItems.value.findIndex(item => item.postId === postId)
    if (index > -1) {
      historyItems.value.splice(index, 1)
    }

    const postIndex = recentPosts.value.findIndex(p => p.id === postId)
    if (postIndex > -1) {
      recentPosts.value.splice(postIndex, 1)
    }
  }

  const loadRecentPosts = (posts: Post[]) => {
    // Filter posts that are in history
    const postMap = new Map(posts.map(p => [p.id, p]))
    recentPosts.value = historyItems.value
      .map(item => postMap.get(item.postId))
      .filter((p): p is Post => p !== undefined)
      .slice(0, 10)
  }

  return {
    // State
    historyItems,
    recentPosts,

    // Getters
    hasViewed,
    recentPostIds,

    // Actions
    addToHistory,
    clearHistory,
    removeFromHistory,
    loadRecentPosts
  }
})
