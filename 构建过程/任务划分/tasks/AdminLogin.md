# AdminLogin — 后台登录模块

> 路径: `src/views/admin/AdminLogin.vue` | 依赖: api-client(login) + router | 测试层级: Vitest + Vue Test Utils

## 任务列表

### 1. 组件实现
- [ ] 创建 `src/views/admin/AdminLogin.vue`

#### 1.1 状态与表单
- [ ] 响应式状态：
  - `form` — `{username: '', password: ''}`
  - `loading` — `false`
  - `errorMessage` — `''`
- [ ] 表单验证规则（`el-form` rules）：
  - username: `{required: true, message: '请输入用户名', trigger: 'blur'}`
  - password: `{required: true, message: '请输入密码', trigger: 'blur'}`

#### 1.2 方法
- [ ] `handleLogin()` —
  1. `el-form.validate` 验证
  2. 调用 `login(form.username, form.password)`
  3. 成功 → `router.push('/admin/travels')`
  4. 失败 → `errorMessage = '用户名或密码错误'`

#### 1.3 模板
- [ ] 居中登录卡片布局
- [ ] Logo/标题："100种不可思议旅行 - 后台管理"
- [ ] `el-form` + `el-input`(username) + `el-input`(password, show-password)
- [ ] `el-button`(submit) + loading 状态
- [ ] `el-alert` 显示 `errorMessage`（type=error, 可关闭）

### 2. 组件测试
- [ ] Mock `login` 函数 + Mount 组件
- [ ] 空表单提交 → 验证提示出现
- [ ] 成功登录 → `router.push('/admin/travels')` 被调用
- [ ] 失败登录 → `errorMessage` 显示 "用户名或密码错误"
- [ ] 登录按钮 loading 状态正确切换
