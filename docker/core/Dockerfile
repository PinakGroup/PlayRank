#
# Build stage
#
FROM maven:3.6.2-jdk-8-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/app/target/PlayRank-0.0.1-SNAPSHOT.jar /usr/app/PlayRank.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/app/PlayRank.jar"]