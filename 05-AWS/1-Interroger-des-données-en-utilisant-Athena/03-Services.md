Les services évoqués dans ce lab sont des services AWS qui permettent de stocker, interroger, analyser et gérer les données de manière sécurisée et optimisée. Voici un récapitulatif des principaux services abordés :

### 1. **Amazon S3 (Simple Storage Service)** :
   - **Fonction principale** : Stockage d'objets dans le cloud.
   - **Rôle dans le lab** : Les données sources, comme les fichiers CSV contenant des données de taxis, sont stockées dans des buckets S3. Amazon Athena interroge directement ces données dans S3.

### 2. **Amazon Athena** :
   - **Fonction principale** : Service d'interrogation interactif qui vous permet d'exécuter des requêtes SQL directement sur les données stockées dans Amazon S3.
   - **Rôle dans le lab** : Utilisé pour interroger les fichiers CSV dans S3, optimiser les performances des requêtes avec des buckets, partitions, et vues, et créer des requêtes nommées pour faciliter la réutilisation.

### 3. **AWS Glue** :
   - **Fonction principale** : Service d'intégration de données qui permet la création de catalogues de données, la gestion des métadonnées et la préparation des données pour l'analyse.
   - **Rôle dans le lab** : AWS Glue est utilisé pour créer des bases de données et des tables, et pour stocker les métadonnées sur les fichiers stockés dans S3 (comme les colonnes, les types de données, etc.).

### 4. **AWS CloudFormation** :
   - **Fonction principale** : Service d'infrastructure-as-code permettant de modéliser, provisionner et gérer des ressources AWS à travers des templates JSON ou YAML.
   - **Rôle dans le lab** : CloudFormation est utilisé pour créer et déployer des requêtes nommées dans Athena via des templates. Cela permet de partager et réutiliser facilement des configurations.

### 5. **AWS IAM (Identity and Access Management)** :
   - **Fonction principale** : Gestion des utilisateurs et de leurs permissions sur les ressources AWS. IAM permet de définir des politiques d'accès afin de respecter le principe du moindre privilège.
   - **Rôle dans le lab** : IAM est utilisé pour définir des politiques spécifiques pour les utilisateurs, notamment pour restreindre l'accès d'un utilisateur (comme `mary`) à certains services comme Athena, S3 et Glue, tout en leur permettant d'effectuer des tâches spécifiques.

### 6. **AWS Cloud9** :
   - **Fonction principale** : Environnement de développement intégré (IDE) basé sur le cloud qui permet d'écrire, d'exécuter et de déboguer du code via un navigateur.
   - **Rôle dans le lab** : Cloud9 est utilisé comme environnement pour tester et exécuter des commandes AWS CLI afin de valider l'accès et l'utilisation des requêtes nommées dans Athena.

Ces services interagissent ensemble pour offrir une solution complète de gestion, d'interrogation et d'optimisation des données dans AWS.
