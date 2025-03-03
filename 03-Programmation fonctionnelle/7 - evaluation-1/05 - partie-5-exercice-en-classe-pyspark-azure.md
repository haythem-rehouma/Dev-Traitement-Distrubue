# **Tableau des Exécutions et Explications**

Le tableau ci-dessous présente **40 références** à du code. Chacune est associée à une **brève description** et une **explication générale**. Les extraits de code se trouvent dans la **section Annexe** située après le tableau.  

| **Référence** | **Brève Description**                                | **Explication Générale**                                                                                                                  |
|:------------:|:-----------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------|
| **Code #1**  | `%run ./Authorization.py`                            | Exécute un script externe nommé `Authorization.py`. Généralement utilisé pour configurer ou autoriser l’accès aux ressources (Azure, etc.).|
| **Code #2**  | Chargement du fichier `links.csv`                    | Définit l’emplacement du fichier `links.csv`, le type (CSV), les options (schéma, en-tête, séparateur) et crée le DataFrame `df_movies`.  |
| **Code #3**  | Import de bibliothèques Python                       | Importe `datetime`, `pyspark.sql.functions` (alias `f`), `pyspark.sql.types` et `pandas` pour la manipulation des données.                |
| **Code #4**  | Import de fonctions Spark supplémentaires            | Importe des fonctions spécialisées (`year`, `month`, `dayofmonth`, `unix_timestamp`, `from_unixtime`) et la classe `Window` pour les fenêtrages. |
| **Code #5**  | Chargement du fichier `movies.csv`                   | Charge `movies.csv` dans un DataFrame Spark, en détectant le schéma et en utilisant le séparateur virgule, puis l’affiche.               |
| **Code #6**  | Création d’une vue temporaire `movies_csv`           | Transforme le DataFrame `df_movies` en vue temporaire SQL pour pouvoir utiliser `SELECT`, `JOIN`, etc. via Spark SQL.                     |
| **Code #7**  | Requête SQL sur la vue temporaire `movies_csv`       | Affiche toutes les colonnes de la table temporaire `movies_csv` en utilisant la magie `%sql` (Databricks).                                 |
| **Code #8**  | Définition d’un nom de table permanent               | Prépare la variable `permanent_table_name` avec la valeur `"movies_csv"`. Peut être utilisé pour un futur enregistrement persistant.      |
| **Code #9**  | Chargement du fichier `links.csv` dans `df_links`    | Charge le fichier `links.csv` (avec schéma, en-tête, virgule) dans un nouveau DataFrame `df_links`. L’instruction `display` permet de visualiser. |
| **Code #10** | Chargement du fichier `tags.csv` dans `df_tags`      | Charge le fichier `tags.csv` (mêmes paramètres que ci-dessus) dans `df_tags` et l’affiche.                                                |
| **Code #11** | Chargement du fichier `ratings.csv` dans `df_ratings`| Idem, charge `ratings.csv` en tenant compte du schéma, de l’en-tête et du séparateur.                                                     |
| **Code #12** | Comptage des enregistrements de `df_movies`          | Affiche `df_movies.count()` pour connaître la taille du DataFrame, puis `display(df_movies)` pour un aperçu des données.                  |
| **Code #13** | Jointure `df_movies` avec `df_ratings`               | Associe le DataFrame `df_movies` au DataFrame `df_ratings` sur la colonne `movieID`, en mode `LEFT JOIN`. Le résultat est `df_movies_with_ratings`. |
| **Code #14** | Comptage par `movieId`                               | Regroupe (`groupBy`) sur `movieId` et applique la fonction `count()`. Sert à repérer des doublons ou la distribution des lignes par film. |
| **Code #15** | Jointure de `df_movies_with_ratings` avec `df_tags`  | Nouvelle jointure (INNER) sur `movieId` pour associer les tags. Le DataFrame résultant inclut désormais films, notes et tags.             |
| **Code #16** | Jointure de `df_ratings` avec `df_tags`              | Crée `df_ratings_tags` en associant (INNER) les notations et les tags. Affiche aussi `df_movies_with_ratings` pour vérification.          |
| **Code #17** | Affichage du DataFrame `df_ratings`                  | Montre le contenu du DataFrame `df_ratings` contenant les notes, les ID d’utilisateurs, etc.                                              |
| **Code #18** | Simple appel à `df_ratings`                          | Permet de récupérer l’objet Spark DataFrame ; dans Databricks, cela ne produit généralement pas d’affichage de table.                      |
| **Code #19** | Conversion du timestamp en format date lisible       | Ajoute la colonne `tsDate` via `df_ratings.withColumn("tsDate", f.from_unixtime("timestamp"))`. Transforme le champ `timestamp` brut.     |
| **Code #20** | Affichage post-conversion                            | `display(df_ratings)` pour vérifier la réussite de la transformation (voir la nouvelle colonne `tsDate`).                                 |
| **Code #21** | Sélection et conversion en `rating_date`             | Ne conserve que `userId`, `movieId`, `rating`, et crée une colonne `rating_date` (conversion du `tsDate` en format `yyyy-MM-dd`).         |
| **Code #22** | Configuration Spark et affichage                     | Modifie `spark.sql.legacy.timeParserPolicy` en `"LEGACY"` pour la prise en charge d’anciens formats de dates, puis affiche `df_ratings`.  |
| **Code #23** | Calcul de la moyenne des notes par `movieId`         | `df_ratings.groupBy('movieId').agg(mean_('rating'))` : regroupe par film et calcule la moyenne (aliasé par `mean_`).                       |
| **Code #24** | Jointure avec `df_movies` et renommage de colonne    | Associe la moyenne des notes (`df_avg_ratings`) avec les infos de films (`df_movies`), puis renomme `avg(rating)` en `avg_rating`.        |
| **Code #25** | Comptage des notations par `movieID`                 | `df_ratings.groupBy('movieID').count()` : compte le nombre de notes par film.                                                            |
| **Code #26** | Filtrage des films avec plus de 5 notations          | Retire ceux qui ont `count() <= 5` et joint `df_ratings` pour obtenir `df_ratings_filtered`, ne contenant plus que les films populaires.  |
| **Code #27** | Affichage et comptage post-filtrage                  | Montre le nouveau DataFrame `df_total_rating` et affiche son `.count()` afin de connaître le nombre de films répondant au critère.        |
| **Code #28** | Calcul de la note max par `(userID, movieID)`        | 1) Sélectionne `(userID, movieID, rating)` et regroupe par utilisateur et film pour trouver la note maximale.<br>2) Joint avec `df_movies` pour enrichir l’information. |
| **Code #29** | Renommage en `max_rating` et affichage               | Renomme `max(rating)` en `max_rating` (si nécessaire) et affiche `df_rating_per_user_movie`.                                              |
| **Code #30** | Simple référence à `df_rating_per_user_movie`        | Dans Databricks, cela ne produit pas forcément d’affichage, mais on conserve la variable pour traitement ultérieur.                       |
| **Code #31** | Agrégation supplémentaire pour `df_rating`           | `groupBy('userId','movieId','title','genres').max('max_rating')` : regroupe par ces colonnes et calcule la valeur max.                    |
| **Code #32** | Affichage de `df_rating`                             | Montre le DataFrame `df_rating` issu de l’agrégation précédente.                                                                          |
| **Code #33** | Filtrage pour notes ≥ 4                              | Renomme `max(max_rating)` en `max_rating`, puis `filter(df_rating['max_rating'] >= 4)`, pour ne garder que les films jugés bons ou très bons. |
| **Code #34** | Comptage par `(genres, title)`                       | `df_rating.groupBy('genres','title').count()`. Indique combien d’utilisateurs (ou combien de fois) un film figure dans chaque genre avec une bonne note. |
| **Code #35** | Analyse du genre par utilisateur                     | Sélectionne `(userId, title, genres)` et regroupe par `(userId, genres)` pour compter combien de films par genre un utilisateur a notés.  |
| **Code #36** | Affichage de `df_rating_genre`                       | Permet de visualiser la répartition des notes par genre pour chaque utilisateur.                                                          |
| **Code #37** | Création de `df_recent_movie`                        | `df_ratings.groupBy('userId','movieId').agg(f.max(df_ratings['rating_date']))` pour connaître la date la plus récente de notation par utilisateur et film. |
| **Code #38** | Affichage de `df_recent_movie`                       | Montre le résultat de cette agrégation, utile pour déterminer quels films ont été notés récemment.                                        |
| **Code #39** | Commentaire sur les films tendance, usage de `df_rating` | Le commentaire #Latest Trending movies (Overall) indique qu’on pourrait identifier les tendances, mais aucune opération n’est lancée.     |
| **Code #40** | Moyenne des moyennes par genre                       | `df.groupBy('genres').avg('avg_rating')` : dans `df` (où on a `avg_rating`), on calcule à nouveau la **moyenne de la moyenne** par `genres`. |

