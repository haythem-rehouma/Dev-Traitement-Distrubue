### **Quiz : PySpark et Manipulation de Données avec DataFrames**
**Instructions :**  
- Ce quiz contient **40 questions** basées sur le code PySpark ci-dessus.  
- Chaque question est à **choix unique**.  
- Le quiz teste la **compréhension des fonctions utilisées**, leur **effet sur les DataFrames** et leur **utilisation correcte**.

---

## **Section 1 : Chargement des Données (Q1 - Q10)**

**Q1.** Quelle option permet d'inférer automatiquement les types de colonnes lors du chargement d'un fichier CSV avec PySpark ?  
a) `.option("header", "true")`  
b) `.option("delimiter", ",")`  
c) `.option("inferSchema", "true")`  
d) `.option("schema", "auto")`  

**Q2.** Quelle commande permet de créer une vue temporaire d’un DataFrame pour exécuter des requêtes SQL ?  
a) `df.createTempView("table")`  
b) `df.createOrReplaceTempView("table")`  
c) `df.createSQLView("table")`  
d) `df.registerTempTable("table")`  

**Q3.** Quel est l’effet de la fonction `display(df_movies)` dans Databricks ?  
a) Crée un graphique automatiquement  
b) Affiche le contenu du DataFrame dans un tableau interactif  
c) Affiche seulement les premières 5 lignes du DataFrame  
d) Retourne la structure du DataFrame  

**Q4.** Quelle méthode Spark permet de filtrer les lignes où la colonne `rating` est supérieure à 3 ?  
a) `df.filter(df.rating > 3)`  
b) `df.select(df.rating > 3)`  
c) `df.where(df.rating > 3)`  
d) `df.filter("rating > 3")`  

**Q5.** Quelle commande permet d’afficher le nombre total de lignes dans `df_movies` ?  
a) `df_movies.show()`  
b) `df_movies.describe()`  
c) `df_movies.count()`  
d) `df_movies.len()`  

**Q6.** Quelle fonction PySpark est utilisée pour charger un fichier CSV ?  
a) `spark.read("file.csv")`  
b) `spark.load("file.csv")`  
c) `spark.read.format("csv").load("file.csv")`  
d) `pandas.read_csv("file.csv")`  

**Q7.** Quel argument est nécessaire pour indiquer que la première ligne du fichier contient des en-têtes ?  
a) `.option("schema", "header")`  
b) `.option("header", "true")`  
c) `.option("header", "false")`  
d) `.option("columns", "first_row")`  

**Q8.** Comment ajouter une nouvelle colonne `year` contenant uniquement l'année de la colonne `Date` ?  
a) `df.withColumn("year", df.Date.year())`  
b) `df.withColumn("year", f.year(df.Date))`  
c) `df.with("year", f.year(df.Date))`  
d) `df.addColumn("year", f.year(df.Date))`  

**Q9.** Quelle fonction permet de grouper les lignes d’un DataFrame selon une colonne ?  
a) `df.groupBy("column_name")`  
b) `df.aggregate("column_name")`  
c) `df.summarize("column_name")`  
d) `df.split("column_name")`  

**Q10.** Quelle est la principale différence entre `df.show()` et `display(df)` dans Databricks ?  
a) `df.show()` affiche une table interactive, `display(df)` non  
b) `display(df)` fonctionne uniquement sur les DataFrames SQL  
c) `df.show()` affiche un tableau en texte brut, `display(df)` crée un tableau interactif  
d) `df.show()` ne fonctionne pas dans Databricks  

---

## **Section 2 : Manipulation des Données (Q11 - Q20)**

**Q11.** Quelle commande permet de supprimer les lignes dupliquées d’un DataFrame ?  
a) `df.dropDuplicates()`  
b) `df.removeDuplicates()`  
c) `df.dropDuplicates(["col1", "col2"])`  
d) `df.deleteDuplicates()`  

**Q12.** Comment renommer une colonne `old_name` en `new_name` ?  
a) `df.renameColumn("old_name", "new_name")`  
b) `df.withColumnRenamed("old_name", "new_name")`  
c) `df.rename("old_name", "new_name")`  
d) `df.changeColumn("old_name", "new_name")`  

**Q13.** Quelle commande permet de trier un DataFrame en ordre décroissant selon une colonne `rating` ?  
a) `df.orderBy("rating")`  
b) `df.sort("rating", ascending=False)`  
c) `df.orderBy(df.rating.desc())`  
d) `df.sortBy(df.rating, desc=True)`  

**Q14.** Quelle est la différence entre `select()` et `filter()` ?  
a) `select()` filtre les lignes, `filter()` sélectionne des colonnes  
b) `select()` sélectionne des colonnes, `filter()` filtre les lignes  
c) `select()` et `filter()` font la même chose  
d) `filter()` ne fonctionne que sur les DataFrames SQL  

