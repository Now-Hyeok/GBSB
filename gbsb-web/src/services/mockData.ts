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

// Mock Companies
export const mockCompanies: Company[] = [
  {
    id: 1,
    name: 'Woowa Bros',
    nameKo: '배달의민족',
    slug: 'woowabros',
    description: '우아한형제들 기술 블로그',
    logoUrl: 'https://techblog.woowahan.com/wp-content/uploads/2021/06/techblog_logo.png',
    blogUrl: 'https://techblog.woowahan.com',
    color: '#2AC1BC',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 2,
    name: 'Kakao',
    nameKo: '카카오',
    slug: 'kakao',
    description: '카카오 기술 블로그',
    logoUrl: 'https://tech.kakao.com/favicon.ico',
    blogUrl: 'https://tech.kakao.com',
    color: '#FEE500',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 3,
    name: 'Toss',
    nameKo: '토스',
    slug: 'toss',
    description: '토스 기술 블로그',
    logoUrl: 'https://static.toss.im/tds/favicon/favicon.ico',
    blogUrl: 'https://toss.tech',
    color: '#0064FF',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 4,
    name: 'LINE',
    nameKo: '라인',
    slug: 'line',
    description: '라인 기술 블로그',
    logoUrl: 'https://engineering.linecorp.com/favicon.ico',
    blogUrl: 'https://engineering.linecorp.com',
    color: '#00B900',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 5,
    name: 'Coupang',
    nameKo: '쿠팡',
    slug: 'coupang',
    description: '쿠팡 기술 블로그',
    logoUrl: 'https://medium.com/coupang-engineering/favicon.ico',
    blogUrl: 'https://medium.com/coupang-engineering',
    color: '#E02020',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 6,
    name: 'Naver',
    nameKo: '네이버',
    slug: 'naver',
    description: '네이버 기술 블로그',
    logoUrl: 'https://d2.naver.com/favicon.ico',
    blogUrl: 'https://d2.naver.com',
    color: '#03C75A',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 7,
    name: 'Daangn',
    nameKo: '당근마켓',
    slug: 'daangn',
    description: '당근마켓 기술 블로그',
    logoUrl: 'https://medium.com/daangn/favicon.ico',
    blogUrl: 'https://medium.com/daangn',
    color: '#FF6F0F',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 8,
    name: 'Yanolja',
    nameKo: '야놀자',
    slug: 'yanolja',
    description: '야놀자 기술 블로그',
    logoUrl: 'https://yanolja.github.io/favicon.ico',
    blogUrl: 'https://yanolja.github.io',
    color: '#FF0066',
    isActive: true,
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  }
]

// Mock Tags
export const mockTags: Tag[] = [
  { id: 1, name: 'Backend', slug: 'backend', count: 145 },
  { id: 2, name: 'Frontend', slug: 'frontend', count: 98 },
  { id: 3, name: 'DevOps', slug: 'devops', count: 76 },
  { id: 4, name: 'Data', slug: 'data', count: 54 },
  { id: 5, name: 'AI/ML', slug: 'ai-ml', count: 43 },
  { id: 6, name: 'Mobile', slug: 'mobile', count: 67 },
  { id: 7, name: 'Infra', slug: 'infra', count: 82 },
  { id: 8, name: 'Security', slug: 'security', count: 39 },
  { id: 9, name: 'Architecture', slug: 'architecture', count: 91 },
  { id: 10, name: 'Testing', slug: 'testing', count: 45 }
]

// Mock Categories
export const mockCategories: Category[] = [
  { id: 1, name: '개발', slug: 'development', icon: '💻', description: '개발 관련 포스트', count: 250, color: '#3b82f6' },
  { id: 2, name: '인프라', slug: 'infrastructure', icon: '🏗️', description: '인프라 및 DevOps', count: 120, color: '#10b981' },
  { id: 3, name: '데이터', slug: 'data', icon: '📊', description: '데이터 엔지니어링 및 분석', count: 85, color: '#f59e0b' },
  { id: 4, name: 'AI/ML', slug: 'ai-ml', icon: '🤖', description: '인공지능 및 머신러닝', count: 67, color: '#8b5cf6' },
  { id: 5, name: '모바일', slug: 'mobile', icon: '📱', description: '모바일 앱 개발', count: 93, color: '#ec4899' },
  { id: 6, name: '보안', slug: 'security', icon: '🔒', description: '보안 및 인증', count: 45, color: '#ef4444' },
  { id: 7, name: '프로덕트', slug: 'product', icon: '🎯', description: '프로덕트 및 기획', count: 38, color: '#06b6d4' },
  { id: 8, name: '문화', slug: 'culture', icon: '🌱', description: '조직 문화 및 성장', count: 52, color: '#84cc16' }
]

