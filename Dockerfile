# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Only copy necessary files first (for layer caching)
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Download dependencies early (cache layer)
RUN ./mvnw dependency:go-offline

# Now copy the rest of the app
COPY src ./src

# Build the application
RUN ./mvnw clean install || (cat target/surefire-reports/*.txt && false)

# ---------- Stage 2: Runtime ----------
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expose port
EXPOSE 8080

# Copy built jar from builder stage
COPY --from=build /app/target/RestAPI-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
