# 개발새발 (GBSB) - 최종 완성 개발계획서

> **작성일**: 2026-03-03  
> **프로젝트**: 개발새발 (Dev Blog Hub) - 국내 IT 기업 기술 블로그 큐레이션 플랫폼  
> **목표**: 프로젝트를 프로덕션 가능한 상태로 완성

---

## 1. 현황 분석 요약

### 1.1 프로젝트 구조

```
GBSB/
├── gbsb-web/           # Vue 3 + TypeScript 프론트엔드 (✅ 거의 완성)
├── gbsb-api/           # Spring Boot 4.0.1 백엔드 (🔶 골격 존재, 구현 미완)
├── docs/               # API 명세서
├── docker-compose.yml  # Docker 배포 설정 (✅ 존재)
└── CLAUDE.md           # 개발 가이드 (⚠️ 경로 불일치)
```

### 1.2 기술 스택 현황

| 영역 | 기술 | 상태 |
|------|------|------|
| **프론트엔드** | Vue 3.4 + TypeScript 5.4 + Vite 5.2 + Tailwind 3.4 + Pinia 2.1 | ✅ 완성 |
| **백엔드** | Spring Boot 4.0.1 + Java 21 + JPA + Jsoup | 🔶 부분 구현 |
| **데이터베이스** | Oracle Cloud Autonomous DB | ✅ 연결 설정 완료 |
| **배포** | Docker + Docker Compose + Nginx 리버스 프록시 | ✅ 기본 설정 완료 |

### 1.3 구현 상태 매트릭스

#### 프론트엔드 (gbsb-web) — 완성도: **95%**

| 구분 | 항목 | 상태 |
|------|------|------|
| **Pages** | HomePage, CompanyPage, PostDetailPage, TagPage, CategoryPage, BookmarksPage, AboutPage, NotFoundPage | ✅ 8/8 완성 |
| **Components** | PostCard, CompanyBadge, TagChip, CategoryCard, ShareButtons, RecentPosts, LoadingSpinner, EmptyState, Pagination, InfiniteScroll, AdBanner | ✅ 11/11 완성 |
| **Layout** | TheHeader, TheFooter, SearchModal | ✅ 3/3 완성 |
| **Stores** | usePostStore, useCompanyStore, useTagStore, useCategoryStore, useBookmarkStore, useHistoryStore, useUiStore | ✅ 7/7 완성 |
| **Services** | api.ts (Dual-mode), mockData.ts | ✅ 완성 |
| **Router** | 8 routes with lazy loading | ✅ 완성 |
| **Types** | Post, Company, Tag, Category + API/UI types | ✅ 완성 |

#### 백엔드 (gbsb-api) — 완성도: **35%**

| 구분 | 항목 | 상태 | 상세 |
|------|------|------|------|
| **Entity** | Post, Company, Tag, Category | ✅ 완성 | JPA 엔티티, 관계 매핑 완료 |
| **Entity** | User, OAuthAccount | ✅ 완성 | OAuth 지원 설계 포함 |
| **Enum** | CompanyName, PostCategory, PostTag, PostSortable, UserRole, UserStatus, OAuthProvider | ✅ 완성 | 단, CompanyName은 3개(배민/카카오/토스)만 정의 |
| **DTO** | PostDto, CompanyDto, TagDto, CategoryDto | ✅ 완성 | |
| **DTO Request** | PostRequestDto, PostSearchRequestDto, PageRequestDto, CompanyRequestDto | ✅ 완성 | 단, PageRequestDto에 Getter/Setter 누락 |
| **Repository** | PostRepository | ✅ 완성 | 커스텀 쿼리 포함 |
| **Repository** | CompanyRepository, TagRepository, CategoryRepository, UserRepository | ✅ 기본 | 최소 메서드만 존재 |
| **Controller** | PostController | ✅ 완성 | 6개 엔드포인트 (목록/상세/기업별/태그별/카테고리별/검색) |
| **Controller** | CompanyController | ✅ 완성 | 2개 엔드포인트 (목록/상세) |
| **Controller** | TagController | ✅ 완성 | 2개 엔드포인트 (목록/인기) |
| **Controller** | CategoryController | ✅ 완성 | 2개 엔드포인트 (목록/상세) |
| **Controller** | UserController | ❌ 비어있음 | 껍데기만 존재 |
| **Service** | PostServiceImpl | ✅ 완성 | CRUD + 필터링 + 검색 + 조회수 증가 |
| **Service** | CompanyServiceImpl | ❌ 비어있음 | 모든 메서드 `return null` |
| **Service** | TagServiceImpl | ❌ 비어있음 | 모든 메서드 `return null` / `List.of()` |
| **Service** | CategoryServiceImpl | ❌ 비어있음 | 모든 메서드 `return null` |
| **Service** | UserService | ❌ 인터페이스만 | 구현체 없음 |
| **Crawler** | BaeMinCrawler | ❌ 비어있음 | Jsoup connect만 있고 파싱 로직 없음 |
| **Crawler** | KakaoCrawler | 🔶 부분 구현 | 파싱 로직 있으나 실제 동작 미검증 |
| **Crawler** | TossCrawler | 🔶 부분 구현 | 파싱 로직 있으나 SPA 특성상 동작 불확실 |
| **Scheduler** | PostScheduler | ✅ 완성 | 10분 주기 크롤링 (크롤러 결과 저장 로직 누락) |
| **Config** | WebConfig | ❌ 비어있음 | CORS 미설정 |
| **Config** | WebSecurityConfig | ❌ 비어있음 | Security 필터 체인 없음 |
| **Config** | ModelMapperConfig | ✅ 완성 | |
| **Security** | JwtProvider | ❌ 비어있음 | |
| **Exception** | GlobalExceptionHandler | 🔶 최소 구현 | PostNotFoundException만 처리 |

