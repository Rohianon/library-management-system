# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-25-alpine AS build
WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app

# Copy the JAR from the build stage
# Note: Using a wildcard helps if the version number changes
COPY --from=build /app/target/*.jar app.jar

# Render requires the app to listen on a port (usually 8080 or 10000)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]