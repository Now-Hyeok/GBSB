// Core entity types
export interface Post {
  id: number
  title: string
  summary?: string
  content?: string
  url: string
  publishedAt: string
  createdAt: string
  updatedAt: string
  companyId: number
  company?: Company
  tags: Tag[]
  categoryId?: number
  category?: Category
  viewCount?: number
  thumbnailUrl?: string
}

export interface Company {
  id: number
  name: string
  nameKo: string
  slug: string
  description?: string
  logoUrl: string
  blogUrl: string
  color?: string
  isActive: boolean
  createdAt: string
  updatedAt: string
}

export interface Tag {
  id: number
  name: string
  slug: string
  count?: number
}

export interface Category {
  id: number
  name: string
  slug: string
  description?: string
  icon?: string
  count?: number
  color?: string
}

// API Response types
export interface ApiResponse<T> {
  success: boolean
  data: T
  message?: string
  error?: string
}

export interface PaginatedResponse<T> {
  content: T[]
  page: number
  size: number
  totalElements: number
  totalPages: number
  last: boolean
  first: boolean
}

// API Request types
export interface PostListParams {
  page?: number
  size?: number
  companyId?: number
  tagId?: number
  categoryId?: number
  search?: string
  sort?: 'latest' | 'popular'
}

export interface CompanyListParams {
  page?: number
  size?: number
  isActive?: boolean
}

// UI State types
export interface FilterState {
  selectedCompany: number | null
  selectedTag: string | null
  searchQuery: string
  sortBy: 'latest' | 'popular'
}

export interface UiState {
  isDarkMode: boolean
  isMobileMenuOpen: boolean
  isLoading: boolean
}

// Mock data types
export interface MockDataset {
  posts: Post[]
  companies: Company[]
  tags: Tag[]
  categories: Category[]
}
