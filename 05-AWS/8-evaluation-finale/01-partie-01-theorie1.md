# Partie 01 - Quiz avec *justification de la réponse* 

### *Pour chaque scénario décrit dans les questions suivantes, choisissez le ou les services AWS les plus adaptés. Justifiez votre choix en expliquant votre (ou vos) choix et pourquoi vous avez éliminé les autres options.*

-----------

#### Cas 1 :
**Scénario :** Vous avez besoin d’un stockage économique pour conserver des fichiers journaux non structurés provenant de plusieurs systèmes pendant de longues périodes. Ces données ne seront pas souvent requêtées, mais doivent être accessibles si nécessaire.
- **Choix :**
  - a) S3
  - b) Redshift
  - c) Glue
  - d) Lambda

#### Cas 2 :
**Scénario :** Vous devez exécuter une série de transformations ETL (Extract, Transform, Load) sur des fichiers CSV provenant de plusieurs sources, avant de les charger dans un entrepôt de données pour analyse.
- **Choix :**
  - a) Glue
  - b) S3
  - c) Redshift
  - d) Lambda

#### Cas 3 :
**Scénario :** Votre équipe doit analyser des données historiques structurées provenant de plusieurs années d'activité. Vous recherchez une solution qui offre de hautes performances pour les requêtes analytiques, avec un support pour des requêtes SQL complexes.
- **Choix :**
  - a) Redshift
  - b) S3
  - c) Glue
  - d) Lambda

#### Cas 4 :
**Scénario :** Vous souhaitez mettre en place une fonction qui se déclenche automatiquement dès que de nouveaux fichiers sont déposés dans un bucket de stockage. Cette fonction doit appliquer des transformations légères avant d'envoyer les données vers une autre application.
- **Choix :**
  - a) Lambda
  - b) Glue
  - c) S3
  - d) Redshift

#### Cas 5 :
**Scénario :** Votre entreprise souhaite construire un pipeline de traitement de données massives, où les fichiers sont transformés et chargés dans un entrepôt de données en continu. Vous devez gérer des volumes de données élevés avec des transformations complexes.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) S3
  - d) Redshift

#### Cas 6 :
**Scénario :** Vous avez besoin de stocker des données semi-structurées provenant de fichiers JSON, et vous devez permettre l'accès rapide à ces données pour des analyses en temps réel. Le coût et la rapidité d'accès aux données sont des facteurs importants.
- **Choix :**
  - a) S3
  - b) Redshift
  - c) Glue
  - d) Lambda

#### Cas 7 :
**Scénario :** Votre équipe doit déclencher des opérations ponctuelles basées sur des événements provenant de plusieurs sources. Les transformations sont mineures, mais la réactivité est cruciale. Vous cherchez une solution qui ne nécessite pas de gestion d’infrastructure.
- **Choix :**
  - a) Lambda
  - b) Glue
  - c) S3
  - d) Redshift

#### Cas 8 :
**Scénario :** Vous travaillez sur un projet qui nécessite le stockage et la préparation de grandes quantités de données pour des analyses futures. Cependant, les analyses ne sont pas encore définies et les données doivent être prêtes à tout moment.
- **Choix :**
  - a) S3
  - b) Redshift
  - c) Glue
  - d) Lambda

#### Cas 9 :
**Scénario :** Vous devez exécuter une analyse ad hoc sur un ensemble de données volumineuses stockées dans S3. Vous recherchez une solution qui ne nécessite pas le déplacement de ces données et qui permet de faire des requêtes SQL directement sur les fichiers.
- **Choix :**
  - a) Athena
  - b) Redshift
  - c) Glue
  - d) Lambda

#### Cas 10 :
**Scénario :** Un système d'alertes doit se déclencher lorsque certains seuils sont atteints dans les données entrantes. Les seuils sont analysés en temps réel et des actions doivent être prises immédiatement sur la base des résultats.
- **Choix :**
  - a) Lambda
  - b) Glue
  - c) S3
  - d) Redshift

#### Cas 11 :
**Scénario :** Vous devez mettre en place une solution de stockage haute performance capable d'effectuer des analyses OLAP sur de grandes quantités de données historiques. Ces analyses doivent être réalisées rapidement et sur des données structurées.
- **Choix :**
  - a) Redshift
  - b) Glue
  - c) Lambda
  - d) S3

#### Cas 12 :
**Scénario :** Votre équipe doit automatiser le traitement de fichiers XML entrant dans un bucket de stockage. Le processus doit valider les fichiers, les transformer au besoin, puis les envoyer vers un autre système pour traitement.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) S3
  - d) Redshift

#### Cas 13 :
**Scénario :** Vous devez effectuer des requêtes SQL complexes sur des jeux de données très volumineux et structurés dans un environnement analytique en temps quasi réel. Vous recherchez une solution capable de supporter ces volumes avec un coût modéré.
- **Choix :**
  - a) Redshift
  - b) Glue
  - c) Lambda
  - d) S3

#### Cas 14 :
**Scénario :** Un traitement de données léger doit être déclenché par l'ajout d'un fichier dans S3. Le traitement est simple, mais doit être effectué immédiatement après la réception du fichier.
- **Choix :**
  - a) Lambda
  - b) Glue
  - c) Redshift
  - d) S3

#### Cas 15 :
**Scénario :** Vous devez effectuer des transformations ETL complexes sur un ensemble de données provenant de plusieurs sources avant de charger ces données dans un entrepôt de données. Le volume des données et les opérations à effectuer nécessitent une gestion fine des ressources.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) Redshift
  - d) S3

#### Cas 16 :
**Scénario :** Vous avez un flux continu de données qui doivent être analysées en temps réel. Les données doivent être transformées et filtrées avant d'être envoyées vers une autre application pour un traitement supplémentaire.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) S3
  - d) Redshift

#### Cas 17 :
**Scénario :** Vous devez configurer un pipeline de traitement de données qui nécessite une transformation importante de données semi-structurées avant de les charger dans un entrepôt de données pour analyse. Les données proviennent de plusieurs sources et la gestion des dépendances est cruciale.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) Redshift
  - d) S3

#### Cas 18 :
**Scénario :** Vous avez besoin de déclencher automatiquement des fonctions lorsqu'un certain seuil de données est atteint dans un entrepôt de données. Les transformations à effectuer sont légères, mais doivent être faites rapidement.
- **Choix :**
  - a) Lambda
  - b) Glue
  - c) Redshift
  - d) S3

#### Cas 19 :
**Scénario :** Vous devez analyser des fichiers JSON directement à partir de leur stockage dans S3 sans avoir à les charger dans une base de données.
- **Choix :**
  - a) Athena
  - b) Glue
  - c) Redshift
  - d) Lambda

#### Cas 20 :
**Scénario :** Vous devez traiter un grand volume de données avec des transformations complexes qui nécessitent des performances élevées et une bonne gestion des ressources tout en les chargeant ensuite dans un entrepôt de données.
- **Choix :**
  - a) Glue
  - b) Lambda
  - c) Redshift
  - d) S3