---

## 2. 핵심 이슈 목록

### 🔴 Critical (반드시 해결)

| # | 이슈 | 영향 |
|---|------|------|
| C1 | **API 경로 불일치** — Controller들이 `/posts`, `/companies` 사용. 프론트엔드는 `/api/posts` 기대. `context-path: /`로 설정됨 | 프론트-백 연동 불가 |
| C2 | **CompanyServiceImpl 미구현** — `return null` | 기업 목록/상세 API 동작 불가 |
| C3 | **TagServiceImpl 미구현** — `return null` / `List.of()` | 태그 API 동작 불가 |
| C4 | **CategoryServiceImpl 미구현** — `return null` | 카테고리 API 동작 불가 |
| C5 | **WebSecurityConfig 비어있음** — Security 필터 체인 없음 | API 접근 차단될 가능성 (Spring Security 의존성은 있음) |
| C6 | **CORS 미설정** — WebConfig의 CORS 매핑이 비어있음 | 프론트엔드에서 API 호출 차단 |
| C7 | **크롤러 미완성** — BaeMinCrawler 파싱 로직 없음, 나머지 크롤러 미검증 | 실제 데이터 수집 불가 |
| C8 | **스케줄러 결과 미저장** — PostScheduler가 crawlPosts() 호출만 하고 결과를 DB에 저장하지 않음 | 크롤링 데이터 유실 |
| C9 | **PageRequestDto Getter 누락** — Lombok @Getter/@Setter 어노테이션 없음 | TagController, CategoryController 바인딩 실패 |

### 🟡 Important (조속히 해결)

| # | 이슈 | 영향 |
|---|------|------|
| I1 | **DB dialect 불일치** — application.yaml에 `OracleDialect` 하드코딩. 프로파일별 분리 필요 | 로컬 개발 환경 전환 어려움 |
| I2 | **Spring Boot 4.0.1 사용** — 2026년 3월 기준 최신이지만, 일부 라이브러리와 호환성 이슈 가능 | 빌드 실패 가능성 |
| I3 | **CompanyName enum 부족** — 배민/카카오/토스 3개만. 네이버/라인/쿠팡/당근/야놀자 누락 | 크롤러 확장 시 매번 수정 필요 |
| I4 | **초기 데이터 없음** — companies, categories, tags 테이블 seed 데이터 SQL 없음 | 서버 최초 실행 시 빈 상태 |
| I5 | **PostCategory/PostTag enum 미완성** — 1개씩만 정의 (DEVELOPMENT, BACKEND) | 카테고리/태그 분류 불가 |
| I6 | **docker-compose.yml 비밀정보 노출** — DB 비밀번호 평문 기재, wallet 경로 로컬 하드코딩 | 보안 취약 |
| I7 | **CLAUDE.md 경로 불일치** — `web/` 경로를 참조하지만 실제는 `gbsb-web/` | 개발 가이드 혼동 |
| I8 | **README.md 대부분 주석 처리** — 프로젝트 소개가 HTML 주석으로 감싸져 있음 | |

