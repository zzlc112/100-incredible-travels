# 我的 Prompt 构建流程

> 使用 VS Code Claude 插件接入 DeepSeek V4 Pro 进行任务构建与分配，终端 Claude (Medium) 派发子 Agent，完成项目构建、测试与 Bug 修复。

---

## 目录

- [概述](#概述)
- [Step 1 — 需求文档 + Skills / MCP 推荐](#step-1--需求文档--skills--mcp-推荐)
- [Step 2 — 详细设计文档](#step-2--详细设计文档)
- [Step 3 — 模块任务拆分](#step-3--模块任务拆分)
- [Step 4 — Vibe Coding 起始 Prompt](#step-4--vibe-coding-起始-prompt)
- [Step 5 — 终端启动主 Agent 构建项目](#step-5--终端启动主-agent-构建项目)
- [防幻觉策略](#防幻觉策略)

---

## 概述

整个构建流程分为 **5 个对话**，逐层推进，最终由主 Agent 自动完成全部编码与测试：

```
需求文档 → 详细设计 → 任务拆分 → 主/子Agent Prompt → 自动构建
```

---

## Step 1 — 需求文档 + Skills / MCP 推荐

**目标**: 分析项目需求，生成需求规格说明书，并推荐可用的 Skills 和 MCP 工具。

**对话角色**: VS Code Claude 插件 (DeepSeek V4 Pro)

**输入**: `F:\code\Java面试\项目要求.txt`

**输出**: `需求文档/需求规格说明书.md`

**Prompt**:

```markdown
我有一个项目开发的任务。
输入: 任务内容在 "F:\code\Java面试\项目要求.txt"。

输出: 需求文档，放在 /当前目录/需求文档/**.md

步骤:
  请分析然后生成对应的需求文档。
  同时我需要你给出你推荐我使用的 skill 和 mcp。
  有不明确的地方以提问的方式向我提问，严禁自己猜测。
```

---

## Step 2 — 详细设计文档

**目标**: 根据需求文档进行模块划分，生成详细设计文档。模块之间保持独立，可独立测试。

**对话角色**: VS Code Claude 插件 (DeepSeek V4 Pro)

**输入**: `需求文档/需求规格说明书.md`

**输出**: `需求文档/detailed-design.md`

**Prompt**:

```markdown
目标: 根据需求文档生成详细设计文档。

输入: 需求文档/需求规格说明书.md

输出: 详细设计文档 → 需求文档/detailed-design.md

步骤:
  根据需求文档的内容，根据里面划分出的模块详细设计文档。
  模块与模块之间尽量保持独立，可以独立测试。
  不要猜测我的意图，任何不明确的地方必须向我提问。
```

---

## Step 3 — 模块任务拆分

**目标**: 为每个模块划分最小可执行任务，生成任务清单和进度跟踪文件。

**对话角色**: VS Code Claude 插件 (DeepSeek V4 Pro)

**输入**:
- `需求文档/需求规格说明书.md`
- `需求文档/detailed-design.md`

**输出**:
- `doc/tasks/<module-name>.md` — 每个模块一个任务清单
- `doc/tasks/progress.md` — 总体进度跟踪

**Prompt**:

```markdown
目标: 为每个模块划分最小可执行任务。

输入:
  需求文档 → 需求文档/需求规格说明书.md
  详细设计 → 需求文档/detailed-design.md

输出:
  - doc/tasks/<module-name>.md (每个模块对应一个)
  - doc/tasks/progress.md (总体进度)

步骤:
  根据需求文档和详细设计，为每个模块生成 Vibe Coding 用的最小任务。
  每个模块对应一个 <module-name>.md，用 check list 表示子任务是否完成。
  progress.md 中用 check list 表示模块已经完成。
```

---

## Step 4 — Vibe Coding 起始 Prompt

**目标**: 生成包含主 Agent 和子 Agent Prompt 的完整 Vibe Coding 起始文档。

**对话角色**: VS Code Claude 插件 (DeepSeek V4 Pro)

**输入**:
- `需求文档/需求规格说明书.md`
- `需求文档/detailed-design.md`
- `doc/tasks/` (任务目录)

**输出**: `doc/prompt.md`

**Prompt**:

```markdown
目标: 生成 Vibe Coding 用的 prompt。

输入:
  需求文档 → 需求文档/需求规格说明书.md
  详细设计 → 需求文档/detailed-design.md
  任务划分 → doc/tasks/

输出: doc/prompt.md

步骤:
  阅读输入信息，了解当前要实现的工程。
  生成 doc/prompt.md 作为 Vibe Coding 的起始 Prompt。

  主 Agent，用来跟踪整体的进度。
  主 Agent 生成子 Agent，用来实现每一个模块，并完成测试。
  整个过程不会有人工参与。

  代码必须有整体的单元测试，并通过 mypy 和 ruff 检测。

  生成 prompt 过程中，如果有任何不明确的地方直接给我弹提问让我选择。
```

---

## Step 5 — 终端启动主 Agent 构建项目

**对话角色**: 终端 Claude (Medium)

**操作**: 在终端中让 Claude 读取 `doc/prompt.md`，按 Phase 1→2→3→4 逐模块构建项目。

**执行流程**:

```
Phase 1: 后端基础 (7 个模块)
  config → entity → dto → mapper → service → controller → auth

Phase 2: 前端基础 (5 个模块)
  api-client → router → CommonComponents → TravelCard → FilterBar

Phase 3: 页面组装 (5 个模块)
  Home → TravelDetail → AdminLogin → AdminList → AdminEdit

Phase 4: 集成与交付
  联调 → E2E → 文档
```

---

## 防幻觉策略

为避免 AI 在长上下文中出现幻觉，采用以下 3 项措施：

| # | 策略 | 说明 |
|---|------|------|
| 1 | **多对话分段** | 将需求分析、任务分配、代码生成拆分到独立对话中，防止上下文压缩导致信息丢失 |
| 2 | **强制提问** | 在 Prompt 中明确要求"有不明确的地方直接弹提问让我选择"，避免 AI 做出危险或错误操作 |
| 3 | **Superpowers 规范** | 使用 `using-superpowers` 等 Skill 规范 AI 的开发流程，约束行为边界 |
