# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN mkdir -p uploads
EXPOSE 8000
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-Dserver.port=${PORT:-8000}", "-jar", "app.jar"]