### 🟢 Enhancement (개선사항)

| # | 이슈 | 설명 |
|---|------|------|
| E1 | 테스트 코드 부재 | 프론트/백 모두 테스트 미작성 (ApiApplicationTests + BaeMinCrawlerTest 껍데기만) |
| E2 | 검색 고도화 | 현재 LIKE 검색. Full-text search 또는 QueryDSL 도입 고려 |
| E3 | AdSense 통합 | AdBanner 컴포넌트는 있으나 실제 연동 미완 |
| E4 | KakaoTalk 공유 | ShareButtons에서 Kakao SDK 통합 미완 |
| E5 | 프론트엔드 ESLint | eslint 스크립트는 있으나 .eslintrc 설정 파일 부재 |
| E6 | `@PrePersist` / `@PreUpdate` 미적용 | Entity의 createdAt, updatedAt 자동 갱신 로직 없음 |

---

## 3. 개발 계획

### Phase 1: 백엔드 핵심 완성 (MVP 동작) — 예상 3~5일

> 목표: 프론트엔드와 백엔드가 연동되어 실제 데이터로 서비스가 동작하는 상태

#### 1-1. 인프라 기반 수정 (Day 1 AM)

- [ ] **C1: API 경로 표준화**
  - `application.yaml`에 `server.servlet.context-path: /api` 추가
  - 또는 모든 Controller의 `@RequestMapping`을 `/api/xxx`로 변경
  - **권장**: context-path 방식 (컨트롤러 수정 최소화)

- [ ] **C5: Spring Security 설정**
  ```java
  @Configuration
  @EnableWebSecurity
  public class WebSecurityConfig {
      @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
              .csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/**").permitAll()
                  .anyRequest().authenticated()
              )
              .sessionManagement(session -> 
                  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              );
          return http.build();
      }
  }
  ```

- [ ] **C6: CORS 설정**
  ```java
  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/api/**")
          .allowedOrigins("http://localhost:3001", "http://localhost:8080")
          .allowedMethods("GET", "POST", "PUT", "DELETE")
          .allowedHeaders("*");
  }
  ```

- [ ] **C9: PageRequestDto Getter/Setter 추가**
  - `@Getter`, `@Setter` 어노테이션 추가

- [ ] **E6: BaseEntity 추출 — `@PrePersist` / `@PreUpdate` 적용**
  ```java
  @MappedSuperclass
  public abstract class BaseEntity {
      @Column(name = "created_at", updatable = false)
      private LocalDateTime createdAt;
      
      @Column(name = "updated_at")
      private LocalDateTime updatedAt;
      
      @PrePersist
      protected void onCreate() { 
          createdAt = updatedAt = LocalDateTime.now(); 
      }
      
      @PreUpdate
      protected void onUpdate() { 
          updatedAt = LocalDateTime.now(); 
      }
  }
  ```

#### 1-2. Service 구현 (Day 1 PM ~ Day 2 AM)

- [ ] **C2: CompanyServiceImpl 구현**
  - `getCompanies()`: Pageable 변환 → `companyRepository.findAll(pageable)` → PagedModel 변환
  - `getCompanyDetail()`: `findById()` → DTO 변환
  - isActive 필터링: `companyRepository.findByIsActive(boolean, Pageable)` 추가

- [ ] **C3: TagServiceImpl 구현**
  - `getTags()`: Pageable 변환 → `tagRepository.findAll(pageable)` → PagedModel 변환
  - `getPopularTags()`: 커스텀 쿼리 필요 — `@Query("SELECT t, COUNT(p) FROM Tag t JOIN Post p WHERE p MEMBER OF ... GROUP BY t ORDER BY COUNT(p) DESC")`
  - 또는 `post_tags` 테이블에서 직접 카운트 쿼리

- [ ] **C4: CategoryServiceImpl 구현**
  - `getCategories()`: 전체 조회 + count 계산
  - `getCategoryDetail()`: `findById()`

