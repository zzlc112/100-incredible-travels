# Vibe Coding 起始 Prompt — 「100种不可思议旅行」MVP

> **角色**: 你是本项目的**主 Agent（Orchestrator）**，负责统筹整个开发流程。
> **目标**: 从零构建一个完整的、可运行的内容展示 Web App，包含后端、前端、测试、文档。
> **约束**: 全程自动化——主 Agent 派发子 Agent 执行具体模块实现，无人参与。

---

## 一、项目概述

### 1.1 我们要构建什么

**「100种不可思议旅行」** — 飞猪旅行旗下子品牌的轻量级内容展示 Web App MVP。

- **前台**: 旅行卡片列表首页（支持多维度筛选）、旅行详情页（Markdown 正文渲染）
- **后台**: 管理员登录、内容列表管理（搜索/删除）、内容新增/编辑表单
- **MVP 数据**: ≥5 条高质量示例数据

### 1.2 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.x |
| ORM | MyBatis-Plus 3.5.x |
| 数据库 | SQLite 3.x |
| 前端框架 | Vue 3 (Composition API + `<script setup>`) |
| UI 组件库 | Element Plus 2.x |
| 构建工具 | Vite 5.x |
| HTTP 客户端 | Axios 1.x |
| Markdown 渲染 | marked |
| 后端测试 | JUnit 5 + Mockito + Spring Boot Test |
| 前端测试 | Vitest + Vue Test Utils + jsdom |
| E2E 测试 | Playwright |
| Java 代码质量 | Checkstyle + SpotBugs |
| JS 代码质量 | ESLint + Prettier |

### 1.3 项目目录结构

```
100-incredible-travels/
├── backend/                         # Spring Boot 后端
│   ├── pom.xml
│   ├── src/main/java/com/travel/
│   │   ├── TravelApplication.java   # 启动类
│   │   ├── entity/                   # 实体类
│   │   │   ├── Travel.java
│   │   │   └── AdminUser.java
│   │   ├── dto/                      # 数据传输对象
│   │   │   ├── ApiResponse.java
│   │   │   ├── PageResult.java
│   │   │   ├── TravelListRequest.java
│   │   │   ├── TravelSaveRequest.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── TravelListResponse.java
│   │   │   └── TravelDetailResponse.java
│   │   ├── mapper/                   # MyBatis-Plus Mapper
│   │   │   ├── TravelMapper.java
│   │   │   └── AdminUserMapper.java
│   │   ├── service/                  # 业务逻辑
│   │   │   ├── TravelService.java    # 接口
│   │   │   └── impl/
│   │   │       └── TravelServiceImpl.java
│   │   ├── controller/               # REST 控制器
│   │   │   ├── TravelController.java
│   │   │   ├── AdminTravelController.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── auth/                     # 认证模块
│   │   │   ├── AuthConfig.java
│   │   │   ├── AuthService.java
│   │   │   ├── AuthFilter.java
│   │   │   └── LoginController.java
│   │   └── config/                   # 配置模块
│   │       ├── MybatisPlusConfig.java
│   │       ├── MetaObjectHandlerConfig.java
│   │       ├── WebMvcConfig.java
│   │       └── JacksonConfig.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── db/
│   │   │   ├── schema.sql
│   │   │   └── data.sql
│   │   └── mapper/
│   │       ├── TravelMapper.xml
│   │       └── AdminUserMapper.xml
│   └── src/test/java/com/travel/
│       ├── entity/
│       ├── dto/
│       ├── mapper/
│       ├── service/
│       ├── controller/
│       └── auth/
│
├── frontend/                        # Vue 3 前端
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   ├── src/
│   │   ├── App.vue
│   │   ├── main.js
│   │   ├── api/
│   │   │   ├── request.js
│   │   │   ├── travel.js
│   │   │   ├── admin-travel.js
│   │   │   ├── auth.js
│   │   │   └── index.js
│   │   ├── router/
│   │   │   └── index.js
│   │   ├── components/
│   │   │   ├── TravelCard.vue
│   │   │   ├── FilterBar.vue
│   │   │   ├── ImagePreview.vue
│   │   │   ├── MarkdownRenderer.vue
│   │   │   ├── AppLayout.vue
│   │   │   └── AdminLayout.vue
│   │   ├── views/
│   │   │   ├── Home.vue
│   │   │   ├── TravelDetail.vue
│   │   │   └── admin/
│   │   │       ├── AdminLogin.vue
│   │   │       ├── AdminList.vue
│   │   │       └── AdminEdit.vue
│   │   └── styles/
│   │       └── global.css
│   └── tests/
│       ├── api/
│       ├── router/
│       ├── components/
│       └── views/
│
├── e2e/                             # E2E 测试
│   ├── package.json
│   ├── playwright.config.js
│   └── tests/
│       ├── browse-and-filter.spec.js
│       ├── view-detail.spec.js
│       └── admin-create.spec.js
│
├── docs/                            # 文档
│   ├── prompt.md                    # 本文档
│   ├── PRD.md
│   ├── ER图.md
│   └── API文档.md
│
└── README.md
```

---

## 二、主 Agent 工作流程

### 2.1 你的职责

作为主 Agent，你的职责是：
1. **阶段规划**: 按照依赖关系，将模块按 Phase 1→2→3→4 顺序排期
2. **派发子 Agent**: 为每个模块创建独立的子 Agent，附带完整的模块规格说明
3. **质量把关**: 每个模块完成后，验证其测试通过 + 代码质量检查通过
4. **进度跟踪**: 维护 `doc/tasks/progress.md`，实时更新各模块状态
5. **集成联调**: 所有模块完成后，进行前后端联调 + E2E 测试
6. **文档生成**: 自动生成 README、API 文档等交付物

### 2.2 子 Agent 派发规范

每条子 Agent 任务必须包含以下信息：
- **模块名称**: 唯一标识
- **前置依赖**: 哪些模块必须先完成
- **目标文件清单**: 需要创建/修改的文件完整列表
- **实现规格**: 具体的类/方法签名、字段、逻辑要求
- **测试要求**: 测试框架、测试用例清单
- **质量检查**: 必须通过的 lint/checkstyle 命令
- **完成标准**: 可验证的验收条件

### 2.3 实施阶段总览

```
Phase 1: 后端基础（自底向上）
  travel-config → travel-entity → travel-dto → travel-mapper
  → travel-service → travel-controller → admin-auth

Phase 2: 前端基础（自底向上）
  api-client → router → CommonComponents → TravelCard → FilterBar

Phase 3: 页面组装
  Home → TravelDetail → AdminLogin → AdminList → AdminEdit

Phase 4: 集成与交付
  前后端联调 → E2E 测试 → README + 文档
```

### 2.4 质量门禁（每个模块必须通过）