**Q15.** Comment ajouter une colonne contenant la valeur 10 pour toutes les lignes ?  
a) `df.withColumn("new_col", f.lit(10))`  
b) `df.addColumn("new_col", f.lit(10))`  
c) `df.newColumn("new_col", f.lit(10))`  
d) `df.assign("new_col", f.lit(10))`  

**Q16.** Quelle méthode permet de combiner deux DataFrames verticalement (concaténation) ?  
a) `df1.join(df2)`  
b) `df1.concat(df2)`  
c) `df1.union(df2)`  
d) `df1.merge(df2)`  

**Q17.** Quelle est la fonction utilisée pour appliquer une transformation à chaque élément d'une colonne ?  
a) `df.map(f.transform_function)`  
b) `df.withColumn("new_col", f.transform_function(df.col))`  
c) `df.apply(f.transform_function, axis=1)`  
d) `df.transform(f.transform_function, "column_name")`  

**Q18.** Quelle fonction permet de transformer un timestamp en date lisible ?  
a) `df.withColumn("date", f.to_date(df.timestamp))`  
b) `df.transform("timestamp", "date")`  
c) `df.withColumn("date", f.timestampToDate(df.timestamp))`  
d) `df.withColumn("date", f.date_format(df.timestamp, "yyyy-MM-dd"))`  

**Q19.** Quelle commande permet de limiter l’affichage à 20 lignes ?  
a) `df.limit(20)`  
b) `df.show(n=20)`  
c) `df.head(20)`  
d) `df.display(20)`  

**Q20.** Comment convertir une colonne en type entier ?  
a) `df.withColumn("col", df.col.cast(IntegerType()))`  
b) `df.withColumn("col", df.col.cast("integer"))`  
c) `df.withColumn("col", df.col.astype(int))`  
d) `df.convertColumn("col", "int")`  

---

## **Section 3 : Agrégation et Jointures (Q21 - Q30)**

**Q21.** Quelle commande permet de calculer la moyenne des notes ?  
a) `df.agg(f.mean("rating"))`  
b) `df.aggregate(f.avg("rating"))`  
c) `df.groupBy().mean("rating")`  
d) `df.groupBy().agg(f.avg("rating"))`  

**Q22.** Quelle fonction est utilisée pour joindre deux DataFrames sur `movieId` ?  
a) `df1.merge(df2, "movieId")`  
b) `df1.join(df2, "movieId", "inner")`  
c) `df1.concat(df2, "movieId")`  
d) `df1.link(df2, "movieId")`  

**Q23.** Quelle commande permet de sélectionner les films avec plus de 100 évaluations ?  
a) `df.filter(df.count() > 100)`  
b) `df.where(df.rating_count > 100)`  
c) `df.filter("rating_count > 100")`  
d) `df.select(df.rating_count > 100)`  

---

## **Section 4 : Fenêtrage et Groupement Avancé (Q24 - Q30)**  

**Q24.** Quelle fonction permet d’obtenir le rang d’une ligne dans une fenêtre définie par une colonne spécifique ?  
a) `rank().over(Window.partitionBy("col"))`  
b) `row_number().over(Window.partitionBy("col"))`  
c) `dense_rank().over(Window.partitionBy("col"))`  
d) `ntile().over(Window.partitionBy("col"))`  

**Q25.** Quelle est la principale différence entre `rank()` et `dense_rank()` ?  
a) `rank()` saute les valeurs en cas d’égalité, `dense_rank()` ne saute pas  
b) `dense_rank()` saute les valeurs en cas d’égalité, `rank()` ne saute pas  
c) `rank()` fonctionne seulement sur les dates  
d) `dense_rank()` ne fonctionne que sur les nombres  

**Q26.** Comment calculer une moyenne mobile sur une colonne `rating` en utilisant une fenêtre de 7 jours ?  
a) `df.withColumn("moving_avg", avg("rating").over(Window.rowsBetween(-6, 0)))`  
b) `df.withColumn("moving_avg", avg("rating").over(Window.rangeBetween(-6, 0)))`  
c) `df.withColumn("moving_avg", moving_avg("rating", 7))`  
d) `df.withColumn("moving_avg", Window.avg("rating").partitionBy(7))`  

**Q27.** Quelle fonction permet de récupérer la valeur maximale d’une colonne dans une fenêtre définie par `movieId` ?  
a) `df.withColumn("max_value", max("rating").over(Window.partitionBy("movieId")))`  
b) `df.withColumn("max_value", Window.max("rating").groupBy("movieId"))`  
c) `df.groupBy("movieId").max("rating")`  
d) `df.withColumn("max_value", ranking("rating").over(Window.partitionBy("movieId")))`  

**Q28.** Comment créer une fenêtre de partition basée sur `userId` et triée par `rating_date` ?  
a) `Window.partitionBy("userId").orderBy("rating_date")`  
b) `Window.groupBy("userId").sortBy("rating_date")`  
c) `Window.partitionBy("userId").sort("rating_date")`  
d) `Window.over("userId", "rating_date")`  

