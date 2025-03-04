
### **Cheatsheet PySpark : Manipulation de Données avec DataFrames**

| **Concept**                 | **Description** | **Syntaxe** | **Exemple** |
|-----------------------------|---------------|------------|-------------|
| **Création d'un DataFrame** | Créer un DataFrame à partir d'une source de données ou de données locales | `spark.createDataFrame(data, schema)` | ```python df = spark.createDataFrame([(1, "Alice", 30)], ["id", "name", "age"]) ``` |
| **Chargement CSV** | Charger un fichier CSV avec options | `spark.read.option("header", "true").csv("file.csv")` | ```python df = spark.read.option("inferSchema", "true").csv("movies.csv") ``` |
| **Afficher les données** | Afficher le contenu d’un DataFrame | `df.show(n)` | ```python df.show(5) ``` |
| **Afficher le schéma** | Afficher la structure du DataFrame | `df.printSchema()` | ```python df.printSchema() ``` |
| **Compter les lignes** | Nombre total de lignes | `df.count()` | ```python total_rows = df.count() ``` |
| **Sélectionner des colonnes** | Extraire une ou plusieurs colonnes | `df.select("col_name")` | ```python df.select("name").show() ``` |
| **Filtrer les lignes** | Filtrer avec une condition | `df.filter(df.col > valeur)` | ```python df.filter(df.age > 25).show() ``` |
| **Filtrer avec `where`** | Alternative à `filter` | `df.where("col > valeur")` | ```python df.where("age > 25").show() ``` |
| **Renommer une colonne** | Modifier le nom d’une colonne | `df.withColumnRenamed("old", "new")` | ```python df.withColumnRenamed("age", "years").show() ``` |
| **Trier un DataFrame** | Trier en ordre croissant ou décroissant | `df.orderBy(df.col.desc())` | ```python df.orderBy(df.age.desc()).show() ``` |
| **Ajouter une colonne** | Ajouter une nouvelle colonne calculée | `df.withColumn("new_col", expression)` | ```python df.withColumn("new_age", df.age + 5).show() ``` |
| **Supprimer une colonne** | Supprimer une colonne du DataFrame | `df.drop("col_name")` | ```python df.drop("age").show() ``` |
| **Supprimer les doublons** | Supprimer les lignes dupliquées | `df.dropDuplicates()` | ```python df.dropDuplicates(["name"]).show() ``` |
| **Grouper les données** | Effectuer un groupement | `df.groupBy("col").agg(fct)` | ```python df.groupBy("department").count().show() ``` |
| **Agrégation** | Moyenne, somme, etc. | `df.agg(fct("col"))` | ```python df.agg(f.avg("salary")).show() ``` |
| **Jointure entre DataFrames** | Joindre deux DataFrames | `df1.join(df2, "col", "type")` | ```python df1.join(df2, "id", "inner").show() ``` |
| **Transformation d'une colonne** | Convertir un type | `df.withColumn("col", df.col.cast("type"))` | ```python df.withColumn("age", df.age.cast("integer")).show() ``` |
| **Remplacer les valeurs nulles** | Remplir les valeurs manquantes | `df.fillna(valeur, "col")` | ```python df.fillna(0, "salary").show() ``` |
| **Supprimer les valeurs nulles** | Supprimer les lignes avec `null` | `df.dropna()` | ```python df.dropna().show() ``` |
| **Fenêtrage** | Définir une fenêtre pour les calculs | `Window.partitionBy("col")` | ```python from pyspark.sql.window import Window windowSpec = Window.partitionBy("department").orderBy("salary") ``` |
| **Calculer un rang dans une fenêtre** | Assigner un rang à chaque ligne | `rank().over(windowSpec)` | ```python df.withColumn("rank", f.rank().over(windowSpec)).show() ``` |
| **Créer une vue temporaire** | Permet d'exécuter du SQL sur un DataFrame | `df.createOrReplaceTempView("table")` | ```python df.createOrReplaceTempView("movies") ``` |
| **Exécuter une requête SQL** | Requête SQL sur un DataFrame | `spark.sql("SELECT * FROM table")` | ```python spark.sql("SELECT name, age FROM movies WHERE age > 25").show() ``` |
| **Cacher un DataFrame** | Améliorer les performances | `df.cache()` | ```python df.cache() ``` |
| **Écrire un fichier CSV** | Exporter un DataFrame en CSV | `df.write.csv("path")` | ```python df.write.option("header", "true").csv("output.csv") ``` |

---

### **Résumé des Commandes Clés**
| **Action**                  | **Commande PySpark** |
|-----------------------------|----------------------|
| Charger un CSV              | `spark.read.option("header", "true").csv("file.csv")` |
| Afficher les premières lignes | `df.show(5)` |
| Vérifier le schéma           | `df.printSchema()` |
| Filtrer les données          | `df.filter(df.age > 30)` |
| Trier les données            | `df.orderBy(df.age.desc())` |
| Ajouter une colonne          | `df.withColumn("new_col", df.old_col + 10)` |
| Supprimer une colonne        | `df.drop("col_name")` |
| Agréger les données          | `df.groupBy("dept").agg(f.avg("salary"))` |
| Joindre deux DataFrames      | `df1.join(df2, "id", "inner")` |
| Supprimer les doublons       | `df.dropDuplicates()` |
| Créer une vue SQL            | `df.createOrReplaceTempView("table")` |
| Exécuter du SQL              | `spark.sql("SELECT * FROM table")` |
| Cacher un DataFrame          | `df.cache()` |
| Enregistrer en CSV           | `df.write.option("header", "true").csv("output.csv")` |
