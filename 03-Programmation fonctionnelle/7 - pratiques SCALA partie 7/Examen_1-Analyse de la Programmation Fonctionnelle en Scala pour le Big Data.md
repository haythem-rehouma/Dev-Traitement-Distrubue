# **Examen 1 : Analyse de la Programmation Fonctionnelle en Scala pour le Big Data**  

**Objectif :** Cet examen ne vise pas à coder mais à **analyser et interpréter** les concepts abordés en **Big Data avec Scala et Apache Spark**.  

**Instructions :** Pour chaque question, **expliquez votre raisonnement** en analysant le code fourni et en répondant aux questions posées.  

---

# **Partie 1 : Définition d'une `case class` pour représenter des logs de serveur**  

### **Question 1 : Création de la `case class` `LogEntry`**  

```plaintext
Définir une case class LogEntry avec les attributs indiqués  
Créer une instance de LogEntry et l'afficher  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
LogEntry(2024-03-06T12:00:00, 192.168.1.1, /home, 200)
```

#### **💬 Réponse du code :**  

```scala
case class LogEntry(timestamp: String, ip: String, url: String, statusCode: Int)

val log = LogEntry("2024-03-06T12:00:00", "192.168.1.1", "/home", 200)
println(log)
```

#### **💬 Analyse et réflexion**  
1. **Pourquoi utilise-t-on une `case class` au lieu d'une `class` normale dans ce contexte ?**  

Les `case class` en Scala offrent plusieurs avantages par rapport aux classes normales. Elles sont **immutables par défaut**, ce qui signifie que leurs valeurs ne peuvent pas être modifiées après leur création, ce qui garantit l'intégrité des données dans un environnement distribué comme Spark. Elles génèrent également **automatiquement les méthodes `equals`, `hashCode` et `toString`**, facilitant la comparaison et la manipulation des objets. De plus, elles supportent le **pattern matching**, ce qui est utile pour manipuler les données de manière expressive et lisible.  

2. **Quels sont les avantages d'une `case class` en Big Data, notamment avec Spark ?**  

Dans Spark, les `case class` sont utilisées pour définir des **schémas explicites** pour les `Dataset` et `DataFrame`. Cela permet à Spark d'inférer le schéma automatiquement et de **bénéficier des optimisations Catalyst**, ce qui améliore la vitesse de traitement. Contrairement aux `RDD` qui traitent des tuples anonymes, les `case class` permettent un **typage fort**, réduisant les erreurs à l'exécution et améliorant la lisibilité du code.  

3. **Quels problèmes pourrait-on rencontrer si on ne définissait pas de `case class` et utilisait une structure générique comme un `Tuple` ou un `Map` ?**  

Si nous utilisions des `Tuple` ou des `Map`, le code deviendrait moins lisible et plus sujet aux erreurs. Par exemple, au lieu d'accéder aux attributs par leur nom (`logEntry.timestamp`), il faudrait utiliser des indices (`tuple._1`), ce qui rend le code plus difficile à comprendre et à maintenir. De plus, Spark ne pourrait pas optimiser les transformations aussi efficacement, car il ne pourrait pas **inférer un schéma structuré** comme avec une `case class`.  

---

# **Partie 2 : Chargement des logs en RDD et transformation**  

### **Question 2 : Charger un RDD de logs depuis un fichier**  

```plaintext
Lire un fichier de logs contenant des lignes au format : timestamp,ip,url,statusCode  
Convertir chaque ligne en un objet LogEntry  
Afficher les 5 premières lignes du RDD  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
Array(LogEntry(2024-03-06T12:00:00,192.168.1.1,/home,200), ...)
```

#### **💬 Réponse du code :**  

```scala
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder.appName("LogProcessing").master("local").getOrCreate()
val sc = spark.sparkContext

val logsRDD = sc.textFile("logs.csv")
  .map(_.split(","))
  .filter(_.length == 4)
  .map(cols => LogEntry(cols(0), cols(1), cols(2), cols(3).toInt))

logsRDD.take(5).foreach(println)
```

#### **💬 Analyse et réflexion**  
1. **Pourquoi Spark utilise-t-il des RDD pour le traitement de fichiers volumineux ?**  

Les `RDD` (Resilient Distributed Datasets) sont la **structure de base** de Spark pour le traitement distribué. Ils permettent de **répartir automatiquement** les données sur plusieurs nœuds et assurent la **tolérance aux pannes** en conservant un graphe d'exécution permettant de **recalculer les données perdues**. Les `RDD` supportent également des **transformations paresseuses**, c'est-à-dire qu'aucun calcul n'est effectué tant qu'une action (comme `collect()` ou `count()`) n'est déclenchée. Cela permet d'optimiser le traitement des fichiers volumineux en **minimisant le nombre de passes sur les données**.  

2. **Quels avantages apporte le passage de données brutes (`String`) à des objets `LogEntry` en termes de performance et de lisibilité ?**  

