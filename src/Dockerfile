# 构建 JAR 文件
FROM maven:3.8.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn -DskipTests=true clean package

# 最终镜像
FROM amazoncorretto:17-alpine
WORKDIR /app

# 复制 应用
COPY --from=build /app/target/emsp-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080 80


# 复制并设置启动脚本
WORKDIR /app
COPY ./src/start.sh /start.sh
RUN chmod +x /start.sh

# 启动 Spring Boot 应用
CMD ["/start.sh"]