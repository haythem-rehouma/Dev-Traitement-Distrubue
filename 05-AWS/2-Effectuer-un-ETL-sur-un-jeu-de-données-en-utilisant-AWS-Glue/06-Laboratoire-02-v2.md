# Version 1.0.21 : Effectuer un ETL sur un jeu de données avec AWS Glue

## Vue d'ensemble et objectifs du lab

Les problèmes de *big data* impliquent souvent un grand nombre de sources de données hétérogènes. En tant qu'analyste de données, il se peut que vous ne connaissiez pas le schéma pour certaines de ces sources. Cela fait référence à l’aspect de la *variété* parmi les cinq V du big data (volume, variété, vélocité, véracité et valeur). Dans ce lab, vous utiliserez AWS Glue pour effectuer l’extraction, la transformation et le chargement (ETL) d’un jeu de données. Vous pouvez diriger AWS Glue vers une source de données, et il peut inférer un schéma en fonction des types de données qu'il découvre. Ensuite, AWS Glue crée un catalogue de données (*Data Catalog*) contenant des métadonnées sur les différentes sources de données.

AWS Glue est similaire à Amazon Athena dans le sens où les données que vous analysez restent dans la source de données. La différence clé est que vous pouvez créer un crawler avec AWS Glue pour découvrir le schéma, puis extraire les données du jeu de données. Vous pouvez également transformer le schéma, puis charger les données dans une base de données AWS Glue pour une analyse ultérieure avec Athena.

Dans ce lab, vous apprendrez à utiliser AWS Glue pour importer un jeu de données à partir d’Amazon Simple Storage Service (Amazon S3). Vous extrairez ensuite les données, transformerez leur schéma, et chargerez le jeu de données dans une base de données AWS Glue pour analyse ultérieure avec Athena.

### Objectifs d'apprentissage

À la fin de ce lab, vous serez capable de :

- Accéder à AWS Glue via la console de gestion AWS et créer un crawler.
- Créer une base de données AWS Glue avec des tables et un schéma en utilisant un crawler.
- Interroger des données dans une base de données AWS Glue en utilisant Athena.
- Créer et déployer un crawler AWS Glue à l’aide d’un modèle AWS CloudFormation.
- Examiner une politique IAM pour permettre aux utilisateurs de lancer un crawler AWS Glue et d’interroger une base de données AWS Glue dans Athena.
- Confirmer qu'un utilisateur avec la politique IAM peut utiliser l'interface en ligne de commande AWS (CLI) pour accéder à la base de données AWS Glue créée par le crawler.
- Confirmer qu'un utilisateur peut exécuter le crawler AWS Glue lorsque les données sources changent.

---

## Durée estimée

Ce lab prendra environ 90 minutes pour être complété.


## Scénario

L'équipe de science des données de votre université vous a demandé de créer une série de preuves de concept (POC) pour utiliser des services AWS afin de répondre à plusieurs besoins en ingénierie et analyse des données. Mary, un membre de l'équipe, a vu ce qu'Athena peut faire pour créer des tables avec des schémas définis et est impressionnée. Elle vous demande s'il est possible d'inférer automatiquement les colonnes et types de données, car définir manuellement le schéma prend beaucoup de temps lorsqu'elle traite de grandes quantités de données variées. Vous voulez développer une POC pour utiliser AWS Glue, qui est conçu pour de tels cas d’usage.

Pour cette POC, Mary vous suggère d’utiliser un jeu de données public : le *Global Historical Climatology Network Daily* (GHCN-D). Ce jeu de données contient des résumés météorologiques quotidiens provenant de stations au sol depuis 1763. Ce jeu de données est disponible publiquement dans un bucket S3.

Mary explique que les paramètres les plus couramment enregistrés dans ce jeu de données sont les températures quotidiennes, les précipitations et les chutes de neige. Ces paramètres sont utiles pour évaluer les risques de sécheresse, d'inondation et de conditions météorologiques extrêmes. Les définitions des données utilisées dans ce lab sont disponibles sur la page du *NOAA Global Historical Climatology Network Daily (GHCN-D) Dataset*.

