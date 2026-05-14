FROM eclipse-temurin:18-jre-alpine
WORKDIR /app
COPY build/libs/We-Commerce_API-0.0.1-SNAPSHOT.jar easycart_api.jar
ENTRYPOINT ["java", "-jar", "easycart_api.jar"]