Lorsque les données restent sous forme de `String`, chaque transformation doit effectuer une **conversion explicite**, ce qui **augmente la charge de calcul** et rend le code plus difficile à maintenir. En transformant directement les données en objets `LogEntry`, nous bénéficions d’un **typage fort**, ce qui permet **d'éviter des erreurs de conversion** et de rendre le code **plus clair et plus expressif**. De plus, cela permet à Spark d’appliquer des **optimisations basées sur le schéma** lorsqu’on convertit l’`RDD` en `DataFrame`.  

3. **Dans un environnement de production, quel format de fichier serait préférable (CSV, Parquet, JSON) et pourquoi ?**  

Le format **Parquet** est généralement préféré en production, car il est **colonne-orienté**, ce qui permet d’accélérer les requêtes en ne lisant que les colonnes nécessaires. Il offre également une **compression efficace**, réduisant l’espace de stockage et **accélérant la lecture** des fichiers. Contrairement au **CSV**, qui est ligne par ligne et non optimisé pour la lecture partielle, Parquet est particulièrement **adapté aux traitements analytiques**. Le **JSON**, quant à lui, est plus lisible mais **consomme plus d’espace** et **nécessite plus de puissance de calcul** pour être parsé.  





---

# **Partie 3 : Filtrage des erreurs 500 dans les logs**  

### **Question 3 : Filtrer les logs où `statusCode == 500`**  

```plaintext
Filtrer les logs pour ne garder que ceux avec un statusCode == 500  
Afficher le nombre d'erreurs 500  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
Nombre d'erreurs 500 : 42
```

#### **💬 Réponse du code :**  

```scala
val erreurs500 = logsRDD.filter(_.statusCode == 500)
val nombreErreurs500 = erreurs500.count()
println(s"Nombre d'erreurs 500 : $nombreErreurs500")
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi le filtrage des erreurs 500 est-il essentiel dans l'analyse des logs serveurs ?**  

Le code HTTP `500` indique une **erreur interne du serveur**, ce qui signifie qu'une requête a échoué **non pas à cause du client**, mais à cause d'un **problème de traitement sur le serveur**. Ces erreurs peuvent résulter de **problèmes logiciels**, d'une **base de données surchargée**, ou d'un **bug dans l'application**. Filtrer et analyser ces erreurs permet d’**identifier rapidement les dysfonctionnements**, d’**optimiser la stabilité du serveur**, et de **réduire le temps d'arrêt** des services.  

2. **Si nous devions surveiller les erreurs en temps réel, quel type d’architecture (batch vs streaming) recommanderiez-vous et pourquoi ?**  

Une **architecture en streaming** serait recommandée pour une **détection immédiate** des erreurs et une **réaction rapide**. Par exemple, **Apache Kafka** pourrait être utilisé pour **collecter les logs en temps réel**, tandis que **Spark Streaming** permettrait de **traiter ces données en continu** et d’**envoyer des alertes** dès qu'un certain seuil d'erreurs `500` est atteint.  

À l’inverse, un **traitement batch** serait plus adapté pour **une analyse historique**, permettant de comprendre **les tendances et l'évolution des erreurs** sur une longue période.  

3. **Comment pourrait-on optimiser ce filtrage pour des données massives dépassant plusieurs téraoctets ?**  

Dans un contexte **Big Data**, plusieurs stratégies peuvent être mises en place pour accélérer ce filtrage :  

- **Partitionnement des données** : Stocker les logs par **date** ou par **code HTTP** pour ne traiter que les fichiers pertinents plutôt que l’ensemble des logs.  
- **Indexation des fichiers** : Utiliser des formats comme **Parquet** qui permettent de filtrer les données directement au niveau du stockage sans charger l’ensemble en mémoire.  
- **Filtrage avant stockage** : Appliquer une **prémodélisation** des logs en attribuant une **catégorie d’erreur** pour ne récupérer que celles qui sont critiques.  
- **Stockage en mémoire avec cache()** : Si le filtrage est souvent répété, stocker les résultats intermédiaires en mémoire avec `cache()` pour éviter de recharger les données à chaque exécution.  

En combinant ces approches, on peut considérablement **réduire le temps de traitement** et **optimiser l’analyse des erreurs** sur de très gros volumes de logs.  




----

# **Partie 4 : Compter les accès par URL (`reduceByKey`)**  

### **Question 4 : Nombre d'accès par URL**  

```plaintext
Transformer le RDD pour obtenir des tuples (url, 1)  
Utiliser reduceByKey pour compter le nombre d'accès par URL  
Afficher les 5 URLs les plus visitées  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
/home -> 5000 accès  
/about -> 3500 accès  
/contact -> 1200 accès  
```

#### **💬 Réponse du code :**  

```scala
val urlCounts = logsRDD
  .map(log => (log.url, 1)) // Transformer chaque log en un tuple (url, 1)
  .reduceByKey(_ + _) // Agréger les accès par URL

val top5Urls = urlCounts
  .sortBy(_._2, ascending = false) // Trier par nombre d'accès, en ordre décroissant
  .take(5) // Récupérer les 5 URLs les plus visitées

top5Urls.foreach { case (url, count) => println(s"$url -> $count accès") }
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi `reduceByKey` est-il plus efficace que `groupByKey` dans Spark ?**  

