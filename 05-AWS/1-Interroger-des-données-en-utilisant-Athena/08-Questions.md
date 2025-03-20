
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



| **Service**      | **Description**                                                                                     | **Rôle de Mary (Ce qu'elle peut faire)**                       | **Limites de Mary (Ce qu'elle ne peut pas faire)**                     |
|------------------|---------------------------------------------------------------------------------------------------|----------------------------------------------------------------|-----------------------------------------------------------------------|
| **IAM (Identity and Access Management)** | Gère les permissions des utilisateurs et leurs accès aux services AWS.       | Se connecter avec un profil limité défini par une politique.   | Modifier les permissions ou créer des rôles/politiques IAM.           |
| **Amazon S3**     | Stocke les données brutes (par exemple, fichiers CSV).                                            | Interroger des données stockées via Athena.                   | Créer, modifier ou supprimer des buckets/partitions.                  |
| **AWS Glue**      | Catalogue de métadonnées décrivant les bases de données et tables pour Athena.                    | Accéder aux métadonnées existantes (tables, schémas).          | Créer ou modifier des tables, partitions ou crawlers.                 |
| **Amazon Athena** | Service permettant d'exécuter des requêtes SQL sur des données stockées dans S3.                  | Exécuter des requêtes SQL, utiliser des vues pour simplification. | Créer ou modifier des tables, partitions, ou optimiser les requêtes.  |
| **Partitioning/Buckets** | Méthodes d'optimisation pour améliorer les performances des requêtes sur S3.              | Bénéficier des optimisations déjà mises en place.              | Créer ou modifier les partitions ou le bucketization.                 |
| **Athena Views**  | Vues créées dans Athena pour simplifier les requêtes SQL complexes.                               | Interroger les vues créées par les ingénieurs.                | Créer, modifier ou supprimer des vues.                                |
| **CloudFormation** | Infrastructure-as-Code pour automatiser la création de requêtes nommées.                         | Exécuter des requêtes nommées déjà déployées.                  | Déployer, modifier ou créer des requêtes nommées.                     |
| **AWS Cloud9**    | IDE en ligne utilisé pour tester l'exécution de requêtes via AWS CLI.                            | Tester l'accès aux requêtes nommées via AWS CLI.               | Modifier les configurations d'environnement ou de sécurité.          |





# **Les acteurs dans ce workflow sont :**  

| **Acteur**      | **Rôle**                                              | **Description**                                               |
|----------------|-------------------------------------------------------|--------------------------------------------------------------|
| **Mary (Utilisateur IAM)**  | Data Scientist / Utilisateur Avancé        | Interroge les données via Athena. Utilise AWS CLI via Cloud9. Accès limité par une politique IAM.  |
| **AWS Athena**  | Moteur de requête SQL                                  | Permet à Mary d'exécuter des requêtes SQL sur des fichiers stockés dans Amazon S3.  |
| **AWS Glue**    | Catalogue de métadonnées                              | Gère les schémas, tables et bases de données pour Athena.    |
| **Amazon S3**   | Service de stockage                                    | Stocke les fichiers bruts (par exemple, fichiers CSV).       |
| **AWS CloudFormation** | Infrastructure-as-Code                          | Automatisation de requêtes nommées pour une utilisation simplifiée. |
| **AWS Cloud9**  | IDE en ligne                                           | Permet à Mary de tester les requêtes via AWS CLI.           |
| **Administrateur AWS / Data Engineer (Invisible)** | Créateur de l'infrastructure   | Configure S3, AWS Glue, Athena, CloudFormation, et met en place les vues et optimisations.  |

### **Résumé :**  
- **Mary** est l'utilisateur final qui interagit avec les données via Athena et teste son accès via Cloud9.  
- Les **services AWS** fournissent l'infrastructure pour stocker, interroger, et automatiser l'accès aux données.  
- Les **Administrateurs / Data Engineers** sont ceux qui mettent en place cette infrastructure et donnent à Mary les accès nécessaires.  


