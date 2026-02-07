# 개발새발 (Dev Blog Hub) - API 개발 요구사항 분석

## 1. 프로젝트 현황 분석

### 기술 스택
- **Framework**: Spring Boot 3.x (Gradle 설정은 4.0.1이나 호환성 고려 필요)
- **Language**: Java 21
- **Database**: 
  - 설정(`application.yaml`): Oracle Database (`ojdbc11`)
  - 명세(`BACKEND_API_SPEC.md`): PostgreSQL / MySQL
  - **이슈**: 명세와 실제 설정이 불일치함. 오라클 DB 사용 여부 확인 필요.
- **Persistence**: JPA (Spring Data JPA) & MyBatis 혼용
- **Crawler**: Jsoup (HTML 파싱), Rome 사용 흔적 없음 (명세와 다름)

### API 구현 상태
- **PostController**: 구현됨. 단, URL 경로가 명세서와 다름 (`/posts` vs `/api/posts`).
- **CompanyController**: 구현됨. (`/companies` vs `/api/companies`)
- **CategoryController**: 구현됨. (`/categories` vs `/api/categories`)
- **TagController**: 파일 존재 (내부 로직 확인 필요).
- **Search**: `PostService`에 `LIKE` 검색으로 단순 구현됨.

### 크롤러 구현 상태
- **구조**: `PostScheduler` -> `List<PostCrawler>` 구조로 확장성 있게 설계됨.
- **구현체**: 
  - `BaeMinCrawler`: 클래스는 존재하나 **실제 파싱 로직이 비어있음**.
  - 그 외(카카오, 토스, 네이버 등): **구현되지 않음**.
- **방식**: RSS(Rome) 대신 Jsoup을 이용한 HTML 스크래핑 방식 채택됨.

---

## 2. 개발 필요 기능 목록 (To-Do)

### ✅ 우선순위 1: API 표준화 및 설정 확정 (Phase 1 보완)

1.  **API 경로 표준화 (Refactoring)**
    *   **문제**: 현재 컨트롤러들이 `/posts`, `/companies` 등 루트 경로를 사용 중. 명세서는 `/api/posts` 형식을 따름.
    *   **작업**: 
        *   `application.yaml`에 `server.servlet.context-path: /api` 설정 추가 또는
        *   모든 Controller의 `@RequestMapping`을 `/api/...`로 수정.
    *   **대상**: `PostController`, `CompanyController`, `CategoryController`, `TagController`.

2.  **데이터베이스 의사결정 및 설정**
    *   **문제**: 명세서는 MySQL/PostgreSQL, 설정은 Oracle.
    *   **작업**: 배포 환경에 맞춰 DB 확정 후 `build.gradle` 및 `application.yaml` 의존성/설정 통일.

### ✅ 우선순위 2: 크롤러 엔진 개발 (핵심 기능)

현재 껍데기만 있는 크롤러의 실제 로직 구현이 시급함.

1.  **배달의민족 (Woowa Bros) 크롤러 구현**
    *   `BaeMinCrawler.java` 내부 로직 구현.
    *   Jsoup을 사용하여 `https://techblog.woowahan.com/`의 글 목록, 제목, 날짜, 태그 파싱.
    *   중복 수집 방지 로직 (URL Unique Constraint 활용 등).

2.  **주요 기술 블로그 크롤러 추가 구현**
    *   **카카오 (Kakao Tech)**: `KakaoCrawler` 추가. (RSS 또는 Jsoup)
    *   **토스 (Toss Tech)**: `TossCrawler` 추가.
    *   **네이버 (Naver D2)**: `NaverCrawler` 추가.
    *   **라인 (Line Engineering)**: `LineCrawler` 추가.

### ✅ 우선순위 3: 스케줄러 및 시스템 안정화

1.  **스케줄러 검증**
    *   `PostScheduler`의 Cron 식(`0 0/10 * * * *`) 확인 (현재 10분 주기).
    *   크롤링 실패 시 예외 처리 및 로깅 강화.

2.  **검색/필터링 고도화 (Phase 2)**
    *   현재 `PostServiceImpl`의 검색은 단순 텍스트 매칭. 성능 이슈 발생 가능성 있음.
    *   필요 시 QueryDSL 도입 고려.

### ✅ 우선순위 4: 인증/인가 (Phase 3 - 향후)

1.  **보안 설정**
    *   `JwtProvider` 등 Security 관련 클래스가 비어있음.
    *   사용자 로그인/회원가입 기능이 필요할 때 구현 시작.

---

## 3. 추천 개발 프로세스

1.  **DB 설정 확정**: 로컬 개발 환경(Docker 등)에 맞는 DB로 `application.yaml` 수정.
2.  **BaeMinCrawler 구현 & 테스트**: `Jsoup`으로 실제 데이터를 가져와지는지 단위 테스트 작성.
3.  **API 경로 수정**: `/api` 프리픽스 적용 후 프론트엔드 연동 확인.
4.  **나머지 크롤러 구현**: 기업별로 하나씩 추가하며 데이터 확보.
