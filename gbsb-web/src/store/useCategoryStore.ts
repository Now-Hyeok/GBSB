import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Category } from '@/types'
import { api } from '@/services/api'

export const useCategoryStore = defineStore('category', () => {
  // State
  const categories = ref<Category[]>([])
  const currentCategory = ref<Category | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const getCategoryById = computed(() => (id: number) =>
    categories.value.find((c) => c.id === id)
  )
  const getCategoryBySlug = computed(() => (slug: string) =>
    categories.value.find((c) => c.slug === slug)
  )

  // Actions
  const fetchCategories = async () => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getCategories()
      categories.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch categories'
      console.error('Error fetching categories:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchCategoryById = async (id: number) => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getCategory(id)
      currentCategory.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch category'
      console.error('Error fetching category:', err)
    } finally {
      loading.value = false
    }
  }

  const reset = () => {
    currentCategory.value = null
    error.value = null
  }

  return {
    // State
    categories,
    currentCategory,
    loading,
    error,

    // Getters
    getCategoryById,
    getCategoryBySlug,

    // Actions
    fetchCategories,
    fetchCategoryById,
    reset
  }
})
