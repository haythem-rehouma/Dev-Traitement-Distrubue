
# Plan de cours — *Scala & Spark pour le Big Data (de 0 à Pro)*

## Module 1 — Paradigmes & positionnement de Scala

* **1.1** Paradigme structuré : déf., caractéristiques, sous-routines/boucles/conditions (C, Pascal).
  *Contenu à utiliser :* ta section « Paradigme de Programmation Structuré ».
* **1.2** Paradigme OO : encapsulation, héritage, polymorphisme (Java/C++/Python).
  *Contenu à utiliser :* ta section « Paradigme de Programmation Orienté Objet ».
* **1.3** Paradigme fonctionnel : fonctions 1ʳᵉ classe, immutabilité, éval. paresseuse (Haskell, Clojure).
  *Contenu à utiliser :* ta section « Paradigme de Programmation Fonctionnel ».
* **1.4** Pourquoi **Scala** pour la donnée : définition, JVM & interop Java, système de types, pattern matching, concur­rence (futures/actors).
  *Contenu à utiliser :* ta section « Le langage SCALA — Définition & Caractéristiques ».
* **Quiz 1** : choisir le bon paradigme selon un problème; vrai/faux sur immutabilité vs encapsulation.

## Module 2 — Bases de Scala (prise en main rapide)

* **2.1** Syntaxe essentielle : `val`/`var`, types, `if/else`, `while`, `for`, fonctions, `Unit`.
  *Contenu à utiliser :* `TestHello`, `TestHelloVal`, `TestHelloWhile`, `TestHelloFOR`.
* **2.2** Collections & opérations : `List`, `Array`, `Map`, `Tuple`; `map/filter/foreach`.
  *Contenu à utiliser :* `TestHelloLISTES`, `TestHelloMaps`, `TestHelloTableaux`, `TestHelloTuples`.
* **2.3** Pattern matching & options (aperçu) + bonnes pratiques d’immutabilité.
* **TP 1 (20 min)** : réécrire `TestHello*` en **style immuable** + mini-exercices.

## Module 3 — Fonctions lambda & FP pragmatique

* **3.1** Lambdas en Scala : syntaxe courte `x => ...` et underscores `_`.
  *Contenu à utiliser :* ta section « Les fonctions lambda » + l’exemple `nombres.filter`.
* **3.2** Chaînage d’opérateurs sur collections (pipeline), différences `map` vs `flatMap`.
  *Contenu à utiliser :* tes exemples `map`/`flatMap`.
* **3.3** Erreurs fréquentes (capture d’état mutable, effets de bord).
* **Exos** : 5 katas de collection (filtrage, comptage, groupements).

## Module 4 — Environnement de dev (Windows) & IDE

* **4.1** Installer **JDK** (lien officiel) → vérifier `JAVA_HOME`.
* **4.2** Installer **Hadoop winutils** (HDP binaries) & **Spark** → dossier `C:\SPARKHADOOP\{hadoop-3.0.0, spark-x.y.z}`.
  *Contenu à utiliser :* « M. HADOOP (PARTIE 2) », « M. SPARK (PARTIE 3) », captures figure 1.1/1.2.
* **4.3** Variables d’environnement : `JAVA_HOME`, `HADOOP_HOME`, `SPARK_HOME`, **PATH**.
  *Contenu à utiliser :* tes étapes « AJOUT DES VARIABLES D’ENVIRONNEMENT / PATH ».
* **4.4** **IntelliJ IDEA Community** + plugin Scala; project template.
* **Note version** : différences Spark **3.x** vs anciennes distros (Cloudera **1.3.0**), méthodes `SQLContext`, `toDF`.

## Module 5 — Spark Core : RDD, transformations & actions

* **5.1** Création d’un RDD : `textFile`, `parallelize`, `collect`.
* **5.2** Transformations vs actions (lazy/eager) ; WordCount complet.
  *Contenu à utiliser :* toute ta **PARTIE 1** (WordCount, `flatMap/map/reduceByKey`, DAG `rdd1→rdd4`).
* **5.3** Filtres, `groupBy`, `sortBy`, `keys/values`, `glom`, `reduce/fold`.
  *Contenu à utiliser :* exemples `filter`, `groupBy`, `glom`, `fold` (explications multi-partitions).
* **5.4** Opés ensemblistes & key-based : `union/intersection/subtract/distinct`, `cartesian`, `groupByKey/reduceByKey/sortByKey`.
  *Contenu à utiliser :* **PARTIE 3**.
* **TP 2** : WordCount + top-N (`takeOrdered`) + comparaison `groupByKey` vs `reduceByKey`.

## Module 6 — Spark SQL, DataFrames & Datasets

* **6.1** `RDD → DataFrame` (`toDF`, `SQLContext` vs `SparkSession`) + *pièges de version*.
  *Contenu à utiliser :* tes notes **PARTIE 6** (+ « SOLUTION 0/1/2 » : Spark 3.x, PySpark/Colab, Cloudera).