`reduceByKey` est **plus efficace** que `groupByKey` car il **réduit localement les valeurs** avant de les **envoyer sur le réseau**, ce qui diminue **le volume de données transférées** entre les nœuds.  

Dans `groupByKey`, toutes les valeurs associées à une clé sont envoyées à un même nœud, ce qui **peut créer un goulet d’étranglement mémoire** si une clé possède trop de valeurs. En revanche, `reduceByKey` applique l’agrégation **en amont**, permettant une meilleure **répartition des charges** et **une exécution plus rapide** sur des datasets volumineux.  

2. **Si nous devions calculer les URL les plus consultées sur une période d’un an, quelles techniques de partitionnement ou d'indexation proposeriez-vous ?**  

Pour optimiser la requête sur **une année complète de logs**, il serait intéressant de :  

- **Partitionner les données par date** (ex: année/mois/jour) pour limiter la quantité de données traitées à chaque requête.  
- **Stocker les logs sous format Parquet** avec une organisation en colonnes, permettant des lectures sélectives très rapides.  
- **Utiliser des index secondaires** (comme **Z-Order Indexing** sur Databricks) pour accélérer la recherche des URL populaires sans nécessiter de lecture complète du dataset.  
- **Mettre en cache les résultats intermédiaires** si cette requête est exécutée fréquemment.  

Avec ces stratégies, le calcul des URLs les plus visitées pourrait être **optimisé et exécuté de manière incrémentale** au lieu de tout recalculer à chaque fois.  

3. **Comment Spark gère-t-il la distribution des calculs lorsqu’on utilise `reduceByKey` ?**  

Lorsqu'on utilise `reduceByKey`, Spark applique une **exécution distribuée** en plusieurs étapes :  

1. **Phase de mappage** : Spark transforme chaque ligne en une paire clé-valeur `(url, 1)`.  
2. **Phase de réduction locale** : Chaque partition effectue une agrégation intermédiaire **localement** pour réduire la quantité de données envoyées sur le réseau.  
3. **Phase de shuffle et agrégation finale** : Les résultats intermédiaires sont envoyés aux nœuds responsables des différentes clés (urls), qui effectuent une **réduction finale** pour obtenir le nombre total d’accès par URL.  

Ce mécanisme de **combinaison locale avant transmission** permet d'optimiser **l'utilisation du réseau** et d'assurer un traitement efficace sur des **datasets massifs**.  



---


# **Partie 5 : Transformation en `DataFrame`**  

### **Question 5 : Convertir les logs en `DataFrame`**  

```plaintext
Convertir le RDD de LogEntry en DataFrame  
Afficher le schéma du DataFrame  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
root
 |-- timestamp: string
 |-- ip: string
 |-- url: string
 |-- statusCode: integer
```

#### **💬 Réponse du code :**  

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

val spark = SparkSession.builder.appName("LogProcessing").master("local").getOrCreate()
import spark.implicits._

// Convertir le RDD en DataFrame
val logsDF = logsRDD.toDF()

// Afficher le schéma du DataFrame
logsDF.printSchema()
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi est-il préférable d'utiliser des `DataFrame` plutôt que des `RDD` en termes de performances ?**  

Les `DataFrame` sont **plus optimisés** que les `RDD` car ils bénéficient de **Catalyst Optimizer**, un moteur d'optimisation interne de Spark qui :  

- **Réorganise les opérations** pour minimiser le nombre de scans des données.  
- **Fusionne les transformations** pour éviter les recalculs inutiles.  
- **Utilise Tungsten** pour optimiser l’exécution en mémoire et tirer parti des **instructions bas niveau du processeur**.  
- **Prend en charge des formats colonne-orientés** comme **Parquet**, qui permettent des lectures plus rapides que les RDD traditionnels.  

Contrairement aux RDD, qui sont **des collections d’objets distribuées**, les `DataFrame` sont **structurés** et permettent d’utiliser une syntaxe proche de SQL, facilitant l’interaction avec les données.  

2. **Quelles optimisations Spark peut-il appliquer automatiquement sur un `DataFrame` mais pas sur un `RDD` ?**  

Spark applique plusieurs **optimisations automatiques** sur un `DataFrame` grâce à Catalyst Optimizer :  

- **Projection Pushdown** : Spark lit uniquement les colonnes nécessaires au lieu de charger l’ensemble du dataset.  
- **Predicate Pushdown** : Les filtres (`WHERE` en SQL) sont appliqués **avant** de charger les données, réduisant ainsi le volume à traiter.  
- **Rewriting des requêtes** : Spark peut réarranger certaines expressions SQL pour exécuter les calculs plus efficacement.  
- **Optimisation des jointures** : Catalyst choisit automatiquement l’algorithme de jointure optimal (`Broadcast Join`, `Sort Merge Join`, etc.) en fonction de la taille des datasets.  

Ces optimisations permettent de **réduire la latence et d’améliorer les performances** sans nécessiter d’interventions manuelles.  

3. **Quels seraient les avantages d'utiliser un `Dataset` au lieu d'un `DataFrame` dans ce cas ?**  

