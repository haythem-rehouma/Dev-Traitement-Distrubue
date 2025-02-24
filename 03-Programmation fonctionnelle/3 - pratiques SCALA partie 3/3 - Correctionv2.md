# README.md pour l'Analyse de Données avec Apache Spark SQL

Ce document README.md fournit un guide complet pour un code Scala qui utilise Apache Spark et Spark SQL pour effectuer une analyse de données sur les données du marché boursier. Le code est structuré en deux parties principales, se concentrant sur la manipulation et l'analyse des données boursières (`AAPL.csv`) et des données de revenu (`income.csv`).

## Pour Commencer

### Prérequis

- Apache Spark version 2.x ou ultérieure.
- Outil de construction Scala (SBT) ou un IDE compatible avec Scala.
- Connaissance de base de la programmation Scala et des DataFrames Spark.

### Bibliothèques et Configuration

Le code commence par importer les bibliothèques nécessaires et configurer une `SparkSession`, qui est le point d'entrée pour programmer Spark avec l'API Dataset et DataFrame.

```scala
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("Exemple de base Spark SQL")
  .config("spark.some.config.option", "valeur-quelconque")
  .getOrCreate()

import spark.implicits._
import spark.sql
```

## Partie 1 : Analyse des Données Boursières

### Classe de Cas et Analyse des Données

- Une classe de cas `Stock` est définie pour modéliser les données boursières.
- Deux fonctions, `parseStock` et `parseRDD`, sont implémentées pour analyser les données d'entrée en RDDs et DataFrames.

```scala
case class Stock(dt: String, openprice: Double, highprice: Double, lowprice: Double, closeprice: Double, volume: Double, adjcloseprice: Double)

def parseStock(str: String): Stock = {
  val ligne = str.split(",")
  Stock(ligne(0), ligne(1).toDouble, ligne(2).toDouble, ligne(3).toDouble, ligne(4).toDouble, ligne(5).toDouble, ligne(6).toDouble)
}

def parseRDD(rdd: RDD[String]): RDD[Stock] = {
  val entete = rdd.first()
  rdd.filter(_ != entete).map(parseStock).cache()
}
```

### Chargement et Transformation des Données

- Les données boursières sont chargées à partir d'un fichier CSV dans un DataFrame et mises en cache pour optimisation.
- Le renommage des colonnes et l'ajout de nouvelles colonnes sont effectués en utilisant les méthodes `withColumn` et `withColumnRenamed`.

```scala
val stocksAAPLDF = parseRDD(spark.sparkContext.textFile("C:/Users/Dell/Desktop/Spark/AApl.csv")).toDF.cache()

// Renommage et calcul de la différence
val stocksAAPLDF2 = stocksAAPLDF.withColumnRenamed("dt", "Date")
  .withColumn("diff", $"closeprice" - $"openprice")
```

### Agrégation des Données et Affichage

- Les opérations telles que le groupement, l'agrégation, et le calcul des moyennes et des sommes sont réalisées en utilisant les fonctions Spark SQL.
- Les résultats sont affichés en utilisant la méthode `show`.

```scala
// Calcul du max, min, moyenne, et somme
stocksAAPLDF2.groupBy("Date").max("volume").show()
stocksAAPLDF2.agg(max("volume"), min("volume")).show()

val stocksAAPLDF3 = stocksAAPLDF2.withColumn("Date", col("Date").cast(DateType))
val solution = stocksAAPLDF3.withColumn("année", year(to_timestamp($"Date", "yyyy/MM/dd")))
solution.groupBy("année").avg("volume").show()
```

## Partie 2 : Analyse des Données de Revenu

### Méthode 1 : Opérations Directes sur DataFrame

- Les données de revenu sont chargées en utilisant `SQLContext` et analysées pour calculer l'âge moyen regroupé par revenu.

```scala
val sqlContext = new SQLContext(spark.sparkContext)
val df = sqlContext.read.format("csv").option("header", "true").option("inferSchema", "true").load("C:/Users/dell/Desktop/SPARK/Income.csv")
df.groupBy("revenu").avg("age").show()
```

### Méthode 2 : Analyse Personn

alisée et Analyse

- Une approche similaire à la partie 1 est utilisée pour les données de revenu, avec une classe de cas personnalisée `Income` et des fonctions d'analyse de données.

```scala
case class Income(id: Double, workclass: String, education: String, maritalstatus: String, occupation: String, relationship: String, race: String, gender: String, nativecountry: String, revenu: String, age: Double, fnlwgt: Double, educationalnum: Double, capitalgain: Double, capitalloss: Double, hoursperweek: Double)

def parseIncome(str: String): Income = {
  val ligne = str.split(",")
  Income(ligne(0).toDouble, ligne(1), ligne(2), ligne(3), ligne(4), ligne(5), ligne(6), ligne(7), ligne(8), ligne(9), ligne(10).toDouble, ligne(11).toDouble, ligne(12).toDouble, ligne(13).toDouble, ligne(14).toDouble, ligne(15).toDouble)
}

val IncomeDF = parseRDD(spark.sparkContext.textFile("C:/Users/CST/Income.csv")).toDF.cache()
IncomeDF.groupBy("revenu").avg("age").show()
```

