FROM openjdk:17-jdk-alpine3.14

RUN apk --no-cache add curl

RUN mkdir /app
WORKDIR /app

HEALTHCHECK \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]