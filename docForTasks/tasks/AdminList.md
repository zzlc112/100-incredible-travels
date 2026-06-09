# AdminList — 后台列表模块

> 路径: `src/views/admin/AdminList.vue` | 依赖: api-client(fetchTravels, deleteTravel) + router | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `src/views/admin/AdminList.vue`

#### 1.1 状态管理
- [ ] 响应式状态：
  - `travels` — `[]`
  - `total` — `0`
  - `currentPage` — `1`
  - `pageSize` — `20`
  - `searchKeyword` — `''`
  - `loading` — `false`
- [ ] `computed`: `filteredTableData` — 按 `searchKeyword` 前端过滤标题（简单搜索）

#### 1.2 表格列设计
- [ ] ID（60px）
- [ ] 封面缩略图（80px, `el-image`）
- [ ] 标题（200px, 截断 + `el-tooltip`）
- [ ] 体验类型（150px, `el-tag` × N）
- [ ] 小众程度（100px, ⭐）
- [ ] 目的地（120px）
- [ ] 创建时间（160px, 格式化）
- [ ] 操作列（150px）：编辑按钮 + 删除按钮

#### 1.3 方法
- [ ] `loadList()` — 调用 `fetchTravels({page: currentPage, size: pageSize})`
- [ ] `handleSearch()` — 搜索触发（可选：防抖后 API 搜索或前端过滤）
- [ ] `handleDelete(id)` — `el-message-box.confirm` 确认 → `deleteTravel(id)` → 刷新列表 + 成功提示
- [ ] `handleEdit(id)` — `router.push('/admin/travels/' + id + '/edit')`
- [ ] `handleCreate()` — `router.push('/admin/travels/new')`
- [ ] `handlePageChange(page)` — `currentPage = page` → `loadList()`

#### 1.4 模板
- [ ] 顶部操作栏：搜索框 + 新增按钮
- [ ] `el-table` 绑定数据 + 各列渲染
- [ ] `el-pagination` 分页
- [ ] 空数据状态：`el-empty`

### 2. 组件测试
- [ ] Mock `fetchTravels` / `deleteTravel` + Mount
- [ ] 列表正确渲染（行数、列内容）
- [ ] 翻页触发 `loadList` + page 参数正确
- [ ] 删除确认弹窗 → 确认后调用 `deleteTravel` → 刷新列表
- [ ] 删除取消 → 不调用 `deleteTravel`
- [ ] 编辑按钮 → 跳转 `/admin/travels/{id}/edit`
- [ ] 新增按钮 → 跳转 `/admin/travels/new`
- [ ] 搜索过滤 → 前端过滤标题
