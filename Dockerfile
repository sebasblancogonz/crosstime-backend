#
# Build stage
#
FROM gradle:jdk17-jammy  as BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

LABEL org.name="crosstime"
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