**后端模块**:
```bash
# 单元测试
cd backend && mvn test

# Checkstyle（代码风格）
mvn checkstyle:check

# SpotBugs（静态分析）
mvn spotbugs:check
```

**前端模块**:
```bash
# 单元测试
cd frontend && npx vitest run

# ESLint
npx eslint src/ --ext .js,.vue

# Prettier
npx prettier --check src/
```

**E2E 测试**:
```bash
cd e2e && npx playwright test
```

---

## 三、模块规格详细说明

> **使用说明**: 以下每个模块为一个独立的子 Agent 任务。派发时，将模块规格完整传递给子 Agent。

---

### Phase 1: 后端基础模块

---

#### 模块 1.1: travel-config（配置模块）

**前置依赖**: 无

**目标文件**:
- `backend/pom.xml`
- `backend/src/main/java/com/travel/TravelApplication.java`
- `backend/src/main/java/com/travel/config/MybatisPlusConfig.java`
- `backend/src/main/java/com/travel/config/MetaObjectHandlerConfig.java`
- `backend/src/main/java/com/travel/config/WebMvcConfig.java`
- `backend/src/main/java/com/travel/config/JacksonConfig.java`
- `backend/src/main/resources/application.yml`

**实现规格**:

1. **pom.xml** — Maven 项目配置:
   - Spring Boot 3.x parent
   - 依赖: `spring-boot-starter-web`, `mybatis-plus-spring-boot3-starter` (3.5.x), `sqlite-jdbc`, `lombok`, `spring-boot-starter-validation`
   - 测试依赖: `spring-boot-starter-test`, `h2` (测试用内存数据库)
   - 插件: `maven-surefire-plugin`, `checkstyle`, `spotbugs`

2. **TravelApplication.java** — Spring Boot 启动类:
   ```java
   @SpringBootApplication
   @EnableConfigurationProperties
   @ServletComponentScan  // 用于扫描 @WebFilter
   @MapperScan("com.travel.mapper")
   public class TravelApplication {
       public static void main(String[] args) {
           SpringApplication.run(TravelApplication.class, args);
       }
   }
   ```

3. **MybatisPlusConfig.java** — MyBatis-Plus 配置:
   - 注册 `MybatisPlusInterceptor` Bean
   - 添加 `PaginationInnerInterceptor(DbType.SQLITE)`

4. **MetaObjectHandlerConfig.java** — 自动填充时间戳:
   - 实现 `MetaObjectHandler`
   - `insertFill`: 填充 `createdAt` + `updatedAt` = `LocalDateTime.now()`
   - `updateFill`: 填充 `updatedAt` = `LocalDateTime.now()`

5. **WebMvcConfig.java** — CORS 配置:
   - 实现 `WebMvcConfigurer`
   - `/api/**` 路径允许跨域: `allowedOriginPatterns("*")`, methods: GET/POST/PUT/DELETE/OPTIONS, `allowCredentials(true)`

6. **JacksonConfig.java** — JSON 序列化配置:
   - 日期格式 `yyyy-MM-dd HH:mm:ss`
   - 时区 `Asia/Shanghai`

7. **application.yml**:
   ```yaml
   spring:
     datasource:
       url: jdbc:sqlite:travel.db
       driver-class-name: org.sqlite.JDBC
     sql:
       init:
         mode: always
         schema-locations: classpath:db/schema.sql
         data-locations: classpath:db/data.sql
   mybatis-plus:
     mapper-locations: classpath:mapper/*.xml
     configuration:
       map-underscore-to-camel-case: true
   admin:
     username: admin
     password: admin123
   ```

**测试要求**:
- `@SpringBootTest` 验证应用上下文加载成功
- 验证 `MybatisPlusInterceptor` Bean 存在且包含 `PaginationInnerInterceptor`
- 验证 `MetaObjectHandlerConfig` 的 `insertFill` / `updateFill` 正确填充

**质量检查**: `mvn test` 通过

---

#### 模块 1.2: travel-entity（数据实体模块）

**前置依赖**: travel-config

**目标文件**:
- `backend/src/main/resources/db/schema.sql`
- `backend/src/main/resources/db/data.sql`
- `backend/src/main/java/com/travel/entity/Travel.java`
- `backend/src/main/java/com/travel/entity/AdminUser.java`
- `backend/src/test/java/com/travel/entity/TravelEntityTest.java`

**实现规格**:

1. **schema.sql** — 建表 DDL:
   ```sql
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

   CREATE TABLE IF NOT EXISTS admin_user (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       username VARCHAR(50) NOT NULL UNIQUE,
       password VARCHAR(200) NOT NULL
   );
   ```

2. **data.sql** — ≥5 条示例数据:
   - 冰岛火山内部探险 (extreme+hidden, cinematic, rarity=3)
   - 日本寺庙禅修七日 (culture, minimal, rarity=2)
   - 西伯利亚驯鹿牧民冬季迁徙 (hidden, bw, rarity=4)
   - 意大利阿尔巴白松露狩猎 (taste, vintage, rarity=2)
   - 新加坡城市天际线屋顶农场 (urban, natural, rarity=1)
   - 注意: `experience_types` 字段用 JSON 数组格式 `["extreme","hidden"]`
   - 注意: `detail_images` 和 `highlights` 也用 JSON 数组格式
   - 预置管理员: username=admin, password=admin123（明文即可，MVP 阶段不加密）

3. **Travel.java** — 核心实体类:
   - `@TableName("travel")`
   - 13 个字段（见 schema.sql 对应列）
   - `id`: `@TableId(type = IdType.AUTO)`
   - `createdAt`: `@TableField(fill = FieldFill.INSERT)`
   - `updatedAt`: `@TableField(fill = FieldFill.INSERT_UPDATE)`
   - `detailImages` / `experienceTypes` / `highlights` 类型为 `String`（存 JSON 文本）
   - 使用 Lombok `@Data`（如果引入）

4. **AdminUser.java** — 管理员实体:
   - `@TableName("admin_user")`
   - 字段: id(Long) + username(String) + password(String)

**测试要求**:
- 反射验证 `@TableName("travel")` 注解存在
- 反射验证 `@TableId` 注解存在且 type=IdType.AUTO
- 启动 Spring Boot → 验证 data.sql 执行 → 查询 ≥5 条数据

**质量检查**: `mvn test` 通过

---

#### 模块 1.3: travel-dto（数据传输对象模块）

**前置依赖**: travel-entity

**目标文件** (全部创建在 `backend/src/main/java/com/travel/dto/`):
- `ApiResponse.java`
- `PageResult.java`
- `TravelListRequest.java`
- `TravelSaveRequest.java`
- `LoginRequest.java`
- `TravelListResponse.java`
- `TravelDetailResponse.java`
- `backend/src/test/java/com/travel/dto/DtoValidationTest.java`

