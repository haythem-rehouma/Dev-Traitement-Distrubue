------------------------------------------------------
# Requete nommée 
------------------------------------------------------

- Dans AWS (Amazon Web Services), une **requête nommée** est souvent associée à **Amazon DynamoDB**, un service de base de données NoSQL géré. 
- Les requêtes nommées sont en fait des requêtes préconfigurées que vous pouvez exécuter directement à partir de DynamoDB ou d’autres services AWS, comme **Amazon Athena** ou **Amazon Redshift**.

Je vous donne deux cas les plus courants pour une requête nommée :

# 1. **Requête nommée dans Amazon Athena** :
Dans **Amazon Athena**, un service qui vous permet de faire des requêtes SQL sur des données stockées dans Amazon S3, une requête nommée est une requête SQL que vous avez **sauvegardée** avec un nom spécifique. Cela permet de réutiliser la requête plus tard sans avoir à la réécrire. Vous pouvez exécuter la requête enregistrée à tout moment en appelant simplement son nom.

#### Exemple :
Si vous avez une requête SQL complexe qui interroge vos données dans S3, vous pouvez la sauvegarder comme une requête nommée pour ne pas avoir à la ressaisir. Vous pouvez aussi l’automatiser via des **AWS Lambda** ou des **CloudWatch Events**.

# 2. **Requête nommée dans Amazon Redshift** :
Dans **Amazon Redshift**, une base de données analytique entièrement gérée, vous pouvez également sauvegarder des requêtes SQL comme des **requêtes nommées**. Elles permettent d’automatiser l’analyse de données complexes ou de faciliter les tâches répétitives.

# Avantages des requêtes nommées :
- **Réutilisabilité** : Elles permettent de gagner du temps et d’éviter des erreurs de frappe en exécutant des requêtes récurrentes déjà optimisées.
- **Partage** : Vous pouvez partager des requêtes nommées avec d’autres utilisateurs dans votre organisation.
- **Automatisation** : Elles peuvent être intégrées dans des pipelines d’automatisation pour effectuer des analyses régulières.

