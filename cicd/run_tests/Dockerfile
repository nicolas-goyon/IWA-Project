# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the run_tests.sh script
COPY run_tests.sh ./run_tests.sh

# Set the permissions for the shell script
RUN chmod +x /app/run_tests.sh

# Set the entry point to the run_tests.sh script
ENTRYPOINT ["./run_tests.sh"]