**Remarque** : À partir d'octobre 2022, le jeu de données a été divisé en sous-jeux de données : `by_year` et `by_station`. Dans ce lab, vous utiliserez `by_year`, disponible à `s3://noaa-ghcn-pds/csv/by_year/`.

---

## Environnement de lab

L'environnement du lab est créé avec un modèle CloudFormation qui sera déployé lorsque vous lancerez le lab. Le modèle CloudFormation crée deux buckets S3 appelés `data-science-bucket` et `glue-1950-bucket`, une politique IAM intitulée `Policy-For-Data-Scientists`, et un rôle IAM intitulé `gluelab`.

---

## Tâches du lab

### Tâche 1 : Utiliser un crawler AWS Glue avec le jeu de données GHCN-D

En tant qu'ingénieur ou analyste de données, vous ne connaissez pas toujours le schéma des données à analyser. AWS Glue est conçu pour ce type de situation. Vous pouvez diriger AWS Glue vers des données stockées sur AWS, et le service découvrira vos données. AWS Glue stockera ensuite les métadonnées associées (par exemple, la définition et le schéma de la table) dans le catalogue de données AWS Glue.

Dans cette tâche, vous allez travailler avec des données disponibles publiquement dans un bucket S3 pour effectuer les opérations suivantes :

- Configurer et créer un crawler AWS Glue.
- Exécuter le crawler pour extraire, transformer et charger les données dans une base de données AWS Glue.
- Examiner les métadonnées d’une table créée par le crawler.
- Modifier le schéma de la table.

#### Étape 1 : Configurer et créer un crawler AWS Glue

1. **Ouvrir la console AWS Glue**  
   Dans la console de gestion AWS, dans la barre de recherche à côté de **Services**, recherchez et sélectionnez **AWS Glue** pour ouvrir la console AWS Glue.

2. **Créer le crawler**  
   Dans le panneau de navigation, sous **Bases de données**, sélectionnez **Tables**, puis cliquez sur **Ajouter des tables avec un crawler**.

   - Pour le **Nom**, saisissez `Weather`.
   - Sous **Tags (facultatif)**, conservez les paramètres par défaut.
   - Cliquez sur **Suivant** en bas de la page.
   
   Configurez la source de données comme suit :

   - **Source de données** : Sélectionnez **S3**.
   - **Emplacement des données S3** : Choisissez **Dans un autre compte**.
   - **Chemin S3** : Saisissez le chemin suivant pour le jeu de données disponible publiquement :  
     `s3://noaa-ghcn-pds/csv/by_year/`.
   - **Exécutions ultérieures du crawler** : Sélectionnez **Explorer tous les sous-dossiers**.
   - Cliquez sur **Ajouter une source de données S3**.

3. **Configurer les rôles IAM**  
   Sur l'écran suivant, pour **Rôle IAM existant**, sélectionnez `gluelab`. Ce rôle est déjà fourni dans l’environnement du lab.

   Extrait YAML pour ce rôle dans le modèle CloudFormation :
   
   ```yaml
   GlueLab:
     Type: AWS::IAM::Role
     Properties:
       RoleName: "gluelab"
       Path: "/"
       AssumeRolePolicyDocument:
         Version: 2012-10-17
         Statement:
           - Effect: Allow
             Principal:
               Service:
                 - glue.amazonaws.com
             Action:
               - sts:AssumeRole
       ManagedPolicyArns:
         - arn:aws:iam::aws:policy/service-role/AWSGlueServiceRole
         - arn:aws:iam::aws:policy/AmazonS3FullAccess
   ```

4. **Créer une base de données AWS Glue**  
   Dans la section **Configuration de sortie**, sélectionnez **Ajouter une base de données**.

   - Dans la nouvelle fenêtre, pour le **Nom**, entrez `weatherdata`.
   - Cliquez sur **Créer une base de données**, puis revenez à l'onglet précédent.
   - Pour **Base de données cible**, sélectionnez `weatherdata`.

   **Astuce** : Si la base de données n'apparaît pas, cliquez sur l'icône de rafraîchissement à côté du menu déroulant.

5. **Configurer la fréquence d'exécution du crawler**  
   Dans la section **Planification du crawler**, laissez la fréquence par défaut sur **À la demande**.

