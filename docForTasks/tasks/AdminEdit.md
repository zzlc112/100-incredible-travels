# AdminEdit — 后台编辑模块

> 路径: `src/views/admin/AdminEdit.vue` | 依赖: api-client + router + ImagePreview(MarkdownRenderer) | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `src/views/admin/AdminEdit.vue`

#### 1.1 状态管理
- [ ] 响应式状态：
  - `form` — 10 个字段（与 `TravelSaveRequest` 对应）
  - `isEdit` — `computed`：`!!route.params.id`
  - `loading` — `false`（编辑模式加载数据）
  - `submitting` — `false`（提交中）
- [ ] `computed`: `pageTitle` — `isEdit ? '编辑旅行' : '新增旅行'`

#### 1.2 表单控件
- [ ] `title` → `el-input`（必填，maxlength=200）
- [ ] `subtitle` → `el-input`（必填，maxlength=500）
- [ ] `coverImage` → `el-input` + `ImagePreview` 实时预览
- [ ] `detailImages` → 动态列表（`v-for` + 添加/删除按钮） + `ImagePreview`
- [ ] `experienceTypes` → `el-checkbox-group`（多选，选项来自常量 `EXPERIENCE_TYPE_MAP`）
- [ ] `visualStyle` → `el-radio-group`（选项来自常量 `VISUAL_STYLE_MAP`）
- [ ] `rarityLevel` → `el-rate`（max=4，显示 ⭐）
- [ ] `destination` → `el-input`（必填，maxlength=200）
- [ ] `content` → `el-input`(type=textarea, rows=12) + 顶部提示"支持 Markdown 格式"
- [ ] `highlights` → 动态 tag 列表（可添加/删除，最多 5 个）

#### 1.3 表单验证规则
- [ ] title — 必填 + max 200
- [ ] subtitle — 必填 + max 500
- [ ] coverImage — 必填 + URL 格式
- [ ] experienceTypes — 必填，至少选 1 个
- [ ] visualStyle — 必填
- [ ] rarityLevel — 必填，1-4
- [ ] destination — 必填 + max 200
- [ ] content — 必填

#### 1.4 方法
- [ ] `loadTravel(id)` — 调用 `fetchTravelDetail(id)` → 填充 form 各字段
- [ ] `handleSubmit()` —
  1. `el-form.validate` 验证
  2. `isEdit ? updateTravel(id, form) : createTravel(form)`
  3. 成功 → `ElMessage.success` + `router.push('/admin/travels')`
  4. 失败 → `ElMessage.error`
- [ ] `handleCancel()` — `router.back()` 或 `router.push('/admin/travels')`
- [ ] `addDetailImage()` — form.detailImages.push('')
- [ ] `removeDetailImage(i)` — form.detailImages.splice(i, 1)
- [ ] `addHighlight()` — form.highlights.push('')
- [ ] `removeHighlight(i)` — form.highlights.splice(i, 1)

#### 1.5 模板
- [ ] 页面标题（`pageTitle`）
- [ ] `el-form` + 各字段控件
- [ ] 底部操作栏：保存按钮（`submitting` loading）+ 取消按钮

#### 1.6 生命周期
- [ ] `onMounted`：if `isEdit` → `loadTravel(route.params.id)`

### 2. 组件测试
- [ ] 新增模式：Mount + Mock API
  - 表单为空
  - 提交成功 → `createTravel` 被调用 → 跳转
  - 空表单提交 → 验证提示
- [ ] 编辑模式：Mock `fetchTravelDetail` 返回数据
  - 表单预填充正确
  - 提交 → `updateTravel` 被调用
- [ ] 取消按钮 → `router.back`
- [ ] 动态列表：添加/删除 detailImages、highlights
