# Partie 3 - Rapport avec des imprimes écrans des étapes à remettre avant le *20 Octobre 2024* à minuit


# **Instructions pour le rapport :**

Vous devez me remettre un **rapport détaillé** contenant des **captures d'écran de chaque étape** que vous avez suivie dans le lab. Le rapport peut être présenté sous forme de **document WORD, PDF** ou de **présentation PowerPoint (PPT)**.

# **Ce que vous devez inclure :**
- Assurez-vous de capturer **chaque étape** clé que vous avez réalisée tout au long du projet.
- Le rapport doit contenir vos **conclusions** ainsi que des **captures d'écran** pour illustrer les différentes étapes du lab.
- Les réponses aux questions suivantes :

# **Questions**

1. **Quel est le rôle d'AWS Glue dans le traitement des données avant de les interroger avec Athena ?**

2. **Pourquoi est-il nécessaire de transformer les fichiers CSV en format Parquet avant de les stocker dans Amazon S3 pour les analyser avec Athena ?**

3. **Quelle est l'importance de créer une vue dans Athena avant d'exécuter certaines requêtes sur un grand ensemble de données, et comment cela optimise-t-il les performances des requêtes ?**



# **Date limite de remise :**
Le **20 octobre 2024 à minuit**.



---------------------------
# Projet Capstone 
---------------------------

# **Aperçu et objectifs**

Tout au long de ce cours, vous avez réalisé des travaux pratiques, où vous avez utilisé les fonctionnalités de différents services AWS pour pratiquer l'ingestion de grands ensembles de données, leur transformation et l'extraction d'informations.

Dans ce projet de fin de parcours, vous êtes mis au défi de construire une solution qui utilise de nombreux services AWS que vous connaissez déjà, sans recevoir de directives détaillées étape par étape. Certaines parties de l'exercice sont conçues pour être difficiles.

Ce projet capstone vous mettra au défi de faire les choses suivantes :

- Lancer et configurer une instance d'environnement de développement intégré (IDE) AWS Cloud9.
- Transformer des fichiers de données au format CSV en format Apache Parquet et les télécharger sur Amazon S3.
- Créer un robot d'exploration AWS Glue pour déduire la structure des données.
- Utiliser Amazon Athena pour interroger les données.
- Créer une vue Athena.
- Exécuter des requêtes sur la vue Athena pour analyser les données.

# **Durée et suivi de votre budget**

Ce projet de fin de parcours devrait prendre environ 4 heures à compléter.

Cet environnement est persistant. Lorsque le minuteur de session arrive à 0:00, la session se termine, mais toutes les données et ressources que vous avez créées dans le compte AWS seront conservées. Si vous lancez une nouvelle session plus tard (par exemple, le jour suivant), vous retrouverez votre travail dans l'environnement de lab. De plus, à tout moment avant que le minuteur de session n'atteigne 0:00, vous pouvez choisir "Start Lab" à nouveau pour prolonger la durée de la session.

# **Important :

** Surveillez votre budget de lab dans l'interface du lab. Lorsque vous avez une session de lab active, les dernières informations connues sur le budget restant s'affichent en haut de cet écran. Ces données proviennent de AWS Budgets, qui se met à jour généralement toutes les 8 à 12 heures. Ainsi, le budget restant que vous voyez pourrait ne pas refléter votre activité la plus récente sur le compte. Si vous dépassez votre budget de lab, votre compte de lab sera désactivé, et tout progrès et toutes les ressources seront perdus. Il est donc important de gérer vos dépenses.

# **Restrictions des services AWS**

Dans cet environnement de lab, l'accès aux services AWS et aux actions de service pourrait être limité à ceux nécessaires pour suivre les instructions du lab. Vous pourriez rencontrer des erreurs si vous tentez d'accéder à d'autres services ou d'effectuer des actions au-delà de ce qui est décrit dans ce lab.

# **Le jeu de données**

Le site web *The Sea Around Us* fournit un jeu de données contenant des informations historiques étendues sur les pêcheries dans toutes les parties de chaque océan du monde. Les données incluent des informations sur les prises de pêcherie annuelles de 1950 à 2018.

