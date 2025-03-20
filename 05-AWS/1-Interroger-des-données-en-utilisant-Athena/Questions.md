
- L'objectif est de permettre à un utilisateur avec des permissions limitées, comme Mary (Data Scientist), d'interroger efficacement des données brutes stockées dans Amazon S3 via Amazon Athena. 
- Le workflow utilise AWS Glue pour gérer les métadonnées, partitionner ou bucketiser les données pour optimiser les requêtes, et créer des vues Athena pour simplifier l'accès aux données. 
- L'automatisation est assurée par CloudFormation pour partager des requêtes nommées, tandis que l'accès et les tests sont réalisés via AWS CLI dans Cloud9



# ✅ **Ce que Mary peut faire :**  
- Interroger les données stockées dans Amazon S3 via Amazon Athena en utilisant des requêtes SQL.  
- Accéder aux métadonnées gérées par AWS Glue (bases de données, tables, colonnes).  
- Utiliser des vues créées dans Athena pour simplifier ses analyses.  
- Exécuter des requêtes nommées déployées via CloudFormation.  
- Tester et vérifier l'accès aux requêtes via AWS CLI dans l'environnement Cloud9.  

# ❌ **Ce que Mary ne peut pas faire :**  
- Créer, modifier ou supprimer des buckets S3 ou des partitions de données.  
- Configurer ou modifier les tables et bases de données dans le catalogue AWS Glue.  
- Déployer ou modifier des requêtes nommées via CloudFormation.  
- Modifier les permissions IAM qui contrôlent son accès aux services AWS.  
- Gérer l'infrastructure sous-jacente (par exemple, S3, Glue, CloudFormation).  

