# Représentation du  workflow de votre pipeline ETL avec **Step Functions**, **AWS Glue**, **Amazon S3**, et **Athena**. 

- Ce schéma explique chaque étape de manière très simplifiée et vulgarisée.

```
+-------------------+     +---------------------+     +---------------------+
|                   |     |                     |     |                     |
|   Start Workflow  +----->  Check Glue Tables   +----->  Create Glue Tables |
|                   |     |                     |     |                     |
+-------------------+     +---------------------+     +---------------------+
                                 |                           |
                                 |                           |
                                 v                           v
                      +---------------------+     +---------------------+
                      |    Tables Exist?     |     |  Create Parquet &   |
                      |      (Yes/No)        |     | Compress with Snappy|
                      +----------+----------+     +---------------------+
                                 |                                 
                                 |                                 
                                 v                                 
                      +---------------------+                      
                      |      Check S3       |                      
                      |   Add New Data?     |                      
                      +---------------------+                      
                                 |                                 
                                 v                                 
                      +---------------------+                      
                      |  Insert New Data    |                      
                      |    (e.g., February) |                      
                      +---------------------+                      
                                 |                                 
                                 v                                 
                      +---------------------+                      
                      |     Create View     |                      
                      |   in Athena for     |                      
                      |    SQL Queries      |                      
                      +---------------------+                      
                                 |                                 
                                 v                                 
                      +---------------------+                      
                      |     End Workflow    |                      
                      +---------------------+                      
```

### Explication de chaque étape :

1. **Start Workflow** : Le workflow démarre avec AWS Step Functions.
2. **Check Glue Tables** : Vérification des tables existantes dans AWS Glue Data Catalog.
   - Si les tables n'existent pas, elles seront créées.
3. **Create Glue Tables** : Création des tables pour les données brutes (au format CSV).
4. **Create Parquet & Compress with Snappy** : Conversion des données brutes en format **Parquet** et compression avec **Snappy**.
5. **Check S3 Add New Data?** : Vérification des nouvelles données (comme les données de février).
6. **Insert New Data** : Ajout des nouvelles données dans les tables AWS Glue.
7. **Create View in Athena for SQL Queries** : Création d'une vue dans Amazon Athena pour interroger les données de manière efficace.
8. **End Workflow** : Le workflow se termine.

Ce schéma représente de manière simplifiée le flux du pipeline ETL. Chaque étape correspond à une tâche spécifique, et les décisions (comme vérifier si des tables existent ou si de nouvelles données doivent être ajoutées) sont prises en fonction des résultats de l'étape précédente.