Les `Dataset` offrent plusieurs avantages :  

- **Typage fort** : Contrairement aux `DataFrame`, qui sont typés dynamiquement (`Row`), un `Dataset[LogEntry]` conserve les types Scala, évitant les erreurs de conversion et améliorant la sécurité à la compilation.  
- **Meilleure expressivité** : Avec un `Dataset`, on peut utiliser des **opérations fonctionnelles Scala** (`map`, `filter`, etc.) avec un typage strict, ce qui améliore la lisibilité du code.  
- **Combinaison des avantages de RDD et DataFrame** : Il offre à la fois **les optimisations Catalyst** et la **puissance des transformations fonctionnelles**.  

Cependant, les `Dataset` sont légèrement plus **lents que les DataFrame** dans les cas où Spark peut utiliser des **opérations SQL pures**, car la sérialisation d’objets Scala peut introduire une légère surcharge.  




---

# **Partie 6 : Filtrer les logs avec les `DataFrame API`**  

### **Question 6 : Sélectionner les logs d’erreur avec DataFrame API**  

```plaintext
Filtrer le DataFrame pour ne garder que les erreurs  
Sélectionner les colonnes timestamp, ip et statusCode  
Afficher les 5 premières erreurs  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```
timestamp       | ip             | statusCode
-------------------------------------------------
2024-03-06T12:00:00 | 192.168.1.1 | 500
2024-03-06T12:05:00 | 192.168.1.2 | 404
```

#### **💬 Réponse du code :**  

```scala
import org.apache.spark.sql.functions._

// Filtrer uniquement les erreurs (statusCode >= 400)
val erreursDF = logsDF.filter(col("statusCode") >= 400)
  .select("timestamp", "ip", "statusCode")

// Afficher les 5 premières erreurs
erreursDF.show(5)
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi est-il préférable d'utiliser `DataFrame API` au lieu de manipuler les `RDD` directement pour filtrer les erreurs ?**  

L’utilisation des `DataFrame API` offre plusieurs **avantages par rapport aux RDD** :  

- **Optimisation automatique** : Spark applique **Catalyst Optimizer**, qui optimise la requête en **réorganisant les filtres**, **réduisant le volume des données** traitées et **optimisant l’accès aux fichiers**.  
- **Utilisation de code SQL-like** : La syntaxe est **plus concise** et **plus lisible** qu’avec les transformations RDD (`map`, `filter`, etc.).  
- **Meilleure gestion de la mémoire** : Les `DataFrame` utilisent des **structures de données optimisées** en mémoire (Tungsten), ce qui réduit **l'empreinte mémoire** et améliore **l’exécution parallèle** sur le cluster Spark.  

Ainsi, filtrer les erreurs directement sur un `DataFrame` est **plus efficace et plus performant** que d’utiliser un `RDD`.  

2. **Pourquoi le filtrage est-il plus efficace lorsqu’il est appliqué en amont du traitement des données ?**  

Le **Predicate Pushdown** est une technique d’optimisation utilisée par Spark qui consiste à **appliquer les filtres aussi tôt que possible** dans l’exécution du plan de requête.  

- Si le dataset est stocké dans un **format colonne-orienté comme Parquet**, Spark peut **charger uniquement les colonnes et lignes nécessaires** au lieu de lire l’intégralité du fichier.  
- En filtrant en amont, on **réduit le volume de données traitées** par les transformations suivantes (`select`, `groupBy`, etc.), améliorant ainsi **les performances globales**.  
- Moins de données en mémoire signifie **moins de consommation de ressources**, ce qui réduit les risques d’**OOM (Out of Memory)** sur les grands datasets.  

3. **Comment pourrait-on optimiser davantage ce filtrage sur un cluster Spark exécutant des requêtes en continu ?**  

Si le filtrage est exécuté en **streaming ou en batch récurrent**, plusieurs optimisations peuvent être mises en place :  

- **Indexation et partitionnement** : Stocker les logs **partitionnés par date et statusCode**, pour que Spark ne charge que les partitions concernées.  
- **Utilisation de cache ou persist()** : Si les erreurs doivent être analysées plusieurs fois, stocker le `DataFrame` en mémoire avec `.cache()` ou `.persist(StorageLevel.MEMORY_AND_DISK)`.  
- **Filtrage précoce avec Delta Lake** : En utilisant **Delta Lake**, on peut exécuter **des filtres transactionnels** et ne récupérer que les nouvelles erreurs depuis la dernière exécution.  
- **Optimisation via le format de stockage** : Préférer **Parquet** avec **Z-Ordering sur la colonne statusCode** pour accélérer les requêtes filtrées.  

En combinant ces approches, on **réduit le temps de traitement** et on **améliore la scalabilité** du pipeline d'analyse des erreurs.  



---

# **Partie 7 : Création d’un `Dataset` pour un typage fort**  

### **Question 7 : Utiliser `Dataset[LogEntry]` au lieu de `DataFrame`**  

