# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**개발새발 (Dev Blog Hub)** is a tech blog aggregation platform that curates posts from major Korean IT companies (배달의민족, 카카오, 토스, 네이버, etc.) in one place.

**Current Status:**
- ✅ **Frontend**: Fully implemented Vue 3 + TypeScript SPA (`web/`)
- 🚧 **Backend**: Planned Spring Boot REST API (scaffolded but not implemented)
- 📋 **API Spec**: Complete backend API specification available in `docs/BACKEND_API_SPEC.md`

## Common Commands

### Frontend Development

```bash
# Navigate to frontend
cd web

# Install dependencies
npm install

# Start dev server (runs on http://localhost:3001)
npm run dev

# Production build
npm run build

# Preview production build
npm run preview

# Lint and auto-fix
npm run lint
```

### Mock vs Real API Toggle

The frontend works standalone with mock data by default. To switch:

```typescript
// In src/services/api.ts
private useMock: boolean = true   // Use mock data (default)
private useMock: boolean = false  // Use real backend API
```

## Architecture

### Monorepo Structure

```
개발새발/
├── web/                        # Vue 3 SPA (COMPLETE)
├── api/                        # Spring Boot backend (PLANNED)
└── docs/                       # Documentation
```

### Frontend Architecture (Vue 3)

**Tech Stack:**
- Vue 3 (Composition API with `<script setup>`)
- TypeScript 5.4 (strict mode)
- Vite 5.2
- Tailwind CSS 3.4
- Pinia 2.1 (state management)
- Vue Router 4.3
- Axios 1.6
- @vueuse/core (composables)

**Source Organization:**

```
web/src/
├── pages/              # Route-level components (HomePage, PostDetailPage, etc.)
├── components/
│   ├── common/        # Atomic reusable components (PostCard, TagChip, etc.)
│   └── layout/        # Layout components (TheHeader, TheFooter, SearchModal)
├── store/             # Pinia stores (7 stores - posts, companies, tags, categories, bookmarks, history, ui)
├── services/          # API layer (api.ts) and mock data (mockData.ts)
├── router/            # Vue Router config with lazy-loaded routes
├── types/             # TypeScript interfaces (Post, Company, Tag, Category, etc.)
└── assets/styles/     # Tailwind CSS with custom utility layers
```

### Key Architectural Patterns

**1. Dual-Mode API Service Pattern:**

The frontend can operate completely standalone without a backend. All API calls go through a unified service that switches between mock and real data:

```typescript
// src/services/api.ts
class ApiService {
  private useMock: boolean = true

  async getPosts(params?: PostListParams) {
    if (this.useMock) {
      const { mockGetPosts } = await import('./mockData')
      return mockGetPosts(params)
    }
    return this.client.get('/posts', { params })
  }
}
```

**2. Store Pattern (Pinia):**

Stores follow the composition API style with ref/computed/functions:

```typescript
export const usePostStore = defineStore('post', () => {
  const posts = ref<Post[]>([])
  const hasMore = computed(() => !pagination.value.last)
  const fetchPosts = async (params) => { /* ... */ }
  return { posts, hasMore, fetchPosts }
})
```

**3. Client-Side Persistence:**

Bookmarks, reading history, and dark mode use `@vueuse/core`'s `useStorage` for automatic localStorage sync:

```typescript
const bookmarkedPostIds = useStorage<number[]>('bookmarked-posts', [])
```

**4. Component Hierarchy:**

- `App.vue` → Root layout with TheHeader, router-view, TheFooter
- Pages compose multiple reusable components
- Atomic components are simple, props-based (PostCard, TagChip, CompanyBadge)

**5. Route Structure:**

- `/` - HomePage with filters, categories, infinite scroll
- `/company/:companyId` - Company-specific posts
- `/post/:postId` - Post detail with share buttons
- `/category/:categoryId` - Category-filtered posts
- `/tag/:tagSlug` - Tag-filtered posts
- `/bookmarks` - Saved posts (localStorage)
- `/about` - About page

All routes use lazy loading for code splitting.

### Data Flow

```
User Action → Page Component → Pinia Store → API Service → Mock/Real Backend
                                    ↓
                            Updates reactive state
                                    ↓
                            UI automatically re-renders
```

### Styling Architecture

**Tailwind Configuration:**
- Custom color palette: primary (sky blue), dark mode colors
- Class-based dark mode (`darkMode: 'class'`)
- Custom utility layers in `main.css` (btn, card, input, tag-chip, etc.)
- Mobile-first responsive design

**Dark Mode:**
- Toggled via `useUiStore.toggleDarkMode()`
- Persisted in localStorage
- Applies/removes `dark` class on `document.documentElement`

## Critical Implementation Details

### API Proxy Configuration

Vite proxies `/api` requests to backend (when not using mock):

```typescript
// vite.config.ts
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true
  }
}
```

### TypeScript Path Aliases

Use `@/` prefix for absolute imports:

```typescript
import { usePostStore } from '@/store/usePostStore'
import PostCard from '@/components/common/PostCard.vue'
```

Configured in both `vite.config.ts` and `tsconfig.json`.

### Pagination & Infinite Scroll

