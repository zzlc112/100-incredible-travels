<template>
  <div class="detail-page" v-loading="loading">
    <template v-if="travel">
      <el-page-header @back="goBack" class="back-header">
        <template #content>
          <span class="back-title">返回</span>
        </template>
      </el-page-header>

      <div class="cover-wrap">
        <img v-if="!imageError" :src="travel.coverImage" class="cover-img" @error="imageError = true" />
        <div v-else class="cover-fallback">
          <el-icon :size="64"><PictureFilled /></el-icon>
          <span>封面加载失败</span>
        </div>
      </div>

      <h1 class="detail-title">{{ travel.title }}</h1>
      <p class="detail-subtitle">{{ travel.subtitle }}</p>

      <div class="detail-meta">
        <span class="destination-text">
          <el-icon :size="16"><LocationFilled /></el-icon>
          {{ travel.destination }}
        </span>
        <span class="rarity-badge">小众程度: {{ '⭐'.repeat(travel.rarityLevel || 0) }}</span>
      </div>

      <div class="tag-row">
        <el-tag v-for="t in travel.experienceTypes || []" :key="t" size="default" class="meta-tag">
          {{ typeMap[t]?.icon }} {{ typeMap[t]?.name || t }}
        </el-tag>
        <el-tag type="info" size="default" class="meta-tag">
          {{ styleMap[travel.visualStyle] || travel.visualStyle }}
        </el-tag>
      </div>

      <div v-if="travel.highlights && travel.highlights.length > 0" class="highlights-section">
        <h3>特色亮点</h3>
        <div class="highlight-tags">
          <el-tag
            v-for="(h, i) in travel.highlights"
            :key="i"
            type="warning"
            size="default"
            effect="dark"
          >
            {{ h }}
          </el-tag>
        </div>
      </div>

      <div v-if="travel.detailImages && travel.detailImages.length > 0" class="detail-images">
        <img
          v-for="(img, i) in travel.detailImages"
          :key="i"
          :src="img"
          class="detail-img"
        />
      </div>

      <div class="content-section">
        <MarkdownRenderer :content="travel.content || ''" />
      </div>

      <div class="detail-footer">
        <span>发布于 {{ formatDate(travel.createdAt) }}</span>
        <span v-if="travel.updatedAt && travel.updatedAt !== travel.createdAt">
           · 更新于 {{ formatDate(travel.updatedAt) }}
        </span>
      </div>
    </template>

    <el-empty v-else-if="!loading" description="内容不存在" class="not-found" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { fetchTravelDetail } from '@/api/travel'
import MarkdownRenderer from '@/components/MarkdownRenderer.vue'
import { PictureFilled, LocationFilled } from '@element-plus/icons-vue'

const EXPERIENCE_TYPE_MAP = {
  extreme: { name: '极限探险', icon: '🧗' },
  culture: { name: '文化沉浸', icon: '🎭' },
  hidden: { name: '秘境探索', icon: '🗺️' },
  eco: { name: '生态旅行', icon: '🌿' },
  urban: { name: '城市隐世', icon: '🏙️' },
  taste: { name: '味觉之旅', icon: '🍜' }
}

const VISUAL_STYLE_MAP = {
  minimal: '极简',
  cinematic: '电影感',
  vintage: '复古胶片',
  vivid: '高饱和冲击',
  bw: '黑白纪实',
  natural: '自然光'
}

const route = useRoute()
const router = useRouter()

const travel = ref(null)
const loading = ref(false)
const imageError = ref(false)
const typeMap = EXPERIENCE_TYPE_MAP
const styleMap = VISUAL_STYLE_MAP

async function loadDetail(id) {
  loading.value = true
  imageError.value = false
  try {
    travel.value = await fetchTravelDetail(id)
  } catch {
    travel.value = null
  } finally {
    loading.value = false
  }
}

function goBack() {
  router.back()
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 19)
}

onMounted(() => loadDetail(route.params.id))
watch(() => route.params.id, (newId) => loadDetail(newId))
</script>

<style scoped>
.detail-page {
  max-width: 800px;
  margin: 0 auto;
}
.back-header {
  margin-bottom: 20px;
}
.back-title {
  font-size: 14px;
}
.cover-wrap {
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 24px;
}
.cover-img {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
}
.cover-fallback {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 250px;
  background: #e0e0e0;
  color: #999;
  gap: 12px;
}
.detail-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
}
.detail-subtitle {
  font-size: 16px;
  color: #888;
  margin-bottom: 16px;
}
.detail-meta {
  display: flex;
  gap: 20px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
.destination-text {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #555;
}
.rarity-badge {
  font-size: 14px;
}
.tag-row {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-bottom: 20px;
}
.meta-tag {
  white-space: nowrap;
}
.highlights-section {
  background: #fff8e1;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 20px;
}
.highlights-section h3 {
  font-size: 15px;
  margin-bottom: 10px;
}
.highlight-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}
.detail-images {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 12px;
  margin-bottom: 24px;
}
.detail-img {
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
}
.detail-footer {
  border-top: 1px solid #eee;
  margin-top: 32px;
  padding-top: 16px;
  color: #aaa;
  font-size: 13px;
}
.not-found {
  padding: 80px 0;
}
</style>
