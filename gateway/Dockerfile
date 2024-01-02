FROM openjdk:17-jdk-alpine
COPY target/*.jar gateway-0.2.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/gateway-0.2.0-SNAPSHOT.jar"]