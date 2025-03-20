# Mise à jour de données dynamiques en place

## Vue d'ensemble du Lab et objectifs

Un défi fréquent lors du traitement des données en continu provenant d'appareils de l'Internet des objets (IoT) est la modification fréquente du schéma. Par exemple, un capteur peut envoyer initialement quatre valeurs, puis deux, et ensuite dix. Avec plusieurs capteurs, cela peut rapidement s’amplifier. Il devient donc difficile de s'adapter à ces changements constants et de mettre à jour le lac de données avec des enregistrements modifiés. Cela est particulièrement complexe pour les lacs de données basés sur le stockage d'objets, comme Amazon Simple Storage Service (Amazon S3). L'outil Apache Hudi Connector, disponible sur AWS Marketplace, peut vous aider à relever ce défi.

Dans ce lab, vous utiliserez Amazon S3, Amazon Athena, AWS Glue et Apache Hudi pour relever ce défi d'adaptation aux schémas dynamiques. Vous utiliserez ces services pour faciliter les mises à jour efficaces en place des données et exécuter des requêtes en quasi temps réel.

### Objectifs à atteindre à la fin du Lab :

- Créer un job AWS Glue pour exécuter des scripts ETL personnalisés.
- Utiliser Athena pour exécuter des requêtes.
- Utiliser le connecteur Apache Hudi pour effectuer des mises à jour en place.

### Durée : 
Ce lab durera environ 90 minutes.

---

### Restrictions sur les services AWS :
Dans cet environnement de lab, l'accès aux services AWS et aux actions de service peut être limité à ceux nécessaires pour réaliser les instructions du lab. Vous pourriez rencontrer des erreurs si vous essayez d'accéder à d'autres services ou d'effectuer des actions au-delà de celles décrites dans ce lab.

---

### Scénario

Marie fait partie de l'équipe de data science et travaille avec beaucoup de données en continu collectées par des appareils IoT. À chaque fois que ces appareils sont réinitialisés, la taille et la structure des données changent. Un appareil qui envoie généralement peu de champs peut parfois en envoyer beaucoup plus. Cela devient complexe à gérer, car les outils standard s’attendent à une structure fixe des données. De plus, les données modifiées ne doivent affecter que les lignes concernées et non l’ensemble des données.

Votre défi est de développer un **proof of concept (POC)** pour permettre une adaptation aux schémas changeants tout en ne mettant à jour que les enregistrements concernés.

Vous utiliserez un job AWS Glue avec des scripts personnalisés pour gérer ce schéma dynamique et le connecteur Apache Hudi pour les mises à jour en place. Vous utiliserez Athena pour exécuter des requêtes SQL sur les données dynamiques, et S3 pour le stockage des données. Enfin, vous utiliserez Amazon Kinesis Data Streams pour ingérer des données générées aléatoirement par Amazon Kinesis Data Generator (KDG), qui émule un appareil IoT.

### Diagramme d'architecture initial :
Lors du démarrage du lab, l'environnement contiendra les ressources indiquées dans le diagramme suivant :

*(le diagramme)*

