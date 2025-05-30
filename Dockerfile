# Use official OpenJDK 17 image as base
FROM eclipse-temurin:17-jdk-alpine

# Add a volume pointing to /tmp (optional, useful for Spring Boot)
VOLUME /tmp

# Copy jar file built by Maven
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
