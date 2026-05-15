# syntax=docker/dockerfile:1

# ============================================================
# Stage 1: build the JAR with Gradle
# ============================================================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Copy the Gradle wrapper + build scripts first.
# This layer is cached as long as gradle config doesn't change.
COPY gradle ./gradle
COPY gradlew settings.gradle build.gradle ./
RUN chmod +x ./gradlew

# Pre-warm the dependency cache (best-effort, doesn't fail the build).
RUN ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

# Now copy the source. Source-only changes hit only this layer.
COPY src ./src

# Build the bootable JAR. Skip tests for faster CI/CD.
RUN ./gradlew --no-daemon clean bootJar -x test

# ============================================================
# Stage 2: runtime — smaller JRE-only image
# ============================================================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the build stage.
# Wildcard is safe — bootJar produces exactly one *.jar in build/libs.
COPY --from=build /workspace/build/libs/*.jar app.jar

# Spring Boot binds to $PORT (set by Render) via application.yml's
# `server.port: ${PORT:8080}` — no need for command-line override.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