```plaintext
Convertir le DataFrame en Dataset[LogEntry]  
Afficher les 5 premiers logs du Dataset  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
Dataset[LogEntry]
+-------------------+--------------+--------+-----------+
| timestamp        | ip           | url    | statusCode |
+-------------------+--------------+--------+-----------+
| 2024-03-06T12:00 | 192.168.1.1  | /home  | 200       |
+-------------------+--------------+--------+-----------+
```

#### **💬 Réponse du code :**  

```scala
// Import nécessaire pour les Datasets
import spark.implicits._

// Convertir le DataFrame en Dataset[LogEntry]
val logsDS = logsDF.as[LogEntry]

// Afficher les 5 premiers logs du Dataset
logsDS.show(5)
```

#### **💬 Analyse et réflexion**  

1. **Quelle est la principale différence entre un `DataFrame` et un `Dataset` ?**  

Les `DataFrame` et `Dataset` ont une structure **similaire**, mais la **différence principale** réside dans le **typage** :  

- Un **`DataFrame`** est une collection de **`Row` dynamiques**, où les types de colonnes ne sont **connus qu’à l’exécution**. Il est optimisé pour les **opérations SQL et les requêtes analytiques**.  
- Un **`Dataset[LogEntry]`** est **typé statiquement**, ce qui signifie que les erreurs sont **détectées à la compilation**. Il permet d’utiliser **les opérations fonctionnelles Scala (`map`, `filter`) tout en conservant les optimisations Catalyst**.  

En résumé, **le Dataset combine la puissance de la programmation fonctionnelle avec les optimisations de Spark SQL**.  

2. **Pourquoi choisir un `Dataset[LogEntry]` dans ce cas plutôt qu’un `DataFrame` ?**  

Le choix d’un `Dataset[LogEntry]` est préférable lorsque :  

- **On veut un typage fort** : En utilisant une `case class`, Spark garantit que **les opérations sont sûres** et que les erreurs de type sont détectées **avant l’exécution**.  
- **On effectue beaucoup d'opérations fonctionnelles** (`map`, `flatMap`, `filter`) : Le `Dataset` offre **une meilleure expressivité** que les `DataFrame`.  
- **On veut éviter des erreurs de conversion** : Dans un `DataFrame`, une erreur peut survenir si l’on accède à une colonne avec un mauvais type (`df("statusCode").as[Int]` peut échouer à l’exécution).  

Cependant, **un `DataFrame` est plus rapide** lorsqu’on exécute uniquement des requêtes **SQL-like**, car Spark peut optimiser davantage les plans d’exécution sans se soucier du typage Scala.  

3. **Quels seraient les cas où un `DataFrame` serait plus approprié qu’un `Dataset` ?**  

L’utilisation d’un `DataFrame` est **préférable** dans les situations suivantes :  

- **Traitement de très grands volumes de données** : Un `DataFrame` bénéficie de meilleures **optimisations Catalyst**, alors qu’un `Dataset` doit manipuler des **objets Scala**, ce qui **augmente la sérialisation** et peut ralentir les traitements.  
- **Requêtes SQL complexes** : Si l'on exécute beaucoup de **jointures et d’agrégations**, un `DataFrame` est plus efficace car Spark optimise **l’exécution en colonne-orienté**.  
- **Interopérabilité avec d'autres outils** : Les `DataFrame` sont compatibles avec **PySpark, R et d’autres langages**, alors que les `Dataset` sont spécifiques à **Scala et Java**.  

Dans ce cas précis, **si nous devons effectuer des analyses avancées sur les logs**, un `Dataset[LogEntry]` est un bon choix car il offre **la lisibilité du code avec la sécurité du typage fort**.  





---

# **Partie 8 : Aggregation avec `groupBy` et `count`**  

### **Question 8 : Compter les accès par IP**  

```plaintext
Grouper les logs par IP  
Compter le nombre d'accès par IP  
Afficher les 5 IPs les plus actives  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
192.168.1.1 -> 1000 accès  
192.168.1.2 -> 850 accès  
192.168.1.3 -> 720 accès  
192.168.1.4 -> 500 accès  
192.168.1.5 -> 350 accès  
```

#### **💬 Réponse du code :**  

