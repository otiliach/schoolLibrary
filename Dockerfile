FROM maven:3-amazoncorretto-21 AS builder
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=builder /target/library-0.0.1.jar library-0.0.1.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-0.0.1.jar"]