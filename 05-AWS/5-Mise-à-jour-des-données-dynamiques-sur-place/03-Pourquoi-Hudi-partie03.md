--------------------------
# Le contexte de AWS Hudi dans notre laboratoire : 
--------------------------

Le **AWS Hudi Connector** permet d’utiliser le format de données **Hudi** pour effectuer des mises à jour et des suppressions dynamiques directement dans des tables stockées dans **Amazon S3**. Hudi est un framework qui permet de gérer les données transactionnelles sur des fichiers dans un data lake (comme S3), en apportant des capacités de mise à jour, d'insertion et de suppression à des formats de fichiers tels que Parquet ou Avro.

Je vous propose une explication du processus de **mise à jour dynamique des données en place avec AWS Hudi** :

### 1. **Hudi et la gestion des données incrémentales**
Hudi permet de stocker des **données transactionnelles** dans des tables et prend en charge deux types de tables principales :
   - **Copy on Write (COW)** : pour chaque opération de mise à jour ou d'insertion, les fichiers sont réécrits avec les nouvelles données.
   - **Merge on Read (MOR)** : ici, les mises à jour et insertions sont stockées dans des fichiers de type log et sont fusionnées à la lecture.

Ces deux approches permettent de mettre à jour les données **in place** (directement sur S3) sans devoir réécrire l'intégralité des fichiers à chaque modification.

### 2. **Utilisation de AWS Hudi Connector**
Le **AWS Hudi Connector** est souvent utilisé en conjonction avec **AWS Glue**, **Apache Spark** ou **AWS EMR** pour manipuler des datasets sur S3.

- **Insertion et mise à jour des données** : Vous pouvez insérer de nouvelles données dans une table Hudi tout en mettant à jour les données existantes. Par exemple, si une ligne avec un `primary key` particulier existe déjà dans une table, la nouvelle donnée remplace l'ancienne.
- **Suppression des données** : Le framework Hudi permet également de supprimer des données directement sur S3 sans devoir passer par des fichiers intermédiaires ou un recalcul complet de la table.

### 3. **Le processus de mise à jour dynamique dans AWS Hudi**
   - **Étape 1** : Définir une clé primaire (`primary key`) pour chaque enregistrement dans vos données. Cela permet à Hudi d'identifier quel enregistrement doit être mis à jour ou supprimé.
   - **Étape 2** : Charger les données dans S3 en utilisant un job Spark ou Glue qui utilise le Hudi connector.
   - **Étape 3** : Lors de la mise à jour, le job va automatiquement identifier les lignes avec le même `primary key` et les remplacer ou les fusionner dans les fichiers existants sur S3.

### 4. **Avantages du connecteur Hudi**
   - **Mise à jour efficace** : Vous n'avez pas besoin de réécrire toute la table ou tous les fichiers sur S3. Seules les parties modifiées sont affectées, ce qui permet d'économiser du temps et de l'espace de stockage.
   - **Support pour des tables transactionnelles** : Hudi gère des transactions ACID-like (atomicité, cohérence, isolation et durabilité), ce qui permet de maintenir l'intégrité des données même avec des modifications fréquentes.
   - **Optimisation des coûts et performances** : Hudi permet de maintenir un data lake efficace tout en évitant des opérations coûteuses sur de gros volumes de données.

### 5. **Exemple d’utilisation de Hudi avec Spark**

Voici un exemple basique pour charger et mettre à jour des données Hudi avec Spark :

```python
# Configuration Hudi pour Spark
hudi_options = {
  'hoodie.table.name': 'my_hudi_table',
  'hoodie.datasource.write.recordkey.field': 'primary_key',
  'hoodie.datasource.write.partitionpath.field': 'date',
  'hoodie.datasource.write.table.type': 'COPY_ON_WRITE',
  'hoodie.datasource.write.operation': 'upsert',
  'hoodie.datasource.hive_sync.enable': 'true',
  'hoodie.datasource.hive_sync.database': 'default',
  'hoodie.datasource.hive_sync.table': 'my_hudi_table',
}

# Charger les nouvelles données à insérer/mettre à jour
df = spark.read.format("parquet").load("s3://my-bucket/new-data")

# Ecrire les données dans la table Hudi
df.write.format("org.apache.hudi").options(**hudi_options).mode("append").save("s3://my-bucket/hudi-table")
```

### 6. **Ce que cela signifie pour un Lab**
Dans notre lab, **updating dynamique des données in place** signifie que vous pouvez insérer de nouvelles données ou mettre à jour les données existantes directement dans une table Hudi stockée sur S3 sans avoir à recréer ou réécrire toute la table. Vous utilisez probablement des jobs AWS Glue ou Spark qui appliquent les mises à jour et les modifications directement dans le data lake, en profitant des capacités transactionnelles de Hudi.

