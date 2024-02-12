FROM openjdk:23-slim

COPY target/my-application.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
