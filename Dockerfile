FROM maven:3.9.9-eclipse-temurin-21 as builder
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn package -Pproduction -DskipTests

FROM eclipse-temurin:21.0.5_11-jre
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=builder /app/target/school-grades-spring-security-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT exec java -jar app.jar
EXPOSE 8080