## Résumé

Ce code Scala démontre des tâches d'analyse de données de base sur les données boursières et de revenu en utilisant Apache Spark et Spark SQL. Il illustre comment charger des données, effectuer des transformations, des agrégations, et afficher les résultats de manière efficace.

```scala
import org.apache.spark._;
import org.apache.spark.rdd.RDD;
import org.apache.spark.util.IntParam;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions._;
import org.apache.spark.sql.types._;
import org.apache.spark.sql._;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.SparkSession;
val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.some.config.option", "some-value").getOrCreate()
import sqlContext.implicits._
import spark._
import sqlContext._

###################################################################################################
###   PARTIE_1: Activité avec Spark SQL  ##########################################################
###################################################################################################

#Créer Case Class
case class Stock(dt: String, openprice: Double, highprice: Double, lowprice: Double, closeprice:Double, volume: Double, adjcloseprice: Double)
def parseStock(str: String): Stock = {
 val line = str.split(",") 
 Stock(line(0), line(1).toDouble, line(2).toDouble, line(3).toDouble, line(4).toDouble,line(5).toDouble,line(6).toDouble)
}
def parseRDD(rdd: RDD[String]): RDD[Stock] = {
 val header = rdd.first
 rdd.filter(_(0) != header(0)).map(parseStock).cache()
}
val stocksAAPLDF = parseRDD(sc.textFile("C:/Users/Dell/Desktop/Spark/AApl.csv")).toDF.cache()
stocksAAPLDF.show()

#Renommer la colonne « dt » en « Date »
val stocksAAPLDF2= stocksAAPLDF.withColumnRenamed("dt","Date")
stocksAAPLDF2.show()
stocksAAPLDF2.printSchema()
stocksAAPLDF2.select("Date","openprice","closeprice").show()

#Créer la val qui calcule la difference 
val diff = stocksAAPLDF2("closeprice")-stocksAAPLDF2("openprice")

#Utiliser la fonction WithColumn pour rajouter une autre colonne 
val calcule = stocksAAPLDF2.withColumn("diff",stocksAAPLDF2("closeprice")-stocksAAPLDF2("openprice"))
 calcule.select("Date","diff").show()
 
#Calculer le maximum
 stocksAAPLDF2.groupBy("Date").max("volume").show()
 stocksAAPLDF2.agg(max("volume")).show()

#Calculer le minimum 
 stocksAAPLDF2.agg(min("volume")).show()
val  stocksAAPLDF3 = stocksAAPLDF2.withColumn("Date",col("Date")cast(DateType))
stocksAAPLDF3.printSchema()
val solution = stocksAAPLDF3.withColumn("year", year(to_timestamp($"Date", "yyyy/MM/dd")))
solution.show()

#Calculer la moyenne des volumes 
 solution.groupBy("year").avg("volume").show()
 val solution2 = stocksAAPLDF3.withColumn("Month", month(to_timestamp($"Date", "yyyy/MM/dd
 solution2.groupBy("Month").sum("volume").show()

###################################################################################################
##  PARTIE_2: Refaire le travail de l'evaluation 01 BigData2 avec SPARK à la place de MAPREDUCE ###
###################################################################################################

#Méthode 1: 
import org.apache.spark.sql.SQLContext
val sqlContext = new SQLContext (sc)
val df = sqlContext.read.format("csv").option("header","true").option("inferSchema","true").load("C:/Users/dell/Desktop/SPARK/Income.csv")
df.printSchema
df.show()
df.groupBy("income").avg("age").show()

#Méthode 2:
case class Income(id: Double, workclass: String, education: String, maritalstatus: String, occupation: String, relationship: String, race: String, gender: String, nativecountry: String, income: String, age: Double, fnlwgt: Double, educationalnum: Double, capitalgain: Double, capitalloss: Double, hoursperweek: Double)
 
def parseIncome(str: String): Income = {
 val line = str.split(",") 
 Income(line(0).toDouble, line(1).toString, line(2).toString, line(3).toString,line(4).toString, line(5).toString, line(6).toString, line(7).toString,line(8).toString,line(9).toString, line(10).toDouble, line(11).toDouble, line(12).toDouble,line(13).toDouble,line(14).toDouble, line(14).toDouble)
}

def parseRDD(rdd: RDD[String]): RDD[Income] = {
 val header = rdd.first
 rdd.filter(_(0) != header(0)).map(parseIncome).cache()
}

val Income = parseRDD(sc.textFile("C:/Users/CST/Income.csv")).toDF.cache()

Income.printSchema()
Income.show()
Income.groupBy("income").avg("age").show()
```

## Références

- Documentation Apache Spark : [Guide Spark SQL, DataFrames et Datasets](https://spark.apache.org/docs/latest/sql-programming-guide.html)
- Tutoriel YouTube sur l'analyse du marché boursier : [Lien](https://www.youtube.com/watch?v=Mxw6QZk1CMY)
```
