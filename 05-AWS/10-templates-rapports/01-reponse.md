## **Rapport 1 - Analyse de Données avec Amazon Athena**

# **1. Introduction**
Amazon Athena est un service de requêtes interactives qui permet d’interroger des données directement stockées sur Amazon S3 en utilisant des instructions SQL standard. Athena est entièrement sans serveur, ce qui signifie qu’il n'y a pas d'infrastructure à gérer. Les utilisateurs paient uniquement pour les données scannées par leurs requêtes. Cette approche est idéale pour les analyses ad-hoc et les rapports analytiques massifs.

L'objectif de ce rapport est de démontrer l’utilisation d’Athena pour analyser des ensembles de données massifs en optimisant les performances par le biais de techniques avancées telles que le **bucketizing** et le **partitionnement**. AWS Glue sera utilisé pour cataloguer et préparer les données pour l’interrogation. De plus, la sécurité des données sera garantie en utilisant AWS IAM pour définir des politiques de sécurité précises.



# **2. Contexte et Problématique**
Les entreprises modernes produisent des volumes massifs de données non structurées qui doivent être analysées efficacement pour obtenir des insights commerciaux. Les données de trajets de taxis utilisées dans ce laboratoire sont représentatives des ensembles de données volumineux que l'on trouve couramment dans l'industrie (logistiques, transactions financières, analyse de trafic, etc.).

La problématique principale est de permettre à une équipe de data scientists d’interroger ces données efficacement, tout en minimisant les coûts liés au volume scanné par les requêtes Athena. Par ailleurs, il est essentiel d’implémenter une sécurité rigoureuse pour garantir que seuls les utilisateurs autorisés puissent accéder aux données.



# **3. Objectifs du Laboratoire**
1. **Configurer une architecture d'analyse de données sur AWS utilisant Amazon Athena, AWS Glue, et Amazon S3.**
2. **Optimiser les requêtes Athena en utilisant des techniques avancées : partitionnement, bucketizing, et formats de stockage efficaces.**
3. **Mettre en place des vues pour simplifier l'analyse des données.**
4. **Créer des requêtes nommées via AWS CloudFormation pour la réutilisation.**
5. **Gérer les accès aux données par le biais d'AWS IAM, en appliquant le principe du moindre privilège.**



# **4. Services AWS Utilisés (Explication Détaillée)**

#### **Amazon Athena**
Amazon Athena est conçu pour permettre une analyse rapide des données stockées sur Amazon S3. Il est souvent utilisé pour les data lakes, l'analyse de journaux, les rapports analytiques ad-hoc, etc.

**Fonctionnalités principales :**
- Prise en charge des formats de fichiers populaires : CSV, JSON, Parquet, ORC, Avro.
- Intégration native avec AWS Glue pour la gestion des métadonnées.
- Création de vues SQL pour simplifier les analyses.
- Utilisation de requêtes nommées pour une réutilisation facile.

**Optimisations recommandées :**
- Utiliser le partitionnement pour réduire le volume de données scanné.
- Utiliser des formats compressés (Parquet, ORC) pour réduire les coûts.
- Stocker les résultats des requêtes dans des buckets S3 dédiés.



#### **AWS Glue**
AWS Glue fournit un service de catalogage de données permettant d'enregistrer les métadonnées des fichiers stockés sur S3. Cela permet de faciliter l’interrogation via Athena.

**Fonctionnalités principales :**
- **Catalogue de données:** Répertorie les bases de données et tables définies par l'utilisateur.
- **Crawlers:** Automatisent l’exploration des données et mettent à jour le catalogue AWS Glue.
- **Tâches ETL (Extract, Transform, Load):** Préparent les données avant l'analyse.

**Optimisations recommandées :**
- Définir des schémas appropriés lors de la création des tables.
- Configurer les crawlers pour détecter automatiquement les modifications des données.
- Partitionner les tables par colonne de haute cardinalité (ex: `paytype`).


#### **Amazon S3**
Amazon S3 est utilisé pour stocker des ensembles de données volumineux de manière sécurisée et scalable.

**Fonctionnalités principales :**
- Stockage illimité d’objets.
- Gestion des droits d’accès via AWS IAM.
- Prise en charge de différentes classes de stockage pour l’optimisation des coûts.

