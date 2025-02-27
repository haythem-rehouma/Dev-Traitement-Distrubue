 # Exemples Spark en Scala

### **1️⃣ Chargement d'un CSV en DataFrame**
```scala
import org.apache.spark.sql.{SparkSession, DataFrame}

val spark = SparkSession.builder().appName("Load CSV").master("local[*]").getOrCreate()
val df: DataFrame = spark.read.option("header", "true").csv("file:///C:/Users/rehou/Downloads/AAPL.csv")
df.show()
```

---

### **2️⃣ Ajouter une colonne calculée (TVA 20%)**
```scala
import org.apache.spark.sql.functions._

val dfAug = df.withColumn("prix_ttc", col("closeprice") * 1.20)
dfAug.show()
```

---

### **3️⃣ Filtrage des données**
```scala
val dfFiltered = df.filter(col("closeprice") > 100)
dfFiltered.show()
```

---

### **4️⃣ Agrégation (Moyenne des prix)**
```scala
df.groupBy("dt").agg(avg("closeprice").as("moyenne_close")).show()
```

---

### **5️⃣ Pivot Table**
```scala
df.groupBy("dt").pivot("openprice").agg(sum("closeprice")).show()
```

---

### **6️⃣ Jointure entre deux DataFrames**
```scala
val df1 = df.select("dt", "closeprice")
val df2 = df.select("dt", "volume")
val joinedDF = df1.join(df2, Seq("dt"))
joinedDF.show()
```

---

### **7️⃣ Tri des données**
```scala
df.orderBy(desc("closeprice")).show()
```

---

### **8️⃣ UDF (User-Defined Function)**
```scala
import org.apache.spark.sql.functions.udf

val categorize = udf((price: Double) => if (price > 100) "High" else "Low")
val dfUDF = df.withColumn("category", categorize(col("closeprice")))
dfUDF.show()
```

---

### **9️⃣ Création d’une Table Temporaire & Requête SQL**
```scala
df.createOrReplaceTempView("stocks")
val result = spark.sql("SELECT dt, closeprice FROM stocks WHERE closeprice > 100")
result.show()
```

---

### **🔟 Streaming (Lecture en direct d’un Socket)**
```scala
val streamingDF = spark.readStream.format("socket").option("host", "localhost").option("port", 9999).load()
val wordCounts = streamingDF.groupBy("value").count()
wordCounts.writeStream.outputMode("complete").format("console").start().awaitTermination()
```

---

### **1️⃣1️⃣ Écriture en JSON**
```scala
df.write.mode("overwrite").json("file:///C:/Users/rehou/Downloads/output_json")
```

---

### **1️⃣2️⃣ Écriture en Parquet**
```scala
df.write.mode("overwrite").parquet("file:///C:/Users/rehou/Downloads/output_parquet")
```

---

### **1️⃣3️⃣ Écriture en CSV**
```scala
df.write.mode("overwrite").csv("file:///C:/Users/rehou/Downloads/output_csv")
```

---

### **1️⃣4️⃣ Calcul d’une Moyenne Mobile**
```scala
import org.apache.spark.sql.expressions.Window
val windowSpec = Window.orderBy("dt").rowsBetween(-4, 0)
val dfMovingAvg = df.withColumn("moving_avg", avg("closeprice").over(windowSpec))
dfMovingAvg.show()
```

---

### **1️⃣5️⃣ Accumulateurs (Compter les éléments filtrés)**
```scala
val acc = spark.sparkContext.longAccumulator("countFiltered")
val filteredRDD = df.rdd.filter(row => { if (row.getAs[Double]("closeprice") < 100) { acc.add(1); false } else true })
println(s"Nombre d'éléments filtrés: ${acc.value}")
```

---

### **1️⃣6️⃣ Variables Broadcast**
```scala
val broadcastVar = spark.sparkContext.broadcast(Map("AAPL" -> "Apple", "GOOGL" -> "Google"))
val dfWithCompany = df.withColumn("company_name", lit(broadcastVar.value.getOrElse("AAPL", "Unknown")))
dfWithCompany.show()
```

