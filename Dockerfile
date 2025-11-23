# Multi-stage Dockerfile: build with Maven, run with lightweight JRE
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy only pom first for caching
COPY pom.xml ./
COPY src ./src

RUN mvn -DskipTests package -DskipTests=true

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/api-agencia-de-viagens-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
