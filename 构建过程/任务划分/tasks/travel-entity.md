# travel-entity — 数据实体模块

> 包路径: `com.travel.entity` | 依赖: 无 | 测试层级: 单元测试

## 任务列表

### 1. 创建项目骨架
- [ ] 创建 `backend/` 目录结构（Maven 多模块或单模块）
- [ ] 配置 `pom.xml`：Spring Boot 3.x + MyBatis-Plus 3.5.x + SQLite 驱动 + JUnit 5
- [ ] 创建包路径 `com.travel.entity`

### 2. 数据库初始化脚本
- [ ] 编写 `src/main/resources/db/schema.sql`
  - `travel` 表：13 个字段，含 CHECK 约束 `rarity_level BETWEEN 1 AND 4`
  - `admin_user` 表：id + username(UNIQUE) + password
- [ ] 编写 `src/main/resources/db/data.sql`（≥5 条示例数据，使用 PRD 中的 5 条）
- [ ] 配置 `application.yml` 指向 SQLite 文件路径 + 初始化脚本执行

### 3. Travel 实体类
- [ ] 创建 `Travel.java`
  - `@TableName("travel")` 显式指定表名
  - 13 个字段 + `@TableId(type = IdType.AUTO)` 主键策略
  - `createdAt` / `updatedAt` 使用 `@TableField(fill = FieldFill.INSERT)` / `FieldFill.INSERT_UPDATE`
- [ ] 字段类型正确：`detailImages`/`experienceTypes`/`highlights` 为 `String`（JSON 文本）
- [ ] 使用 Lombok `@Data` 简化 getter/setter（可选）

### 4. AdminUser 实体类
- [ ] 创建 `AdminUser.java`
  - `@TableName("admin_user")`
  - 字段：id(Long) + username(String) + password(String)

### 5. 实体层单元测试
- [ ] 测试 `@TableName` / `@TableId` 注解存在（反射检查）
- [ ] 测试字段名与数据库列名映射正确（驼峰 → 下划线）

### 6. 数据库连通性验证
- [ ] 启动 Spring Boot，验证 `schema.sql` + `data.sql` 成功执行
- [ ] 通过 MyBatis-Plus `BaseMapper.selectList()` 验证 ≥5 条数据可查询
