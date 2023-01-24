FROM maven:3.6-jdk-11-slim

WORKDIR /ui-framework
COPY src /ui-framework/src
COPY pom.xml /ui-framework