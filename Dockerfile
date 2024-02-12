# 시작 이미지를 지정합니다.
FROM openjdk:8-jdk-al

# 애플리케이션의 jar 파일에 대한 절대 경로를 변수로 설정합니다.
ARG JAR_FILE=target/*.jar

# jar 파일을 app.jar라는 이름의 파일로 복사합니다.
COPY ${JAR_FILE} app.jar

# 컨테이너가 시작 때 실행될 명령어를 지정합니다.
ENTRYPOINT ["java","-jar","/app.jar"]