* **6.2** Lecture JSON & CSV, schéma, `printSchema`, vues temporaires, requêtes SQL.
  *Contenu à utiliser :* **PARTIE 7** (people.json), **PARTIE 8** (titanic.csv).
* **6.3** Aller/retour `DataFrame ⇄ RDD` pour MapReduce custom ; nettoyage chaînes, `toString`, `replace`, `flatMap`.
  *Contenu à utiliser :* **PARTIE 9** (comptage de prénoms, ex. *Emil*).
* **TP 3** : charger Titanic, 5 requêtes SQL + 3 transfo RDD, écrire résultats.

## Module 7 — JDBC & intégration SGBDR (PostgreSQL)

* **7.1** Driver JDBC (classpath), `spark.read.format("jdbc")`, `dbtable`, filtres, `distinct`.
  *Contenu à utiliser :* **PARTIE 10** (commande `spark-shell --driver-class-path …`, code `jdbcDF`).
* **7.2** Lecture/écriture tables, bonnes pratiques (pushdown, partitions).

## Module 8 — Streaming

* **8.1** **DStreams (legacy)** : `socketTextStream`, `reduceByKey`, fenêtres (`reduceByKeyAndWindow`).
  *Contenu à utiliser :* **PARTIE 14/15** (netcat `nc -lk 9988`, `foreachRDD`, file stream).
* **8.2** **Structured Streaming (recommandé)** : équivalents en DataFrame (expliquer l’approche moderne).
* **TP 4** : compteur de mots temps réel + fenêtre glissante 9s/3s.

## Module 9 — Outils de build & tests

* **9.1** *Java refresher* & **Maven** : cycle de vie, `pom.xml`, tests JUnit.
  *Contenu à utiliser :* « Pratique 3 – Maven (1/2) », commandes `mvn compile/test/package/...`.
* **9.2** Passer à **sbt** pour Scala (aperçu) + **ScalaTest** (à ajouter en démo).
* **TP 5** : convertir le mini-projet *Calculator* Maven → sbt (bonus : ré-écrire tests en ScalaTest).

## Module 10 — Projet fil rouge (ETL batch + SQL + streaming)

* **10.1** Ingestion : fichier texte/CSV + WordCount + stats titanic.
* **10.2** Nettoyage/agrégations, persistance (Parquet/Delta si dispo).
* **10.3** Option temps réel : source socket → agrégations fenêtrées.
* **Livrables** : notebook/`main.scala`, rapport court, scripts de lancement.

## Module 11 — Révision & optimisation (survol)

* Partitions, `cache/persist`, *wide vs narrow*, coûteux `groupByKey`, lecture colonne (CSV→Parquet), *tips* de perf.

## Module 12 — Évaluations & ressources

* **Éval. formative** : ateliers RDD/DF/SQL (tes liens Drive/Youtube).
* **Éval. sommative** : projet fil rouge + quiz.
* **Ressources** : site Scala, docs Spark, *sparkbyexamples*, *mungingdata* (références déjà listées dans tes notes).

---

## Correspondance « tes contenus → modules »

* Paradigmes (structuré/OO/fonctionnel) → **Module 1**.
* Scala déf./caractéristiques + lambdas → **Modules 1–3**.
* Install JDK/Hadoop/Spark + ENV/PATH + IntelliJ → **Module 4**.
* `HelloWorldBigDataStreaming` + `TestHello*` → **Modules 2–5**.
* **PARTIE 1/2/3/4/5** (RDD, WordCount, union/intersection, fold/glom…) → **Module 5**.
* **PARTIE 6/7/8/9** (DF/SQL, JSON/CSV, DF⇄RDD, Titanic) → **Module 6**.
* **PARTIE 10** (Postgres JDBC) → **Module 7**.
* **PARTIE 14/15** (Streaming DStreams, fenêtres, `foreachRDD`) → **Module 8**.
* **Pratique Maven** (commands, pom, tests) → **Module 9**.

---

## Format Thinkific (exemple de squelette)

* **Module 1** : 1.1–1.4 + *Quiz 1*
* **Module 2** : 2.1–2.3 + *TP 1*
* **Module 3** : 3.1–3.3 + *Exos lambdas*
* **Module 4** : 4.1–4.4 (avec captures d’écran)
* **Module 5** : 5.1–5.4 + *TP 2*
* **Module 6** : 6.1–6.3 + *TP 3*
* **Module 7** : 7.1–7.2
* **Module 8** : 8.1–8.2 + *TP 4*
* **Module 9** : 9.1–9.2 + *TP 5*
* **Module 10** : 10.1–10.3 (*Projet fil rouge*)
* **Module 11** : révision/perf
* **Module 12** : évaluations & ressources

