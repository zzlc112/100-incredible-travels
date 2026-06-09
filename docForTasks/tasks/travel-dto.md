# travel-dto — 数据传输对象模块

> 包路径: `com.travel.dto` | 依赖: 仅 `jakarta.validation` + Jackson | 测试层级: 单元测试

## 任务列表

### 1. DTO 类创建

#### 1.1 通用响应体
- [ ] 创建 `ApiResponse<T>.java`
  - 字段：`code`(Integer) + `data`(T) + `message`(String)
  - 静态工厂方法：`success(T data)` / `error(Integer code, String message)`
- [ ] 创建 `PageResult<T>.java`
  - 字段：`total`(Long) + `page`(Integer) + `size`(Integer) + `list`(List\<T\>)
  - 静态工厂方法：`from(IPage<T> page)` — 从 MyBatis-Plus 分页结果转换

#### 1.2 请求 DTO
- [ ] 创建 `TravelListRequest.java`
  - 字段：experienceType / visualStyle / rarityLevel / page / size
  - 校验：`rarityLevel` `@Min(1) @Max(4)`，`page` `@Min(1)`，`size` `@Min(1) @Max(100)`
  - 默认值：page=1, size=20
- [ ] 创建 `TravelSaveRequest.java`
  - 10 个字段，含完整校验注解（`@NotBlank` / `@NotNull` / `@Size` / `@URL` / `@NotEmpty`）
  - `detailImages` 用 `List<String>` + `@Size(max=10)`
  - `highlights` 用 `List<String>` + `@Size(max=5)`
- [ ] 创建 `LoginRequest.java`
  - 字段：username + password，均 `@NotBlank`

#### 1.3 响应 DTO
- [ ] 创建 `TravelListResponse.java`
  - 10 个字段（不含 content / detailImages，减少传输量）
  - `experienceTypes` / `highlights` 为 `List<String>`
- [ ] 创建 `TravelDetailResponse.java`
  - 13 个字段（全部字段，含 content / detailImages / createdAt / updatedAt）
  - JSON 数组字段均为 `List<String>`

### 2. DTO 单元测试
- [ ] 测试 `TravelSaveRequest` 校验注解 — 必填字段为空时触发 `@NotBlank`
- [ ] 测试 `ApiResponse.success()` / `ApiResponse.error()` 工厂方法
- [ ] 测试 `PageResult.from()` 转换方法（mock `IPage` 对象）
- [ ] 测试 Jackson 序列化/反序列化 — 字段名 camelCase 一致
