FROM maven:3.8.1-openjdk-17-slim as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:resolve && mvn clean install -DskipTests --no-transfer-progress
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/challenge-java-v2.jar challenge-java-v2.jar
CMD ["java", "-jar", "challenge-java-v2.jar"]