# IWA-Project

Ce projet n'est pas encore déployé, mais en suivant les étapes décrites ci-dessous, vous pourrez tester notre application comme si elle était déjà disponible sur le PlayStore ! Aucun compte spécifique, tel qu'un compte administrateur, n'est nécessaire puisque cette fonctionnalité n'a pas été mise en place. Vous pourrez ainsi explorer l'expérience utilisateur, que ce soit en tant qu'hôte ou voyageur, en remplissant le formulaire d'inscription accessible depuis la page de connexion.


# Quickstart 

## Prerequis

- Avoir docker
- Pouvoir faire docker-compose

## Instruction

### Pour lancer le projet en mode dev : 

1 - Cloner le front avec la commande git clone git@github.com:nicolas-goyon/IWA-Project-Frontend.git

2 - Cloner le back avec la commande git clone git@github.com:nicolas-goyon/IWA-Project.git

3 - Pour chaque micro service, créer une base de données postgrès et lancer le script situé dans le dossier database

4 - Pour chaque micro services du back, le lancer avec la commande ./start.bat

5 - Pour le front,  à la racine, lancer avec la commande npm expo start

6 - Installer Expo Go sur votre smartphone et scanner le qr code







### Pour lancer le projet en mode prod: 

1 - Cloner le front avec la commande git clone git@github.com:nicolas-goyon/IWA-Project-Frontend.git

2 - Copier le fichier deploy.docker-compose.yml

3 - Pour lancer le back-end : docker-compose -f deploy.docker-compose.yml up

4 - Pour le front,  à la racine, lancer avec la commande npm expo start

5 - Installer Expo Go sur votre smartphone et scanner le qr code

