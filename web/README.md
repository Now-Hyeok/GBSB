# 개발새발 - Tech Blog Hub (Frontend)

> 국내외 테크 기업들의 기술 블로그를 한 곳에서 모아보는 큐레이션 허브

## 📋 프로젝트 개요

개발새발은 배달의민족, 카카오, 토스, 네이버, 라인, 쿠팡, 당근마켓, 야놀자 등 주요 IT 기업의 기술 블로그 포스트를 한 곳에서 모아볼 수 있는 웹 애플리케이션입니다.

**이 저장소는 Frontend만 포함합니다.** Backend API는 별도의 Spring Boot 프로젝트로 구성되어 있습니다.

## 🛠 기술 스택

### Core
- **Vue 3** - Composition API
- **TypeScript** - 타입 안정성
- **Vite** - 빌드 도구

### UI & Styling
- **Tailwind CSS** - 유틸리티 기반 스타일링
- 모바일 우선 반응형 디자인
- 다크 모드 지원

### State Management & Data
- **Pinia** - Vue 3 공식 상태 관리
- **Vue Router** - SPA 라우팅
- **Axios** - HTTP 클라이언트

### Developer Experience
- **@vueuse/core** - Vue Composition 유틸리티
- **@vueuse/head** - SEO 메타 태그 관리

## 📁 프로젝트 구조

```
src/
├── assets/              # 정적 리소스
│   └── styles/          # 글로벌 스타일 (Tailwind CSS)
├── components/          # Vue 컴포넌트
│   ├── common/          # 재사용 가능한 공통 컴포넌트
│   │   ├── PostCard.vue
│   │   ├── CompanyBadge.vue
│   │   ├── TagChip.vue
│   │   ├── LoadingSpinner.vue
│   │   ├── EmptyState.vue
│   │   ├── Pagination.vue
│   │   └── InfiniteScroll.vue
│   └── layout/          # 레이아웃 컴포넌트
│       ├── TheHeader.vue
│       ├── TheFooter.vue
│       └── SearchModal.vue
├── pages/               # 페이지 컴포넌트
│   ├── HomePage.vue
│   ├── CompanyPage.vue
│   ├── PostDetailPage.vue
│   ├── TagPage.vue
│   ├── AboutPage.vue
│   └── NotFoundPage.vue
├── router/              # Vue Router 설정
│   └── index.ts
├── store/               # Pinia 스토어
│   ├── index.ts
│   ├── usePostStore.ts
│   ├── useCompanyStore.ts
│   ├── useTagStore.ts
│   └── useUiStore.ts
├── services/            # API 서비스 레이어
│   ├── api.ts           # Axios 클라이언트 및 API 호출
│   └── mockData.ts      # 개발용 Mock 데이터
├── types/               # TypeScript 타입 정의
│   ├── index.ts
│   └── env.d.ts
├── App.vue              # 루트 컴포넌트
└── main.ts              # 앱 엔트리 포인트
```

## 🚀 시작하기

### 사전 요구사항

- Node.js 18+
- npm 또는 yarn

### 설치 및 실행

```bash
# 의존성 설치
npm install

# 개발 서버 실행 (http://localhost:3001)
npm run dev

# 프로덕션 빌드
npm run build

# 빌드 미리보기
npm run preview
```

## 🔌 Backend API 연동

### API 엔드포인트 구조

이 프론트엔드는 다음과 같은 REST API를 기대합니다:

#### Posts
- `GET /api/posts` - 포스트 목록 조회 (페이징, 필터, 정렬)
- `GET /api/posts/{id}` - 포스트 상세 조회
- `GET /api/posts/search?q={query}` - 포스트 검색

#### Companies
- `GET /api/companies` - 기업 목록 조회
- `GET /api/companies/{id}` - 기업 상세 조회
- `GET /api/companies/{id}/posts` - 특정 기업의 포스트 목록

#### Tags
- `GET /api/tags` - 태그 목록 조회
- `GET /api/tags/popular` - 인기 태그 조회
- `GET /api/tags/{slug}/posts` - 특정 태그의 포스트 목록

### API 응답 형식

```typescript
// 단일 응답
{
  "success": true,
  "data": { ... }
}

// 페이징 응답
{
  "content": [...],
  "page": 0,
  "size": 12,
  "totalElements": 100,
  "totalPages": 9,
  "last": false,
  "first": true
}
```

### Mock Data 사용

Backend가 준비되지 않은 경우, Mock Data로 개발 가능합니다:

```typescript
// src/services/api.ts
const api = new ApiService()
api.setUseMock(true)  // Mock 데이터 사용
api.setUseMock(false) // 실제 API 사용
```

기본적으로 `src/services/api.ts`의 `useMock` 플래그가 `true`로 설정되어 있어 즉시 개발을 시작할 수 있습니다.