- [ ] **CategoryController 응답 타입 수정**
  - 현재 `PagedModel<Category>` (Entity 직접 노출) → `PagedModel<CategoryDto>`로 변경

#### 1-3. 초기 데이터 준비 (Day 2 PM)

- [ ] **I4: data.sql 작성**
  ```sql
  -- Companies (8개 기업)
  INSERT INTO companies (name, name_ko, slug, description, logo_url, blog_url, color, is_active) VALUES
  ('Woowa Bros', '배달의민족', 'woowabros', '우아한형제들 기술 블로그', '...', 'https://techblog.woowahan.com', '#2AC1BC', true),
  ('Kakao', '카카오', 'kakao', '카카오 기술 블로그', '...', 'https://tech.kakao.com/blog', '#FEE500', true),
  ('Toss', '토스', 'toss', '토스 기술 블로그', '...', 'https://toss.tech', '#0064FF', true),
  ('Naver', '네이버', 'naver', '네이버 D2', '...', 'https://d2.naver.com', '#03C75A', true),
  ('Line', '라인', 'line', '라인 엔지니어링', '...', 'https://engineering.linecorp.com/ko', '#06C755', true),
  ('Coupang', '쿠팡', 'coupang', '쿠팡 기술 블로그', '...', 'https://medium.com/coupang-engineering', '#C33332', true),
  ('Karrot', '당근마켓', 'karrot', '당근마켓 기술 블로그', '...', 'https://medium.com/daangn', '#FF6F0F', true),
  ('Yanolja', '야놀자', 'yanolja', '야놀자 기술 블로그', '...', 'https://medium.com/yanolja', '#FF3478', true);

  -- Categories (8개 카테고리)
  INSERT INTO categories (name, slug, description, icon, color) VALUES
  ('개발', 'development', '소프트웨어 개발 전반', '💻', '#3b82f6'),
  ('인프라', 'infrastructure', '인프라 및 DevOps', '🏗️', '#10b981'),
  ('데이터', 'data', '데이터 엔지니어링 및 분석', '📊', '#f59e0b'),
  ('AI/ML', 'ai-ml', '인공지능 및 머신러닝', '🤖', '#8b5cf6'),
  ('프론트엔드', 'frontend', '웹/앱 프론트엔드', '🎨', '#ec4899'),
  ('백엔드', 'backend', '서버 및 API 개발', '⚙️', '#6366f1'),
  ('보안', 'security', '보안 및 인증', '🔒', '#ef4444'),
  ('문화', 'culture', '개발 문화 및 조직', '🌱', '#22c55e');

  -- Tags (주요 기술 태그)
  INSERT INTO tags (name, slug) VALUES
  ('Backend', 'backend'), ('Frontend', 'frontend'), ('DevOps', 'devops'),
  ('Kubernetes', 'kubernetes'), ('Spring', 'spring'), ('React', 'react'),
  ('Java', 'java'), ('Kotlin', 'kotlin'), ('TypeScript', 'typescript'),
  ('Python', 'python'), ('MSA', 'msa'), ('Database', 'database'),
  ('Redis', 'redis'), ('Kafka', 'kafka'), ('AWS', 'aws'),
  ('Docker', 'docker'), ('CI/CD', 'ci-cd'), ('Machine Learning', 'machine-learning'),
  ('iOS', 'ios'), ('Android', 'android');
  ```

- [ ] **I3: CompanyName enum 확장**
  - NAVER, LINE, COUPANG, KARROT, YANOLJA 추가

- [ ] **I5: PostCategory/PostTag enum 확장 또는 제거**
  - 현재 DB 기반 Category/Tag 엔티티와 중복. enum은 제거하고 DB 데이터로 통합 권장

#### 1-4. 크롤러 완성 (Day 3 ~ Day 4)

- [ ] **C7: BaeMinCrawler 구현**
  - 우아한형제들 기술 블로그(`https://techblog.woowahan.com`) HTML 구조 분석
  - 글 목록 셀렉터 확인 (DevTools 사용)
  - 제목, URL, 발행일, 태그 파싱 로직 구현
  - 중복 방지: DB에 URL unique constraint 활용

- [ ] **C7: KakaoCrawler 검증 및 수정**
  - 현재 구현된 파싱 로직이 실제 카카오 블로그와 매칭되는지 확인
  - CSS 셀렉터 업데이트

