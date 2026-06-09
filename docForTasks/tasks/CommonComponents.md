# CommonComponents — 公共组件模块

> 路径: `src/components/` | 依赖: Element Plus + marked | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. ImagePreview — 图片预览组件
- [ ] 创建 `src/components/ImagePreview.vue`
- [ ] **Props**: `url`(String, required) + `width`(String, '100%') + `height`(String, 'auto') + `fit`(String, 'cover')
- [ ] **State**: `error` — 加载失败时 `true`
- [ ] **模板**:
  - 成功：`<img :src="url" @error="error = true" />`
  - 失败：占位符 div + `el-icon` (PictureFilled) + "图片加载失败"
- [ ] **测试**：传入有效 URL → 渲染 img / 传入无效 URL → 显示占位

### 2. MarkdownRenderer — Markdown 渲染组件
- [ ] 创建 `src/components/MarkdownRenderer.vue`
- [ ] **Props**: `content`(String, required)
- [ ] **Computed**: `renderedHtml` — `marked.parse(props.content || '')`
- [ ] **模板**: `<div class="markdown-body" v-html="renderedHtml">`
- [ ] 配置 marked：链接 `target="_blank"`、`rel="noopener"`
- [ ] 引入 `github-markdown-css` 或自定义 markdown-body 样式
- [ ] **测试**：传入 Markdown 字符串 → 验证 HTML 输出、链接 target 正确

### 3. AppLayout — 前台布局组件
- [ ] 创建 `src/components/AppLayout.vue`
- [ ] **Slots**: `default`（主内容区）
- [ ] **模板**:
  - `<header>` — Logo "100种不可思议旅行" + 导航 `<router-link to="/">` 首页
  - `<main>` — `<slot />`
- [ ] 基础样式：header 固定/粘性、main 居中最大宽度
- [ ] **测试**：Mount 组件 + slot 内容 → 验证渲染

### 4. AdminLayout — 后台布局组件
- [ ] 创建 `src/components/AdminLayout.vue`
- [ ] **Slots**: `default`（主内容区）
- [ ] **模板**:
  - `<header>` — "后台管理" 标题 + `el-button` "退出登录" + `@click="handleLogout"`
  - `<main>` — `<slot />`
- [ ] 方法：`handleLogout()` → 调用 `logout()` → `router.push('/admin/login')`
- [ ] **测试**：验证 slot 渲染 + 退出登录按钮行为

### 5. 全局样式
- [ ] 创建 `src/styles/` 目录
- [ ] 全局 CSS 变量（配色：主色 `#1a1a2e`、辅色 `#e94560`、背景 `#fafafa`）
- [ ] Element Plus 主题定制（可选，使用 CSS 变量覆盖）
- [ ] 响应式断点（mobile < 768px < tablet < 1024px < desktop）
- [ ] 基础排版样式（`body` / `h1-h6` / `p` 等）