```scala
import org.apache.spark.sql.functions._

// Grouper les logs par IP et compter les accès
val accessByIP = logsDF
  .groupBy("ip")
  .count()
  .orderBy(desc("count"))

// Afficher les 5 IPs les plus actives
accessByIP.show(5)
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi utiliser `groupBy` et `count` dans ce cas, et quelles optimisations Spark applique-t-il en interne ?**  

L'agrégation avec `groupBy("ip").count()` permet de **compter efficacement le nombre d'accès par IP** sans nécessiter de calculs complexes.  

**Optimisations appliquées par Spark :**  

- **Aggregation Pushdown** : Spark exécute **les filtres et regroupements directement au niveau du stockage** (ex. Parquet, ORC), ce qui réduit la quantité de données à lire.  
- **Hash Aggregation** : Au lieu d’un tri coûteux, Spark utilise un **hash map distribué**, qui est plus rapide pour agréger des milliers de valeurs uniques.  
- **Combinaison locale** : Avant d'envoyer les résultats sur le réseau, Spark **pré-agrège les données dans chaque partition**, limitant le volume de shuffle (transfert réseau).  

Grâce à ces optimisations, Spark peut **analyser des milliards de logs en quelques secondes**.  

2. **Pourquoi faut-il trier les résultats (`orderBy(desc("count"))`) avant d’afficher les IPs les plus actives ?**  

Sans tri, les résultats seraient affichés **dans un ordre aléatoire**, ce qui ne permettrait pas d'identifier immédiatement les IPs les plus actives.  

L'ajout de `.orderBy(desc("count"))` permet de :  

- **Prioriser les IPs générant le plus de trafic**, facilitant l’analyse des comportements anormaux.  
- **Optimiser les décisions de sécurité**, comme identifier des attaques DDoS ou bloquer des IPs suspectes.  
- **Faciliter la visualisation des données**, en mettant en évidence les IPs à surveiller en priorité.  

Dans un **environnement distribué**, Spark optimise le tri en utilisant **un algorithme de tri distribué**, garantissant une exécution efficace même sur des **datasets massifs**.  

3. **Comment gérer ce type d’agrégation si les logs sont stockés sur plusieurs jours ou mois ?**  

Si l’on doit **analyser les accès IP sur plusieurs jours/mois**, il est préférable de :  

- **Partitionner les logs par date (`logsDF.write.partitionBy("date")`)** pour éviter de charger toutes les données à chaque requête.  
- **Utiliser des index secondaires (`Z-Order`, `Bloom Filters`)** pour accélérer la recherche des IPs les plus actives sur une période donnée.  
- **Mettre en cache les résultats (`cache()` ou `persist()`)** si l’analyse des IPs les plus actives est réalisée fréquemment.  
- **Utiliser Delta Lake avec Time Travel** : Cela permet d’analyser **les logs à différentes périodes** sans requêtes coûteuses.  

Grâce à ces techniques, il est possible d'effectuer des **analyses de tendances sur plusieurs mois tout en gardant un temps de requête optimal**.  





---

# **Partie 9 : Gestion des erreurs avec `Try` et `Option`**  

### **Question 9 : Sécuriser la transformation de données avec `Option`**  

```plaintext
Utiliser Option pour éviter les erreurs lors du parsing des logs  
Retourner None si une ligne est mal formatée  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
Certaines lignes de logs mal formées sont ignorées.
```

#### **💬 Réponse du code :**  

```scala
import scala.util.Try

// Fonction pour parser une ligne de log en LogEntry
def parseLogLine(line: String): Option[LogEntry] = {
  val cols = line.split(",")
  if (cols.length == 4) {
    Try(LogEntry(cols(0), cols(1), cols(2), cols(3).toInt)).toOption
  } else {
    None
  }
}

// Appliquer la transformation en sécurisant le parsing
val parsedLogsRDD = logsRDD.flatMap(parseLogLine)

// Vérifier le nombre de logs valides après filtrage
println(s"Nombre de logs valides : ${parsedLogsRDD.count()}")
```

#### **💬 Analyse et réflexion**  

1. **Pourquoi utiliser `Option` et `Try` pour le parsing des logs ?**  

L’utilisation combinée de `Option` et `Try` permet de **sécuriser le parsing** en évitant les erreurs qui pourraient interrompre l’exécution du programme.  

- **`Option`** est utilisé pour gérer la possibilité qu’une ligne de log soit mal formée et éviter les erreurs fatales en **retournant `None` au lieu d'une exception**.  
- **`Try`** permet de capturer les erreurs lors de la conversion (`.toInt` peut échouer si `statusCode` n’est pas un entier) et de retourner un `None` proprement.  

Avec cette approche, **les logs valides sont traités normalement** tandis que **les logs mal formés sont ignorés sans perturber l’exécution globale**.  

2. **Quels problèmes pourraient survenir si l’on n’utilise pas `Option` pour filtrer les logs mal formés ?**  

Si on n’utilise pas `Option`, plusieurs problèmes peuvent apparaître :  

- **Crash de l’application** : Une erreur de conversion (`cols(3).toInt`) pourrait interrompre Spark et stopper le traitement de l’ensemble des logs.  
- **Perte de scalabilité** : Un seul log mal formaté pourrait empêcher l’analyse de plusieurs téraoctets de données.  
- **Difficulté de debugging** : Les erreurs pourraient être propagées sans être gérées proprement, compliquant le diagnostic des logs erronés.  

Avec `Option`, on garantit que **les logs erronés sont automatiquement filtrés**, tout en **préservant l’analyse des logs valides**.  

3. **Comment pourrait-on gérer ces erreurs à grande échelle sans perdre d’informations sur les logs mal formés ?**  

Si nous voulons **analyser aussi les erreurs** et non juste les ignorer, nous pouvons utiliser **une approche plus avancée** :  

- **Diviser les logs en deux catégories** :  
  - Les logs valides sont stockés dans un `RDD[LogEntry]`.  
  - Les logs mal formés sont enregistrés séparément avec un message d’erreur.  

- **Stocker les erreurs dans un fichier dédié** :  
  - Au lieu de perdre ces logs, on pourrait **les stocker dans un DataFrame contenant `ligne_originale` + `erreur_detectée`**.  

- **Créer une alerte en cas de pics d’erreurs** :  
  - Si un nombre trop élevé de logs mal formés est détecté, une **alerte** peut être déclenchée pour avertir l’équipe d’ingénierie.  

Voici une alternative qui permettrait d'archiver les erreurs tout en filtrant les logs valides :  

```scala
val (validLogsRDD, invalidLogsRDD) = logsRDD.map(line => (line, parseLogLine(line)))
  .partition { case (_, parsed) => parsed.isDefined }

