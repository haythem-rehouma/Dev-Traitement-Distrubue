# Plus de détails : 


```
+------------------------------------------------------------------------------------------------------------+
|                                                    Utilisateur                                              |
|                                                    (You/Mary)                                               |
+--------------------------------------------------------+---------------------------------------------------+
                                                         |
                                                         v
                                   +---------------------------------------------+
                                   |                  IAM Role                   |
                                   |               (gluelab IAM)                 |
                                   +---------------------------------------------+
                                                         |
                                                         v
                                       +-----------------------------------+
                                       |        AWS Cloud9 IDE             |
                                       | (Utilisé pour créer le modèle     |
                                       | CloudFormation et exécuter AWS CLI|
                                       +-----------------------------------+
                                                         |
                                                         v
                                    +-------------------------------------------+
                                    |     Modèle AWS CloudFormation (Stack)     |
                                    |  (Déploie les ressources dans AWS)        |
                                    +-------------------------------------------+
                                                         |
                                                         v
                                    +-----------------------------------+
                                    |     AWS Glue Data Catalog         |
                                    |  (Contient les métadonnées)       |
                                    +-----------------------------------+
                                                         |
                            +----------------------------+------------------------------+
                            |                                                           |
                            v                                                           v
         +--------------------------------+                                +----------------------------+
         |         Weather Crawler        |                                |  AWS Glue Base de Données  |
         | (Crawler pour le dataset GHCN-D)|                                |    (weatherdata)           |
         +--------------------------------+                                +----------------------------+
                 |                                                                 |
                 |                                                                 v
                 v                                           +--------------------------------------------+
     +-----------------------------+                        |  Transformation et modification du schéma  |
     | S3 Bucket (noaa-ghcn-pds)    |                        |  (via la console AWS Glue)                 |
     | (Contient le dataset GHCN-D) |                        +--------------------------------------------+
     +-----------------------------+                                          |
                                                                               v
                                                      +-------------------------------------------+
                                                      | AWS Glue Data Catalog (Schéma mis à jour) |
                                                      +-------------------------------------------+
                                                                               |
                                                                               v
                                                         +----------------------------------+
                                                         |     Amazon Athena                |
                                                         | (Interroger la base de données   |
                                                         | avec SQL)                        |
                                                         +----------------------------------+
                                                                               |
                                                                               v
                               +---------------------------------------------+  +--------------------------------+
                               |   data-science-bucket (S3)                 |  |   glue-1950-bucket (S3)       |
                               | (Stocke les résultats des requêtes Athena) |  | (Stocke les données post-1950) |
                               +---------------------------------------------+  +--------------------------------+
```

### Explication du schéma :

#### **1. Utilisateur (You/Mary) :**
L'utilisateur démarre les processus en accédant à **AWS Glue** ou en utilisant l'interface de ligne de commande AWS CLI via **AWS Cloud9**.

#### **2. IAM Role (gluelab IAM) :**
L'utilisateur doit avoir le rôle **IAM gluelab** qui lui permet d'accéder aux services AWS Glue, S3, et Athena, ainsi que d'exécuter des commandes AWS CLI. Ce rôle est nécessaire pour que le crawler puisse interagir avec les datasets dans S3 et mettre à jour le **Glue Data Catalog**.

#### **3. AWS Cloud9 IDE :**
**AWS Cloud9** est l'environnement où l'utilisateur crée et exécute les modèles **CloudFormation** pour automatiser la création du **crawler** et des bases de données Glue. Il est également utilisé pour exécuter des commandes AWS CLI et gérer l'infrastructure.

#### **4. Modèle AWS CloudFormation (Stack) :**
Le modèle **CloudFormation** crée un **crawler AWS Glue**, une base de données Glue, et les autres ressources nécessaires pour gérer les données climatiques provenant de S3. Ce modèle est réutilisable pour différents environnements.

#### **5. AWS Glue Data Catalog :**
Le **Data Catalog** contient toutes les métadonnées concernant les tables et les schémas découverts par le crawler. Ces métadonnées sont stockées ici pour être utilisées par Athena et d'autres services AWS.

#### **6. Weather Crawler :**
Le **Weather Crawler** est configuré pour explorer les données du dataset GHCN-D stockées dans le bucket S3 public (`s3://noaa-ghcn-pds/csv/by_year/`). Ce crawler découvre automatiquement le schéma des données et les enregistre dans le **AWS Glue Data Catalog**.

#### **7. S3 Bucket (noaa-ghcn-pds) :**
Le dataset **GHCN-D** est stocké dans un bucket S3 public. Le crawler accède à ces données pour en découvrir le schéma et les importer dans AWS Glue.

#### **8. Transformation et modification du schéma :**
Une fois que le **crawler** a découvert le schéma, l'utilisateur peut modifier le schéma des tables via la console AWS Glue. Par exemple, les colonnes peuvent être renommées, supprimées, ou modifiées selon les besoins de l'analyse.

#### **9. AWS Glue Database :**
Les données découvertes par le crawler sont stockées dans une base de données AWS Glue appelée **weatherdata**. Cette base contient toutes les tables et les schémas des données climatiques.

#### **10. Amazon Athena :**
Après que les données ont été importées et transformées dans Glue, **Amazon Athena** est utilisé pour exécuter des requêtes SQL sur ces données. Les résultats des requêtes sont stockés dans des buckets S3.

#### **11. Buckets S3 (data-science-bucket et glue-1950-bucket) :**
   - Le **data-science-bucket** stocke les résultats des requêtes SQL exécutées dans **Athena**.
   - Le **glue-1950-bucket** contient les données filtrées (post-1950) après les transformations effectuées dans Athena.

---

### Détails supplémentaires des tâches :

- **Accès IAM :** Le rôle **gluelab** donne les permissions nécessaires pour que le crawler puisse lire les données dans S3, écrire dans le Glue Data Catalog, et exécuter des requêtes avec Athena.
- **Crawler :** Le **crawler** est un processus automatique qui inspecte les données dans S3, découvre leur schéma, et crée des tables dans Glue. Il peut être exécuté manuellement ou programmé pour s'exécuter à intervalles réguliers.
- **Transformation de schéma :** Une étape clé du processus ETL est la transformation des schémas de données. L'utilisateur peut renommer des colonnes, ajuster les types de données, ou filtrer les lignes de données.
- **Requêtes Athena :** Une fois les données disponibles dans la base de données Glue, Athena permet de les analyser facilement en utilisant SQL.