Les données peuvent être téléchargées au format CSV depuis le site *The Sea Around Us*. Le jeu de données contient des colonnes d'informations pour chaque année, notamment quels pays ont capturé quels types de poissons et dans quelles zones. Les données indiquent également combien de tonnes de poissons ont été capturées et quelle était la valeur des prises, mesurée en dollars américains de 2010.

Pour comprendre les données, il est utile de comprendre ce que signifient les zones de haute mer et les zones ZEE :

- **Haute mer (aussi appelée mers internationales)** : Zones de l'océan situées à au moins 200 miles nautiques de la côte de tout pays. Les ressources, y compris les poissons dans ces zones, sont généralement considérées comme n'appartenant à aucun pays en particulier. La carte suivante, qui est une capture d'écran du site web *The Sea Around Us*, montre comment le jeu de données divise les zones de haute mer (zones en gris foncé) en zones uniques de haute mer.

![image](https://github.com/user-attachments/assets/0e68393e-c657-4673-af96-7a64935e306a)

- **Zones économiques exclusives (ZEE)** : Zones situées à moins de 200 miles nautiques de la côte d'un pays. Chaque pays revendique généralement un accès exclusif aux ressources dans ces zones, y compris les poissons qui s'y trouvent. La carte suivante, qui est une capture d'écran du site web *The Sea Around Us*, montre les ZEE dans le monde.

![image](https://github.com/user-attachments/assets/d743aabd-7067-4410-9d8c-baf4c2a7a513)


Les données de *The Sea Around Us* sont (lorsqu'elles ne sont pas autrement réglementées) sous licence Creative Commons Attribution Non-Commercial 4.0 International (https://creativecommons.org/licenses/by-nc/4.0/). Les avis concernant les droits d'auteur (© Université de la Colombie-Britannique), la licence et les avertissements sont disponibles sur http://www.seaaroundus.org/terms-and-conditions/. Pauly D., Zeller D., Palomares M.L.D. (éditeurs), 2020. *Sea Around Us Concepts, Design and Data* (seaaroundus.org).

# **Scénario**

Vous avez pour mission de créer l'infrastructure pour héberger des données sur la pêche afin que les analystes de données de votre organisation puissent créer des rapports sur l'impact de la pêche en haute mer. Vous avez décidé de construire l'infrastructure dans votre compte AWS et de la tester en utilisant trois fichiers de données provenant du jeu de données *The Sea Around Us*.

Dans ce projet de fin de parcours, vous allez travailler avec trois fichiers de données provenant du site web *The Sea Around Us* :

- Le premier fichier contient des données de toutes les zones de haute mer.
- Le deuxième fichier contient des données d'une seule zone de haute mer dans l'océan Pacifique, appelée **Pacifique, Centre-Ouest**, qui se trouve près de Fidji et de nombreux autres pays.
- Le troisième fichier contient des données de la ZEE d'un seul pays (Fidji), qui est situé près de la zone de haute mer **Pacifique, Centre-Ouest**.

En travaillant avec cette variété de fichiers de données d'échantillon, vous serez en mesure de tester si la solution que vous construisez peut prendre en charge un ensemble de données beaucoup plus grand.

Lorsque vous commencerez ce projet de fin de parcours, l'environnement contiendra les ressources illustrées dans le diagramme suivant.

![image](https://github.com/user-attachments/assets/a0264aeb-b3a7-4daa-b951-8fd9a99a4722)

À la fin de ce projet de fin de parcours, vous aurez créé une architecture similaire à celle illustrée dans le diagramme suivant.

![image](https://github.com/user-attachments/assets/d19b7dbc-9d9b-45dd-bc0c-6c71dfd22fc6)













# Accéder à la console de gestion AWS

1. En haut de ces instructions, choisissez **Start Lab**.

   - La session de lab démarre.
   - Un minuteur s'affiche en haut de la page et montre le temps restant dans la session.
   
   **Astuce :** Pour rafraîchir la durée de la session à tout moment, choisissez à nouveau **Start Lab** avant que le minuteur n'atteigne 0:00.

   - Avant de continuer, attendez que l'icône circulaire à droite du lien **AWS** dans le coin supérieur gauche devienne verte.

2. Pour vous connecter à la console de gestion AWS, choisissez le lien **AWS** dans le coin supérieur gauche.

   - Un nouvel onglet de navigateur s'ouvre et vous connecte à la console.

   **Astuce :** Si un nouvel onglet de navigateur ne s'ouvre pas, une bannière ou une icône apparaît généralement en haut de votre navigateur avec le message indiquant que votre navigateur empêche le site d'ouvrir des fenêtres pop-up. Choisissez la bannière ou l'icône, puis sélectionnez **Autoriser les pop-ups**.

---

# Tâche 1 : Configuration de l'environnement de développement

Dans cette première partie du projet de fin de parcours, vous allez configurer votre environnement de développement.

1. Observez les détails du rôle **CapstoneGlueRole** d'AWS Identity and Access Management (IAM) qui a été créé pour vous.

2. Créez un environnement AWS Cloud9 avec les paramètres suivants :

   - Nommez l'environnement **CapstoneIDE**.
   - Créez une nouvelle instance EC2 pour l'environnement, et utilisez une instance **t2.micro**.
   - Déployez l'instance pour prendre en charge les connexions SSH au VPC Capstone, dans le sous-réseau public Capstone.
   - Conservez tous les autres paramètres par défaut.

3. Créez deux buckets S3 avec les paramètres suivants :

   - Créez les buckets dans la région **us-east-1**.
   - Nommez le premier bucket **data-source-#####** où ##### est un nombre aléatoire.
   - Nommez le deuxième bucket **query-results-#####** où ##### est également un nombre aléatoire.
   - Conservez tous les autres paramètres par défaut.

4. Pour télécharger les trois fichiers sources au format .csv, exécutez les commandes suivantes dans le terminal de votre IDE AWS Cloud9 :

   ```
   wget https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1-91570/lab-capstone/s3/SAU-GLOBAL-1-v48-0.csv
   wget https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1-91570/lab-capstone/s3/SAU-HighSeas-71-v48-0.csv
   wget https://aws-tc-largeobjects.s3.us-west-2.amazonaws.com/CUR-TF-200-ACDENG-1-91570/lab-capstone/s3/SAU-EEZ-242-v48-0.csv
   ```

5. Pour observer la ligne d'en-tête des colonnes et les cinq premières lignes de données du fichier **SAU-GLOBAL-1-v48-0.csv**, exécutez la commande suivante :

   ```
   head -6 SAU-GLOBAL-1-v48-0.csv
   ```

   **Analyse :** Parmi d'autres détails, chaque ligne de données dans cet ensemble inclut :

   - L'année de la pêche
   - Le pays (**fishing_entity**) ayant réalisé la pêche
   - Les tonnes de poissons pêchées cette année-là par ce pays
   - La valeur en dollars américains de 2010 (**landed_value**) des poissons capturés cette année-là par ce pays

   **Remarque :**

   - Cet ensemble de données contient **561 675 lignes**.

   **Astuce :** Pour confirmer cela, exécutez la commande suivante : 

   ```
   wc -l <filename>
   ```

   - L'ensemble de données comprend des données déclarées et des "meilleures estimations" pour toutes les pêches qui ont eu lieu dans les eaux internationales (c'est-à-dire, en dehors de la ZEE de tout pays) entre 1950 et 2018.
   - Les zones ZEE comprennent les eaux océaniques situées à moins de 200 miles nautiques du littoral d'un pays. Par conséquent, les pêches reportées dans cet ensemble de données ont eu lieu à au moins 200 miles des côtes de tout pays.

---

6. Convertissez le fichier **SAU-GLOBAL-1-v48-0.csv** au format Parquet.

   - Tout d'abord, vous devez installer certains outils sur votre IDE AWS Cloud9. Exécutez la commande suivante :

   ```
   sudo pip3 install pandas pyarrow fastparquet
   ```

   - Ensuite, pour convertir le fichier au format Parquet, exécutez le code suivant :

   ```python
   # Démarrer l'interpréteur Python interactif
   python3

   # Utiliser pandas pour convertir le fichier au format parquet
   import pandas as pd
   df = pd.read_csv('SAU-GLOBAL-1-v48-0.csv')
   df.to_parquet('SAU-GLOBAL-1-v48-0.parquet')
   exit()
   ```

   **Remarque :** Pandas est un outil utile pour travailler avec des fichiers de données. Pour plus d'informations, consultez le site web de pandas.

7. Pour télécharger le fichier **SAU-GLOBAL-1-v48-0.parquet** dans le bucket **data-source**, utilisez une commande AWS CLI dans le terminal de votre AWS Cloud9.



# Tâche 2 : Utilisation d'un robot d'exploration AWS Glue et interrogation de plusieurs fichiers avec Athena

La requête que vous avez exécutée dans la tâche précédente fonctionne bien pour un seul fichier de données. Cependant, que faire si vous devez interroger un ensemble de données plus volumineux qui se compose de plusieurs fichiers ?

Dans cette deuxième partie du projet de fin de parcours, vous allez interroger des données stockées dans plusieurs fichiers. Pour ce faire, vous allez configurer un robot d'exploration AWS Glue pour découvrir la structure des données, puis utiliser Athena pour interroger les données.

1. Pour observer la ligne d'en-tête des colonnes et les premières lignes de données du fichier **SAU-HighSeas-71-v48-0.csv**, utilisez la commande `head`.

2. Rappelez-vous que vous avez déjà téléchargé le fichier sur votre IDE AWS Cloud9.

3. Le fichier contient les mêmes colonnes que le fichier **SAU-GLOBAL-1-v48-0.csv**, mais il possède des colonnes supplémentaires. Le tableau suivant montre les colonnes contenues dans chaque fichier.


![image](https://github.com/user-attachments/assets/d842e258-e7e7-4128-93e1-b5a8218fc251)

# Analyse :

Tout comme le jeu de données **SAU-GLOBAL-1-v48-0** que vous avez déjà téléchargé sur Amazon S3 et interrogé, le jeu de données **SAU-HighSeas-71-v48-0** décrit également les prises de poissons en haute mer. Cependant, le jeu de données **HighSeas** inclut uniquement des données provenant d'une seule zone de haute mer, appelée **Pacifique, Centre-Ouest**. Cette zone est mise en évidence dans la capture d'écran suivante du site web *The Sea Around Us*.


![image](https://github.com/user-attachments/assets/48d54c9b-ea41-454b-88ae-a79c072708a6)



Parmi les colonnes supplémentaires du jeu de données **HighSeas**, deux sont particulièrement intéressantes :

- La colonne **area_name** contient la valeur "Pacific, Western Central" dans chaque ligne.
- La colonne **common_name** contient des valeurs décrivant certains types de poissons (par exemple, "Maquereaux, thons, bonites").


1. Convertissez le fichier **SAU-HighSeas-71-v48-0.csv** au format Parquet et téléchargez-le dans le bucket **data-source**.

2. Créez une base de données AWS Glue et un robot d'exploration AWS Glue avec les paramètres suivants :

   - Nommez la base de données **fishdb**.
   - Nommez le robot d'exploration **fishcrawler**.
   - Configurez le robot d'exploration pour utiliser le rôle IAM **CapstoneGlueRole** afin d'explorer le contenu du bucket S3 **data-source**.
   - Exportez les résultats du robot d'exploration vers la base de données **fishdb**.
   - Réglez la fréquence du robot d'exploration sur **À la demande (On demand)**.

3. Exécutez le robot d'exploration pour créer une table contenant les métadonnées dans la base de données AWS Glue.

4. Vérifiez que la table attendue a bien été créée.

5. Pour confirmer que la table a correctement catégorisé les données, utilisez Athena pour exécuter des requêtes SQL sur chaque colonne de la nouvelle table.

   **Important :** Avant d'exécuter la première requête dans Athena, configurez l'éditeur de requêtes Athena pour exporter les données dans le bucket **query-results**.

   Exemple de requête :

   ```
   SELECT DISTINCT area_name FROM fishdb.data_source_xxxxx;
   ```

   **Remarque :** La requête exemple renvoie deux résultats. Pour cette colonne, chaque ligne du jeu de données contient soit la valeur "Pacific, Western Central" (pour les lignes issues de **SAU-HighSeas-71-v48-0.parquet**), soit une valeur nulle (pour les lignes issues de **SAU-GLOBAL-1-v48-0.parquet**).



6. Maintenant que votre table de données est définie, exécutez des requêtes pour confirmer qu'elle fournit des résultats utiles.

   - Pour trouver la valeur en dollars US de tous les poissons capturés par le pays Fidji dans la zone de haute mer **Pacific, Western Central** depuis 2001, organisés par année, utilisez la requête suivante (assurez-vous de remplacer **<FMI_1>** et **<FMI_2>** par les valeurs correctes) :

   ```
   SELECT year, fishing_entity AS Country, CAST(CAST(SUM(landed_value) AS DOUBLE) AS DECIMAL(38,2)) AS ValuePacificWCSeasCatch
   FROM <FMI_1>
   WHERE area_name LIKE '%Pacific%' AND fishing_entity = 'Fiji' AND year > <FMI_2>
   GROUP BY year, fishing_entity
   ORDER BY year;
   ```

   **Remarque :** La partie **CAST(CAST(SUM(landed_value) AS DOUBLE) AS DECIMAL(38,2))** de la requête garantit que le format des données retournées dans la colonne **landed_value** est affiché dans un format convivial (dollars et centimes) au lieu d'un format scientifique.

7. **Défi :** Trouvez la valeur en dollars US de tous les poissons capturés par le pays Fidji dans toutes les zones de haute mer depuis 2001, organisés par année. Dans vos résultats, nommez la colonne des valeurs en dollars US **ValueAllHighSeasCatch**.

   **Conseils utiles pour le défi :**

   - Votre clause **WHERE** doit inclure deux mots-clés **AND**.
   - Pour renvoyer des lignes qui ne contiennent pas d'entrée pour une colonne particulière, utilisez **IS NULL**.

8. Après avoir créé et exécuté la requête correcte et visualisé les résultats, créez une vue basée sur la requête :

   - Choisissez **Create > View from query**.
   - Nommez la vue **challenge**.












# Tâche 3 : Transformer un nouveau fichier et l'ajouter à l'ensemble de données

Dans cette partie du projet de fin de parcours, vous allez ajouter le fichier de données **SAU-EEZ-242-v48-0.csv** à votre ensemble de données dans Amazon S3.

Ce fichier comporte quelques noms de colonnes qui ne correspondent pas aux données que vous avez déjà ajoutées à votre bucket S3. Cependant, les données de ces colonnes sont alignées avec les données existantes. Vous devrez modifier les noms de colonnes avant d'ajouter les données à votre bucket.

1. Analysez la structure des données du fichier **SAU-EEZ-242-v48-0.csv**. Comparez les colonnes qu'il contient avec les colonnes des autres fichiers de données.

   Utilisez la même technique que celle utilisée précédemment dans le lab pour découvrir les noms de colonnes du fichier **EEZ**.

   **Astuce :** La plupart des noms de colonnes correspondent entre les trois fichiers. Cependant, deux des noms de colonnes dans le fichier **EEZ** ne correspondent pas exactement. Le tableau suivant montre les colonnes contenues dans chaque fichier.

![image](https://github.com/user-attachments/assets/a1c4d607-e940-441a-ae43-26dd78be07da)




















# Analyse :

Les données de la colonne **fish_name** doivent être fusionnées avec les données de la colonne **common_name** du jeu de données **HighSeas**. De même, les données de la colonne **country** doivent être fusionnées avec les données de la colonne **fishing_entity** du jeu de données **HighSeas**.

---

1. Utilisez la bibliothèque Python d'analyse de données, appelée **pandas**, pour corriger les noms de colonnes. De plus, convertissez le fichier **EEZ** au format Parquet.

   Pour accomplir ces tâches, exécutez toutes les commandes du bloc de code suivant. Cependant, avant d'exécuter la commande `df.rename`, remplacez les espaces réservés **<FMI_#>** par les valeurs correctes.

   **Astuces :**
   - Par exemple, **<FMI_1>** doit être défini sur l'un des noms de colonnes que vous souhaitez changer et **<FMI_2>** doit être défini sur ce que vous souhaitez qu'il devienne.
   - Il sera plus facile de lire la sortie des lignes `print` si vous élargissez au maximum la fenêtre de votre navigateur.

   ```bash
   # Faites une copie de sauvegarde du fichier avant de le modifier sur place
   cp SAU-EEZ-242-v48-0.csv SAU-EEZ-242-v48-0-old.csv

   # Démarrez l'interpréteur Python interactif
   python3

   import pandas as pd

   # Chargez la version de sauvegarde du fichier
   data_location = 'SAU-EEZ-242-v48-0-old.csv'

   # Utilisez Pandas pour lire le fichier CSV dans un dataframe
   df = pd.read_csv(data_location)

   # Affichez les noms de colonnes actuels
   print(df.head(1))

   # Changez les noms des colonnes 'fish_name' et 'country' pour correspondre aux noms de colonnes dans les autres fichiers de données déjà dans votre bucket data-source
   df.rename(columns={"<FMI_1>": "<FMI_2>", "<FMI_3>": "<FMI_4>"}, inplace=True)

   # Vérifiez que les noms de colonnes ont été modifiés
   print(df.head(1))

   # Écrivez les modifications sur le disque
   df.to_csv('SAU-EEZ-242-v48-0.csv', header=True, index=False)
   df.to_parquet('SAU-EEZ-242-v48-0.parquet')

   exit()
   ```

2. Téléchargez le nouveau fichier de données **EEZ** dans le bucket **data-source**.

3. Pour mettre à jour les métadonnées de la table avec les colonnes supplémentaires qui font maintenant partie de votre ensemble de données, exécutez à nouveau le robot d'exploration AWS Glue.

4. Exécutez quelques requêtes dans Athena.

   **Remarque :** Pour toutes ces requêtes, remplacez **data_source_#####** par le nom de votre table **data_source**.

   - Pour vérifier les valeurs dans la colonne **area_name**, comme vous l'avez fait précédemment, utilisez la requête suivante :

   ```sql
   SELECT DISTINCT area_name FROM fishdb.data_source_#####;
   ```

   Avec l'ajout du fichier **EEZ** à l'ensemble de données, cette requête renvoie maintenant trois résultats, y compris le résultat pour les lignes où la colonne **area_name** ne contient aucune donnée. (Rappelez-vous que cette requête renvoyait seulement deux résultats auparavant.)

   - Pour trouver la valeur en dollars US de tous les poissons capturés par Fidji dans les eaux internationales depuis 2001, organisés par année, utilisez la requête suivante :

   ```sql
   SELECT year, fishing_entity AS Country, CAST(CAST(SUM(landed_value) AS DOUBLE) AS DECIMAL(38,2)) AS ValueOpenSeasCatch 
   FROM fishdb.data_source_#####
   WHERE area_name IS NULL AND fishing_entity='Fiji' AND year > 2000
   GROUP BY year, fishing_entity
   ORDER BY year;
   ```

   - Pour trouver la valeur en dollars US de tous les poissons capturés par Fidji dans la ZEE de Fidji depuis 2001, organisés par année, utilisez la requête suivante :

   ```sql
   SELECT year, fishing_entity AS Country, CAST(CAST(SUM(landed_value) AS DOUBLE) AS DECIMAL(38,2)) AS ValueEEZCatch
   FROM fishdb.data_source_#####
   WHERE area_name LIKE '%Fiji%' AND fishing_entity='Fiji' AND year > 2000
   GROUP BY year, fishing_entity
   ORDER BY year;
   ```

   - Pour trouver la valeur en dollars US de tous les poissons capturés par Fidji, que ce soit dans la ZEE de Fidji ou en haute mer, depuis 2001, organisés par année, utilisez la requête suivante :

   ```sql
   SELECT year, fishing_entity AS Country, CAST(CAST(SUM(landed_value) AS DOUBLE) AS DECIMAL(38,2)) AS ValueEEZAndOpenSeasCatch 
   FROM fishdb.data_source_#####
   WHERE (area_name LIKE '%Fiji%' OR area_name IS NULL) AND fishing_entity='Fiji' AND year > 2000
   GROUP BY year, fishing_entity
   ORDER BY year;
   ```

   **Analyse :** Si vos données sont bien formatées et que le robot d'exploration AWS Glue a correctement mis à jour la table de métadonnées, alors les résultats que vous obtenez des deux premières requêtes de cette étape devraient s'additionner aux résultats que vous obtenez de la troisième requête.

   Par exemple, si vous additionnez la valeur **ValueOpenSeasCatch** de 2001 et la valeur **ValueEEZCatch** de 2001, le total devrait être égal à la valeur **ValueEEZAndOpenSeasCatch** de 2001. Si vos résultats sont cohérents avec cette description, cela indique que votre solution fonctionne comme prévu.

---

5. Créez une vue dans Athena, qui sera utile pour examiner les données dans la prochaine section de ce projet de fin de parcours.

   Exécutez la requête suivante. Remplacez **data_source_#####** par le nom de votre table **data_source** :

   ```sql
   CREATE OR REPLACE VIEW MackerelsCatch AS
   SELECT year, area_name AS WhereCaught, fishing_entity AS Country, SUM(tonnes) AS TotalWeight
   FROM fishdb.data_source_#####
   WHERE common_name LIKE '%Mackerels%' AND year > 2014
   GROUP BY year, area_name, fishing_entity, tonnes
   ORDER BY tonnes DESC;
   ```

   - Pour vérifier que la vue contient des données, dans le panneau **Data**, sous **Views**, choisissez l'icône avec trois points à droite de la vue **mackerelscatch**, puis choisissez **Preview View**. Vous devriez voir une sortie similaire à l'exemple suivant.


![image](https://github.com/user-attachments/assets/c1ba626e-c787-4b88-b6da-792a464f555f)




Ensuite, vous exécuterez quelques requêtes sur la vue Athena pour analyser les données.

1. Pour voir les données suivantes : **Tonnes de maquereaux capturés par année et par pays**.

   - Retournez dans l'éditeur de requêtes Athena et exécutez la requête SQL suivante pour identifier les pays ayant capturé le plus de maquereaux chaque année.

   ```sql
   SELECT year, Country, MAX(TotalWeight) AS Weight
   FROM fishdb.mackerelscatch 
   GROUP BY year, Country
   ORDER BY year, Weight DESC;
   ```

   Vous devriez voir une sortie similaire à ce qui suit.


![image](https://github.com/user-attachments/assets/7f75bf7e-2b96-4876-94fd-b54d53cbcc31)



Pour afficher les captures de maquereaux (**MackerelsCatch**) pour un pays particulier, par exemple la Chine, exécutez la requête suivante.

```sql
SELECT * FROM "fishdb"."mackerelscatch" 
WHERE country IN ('China');
```


![image](https://github.com/user-attachments/assets/bf25f050-ae4a-4d88-8db9-5e50f712fdff)




Remarque : Vous pouvez exécuter des requêtes supplémentaires sur la vue ou la table pour obtenir plus d'informations sur les données.

# Soumission de votre travail

1. Pour enregistrer vos progrès, choisissez **Submit** en haut de ces instructions.

2. Lorsque vous y êtes invité, choisissez **Yes**.

3. Après quelques minutes, le panneau de notes apparaît et vous montre combien de points vous avez obtenus pour chaque tâche. Si les résultats ne s'affichent pas après quelques minutes, choisissez **Grades** en haut de ces instructions.

   **Astuce :** Vous pouvez soumettre votre travail plusieurs fois. Après avoir modifié votre travail, choisissez à nouveau **Submit**. Votre dernière soumission sera enregistrée pour ce lab.

4. Pour obtenir un retour détaillé sur votre travail, choisissez **Submission Report**.

---

# Fin de votre session

**Rappel :** Cet environnement de lab est persistant. Les données sont conservées jusqu'à ce que vous utilisiez le budget alloué ou jusqu'à la date de fin du cours (selon ce qui survient en premier).

Pour préserver votre budget lorsque vous avez terminé pour la journée, ou lorsque vous avez terminé de travailler activement sur la tâche pour le moment, procédez comme suit :

1. En haut de cette page, choisissez **End Lab**, puis choisissez **Yes** pour confirmer que vous souhaitez mettre fin au lab.

   Un panneau de message indique que le lab est en cours de terminaison.

   **Remarque :** Choisir **End Lab** dans cet environnement de projet de fin de parcours ne supprimera pas les ressources que vous avez créées. Elles seront toujours là la prochaine fois que vous choisirez **Start Lab** (par exemple, un autre jour).

2. Pour fermer le panneau, choisissez **Close** dans le coin supérieur droit.









