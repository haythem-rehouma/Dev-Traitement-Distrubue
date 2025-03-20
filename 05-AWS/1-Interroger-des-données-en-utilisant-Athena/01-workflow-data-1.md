
```
          +-------------------+
          |     IAM User       |        <- Utilisateur Mary avec des permissions limitées
          +-------------------+
                   |
                   v
          +-------------------+
          |       Athena       |        <- Interroge les données stockées dans Amazon S3
          +-------------------+
                   |
                   v
          +-------------------+   
          |      AWS Glue      |        <- Gère les métadonnées (bases de données, tables, colonnes)
          +-------------------+
                   |
                   v
          +-------------------+
          |     Amazon S3      |        <- Stocke les données brutes (fichiers CSV)
          +-------------------+
                   |
                   v
          +-------------------+        <- Optimisation des requêtes avec des partitions/buckets
          |    Partitioning    |
          +-------------------+
                   |
                   v
          +-------------------+        <- Création de vues pour simplifier les analyses
          |     Athena Views   |
          +-------------------+
                   |
                   v
          +-------------------+        <- Partage de requêtes nommées via CloudFormation
          |  CloudFormation    |
          +-------------------+
                   |
                   v
          +-------------------+        <- Validation et test des requêtes via AWS CLI dans Cloud9
          |      Cloud9        |
          +-------------------+
```

### Explication du workflow :

1. **IAM User** : L'utilisateur (`mary`) avec des permissions limitées se connecte à AWS et accède à Athena.
   
2. **Athena** : Mary utilise Amazon Athena pour exécuter des requêtes SQL sur des fichiers CSV stockés dans Amazon S3.
   
3. **AWS Glue** : Athena s'appuie sur AWS Glue pour gérer les métadonnées des fichiers, comme les schémas des tables et les types de données.

4. **Amazon S3** : Les données brutes (comme les fichiers CSV du dataset de taxis) sont stockées dans des buckets S3. Athena interroge ces fichiers directement.

5. **Partitioning & Buckets** : Pour optimiser les requêtes, les données peuvent être partitionnées (selon des colonnes comme `paytype`) ou bucketisées pour améliorer les performances des requêtes et réduire les coûts.

6. **Athena Views** : Des vues sont créées pour simplifier les requêtes et cacher la complexité des jointures et calculs aux utilisateurs finaux.

7. **CloudFormation** : Des templates CloudFormation sont utilisés pour automatiser la création de requêtes nommées afin de partager facilement ces configurations avec d'autres utilisateurs ou départements.

8. **Cloud9** : L'environnement de développement intégré (IDE) Cloud9 est utilisé pour tester l'accès d'un utilisateur (comme `mary`) à ces requêtes nommées en utilisant l'AWS CLI.

Ce workflow montre la manière dont Athena, AWS Glue, S3 et d'autres services AWS sont utilisés ensemble pour interroger et gérer des données dans un environnement sécurisé et optimisé.


![image](https://github.com/user-attachments/assets/52f24d08-259e-43ba-ab24-8f2f3219a261)


- L'image ci-dessus représente une vue plus complète du pipeline du lab, et elle ajoute des détails visuels qui complètent la représentation ci-haut. 
- Voici une description plus précise  :

### Pipeline global :
1. **Athena récupère le dataset taxi jaune (Yellow taxi dataset) depuis un bucket S3** dans **AWS Account 2** et crée une **base de données** et des **tables** dans le **catalogue AWS Glue**. Cela correspond à l'étape 1 où vous configurez la requête Athena pour interagir avec les données.

2. **Optimisation des requêtes avec des "buckets"** : Vous optimisez les requêtes en séparant les données dans des "buckets", qui permettent de réduire la taille des données scannées pour des requêtes spécifiques, comme montré dans l'image.

3. **Optimisation des requêtes avec des partitions** : En plus des "buckets", les données peuvent être partitionnées par des valeurs spécifiques (comme `paytype`) pour optimiser davantage les performances des requêtes, ce qui est la troisième étape dans l'image.

4. **Création de vues dans Athena** : Cela permet de simplifier les requêtes et de combiner les résultats de différentes tables. C'est une étape clé dans l'image (point 4).

5. **Création de requêtes nommées via AWS CloudFormation** : Les requêtes peuvent être encapsulées et automatisées via des templates CloudFormation, comme illustré dans l'image. Cela permet de réutiliser les requêtes facilement et de les partager avec d'autres comptes ou utilisateurs.

6. **IAM et politiques de permissions** : L'utilisateur `Mary`, qui est membre du groupe Data-Scientists, bénéficie des permissions fournies par la politique **Policy-For-Data-Scientists**, définissant les permissions nécessaires pour interagir avec les services AWS mentionnés (Athena, CloudFormation, S3, etc.).

7. **Utilisation de la requête nommée dans AWS Cloud9** : `Mary` peut utiliser la requête nommée créée via CloudFormation dans AWS Cloud9, vérifiant ainsi son accès et sa capacité à exécuter la requête via AWS CLI.

### Conclusion du workflow :

L'image montre comment vous configurez Athena pour interagir avec des données brutes stockées dans S3, optimisez les requêtes avec des buckets et partitions, et simplifiez l'analyse de données via des vues. Finalement, les requêtes sont encapsulées via CloudFormation pour être partagées et utilisées par d'autres utilisateurs comme `Mary` avec des permissions limitées, testant leur utilisation dans un environnement Cloud9.

