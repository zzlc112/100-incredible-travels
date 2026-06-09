# Home — 首页模块

> 路径: `src/views/Home.vue` | 依赖: FilterBar + TravelCard + api-client | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `Home.vue`

#### 1.1 状态管理
- [ ] 响应式状态：
  - `filter` — `{experienceTypes: [], visualStyle: '', rarityLevel: 0}`
  - `travels` — `[]`
  - `total` — `0`
  - `currentPage` — `1`
  - `pageSize` — `12`
  - `loading` — `false`

#### 1.2 方法
- [ ] `loadTravels()` — 调用 `fetchTravels({...filter, page: currentPage, size: pageSize})` → 更新 travels/total
- [ ] `handleFilterChange(val)` — 更新 filter → 重置 currentPage=1 → 调用 loadTravels
- [ ] `handleReset()` — 重置 filter 为默认值 → 调用 loadTravels
- [ ] `handlePageChange(page)` — 更新 currentPage → 调用 loadTravels
- [ ] `handleCardClick(id)` — `router.push('/travel/' + id)`

#### 1.3 生命周期
- [ ] `onMounted` → 调用 `loadTravels()`
- [ ] `watch(filter, {deep: true})` → 调用 `loadTravels()`（debounce 可选）

#### 1.4 模板
- [ ] `<FilterBar v-model="filter" @reset="handleReset" />`
- [ ] 加载中 → `el-skeleton` / `v-loading`
- [ ] 卡片网格：`el-row` + `el-col`（响应式：桌面 3-4 列，平板 2 列，手机 1 列）
  - `v-for` 渲染 `TravelCard` + `@click="handleCardClick"`
- [ ] 空数据状态 → `el-empty` 提示 "暂无内容"
- [ ] 分页器：`el-pagination`（total / currentPage / pageSize / @current-change）

### 2. 组件测试
- [ ] Mock `fetchTravels` + Mount Home 组件
- [ ] 首次加载时显示 loading 状态
- [ ] 数据加载后正确渲染 N 张卡片
- [ ] 筛选条件变化 → API 参数正确（experienceType / visualStyle / rarityLevel 映射）
- [ ] 翻页触发 `loadTravels` + page 参数正确
- [ ] 卡片点击 → `router.push` 携带正确 id
- [ ] API 返回空列表 → 显示 `el-empty`