6. **Créer le crawler**  
   Passez en revue la configuration du crawler et cliquez sur **Créer un crawler**. Ensuite, exécutez le crawler pour effectuer les étapes d'extraction et de chargement de l'ETL.

#### Étape 2 : Exécuter le crawler

1. **Exécuter le crawler**  
   Sur la page **Crawlers**, sélectionnez le crawler `Weather` que vous venez de créer, puis cliquez sur **Exécuter**.  
   Le statut du crawler passera à **En cours d’exécution**. Attendez jusqu'à ce que le statut du crawler change en **Prêt** avant de passer à l'étape suivante. Cela peut prendre environ 3 minutes.

#### Étape 3 : Examiner les métadonnées créées par AWS Glue

1. **Examiner la base de données**  
   Dans le panneau de navigation, sélectionnez **Bases de données**.  
   Cliquez sur le lien vers la base de données `weatherdata` que vous avez créée.

2. **Examiner les tables**  
   Dans la section **Tables**, sélectionnez le lien vers la table `by_year`.  
   Examinez les métadonnées capturées par le crawler, notamment le schéma des colonnes découvertes dans le jeu de données importé.

#### Étape 4 : Modifier le schéma

Maintenant, vous allez modifier le schéma de la base de données, qui fait partie de la transformation des données dans le processus ETL.

1. **Modifier le schéma**  
   Dans le coin supérieur droit de la page, sélectionnez **Modifier le schéma** dans le menu **Actions**.

2. **Changer les noms de colonnes**  
   Utilisez le tableau suivant pour modifier les noms des colonnes en sélectionnant la case à cocher pour chaque élément, puis en cliquant sur **Modifier** :

   | Ancien nom   | Nouveau nom |
   | ------------ | ----------- |
   | id           | station     |
   | date         | date        |
   | element      | type        |
   | data_value   | observation |
   | m_flag       | mflag       |
   | q_flag       | qflag       |
   | s_flag       | sflag       |
   | obs_time     | time        |

3. **Mettre à jour le schéma**  
   Après avoir modifié les colonnes, cliquez sur **Mettre à jour le schéma**.  
   Le schéma modifié ressemblera à la capture d’écran suivante avec les nouveaux noms de colonnes.

---

### Résumé de la Tâche 1

Dans cette tâche, vous avez utilisé la console AWS Glue pour créer un crawler. Vous avez dirigé le crawler vers des données stockées dans un bucket S3, et celui-ci a découvert le schéma et extrait les données. Ensuite, les métadonnées (définition et schéma de la table) ont été stockées dans un catalogue de données. Ce processus permet d'inspecter une source de données et d'inférer son schéma automatiquement.

---

### Tâche 2 : Interroger une table à l’aide d'Athena

Maintenant que vous avez créé le catalogue de données, vous pouvez utiliser Athena pour interroger les données via les métadonnées.

Dans cette tâche, vous allez effectuer les actions suivantes :

- Configurer un bucket S3 pour stocker les résultats des requêtes Athena.
- Prévisualiser une table dans Athena.
- Créer une table pour les données après 1950.
- Exécuter une requête sur des données sélectionnées.

#### Étape 1 : Configurer un bucket S3 pour les résultats d'Athena

1. **Naviguer vers la table `by_year`**  
   Dans le panneau de navigation, sous **Bases de données**, sélectionnez **Tables**, puis cliquez sur le lien vers la table `by_year`.

2. **Afficher les données dans Athena**  
   Sélectionnez **Actions** > **Afficher les données**. Une fenêtre contextuelle vous informera que vous serez redirigé vers la console Athena. Cliquez sur **Procéder**.

3. **Configurer l'emplacement des résultats d'Athena**  
   Une fois dans la console Athena, vous verrez un message d'erreur indiquant qu'aucun emplacement de sortie n'a été fourni. Vous devez spécifier un bucket S3 pour stocker les résultats des requêtes.

   - Allez dans l'onglet **Paramètres**, puis cliquez sur **Gérer**.
   - Pour l'emplacement des résultats, choisissez **Parcourir S3** et sélectionnez le bucket nommé `data-science-bucket-XXXXXX`.
   - **Remarque** : Ne sélectionnez pas le bucket `glue-1950-bucket`.

