# *Correction avec PySpark + Dataframes - sans le sparkSQL*
Nous allons utiliser **PySpark SQL** pour analyser le fichier **AAPL.csv**.

### **1. Chargement des données**
```python
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, expr, year, month, avg, sum, min, max

# Initialiser une session Spark
spark = SparkSession.builder.appName("AAPL_Analysis").getOrCreate()

# Charger le fichier CSV (assurez-vous que le fichier est bien placé dans le bon répertoire)
df = spark.read.csv("AAPL.csv", header=True, inferSchema=True)

# Afficher les premières lignes du DataFrame
df.show(5)
```

---

## **1. Liste des dates de transaction, ouverture et fermeture**
```python
df.select(col("Date"), col("Open"), col("Close")).show()
```
**Explication :**  
- Sélectionne les colonnes **Date**, **Open** (valeur d’ouverture) et **Close** (valeur de fermeture).

---

## **2. Liste des dates de transaction avec la différence entre fermeture et ouverture**
```python
df.withColumn("Diff_Close_Open", col("Close") - col("Open")) \
  .select(col("Date"), col("Diff_Close_Open")) \
  .show()
```
**Explication :**  
- Ajoute une nouvelle colonne **Diff_Close_Open** correspondant à `Close - Open` et affiche le résultat.

---

## **3. Max et Min des volumes**
```python
df.agg(
    max("Volume").alias("Max_Volume"),
    min("Volume").alias("Min_Volume")
).show()
```
**Explication :**  
- Utilise **`max()`** et **`min()`** pour extraire les volumes maximaux et minimaux.

---

## **4. Moyenne des valeurs d’ouverture par année**
```python
df.withColumn("Year", year(col("Date"))) \
  .groupBy("Year") \
  .agg(avg("Open").alias("Avg_Open")) \
  .orderBy("Year") \
  .show()
```
**Explication :**  
- Extrait **l'année** à partir de la colonne **Date**.
- Regroupe les données par **année**.
- Calcule la **moyenne** des valeurs d’ouverture **par année**.

---

## **5. Somme des volumes par mois**
```python
df.withColumn("Year", year(col("Date"))) \
  .withColumn("Month", month(col("Date"))) \
  .groupBy("Year", "Month") \
  .agg(sum("Volume").alias("Total_Volume")) \
  .orderBy("Year", "Month") \
  .show()
```
**Explication :**  
- Extrait **l’année et le mois** depuis la colonne **Date**.
- Regroupe les données **par année et par mois**.
- Calcule la **somme des volumes échangés** pour chaque mois.

---

## **Conclusion**
Cette correction permet de :
- Charger les données **AAPL.csv** avec **PySpark**.
- Appliquer des **requêtes Spark SQL** pour extraire les informations demandées.
- Afficher les **résultats formatés**.

Assurez-vous que le fichier `AAPL.csv` contient bien les colonnes suivantes :
   - `Date` (format `yyyy-MM-dd`)
   - `Open` (valeur d’ouverture)
   - `Close` (valeur de fermeture)
   - `Volume` (volume des échanges)

Cette approche garantit une exécution efficace des requêtes SQL sur des volumes de données importants avec **PySpark**.
