# **Pipeline 1 – Architecture et Explication**


![image](https://github.com/user-attachments/assets/559c6e02-bc3d-477b-aa70-ef463b4ddcf2)

Ce pipeline de données suit un parcours structuré en sept étapes majeures : Acquire, Stage, Process, Store, Analyze, Serve, et Consume. Il repose sur une architecture serverless et entièrement managée via les services AWS.




# **1. Acquire – Acquisition des données**
Dans cette première phase, les données sont envoyées dans le cloud AWS. Il s’agit ici de données d’information client.

- **Source** : Client (par exemple, une application, un site web ou un système d’entreprise).
- **Destination** : Les données brutes sont stockées dans **Amazon S3** (Simple Storage Service).
- **Objectif** : Centraliser les données brutes dans un espace de stockage durable, économique et hautement disponible.



# **2. Stage – Découverte et préparation initiale**
Avant d’exploiter les données, il est nécessaire de comprendre leur structure. C’est ici qu’intervient AWS Glue Crawler.

- **Service utilisé** : **AWS Glue Crawler**.
- **Fonctionnement** : Le crawler explore les fichiers bruts dans Amazon S3 pour en inférer automatiquement le schéma (types de colonnes, structure, etc.).
- **Résultat** : Les métadonnées (schémas) sont stockées dans le **Glue Data Catalog**. Ce catalogage permet de référencer les données pour qu’elles soient facilement interrogées par d’autres services (Athena, QuickSight, etc.).



# **3. Process – Transformation des données**
À ce stade, les données brutes sont transformées (nettoyées, filtrées, agrégées ou enrichies) à l’aide d’un service de traitement ETL (Extract, Transform, Load).

- **Service utilisé** : **AWS Glue** (service serverless d’ETL).
- **Objectif** : Appliquer des transformations sur les données brutes pour les rendre exploitables par l’analyse.
- **Sortie** : Données transformées, prêtes à être analysées, stockées à nouveau dans **Amazon S3**.



# **4. Store – Stockage des données transformées**
Les données résultant du traitement sont enregistrées dans une autre zone du même bucket ou dans un autre bucket S3.

- **Service complémentaire** : Un **deuxième Glue Crawler** est exécuté sur ces données transformées.
- **But** : Mettre à jour le **Glue Catalog** avec les nouveaux schémas issus des transformations pour que ces données soient également interrogeables.
- Ce catalogage permet de maintenir une traçabilité et une cohérence dans la structure des données tout au long du pipeline.



# **5. Analyze – Exploration et requêtes**
Une fois les données préparées et stockées, elles peuvent être analysées de manière interactive à l’aide d’un moteur de requête SQL serverless.

- **Service utilisé** : **Amazon Athena**.
- **Fonctionnement** : Athena interroge directement les fichiers stockés dans Amazon S3, en s’appuyant sur les métadonnées du Glue Data Catalog.
- **Avantage** : Aucun serveur n’est nécessaire, la tarification est basée sur la quantité de données lues par les requêtes.



# **6. Serve – Visualisation des résultats**
Les résultats des analyses sont ensuite mis à disposition des utilisateurs sous forme de visualisations et de tableaux de bord.

- **Service utilisé** : **Amazon QuickSight**.
- **Objectif** : Permettre aux analystes ou décideurs de consulter les résultats d’analyse dans une interface visuelle et interactive, souvent sous forme de graphiques, tableaux, cartes, etc.



# **7. Consume – Consommation des données**
Enfin, les utilisateurs finaux peuvent consommer les visualisations, les interpréter, et prendre des décisions en fonction des informations obtenues.

- **Profil des utilisateurs** : Analystes métier, Data Scientists, décideurs.
- **Usage** : Pilotage stratégique, reporting, détection d’anomalies, prise de décisions basée sur les données.



# **Résumé synthétique du pipeline**

| Étape      | Service AWS impliqué        | Description                                                        |
|------------|-----------------------------|--------------------------------------------------------------------|
| Acquire    | Amazon S3                   | Stockage initial des données brutes                                |
| Stage      | Glue Crawler + Glue Catalog | Découverte des schémas, enregistrement des métadonnées             |
| Process    | AWS Glue                    | Transformation des données                                         |
| Store      | Amazon S3 + Glue Catalog    | Stockage des données transformées, mise à jour du catalog          |
| Analyze    | Amazon Athena               | Exploration des données via requêtes SQL                           |
| Serve      | Amazon QuickSight           | Visualisation des résultats                                        |
| Consume    | Utilisateur final           | Consommation des analyses pour la prise de décisions               |