**实现规格**:

1. **ApiResponse\<T\>** — 统一响应包装:
   ```java
   public class ApiResponse<T> {
       private Integer code;      // 200/400/401/404/500
       private T data;
       private String message;

       public static <T> ApiResponse<T> success(T data) {
           return new ApiResponse<>(200, data, "success");
       }
       public static <T> ApiResponse<T> error(Integer code, String message) {
           return new ApiResponse<>(code, null, message);
       }
   }
   ```

2. **PageResult\<T\>** — 分页响应:
   ```java
   public class PageResult<T> {
       private Long total;
       private Integer page;
       private Integer size;
       private List<T> list;

       public static <T> PageResult<T> from(IPage<T> page) {
           PageResult<T> r = new PageResult<>();
           r.total = page.getTotal();
           r.page = (int) page.getCurrent();
           r.size = (int) page.getSize();
           r.list = page.getRecords();
           return r;
       }
   }
   ```

3. **TravelListRequest** — 查询参数:
   - `experienceType` (String, 逗号分隔多选)
   - `visualStyle` (String)
   - `rarityLevel` (Integer, `@Min(1) @Max(4)`)
   - `page` (Integer, `@Min(1)`, 默认 1)
   - `size` (Integer, `@Min(1) @Max(100)`, 默认 20)

4. **TravelSaveRequest** — 新增/编辑请求体:
   - `title` — `@NotBlank @Size(max=200)`
   - `subtitle` — `@NotBlank @Size(max=500)`
   - `coverImage` — `@NotBlank`
   - `detailImages` — `List<String>`, `@Size(max=10)`
   - `experienceTypes` — `List<String>`, `@NotEmpty`
   - `visualStyle` — `@NotBlank`
   - `rarityLevel` — `@NotNull @Min(1) @Max(4)`
   - `destination` — `@NotBlank @Size(max=200)`
   - `content` — `@NotBlank`
   - `highlights` — `List<String>`, `@Size(max=5)`

5. **LoginRequest** — 登录请求:
   - `username` — `@NotBlank`
   - `password` — `@NotBlank`

6. **TravelListResponse** (10 个字段) — 列表项响应:
   - id, title, subtitle, coverImage, experienceTypes(List\<String\>), visualStyle, rarityLevel, destination, highlights(List\<String\>), createdAt
   - **不包含** content / detailImages / updatedAt（减少列表传输量）

7. **TravelDetailResponse** (13 个字段) — 详情响应:
   - 全部字段，含 content / detailImages / createdAt / updatedAt
   - JSON 数组字段用 `List<String>`

**测试要求**:
- 使用 `ValidatorFactory` 验证 `TravelSaveRequest` 必填字段校验
- 验证 `ApiResponse.success()` / `ApiResponse.error()` 工厂方法
- 验证 `PageResult.from()` 正确转换 Mock `IPage`
- 验证 Jackson 序列化 — 字段名 camelCase

**质量检查**: `mvn test` 通过

---

#### 模块 1.4: travel-mapper（数据访问模块）

**前置依赖**: travel-entity + travel-config

**目标文件**:
- `backend/src/main/java/com/travel/mapper/TravelMapper.java`
- `backend/src/main/java/com/travel/mapper/AdminUserMapper.java`
- `backend/src/main/resources/mapper/TravelMapper.xml`
- `backend/src/main/resources/mapper/AdminUserMapper.xml`
- `backend/src/test/java/com/travel/mapper/TravelMapperTest.java`

**实现规格**:

1. **TravelMapper.java**:
   ```java
   @Mapper
   public interface TravelMapper extends BaseMapper<Travel> {
       IPage<Travel> selectPageWithFilter(
           Page<Travel> page,
           @Param("experienceType") String experienceType,
           @Param("visualStyle") String visualStyle,
           @Param("rarityLevel") Integer rarityLevel
       );
   }
   ```

2. **AdminUserMapper.java**:
   ```java
   @Mapper
   public interface AdminUserMapper extends BaseMapper<AdminUser> {
       AdminUser selectByUsername(@Param("username") String username);
   }
   ```

3. **TravelMapper.xml** — 筛选查询 SQL:
   ```xml
   <select id="selectPageWithFilter" resultType="com.travel.entity.Travel">
       SELECT * FROM travel
       <where>
           <if test="experienceType != null and experienceType != ''">
               AND (
               <foreach collection="experienceType.split(',')" item="type" separator=" OR ">
                   experience_types LIKE CONCAT('%', #{type}, '%')
               </foreach>
               )
           </if>
           <if test="visualStyle != null and visualStyle != ''">
               AND visual_style = #{visualStyle}
           </if>
           <if test="rarityLevel != null">
               AND rarity_level &gt;= #{rarityLevel}
           </if>
       </where>
       ORDER BY created_at DESC
   </select>
   ```
   - SQLite 不支持 `CONCAT` 时改用 `||`: `experience_types LIKE '%' || #{type} || '%'`

4. **AdminUserMapper.xml**:
   ```xml
   <select id="selectByUsername" resultType="com.travel.entity.AdminUser">
       SELECT * FROM admin_user WHERE username = #{username}
   </select>
   ```

**测试要求**（`@MybatisPlusTest` + H2/SQLite 内存数据库）:
- 基础 CRUD: `selectById`, `insert`, `updateById`, `deleteById`
- 筛选查询: 单独每种筛选条件 + 组合条件
- 分页: page=1/size=3，验证 total + 返回条数
- `selectByUsername`: 存在/不存在用户

**质量检查**: `mvn test -pl backend` 通过

---

#### 模块 1.5: travel-service（业务逻辑模块）

**前置依赖**: travel-mapper + travel-dto

**目标文件**:
- `backend/src/main/java/com/travel/service/TravelService.java`
- `backend/src/main/java/com/travel/service/impl/TravelServiceImpl.java`
- `backend/src/main/java/com/travel/exception/NotFoundException.java`
- `backend/src/test/java/com/travel/service/TravelServiceTest.java`

**实现规格**:

1. **NotFoundException.java**:
   ```java
   public class NotFoundException extends RuntimeException {
       public NotFoundException(String message) {
           super(message);
       }
   }
   ```

2. **TravelService.java** 接口:
   ```java
   public interface TravelService {
       PageResult<TravelListResponse> listTravels(TravelListRequest req);
       TravelDetailResponse getTravelDetail(Long id);
       TravelDetailResponse createTravel(TravelSaveRequest req);
       TravelDetailResponse updateTravel(Long id, TravelSaveRequest req);
       void deleteTravel(Long id);
   }
   ```

