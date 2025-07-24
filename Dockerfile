# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use dynamic port from environment
EXPOSE ${PORT:-8080}

# Set Spring profile to production
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application with dynamic JAR name
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/*.jar"]
