import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Tag } from '@/types'
import { api } from '@/services/api'

export const useTagStore = defineStore('tag', () => {
  // State
  const tags = ref<Tag[]>([])
  const popularTags = ref<Tag[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const getTagBySlug = computed(() => (slug: string) => tags.value.find((t) => t.slug === slug))

  // Actions
  const fetchTags = async () => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getTags()
      tags.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch tags'
      console.error('Error fetching tags:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchPopularTags = async (limit: number = 10) => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getPopularTags(limit)
      popularTags.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch popular tags'
      console.error('Error fetching popular tags:', err)
    } finally {
      loading.value = false
    }
  }

  return {
    // State
    tags,
    popularTags,
    loading,
    error,

    // Getters
    getTagBySlug,

    // Actions
    fetchTags,
    fetchPopularTags
  }
})