3. **TravelServiceImpl.java** — 核心实现要点:
   - **JSON 工具方法**（private）:
     - `parseJsonArray(String json) → List<String>`: 用 Jackson `ObjectMapper` 解析，null/空 → 空 List
     - `toJsonArray(List<String> list) → String`: 用 Jackson 序列化，空 → null
   - **Entity ↔ DTO 转换**（private）:
     - `toListResponse(Travel) → TravelListResponse`: 不含 content/detailImages
     - `toDetailResponse(Travel) → TravelDetailResponse`: 全字段，JSON 字符串 → List
     - `toEntity(TravelSaveRequest) → Travel`: 新建用，List → JSON 字符串
     - `updateEntity(Travel, TravelSaveRequest)`: 逐字段覆盖（保留 id）
   - **listTravels**: 创建 Page 对象 → mapper.selectPageWithFilter → PageResult.from
   - **getTravelDetail**: selectById → null→抛 NotFoundException → toDetailResponse
   - **createTravel**: toEntity → insert → 拿生成的 id → 重新 selectById → toDetailResponse
   - **updateTravel**: selectById → null 抛异常 → updateEntity → updateById → 重新查询 → toDetailResponse
   - **deleteTravel**: selectById → null 抛异常 → deleteById

**测试要求**（Mock TravelMapper，纯单元测试，不启动 Spring 容器）:
- `listTravels`: 正常返回分页数据 / 空列表
- `getTravelDetail`: 存在→返回 / 不存在→抛 NotFoundException
- `createTravel`: 验证 insert 调用 → 返回含 id 的响应
- `updateTravel`: 验证字段覆盖 / id 不变
- `deleteTravel`: 存在→成功删除 / 不存在→抛异常
- `parseJsonArray`: null→[], `"[\"a\"]"`→["a"], `""`→[]
- `toJsonArray`: null→null, ["a"]→`"[\"a\"]"`, []→null
- `toListResponse` / `toDetailResponse`: 逐字段验证映射完整性

**覆盖率目标**: ≥ 80%

**质量检查**: `mvn test` 通过

---

#### 模块 1.6: travel-controller（API 控制器模块）

**前置依赖**: travel-service + travel-dto

**目标文件**:
- `backend/src/main/java/com/travel/controller/TravelController.java`
- `backend/src/main/java/com/travel/controller/AdminTravelController.java`
- `backend/src/main/java/com/travel/controller/GlobalExceptionHandler.java`
- `backend/src/test/java/com/travel/controller/TravelControllerTest.java`
- `backend/src/test/java/com/travel/controller/AdminTravelControllerTest.java`

**实现规格**:

1. **TravelController.java** — 前台接口 `/api`:
   - `GET /api/travels` → `listTravels(@Valid TravelListRequest params)` → `ApiResponse<PageResult<TravelListResponse>>`
   - `GET /api/travels/{id}` → `getTravelDetail(@PathVariable Long id)` → `ApiResponse<TravelDetailResponse>`

2. **AdminTravelController.java** — 后台接口 `/api/admin`:
   - `POST /api/admin/travels` → `createTravel(@Valid @RequestBody TravelSaveRequest body)`
   - `PUT /api/admin/travels/{id}` → `updateTravel(@PathVariable Long id, @Valid @RequestBody TravelSaveRequest body)`
   - `DELETE /api/admin/travels/{id}` → `deleteTravel(@PathVariable Long id)`

3. **GlobalExceptionHandler.java** — `@RestControllerAdvice`:
   - `NotFoundException` → 404: `ApiResponse.error(404, msg)`
   - `MethodArgumentNotValidException` → 400: `ApiResponse.error(400, "字段: 错误信息; ...")`
   - `Exception` → 500: `ApiResponse.error(500, "服务器内部错误")`

**测试要求**（`@WebMvcTest` + MockMvc + `@MockBean` TravelService）:
- **前台**:
  - GET `/api/travels` → 200 + 分页数据
  - GET `/api/travels?experienceType=extreme&rarityLevel=3` → 参数绑定正确
  - GET `/api/travels/{id}` → 200
  - GET `/api/travels/999` → 404
- **后台**:
  - POST `/api/admin/travels` 缺必填字段 → 400 + 字段级错误信息
  - POST `/api/admin/travels` 正常 → 200
  - PUT `/api/admin/travels/999` → 404
  - DELETE `/api/admin/travels/999` → 404

**质量检查**: `mvn test` 通过

---

#### 模块 1.7: admin-auth（认证模块）

**前置依赖**: travel-mapper + travel-config

**目标文件**:
- `backend/src/main/java/com/travel/auth/AuthConfig.java`
- `backend/src/main/java/com/travel/auth/AuthService.java`
- `backend/src/main/java/com/travel/auth/AuthFilter.java`
- `backend/src/main/java/com/travel/auth/LoginController.java`
- `backend/src/test/java/com/travel/auth/AuthServiceTest.java`
- `backend/src/test/java/com/travel/auth/AuthFilterTest.java`
- `backend/src/test/java/com/travel/auth/LoginControllerTest.java`

**实现规格**:

1. **AuthConfig.java**:
   ```java
   @ConfigurationProperties(prefix = "admin")
   public class AuthConfig {
       private String username = "admin";
       private String password = "admin123";
   }
   ```

2. **AuthService.java**:
   - `boolean login(LoginRequest req, HttpSession session)`
   - 匹配 AuthConfig 中的 username/password
   - 成功 → `session.setAttribute("admin", username)` → true
   - 失败 → false（不抛异常）

3. **AuthFilter.java** — 实现 `Filter`:
   - `@WebFilter(urlPatterns = "/api/admin/*")`
   - 放行: OPTIONS 请求、`/api/admin/login`
   - 鉴权: `session.getAttribute("admin") != null`
   - 未登录: `response.setStatus(401)`, 写 JSON `{"code":401,"data":null,"message":"未登录"}`

4. **LoginController.java** — `@RestController @RequestMapping("/api/admin")`:
   - `POST /api/admin/login` — 成功 200 / 失败 401
   - `POST /api/admin/logout` — `session.invalidate()` → 200
   - `GET /api/admin/check` — 已登录 200 / 未登录 401

**测试要求**:
- **AuthService 单元测试**: 正确密码通过 / 错误密码拒绝
- **AuthFilter 单元测试**: Mock Request/Response/Session
  - 无 session → 401
  - 有 session 无 admin → 401
  - 有 session + admin → 放行
  - OPTIONS → 放行
  - `/api/admin/login` → 放行
- **LoginController 集成测试**: `@WebMvcTest` + Mock Session
  - 正确登录 → 200 / 错误 → 401 / 登出 → 200 / 检查 → 200/401

**质量检查**: `mvn test` 通过

---

### Phase 2: 前端基础模块

---

