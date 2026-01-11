# Backend API 인터페이스 정의서

> **개발새발** 프론트엔드를 위한 Spring Boot Backend API 명세서

## 📋 목차

1. [개요](#개요)
2. [공통 사항](#공통-사항)
3. [Posts API](#posts-api)
4. [Companies API](#companies-api)
5. [Tags API](#tags-api)
6. [Categories API](#categories-api)
7. [데이터 모델](#데이터-모델)

---

## 개요

### Base URL
```
http://localhost:8080/api
```

### 인증
현재 버전에서는 인증이 필요하지 않습니다. (향후 추가 예정)

### Content-Type
```
Content-Type: application/json
```

---

## 공통 사항

### 응답 형식

**1. 페이징 응답 (목록 조회)**

모든 목록 조회 API는 Spring Boot의 Page 형식을 사용합니다:

```json
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

| 필드 | 타입 | 설명 |
|------|------|------|
| content | Array | 실제 데이터 배열 |
| page | number | 현재 페이지 번호 (0부터 시작) |
| size | number | 페이지 크기 |
| totalElements | number | 전체 데이터 개수 |
| totalPages | number | 전체 페이지 수 |
| last | boolean | 마지막 페이지 여부 |
| first | boolean | 첫 페이지 여부 |

**2. 단일 객체 응답 (상세 조회)**

상세 조회 API는 객체를 직접 반환합니다:

```json
{
  "id": 1,
  "name": "...",
  ...
}
```

**3. 배열 응답 (특수 케이스)**

인기 태그 등 제한된 개수의 목록은 배열을 직접 반환합니다:

```json
[
  { "id": 1, "name": "..." },
  { "id": 2, "name": "..." }
]
```

**4. 에러 응답**

```json
{
  "timestamp": "2024-03-15T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "포스트를 찾을 수 없습니다",
  "path": "/api/posts/999"
}
```

> Spring Boot의 기본 에러 응답 형식을 사용합니다.

---

## Posts API

### 1. 포스트 목록 조회

```
GET /api/posts
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|----------|------|------|--------|------|
| page | number | X | 0 | 페이지 번호 (0부터 시작) |
| size | number | X | 12 | 페이지 크기 |
| companyId | number | X | - | 특정 기업 필터 |
| tagId | number | X | - | 특정 태그 필터 |
| categoryId | number | X | - | 특정 카테고리 필터 |
| search | string | X | - | 검색어 (제목, 요약에서 검색) |
| sort | string | X | latest | 정렬 (latest, popular) |

**Response:** 페이징 응답 (Post[])

```json
{
  "content": [
    {
      "id": 1,
      "title": "Spring Cloud Gateway를 이용한 API Gateway 구축기",
      "summary": "MSA 환경에서 Spring Cloud Gateway를 도입하고...",
      "url": "https://techblog.woowahan.com/...",
      "publishedAt": "2024-03-15T09:00:00Z",
      "createdAt": "2024-03-15T09:00:00Z",
      "updatedAt": "2024-03-15T09:00:00Z",
      "companyId": 1,
      "company": {
        "id": 1,
        "name": "Woowa Bros",
        "nameKo": "배달의민족",
        "slug": "woowabros",
        "logoUrl": "https://...",
        "blogUrl": "https://techblog.woowahan.com"
      },
      "categoryId": 1,
      "category": {
        "id": 1,
        "name": "개발",
        "slug": "development",
        "icon": "💻"
      },
      "tags": [
        {
          "id": 1,
          "name": "Backend",
          "slug": "backend"
        }
      ],
      "viewCount": 1523,
      "thumbnailUrl": null
    }
  ],
  "page": 0,
  "size": 12,
  "totalElements": 100,
  "totalPages": 9,
  "last": false,
  "first": true
}
```

### 2. 포스트 상세 조회

```
GET /api/posts/{id}
```

**Path Parameters:**
- `id` (number, required): 포스트 ID

**Response:** 단일 객체 (Post)

```json
{
  "id": 1,
  "title": "Spring Cloud Gateway를 이용한 API Gateway 구축기",
  "summary": "MSA 환경에서 Spring Cloud Gateway를 도입하고...",
  "url": "https://techblog.woowahan.com/...",
  "publishedAt": "2024-03-15T09:00:00Z",
  "createdAt": "2024-03-15T09:00:00Z",
  "updatedAt": "2024-03-15T09:00:00Z",
  "companyId": 1,
  "company": {
    "id": 1,
    "name": "Woowa Bros",
    "nameKo": "배달의민족",
    "slug": "woowabros",
    "logoUrl": "https://...",
    "blogUrl": "https://techblog.woowahan.com"
  },
  "categoryId": 1,
  "category": {
    "id": 1,
    "name": "개발",
    "slug": "development",
    "icon": "💻"
  },
  "tags": [
    {
      "id": 1,
      "name": "Backend",
      "slug": "backend"
    }
  ],
  "viewCount": 1523,
  "thumbnailUrl": null
}
```

### 3. 기업별 포스트 조회

```
GET /api/companies/{companyId}/posts
```

**Path Parameters:**
- `companyId` (number, required): 기업 ID

**Query Parameters:** (포스트 목록 조회와 동일)

**Response:** 페이징 응답 (Post[])

### 4. 태그별 포스트 조회

```
GET /api/tags/{tagSlug}/posts
```

**Path Parameters:**
- `tagSlug` (string, required): 태그 슬러그 (예: "backend", "frontend")

**Query Parameters:** (포스트 목록 조회와 동일)

**Response:** 페이징 응답 (Post[])

### 5. 카테고리별 포스트 조회

```
GET /api/categories/{categoryId}/posts
```

**Path Parameters:**
- `categoryId` (number, required): 카테고리 ID

**Query Parameters:** (포스트 목록 조회와 동일)

**Response:** 페이징 응답 (Post[])

### 6. 포스트 검색

```
GET /api/posts/search
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| q | string | O | 검색어 |
| page | number | X | 페이지 번호 |
| size | number | X | 페이지 크기 |

**Response:** 페이징 응답 (Post[])

---

## Companies API

### 1. 기업 목록 조회

```
GET /api/companies
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|----------|------|------|--------|------|
| page | number | X | 0 | 페이지 번호 |
| size | number | X | 20 | 페이지 크기 |
| isActive | boolean | X | true | 활성 기업만 조회 |

**Response:** 페이징 응답 (Company[])

```json
{
  "content": [
    {
      "id": 1,
      "name": "Woowa Bros",
      "nameKo": "배달의민족",
      "slug": "woowabros",
      "description": "우아한형제들 기술 블로그",
      "logoUrl": "https://techblog.woowahan.com/...",
      "blogUrl": "https://techblog.woowahan.com",
      "color": "#2AC1BC",
      "isActive": true,
      "createdAt": "2024-01-01T00:00:00Z",
      "updatedAt": "2024-01-01T00:00:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 8,
  "totalPages": 1,
  "last": true,
  "first": true
}
```

### 2. 기업 상세 조회

```
GET /api/companies/{id}
```

**Path Parameters:**
- `id` (number, required): 기업 ID

**Response:** 단일 객체 (Company)

```json
{
  "id": 1,
  "name": "Woowa Bros",
  "nameKo": "배달의민족",
  "slug": "woowabros",
  "description": "우아한형제들 기술 블로그",
  "logoUrl": "https://...",
  "blogUrl": "https://techblog.woowahan.com",
  "color": "#2AC1BC",
  "isActive": true,
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

---

## Tags API

### 1. 태그 목록 조회

```
GET /api/tags
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|----------|------|------|--------|------|
| page | number | X | 0 | 페이지 번호 (0부터 시작) |
| size | number | X | 50 | 페이지 크기 |
| sort | string | X | count | 정렬 (count: 사용 빈도순, name: 이름순) |

**Response:** 페이징 응답 (Tag[])

```json
{
  "content": [
    {
      "id": 1,
      "name": "Backend",
      "slug": "backend",
      "count": 145
    },
    {
      "id": 2,
      "name": "Frontend",
      "slug": "frontend",
      "count": 98
    }
  ],
  "page": 0,
  "size": 50,
  "totalElements": 45,
  "totalPages": 1,
  "last": true,
  "first": true
}
```

### 2. 인기 태그 조회

```
GET /api/tags/popular
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|----------|------|------|--------|------|
| limit | number | X | 10 | 조회할 태그 개수 |

**Response:** 배열 응답 (Tag[])

```json
[
  {
    "id": 1,
    "name": "Backend",
    "slug": "backend",
    "count": 145
  },
  {
    "id": 2,
    "name": "Frontend",
    "slug": "frontend",
    "count": 98
  }
]
```

---

## Categories API

### 1. 카테고리 목록 조회

```
GET /api/categories
```

**Query Parameters:**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|----------|------|------|--------|------|
| page | number | X | 0 | 페이지 번호 (0부터 시작) |
| size | number | X | 20 | 페이지 크기 |
| sort | string | X | name | 정렬 (name: 이름순, count: 포스트 수순) |

**Response:** 페이징 응답 (Category[])

```json
{
  "content": [
    {
      "id": 1,
      "name": "개발",
      "slug": "development",
      "description": "개발 관련 포스트",
      "icon": "💻",
      "color": "#3b82f6",
      "count": 250
    },
    {
      "id": 2,
      "name": "인프라",
      "slug": "infrastructure",
      "description": "인프라 및 DevOps",
      "icon": "🏗️",
      "color": "#10b981",
      "count": 120
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 8,
  "totalPages": 1,
  "last": true,
  "first": true
}
```

### 2. 카테고리 상세 조회

```
GET /api/categories/{id}
```

**Path Parameters:**
- `id` (number, required): 카테고리 ID

**Response:** 단일 객체 (Category)

```json
{
  "id": 1,
  "name": "개발",
  "slug": "development",
  "description": "개발 관련 포스트",
  "icon": "💻",
  "color": "#3b82f6",
  "count": 250
}
```

---

## 데이터 모델

### Post (포스트)

```typescript
interface Post {
  id: number                    // 포스트 ID
  title: string                 // 제목
  summary?: string              // 요약 (선택)
  content?: string              // 내용 (선택, 향후 사용)
  url: string                   // 원문 URL
  publishedAt: string           // 발행일 (ISO 8601)
  createdAt: string             // 생성일 (ISO 8601)
  updatedAt: string             // 수정일 (ISO 8601)
  companyId: number             // 기업 ID
  company?: Company             // 기업 정보 (조인)
  categoryId?: number           // 카테고리 ID (선택)
  category?: Category           // 카테고리 정보 (조인)
  tags: Tag[]                   // 태그 배열
  viewCount?: number            // 조회수 (선택)
  thumbnailUrl?: string         // 썸네일 URL (선택)
}
```

### Company (기업)

```typescript
interface Company {
  id: number                    // 기업 ID
  name: string                  // 영문 이름
  nameKo: string                // 한글 이름
  slug: string                  // URL 슬러그 (고유)
  description?: string          // 설명 (선택)
  logoUrl: string               // 로고 URL
  blogUrl: string               // 블로그 URL
  color?: string                // 대표 색상 (HEX) (선택)
  isActive: boolean             // 활성화 여부
  createdAt: string             // 생성일 (ISO 8601)
  updatedAt: string             // 수정일 (ISO 8601)
}
```

### Tag (태그)

```typescript
interface Tag {
  id: number                    // 태그 ID
  name: string                  // 태그 이름
  slug: string                  // URL 슬러그 (고유)
  count?: number                // 포스트 개수 (선택)
}
```

### Category (카테고리)

```typescript
interface Category {
  id: number                    // 카테고리 ID
  name: string                  // 카테고리 이름
  slug: string                  // URL 슬러그 (고유)
  description?: string          // 설명 (선택)
  icon?: string                 // 아이콘 (이모지) (선택)
  color?: string                // 대표 색상 (HEX) (선택)
  count?: number                // 포스트 개수 (선택)
}
```

---

## 데이터베이스 스키마 예시

### posts 테이블

posts 테이블
CREATE TABLE posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    summary TEXT,
    content TEXT,
    url VARCHAR(1000) NOT NULL UNIQUE,
    published_at TIMESTAMP NOT NULL,
    company_id BIGINT NOT NULL,
    category_id BIGINT,
    view_count INT DEFAULT 0,
    thumbnail_url VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_posts_company
        FOREIGN KEY (company_id) REFERENCES companies(id),

    CONSTRAINT fk_posts_category
        FOREIGN KEY (category_id) REFERENCES categories(id),

    INDEX idx_posts_published_at (published_at DESC),
    INDEX idx_posts_company_id (company_id),
    INDEX idx_posts_category_id (category_id),
    INDEX idx_posts_view_count (view_count DESC)
);

companies 테이블
CREATE TABLE companies (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    name_ko VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    logo_url VARCHAR(1000) NOT NULL,
    blog_url VARCHAR(1000) NOT NULL,
    color VARCHAR(7),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_companies_is_active (is_active)
);

tags 테이블
CREATE TABLE tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

categories 테이블
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    icon VARCHAR(10),
    color VARCHAR(7),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

post_tags 테이블 (Posts ↔ Tags 다대다 관계)
CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,

    PRIMARY KEY (post_id, tag_id),

    CONSTRAINT fk_post_tags_post
        FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,

    CONSTRAINT fk_post_tags_tag
        FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);
---

## JPA Entity 예시

### Post Entity

```java
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 1000, unique = true)
    private String url;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnailUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters, Setters, Constructors...
}
```

---

## 구현 우선순위

### Phase 1 (필수)
1. ✅ Companies API (기업 목록, 상세)
2. ✅ Posts API (목록, 상세, 기업별 조회)
3. ✅ Tags API (목록, 인기 태그)

### Phase 2 (중요)
4. ✅ Categories API (목록, 카테고리별 포스트)
5. ✅ Posts Search API (검색)
6. ✅ Posts Filtering (복합 필터링)

### Phase 3 (향후)
7. ⬜ 회원가입/로그인
8. ⬜ 북마크 API (서버 동기화)
9. ⬜ 댓글 API
10. ⬜ 추천 시스템

---

## 크롤러 구현 가이드

### RSS 피드 크롤링

대부분의 기술 블로그는 RSS 피드를 제공합니다:

```java
@Service
public class RssCrawlerService {

    public List<Post> crawlRss(String rssUrl, Company company) {
        // Rome 라이브러리 사용
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL(rssUrl)));

        return feed.getEntries().stream()
            .map(entry -> convertToPost(entry, company))
            .collect(Collectors.toList());
    }

    private Post convertToPost(SyndEntry entry, Company company) {
        Post post = new Post();
        post.setTitle(entry.getTitle());
        post.setSummary(entry.getDescription().getValue());
        post.setUrl(entry.getLink());
        post.setPublishedAt(convertToLocalDateTime(entry.getPublishedDate()));
        post.setCompany(company);
        // 태그, 카테고리 추출 로직...
        return post;
    }
}
```

### 스케줄링

```java
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private RssCrawlerService crawlerService;

    // 매일 오전 6시에 크롤링
    @Scheduled(cron = "0 0 6 * * *")
    public void crawlAllCompanies() {
        List<Company> companies = companyRepository.findByIsActive(true);

        for (Company company : companies) {
            try {
                List<Post> posts = crawlerService.crawlRss(company.getRssUrl(), company);
                postRepository.saveAll(posts);
            } catch (Exception e) {
                log.error("Failed to crawl: " + company.getName(), e);
            }
        }
    }
}
```

---

## 테스트용 초기 데이터

```sql
-- Companies
INSERT INTO companies (name, name_ko, slug, logo_url, blog_url, color, is_active) VALUES
('Woowa Bros', '배달의민족', 'woowabros', 'https://...', 'https://techblog.woowahan.com', '#2AC1BC', true),
('Kakao', '카카오', 'kakao', 'https://...', 'https://tech.kakao.com', '#FEE500', true),
('Toss', '토스', 'toss', 'https://...', 'https://toss.tech', '#0064FF', true);

-- Categories
INSERT INTO categories (name, slug, description, icon, color) VALUES
('개발', 'development', '개발 관련 포스트', '💻', '#3b82f6'),
('인프라', 'infrastructure', '인프라 및 DevOps', '🏗️', '#10b981'),
('데이터', 'data', '데이터 엔지니어링', '📊', '#f59e0b');

-- Tags
INSERT INTO tags (name, slug) VALUES
('Backend', 'backend'),
('Frontend', 'frontend'),
('DevOps', 'devops');
```

---

## 참고 사항

- 모든 날짜는 **ISO 8601 형식** 사용 (예: `2024-03-15T09:00:00Z`)
- slug는 **URL-safe한 문자열**로 생성 (소문자, 하이픈만 사용)
- 페이지 번호는 **0부터 시작**
- 정렬 옵션: `latest` (최신순), `popular` (인기순)
- 검색은 **제목**과 **요약**에서 수행

---

**작성일:** 2024-12-29
**버전:** 1.0
**문의:** GitHub Issues