![image](https://github.com/user-attachments/assets/81d4eec3-c5b8-46af-8e21-8abf3a762fc8)

*(digramme final)*

![image](https://github.com/user-attachments/assets/8d8e5b28-0b0e-4b6d-b8f1-f54b778702c5)

---

### Tâche 1 : Analyser l’environnement du lab

Dans cette tâche, vous allez examiner l'environnement du lab et noter les détails de la configuration initiale.

1. Ouvrir la console CloudFormation.
2. Dans la liste des stacks, sélectionnez celle dont la description ne contient pas "ADE".
3. Accédez à l'onglet **Outputs** et copiez les valeurs dans un éditeur de texte pour utilisation ultérieure.

### Tâche 2 : S'abonner et activer le connecteur Hudi

1. Accédez à AWS Glue Studio.
2. Dans le menu, sélectionnez **Marketplace**.
3. Recherchez "hudi", sélectionnez **Apache Hudi Connector for AWS Glue**, et suivez les étapes pour l'activer.

### Tâche 3 : Configuration des scripts pour AWS Glue

1. Ouvrez la console AWS Cloud9.
2. Téléchargez les scripts nécessaires via les commandes `wget`.
3. Chargez ces fichiers dans un bucket S3 en utilisant la commande `aws s3 cp`.

### Tâche 4 : Configuration et exécution du job AWS Glue

1. Créez un stack CloudFormation pour configurer le job AWS Glue en utilisant un modèle CloudFormation stocké dans votre bucket S3.
2. Lancez le job Glue en accédant à la section "Jobs" d'AWS Glue et en sélectionnant le job créé.

### Tâche 5 : Utilisation de Kinesis Data Generator

1. Ouvrez Kinesis Data Generator et configurez les champs pour générer des données de capteurs.
2. Envoyez ces données dans Kinesis Data Streams.

### Tâche 6 : Utilisation d'Athena pour interroger les données

1. Utilisez Athena pour inspecter le schéma des données.
2. Exécutez des requêtes sur les tables stockées dans S3 via AWS Glue.

### Tâche 7 : Modification dynamique du schéma

1. Modifiez le schéma des données envoyées par Kinesis Data Generator en ajoutant une nouvelle colonne.
2. Observez les résultats des requêtes dans Athena sans effectuer de modifications dans AWS Glue.

### Tâche 8 : Réinitialisation du schéma

1. Revenez au schéma initial et vérifiez que les anciennes données sont toujours correctement gérées par AWS Glue et Athena.

---

### Soumission de votre travail

Pour soumettre votre progression, sélectionnez **Submit** en haut de la page.

---

Félicitations, vous avez complété le lab !


-------------------------
# Annexe 1 - Étapes et détails étape par étape du flux de travail dans ce lab sur la mise à jour dynamique des données avec Apache Hudi, AWS Glue, Athena, et Kinesis :
-------------------------


### Étape 1 : Lancement du Job AWS Glue et Ingestion de Données par Kinesis Data Stream
- **Déclenchement du Job AWS Glue** : Vous démarrez un job AWS Glue qui est configuré pour lire les données depuis un flux Kinesis.
- **Ingestion des données IoT simulées** : Simultanément, le **Kinesis Data Generator (KDG)** commence à générer des données aléatoires pour simuler des appareils IoT qui envoient des informations de capteurs à des intervalles réguliers. Ces données sont envoyées dans un flux Kinesis, servant de point d’entrée pour le traitement.

### Étape 2 : Traitement des Données par un Script Python dans AWS Glue
- **Exécution du script Python** : Le job AWS Glue exécute un script Python personnalisé. Ce script est chargé de parcourir les données du flux Kinesis, les traitant au fur et à mesure qu'elles arrivent.
- **Itération sur le flux de données** : Le script Python itère à travers chaque enregistrement du flux Kinesis, analysant les données reçues et décidant de la meilleure approche pour les stocker ou les mettre à jour.

### Étape 3 : Insertion ou Mise à Jour des Données dans un Bucket S3
- **Insertion ou mise à jour des enregistrements** : Le script Python, en utilisant le connecteur Apache Hudi, effectue des opérations en place sur les données stockées dans un bucket Amazon S3. Selon l'état des enregistrements (nouveaux ou déjà existants), le script insère de nouvelles données ou met à jour les enregistrements existants.
  - **Insertion de nouvelles données** : Si un enregistrement est nouveau, il est ajouté dans le bucket S3.
  - **Mise à jour des enregistrements existants** : Si l'enregistrement existe déjà, les nouvelles informations mises à jour remplacent les anciennes données en utilisant la fonctionnalité de mise à jour en place (in-place updates) d'Apache Hudi.

### Étape 4 : Catalogage des Métadonnées par AWS Glue Data Catalog
- **Catalogue des métadonnées** : Le **Glue Data Catalog** est mis à jour automatiquement avec les métadonnées associées aux tables, colonnes, et schémas des données présentes dans le bucket S3. Cela permet à d'autres services, comme Athena, d'accéder facilement à la structure des données sans avoir à analyser directement les fichiers.

### Étape 5 : Interaction d'Athena avec Amazon S3
- **Requêtes Athena** : Athena est un service de requête basé sur SQL qui interagit directement avec les fichiers stockés dans S3. En utilisant les métadonnées fournies par AWS Glue Data Catalog, Athena est capable d'interroger les données dans le bucket S3. Ce mécanisme permet de visualiser les données en quasi temps réel.
- **Accès aux partitions et colonnes** : Grâce à ces métadonnées, Athena sait comment accéder aux différentes partitions et colonnes du dataset stocké, facilitant les analyses.

### Étape 6 : Exécution de Requêtes dans Athena pour Visualiser les Données
- **Exécution des requêtes SQL** : Vous utilisez Athena pour exécuter des requêtes SQL sur les tables stockées dans S3. Cela permet d'explorer les données en temps réel, d'identifier des tendances ou anomalies, et de tester la validité du processus d'ingestion et de mise à jour des données.
  - **Exemple de requête** : Une requête simple pourrait être `SELECT * FROM "hudi_demo_table" LIMIT 10;` pour visualiser un échantillon de 10 enregistrements.

### Étape 7 : Changement Dynamique du Schéma et Requêtes sur les Nouvelles Données
- **Changement du schéma** : À ce stade, vous modifiez le schéma des données envoyées par Kinesis (par exemple, en ajoutant une nouvelle colonne). Cette modification ne nécessite aucune intervention manuelle dans le job AWS Glue ni dans le Data Catalog.
- **Requêtes dans Athena** : Vous continuez à exécuter des requêtes dans Athena pour voir comment les nouvelles données, avec le schéma modifié, sont correctement intégrées et analysées.

### Étape 8 : Réinitialisation du Schéma et Analyse des Données
- **Réversion du schéma** : Vous revenez ensuite au schéma initial en arrêtant l’envoi de données avec le nouveau schéma et en recommençant à envoyer des données avec l'ancien schéma.
- **Requêtes sur les données réinitialisées** : Vous exécutez des requêtes pour vérifier que même après le retour à l'ancien schéma, les enregistrements sont toujours gérés correctement et que les nouvelles colonnes n’affichent que des valeurs nulles.

---



-------------------------
# Annexe 2 - Flux de travail étape par étape du lab :
-------------------------


```
+----------------------+          +------------------------+           +--------------------------+
|    Kinesis Data      |          |      AWS Glue Job      |           |          S3 Bucket        |
|     Generator        |  ----->  |   (Python Script)      |  ----->   |     (Stockage des         |
|  (IoT Data Source)   |          |      Traitement        |           |    données mises à jour)  |
+----------------------+          +------------------------+           +--------------------------+
        (1)                              (2)                                      (3)

    +--------------------+  
    | Kinesis Data Stream |    
    +--------------------+ 

------------------------------------------------------------------------------------------------------

+----------------------+          +------------------------+           +--------------------------+
|    AWS Glue Data     |          |        Athena          |           |    AWS Glue Data Catalog  |
|       Catalog        |  <---->  |    (Exécution de SQL)  |  <---->   |     (Métadonnées sur les  |
|    (Métadonnées)     |          |   pour requêter S3     |           |   tables et colonnes)     |
+----------------------+          +------------------------+           +--------------------------+
        (4)                               (5)                                      (6)

------------------------------------------------------------------------------------------------------

   Étape 7 : Changement du schéma dans le flux de données Kinesis
   +--------------------------+     
   |   Schéma Modifié :        |
   |   Ajout d'une colonne     |  
   +--------------------------+     

   Résultat : Mise à jour dynamique dans S3 et Athena sans modifier AWS Glue

------------------------------------------------------------------------------------------------------

   Étape 8 : Retour au schéma initial
   +---------------------------+     
   |   Schéma Réinitialisé :    |
   |   Retour à l'ancien format |
   +---------------------------+     

   Résultat : Les anciennes colonnes fonctionnent, la nouvelle colonne affiche des valeurs nulles
```

### Détails étape par étape :
1. **Génération des données IoT** : Le Kinesis Data Generator (KDG) envoie les données IoT au flux Kinesis.
2. **Traitement par AWS Glue** : Le job AWS Glue exécute un script Python qui traite les données issues du flux Kinesis.
3. **Mise à jour des données dans S3** : Le script Python insère ou met à jour les enregistrements dans un bucket S3 en utilisant Apache Hudi.
4. **Catalogue des métadonnées** : Le AWS Glue Data Catalog stocke les métadonnées sur les tables et les colonnes des données dans S3.
5. **Requêtes SQL avec Athena** : Athena utilise les métadonnées du Data Catalog pour interagir avec S3 et exécuter des requêtes SQL.
6. **Visualisation des données** : Les requêtes sont exécutées dans Athena pour visualiser les données mises à jour.
7. **Changement de schéma** : Un changement de schéma est introduit dans les données envoyées par Kinesis, sans modifier AWS Glue.
8. **Réinitialisation du schéma** : Le schéma est réinitialisé et les requêtes continuent à fonctionner correctement, avec des valeurs nulles pour les colonnes supprimées.

