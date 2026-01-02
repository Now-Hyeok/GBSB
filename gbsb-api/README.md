# Backend API

> Spring Boot backend for 개발새발 (Dev Blog Hub)

## 📋 Status

🚧 **Under Development** - Backend implementation coming soon

## 🛠 Planned Tech Stack

- **Java** 17+
- **Spring Boot** 3.x
- **Spring Data JPA** - ORM
- **PostgreSQL** or **MySQL** - Database
- **Rome RSS Reader** - Blog crawling
- **Spring Boot Scheduler** - Automated crawling

## 📖 API Specification

Complete REST API specification: [BACKEND_API_SPEC.md](../docs/BACKEND_API_SPEC.md)

### Key Features

- 📄 **Posts API** - CRUD, filtering, search, pagination
- 🏢 **Companies API** - Tech company management
- 🏷️ **Tags API** - Tag management and popular tags
- 📂 **Categories API** - Category management
- 🕷️ **RSS Crawler** - Automated blog post collection
- ⏰ **Scheduler** - Daily crawling tasks

## 🚀 Quick Start (When Implemented)

```bash
# Build
./gradlew build

# Run
./gradlew bootRun

# Test
./gradlew test
```

## 📁 Expected Structure

```
api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/devblog/hub/
│   │   │       ├── domain/        # Entities
│   │   │       ├── repository/    # JPA Repositories
│   │   │       ├── service/       # Business Logic
│   │   │       ├── controller/    # REST Controllers
│   │   │       ├── dto/           # Data Transfer Objects
│   │   │       └── crawler/       # RSS Crawler
│   │   └── resources/
│   │       ├── application.yml
│   │       └── data.sql           # Initial data
│   └── test/
├── build.gradle
└── README.md
```

## 🔌 Integration with Frontend

Frontend expects backend to run on:
```
http://localhost:8080
```

All API endpoints should be prefixed with `/api`:
- `GET /api/posts`
- `GET /api/companies`
- `GET /api/tags`
- etc.

See [API Specification](../docs/BACKEND_API_SPEC.md) for complete details.

## 📦 Database Schema

Complete database schema with SQL CREATE statements available in the API specification document.

### Core Tables
- `posts` - Blog posts
- `companies` - Tech companies
- `tags` - Post tags
- `categories` - Post categories
- `post_tags` - Many-to-many relationship

## 🕷️ RSS Crawler Implementation

The backend should implement RSS feed crawlers for major Korean tech companies:

- 배달의민족 (Woowa Bros)
- 카카오 (Kakao)
- 토스 (Toss)
- 네이버 (Naver)
- 라인 (Line)
- 쿠팡 (Coupang)
- 당근마켓 (Karrot)
- 야놀자 (Yanolja)

Refer to the API specification for implementation guidance.

## 🔧 Development Notes

- Use Spring Boot 3.x conventions
- Follow RESTful API best practices
- Implement proper error handling
- Add pagination for all list endpoints
- Use Spring Data JPA specifications for filtering
- Implement scheduled tasks for RSS crawling

## 📄 License

MIT License - See [LICENSE](../LICENSE)
