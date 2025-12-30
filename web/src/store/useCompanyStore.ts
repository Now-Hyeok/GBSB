import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Company, CompanyListParams } from '@/types'
import { api } from '@/services/api'

export const useCompanyStore = defineStore('company', () => {
  // State
  const companies = ref<Company[]>([])
  const currentCompany = ref<Company | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const activeCompanies = computed(() => companies.value.filter((c) => c.isActive))
  const getCompanyById = computed(() => (id: number) =>
    companies.value.find((c) => c.id === id)
  )
  const getCompanyBySlug = computed(() => (slug: string) =>
    companies.value.find((c) => c.slug === slug)
  )

  // Actions
  const fetchCompanies = async (params?: CompanyListParams) => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getCompanies(params)
      companies.value = response.content
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch companies'
      console.error('Error fetching companies:', err)
    } finally {
      loading.value = false
    }
  }

  const fetchCompanyById = async (id: number) => {
    loading.value = true
    error.value = null

    try {
      const response = await api.getCompany(id)
      currentCompany.value = response.data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to fetch company'
      console.error('Error fetching company:', err)
    } finally {
      loading.value = false
    }
  }

  const reset = () => {
    currentCompany.value = null
    error.value = null
  }

  return {
    // State
    companies,
    currentCompany,
    loading,
    error,

    // Getters
    activeCompanies,
    getCompanyById,
    getCompanyBySlug,

    // Actions
    fetchCompanies,
    fetchCompanyById,
    reset
  }
})