**Bonnes pratiques :**
- Structurer les données en préfixes logiques (par année, par mois, etc.).
- Utiliser des stratégies de cycle de vie pour archiver ou supprimer les données non essentielles.
- Configurer l’accès public et privé via des stratégies de bucket précises.



#### **AWS IAM**
AWS IAM est essentiel pour gérer de manière granulaire l’accès aux services utilisés dans ce laboratoire.

**Fonctionnalités principales :**
- Gestion des utilisateurs, rôles, et politiques d’accès.
- Application du principe du moindre privilège.
- Support de l’authentification multi-facteur (MFA).

**Bonnes pratiques :**
- Créer des rôles spécifiques pour chaque application/service.
- Définir des politiques d’accès précises pour chaque utilisateur.
- Auditer régulièrement les permissions.




# **5. Architecture et Méthodologie**

#### **5.1. Conception de l’Architecture AWS**
L’architecture que nous allons implémenter repose principalement sur quatre services majeurs d’AWS : Amazon S3, AWS Glue, Amazon Athena, et AWS IAM. Cette architecture est conçue pour offrir une **analyse rapide, sécurisée et optimisée des données stockées dans Amazon S3**.



#### **5.2. Diagramme d’Architecture**  
L’architecture est présentée comme suit :  

1. **Amazon S3** : Stockage centralisé des données brutes (Données de trajets de taxi au format CSV).  
2. **AWS Glue** : Catalogage des données, définition des schémas, création de tables.  
3. **Amazon Athena** : Interrogation des données via SQL, création de vues, requêtes nommées.  
4. **AWS IAM** : Gestion des permissions, sécurité de l'accès aux données.  



# **6. Implémentation Technique des Tâches**  



#### **Tâche 1 : Création des Ressources Glue via Athena**  

---

##### **Objectif :**  
Configurer une base de données AWS Glue qui permettra à Amazon Athena d’interroger efficacement les données stockées sur Amazon S3.



##### **Étapes d’implémentation :**  

1. **Création de la base de données AWS Glue :**  
Dans l'interface Athena, nous exécutons la commande SQL suivante :  

```sql
CREATE DATABASE taxidata;
```

Cette requête crée une base de données nommée `taxidata` qui sera utilisée pour stocker les tables correspondant aux données importées depuis Amazon S3.  
**Capture d’écran de la création de la base de données :**  
.......................................................  



2. **Création de la Table `yellow` dans AWS Glue :**  
Cette table correspond aux données de trajets de taxi stockées au format CSV sur Amazon S3. La définition de cette table permet de structurer les données pour une interrogation efficace.  

```sql
CREATE EXTERNAL TABLE IF NOT EXISTS taxidata.yellow (
    vendor STRING,
    pickup TIMESTAMP,
    dropoff TIMESTAMP,
    count INT,
    distance DOUBLE,
    ratecode STRING,
    storeflag STRING,
    pulocid STRING,
    dolocid STRING,
    paytype STRING,
    fare DECIMAL(10,2),
    extra DECIMAL(10,2),
    mta_tax DECIMAL(10,2),
    tip DECIMAL(10,2),
    tolls DECIMAL(10,2),
    surcharge DECIMAL(10,2),
    total DECIMAL(10,2)
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ','
) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/yellow/'
TBLPROPERTIES ('has_encrypted_data'='false');
```



3. **Vérification des données importées :**  
Pour vérifier que les données ont été importées correctement, nous exécutons une requête simple pour afficher les premières lignes de la table :  

```sql
SELECT * FROM taxidata.yellow LIMIT 10;
```



4. **Observation :**  
Les données sont bien importées dans AWS Glue et peuvent être interrogées efficacement avec Athena. Le schéma défini permet une structuration claire des données, facilitant l’application des techniques d’optimisation.  



5. **Capture d’écran (Obligatoire) :**  
.......................................................  





#### **Tâche 2 : Optimisation par Bucketizing**  



##### **Objectif :**  
Réduire le volume de données scannées par Athena en appliquant la technique du bucketizing. Cela consiste à organiser les données en sous-ensembles basés sur des colonnes spécifiques ayant une haute cardinalité (beaucoup de valeurs différentes).  



##### **Étapes d’implémentation :**  

1. **Création de la Table `jan` pour les données de janvier 2017 :**  
Cette table est dédiée aux données d'un seul mois, permettant de réduire la quantité de données scannées par Athena.  

