#!/bin/bash

# ZJUT 数据库管理系统 Docker 部署脚本
# 作者: daizxn
# 日期: 2025年7月6日

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 日志函数
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查 Docker 和 Docker Compose
check_dependencies() {
    log_info "检查依赖..."
    
    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装或未在 PATH 中"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        log_error "Docker Compose 未安装或未在 PATH 中"
        exit 1
    fi
    
    log_success "依赖检查通过"
}

# 创建必要的目录
create_directories() {
    log_info "创建必要的目录..."
    
    mkdir -p database_data
    mkdir -p nginx/ssl
    mkdir -p logs/backend
    mkdir -p logs/nginx
    
    log_success "目录创建完成"
}

# 构建镜像
build_images() {
    log_info "构建 Docker 镜像..."
    
    # 构建后端镜像
    log_info "构建后端镜像..."
    docker-compose build backend
    
    # 构建前端镜像
    log_info "构建前端镜像..."
    docker-compose build frontend
    
    log_success "镜像构建完成"
}

# 启动服务
start_services() {
    log_info "启动服务..."
    
    # 启动数据库和 Redis
    log_info "启动数据库和缓存服务..."
    docker-compose up -d database redis
    
    # 等待数据库启动
    log_info "等待数据库启动..."
    sleep 30
    
    # 启动后端服务
    log_info "启动后端服务..."
    docker-compose up -d backend
    
    # 等待后端启动
    log_info "等待后端服务启动..."
    sleep 20
    
    # 启动前端服务
    log_info "启动前端服务..."
    docker-compose up -d frontend
    
    # 启动管理工具（可选）
    log_info "启动管理工具..."
    docker-compose up -d pgadmin
    
    log_success "所有服务启动完成"
}

# 检查服务状态
check_status() {
    log_info "检查服务状态..."
    
    echo ""
    docker-compose ps
    echo ""
    
    # 检查服务健康状态
    services=("database" "redis" "backend" "frontend")
    
    for service in "${services[@]}"; do
        if docker-compose ps -q $service > /dev/null; then
            status=$(docker-compose ps $service | grep $service | awk '{print $4}')
            if [[ $status == *"Up"* ]]; then
                log_success "$service 服务运行正常"
            else
                log_warning "$service 服务状态异常: $status"
            fi
        else
            log_error "$service 服务未运行"
        fi
    done
}

# 显示访问信息
show_access_info() {
    log_info "服务访问信息:"
    echo ""
    echo -e "${GREEN}前端应用:${NC} http://localhost"
    echo -e "${GREEN}后端API:${NC} http://localhost:9091"
    echo -e "${GREEN}数据库管理:${NC} http://localhost:8080"
    echo -e "${GREEN}数据库连接:${NC} localhost:5432"
    echo ""
    echo -e "${YELLOW}默认账户信息:${NC}"
    echo "- 数据库: db_user30 / Enmo@123"
    echo "- pgAdmin: admin@zjut.edu.cn / admin123"
    echo ""
}

# 停止服务
stop_services() {
    log_info "停止服务..."
    docker-compose down
    log_success "服务已停止"
}

# 重启服务
restart_services() {
    stop_services
    start_services
}

# 查看日志
view_logs() {
    service=${1:-}
    if [ -z "$service" ]; then
        docker-compose logs -f
    else
        docker-compose logs -f $service
    fi
}

# 清理数据
clean_data() {
    log_warning "这将删除所有数据，包括数据库和日志文件"
    read -p "确认继续? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        log_info "清理数据..."
        docker-compose down -v
        sudo rm -rf database_data logs
        docker system prune -f
        log_success "数据清理完成"
    else
        log_info "操作已取消"
    fi
}

# 备份数据库
backup_database() {
    log_info "备份数据库..."
    timestamp=$(date +"%Y%m%d_%H%M%S")
    backup_file="backup_${timestamp}.sql"
    
    docker-compose exec database pg_dump -U db_user30 -d db_zjut > $backup_file
    log_success "数据库备份完成: $backup_file"
}

# 显示帮助信息
show_help() {
    echo "ZJUT 数据库管理系统 Docker 部署脚本"
    echo ""
    echo "用法: $0 [命令]"
    echo ""
    echo "命令:"
    echo "  start     - 启动所有服务"
    echo "  stop      - 停止所有服务"
    echo "  restart   - 重启所有服务"
    echo "  status    - 查看服务状态"
    echo "  logs      - 查看所有日志"
    echo "  logs <服务名> - 查看指定服务日志"
    echo "  build     - 构建镜像"
    echo "  backup    - 备份数据库"
    echo "  clean     - 清理所有数据"
    echo "  help      - 显示此帮助信息"
    echo ""
    echo "示例:"
    echo "  $0 start          # 启动所有服务"
    echo "  $0 logs backend   # 查看后端日志"
    echo "  $0 backup         # 备份数据库"
}

# 主函数
main() {
    case "${1:-start}" in
        "start")
            check_dependencies
            create_directories
            build_images
            start_services
            check_status
            show_access_info
            ;;
        "stop")
            stop_services
            ;;
        "restart")
            restart_services
            check_status
            show_access_info
            ;;
        "status")
            check_status
            ;;
        "logs")
            view_logs $2
            ;;
        "build")
            check_dependencies
            build_images
            ;;
        "backup")
            backup_database
            ;;
        "clean")
            clean_data
            ;;
        "help"|"-h"|"--help")
            show_help
            ;;
        *)
            log_error "未知命令: $1"
            show_help
            exit 1
            ;;
    esac
}

# 执行主函数
main "$@"