Backend returns Spring Boot Page format:

```typescript
interface PaginatedResponse<T> {
  content: T[]
  page: number          // 0-indexed
  size: number
  totalElements: number
  totalPages: number
  last: boolean
  first: boolean
}
```

Frontend uses `InfiniteScroll` component that monitors scroll position and emits `load-more` event.

### Data Models

Core interfaces in `src/types/index.ts`:

```typescript
interface Post {
  id: number
  title: string
  summary?: string
  url: string
  publishedAt: string
  companyId: number
  company?: Company
  categoryId?: number
  category?: Category
  tags: Tag[]
  viewCount?: number
}

interface Company {
  id: number
  name: string        // English name
  nameKo: string      // Korean name
  slug: string        // URL slug
  logoUrl: string
  blogUrl: string
  color?: string
  isActive: boolean
}

interface Tag {
  id: number
  name: string
  slug: string
  count?: number
}

interface Category {
  id: number
  name: string
  slug: string
  icon?: string       // Emoji
  color?: string
  count?: number
}
```

## Backend Development (When Implementing)

### API Specification

Complete REST API spec: `docs/BACKEND_API_SPEC.md`

**Key Endpoints:**
- `GET /api/posts` - List with pagination, filtering, sorting
- `GET /api/posts/{id}` - Post detail
- `GET /api/companies/{companyId}/posts` - Company posts
- `GET /api/tags/{tagSlug}/posts` - Tag posts
- `GET /api/categories/{categoryId}/posts` - Category posts
- `GET /api/posts/search?q={query}` - Search
- `GET /api/companies` - Company list
- `GET /api/tags` - Tag list
- `GET /api/tags/popular` - Popular tags
- `GET /api/categories` - Category list

**Expected Response Formats:**
- Paginated: Spring Boot `Page` format (content, page, size, totalElements, etc.)
- Single: `{ success: true, data: {...} }`
- Error: `{ success: false, error: "...", code: "..." }`

### Planned Backend Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL/MySQL
- Spring Cloud Gateway (API Gateway)
- Rome RSS Reader (for blog crawling)

### Database Schema

Complete SQL schemas in `BACKEND_API_SPEC.md` including:
- `posts` table
- `companies` table
- `tags` table
- `categories` table
- `post_tags` junction table

## Extending the Frontend

### Adding a New Page

1. Create component in `src/pages/NewPage.vue`
2. Add route in `src/router/index.ts`:
```typescript
{
  path: '/new-page',
  component: () => import('@/pages/NewPage.vue'),
  meta: { title: 'New Page' }
}
```
3. Compose existing components from `components/common/`

### Adding a New API Endpoint

1. Define types in `src/types/index.ts`
2. Add method in `src/services/api.ts` (both real and mock paths)
3. Add mock implementation in `src/services/mockData.ts`

### Adding a New Store

```typescript
// src/store/useNewStore.ts
import { defineStore } from 'pinia'

export const useNewStore = defineStore('new', () => {
  const data = ref([])
  const fetchData = async () => { /* ... */ }
  return { data, fetchData }
})
```

Export from `src/store/index.ts`.

### Adding a New Component

1. Create in `src/components/common/ComponentName.vue`
2. Use Composition API with `<script setup lang="ts">`
3. Follow existing patterns:
   - Props with TypeScript interfaces
   - Emit events with typed emits
   - Use Tailwind classes with dark mode variants

## Important Notes

### Features Using LocalStorage

These work client-side only (no backend required):
- Bookmarks (`useBookmarkStore`)
- Reading history (`useHistoryStore`)
- Dark mode preference (`useUiStore`)

### Google AdSense

`AdBanner` component is prepared but needs:
- Real publisher ID and ad slot IDs
- Production environment check

### Social Sharing

- Twitter/Facebook: Implemented with URL encoding
- KakaoTalk: Placeholder (needs Kakao SDK integration)
- URL copy: Uses Clipboard API

### SEO

- Dynamic meta tags via `@vueuse/head`
- Route meta for page titles
- Navigation guard updates `document.title`

## Development Workflow

1. **Frontend-only development**: Use mock data (default)
2. **With backend**: Set `useMock = false` in `api.ts`, ensure backend runs on `localhost:8080`
3. **Production build**: `npm run build` in frontend directory
4. **Type checking**: Automatic during build via `vue-tsc`

## Troubleshooting

**Issue: PostCSS error about `border-border` class**
- **Cause**: Non-existent Tailwind class in global CSS
- **Fix**: Already removed from `src/assets/styles/main.css`

**Issue: API calls fail in dev mode**
- **Check**: Is `useMock` set to `true`? Mock data should work without backend
- **Check**: If using real API, is backend running on `localhost:8080`?

**Issue: Dark mode not persisting**
- **Check**: localStorage access (may fail in incognito mode)
- **Check**: `useUiStore` is properly initialized in `App.vue`

## Project Context

This is a **portfolio/side project** showcasing:
- Modern Vue 3 best practices
- TypeScript strict mode
- Clean architecture with separation of concerns
- Progressive enhancement (works without backend)
- Production-ready code quality

The frontend is **100% complete and functional**. Backend implementation can follow the API spec when ready.
