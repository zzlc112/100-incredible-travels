<template>
  <div class="admin-layout">
    <header class="admin-header">
      <span class="admin-title">后台管理</span>
      <el-button type="danger" size="small" plain @click="handleLogout">退出登录</el-button>
    </header>
    <main class="admin-main">
      <slot />
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { logout } from '@/api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()

async function handleLogout() {
  try {
    await logout()
  } finally {
    router.push('/admin/login')
    ElMessage.success('已退出登录')
  }
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
  background: #f5f7fa;
}
.admin-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  padding: 0 24px;
  background: #1a1a2e;
  color: #fff;
}
.admin-title {
  font-size: 16px;
  font-weight: 600;
}
.admin-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}
</style>
