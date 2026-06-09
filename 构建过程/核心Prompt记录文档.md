# 核心 Prompt 记录文档

> 摘录项目开发过程中对 AI 分发的核心 Prompt，按开发范式分阶段标注。

---

## 【SDD 阶段】数据建模与 API 契约生成

### 1. 数据库 Schema + 实体类 Prompt

```markdown
*** 模块 1.2: travel-entity ***

实现规格:
1. schema.sql:
   CREATE TABLE IF NOT EXISTS travel (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       title VARCHAR(200) NOT NULL,
       subtitle VARCHAR(500) NOT NULL,
       cover_image VARCHAR(1000) NOT NULL,
       detail_images TEXT,
       experience_types TEXT NOT NULL,
       visual_style VARCHAR(50) NOT NULL,
       rarity_level INTEGER NOT NULL CHECK(rarity_level >= 1 AND rarity_level <= 4),
       destination VARCHAR(200) NOT NULL,
       content TEXT NOT NULL,
       highlights TEXT,
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
   );

2. data.sql — ≥5 条示例数据（冰岛火山、日本禅修、西伯利亚驯鹿...）
   experience_types / detail_images / highlights 用 JSON 数组格式

3. Travel.java — @TableName("travel"), 13 字段, @TableId(type=IdType.AUTO)
   JSON 字段存 String, createdAt/updatedAt 自动填充

4. AdminUser.java — @TableName("admin_user"), id/username/password
```

---

### 2. DTO + API 契约 Prompt

```markdown
*** 模块 1.3: travel-dto ***

ApiResponse<T>: code(200/400/401/404/500) + data + message
PageResult<T>: total + page + size + list, from(IPage) 工厂方法

TravelListResponse (10字段) — 不包含 content/detailImages/updatedAt
TravelDetailResponse (13字段) — 全字段 + JSON数组→List<String>

TravelSaveRequest: @NotBlank title(≤200) subtitle(≤500) content
  @NotEmpty experienceTypes, @NotNull @Min(1)@Max(4) rarityLevel
  detailImages @Size(max=10), highlights @Size(max=5)
```


---

## 【TDD 阶段】核心逻辑的测试用例编写与业务实现

### 3. Service 层测试 + 实现 Prompt

```markdown
*** 模块 1.5: travel-service ***

TravelServiceImpl 核心逻辑:
- JSON工具方法(parseJsonArray/toJsonArray) — Jackson ObjectMapper
- Entity↔DTO转换(toListResponse/toDetailResponse/toEntity/updateEntity)
- CRUD: listTravels(分页筛选) / getDetail(404) / create / update / delete

测试要求（Mock TravelMapper, 纯单元测试, 不启动Spring容器）:
- listTravels: 正常返回分页数据 / 空列表
- getTravelDetail: 存在→返回 / 不存在→抛 NotFoundException
- createTravel: 验证 insert 调用 → 返回含 id 的响应
- parseJsonArray: null→[], "[\"a\"]"→["a"], ""→[]
- 覆盖率目标: ≥ 80%
```



---

## 【DDD 阶段】前端组件与页面生成

### 4. TravelCard 组件 Prompt

```markdown
*** 模块 2.4: TravelCard ***

本地常量映射表:
  EXPERIENCE_TYPE_MAP: extreme→🧗极限探险, culture→🎭文化沉浸, hidden→🗺️秘境探索...
  VISUAL_STYLE_MAP: minimal→极简, cinematic→电影感, vintage→复古胶片...

Props: travel (Object, required) — {id, title, subtitle, coverImage, experienceTypes[], visualStyle, rarityLevel, destination}
Emits: click → travel.id

模板: 封面图(el-image+@error→占位图) + 标题+副标题 + 体验类型标签(不同颜色) +
      视觉风格标签 + 小众程度⭐(rarityLevel=3→⭐⭐⭐) + 目的地

测试: 渲染标题/目的地/星级, click emit正确id, 图片失败→占位符, 不依赖router/API
```


---

### 5. Home 页面组装 Prompt

```markdown
*** 模块 3.1: Home ***

响应式状态: filter/{experienceTypes:[],visualStyle:'',rarityLevel:0}, travels[], total, currentPage, pageSize, loading

方法:
- loadTravels(): 调 fetchTravels(提取filter参数, page, size)
- handleFilterChange(val): 更新filter→currentPage=1→loadTravels
- handleReset(): filter重置→loadTravels
- handleCardClick(id): router.push('/travel/'+id)

模板: FilterBar(v-model="filter" @reset) + v-loading + 卡片网格(el-row/el-col响应式4/3/2/1列)
      + v-for渲染TravelCard+@click + el-empty + el-pagination

过滤器参数映射: experienceTypes→逗号拼接experienceType, visualStyle直接传, rarityLevel>0才传
```


---

## 【E2E 阶段】系统级端到端测试与质量闭环

### 6. Playwright E2E Prompt

```markdown
*** 模块 4.2: E2E 测试 ***

E2E-01 浏览+筛选 (browse-and-filter.spec.js):
  打开首页→验证≥5张卡片→点击体验类型"极限探险"→验证卡片过滤→设置小众程度≥3→验证URL参数同步

E2E-02 查看详情 (view-detail.spec.js):
  从首页点击第一张卡片→验证详情页(标题、图片、Markdown渲染)→点击返回→返回首页

E2E-03 后台新增 (admin-create.spec.js):
  访问/admin/login→输入admin/admin123→登录→点击"新增"→填写表单→提交→验证跳转回列表→访问前台→验证新卡片

Playwright配置: baseURL(localhost:5173), webServer自动启动前后端, headless+retry
```



---

## 质量门禁（全阶段通用）

```markdown
后端: mvn test (56用例) + Checkstyle + SpotBugs
前端: npx vitest run (34用例) + ESLint + Prettier
E2E:  npx playwright test (3 spec)
每个模块完成后 git commit, 提交格式: docs/schema | feat/backend | feat/frontend | test/unit | test/e2e | docs/readme
```

> **意图**: 每个模块独立测试、独立提交，确保任何一次回退不会影响其他模块。最终 Git 历史 18 个 commit，从 `docs/schema` 到 `docs/readme` 形成清晰演进链。

---

## 防幻觉策略

| # | 策略 | 说明 |
|---|------|------|
| 1 | **多对话分段** | 需求分析→设计→任务拆分→Prompt生成→代码实现，5个独立对话防止上下文压缩 |
| 2 | **强制提问** | Prompt 中写死"有不明确的地方直接弹提问让我选择"，避免 AI 猜测意图 |
| 3 | **子 Agent 隔离** | 每个模块独立子 Agent，出问题只影响一个模块，不会连锁崩溃 |