- [ ] **C7: TossCrawler 검증 및 수정**
  - Toss 블로그는 SPA(Next.js)이므로 Jsoup 직접 파싱 불가능할 수 있음
  - **대안**: RSS 피드 확인, 또는 Selenium/Playwright 도입 고려
  - 또는 API 직접 호출 방식 검토

- [ ] **추가 크롤러 구현** (우선순위별)
  1. `NaverD2Crawler` — d2.naver.com (RSS 피드 확인)
  2. `LineCrawler` — engineering.linecorp.com/ko
  3. `CoupangCrawler` — Medium RSS 활용 가능
  4. `KarrotCrawler` — Medium RSS 활용 가능
  5. `YanoljaCrawler` — Medium RSS 활용 가능

- [ ] **C8: PostScheduler 수정 — 크롤링 결과 DB 저장**
  ```java
  @Scheduled(cron = "0 0 6 * * *")  // 매일 06:00
  public void postCrawlScheduler() {
      for (PostCrawler crawler : postCrawlers) {
          try {
              List<PostDto> posts = crawler.crawlPosts();
              postService.insertAllPosts(posts);  // DB 저장 추가
              log.info("Crawled {} posts from {}", posts.size(), crawler.getCompanyName());
          } catch (Exception e) {
              log.error("Crawl failed: {}", crawler.getCompanyName(), e);
          }
      }
  }
  ```

- [ ] **크롤러 공통 로직 추출**
  - 날짜 파싱 유틸: `DateParseUtil` — KakaoCrawler/TossCrawler에서 중복 제거
  - 중복 검사 로직: URL 기반 이미 존재하는 포스트 스킵

#### 1-5. 에러 처리 보강 (Day 4)

- [ ] **GlobalExceptionHandler 확장**
  ```java
  @ExceptionHandler(EntityNotFoundException.class)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ExceptionHandler(ConstraintViolationException.class)
  @ExceptionHandler(CrawlException.class)
  @ExceptionHandler(Exception.class)  // 범용 fallback
  ```

- [ ] **PostNotFoundException에 상세 메시지 추가**
  ```java
  public class PostNotFoundException extends RuntimeException {
      public PostNotFoundException(Long id) {
          super("Post not found with id: " + id);
      }
  }
  ```

- [ ] **CompanyNotFoundException, CategoryNotFoundException 추가**

---

### Phase 2: 프론트-백 연동 및 안정화 — 예상 2~3일

> 목표: Mock 데이터를 제거하고 실제 백엔드와 완전히 연동

#### 2-1. API 응답 형식 정합성 확인 (Day 5)

- [ ] **프론트엔드 API 호출 경로 vs 백엔드 엔드포인트 매핑 검증**

  | 프론트엔드 (api.ts) | 백엔드 (Controller) | 일치 여부 |
  |---|---|---|
  | `GET /posts` | `GET /posts` (→ `/api/posts` after context-path) | ⚠️ context-path 적용 후 확인 |
  | `GET /posts/{id}` | `GET /posts/{id}` | ✅ |
  | `GET /companies/{id}/posts` | `GET /posts/companies/{companyId}` | ❌ 경로 불일치! |
  | `GET /tags/{slug}/posts` | `GET /posts/tags/{tagSlug}` | ❌ 경로 불일치! |
  | `GET /categories/{id}/posts` | `GET /posts/categories/{categoryId}` | ❌ 경로 불일치! |
  | `GET /posts/search` | `GET /posts/search` | ✅ |
  | `GET /companies` | `GET /companies/` | ✅ (trailing slash 주의) |
  | `GET /companies/{id}` | `GET /companies/{id}` | ✅ |
  | `GET /tags` | `GET /tags` | ✅ |
  | `GET /tags/popular` | `GET /tags/popular` | ✅ |
  | `GET /categories` | `GET /categories` | ✅ |
  | `GET /categories/{id}` | `GET /categories/{id}` | ✅ |

- [ ] **경로 통일 (택 1)**
  - **Option A**: 백엔드 수정 — `/posts/companies/{id}` → 별도 CompanyController에 `GET /companies/{id}/posts` 추가
  - **Option B**: 프론트엔드 수정 — api.ts 경로를 백엔드에 맞게 변경
  - **권장**: Option A (API 명세서 기준으로 RESTful 경로 유지)

