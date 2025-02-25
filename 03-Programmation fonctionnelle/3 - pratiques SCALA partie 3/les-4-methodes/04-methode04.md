
# **Correction avec toute la puissance de Scala** en combinant **Spark SQL, Dataset optimisé et transformations fonctionnelles**
_Je vous propose ici une **implémentation complète en Scala** exploitant les **forces de Scala**, notamment en utilisant **les case classes**, les **DataFrames**, et les **transformations fonctionnelles de Spark**._

---

## **1. Configuration de Spark et Définition de la Case Class**
```scala
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

case class AAPL(Date: String, Open: Double, Close: Double, Volume: Long)

val spark = SparkSession.builder()
  .appName("AAPL_Analysis")
  .master("local[*]")
  .getOrCreate()

import spark.implicits._ // Permet d'utiliser les Dataset et les transformations fonctionnelles
```

---

## **2. Chargement des Données en Case Class**
```scala
val df: DataFrame = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .csv("AAPL.csv")
  .as[AAPL] // Convertit en Dataset[AAPL]
```
✅ Ici, **Spark infère automatiquement le schéma** et convertit les données en un `Dataset[AAPL]` pour **bénéficier de la sécurité de type** et des **transformations optimisées**.

---

## **3. Liste des dates de transaction, ouverture et fermeture**
```scala
df.select($"Date", $"Open", $"Close").show()
```
✅ **Utilise les colonnes du Dataset directement avec `$"col"`** pour afficher les valeurs d'ouverture et de fermeture.

---

## **4. Liste des dates de transaction avec la différence entre fermeture et ouverture**
```scala
df.withColumn("Diff_Close_Open", $"Close" - $"Open")
  .select($"Date", $"Diff_Close_Open")
  .show()
```
✅ **Ajoute une nouvelle colonne calculée** tout en gardant la structure de `Dataset[AAPL]`.

---

## **5. Max et Min des volumes**
```scala
df.agg(
  max($"Volume").alias("Max_Volume"),
  min($"Volume").alias("Min_Volume")
).show()
```
✅ **Utilise les fonctions `max()` et `min()` de Spark pour calculer ces valeurs sur l'ensemble du Dataset**.

---

## **6. Moyenne des valeurs d’ouverture par année (via transformation Dataset)**
```scala
df.withColumn("Year", substring($"Date", 1, 4))
  .groupBy("Year")
  .agg(avg($"Open").alias("Avg_Open"))
  .orderBy("Year")
  .show()
```
✅ **Extrait l’année et regroupe les données pour obtenir la moyenne des ouvertures**.

---

## **7. Somme des volumes par mois (via transformation Dataset)**
```scala
df.withColumn("Year_Month", substring($"Date", 1, 7))
  .groupBy("Year_Month")
  .agg(sum($"Volume").alias("Total_Volume"))
  .orderBy("Year_Month")
  .show()
```
✅ **Extrait l’année et le mois et calcule la somme des volumes échangés**.

---

## **Avantages de cette implémentation en Scala**
### **1. Sécurité de Type avec `case class AAPL`**
- La **case class** permet de **typer les données** et évite les erreurs de type.
- On travaille directement avec un `Dataset[AAPL]`, **plus sécurisé et optimisé** qu’un simple `DataFrame`.

### **2. Transformations Fonctionnelles Scala**
- `withColumn()`, `groupBy()`, `agg()`, `orderBy()` sont des **opérations immuables**, **chaînées** de manière élégante.
- **Évite les manipulations SQL brutes** et favorise les API fonctionnelles.

### **3. Optimisation avec Spark Catalyst**
- Spark optimise automatiquement les transformations grâce à son **moteur Catalyst**, améliorant ainsi les performances.

### **4. Scalabilité et Lisibilité**
- Cette approche **est plus lisible**, **plus modulaire** et **s’adapte aux grands volumes de données**.

---

Cette implémentation exploite **toute la puissance de Scala** en combinant **Spark SQL, Dataset optimisé et transformations fonctionnelles** pour une **analyse rapide et efficace** des données financières.
