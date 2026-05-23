# Smart Childcare Management System - Backend Service  
# 托管班管理系统 - 后端服务

Backend service for a smart childcare management platform designed for childcare centers and after-school programs.

面向托管班与课后辅导机构的智能管理系统后端服务。

---

# 📌 Overview ｜ 项目简介

This repository currently contains the backend service module of the system.

Main functionalities include:

- Student pickup management
- Attendance tracking
- User authentication
- Role-based access control
- RESTful API support for mobile and web clients

该仓库目前包含系统的后端 service 模块。

当前主要功能包括：

- 学生接送管理
- 出勤签到管理
- 用户身份认证
- 基于角色的权限控制
- 为移动端与管理后台提供 RESTful API 接口

---

# ✅ Current Status ｜ 当前状态

## Completed ｜ 已完成

- Spring Boot backend service architecture
- JWT-based authentication
- BCrypt password encryption
- MySQL database integration
- Production environment deployment
- Basic API permission control
- Environment configuration separation
- Cloud server deployment completed

已完成内容：

- Spring Boot 后端服务架构
- JWT 登录鉴权
- BCrypt 密码加密
- MySQL 数据库集成
- 生产环境部署
- 基础接口权限控制
- 环境配置隔离
- 云服务器部署上线

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
- Aliyun SMS Service（planned / 部分集成）

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

# 🔒 Security Improvements ｜ 已完成的安全整改

The current production deployment includes:

- JWT authentication
- BCrypt password hashing
- Sensitive configuration isolation
- HTTPS-ready deployment structure
- Production/development environment separation

当前生产环境已完成：

- JWT 登录鉴权
- BCrypt 密码加密
- 敏感配置隔离
- HTTPS 部署结构准备
- 开发 / 生产环境分离

---

# 🚧 Future Improvements ｜ 后续改进方向

The project is still under active development.

Planned improvements include:

- Full HTTPS deployment
- Refresh token mechanism
- Operation audit logs
- Automated database backup
- Improved role permission management
- API rate limiting
- Docker deployment support
- CI/CD workflow integration

项目目前仍在持续开发中。

后续计划包括：

- 完整 HTTPS 部署
- Refresh Token 机制
- 操作审计日志
- 数据库自动备份
- 更完善的角色权限管理
- API 限流
- Docker 部署支持
- CI/CD 自动化流程

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

# 📂 Project Scope ｜ 仓库范围说明

This repository currently contains only the backend service module.

Frontend applications and management platforms are maintained separately.

当前仓库仅包含后端 service 模块。

Android 客户端与 Vue 管理后台暂未包含在本仓库中。

---

# 👨‍💻 Author ｜ 作者

Lingjun Liao  
University of Ottawa  
Computer Engineering
