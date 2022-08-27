FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /auth-api
ARG PORT_BUILD=8080
EXPOSE $PORT_BUILD
ARG JAR_FILE=./target/*.jar
COPY $JAR_FILE ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]