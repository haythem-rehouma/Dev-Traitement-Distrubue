# **Script complet — Pipeline S3 → Glue → Athena → Redshift (via CLI)**

**À exécuter depuis un terminal Cloud9 ou toute machine avec AWS CLI v2 configurée.**

---

## **Étape 0 — Variables d’environnement**

```bash
# Identifiants
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
REGION="us-east-1"

# Buckets et noms
BUCKET_NAME="pipeline-${ACCOUNT_ID}-data"
RESULTS_PATH="s3://${BUCKET_NAME}/results/"
DATASET_PATH="s3://${BUCKET_NAME}/datasets/"
GLUE_DB="taxidata"
GLUE_TABLE="yellow_taxi"
ATHENA_OUTPUT="${RESULTS_PATH}athena-output/"
```

---

## **Étape 1 — Créer un bucket S3**

```bash
aws s3api create-bucket \
  --bucket ${BUCKET_NAME} \
  --region ${REGION} \
  --create-bucket-configuration LocationConstraint=${REGION}
```

---

## **Étape 2 — Envoyer un fichier CSV de démonstration**

Créez un fichier local `yellow_taxi_sample.csv` :

```bash
cat > yellow_taxi_sample.csv <<EOF
vendor,pickup,dropoff,count,distance,ratecode,storeflag,pulocid,dolocid,paytype,fare,extra,mta_tax,tip,tolls,surcharge,total
VTS,2017-01-01 00:30:00,2017-01-01 00:45:00,1,3,1,0,100,150,1,12.5,0.5,0.5,2.0,0.0,0.0,15.5
EOF
```

Puis envoyez-le dans S3 :

```bash
aws s3 cp yellow_taxi_sample.csv ${DATASET_PATH}
```

---

## **Étape 3 — Créer une base de données Glue**

```bash
aws glue create-database \
  --database-input Name=${GLUE_DB}
```

---

## **Étape 4 — Définir une table Glue (schema CSV)**

Créez un fichier `table-definition.json` :

```bash
cat > table-definition.json <<EOF
{
  "Name": "${GLUE_TABLE}",
  "StorageDescriptor": {
    "Columns": [
      {"Name": "vendor", "Type": "string"},
      {"Name": "pickup", "Type": "timestamp"},
      {"Name": "dropoff", "Type": "timestamp"},
      {"Name": "count", "Type": "int"},
      {"Name": "distance", "Type": "int"},
      {"Name": "ratecode", "Type": "string"},
      {"Name": "storeflag", "Type": "string"},
      {"Name": "pulocid", "Type": "string"},
      {"Name": "dolocid", "Type": "string"},
      {"Name": "paytype", "Type": "string"},
      {"Name": "fare", "Type": "decimal"},
      {"Name": "extra", "Type": "decimal"},
      {"Name": "mta_tax", "Type": "decimal"},
      {"Name": "tip", "Type": "decimal"},
      {"Name": "tolls", "Type": "decimal"},
      {"Name": "surcharge", "Type": "decimal"},
      {"Name": "total", "Type": "decimal"}
    ],
    "Location": "${DATASET_PATH}",
    "InputFormat": "org.apache.hadoop.mapred.TextInputFormat",
    "OutputFormat": "org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat",
    "SerdeInfo": {
      "SerializationLibrary": "org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe",
      "Parameters": {
        "field.delim": ","
      }
    }
  },
  "TableType": "EXTERNAL_TABLE"
}
EOF
```

Créer la table :

```bash
aws glue create-table \
  --database-name ${GLUE_DB} \
  --table-input file://table-definition.json
```

---

## **Étape 5 — Requête Athena**

Créer la requête SQL dans un fichier :

```bash
cat > query.sql <<EOF
SELECT paytype, sum(total) as revenu_total
FROM ${GLUE_TABLE}
GROUP BY paytype;
EOF
```

Exécuter la requête :

```bash
aws athena start-query-execution \
  --query-string file://query.sql \
  --query-execution-context Database=${GLUE_DB} \
  --result-configuration OutputLocation=${ATHENA_OUTPUT}
```

---

## **Étape 6 — Charger les résultats dans Redshift**

> **Option 1** : vous créez une table Redshift et utilisez `COPY` depuis S3  
> **Option 2** : vous configurez Kinesis Firehose pour charger automatiquement dans Redshift  