### 환경 변수 설정

`.env` 파일에서 API 베이스 URL을 설정할 수 있습니다:

```env
# .env.development
VITE_API_BASE_URL=http://localhost:8080/api

# .env.production
VITE_API_BASE_URL=/api
```

## 🎨 주요 기능

### 1. 홈 페이지 (/)
- 최신/인기 포스트 목록
- 기업별 필터 탭
- 무한 스크롤
- 정렬 (최신순/인기순)

### 2. 기업 페이지 (/company/:id)
- 기업 정보 헤더
- 해당 기업의 포스트 목록
- 공식 블로그 링크

### 3. 포스트 상세 (/post/:id)
- 포스트 제목, 요약, 메타 정보
- 원문 링크
- 관련 포스트 추천
- 태그 표시

### 4. 태그 페이지 (/tag/:slug)
- 특정 태그의 포스트 목록

### 5. About 페이지 (/about)
- 서비스 소개
- 기술 스택
- 데이터 출처 및 면책 사항

### 6. 공통 기능
- 통합 검색 (모달)
- 다크 모드
- 반응형 디자인
- SEO 최적화

## 🧩 컴포넌트 가이드

### PostCard
포스트 카드 컴포넌트

```vue
<PostCard :post="post" />
```

### CompanyBadge
기업 배지 컴포넌트

```vue
<CompanyBadge :company="company" />
```

### TagChip
태그 칩 컴포넌트

```vue
<TagChip :tag="tag" size="sm" :show-count="true" />
```

### InfiniteScroll
무한 스크롤 컴포넌트

```vue
<InfiniteScroll
  :loading="loading"
  :has-more="hasMore"
  @load-more="loadMore"
>
  <!-- 컨텐츠 -->
</InfiniteScroll>
```

## 📦 상태 관리

### Post Store
```typescript
import { usePostStore } from '@/store/usePostStore'

const postStore = usePostStore()

// 포스트 조회
await postStore.fetchPosts({ page: 0, size: 12 })

// 기업별 포스트
await postStore.fetchPostsByCompany(companyId, params)

// 태그별 포스트
await postStore.fetchPostsByTag(tagSlug, params)

// 검색
await postStore.searchPosts(query, params)
```

### Company Store
```typescript
import { useCompanyStore } from '@/store/useCompanyStore'

const companyStore = useCompanyStore()
await companyStore.fetchCompanies()
```

### UI Store
```typescript
import { useUiStore } from '@/store/useUiStore'

const uiStore = useUiStore()
uiStore.toggleDarkMode()
```

## 🎯 확장 가이드

### 새로운 페이지 추가

1. `src/pages/`에 페이지 컴포넌트 생성
2. `src/router/index.ts`에 라우트 추가

```typescript
{
  path: '/new-page',
  name: 'NewPage',
  component: () => import('@/pages/NewPage.vue')
}
```

### 새로운 API 엔드포인트 추가

1. `src/types/index.ts`에 타입 정의
2. `src/services/api.ts`에 API 메서드 추가
3. 필요시 `src/services/mockData.ts`에 Mock 데이터 추가

### 새로운 Store 추가

```typescript
// src/store/useNewStore.ts
import { defineStore } from 'pinia'

export const useNewStore = defineStore('new', () => {
  // state, getters, actions
})
```

## 🌙 다크 모드

다크 모드는 Tailwind의 `dark:` variant와 `useUiStore`로 구현되어 있습니다.

```vue
<!-- 다크 모드 대응 스타일 -->
<div class="bg-white dark:bg-dark-bg text-gray-900 dark:text-white">
  컨텐츠
</div>
```

다크 모드 상태는 localStorage에 자동 저장됩니다.

## 📱 반응형 디자인

모바일 우선(Mobile-first) 접근 방식을 사용합니다:

```vue
<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
  <!-- 모바일: 1열, 태블릿: 2열, 데스크톱: 3열 -->
</div>
```

## 🔧 개발 팁

### 1. Mock 데이터 vs 실제 API 전환

```typescript
// src/services/api.ts
this.useMock = false  // 실제 API 사용 시
```

### 2. Hot Module Replacement

Vite는 자동으로 HMR을 지원합니다. 코드 변경 시 자동 새로고침됩니다.

### 3. TypeScript 타입 체크

```bash
npm run build  # 빌드 시 타입 체크
```

### 4. Tailwind CSS IntelliSense

VSCode의 "Tailwind CSS IntelliSense" 확장을 설치하면 자동완성을 사용할 수 있습니다.

## 📄 라이선스

This project is licensed under the MIT License.

## 🤝 기여

이슈 및 풀 리퀘스트는 언제나 환영합니다!

## 📞 문의

프로젝트 관련 문의사항은 GitHub Issues를 이용해주세요.

---

**Made with ❤️ for Developers**
