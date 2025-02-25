# **📌 Exercice : Analyse et Transformation de Données Boursières avec Spark**
## 🏆 **Objectif**
- Charger un fichier CSV contenant des données boursières.
- Transformer les données en **RDD** puis en **DataFrame**.
- Effectuer des calculs de **moyenne mobile** et **analyse statistique**.
- Effectuer une **jointure entre plusieurs fichiers** (ex : comparer plusieurs actions).
- Sauvegarder les résultats en **Parquet**.

---

## **💡 Partie 1 : Chargement et Exploration des Données**
### **1️⃣ Charger un fichier CSV en RDD**
```scala
// Initialisation de la session Spark
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder()
  .appName("Stock Analysis")
  .master("local[*]") // Mode local multi-thread
  .getOrCreate()

// Création du SparkContext
val sc = spark.sparkContext

// Charger un fichier CSV
val filePath = "file:///C:/Users/Etudiant/Downloads/AAPL.csv"
val rawRDD = sc.textFile(filePath)

// Afficher quelques lignes du fichier
rawRDD.take(5).foreach(println)
```

### **2️⃣ Convertir le fichier en RDD d'objets Scala**
```scala
case class Stock(
  dt: String,
  open: Double,
  high: Double,
  low: Double,
  close: Double,
  volume: Double,
  adjClose: Double
)

// Fonction de parsing du CSV en objet Stock
def parseStock(line: String): Option[Stock] = {
  val arr = line.split(",")
  try {
    Some(Stock(
      arr(0), arr(1).toDouble, arr(2).toDouble, arr(3).toDouble,
      arr(4).toDouble, arr(5).toDouble, arr(6).toDouble
    ))
  } catch {
    case _: Exception => None // Gestion des erreurs
  }
}

// Transformer l’ensemble des lignes en objets Stock
val header = rawRDD.first() // Supprimer l'entête
val stockRDD = rawRDD.filter(_ != header).flatMap(parseStock)
stockRDD.take(5).foreach(println)
```

---

## **💡 Partie 2 : Manipulation et Transformation des RDD**
### **3️⃣ Transformer un RDD en DataFrame**
```scala
import spark.implicits._
val stockDF = stockRDD.toDF()
stockDF.show(5)
stockDF.printSchema()
```

### **4️⃣ Appliquer une moyenne mobile sur 5 jours**
```scala
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

// Définition de la fenêtre glissante
val windowSpec = Window.orderBy("dt").rowsBetween(-4, 0)

// Ajouter une colonne avec la moyenne mobile des 5 derniers jours
val stockWithMovingAvgDF = stockDF
  .withColumn("moving_avg_5", avg(col("close")).over(windowSpec))

stockWithMovingAvgDF.show(10)
```

---

## **💡 Partie 3 : Agrégations et Analyses**
### **5️⃣ Calculer les statistiques globales**
```scala
stockDF.describe("open", "high", "low", "close", "volume").show()
```

### **6️⃣ Déterminer la volatilité d'une action**
```scala
val stockVolatilityDF = stockDF
  .withColumn("daily_return", (col("close") - col("open")) / col("open") * 100)

stockVolatilityDF.select("dt", "daily_return").show(10)
```

---

## **💡 Partie 4 : Jointure et Analyse Multi-Actions**
### **7️⃣ Comparer plusieurs actions**
```scala
val filePath2 = "file:///C:/Users/Etudiant/Downloads/MSFT.csv"
val rawRDD2 = sc.textFile(filePath2)
val stockRDD2 = rawRDD2.filter(_ != header).flatMap(parseStock)
val stockDF2 = stockRDD2.toDF()

// Ajouter un identifiant pour différencier les actions
val stockDF_AAPL = stockDF.withColumn("symbol", lit("AAPL"))
val stockDF_MSFT = stockDF2.withColumn("symbol", lit("MSFT"))

// Fusionner les deux DataFrames
val mergedDF = stockDF_AAPL.union(stockDF_MSFT)
mergedDF.show(10)
```

---

## **💡 Partie 5 : Sauvegarde et Optimisation**
### **8️⃣ Sauvegarder en format Parquet**
```scala
val outputPath = "file:///C:/Users/Etudiant/Downloads/stock_results"
stockWithMovingAvgDF.write.mode("overwrite").parquet(outputPath)
```

---

## **📌 Challenge Supplémentaire**
1. **Calculer la variation hebdomadaire (%)** du cours de clôture d'une action.
2. **Filtrer les jours où la volatilité dépasse 5%.**
3. **Créer un histogramme des volumes de transactions** par intervalle.
4. **Utiliser Spark SQL** pour interroger directement le DataFrame.

