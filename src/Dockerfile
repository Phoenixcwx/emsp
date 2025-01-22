# 第一阶段：构建 JAR 文件
FROM maven:3.8.6-amazoncorretto-17 AS build
LABEL authors="Sean Cui"
WORKDIR /app
COPY . .
RUN mvn clean package

# 第二阶段：运行 JAR 文件
FROM amazoncorretto:17-alpine
LABEL authors="Sean Cui"
WORKDIR /app
COPY --from=build /app/target/emsp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]