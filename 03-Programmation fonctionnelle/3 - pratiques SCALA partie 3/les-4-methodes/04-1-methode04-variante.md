# Variante solution 4

### **1. Importation des bibliothèques Spark**
```scala
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.stat.Statistics

// Initialisation de SparkSession
val spark = SparkSession.builder()
  .appName("Spark SQL Evaluation")
  .config("spark.some.config.option", "some-value")
  .master("local[*]") // Exécution en local
  .getOrCreate()

import spark.implicits._
```

---

## **PARTIE 1: Activité avec Spark SQL**
### **1. Définition de la `Case Class` et parsing des données**
```scala
case class Stock(dt: String, openprice: Double, highprice: Double, lowprice: Double, closeprice: Double, volume: Double, adjcloseprice: Double)

def parseStock(str: String): Stock = {
  val line = str.split(",")
  Stock(line(0), line(1).toDouble, line(2).toDouble, line(3).toDouble, line(4).toDouble, line(5).toDouble, line(6).toDouble)
}

def parseRDD(rdd: RDD[String]): RDD[Stock] = {
  val header = rdd.first()
  rdd.filter(_ != header).map(parseStock).cache()
}

// Charger le fichier CSV et transformer en DataFrame
val stocksAAPLDF = parseRDD(spark.sparkContext.textFile("C:/Users/Dell/Desktop/Spark/AAPL.csv")).toDF().cache()
stocksAAPLDF.show()
```

---

### **2. Renommer la colonne `dt` en `Date`**
```scala
val stocksAAPLDF2 = stocksAAPLDF.withColumnRenamed("dt", "Date")
stocksAAPLDF2.show()
stocksAAPLDF2.printSchema()
```

---

### **3. Affichage des colonnes `Date`, `Open`, `Close`**
```scala
stocksAAPLDF2.select("Date", "openprice", "closeprice").show()
```

---

### **4. Ajout de la colonne `diff` (différence entre fermeture et ouverture)**
```scala
val stocksAAPLDF3 = stocksAAPLDF2.withColumn("diff", col("closeprice") - col("openprice"))
stocksAAPLDF3.select("Date", "diff").show()
```

---

### **5. Calcul des volumes maximaux et minimaux**
```scala
stocksAAPLDF2.agg(max("volume").alias("Max_Volume"), min("volume").alias("Min_Volume")).show()
```

---

### **6. Conversion de `Date` en format `DateType` et extraction de l'année**
```scala
val stocksAAPLDF4 = stocksAAPLDF2.withColumn("Date", col("Date").cast(DateType))
stocksAAPLDF4.printSchema()

val solution = stocksAAPLDF4.withColumn("year", year(to_timestamp($"Date", "yyyy/MM/dd")))
solution.show()
```

---

### **7. Calcul de la moyenne des volumes par année**
```scala
solution.groupBy("year").agg(avg("volume").alias("Avg_Volume")).show()
```

---

### **8. Calcul de la somme des volumes par mois**
```scala
val solution2 = stocksAAPLDF4.withColumn("Month", month(to_timestamp($"Date", "yyyy/MM/dd")))
solution2.groupBy("Month").agg(sum("volume").alias("Total_Volume")).show()
```

---

## **PARTIE 2: Refaire le travail de l'évaluation 01 BigData2 avec Spark au lieu de MapReduce**
### **Méthode 1: Chargement via Spark SQL**
```scala
val sqlContext = new SQLContext(spark.sparkContext)
val df = sqlContext.read
  .format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("C:/Users/dell/Desktop/SPARK/Income.csv")

df.printSchema()
df.show()

// Calcul de l'âge moyen par catégorie de revenu
df.groupBy("income").agg(avg("age").alias("Avg_Age")).show()
```

---

### **Méthode 2: Utilisation d'une `Case Class` et RDD**
```scala
case class Income(id: Double, workclass: String, education: String, maritalstatus: String, 
                  occupation: String, relationship: String, race: String, gender: String, 
                  nativecountry: String, income: String, age: Double, fnlwgt: Double, 
                  educationalnum: Double, capitalgain: Double, capitalloss: Double, 
                  hoursperweek: Double)

def parseIncome(str: String): Income = {
  val line = str.split(",")
  Income(line(0).toDouble, line(1), line(2), line(3), line(4), line(5), line(6), line(7),
         line(8), line(9), line(10).toDouble, line(11).toDouble, line(12).toDouble,
         line(13).toDouble, line(14).toDouble, line(15).toDouble)
}

def parseIncomeRDD(rdd: RDD[String]): RDD[Income] = {
  val header = rdd.first()
  rdd.filter(_ != header).map(parseIncome).cache()
}

// Charger le fichier CSV et transformer en DataFrame
val incomeDF = parseIncomeRDD(spark.sparkContext.textFile("C:/Users/CST/Income.csv")).toDF().cache()

incomeDF.printSchema()
incomeDF.show()

// Calcul de l'âge moyen par catégorie de revenu
incomeDF.groupBy("income").agg(avg("age").alias("Avg_Age")).show()
```

---

## **Résumé et Points Forts de cette Implémentation**
### **1. Utilisation des `case class` pour typer les données**
- Permet une **meilleure manipulation** et **optimisation** des requêtes Spark SQL.
- Sécurité des types grâce à la transformation **RDD → Dataset**.

### **2. Transformation des RDD en DataFrames optimisés**
- Utilisation de `RDD[Stock]` et `RDD[Income]` convertis en **DataFrame** pour des traitements Spark SQL optimisés.

### **3. Optimisation des traitements avec Spark SQL et DataFrame API**
- Utilisation des **fonctions Spark SQL** (`agg()`, `groupBy()`, `avg()`, `sum()`, `max()`, `min()`, etc.).
- Exécution **scalable et efficace** avec l'optimisation Catalyst.

### **4. Séparation des parties et meilleure modularité**
- **PARTIE 1 :** Analyse de `AAPL.csv` avec Spark SQL.
- **PARTIE 2 :** Équivalent Spark de l'évaluation BigData2 avec `Income.csv`.

---

Cette solution est **optimisée**, **lisible**, et exploite **toute la puissance de Scala et Spark** pour le traitement de données volumineuses.