4. **Enregistrer les paramètres**  
   Conservez les paramètres par défaut et cliquez sur **Enregistrer**.

#### Étape 2 : Prévisualiser une table dans Athena

1. **Utiliser l'éditeur Athena**  
   Allez dans l'onglet **Éditeur**. À gauche, sous le panneau de **Données**, vérifiez que la source de données est bien **AwsDataCatalog**.

2. **Prévisualiser la table `by_year`**  
   Sous la section **Tables**, cliquez sur l'icône en forme de trois points à côté de la table `by_year`, puis sélectionnez **Prévisualiser la table**.

3. **Vérifier les résultats**  
   Les premiers enregistrements de la table `weatherdata` s'affichent. Vous pouvez vérifier le temps d'exécution de la requête et la quantité de données scannées pour chaque requête.

#### Étape 3 : Créer une table pour les données postérieures à 1950

1. **Rechercher le bucket pour les résultats**  
   Dans la barre de recherche à côté de **Services**, recherchez **S3** et copiez le nom du bucket `glue-1950-bucket`.

2. **Créer une nouvelle table pour les données après 1950**  
   Revenez à l'éditeur de requêtes Athena, et copiez le code suivant en remplaçant `<glue-1950-bucket>` par le nom du bucket :

   ```sql
   CREATE table weatherdata.late20th
   WITH (
     format='PARQUET', external_location='s3://<glue-1950-bucket>/lab3'
   ) AS SELECT date, type, observation FROM by_year
   WHERE date/10000 BETWEEN 1950 AND 2015;
   ```

3. **Exécuter la requête**  
   Cliquez sur **Exécuter** pour créer la table `late20th`.

4. **Prévisualiser les résultats**  
   Une fois la requête exécutée, vous pouvez prévisualiser la table `late20th` en cliquant sur les trois points à droite de la table, puis sur **Prévisualiser la table**.

#### Étape 4 : Exécuter des requêtes sur la nouvelle table

1. **Créer une vue pour les données TMAX**  
   Exécutez la requête suivante pour créer une vue incluant uniquement les températures maximales (`TMAX`) :

   ```sql
   CREATE VIEW TMAX AS
   SELECT date, observation, type
   FROM late20th
   WHERE type = 'TMAX';
   ```

2. **Prévisualiser la vue**  
   Cliquez sur les trois points à côté de la vue `TMAX` pour prévisualiser ses résultats.

3. **Calculer la température maximale annuelle moyenne**  
   Exécutez la requête suivante pour calculer la température maximale annuelle moyenne :

   ```sql
   SELECT date/10000 AS Year, AVG(observation)/10 AS Max
   FROM TMAX
   GROUP BY date/10000
   ORDER BY date/10000;
   ```

4. **Vérifier les résultats**  
   Les résultats affichent la température maximale moyenne pour chaque année de 1950 à 2015.

---

### Résumé de la Tâche 2

Dans cette tâche, vous avez appris à utiliser Athena pour interroger des tables dans une base de données créée par un crawler AWS Glue. Vous avez créé une table pour inclure uniquement les données après 1950 et avez utilisé le format **Apache Parquet** pour optimiser les requêtes Athena. Ensuite, vous avez créé une vue pour calculer la température maximale annuelle moyenne.

---

### Tâche 3 : Créer un modèle CloudFormation pour un crawler AWS Glue

Dans cette tâche, vous apprendrez à créer et déployer un crawler en utilisant AWS CloudFormation.

#### Étape 1 : Obtenir l'ARN du rôle IAM `gluelab`

1. **Ouvrir la console IAM**  
   Dans la barre de recherche à côté de **Services**, recherchez et sélectionnez **IAM** pour ouvrir la console IAM.

2. **Rechercher le rôle `gluelab`**  
   Dans le panneau de navigation, sélectionnez **Rôles**. Recherchez le rôle **gluelab** et cliquez dessus.

3. **Copier l'ARN**  
   Copiez l'ARN du rôle qui s'affiche dans la section **Résumé**.

#### Étape 2 : Créer un modèle CloudFormation dans AWS Cloud9

