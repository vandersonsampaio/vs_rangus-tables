FROM openjdk:8-jdk-alpine
MAINTAINER vs-rangus-table
RUN mkdir /apps
COPY tables.controller/build/libs/tables.controller.jar /apps/rangus-table.jar
ENTRYPOINT ["java", "-jar", "/apps/rangus-table.jar"]