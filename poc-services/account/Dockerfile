# Use the OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the war file into the container
COPY target/*.jar /app/account.jar


# Expose port 4000 for the GraphQL server
EXPOSE 4000

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/account.jar"]
