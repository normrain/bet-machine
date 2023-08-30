FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

WORKDIR /app
COPY . /app

EXPOSE 4000

CMD ["java", "-jar", "/app.jar"]