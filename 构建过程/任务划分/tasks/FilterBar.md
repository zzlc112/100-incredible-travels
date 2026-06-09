# FilterBar — 筛选栏组件

> 路径: `src/components/FilterBar.vue` | 依赖: Element Plus | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `FilterBar.vue`

#### 1.1 Props 与 Emits
- [ ] **Props**:
  - `modelValue` (Object, required) — 筛选状态 `{experienceTypes: [], visualStyle: '', rarityLevel: 0}`
  - `availableExperienceTypes` (Array) — 可选体验类型列表 `[{code, name, icon}]`
  - `availableVisualStyles` (Array) — 可选视觉风格列表 `[{code, name}]`
- [ ] **Emits**: `update:modelValue` + `reset`

#### 1.2 v-model 双向绑定
- [ ] 使用 computed getter/setter 实现 v-model
  - get → `props.modelValue`
  - set → `emit('update:modelValue', val)`

#### 1.3 筛选控件
- [ ] **体验类型** — `el-checkbox-button` 或 `el-tag` 多选
  - 点击切换选中/取消，更新 `modelValue.experienceTypes`
- [ ] **视觉风格** — `el-radio-group` + `el-radio-button`
  - 单选，可取消已选，更新 `modelValue.visualStyle`
- [ ] **小众程度** — `el-slider` 或 `el-rate`（范围 1-4）
  - 显示 ⭐ 指示，更新 `modelValue.rarityLevel`
- [ ] **重置按钮** — 触发 `emit('reset')`

#### 1.4 样式
- [ ] 筛选栏布局：水平排列，响应式换行
- [ ] 选中状态视觉反馈（颜色高亮）

### 2. 组件测试
- [ ] Mount 组件 + 传入 props
- [ ] 点击体验类型标签 → emit 新的 `modelValue`（含选中/取消逻辑）
- [ ] 选择视觉风格 → emit 新的 `modelValue`
- [ ] 拖拽滑块 → emit 新的 `modelValue`
- [ ] 点击重置按钮 → emit `reset` 事件
- [ ] 多个筛选条件组合 → modelValue 正确更新
