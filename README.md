# 100种不可思议旅行

飞猪旅行旗下子品牌的轻量级内容展示 Web App MVP。展示全球极致旅行体验，支持多维度筛选和后台内容管理。

## 技术选型

| 层级 | 技术 | 选型理由 |
|------|------|----------|
| 后端框架 | Spring Boot 3.3 | 企业级 Java 框架，生态成熟 |
| ORM | MyBatis-Plus 3.5 | 轻量级 CRUD 增强，分页插件内置 |
| 数据库 | SQLite 3 | 零配置嵌入式数据库，适合 MVP 快速启动 |
| 前端框架 | Vue 3 (Composition API) | 响应式 UI 框架，`<script setup>` 语法简洁 |
| UI 组件库 | Element Plus 2 | Vue 3 生态首选，组件丰富 |
| 构建工具 | Vite 5 | 极速冷启动和 HMR |
| HTTP 客户端 | Axios 1 | Promise 封装，拦截器支持 |
| Markdown | marked | 轻量级 Markdown 解析器 |
| 测试 | JUnit 5 + Vitest + Playwright | 后端/前端/E2E 全覆盖 |

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+ 或使用 Maven Wrapper
- Node.js 18+

### 启动后端

```bash
cd backend
mvn spring-boot:run
# 后端运行在 http://localhost:8080
```

### 启动前端

```bash
cd frontend
npm install
npm run dev
# 前端运行在 http://localhost:5173
```

### 运行测试

```bash
# 后端单元测试
cd backend && mvn test

# 前端单元测试
cd frontend && npm test

# E2E 测试
cd e2e && npm install && npx playwright test
```

## 后台管理

- 地址: http://localhost:5173/admin/login
- 账号: admin
- 密码: admin123

## 项目结构

```
100-incredible-travels/
├── backend/                        # Spring Boot 后端
│   ├── src/main/java/com/travel/
│   │   ├── entity/                 # 数据实体
│   │   ├── dto/                    # 数据传输对象
│   │   ├── mapper/                 # MyBatis-Plus Mapper
│   │   ├── service/                # 业务逻辑层
│   │   ├── controller/             # REST 控制器
│   │   ├── auth/                   # 认证模块
│   │   ├── config/                 # 配置类
│   │   └── exception/              # 异常类
│   └── src/test/                   # 后端测试 (56 用例)
├── frontend/                       # Vue 3 前端
│   ├── src/
│   │   ├── api/                    # HTTP API 封装
│   │   ├── router/                 # 路由配置
│   │   ├── components/             # 公共组件
│   │   ├── views/                  # 页面视图
│   │   └── styles/                 # 全局样式
│   └── tests/                      # 前端测试 (34 用例)
├── e2e/                            # Playwright E2E 测试 (3 spec)
├── docs/                           # 技术文档
│   ├── API文档.md                  # 8 端点完整 API 文档
│   ├── ER图.md                     # 数据模型 ER 图
│   └── prompt.md                   # Vibe Coding 起始 Prompt
├── docForTasks/                    # 任务规格
│   ├── prompt.md                   # 主 Agent Prompt
│   └── tasks/                      # 各模块任务清单 (17 个)
├── doc/                            # 项目跟踪
│   └── tasks/progress.md           # 模块进度跟踪表
├── 需求文档/                        # 需求阶段产出
│   ├── 01-需求规格说明书.md          # 功能需求规格
│   ├── 02-Skill与MCP推荐清单.md      # Skills / MCP 推荐
│   └── detailed-design.md           # 详细设计文档
├── 核心Prompt记录文档.md            # Prompt 工程记录
└── Prompt构建过程.md                # 构建流程回顾
```

## 文档索引

| 文档 | 说明 |
|------|------|
| [docs/API文档.md](docs/API文档.md) | 8 端点完整 API 文档（请求/响应/错误码/枚举） |
| [docs/ER图.md](docs/ER图.md) | 数据模型设计（Mermaid） |
| [docs/prompt.md](docs/prompt.md) | Vibe Coding 起始 Prompt |
| [docForTasks/prompt.md](docForTasks/prompt.md) | 主 Agent 调度 Prompt |
| [docForTasks/tasks/](docForTasks/tasks/) | 各模块子 Agent 任务规格（17 个） |
| [doc/tasks/progress.md](doc/tasks/progress.md) | 模块进度跟踪 |
| [需求文档/01-需求规格说明书.md](需求文档/01-需求规格说明书.md) | 功能需求规格 |
| [需求文档/detailed-design.md](需求文档/detailed-design.md) | 详细设计 |
| [核心Prompt记录文档.md](核心Prompt记录文档.md) | Prompt 工程经验记录 |
| [Prompt构建过程.md](Prompt构建过程.md) | 5 步对话构建流程回顾 |

## API 接口

完整 API 文档见 [docs/API文档.md](docs/API文档.md)（8 个端点，含请求/响应示例、错误码、枚举字典）。
