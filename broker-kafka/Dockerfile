# Utiliser une image de base Alpine
FROM alpine:3.14

# Installer les dépendances nécessaires
RUN apk update && \
    apk add --no-cache openjdk11-jre wget tar && \
    rm -rf /var/cache/apk/*

# Définir des variables d'environnement pour Kafka
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS_HOST=localhost
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS_PORT=9092
ENV SPRING_KAFKA_CONSUMER_GROUP_ID=notification-group
ENV SPRING_KAFKA_TOPIC_NAME=message-notification

# Télécharger et extraire Kafka
RUN wget https://dlcdn.apache.org/kafka/3.9.0/kafka_2.13-3.9.0.tgz -O /tmp/kafka.tgz && \
    tar -xzf /tmp/kafka.tgz -C /opt && \
    mv /opt/kafka_2.13-3.9.0 /opt/kafka && \
    rm /tmp/kafka.tgz

# Configurer Zookeeper
COPY zookeeper.properties /opt/kafka/config/zookeeper.properties

# Configurer Kafka
COPY server.properties /opt/kafka/config/server.properties

# Exposer les ports nécessaires
EXPOSE 2181 9092

# Démarrer Zookeeper et Kafka
CMD ["sh", "-c", "/opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties & /opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties"]
