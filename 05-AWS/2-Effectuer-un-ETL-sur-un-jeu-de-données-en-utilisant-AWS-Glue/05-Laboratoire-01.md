
-----
------
# Pipeline
-----
------

![image](https://github.com/user-attachments/assets/6161b0d0-aee1-499b-844b-94d78b89b690)


-------
-------
# Étape 1 : Créer un **IAM Role** pour AWS Glue
-------
-------

Le rôle IAM est essentiel pour donner à **AWS Glue** et **Athena** les permissions nécessaires pour accéder à vos données dans **Amazon S3** et écrire dans le **Glue Data Catalog**.

#### Détails :
- **Nom du rôle** : `gluelab`
- **Permissions nécessaires** :
  - **AmazonS3FullAccess** : Pour permettre au crawler d'accéder aux buckets S3 où les données sont stockées.
  - **AWSGlueServiceRole** : Pour que le crawler et les jobs Glue puissent s'exécuter.
  - **Athena permissions** : Accorder les permissions nécessaires pour qu'Athena puisse lire et écrire les résultats des requêtes dans S3.

#### Étapes pour créer le rôle IAM :
1. Allez dans la console **IAM**.
2. Sélectionnez **Roles** > **Create Role**.
3. Choisissez le service **AWS Glue** comme principal.
4. Assignez les politiques suivantes :
   - **AmazonS3FullAccess**
   - **AWSGlueServiceRole**
   - **AmazonAthenaFullAccess** (pour autoriser les requêtes Athena).
5. Donnez un nom au rôle, par exemple **gluelab IAM**, et créez le rôle.


---
-------
-------
# Étape 2 : Utiliser **AWS Cloud9** pour configurer l'infrastructure avec **AWS CloudFormation**
-------
-------
-------

AWS Cloud9 est utilisé ici pour exécuter des commandes **AWS CLI** et créer des fichiers **CloudFormation** directement dans un environnement de développement intégré (IDE). Cela vous permet de configurer facilement l'infrastructure sans passer par des interfaces graphiques complexes.

#### Détails :
- **AWS CloudFormation** vous permet d’automatiser la création de ressources AWS. Vous écrivez un modèle en YAML ou JSON, puis AWS se charge de créer les ressources spécifiées.
- Dans ce laboratoire, vous utiliserez un modèle **CloudFormation** pour :
  - Créer un **crawler AWS Glue**.
  - Créer une **base de données Glue**.

#### Exemple de modèle YAML pour CloudFormation :
```yaml
AWSTemplateFormatVersion: '2010-09-09'
Parameters:
  CFNCrawlerName:
    Type: String
    Default: cfn-crawler-weather
  CFNDatabaseName:
    Type: String
    Default: cfn-database-weather
  CFNTablePrefixName:
    Type: String
    Default: cfn_sample_1-weather    
Resources:
  CFNDatabaseWeather:
    Type: AWS::Glue::Database
    Properties:
      CatalogId: !Ref AWS::AccountId
      DatabaseInput:
        Name: !Ref CFNDatabaseName
        Description: "AWS Glue database for weather data"
  CFNCrawlerWeather:
    Type: AWS::Glue::Crawler
    Properties:
      Name: !Ref CFNCrawlerName
      Role: <ARN de votre rôle gluelab>
      DatabaseName: !Ref CFNDatabaseName
      Targets:
        S3Targets:
          - Path: "s3://noaa-ghcn-pds/csv/by_year/"
      TablePrefix: !Ref CFNTablePrefixName
      SchemaChangePolicy:
        UpdateBehavior: "UPDATE_IN_DATABASE"
        DeleteBehavior: "LOG"
```

#### Actions :
1. Ouvrez **AWS Cloud9** dans la console AWS.
2. Créez un nouveau fichier appelé `glue-crawler-template.yml`.
3. Collez le modèle YAML ci-dessus dans le fichier.
4. Remplacez `<ARN de votre rôle gluelab>` par l'ARN du rôle que vous avez créé.
5. Enregistrez le fichier.
6. Dans AWS CLI, validez le modèle :
   ```bash
   aws cloudformation validate-template --template-body file://glue-crawler-template.yml
   ```
7. Créez le stack CloudFormation :
   ```bash
   aws cloudformation create-stack --stack-name gluecrawler \
   --template-body file://glue-crawler-template.yml \
   --capabilities CAPABILITY_NAMED_IAM
   ```

---
-------
-------
# Étape 3 : Créer un **crawler AWS Glue** pour explorer les données S3
-------
-------

Le **crawler AWS Glue** est chargé de découvrir automatiquement les schémas des données stockées dans S3 et de les cataloguer dans le **Glue Data Catalog**.

#### Détails :
- **Source de données** : Le dataset **Global Historical Climatology Network Daily (GHCN-D)** est stocké dans un bucket S3 public : `s3://noaa-ghcn-pds/csv/by_year/`.
- **Découverte automatique du schéma** : Le crawler va parcourir les fichiers, identifier les types de données (par exemple CSV) et créer une table dans Glue Data Catalog.
- **Schéma inféré** : Le crawler identifiera automatiquement les colonnes comme `date`, `station`, `type`, et `observation`.

#### Étapes pour créer un crawler :
1. Allez dans la console **AWS Glue**.
2. Cliquez sur **Crawlers** > **Add Crawler**.
3. Donnez un nom au crawler : **Weather Crawler**.
4. Sélectionnez **S3** comme source de données et entrez le chemin S3 : `s3://noaa-ghcn-pds/csv/by_year/`.
5. Choisissez le rôle **gluelab IAM** que vous avez créé pour permettre au crawler d'accéder aux données.
6. Dans la section **Output**, créez une nouvelle base de données appelée **weatherdata**.
7. Exécutez le crawler pour découvrir les schémas.

---
------------
-------

# Étape 4 : Transformer le schéma dans la console AWS Glue
-------
-------

Une fois le crawler exécuté, il aura créé une table avec les schémas des données découvertes. Vous pouvez maintenant modifier ce schéma si nécessaire.

#### Détails :
- Vous pouvez modifier les **noms des colonnes**, ajuster les types de données ou supprimer des colonnes inutiles.
- Dans cet exemple, vous allez renommer des colonnes pour rendre le schéma plus cohérent.

#### Étapes pour modifier le schéma :
1. Allez dans **AWS Glue** > **Databases** > **Tables**.
2. Sélectionnez la table **weatherdata** créée par le crawler.
3. Cliquez sur **Edit Schema**.
4. Changez les noms de colonnes suivants :
   - `id` → `station`
   - `date` → `date`
   - `element` → `type`
   - `data_value` → `observation`
5. Sauvegardez les changements.


---
-------
-------
# Étape 5 : Utiliser **Amazon Athena** pour interroger les données
-------
-------
-------

Amazon Athena permet d'interroger les données stockées dans S3 via des requêtes SQL standard. Athena est idéal pour les **analyses ad hoc** et pour l'exploration rapide de données sans avoir à déplacer ou transformer manuellement les données.

#### Détails :
- Vous allez utiliser Athena pour exécuter des requêtes SQL sur la base de données **weatherdata**.
- Les résultats des requêtes sont stockés dans un **bucket S3**.

#### Étapes pour interroger avec Athena :
1. Accédez à **Amazon Athena** dans la console AWS.
2. Sélectionnez la base de données **weatherdata** créée par AWS Glue.
3. Exécutez la requête suivante pour afficher les températures maximales :
   ```sql
   SELECT date, observation, type
   FROM weatherdata
   WHERE type = 'TMAX';
   ```
4. Spécifiez un bucket S3 pour stocker les résultats des requêtes, comme **data-science-bucket**.
5. Visualisez les résultats de la requête dans Athena.



---
-------
-------
# Étape 6 : Créer une table filtrée avec les données postérieures à 1950
-------
-------

Vous allez maintenant filtrer les données pour ne conserver que celles postérieures à 1950 et stocker les résultats dans un bucket S3 spécifique.

#### Détails :
- Vous utiliserez Athena pour créer une nouvelle table qui ne contient que les données entre 1950 et 2015.
- Les résultats seront stockés dans un bucket S3 nommé **glue-1950-bucket**.

#### Étapes pour créer la table filtrée :
1. Dans **Athena**, exécutez la requête suivante pour créer une nouvelle table :
   ```sql
   CREATE TABLE weatherdata.late20th
   WITH (
      format = 'PARQUET',
      external_location = 's3://glue-1950-bucket/lab3'
   ) AS
   SELECT date, type, observation
   FROM weatherdata
   WHERE date/10000 BETWEEN 1950 AND 2015;
   ```
2. Attendez que la requête soit terminée et vérifiez que les résultats sont bien enregistrés dans le **glue-1950-bucket**.


-----
-------
-------


# Étape 7 : Calculer la température moyenne annuelle dans Athena

Pour obtenir des insights sur les données climatiques, vous allez maintenant calculer la **température maximale moyenne annuelle** à partir des données stockées.

#### Détails :
- Vous créerez une vue pour isoler les températures maximales (`TMAX`).
- Ensuite, vous exécuterez une requête pour obtenir la température moyenne annuelle.

#### Étapes pour calculer la température moyenne annuelle :


### Étape 7 (suite) : Calculer la température moyenne annuelle dans Athena

-------
-------

1. **Créer une vue pour isoler les températures maximales** :
   Vous allez créer une vue dans Athena qui filtre les données pour ne conserver que les observations liées à la température maximale (`TMAX`).

   Exécutez la requête suivante dans Athena :
   ```sql
   CREATE VIEW TMAX AS
   SELECT date, observation, type
   FROM weatherdata.late20th
   WHERE type = 'TMAX';
   ```

   - **Explication** :
     - La vue `TMAX` va créer un sous-ensemble des données, où `type` correspond à `TMAX`, ce qui représente la température maximale pour chaque jour.
   
2. **Calculer la température moyenne annuelle** :
   Après avoir créé la vue, vous allez calculer la moyenne des températures maximales pour chaque année. Exécutez la requête suivante :

   ```sql
   SELECT date/10000 AS Year, AVG(observation)/10 AS MaxTemp
   FROM TMAX
   GROUP BY date/10000
   ORDER BY Year;
   ```

   - **Explication** :
     - La fonction `AVG(observation)/10` calcule la moyenne des observations (températures maximales) en divisant par 10, car les valeurs de température dans le dataset GHCN-D sont stockées en dixièmes de degrés Celsius.
     - `date/10000` permet de grouper les résultats par année, en extrayant la partie année de la date (le format de la date est AAAAMMJJ).
     - Cette requête vous donne la température maximale moyenne pour chaque année entre 1950 et 2015.

3. **Stocker les résultats** :
   Les résultats de la requête sont stockés dans le **bucket S3** que vous avez configuré pour Athena (par exemple, `data-science-bucket`). Cela vous permet de réutiliser ces résultats pour d'autres analyses ou pour les visualiser avec des outils comme **Amazon QuickSight**.

-------
---
-------
-------
# Étape 8 : Utiliser AWS CLI pour exécuter et tester le crawler avec un autre utilisateur
-------
-------
-------

Dans cette étape, vous allez tester l'accès d'un autre utilisateur (par exemple, **Mary**) pour vérifier qu'elle peut exécuter le **crawler AWS Glue** et interagir avec les données en utilisant les permissions IAM assignées.

#### Détails :
- Vous utiliserez **AWS CLI** pour vérifier que Mary peut lister, obtenir et exécuter le crawler AWS Glue.
- Vous allez tester si Mary peut accéder au crawler et exécuter une commande pour extraire, transformer, et charger les données.

#### Étapes pour tester l'accès utilisateur :
1. **Récupérer les informations d'identification de Mary** :
   Si vous travaillez dans un environnement contrôlé, vous pouvez obtenir les informations d’identification de Mary dans la console **CloudFormation** :
   - Allez dans **CloudFormation** > **Stacks** > sélectionnez le stack qui a créé l'environnement.
   - Sous l’onglet **Outputs**, récupérez l’**Access Key** et le **Secret Access Key** de Mary.

2. **Définir les variables d'environnement pour les informations d'identification de Mary** :
   Dans **AWS Cloud9**, définissez les variables d’environnement pour utiliser les informations d’identification de Mary via AWS CLI :
   ```bash
   AK=<Access Key de Mary>
   SAK=<Secret Access Key de Mary>
   ```
   Par exemple :
   ```bash
   export AWS_ACCESS_KEY_ID=$AK
   export AWS_SECRET_ACCESS_KEY=$SAK
   ```

3. **Lister les crawlers disponibles** :
   Vérifiez que Mary a accès aux crawlers en exécutant la commande suivante :
   ```bash
   aws glue list-crawlers
   ```
   Vous devriez voir une sortie semblable à celle-ci :
   ```json
   {
       "CrawlerNames": [
           "Weather",
           "cfn-crawler-weather"
       ]
   }
   ```

4. **Exécuter le crawler avec les informations de Mary** :
   Demandez à Mary d’exécuter le crawler en utilisant AWS CLI. Cette commande va démarrer le **Weather Crawler** :
   ```bash
   aws glue start-crawler --name cfn-crawler-weather
   ```

   Si le crawler s'exécute correctement, vous ne verrez aucune sortie, mais vous pourrez suivre l’état d’avancement dans la console **AWS Glue**.

5. **Vérifier l’état du crawler** :
   Vous pouvez vérifier si le crawler a terminé avec succès en exécutant la commande suivante :
   ```bash
   aws glue get-crawler --name cfn-crawler-weather
   ```

   Vous devriez voir une section `LastCrawl` dans la sortie, indiquant que le crawler a terminé avec succès :
   ```json
   {
       "Crawler": {
           "Name": "cfn-crawler-weather",
           "State": "READY",
           "LastCrawl": {
               "Status": "SUCCEEDED",
               "LogGroup": "/aws-glue/crawlers",
               "StartTime": 1649267649.0
           }
       }
   }
   ```

---
-------
-------

### Conclusion

En suivant ces étapes, vous aurez :

1. **Créé un rôle IAM** pour AWS Glue et les autres services nécessaires (S3, Athena).
2. **Déployé un modèle CloudFormation** pour automatiser la création du crawler et de la base de données AWS Glue.
3. **Exécuté un crawler AWS Glue** pour découvrir automatiquement les schémas des données dans un bucket S3 public.
4. **Modifié les schémas de données** dans la console AWS Glue pour mieux les adapter à vos besoins.
5. **Utilisé Amazon Athena** pour interroger les données et exécuter des analyses SQL directement sur les fichiers dans S3.
6. **Créé des tables filtrées** pour isoler les données post-1950 et stocké les résultats dans un bucket S3 dédié.
7. **Calculé la température maximale moyenne annuelle** en utilisant des vues et des requêtes SQL dans Athena.
8. **Testé l’accès utilisateur** pour vérifier que les bonnes permissions étaient en place pour exécuter le crawler via AWS CLI.

Ces étapes vous permettent d'utiliser pleinement les capacités d'**AWS Glue** et d'**Amazon Athena** pour automatiser et optimiser vos flux de travail d'analyse de données dans AWS. Si vous avez besoin de plus de précisions ou d'autres informations sur une étape spécifique, n'hésitez pas à me le dire !
