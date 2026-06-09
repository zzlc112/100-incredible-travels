<template>
  <AppLayout v-if="isFrontendRoute">
    <router-view />
  </AppLayout>
  <AdminLayout v-else-if="isAdminRoute && !isLoginRoute">
    <router-view />
  </AdminLayout>
  <router-view v-else />
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import AppLayout from '@/components/AppLayout.vue'
import AdminLayout from '@/components/AdminLayout.vue'

const route = useRoute()

const isFrontendRoute = computed(() =>
  route.path === '/' || route.path.startsWith('/travel/')
)

const isAdminRoute = computed(() => route.path.startsWith('/admin'))

const isLoginRoute = computed(() => route.path === '/admin/login')
</script>
