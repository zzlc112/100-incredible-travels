# API 接口文档

> 版本: v1.0.0 | Base URL: `http://localhost:8080/api` | 格式: JSON

## 目录

- [1. 通用约定](#1-通用约定)
- [2. 认证机制](#2-认证机制)
- [3. 错误码](#3-错误码)
- [4. 枚举字典](#4-枚举字典)
- [5. 前台接口](#5-前台接口)
  - [5.1 旅行列表](#51-旅行列表)
  - [5.2 旅行详情](#52-旅行详情)
- [6. 后台接口](#6-后台接口)
  - [6.1 管理员登录](#61-管理员登录)
  - [6.2 检查登录状态](#62-检查登录状态)
  - [6.3 退出登录](#63-退出登录)
  - [6.4 新增旅行](#64-新增旅行)
  - [6.5 编辑旅行](#65-编辑旅行)
  - [6.6 删除旅行](#66-删除旅行)
- [7. 字段字典](#7-字段字典)

---

## 1. 通用约定

### 请求格式

- `Content-Type: application/json`（POST / PUT 请求必须携带）
- 字符编码: UTF-8
- 日期格式: `yyyy-MM-dd HH:mm:ss`，时区 `Asia/Shanghai`

### 响应格式

所有接口返回统一结构的 JSON:

```json
{
  "code": 200,
  "data": <具体数据>,
  "message": "success"
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| code | Integer | 业务状态码，参见[错误码](#3-错误码) |
| data | any | 业务数据，成功时返回具体数据，失败时为 `null` |
| message | String | 提示信息 |

### 分页响应

列表类接口的 `data` 使用分页结构:

```json
{
  "code": 200,
  "data": {
    "total": 5,
    "page": 1,
    "size": 20,
    "list": [ ... ]
  },
  "message": "success"
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| total | Long | 总记录数 |
| page | Integer | 当前页码 |
| size | Integer | 每页条数 |
| list | Array | 当前页数据列表 |

---

## 2. 认证机制

后台接口（路径前缀 `/api/admin/`）需要 Session 认证，以下三个端点除外:

- `POST /api/admin/login` — 登录
- `POST /api/admin/logout` — 登出
- `GET /api/admin/check` — 检查登录状态

### 认证方式

登录成功后，服务端创建 Session 并写入 `admin` 属性。后续请求自动携带 Cookie（`JSESSIONID`）。

### 认证流程

```
客户端                       服务端
  |                            |
  |-- POST /api/admin/login -->|  Session.setAttribute("admin", username)
  |<-- 200 + Set-Cookie -------|
  |                            |
  |-- GET /api/admin/travels ->|  AuthFilter 校验 Session
  |   (Cookie: JSESSIONID=..)  |  |
  |                            |  if session != null && session.admin != null:
  |                            |    → 放行
  |                            |  else:
  |<-- 200 --------------------|    → 401
```

---

## 3. 错误码

| code | 含义 | 触发场景 |
|------|------|----------|
| 200 | 成功 | 正常响应 |
| 400 | 请求参数错误 | 必填字段缺失、格式校验失败 |
| 401 | 未登录 / 认证失败 | Session 失效、用户名密码错误 |
| 404 | 资源不存在 | 访问不存在的 id |
| 500 | 服务器内部错误 | 未预期的运行时异常 |

400 响应示例:

```json
{
  "code": 400,
  "data": null,
  "message": "title: 不能为空; rarityLevel: 必须在1-4之间"
}
```

---

## 4. 枚举字典

### 体验类型 (experienceTypes)

| 编码 | 名称 | 说明 |
|------|------|------|
| `extreme` | 极限探险 | 攀岩、潜水、跳伞等极限运动 |
| `culture` | 文化沉浸 | 寺庙住宿、手工艺、民俗体验 |
| `hidden` | 秘境探索 | 鲜为人知的绝景、秘境 |
| `eco` | 生态旅行 | 野生动物、自然保护区 |
| `urban` | 城市隐世 | 城市屋顶农场、隐秘餐厅 |
| `taste` | 味觉之旅 | 松露狩猎、米其林、地方美食 |

### 视觉风格 (visualStyle)

| 编码 | 名称 | 说明 |
|------|------|------|
| `minimal` | 极简 | 干净线条、大量留白 |
| `cinematic` | 电影感 | 史诗级构图、戏剧化光影 |
| `vintage` | 复古胶片 | 低饱和度、颗粒感 |
| `vivid` | 高饱和冲击 | 鲜艳色彩、视觉冲击力 |
| `bw` | 黑白纪实 | 黑白摄影、纪实风格 |
| `natural` | 自然光 | 柔和自然光线 |

### 小众程度 (rarityLevel)

| 级别 | 标识 | 说明 |
|------|------|------|
| 1 | ⭐ | 大众热门 |
| 2 | ⭐⭐ | 精品之选 |
| 3 | ⭐⭐⭐ | 小众珍稀 |
| 4 | ⭐⭐⭐⭐ | 此生难遇 |

---

## 5. 前台接口

### 5.1 旅行列表

获取旅行卡片列表，支持多维度筛选和分页。

```
GET /api/travels
```

**请求参数** (Query String):

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| experienceType | String | 否 | — | 体验类型，逗号分隔多选: `extreme,culture` |
| visualStyle | String | 否 | — | 视觉风格: `minimal`,`cinematic`,`vintage`,`vivid`,`bw`,`natural` |
| rarityLevel | Integer | 否 | — | 小众程度下限 (1-4)，筛选 `>=` 该级别的记录 |
| page | Integer | 否 | 1 | 页码，最小值 1 |
| size | Integer | 否 | 20 | 每页条数，范围 1-100 |

**请求示例**:

```
GET /api/travels?experienceType=extreme,hidden&rarityLevel=3&page=1&size=12
```

**响应示例**:

```json
{
  "code": 200,
  "data": {
    "total": 2,
    "page": 1,
    "size": 12,
    "list": [
      {
        "id": 1,
        "title": "冰岛火山内部探险",
        "subtitle": "深入休眠火山Thrihnukagigur的岩浆房，下降120米进入地球深处",
        "coverImage": "https://images.unsplash.com/photo-1504893524553-b855bce32c67?w=800",
        "experienceTypes": ["extreme", "hidden"],
        "visualStyle": "cinematic",
        "rarityLevel": 3,
        "destination": "冰岛 · 雷克雅未克",
        "highlights": ["唯一可进入的火山内部", "120米垂直下降", "4000年地质奇观"],
        "createdAt": "2026-06-09 10:00:00"
      },
      {
        "id": 3,
        "title": "西伯利亚驯鹿牧民冬季迁徙",
        "subtitle": "跟随涅涅茨人穿越北极圈，见证地球上最后的游牧文明",
        "coverImage": "https://images.unsplash.com/photo-1517783999520-f068d7431a60?w=800",
        "experienceTypes": ["hidden", "eco"],
        "visualStyle": "bw",
        "rarityLevel": 4,
        "destination": "俄罗斯 · 亚马尔-涅涅茨自治区",
        "highlights": ["零下40度极地生存", "与游牧民族同吃同住", "400公里驯鹿迁徙"],
        "createdAt": "2026-06-09 10:00:00"
      }
    ]
  },
  "message": "success"
}
```

> **注意**: 列表接口不返回 `content`（正文）、`detailImages`（详情图片）、`updatedAt` 三个字段，以减少传输量。

---

### 5.2 旅行详情

获取单条旅行的完整信息，包含 Markdown 正文和详情图片。

```
GET /api/travels/{id}
```

**路径参数**:

| 参数 | 类型 | 说明 |
|------|------|------|
| id | Long | 旅行内容 ID |

**成功响应** (200):

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "title": "冰岛火山内部探险",
    "subtitle": "深入休眠火山Thrihnukagigur的岩浆房，下降120米进入地球深处",
    "coverImage": "https://images.unsplash.com/photo-1504893524553-b855bce32c67?w=800",
    "detailImages": [
      "https://images.unsplash.com/photo-1504893524553-b855bce32c67?w=800",
      "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?w=800",
      "https://images.unsplash.com/photo-1506806732259-39c2d0268443?w=800"
    ],
    "experienceTypes": ["extreme", "hidden"],
    "visualStyle": "cinematic",
    "rarityLevel": 3,
    "destination": "冰岛 · 雷克雅未克",
    "content": "# 冰岛火山内部探险\n\n## 独一无二的地心之旅\n\n冰岛是世界上唯一一个可以进入火山内部的国家...",
    "highlights": ["唯一可进入的火山内部", "120米垂直下降", "4000年地质奇观"],
    "createdAt": "2026-06-09 10:00:00",
    "updatedAt": "2026-06-09 10:00:00"
  },
  "message": "success"
}
```

**错误响应** (404):

```json
{
  "code": 404,
  "data": null,
  "message": "旅行内容不存在: id=999"
}
```

---

## 6. 后台接口

> 路径前缀 `/api/admin/`，除 login/logout/check 外均需认证。

---

### 6.1 管理员登录

```
POST /api/admin/login
Content-Type: application/json
```

**请求体**:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

**成功响应** (200):

```json
{
  "code": 200,
  "data": "登录成功",
  "message": "success"
}
```

**失败响应** (401):

```json
{
  "code": 401,
  "data": null,
  "message": "用户名或密码错误"
}
```

---

### 6.2 检查登录状态

```
GET /api/admin/check
```

**已登录** (200):

```json
{ "code": 200, "data": "已登录", "message": "success" }
```

**未登录** (401):

```json
{ "code": 401, "data": null, "message": "未登录" }
```

---

### 6.3 退出登录

```
POST /api/admin/logout
```

**响应** (200):

```json
{ "code": 200, "data": "已退出登录", "message": "success" }
```

> 退出后 Session 被销毁，后续请求需重新登录。

---

### 6.4 新增旅行

```
POST /api/admin/travels
Content-Type: application/json
Cookie: JSESSIONID=...
```

**请求体**:

```json
{
  "title": "摩洛哥撒哈拉沙漠星空露营",
  "subtitle": "在撒哈拉深处扎营，看银河横跨天际",
  "coverImage": "https://images.unsplash.com/photo-1489749798305-4fea3ae63d43?w=800",
  "detailImages": [
    "https://images.unsplash.com/photo-1489749798305-4fea3ae63d43?w=800",
    "https://images.unsplash.com/photo-1451337516015-6b6e9a44a8a3?w=800"
  ],
  "experienceTypes": ["hidden", "eco"],
  "visualStyle": "cinematic",
  "rarityLevel": 3,
  "destination": "摩洛哥 · 梅尔祖卡",
  "content": "# 撒哈拉星空露营\n\n骑骆驼进入 Erg Chebbi 沙丘群...",
  "highlights": ["柏柏尔人篝火晚宴", "银河拱桥拍摄", "沙丘日出"]
}
```

**字段约束**:

| 字段 | 类型 | 必填 | 约束 |
|------|------|------|------|
| title | String | 是 | 最大 200 字符 |
| subtitle | String | 是 | 最大 500 字符 |
| coverImage | String | 是 | 图片 URL |
| detailImages | List\<String\> | 否 | 最多 10 张 |
| experienceTypes | List\<String\> | 是 | 至少 1 项，参见[枚举字典](#4-枚举字典) |
| visualStyle | String | 是 | 参见[枚举字典](#4-枚举字典) |
| rarityLevel | Integer | 是 | 1-4，参见[枚举字典](#4-枚举字典) |
| destination | String | 是 | 最大 200 字符 |
| content | String | 是 | Markdown 格式 |
| highlights | List\<String\> | 否 | 最多 5 项，每项建议 15 字以内 |

**成功响应** (200):

```json
{
  "code": 200,
  "data": {
    "id": 6,
    "title": "摩洛哥撒哈拉沙漠星空露营",
    "subtitle": "在撒哈拉深处扎营，看银河横跨天际",
    "...": "(全部13字段，同详情接口响应)"
  },
  "message": "success"
}
```

**校验失败** (400):

```json
{
  "code": 400,
  "data": null,
  "message": "title: 不能为空; experienceTypes: 至少需要1个; rarityLevel: 必须在1-4之间"
}
```

---

### 6.5 编辑旅行

```
PUT /api/admin/travels/{id}
Content-Type: application/json
Cookie: JSESSIONID=...
```

**路径参数**:

| 参数 | 类型 | 说明 |
|------|------|------|
| id | Long | 旅行内容 ID |

**请求体**: 同 [6.4 新增旅行](#64-新增旅行)。

**成功响应** (200): 返回更新后的完整对象（格式同详情接口）。

**资源不存在** (404):

```json
{ "code": 404, "data": null, "message": "旅行内容不存在: id=999" }
```

---

### 6.6 删除旅行

```
DELETE /api/admin/travels/{id}
Cookie: JSESSIONID=...
```

**路径参数**:

| 参数 | 类型 | 说明 |
|------|------|------|
| id | Long | 旅行内容 ID |

**成功响应** (200):

```json
{ "code": 200, "data": null, "message": "success" }
```

**资源不存在** (404):

```json
{ "code": 404, "data": null, "message": "旅行内容不存在: id=999" }
```

---

## 7. 字段字典

### TravelListResponse (列表项)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| title | String | 标题 |
| subtitle | String | 副标题 |
| coverImage | String | 封面图 URL |
| experienceTypes | List\<String\> | 体验类型编码列表 |
| visualStyle | String | 视觉风格编码 |
| rarityLevel | Integer | 小众程度 1-4 |
| destination | String | 目的地 |
| highlights | List\<String\> | 特色亮点 |
| createdAt | String | 创建时间 `yyyy-MM-dd HH:mm:ss` |

### TravelDetailResponse (详情)

列表项全部字段 + 以下:

| 字段 | 类型 | 说明 |
|------|------|------|
| detailImages | List\<String\> | 详情图片 URL 列表 |
| content | String | 正文 (Markdown) |
| updatedAt | String | 更新时间 `yyyy-MM-dd HH:mm:ss` |

### TravelSaveRequest (新增/编辑)

| 字段 | 类型 | 必填 | 约束 |
|------|------|------|------|
| title | String | 是 | @NotBlank, max 200 |
| subtitle | String | 是 | @NotBlank, max 500 |
| coverImage | String | 是 | @NotBlank |
| detailImages | List\<String\> | 否 | @Size(max=10) |
| experienceTypes | List\<String\> | 是 | @NotEmpty |
| visualStyle | String | 是 | @NotBlank |
| rarityLevel | Integer | 是 | @NotNull, @Min(1), @Max(4) |
| destination | String | 是 | @NotBlank, max 200 |
| content | String | 是 | @NotBlank |
| highlights | List\<String\> | 否 | @Size(max=5) |

---

## 接口总览

| # | 方法 | 路径 | 说明 | 认证 | 请求体 |
|---|------|------|------|------|--------|
| 1 | GET | `/api/travels` | 旅行列表（筛选+分页） | 否 | — |
| 2 | GET | `/api/travels/{id}` | 旅行详情 | 否 | — |
| 3 | POST | `/api/admin/login` | 管理员登录 | 否 | LoginRequest |
| 4 | GET | `/api/admin/check` | 检查登录状态 | 否 | — |
| 5 | POST | `/api/admin/logout` | 退出登录 | 否 | — |
| 6 | POST | `/api/admin/travels` | 新增旅行 | 是 | TravelSaveRequest |
| 7 | PUT | `/api/admin/travels/{id}` | 编辑旅行 | 是 | TravelSaveRequest |
| 8 | DELETE | `/api/admin/travels/{id}` | 删除旅行 | 是 | — |
