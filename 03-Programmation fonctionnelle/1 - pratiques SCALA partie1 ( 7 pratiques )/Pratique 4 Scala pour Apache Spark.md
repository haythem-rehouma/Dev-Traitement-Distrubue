# Pratique 4 Scala pour Apache Spark

## Table des matières

1. [Création d'un DataFrame à partir d'un fichier JSON](#partie-01---création-dun-dataframe-en-utilisant-un-objet-json)
2. [Création d'un DataFrame à partir d'un fichier CSV](#partie-02---création-dun-dataframe-en-utilisant-un-fichier-csv)
3. [Conversion d'un DataFrame en RDD](#partie-03---convertir-un-df-en-rdd)
4. [SparkSQL avec PostgreSQL](#partie-04---sparksql-avec-les-sgbdr-postgresql)
5. [Plus sur les DataFrames avec SparkSQL](#partie-05---plus-sur-les-dataframes-avec-sparksql)
6. [Introduction aux Datasets](#partie-06---les-datasets)

---

## Partie 01 - Création d'un DataFrame en utilisant un objet JSON

Exemple de fichier JSON :
```json
{"name":"Michael"}
{"name":"Andy","age":30}
{"name":"Justin","age":19}
```

Chargement et manipulation du fichier JSON avec Scala :
```scala
import org.apache.spark.sql.SQLContext
val sqlContext = new SQLContext(sc)
val dataframe = sqlContext.read.format("json").option("inferSchema","true").load("C:/people.json")
dataframe.createOrReplaceTempView("people")
dataframe.printSchema()
spark.sql("SELECT * FROM people")
spark.sql("SELECT * FROM people WHERE age IS NOT NULL").show()
```

---

## Partie 02 - Création d'un DataFrame en utilisant un fichier CSV

Chargement et manipulation du fichier CSV avec Scala :
```scala
import org.apache.spark.sql.SQLContext
val sqlContext = new SQLContext(sc)
val dataframe = sqlContext.read.format("csv").option("header","true").option("inferSchema","true").load("C:/titanic.csv")
dataframe.printSchema()
dataframe.createOrReplaceTempView("titanic")
spark.sql("SELECT name, age FROM titanic").show()
```

---

## Partie 03 - Convertir un DF en RDD

Exemple de conversion d'un DataFrame en RDD, puis manipulation de celui-ci :
```scala
import org.apache.spark.sql.SQLContext
val sqlContext = new SQLContext(sc)
val df = sqlContext.read.format("csv").option("header","true").option("inferSchema","true").load("C:/titanic.csv")
val dfToRdd = df.rdd
```

---

## Partie 04 - SparkSQL avec les SGBDR (PostgreSQL)

Configuration et utilisation de SparkSQL pour interagir avec une base de données PostgreSQL :
```scala
spark-shell --driver-class-path C:\path\to\postgresql-42.5.0.jar --jars C:\path\to\postgresql-42.5.0.jar
val jdbcDF = spark.read.format("jdbc").option("url","jdbc:postgresql://127.0.0.1/postgres").option("dbtable","public.personnes").option("user","postgres").option("password","postgres").load()
jdbcDF.printSchema()
```
## Exemple détaillé - suite de la partie 04 - SparkSQL avec les SGBDR (PostgreSQL)

Pour utiliser SparkSQL avec PostgreSQL, vous devez suivre ces étapes :

1. Téléchargez et installez PostgreSQL pour Windows à partir de [EnterpriseDB](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
2. Téléchargez le connecteur JDBC pour PostgreSQL à partir de [jdbc.postgresql.org](https://jdbc.postgresql.org/download/).

### Configuration de la base de données

Créez une table `personnes` dans PostgreSQL avec la structure suivante :

```sql
CREATE TABLE personnes (
  id INTEGER, 
  nom CHARACTER VARYING(255), 
  prenom CHARACTER VARYING(255),
  age INTEGER,
  cp INTEGER
);
```

Insérez quelques données dans la table `personnes` :

```sql
INSERT INTO personnes VALUES (1, 'Smith', 'John', 20, 31000);
INSERT INTO personnes VALUES (2, 'Johnson', 'Emma', 25, 31000);
INSERT INTO personnes VALUES (3, 'Williams', 'Olivia', 30, 31000);
INSERT INTO personnes VALUES (4, 'Brown', 'Noah', 35, 31000);
INSERT INTO personnes VALUES (5, 'Jones', 'Liam', 40, 31000);
INSERT INTO personnes VALUES (6, 'Garcia', 'Sophia', 20, 32000);
INSERT INTO personnes VALUES (7, 'Miller', 'Ava', 25, 32000);
INSERT INTO personnes VALUES (8, 'Davis', 'Isabella', 30, 32000);
INSERT INTO personnes VALUES (9, 'Rodriguez', 'Mia', 35, 32000);
INSERT INTO personnes VALUES (10, 'Martinez', 'Ethan', 40, 32000);
INSERT INTO personnes VALUES (11, 'Hernandez', 'Alexander', 22, 33000);
INSERT INTO personnes VALUES (12, 'Lopez', 'Charlotte', 27, 33000);
INSERT INTO personnes VALUES (13, 'Wilson', 'Amelia', 32, 33000);
INSERT INTO personnes VALUES (14, 'Anderson', 'Harper', 37, 33000);
INSERT INTO personnes VALUES (15, 'Thomas', 'Evelyn', 42, 33000);
INSERT INTO personnes VALUES (16, 'Taylor', 'Abigail', 47, 34000);
INSERT INTO personnes VALUES (17, 'Moore', 'Emily', 52, 34000);
INSERT INTO personnes VALUES (18, 'Jackson', 'Elizabeth', 57, 34000);
INSERT INTO personnes VALUES (19, 'Martin', 'Sofia', 62, 34000);
INSERT INTO personnes VALUES (20, 'Lee', 'Avery', 67, 34000);

```

Pour sélectionner et vérifier les données :

```sql
SELECT * FROM public.personnes;
```

### Configuration de Spark pour utiliser PostgreSQL

Lancez `spark-shell` en incluant le chemin du connecteur JDBC pour PostgreSQL :

```scala
spark-shell --driver-class-path C:\path\to\postgresql-42.5.0.jar --jars C:\path\to\postgresql-42.5.0.jar
```

### exemple
```shell
spark-shell 
 --driver-class-path C:\SPARKHADOOP\spark-3.3.0-bin-hadoop3\jars\postgresql-42.5.0.jar
 --jars C:\SPARKHADOOP\spark-3.3.0-bin-hadoop3\jars\postgresql-42.5.0.jar
```
```shell
spark-shell --driver-class-path C:\SPARKHADOOP\spark-3.3.0-bin-hadoop3\jars\postgresql-42.5.0.jar --jars C:\SPARKHADOOP\spark-3.3.0-bin-hadoop3\jars\postgresql-42.5.0.jar
```
```scala
val jdbcDF = spark.read.format("jdbc").
     | option("url","jdbc:postgresql://127.0.0.1/postgres").
     | option("dbtable","public.personnes").
     | option("user","postgres").
     | option("password","postgres").
     | load
```
Lisez les données de la table `personnes` avec SparkSQL :

```scala
val jdbcDF = spark.read
  .format("jdbc")
  .option("url", "jdbc:postgresql://127.0.0.1/postgres")
  .option("dbtable", "public.personnes")
  .option("user", "postgres")
  .option("password", "postgres")
  .load()

jdbcDF.printSchema()
jdbcDF.show()

// Exemples de manipulations DataFrame
jdbcDF.select("age", "cp").distinct().show()
```
---

## Partie 05 - Plus sur les DataFrames avec SparkSQL

Dans cette section, nous explorerons des manipulations avancées des DataFrames en utilisant SparkSQL, telles que les jointures, les agrégations, et les fonctions de fenêtrage.

### Exemple avec un fichier JSON

Supposons que vous ayez un fichier JSON `employees.json` contenant les données suivantes :

```json
{"id":1,"name":"John Doe","department":"Finance","salary":3000}
{"id":2,"name":"Jane Smith","department":"IT","salary":4000}
{"id":3,"name":"Jake Brown","department":"IT","salary":3500}
{"id":4,"name":"Claire White","department":"HR","salary":2500}
```

Chargement et manipulation du fichier JSON :

```scala
val employeesDF = spark.read.json("employees.json")

// Afficher le schéma pour comprendre la structure des données
employeesDF.printSchema()

// Sélectionner les employés du département IT avec un salaire supérieur à 3500
employeesDF.filter($"department" === "IT" && $"salary" > 3500).show()

// Calculer le salaire moyen par département
employeesDF.groupBy("department").avg("salary").show()
```

### Exemple avec un fichier CSV

Imaginons maintenant un fichier CSV `departments.csv` contenant les informations suivantes :

```csv
id,department,location
1,Finance,New York
2,IT,San Francisco
3,HR,London
```

Chargement et manipulation du fichier CSV :

```scala
val departmentsDF = spark.read.option("header", "true").csv("departments.csv")

// Jointure entre employeesDF et departmentsDF pour obtenir la localisation de chaque employé
val joinedDF = employeesDF.join(departmentsDF, "department")
joinedDF.select("name", "department", "location").show()
```

## Partie 06 - Les Datasets

Les Datasets sont une abstraction optimisée et typée sur les DataFrames qui offrent les avantages de la vérification de type au moment de la compilation et de la performance d'exécution. Ils sont particulièrement utiles lorsque vous avez besoin de manipuler des données avec des types de données complexes et personnalisés.

### Création d'un Dataset

Imaginons que vous disposiez d'une classe case `Employee` :

```scala
case class Employee(id: Long, name: String, department: String, salary: Long)
```

Vous pouvez créer un Dataset à partir d'une séquence d'objets `Employee` :

```scala
val employeesSeq = Seq(
  Employee(1, "John Doe", "Finance", 3000),
  Employee(2, "Jane Smith", "IT", 4000),
  Employee(3, "Jake Brown", "IT", 3500),
  Employee(4, "Claire White", "HR", 2500)
)

val employeesDS = employeesSeq.toDS()

// Afficher le Dataset
employeesDS.show()

// Filtrer les employés du département IT
employeesDS.filter(_.department == "IT").show()
```

Les Datasets offrent une interface fluide pour la manipulation de données typées tout en bénéficiant des optimisations Catalyst et Tungsten de Spark.
