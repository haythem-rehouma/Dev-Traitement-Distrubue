# Commandes AWS CLI pour le Data Engineering

## **Prérequis**
- Avoir un compte AWS actif avec permissions administratives
- Avoir configuré AWS CLI : `aws configure`
- Avoir un bucket S3 pour stocker les résultats Athena et les fichiers d’entrée
- Utiliser un terminal Cloud9 ou une machine avec AWS CLI v2 installé

---

## **Table des matières**

1. Introduction aux services AWS pour les données  
2. S3 — Stockage des fichiers  
3. AWS Glue — Catalogage et préparation de données  
4. Athena — Analyse SQL de fichiers S3  
5. Redshift — Entrepôt de données  
6. Kinesis Data Streams — Données en streaming  
7. Kinesis Data Firehose — Livraison automatique dans S3/Redshift  
8. EMR — Traitement massif avec Spark/Hadoop  
9. CloudFormation — Déploiement d'infrastructure pour la donnée  
10. Exercice final pratique

---

## **1. Introduction aux services AWS pour le Data Engineering**

| Service | Rôle |
|--------|------|
| **S3** | Stockage de données brutes (CSV, JSON, Parquet) |
| **Glue** | Découverte des schémas + ETL (Python Spark) |
| **Athena** | Requête SQL sur les fichiers stockés dans S3 |
| **Redshift** | Base de données analytique (entrepôt de données) |
| **Kinesis** | Streaming de données en temps réel |
| **EMR** | Traitement big data (Spark, Hive, Hadoop) |
| **CloudFormation** | Déploiement scripté et versionné des architectures |

---

## **2. S3 — Stockage et organisation**

### Créer un bucket S3
```bash
aws s3api create-bucket --bucket mon-bucket-data --region us-east-1 --create-bucket-configuration LocationConstraint=us-east-1
```

### Envoyer un fichier vers S3
```bash
aws s3 cp fichier.csv s3://mon-bucket-data/donnees/
```

### Télécharger un fichier depuis S3
```bash
aws s3 cp s3://mon-bucket-data/donnees/fichier.csv .
```

### Lister le contenu d’un dossier dans S3
```bash
aws s3 ls s3://mon-bucket-data/donnees/
```

---

## **3. Glue — Catalogue de données (métadonnées)**

### Créer une base de données Glue
```bash
aws glue create-database --database-input Name=demodb
```

### Créer une table Glue (définie dans un fichier JSON)
```bash
aws glue create-table --database-name demodb --table-input file://table.json
```

### Lister les bases Glue
```bash
aws glue get-databases
```

### Lister les tables d’une base Glue
```bash
aws glue get-tables --database-name demodb
```

---

## **4. Athena — Requêtes SQL sur S3**

### Exécuter une requête SQL
```bash
aws athena start-query-execution \
  --query-string "SELECT * FROM yellow LIMIT 10;" \
  --query-execution-context Database=demodb \
  --result-configuration OutputLocation=s3://mon-bucket-data/resultats/
```

### Créer une requête nommée avec CloudFormation
```bash
aws cloudformation create-stack \
--stack-name requete100 \
--template-body file://athenaquery.yml
```

### Lister les requêtes nommées
```bash
aws athena list-named-queries
```

### Obtenir le détail d'une requête nommée
```bash
aws athena get-named-query --named-query-id <ID>
```

---

## **5. Redshift — Entrepôt de données**

### Créer un cluster Redshift
```bash
aws redshift create-cluster \
--cluster-identifier demo-cluster \
--node-type dc2.large \
--master-username admin \
--master-user-password Password1234 \
--number-of-nodes 2
```

### Supprimer un cluster
```bash
aws redshift delete-cluster \
--cluster-identifier demo-cluster \
--skip-final-cluster-snapshot
```

---

## **6. Kinesis Data Streams — Streaming**

### Créer un flux de données
```bash
aws kinesis create-stream --stream-name flux-taxi --shard-count 1
```

### Envoyer un message dans le flux
```bash
aws kinesis put-record \
--stream-name flux-taxi \
--partition-key taxi001 \
--data "7,2023-11-01 08:00:00,34.5,CreditCard"
```

### Lire les données d’un shard (nécessite l’ID du shard)
```bash
aws kinesis get-shard-iterator \
--stream-name flux-taxi \
--shard-id shardId-000000000000 \
--shard-iterator-type TRIM_HORIZON
```

---

## **7. Kinesis Firehose — Livraison automatique**

### Créer un flux Firehose vers S3
```bash
aws firehose create-delivery-stream \
--delivery-stream-name firehose-taxi \
--s3-destination-configuration file://firehose-s3.json
```

**Exemple de fichier `firehose-s3.json` :**
```json
{
  "RoleARN": "arn:aws:iam::123456789012:role/firehose-role",
  "BucketARN": "arn:aws:s3:::mon-bucket-data",
  "Prefix": "firehose/",
  "BufferingHints": {
    "SizeInMBs": 5,
    "IntervalInSeconds": 300
  },
  "CompressionFormat": "GZIP"
}
```

### Lister les flux Firehose
```bash
aws firehose list-delivery-streams
```

---

## **8. EMR — Traitement massif Spark**

### Créer un cluster Spark
```bash
aws emr create-cluster \
--name "DemoSparkCluster" \
--release-label emr-6.13.0 \
--applications Name=Spark \
--instance-type m5.xlarge \
--instance-count 3 \
--use-default-roles
```

### Lister les clusters actifs
```bash
aws emr list-clusters --active
```

### Ajouter une tâche Spark à un cluster existant
```bash
aws emr add-steps \
--cluster-id j-XXXXXXXX \
--steps Type=Spark,Name="TP Spark",ActionOnFailure=CONTINUE,Args=[--class,org.apache.spark.examples.SparkPi,s3://bucket/scripts/pi.jar,10]
```

---

## **9. CloudFormation — Déploiement Infrastructure as Code**

### Valider un modèle
```bash
aws cloudformation validate-template --template-body file://monmodele.yml
```

### Déployer un modèle
```bash
aws cloudformation create-stack \
--stack-name datapipeline \
--template-body file://monmodele.yml
```

### Supprimer une pile
```bash
aws cloudformation delete-stack --stack-name datapipeline
```

---

## **10. Exercice final**

### Objectif :
Créer un pipeline complet :
- Stocker des données dans S3
- Les cataloguer avec Glue
- Les interroger avec Athena
- Les insérer dans Redshift
- Créer un modèle CloudFormation pour tout automatiser


