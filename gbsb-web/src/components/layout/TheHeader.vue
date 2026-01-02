<template>
  <header class="sticky top-0 z-50 bg-white/95 dark:bg-dark-bg/95 backdrop-blur-sm border-b border-gray-200 dark:border-dark-border">
    <div class="container-custom">
      <div class="flex items-center justify-between h-16">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-3 group">
          <div class="text-2xl">💻</div>
          <div>
            <h1 class="text-xl font-bold text-gray-900 dark:text-white group-hover:text-primary-600 dark:group-hover:text-primary-400 transition-colors">
              개발새발
            </h1>
            <p class="text-xs text-gray-500 dark:text-gray-400 hidden sm:block">
              Tech Blog Hub
            </p>
          </div>
        </router-link>

        <!-- Desktop Navigation -->
        <nav class="hidden md:flex items-center gap-6">
          <!-- Company Dropdown -->
          <div class="relative" ref="companyDropdownRef">
            <button
              @click="toggleCompanyDropdown"
              class="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
            >
              <span class="font-medium">기업 블로그</span>
              <svg
                class="w-4 h-4 transition-transform"
                :class="{ 'rotate-180': isCompanyDropdownOpen }"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>

            <!-- Dropdown Menu -->
            <transition
              enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95"
              enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75"
              leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95"
            >
              <div
                v-if="isCompanyDropdownOpen"
                class="absolute top-full mt-2 w-64 bg-white dark:bg-dark-card rounded-lg shadow-lg border border-gray-200 dark:border-dark-border py-2 max-h-96 overflow-y-auto"
              >
                <router-link
                  v-for="company in companies"
                  :key="company.id"
                  :to="`/company/${company.id}`"
                  class="flex items-center gap-3 px-4 py-2 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                  @click="closeCompanyDropdown"
                >
                  <img
                    v-if="company.logoUrl"
                    :src="company.logoUrl"
                    :alt="company.nameKo"
                    class="w-6 h-6 rounded object-contain"
                  />
                  <span class="text-sm">{{ company.nameKo }}</span>
                </router-link>
              </div>
            </transition>
          </div>

          <router-link
            to="/bookmarks"
            class="px-4 py-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors font-medium flex items-center gap-2"
          >
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 5a2 2 0 012-2h10a2 2 0 012 2v16l-7-3.5L5 21V5z" />
            </svg>
            북마크
            <span v-if="bookmarkStore.bookmarkCount > 0" class="px-2 py-0.5 text-xs bg-primary-600 text-white rounded-full">
              {{ bookmarkStore.bookmarkCount }}
            </span>
          </router-link>

          <router-link
            to="/about"
            class="px-4 py-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors font-medium"
          >
            About
          </router-link>
        </nav>

        <!-- Actions -->
        <div class="flex items-center gap-2">
          <!-- Search -->
          <button
            @click="toggleSearch"
            class="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
            aria-label="Search"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </button>

          <!-- Dark Mode Toggle -->
          <button
            @click="uiStore.toggleDarkMode()"
            class="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
            aria-label="Toggle dark mode"
          >
            <svg v-if="!uiStore.isDarkMode" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
            </svg>
            <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
          </button>

          <!-- Mobile Menu Toggle -->
          <button
            @click="uiStore.toggleMobileMenu()"
            class="md:hidden p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
            aria-label="Menu"
          >
            <svg v-if="!uiStore.isMobileMenuOpen" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
            </svg>
            <svg v-else class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <!-- Mobile Menu -->
      <transition
        enter-active-class="transition ease-out duration-200"
        enter-from-class="opacity-0 -translate-y-4"
        enter-to-class="opacity-100 translate-y-0"
        leave-active-class="transition ease-in duration-150"
        leave-from-class="opacity-100 translate-y-0"
        leave-to-class="opacity-0 -translate-y-4"
      >
        <div v-if="uiStore.isMobileMenuOpen" class="md:hidden py-4 border-t border-gray-200 dark:border-dark-border">
          <nav class="flex flex-col gap-2">
            <div class="px-4 py-2 text-sm font-semibold text-gray-500 dark:text-gray-400">
              기업 블로그
            </div>
            <router-link
              v-for="company in companies"
              :key="company.id"
              :to="`/company/${company.id}`"
              class="flex items-center gap-3 px-4 py-2 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors rounded-lg"
              @click="uiStore.closeMobileMenu()"
            >
              <img
                v-if="company.logoUrl"
                :src="company.logoUrl"
                :alt="company.nameKo"
                class="w-5 h-5 rounded object-contain"
              />
              <span class="text-sm">{{ company.nameKo }}</span>
            </router-link>

            <div class="border-t border-gray-200 dark:border-dark-border my-2"></div>

            <router-link
              to="/about"
              class="px-4 py-2 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors rounded-lg"
              @click="uiStore.closeMobileMenu()"
            >
              About
            </router-link>
          </nav>
        </div>
      </transition>
    </div>

    <!-- Search Modal -->
    <SearchModal v-if="uiStore.isSearchOpen" @close="uiStore.closeSearch()" />
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useCompanyStore } from '@/store/useCompanyStore'
import { useBookmarkStore } from '@/store/useBookmarkStore'
import { useUiStore } from '@/store/useUiStore'
import SearchModal from './SearchModal.vue'

const companyStore = useCompanyStore()
const bookmarkStore = useBookmarkStore()
const uiStore = useUiStore()

const companies = ref(companyStore.activeCompanies)
const isCompanyDropdownOpen = ref(false)
const companyDropdownRef = ref<HTMLElement | null>(null)

const toggleCompanyDropdown = () => {
  isCompanyDropdownOpen.value = !isCompanyDropdownOpen.value
}

const closeCompanyDropdown = () => {
  isCompanyDropdownOpen.value = false
}

const toggleSearch = () => {
  uiStore.toggleSearch()
}

const handleClickOutside = (event: MouseEvent) => {
  if (companyDropdownRef.value && !companyDropdownRef.value.contains(event.target as Node)) {
    closeCompanyDropdown()
  }
}

onMounted(async () => {
  if (companyStore.companies.length === 0) {
    await companyStore.fetchCompanies({ isActive: true })
    companies.value = companyStore.activeCompanies
  }
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>