val errorsDF = invalidLogsRDD.map { case (line, _) => (line, "Parsing error") }.toDF("raw_log", "error_reason")
errorsDF.write.format("parquet").save("errors_logs/")
```

Avec cette approche, nous avons **un contrôle total sur les erreurs**, et nous pouvons analyser **les logs erronés pour améliorer la qualité des données en amont**.  



---

# **Partie 10 : Optimisation avec `cache()` et `persist()`**  

### **Question 10 : Accélérer les requêtes avec `cache()`**  

```plaintext
Ajouter un cache() ou persist() sur le DataFrame des erreurs  
```

```scala
...........................................................
...........................................................
...........................................................
```

📌 **💡 Output attendu :**  

```scala
Les requêtes sont accélérées grâce au cache.
```

#### **💬 Réponse du code :**  

```scala
import org.apache.spark.storage.StorageLevel

// Appliquer un cache sur le DataFrame des erreurs pour éviter de recalculer les transformations
val erreursDF = logsDF.filter(col("statusCode") >= 400)
  .select("timestamp", "ip", "statusCode")
  .cache() // Mise en cache en mémoire

// Vérifier si le cache est bien utilisé
println(s"Nombre d'erreurs 400 et plus : ${erreursDF.count()}")

// Alternative avec persist() pour stockage sur disque et mémoire
val erreursPersistedDF = logsDF.filter(col("statusCode") >= 400)
  .select("timestamp", "ip", "statusCode")
  .persist(StorageLevel.MEMORY_AND_DISK) // Sauvegarde en mémoire et sur disque en cas de surcharge
```

#### **💬 Analyse et réflexion**  

1. **Quelle est la différence entre `cache()` et `persist()` en Spark ?**  

- **`cache()`** : Stocke les données **uniquement en mémoire**, ce qui permet d’éviter de **recalculer les transformations** à chaque exécution.  
- **`persist(level)`** : Permet de choisir **le niveau de stockage**, par exemple :  
  - `MEMORY_ONLY` : Stocke les données uniquement en mémoire (équivalent à `cache()`).  
  - `MEMORY_AND_DISK` : Stocke les données en mémoire, et si l’espace manque, les écrit sur disque.  
  - `DISK_ONLY` : Garde les données uniquement sur disque, utile si la mémoire est limitée.  

Si les données **tiennent en mémoire**, `cache()` est plus rapide. Si elles sont **trop volumineuses**, `persist(StorageLevel.MEMORY_AND_DISK)` est préférable.  

2. **Pourquoi utiliser `cache()` améliore-t-il les performances, notamment dans les requêtes répétées ?**  

Sans `cache()`, Spark **recalcule à chaque fois toutes les transformations** (`filter()`, `select()`, etc.) dès qu'une nouvelle action (`count()`, `show()`) est déclenchée.  

Avec `cache()` :  
- Les **données transformées** sont conservées en mémoire, **évitant un recalcul coûteux**.  
- Les **requêtes répétées** sur les mêmes données sont **beaucoup plus rapides**.  
- Cela **réduit l’utilisation du CPU et des ressources du cluster**, car Spark **n’a plus besoin de relire et retraiter les données**.  

⚠ **Attention** : `cache()` doit être utilisé uniquement si **les données sont utilisées plusieurs fois**. Si les données ne sont utilisées qu'une seule fois, `cache()` peut **alourdir inutilement la mémoire**.  

3. **Dans quels cas le `cache()` pourrait-il être une mauvaise pratique, et quelle alternative recommanderiez-vous ?**  

Le `cache()` peut **dégrader les performances** si :  
- **Les données sont trop volumineuses** pour tenir en mémoire, ce qui **entraîne des évictions fréquentes** et des recalculs involontaires.  
- **Les transformations ne sont utilisées qu'une seule fois**, rendant le cache inutile.  
- **D’autres processus Spark utilisent la mémoire**, ce qui peut **entraîner des erreurs OutOfMemory (OOM)**.  

📌 **Alternatives recommandées :**  
- **Utiliser `persist(StorageLevel.MEMORY_AND_DISK)`** pour éviter la perte de cache en cas de manque de mémoire.  
- **Matérialiser les résultats en écrivant dans un fichier temporaire** (ex. format Parquet sur HDFS ou S3).  
- **Utiliser Delta Lake** qui permet de conserver **des snapshots optimisés** de DataFrames.  

Exemple d’une alternative avec écriture de DataFrame sur disque pour éviter l’usage intensif de la mémoire :  

```scala
erreursDF.write.mode("overwrite").parquet("hdfs://path/to/temp/errors")
```

Cela permet de **stocker et récupérer les erreurs** sans surcharger la mémoire de Spark.  

---

Avec cette dernière optimisation, nous avons vu **comment Spark gère efficacement la mémoire et les performances** en utilisant **cache et persist**.  

💡 **Dernière question générale :**  
✅ **Si vous deviez améliorer cet algorithme pour le rendre encore plus performant et scalable, quelles stratégies proposeriez-vous ?**  

---

🎯 **Cet examen vous a permis d'explorer** :  
- **Les bases du traitement Big Data avec Scala et Spark**.  
- **L’importance des structures optimisées (`case class`, RDD, DataFrame, Dataset)`**.  
- **Les bonnes pratiques pour analyser les logs et gérer les erreurs**.  
- **Les techniques avancées pour optimiser les performances en environnement distribué**.  

