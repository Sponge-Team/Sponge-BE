# Set the base image to use, in this case, adoptopenjdk with Java 17
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY build/libs/sponge-1.0.jar /app/

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "/app/sponge-1.0.jar"]
