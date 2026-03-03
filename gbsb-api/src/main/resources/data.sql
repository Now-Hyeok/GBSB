-- ============================================
-- GBSB Initial Seed Data
-- Oracle-compatible SQL
-- ============================================

-- Companies (8개 주요 IT 기업)
MERGE INTO gbsb.companies c
USING (SELECT 'Woowa Bros' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Woowa Bros', '배달의민족', 'woowabros', '우아한형제들 기술 블로그', 'https://techblog.woowahan.com/wp-content/uploads/2021/06/favicon.ico', 'https://techblog.woowahan.com', '#2AC1BC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Kakao' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Kakao', '카카오', 'kakao', '카카오 기술 블로그', 'https://tech.kakao.com/favicon.ico', 'https://tech.kakao.com/blog', '#FEE500', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Toss' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Toss', '토스', 'toss', '토스 기술 블로그', 'https://toss.tech/favicon.ico', 'https://toss.tech', '#0064FF', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Naver' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Naver', '네이버', 'naver', '네이버 D2 기술 블로그', 'https://d2.naver.com/favicon.ico', 'https://d2.naver.com/helloworld', '#03C75A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Line' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Line', '라인', 'line', '라인 엔지니어링 블로그', 'https://engineering.linecorp.com/favicon.ico', 'https://engineering.linecorp.com/ko/blog', '#06C755', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Coupang' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Coupang', '쿠팡', 'coupang', '쿠팡 엔지니어링 블로그', 'https://medium.com/favicon.ico', 'https://medium.com/coupang-engineering', '#C33332', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Karrot' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Karrot', '당근마켓', 'karrot', '당근마켓 기술 블로그', 'https://medium.com/favicon.ico', 'https://medium.com/daangn', '#FF6F0F', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.companies c
USING (SELECT 'Yanolja' AS name FROM dual) src ON (c.name = src.name)
WHEN NOT MATCHED THEN INSERT (name, name_ko, slug, description, logo_url, blog_url, color, is_active, created_at, updated_at)
VALUES ('Yanolja', '야놀자', 'yanolja', '야놀자 기술 블로그', 'https://medium.com/favicon.ico', 'https://medium.com/yanolja', '#FF3478', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Categories (8개 카테고리)
MERGE INTO gbsb.categories c
USING (SELECT 'development' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('개발', 'development', '소프트웨어 개발 전반', '💻', '#3b82f6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'infrastructure' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('인프라', 'infrastructure', '인프라 및 DevOps', '🏗️', '#10b981', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'data' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('데이터', 'data', '데이터 엔지니어링 및 분석', '📊', '#f59e0b', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'ai-ml' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('AI/ML', 'ai-ml', '인공지능 및 머신러닝', '🤖', '#8b5cf6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'frontend' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('프론트엔드', 'frontend', '웹/앱 프론트엔드 개발', '🎨', '#ec4899', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'backend' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('백엔드', 'backend', '서버 및 API 개발', '⚙️', '#6366f1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'security' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('보안', 'security', '보안 및 인증', '🔒', '#ef4444', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.categories c
USING (SELECT 'culture' AS slug FROM dual) src ON (c.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, description, icon, color, created_at, updated_at)
VALUES ('문화', 'culture', '개발 문화 및 조직', '🌱', '#22c55e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Tags (주요 기술 태그 20개)
MERGE INTO gbsb.tags t
USING (SELECT 'backend' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Backend', 'backend', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'frontend' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Frontend', 'frontend', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'devops' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('DevOps', 'devops', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'kubernetes' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Kubernetes', 'kubernetes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'spring' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Spring', 'spring', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'react' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('React', 'react', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'java' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Java', 'java', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'kotlin' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Kotlin', 'kotlin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'typescript' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('TypeScript', 'typescript', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'python' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Python', 'python', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'msa' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('MSA', 'msa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'database' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Database', 'database', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'redis' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Redis', 'redis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'kafka' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Kafka', 'kafka', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'aws' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('AWS', 'aws', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'docker' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Docker', 'docker', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'ci-cd' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('CI/CD', 'ci-cd', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'machine-learning' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Machine Learning', 'machine-learning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'ios' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('iOS', 'ios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO gbsb.tags t
USING (SELECT 'android' AS slug FROM dual) src ON (t.slug = src.slug)
WHEN NOT MATCHED THEN INSERT (name, slug, created_at, updated_at)
VALUES ('Android', 'android', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