💭 **Prêt à passer à un cas d'utilisation réel ?**

---

# Annexe - **Cas d'utilisation réel : Analyse des logs d'un site web en production**  

### **Problématique :**  
Une entreprise gérant un site à **fort trafic** souhaite **analyser les logs de ses serveurs web** afin de :  
- Identifier les **erreurs fréquentes** (ex : erreurs 500, requêtes non trouvées).  
- Détecter les **IPs suspectes** générant un trafic anormal.  
- Trouver les **pages les plus visitées** et celles posant problème.  

Les logs sont stockés sur un **cluster HDFS** et sont mis à jour **en continu**.  

---

## **Objectifs de l'analyse :**  
Vous devez répondre aux questions suivantes **en analysant les données avec Spark** :  

### **1. Identification des erreurs les plus fréquentes**  
📌 **Question :**  
- Comment détecter les **erreurs HTTP les plus fréquentes** et en visualiser l'évolution dans le temps ?  

💡 **Piste de réflexion :**  
- Faut-il utiliser un **batch processing** ou un **streaming processing** ? Pourquoi ?  
- Comment **structurer les données** pour obtenir des tendances temporelles efficaces ?  

---

### **2. Détection des IPs suspectes**  
📌 **Question :**  
- Comment détecter **des adresses IP suspectes** ayant un comportement anormal (ex : attaques DDoS) ?  

💡 **Piste de réflexion :**  
- Quels indicateurs pourraient signaler une attaque (ex : **nombre de requêtes par seconde**, **répartition des codes d’erreurs**) ?  
- Comment **partitionner les données** pour améliorer la détection d’anomalies ?  

---

### **3. Analyse des pages les plus visitées**  
📌 **Question :**  
- Comment identifier **les pages qui génèrent le plus de trafic** et celles qui posent problème ?  

💡 **Piste de réflexion :**  
- Faut-il compter uniquement les accès ou également **le temps de réponse des pages** ?  
- Comment visualiser ces informations efficacement (ex : **tableau de bord avec Grafana, tableau Parquet optimisé**).  

---

## **Approche technique recommandée :**  
Voici les grandes étapes d’implémentation avec **Spark et Scala** :  

1. **Lecture des logs depuis HDFS ou S3** :  
   - Stockés en **format Parquet (optimisé)** ou en **CSV/JSON brut**.  
   - Chargement via `spark.read.parquet()` ou `spark.read.csv()`.  

2. **Transformation et filtrage des données** :  
   - Convertir en **DataFrame / Dataset** pour bénéficier des **optimisations Catalyst**.  
   - Nettoyage des logs erronés via **`Option` et `Try`**.  

3. **Analyse des tendances et anomalies** :  
   - **Agrégation temporelle** (`groupBy("timestamp").count()`) pour visualiser l’évolution des erreurs.  
   - **Détection d’IP suspectes** avec des **requêtes Spark SQL** et un **seuil d’alerte dynamique**.  

4. **Optimisation et persistance des résultats** :  
   - Stockage des résultats **en mémoire (`cache()` ou `persist()`)** si requêtes fréquentes.  
   - Sauvegarde des résultats optimisés en **Parquet** sur HDFS ou **Delta Lake**.  

---

## **Livrables attendus :**  
🎯 **Dans un contexte professionnel, vous devriez livrer :**  
✔ **Un script Scala Spark** exécutant l'analyse et générant des tableaux de bord.  
✔ **Un rapport technique** expliquant les résultats et les axes d'amélioration.  
✔ **Une proposition d’optimisation de l’architecture** pour un meilleur traitement des logs en temps réel.  

---

### **Dernière question d’analyse :**  
✅ **Si vous deviez mettre en place un système d’analyse de logs scalable sur plusieurs années, comment structureriez-vous votre pipeline de données ?**  

💡 **Réflexion attendue :**  
- **Comment stocker les logs efficacement (Partitionnement, Delta Lake, Indexation) ?**  
- **Quelle solution utiliser pour accélérer les requêtes sur des gros volumes (Spark, Presto, Apache Druid) ?**  
- **Comment intégrer un système de monitoring pour les erreurs en temps réel ?**  
