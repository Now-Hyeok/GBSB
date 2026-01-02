import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useStorage } from '@vueuse/core'
import type { Post } from '@/types'

export const useBookmarkStore = defineStore('bookmark', () => {
  // State (persisted in localStorage)
  const bookmarkedPostIds = useStorage<number[]>('bookmarked-posts', [])
  const bookmarkedPosts = ref<Post[]>([])

  // Getters
  const isBookmarked = computed(() => (postId: number) => {
    return bookmarkedPostIds.value.includes(postId)
  })

  const bookmarkCount = computed(() => bookmarkedPostIds.value.length)

  // Actions
  const toggleBookmark = (post: Post) => {
    const index = bookmarkedPostIds.value.indexOf(post.id)

    if (index > -1) {
      // Remove bookmark
      bookmarkedPostIds.value.splice(index, 1)
      const postIndex = bookmarkedPosts.value.findIndex(p => p.id === post.id)
      if (postIndex > -1) {
        bookmarkedPosts.value.splice(postIndex, 1)
      }
    } else {
      // Add bookmark
      bookmarkedPostIds.value.push(post.id)
      bookmarkedPosts.value.unshift(post)
    }
  }

  const addBookmark = (post: Post) => {
    if (!isBookmarked.value(post.id)) {
      bookmarkedPostIds.value.push(post.id)
      bookmarkedPosts.value.unshift(post)
    }
  }

  const removeBookmark = (postId: number) => {
    const index = bookmarkedPostIds.value.indexOf(postId)
    if (index > -1) {
      bookmarkedPostIds.value.splice(index, 1)
      const postIndex = bookmarkedPosts.value.findIndex(p => p.id === postId)
      if (postIndex > -1) {
        bookmarkedPosts.value.splice(postIndex, 1)
      }
    }
  }

  const clearBookmarks = () => {
    bookmarkedPostIds.value = []
    bookmarkedPosts.value = []
  }

  const loadBookmarkedPosts = (posts: Post[]) => {
    // Filter posts that are bookmarked
    bookmarkedPosts.value = posts.filter(post =>
      bookmarkedPostIds.value.includes(post.id)
    )
  }

  return {
    // State
    bookmarkedPostIds,
    bookmarkedPosts,

    // Getters
    isBookmarked,
    bookmarkCount,

    // Actions
    toggleBookmark,
    addBookmark,
    removeBookmark,
    clearBookmarks,
    loadBookmarkedPosts
  }
})
