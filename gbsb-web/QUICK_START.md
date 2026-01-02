# ⚡ Quick Start Guide

## 🚀 3분 안에 시작하기

### Step 1: 설치
```bash
cd frontends/dev-blog-hub
npm install
```

### Step 2: 실행
```bash
npm run dev
```

### Step 3: 브라우저 열기
```
http://localhost:3001
```

**끝!** Mock 데이터로 즉시 모든 기능을 확인할 수 있습니다.

---

## 📱 주요 기능 테스트

### 1. 홈 페이지 탐색
- ✅ 기업 필터 탭 클릭해보기
- ✅ 최신순/인기순 정렬 전환
- ✅ 스크롤 내려서 무한 로딩 확인

### 2. 검색 기능
- ✅ 우측 상단 검색 아이콘 클릭
- ✅ "Spring", "React" 등 키워드 검색
- ✅ ESC로 모달 닫기

### 3. 다크 모드
- ✅ 우측 상단 달/해 아이콘 클릭
- ✅ localStorage에 자동 저장됨

### 4. 포스트 상세
- ✅ 포스트 카드 클릭
- ✅ 원문 보러가기 링크 확인
- ✅ 관련 포스트 확인

### 5. 기업 페이지
- ✅ 기업 배지 또는 필터 탭 클릭
- ✅ 해당 기업의 포스트만 표시

### 6. 태그 페이지
- ✅ 포스트 카드의 태그 클릭
- ✅ 같은 태그의 다른 포스트 확인

### 7. 모바일 테스트
- ✅ 브라우저 창 줄여서 반응형 확인
- ✅ 햄버거 메뉴 동작 확인

---

## 🔧 Backend 연동하기

### Option 1: Spring Boot API 준비되어 있는 경우

```typescript
// src/services/api.ts (Line 9)
private useMock: boolean = false  // 변경
```

```env
# .env.development
VITE_API_BASE_URL=http://localhost:8080/api
```

```bash
npm run dev
```

### Option 2: Backend 없이 계속 개발

아무것도 변경하지 않고 Mock 데이터로 개발 가능!

---

## 📂 주요 파일 위치

| 파일 | 경로 | 용도 |
|------|------|------|
| 페이지 | `src/pages/*.vue` | 라우트별 페이지 |
| 컴포넌트 | `src/components/` | 재사용 컴포넌트 |
| API | `src/services/api.ts` | Backend 호출 |
| Mock | `src/services/mockData.ts` | 개발용 데이터 |
| 스토어 | `src/store/` | 상태 관리 |
| 스타일 | `src/assets/styles/main.css` | 글로벌 CSS |
| 타입 | `src/types/index.ts` | TypeScript 타입 |

---

## 🎨 커스터마이징

### 색상 변경
```js
// tailwind.config.js
theme: {
  extend: {
    colors: {
      primary: {
        500: '#YOUR_COLOR',  // 메인 색상
      }
    }
  }
}
```

### 로고 변경
```vue
<!-- src/components/layout/TheHeader.vue -->
<div class="text-2xl">💻</div>  <!-- 이모지 변경 -->
<h1>개발새발</h1>  <!-- 텍스트 변경 -->
```

### Mock 데이터 추가
```typescript
// src/services/mockData.ts
export const mockCompanies: Company[] = [
  // 여기에 새 기업 추가
]
```

---

## ⚠️ 문제 해결

### Port 3001이 이미 사용 중인 경우
```js
// vite.config.ts
server: {
  port: 3002,  // 다른 포트로 변경
}
```

### npm install 실패
```bash
# 캐시 삭제 후 재시도
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

### 다크 모드가 작동하지 않는 경우
브라우저 개발자 도구 → Application → Local Storage → 삭제 후 새로고침

---

## 📚 더 알아보기

- [README.md](./README.md) - 전체 문서
- [PROJECT_SUMMARY.md](./PROJECT_SUMMARY.md) - 프로젝트 요약
- [Tailwind CSS Docs](https://tailwindcss.com/docs)
- [Vue 3 Docs](https://vuejs.org/)
- [Pinia Docs](https://pinia.vuejs.org/)

---

## 💬 도움이 필요하신가요?

GitHub Issues에 질문을 남겨주세요!

**Happy Coding! 🎉**
