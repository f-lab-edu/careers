FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG SPRING_PROFILE_ACTIVE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "${SPRING_PROFILE_ACTIVE}"]