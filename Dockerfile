FROM maven:3.8.4-jdk-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-alpine

EXPOSE 8080

COPY --from=build /app/target/fatech-0.0.1-SNAPSHOT.jar /app/fatech-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/fatech-0.0.1-SNAPSHOT.jar"]