---

# **Annexe : Code Complet**

Ci-dessous se trouvent, dans l’ordre, les 40 extraits de code référencés dans le tableau ci-dessus. Chaque extrait est préfixé par son identifiant (**Code #X**).

---

### **Code #1**  
```python
# DBTITLE 1,Cmd1
# MAGIC %run ./Authorization.py
```

### **Code #2**  
```python
# File location and type
file_location = "abfss://containeurmovieal3@comptedestockagemovieal3.dfs.core.windows.net/links.csv"
file_type = "csv"
# CSV options
infer_schema = "true"
first_row_is_header = "true"
delimiter = ","
# The applied options are for CSV files. For other file types, these will be ignored.
df_movies = spark.read.format(file_type) \
      .option("inferSchema", infer_schema) \
      .option("header", first_row_is_header) \
      .option("sep", delimiter) \
      .load(file_location)
    
display(df_movies)
```

### **Code #3**  
```python
# DBTITLE 1,Cmd3
import datetime
import pyspark.sql.functions as f
import pyspark.sql.types
import pandas as pd
```

### **Code #4**  
```python
# DBTITLE 1,Cmd4
from pyspark.sql.functions import year, month, dayofmonth
from pyspark.sql.functions import unix_timestamp, from_unixtime
from pyspark.sql import Window
from pyspark.sql.functions import rank, min
```

### **Code #5**  
```python
# DBTITLE 1,Cmd5
file_location = "abfss://containeurmovieal3@comptedestockagemovieal3.dfs.core.windows.net/movies.csv"
file_type = "csv"
infer_schema = "true"
first_row_is_header = "true"
delimiter = ","

df_movies = spark.read.format(file_type) \
      .option("inferSchema", infer_schema) \
      .option("header", first_row_is_header) \
      .option("sep", delimiter) \
      .load(file_location)
    
display(df_movies)
```

### **Code #6**  
```python
# DBTITLE 1,Cmd6
temp_table_name = "movies_csv"
df_movies.createOrReplaceTempView(temp_table_name)
```

### **Code #7**  
```sql
-- DBTITLE 1,Cmd7
%sql
select * from `movies_csv`
```

### **Code #8**  
```python
# DBTITLE 1,Cmd8
permanent_table_name = "movies_csv"
```

### **Code #9**  
```python
# DBTITLE 1,Cmd9
links="abfss://containeurmovieal3@comptedestockagemovieal3.dfs.core.windows.net/links.csv"
df_links = spark.read.format(file_type) \
    .option("inferShema", infer_schema) \
    .option("header", first_row_is_header) \
    .option("sep", delimiter) \
    .load(links)

display(df_links)
```

### **Code #10**  
```python
# DBTITLE 1,Cmd10
tags = "abfss://containeurmovieal3@comptedestockagemovieal3.dfs.core.windows.net/tags.csv"
df_tags = spark.read.format(file_type) \
    .option("inferShema", infer_schema) \
    .option("header", first_row_is_header) \
    .option("sep", delimiter) \
    .load(tags)

display(df_tags)
```

### **Code #11**  
```python
# DBTITLE 1,Cmd11
ratings = "abfss://containeurmovieal3@comptedestockagemovieal3.dfs.core.windows.net/ratings.csv"
df_ratings = spark.read.format(file_type) \
    .option("inferShema", infer_schema) \
    .option("header", first_row_is_header) \
    .option("sep", delimiter) \
    .load(ratings)

display(df_ratings)
```

### **Code #12**  
```python
# DBTITLE 1,Cmd12
#count of records
print(df_movies.count())
display(df_movies)
```

### **Code #13**  
```python
# DBTITLE 1,Cmd13
df_movies_with_ratings = df_movies.join(df_ratings,'movieID','left')
display(df_movies_with_ratings)
```

### **Code #14**  
```python
# DBTITLE 1,Cmd14
df_movies_no_dups = df_movies_with_ratings.groupby('movieId').count()
display(df_movies_no_dups)
```

### **Code #15**  
```python
# DBTITLE 1,Cmd15
df_movies_with_ratings = df_movies_with_ratings.join(df_tags,['movieId'], 'inner')
display(df_movies_with_ratings)
```

### **Code #16**  
```python
# DBTITLE 1,Cmd16
df_ratings_tags = df_ratings.join(df_tags,['movieId'], 'inner')
display(df_movies_with_ratings)
```

### **Code #17**  
```python
# DBTITLE 1,Cmd17
display(df_ratings)
```

### **Code #18**  
```python
# DBTITLE 1,Cmd18
df_ratings
```

### **Code #19**  
```python
# DBTITLE 1,Cmd19
df_ratings = df_ratings.withColumn("tsDate", f.from_unixtime("timestamp"))
```

### **Code #20**  
```python
# DBTITLE 1,Cmd20
display(df_ratings)
```

### **Code #21**  
```python
# DBTITLE 1,Cmd21
df_ratings = df_ratings.select(
    'userId',
    'movieId',
    'rating',
    f.to_date(
        unix_timestamp('tsDate', 'yyyy-MM-dd HH:MM:SS').cast('timestamp')
    ).alias('rating_date')
)
```

### **Code #22**  
```python
# DBTITLE 1,Cmd22
spark.conf.set("spark.sql.legacy.timeParserPolicy", "LEGACY")
display(df_ratings)
```

### **Code #23**  
```python
# DBTITLE 1,Cmd23
from pyspark.sql.functions import mean as mean_
df_avg_ratings = df_ratings.groupBy('movieId').agg(mean_('rating'))
display(df_avg_ratings)
```

### **Code #24**  
```python
# DBTITLE 1,Cmd24
df = df_avg_ratings.join(df_movies,'movieId','inner')
df = df.withColumnRenamed('avg(rating)','avg_rating')
display(df)
```

### **Code #25**  
```python
# DBTITLE 1,Cmd25
df_total_rating = df_ratings.groupBy('movieID').count()
display(df_total_rating)
```

### **Code #26**  
```python
# DBTITLE 1,Cmd26
df_total_rating = df_total_rating.filter(df_total_rating['count'] > 5)
df_ratings_filtered = df_ratings.join(df_total_rating,'movieID','inner')
```

### **Code #27**  
```python
# DBTITLE 1,Cmd27
display(df_total_rating)
print(df_total_rating.count())
```

### **Code #28**  
```python
# DBTITLE 1,Cmd28
from pyspark.sql.functions import max as max_, col

df_rating_per_user = (
    df_ratings_filtered
    .select('userID','movieID','rating')
    .groupBy('userID','movieID')
    .agg(max_('rating').cast("float").alias("max_rating"))
)

df_rating_per_user_movie = df_rating_per_user.join(df_movies, 'movieID', 'inner')
```

### **Code #29**  
```python
# DBTITLE 1,Cmd29
df_rating_per_user_movie = df_rating_per_user_movie.withColumnRenamed('max(rating)','max_rating')
display(df_rating_per_user_movie)
```

### **Code #30**  
```python
# DBTITLE 1,Cmd30
df_rating_per_user_movie
```

### **Code #31**  
```python
df_rating = df_rating_per_user_movie.groupBy('userId','movieId','title','genres').max('max_rating')
```

### **Code #32**  
```python
display(df_rating)
```

### **Code #33**  
```python
# Users wit movies with > 4 ratings
df_rating = df_rating.withColumnRenamed('max(max_rating)','max_rating')
df_rating = df_rating.filter(df_rating['max_rating'] >= 4)
display(df_rating)
```

### **Code #34**  
```python
# Identify best movies per genre
df_rating_per_genre = df_rating.groupBy('genres','title').count()
display(df_rating_per_genre)
```

### **Code #35**  
```python
# Identify genre of user
df_rating_genre = df_rating.select('userId','title','genres').groupBy('userId','genres').count()
```

### **Code #36**  
```python
display(df_rating_genre)
```

### **Code #37**  
```python
df_recent_movie = df_ratings.groupBy('userId','movieId').agg(f.max(df_ratings['rating_date']))
```

### **Code #38**  
```python
display(df_recent_movie)
```

### **Code #39**  
```python
#Latest Trending movies (Overall)
df_rating
```

### **Code #40**  
```python
df_ratings_per_genre = df.groupBy('genres').avg('avg_rating')
display(df_ratings_per_genre)
```

---

**Fin du document.**
