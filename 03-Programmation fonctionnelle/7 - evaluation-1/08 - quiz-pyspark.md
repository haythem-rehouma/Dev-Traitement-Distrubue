### **Quiz : PySpark et Manipulation de Données avec DataFrames**

**Instructions :**  

- Chaque question est à **choix unique**.  
- Sélectionnez la **meilleure réponse** pour chaque question.

---

## **Section 1 : Chargement des Données (Q1 - Q10)**

**Q1.** Quelle option permet d'inférer automatiquement les types de colonnes lors du chargement d'un fichier CSV avec PySpark ?  
🔘 `.option("header", "true")`  
🔘 `.option("delimiter", ",")`  
🔘 `.option("inferSchema", "true")`  
🔘 `.option("schema", "auto")`  

**Q2.** Quelle commande permet de créer une vue temporaire d’un DataFrame pour exécuter des requêtes SQL ?  
🔘 `df.createTempView("table")`  
🔘 `df.createOrReplaceTempView("table")`  
🔘 `df.createSQLView("table")`  
🔘 `df.registerTempTable("table")`  

**Q3.** Quel est l’effet de la fonction `display(df_movies)` dans Databricks ?  
🔘 Crée un graphique automatiquement  
🔘 Affiche le contenu du DataFrame dans un tableau interactif  
🔘 Affiche seulement les premières 5 lignes du DataFrame  
🔘 Retourne la structure du DataFrame  

**Q4.** Quelle méthode Spark permet de filtrer les lignes où la colonne `rating` est supérieure à 3 ?  
🔘 `df.filter(df.rating > 3)`  
🔘 `df.select(df.rating > 3)`  
🔘 `df.where(df.rating > 3)`  
🔘 `df.filter("rating > 3")`  

**Q5.** Quelle commande permet d’afficher le nombre total de lignes dans `df_movies` ?  
🔘 `df_movies.show()`  
🔘 `df_movies.describe()`  
🔘 `df_movies.count()`  
🔘 `df_movies.len()`  

---

## **Section 2 : Manipulation des Données (Q11 - Q20)**

**Q11.** Quelle commande permet de supprimer les lignes dupliquées d’un DataFrame ?  
🔘 `df.dropDuplicates()`  
🔘 `df.removeDuplicates()`  
🔘 `df.dropDuplicates(["col1", "col2"])`  
🔘 `df.deleteDuplicates()`  

**Q12.** Comment renommer une colonne `old_name` en `new_name` ?  
🔘 `df.renameColumn("old_name", "new_name")`  
🔘 `df.withColumnRenamed("old_name", "new_name")`  
🔘 `df.rename("old_name", "new_name")`  
🔘 `df.changeColumn("old_name", "new_name")`  

**Q13.** Quelle commande permet de trier un DataFrame en ordre décroissant selon une colonne `rating` ?  
🔘 `df.orderBy("rating")`  
🔘 `df.sort("rating", ascending=False)`  
🔘 `df.orderBy(df.rating.desc())`  
🔘 `df.sortBy(df.rating, desc=True)`  

**Q14.** Quelle est la différence entre `select()` et `filter()` ?  
🔘 `select()` filtre les lignes, `filter()` sélectionne des colonnes  
🔘 `select()` sélectionne des colonnes, `filter()` filtre les lignes  
🔘 `select()` et `filter()` font la même chose  
🔘 `filter()` ne fonctionne que sur les DataFrames SQL  

---

## **Section 3 : Agrégation et Jointures (Q21 - Q30)**

**Q21.** Quelle commande permet de calculer la moyenne des notes ?  
🔘 `df.agg(f.mean("rating"))`  
🔘 `df.aggregate(f.avg("rating"))`  
🔘 `df.groupBy().mean("rating")`  
🔘 `df.groupBy().agg(f.avg("rating"))`  

**Q22.** Quelle fonction est utilisée pour joindre deux DataFrames sur `movieId` ?  
🔘 `df1.merge(df2, "movieId")`  
🔘 `df1.join(df2, "movieId", "inner")`  
🔘 `df1.concat(df2, "movieId")`  
🔘 `df1.link(df2, "movieId")`  

---

## **Section 4 : Fenêtrage et Groupement Avancé (Q31 - Q35)**  

**Q31.** Quelle fonction permet d’obtenir le rang d’une ligne dans une fenêtre définie par une colonne spécifique ?  
🔘 `rank().over(Window.partitionBy("col"))`  
🔘 `row_number().over(Window.partitionBy("col"))`  
🔘 `dense_rank().over(Window.partitionBy("col"))`  
🔘 `ntile().over(Window.partitionBy("col"))`  

**Q32.** Quelle est la principale différence entre `rank()` et `dense_rank()` ?  
🔘 `rank()` saute les valeurs en cas d’égalité, `dense_rank()` ne saute pas  
🔘 `dense_rank()` saute les valeurs en cas d’égalité, `rank()` ne saute pas  
🔘 `rank()` fonctionne seulement sur les dates  
🔘 `dense_rank()` ne fonctionne que sur les nombres  

---

## **Section 5 : Requêtes SQL dans PySpark (Q36 - Q40)**  

**Q36.** Quelle commande permet d’exécuter une requête SQL sur un DataFrame après l’avoir enregistré comme vue temporaire ?  
🔘 `spark.sql("SELECT * FROM movies_csv")`  
🔘 `df.sql("SELECT * FROM movies_csv")`  
🔘 `movies_csv.run("SELECT *")`  
🔘 `execute("SELECT * FROM movies_csv")`  

**Q37.** Comment transformer une colonne contenant une date en type `DATE` en SQL sur une vue temporaire ?  
🔘 `SELECT TO_DATE(date_column) FROM table`  
🔘 `SELECT CAST(date_column AS DATE) FROM table`  
🔘 `SELECT FORMAT(date_column, "yyyy-MM-dd") FROM table`  
🔘 `SELECT date_format(date_column, "DATE") FROM table`  

---

## **Fin du Quiz**  

Ce quiz vous permet de tester vos **compétences avancées** sur **PySpark**, en particulier sur :  
- **Chargement et transformation des données**  
- **Agrégation et jointures**  
- **Fenêtrage et requêtes SQL**  
- **Optimisation et gestion des données**  
