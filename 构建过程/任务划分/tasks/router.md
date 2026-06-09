# router — 路由模块

> 路径: `src/router/index.js` | 依赖: `vue-router` + `api-client`(checkAuth) | 测试层级: Vitest 单元测试

## 任务列表

### 1. 路由表配置
- [ ] 创建 `src/router/index.js`
  - `/` → `Home.vue`
  - `/travel/:id` → `TravelDetail.vue`
  - `/admin/login` → `AdminLogin.vue`（`meta: {guest: true}`）
  - `/admin/travels` → `AdminList.vue`（`meta: {auth: true}`）
  - `/admin/travels/new` → `AdminEdit.vue`（`meta: {auth: true}`）
  - `/admin/travels/:id/edit` → `AdminEdit.vue`（`meta: {auth: true}`）
- [ ] 配置 404 通配路由 → 重定向到 `/`

### 2. 全局前置守卫
- [ ] 实现 `router.beforeEach`
  - `to.meta.auth` → 调用 `checkAuth()`，失败则 `next('/admin/login')`
  - `to.meta.guest` → 调用 `checkAuth()`，成功则 `next('/admin/travels')`
  - 其他 → `next()`

### 3. 路由模块测试
- [ ] 创建 router 实例 + Mock `checkAuth` 函数
- [ ] 测试 `auth` meta 页面 — 未登录 → 重定向 `/admin/login`
- [ ] 测试 `auth` meta 页面 — 已登录 → 正常进入
- [ ] 测试 `guest` meta 页面 — 已登录 → 重定向 `/admin/travels`
- [ ] 测试 `guest` meta 页面 — 未登录 → 正常进入
- [ ] 测试不存在的路径 → 404 行为