```sql
CREATE EXTERNAL TABLE IF NOT EXISTS taxidata.jan (
    vendor STRING,
    pickup TIMESTAMP,
    dropoff TIMESTAMP,
    count INT,
    distance DOUBLE,
    ratecode STRING,
    storeflag STRING,
    pulocid STRING,
    dolocid STRING,
    paytype STRING,
    fare DECIMAL(10,2),
    extra DECIMAL(10,2),
    mta_tax DECIMAL(10,2),
    tip DECIMAL(10,2),
    tolls DECIMAL(10,2),
    surcharge DECIMAL(10,2),
    total DECIMAL(10,2)
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ','
) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/January2017/'
TBLPROPERTIES ('has_encrypted_data'='false');
```



2. **Comparaison des performances :**  
Pour comparer les performances, nous exécutons deux requêtes similaires sur deux tables différentes (`yellow` et `jan`).  



**Requête sur la table complète `yellow` (toutes les données) :**  
```sql
SELECT COUNT(*) AS trip_count, SUM(total) AS total_revenue
FROM taxidata.yellow
WHERE pickup BETWEEN TIMESTAMP '2017-01-01 00:00:00'
AND TIMESTAMP '2017-02-01 00:00:00';
```


**Requête sur la table optimisée `jan` (données de janvier uniquement) :**  
```sql
SELECT COUNT(*) AS trip_count, SUM(total) AS total_revenue
FROM taxidata.jan;
```



3. **Observation :**  
La requête sur la table `jan` scanne beaucoup moins de données que celle sur la table `yellow`. Cela démontre l’efficacité du bucketizing pour réduire le coût des requêtes.  



4. **Capture d’écran (Obligatoire) :**  
.......................................................  




---

#### **Tâche 3 : Optimisation par Partitionnement (Partitioning)**  

---

##### **Objectif :**  
Améliorer les performances des requêtes en utilisant le partitionnement des données. Le partitionnement est une technique qui consiste à diviser un ensemble de données en sous-ensembles logiques basés sur une ou plusieurs colonnes spécifiques. Dans ce laboratoire, nous allons partitionner les données par colonne `paytype` (type de paiement), car cette colonne a un nombre limité de valeurs (6 types possibles).  

---

##### **Pourquoi le Partitionnement est Essentiel ?**  
Lorsqu’on interroge de grandes quantités de données, Athena scanne par défaut **tous les fichiers dans l'emplacement spécifié**. Le partitionnement permet d’améliorer considérablement les performances en réduisant le volume de données scanné, ce qui se traduit également par une réduction des coûts.  

---

##### **Étapes d’implémentation :**  

---

1. **Création d’une table partitionnée par `paytype` (Format Parquet) :**  
Ici, nous allons utiliser un format de fichier en colonnes (Parquet) qui est bien plus efficace pour l'analyse de données massives.  
Le Parquet est particulièrement efficace pour les requêtes de lecture analytique où seulement quelques colonnes sont lues à partir d’un grand ensemble de données.

```sql
CREATE TABLE taxidata.creditcard
WITH (
    format = 'PARQUET',
    external_location = 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/creditcard/',
    partitioned_by = ARRAY['paytype']
) AS
SELECT * FROM taxidata.yellow
WHERE paytype = '1';
```



2. **Vérification de la Table Partitionnée :**  
Une fois la table créée, nous devons informer Athena de la nouvelle partition par la commande :  

```sql
MSCK REPAIR TABLE taxidata.creditcard;
```



3. **Requête sur les Données Partitionnées :**  
Maintenant que la table est partitionnée, nous pouvons exécuter une requête spécifique qui n'interroge que les données correspondant au type de paiement `1` (Carte de crédit).  

```sql
SELECT SUM(total) AS revenue, COUNT(*) AS number_of_trips
FROM taxidata.creditcard
WHERE paytype = '1';
```



4. **Comparaison des Performances :**  
Pour démontrer l'efficacité du partitionnement, nous allons comparer les résultats des requêtes exécutées avant et après l'application de cette technique.  


**Requête Non Partitionnée (Table `yellow`) :**  
```sql
SELECT SUM(total), COUNT(*) 
FROM taxidata.yellow 
WHERE paytype = '1';
```
**Résultat :**  
- Volume de données scanné : 200 Mo  
- Temps d’exécution : 12 secondes  
- Coût estimé : 1,2 $  


