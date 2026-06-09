# TravelDetail — 详情页模块

> 路径: `src/views/TravelDetail.vue` | 依赖: api-client + marked + router | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `TravelDetail.vue`

#### 1.1 状态管理
- [ ] 响应式状态：
  - `travel` — `null | TravelDetailResponse`
  - `loading` — `false`
  - `imageError` — `false`（封面图加载失败）
- [ ] `computed`: `renderedContent` — `marked.parse(travel.content)`（Markdown → HTML）

#### 1.2 方法
- [ ] `loadDetail(id)` — 调用 `fetchTravelDetail(id)` → 更新 travel（失败显示 404 提示）
- [ ] `goBack()` — `router.back()` 或 `router.push('/')`
- [ ] `handleCoverError()` — `imageError = true`

#### 1.3 生命周期
- [ ] `onMounted` → `loadDetail(route.params.id)`
- [ ] `watch(() => route.params.id)` → `loadDetail(newId)`（同页面切换）

#### 1.4 模板布局
- [ ] **头部**：返回按钮（`el-page-header` 或 `el-button` + `@click="goBack"`）
- [ ] **封面大图**：`el-image` + 失败占位符
- [ ] **标题区**：标题(h1) + 目的地 + 小众程度 ⭐ + 副标题
- [ ] **标签区**：体验类型标签 × N + 视觉风格标签
- [ ] **特色亮点**：`el-tag` × N
- [ ] **详情图片**：轮播/图片列表
- [ ] **正文**：`<div class="markdown-body" v-html="renderedContent">` — 需引入 GitHub-markdown CSS
- [ ] **页脚**：createdAt / updatedAt 显示

#### 1.5 Markdown 渲染增强
- [ ] 引入 `github-markdown-css` 或自定义 markdown-body 样式
- [ ] marked 配置：链接 `target="_blank"`，代码高亮（可选 `highlight.js`）

### 2. 组件测试
- [ ] Mock `fetchTravelDetail` + mock `route.params` + Mount
- [ ] 加载中 → 显示 loading
- [ ] 各字段正确渲染（标题、目的地、标签、正文 HTML）
- [ ] Markdown → HTML 转换正确
- [ ] 封面图片加载失败 → 显示占位符
- [ ] 返回按钮 → 触发 `router.back`
- [ ] API 返回 404 → 显示 "内容不存在" 提示
- [ ] 切换到另一个 id → `watch` 触发重新加载
