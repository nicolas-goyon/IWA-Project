# IWA-Project

Résumé des commandes:
 
Pour lancer l'application/microservices, aller dans les dossiers et lancer : ./gradlew bootRun

api gateway: http://localhost:8080

ms user: http://localhost:8081/users
ms location: http://localhost:8082/locations


Builder l'application: ./gradlew build
Exécuter le JAR: java -jar build/libs/mon-application.jar


# Quickstart 

## Prerequis

- Avoir docker
- Pouvoir faire docker-compose

## Instruction

- Faire `docker network create iwa_network`
- Démarer chaque application via `docker-compose up --build`