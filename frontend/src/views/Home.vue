<template>
  <div class="home-page">
    <FilterBar v-model="filter" @reset="handleReset" />

    <div v-loading="loading" class="travel-grid">
      <template v-if="travels.length > 0">
        <div
          v-for="travel in travels"
          :key="travel.id"
          class="grid-item"
        >
          <TravelCard :travel="travel" @click="handleCardClick" />
        </div>
      </template>
      <el-empty v-else-if="!loading" description="暂无内容" class="empty-state" />
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
import { ref, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { fetchTravels } from '@/api/travel'
import TravelCard from '@/components/TravelCard.vue'
import FilterBar from '@/components/FilterBar.vue'

const router = useRouter()

const filter = ref({ experienceTypes: [], visualStyle: '', rarityLevel: 0 })
const travels = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(12)
const loading = ref(false)

async function loadTravels() {
  loading.value = true
  try {
    const params = { page: currentPage.value, size: pageSize.value }
    if (filter.value.experienceTypes.length > 0) {
      params.experienceType = filter.value.experienceTypes.join(',')
    }
    if (filter.value.visualStyle) {
      params.visualStyle = filter.value.visualStyle
    }
    if (filter.value.rarityLevel > 0) {
      params.rarityLevel = filter.value.rarityLevel
    }
    const data = await fetchTravels(params)
    travels.value = data.list || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleReset() {
  filter.value = { experienceTypes: [], visualStyle: '', rarityLevel: 0 }
  currentPage.value = 1
  loadTravels()
}

function handlePageChange(page) {
  currentPage.value = page
  loadTravels()
}

function handleCardClick(id) {
  router.push('/travel/' + id)
}

let timer = null
watch(filter, () => {
  clearTimeout(timer)
  timer = setTimeout(() => {
    currentPage.value = 1
    loadTravels()
  }, 300)
}, { deep: true })

onMounted(() => {
  loadTravels()
})
</script>

<style scoped>
.home-page {
  padding: 0;
}
.travel-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  min-height: 300px;
}
@media (max-width: 1024px) {
  .travel-grid { grid-template-columns: repeat(3, 1fr); }
}
@media (max-width: 768px) {
  .travel-grid { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 480px) {
  .travel-grid { grid-template-columns: 1fr; }
}
.grid-item {
  width: 100%;
}
.empty-state {
  grid-column: 1 / -1;
  padding: 60px 0;
}
.pagination-wrap {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}
</style>
