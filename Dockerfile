FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/TestPlatform-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "app.jar"]