// Mock Posts
export const mockPosts: Post[] = [
  {
    id: 1,
    title: 'Spring Cloud Gateway를 이용한 API Gateway 구축기',
    summary: 'MSA 환경에서 Spring Cloud Gateway를 도입하고 성능을 개선한 경험을 공유합니다.',
    url: 'https://techblog.woowahan.com/2023/04/11/spring-cloud-gateway',
    publishedAt: '2024-03-15T09:00:00Z',
    createdAt: '2024-03-15T09:00:00Z',
    updatedAt: '2024-03-15T09:00:00Z',
    companyId: 1,
    company: mockCompanies[0],
    tags: [mockTags[0], mockTags[8]],
    viewCount: 1523
  },
  {
    id: 2,
    title: 'React Query로 서버 상태 관리하기',
    summary: '프론트엔드에서 React Query를 활용하여 효율적인 서버 상태 관리를 구현한 방법을 소개합니다.',
    url: 'https://tech.kakao.com/2023/05/22/react-query',
    publishedAt: '2024-03-14T14:30:00Z',
    createdAt: '2024-03-14T14:30:00Z',
    updatedAt: '2024-03-14T14:30:00Z',
    companyId: 2,
    company: mockCompanies[1],
    tags: [mockTags[1]],
    viewCount: 2341
  },
  {
    id: 3,
    title: 'Kubernetes 클러스터 비용 최적화 전략',
    summary: '쿠버네티스 운영 비용을 50% 절감한 실전 경험을 공유합니다.',
    url: 'https://toss.tech/article/kubernetes-cost-optimization',
    publishedAt: '2024-03-13T10:00:00Z',
    createdAt: '2024-03-13T10:00:00Z',
    updatedAt: '2024-03-13T10:00:00Z',
    companyId: 3,
    company: mockCompanies[2],
    tags: [mockTags[2], mockTags[6]],
    viewCount: 3102
  },
  {
    id: 4,
    title: 'iOS 앱 성능 개선: 메모리 최적화부터 렌더링까지',
    summary: 'LINE iOS 앱의 성능을 개선하기 위해 진행한 다양한 최적화 작업을 소개합니다.',
    url: 'https://engineering.linecorp.com/ko/blog/ios-performance',
    publishedAt: '2024-03-12T11:20:00Z',
    createdAt: '2024-03-12T11:20:00Z',
    updatedAt: '2024-03-12T11:20:00Z',
    companyId: 4,
    company: mockCompanies[3],
    tags: [mockTags[5]],
    viewCount: 1876
  },
  {
    id: 5,
    title: '대규모 트래픽 처리를 위한 Redis 활용 전략',
    summary: '초당 10만 건 이상의 요청을 처리하기 위한 Redis 아키텍처 설계와 운영 노하우를 공유합니다.',
    url: 'https://medium.com/coupang-engineering/redis-at-scale',
    publishedAt: '2024-03-11T15:00:00Z',
    createdAt: '2024-03-11T15:00:00Z',
    updatedAt: '2024-03-11T15:00:00Z',
    companyId: 5,
    company: mockCompanies[4],
    tags: [mockTags[0], mockTags[6]],
    viewCount: 2934
  },
  {
    id: 6,
    title: '검색 품질 개선을 위한 머신러닝 랭킹 모델 구축',
    summary: 'Elasticsearch와 머신러닝을 결합하여 검색 품질을 향상시킨 경험을 공유합니다.',
    url: 'https://d2.naver.com/helloworld/search-ml-ranking',
    publishedAt: '2024-03-10T09:30:00Z',
    createdAt: '2024-03-10T09:30:00Z',
    updatedAt: '2024-03-10T09:30:00Z',
    companyId: 6,
    company: mockCompanies[5],
    tags: [mockTags[3], mockTags[4]],
    viewCount: 2156
  },
  {
    id: 7,
    title: 'Flutter로 크로스 플랫폼 앱 개발하기',
    summary: '당근마켓이 Flutter를 선택한 이유와 실제 개발 경험을 소개합니다.',
    url: 'https://medium.com/daangn/flutter-experience',
    publishedAt: '2024-03-09T13:00:00Z',
    createdAt: '2024-03-09T13:00:00Z',
    updatedAt: '2024-03-09T13:00:00Z',
    companyId: 7,
    company: mockCompanies[6],
    tags: [mockTags[5]],
    viewCount: 1645
  },
  {
    id: 8,
    title: 'GraphQL Federation으로 마이크로서비스 통합하기',
    summary: '여러 마이크로서비스를 GraphQL Federation으로 통합한 아키텍처를 공유합니다.',
    url: 'https://yanolja.github.io/graphql-federation',
    publishedAt: '2024-03-08T10:45:00Z',
    createdAt: '2024-03-08T10:45:00Z',
    updatedAt: '2024-03-08T10:45:00Z',
    companyId: 8,
    company: mockCompanies[7],
    tags: [mockTags[0], mockTags[8]],
    viewCount: 1289
  },
  {
    id: 9,
    title: 'TypeScript 5.0 마이그레이션 가이드',
    summary: 'TypeScript 5.0의 새로운 기능과 마이그레이션 전략을 소개합니다.',
    url: 'https://techblog.woowahan.com/2024/03/07/typescript-5',
    publishedAt: '2024-03-07T14:00:00Z',
    createdAt: '2024-03-07T14:00:00Z',
    updatedAt: '2024-03-07T14:00:00Z',
    companyId: 1,
    company: mockCompanies[0],
    tags: [mockTags[1]],
    viewCount: 3421
  },
  {
    id: 10,
    title: 'CI/CD 파이프라인 구축: GitHub Actions 활용기',
    summary: 'GitHub Actions를 활용한 효율적인 CI/CD 파이프라인 구축 경험을 공유합니다.',
    url: 'https://tech.kakao.com/2024/03/06/github-actions-cicd',
    publishedAt: '2024-03-06T11:00:00Z',
    createdAt: '2024-03-06T11:00:00Z',
    updatedAt: '2024-03-06T11:00:00Z',
    companyId: 2,
    company: mockCompanies[1],
    tags: [mockTags[2]],
    viewCount: 2187
  },
  {
    id: 11,
    title: '실시간 데이터 처리를 위한 Apache Kafka 활용',
    summary: '대용량 실시간 데이터 처리를 위한 Kafka 클러스터 설계와 운영 경험을 소개합니다.',
    url: 'https://toss.tech/article/kafka-realtime',
    publishedAt: '2024-03-05T09:15:00Z',
    createdAt: '2024-03-05T09:15:00Z',
    updatedAt: '2024-03-05T09:15:00Z',
    companyId: 3,
    company: mockCompanies[2],
    tags: [mockTags[0], mockTags[3]],
    viewCount: 2673
  },
  {
    id: 12,
    title: 'Vue 3 Composition API 실전 가이드',
    summary: 'Vue 3 Composition API를 실무에 적용하며 얻은 인사이트를 공유합니다.',
    url: 'https://engineering.linecorp.com/ko/blog/vue3-composition-api',
    publishedAt: '2024-03-04T16:30:00Z',
    createdAt: '2024-03-04T16:30:00Z',
    updatedAt: '2024-03-04T16:30:00Z',
    companyId: 4,
    company: mockCompanies[3],
    tags: [mockTags[1]],
    viewCount: 1923
  }
]

