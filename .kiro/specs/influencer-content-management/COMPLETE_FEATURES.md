# 新媒体内容平台 - 完整功能清单

## ✅ 前台功能（front_home）

### 1. 首页功能 - `/`
- **轮播图展示** - 首页横幅轮播
- **精选内容** - 展示精选稿件列表
- **最新内容** - 展示最新发布的稿件
- **热门内容** - 展示热门稿件
- **分类浏览** - 按分类筛选内容（美食、旅游、科技等）
- **平台筛选** - 按平台筛选（抖音、小红书、微博等）
- **优质达人展示** - 展示平台优质达人
- **内容详情查看** - 点击查看稿件详情弹窗
- **达人主页查看** - 查看达人个人主页弹窗
- **搜索功能** - 搜索内容和达人
- **用户登录/注册** - 用户认证功能
- **用户菜单** - 个人中心、退出登录
- **响应式设计** - 支持移动端和桌面端

### 2. 内容浏览 - `/view-all`
- 查看全部内容列表
- 按类型筛选（精选/最新/热门/达人）
- 按分类筛选
- 按平台筛选
- 分页浏览

### 3. 达人入驻申请 - `/influencer-application`
- 填写申请表单
  - 平台账号
  - 粉丝量
  - 内容领域
  - 代表作品链接（可添加多个）
- 表单验证
- 提交申请
- 成功后跳转个人中心

### 4. 个人中心 - `/personal-center`
- **个人信息Tab**
  - 查看头像、用户名、邮箱
  - 达人身份标识
- **达人申请Tab**
  - 查看申请状态（待审核/已通过/已驳回）
  - 查看申请详情
  - 查看驳回原因
  - 查看审核通过时间
- **我的稿件Tab**（达人用户）
  - 查看稿件列表
  - 状态筛选

### 5. 内容投稿管理 - `/content-management`（达人专属）
- **权限控制** - 非达人用户显示申请引导
- **创建稿件**
  - 标题
  - 正文
  - 封面图上传（支持预览）
  - 平台类型选择（抖音/小红书/微博/其他）
  - 预计发布时间
- **保存草稿** - 保存未完成的稿件
- **提交审核** - 提交完整稿件
- **稿件列表**
  - 查看所有稿件
  - 状态筛选（草稿/待审核/已通过/已驳回/已发布）
  - 状态标签颜色区分
- **编辑稿件** - 编辑草稿或被驳回的稿件
- **删除稿件** - 删除草稿
- **查看驳回原因** - 被驳回稿件显示原因

---

## ✅ 后台管理功能（front-admin）

### 1. 内容管理
#### 稿件管理 - `/content/article`
- 查看稿件列表
- 搜索筛选（标题、分类、类型、平台、状态、审核状态）
- 新增稿件
- 编辑稿件
- 删除稿件
- 批量删除
- 稿件审核（通过/拒绝）

#### 达人管理 - `/content/influencer`
- 查看达人列表
- 搜索筛选（名称、平台、领域、状态）
- 新增达人
- 编辑达人
- 删除达人
- 批量删除
- 查看达人头像

#### 横幅管理 - `/content/banner`
- 查看横幅列表
- 新增横幅
- 编辑横幅
- 删除横幅
- 排序管理

#### 平台管理 - `/content/platform`
- 查看平台列表
- 新增平台
- 编辑平台
- 删除平台

#### 标签管理 - `/content/tag`
- 查看标签列表
- 新增标签
- 编辑标签
- 删除标签

### 2. 达人申请审核 - `/content/application` ✨新增
- **查看申请列表**
  - 显示申请ID、用户名、平台账号、粉丝量、领域、状态、申请时间
  - 状态标签（待审核/已通过/已驳回）
- **搜索筛选**
  - 按用户名搜索
  - 按平台账号搜索
  - 按内容领域搜索
  - 按审核状态筛选
- **查看详情**
  - 申请ID
  - 用户信息
  - 平台账号
  - 粉丝量
  - 内容领域
  - 代表作品链接（可点击）
  - 审核状态
  - 驳回原因（如有）
  - 申请时间
  - 审核时间（如有）
- **审核通过** - 用户角色升级为达人
- **审核驳回** - 填写驳回原因（必填）

### 3. 内容投稿审核 - `/content/submission` ✨新增
- **查看投稿列表**
  - 显示稿件ID、封面图、标题、投稿用户、平台类型、状态、预计发布时间、创建时间
  - 状态标签（草稿/待审核/已通过/已驳回/已发布）
- **搜索筛选**
  - 按标题搜索
  - 按平台类型筛选
  - 按状态筛选
- **查看详情**
  - 稿件ID
  - 投稿用户
  - 平台类型
  - 标题
  - 正文（可滚动查看）
  - 封面图（可预览）
  - 预计发布时间
  - 状态
  - 创建时间
  - 驳回原因（如有）
  - 发布时间（如有）
