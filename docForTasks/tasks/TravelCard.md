# TravelCard — 旅行卡片组件

> 路径: `src/components/TravelCard.vue` | 依赖: 无（纯展示组件） | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 本地常量映射表
- [ ] 定义 `EXPERIENCE_TYPE_MAP`（6 个类型：extreme/culture/hidden/eco/urban/taste → 中文名+emoji）
- [ ] 定义 `VISUAL_STYLE_MAP`（6 个风格：minimal/cinematic/vintage/vivid/bw/natural → 中文名）
- [ ] 定义 `RARITY_LEVEL_MAP`（1-4 → ⭐ 字符串）

### 2. 组件实现
- [ ] 创建 `TravelCard.vue`
  - **Props**: `travel` (Object, required)，shape 含 id/title/subtitle/coverImage/experienceTypes/visualStyle/rarityLevel/destination
  - **Emits**: `click` — payload 为 `travel.id`
  - **模板**:
    - 封面图片 `el-image` + `@error` 处理 → 占位图
    - 标题 + 副标题展示
    - 体验类型标签（`el-tag` × N，颜色不同）
    - 视觉风格标签
    - 小众程度星级 ⭐
    - 目的地文字
  - **计算属性**:
    - `experienceLabels` — 编码 → 中文标签映射
    - `rarityStars` — 数字 → ⭐ 字符串
    - `visualStyleLabel` — 编码 → 中文名
  - **状态**: `imageError` — 图片加载失败时显示占位符

### 3. 组件测试
- [ ] Mount 组件 + 传入 mock `travel` 数据
- [ ] 正确渲染标题、副标题、目的地
- [ ] 正确显示星星数量（rarityLevel=3 → ⭐⭐⭐）
- [ ] 体验类型标签正确映射显示
- [ ] 图片加载失败时显示占位图
- [ ] 点击卡片触发 `@click` 事件且 payload 为正确 id
- [ ] 不依赖 router / API / 其他组件（纯展示验证）
