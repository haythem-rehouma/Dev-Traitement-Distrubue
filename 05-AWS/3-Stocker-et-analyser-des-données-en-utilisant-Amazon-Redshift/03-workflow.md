# Workflow

```
               +---------------------+
               |                     |
               |    Utilisateur       |
               |  (Mary, Administrateur)|
               |                     |
               +---------------------+
                        |
                        |
                        V
              +-------------------------+
              |   Console AWS/CLI        |         +---------------------+
              |  (Accès à Redshift, IAM, |-------->|   AWS IAM            |
              |   S3, EC2, Cloud9)       |         | (Rôles et Politiques)|
              +-------------------------+         +---------------------+
                        |
                        | 1. Création du cluster Redshift et
                        |    configuration IAM (MyRedshiftRole)
                        V
                +----------------------+
                |                      |
                |    Cluster Redshift   |
                |  (redshift-cluster-1)|
                |                      |
                +----------------------+
                         |
                         | 2. Chargement des données depuis
                         |    S3 vers Redshift
                         V
                +----------------------+
                |                      |
                |       Amazon S3       |
                |   (Stockage de données)|
                |                      |
                +----------------------+
                         |
                         | 3. Requêtes SQL exécutées 
                         |    via Console/CLI
                         V
               +-----------------------+
               |                       |
               |    Tables Redshift     |
               |   (users, date, sales) |
               |                       |
               +-----------------------+
                         |
                         | 4. Récupération des résultats
                         V
               +-----------------------+
               |                       |
               |   Résultats SQL        |
               |  (Rapports, Analyses)  |
               |                       |
               +-----------------------+
                         |
                         |
                         V
               +-----------------------+
               |                       |
               |    EC2 (Optionnel)     |
               | (Accès à Redshift via  |
               |    Bastion ou app Web) |
               +-----------------------+
```

### Explication du schéma :

1. **Utilisateur (Mary ou l'Administrateur)** : L'utilisateur interagit avec les services AWS via la console ou la CLI (Cloud9), utilisant les permissions et rôles IAM définis pour accéder à Redshift et S3.

2. **Console AWS/CLI** : Interface utilisée pour la création du cluster Redshift, la gestion des rôles IAM, et l'exécution des requêtes SQL.

3. **Amazon Redshift** : Le cluster Redshift est créé, et les données sont chargées depuis un compartiment S3. Redshift stocke les tables et exécute les requêtes SQL.

4. **Amazon S3** : Source des données, où les fichiers de ventes sont stockés et récupérés par Redshift pour analyse.

5. **Tables Redshift** : Les données sont organisées dans des tables (comme `users`, `sales`, et `date`) et prêtes à être interrogées via SQL.

6. **Résultats SQL** : Les requêtes sont exécutées et les résultats des rapports ou analyses sont obtenus.

7. **EC2 (optionnel)** : Les instances EC2 peuvent être configurées pour accéder au cluster Redshift, via un serveur bastion ou une application web hébergée, permettant des interactions supplémentaires avec la base de données.

Ce schéma montre le flux d'opérations, de la configuration des services jusqu'à l'exécution des requêtes et la récupération des résultats.
