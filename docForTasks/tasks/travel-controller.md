# travel-controller — API 控制器模块

> 包路径: `com.travel.controller` | 依赖: `travel-service`(接口) + `travel-dto` | 测试层级: `@WebMvcTest` + MockMvc

## 任务列表

### 1. 全局异常处理
- [ ] 创建 `GlobalExceptionHandler.java`（`@RestControllerAdvice`）
  - `NotFoundException` → 404 `ApiResponse.error(404, msg)`
  - `MethodArgumentNotValidException` → 400 `ApiResponse.error(400, 字段级错误拼接)`
  - `Exception`（兜底）→ 500 `ApiResponse.error(500, "服务器内部错误")`

### 2. TravelController（前台接口 `/api`）
- [ ] 创建 `TravelController.java`
  - `@RestController` + `@RequestMapping("/api")`
  - `GET /api/travels` — `listTravels(@Valid TravelListRequest params)` → `ApiResponse<PageResult<TravelListResponse>>`
  - `GET /api/travels/{id}` — `getTravelDetail(@PathVariable Long id)` → `ApiResponse<TravelDetailResponse>`

### 3. AdminTravelController（后台接口 `/api/admin`）
- [ ] 创建 `AdminTravelController.java`
  - `@RestController` + `@RequestMapping("/api/admin")`
  - `POST /api/admin/travels` — `createTravel(@Valid @RequestBody TravelSaveRequest body)` → `ApiResponse<TravelDetailResponse>`
  - `PUT /api/admin/travels/{id}` — `updateTravel(@PathVariable Long id, @Valid @RequestBody TravelSaveRequest body)` → `ApiResponse<TravelDetailResponse>`
  - `DELETE /api/admin/travels/{id}` — `deleteTravel(@PathVariable Long id)` → `ApiResponse<Void>`

### 4. Controller 集成测试
- [ ] `@WebMvcTest(TravelController.class)` + `@MockBean TravelService`
  - GET `/api/travels` — 200 + 分页数据正确
  - GET `/api/travels?experienceType=extreme&rarityLevel=3` — 参数绑定正确
  - GET `/api/travels/{id}` — 200
  - GET `/api/travels/999` — 404
- [ ] `@WebMvcTest(AdminTravelController.class)` + `@MockBean TravelService`
  - POST `/api/admin/travels` 缺少必填字段 → 400 + 校验错误信息
  - POST `/api/admin/travels` 正常 → 200
  - PUT `/api/admin/travels/{id}` → 200
  - DELETE `/api/admin/travels/{id}` → 200
- [ ] 测试 `GlobalExceptionHandler` — 500 兜底异常
