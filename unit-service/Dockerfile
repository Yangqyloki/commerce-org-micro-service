FROM openjdk:11-slim

VOLUME /tmp

ARG JAR_FILE=unit-service.jar
COPY ${JAR_FILE} /opt/unit-service.jar

RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/unit-service.jar"]