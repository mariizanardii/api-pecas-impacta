FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/api-gateway-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]