FROM openjdk:17-slim

COPY target/web-0.0.1-SNAPSHOT.jar web-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "web-0.0.1-SNAPSHOT.jar" ]