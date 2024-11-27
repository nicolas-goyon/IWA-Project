# Use the official OpenJDK runtime as a parent image
FROM openjdk:8-jre-alpine

# Set environment variables
ENV ZOOKEEPER_HOST=${ZOOKEEPER_HOST:-localhost}
ENV ZOOKEEPER_PORT=${ZOOKEEPER_PORT:-2181}
ENV KAFKA_HOST=${KAFKA_HOST:-localhost}
ENV KAFKA_PORT=${KAFKA_PORT:-9092}
ENV KAFKA_BROKER_ID=${KAFKA_BROKER_ID:-0}
ENV KAFKA_ZOOKEEPER_CONNECT=${ZOOKEEPER_HOST}:${ZOOKEEPER_PORT}
ENV KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://${KAFKA_HOST}:${KAFKA_PORT}
ENV KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:${KAFKA_PORT}

# Optional: Set user group ID and topic name
ENV KAFKA_GROUP_ID=${KAFKA_GROUP_ID:-mygroup}
ENV KAFKA_TOPIC_NAME=${KAFKA_TOPIC_NAME:-mytopic}

# Install Kafka
RUN apk add --no-cache bash \
    && wget https://downloads.apache.org/kafka/3.7.1/kafka_2.12-3.7.1.tgz \
    && tar -xzf kafka_2.12-3.7.1.tgz \
    && mv kafka_2.12-3.7.1 /opt/kafka \
    && rm kafka_2.12-3.7.1.tgz

# Set the working directory
WORKDIR /opt/kafka

# Expose the Kafka port
EXPOSE ${KAFKA_PORT}

# Start Zookeeper and Kafka
CMD ["sh", "-c", \
    "bin/zookeeper-server-start.sh config/zookeeper.properties & \
    bin/kafka-server-start.sh config/server.properties"]
