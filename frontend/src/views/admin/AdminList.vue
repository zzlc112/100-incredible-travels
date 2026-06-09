<template>
  <div class="admin-list-page">
    <div class="top-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索标题..."
        clearable
        class="search-input"
        @input="handleSearch"
      />
      <el-button type="primary" @click="handleCreate">新增旅行</el-button>
    </div>

    <el-table :data="filteredTravels" v-loading="loading" stripe class="travel-table">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column label="封面" width="80">
        <template #default="{ row }">
          <el-image
            :src="row.coverImage"
            style="width:60px;height:40px;border-radius:4px"
            fit="cover"
          >
            <template #error>
              <div class="img-error">-</div>
            </template>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" show-overflow-tooltip />
      <el-table-column label="体验类型" width="150">
        <template #default="{ row }">
          <el-tag
            v-for="t in row.experienceTypes || []"
            :key="t"
            size="small"
            class="cell-tag"
          >
            {{ typeMap[t]?.icon }} {{ typeMap[t]?.name || t }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="小众程度" width="100">
        <template #default="{ row }">
          {{ '⭐'.repeat(row.rarityLevel || 0) }}
        </template>
      </el-table-column>
      <el-table-column prop="destination" label="目的地" width="120" show-overflow-tooltip />
      <el-table-column label="创建时间" width="160">
        <template #default="{ row }">
          {{ formatDate(row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div v-if="travels.length === 0 && !loading" class="empty-state">
      <el-empty description="暂无数据" />
    </div>

    <div v-if="total > pageSize" class="pagination-wrap">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next, total"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { fetchTravels } from '@/api/travel'
import { deleteTravel } from '@/api/admin-travel'
import { ElMessage, ElMessageBox } from 'element-plus'

const EXPERIENCE_TYPE_MAP = {
  extreme: { name: '极限探险', icon: '🧗' },
  culture: { name: '文化沉浸', icon: '🎭' },
  hidden: { name: '秘境探索', icon: '🗺️' },
  eco: { name: '生态旅行', icon: '🌿' },
  urban: { name: '城市隐世', icon: '🏙️' },
  taste: { name: '味觉之旅', icon: '🍜' }
}

const router = useRouter()
const typeMap = EXPERIENCE_TYPE_MAP

const travels = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(20)
const loading = ref(false)
const searchKeyword = ref('')

const filteredTravels = computed(() => {
  if (!searchKeyword.value) return travels.value
  const kw = searchKeyword.value.toLowerCase()
  return travels.value.filter(t => t.title.toLowerCase().includes(kw))
})

async function loadList() {
  loading.value = true
  try {
    const data = await fetchTravels({ page: currentPage.value, size: pageSize.value })
    travels.value = data.list || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  // client-side filtering via computed
}

function handleCreate() {
  router.push('/admin/travels/new')
}

function handleEdit(id) {
  router.push('/admin/travels/' + id + '/edit')
}

async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('确认删除该旅行内容？', '确认删除', {
      type: 'warning',
      confirmButtonText: '删除',
      cancelButtonText: '取消'
    })
    await deleteTravel(id)
    ElMessage.success('删除成功')
    loadList()
  } catch {
    // user cancelled
  }
}

function handlePageChange(page) {
  currentPage.value = page
  loadList()
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 19)
}

onMounted(() => loadList())
</script>

<style scoped>
.admin-list-page {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
}
.top-bar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 20px;
}
.search-input {
  max-width: 300px;
}
.travel-table {
  width: 100%;
}
.cell-tag {
  margin-right: 4px;
}
.img-error {
  width: 60px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f0f0;
  border-radius: 4px;
  font-size: 12px;
  color: #999;
}
.empty-state {
  padding: 40px 0;
}
.pagination-wrap {
  display: flex;
  justify-content: center;
  margin-top: 24px;
}
</style>
