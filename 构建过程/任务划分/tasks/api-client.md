# api-client — HTTP 客户端模块

> 路径: `src/api/` | 依赖: `axios` | 测试层级: Vitest + Mock Axios

## 任务列表

### 1. 项目骨架
- [ ] 用 Vite 创建 Vue 3 项目：`npm create vite@latest frontend -- --template vue`
- [ ] 安装依赖：`vue-router`、`axios`、`element-plus`、`marked`
- [ ] 安装测试依赖：`vitest`、`@vue/test-utils`、`jsdom`
- [ ] 配置 `vite.config.js`：`@` 别名、dev server proxy `/api` → `http://localhost:8080`

### 2. Axios 实例封装 (request.js)
- [ ] 创建 `src/api/request.js`
  - 创建 Axios 实例：`baseURL: "/api"`、`timeout: 10000`、`withCredentials: true`
  - 响应拦截器：
    - `code == 200` → 返回 `response.data.data`
    - `code != 200` → `Promise.reject({code, message})`
    - 网络错误 → `Promise.reject({code: 500, message: "网络错误"})`

### 3. 前台 API 函数 (travel.js)
- [ ] `fetchTravels(params)` — `GET /api/travels`，返回 `{total, page, size, list}`
- [ ] `fetchTravelDetail(id)` — `GET /api/travels/{id}`，返回 `TravelDetailResponse`

### 4. 后台 API 函数 (admin-travel.js)
- [ ] `createTravel(data)` — `POST /api/admin/travels`
- [ ] `updateTravel(id, data)` — `PUT /api/admin/travels/{id}`
- [ ] `deleteTravel(id)` — `DELETE /api/admin/travels/{id}`

### 5. 认证 API 函数 (auth.js)
- [ ] `login(username, password)` — `POST /api/admin/login`
- [ ] `logout()` — `POST /api/admin/logout`
- [ ] `checkAuth()` — `GET /api/admin/check`

### 6. 统一导出 (index.js)
- [ ] `src/api/index.js` — 集中 re-export 所有 API 函数

### 7. API 模块单元测试
- [ ] Mock Axios → 测试 `fetchTravels` 请求参数正确拼接到 URL
- [ ] 测试响应数据正确解包（`code==200` 返回 `data` 字段）
- [ ] 测试业务错误 → `Promise.reject`
- [ ] 测试 `checkAuth` 返回 401 时的错误处理