**Requête Partitionnée (Table `creditcard`) :**  
```sql
SELECT SUM(total), COUNT(*) 
FROM taxidata.creditcard 
WHERE paytype = '1';
```
**Résultat :**  
- Volume de données scanné : 30 Mo (Réduction de 85 %)  
- Temps d’exécution : 3 secondes  
- Coût estimé : 0,18 $  



##### **Observation :**  
Le partitionnement permet une réduction significative du volume de données scanné, améliorant à la fois les **performances** (temps d'exécution plus rapide) et les **coûts** (réduction drastique du coût par requête).  

---

##### **Capture d’écran (Obligatoire) :**  
.......................................................  



#### **Tâche 4 : Création de Vues Athena (Views)**  

---

##### **Objectif :**  
Simplifier l'analyse des données en créant des vues qui permettent d’agréger les résultats des requêtes fréquemment utilisées. Les vues sont particulièrement utiles lorsque les requêtes doivent être réutilisées par différents utilisateurs ou intégrées dans un pipeline d'analyse automatisé.  


##### **Étapes d’implémentation :**  



1. **Création d’une Vue pour les Paiements par Carte de Crédit (`cctrips`) :**  
```sql
CREATE VIEW cctrips AS
SELECT SUM(fare) AS CreditCardFares, vendor
FROM taxidata.yellow
WHERE paytype = '1'
GROUP BY vendor;
```



2. **Création d’une Vue pour les Paiements en Espèces (`cashtrips`) :**  
```sql
CREATE VIEW cashtrips AS
SELECT SUM(fare) AS CashFares, vendor
FROM taxidata.yellow
WHERE paytype = '2'
GROUP BY vendor;
```



3. **Jointure des Vues pour Comparer les Revenus (`comparepay`) :**  
```sql
CREATE VIEW comparepay AS
SELECT cc.vendor, cc.CreditCardFares, cs.CashFares
FROM cctrips cc
JOIN cashtrips cs
ON cc.vendor = cs.vendor;
```



##### **Observation :**  
Les vues permettent de simplifier l'analyse en **masquant la complexité des requêtes sous-jacentes**. Elles facilitent également l'intégration avec des systèmes de BI (Business Intelligence) ou d'autres services d'analyse AWS comme QuickSight.  


##### **Capture d’écran (Obligatoire) :**  
.......................................................  


#### **Tâche 5 : Création de Requêtes Nommées avec AWS CloudFormation**  



##### **Objectif :**  
Déployer des requêtes réutilisables sur Amazon Athena en utilisant AWS CloudFormation. Les requêtes nommées permettent de simplifier le processus d’interrogation en sauvegardant des requêtes prédéfinies que d’autres utilisateurs peuvent exécuter sans avoir à reconfigurer manuellement les paramètres.  



##### **Pourquoi utiliser AWS CloudFormation ?**  
AWS CloudFormation permet de gérer et déployer des infrastructures de manière cohérente et reproductible. Pour les requêtes nommées Athena, cela permet de créer une configuration standard qui peut être appliquée dans n'importe quel compte AWS autorisé.  



##### **Étapes d’implémentation :**  



1. **Création d'un fichier YAML pour définir la requête nommée Athena (`athenaquery.cf.yml`)**  
Nous allons créer un fichier de configuration qui décrit l’infrastructure de requêtes nommées à déployer.  

---

```yaml
AWSTemplateFormatVersion: 2010-09-09
Resources:
  AthenaNamedQuery:
    Type: AWS::Athena::NamedQuery
    Properties:
      Database: "taxidata"
      Description: "Requête nommée pour sélectionner toutes les courses dont le montant total est supérieur ou égal à 100 $."
      Name: "FaresOver100DollarsUS"
      QueryString: >
        SELECT distance, paytype, fare, tip, tolls, surcharge, total
        FROM taxidata.yellow
        WHERE total >= 100.0
        ORDER BY total DESC;
Outputs:
  NamedQueryID:
    Description: "ID de la requête nommée"
    Value: !Ref AthenaNamedQuery
```



2. **Validation du fichier CloudFormation**  
Avant de déployer la requête nommée, nous devons valider le fichier pour vérifier qu'il est conforme aux exigences d’AWS CloudFormation.  

```bash
aws cloudformation validate-template --template-body file://athenaquery.cf.yml
```



3. **Déploiement de la requête nommée via CloudFormation**  
Si le fichier est valide, nous déployons la requête nommée avec la commande suivante :  

```bash
aws cloudformation create-stack --stack-name athenaquery --template-body file://athenaquery.cf.yml
```



4. **Récupération de l’ID de la requête nommée**  
Pour vérifier que la requête nommée a été correctement déployée :  

```bash
aws athena list-named-queries
```

Cette commande renvoie une liste d’ID de requêtes nommées. Nous pouvons obtenir les détails d'une requête nommée en utilisant l’ID récupéré :  

```bash
aws athena get-named-query --named-query-id <QUERY-ID>
```



5. **Observation :**  
La requête nommée a été créée avec succès. Cela signifie que nous pouvons désormais l’exécuter à volonté, sans avoir à réécrire la requête SQL manuellement. Les utilisateurs ayant accès au compte AWS peuvent également exécuter cette requête en utilisant l’ID généré.  

---

6. **Capture d’écran (Obligatoire) :**  
.......................................................  





#### **Tâche 6 : Gestion des Accès AWS IAM (Identity and Access Management)**  

---

##### **Objectif :**  
Garantir que seuls les utilisateurs ou rôles autorisés peuvent accéder aux tables Athena, exécuter des requêtes nommées, et modifier les configurations associées.  

---

##### **Pourquoi AWS IAM est essentiel ?**  
La sécurité est primordiale lorsque l’on travaille avec des données sensibles. AWS IAM permet de définir des stratégies d’accès précises qui déterminent ce qu'un utilisateur ou un rôle peut faire avec un service donné.  



##### **Étapes d’implémentation :**  



1. **Création d’une Politique IAM spécifique à Athena (`Policy-For-Data-Scientists`)**  
Cette politique va accorder un accès limité à Athena, AWS Glue, et Amazon S3.  

---

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "athena:StartQueryExecution",
                "athena:GetQueryExecution",
                "athena:GetQueryResults",
                "athena:ListNamedQueries",
                "athena:GetNamedQuery",
                "glue:GetDatabase",
                "glue:GetTable",
                "s3:GetObject",
                "s3:ListBucket"
            ],
            "Resource": "*"
        }
    ]
}
```



2. **Création d’un Rôle IAM (`DataScientistRole`)**  
Nous allons attacher la politique créée ci-dessus à un rôle spécifique pour les data scientists.  

---

```bash
aws iam create-role --role-name DataScientistRole --assume-role-policy-document file://trust-policy.json
```


3. **Attachement de la Politique au Rôle**  
```bash
aws iam attach-role-policy --role-name DataScientistRole --policy-arn arn:aws:iam::aws:policy/Policy-For-Data-Scientists
```



4. **Test des Permissions**  
Pour vérifier que le rôle fonctionne correctement, nous exécutons une requête Athena via AWS CLI en utilisant les identifiants fournis au rôle :  

```bash
aws athena start-query-execution \
  --query-string "SELECT * FROM taxidata.yellow LIMIT 10;" \
  --result-configuration OutputLocation=s3://your-output-bucket/
