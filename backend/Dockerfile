FROM eclipse-temurin:25-jdk-alpine
LABEL authors="rohianon"
ARG JAR_FILE=target/*.jar
COPY ./target/library-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]