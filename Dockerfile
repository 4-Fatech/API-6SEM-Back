# Estágio 1: Construir o aplicativo usando Maven
FROM maven:3.8.4-jdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Estágio 2: Executar o aplicativo em um contêiner
FROM adoptopenjdk/openjdk17:jre-17.0.2_8-alpine
EXPOSE 8080
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