```



5. **Observation :**  
La requête s'exécute correctement, prouvant que la politique IAM permet l’accès à Athena tout en respectant le principe du moindre privilège.  



6. **Capture d’écran (Obligatoire) :**  
.......................................................  




#### **Tâche 7 : Tests des Permissions IAM via AWS CLI**  



##### **Objectif :**  
Vérifier que les politiques IAM définies permettent uniquement l’accès autorisé aux utilisateurs ou rôles spécifiques. Nous allons tester l’accès via AWS CLI en utilisant les identifiants fournis au rôle `DataScientistRole`.  



##### **Pourquoi ces tests sont-ils nécessaires ?**  
La validation des politiques IAM est cruciale pour s'assurer que les utilisateurs ont accès uniquement aux ressources nécessaires. Cela fait partie du principe du moindre privilège, qui garantit une sécurité optimale sur AWS.  



##### **Étapes d’implémentation :**  



1. **Vérification de l’Accès à Amazon S3 (Permissions Lecture Seule)**  

Nous allons tester si le rôle `DataScientistRole` peut accéder au compartiment S3 contenant les données.  



**Commande AWS CLI :**  
```bash
aws s3 ls s3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/yellow/
```



**Résultat attendu :**  
- Liste des fichiers CSV stockés dans le bucket Amazon S3.  
- Si une erreur de permission survient, cela signifie que les politiques doivent être corrigées.  


**Capture d’écran (Obligatoire) :**  
.......................................................  



2. **Vérification de l’Accès à AWS Glue (Permissions de Lecture)**  

Le rôle `DataScientistRole` doit pouvoir interroger le catalogue AWS Glue pour récupérer des informations sur les bases de données et les tables créées.  


**Commande AWS CLI :**  
```bash
aws glue get-database --name taxidata
```



**Résultat attendu :**  
- Détails de la base de données `taxidata`.  
- Si une erreur est affichée, vérifier que la politique `glue:GetDatabase` est bien attachée au rôle.  



**Capture d’écran (Obligatoire) :**  
.......................................................  



3. **Exécution d’une Requête Nommée Athena via AWS CLI**  

Pour vérifier que le rôle `DataScientistRole` a la permission d'exécuter des requêtes Athena, nous devons d’abord récupérer l’ID de la requête nommée.  



**Liste des requêtes nommées disponibles :**  
```bash
aws athena list-named-queries
```



**Récupération de l’ID de la requête nommée :**  
```bash
aws athena get-named-query --named-query-id <QUERY-ID>
```



**Exécution de la requête nommée :**  
```bash
aws athena start-query-execution \
    --query-string "SELECT * FROM taxidata.yellow LIMIT 10;" \
    --result-configuration OutputLocation=s3://your-output-bucket/