**Q29.** Quelle fonction permet de créer une fenêtre mobile pour calculer un total cumulatif sur `rating` ?  
a) `df.withColumn("cumsum", sum("rating").over(Window.orderBy("rating_date").rowsBetween(Window.unboundedPreceding, 0)))`  
b) `df.withColumn("cumsum", sum("rating").cumulative(Window.partitionBy("rating_date")))`  
c) `df.withColumn("cumsum", rolling_sum("rating", Window.partitionBy("rating_date")))`  
d) `df.withColumn("cumsum", total("rating").over(Window.partitionBy("rating_date")))`  

**Q30.** Quel est l’effet d’une clause `Window.partitionBy("userId")` dans PySpark ?  
a) Il regroupe les données en sous-ensembles basés sur `userId`, permettant l’application de fonctions analytiques  
b) Il divise les DataFrames en plusieurs fichiers stockés séparément  
c) Il réorganise les lignes en fonction de `userId`  
d) Il applique uniquement des tris sur `userId` sans partitionnement réel  

---

## **Section 5 : Requêtes SQL dans PySpark (Q31 - Q35)**  

**Q31.** Quelle commande permet d’exécuter une requête SQL sur un DataFrame après l’avoir enregistré comme vue temporaire ?  
a) `spark.sql("SELECT * FROM movies_csv")`  
b) `df.sql("SELECT * FROM movies_csv")`  
c) `movies_csv.run("SELECT *")`  
d) `execute("SELECT * FROM movies_csv")`  

**Q32.** Comment transformer une colonne contenant une date en type `DATE` en SQL sur une vue temporaire ?  
a) `SELECT TO_DATE(date_column) FROM table`  
b) `SELECT CAST(date_column AS DATE) FROM table`  
c) `SELECT FORMAT(date_column, "yyyy-MM-dd") FROM table`  
d) `SELECT date_format(date_column, "DATE") FROM table`  

**Q33.** Quel est l’effet de `spark.conf.set("spark.sql.legacy.timeParserPolicy", "LEGACY")` ?  
a) Active la compatibilité avec d’anciennes versions de gestion des dates dans Spark  
b) Convertit automatiquement les timestamps en format UTC  
c) Force Spark à refuser toute conversion de date incorrecte  
d) Active un mode strict empêchant les formats de dates non conformes  

**Q34.** Comment exécuter une requête SQL qui calcule la moyenne des `rating` par `movieId` ?  
a) `spark.sql("SELECT movieId, AVG(rating) FROM movies_csv GROUP BY movieId")`  
b) `spark.sql("SELECT movieId, SUM(rating) FROM movies_csv GROUP BY movieId")`  
c) `df.select("movieId", "AVG(rating)").groupBy("movieId")`  
d) `df.runQuery("SELECT AVG(rating) FROM movies_csv GROUP BY movieId")`  

**Q35.** Comment convertir une chaîne `timestamp` en format date directement via une requête SQL ?  
a) `SELECT TO_DATE(timestamp_column) FROM table`  
b) `SELECT FROM_UNIXTIME(timestamp_column) FROM table`  
c) `SELECT TO_TIMESTAMP(timestamp_column) FROM table`  
d) `SELECT CAST(timestamp_column AS DATE) FROM table`  

---

## **Section 6 : Transformations et Optimisation (Q36 - Q40)**  

**Q36.** Quelle fonction permet d’afficher le schéma d’un DataFrame ?  
a) `df.describe()`  
b) `df.printSchema()`  
c) `df.showSchema()`  
d) `df.schema()`  

**Q37.** Comment vérifier le type d’une colonne spécifique dans un DataFrame ?  
a) `df.schema["col_name"]`  
b) `df.printSchema("col_name")`  
c) `df.dtypes["col_name"]`  
d) `df.schema.fields[df.schema.fieldNames().index("col_name")].dataType`  

**Q38.** Quelle fonction permet de supprimer les valeurs nulles d’un DataFrame ?  
a) `df.dropna()`  
b) `df.filter(df.isNotNull())`  
c) `df.removeNulls()`  
d) `df.cleanData()`  

**Q39.** Comment remplir les valeurs manquantes dans une colonne `rating` avec la moyenne des valeurs existantes ?  
a) `df.fillna(df.agg({"rating": "avg"}).collect()[0][0], "rating")`  
b) `df.fillna("rating", df.mean("rating"))`  
c) `df.replaceNulls("rating", "mean")`  
d) `df.fillna(df.avg("rating"))`  

**Q40.** Quelle méthode permet d’optimiser les performances en **cache** un DataFrame ?  
a) `df.cache()`  
b) `df.persist(StorageLevel.MEMORY_AND_DISK)`  
c) `df.memoryCache()`  
d) `df.optimizeCache()`  

---

## **Fin du Quiz** 🎯  

Ce quiz vous permet de tester vos **compétences avancées** sur **PySpark**, en particulier sur :  
- **Chargement et transformation des données**  
- **Agrégation et jointures**  
- **Fenêtrage et requêtes SQL**  
- **Optimisation et gestion des données**  
