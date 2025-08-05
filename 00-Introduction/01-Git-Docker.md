### **1. Qu'est-ce que Git ?**

**Git** est un **système de contrôle de version décentralisé** utilisé principalement dans le développement logiciel. Il permet à plusieurs personnes de **travailler simultanément sur le même projet** sans écraser le travail des autres.

#### Objectifs principaux de Git :

* Suivre **l’historique complet des modifications** sur un projet.
* Travailler en équipe sur le **même code source**, de façon **structurée et sécurisée**.
* Permettre de **revenir à une version antérieure** du projet en cas d’erreur.
* Favoriser une **expérimentation sûre** grâce à la gestion des **branches**.

#### Fonctionnement de base :

* Le projet est initialisé avec `git init`.
* Les changements sont ajoutés avec `git add`.
* Ils sont enregistrés avec `git commit`.
* On peut partager le code avec `git push` et récupérer celui des autres avec `git pull`.

#### Avantage majeur :

Chaque développeur possède une **copie complète du projet**, avec tout l'historique. Cela rend Git très fiable, même en cas de déconnexion ou de perte de données sur un serveur.

---

### **2. Qu'est-ce que Docker ?**

**Docker** est une **plateforme de virtualisation légère** qui permet de créer, exécuter et déployer des applications dans des **conteneurs**.

#### Objectifs principaux de Docker :

* **Isoler les applications** dans des environnements reproductibles.
* Éviter les problèmes du type "ça marche sur mon ordinateur mais pas sur le serveur".
* **Standardiser le déploiement** des applications, quel que soit le système d'exploitation hôte.

#### Fonctionnement de base :

* On décrit un environnement dans un fichier `Dockerfile`.
* On construit une **image Docker** avec `docker build`.
* On exécute l’application dans un **conteneur** avec `docker run`.

#### Différence avec une machine virtuelle :

Contrairement aux machines virtuelles, les conteneurs **partagent le noyau du système hôte**. Ils sont donc plus légers, plus rapides à démarrer, et consomment moins de ressources.