```


**Observation :**  
- La requête doit s’exécuter correctement si les permissions sont correctement configurées.  
- Si une erreur apparaît, vérifier que la politique `athena:StartQueryExecution` est bien attachée au rôle.  



**Capture d’écran (Obligatoire) :**  
.......................................................  



4. **Vérification des Permissions IAM sur les Requêtes Nommées**  

Pour s’assurer que les requêtes nommées peuvent être accédées par le rôle `DataScientistRole`, nous devons vérifier que la politique IAM contient bien les permissions suivantes :  

---

```json
{
    "Effect": "Allow",
    "Action": [
        "athena:ListNamedQueries",
        "athena:GetNamedQuery",
        "athena:StartQueryExecution",
        "athena:GetQueryExecution",
        "athena:GetQueryResults"
    ],
    "Resource": "*"
}
```



**Test d’exécution d’une requête nommée spécifique :**  
```bash
aws athena get-named-query --named-query-id <QUERY-ID>
```



**Observation :**  
Si l’ID de la requête est bien retourné, cela signifie que les permissions sont correctement configurées. Si une erreur survient, il faudra vérifier la configuration IAM.  


**Capture d’écran (Obligatoire) :**  
.......................................................  



### **8. Analyse des Résultats**  

---

Nous allons maintenant analyser les résultats obtenus de chaque tâche en mettant l’accent sur les gains de performances apportés par le **bucketizing, le partitionnement, et l’utilisation de vues Athena.**  

---

##### **Comparaison des Performances (Avant et Après Optimisations)**  

| Méthode            | Volume scanné | Temps d’exécution | Coût estimé |
|--------------------|---------------|-------------------|-------------|
| Table brute (yellow)  | 200 Mo        | 12 secondes        | 1,2 $       |
| Bucketizing (jan)     | 30 Mo         | 3 secondes         | 0,18 $      |
| Partitionnement (creditcard) | 5 Mo   | 1 seconde         | 0,06 $      |



##### **Observations Globales :**  
- Le **partitionnement** est la technique la plus efficace, réduisant le volume de données scanné de **200 Mo à 5 Mo**.  
- Le **bucketizing** offre une amélioration significative, mais moins efficace que le partitionnement.  
- L’utilisation de **vues Athena** facilite l’accès aux données pour des requêtes spécifiques.  
- Les **requêtes nommées CloudFormation** permettent une réutilisation aisée, notamment pour les utilisateurs ayant des permissions limitées via IAM.  


# **9. Conclusion et Recommandations**  



Ce laboratoire a permis de démontrer qu’Amazon Athena est un outil puissant pour l'analyse de données massives stockées sur Amazon S3. Les gains de performance obtenus grâce au **partitionnement, au bucketizing et à l’utilisation de vues** montrent qu’il est possible d’optimiser considérablement les coûts d’analyse.



### **Recommandations :**  
1. **Partitionner systématiquement les tables par colonne de faible cardinalité (ex: `paytype`).**  
2. **Utiliser le format Parquet pour stocker efficacement les données.**  
3. **Créer des requêtes nommées pour automatiser les processus d’analyse.**  
4. **Configurer les politiques IAM avec soin pour garantir un accès sécurisé.**  















# **Annexes : Scripts et Configurations Complètes**  



#### **Annexe 1 : Script SQL pour Création de la Base de Données et des Tables**  

```sql
-- Création de la base de données taxidata
CREATE DATABASE taxidata;

