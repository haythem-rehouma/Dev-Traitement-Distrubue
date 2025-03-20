### 1. **Amazon EMR (Elastic MapReduce)**
Amazon EMR est un service cloud d'Amazon Web Services (AWS) qui facilite le traitement de grands ensembles de données en utilisant des frameworks tels que Hadoop, Spark, HBase, et Presto. EMR permet de créer et gérer des clusters d'instances EC2 pour le traitement de données distribuées. Il simplifie le processus en gérant les configurations matérielles, la mise à l'échelle et les défaillances matérielles.

- **Utilisation dans ce lab** : Dans ce laboratoire, Amazon EMR est utilisé pour traiter de grands volumes de données de journaux web. Le service gère le cluster Hadoop qui exécute Apache Hive pour permettre l'analyse des données.

### 2. **Amazon EC2 (Elastic Compute Cloud)**
Amazon EC2 est un service d'informatique en nuage qui permet de louer des machines virtuelles (instances) sur lesquelles vous pouvez déployer vos applications. EC2 fournit une capacité de calcul évolutive qui peut être utilisée pour une variété de charges de travail.

- **Utilisation dans ce lab** : Dans ce laboratoire, Amazon EMR utilise des instances EC2 pour exécuter les nœuds du cluster Hadoop. Ces instances sont configurées pour traiter les données et exécuter des tâches MapReduce.

### 3. **Amazon S3 (Simple Storage Service)**
Amazon S3 est un service de stockage d'objets qui permet de stocker et récupérer des données à grande échelle via une interface simple et hautement disponible. Il est utilisé pour stocker des fichiers tels que des données de journaux, des images, des vidéos, etc.

- **Utilisation dans ce lab** : Les journaux de clics et d'impressions sont stockés dans des compartiments S3, et les résultats des requêtes Hive sont également écrits dans un compartiment S3. Hive utilise S3 comme source et destination de données.

### 4. **Apache Hadoop**
Hadoop est un framework open-source permettant de stocker et traiter de grandes quantités de données dans un environnement distribué. Le composant clé de Hadoop est HDFS (Hadoop Distributed File System), qui divise les données en blocs et les stocke sur différents nœuds du cluster.

- **Utilisation dans ce lab** : Hadoop gère le stockage et le traitement des données sur le cluster EMR. Il utilise HDFS pour stocker les métadonnées des tables Hive et les partitions temporaires des tables.

### 5. **Apache Hive**
Hive est un entrepôt de données qui permet d'interroger de grandes quantités de données stockées dans des systèmes distribués à l'aide de requêtes de type SQL. Les requêtes Hive sont traduites en tâches MapReduce qui s'exécutent sur le cluster Hadoop.

- **Utilisation dans ce lab** : Hive est utilisé pour créer des tables à partir des journaux d'impressions et de clics, et pour exécuter des requêtes SQL afin de découvrir des tendances dans les données. Les résultats sont stockés dans S3.

### 6. **YARN (Yet Another Resource Negotiator)**
YARN est un système de gestion des ressources de Hadoop. Il permet de planifier et d'exécuter des tâches en distribuant la charge de travail sur plusieurs nœuds du cluster.

- **Utilisation dans ce lab** : Lorsque vous exécutez des requêtes Hive, elles sont converties en tâches YARN qui sont distribuées sur les nœuds du cluster pour un traitement efficace.

### 7. **AWS Cloud9**
AWS Cloud9 est un environnement de développement intégré (IDE) qui fonctionne dans le cloud et permet de coder, exécuter et déboguer vos projets directement dans un navigateur web. Il prend en charge plusieurs langages de programmation et s'intègre facilement avec d'autres services AWS.

- **Utilisation dans ce lab** : AWS Cloud9 est utilisé pour se connecter au nœud principal du cluster EMR via SSH et exécuter des commandes Hive et Hadoop dans un terminal bash intégré.

Ces services permettent de traiter des ensembles de données massifs en fournissant une infrastructure élastique et un traitement distribué efficace, tout en simplifiant l'analyse avec des outils comme Hive et S3 pour le stockage.
