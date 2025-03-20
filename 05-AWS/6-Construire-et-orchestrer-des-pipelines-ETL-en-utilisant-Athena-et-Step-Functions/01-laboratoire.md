## Présentation du lab et objectifs

Dans ce laboratoire, vous utiliserez **AWS Step Functions** pour construire un pipeline ETL (Extract, Transform, Load) qui utilise **Amazon S3**, un **AWS Glue Data Catalog**, et **Amazon Athena** pour traiter un grand jeu de données.

Les Step Functions permettent d'automatiser les processus métiers en créant des workflows, également appelés machines à états. Dans ce laboratoire, vous utiliserez Step Functions pour créer un workflow qui invoque Athena pour effectuer une série d'actions, comme exécuter une requête pour vérifier si des tables AWS Glue existent.

Le catalogue de données AWS Glue fournit un stockage persistant des métadonnées, y compris les définitions de tables, les schémas et d'autres informations de contrôle, qui vous aideront à créer le pipeline ETL.

**Athena** est un service de requêtes interactives sans serveur qui simplifie l'analyse des données stockées sur Amazon S3 en utilisant du SQL standard.

Vous allez concevoir un workflow qui vérifiera si des tables AWS Glue existent. Si elles n'existent pas, le workflow invoquera des requêtes supplémentaires d'Athena pour les créer. Si les tables existent, le workflow exécutera une requête supplémentaire d'AWS Glue pour créer une vue dans Athena, qui combinera les données de deux tables. Vous pourrez ensuite interroger cette vue pour découvrir des informations intéressantes basées sur le temps et l'emplacement dans le grand jeu de données.

À la fin de ce laboratoire, vous devrez être capable de :
- Créer et tester un workflow avec Step Functions Studio.
- Créer une base de données et des tables AWS Glue.
- Stocker des données sur Amazon S3 au format Parquet pour économiser de l'espace de stockage et accélérer la lecture des données.
- Partitionner des données sur Amazon S3 et utiliser la compression Snappy pour optimiser les performances.
- Créer une vue dans Athena.
- Ajouter une vue Athena à un workflow de Step Functions.
- Construire un pipeline ETL en utilisant Step Functions, Amazon S3, Athena et AWS Glue.

### Durée

Ce laboratoire nécessite environ **120 minutes** pour être complété.

---

### Restrictions des services AWS

Dans cet environnement de laboratoire, l'accès aux services et actions AWS peut être limité aux seuls services nécessaires pour accomplir les instructions du laboratoire. Vous pourriez rencontrer des erreurs si vous essayez d'accéder à d'autres services ou d'effectuer des actions non décrites dans ce laboratoire.

---

### Scénario

Précédemment, vous avez créé une preuve de concept (PoC) pour démontrer comment utiliser AWS Glue pour inférer un schéma de données et ajuster manuellement les noms des colonnes. Ensuite, vous avez utilisé Athena pour interroger les données. Bien que cette approche plaise à Mary, elle doit effectuer de nombreuses étapes manuelles à chaque début de projet. Elle vous a demandé de créer un pipeline de données réutilisable qui l'aidera à démarrer rapidement de nouveaux projets de traitement de données.

L'un des projets de Mary consiste à étudier les données de taxis de New York. Elle connaît les noms des colonnes des données de la table et a déjà créé des vues et des commandes SQL d'ingestion pour vous. Elle souhaite étudier les modèles d'utilisation des taxis à New York au début de 2020.

Mary vous a demandé de stocker les données de la table partitionnées par mois, au format **Parquet** avec compression **Snappy** pour favoriser l'efficacité et réduire les coûts. Comme il s'agit d'une PoC, Mary accepte que vous utilisiez des valeurs codées en dur pour les noms des colonnes, les partitions, les vues et les informations de bucket S3.

Mary a fourni les éléments suivants :
- Des liens pour accéder aux données des taxis.
- Les partitions qu'elle souhaite créer (pickup_year et pickup_month).
- Des scripts SQL d'ingestion.
- Un script qui créera une vue en SQL qu'elle veut utiliser pour ce projet.

Lorsque vous commencez le laboratoire, l'environnement contient les ressources montrées dans le diagramme suivant.

---

### Diagramme d'architecture de début de lab

