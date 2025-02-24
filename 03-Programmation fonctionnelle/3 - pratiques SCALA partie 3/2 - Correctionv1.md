# Correction Détaillée: Évaluation Formative avec Apache Spark et Spark SQL

Ce document fournit une correction détaillée de l'évaluation formative portant sur l'analyse de données à l'aide d'Apache Spark et Spark SQL. 
Nous couvrirons deux parties principales: l'analyse de données boursières (`AAPL.csv`) et l'analyse de données de revenu (`income.csv`).

## Configuration Initiale

Avant de commencer, assurez-vous d'avoir Apache Spark correctement installé et configuré sur votre machine. Vous aurez besoin de Spark 2.x ou une version ultérieure pour exécuter les exemples de code de cette évaluation.

### Importation des Librairies

La première étape consiste à importer toutes les librairies nécessaires pour notre projet Spark. Voici les imports requis pour notre session Spark :

```scala
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession
```

### Initialisation de SparkSession

Ensuite, initialisez une `SparkSession`, qui est le point d'entrée de la programmation Spark avec le Dataset et le DataFrame API.

```scala
val spark = SparkSession.builder()
  .appName("Spark SQL Evaluation")
  .config("spark.some.config.option", "some-value")
  .getOrCreate()

import spark.implicits._
```

## Partie 1: Analyse des Données Boursières

### Lecture et Préparation des Données

1. **Lecture du fichier `AAPL.csv`:** Commencez par lire le fichier contenant les données boursières.

```scala
case class Stock(dt: String, openprice: Double, highprice: Double, lowprice: Double, closeprice: Double, volume: Double, adjcloseprice: Double)

def parseStock(str: String): Stock = {
  val line = str.split(",")
  Stock(line(0), line(1).toDouble, line(2).toDouble, line(3).toDouble, line(4).toDouble, line(5).toDouble, line(6).toDouble)
}

def parseRDD(rdd: RDD[String]): RDD[Stock] = {
  val header = rdd.first() // Assumer la première ligne comme en-tête
  rdd.filter(_ != header).map(parseStock).cache()
}

val stocksRDD = parseRDD(spark.sparkContext.textFile("path/to/AAPL.csv"))
val stocksDF = stocksRDD.toDF.cache()
stocksDF.show()
```

2. **Renommage de la colonne `dt` en `Date`:** Il est souvent utile de renommer les colonnes pour une meilleure lisibilité.

```scala
val stocksAAPLDF = stocksDF.withColumnRenamed("dt", "Date")
stocksAAPLDF.show()
```

### Requêtes sur les Données

1. **Dates de transaction, ouverture et fermeture:**

```scala
stocksAAPLDF.select("Date", "openprice", "closeprice").show()
```

2. **Différence entre fermeture et ouverture:**

```scala
val withDiff = stocksAAPLDF.withColumn("diff", $"closeprice" - $"openprice")
withDiff.select("Date", "diff").show()
```

3. **Max et Min des volumes:**

```scala
stocksAAPLDF.agg(max("volume"), min("volume")).show()
```

4. **Moyenne des valeurs d’ouverture par années:**

D'abord, convertissez les dates en type Date, extrayez l'année, puis calculez la moyenne.

```scala
val stocksDateFormatted = stocksAAPLDF.withColumn("Date", to_date($"Date", "yyyy-MM-dd"))
val withYear = stocksDateFormatted.withColumn("Year", year($"Date"))
withYear.groupBy("Year").avg("openprice").show()
```

5. **Somme des volumes par mois:**

Similairement, extrayez le mois et calculez la somme.

```scala
val withMonth = stocksDateFormatted.withColumn("Month", month($"Date"))
withMonth.groupBy("Month").sum("volume").show()
```

## Partie 2: Analyse des Données de Revenu

### Lecture et Analyse des Données

1. **Lecture de `income.csv`:** Utilisez SQLContext pour charger les données de revenu dans un DataFrame.

```scala
val dfIncome = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")


  .load("path/to/income.csv")
dfIncome.show()
```

2. **Calcul de la Moyenne d'Age selon les Revenus:**

Effectuez une agrégation pour calculer la moyenne d'âge par catégorie de revenu.

```scala
dfIncome.groupBy("income").avg("age").show()
```

### Conclusion

Ce document fournit une correction exhaustive de l'évaluation formative en détail, depuis la configuration initiale de Spark jusqu'à l'exécution de requêtes spécifiques sur deux ensembles de données. Il illustre l'importance de la manipulation de DataFrame et l'utilisation des fonctions Spark SQL pour l'analyse de données.

## Références

- [Documentation officielle Apache Spark](https://spark.apache.org/docs/latest/)
- [ScalaProject sur GitHub](https://github.com/brahmbhattspandan/ScalaProject/tree/master/data/stocks)
- [Vidéo YouTube sur Market Stock Analysis](https://www.youtube.com/watch?v=Mxw6QZk1CMY)
