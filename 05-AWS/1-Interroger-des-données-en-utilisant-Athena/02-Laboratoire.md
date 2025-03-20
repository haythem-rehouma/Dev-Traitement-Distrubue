--------
# Laboratoire : Interrogation des données à l'aide d'Athena  
-----------

# Vue d'ensemble et objectifs du laboratoire

Sofía est satisfaite de la preuve de concept (POC) que vous avez créée pour Amazon Simple Storage Service (Amazon S3), et l'équipe apprend à l'utiliser. Cependant, une des scientifiques des données, Mary, souhaite effectuer des requêtes plus avancées sur les données au format CSV (valeurs séparées par des virgules). Elle travaille souvent avec de grands ensembles de données stockées dans plusieurs fichiers CSV. Elle aimerait pouvoir interroger plusieurs fichiers dans le même compartiment S3 et agréger les données de ces fichiers dans la même base de données. Elle veut également être capable de transformer les métadonnées du jeu de données, comme les noms des colonnes et les déclarations de type de données. Ces informations font partie du schéma du jeu de données lorsqu'elles sont stockées sous forme de base de données relationnelle.

Après quelques recherches, vous découvrez qu'Athena d'Amazon fournit cette fonctionnalité. Vous explorez Athena et ses capacités pour voir si vous pouvez répondre aux besoins de Mary. Athena est un service de requêtes interactives que vous pouvez utiliser pour interroger des données stockées dans Amazon S3. Athena stocke des informations sur les sources de données que vous interrogez. Vous pouvez également stocker vos requêtes pour les réutiliser et les partager avec d'autres utilisateurs.

Dans ce laboratoire, vous apprendrez à utiliser Athena et AWS Glue pour interroger des données stockées dans Amazon S3.

# À la fin de ce laboratoire, vous devriez être capable de :

- Utiliser l'éditeur de requêtes Athena pour créer une base de données et une table AWS Glue.
- Définir le schéma de la base de données AWS Glue et des tables associées en utilisant la fonctionnalité Athena d'ajout en bloc de colonnes.
- Configurer Athena pour utiliser un jeu de données situé dans Amazon S3.
- Optimiser les requêtes Athena sur un jeu de données d'exemple.
- Créer des vues dans Athena pour simplifier l'analyse des données pour d'autres utilisateurs.
- Créer des requêtes nommées Athena en utilisant AWS CloudFormation.
- Examiner une politique IAM (Identity and Access Management) qui peut être attribuée aux utilisateurs souhaitant utiliser des requêtes nommées Athena.
- Confirmer qu'un utilisateur peut accéder à une requête nommée Athena en utilisant l'interface en ligne de commande AWS (CLI AWS) dans le terminal AWS Cloud9.

# Durée du laboratoire

Ce laboratoire prendra environ **90 minutes** à compléter.

## Scénario

Dans ce laboratoire, vous jouez le rôle d'un membre de l'équipe de data science. Vous allez construire une POC en utilisant AWS Glue et Athena pour analyser les données stockées dans Amazon S3. Vous souhaitez expérimenter avec Athena en accédant aux données brutes dans un compartiment S3, en construisant une base de données AWS Glue, et en interrogeant cette base de données avec Athena.

Vous souhaitez également déterminer si cette solution peut être mise à l'échelle afin que d'autres membres de l'équipe puissent y accéder. Mary fait partie du groupe IAM dans AWS pour l'équipe de data science et dispose d'un accès similaire au compartiment S3, à la base de données AWS Glue, et à Athena. Cependant, vous avez limité son accès afin d'éviter la création de ressources non nécessaires, suivant ainsi le principe du moindre privilège, où un administrateur limite l'accès aux services et ressources AWS en fonction des permissions nécessaires pour effectuer une tâche ou un travail spécifique. Dans une tâche, vous utiliserez un utilisateur IAM pour tester la capacité d'un membre de l'équipe de data science à exécuter des requêtes gérées par Athena. Cet utilisateur IAM a été créé pour vous et appartient à un groupe IAM avec une politique attachée qui définit les permissions.

Vous demandez à Mary si elle dispose de quelques jeux de données d'exemple que vous pouvez utiliser pour vos expériences. Mary vous donne accès à un jeu de données contenant les données de trajets de taxis pour 2017. Vous utiliserez ces données pour votre POC.

Le diagramme suivant illustre l'environnement qui a été créé pour vous dans AWS au début du laboratoire.

