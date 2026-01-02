import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { useStorage } from '@vueuse/core'

export const useUiStore = defineStore('ui', () => {
  // State (persisted in localStorage)
  const isDarkMode = useStorage('theme-dark-mode', false)
  const isMobileMenuOpen = ref(false)
  const isSearchOpen = ref(false)

  // Actions
  const toggleDarkMode = () => {
    isDarkMode.value = !isDarkMode.value
  }

  const setDarkMode = (value: boolean) => {
    isDarkMode.value = value
  }

  const toggleMobileMenu = () => {
    isMobileMenuOpen.value = !isMobileMenuOpen.value
  }

  const closeMobileMenu = () => {
    isMobileMenuOpen.value = false
  }

  const toggleSearch = () => {
    isSearchOpen.value = !isSearchOpen.value
  }

  const closeSearch = () => {
    isSearchOpen.value = false
  }

  // Watch dark mode and update document class
  watch(
    isDarkMode,
    (newValue) => {
      if (newValue) {
        document.documentElement.classList.add('dark')
      } else {
        document.documentElement.classList.remove('dark')
      }
    },
    { immediate: true }
  )

  return {
    // State
    isDarkMode,
    isMobileMenuOpen,
    isSearchOpen,

    // Actions
    toggleDarkMode,
    setDarkMode,
    toggleMobileMenu,
    closeMobileMenu,
    toggleSearch,
    closeSearch
  }
})
