# syntax=docker/dockerfile:1

FROM paketobuildpacks/graalvm
FROM gradle:7.6-jdk17-alpine

WORKDIR /eureka-service

COPY .gradle/ .gradle
COPY gradlew ./
COPY build.gradle ./
COPY gradlew.bat ./
COPY settings.gradle ./

COPY eureka-discovery-service/src/ ./src

RUN gradle nativeCompile

CMD ["./build/native"]