#### 模块 2.1: api-client（HTTP 客户端 + 项目骨架）

**前置依赖**: 无（可并行启动）

**目标文件**:
- `frontend/package.json`
- `frontend/vite.config.js`
- `frontend/index.html`
- `frontend/src/main.js`
- `frontend/src/App.vue`
- `frontend/src/api/request.js`
- `frontend/src/api/travel.js`
- `frontend/src/api/admin-travel.js`
- `frontend/src/api/auth.js`
- `frontend/src/api/index.js`

**实现规格**:

1. **创建 Vue 3 项目**: `npm create vite@latest frontend -- --template vue`
   - 安装: `vue-router`, `axios`, `element-plus`, `marked`, `github-markdown-css`
   - 安装测试: `vitest`, `@vue/test-utils`, `jsdom`, `eslint`, `prettier`

2. **vite.config.js** — 配置 `@` 别名 + dev server proxy:
   ```javascript
   resolve: { alias: { '@': '/src' } }
   server: { proxy: { '/api': 'http://localhost:8080' } }
   ```

3. **request.js** — Axios 实例:
   - `baseURL: '/api'`, `timeout: 10000`, `withCredentials: true`
   - 响应拦截器: `code == 200` → 返回 `response.data.data`；否则 `Promise.reject`
   - 网络错误 → `Promise.reject({code: 500, message: '网络错误'})`

4. **travel.js** — 前台 API:
   - `fetchTravels(params)` — `GET /api/travels` → `{total, page, size, list}`
   - `fetchTravelDetail(id)` — `GET /api/travels/{id}` → `TravelDetailResponse`

5. **admin-travel.js** — 后台 API:
   - `createTravel(data)` — `POST /api/admin/travels`
   - `updateTravel(id, data)` — `PUT /api/admin/travels/{id}`
   - `deleteTravel(id)` — `DELETE /api/admin/travels/{id}`

6. **auth.js** — 认证 API:
   - `login(username, password)` — `POST /api/admin/login`
   - `logout()` — `POST /api/admin/logout`
   - `checkAuth()` — `GET /api/admin/check`

**测试要求**（Mock Axios）:
- `fetchTravels` 请求参数正确拼接到 URL query
- 响应数据正确解包（`code==200` → 返回 `data` 字段）
- 业务错误返回 Promise.reject
- `checkAuth` 401 错误处理

**质量检查**: `npx vitest run` 通过 + `npx eslint src/api/` 通过

---

#### 模块 2.2: router（路由模块）

**前置依赖**: api-client（需要 `checkAuth` 函数）

**目标文件**:
- `frontend/src/router/index.js`
- `frontend/tests/router/router.test.js`

**实现规格**:

1. **路由表**:
   | 路径 | 组件 | meta |
   |------|------|------|
   | `/` | `Home.vue` | `{}` |
   | `/travel/:id` | `TravelDetail.vue` | `{}` |
   | `/admin/login` | `AdminLogin.vue` | `{guest: true}` |
   | `/admin/travels` | `AdminList.vue` | `{auth: true}` |
   | `/admin/travels/new` | `AdminEdit.vue` | `{auth: true}` |
   | `/admin/travels/:id/edit` | `AdminEdit.vue` | `{auth: true}` |

2. **全局前置守卫** `beforeEach`:
   - `to.meta.auth`: 调 `checkAuth()`，失败 → `next('/admin/login')`
   - `to.meta.guest`: 调 `checkAuth()`，成功 → `next('/admin/travels')`
   - 其他 → `next()`
   - PS: `admin/login` 不经过 AuthFilter

3. **404 处理**: 通配路由 `/:pathMatch(.*)*` → 重定向 `/`

**测试要求**（Mock `checkAuth`）:
- auth 页面未登录 → 重定向 `/admin/login`
- auth 页面已登录 → 正常进入
- guest 页面已登录 → 重定向 `/admin/travels`
- guest 页面未登录 → 正常进入
- 不存在路径 → 重定向 `/`

**质量检查**: `npx vitest run` 通过

---

#### 模块 2.3: CommonComponents（公共组件模块）

**前置依赖**: 无（纯 UI 组件）

**目标文件**:
- `frontend/src/components/ImagePreview.vue`
- `frontend/src/components/MarkdownRenderer.vue`
- `frontend/src/components/AppLayout.vue`
- `frontend/src/components/AdminLayout.vue`
- `frontend/src/styles/global.css`
- `frontend/tests/components/ImagePreview.test.js`
- `frontend/tests/components/MarkdownRenderer.test.js`
- `frontend/tests/components/AppLayout.test.js`
- `frontend/tests/components/AdminLayout.test.js`

**实现规格**:

1. **ImagePreview.vue** — 图片预览:
   - Props: `url`(String, required), `width`(String, '100%'), `height`(String, 'auto'), `fit`(String, 'cover')
   - State: `error` (加载失败 → true)
   - 模板: `<img :src="url" @error="error = true">` / 失败 → 占位符 + el-icon(PictureFilled) + "图片加载失败"

2. **MarkdownRenderer.vue** — Markdown 渲染:
   - Props: `content`(String, required)
   - Computed: `renderedHtml` → `marked.parse(content || '')`
   - 模板: `<div class="markdown-body" v-html="renderedHtml">`
   - marked 配置: 链接 `target="_blank"`, `rel="noopener"`
   - 引入 `github-markdown-css`

3. **AppLayout.vue** — 前台布局:
   - Slots: `default`
   - 模板: `<header>` (Logo + 导航) + `<main>` (slot)
   - 样式: header 固定顶部, main 居中 max-width: 1200px

4. **AdminLayout.vue** — 后台布局:
   - Slots: `default`
   - 模板: `<header>` ("后台管理" + 退出登录按钮) + `<main>` (slot)
   - `handleLogout()`: 调 `logout()` API → `router.push('/admin/login')`

5. **global.css** — 全局样式:
   - CSS 变量: `--color-primary: #1a1a2e`, `--color-accent: #e94560`, `--color-bg: #fafafa`
   - 响应式断点: mobile < 768px, tablet 768-1023px, desktop ≥ 1024px
   - 基础排版: body/h1-h6/p/a

**测试要求**:
- ImagePreview: 有效 URL → 渲染 img / 无效 URL → 显示占位
- MarkdownRenderer: Markdown → HTML 正确转换，链接 target 正确
- AppLayout: slot 内容正确渲染
- AdminLayout: 退出按钮 → 调用 logout → 跳转

**质量检查**: `npx vitest run` 通过

---

#### 模块 2.4: TravelCard（旅行卡片组件）

**前置依赖**: 无（纯展示组件）

**目标文件**:
- `frontend/src/components/TravelCard.vue`
- `frontend/tests/components/TravelCard.test.js`

