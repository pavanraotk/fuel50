FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /feeling-tracker/
RUN mkdir -p /feeling-tracker/logs/
ADD target/feeling-tracker-0.0.1-SNAPSHOT.jar /feeling-tracker/feeling-tracker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/feeling-tracker/feeling-tracker.jar"]