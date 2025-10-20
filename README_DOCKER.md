# ZJUT 数据库管理系统 Docker 部署

这是一个完整的基于 Docker 的部署方案，包含前端（Vue.js）、后端（Spring Boot）、数据库（OpenGauss）和管理工具。

## 系统架构

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │    Backend      │    │   Database      │
│   (Vue.js)      │───▶│  (Spring Boot)  │───▶│  (OpenGauss)    │
│   Nginx:80      │    │   Port:9091     │    │   Port:5432     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐    ┌─────────────────┐
                    │     Redis       │    │    pgAdmin      │
                    │   Port:6379     │    │   Port:8080     │
                    └─────────────────┘    └─────────────────┘
```

## 快速开始

### 1. 前置要求

- Docker 20.10+
- Docker Compose 2.0+
- 至少 4GB 可用内存
- 至少 10GB 可用磁盘空间

### 2. 一键部署

```bash
# 克隆项目后，进入项目目录
cd /home/daizxn/coding/zjut/dataBase

# 一键启动所有服务
./deploy.sh start
```

### 3. 访问应用

部署完成后，可以通过以下地址访问：

- **前端应用**: http://localhost
- **后端 API**: http://localhost:9091
- **数据库管理**: http://localhost:8080
- **数据库连接**: localhost:5432

### 4. 默认账户

- **数据库**: 
  - 用户名: `db_user30`
  - 密码: `Enmo@123`
  - 数据库: `db_zjut`

- **pgAdmin**:
  - 邮箱: `admin@zjut.edu.cn`
  - 密码: `admin123`

## 管理命令

使用 `deploy.sh` 脚本来管理整个系统：

```bash
# 启动所有服务
./deploy.sh start

# 停止所有服务
./deploy.sh stop

# 重启所有服务
./deploy.sh restart

# 查看服务状态
./deploy.sh status

# 查看所有日志
./deploy.sh logs

# 查看特定服务日志
./deploy.sh logs backend
./deploy.sh logs frontend
./deploy.sh logs database

# 重新构建镜像
./deploy.sh build

# 备份数据库
./deploy.sh backup

# 清理所有数据（谨慎使用）
./deploy.sh clean

# 显示帮助信息
./deploy.sh help
```

## 手动 Docker Compose 命令

如果你更喜欢使用原生的 Docker Compose 命令：

```bash
# 启动所有服务
docker-compose up -d

# 停止所有服务
docker-compose down

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f

# 重新构建并启动
docker-compose up -d --build

# 停止并删除卷（删除所有数据）
docker-compose down -v
```

## 服务配置

### 数据库服务 (OpenGauss)
- **镜像**: enmotech/opengauss:3.1.0
- **端口**: 5432
- **数据持久化**: `./database_data`
- **初始化脚本**: 自动执行 `system.sql` 和 `data.sql`

### 后端服务 (Spring Boot)
- **构建**: 本地 Dockerfile
- **端口**: 9091
- **配置文件**: `application-docker.yml`
- **日志**: 持久化到 `./logs/backend`

### 前端服务 (Vue.js + Nginx)
- **构建**: 本地 Dockerfile
- **端口**: 80
- **反向代理**: API 请求转发到后端
- **静态资源**: 缓存优化

### Redis 缓存
- **镜像**: redis:7-alpine
- **端口**: 6379
- **数据持久化**: Redis AOF

### pgAdmin 管理工具
- **镜像**: dpage/pgadmin4:latest
- **端口**: 8080
- **用途**: 数据库可视化管理

## 数据持久化

系统使用 Docker 卷来持久化重要数据：

- `database_data`: 数据库文件
- `redis_data`: Redis 数据
- `backend_logs`: 后端日志
- `pgadmin_data`: pgAdmin 配置

## 网络配置

所有服务都运行在同一个 Docker 网络 `zjut_network` 中，服务间可以通过服务名进行通信。

## 健康检查

每个服务都配置了健康检查：

- **数据库**: 检查 OpenGauss 状态
- **Redis**: 执行 ping 命令
- **后端**: 检查 actuator/health 端点
- **前端**: 检查 HTTP 响应

## 开发模式

如果需要开发调试，可以：

1. 只启动数据库和 Redis：
```bash
docker-compose up -d database redis
```

2. 本地运行后端和前端进行开发

3. 修改 `application-docker.yml` 中的数据库连接地址为 `localhost`

## 生产环境配置

在生产环境中，建议：

1. 修改默认密码
2. 配置 HTTPS 证书
3. 设置防火墙规则
4. 配置日志轮转
5. 设置监控和告警
6. 定期备份数据

## 故障排除

### 常见问题

1. **端口冲突**
   - 检查 80、9091、5432、6379、8080 端口是否被占用
   - 修改 `docker-compose.yml` 中的端口映射

2. **内存不足**
   - 确保系统有足够内存
   - 调整 JVM 堆内存设置

3. **数据库连接失败**
   - 检查数据库服务是否正常启动
   - 查看数据库日志：`./deploy.sh logs database`

4. **前端无法访问后端**
   - 检查 Nginx 配置
   - 确保后端服务正常运行

### 日志查看

```bash
# 查看所有服务日志
./deploy.sh logs

# 查看特定服务日志
./deploy.sh logs database
./deploy.sh logs backend
./deploy.sh logs frontend
./deploy.sh logs redis
```

### 数据备份恢复

```bash
# 备份数据库
./deploy.sh backup

# 手动恢复（示例）
docker-compose exec database psql -U db_user30 -d db_zjut < backup_20250706_120000.sql
```

## 更新和维护

### 更新镜像

```bash
# 拉取最新的基础镜像
docker-compose pull

# 重新构建自定义镜像
./deploy.sh build

# 重启服务
./deploy.sh restart
```

### 清理旧数据

```bash
# 清理未使用的镜像和容器
docker system prune -f

# 完全清理（包括数据）
./deploy.sh clean
```

## 许可证

本项目仅供学习和研究使用。

## 联系方式

如有问题，请联系项目维护者。
