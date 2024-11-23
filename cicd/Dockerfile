# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-jammy AS builder

# Set the working directory in the container
WORKDIR /app

# Define build path to ./
ARG PROJECT_PATH=./

# if JWT_SECRET is not set, set it to "" as a default value
ENV JWT_SECRET=${JWT_SECRET:-""}

# General environment variables
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV API_GATEWAY_URL=${API_GATEWAY_URL}
ENV MS_NAME=${MS_NAME}
ENV MS_PORT=${MS_PORT}
# Copy the build.gradle and settings.gradle files
COPY ${PROJECT_PATH}/build.gradle.kts ${PROJECT_PATH}/settings.gradle.kts ${PROJECT_PATH}/gradlew ${PROJECT_PATH}/gradlew.bat ./

# Copy the source code
COPY ${PROJECT_PATH}/src ./src

# Copy the gradle directory
COPY ${PROJECT_PATH}/gradle ./gradle

# Set the permissions for the gradlew file
RUN chmod +x gradlew

# Build the application without running tests
RUN ./gradlew clean build -x test



# Stage 2: Create the runtime image
FROM openjdk:24-ea-21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