![image](https://github.com/user-attachments/assets/34b88fa1-e2c3-4a0d-b5f8-fcf95ab5f001)


```
┌────────────────────┬─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ TÂCHE NUMÉROTÉE    │ DÉTAIL                                                                                                                         │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 1                  │ Vous utiliserez les étapes de configuration et les requêtes d'Athena pour créer une base de données AWS Glue avec les données   │
│                    │ du jeu de données de taxi.                                                                                                      │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 2                  │ Vous utiliserez des buckets pour analyser les données de taxi pour janvier. Note : le jeu de données original contient des      │
│                    │ données pour tous les mois dans le même fichier. Les données de janvier ont été extraites du jeu de données original dans un    │
│                    │ fichier séparé pour optimiser vos requêtes Athena.                                                                              │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 3                  │ Vous utiliserez Athena pour analyser les données de taxi en utilisant **paytype** pour partitionner les données.                │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 4                  │ Vous utiliserez des vues Athena pour calculer la valeur totale des tarifs payés par carte de crédit ou en espèces.               │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 5                  │ Vous compresserez le fichier en utilisant les formats **.zip** et **.gz**, puis comparerez la taille des fichiers résultants.   │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 6                  │ Vous examinerez la politique IAM appelée **Policy-For-Data-Scientists**.                                                        │
├────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│ 7                  │ Mary a les permissions de cette politique car elle fait partie du groupe IAM auquel la politique est assignée. Vous utiliserez  │
│                    │ cette politique pour tester son accès au jeu de données et sa capacité à modifier les propriétés des fichiers d'origine dans     │
│                    │ Amazon S3.                                                                                                                      │
└────────────────────┴─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

---

# Version 1.0.20 - Interroger des données avec Athena

### Présentation et objectifs du laboratoire

Sofía est satisfaite de la preuve de concept (POC) que vous avez créée pour Amazon Simple Storage Service (Amazon S3) et l'équipe commence à l'utiliser. Cependant, l'une des scientifiques de données, Mary, souhaite effectuer des requêtes plus avancées sur des données en valeurs séparées par des virgules (CSV). Elle travaille souvent avec de grands ensembles de données stockées dans plusieurs fichiers CSV. Elle aimerait pouvoir interroger plusieurs fichiers dans le même compartiment S3 et agréger les données de ces fichiers dans une même base de données. Elle souhaite également pouvoir transformer les métadonnées des ensembles de données, telles que les noms de colonnes et les déclarations de type de données.

Après avoir effectué quelques recherches, vous découvrez qu'Amazon Athena offre cette fonctionnalité. Vous explorez Athena et ses capacités pour voir si vous pouvez répondre aux besoins de Mary. Athena est un service de requêtes interactives permettant d'interroger des données stockées dans Amazon S3. Athena stocke des informations sur les sources de données que vous interrogez. Vous pouvez stocker vos requêtes pour une réutilisation et les partager avec d'autres utilisateurs.

Dans ce laboratoire, vous apprendrez à utiliser Athena et AWS Glue pour interroger des données stockées dans Amazon S3.

**À la fin de ce laboratoire, vous serez capable de :**

- Utiliser l'éditeur de requêtes Athena pour créer une base de données et une table AWS Glue.
- Définir le schéma de la base de données AWS Glue et des tables associées à l'aide de la fonctionnalité d'ajout en masse de colonnes dans Athena.
- Configurer Athena pour utiliser un ensemble de données situé dans Amazon S3.
- Optimiser les requêtes Athena contre un ensemble de données d'exemple.
- Créer des vues dans Athena pour simplifier l'analyse des données pour d'autres utilisateurs.
- Créer des requêtes nommées Athena en utilisant AWS CloudFormation.
- Examiner une politique AWS Identity and Access Management (IAM) qui peut être affectée aux utilisateurs qui souhaitent utiliser des requêtes nommées Athena.
- Confirmer qu'un utilisateur peut accéder à une requête nommée Athena à l'aide de l'interface de ligne de commande AWS (AWS CLI) dans le terminal AWS Cloud9.

### Durée
Ce laboratoire nécessite environ **90 minutes** pour être complété.

### Restrictions des services AWS

Dans cet environnement de laboratoire, l'accès aux services AWS et aux actions de service peut être limité à ceux nécessaires pour compléter les instructions du laboratoire. Vous pourriez rencontrer des erreurs si vous tentez d'accéder à d'autres services ou d'effectuer des actions au-delà de celles décrites dans ce laboratoire.

### Scénario

Dans ce laboratoire, vous assumerez le rôle d'un membre de l'équipe de data science. Vous allez construire une POC utilisant AWS Glue et Athena pour analyser des données stockées dans Amazon S3. Vous souhaitez expérimenter avec Athena en accédant aux données brutes dans un compartiment S3, en construisant une base de données AWS Glue et en interrogeant cette base de données à l'aide d'Athena.

Vous souhaitez également déterminer si vous pouvez faire évoluer cette solution pour que d'autres membres de l'équipe y aient accès. Mary fait partie du groupe IAM dans AWS pour l'équipe de data science et a un accès similaire au compartiment S3, à la base de données AWS Glue et à Athena. Cependant, vous avez limité son accès pour éviter la création inutile de ressources. Cela suit le principe du moindre privilège, où un administrateur limite l'accès aux services AWS et aux ressources en fonction des autorisations nécessaires pour exécuter une tâche spécifique. Dans une des tâches, vous utiliserez un utilisateur IAM pour tester la capacité d'un membre de l'équipe de data science à exécuter des requêtes gérées par Athena. L'utilisateur IAM a été créé pour vous et appartient à un groupe IAM auquel est attachée une politique définissant les autorisations.

Mary vous fournit un ensemble de données contenant des données sur les trajets en taxi pour l'année 2017 que vous utiliserez pour votre POC.

---

# Tâche 1 : Créer et interroger une base de données et une table AWS Glue dans Athena

Votre première tâche consiste à utiliser des instructions SQL pour définir un schéma pour une table permettant de travailler avec les données CSV d'exemple.

Dans cette tâche, vous allez faire les actions suivantes :

- Spécifier un compartiment S3 pour les résultats des requêtes.
- Créer une base de données AWS Glue en utilisant l'éditeur de requêtes Athena.
- Créer une table dans la base de données AWS Glue et importer les données.
- Prévisualiser les données dans la table AWS Glue.

### Instructions

1. **Ouvrir l'éditeur Athena :**
   - Dans la console de gestion AWS, dans la barre de recherche située à droite de **Services**, cherchez et sélectionnez **Athena**.
   
2. **Spécifier un compartiment S3 pour les résultats des requêtes :**
   - Lorsque vous utilisez Athena, vous devez d'abord spécifier un compartiment S3 pour contenir les résultats des requêtes que vous exécutez. Un compartiment a été créé pour vous.
   - Dans la console Athena, choisissez **Explorer l'éditeur de requêtes**.
   - Cliquez sur l'onglet **Paramètres**.
   - Dans la section **Paramètres des résultats de requête et de chiffrement**, sélectionnez **Gérer**.
   - Pour l'option **Emplacement des résultats de la requête**, sélectionnez **Parcourir S3**.
   - Choisissez le compartiment qui a été créé pour vous, puis sélectionnez **Choisir**.
   - Conservez les paramètres par défaut pour les autres options et sélectionnez **Enregistrer**.

3. **Créer une base de données AWS Glue en utilisant l'éditeur de requêtes Athena :**
   - Revenez à l'onglet **Éditeur**.
   - Dans la section **Requête 1**, entrez la commande SQL suivante :
   ```sql
   CREATE DATABASE taxidata;
   ```
   - Choisissez **Exécuter**.
   - Le message **Requête réussie** s'affiche et une base de données AWS Glue nommée `taxidata` est créée.

4. **Créer une table dans la base de données AWS Glue et importer des données :**
   - Dans la console Athena, dans la section **Tables et vues** à gauche, sélectionnez **Créer > Données de compartiment S3** et configurez les options suivantes :
     - **Nom de la table** : `yellow`
     - **Description** : Table des données de taxi
     - **Configuration de la base de données** : Sélectionnez **Choisir une base de données existante**, puis choisissez `taxidata` dans la liste déroulante.
     - **Emplacement de l'ensemble de données** : Copiez et collez ce lien :
       ```text
       s3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/yellow/
       ```
     - **Chiffrement** : Conservez le paramètre par défaut (non sélectionné).
     - **Format de données** : Choisissez **CSV**.
     - Dans la section **Détails des colonnes**, sélectionnez **Ajouter des colonnes en masse**.

5. **Ajouter les colonnes et types de données à la table :**
   - Dans la boîte de dialogue qui s'affiche, copiez et collez le texte suivant, puis sélectionnez **Ajouter** :
   ```text
   vendor string,
   pickup timestamp,
   dropoff timestamp,
   count int,
   distance int,
   ratecode string,
   storeflag string,
   pulocid string,
   dolocid string,
   paytype string,
   fare decimal,
   extra decimal,
   mta_tax decimal,
   tip decimal,
   tolls decimal,
   surcharge decimal,
   total decimal
   ```

6. **Finaliser la création de la table :**
   - Vérifiez le texte de la requête de création de table dans la section **Aperçu de la requête de table**, qui devrait correspondre au texte suivant :
   ```sql
   CREATE EXTERNAL TABLE IF NOT EXISTS taxidata.yellow (
      `vendor` string,
      `pickup` timestamp,
      `dropoff` timestamp,
      `count` int,
      `distance` int,
      `ratecode` string,
      `storeflag` string,
      `pulocid` string,
      `dolocid` string,
      `paytype` string,
      `fare` decimal,
      `extra` decimal,
      `mta_tax` decimal,
      `tip` decimal,
      `tolls` decimal,
      `surcharge` decimal,
      `total` decimal
   )
   ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
   WITH SERDEPROPERTIES (
      'serialization.format' = ',',
      'field.delim' = ','
   ) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/yellow/'
   TBLPROPERTIES ('has_encrypted_data'='false');
   ```

7. **Prévisualiser les données dans la table AWS Glue :**
   - Dans la section **Données** à gauche, pour **Base de données**, choisissez `taxidata`.
   - Dans la section **Tables**, à droite de la table `yellow`, choisissez l'icône à trois points, puis sélectionnez **Prévisualiser la table**.
   - Les 10 premiers enregistrements de la table s'affichent.






---------
--------
---------


# Tâche 2 : Optimiser les requêtes Athena en utilisant des buckets

Lorsque vous travaillez avec de grands ensembles de données répartis sur plusieurs fichiers, deux objectifs principaux sont d'optimiser les performances des requêtes et de minimiser les coûts. Le coût d'Athena est basé sur l'utilisation, c'est-à-dire la quantité de données scannées, et les prix varient en fonction de votre région.

Trois stratégies possibles peuvent être utilisées pour minimiser vos coûts et améliorer les performances :

1. **Compression des données** : Compressez vos données à l'aide de standards ouverts pour la compression de fichiers (comme Gzip ou tar). La compression entraîne une taille plus petite pour l'ensemble de données lorsqu'il est stocké dans Amazon S3.
   
2. **Bucketiser les données** : Pour les données à haute cardinalité (nombre élevé de valeurs uniques dans un champ spécifique), stockez les enregistrements dans des **buckets** distincts (à ne pas confondre avec les compartiments S3) en fonction d'une valeur partagée dans un champ spécifique. Le **bucketizing** est considéré comme une étape de prétraitement dans le pipeline de données.

3. **Partitionner les données** : Cette stratégie est utilisée pour les données à faible cardinalité, c'est-à-dire que les champs ont peu de valeurs uniques. Le partitionnement améliore également les performances et réduit les coûts.

Dans cette tâche, vous allez expérimenter le **bucketizing** des données pour optimiser les requêtes Athena.

**Étapes à suivre :**

1. Créez une table nommée `jan` pour contenir les données bucketisées de janvier 2017.
2. Comparez le temps nécessaire pour exécuter une requête sur les données bucketisées de janvier 2017 par rapport à l'ensemble complet des données de janvier 2017.

#### Instructions

1. **Créer une table pour les données de janvier 2017 :**
   - Dans l'éditeur de requêtes Athena, choisissez l'onglet **Éditeur**.
   - Pour ouvrir un nouvel onglet de requête, choisissez l'icône plus sur la droite de la section des requêtes.
   - Copiez et collez le texte suivant dans la nouvelle requête, puis choisissez **Exécuter** :
   ```sql
   CREATE EXTERNAL TABLE IF NOT EXISTS jan (
     `vendor` string,
     `pickup` timestamp,
     `dropoff` timestamp,
     `count` int,
     `distance` int,
     `ratecode` string,
     `storeflag` string,
     `pulocid` string,
     `dolocid` string,
     `paytype` string,
     `fare` decimal,
     `extra` decimal,
     `mta_tax` decimal,
     `tip` decimal,
     `tolls` decimal,
     `surcharge` decimal,
     `total` decimal
   )
   ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
   WITH SERDEPROPERTIES (
     'serialization.format' = ',',
     'field.delim' = ','
   ) LOCATION 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab2/January2017/'
   TBLPROPERTIES ('has_encrypted_data'='false');
   ```

2. **Exécuter une requête sur la table `yellow` pour les données complètes de 2017 :**
   - Copiez et exécutez la requête suivante sur la table `yellow`, qui contient les données de toute l'année :
   ```sql
   SELECT count(count) AS "Nombre de trajets",
          sum(total) AS "Total des revenus",
          pickup AS "Date du trajet"
   FROM yellow
   WHERE pickup BETWEEN TIMESTAMP '2017-01-01 00:00:00'
                    AND TIMESTAMP '2017-02-01 00:00:01'
   GROUP BY pickup;
   ```
   - Notez le **temps d'exécution** et la **quantité de données scannées** pour cette requête.

3. **Exécuter une requête sur la table `jan` pour les données bucketisées de janvier 2017 :**
   - Exécutez la requête suivante sur la table `jan` :
   ```sql
   SELECT count(count) AS "Nombre de trajets",
          sum(total) AS "Total des revenus",
          pickup AS "Date du trajet"
   FROM jan
   GROUP BY pickup;
   ```
   - Notez le **temps d'exécution** et la **quantité de données scannées** pour cette requête.

#### Résultats et Analyse :
- Vous devriez remarquer que beaucoup moins de données sont scannées lorsque la requête est exécutée sur la table bucketisée `jan`.
- **Analyse** : Le **bucketizing** des données est une stratégie efficace lorsque vous avez des données avec une haute cardinalité (beaucoup de valeurs uniques dans un champ).

#### Résumé de la tâche 2

Dans cette tâche, vous avez créé une table pour les données de janvier 2017 et optimisé les requêtes à l'aide du **bucketizing**. Vous avez comparé le temps d'exécution et les données scannées entre une requête exécutée sur des données non bucketisées et une requête sur des données bucketisées.

---

# Tâche 3 : Optimiser les requêtes Athena en utilisant des partitions

Si vous interrogez un champ avec une faible cardinalité (c'est-à-dire peu de valeurs uniques), il est préférable de partitionner les données plutôt que d'utiliser des **buckets**. Dans certains cas, les données peuvent déjà être partitionnées par un autre processus.

Dans cette tâche, vous allez utiliser le champ `paytype` (type de paiement) pour créer des partitions. Les valeurs possibles pour le champ `paytype` sont les suivantes :

- 1 = Carte de crédit
- 2 = Espèces
- 3 = Pas de charge
- 4 = Litige
- 5 = Inconnu
- 6 = Trajet annulé

Comme les valeurs possibles sont limitées, `paytype` est un excellent champ pour partitionner les données.

Vous allez également utiliser un format de stockage en colonnes (comme **Parquet**) pour stocker les données. Le format en colonnes permet de compresser les données, ce qui réduit encore les coûts de vos requêtes.

**Étapes à suivre :**

1. Créez une table en utilisant le format Parquet et partitionnée par `paytype`.
2. Comparez le temps nécessaire pour exécuter une requête sur la table `yellow` et sur la nouvelle table partitionnée.

#### Instructions

1. **Créer une table partitionnée par `paytype` :**
   - Créez une nouvelle table appelée `creditcard` partitionnée pour `paytype = 1` (transactions par carte de crédit) en exécutant la requête suivante :
   ```sql
   CREATE TABLE taxidata.creditcard
   WITH (
     format = 'PARQUET'
   ) AS
   SELECT * FROM yellow
   WHERE paytype = '1';
   ```

2. **Comparer les performances des requêtes sur les données partitionnées et non partitionnées :**
   - Exécutez cette requête sur la table `yellow` pour les données non partitionnées :
   ```sql
   SELECT sum(total), paytype FROM yellow
   WHERE paytype = '1' GROUP BY paytype;
   ```
   - Exécutez la même requête sur la table `creditcard` (partitionnée) :
   ```sql
   SELECT sum(total), paytype FROM creditcard
   WHERE paytype = '1' GROUP BY paytype;
   ```

#### Analyse :
- Vous remarquerez que la requête sur les données partitionnées scanne beaucoup moins de données, ce qui réduit le coût de la requête.

#### Résumé de la tâche 3

Dans cette tâche, vous avez partitionné les données en fonction du champ `paytype`. Vous avez comparé les performances des requêtes sur des données partitionnées et non partitionnées, et vous avez constaté une amélioration des performances et une réduction des coûts.


---------
--------
---------


# Tâche 4 : Utilisation des vues dans Athena

Athena permet de créer des vues. L'utilisation de vues sur des données peut simplifier l'analyse en masquant la complexité des requêtes aux utilisateurs. Bien qu'Athena n'autorise l'exécution que d'une seule instruction SQL à la fois, vous pouvez utiliser des vues pour combiner des données provenant de plusieurs tables. De plus, vous pouvez optimiser les performances des requêtes en expérimentant différentes manières de récupérer les données, puis enregistrer la meilleure requête sous forme de vue.

Dans cette tâche, vous allez :

- Créer une vue pour calculer la valeur totale des courses payées par carte de crédit.
- Créer une vue pour calculer la valeur totale des courses payées en espèces.
- Récupérer toutes les données de chacune de ces vues.
- Créer une nouvelle vue qui joint les données provenant de ces deux vues.
- Prévisualiser les résultats de la jointure.

#### Instructions

1. **Créer une vue pour les courses payées par carte de crédit :**
   - Exécutez la requête suivante pour créer une vue nommée `cctrips`, qui calcule le total des revenus pour les trajets payés par carte de crédit :
   ```sql
   CREATE VIEW cctrips AS
     SELECT sum(fare) AS "CreditCardFares"
     FROM yellow
     WHERE paytype = '1';
   ```

2. **Créer une vue pour les courses payées en espèces :**
   - Exécutez cette requête pour créer une vue nommée `cashtrips`, qui calcule le total des revenus pour les trajets payés en espèces :
   ```sql
   CREATE VIEW cashtrips AS
     SELECT sum(fare) AS "CashFares"
     FROM yellow
     WHERE paytype = '2';
   ```

3. **Sélectionner toutes les données des vues `cctrips` et `cashtrips` :**
   - Exécutez cette requête pour récupérer toutes les données de la vue `cctrips` :
   ```sql
   SELECT * FROM cctrips;
   ```
   - Exécutez cette requête pour récupérer toutes les données de la vue `cashtrips` :
   ```sql
   SELECT * FROM cashtrips;
   ```

4. **Créer une vue qui joint les données des deux vues :**
   - Vous allez maintenant créer une nouvelle vue qui joint les données de `cctrips` et `cashtrips` afin de comparer les revenus entre les paiements par carte de crédit et en espèces pour deux vendeurs différents. Exécutez la requête suivante :
   ```sql
   CREATE VIEW comparepay AS
   WITH
     cc AS (
       SELECT sum(fare) AS cctotal, vendor
       FROM yellow
       WHERE paytype = '1'
       GROUP BY paytype, vendor),
     cs AS (
       SELECT sum(fare) AS cashtotal, vendor
       FROM yellow
       WHERE paytype = '2'
       GROUP BY paytype, vendor)
   SELECT cc.cctotal, cs.cashtotal
   FROM cc
   JOIN cs
   ON cc.vendor = cs.vendor;
   ```

5. **Prévisualiser les résultats de la jointure :**
   - Dans la section **Vues** à gauche, à droite de la vue `comparepay`, cliquez sur l'icône avec les trois points, puis choisissez **Prévisualiser la vue**. Les résultats s'affichent, présentant les totaux des revenus par carte de crédit et en espèces pour chaque vendeur.

#### Résultats et Analyse :
- Vous avez maintenant une vue qui compare les revenus entre les paiements par carte de crédit et en espèces pour différents vendeurs.

#### Résumé de la tâche 4

Dans cette tâche, vous avez appris à créer des vues dans Athena pour simplifier l'analyse des données. Vous avez créé deux vues pour calculer les revenus totaux des trajets payés par carte de crédit et en espèces. Vous avez également appris à utiliser une vue pour joindre les données provenant de deux autres vues.

---

# Tâche 5 : Créer des requêtes nommées Athena avec AWS CloudFormation

L'équipe de data science souhaite partager les requêtes qu'elle a créées en utilisant le jeu de données des taxis. L'équipe veut les partager avec d'autres départements, mais ces départements utilisent d'autres comptes AWS. Ces autres départements ont moins d'expérience avec AWS, donc l'équipe de data science souhaite simplifier l'utilisation d'Athena pour ces départements.

Dans cette tâche, vous allez créer un modèle **AWS CloudFormation** pour créer une requête nommée dans Athena, qui pourra être réutilisée et partagée avec d'autres départements.

#### Instructions

1. **Examiner et exécuter la requête exemple fournie par Mary :**
   - Mary vous a fourni la requête suivante à utiliser avec le jeu de données `yellow` :
   ```sql
   SELECT distance, paytype, fare, tip, tolls, surcharge, total
   FROM yellow
   WHERE total >= 100.0
   ORDER BY total DESC;
   ```
   - Exécutez cette requête dans l'éditeur de requêtes Athena et vérifiez que les résultats s'affichent correctement. Les trajets les plus chers doivent apparaître en premier.

2. **Créer un modèle CloudFormation :**
   - Ouvrez AWS Cloud9 à partir de la console AWS.
   - Créez un nouveau fichier dans AWS Cloud9 et enregistrez-le sous le nom `athenaquery.cf.yml`.
   - Copiez et collez le code suivant dans le fichier :
   ```yaml
   AWSTemplateFormatVersion: 2010-09-09

   Resources:
     AthenaNamedQuery:
       Type: AWS::Athena::NamedQuery
       Properties:
         Database: "taxidata"
         Description: "Une requête qui sélectionne tous les trajets avec un montant supérieur à 100 $."
         Name: "FaresOver100DollarsUS"
         QueryString: >
           SELECT distance, paytype, fare, tip, tolls, surcharge, total
           FROM yellow
           WHERE total >= 100.0
           ORDER BY total DESC

   Outputs:
     AthenaNamedQuery:
       Value: !Ref AthenaNamedQuery
   ```

3. **Valider le modèle CloudFormation :**
   - Exécutez la commande suivante dans le terminal AWS Cloud9 pour valider le modèle :
   ```bash
   aws cloudformation validate-template --template-body file://athenaquery.cf.yml
   ```
   - Si le modèle est valide, vous verrez une réponse contenant `"Parameters": []`.

4. **Créer la pile CloudFormation :**
   - Exécutez la commande suivante pour créer une pile CloudFormation qui déploie la requête nommée :
   ```bash
   aws cloudformation create-stack --stack-name athenaquery --template-body file://athenaquery.cf.yml
   ```

5. **Vérifier que la requête nommée a été créée :**
   - Exécutez cette commande pour lister les requêtes nommées dans Athena :
   ```bash
   aws athena list-named-queries
   ```
   - Copiez l'ID de la requête nommée.
   - Pour vérifier les détails de la requête nommée, exécutez la commande suivante (en remplaçant `<QUERY-ID>` par l'ID copié) :
   ```bash
   aws athena get-named-query --named-query-id <QUERY-ID>
   ```

#### Résumé de la tâche 5

Dans cette tâche, vous avez appris à intégrer une requête nommée dans un modèle CloudFormation. Vous avez validé et déployé un modèle pour créer une requête nommée dans une pile CloudFormation. Cette approche permet de réutiliser facilement la requête dans différents comptes AWS en changeant simplement les paramètres du modèle.

---

# Tâche 6 : Revoir la politique IAM pour l'accès à Athena et AWS Glue

Maintenant que vous avez créé une requête nommée Athena à l'aide de CloudFormation, vous allez revoir la politique **IAM** associée pour garantir que d'autres utilisateurs peuvent l'utiliser dans un environnement de production.

#### Instructions

1. **Revoir la politique IAM pour les data scientists :**
   - Accédez à la console IAM, puis sélectionnez l'utilisateur `mary` dans la liste des utilisateurs.
   - Sous l'onglet **Autorisations**, examinez la politique `Policy-For-Data-Scientists`.
   - Cette politique donne un accès limité à Amazon S3, AWS Glue, et Athena. Vous constaterez que Mary a l'autorisation de lister les requêtes nommées, ainsi que les permissions pour :
     - Accéder aux compartiments S3 et lire/écrire des objets, sans toutefois pouvoir en créer de nouveaux.
     - Créer des bases de données et des tables dans AWS Glue.
     - Utiliser CloudShell pour exécuter des commandes AWS CLI.
     - Créer des piles CloudFormation et valider des modèles.
     - Créer un catalogue de données et exécuter des requêtes dans Athena.

#### Résumé de la tâche 6

Dans cette tâche, vous avez examiné une politique IAM qui fournit un accès limité à des services comme Amazon S3, AWS Glue, CloudFormation, et Athena. Vous avez vu comment cette politique permet aux membres de l'équipe de data science d'exécuter des requêtes nommées Athena tout en respectant le principe de moindre privilège.

---



# Tâche 7 : Confirmer que Mary peut accéder et utiliser la requête nommée

Après avoir examiné la politique IAM, vous allez tester l'accès d'un autre utilisateur (Mary) à la requête nommée que vous avez créée. Vous allez également tester la capacité de cet utilisateur à exécuter la requête en utilisant l'interface en ligne de commande AWS (CLI).

#### Instructions

1. **Récupérer les identifiants de l'utilisateur IAM `mary` :**
   - Accédez à la console CloudFormation dans AWS.
   - Dans le panneau de navigation, sélectionnez **Stacks** (Piles).
   - Choisissez le lien pour la pile qui a créé l'environnement du lab. Le nom de la pile inclut une chaîne de lettres et de chiffres aléatoires et est la plus ancienne dans la liste.
   - Sur la page de détails de la pile, sélectionnez l'onglet **Outputs**.
     - **Remarque** : La pile CloudFormation qui a créé les ressources pour ce lab a généré les identifiants (clé d'accès et clé secrète) pour l'utilisateur `mary`.
   - Copiez la valeur du champ **MarysAccessKey** dans votre presse-papiers.
   
2. **Créer une variable pour la clé d'accès dans le terminal AWS Cloud9 :**
   - Retournez dans le terminal AWS Cloud9 et exécutez la commande suivante (en remplaçant `<ACCESS-KEY>` par la valeur copiée) :
   ```bash
   AK=<ACCESS-KEY>
   ```

3. **Récupérer la clé secrète de `mary` :**
   - Revenez dans la console CloudFormation et copiez la valeur du champ **MarysSecretAccessKey** dans votre presse-papiers.

4. **Créer une variable pour la clé secrète dans le terminal AWS Cloud9 :**
   - Retournez dans le terminal AWS Cloud9 et exécutez la commande suivante (en remplaçant `<SECRET-ACCESS-KEY>` par la valeur copiée) :
   ```bash
   SAK=<SECRET-ACCESS-KEY>
   ```

5. **Tester l'accès de `mary` à la requête nommée :**
   - Vous allez tester si l'utilisateur `mary` peut accéder à la requête nommée en utilisant les identifiants que vous avez définis comme variables `AK` et `SAK`. Vous utiliserez également la variable `NQ` (qui contient l'ID de la requête nommée) pour inclure l'ID de la requête dans la commande.
   - Exécutez la commande suivante :
   ```bash
   AWS_ACCESS_KEY_ID=$AK AWS_SECRET_ACCESS_KEY=$SAK aws athena get-named-query --named-query-id $NQ
   ```
   - Si la commande réussit, vous devriez voir un résultat similaire à ceci :
   ```json
   {
     "NamedQuery": {
       "Name": "FaresOver100DollarsUS",
       "Description": "A query that selects all fares over $100.00 (US)",
       "Database": "taxidata",
       "QueryString": "SELECT distance, paytype, fare, tip, tolls, surcharge, total FROM yellow WHERE total >= 100.0 ORDER BY total DESC",
       "NamedQueryId": "644f6c10-bf57-48a2-a0ec-a3de179db511",
       "WorkGroup": "primary"
     }
   }
   ```

   - Ce résultat confirme que `mary` a accès à la requête nommée que vous avez créée et déployée en utilisant CloudFormation.

---

### Résumé de la tâche 7

Dans cette tâche, vous avez confirmé que `mary`, un utilisateur IAM avec des autorisations limitées, peut accéder et utiliser la requête nommée que vous avez créée à l'aide de CloudFormation. Vous avez utilisé les identifiants de `mary` dans AWS CLI pour tester cette capacité.

---

### Conclusion du lab

Félicitations ! Vous avez maintenant terminé le lab. Voici un récapitulatif des compétences que vous avez acquises :

- Utilisation d'Athena pour créer des bases de données et des tables à partir de données stockées dans Amazon S3.
- Optimisation des requêtes Athena en utilisant des stratégies telles que la compression des données, la création de buckets, et le partitionnement.
- Création de vues dans Athena pour simplifier les analyses de données.
- Création et déploiement de requêtes nommées Athena via AWS CloudFormation.
- Revue et gestion des politiques IAM pour garantir un accès sécurisé et approprié aux ressources AWS, tout en suivant le principe du moindre privilège.

---

### Soumission de votre travail

- Pour enregistrer votre progression, sélectionnez **Submit** en haut de ces instructions.
- Lorsque vous y êtes invité, choisissez **Yes**.
- Après quelques minutes, le panneau des notes apparaîtra pour vous montrer combien de points vous avez obtenus pour chaque tâche.
  - **Astuce** : Si les résultats ne s'affichent pas après quelques minutes, sélectionnez **Grades** en haut de ces instructions.

---

### Terminer le lab

- Pour terminer le lab, en haut de la page, sélectionnez **End Lab**, puis **Yes** pour confirmer.
- Un panneau s'affiche pour indiquer que le lab est en train de se terminer.

---

### Ressources supplémentaires

Pour plus d'informations sur les services et concepts abordés dans ce lab, consultez les ressources suivantes :

- [Documentation Amazon Athena](https://docs.aws.amazon.com/athena/)
- [Démarrage avec AWS Glue](https://docs.aws.amazon.com/glue/)
- [Documentation Presto](https://prestodb.io/docs/current/)
- [Requêtes DML, Fonctions et Opérateurs](https://docs.aws.amazon.com/athena/latest/ug/functions-operators.html)
- [Tarification d'Amazon Athena](https://aws.amazon.com/athena/pricing/)
- [Utilisation d'un SerDe](https://docs.aws.amazon.com/athena/latest/ug/serde.html)
- [Formats de stockage en colonnes](https://docs.aws.amazon.com/athena/latest/ug/file-formats.html)
- [Bucketing vs Partitioning](https://docs.aws.amazon.com/athena/latest/ug/tables.html)
- [Travailler avec les vues dans Athena](https://docs.aws.amazon.com/athena/latest/ug/views.html)
- [Politiques et permissions dans IAM](https://docs.aws.amazon.com/IAM/)