#### 2-2. 응답 포맷 정합성 (Day 5)

- [ ] **PagedModel 응답 필드 확인**
  - Spring Boot의 `PagedModel`은 `content` 대신 `_embedded` + `page` 구조를 사용할 수 있음
  - 프론트엔드가 기대하는 포맷:
    ```json
    { "content": [...], "page": 0, "size": 12, "totalElements": 100, "totalPages": 9, "last": false, "first": true }
    ```
  - `PagedModel`의 실제 직렬화 결과와 프론트엔드 기대 포맷이 다를 경우 커스텀 응답 래퍼 필요

- [ ] **단일 객체 응답 래핑 검토**
  - 프론트엔드 `ApiResponse<T>`: `{ success: boolean, data: T }`
  - 백엔드는 `ResponseEntity<PostDto>`로 직접 반환
  - 불일치 시 프론트엔드의 `ApiResponse` 래퍼 제거 또는 백엔드에 래퍼 추가

#### 2-3. 프론트엔드 Mock 전환 (Day 6)

- [ ] **api.ts의 `useMock` 플래그를 환경변수로 제어**
  ```typescript
  private useMock: boolean = import.meta.env.VITE_USE_MOCK === 'true'
  ```

- [ ] **.env.development에 추가**
  ```env
  VITE_USE_MOCK=false
  ```

- [ ] **프론트엔드 → 백엔드 통합 테스트**
  - 각 페이지별 수동 테스트 체크리스트:
    - [ ] HomePage: 포스트 목록 로딩, 기업 필터, 카테고리 필터, 정렬, 무한 스크롤
    - [ ] CompanyPage: 기업 정보 + 해당 기업 포스트
    - [ ] PostDetailPage: 포스트 상세 + 조회수 증가
    - [ ] TagPage: 태그별 포스트 목록
    - [ ] CategoryPage: 카테고리별 포스트 목록
    - [ ] SearchModal: 검색 기능
    - [ ] BookmarksPage: 북마크 기능 (로컬스토리지)

#### 2-4. Vite Proxy 설정 확인 (Day 6)

- [ ] **vite.config.ts의 proxy rewrite 규칙 검증**
  - 현재: `rewrite: (path) => path.replace(/^\/api/, '/api')` — 실질적으로 변환 없음
  - context-path `/api` 적용 시: rewrite 불필요, proxy target만 유지
  - context-path `/` 유지 시: `rewrite: (path) => path.replace(/^\/api/, '')` 필요

---

### Phase 3: 품질 및 안정성 — 예상 3~5일

#### 3-1. 테스트 코드 작성 (Day 7 ~ Day 8)

- [ ] **백엔드 단위 테스트**
  - `PostServiceImplTest` — 목록 조회, 상세 조회, 필터링, 검색
  - `CompanyServiceImplTest`
  - `TagServiceImplTest`
  - `CategoryServiceImplTest`
  - 각 크롤러 테스트 (실제 사이트 호출 대신 HTML fixture 사용)

- [ ] **백엔드 통합 테스트**
  - `PostControllerTest` — `@WebMvcTest` + MockMvc
  - `CompanyControllerTest`
  - `TagControllerTest`
  - `CategoryControllerTest`

- [ ] **프론트엔드 테스트 환경 구축**
  - Vitest 설치 및 설정
  - Store 단위 테스트 (Pinia testing)
  - Component 테스트 (Vue Test Utils)

#### 3-2. 코드 품질 (Day 9)

- [ ] **프론트엔드 ESLint 설정**
  - `.eslintrc.cjs` 생성 (Vue + TypeScript 룰셋)
  - `npm run lint` 실행하여 기존 코드 정리

- [ ] **백엔드 코드 정리**
  - 사용하지 않는 import 제거
  - `PostCategory.java`, `PostTag.java` — entity.enums 패키지에 있지만 package 선언이 `com.gbsb.api.entity`로 불일치 → 수정
  - `BaeMinCrawler.java`의 `import com.gbsb.api.entity.PostTag` — 존재하지 않는 참조일 가능성 확인

