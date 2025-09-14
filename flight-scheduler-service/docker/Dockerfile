# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
# or: FROM maven:3.9.6-openjdk-21-slim
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Runtime stage
FROM openjdk:21-slim

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]