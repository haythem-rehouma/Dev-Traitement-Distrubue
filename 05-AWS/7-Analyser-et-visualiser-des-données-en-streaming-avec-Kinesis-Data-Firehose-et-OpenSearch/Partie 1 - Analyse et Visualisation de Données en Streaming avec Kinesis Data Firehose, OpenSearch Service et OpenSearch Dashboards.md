# **Analyse et Visualisation de Données en Streaming avec Kinesis Data Firehose, OpenSearch Service et OpenSearch Dashboards**

### **Remarque : La configuration complète du laboratoire peut prendre jusqu’à 25 minutes.**


# 01 - **Vue d’ensemble du laboratoire et objectifs**

Les problèmes de **big data** nécessitent souvent des solutions **en temps réel**. C’est ce qu’on appelle la **vélocité**, l’un des cinq « V » du big data (volume, variété, vélocité, véracité, valeur).

Parmi les sources de données les plus fréquentes pour ces scénarios :
- Flux vidéo,
- Journaux d’application (logs),
- Équipements d’infrastructure.

Les données issues de ces cas d’usage à haute vélocité sont appelées **données en streaming**.  
**Amazon Kinesis** est un ensemble de services que vous pouvez utiliser pour analyser ces données en streaming.

Dans ce laboratoire, vous allez :
- Utiliser **Amazon Kinesis Data Streams** pour collecter des données issues d’un serveur web hébergé sur **Amazon EC2**.
- Utiliser **AWS Lambda** pour traiter et enrichir ces données.
- Analyser ces données dans **OpenSearch Dashboards** : vous allez y créer un index, visualiser les données, et construire des tableaux de bord accessibles aux utilisateurs métier via **Amazon Cognito**.

### **À la fin de ce laboratoire, vous saurez :**

- Décrire l’architecture du laboratoire dans la console AWS.
- Ingest**er** les journaux du serveur web dans **Kinesis Data Firehose** et **Amazon OpenSearch Service**.
- Observer l’ingestion et la transformation des données via les événements de logs CloudWatch.
- Créer un index dans OpenSearch Service.
- Visualiser les données dans OpenSearch Dashboards, notamment :
  - Un **diagramme circulaire** (camembert) illustrant les systèmes d’exploitation et navigateurs des visiteurs.
  - Une **carte thermique (heat map)** montrant comment les utilisateurs sont redirigés vers les pages produits (depuis la page de recherche ou de recommandations).



## **Durée estimée**

Ce laboratoire prend environ **90 minutes** à compléter.



## **Restrictions sur les services AWS**

Dans cet environnement de laboratoire, l’accès aux services AWS peut être limité aux seuls services nécessaires pour compléter les instructions du labo. Des erreurs peuvent survenir si vous tentez d’utiliser des services non prévus.



# 02 - **Scénario**

L’administratrice du site web de la **librairie universitaire** souhaite obtenir des **informations sur le comportement des visiteurs** du site.  
Elle utilise actuellement un système de suivi JavaScript avec des graphiques sur :
- la localisation des utilisateurs,
- leur navigateur,
- et s’ils utilisent des appareils mobiles.

Le nombre de visiteurs a augmenté chaque année.  
Cependant, de nombreux navigateurs **bloquant les scripts tiers**, les données collectées deviennent **peu fiables**.

Elle découvre alors un article du blog AWS Database intitulé :

> *« Analyser le comportement des utilisateurs avec Amazon Elasticsearch Service, Amazon Kinesis Data Firehose et Kibana »*

Ce billet propose une solution utilisant des **données en streaming** pour analyser les **logs d’un serveur web** et en déduire des **modèles d’accès utilisateur**.

Elle vous contacte pour savoir si cette approche pourrait **remplacer son système actuel**.

L’article contient des fichiers d’un site web de démonstration, avec :
- une **page de recherche**,
- une **page de recommandations**,
- quelques pages produit.

Vous décidez d’utiliser ce site de démo pour construire un **Proof of Concept (POC)** permettant d’analyser les comportements utilisateurs via les données en streaming.



# 03 - **Architecture du POC**

Le système utilise :

1. **Kinesis Data Firehose** pour collecter les logs d’accès du serveur web.
2. Des **fonctions Lambda** pour enrichir les données (géolocalisation par IP, etc.).
3. **OpenSearch Service** pour indexer et stocker les données.
4. **OpenSearch Dashboards** pour les visualiser.
5. **Amazon Cognito** pour l’authentification des utilisateurs aux tableaux de bord.

Lorsque vous démarrez le labo, toutes les ressources AWS nécessaires sont déjà déployées, comme le montre un diagramme fourni.