- [ ] **보안 점검**
  - `docker-compose.yml`에서 DB 비밀번호 제거 → 환경변수 또는 Docker secrets 사용
  - `application-dev.yaml`의 하드코딩된 비밀번호 제거
  - `.env.example` 파일 생성하여 필요한 환경변수 문서화

#### 3-3. 로깅 및 모니터링 (Day 9)

- [ ] **구조화된 로깅**
  - 크롤링 결과 로그 (성공/실패/건수)
  - API 호출 로그 (request/response)
  - Spring Boot Actuator 엔드포인트 활성화

- [ ] **Health Check 개선**
  - `docker-compose.yml`의 health check가 `actuator/health` 사용 — Actuator 의존성 확인 및 추가

---

### Phase 4: 배포 준비 — 예상 2~3일

#### 4-1. Docker 개선 (Day 10)

- [ ] **docker-compose.yml 개선**
  ```yaml
  services:
    gbsb-api:
      build: ./gbsb-api
      env_file: .env
      environment:
        - SPRING_PROFILES_ACTIVE=prod
        - TNS_ADMIN=/wallet
      volumes:
        - ${WALLET_PATH}:/wallet:ro
      ports:
        - "8081:8080"
      healthcheck:
        test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
        interval: 30s
        timeout: 10s
        retries: 3
        start_period: 60s

    gbsb-web:
      build: ./gbsb-web
      ports:
        - "80:80"
      depends_on:
        gbsb-api:
          condition: service_healthy
  ```

- [ ] **.env.example 생성**
  ```env
  DB_USERNAME=admin
  DB_PASSWORD=
  WALLET_PATH=/path/to/wallet
  SPRING_PROFILES_ACTIVE=prod
  ```

#### 4-2. Nginx 설정 최적화 (Day 10)

- [ ] **캐싱 헤더 추가**
  ```nginx
  location /assets {
      expires 1y;
      add_header Cache-Control "public, immutable";
  }
  ```

- [ ] **gzip 압축 활성화**
- [ ] **보안 헤더 추가** (X-Frame-Options, CSP 등)

#### 4-3. CI/CD 파이프라인 (Day 11)

- [ ] **GitHub Actions 워크플로우 생성**
  ```yaml
  # .github/workflows/ci.yml
  - 프론트엔드: npm install → lint → type-check → build
  - 백엔드: gradle build → test
  - Docker: Build images → Push to registry
  ```

---

### Phase 5: 고급 기능 (선택) — 향후

> Phase 3 로드맵에 해당. MVP 완성 후 진행

#### 5-1. 사용자 인증 시스템

- [ ] JWT 기반 인증 구현 (JwtProvider 완성)
- [ ] OAuth2 로그인 (Google, Kakao, Naver)
- [ ] UserController / UserService 구현
- [ ] 프론트엔드 로그인/회원가입 페이지

#### 5-2. 서버 사이드 북마크

- [ ] Bookmark 엔티티 + API
- [ ] 프론트엔드 북마크 동기화 (로컬 → 서버)

#### 5-3. 알림/구독 시스템

- [ ] 기업/태그 구독 기능
- [ ] 이메일 알림 (새 포스트)

#### 5-4. 검색 고도화

- [ ] Oracle Text 또는 Elasticsearch 도입
- [ ] 자동완성
- [ ] 검색 결과 하이라이팅

#### 5-5. 추천 시스템

- [ ] 사용자 읽기 이력 기반 추천
- [ ] 유사 포스트 추천

#### 5-6. SEO 최적화

- [ ] SSR 또는 SSG 도입 검토 (Nuxt.js 마이그레이션 또는 Prerender)
- [ ] 사이트맵 자동 생성
- [ ] Open Graph 메타 태그 동적 생성

---

## 4. 우선순위 요약

```
Day 1~2  : Phase 1-1, 1-2, 1-3  (인프라 + 서비스 구현 + 초기 데이터)
Day 3~4  : Phase 1-4, 1-5       (크롤러 완성 + 에러 처리)
Day 5~6  : Phase 2              (프론트-백 연동)
Day 7~9  : Phase 3              (테스트 + 품질)
Day 10~11: Phase 4              (배포)
향후     : Phase 5              (고급 기능)
```

---

## 5. 리스크 및 주의사항

### 기술적 리스크

