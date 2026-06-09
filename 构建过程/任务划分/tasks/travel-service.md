# travel-service — 业务逻辑模块

> 包路径: `com.travel.service` | 依赖: `travel-mapper` + `travel-dto` + `travel-entity` | 测试层级: 单元测试(Mock Mapper)

## 任务列表

### 1. 异常类
- [ ] 创建 `NotFoundException.java`（RuntimeException，携带 message）

### 2. JSON 工具方法
- [ ] 实现 `parseJsonArray(String json) → List<String>`
  - `"[\"a\",\"b\"]"` → `["a", "b"]`
  - null / 空字符串 / "[]" → 空 List
- [ ] 实现 `toJsonArray(List<String> list) → String`
  - `["a", "b"]` → `"[\"a\",\"b\"]"`
  - null / 空 List → null

### 3. Entity ↔ DTO 转换方法
- [ ] `toListResponse(Travel entity) → TravelListResponse`（不含 content/detailImages）
- [ ] `toDetailResponse(Travel entity) → TravelDetailResponse`（全字段 + JSON 反序列化）
- [ ] `toEntity(TravelSaveRequest req) → Travel`（新建用，id=null，JSON 序列化）
- [ ] `updateEntity(Travel entity, TravelSaveRequest req) → void`（更新用，保留 id，逐字段覆盖）

### 4. TravelService 接口与实现
- [ ] 创建 `TravelService` 接口（5 个方法签名）
- [ ] 创建 `TravelServiceImpl` 实现类

#### 4.1 listTravels
- [ ] 实现 `listTravels(TravelListRequest req) → PageResult<TravelListResponse>`
  - 创建 `Page` 对象 → 调用 `travelMapper.selectPageWithFilter`
  - `IPage<Travel>` → `PageResult<TravelListResponse>`（逐条转换）
  - 空结果返回空列表，不抛异常

#### 4.2 getTravelDetail
- [ ] 实现 `getTravelDetail(Long id) → TravelDetailResponse`
  - `selectById` → null 时抛 `NotFoundException`
  - Entity → DetailResponse（含 JSON 反序列化）

#### 4.3 createTravel
- [ ] 实现 `createTravel(TravelSaveRequest req) → TravelDetailResponse`
  - Request → Entity → `insert(entity)`
  - 获取生成的 id → 重新 `selectById` → 返回 DetailResponse

#### 4.4 updateTravel
- [ ] 实现 `updateTravel(Long id, TravelSaveRequest req) → TravelDetailResponse`
  - `selectById` → null 时抛 `NotFoundException`
  - `updateEntity` 覆盖字段 → `updateById(entity)`
  - 重新查询 → 返回 DetailResponse

#### 4.5 deleteTravel
- [ ] 实现 `deleteTravel(Long id) → void`
  - `selectById` → null 时抛 `NotFoundException`
  - `deleteById(id)`

### 5. Service 层单元测试
- [ ] Mock `TravelMapper`，测试 `listTravels` — 正常返回 / 空列表
- [ ] 测试 `getTravelDetail` — 存在返回 / id 不存在抛 NotFoundException
- [ ] 测试 `createTravel` — 验证 insert 入参 / 返回含 id
- [ ] 测试 `updateTravel` — 验证字段覆盖 / id 不变
- [ ] 测试 `deleteTravel` — 存在删除 / 不存在抛异常
- [ ] 测试 `parseJsonArray` / `toJsonArray` — 边界输入（null、空、正常）
- [ ] 测试 `toListResponse` / `toDetailResponse` — 字段映射完整性
