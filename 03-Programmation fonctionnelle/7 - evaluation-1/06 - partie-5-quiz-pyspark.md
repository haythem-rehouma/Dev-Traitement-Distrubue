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
