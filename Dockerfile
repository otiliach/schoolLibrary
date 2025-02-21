FROM openjdk:21-jdk
RUN mvn clean install
ADD ./target/library-0.0.1.jar library-0.0.1.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","library-0.0.1.jar"]