- **审核通过** - 稿件状态变为已通过
- **审核驳回** - 填写驳回原因（必填）
- **删除稿件** - 删除单个稿件
- **批量删除** - 批量删除多个稿件

### 4. 系统管理
- 用户管理
- 角色管理
- 菜单管理
- 部门管理
- 岗位管理
- 配置管理
- 通知公告
- 日志管理
- 系统监控

---

## 🔌 API接口

### 前台API（/api）
**达人申请**
- `POST /api/influencer/application` - 提交申请
- `GET /api/influencer/application/my` - 查询我的申请
- `GET /api/influencer/role` - 查询角色信息

**内容投稿**
- `POST /api/content/submission` - 保存稿件
- `PUT /api/content/submission/{id}/submit` - 提交审核
- `GET /api/content/submission/my` - 查询我的稿件
- `GET /api/content/submission/{id}` - 查询详情
- `DELETE /api/content/submission/{id}` - 删除稿件

**首页内容**
- `GET /home/categories` - 获取分类列表
- `GET /home/featured` - 获取精选内容
- `GET /home/latest` - 获取最新内容
- `GET /home/influencers` - 获取优质达人
- `GET /home/platforms` - 获取平台列表
- `GET /home/banners` - 获取轮播图

**用户认证**
- `POST /login` - 用户登录
- `POST /logout` - 用户登出
- `GET /user/info` - 获取用户信息

### 后台管理API（/admin）
**达人申请审核**
- `GET /admin/influencer/application/list` - 查询申请列表
- `GET /admin/influencer/application/{id}` - 查询详情
- `PUT /admin/influencer/application/{id}/audit` - 审核申请

**内容投稿审核**
- `GET /admin/content/submission/list` - 查询投稿列表
- `GET /admin/content/submission/{id}` - 查询详情
- `PUT /admin/content/submission/{id}/audit` - 审核投稿
- `DELETE /admin/content/submission/{id}` - 删除投稿
- `DELETE /admin/content/submission/batch` - 批量删除

**内容管理**
- `GET /admin/home/content/list` - 稿件列表
- `POST /admin/home/content` - 新增稿件
- `PUT /admin/home/content/{id}` - 更新稿件
- `DELETE /admin/home/content/{id}` - 删除稿件
- `PUT /admin/home/content/{id}/audit` - 审核稿件

**达人管理**
- `GET /admin/home/influencer/list` - 达人列表
- `POST /admin/home/influencer` - 新增达人
- `PUT /admin/home/influencer/{id}` - 更新达人
- `DELETE /admin/home/influencer/{id}` - 删除达人

**其他管理**
- 横幅、平台、标签、分类管理API

---

## 🗄️ 数据库表

### 核心业务表
1. **influencer_application** - 达人申请表
2. **content_submission** - 内容投稿表
3. **home_content** - 稿件内容表
4. **home_influencer** - 达人信息表
5. **home_banner** - 轮播图表
6. **home_category** - 分类表
7. **home_platform** - 平台表
8. **home_tag** - 标签表

### 系统表
- sys_user - 用户表
- sys_role - 角色表
- sys_menu - 菜单表
- sys_dept - 部门表
- sys_post - 岗位表
- sys_config - 配置表
- sys_notice - 通知表
- sys_log - 日志表

---

## 📊 功能统计

### 前台
- **页面数量**: 5个
- **功能模块**: 5个
- **API接口**: 15+个

### 后台管理
- **管理页面**: 8个
- **功能模块**: 10+个
- **API接口**: 30+个

### 后端
- **Entity实体**: 10+个
- **Service服务**: 20+个
- **Controller控制器**: 10+个
- **总代码文件**: 100+个

---

## 🎯 核心业务流程

### 用户使用流程
```
访问首页 → 浏览内容 → 注册/登录 → 申请达人 → 等待审核 → 
审核通过 → 创建稿件 → 提交审核 → 审核通过 → 内容发布
```

### 管理员审核流程
```
查看申请列表 → 查看详情 → 审核（通过/驳回） → 
查看投稿列表 → 查看详情 → 审核（通过/驳回）
```

---

## ✨ 特色功能

1. **完整的审核流程** - 达人申请和内容投稿双重审核
2. **权限分级** - 普通用户、达人用户、管理员
3. **状态管理** - 完整的状态流转（草稿→待审核→已通过/已驳回）
4. **驳回机制** - 可修改被驳回的内容重新提交
5. **响应式设计** - 支持多端访问
6. **实时搜索** - 多维度搜索和筛选
7. **批量操作** - 支持批量删除等操作
8. **图片上传** - 支持封面图上传和预览

---

## 🚀 技术栈

### 前端
- Vue 3 + TypeScript
- Pinia (状态管理)
- Vue Router (路由)
- Tailwind CSS / Element Plus (UI)
- Axios (HTTP)

### 后端
- Spring Boot
- MyBatis-Plus
- MySQL
- Spring Security + JWT
- Swagger (API文档)

---

**项目完成度：100%**
**可直接部署使用！**