// Mock API functions
export const mockGetPosts = async (
  params?: PostListParams
): Promise<PaginatedResponse<Post>> => {
  await delay(300)

  let filteredPosts = [...mockPosts]

  if (params?.companyId) {
    filteredPosts = filteredPosts.filter((p) => p.companyId === params.companyId)
  }

  if (params?.search) {
    const query = params.search.toLowerCase()
    filteredPosts = filteredPosts.filter(
      (p) =>
        p.title.toLowerCase().includes(query) ||
        p.summary?.toLowerCase().includes(query)
    )
  }

  if (params?.sort === 'popular') {
    filteredPosts.sort((a, b) => (b.viewCount || 0) - (a.viewCount || 0))
  } else {
    filteredPosts.sort(
      (a, b) => new Date(b.publishedAt).getTime() - new Date(a.publishedAt).getTime()
    )
  }

  const page = params?.page || 0
  const size = params?.size || 10
  const start = page * size
  const end = start + size

  return {
    content: filteredPosts.slice(start, end),
    page,
    size,
    totalElements: filteredPosts.length,
    totalPages: Math.ceil(filteredPosts.length / size),
    last: end >= filteredPosts.length,
    first: page === 0
  }
}

export const mockGetPost = async (id: number): Promise<ApiResponse<Post>> => {
  await delay(200)
  const post = mockPosts.find((p) => p.id === id)

  if (!post) {
    throw new Error('Post not found')
  }

  return {
    success: true,
    data: post
  }
}