---

### **1️⃣7️⃣ Partitionner les données**
```scala
val dfPartitioned = df.repartition(5)
println(s"Nombre de partitions: ${dfPartitioned.rdd.getNumPartitions}")
```

---

### **1️⃣8️⃣ Utilisation de `mapPartitions`**
```scala
val rdd = spark.sparkContext.parallelize(1 to 100, 4)
val processedRdd = rdd.mapPartitions(iter => Iterator(iter.sum))
processedRdd.collect().foreach(println)
```

---

### **1️⃣9️⃣ Utilisation de `mapPartitionsWithIndex`**
```scala
val indexedRDD = df.rdd.mapPartitionsWithIndex((index, iter) => iter.map(row => s"Partition: $index -> $row"))
indexedRDD.collect().foreach(println)
```

---

### **2️⃣0️⃣ Checkpointing**
```scala
spark.sparkContext.setCheckpointDir("file:///C:/Users/rehou/Downloads/checkpoints")
val rdd = spark.sparkContext.parallelize(1 to 100000).map(_ * 2)
rdd.checkpoint()
rdd.count()
```

---
# Annexes  idées de projets
---



### 1️⃣ **Projet 1 : Analyse des stocks boursiers**
- **Objectif** : Charger un fichier CSV contenant des données boursières, le convertir en `RDD`, puis en `DataFrame` et calculer des indicateurs financiers.
- **Tâches** :
  1. Lire et parser un fichier CSV (exclure l'en-tête).
  2. Transformer les données en `RDD[Stock]` puis en `DataFrame`.
  3. Calculer la **moyenne mobile** sur 5 jours.
  4. Partitionner et sauvegarder les résultats en **Parquet**.
  5. Générer des statistiques : prix moyen, volume moyen.

### 2️⃣ **Projet 2 : Analyse des logs web**
- **Objectif** : Traiter un fichier de logs Apache (`access.log`), analyser le trafic et détecter les IPs les plus actives.
- **Tâches** :
  1. Charger les logs depuis un fichier.
  2. Extraire les champs utiles : IP, URL, code HTTP.
  3. Déterminer les **top 10 IPs** en termes de requêtes.
  4. Détecter les erreurs 404 et 500.
  5. Sauvegarder les résultats sous format **Parquet**.

### 3️⃣ **Projet 3 : Recommandation de films avec Spark MLlib**
- **Objectif** : Utiliser un dataset de films (`ratings.csv`) et appliquer un **filtrage collaboratif** pour la recommandation.
- **Tâches** :
  1. Charger et nettoyer les données (`userId, movieId, rating`).
  2. Utiliser l’algorithme **ALS (Alternating Least Squares)** pour recommander des films.
  3. Évaluer le modèle en calculant **RMSE**.
  4. Sauvegarder les résultats en **Parquet**.

### 4️⃣ **Projet 4 : Détection d’anomalies sur des transactions**
- **Objectif** : Identifier des transactions suspectes dans un dataset bancaire.
- **Tâches** :
  1. Charger un fichier **transactions.csv** (`userId, amount, timestamp`).
  2. Déterminer les transactions dont le montant dépasse de **3 écarts-types** la moyenne de l’utilisateur.
  3. Utiliser une **fenêtre temporelle** pour suivre l’évolution des dépenses.
  4. Sauvegarder les résultats sous **format Parquet**.

### 📌 **Matériel et outils**
- **Scala + Apache Spark** (`RDD`, `DataFrame`, `Window functions`, `MLlib`)
- **Formats de sortie** : CSV, JSON, Parquet
- **IDE recommandés** : IntelliJ, VS Code avec Metals

💡 **Bonus** : Ajoute une partie **optimisation** où ils doivent expérimenter avec `cache()`, `persist()`, et la gestion des partitions.