*(exemples complets disponibles sur demande)*

---

## **Étape 7 — Nettoyage (optionnel)**

```bash
aws glue delete-table --database-name ${GLUE_DB} --name ${GLUE_TABLE}
aws glue delete-database --name ${GLUE_DB}
aws s3 rm s3://${BUCKET_NAME} --recursive
aws s3api delete-bucket --bucket ${BUCKET_NAME}
```


# Annexe 1 : chargement des résultats Athena dans Redshift avec COPY depuis S3




L’**étape 6 n’a pas été développée** est simplement parce qu’elle nécessite :

1. **Des paramètres précis de Redshift** (nom de la base de données, identifiants, rôle IAM pour le COPY, etc.)
2. Une configuration plus longue et sensible, notamment pour le rôle d'accès à S3 et la gestion des droits (`COPY` sur Redshift échoue si les permissions ne sont pas exactes)
3. Le choix entre deux approches très différentes (manuel via `COPY` ou automatisé via Firehose)



#  Étape 6 — Charger les résultats Athena dans Redshift (via `COPY` depuis S3)

### Objectif :
Transférer les résultats de requêtes Athena (stockés en CSV dans S3) vers une **table Redshift**.

---

## **Préparation : éléments requis**

- Un **cluster Redshift** actif
- Un **rôle IAM** avec accès S3 (et attaché au cluster Redshift)
- Le **bucket S3** où sont stockés les résultats Athena (`OutputLocation`)
- Le **fichier CSV** résultat d’une requête (Athena)
- Le **nom de la base de données Redshift**
- Le **groupe de sécurité** autorisant l’accès au port 5439 (si connexion externe)

---

## **1. Créer une table Redshift**

Supposons que la sortie Athena contient deux colonnes : `paytype` et `revenu_total`.

```sql
CREATE TABLE public.revenus_par_type (
  paytype VARCHAR(10),
  revenu_total DECIMAL(10,2)
);
```

Vous pouvez créer cette table :
- Via une session SQL dans la **console Redshift**  
- Ou en utilisant la CLI :
```bash
aws redshift-data execute-statement \
--cluster-identifier my-redshift-cluster \
--database mydb \
--db-user admin \
--sql "CREATE TABLE public.revenus_par_type (paytype VARCHAR(10), revenu_total DECIMAL(10,2));"
```

---

## **2. Vérifier le rôle IAM attaché à Redshift**

Ce rôle doit avoir cette policy minimale :

```json
{
  "Effect": "Allow",
  "Action": ["s3:GetObject", "s3:ListBucket"],
  "Resource": [
    "arn:aws:s3:::mon-bucket-data",
    "arn:aws:s3:::mon-bucket-data/*"
  ]
}
```

Et être attaché au cluster Redshift avec :

```bash
aws redshift associate-iam-roles \
--cluster-identifier my-redshift-cluster \
--iam-role-arns arn:aws:iam::<account-id>:role/MyRedshiftRole
```

---

## **3. Utiliser la commande `COPY`**

Déterminez le chemin du fichier CSV de résultats Athena :

```bash
RESULT_PATH="s3://mon-bucket-data/results/athena-output/xxxxxxxx.csv"
```

Puis exécutez la commande SQL COPY :

```bash
aws redshift-data execute-statement \
--cluster-identifier my-redshift-cluster \
--database mydb \
--db-user admin \
--sql "COPY revenus_par_type FROM '${RESULT_PATH}' IAM_ROLE 'arn:aws:iam::<account-id>:role/MyRedshiftRole' FORMAT AS CSV IGNOREHEADER 1;"
```

---

##  Résultat

Les résultats Athena sont maintenant **chargés dans la table Redshift `revenus_par_type`**. Vous pouvez faire :

```sql
SELECT * FROM revenus_par_type;
```


# Annexe 2 - Option 2 - Charger des données automatiquement dans Redshift avec **Kinesis Data Firehose**


## **Objectif**

Configurer un pipeline en temps réel où :
- Des enregistrements JSON ou CSV sont envoyés dans **Kinesis Firehose**
- Firehose les **transforme (optionnellement)** et les **insère dans Redshift**
- Le tout sans intervention manuelle : ingestion + transformation + livraison

