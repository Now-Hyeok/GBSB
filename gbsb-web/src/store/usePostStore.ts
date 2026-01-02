import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Post, PaginatedResponse, PostListParams } from '@/types'
import { api } from '@/services/api'

export const usePostStore = defineStore('post', () => {
  // State
  const posts = ref<Post[]>([])
  const currentPost = ref<Post | null>(null)
  const pagination = ref({
    page: 0,
    size: 12,
    totalElements: 0,
    totalPages: 0,
    last: false,
    first: true
  })
  const loading = ref(false)
  const error = ref<string | null>(null)

  // Filters
  const filters = ref<PostListParams>({
    page: 0,
    size: 12,
    sort: 'latest'
  })

  // Getters
  const hasMore = computed(() => !pagination.value.last)
  const isEmpty = computed(() => posts.value.length === 0 && !loading.value)

  // Actions
  const fetchPosts = async (params?: PostListParams) => {
    loading.value = true
    error.value = null

    try {
      const mergedParams = { ...filters.value, ...params }
      const response: PaginatedResponse<Post> = await api.getPosts(mergedParams)

      if (params?.page === 0 || !params?.page) {
        posts.value = response.content
      } else {
        posts.value = [...posts.value, ...response.content]
      }

      pagination.value = {
        page: response.page,
        size: response.size,
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        last: response.last,
        first: response.first
      }

      filters.value = mergedParams
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch posts'
      console.error('Error fetching posts:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchPostById = async (id: number) => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getPost(id)
      currentPost.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch post'
      console.error('Error fetching post:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchPostsByCompany = async (companyId: number, params?: PostListParams) => {
    loading.value = true
    error.value = null

    try {
      const mergedParams = { ...filters.value, ...params }
      const response: PaginatedResponse<Post> = await api.getPostsByCompany(
        companyId,
        mergedParams
      )

      if (params?.page === 0 || !params?.page) {
        posts.value = response.content
      } else {
        posts.value = [...posts.value, ...response.content]
      }

      pagination.value = {
        page: response.page,
        size: response.size,
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        last: response.last,
        first: response.first
      }

      filters.value = mergedParams
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch posts'
      console.error('Error fetching posts:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchPostsByTag = async (tagSlug: string, params?: PostListParams) => {
    loading.value = true
    error.value = null

    try {
      const mergedParams = { ...filters.value, ...params }
      const response: PaginatedResponse<Post> = await api.getPostsByTag(tagSlug, mergedParams)

      if (params?.page === 0 || !params?.page) {
        posts.value = response.content
      } else {
        posts.value = [...posts.value, ...response.content]
      }

      pagination.value = {
        page: response.page,
        size: response.size,
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        last: response.last,
        first: response.first
      }

      filters.value = mergedParams
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch posts'
      console.error('Error fetching posts:', err)
    } finally {
      loading.value = false
    }
  }

  const searchPosts = async (query: string, params?: PostListParams) => {
    loading.value = true
    error.value = null

    try {
      const mergedParams = { ...filters.value, ...params }
      const response: PaginatedResponse<Post> = await api.searchPosts(query, mergedParams)

      posts.value = response.content
      pagination.value = {
        page: response.page,
        size: response.size,
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        last: response.last,
        first: response.first
      }

      filters.value = mergedParams
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to search posts'
      console.error('Error searching posts:', err)
    } finally {
      loading.value = false
    }
  }

  const loadMore = async () => {
    if (!hasMore.value || loading.value) return

    const nextPage = pagination.value.page + 1
    await fetchPosts({ ...filters.value, page: nextPage })
  }

  const setFilters = (newFilters: Partial<PostListParams>) => {
    filters.value = { ...filters.value, ...newFilters, page: 0 }
  }

  const resetFilters = () => {
    filters.value = {
      page: 0,
      size: 12,
      sort: 'latest'
    }
  }

  const reset = () => {
    posts.value = []
    currentPost.value = null
    pagination.value = {
      page: 0,
      size: 12,
      totalElements: 0,
      totalPages: 0,
      last: false,
      first: true
    }
    resetFilters()
    error.value = null
  }

  return {
    // State
    posts,
    currentPost,
    pagination,
    loading,
    error,
    filters,

    // Getters
    hasMore,
    isEmpty,

    // Actions
    fetchPosts,
    fetchPostById,
    fetchPostsByCompany,
    fetchPostsByTag,
    searchPosts,
    loadMore,
    setFilters,
    resetFilters,
    reset
  }
})
