# 使用多阶段构建
# 第一阶段：构建应用
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src
COPY lib ./lib

# 安装本地 jar 依赖到 Maven 仓库
RUN mvn install:install-file -Dfile=lib/opengauss-jdbc-5.0.0.jar \
    -DgroupId=org.opengauss -DartifactId=opengauss-jdbc \
    -Dversion=5.0.0 -Dpackaging=jar

RUN mvn install:install-file -Dfile=lib/gsjdbc200.jar \
    -DgroupId=com.huawei.gauss -DartifactId=gsjdbc200 \
    -Dversion=1.0.0 -Dpackaging=jar

# 构建应用
RUN mvn clean package -DskipTests

# 第二阶段：运行应用
FROM openjdk:17-jre-slim

WORKDIR /app

# 安装必要的工具
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 创建应用用户
RUN groupadd -r appuser && useradd -r -g appuser appuser

# 复制构建的 jar 文件
COPY --from=builder /app/target/*.jar app.jar

# 创建日志目录
RUN mkdir -p /app/logs && chown -R appuser:appuser /app

# 切换到应用用户
USER appuser

# 暴露端口
EXPOSE 9091

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:9091/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", \
    "-Xms512m", "-Xmx1024m", \
    "-Dspring.profiles.active=docker", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "app.jar"]
