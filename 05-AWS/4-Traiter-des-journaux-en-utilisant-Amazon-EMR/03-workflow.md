# Workflow

```
+--------------------------------------------------+
|               Amazon S3 (Source)                 |
|--------------------------------------------------|
|  - Journaux d'Impressions                        |
|  - Journaux de Clics                             |
+--------------------------------------------------+
               |                    
               v                    
+--------------------------------------------------+
|              Amazon EMR Cluster                  |
|--------------------------------------------------|
|  - Lance un cluster avec des instances EC2       |
|  - Utilise Hadoop pour gérer le stockage et      |
|    le traitement des données                    |
+--------------------------------------------------+
               |                    
               v                    
+--------------------------------------------------+
|          Apache Hive (Traitement SQL)            |
|--------------------------------------------------|
|  Étapes de traitement :                          |
|  1. Créer des tables externes à partir des journaux |
|     dans Amazon S3                               |
|  2. Exécuter une jointure des tables (impressions |
|     et clics)                                    |
|  3. Effectuer des requêtes SQL pour extraire des |
|     tendances                                    |
+--------------------------------------------------+
               |                    
               v                    
+--------------------------------------------------+
|        Amazon S3 (Résultats du traitement)       |
|--------------------------------------------------|
|  - Table jointe des impressions et des clics     |
|  - Résultats disponibles pour analyse future     |
+--------------------------------------------------+
```

### Explication étape par étape :

1. **Amazon S3 (Source)** :
   - Les journaux d'impressions et les journaux de clics sont stockés dans des fichiers sur Amazon S3.

2. **Amazon EMR Cluster** :
   - Le cluster EMR est lancé pour traiter les données avec des instances EC2. Hadoop gère le traitement distribué à grande échelle.

3. **Apache Hive (Traitement SQL)** :
   - **Étape 1** : Hive crée des tables externes pour représenter les journaux d'impressions et de clics.
   - **Étape 2** : Une jointure est réalisée entre les deux tables pour identifier quelles impressions ont conduit à des clics.
   - **Étape 3** : Des requêtes SQL-like sont exécutées pour extraire des tendances à partir des données jointes.

4. **Amazon S3 (Résultats du traitement)** :
   - Les résultats de la jointure (les données combinées) sont écrits dans un autre compartiment S3, où ils peuvent être utilisés pour d'autres analyses ou rapportages.
