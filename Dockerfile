FROM openjdk:17
WORKDIR /Docker-Batch-app
COPY . .
ADD  /target/Docker-Batch-app-0.0.1-SNAPSHOT.jar Docker-Batch-app.jar
ENTRYPOINT ["java", "-jar", "Docker-Batch-app.jar"]
