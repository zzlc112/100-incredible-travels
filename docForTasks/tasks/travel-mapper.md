# travel-mapper — 数据访问模块

> 包路径: `com.travel.mapper` | 依赖: `travel-entity` | 测试层级: 轻量集成测试

## 任务列表

### 1. Mapper 接口
- [ ] 创建 `TravelMapper.java` 继承 `BaseMapper<Travel>`
  - `@Mapper` 注解
  - 自定义方法：`IPage<Travel> selectPageWithFilter(Page<Travel> page, @Param("experienceType") String, @Param("visualStyle") String, @Param("rarityLevel") Integer)`
- [ ] 创建 `AdminUserMapper.java` 继承 `BaseMapper<AdminUser>`
  - `@Mapper` 注解
  - 自定义方法：`AdminUser selectByUsername(@Param("username") String username)`

### 2. Mapper XML
- [ ] 编写 `TravelMapper.xml`（`resources/mapper/`）
  - 筛选查询 SQL：`<where>` + `<if>` 动态条件
  - `experience_type` LIKE 模糊匹配 + `<foreach>` 多选 OR 连接
  - `visual_style` 精确匹配
  - `rarity_level` ≥ 筛选
  - `ORDER BY created_at DESC`
- [ ] 编写 `AdminUserMapper.xml`
  - `selectByUsername` SQL

### 3. MyBatis-Plus 分页插件配置
- [ ] 在配置类中注册 `PaginationInnerInterceptor(DbType.SQLITE)`（可放入 travel-config 后此处验证）

### 4. Mapper 集成测试
- [ ] `@MybatisPlusTest` + SQLite 内存数据库测试
- [ ] 测试基础 CRUD：`selectById` / `insert` / `updateById` / `deleteById`
- [ ] 测试筛选查询 — 每种筛选条件组合至少 1 条用例
- [ ] 测试分页：page=1/size=3，验证 total / 返回条数
- [ ] 测试 `selectByUsername` — 存在/不存在用户
