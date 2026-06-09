<template>
  <div class="filter-bar">
    <div class="filter-row">
      <span class="filter-label">体验类型</span>
      <el-checkbox-group
        :model-value="localValue.experienceTypes"
        @update:model-value="updateType($event)"
        class="filter-group"
      >
        <el-checkbox-button
          v-for="item in experienceTypeOptions"
          :key="item.code"
          :value="item.code"
        >
          {{ item.icon }} {{ item.name }}
        </el-checkbox-button>
      </el-checkbox-group>
    </div>

    <div class="filter-row">
      <span class="filter-label">视觉风格</span>
      <el-radio-group
        :model-value="localValue.visualStyle"
        @update:model-value="updateStyle($event)"
        class="filter-group"
      >
        <el-radio-button
          v-for="item in visualStyleOptions"
          :key="item.code"
          :value="item.code"
        >
          {{ item.name }}
        </el-radio-button>
      </el-radio-group>
      <el-button v-if="localValue.visualStyle" size="small" link @click="updateStyle('')">
        取消
      </el-button>
    </div>

    <div class="filter-row">
      <span class="filter-label">小众程度 ≥</span>
      <div class="slider-wrap">
        <el-slider
          :model-value="localValue.rarityLevel"
          @update:model-value="updateRarity($event)"
          :min="0"
          :max="4"
          :step="1"
          :marks="rarityMarks"
          show-stops
        />
      </div>
      <span class="rarity-text">{{ localValue.rarityLevel > 0 ? '⭐'.repeat(localValue.rarityLevel) + ' 以上' : '不限' }}</span>
    </div>

    <div class="filter-actions">
      <el-button @click="$emit('reset')" plain>重置筛选</el-button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: { type: Object, required: true },
  availableExperienceTypes: {
    type: Array,
    default: () => [
      { code: 'extreme', name: '极限探险', icon: '🧗' },
      { code: 'culture', name: '文化沉浸', icon: '🎭' },
      { code: 'hidden', name: '秘境探索', icon: '🗺️' },
      { code: 'eco', name: '生态旅行', icon: '🌿' },
      { code: 'urban', name: '城市隐世', icon: '🏙️' },
      { code: 'taste', name: '味觉之旅', icon: '🍜' }
    ]
  },
  availableVisualStyles: {
    type: Array,
    default: () => [
      { code: 'minimal', name: '极简' },
      { code: 'cinematic', name: '电影感' },
      { code: 'vintage', name: '复古胶片' },
      { code: 'vivid', name: '高饱和冲击' },
      { code: 'bw', name: '黑白纪实' },
      { code: 'natural', name: '自然光' }
    ]
  }
})

const emit = defineEmits(['update:modelValue', 'reset'])

const experienceTypeOptions = props.availableExperienceTypes
const visualStyleOptions = props.availableVisualStyles

const rarityMarks = {
  0: '不限',
  1: '⭐',
  2: '⭐⭐',
  3: '⭐⭐⭐',
  4: '⭐⭐⭐⭐'
}

const localValue = computed(() => props.modelValue)

function emitUpdate(partial) {
  emit('update:modelValue', { ...props.modelValue, ...partial })
}

function updateType(types) {
  emitUpdate({ experienceTypes: types })
}

function updateStyle(style) {
  emitUpdate({ visualStyle: style })
}

function updateRarity(level) {
  emitUpdate({ rarityLevel: level })
}
</script>

<style scoped>
.filter-bar {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}
.filter-row {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  flex-wrap: wrap;
  gap: 10px;
}
.filter-label {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  min-width: 72px;
}
.filter-group {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
.slider-wrap {
  flex: 1;
  min-width: 150px;
  max-width: 300px;
}
.rarity-text {
  font-size: 13px;
  color: #888;
  min-width: 80px;
}
.filter-actions {
  display: flex;
  justify-content: flex-end;
}
</style>
