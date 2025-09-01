# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only pom.xml first (to leverage Docker cache for dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:17-jre-slim

WORKDIR /app

# Copy the packaged jar from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