**实现规格**:

1. **本地常量映射表**（组件内定义）:
   ```javascript
   const EXPERIENCE_TYPE_MAP = {
     extreme: { name: '极限探险', icon: '🧗' },
     culture: { name: '文化沉浸', icon: '🎭' },
     hidden: { name: '秘境探索', icon: '🗺️' },
     eco: { name: '生态旅行', icon: '🌿' },
     urban: { name: '城市隐世', icon: '🏙️' },
     taste: { name: '味觉之旅', icon: '🍜' }
   }

   const VISUAL_STYLE_MAP = {
     minimal: '极简', cinematic: '电影感', vintage: '复古胶片',
     vivid: '高饱和冲击', bw: '黑白纪实', natural: '自然光'
   }
   ```

2. **组件契约**:
   - Props: `travel` (Object, required) — shape: `{id, title, subtitle, coverImage, experienceTypes[], visualStyle, rarityLevel, destination}`
   - Emits: `click` — payload 为 `travel.id`

3. **模板**:
   - 封面图 (`el-image`, 带 `@error` → 占位图)
   - 标题 + 副标题
   - 体验类型标签 (`el-tag` × N, 每种类型不同颜色)
   - 视觉风格标签
   - 小众程度 ⭐ (rarityLevel=3 → ⭐⭐⭐)
   - 目的地文字

4. **内部状态**:
   - `imageError` — ref(false), 图片加载失败设 true
   - `experienceLabels` — computed, 编码 → `{name, icon}`
   - `rarityStars` — computed, 1-4 → ⭐ 字符串
   - `visualStyleLabel` — computed, 编码 → 中文名

**测试要求**:
- 正确渲染标题、副标题、目的地
- rarityLevel=3 → 显示 ⭐⭐⭐
- 体验类型标签正确映射
- 图片加载失败 → 显示占位符
- 点击触发 `@click` + 正确 id
- 不依赖 router / API: 纯展示组件验证

**质量检查**: `npx vitest run` 通过

---

#### 模块 2.5: FilterBar（筛选栏组件）

**前置依赖**: 无（纯 UI 组件）

**目标文件**:
- `frontend/src/components/FilterBar.vue`
- `frontend/tests/components/FilterBar.test.js`

**实现规格**:

1. **组件契约**:
   - Props:
     - `modelValue` (Object, required) — `{experienceTypes: [], visualStyle: '', rarityLevel: 0}`
     - `availableExperienceTypes` (Array) — `[{code, name, icon}]`
     - `availableVisualStyles` (Array) — `[{code, name}]`
   - Emits: `update:modelValue`, `reset`

2. **v-model 双向绑定**: computed getter/setter

3. **筛选控件**:
   - 体验类型: `el-checkbox-button` 多选, 点击切换
   - 视觉风格: `el-radio-group` + `el-radio-button` 单选, 可取消
   - 小众程度: `el-slider` (1-4, step=1) 显示 ⭐ 标记
   - 重置按钮: `el-button` → `emit('reset')`

4. **可用选项**（组件内定义或 props 传入）:
   - experienceTypes: 6 个（extreme/culture/hidden/eco/urban/taste）
   - visualStyles: 6 个（minimal/cinematic/vintage/vivid/bw/natural）

5. **样式**: 水平排列, 响应式换行, 选中状态高亮

**测试要求**:
- 点击体验类型 → emit 新 modelValue（选中/取消逻辑）
- 选择视觉风格 → emit 新 modelValue
- 拖拽滑块 → emit 新 modelValue
- 点击重置 → emit `reset`
- 多条件组合 → modelValue 正确更新

**质量检查**: `npx vitest run` 通过

---

### Phase 3: 页面组装模块

---

#### 模块 3.1: Home（首页）

**前置依赖**: TravelCard + FilterBar + api-client + router

**目标文件**:
- `frontend/src/views/Home.vue`
- `frontend/tests/views/Home.test.js`

**实现规格**:

1. **响应式状态**:
   - `filter`: `{experienceTypes: [], visualStyle: '', rarityLevel: 0}`
   - `travels`: `[]`
   - `total`: `0`
   - `currentPage`: `1`
   - `pageSize`: `12`
   - `loading`: `false`

2. **方法**:
   - `loadTravels()`: 调 `fetchTravels({...提取的filter参数, page, size})`
   - `handleFilterChange(val)`: 更新 filter → currentPage=1 → loadTravels
   - `handleReset()`: filter 重置 → loadTravels
   - `handlePageChange(page)`: 更新 currentPage → loadTravels
   - `handleCardClick(id)`: `router.push('/travel/' + id)`

3. **生命周期**:
   - `onMounted` → `loadTravels()`
   - `watch(filter, {deep: true})` → `loadTravels()` (建议加 debounce)

4. **模板**:
   - `<FilterBar v-model="filter" @reset="handleReset" />`
   - 加载中: `v-loading` 指令
   - 卡片网格: `el-row` + `el-col` (响应式: lg=6, md=8, sm=12, xs=24)
     - `v-for` 渲染 `TravelCard` + `@click="handleCardClick"`
   - 空数据: `el-empty description="暂无内容"`
   - 分页: `el-pagination` (layout: prev/pager/next/total)

**过滤器参数映射** (发送 API 时):
```javascript
const params = {
  page: currentPage.value,
  size: pageSize.value
}
if (filter.value.experienceTypes.length > 0) {
  params.experienceType = filter.value.experienceTypes.join(',')
}
if (filter.value.visualStyle) {
  params.visualStyle = filter.value.visualStyle
}
if (filter.value.rarityLevel > 0) {
  params.rarityLevel = filter.value.rarityLevel
}
```

**测试要求**:
- 首次加载显示 loading → 数据加载后渲染 N 张卡片
- 筛选变化 → API 参数映射正确
- 翻页 → loadTravels + page 参数
- 卡片点击 → router.push 正确
- 空数据 → el-empty 显示

**质量检查**: `npx vitest run` 通过

---

#### 模块 3.2: TravelDetail（详情页）

**前置依赖**: api-client + router + MarkdownRenderer

**目标文件**:
- `frontend/src/views/TravelDetail.vue`
- `frontend/tests/views/TravelDetail.test.js`

**实现规格**:

1. **响应式状态**:
   - `travel`: `null | TravelDetailResponse`
   - `loading`: `false`
   - `imageError`: `false`

2. **Computed**: `renderedContent` → `marked.parse(travel?.content || '')`

3. **方法**:
   - `loadDetail(id)`: 调 `fetchTravelDetail(id)` → 成功更新 travel / 失败显示 404
   - `goBack()`: `router.back()` 或 `router.push('/')`
   - `handleCoverError()`: `imageError = true`

