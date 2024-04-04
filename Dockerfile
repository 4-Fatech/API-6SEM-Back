# Stage 1: Build stage
FROM ubuntu:20.04 AS build

# Install JDK and Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

# Build the application
RUN mvn clean install

# Stage 2: Runtime stage
FROM openjdk:17-jdk-slim

EXPOSE 8080

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/fatech-0.0.1-SNAPSHOT.jar /app/fatech-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/fatech-0.0.1-SNAPSHOT.jar"]