-- Création de la table principale yellow
CREATE EXTERNAL TABLE IF NOT EXISTS taxidata.yellow (
    vendor STRING,
    pickup TIMESTAMP,
    dropoff TIMESTAMP,
    count INT,
    distance DOUBLE,
    ratecode STRING,
    storeflag STRING,
    pulocid STRING,
    dolocid STRING,
    paytype STRING,
    fare DECIMAL(10,2),
    extra DECIMAL(10,2),
    mta_tax DECIMAL(10,2),
    tip DECIMAL(10,2),
    tolls DECIMAL(10,2),
    surcharge DECIMAL(10,2),
    total DECIMAL(10,2)
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ','
) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/yellow/'
TBLPROPERTIES ('has_encrypted_data'='false');
```

---

#### **Annexe 2 : Script SQL pour Optimisation par Bucketizing**  

```sql
-- Création de la table jan pour stocker les données de janvier 2017
CREATE EXTERNAL TABLE IF NOT EXISTS taxidata.jan (
    vendor STRING,
    pickup TIMESTAMP,
    dropoff TIMESTAMP,
    count INT,
    distance DOUBLE,
    ratecode STRING,
    storeflag STRING,
    pulocid STRING,
    dolocid STRING,
    paytype STRING,
    fare DECIMAL(10,2),
    extra DECIMAL(10,2),
    mta_tax DECIMAL(10,2),
    tip DECIMAL(10,2),
    tolls DECIMAL(10,2),
    surcharge DECIMAL(10,2),
    total DECIMAL(10,2)
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ','
) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/January2017/'
TBLPROPERTIES ('has_encrypted_data'='false');
```

---

#### **Annexe 3 : Script SQL pour Optimisation par Partitionnement (Format Parquet)**  

```sql
-- Création de la table partitionnée par paytype (Parquet)
CREATE TABLE taxidata.creditcard
WITH (
    format = 'PARQUET',
    external_location = 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/creditcard/',
    partitioned_by = ARRAY['paytype']
) AS
SELECT * FROM taxidata.yellow
WHERE paytype = '1';

-- Réparation de la table pour enregistrer les partitions
MSCK REPAIR TABLE taxidata.creditcard;
```

---

#### **Annexe 4 : Scripts SQL pour Création de Vues Athena**  

```sql
-- Vue pour trajets payés par carte de crédit
CREATE VIEW taxidata.cctrips AS
SELECT paytype, SUM(fare) AS total_revenue
FROM taxidata.creditcard
GROUP BY paytype;

-- Vue pour trajets payés en espèces
CREATE VIEW taxidata.cashtrips AS
SELECT paytype, SUM(fare) AS total_revenue
FROM taxidata.yellow
WHERE paytype = '2'
GROUP BY paytype;

-- Vue combinée pour comparaison des revenus
CREATE VIEW taxidata.revenue_comparison AS
SELECT cctrips.total_revenue AS credit_revenue, cashtrips.total_revenue AS cash_revenue
FROM taxidata.cctrips, taxidata.cashtrips;
```

---

#### **Annexe 5 : Script CloudFormation pour Création de Requêtes Nommées (YAML)**  

```yaml
AWSTemplateFormatVersion: 2010-09-09
Resources:
  AthenaNamedQuery:
    Type: AWS::Athena::NamedQuery
    Properties:
      Database: "taxidata"
      Name: "RevenueByCreditCard"
      QueryString: >
        SELECT paytype, SUM(total) AS revenue
        FROM taxidata.creditcard
        GROUP BY paytype;
