<template>
  <div class="image-preview" :style="{ width, height }">
    <img
      v-if="!error"
      :src="url"
      :style="{ objectFit: fit }"
      @error="error = true"
      class="preview-img"
    />
    <div v-else class="image-placeholder">
      <el-icon :size="48"><PictureFilled /></el-icon>
      <span>图片加载失败</span>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { PictureFilled } from '@element-plus/icons-vue'

defineProps({
  url: { type: String, required: true },
  width: { type: String, default: '100%' },
  height: { type: String, default: 'auto' },
  fit: { type: String, default: 'cover' }
})

const error = ref(false)
</script>

<style scoped>
.image-preview {
  overflow: hidden;
  border-radius: 8px;
  background: #f0f0f0;
}
.preview-img {
  width: 100%;
  height: 100%;
}
.image-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-height: 120px;
  color: #999;
  gap: 8px;
  font-size: 14px;
}
</style>