4. **生命周期**:
   - `onMounted` → `loadDetail(route.params.id)`
   - `watch(() => route.params.id)` → `loadDetail(newId)`

5. **模板布局**:
   - 返回按钮 (`el-page-header` @back)
   - 封面大图 (`el-image` + 占位符)
   - 标题(h1) + 目的地 + ⭐ 小众程度 + 副标题
   - 体验类型标签 + 视觉风格标签
   - 特色亮点 `el-tag` × N
   - 详情图片列表
   - 正文 `<div class="markdown-body" v-html="renderedContent">`
   - 页脚: createdAt / updatedAt

**测试要求**:
- 加载中显示 loading
- 各字段正确渲染
- Markdown → HTML 正确
- 封面加载失败 → 占位符
- 返回按钮 → router.back
- API 404 → 显示 "内容不存在"
- 同页面切换到另一个 id → watch 触发重载

**质量检查**: `npx vitest run` 通过

---

#### 模块 3.3: AdminLogin（后台登录）

**前置依赖**: api-client(auth) + router

**目标文件**:
- `frontend/src/views/admin/AdminLogin.vue`
- `frontend/tests/views/admin/AdminLogin.test.js`

**实现规格**:

1. **状态**:
   - `form`: `{username: '', password: ''}`
   - `loading`: `false`
   - `errorMessage`: `''`

2. **表单验证规则**:
   - username: `{required: true, message: '请输入用户名', trigger: 'blur'}`
   - password: `{required: true, message: '请输入密码', trigger: 'blur'}`

3. **handleLogin**:
   - `el-form.validate` → `login(form.username, form.password)`
   - 成功 → `router.push('/admin/travels')`
   - 失败 → `errorMessage = '用户名或密码错误'`

4. **模板**: 居中登录卡片
   - Logo/标题: "100种不可思议旅行 - 后台管理"
   - `el-form` + `el-input`(username) + `el-input`(password, show-password)
   - `el-button` (submit, loading)
   - `el-alert` (errorMessage, type=error, closable)

**测试要求**:
- 空表单提交 → 验证提示
- 成功登录 → router.push
- 失败 → errorMessage 显示

**质量检查**: `npx vitest run` 通过

---

#### 模块 3.4: AdminList（后台列表）

**前置依赖**: api-client(admin-travel) + router

**目标文件**:
- `frontend/src/views/admin/AdminList.vue`
- `frontend/tests/views/admin/AdminList.test.js`

**实现规格**:

1. **状态**:
   - `travels`: `[]`, `total`: `0`, `currentPage`: `1`, `pageSize`: `20`
   - `searchKeyword`: `''`, `loading`: `false`

2. **表格列**: ID(60px), 封面缩略图(80px, el-image), 标题(200px, 截断+tooltip), 体验类型(150px, el-tag), 小众程度(100px, ⭐), 目的地(120px), 创建时间(160px, 格式化), 操作(150px: 编辑+删除按钮)

3. **方法**:
   - `loadList()`: `fetchTravels({page, size})`
   - `handleSearch()`: 前端按标题过滤（简单搜索）
   - `handleDelete(id)`: `el-message-box.confirm` → `deleteTravel(id)` → 刷新 + `ElMessage.success`
   - `handleEdit(id)`: `router.push('/admin/travels/' + id + '/edit')`
   - `handleCreate()`: `router.push('/admin/travels/new')`
   - `handlePageChange(page)`: `currentPage = page` → `loadList()`

4. **模板**:
   - 顶部栏: 搜索框 + 新增按钮
   - `el-table` + 各列
   - `el-pagination`
   - 空数据: `el-empty`

**测试要求**:
- 列表正确渲染
- 翻页触发 loadList
- 删除确认 → deleteTravel 调用 → 刷新
- 删除取消 → 不调用
- 编辑按钮 → router.push 正确
- 新增按钮 → router.push 正确

**质量检查**: `npx vitest run` 通过

---

#### 模块 3.5: AdminEdit（后台编辑）

**前置依赖**: api-client(admin-travel) + router + ImagePreview

**目标文件**:
- `frontend/src/views/admin/AdminEdit.vue`
- `frontend/tests/views/admin/AdminEdit.test.js`

**实现规格**:

1. **状态**:
   - `form`: 10 个字段（对应 TravelSaveRequest）
   - `isEdit`: computed → `!!route.params.id`
   - `loading`: false, `submitting`: false
   - `pageTitle`: computed → isEdit ? '编辑旅行' : '新增旅行'

2. **表单控件与验证**:
   | 字段 | 控件 | 验证 |
   |------|------|------|
   | title | el-input | 必填, max 200 |
   | subtitle | el-input | 必填, max 500 |
   | coverImage | el-input + ImagePreview | 必填 |
   | detailImages | 动态列表 v-for + 增删按钮 + ImagePreview | 可选, max 10 |
   | experienceTypes | el-checkbox-group (EXPERIENCE_TYPE_MAP) | 必填 ≥1 |
   | visualStyle | el-radio-group (VISUAL_STYLE_MAP) | 必填 |
   | rarityLevel | el-rate (max=4) | 必填 1-4 |
   | destination | el-input | 必填, max 200 |
   | content | el-input(textarea, rows=12) | 必填, 提示"支持 Markdown" |
   | highlights | 动态 tag 增删 | 可选, max 5 |

3. **方法**:
   - `loadTravel(id)`: 编辑模式 → fetchTravelDetail(id) → 填充 form
   - `handleSubmit()`: 验证 → isEdit ? updateTravel : createTravel → 成功跳转
   - `handleCancel()`: router.back
   - `addDetailImage()` / `removeDetailImage(i)`
   - `addHighlight()` / `removeHighlight(i)`

4. **生命周期**: `onMounted` → if isEdit → `loadTravel(route.params.id)`

**测试要求**:
- 新增模式: 表单为空, 提交成功 → createTravel → 跳转
- 编辑模式: fetchTravelDetail 数据预填充, 提交 → updateTravel
- 空表单提交 → 验证提示
- 取消 → router.back
- 动态列表: 增删 detailImages/highlights

**质量检查**: `npx vitest run` 通过

---

### Phase 4: 集成与交付

---

#### 模块 4.1: 前后端联调 + 主 App 组装

**前置依赖**: 所有 Phase 1-3 模块

**目标**: 组装 App.vue，确保前后端联通

**App.vue 结构**:
```vue
<template>
  <AppLayout v-if="isFrontendRoute">
    <router-view />
  </AppLayout>
  <AdminLayout v-else-if="isAdminRoute && !isLoginRoute">
    <router-view />
  </AdminLayout>
  <router-view v-else />
</template>
```
- 前台路由: `/` 和 `/travel/:id` → AppLayout
- 后台路由(非登录): `/admin/travels*` → AdminLayout
- `/admin/login` → 无 Layout

