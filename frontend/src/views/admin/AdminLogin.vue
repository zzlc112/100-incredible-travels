<template>
  <div class="login-page">
    <div class="login-card">
      <h2 class="login-title">100种不可思议旅行</h2>
      <p class="login-subtitle">后台管理</p>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-position="top"
        @submit.prevent="handleLogin"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" :loading="loading" native-type="submit" class="login-btn">
          登 录
        </el-button>
      </el-form>
      <el-alert
        v-if="errorMessage"
        :title="errorMessage"
        type="error"
        :closable="true"
        show-icon
        class="error-alert"
        @close="errorMessage = ''"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'

const router = useRouter()

const formRef = ref(null)
const form = reactive({ username: '', password: '' })
const loading = ref(false)
const errorMessage = ref('')

const rules = {
  username: { required: true, message: '请输入用户名', trigger: 'blur' },
  password: { required: true, message: '请输入密码', trigger: 'blur' }
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  errorMessage.value = ''
  try {
    await login(form.username, form.password)
    router.push('/admin/travels')
  } catch {
    errorMessage.value = '用户名或密码错误'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
}
.login-card {
  width: 400px;
  max-width: 90vw;
  background: #fff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.3);
}
.login-title {
  font-size: 24px;
  text-align: center;
  margin-bottom: 4px;
}
.login-subtitle {
  font-size: 14px;
  color: #888;
  text-align: center;
  margin-bottom: 32px;
}
.login-btn {
  width: 100%;
  margin-top: 8px;
}
.error-alert {
  margin-top: 16px;
}
</style>
