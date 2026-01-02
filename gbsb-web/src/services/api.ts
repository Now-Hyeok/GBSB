import axios, { AxiosInstance, AxiosError } from 'axios'
import type {
  Post,
  Company,
  Tag,
  Category,
  ApiResponse,
  PaginatedResponse,
  PostListParams,
  CompanyListParams
} from '@/types'

class ApiService {
  private client: AxiosInstance
  private useMock: boolean = true // Set to false when backend is ready

  constructor() {
    this.client = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json'
      }
    })

    this.setupInterceptors()
  }

  private setupInterceptors() {
    // Request interceptor
    this.client.interceptors.request.use(
      (config) => {
        // Add auth token if available
        const token = localStorage.getItem('auth_token')
        if (token) {
          config.headers.Authorization = `Bearer ${token}`
        }
        return config
      },
      (error) => Promise.reject(error)
    )

    // Response interceptor
    this.client.interceptors.response.use(
      (response) => response.data,
      (error: AxiosError) => {
        console.error('API Error:', error.response?.data || error.message)
        return Promise.reject(error)
      }
    )
  }

  // Toggle between mock and real API
  setUseMock(useMock: boolean) {
    this.useMock = useMock
  }

  // Posts API
  async getPosts(params?: PostListParams): Promise<PaginatedResponse<Post>> {
    if (this.useMock) {
      const { mockGetPosts } = await import('./mockData')
      return mockGetPosts(params)
    }
    return this.client.get('/posts', { params })
  }

  async getPost(id: number): Promise<ApiResponse<Post>> {
    if (this.useMock) {
      const { mockGetPost } = await import('./mockData')
      return mockGetPost(id)
    }
    return this.client.get(`/posts/${id}`)
  }

  async getPostsByCompany(
    companyId: number,
    params?: PostListParams
  ): Promise<PaginatedResponse<Post>> {
    if (this.useMock) {
      const { mockGetPostsByCompany } = await import('./mockData')
      return mockGetPostsByCompany(companyId, params)
    }
    return this.client.get(`/companies/${companyId}/posts`, { params })
  }

  async getPostsByTag(
    tagSlug: string,
    params?: PostListParams
  ): Promise<PaginatedResponse<Post>> {
    if (this.useMock) {
      const { mockGetPostsByTag } = await import('./mockData')
      return mockGetPostsByTag(tagSlug, params)
    }
    return this.client.get(`/tags/${tagSlug}/posts`, { params })
  }

  async searchPosts(query: string, params?: PostListParams): Promise<PaginatedResponse<Post>> {
    if (this.useMock) {
      const { mockSearchPosts } = await import('./mockData')
      return mockSearchPosts(query, params)
    }
    return this.client.get('/posts/search', { params: { ...params, q: query } })
  }

  // Companies API
  async getCompanies(params?: CompanyListParams): Promise<PaginatedResponse<Company>> {
    if (this.useMock) {
      const { mockGetCompanies } = await import('./mockData')
      return mockGetCompanies(params)
    }
    return this.client.get('/companies', { params })
  }

  async getCompany(id: number): Promise<ApiResponse<Company>> {
    if (this.useMock) {
      const { mockGetCompany } = await import('./mockData')
      return mockGetCompany(id)
    }
    return this.client.get(`/companies/${id}`)
  }

  // Tags API
  async getTags(): Promise<ApiResponse<Tag[]>> {
    if (this.useMock) {
      const { mockGetTags } = await import('./mockData')
      return mockGetTags()
    }
    return this.client.get('/tags')
  }

  async getPopularTags(limit: number = 10): Promise<ApiResponse<Tag[]>> {
    if (this.useMock) {
      const { mockGetPopularTags } = await import('./mockData')
      return mockGetPopularTags(limit)
    }
    return this.client.get('/tags/popular', { params: { limit } })
  }

  // Categories API
  async getCategories(): Promise<ApiResponse<Category[]>> {
    if (this.useMock) {
      const { mockGetCategories } = await import('./mockData')
      return mockGetCategories()
    }
    return this.client.get('/categories')
  }

  async getCategory(id: number): Promise<ApiResponse<Category>> {
    if (this.useMock) {
      const { mockGetCategory } = await import('./mockData')
      return mockGetCategory(id)
    }
    return this.client.get(`/categories/${id}`)
  }

  async getPostsByCategory(
    categoryId: number,
    params?: PostListParams
  ): Promise<PaginatedResponse<Post>> {
    if (this.useMock) {
      const { mockGetPostsByCategory } = await import('./mockData')
      return mockGetPostsByCategory(categoryId, params)
    }
    return this.client.get(`/categories/${categoryId}/posts`, { params })
  }
}

export const api = new ApiService()
export default api
