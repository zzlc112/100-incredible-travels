# admin-auth — 认证模块

> 包路径: `com.travel.auth` | 依赖: `travel-mapper`(AdminUserMapper) + `travel-entity` | 测试层级: 单元+集成测试

## 任务列表

### 1. 认证配置
- [ ] 创建 `AuthConfig.java`（`@ConfigurationProperties(prefix = "admin")`）
  - 字段：username / password（带默认值 admin / admin123）
- [ ] 在 `application.yml` 添加 `admin.username` / `admin.password` 配置项
- [ ] 启动类加 `@EnableConfigurationProperties(AuthConfig.class)`

### 2. AuthService
- [ ] 创建 `AuthService.java`
  - 方法：`boolean login(LoginRequest req, HttpSession session)`
  - 逻辑：匹配配置文件中的账号密码 → session.setAttribute("admin", username)
  - 匹配失败返回 false（不抛异常）

### 3. AuthFilter（拦截器）
- [ ] 创建 `AuthFilter.java` 实现 `Filter` 接口（或 `HandlerInterceptor`）
  - `@WebFilter(urlPatterns = "/api/admin/*")`
  - 放行规则：OPTIONS 请求 + `/api/admin/login` 路径
  - 鉴权逻辑：`session.getAttribute("admin") != null` → 放行
  - 未登录：返回 401 JSON `{"code":401,"message":"未登录"}`
- [ ] 启动类加 `@ServletComponentScan`

### 4. LoginController
- [ ] 创建 `LoginController.java`
  - `POST /api/admin/login` → `login(@Valid @RequestBody LoginRequest req, HttpSession session)` → 成功 200 / 失败 401
  - `POST /api/admin/logout` → `logout(HttpSession session)` → session.invalidate() → 200
  - `GET /api/admin/check` → `checkAuth(HttpSession session)` → 已登录 200 / 未登录 401

### 5. 认证模块测试
- [ ] AuthService 单元测试：正确密码通过 / 错误密码拒绝
- [ ] AuthFilter 单元测试：Mock Request/Response/Session
  - 无 session → 401
  - 有 session 但无 admin 属性 → 401
  - 有 session + admin 属性 → 放行
  - OPTIONS 请求 → 放行
  - `/api/admin/login` → 放行
- [ ] LoginController 集成测试：`@WebMvcTest` + Mock Session
  - 正确登录 → 200
  - 错误登录 → 401
  - 登出 → 200
  - 检查登录状态 → 已登录 200 / 未登录 401
