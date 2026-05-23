# Smart Childcare Management System - Backend Service  
# 托管班管理系统 - 后端服务

Backend service for a smart childcare management platform designed for childcare centers and after-school programs.

面向托管班与课后辅导机构的智能管理系统后端服务。

---

# 📌 Overview ｜ 项目简介

This repository currently contains the backend service module of the system.

The backend is designed to support:

- Student pickup management
- Attendance tracking
- Parent/teacher account management
- Role-based access control
- RESTful API support for Android and web platforms
- SMS verification and notification support

该仓库目前包含系统的后端 service 模块。

当前后端主要用于支持：

- 学生接送管理
- 学生考勤管理
- 家长 / 教师账号管理
- 基于角色的权限控制
- 为 Android 与管理后台提供 RESTful API
- 短信验证与通知支持

---

# ✅ Current Status ｜ 当前开发状态

## Completed ｜ 已完成

### Core Backend
- Spring Boot backend architecture
- MySQL database integration
- RESTful API design
- Basic role-based access control
- JWT authentication
- BCrypt password encryption

### Business Features
- Student pickup record management
- Attendance check-in/check-out
- Parent authorization verification
- User login and identity validation
- Basic SMS service integration

### Production & Security
- Production environment deployment completed
- JWT authentication middleware enabled
- BCrypt password hashing enabled
- Environment configuration separation
- HTTPS deployment structure prepared
- Sensitive configuration isolation
- Development/production environment separation
- Android API token validation support

### Infrastructure
- Maven project management
- Cloud server deployment completed
- Git version control integration

已完成内容：

### 后端核心功能
- Spring Boot 后端架构
- MySQL 数据库集成
- RESTful API 设计
- 基础 RBAC 权限控制
- JWT 登录鉴权
- BCrypt 密码加密

### 业务功能
- 学生接送记录管理
- 学生签到 / 签退管理
- 家长授权验证
- 用户登录身份校验
- 基础短信服务集成

### 生产环境与安全整改
- 生产环境部署完成
- JWT 鉴权中间件启用
- BCrypt 密码加密启用
- 环境配置隔离
- HTTPS 部署结构准备
- 敏感配置隔离
- 开发 / 生产环境分离
- Android 接口 Token 校验支持

### 基础设施
- Maven 项目管理
- 云服务器部署完成
- Git 版本管理接入

---

# 🛠 Tech Stack ｜ 技术栈

## Backend
- Java 21
- Spring Boot
- Spring Security
- JWT
- Maven

## Database
- MySQL

## Additional Services
- Aliyun SMS Service

---

# 🏗 System Architecture ｜ 系统架构

```text
Android App / Vue Admin
        ↓
   RESTful API
        ↓
Spring Boot Backend
        ↓
      MySQL
```

Current repository scope:

- Backend service only

Not included in this repository:

- Android client
- Vue admin panel

当前仓库仅包含：

- 后端 service 模块

暂未包含：

- Android 客户端
- Vue 管理后台

---

# ✨ Main Features ｜ 当前功能

## Authentication ｜ 用户认证
- JWT token authentication
- BCrypt password encryption
- Login session validation

- JWT Token 登录鉴权
- BCrypt 密码加密
- 登录状态校验

---

## Student Pickup Management ｜ 学生接送管理
- Pickup record management
- Parent authorization verification
- Pickup history tracking

- 学生接送记录管理
- 家长授权验证
- 接送历史记录

---

## Attendance Management ｜ 考勤管理
- Student attendance records
- Daily attendance queries
- Check-in / check-out support

- 学生签到记录
- 每日考勤查询
- 上下课签到支持

---

## SMS Support ｜ 短信支持
- Verification code support
- Notification service integration

- 验证码支持
- 通知服务集成

---

# 🔒 Security Improvements ｜ 已完成的安全整改

The current production deployment includes:

- JWT authentication
- BCrypt password hashing
- Sensitive configuration isolation
- HTTPS-ready deployment structure
- API token validation
- Restricted development configuration exposure
- Production/development environment separation

当前生产环境已完成：

- JWT 登录鉴权
- BCrypt 密码加密
- 敏感配置隔离
- HTTPS 部署结构准备
- API Token 校验
- 开发环境配置隔离
- 开发 / 生产环境分离

---

# 🚧 Future Improvements ｜ 后续开发方向

The project is still under active development.

Planned improvements include:

- Full HTTPS deployment
- Refresh token mechanism
- Operation audit logs
- Automated database backup
- Docker deployment support
- CI/CD workflow integration
- Fine-grained permission management
- API rate limiting
- Android client integration
- Vue admin panel integration
- Parent notification system
- Student pickup QR verification
- Real-time attendance dashboard

项目目前仍在持续开发中。

后续计划包括：

- 完整 HTTPS 部署
- Refresh Token 机制
- 操作审计日志
- 数据库自动备份
- Docker 部署支持
- CI/CD 自动化流程
- 更细粒度的权限控制
- API 限流
- 家长通知系统
- 学生接送二维码验证
- 实时考勤数据面板

---

# 🚀 Deployment ｜ 部署说明

## Environment Requirements ｜ 环境要求

- Java 21+
- MySQL 8+
- Maven 3.9+

---

## Run Locally ｜ 本地运行

```bash
git clone <repository-url>
cd service
mvn spring-boot:run
```

---

# ⚙ Configuration ｜ 配置说明

Sensitive production configurations are excluded from this repository.

Please configure locally:

- Database credentials
- JWT secret
- SMS service keys

生产环境敏感配置未包含在仓库中。

请自行配置：

- 数据库连接信息
- JWT 密钥
- 短信服务密钥

---

# 📂 Repository Scope ｜ 仓库范围说明

This repository currently contains only the backend service module.

Frontend applications and management platforms are maintained separately.

当前仓库仅包含后端 service 模块。

Vue 管理后台参考 https://github.com/LlockieLleone/pickup-system-admin

Android 应用 https://github.com/LlockieLleone/pickup-system-android

---

# 👨‍💻 Author ｜ 作者

Lingjun Liao  
University of Ottawa  
Computer Engineering
