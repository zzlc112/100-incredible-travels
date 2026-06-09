<template>
  <div class="admin-edit-page" v-loading="loading">
    <h2 class="page-title">{{ pageTitle }}</h2>

    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-position="top"
      class="edit-form"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="标题" prop="title">
            <el-input v-model="form.title" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="目的地" prop="destination">
            <el-input v-model="form.destination" maxlength="200" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="副标题" prop="subtitle">
        <el-input v-model="form.subtitle" maxlength="500" show-word-limit />
      </el-form-item>

      <el-form-item label="封面图片URL" prop="coverImage">
        <el-input v-model="form.coverImage" placeholder="https://..." />
        <div class="preview-tip">
          <ImagePreview :url="form.coverImage" width="200px" height="120px" v-if="form.coverImage" />
        </div>
      </el-form-item>

      <el-form-item label="详情图片">
        <div v-for="(img, i) in form.detailImages" :key="i" class="dynamic-row">
          <el-input v-model="form.detailImages[i]" placeholder="https://..." />
          <ImagePreview v-if="img" :url="img" width="80px" height="50px" />
          <el-button size="small" type="danger" @click="removeDetailImage(i)">删除</el-button>
        </div>
        <el-button size="small" @click="addDetailImage" :disabled="form.detailImages.length >= 10">
          添加图片
        </el-button>
      </el-form-item>

      <el-form-item label="体验类型" prop="experienceTypes">
        <el-checkbox-group v-model="form.experienceTypes">
          <el-checkbox
            v-for="opt in experienceTypeOptions"
            :key="opt.code"
            :value="opt.code"
          >
            {{ opt.icon }} {{ opt.name }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="视觉风格" prop="visualStyle">
            <el-radio-group v-model="form.visualStyle">
              <el-radio
                v-for="opt in visualStyleOptions"
                :key="opt.code"
                :value="opt.code"
              >
                {{ opt.name }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="小众程度" prop="rarityLevel">
            <el-rate v-model="form.rarityLevel" :max="4" show-score />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="特色亮点">
        <div class="highlight-inputs">
          <div v-for="(h, i) in form.highlights" :key="i" class="dynamic-row">
            <el-input v-model="form.highlights[i]" placeholder="亮点描述" />
            <el-button size="small" type="danger" @click="removeHighlight(i)">删除</el-button>
          </div>
        </div>
        <el-button size="small" @click="addHighlight" :disabled="form.highlights.length >= 5">
          添加亮点
        </el-button>
      </el-form-item>

      <el-form-item label="正文 (支持 Markdown)" prop="content">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="12"
          placeholder="在此输入 Markdown 内容..."
        />
      </el-form-item>

      <div class="form-actions">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">
          保存
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { fetchTravelDetail } from '@/api/travel'
import { createTravel, updateTravel } from '@/api/admin-travel'
import { ElMessage } from 'element-plus'
import ImagePreview from '@/components/ImagePreview.vue'

const experienceTypeOptions = [
  { code: 'extreme', name: '极限探险', icon: '🧗' },
  { code: 'culture', name: '文化沉浸', icon: '🎭' },
  { code: 'hidden', name: '秘境探索', icon: '🗺️' },
  { code: 'eco', name: '生态旅行', icon: '🌿' },
  { code: 'urban', name: '城市隐世', icon: '🏙️' },
  { code: 'taste', name: '味觉之旅', icon: '🍜' }
]

const visualStyleOptions = [
  { code: 'minimal', name: '极简' },
  { code: 'cinematic', name: '电影感' },
  { code: 'vintage', name: '复古胶片' },
  { code: 'vivid', name: '高饱和冲击' },
  { code: 'bw', name: '黑白纪实' },
  { code: 'natural', name: '自然光' }
]

const route = useRoute()
const router = useRouter()

const formRef = ref(null)
const loading = ref(false)
const submitting = ref(false)

const isEdit = computed(() => !!route.params.id)
const pageTitle = computed(() => isEdit.value ? '编辑旅行' : '新增旅行')

function emptyForm() {
  return {
    title: '',
    subtitle: '',
    coverImage: '',
    detailImages: [],
    experienceTypes: [],
    visualStyle: '',
    rarityLevel: 0,
    destination: '',
    content: '',
    highlights: []
  }
}

const form = reactive(emptyForm())

const rules = {
  title: { required: true, message: '请输入标题', trigger: 'blur' },
  subtitle: { required: true, message: '请输入副标题', trigger: 'blur' },
  coverImage: { required: true, message: '请输入封面图片URL', trigger: 'blur' },
  experienceTypes: { type: 'array', required: true, min: 1, message: '请至少选择一种体验类型', trigger: 'change' },
  visualStyle: { required: true, message: '请选择视觉风格', trigger: 'change' },
  rarityLevel: { type: 'number', required: true, min: 1, max: 4, message: '请设置小众程度', trigger: 'change' },
  destination: { required: true, message: '请输入目的地', trigger: 'blur' },
  content: { required: true, message: '请输入正文内容', trigger: 'blur' }
}

function populateForm(detail) {
  form.title = detail.title || ''
  form.subtitle = detail.subtitle || ''
  form.coverImage = detail.coverImage || ''
  form.detailImages = detail.detailImages ? [...detail.detailImages] : []
  form.experienceTypes = detail.experienceTypes ? [...detail.experienceTypes] : []
  form.visualStyle = detail.visualStyle || ''
  form.rarityLevel = detail.rarityLevel || 0
  form.destination = detail.destination || ''
  form.content = detail.content || ''
  form.highlights = detail.highlights ? [...detail.highlights] : []
}

function addDetailImage() {
  form.detailImages.push('')
}

function removeDetailImage(i) {
  form.detailImages.splice(i, 1)
}

function addHighlight() {
  form.highlights.push('')
}

function removeHighlight(i) {
  form.highlights.splice(i, 1)
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  submitting.value = true
  try {
    if (isEdit.value) {
      await updateTravel(route.params.id, form)
      ElMessage.success('更新成功')
    } else {
      await createTravel(form)
      ElMessage.success('创建成功')
    }
    router.push('/admin/travels')
  } catch {
    ElMessage.error('保存失败')
  } finally {
    submitting.value = false
  }
}

function handleCancel() {
  router.back()
}

onMounted(async () => {
  if (isEdit.value) {
    loading.value = true
    try {
      const detail = await fetchTravelDetail(route.params.id)
      populateForm(detail)
    } finally {
      loading.value = false
    }
  }
})
</script>

<style scoped>
.admin-edit-page {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  max-width: 900px;
  margin: 0 auto;
}
.page-title {
  margin-bottom: 24px;
  font-size: 20px;
}
.edit-form {
  max-width: 100%;
}
.dynamic-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}
.dynamic-row .el-input {
  flex: 1;
}
.preview-tip {
  margin-top: 8px;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid #eee;
}
</style>
