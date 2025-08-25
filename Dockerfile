# Use OpenJDK 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot jar into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 9990

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
