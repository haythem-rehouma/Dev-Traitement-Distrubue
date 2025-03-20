
# Ensemble du workflow détaillé pour ce laboratoire
- depuis :
- (1) la création et l'exécution du crawler
- (2) jusqu'à l'interrogation des données 
- (3) avec Athena et
- (4) la gestion des résultats dans des buckets S3. 


```
+------------------------------------------------------------------------------------+
|                                       Utilisateur                                  |
|                                       (You/Mary)                                   |
+---------------------------------------------+--------------------------------------+
                                              |
                                              v
                        +---------------------+----------------------+
                        |                IAM Role (gluelab)           |
                        |          Policy: Policy-For-Data-Scientists  |
                        +---------------------+----------------------+
                                              |
                                              v
                        +---------------------+----------------------+
                        |                AWS Cloud9 IDE               |
                        |  (Créer modèle CloudFormation avec AWS CLI)  |
                        +---------------------+----------------------+
                                              |
                                              v
                              +--------------+---------------+
                              |     AWS CloudFormation        |
                              | (Créer cfn-crawler-weather)    |
                              +--------------+---------------+
                                              |
                                              v
                   +----------------------+        +-------------------------+
                   |    Weather Crawler    |------->|     AWS Glue Data Catalog|
                   |(Crawler accède aux    |        | (Schéma des données GHCN-D)|
                   | données dans S3)      |        +-------------------------+
                   +----------------------+                    
                                              |
                                              v
                                    +---------+----------+
                                    |    Transformation    |
                                    |   (Modifications du  |
                                    |       schéma)        |
                                    +----------------------+
                                              |
                                              v
                   +----------------------+         +----------------------+
                   |    Amazon Athena      |-------->|  Data-science-bucket |
                   |(Exécution de requêtes |         +----------------------+
                   | sur les données)      |
                   +----------------------+
                              |
                              v
                +-------------+-------------+
                |   glue-1950-bucket (Données depuis 1950)|
                +-----------------------------------------+
```

### Explication du schéma ASCII :
1. **Utilisateur (You/Mary)** : L'utilisateur déclenche le processus via AWS Cloud9 en utilisant le rôle IAM avec les permissions nécessaires.
2. **IAM Role (gluelab)** : Ce rôle est utilisé pour permettre à l'utilisateur d'accéder et d'interagir avec les ressources AWS.
3. **AWS Cloud9 IDE** : Ici, l'utilisateur crée un modèle **CloudFormation** via AWS CLI pour automatiser la création du **crawler**.
4. **AWS CloudFormation** : Il crée le **cfn-crawler-weather** qui est utilisé pour explorer les données dans le bucket S3.
5. **Weather Crawler** : Ce crawler accède aux données (GHCN-D dataset) stockées dans un bucket S3 et découvre leur schéma, qu'il enregistre dans le **Glue Data Catalog**.
6. **Transformation** : Une fois les données découvertes, vous pouvez transformer le schéma pour qu'il corresponde à vos besoins d'analyse.
7. **Amazon Athena** : Après avoir catalogué les données, vous pouvez exécuter des requêtes SQL sur celles-ci via **Athena**. Les résultats sont stockés dans le **data-science-bucket**.
8. **glue-1950-bucket** : Un bucket supplémentaire est utilisé pour stocker les résultats des requêtes SQL filtrées, comme les données à partir de 1950.

- Ce schéma montre les principales relations et flux d'informations entre les services dans ce workflow AWS.