**验证步骤**:
1. 启动后端: `cd backend && mvn spring-boot:run` (端口 8080)
2. 启动前端: `cd frontend && npm run dev` (端口 5173)
3. 验证: 浏览器访问 `http://localhost:5173` → 首页加载 5 条数据
4. 验证: 筛选功能正常
5. 验证: 详情页正常
6. 验证: 后台登录 → 列表 → 新增 → 编辑 → 删除完整流程

#### 模块 4.2: E2E 测试

**前置依赖**: 前后端联调通过

**目标文件**:
- `e2e/package.json`
- `e2e/playwright.config.js`
- `e2e/tests/browse-and-filter.spec.js`
- `e2e/tests/view-detail.spec.js`
- `e2e/tests/admin-create.spec.js`

**E2E 测试场景**:

1. **E2E-01: 浏览+筛选** (`browse-and-filter.spec.js`):
   - 打开首页 → 验证 ≥5 张卡片
   - 点击体验类型"极限探险" → 验证卡片过滤
   - 设置小众程度 ≥ 3 → 验证只剩 3/4 级内容
   - 验证 URL 参数同步

2. **E2E-02: 查看详情** (`view-detail.spec.js`):
   - 从首页点击第一张卡片
   - 验证详情页内容: 标题、图片、正文 Markdown 渲染
   - 点击返回 → 返回首页

3. **E2E-03: 后台新增** (`admin-create.spec.js`):
   - 访问 `/admin/login` → 输入 admin/admin123 → 登录
   - 点击"新增" → 填写表单 → 提交
   - 验证跳转回列表 → 新内容出现
   - 访问前台首页 → 验证新卡片出现

**Playwright 配置要点**:
```javascript
// playwright.config.js
module.exports = {
  testDir: './tests',
  webServer: [
    { command: 'cd ../backend && mvn spring-boot:run', port: 8080 },
    { command: 'cd ../frontend && npm run dev', port: 5173 }
  ]
}
```

#### 模块 4.3: README + 文档

**目标文件**:
- `README.md`
- `docs/API文档.md`
- `docs/ER图.md`

**README.md 必须包含**:
- 项目名称 + 一句话简介
- 技术选型说明（前端/后端/数据库 + 选型理由）
- 运行指南:
  ```bash
  # 后端
  cd backend && mvn spring-boot:run

  # 前端
  cd frontend && npm install && npm run dev

  # E2E 测试
  cd e2e && npm install && npx playwright test
  ```
- 后台账号: admin / admin123
- 项目结构说明
- API 接口一览表

**API 文档**: 8 个端点的完整说明（方法/路径/参数/响应/错误码）

**ER 图**: 使用 Mermaid 语法绘制

---

## 四、主 Agent 操作指令

### 4.1 启动项目

初始化项目目录和 Git 仓库:
```bash
mkdir 100-incredible-travels
cd 100-incredible-travels
git init
git checkout -b main
```

### 4.2 派发子 Agent 模版

每派发一个子 Agent 时，使用以下格式:

```
## 任务: [模块名称]

### 当前进度
- 已完成模块: [列表]
- 当前阶段: Phase X

### 模块规格
[从本文档第三章复制对应模块的完整规格]

### 前置模块输出
[已完成模块的关键文件内容/路径，供参考]

### 完成标准
1. 所有目标文件已创建
2. 单元测试全部通过
3. 质量检查全部通过
4. 更新 doc/tasks/progress.md 标记本模块为 ✅

请独立完成此模块，无需人工干预。完成后报告结果。
```

### 4.3 进度跟踪

每个模块完成后，更新 `doc/tasks/progress.md`:

```markdown
| 模块 | 状态 | 测试通过 | 质量检查 | 完成时间 |
|------|------|----------|----------|----------|
| travel-config | ✅ | 3/3 | Checkstyle✅ SpotBugs✅ | 2026-06-09 10:30 |
| ... | | | | |
```

### 4.4 异常处理

如果子 Agent 返回失败:
1. 分析失败原因（测试失败 / 编译错误 / 质量检查不通过）
2. 如果是规格理解问题 → 重新派发并补充说明
3. 如果是实现 bug → 派发修复子 Agent
4. 如果是依赖问题 → 先确保依赖模块正确完成
5. 连续 3 次失败 → 暂停该模块，标注 ⏸️，继续下一个可并行模块

### 4.5 并行化策略

以下模块可以并行派发（无相互依赖）:
- **Group A** (后端基础): travel-config + 同时可以准备 travel-entity 的前置工作
- **Group B** (前端基础): api-client + CommonComponents + TravelCard + FilterBar 可同时进行（router 需要 api-client 完成后进行）
- **Group C** (页面): Home + TravelDetail + AdminLogin + AdminList + AdminEdit 可部分并行（都依赖基础组件和 API 客户端）

---

## 五、全局规则

1. **每个 Java 类必须有对应的单元测试**，Service 层测试覆盖率 ≥ 80%
2. **每个 Vue 组件必须有对应的 Vitest 测试**，核心组件必须有 mount 测试
3. **所有 API 端点必须有 Controller 集成测试**（MockMvc）
4. **Maven 项目**: `mvn test` 必须全部绿色通过
5. **前端项目**: `npx vitest run` 必须全部绿色通过
6. **代码质量**: 不允许有任何 lint/checkstyle/spotbugs 警告
7. **禁止一键生成**: 必须逐模块实现，每模块独立测试通过后再进入下一个
8. **Git 提交规范**:
   - `docs/schema:` — 数据模型
   - `feat/backend:` — 后端模块
   - `feat/frontend:` — 前端模块
   - `test/unit:` — 单元测试
   - `test/e2e:` — E2E 测试
   - `docs/readme:` — 文档
9. **每个模块完成后必须 git commit**，保持清晰的提交历史

---

## 六、最终验收清单

所有以下条件满足才算项目完成:

- [ ] 后端 7 个模块全部完成且测试通过
- [ ] 前端 10 个模块全部完成且测试通过
- [ ] 前端 ESLint + Prettier 零错误
- [ ] 后端 Checkstyle + SpotBugs 零错误
- [ ] 3 条 E2E 测试全部通过
- [ ] 前后端联调: 首页/详情/后台全流程可用
- [ ] ≥5 条示例数据可正常展示
- [ ] README.md 包含完整运行指南
- [ ] API 文档生成完整
- [ ] Git 提交历史清晰（≥10 次提交）
- [ ] `doc/tasks/progress.md` 全部模块标记为 ✅

---

> **开始**: 从 Phase 1 第一个模块 `travel-config` 开始，派发第一个子 Agent。严格按照依赖顺序逐模块推进。祝构建顺利！