*(diagramme montrant les ressources initiales disponibles dans le laboratoire)*
![image](https://github.com/user-attachments/assets/8576d4ea-4007-4c3a-a5b8-63fc3985212b)


---

### Diagramme d'architecture à la fin du lab

*( diagramme montrant les ressources créées après l'exécution complète du lab)*
![image](https://github.com/user-attachments/assets/3c71d551-8f6f-47bd-9e3c-4912caff79de)


---

### Démarrage

Vous avez décidé d'exploiter la flexibilité des Step Functions pour créer la logique du pipeline ETL. Avec Step Functions, vous pouvez gérer les exécutions initiales, où les données de la table et la vue SQL n'existent pas, ainsi que les exécutions ultérieures, où les tables et les vues existent déjà.

Commençons !

---

### Accéder à la console de gestion AWS

1. En haut de ces instructions, choisissez **Démarrer le Lab**.
   - La session de laboratoire démarre.
   - Un minuteur s'affiche en haut de la page et montre le temps restant de la session.
   - Astuce : pour rafraîchir la durée de la session à tout moment, choisissez à nouveau **Démarrer le Lab** avant que le minuteur n'atteigne 0:00.

2. Pour vous connecter à la console de gestion AWS, choisissez le lien **AWS** en haut à gauche.
   - Un nouvel onglet du navigateur s'ouvre et vous connecte à la console.
   - Astuce : Si un nouvel onglet ne s'ouvre pas, un message peut apparaître en haut de votre navigateur, indiquant que votre navigateur empêche le site d'ouvrir des fenêtres contextuelles. Choisissez la bannière ou l'icône, puis choisissez **Autoriser les fenêtres contextuelles**.

------------------------------------------------------
### Tâche 1 : Analyser les ressources existantes et charger les données sources
------------------------------------------------------

#### Ouvrir les consoles de services AWS nécessaires

Dans cette première tâche, vous allez analyser un rôle **IAM** et un bucket **S3** qui ont été créés pour vous. Ensuite, vous copierez les données sources des taxis à partir d'un bucket public S3 dans votre bucket. Vous utiliserez ces données plus tard dans le laboratoire lorsque vous créerez un workflow Step Functions.

1. Ouvrez toutes les consoles de services AWS que vous utiliserez pendant ce laboratoire.
   - Astuce : Comme vous utiliserez les consoles de nombreux services AWS tout au long de ce laboratoire, il sera plus facile d'avoir chaque console ouverte dans un onglet séparé du navigateur.

2. Dans la barre de recherche à droite de **Services**, recherchez **Step Functions**.
   - Ouvrez le menu contextuel (clic droit) sur l'entrée Step Functions qui apparaît dans les résultats de recherche et choisissez l'option pour ouvrir le lien dans un nouvel onglet.

3. Répétez ce processus pour ouvrir les consoles AWS des services supplémentaires suivants :
   - S3
   - AWS Glue
   - Athena
   - Cloud9
   - IAM

4. Confirmez que vous avez maintenant chaque console de service AWS ouverte dans des onglets de navigateur distincts.

---

### Analyse du rôle IAM existant

1. Dans la console **IAM**, dans le panneau de navigation, choisissez **Roles**.
2. Recherchez **StepLabRole** et choisissez le lien pour le rôle lorsqu'il apparaît.
3. Dans l'onglet **Permissions**, développez et examinez la politique **Policy-For-Step IAM** qui est attachée au rôle.





### Analyse du rôle IAM existant (suite)

En analysant la politique IAM attachée au rôle **StepLabRole**, vous constaterez que cette politique autorise le workflow Step Functions à faire des appels vers les services **Athena**, **Amazon S3**, **AWS Glue**, et **AWS Lake Formation**. Cela signifie que lorsque vous créerez le workflow dans une prochaine tâche, il pourra interagir avec ces services sans restriction.

---

### Analyse du bucket S3 existant

1. Dans la console **S3**, dans la liste des buckets, choisissez le lien pour le bucket dont le nom contient **gluelab**.
2. Remarquez qu'il ne contient actuellement aucun objet. Plus tard dans ce laboratoire, vous ferez référence à ce bucket dans le workflow Step Functions que vous configurerez.
3. Copiez le nom du bucket dans un fichier texte pour une utilisation ultérieure dans ce laboratoire.

---

### Connexion à l'IDE AWS Cloud9

1. Dans la console **Cloud9**, sur la page **Your environments**, sous **Cloud9 Instance**, choisissez **Open IDE**.

---

### Charger les données dans votre bucket à partir du jeu de données source

Vous allez maintenant charger les données sources de taxis dans votre bucket S3.

1. Exécutez les commandes suivantes dans le terminal bash de Cloud9. Remplacez `<FMI_1>` par le nom de votre bucket réel (celui qui contient **gluelab**).

    ```bash
    mybucket="<FMI_1>"
    echo $mybucket
    ```

    Astuce : Vous pourriez être invité à confirmer le collage sécurisé de texte sur plusieurs lignes. Pour désactiver cette invite à l'avenir, décochez **Ask before pasting multiline code**. Ensuite, choisissez **Paste**.

    **Analyse** : Ces commandes permettent de stocker le nom de votre bucket dans une variable shell. Vous avez ensuite affiché la valeur de cette variable dans le terminal. Cela sera utile lorsque vous exécuterez les prochaines commandes.

2. Copiez les données des taxis jaunes de janvier dans un préfixe (dossier) de votre bucket appelé **nyctaxidata/data**.

    ```bash
    wget -qO- https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1/step-lab/yellow_tripdata_2020-01.csv | aws s3 cp - "s3://$mybucket/nyctaxidata/data/yellow_tripdata_2020-01.csv"
    ```

    **Remarque** : Cette commande prend environ 20 secondes à s'exécuter. Le fichier que vous copiez a une taille d'environ 500 Mo. Attendez que l'invite du terminal s'affiche à nouveau avant de continuer.

3. Copiez les données des taxis jaunes de février dans le même dossier de votre bucket.

    ```bash
    wget -qO- https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1/step-lab/yellow_tripdata_2020-02.csv | aws s3 cp - "s3://$mybucket/nyctaxidata/data/yellow_tripdata_2020-02.csv"
    ```

    **Astuce** : Dans une solution en production, vous incluriez probablement plusieurs années de données, mais pour cette preuve de concept (PoC), l'utilisation de deux mois de données suffira.

4. Copiez les informations de localisation (table de correspondance) dans un préfixe de votre bucket appelé **nyctaxidata/lookup**.

    **Important** : L'espace dans le nom du fichier **taxi _zone_lookup.csv** est intentionnel.

    ```bash
    wget -qO- https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1/step-lab/taxi+_zone_lookup.csv | aws s3 cp - "s3://$mybucket/nyctaxidata/lookup/taxi _zone_lookup.csv"
    ```

---

### Analyse de la structure des données que vous avez copiées

Les données dans la table de correspondance (lookup table) ont la structure suivante. Voici les premières lignes du fichier :

```
"LocationID","Borough","Zone","service_zone"
1,"EWR","Newark Airport","EWR"
2,"Queens","Jamaica Bay","Boro Zone"
3,"Bronx","Allerton/Pelham Gardens","Boro Zone"
4,"Manhattan","Alphabet City","Yellow Zone"
5,"Staten Island","Arden Heights","Boro Zone"
6,"Staten Island","Arrochar/Fort Wadsworth","Boro Zone"
7,"Queens","Astoria","Boro Zone"
8,"Queens","Astoria Park","Boro Zone"
```

**Analyse** : La structure est définie par la liste des noms de colonnes sur la première ligne. Mary est familière avec ces noms de colonnes ; par conséquent, les commandes SQL qu'elle a fournies fonctionneront sans modification plus tard dans le laboratoire.

La structure des fichiers de données des taxis jaunes pour janvier et février est similaire à celle-ci :

```
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount,congestion_surcharge
1,2020-01-01 00:28:15,2020-01-01 00:33:03,1,1.20,1,N,238,239,1,6,3,0.5,1.47,0,0.3,11.27,2.5
1,2020-01-01 00:35:39,2020-01-01 00:43:04,1,1.20,1,N,239,238,1,7,3,0.5,1.5,0,0.3,12.3,2.5
1,2020-01-01 00:47:41,2020-01-01 00:53:52,1,.60,1,N,238,238,1,6,3,0.5,1,0,0.3,10.8,2.5
```

Comme pour le fichier de table de correspondance, la première ligne de chaque fichier définit les noms de colonnes.

---

**Félicitations !** Dans cette tâche, vous avez chargé avec succès les données sources. Vous pouvez maintenant commencer à construire le pipeline.

------------------------------------------------------
### Tâche 2 : Automatiser la création d'une base de données AWS Glue
------------------------------------------------------


Dans cette tâche, vous allez créer un workflow Step Functions qui utilisera Athena pour vérifier si une base de données AWS Glue existe. Si la base de données n'existe pas, Athena la créera.

#### Commencer à créer un workflow

1. Dans la console **Step Functions**, pour ouvrir le panneau de navigation, choisissez l'icône de menu (☰), puis choisissez **State machines**.
2. Choisissez **Create state machine**.
3. Choisissez **Cancel** lorsque l'écran des templates apparaît.
   - L'interface **Step Functions Workflow Studio** s'affiche.

#### Conception du workflow en utilisant l'interface Workflow Studio

1. Si un message **Welcome to Workflow Studio** apparaît, fermez-le en choisissant l'icône **X**.
2. Remarquez qu'un workflow de démarrage, avec des tâches **Start** et **End**, est déjà défini, comme montré dans l'image ci-dessous.

   *(Ajouter une image montrant le workflow de démarrage avec les tâches Start et End)*

3. Dans le panneau **Actions** à gauche, recherchez **Athena**.
4. Faites glisser la tâche **StartQueryExecution** vers le canevas, entre les tâches **Start** et **End**, comme illustré ci-dessous.

   *(Ajouter une image montrant la tâche StartQueryExecution sur le canevas)*

#### Configurer la tâche

1. Dans le panneau **Inspector** à droite :
   - Changez le nom de l'état en **Create Glue DB**.
   - Gardez le type d'intégration comme **Optimized**.
   - Pour les paramètres de l'API, remplacez le code JSON par défaut par le suivant. Remplacez `<FMI_1>` par le nom réel de votre bucket (celui contenant **gluelab**).

     ```json
     {
         "QueryString": "CREATE DATABASE if not exists nyctaxidb",
         "WorkGroup": "primary",
         "ResultConfiguration": {
             "OutputLocation": "s3://<FMI_1>/athena/"
         }
     }
     ```

2. Sélectionnez **Wait for task to complete - optional**.
   - **Remarque** : Cela garantit que le workflow attendra que la tâche soit terminée avant de continuer vers d'autres tâches en aval. Cette tâche est terminée lorsque Athena vérifie que la base de données existe ou la crée.
3. Gardez **Next state** sur **Go to end**.
4. En haut de la page, choisissez **Create**.

---

### Tester le workflow

1. Maintenant que vous avez créé un workflow, exécutez-le et observez ce qui se passe lors de cette première exécution.
   - Choisissez **Start execution**.
   - Pour **Name**, entrez **TaskTwoTest** et choisissez **Start execution**.

   **

Important** : Assurez-vous de nommer vos tests **Start execution** exactement comme indiqué dans ces instructions, sinon vous pourriez ne pas recevoir la totalité des points lorsque vous soumettez le laboratoire pour un score.

2. Dans l'onglet **Details** en haut de la page, le statut indique d'abord **Running**.

   *(Ajouter une image montrant l'état Create Glue DB en bleu dans l'inspecteur de graphe)*

3. Attendez une minute ou deux pendant que le workflow s'exécute.
   - Lorsque l'étape **Create Glue DB** devient verte, cela indique que l'étape a réussi.

   *(Ajouter une image montrant l'étape Create Glue DB en vert dans l'inspecteur de graphe)*


























### Vérification de la création des fichiers de résultats dans le bucket S3

1. Dans la console **S3**, choisissez le lien pour le bucket **gluelab**. Si vous êtes déjà sur cette page, utilisez l'icône de **rafraîchissement** pour actualiser la page.
2. Vous devriez voir un nouveau préfixe (dossier) nommé **athena** dans le bucket.
3. Choisissez le lien **athena** pour voir son contenu.
   - Le dossier contient un fichier texte. Remarquez que la taille du fichier est de 0 B, ce qui indique que le fichier est vide.

---

### Vérification de la création de la base de données AWS Glue

1. Dans la console **AWS Glue**, dans le panneau de navigation, sous **Data Catalog**, choisissez **Databases**.
2. Sélectionnez la base de données **nyctaxidb**.
   - Remarquez que la base de données ne contient actuellement aucune table. Cela est prévu, et vous ajouterez des étapes supplémentaires dans le workflow pour créer les tables. Cependant, vous avez déjà fait de grands progrès !

---

**Félicitations !** Dans cette tâche, vous avez créé avec succès une base de données AWS Glue en utilisant un workflow Step Functions.

------------------------------------------------------
### Tâche 3 : Création de la tâche pour vérifier si les tables existent dans la base de données AWS Glue
------------------------------------------------------

Dans cette tâche, vous allez mettre à jour le workflow afin qu'il vérifie si des tables existent dans la base de données AWS Glue que vous venez de créer.

#### Ajouter une nouvelle tâche au workflow

1. Dans la console **Step Functions**, choisissez la machine d'état **WorkflowPOC**, puis choisissez **Edit**.
2. Dans le panneau **Actions**, recherchez **Athena**.
3. Faites glisser une autre tâche **StartQueryExecution** sur le canevas entre la tâche **Create Glue DB** et la tâche **End**.

---

#### Configurer la tâche et enregistrer les modifications

1. Avec la nouvelle tâche **StartQueryExecution** sélectionnée, dans le panneau **Inspector**, changez le nom de l'état en **Run Table Lookup**.
2. Après avoir renommé l'état, le workflow s'affichera comme indiqué dans l'image ci-dessous.

   *(Ajouter une image montrant la tâche Run Table Lookup entre Create Glue DB et End)*

3. Pour les paramètres de l'API, remplacez le code JSON par défaut par le suivant. Remplacez `<FMI_1>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "show tables in nyctaxidb",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_1>/athena/"
        }
    }
    ```

4. Sélectionnez **Wait for task to complete - optional**.
5. Gardez **Next state** sur **Go to end**.
6. En haut de la page, choisissez **Save**.

---

#### Tester le workflow mis à jour

1. Choisissez **Start execution**.
2. Pour **Name**, entrez **TaskThreeTest**, puis choisissez **Start execution**.
   - Observez le workflow s'exécuter, chaque tâche passant du blanc au bleu, puis au vert dans la section **Graph view**. L'image ci-dessous montre le graphe après la réussite du workflow.

   *(Ajouter une image montrant le workflow avec les tâches Create Glue DB et Run Table Lookup réussies)*

3. Dans la section **Events history**, remarquez que le statut de chaque tâche est fourni ainsi que la durée d'exécution de chaque tâche.
   - Le workflow prend environ 1 minute pour s'exécuter, et il ne trouvera aucune table.

---

### Vérification de la sortie de la requête

1. Après l'exécution du workflow, dans la section **Graph view**, choisissez la tâche **Run Table Lookup**.
2. Dans le panneau **Details** à droite, choisissez l'onglet **Output**.
   - Remarquez que la tâche a généré un **QueryExecutionId**. Vous utiliserez cette information dans la tâche suivante.

3. Dans la console **S3**, choisissez le lien pour le bucket **gluelab**, puis choisissez le préfixe **athena**.
   - Remarquez que le dossier (préfixe) contient désormais plus de fichiers. 
   - **Astuce** : Vous devrez peut-être actualiser l'onglet du navigateur pour les voir.

4. Les fichiers **.txt** sont vides, mais un fichier de métadonnées est présent et contient certaines données. AWS Glue utilisera ce fichier de métadonnées en interne.

---

**Félicitations !** Dans cette tâche, vous avez mis à jour le workflow en ajoutant une tâche qui vérifie si des tables existent dans la base de données AWS Glue.

---

### Tâche 4 : Ajouter une logique de routage au workflow en fonction de l'existence des tables AWS Glue

Dans cette tâche, vous allez référencer l'ID d'exécution renvoyé par la tâche **Run Table Lookup** pour vérifier les tables existantes dans la base de données AWS Glue. Vous allez également ajouter un état de choix (**choice state**) pour déterminer la route logique à suivre en fonction du résultat de la tâche précédente.

---

#### Mettre à jour le workflow pour obtenir les résultats de la requête

1. Dans la console **Step Functions**, choisissez la machine d'état **WorkflowPOC**, puis choisissez **Edit**.
2. Dans le panneau **Actions**, recherchez **Athena**.
3. Faites glisser une tâche **GetQueryResults** sur le canevas entre la tâche **Run Table Lookup** et la tâche **End**.
   - **Attention** : N'utilisez pas une tâche **GetQueryExecution**.

4. Avec la tâche **GetQueryResults** sélectionnée, changez le nom de l'état en **Get lookup query results**.

5. Pour les paramètres de l'API, remplacez le contenu existant par ce qui suit :

    ```json
    {
        "QueryExecutionId.$": "$.QueryExecution.QueryExecutionId"
    }
    ```

6. **Analyse** : Cette tâche utilisera l'ID d'exécution de la requête fourni par la tâche précédente. En passant cette valeur, la prochaine tâche (que vous n'avez pas encore ajoutée) pourra utiliser cette valeur pour évaluer si des tables ont été trouvées.

7. Choisissez **{} Code**.
8. Confirmez la définition. Elle devrait ressembler au code JSON suivant où les espaces réservés `<FMI_1>` contiennent votre vrai nom de bucket **gluelab**.

    ```json
    {
        "Comment": "A description of my state machine",
        "StartAt": "Create Glue DB",
        "States": {
            "Create Glue DB": {
                "Type": "Task",
                "Resource": "arn:aws:states:::athena:startQueryExecution.sync",
                "Parameters": {
                    "QueryString": "CREATE DATABASE if not exists nyctaxidb",
                    "WorkGroup": "primary",
                    "ResultConfiguration": {
                        "OutputLocation": "s3://<FMI_1>/athena/"
                    }
                },
                "Next": "Run Table Lookup"
            },
            "Run Table Lookup": {
                "Type": "Task",
                "Resource": "arn:aws:states:::athena:startQueryExecution.sync",
                "Parameters": {
                    "QueryString": "show tables in nyctaxidb",
                    "WorkGroup": "primary",
                    "ResultConfiguration": {
                        "OutputLocation": "s3://<FMI_1>/athena/"
                    }
                },
                "Next": "Get lookup query results"
            },
            "Get lookup query results": {
                "Type": "Task",
                "Resource": "arn:aws:states:::athena:getQueryResults",
                "Parameters": {
                    "QueryExecutionId.$": "$.QueryExecution.QueryExecutionId"
                },
                "End": true
            }
        }
    }
    ```

9. Choisissez **Save**.

---

#### Ajouter un état de choix au workflow

1. Choisissez **Design** en haut de la page.
2. Dans le panneau **Actions**, choisissez l'onglet **Flow**.
3. Faites glisser un état de **Choice** (choix) sur le canevas entre la tâche **Get lookup query results** et la tâche **End**.

4. Avec l'état **Choice** sélectionné, changez le nom de l'état en **ChoiceStateFirstRun**.
5. Dans la section **Choice Rules**, pour la règle n°1, choisissez l'icône d'édition et configurez ce qui suit :
   - Choisissez **Add conditions**.
   - Conservez la condition par défaut **Simple condition**.
   - Pour **Not**, choisissez **NOT**.
   - Pour **Variable**, entrez la valeur suivante :

     ```json
     $.ResultSet.Rows[0].Data[0].VarCharValue
     ```

   - Pour **Operator**, choisissez **is present**.
   - Assurez-vous que vos paramètres correspondent à l'image suivante.

     *(Ajouter une image montrant la configuration de la règle de choix)*

6. Choisissez **Save conditions**.

---

#### Ajouter deux états de passage (Pass States) au workflow

1. Faites glisser un état **Pass** sur le canevas après l'état **ChoiceStateFirstRun**, à gauche sous la flèche étiquetée **not**.
   - Avec l'état **Pass** sélectionné, changez le nom de l'état en **

REPLACE ME TRUE STATE**.

     **Remarque** : C'est un nom temporaire que vous modifierez plus tard.

2. Faites glisser un autre état **Pass** sur le canevas après l'état **ChoiceStateFirstRun**, à droite sous la flèche étiquetée **Default**.
   - Avec l'état **Pass** sélectionné, changez le nom de l'état en **REPLACE ME FALSE STATE**.

3. Votre canevas de workflow devrait maintenant ressembler à l'image suivante :

   *(Ajouter une image montrant le workflow avec deux états de passage après ChoiceStateFirstRun)*

4. Choisissez **Save**.

---

**Analyse** : Lorsque vous exécutez le workflow et que la tâche **Get lookup query results** est terminée, l'état de choix évaluera les résultats de la dernière requête. Si aucune table n'est trouvée (logique évaluée par **$.ResultSet.Rows[0].Data[0].VarCharValue**), le workflow empruntera la route **REPLACE ME TRUE STATE**. Dans la prochaine tâche, vous remplacerez cet état par un processus pour créer des tables.

Sinon, si des tables sont trouvées, le workflow empruntera la route **Default** (**REPLACE ME FALSE STATE**). Plus tard dans ce laboratoire, vous remplacerez cet état par un processus pour vérifier si de nouvelles données (par exemple, les données des taxis de février) sont présentes et pour les insérer dans une table existante.

---

**Félicitations !** Dans cette tâche, vous avez ajouté un état de choix pour évaluer les résultats de la tâche **Get lookup query results**.





--------------------------------------------------
### Tâche 5 : Création de la table AWS Glue pour les données des taxis jaunes
--------------------------------------------------

Dans cette tâche, vous allez définir la logique dans le workflow pour créer des tables AWS Glue si elles n'existent pas déjà.

#### Ajouter une autre tâche **Athena StartQueryExecution** au workflow et la configurer pour créer une table

1. Dans l'onglet **Actions**, recherchez **athena**.
2. Faites glisser une tâche **StartQueryExecution** sur le canevas entre l'état **ChoiceStateFirstRun** et l'état **REPLACE ME TRUE STATE**.
3. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Run Create data Table Query**.
4. Pour le type d'intégration, laissez **Optimized** sélectionné.
5. Pour les paramètres de l'API, remplacez le code JSON par défaut par le suivant. Remplacez `<FMI_1>` et `<FMI_2>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "CREATE EXTERNAL TABLE nyctaxidb.yellowtaxi_data_csv(  vendorid bigint,   tpep_pickup_datetime string,   tpep_dropoff_datetime string,   passenger_count bigint,   trip_distance double,   ratecodeid bigint,   store_and_fwd_flag string,   pulocationid bigint,   dolocationid bigint,   payment_type bigint,   fare_amount double,   extra double,   mta_tax double,   tip_amount double,   tolls_amount double,   improvement_surcharge double,   total_amount double,   congestion_surcharge double) ROW FORMAT DELIMITED   FIELDS TERMINATED BY ',' STORED AS INPUTFORMAT   'org.apache.hadoop.mapred.TextInputFormat' OUTPUTFORMAT   'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat' LOCATION  's3://<FMI_1>/nyctaxidata/data/' TBLPROPERTIES (  'skip.header.line.count'='1')",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_2>/athena/"
        }
    }
    ```

6. **Analyse** : Vous avez examiné la structure des fichiers sources que vous avez copiés dans votre bucket **gluelab**. Les fichiers **yellow_tripdata_2020-01.csv** et **yellow_tripdata_2020-02.csv** sont au format CSV, avec des colonnes telles que **vendorid**, **tpep_pickup_datetime**, etc. Le fichier CSV ne définit pas les types de données pour chaque colonne, mais votre table AWS Glue les définit (par exemple, **bigint**, **string**). En définissant la table comme **EXTERNAL**, vous indiquez que les données resteront dans Amazon S3, à l'emplacement défini par la clause **LOCATION** (s3://<gluelab-bucket>/nyctaxidata/data/).

7. Sélectionnez **Wait for task to complete - optional**.
    - **Remarque** : Il est important que la table soit entièrement créée avant que le workflow ne continue.
8. Pour **Next state**, choisissez **Go to end**.
9. Sur le canevas, sélectionnez l'état **REPLACE ME TRUE STATE** et supprimez-le en appuyant sur la touche **Delete**.
    - **Important** : Vérifiez que l'état **REPLACE ME TRUE STATE** n'est plus sur le canevas.

10. Votre workflow devrait maintenant ressembler à l'image suivante :

    *(Ajouter une image montrant le workflow avec la tâche Run Create data Table Query)*

11. Choisissez **Save**.

---

#### Tester le workflow

1. Choisissez **Start execution**.
2. Pour **Name**, entrez **TaskFiveTest**, puis choisissez **Start execution**.
    - Cela prendra quelques minutes pour que chaque étape du workflow passe du blanc au bleu, puis au vert. Attendez que le workflow se termine avec succès.

    - Cette exécution ne créera pas de nouvelle base de données, mais comme elle ne trouvera aucune table dans la base de données, elle empruntera la route avec la tâche **Run Create data Table Query**, comme montré dans l'image ci-dessous.

    *(Ajouter une image montrant le workflow avec la route Run Create data Table Query complétée)*

---

### Vérification de la création de la table dans la base de données AWS Glue lors de la première exécution du workflow

1. Dans la console **S3**, accédez au contenu du dossier **athena** dans votre bucket **gluelab**.
    - Remarquez que le dossier contient un autre fichier de métadonnées et des fichiers texte supplémentaires vides.
    - **Remarque** : Les fichiers texte vides sont des fichiers de sortie basiques des tâches Step Functions. Vous pouvez les ignorer.

2. Dans la console **AWS Glue**, dans le panneau de navigation, choisissez **Tables**.
    - Remarquez qu'une table **yellowtaxi_data_csv** existe désormais. Il s'agit de la table AWS Glue qu'Athena a créée lorsque votre workflow Step Functions a invoqué la tâche **Run Create data Table Query**.
    - Pour afficher les détails du schéma, choisissez le lien pour la table **yellowtaxi_data_csv**.

    - Le schéma ressemble à l'image suivante :

    *(Ajouter une image du schéma de la table des données de taxis jaunes)*

---

#### Exécuter à nouveau le workflow pour tester l'autre route de choix

1. Dans la console **Step Functions**, choisissez le lien pour la machine d'état **WorkflowPOC**.
2. Choisissez **Start execution**.
    - Pour **Name**, entrez **NewTest**, puis choisissez **Start execution** à nouveau.
    - **Analyse** : Vous voulez vous assurer que, si le workflow trouve la nouvelle table (ce qui devrait être le cas cette fois-ci), il emprunte l'autre route de choix et invoque l'état **REPLACE ME FALSE STATE**.

    - Le workflow terminé ressemblera à l'image ci-dessous.

    *(Ajouter une image montrant le workflow avec la route REPLACE ME FALSE STATE complétée)*

3. Cette exécution n'a pas recréé la base de données ni tenté d'écraser la table créée lors de l'exécution précédente. Step Functions a cependant généré des fichiers de sortie dans Amazon S3 avec des métadonnées AWS Glue mises à jour.

---

**Félicitations !** Dans cette tâche, vous avez créé une table AWS Glue pointant vers les données des taxis jaunes.

--------------------------------------------------
### Tâche 6 : Création de la table AWS Glue pour les données de correspondance (lookup data)
--------------------------------------------------

Dans cette tâche, vous allez créer une autre table AWS Glue en mettant à jour et en exécutant le workflow Step Functions. La nouvelle table fera référence au fichier source **taxi_zone_lookup.csv** stocké dans Amazon S3. Une fois cette table créée, vous pourrez combiner la table de données des taxis jaunes avec la table de correspondance pour mieux comprendre les données.

Rappelez-vous que la table de correspondance contient des informations sur les lieux d'activité des taxis. Voici un exemple de la structure des colonnes et des premières lignes de données dans le fichier CSV formaté :

```
"LocationID","Borough","Zone","service_zone"
1,"EWR","Newark Airport","EWR"
```

La requête utilisera à nouveau **CTAS** (Create Table As Select) pour qu'Athena crée une table externe.

---

#### Mettre à jour le workflow pour créer la table de correspondance

1. Toujours dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour ouvrir la machine d'état **WorkflowPOC** dans **Workflow Studio**.
2. Dans le panneau **Actions**, recherchez **athena**.
3. Faites glisser une tâche **StartQueryExecution** entre la tâche **Run Create data Table Query** et l'état **End**.

4. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Run Create lookup Table Query**.
5. Pour les paramètres de l'API, remplacez le code JSON par défaut par ce qui suit. Remplacez `<FMI_1>` et `<FMI_2>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "CREATE EXTERNAL TABLE nyctaxidb.nyctaxi_lookup_csv(  locationid bigint,   borough string,   zone string,   service_zone string,   latitude double,   longitude double)ROW FORMAT DELIMITED   FIELDS TERMINATED BY ',' STORED AS INPUTFORMAT   'org.apache.hadoop.mapred.TextInputFormat' OUTPUTFORMAT   'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'LOCATION  's3://<FMI_1>/nyctaxidata/lookup/' TBLPROPERTIES (  'skip.header.line.count'='1')",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_2>/athena/"
        }
    }
    ```

6. Sélectionnez **Wait for task to complete - optional**.
7. Choisissez **Save**.

---

#### Supprimer la table AWS Glue existante

Avant d'exécuter le workflow, vous devez d'abord supprimer la table que le workflow a précédemment créée.

1. Dans la console **AWS Glue**, dans le panneau de navigation, choisissez **Tables**.
2. Sélectionnez la table **yellowtaxi_data_csv**.
3. Choisissez **Delete**.
4. Lorsque vous êtes invité à confirmer, choisissez **Delete** à nouveau.

---

#### Exécuter le workflow mis à jour

1. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour exécuter la machine d'état **WorkflowPOC**. Nommez le test **TaskSixTest**.
2. Attendez que le workflow se termine avec succès.

    - L'image ci-dessous montre le workflow une fois terminé.

    *(Ajouter une image montrant le workflow avec la tâche Run Create lookup Table Query complétée)*

3. Dans la console **AWS Glue**, dans le panneau de navigation, choisissez **Tables**.
    - La base de données AWS Glue contient maintenant deux tables. La dernière exécution du workflow a recréé la table **yellowtaxi_data_csv** et a créé la table **nyctaxi_lookup_csv**.

    **Remarque** : Cela peut prendre une ou deux minutes pour que les tables apparaissent.

4. Observez le schéma de la table de correspondance (lookup table), qui ressemble à l'image ci-dessous.

   *(Ajouter une image montrant le schéma de la table de correspondance)*

---

**Félicitations !** Dans cette tâche, vous avez utilisé un workflow Step Functions pour créer deux tables dans la base de données AWS Glue.

--------------------------------------------------
### Tâche 7 : Optimisation du format des données et utilisation de la compression
--------------------------------------------------

Dans cette tâche, vous allez optimiser les tables pour qu'elles soient plus efficaces à utiliser pour les parties prenantes. Vous allez modifier le stockage des données en utilisant le format **Parquet** et la compression **Snappy**. Ces optimisations permettront aux utilisateurs de travailler avec les données plus rapidement et à moindre coût.

Les données des taxis couvrent plusieurs années et sont mises à jour au fil du temps, il est donc judicieux de compartimenter les données par série temporelle.

Vous allez créer la table en utilisant la table **nyctaxi_lookup_csv** comme source. Vous déciderez de créer une version Parquet de la table ayant les mêmes noms de colonnes que la version CSV de la table et de déclarer **Snappy** comme type de compression.

Pour ce faire, vous allez créer une tâche dans Step Functions pour exécuter la commande suivante et créer un fichier Parquet à partir des données source CSV.

```sql
CREATE table if not exists nyctaxidb.nyctaxi_lookup_parquet WITH (format='PARQUET',parquet_compression='SNAPPY', external_location = 's3://<FMI_1>/nyctaxidata/optimized-data-lookup/') AS SELECT locationid, borough, zone , service_zone , latitude ,longitude  FROM nyctaxidb.nyctaxi_lookup_csv
```

---

#### Mettre à jour la machine d'état **WorkflowPOC** pour créer une table Parquet avec compression Snappy

1. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour ouvrir la machine d'état **WorkflowPOC** dans **Workflow Studio**.
2. Dans le panneau **Actions**, recherchez **athena**.
3. Faites glisser une tâche **StartQueryExecution** sur le canevas entre la tâche **Run Create lookup Table Query** et l'état **End**.
4. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Run Create Parquet lookup Table Query**.
5. Pour les paramètres de l'API, remplacez le code JSON par défaut par le suivant. Remplacez `<FMI_1>` et `<FMI_2>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "CREATE table if not exists nyctaxidb.nyctaxi_lookup_parquet WITH (format='PARQUET',parquet_compression='SNAPPY', external_location = 's3://<FMI_1>/nyctaxidata/optimized-data-lookup/') AS SELECT locationid, borough, zone , service_zone , latitude ,longitude  FROM nyctaxidb.nyctaxi_lookup_csv",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_2>/athena/"
        }
    }
    ```

6. Sélectionnez **Wait for task to complete - optional**.
7. Pour **Next state**, laissez **Go to end** sélectionné.
8. Choisissez **Save**.

---

#### Tester en supprimant les tables existantes dans AWS Glue, en exécutant le workflow, et en vérifiant les résultats

1. Dans la console **AWS Glue**, dans le panneau de navigation, choisissez **Tables**.
    - Supprimez toutes les tables existantes dans la base de données AWS Glue. Cela garantira que le workflow suit le chemin correct lors de l'exécution suivante.

2. Dans la console **S3**, accédez au contenu du dossier **athena** dans votre bucket **gluelab**.
    - Choisissez le préfixe **nyctaxidata**.
    - Sélectionnez le préfixe **optimized-data-lookup**, puis choisissez **Delete**.
    - Sur la page **Delete objects**, saisissez **permanently delete** dans le champ au bas de la page, puis choisissez **Delete objects**.
    - Choisissez **Close**.

    **Explication** : Les permissions accordées à Athena ne permettent pas à Athena de supprimer les informations de table stockées dans Amazon S3. Vous devez donc supprimer manuellement le préfixe **optimized-data-lookup** dans votre bucket S3 avant d'exécuter le workflow. Si vous ne le faites pas, le workflow échouera pendant la tâche **Create Parquet lookup Table Query**.

3. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour exécuter la machine d'état **WorkflowPOC**. Nommez le test **TaskSevenTest**.
    - L'image ci-dessous montre le workflow une fois terminé.

    *(Ajouter une image montrant le workflow avec la tâche Run Create Parquet lookup Table Query complétée)*

4. Vérifiez que la nouvelle table a été créée.
    - Dans la console **AWS Glue**, dans le panneau de navigation, choisissez **Tables**.
    - Une nouvelle table **nyctaxi_lookup_parquet** a été créée. Le schéma de la table devrait ressembler à l'image suivante.

    *(Ajouter une image du schéma de la table Parquet dans AWS Glue)*

---

**Félicitations !** Dans cette tâche, vous avez créé la table de correspondance au format Parquet avec compression Snappy. Vous allez maintenant créer une autre table Parquet et pourrez ensuite combiner les informations des deux tables.








--------------------------------------------------
### Tâche 8 : Optimisation avec des partitions
--------------------------------------------------

Dans cette tâche, vous allez créer une table **yellowtaxi_data_parquet** au format Parquet avec compression Snappy, similaire à ce que vous avez fait avec la table de correspondance. Cependant, vous apporterez un autre changement : comme les données de la table des taxis jaunes sont spécifiques au temps, il est logique de partitionner les données. Vous allez créer un préfixe dans Amazon S3 nommé **optimized-data** pour que vous puissiez partitionner les données par année et mois de prise en charge (pickup year et pickup month).

Grâce aux partitions, vous pourrez restreindre la quantité de données scannée par chaque requête, ce qui améliorera les performances et réduira les coûts. Le partitionnement divise une table en parties et garde les données associées ensemble en fonction des valeurs de colonnes.

En utilisant des partitions, les nouvelles données ajoutées seront compartimentées, et les requêtes incluant une date de prise en charge seront beaucoup plus efficaces. Avec le format Parquet et la compression Snappy en place, le stockage des données sera pleinement optimisé.

---

#### Mettre à jour le workflow avec une nouvelle étape qui crée la table **yellowtaxi_data_parquet**

1. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour ouvrir la machine d'état **WorkflowPOC** dans **Workflow Studio**.
2. Dans le panneau **Actions**, recherchez **athena**.
3. Faites glisser une tâche **StartQueryExecution** entre la tâche **Run Create Parquet lookup Table Query** et l'état **End**.
4. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Run Create Parquet data Table Query**.
5. Pour les paramètres de l'API, remplacez le code JSON par défaut par le suivant. Remplacez `<FMI_1>` et `<FMI_2>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "CREATE table if not exists nyctaxidb.yellowtaxi_data_parquet WITH (format='PARQUET',parquet_compression='SNAPPY',partitioned_by=array['pickup_year','pickup_month'],external_location = 's3://<FMI_1>/nyctaxidata/optimized-data/') AS SELECT vendorid,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,ratecodeid,store_and_fwd_flag,pulocationid,dolocationid,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount,congestion_surcharge,payment_type,substr(\"tpep_pickup_datetime\",1,4) pickup_year, substr(\"tpep_pickup_datetime\",6,2) AS pickup_month FROM nyctaxidb.yellowtaxi_data_csv where substr(\"tpep_pickup_datetime\",1,4) = '2020' and substr(\"tpep_pickup_datetime\",6,2) = '01'",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_2>/athena/"
        }
    }
    ```

6. Sélectionnez **Wait for task to complete - optional**.
7. Choisissez **Save**.

---

#### Tester en supprimant les tables existantes dans AWS Glue, en exécutant le workflow, et en vérifiant les résultats

1. Dans la console **AWS Glue**, supprimez les trois tables existantes.
    - Cela garantira que le workflow emprunte le bon chemin lors de son exécution.

2. Dans la console **S3**, accédez au contenu du dossier **athena** dans votre bucket **gluelab**.
    - Choisissez le préfixe **nyctaxidata**.
    - Supprimez le préfixe **optimized-data-lookup** en choisissant **Delete**.
    - Sur la page **Delete objects**, saisissez **permanently delete** dans le champ en bas de la page, puis choisissez **Delete objects**.
    - Choisissez **Close**.

    **Explication** : Comme mentionné précédemment, les permissions accordées à Athena ne lui permettent pas de supprimer les informations de table stockées dans Amazon S3. Vous devez donc supprimer manuellement le préfixe **optimized-data-lookup** avant de lancer le workflow.

3. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour exécuter la machine d'état **WorkflowPOC**. Nommez le test **TaskEightTest**.

    - L'image ci-dessous montre le workflow une fois terminé.

    *(Ajouter une image montrant le workflow avec les deux tâches de requêtes Parquet complétées)*

4. Vérifiez que la nouvelle table a été créée.

    - Dans la console **Athena**, dans le panneau de navigation, choisissez **Query editor**.
    - **Astuce** : Si le panneau de navigation est réduit, ouvrez-le en choisissant l'icône de menu (☰).
    - **Remarque** : Comme c'est la première fois que vous utilisez Athena, un message en haut de la page vous indique que vous devez configurer un emplacement de résultats de requête dans Amazon S3.
    - Choisissez l'onglet **Settings**.
    - Choisissez **Manage** et configurez ce qui suit :
        - Choisissez **Browse S3**.
        - Choisissez le lien vers votre bucket **gluelab**.
        - Choisissez l'option pour le préfixe **athena**.
        - Sélectionnez **Choose**.
        - Choisissez **Save**.

    - Choisissez l'onglet **Editor**.
    - Dans le panneau **Data**, pour **Database**, choisissez **nyctaxidb**.
    - Développez la table **yellowtaxi_data_parquet**.

    L'image ci-dessous montre la vue développée.

    *(Ajouter une image montrant la vue développée de la table yellowtaxi_data_parquet)*

    - Remarquez l'étiquette **Partitioned** pour la table.
    - **Astuce** : Vous devrez peut-être faire défiler vers le bas pour voir les deux dernières colonnes de la base de données, qui stockeront les données sous forme de chaînes partitionnées.

---

**Excellent travail !** Dans cette tâche, vous avez recréé les deux tables AWS Glue au format Parquet avec compression Snappy. De plus, vous avez configuré la table **yellowtaxi_data_parquet** pour qu'elle soit partitionnée, et vous avez confirmé que vous pouvez charger la table dans l'éditeur de requêtes Athena.

--------------------------------------------------

### Tâche 9 : Création d'une vue dans Athena
--------------------------------------------------

Dans cette tâche, vous allez créer une vue des données dans l'éditeur de requêtes Athena. La vue combinera les données des deux tables qui sont stockées au format Parquet.

---

#### Créer une nouvelle vue des données en utilisant l'éditeur de requêtes Athena

1. Dans le panneau **Data**, dans la section **Tables and views**, choisissez **Create** > **CREATE VIEW**.
    - Les commandes SQL suivantes apparaissent dans un onglet de requête pour vous aider à démarrer.

    ```sql
    -- View Example
    CREATE OR REPLACE VIEW view_name AS
    SELECT column1, column2
    FROM table_name
    WHERE condition;
    ```

2. Remplacez les commandes par défaut par le code suivant :

    ```sql
    create or replace view nyctaxidb.yellowtaxi_data_vw as select a.*,lkup.* from (select  datatab.pulocationid pickup_location ,pickup_month, pickup_year, sum(cast(datatab.total_amount AS decimal(10, 2))) AS sum_fare , sum(cast(datatab.trip_distance AS decimal(10, 2))) AS sum_trip_distance , count(*) AS countrec   FROM nyctaxidb.yellowtaxi_data_parquet datatab WHERE datatab.pulocationid is NOT null  GROUP BY  datatab.pulocationid, pickup_month, pickup_year) a , nyctaxidb.nyctaxi_lookup_parquet lkup where lkup.locationid = a.pickup_location;
    ```

    **Remarque** : Dans ce scénario, Mary vous a fourni ces commandes SQL.

3. Choisissez **Run**.
    - La requête se termine avec succès. Dans le panneau **Data**, dans la section **Views**, une vue **yellowtaxi_data_vw** est maintenant listée.

4. Développez les détails de la nouvelle vue.
    - L'image ci-dessous montre la vue développée.

    *(Ajouter une image montrant la vue développée de la vue yellowtaxi_data_vw)*

5. Remarquez comment la vue contient des colonnes telles que **sum_fare** et **sum_trip_distance**.
    - À droite du nom de la vue, choisissez l'icône **three-dot** (…), puis choisissez **Preview View**.
    - Les résultats s'affichent et sont similaires à l'image suivante.

    *(Ajouter une image montrant un aperçu des résultats de la vue)*

---

**Vous avez fait un excellent progrès !** Dans cette tâche, vous avez créé avec succès une vue des données obtenues en interrogeant les deux tables Parquet.






--------------------------------------------------
### Tâche 10 : Ajouter la vue au workflow Step Functions
--------------------------------------------------

Vous avez appris à créer la vue manuellement en utilisant l'éditeur de requêtes Athena. L'étape suivante consiste à ajouter une tâche au workflow pour que le pipeline ETL crée la vue lorsque celle-ci n'existe pas encore. Vous ajouterez cette nouvelle tâche après la création des tables afin que la vue ne soit pas recréée à chaque fois que de nouvelles données sont ajoutées.

---

#### Supprimer la vue que vous avez créée manuellement

1. Dans la console **S3**, accédez au contenu du dossier **nyctaxidata** dans votre bucket **gluelab**.
2. Supprimez les deux préfixes contenant **optimized** dans leurs noms.
3. Dans la console **AWS Glue**, supprimez toutes les cinq tables.

---

#### Ajouter une étape au workflow Step Functions pour créer la vue

1. Dans la console **Step Functions**, utilisez la méthode que vous avez utilisée dans les étapes précédentes pour ouvrir la machine d'état **WorkflowPOC** dans **Workflow Studio**.
2. Dans le panneau **Actions**, recherchez **athena**.
3. Faites glisser une tâche **StartQueryExecution** entre la tâche **Run Create Parquet data Table Query** et l'état **End**.
4. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Run Create View**.
5. Pour les paramètres de l'API, remplacez le code JSON par le suivant. Remplacez `<FMI_1>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "create or replace view nyctaxidb.yellowtaxi_data_vw as select a.*,lkup.* from (select  datatab.pulocationid pickup_location ,pickup_month, pickup_year, sum(cast(datatab.total_amount AS decimal(10, 2))) AS sum_fare , sum(cast(datatab.trip_distance AS decimal(10, 2))) AS sum_trip_distance , count(*) AS countrec   FROM nyctaxidb.yellowtaxi_data_parquet datatab WHERE datatab.pulocationid is NOT null  GROUP BY  datatab.pulocationid, pickup_month, pickup_year) a , nyctaxidb.nyctaxi_lookup_parquet lkup where lkup.locationid = a.pickup_location",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_1>/athena/"
        }
    }
    ```

6. Remarquez que la chaîne **QueryString** contient la même requête que vous avez exécutée pour créer la vue dans l'éditeur de requêtes Athena lors de la tâche précédente.
7. Sélectionnez **Wait for task to complete - optional**.
8. Choisissez **Save**.

---

#### Tester le workflow

1. Utilisez la méthode que vous avez utilisée dans les étapes précédentes pour exécuter la machine d'état **WorkflowPOC**. Nommez le test **TaskTenTest**.

    - L'image ci-dessous montre le workflow une fois terminé.

    *(Ajouter une image montrant le workflow avec la tâche Run Create View complétée)*

---

**Excellent travail !** Vous avez maintenant un pipeline ETL complet sous forme de preuve de concept (POC). Ce POC est conçu pour être facile à utiliser pour un nouveau projet. Pour utiliser le workflow, quelqu'un n'aurait qu'à remplacer les emplacements des buckets et mettre à jour les requêtes pour le format des données et la façon dont ils souhaitent partitionner et traiter les données.

Le seul problème restant est que cette implémentation ne prend pas encore en charge l'ajout de nouvelles données temporelles (par exemple, les données de février). C'est ce que vous allez traiter dans la prochaine étape.

--------------------------------------------------
### Tâche 11 : Ajouter un nouveau workflow pour l'injection de données
--------------------------------------------------

Dans cette tâche, vous allez ajouter les données de février au workflow.

La commande suivante, que Mary vous a fournie, va insérer les données de février (02).

```sql
INSERT INTO nyctaxidb.yellowtaxi_data_parquet select vendorid,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,ratecodeid,store_and_fwd_flag,pulocationid,dolocationid,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount,congestion_surcharge,payment_type,substr(\"tpep_pickup_datetime\",1,4) pickup_year, substr(\"tpep_pickup_datetime\",6,2) AS pickup_month FROM nyctaxidb.yellowtaxi_data_csv where substr(\"tpep_pickup_datetime\",1,4) = '2020' and substr(\"tpep_pickup_datetime\",6,2) = '02'"
```

Vous pourriez exécuter cette commande pour chaque fichier, mais cela créerait deux fichiers CSV et deux fichiers Parquet. Vous souhaitez ajouter des données uniquement à la table Parquet des données des taxis jaunes.

Vous décidez qu'une approche efficace consiste à utiliser une étape **Map** dans Step Functions, car ce type d'étape peut itérer sur toutes les tables AWS Glue et passer les tables que vous ne souhaitez pas mettre à jour. De cette manière, vous pouvez exécuter la commande uniquement sur le fichier Parquet des données des taxis jaunes.

---

#### Ajouter un état **Map** au workflow

1. Utilisez la méthode que vous avez utilisée dans les étapes précédentes pour ouvrir la machine d'état **WorkflowPOC** dans **Workflow Studio**.
2. Dans le panneau **Flow**, faites glisser un état **Map** sur le canevas entre la tâche **REPLACE ME FALSE STATE** et l'état **End**.
3. Supprimez la tâche **REPLACE ME FALSE STATE** du workflow.
4. Avec l'état **Map** sélectionné, changez le nom de l'état en **Check All Tables**.
5. Pour **Path to items array**, entrez **$.Rows**.
6. Choisissez l'onglet **Input**.
7. Sélectionnez **Filter input with InputPath**, et pour **InputPath**, entrez **$.ResultSet**.

    **Analyse** : Le chemin d'entrée définit les données qui seront analysées lors de chaque itération.

8. Assurez-vous que **Transform array item with Parameters** n'est pas sélectionné.
9. Choisissez l'onglet **Configuration**.
10. Pour **Next state**, laissez **Go to end** sélectionné.

---

#### Ajouter un état de choix au workflow

1. Dans le panneau **Flow**, faites glisser un état **Choice** sur le canevas là où apparaît **Drop state here** après l'état **Check All Tables**.
2. Avec l'état **Choice** sélectionné, changez le nom de l'état en **CheckTable**.
3. Dans la section **Choice Rules**, pour la règle n°1, choisissez l'icône d'édition et configurez ce qui suit :
    - Choisissez **Add conditions**.
    - Conservez la condition simple par défaut.
    - Gardez **Not blank** sans sélectionner d'option.
    - Pour **Variable**, entrez **$.Data[0].VarCharValue**.
    - Pour **Operator**, choisissez **matches string**.
    - Pour **Value**, entrez **data_parquet**.

    **Analyse** : Chaque itération des noms de tables AWS Glue vérifiera si le nom correspond au modèle se terminant par **data_parquet**. Cela ne correspondra qu'à une table, ce qui est la logique souhaitée.

4. Choisissez **Save conditions**.

---

#### Ajouter un état **Pass** au workflow et configurer la logique par défaut de choix

1. Dans le panneau **Flow**, faites glisser un état **Pass** sur le canevas après l'état **CheckTable**, sur le côté droit sous la flèche étiquetée **Default**.
2. Avec l'état **Pass** sélectionné, changez le nom de l'état en **Ignore File**.
3. Sur le canevas, choisissez l'étiquette **Default** sur la flèche sous l'état **CheckTable**.
4. Dans le panneau **Inspector**, dans la section **Choice Rules**, ouvrez les détails de la règle par défaut.
5. Pour **Default state**, vérifiez que **Ignore File** est sélectionné.

    **Analyse** : La règle par défaut sera invoquée pour toute table AWS Glue qui n'est pas celle que vous souhaitez modifier (soit toutes les tables sauf **yellowtaxi_data_parquet**).

---

#### Ajouter une tâche **StartQueryExecution** au workflow pour mettre à jour la table Parquet des taxis

1. Dans le panneau **Actions**, recherchez **athena**.
2. Faites glisser une tâche **StartQueryExecution** sur le canevas après l'état **CheckTable**, sur le côté gauche.
3. Avec la tâche **StartQueryExecution** sélectionnée, changez le nom de l'état en **Insert New Parquet Data**.
4. Pour les paramètres de l'API, remplacez le code JSON par le suivant. Remplacez `<FMI_1>` par le nom réel de votre bucket.

    ```json
    {
        "QueryString": "INSERT INTO nyctaxidb.yellowtaxi_data_parquet select vendorid,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,ratecodeid,store_and_fwd_flag,pulocationid,dolocationid,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount,congestion_surcharge,payment_type,substr

(\"tpep_pickup_datetime\",1,4) pickup_year, substr(\"tpep_pickup_datetime\",6,2) AS pickup_month FROM nyctaxidb.yellowtaxi_data_csv where substr(\"tpep_pickup_datetime\",1,4) = '2020' and substr(\"tpep_pickup_datetime\",6,2) = '02'",
        "WorkGroup": "primary",
        "ResultConfiguration": {
            "OutputLocation": "s3://<FMI_1>/athena/"
        }
    }
    ```

5. Sélectionnez **Wait for task to complete - optional**.
6. Pour **Next state**, laissez **Go to end** sélectionné.
7. Confirmez que la nouvelle logique ajoutée au workflow ressemble à l'image suivante :

   *(Ajouter une image montrant le workflow avec les états Insert New Parquet Data et Ignore File)*

8. Choisissez **Save**.

---

**Excellent travail !** Vous avez ajouté avec succès la logique finale au workflow. Cette logique va traiter le mois supplémentaire de données de taxis.

---






--------------------------------------------------
### Tâche 12 : Tester la solution complète
--------------------------------------------------

Dans cette dernière tâche, vous allez tester la solution complète du POC (Proof of Concept).

---

#### Tester les résultats du dernier workflow dans la console Step Functions

1. Utilisez la méthode que vous avez utilisée dans les étapes précédentes pour exécuter la machine d'état **WorkflowPOC**. Nommez le test **TaskTwelveTest**.

    - L'image ci-dessous montre le workflow une fois terminé.

    *(Ajouter une image montrant le workflow complété pour le POC)*

    **Astuce** : Dans la section **Graph inspector**, pour agrandir le graphe, faites glisser la barre avec les trois points horizontaux vers le bas de la page.

---

#### Vérifier que l'itérateur fonctionne comme prévu

1. Dans la section **Graph inspector**, choisissez l'étape **CheckTable**.
2. En haut de la section, deux menus déroulants apparaissent pour **Iteration status** et **Index**. Par défaut, la première itération avec une valeur d'index de 0 est sélectionnée.
3. Dans le panneau **Details** à droite, vérifiez que la première itération a réussi.
4. Changez l'index à 1 et vérifiez que l'itération a réussi.
5. Répétez ces étapes pour les itérations 2, 3 et 4. Elles devraient toutes avoir réussi.
6. Remettez l'index à 0.
7. Choisissez l'onglet **Step input**, et vérifiez que le code JSON ressemble à l'image suivante. **VarCharValue** doit être défini sur **nyctaxi_lookup_csv**.

   *(Ajouter une image de l'onglet Step input dans la section Graph inspector)*

8. Tout en restant sur l'onglet **Step input**, changez l'index jusqu'à ce que **VarCharValue** dans le code JSON soit égal à **yellowtaxi_data_parquet**.
9. Dans le graphe, choisissez l'étape **Insert New Parquet Data**.
10. Choisissez l'onglet **Step output**.
    - Ici, vous verrez la requête qui a créé la vue, en plus d'autres détails d'exécution des étapes.

---

#### Tester dans la console Athena

1. Dans la console **Athena**, ouvrez l'éditeur de requêtes et assurez-vous que **nyctaxidb** est sélectionné pour **Database**.
2. Dans la section **Views**, choisissez l'icône à trois points (… ) pour la vue listée, puis choisissez **Preview View**.
    - Les 10 premiers enregistrements des résultats s'affichent. Remarquez que la vue scanne désormais des données incluant le mois 02 (février).

3. Pour vérifier que les données de février sont également dans l'ensemble de données, exécutez la requête suivante dans l'éditeur de requêtes :

    ```sql
    SELECT * FROM "nyctaxidb"."yellowtaxi_data_vw" WHERE pickup_month = '02'
    ```

    - Les 100 premiers résultats s'affichent comme montré dans l'image suivante.

    *(Ajouter une image montrant les résultats de la requête incluant les données de février)*

---

**Félicitations !** Le pipeline ETL peut désormais traiter les nouvelles données et les intégrer à la vue afin que ces données puissent être interrogées dans Athena.

---

### Mise à jour de l'équipe

Après avoir construit la preuve de concept complète, vous avez présenté rapidement une démonstration à Mary. Elle a été très impressionnée !

Mary a indiqué qu'elle est confiante qu'elle pourra dupliquer ce POC et ajuster les valeurs en dur selon les besoins pour chaque projet. Elle est impatiente de commencer à utiliser le pipeline ETL que vous avez créé pour accélérer le traitement de ses données sur ses nombreux projets.

---

### Soumettre votre travail

1. Pour enregistrer vos progrès, choisissez **Submit** en haut de ces instructions.
2. Lorsque vous y êtes invité, choisissez **Yes**.
    - Après quelques minutes, le panneau des notes apparaît et vous montre combien de points vous avez obtenus pour chaque tâche. Si les résultats ne s'affichent pas après quelques minutes, choisissez **Grades** en haut de ces instructions.

    **Astuce** : Vous pouvez soumettre votre travail plusieurs fois. Après avoir modifié votre travail, choisissez **Submit** à nouveau. Votre dernière soumission est enregistrée pour ce laboratoire.

3. Pour obtenir un retour détaillé sur votre travail, choisissez **Submission Report**.

---

**Lab terminé**

Félicitations ! Vous avez terminé ce laboratoire.

1. En haut de cette page, choisissez **End Lab**, puis choisissez **Yes** pour confirmer que vous souhaitez terminer le laboratoire.
2. Un panneau de message indique que le laboratoire se termine.
3. Pour fermer le panneau, choisissez **Close** dans le coin supérieur droit.

---

© 2022, Amazon Web Services, Inc. et ses filiales. Tous droits réservés.

---

**Félicitations à vous aussi !** Vous avez mené à bien l'intégralité de ce laboratoire détaillé en construisant et orchestrant des pipelines ETL avec Step Functions, Athena, S3, et AWS Glue.