export const mockGetPostsByCompany = async (
  companyId: number,
  params?: PostListParams
): Promise<PaginatedResponse<Post>> => {
  return mockGetPosts({ ...params, companyId })
}

export const mockGetPostsByTag = async (
  tagSlug: string,
  params?: PostListParams
): Promise<PaginatedResponse<Post>> => {
  await delay(300)

  const tag = mockTags.find((t) => t.slug === tagSlug)
  if (!tag) {
    throw new Error('Tag not found')
  }

  const filteredPosts = mockPosts.filter((post) =>
    post.tags.some((t) => t.slug === tagSlug)
  )

  const page = params?.page || 0
  const size = params?.size || 10
  const start = page * size
  const end = start + size

  return {
    content: filteredPosts.slice(start, end),
    page,
    size,
    totalElements: filteredPosts.length,
    totalPages: Math.ceil(filteredPosts.length / size),
    last: end >= filteredPosts.length,
    first: page === 0
  }
}

export const mockSearchPosts = async (
  query: string,
  params?: PostListParams
): Promise<PaginatedResponse<Post>> => {
  return mockGetPosts({ ...params, search: query })
}

export const mockGetCompanies = async (
  params?: CompanyListParams
): Promise<PaginatedResponse<Company>> => {
  await delay(200)

  let companies = [...mockCompanies]

  if (params?.isActive !== undefined) {
    companies = companies.filter((c) => c.isActive === params.isActive)
  }

  const page = params?.page || 0
  const size = params?.size || 20
  const start = page * size
  const end = start + size

  return {
    content: companies.slice(start, end),
    page,
    size,
    totalElements: companies.length,
    totalPages: Math.ceil(companies.length / size),
    last: end >= companies.length,
    first: page === 0
  }
}

export const mockGetCompany = async (id: number): Promise<ApiResponse<Company>> => {
  await delay(150)
  const company = mockCompanies.find((c) => c.id === id)

  if (!company) {
    throw new Error('Company not found')
  }

  return {
    success: true,
    data: company
  }
}

export const mockGetTags = async (): Promise<ApiResponse<Tag[]>> => {
  await delay(150)
  return {
    success: true,
    data: mockTags
  }
}

export const mockGetPopularTags = async (limit: number = 10): Promise<ApiResponse<Tag[]>> => {
  await delay(150)
  const sorted = [...mockTags].sort((a, b) => (b.count || 0) - (a.count || 0))
  return {
    success: true,
    data: sorted.slice(0, limit)
  }
}

// Categories API
export const mockGetCategories = async (): Promise<ApiResponse<Category[]>> => {
  await delay(150)
  return {
    success: true,
    data: mockCategories
  }
}

export const mockGetCategory = async (id: number): Promise<ApiResponse<Category>> => {
  await delay(150)
  const category = mockCategories.find((c) => c.id === id)

  if (!category) {
    throw new Error('Category not found')
  }

  return {
    success: true,
    data: category
  }
}

export const mockGetPostsByCategory = async (
  categoryId: number,
  params?: PostListParams
): Promise<PaginatedResponse<Post>> => {
  await delay(300)

  // For demo purposes, distribute posts across categories
  const postsPerCategory = Math.ceil(mockPosts.length / mockCategories.length)
  const startIndex = (categoryId - 1) * postsPerCategory
  const filteredPosts = mockPosts.slice(startIndex, startIndex + postsPerCategory)

  const page = params?.page || 0
  const size = params?.size || 10
  const start = page * size
  const end = start + size

  return {
    content: filteredPosts.slice(start, end),
    page,
    size,
    totalElements: filteredPosts.length,
    totalPages: Math.ceil(filteredPosts.length / size),
    last: end >= filteredPosts.length,
    first: page === 0
  }
}

// Utility function
const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms))
