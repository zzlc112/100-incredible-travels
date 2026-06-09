<template>
  <div class="travel-card" @click="$emit('click', travel.id)">
    <div class="card-cover">
      <img v-if="!imageError" :src="travel.coverImage" @error="imageError = true" />
      <div v-else class="cover-placeholder">
        <el-icon :size="36"><PictureFilled /></el-icon>
      </div>
      <div class="rarity-badge">{{ rarityStars }}</div>
    </div>
    <div class="card-body">
      <h3 class="card-title">{{ travel.title }}</h3>
      <p class="card-subtitle">{{ travel.subtitle }}</p>
      <div class="card-tags">
        <el-tag
          v-for="exp in experienceLabels"
          :key="exp.code"
          size="small"
          :type="tagType(exp.code)"
          class="tag-item"
        >
          {{ exp.icon }} {{ exp.name }}
        </el-tag>
        <el-tag size="small" type="info" class="tag-item">
          {{ visualStyleLabel }}
        </el-tag>
      </div>
      <div class="card-footer">
        <span class="destination">
          <el-icon :size="14"><LocationFilled /></el-icon>
          {{ travel.destination }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
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

const TAG_TYPES = ['', 'success', 'warning', 'danger', 'info']

const props = defineProps({
  travel: { type: Object, required: true }
})

defineEmits(['click'])

const imageError = ref(false)

const experienceLabels = computed(() =>
  (props.travel.experienceTypes || []).map(code => ({
    code,
    name: EXPERIENCE_TYPE_MAP[code]?.name || code,
    icon: EXPERIENCE_TYPE_MAP[code]?.icon || ''
  }))
)

const visualStyleLabel = computed(() =>
  VISUAL_STYLE_MAP[props.travel.visualStyle] || props.travel.visualStyle || ''
)

const rarityStars = computed(() => '⭐'.repeat(props.travel.rarityLevel || 0))

function tagType(code) {
  if (code === 'extreme') return 'danger'
  if (code === 'hidden') return 'warning'
  if (code === 'taste') return 'success'
  if (code === 'eco') return 'success'
  if (code === 'culture') return ''
  return 'info'
}
</script>

<style scoped>
.travel-card {
  border-radius: 12px;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}
.travel-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.12);
}
.card-cover {
  position: relative;
  height: 200px;
  overflow: hidden;
}
.card-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.cover-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e0e0e0;
  color: #999;
}
.rarity-badge {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(0,0,0,0.6);
  color: #fff;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
}
.card-body {
  padding: 16px;
}
.card-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.card-subtitle {
  font-size: 13px;
  color: #888;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.card-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 10px;
}
.tag-item {
  white-space: nowrap;
}
.card-footer {
  border-top: 1px solid #f0f0f0;
  padding-top: 10px;
}
.destination {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #666;
}
</style>
