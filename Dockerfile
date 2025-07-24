# Build stage
FROM maven:3.9.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN mkdir -p uploads
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]