1. **Ouvrir AWS Cloud9**  
   Dans la barre de recherche à côté de **Services**, recherchez **Cloud9** et sélectionnez **Ouvrir IDE** pour ouvrir l'environnement de développement intégré.

2. **Créer un nouveau fichier**  
   Dans AWS Cloud9, allez dans **Fichier** > **Nouveau fichier** et enregistrez-le sous le nom `gluecrawler.cf.yml`.

3. **Copier le code du modèle CloudFormation**  
   Copiez et collez le code suivant dans le fichier :

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


    # Ressources définissant les métadonnées pour le catalogue de données
    Resources:
      # Créer une base de données pour contenir les tables créées par le crawler
      CFNDatabaseWeather:
        Type: AWS::Glue::Database
        Properties:
          CatalogId: !Ref AWS::AccountId
          DatabaseInput:
            Name: !Ref CFNDatabaseName
            Description: "Conteneur AWS Glue pour contenir les tables de métadonnées pour le crawler météo."
    
      # Créer un crawler pour explorer les données météorologiques sur un bucket S3 public
      CFNCrawlerWeather:
        Type: AWS::Glue::Crawler
        Properties:
          Name: !Ref CFNCrawlerName
          Role: <GLUELAB-ROLE-ARN>  # Remplacez par l'ARN du rôle `gluelab`
          # Pas de classificateurs, utilisation des classificateurs par défaut
          Description: "Crawler AWS Glue pour explorer les données météorologiques"
          # Pas de planification, exécution à la demande par défaut
          DatabaseName: !Ref CFNDatabaseName
          Targets:
            S3Targets:
              - Path: "s3://noaa-ghcn-pds/csv/by_year/"
          TablePrefix: !Ref CFNTablePrefixName
          SchemaChangePolicy:
            UpdateBehavior: "UPDATE_IN_DATABASE"
            DeleteBehavior: "LOG"
          Configuration: "{\"Version\":1.0,\"CrawlerOutput\":{\"Partitions\":{\"AddOrUpdateBehavior\":\"InheritFromTable\"},\"Tables\":{\"AddOrUpdateBehavior\":\"MergeNewColumns\"}}}"
    ```

Dans ce modèle, le crawler va explorer le jeu de données météorologiques hébergé dans un bucket S3 public, générer une base de données dans AWS Glue, et stocker les métadonnées extraites.

#### Étape 3 : Valider le modèle CloudFormation

1. **Valider le modèle**  
   Dans le terminal AWS Cloud9, exécutez la commande suivante pour valider le modèle CloudFormation :

   ```bash
   aws cloudformation validate-template --template-body file://gluecrawler.cf.yml
   ```

   Si le modèle est valide, la sortie ressemblera à ceci :

   ```json
   {
     "Parameters": [
       {
         "ParameterKey": "CFNCrawlerName",
         "DefaultValue": "cfn-crawler-weather",
         "NoEcho": false
       },
       {
         "ParameterKey": "CFNTablePrefixName",
         "DefaultValue": "cfn_sample_1-weather",
         "NoEcho": false
       },
       {
         "ParameterKey": "CFNDatabaseName",
         "DefaultValue": "cfn-database-weather",
         "NoEcho": false
       }
     ]
   }
   ```

   **Important** : Ne passez pas à l'étape suivante tant que le modèle n'a pas été validé correctement.

#### Étape 4 : Créer la pile CloudFormation

1. **Créer la pile**  
   Exécutez la commande suivante pour créer la pile CloudFormation à partir du modèle validé :

   ```bash
   aws cloudformation create-stack --stack-name gluecrawler --template-body file://gluecrawler.cf.yml --capabilities CAPABILITY_NAMED_IAM
   ```

   **Remarque** : Le paramètre `--capabilities` inclut `CAPABILITY_NAMED_IAM` car nous créons des ressources avec des noms personnalisés, ce qui affecte les permissions.

2. **Vérifier la création de la pile**  
   Une fois la commande exécutée avec succès, vous verrez un ARN de pile dans la sortie, similaire à ceci :

   ```json
   {
     "StackId": "arn:aws:cloudformation:us-east-1:123456789012:stack/gluecrawler/abcdefg-1234-5678-9abc-def123456789"
   }
   ```

   **Astuce** : Pour suivre la progression de la création de la pile, allez dans la console AWS CloudFormation, puis sélectionnez **Stacks** dans le panneau de navigation.

#### Étape 5 : Vérifier les ressources créées

1. **Vérifier la base de données AWS Glue**  
   Exécutez la commande suivante pour vérifier que la base de données AWS Glue a bien été créée dans la pile :

   ```bash
   aws glue get-databases
   ```

   La sortie devrait inclure la base de données créée, par exemple :

   ```json
   {
     "DatabaseList": [
       {
         "Name": "cfn-database-weather",
         "Description": "AWS Glue container to hold metadata tables for the weather crawler",
         "CreateTime": 1649267047.0
       }
     ]
   }
   ```

2. **Vérifier le crawler AWS Glue**  
   Exécutez la commande suivante pour lister les crawlers créés :

   ```bash
   aws glue list-crawlers
   ```

   La sortie devrait inclure les crawlers créés, y compris celui appelé `cfn-crawler-weather` :

   ```json
   {
     "CrawlerNames": [
       "Weather",
       "cfn-crawler-weather"
     ]
   }
   ```

   Pour obtenir plus de détails sur le crawler, exécutez la commande suivante :

   ```bash
   aws glue get-crawler --name cfn-crawler-weather
   ```

   La sortie sera similaire à ceci :

   ```json
   {
     "Crawler": {
       "Name": "cfn-crawler-weather",
       "Role": "gluelab",
       "Targets": {
         "S3Targets": [
           {
             "Path": "s3://noaa-ghcn-pds/csv/by_year/"
           }
         ]
       },
       "DatabaseName": "cfn-database-weather",
       "State": "READY",
       "TablePrefix": "cfn_sample_1-weather"
     }
   }
   ```

---

### Résumé de la Tâche 3

Dans cette tâche, vous avez appris à intégrer un crawler AWS Glue dans un modèle CloudFormation. Vous avez également appris à valider et déployer le modèle via AWS CLI dans AWS Cloud9, ainsi qu'à vérifier les ressources créées, y compris le crawler et la base de données dans AWS Glue.

---

### Tâche 4 : Revoir la politique IAM pour l'accès à Athena et AWS Glue

1. **Examiner la politique IAM**  
   Dans la console IAM, sous **Groupes**, trouvez et sélectionnez le groupe IAM auquel appartient l'utilisateur `mary`, puis examinez la politique associée, intitulée `Policy-For-Data-Scientists`. Assurez-vous que cette politique accorde les permissions nécessaires pour utiliser Athena, AWS Glue, et accéder aux buckets S3.

---

### Tâche 5 : Confirmer que Mary peut accéder et utiliser le crawler AWS Glue

1. **Tester les permissions de Mary avec AWS CLI**  
   Utilisez les informations d'identification de l'utilisateur `mary` pour tester l'accès à AWS Glue via AWS CLI et vérifier que Mary peut exécuter le crawler.

   ```bash
   AWS_ACCESS_KEY_ID=$AK AWS_SECRET_ACCESS_KEY=$SAK aws glue list-crawlers
   ```

2. **Exécuter le crawler**  
   Si `mary` dispose des permissions correctes, exécutez le crawler :

   ```bash
   AWS_ACCESS_KEY_ID=$AK AWS_SECRET_ACCESS_KEY=$SAK aws glue start-crawler --name cfn-crawler-weather
   ```

3. **Vérifier l'état du crawler**  
   Vérifiez que le crawler a bien été exécuté avec succès.

---

### Résumé de la Tâche 5

Cette tâche confirme que `mary`, avec les permissions correctes, peut accéder au crawler AWS Glue et l'exécuter avec succès, grâce à la politique IAM configurée.

---

## Conclusion

Félicitations ! Vous avez terminé le lab. Vous avez appris à créer un crawler AWS Glue, à l'exécuter manuellement et via un modèle CloudFormation, à utiliser Athena pour interroger les données, et à configurer des politiques IAM pour garantir un accès sécurisé. Vous pouvez désormais réutiliser ce processus dans différents environnements AWS.


