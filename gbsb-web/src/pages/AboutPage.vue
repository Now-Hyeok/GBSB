<template>
  <div class="min-h-screen py-12">
    <div class="container-custom max-w-4xl">
      <article class="prose prose-lg dark:prose-invert max-w-none">
        <!-- Header -->
        <div class="text-center mb-12">
          <div class="text-6xl mb-6">💻</div>
          <h1 class="text-4xl font-bold text-gray-900 dark:text-white mb-4">
            개발새발
          </h1>
          <p class="text-xl text-gray-600 dark:text-gray-400">
            Tech Blog Hub
          </p>
        </div>

        <!-- What is -->
        <section class="card p-8 mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
            개발새발이란?
          </h2>
          <p class="text-gray-700 dark:text-gray-300 leading-relaxed mb-4">
            개발새발은 국내외 주요 IT 기업들의 기술 블로그 포스트를 한 곳에서 모아볼 수 있는 큐레이션 허브입니다.
          </p>
          <p class="text-gray-700 dark:text-gray-300 leading-relaxed">
            배달의민족, 카카오, 토스, 네이버, 라인, 쿠팡, 당근마켓, 야놀자 등
            대한민국을 대표하는 테크 기업들의 최신 기술 블로그 포스트를
            놓치지 않고 확인할 수 있습니다.
          </p>
        </section>

        <!-- Features -->
        <section class="card p-8 mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">
            주요 기능
          </h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <div class="flex items-center gap-3 mb-2">
                <div class="w-10 h-10 bg-primary-100 dark:bg-primary-900/30 rounded-lg flex items-center justify-center">
                  <span class="text-2xl">🏢</span>
                </div>
                <h3 class="font-semibold text-gray-900 dark:text-white">기업별 필터링</h3>
              </div>
              <p class="text-gray-600 dark:text-gray-400 text-sm">
                관심 있는 기업의 포스트만 모아서 볼 수 있습니다
              </p>
            </div>

            <div>
              <div class="flex items-center gap-3 mb-2">
                <div class="w-10 h-10 bg-primary-100 dark:bg-primary-900/30 rounded-lg flex items-center justify-center">
                  <span class="text-2xl">#</span>
                </div>
                <h3 class="font-semibold text-gray-900 dark:text-white">태그 분류</h3>
              </div>
              <p class="text-gray-600 dark:text-gray-400 text-sm">
                Backend, Frontend, DevOps 등 주제별로 포스트를 찾아볼 수 있습니다
              </p>
            </div>

            <div>
              <div class="flex items-center gap-3 mb-2">
                <div class="w-10 h-10 bg-primary-100 dark:bg-primary-900/30 rounded-lg flex items-center justify-center">
                  <span class="text-2xl">🔍</span>
                </div>
                <h3 class="font-semibold text-gray-900 dark:text-white">통합 검색</h3>
              </div>
              <p class="text-gray-600 dark:text-gray-400 text-sm">
                모든 기업의 포스트를 한 번에 검색할 수 있습니다
              </p>
            </div>

            <div>
              <div class="flex items-center gap-3 mb-2">
                <div class="w-10 h-10 bg-primary-100 dark:bg-primary-900/30 rounded-lg flex items-center justify-center">
                  <span class="text-2xl">🌙</span>
                </div>
                <h3 class="font-semibold text-gray-900 dark:text-white">다크 모드</h3>
              </div>
              <p class="text-gray-600 dark:text-gray-400 text-sm">
                편안한 읽기 환경을 위한 다크 모드를 지원합니다
              </p>
            </div>
          </div>
        </section>

        <!-- Data Sources -->
        <section class="card p-8 mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
            데이터 출처
          </h2>
          <p class="text-gray-700 dark:text-gray-300 leading-relaxed mb-4">
            개발새발은 각 기업의 공식 기술 블로그에서 제공하는 RSS 피드와 공개 API를 통해
            포스트 정보를 수집합니다.
          </p>
          <div class="bg-yellow-50 dark:bg-yellow-900/20 border border-yellow-200 dark:border-yellow-800 rounded-lg p-4">
            <p class="text-sm text-gray-700 dark:text-gray-300">
              <strong>⚠️ 주의:</strong> 모든 포스트의 저작권은 원 저작자 및 해당 기업에 있습니다.
              개발새발은 포스트의 제목, 요약, 링크만을 제공하며,
              전체 내용은 원본 블로그에서 확인하실 수 있습니다.
            </p>
          </div>
        </section>

        <!-- Tech Stack -->
        <section class="card p-8 mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
            기술 스택
          </h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white mb-3">Frontend</h3>
              <ul class="space-y-2 text-gray-700 dark:text-gray-300">
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Vue 3 + TypeScript
                </li>
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Vite
                </li>
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Tailwind CSS
                </li>
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Pinia
                </li>
              </ul>
            </div>
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white mb-3">Backend</h3>
              <ul class="space-y-2 text-gray-700 dark:text-gray-300">
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Spring Boot
                </li>
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  Spring Cloud
                </li>
                <li class="flex items-center gap-2">
                  <span class="w-1.5 h-1.5 bg-primary-600 rounded-full"></span>
                  PostgreSQL
                </li>
              </ul>
            </div>
          </div>
        </section>

        <!-- Contact -->
        <section class="card p-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
            Contact
          </h2>
          <p class="text-gray-700 dark:text-gray-300 leading-relaxed mb-4">
            개발새발에 대한 피드백, 버그 리포트, 기능 제안 등은 GitHub Issues를 통해 부탁드립니다.
          </p>
          <a
            href="https://github.com"
            target="_blank"
            rel="noopener noreferrer"
            class="btn btn-primary inline-flex items-center gap-2"
          >
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
            </svg>
            GitHub에서 보기
          </a>
        </section>
      </article>
    </div>
  </div>
</template>

<script setup lang="ts">
</script>