---

## **Architecture du pipeline**

```
Application / Script CLI / Producteur
        │
        ▼
Kinesis Data Firehose (livraison automatique)
        │
        ├── [optionnel] Transformation avec Lambda
        │
        ▼
  Amazon Redshift (table cible)
```

---

## **1. Prérequis techniques**

Avant de commencer :

- Cluster Redshift actif (`--cluster-identifier`)
- Table cible dans Redshift
- Rôle IAM avec :
  - Permission **S3 (GetObject, PutObject)**
  - Permission **redshift:CopyFromS3**
- Bucket S3 temporaire pour Firehose
- Groupe de sécurité autorisant **le port 5439**
- Politique IAM attachée à Firehose

---

##  **2. Créer la table cible Redshift**

Exemple : données de trajets taxi

```sql
CREATE TABLE taxi_stream (
  vendor VARCHAR(10),
  pickup TIMESTAMP,
  distance DECIMAL(5,2),
  paytype VARCHAR(10),
  fare DECIMAL(5,2)
);
```

---

##  **3. Créer un bucket S3 pour le buffering Firehose**

```bash
aws s3api create-bucket \
--bucket firehose-buffer-taxi-$(aws sts get-caller-identity --query Account --output text) \
--region us-east-1 --create-bucket-configuration LocationConstraint=us-east-1
```

---

##  **4. Créer le rôle IAM pour Firehose**

Exemple : `firehose_redshift_role`

Il doit inclure une politique comme :

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:PutObject",
        "s3:GetObject",
        "s3:ListBucket"
      ],
      "Resource": [
        "arn:aws:s3:::firehose-buffer-taxi-*",
        "arn:aws:s3:::firehose-buffer-taxi-*/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "redshift:CopyFromS3",
        "redshift:DescribeClusters"
      ],
      "Resource": "*"
    }
  ]
}
```

---

##  **5. Créer le flux Firehose**

### Exemple : `taxi-firehose-redshift`

```bash
aws firehose create-delivery-stream \
--delivery-stream-name taxi-firehose-redshift \
--delivery-stream-type DirectPut \
--redshift-destination-configuration '{
  "RoleARN": "arn:aws:iam::<account-id>:role/firehose_redshift_role",
  "ClusterJDBCURL": "jdbc:redshift://<cluster-endpoint>:5439/<db-name>",
  "CopyCommand": {
    "DataTableName": "taxi_stream",
    "CopyOptions": "format as json 'auto'"
  },
  "Username": "admin",
  "Password": "StrongPass123",
  "S3Configuration": {
    "RoleARN": "arn:aws:iam::<account-id>:role/firehose_redshift_role",
    "BucketARN": "arn:aws:s3:::firehose-buffer-taxi-<account-id>",
    "Prefix": "buffer/",
    "CompressionFormat": "UNCOMPRESSED"
  }
}'
```

---

##  **6. Envoyer des données vers Firehose**

```bash
aws firehose put-record \
--delivery-stream-name taxi-firehose-redshift \
--record='{
  "Data":"{\"vendor\":\"VTS\",\"pickup\":\"2023-12-01T10:00:00\",\"distance\":3.4,\"paytype\":\"Credit\",\"fare\":18.75}\n"
}'
```

 La donnée **doit être encodée en base64 si binaire** et la chaîne JSON doit se terminer par `\n`.

---

##  **7. Vérifier la livraison dans Redshift**

Connectez-vous à Redshift (console, psql ou JDBC) :

```sql
SELECT * FROM taxi_stream;
```

Les lignes apparaîtront automatiquement au fur et à mesure que Firehose bufferise et exécute les `COPY`.

---

##  **8. Nettoyage**

```bash
aws firehose delete-delivery-stream \
--delivery-stream-name taxi-firehose-redshift
```

---

## Résumé des composants créés

| Ressource                     | Rôle                            |
|------------------------------|---------------------------------|
| `taxi-firehose-redshift`     | Pipeline automatique            |
| `taxi_stream` (Redshift)     | Table cible des trajets         |
| `firehose_redshift_role`     | IAM Role pour Firehose          |
| `firehose-buffer-taxi-*`     | Bucket tampon (obligatoire)     |