| 리스크 | 영향도 | 대응 |
|--------|--------|------|
| Toss/카카오 블로그가 SPA로 구현되어 Jsoup으로 파싱 불가 | 높음 | RSS 피드 확인, 대안으로 Playwright/Selenium 또는 API 호출 |
| Spring Boot 4.0.1 호환성 이슈 | 중간 | 필요시 3.x 다운그레이드, 의존성 버전 충돌 확인 |
| Oracle Cloud DB 접근 제한 (Wallet 만료 등) | 중간 | 로컬 개발용 H2/MySQL Docker 컨테이너 추가 |
| 크롤링 대상 사이트의 HTML 구조 변경 | 높음 | 크롤러 모니터링 + 알림 체계, 방어적 파싱 |
| 블로그 크롤링의 법적 이슈 | 중간 | robots.txt 준수, RSS 피드 우선 사용, 적절한 크롤링 간격 |

### 운영적 주의사항

1. **크롤링 간격**: 현재 10분 → 매일 1~2회로 변경 권장 (서버 부담 + 예의)
2. **Rate Limiting**: 크롤링 시 각 사이트 간 딜레이 추가 (최소 1~2초)
3. **User-Agent**: 적절한 봇 식별자 사용
4. **에러 복구**: 한 크롤러 실패 시 나머지 계속 진행 (현재 구현됨 ✅)

---

## 6. 문서 업데이트 필요사항

- [ ] **CLAUDE.md**: `web/` → `gbsb-web/`, `api/` → `gbsb-api/` 경로 수정
- [ ] **README.md**: 주석 처리된 내용 복원 및 실제 구조 반영
- [ ] **docs/BACKEND_API_SPEC.md**: 구현 상태 반영 (Phase 체크리스트 업데이트)
- [ ] **gbsb-api/README.md**: 실제 구현된 내용 반영

---

## 7. 빠른 참조: 파일별 작업 목록

### 수정이 필요한 백엔드 파일

| 파일 | 작업 |
|------|------|
| `application.yaml` | context-path 추가, dialect 프로파일별 분리 |
| `WebSecurityConfig.java` | SecurityFilterChain 구현 |
| `WebConfig.java` | CORS 매핑 구현 |
| `CompanyServiceImpl.java` | 전체 구현 |
| `TagServiceImpl.java` | 전체 구현 |
| `CategoryServiceImpl.java` | 전체 구현 |
| `CategoryController.java` | Entity → DTO 응답 변경 |
| `BaeMinCrawler.java` | 파싱 로직 구현 |
| `KakaoCrawler.java` | 셀렉터 검증/수정 |
| `TossCrawler.java` | SPA 대응 방식 변경 |
| `PostScheduler.java` | DB 저장 로직 추가, cron 주기 변경 |
| `GlobalExceptionHandler.java` | 추가 예외 핸들러 |
| `PageRequestDto.java` | @Getter/@Setter 추가 |
| `CompanyName.java` | 5개 기업 추가 |
| `PostCategory.java` | 패키지 경로 수정 또는 삭제 |
| `PostTag.java` | 패키지 경로 수정 또는 삭제 |
| `CompanyRepository.java` | `findByIsActive` 메서드 추가 |
| `TagRepository.java` | 인기 태그 쿼리 추가 |

### 새로 생성해야 할 파일

| 파일 | 설명 |
|------|------|
| `src/main/resources/data.sql` | 초기 seed 데이터 |
| `BaseEntity.java` | 공통 엔티티 (createdAt, updatedAt) |
| `NaverD2Crawler.java` | 네이버 D2 크롤러 |
| `LineCrawler.java` | 라인 엔지니어링 크롤러 |
| `CoupangCrawler.java` | 쿠팡 크롤러 |
| `KarrotCrawler.java` | 당근마켓 크롤러 |
| `YanoljaCrawler.java` | 야놀자 크롤러 |
| `DateParseUtil.java` | 날짜 파싱 유틸리티 |
| `CompanyNotFoundException.java` | 예외 클래스 |
| `CategoryNotFoundException.java` | 예외 클래스 |
| `.github/workflows/ci.yml` | CI/CD 파이프라인 |
| `.env.example` | 환경변수 템플릿 |

---

**이 문서는 프로젝트의 현재 상태를 기반으로 작성되었으며, 개발 진행에 따라 지속적으로 업데이트해야 합니다.**
