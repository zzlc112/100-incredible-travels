# travel-config — 配置模块

> 包路径: `com.travel.config` | 依赖: 无业务依赖（仅框架层） | 测试层级: 集成测试

## 任务列表

### 1. MyBatis-Plus 分页插件配置
- [ ] 创建 `MybatisPlusConfig.java`
  - `@Configuration` + `@MapperScan("com.travel.mapper")`
  - 注册 `MybatisPlusInterceptor` Bean，添加 `PaginationInnerInterceptor(DbType.SQLITE)`

### 2. 自动填充配置
- [ ] 创建 `MetaObjectHandlerConfig.java` 实现 `MetaObjectHandler`
  - `insertFill`：填充 `createdAt` + `updatedAt` 为 `LocalDateTime.now()`
  - `updateFill`：填充 `updatedAt` 为 `LocalDateTime.now()`

### 3. CORS 配置
- [ ] 创建 `WebMvcConfig.java` 实现 `WebMvcConfigurer`
  - 允许 `/api/**` 跨域
  - `allowedOriginPatterns("*")` + `allowedMethods(GET, POST, PUT, DELETE, OPTIONS)` + `allowCredentials(true)`

### 4. Jackson 配置
- [ ] 创建 `JacksonConfig.java`（可选，根据日期序列化需求）
  - 日期格式 `yyyy-MM-dd HH:mm:ss` + 时区 Asia/Shanghai

### 5. Spring Boot 启动类
- [ ] 创建/完善 `TravelApplication.java`
  - `@SpringBootApplication` + `@EnableConfigurationProperties` + `@ServletComponentScan`

### 6. 配置模块测试
- [ ] 验证 `MybatisPlusInterceptor` Bean 存在且包含 `PaginationInnerInterceptor`
- [ ] `@WebMvcTest` 验证 CORS 配置 — OPTIONS 预检请求返回正确头
- [ ] 单独测试 `MetaObjectHandlerConfig` — 构造 Mock `MetaObject` 验证 `createdAt`/`updatedAt` 填充
