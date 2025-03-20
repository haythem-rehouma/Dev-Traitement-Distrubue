### **Table des services AWS**

| **Service AWS**                              | **Rôle dans le Workflow**                          | **Fonctionnalité Principale**                                                                                          |
|--------------------------------------------|--------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| **AWS Step Functions**                      | Orchestration de Workflows                      | Coordination des processus ETL complexes impliquant AWS Glue, Athena, S3 et d'autres services.                      |
| **Amazon S3**                               | Stockage d'objets                                | Stockage des fichiers sources, des fichiers optimisés, des fichiers Parquet, et des résultats des requêtes Athena.   |
| **AWS Glue Data Catalog**                   | Gestion des métadonnées                          | Centralisation des métadonnées pour les tables, colonnes, schémas, et vues utilisées par Athena.                     |
| **AWS Glue Tables**                         | Stockage de métadonnées                         | Définitions de tables pour les fichiers CSV et Parquet dans S3, permettant leur requête via Athena.                  |
| **AWS Glue Databases**                      | Organisation des Tables                         | Organisation logique des tables au sein du Glue Data Catalog.                                                        |
| **Amazon Athena**                           | Requêtes Serverless                             | Interrogation des données stockées dans S3 avec SQL standard. Utilisation de CTAS (Create Table As Select) pour créer des tables. |
| **AWS Lambda**                              | Traitement Serverless                           | Transformation et enrichissement des données ingérées via Kinesis Data Firehose.                                    |
| **Amazon Kinesis Data Firehose**            | Ingestion de Données en Continu                | Capture et transfert des journaux d'accès web vers Amazon OpenSearch Service.                                        |
| **Amazon Kinesis Data Streams**             | Ingestion de Données en Continu                | Transmission de données générées par les appareils IoT ou autres sources de données en temps réel.                  |
| **Amazon OpenSearch Service**               | Analyse et Visualisation                        | Indexation, stockage, et requête des données par OpenSearch Dashboards.                                             |
| **Amazon OpenSearch Dashboards**            | Visualisation de Données                       | Création de visualisations telles que graphiques circulaires et cartes thermiques pour l'analyse des données.       |
| **Amazon CloudWatch**                       | Surveillance & Automatisation                 | Surveillance des journaux générés par Kinesis Data Firehose et AWS Lambda.                                          |
| **AWS Identity and Access Management (IAM)**| Gestion des permissions                       | Création de rôles, utilisateurs et politiques d'accès pour sécuriser les ressources AWS.                            |
| **Amazon Cognito**                          | Authentification des Utilisateurs            | Authentification des utilisateurs pour accéder aux visualisations OpenSearch Dashboards.                           |
| **AWS Cloud9**                              | IDE Cloud                                     | Environnement de développement pour écrire, tester et déployer des scripts.                                         |
| **AWS CloudFormation**                      | Infrastructure-as-Code                       | Déploiement automatisé de ressources AWS via des modèles JSON/YAML.                                                 |
| **Amazon EC2**                              | Calcul en Nuage                              | Instances EC2 pour héberger des serveurs web collectant des journaux d'accès.                                       |
| **Apache Hudi**                             | Gestion des Données Dynamiques               | Stockage des données modifiées en temps réel avec les mécanismes Copy-On-Write (COW) et Merge-On-Read (MOR).         |
| **Parquet Format**                          | Format de Stockage Optimisé                  | Format de fichier en colonnes compatible avec Snappy pour des requêtes rapides.                                     |
| **Snappy Compression**                      | Compression de Données                       | Compression des fichiers Parquet pour réduire la taille de stockage.                                               |
| **Partitioning**                           | Optimisation des Requêtes                   | Division logique des données par mois et année pour une interrogation plus rapide.                                 |
| **AWS Glue Crawlers**                       | Découverte Automatique                      | Exploration automatique des fichiers S3 pour détecter les schémas et créer des tables dans le Glue Data Catalog.    |
| **Amazon Redshift**                         | Entrepôt de Données                         | Analyse de gros volumes de données, bien que ce service ne soit pas activement utilisé dans ce lab.                |
| **AWS CLI**                                 | Interface en Ligne de Commande             | Interaction programmée avec AWS via des commandes exécutées dans Cloud9 ou d'autres environnements CLI.            |
| **Requêtes Nommées (Athena)**               | Requêtes Réutilisables                      | Sauvegarde et automatisation des requêtes SQL courantes pour simplifier l'analyse.                                  |
| **AmazonS3ReadOnlyAccess Policy**           | Politique IAM                               | Permissions permettant à Athena, Glue, et Step Functions de lire les données dans S3.                              |
| **IAM Roles (StepLabRole, OsDemoWebserverIAMRole)** | Gestion de la Sécurité                    | Permissions d'accès aux services AWS pour les workflows Step Functions, EC2, Lambda, Kinesis, etc.                 |
| **Buckets S3 (gluelab, data-science-bucket)**| Stockage de Données                        | Buckets spécifiques pour le stockage des données sources, des fichiers CSV, Parquet, et des résultats Athena.     |
| **Workflow Studio (Step Functions)**       | Conception Visuelle de Workflows          | Interface visuelle pour créer, tester, et exécuter des machines d'état AWS Step Functions.                         |
| **Choice States (Step Functions)**         | Logique Conditionnelle                    | Définition de routes alternatives en fonction des résultats intermédiaires au sein du workflow.                    |
| **Pass States (Step Functions)**           | Transitions Neutres                       | Permet de contrôler le flux d'exécution sans effectuer de tâches spécifiques.                                      |
| **Map States (Step Functions)**            | Itération sur Listes                      | Traitement de plusieurs éléments de données via un processus parallèle.                                            |
| **Amazon EMR (Elastic MapReduce)**         | Traitement Big Data                       | Exécution d'applications distribuées (Hadoop, Spark) pour traiter de grands volumes de données.                    |
| **Apache Hive (via EMR)**                   | Requêtes SQL sur Hadoop                  | Interface SQL permettant de manipuler des données massives distribuées.                                            |
| **YARN (Yet Another Resource Negotiator)**  | Gestionnaire de Ressources                | Coordination des tâches distribuées au sein des clusters EMR.                                                      |



#  **Résumé :**
Ce workflow intègre une **architecture avancée de traitement des données en continu**, combinant :
- **Ingestion des données** via **Kinesis Data Firehose** & **Kinesis Data Streams**.
- **Transformation & Enrichissement** via **AWS Lambda**.
- **Stockage des données** sur **Amazon S3** avec des tables optimisées Parquet et compression Snappy.
- **Interrogation des données** via **Athena**, avec des vues SQL définies pour simplifier l'analyse.
- **Visualisation des données** par **OpenSearch Dashboards**.
- **Orchestration des workflows** avec **Step Functions** (incluant logique conditionnelle et traitement par lots).