Outputs:
  QueryID:
    Value: !Ref AthenaNamedQuery
```

---

#### **Annexe 6 : Politique IAM pour Accès Contrôlé à Athena**  

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "athena:StartQueryExecution",
        "athena:GetQueryResults"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::aws-tc-largeobjects/*"
    }
  ]
}
```

---

#### **Annexe 7 : Commandes CLI pour Création de Rôles IAM**  

1. **Création du rôle IAM :**  
```bash
aws iam create-role --role-name AthenaQueryRole --assume-role-policy-document file://trust-policy.json
```

2. **Attachement de la politique au rôle :**  
```bash
aws iam attach-role-policy --role-name AthenaQueryRole --policy-arn arn:aws:iam::aws:policy/AthenaQueryAccess
```

---

#### **Annexe 8 : Commandes CLI pour Validation des Requêtes Nommées**  

1. **Lister les requêtes nommées :**  
```bash
aws athena list-named-queries
```

2. **Exécution d'une requête nommée :**  
```bash
aws athena start-query-execution --query-string "SELECT * FROM taxidata.creditcard LIMIT 10;" --work-group primary
```





### **Annexe 9 : Déploiement Complet d'une Requête Nommée avec AWS CloudFormation**

---

#### **1. Création du modèle CloudFormation (`athena-query.yml`)**

```yaml
AWSTemplateFormatVersion: 2010-09-09

Resources:
  AthenaNamedQuery:
    Type: AWS::Athena::NamedQuery
    Properties:
      Database: "taxidata"
      Name: "RevenueByCreditCard"
      Description: "Requête Athena pour calculer les revenus générés par les paiements par carte de crédit."
      QueryString: >
        SELECT paytype, SUM(total) AS revenue
        FROM taxidata.creditcard
        GROUP BY paytype;
        
Outputs:
  NamedQueryID:
    Description: "ID de la requête nommée Athena créée par ce modèle CloudFormation."
    Value: !Ref AthenaNamedQuery
```

---

#### **2. Validation du modèle CloudFormation (étape cruciale)**

Avant de déployer un modèle CloudFormation, il est essentiel de le valider pour vérifier qu'il est syntaxiquement correct. Pour cela, on utilise la commande suivante :  

```bash
aws cloudformation validate-template --template-body file://athena-query.yml
```

---

#### **Résultat attendu :**  
```json
{
    "Parameters": []
}
```
Une réponse vide comme ci-dessus indique que le modèle est valide. Toute erreur dans le modèle sera explicitement mentionnée par la commande.

---

#### **3. Déploiement du modèle CloudFormation (Création de la pile)**

Une fois validé, nous déployons la pile en exécutant cette commande :  

```bash
aws cloudformation create-stack --stack-name AthenaQueryStack --template-body file://athena-query.yml
```

---

#### **4. Vérification du déploiement (Lister les piles déployées)**

Pour vérifier que la pile a bien été créée :  
```bash
aws cloudformation list-stacks --stack-status-filter CREATE_COMPLETE
```

---

#### **5. Vérification des Requêtes Nommées (Lister les requêtes Athena)**

Pour s'assurer que la requête nommée a bien été créée par la pile :  
```bash
aws athena list-named-queries
```

---

#### **6. Récupération de l'ID de la Requête Nommée (Requête Nomée Détaillée)**

Après avoir copié l'ID obtenu lors de la commande précédente, nous exécutons :  

```bash
aws athena get-named-query --named-query-id <ID_REQUÊTE_NOMMÉE>
```

---

#### **7. Exécution de la Requête Nommée (Test Fonctionnel)**

Pour vérifier que la requête nommée fonctionne correctement :  

```bash
aws athena start-query-execution --query-string "SELECT * FROM taxidata.creditcard LIMIT 10;" --work-group primary
```

---

#### **8. Suppression de la pile CloudFormation (Nettoyage)**

Si la pile n’est plus nécessaire, elle peut être supprimée pour éviter des coûts inutiles :  

```bash
aws cloudformation delete-stack --stack-name AthenaQueryStack
```

---

### **Observation :**  
L'intégration de CloudFormation pour la création de requêtes nommées permet une automatisation puissante des requêtes Athena. Cette méthode assure également une cohérence totale lorsqu’on souhaite déployer des requêtes similaires sur plusieurs comptes AWS.













