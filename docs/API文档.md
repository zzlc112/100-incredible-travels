# API 接口文档

## 基础信息

- Base URL: `http://localhost:8080/api`
- 响应格式: JSON
- 统一响应结构:

```json
{
  "code": 200,
  "data": {},
  "message": "success"
}
```

| code | 含义 |
|------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录/认证失败 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

---

## 前台接口

### 1. 旅行列表

```
GET /api/travels
```

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| experienceType | String | 否 | 体验类型，逗号分隔多选: extreme,culture,hidden,eco,urban,taste |
| visualStyle | String | 否 | 视觉风格: minimal,cinematic,vintage,vivid,bw,natural |
| rarityLevel | Integer | 否 | 小众程度下限(1-4) |
| page | Integer | 否 | 页码，默认 1 |
| size | Integer | 否 | 每页条数，默认 20，最大 100 |

**响应示例**:

```json
{
  "code": 200,
  "data": {
    "total": 5,
    "page": 1,
    "size": 20,
    "list": [
      {
        "id": 1,
        "title": "冰岛火山内部探险",
        "subtitle": "深入休眠火山...",
        "coverImage": "https://...",
        "experienceTypes": ["extreme", "hidden"],
        "visualStyle": "cinematic",
        "rarityLevel": 3,
        "destination": "冰岛 · 雷克雅未克",
        "highlights": ["唯一可进入的火山内部"],
        "createdAt": "2026-06-09 10:00:00"
      }
    ]
  },
  "message": "success"
}
```

### 2. 旅行详情

```
GET /api/travels/{id}
```

**响应示例** (包含全部 13 个字段):

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "title": "冰岛火山内部探险",
    "subtitle": "...",
    "coverImage": "https://...",
    "detailImages": ["https://...", "https://..."],
    "experienceTypes": ["extreme", "hidden"],
    "visualStyle": "cinematic",
    "rarityLevel": 3,
    "destination": "冰岛 · 雷克雅未克",
    "content": "# 冰岛火山内部探险\n\n## 独一无二的地心之旅...",
    "highlights": ["唯一可进入的火山内部", "120米垂直下降"],
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

## 后台接口（需认证）

### 3. 管理员登录

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

**成功响应** (200):

```json
{ "code": 200, "data": "登录成功", "message": "success" }
```

**失败响应** (401):

```json
{ "code": 401, "data": null, "message": "用户名或密码错误" }
```

### 4. 检查登录状态

```
GET /api/admin/check
```

**成功响应** (200):

```json
{ "code": 200, "data": "已登录", "message": "success" }
```

**未登录响应** (401):

```json
{ "code": 401, "data": null, "message": "未登录" }
```

### 5. 退出登录

```
POST /api/admin/logout
```

**成功响应** (200):

```json
{ "code": 200, "data": "已退出登录", "message": "success" }
```

### 6. 新增旅行

```
POST /api/admin/travels
Content-Type: application/json
```

**请求体**:

```json
{
  "title": "新旅行标题",
  "subtitle": "副标题",
  "coverImage": "https://...",
  "detailImages": ["https://...", "https://..."],
  "experienceTypes": ["extreme", "hidden"],
  "visualStyle": "cinematic",
  "rarityLevel": 3,
  "destination": "目的地",
  "content": "# Markdown 正文",
  "highlights": ["亮点1", "亮点2"]
}
```

**字段约束**:

| 字段 | 必填 | 约束 |
|------|------|------|
| title | 是 | max 200 |
| subtitle | 是 | max 500 |
| coverImage | 是 | - |
| detailImages | 否 | max 10 |
| experienceTypes | 是 | >= 1 |
| visualStyle | 是 | - |
| rarityLevel | 是 | 1-4 |
| destination | 是 | max 200 |
| content | 是 | - |
| highlights | 否 | max 5 |

### 7. 编辑旅行

```
PUT /api/admin/travels/{id}
Content-Type: application/json
```

请求体同"新增旅行"。

### 8. 删除旅行

```
DELETE /api/admin/travels/{id}
```

**成功响应** (200):

```json
{ "code": 200, "data": null, "message": "success" }
```
