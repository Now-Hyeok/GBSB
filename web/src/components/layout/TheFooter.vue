<template>
  <footer class="bg-gray-50 dark:bg-dark-card border-t border-gray-200 dark:border-dark-border mt-auto">
    <div class="container-custom py-12">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <!-- Brand Section -->
        <div>
          <div class="flex items-center gap-2 mb-4">
            <span class="text-2xl">💻</span>
            <h3 class="text-lg font-bold text-gray-900 dark:text-white">개발새발</h3>
          </div>
          <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">
            국내외 테크 기업들의 기술 블로그를<br />한 곳에서 모아보는 큐레이션 허브
          </p>
        </div>

        <!-- Links Section -->
        <div>
          <h4 class="font-semibold text-gray-900 dark:text-white mb-4">탐색하기</h4>
          <ul class="space-y-2 text-sm">
            <li>
              <router-link to="/" class="text-gray-600 dark:text-gray-400 hover:text-primary-600 dark:hover:text-primary-400 transition-colors">
                홈
              </router-link>
            </li>
            <li>
              <router-link to="/about" class="text-gray-600 dark:text-gray-400 hover:text-primary-600 dark:hover:text-primary-400 transition-colors">
                About
              </router-link>
            </li>
          </ul>
        </div>

        <!-- Contact Section -->
        <div>
          <h4 class="font-semibold text-gray-900 dark:text-white mb-4">Contact</h4>
          <div class="flex gap-4">
            <a
              href="https://github.com"
              target="_blank"
              rel="noopener noreferrer"
              class="text-gray-600 dark:text-gray-400 hover:text-primary-600 dark:hover:text-primary-400 transition-colors"
              aria-label="GitHub"
            >
              <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
              </svg>
            </a>
          </div>
          <p class="text-xs text-gray-500 dark:text-gray-500 mt-4">
            피드백 및 문의는 GitHub Issues로 부탁드립니다.
          </p>
        </div>
      </div>

      <!-- Bottom Section -->
      <div class="mt-8 pt-8 border-t border-gray-200 dark:border-gray-700">
        <div class="flex flex-col md:flex-row justify-between items-center gap-4 text-sm text-gray-500 dark:text-gray-400">
          <p>© 2024 개발새발. All rights reserved.</p>
          <p class="text-xs">
            모든 콘텐츠의 저작권은 원 저작자에게 있습니다.
          </p>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts">
</script>
