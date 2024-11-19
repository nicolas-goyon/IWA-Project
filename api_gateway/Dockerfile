# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Define a build argument for the project path
ARG PROJECT_PATH

# Copy the build.gradle and settings.gradle files
COPY ${PROJECT_PATH}/build.gradle.kts ${PROJECT_PATH}/settings.gradle.kts ${PROJECT_PATH}/gradlew ${PROJECT_PATH}/gradlew.bat ./

# Copy the source code
COPY ${PROJECT_PATH}/src ./src

# Copy the gradle directory
COPY ${PROJECT_PATH}/gradle ./gradle

# Build the application
RUN ./gradlew clean build

# Copy the built jar file to a specific directory
COPY ${PROJECT_PATH}/build/libs/*.jar /app/build/app.jar


# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the previous stage
COPY --from=build /app/build/app.jar /app/app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]