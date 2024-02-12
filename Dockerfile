FROM openjdk:23-slim

COPY target/moyur-0.0.1.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
