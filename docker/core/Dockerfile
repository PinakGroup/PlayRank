FROM maven:3.6.2-jdk-8-slim AS maven
# copy the project files
COPY pom.xml ./pom.xml
# build all dependencies
RUN mvn dependency:go-offline -B
# copy your other files
COPY ./src ./src
# build for release
RUN mvn package -DskipTests

FROM openjdk:8-jre-alpine
# set deployment directory
WORKDIR /PlayRank
# copy over the built artifact from the maven image
COPY --from=maven target/PlayRank-*.jar ./PlayRank.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","./PlayRank.jar"]