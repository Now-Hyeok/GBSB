<template>
  <div class="min-h-screen flex items-center justify-center">
    <EmptyState
      icon="🔍"
      title="페이지를 찾을 수 없습니다"
      description="요청하신 페이지가 존재하지 않거나 이동되었습니다."
    >
      <template #action>
        <router-link to="/" class="btn btn-primary mt-6">
          홈으로 돌아가기
        </router-link>
      </template>
    </EmptyState>
  </div>
</template>

<script setup lang="ts">
import EmptyState from '@/components/common/EmptyState.vue'
</script>
