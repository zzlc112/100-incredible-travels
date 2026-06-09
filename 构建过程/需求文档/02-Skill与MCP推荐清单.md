# Skill 与 MCP 推荐清单

> 配套文档：[01-需求规格说明书](./01-需求规格说明书.md)

---

## 一、已安装的 Skills（共 11 个）

### 1.1 Superpowers 套件（obra/superpowers）

Superpowers 是 Claude Code 生态中最核心的技能套件，覆盖从需求分析到代码审查的完整开发流程。

| Skill | 安装命令 | 用途 | 对应开发范式 |
|-------|----------|------|-------------|
| `using-superpowers` | `npx skills add obra/superpowers@using-superpowers -g -y` | 技能发现与使用引导，每次对话启动时的入口 | 全流程 |
| `brainstorming` | `npx skills add obra/superpowers@brainstorming -g -y` | 创意阶段：需求探索、设计脑暴、方案发散 | **SDD 阶段** |
| `writing-plans` | `npx skills add obra/superpowers@writing-plans -g -y` | 多步骤任务规划：拿到需求后先写计划再写代码 | **SDD → DDD 过渡** |
| `test-driven-development` | `npx skills add obra/superpowers@test-driven-development -g -y` | TDD 流程：红→绿→重构循环 | **TDD 阶段** |
| `systematic-debugging` | `npx skills add obra/superpowers@systematic-debugging -g -y` | 系统化调试：遇到 bug 时的结构化排查方法论 | 全流程 |
| `requesting-code-review` | `npx skills add obra/superpowers@requesting-code-review -g -y` | 代码审查请求：完成功能后请求审查 | **TDD/E2E 阶段** |

### 1.2 专业领域 Skills

| Skill | 安装命令 | 用途 | 对应开发范式 |
|-------|----------|------|-------------|
| `ui-ux-pro-max` | `npx skills add sickn33/antigravity-awesome-skills@ui-ux-pro-max -g -y` | 设计驱动开发：配色、排版、组件设计、UX 审查 | **DDD 阶段** |
| `spring-boot` | `npx skills add mindrally/skills@spring-boot -g -y` | Spring Boot 最佳实践：RESTful API、测试、安全 | 后端全流程 |

### 1.3 内置 Skills（Claude Code 自带）

| Skill | 触发方式 | 用途 |
|-------|----------|------|
| `context-map` | `/context-map` | 生成任务相关文件地图 |
| `code-review` | `/code-review` | 代码审查 |
| `verify` | `/verify` | 验证改动效果 |
| `simplify` | `/simplify` | 代码简化与重构 |
| `run` | `/run` | 启动和验证应用 |

---

## 二、各阶段 Skill 使用指南

### SDD 阶段（数据建模 & API 契约）

```
1. brainstorming    → 脑暴业务需求，澄清模糊点
2. writing-plans    → 制定数据建模计划
3. context-map      → 梳理文件结构
```

### DDD 阶段（UI/UX 设计驱动开发）

```
1. ui-ux-pro-max    → 确定配色、排版、组件设计语言
2. brainstorming    → 对每个组件进行设计评审
3. writing-plans    → 制定前端组件开发计划
```

### TDD 阶段（测试驱动开发）

```
1. test-driven-development  → 先写失败的测试
2. spring-boot              → 后端最佳实践指导
3. systematic-debugging     → 修复测试失败
4. code-review              → 审查实现代码
```

### E2E 阶段（端到端测试 & 质量闭环）

```
1. test-driven-development  → E2E 测试场景设计
2. verify                   → 验证功能完整性
3. requesting-code-review   → 最终代码审查
4. simplify                 → 代码简化收尾
```

---

## 三、推荐的 MCP（Model Context Protocol）

MCP 让 Claude Code 能够直接与外部工具和服务交互，极大提升开发效率。

### 3.1 高优先级

#### SQLite MCP
- **用途**: 直接操作 SQLite 数据库
- **场景**: 
  - 验证数据模型设计
  - 快速插入测试数据
  - 调试时查看数据库状态
- **安装**: 在 Claude Code 中运行 `/mcp add sqlite`

#### GitHub MCP
- **用途**: 管理 GitHub 仓库
- **场景**:
  - 创建仓库、管理 Issue/PR
  - 规范化的提交与发布流程
  - 代码审查辅助
- **安装**: 在 Claude Code 中运行 `/mcp add github`

### 3.2 中优先级

#### Filesystem MCP
- **用途**: 安全的文件系统操作
- **场景**: 批量创建项目骨架、文件管理

#### Playwright MCP
- **用途**: 浏览器自动化
- **场景**: E2E 测试执行、UI 截图验证

---

## 四、使用原则

1. **不要一次性全用**: 按阶段逐步引入，每个阶段聚焦 2-3 个核心 Skill
2. **Skill 是顾问不是替代**: Skill 提供方法论指导，最终决策在开发者
3. **记录 Prompt**: 每次使用 Skill 时记录关键 Prompt，作为交付物的一部分
4. **MCP 按需安装**: 先装 SQLite MCP（数据建模阶段必须），其他按需补充

---

## 五、安装状态

| 类型 | 名称 | 状态 |
|------|------|------|
| Skill | obra/superpowers@using-superpowers | ✅ 已安装 |
| Skill | obra/superpowers@brainstorming | ✅ 已安装 |
| Skill | obra/superpowers@writing-plans | ✅ 已安装 |
| Skill | obra/superpowers@test-driven-development | ✅ 已安装 |
| Skill | obra/superpowers@systematic-debugging | ✅ 已安装 |
| Skill | obra/superpowers@requesting-code-review | ✅ 已安装 |
| Skill | sickn33/antigravity-awesome-skills@ui-ux-pro-max | ✅ 已安装 |
| Skill | mindrally/skills@spring-boot | ✅ 已安装 |
| MCP | SQLite MCP | ✅ 已安装 |
| MCP | Playwright MCP | ✅ 已安装 |
| MCP | GitHub MCP | ✅ 已安装 |
