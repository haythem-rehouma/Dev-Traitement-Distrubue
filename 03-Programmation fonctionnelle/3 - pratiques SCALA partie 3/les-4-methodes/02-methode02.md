## **Correction avec Spark SQL, pas de SCALA, pas de DF, pas de DS**

Nous allons utiliser **Spark SQL** pour effectuer les requêtes demandées sur le fichier **AAPL.csv**.

### **1. Chargement des données et création d'une table SQL**
```python
from pyspark.sql import SparkSession

# Initialiser une session Spark
spark = SparkSession.builder.appName("AAPL_Analysis").getOrCreate()

# Charger le fichier CSV (assurez-vous que le fichier est bien placé dans le bon répertoire)
df = spark.read.csv("AAPL.csv", header=True, inferSchema=True)

# Enregistrer le DataFrame comme une table temporaire pour utiliser SQL
df.createOrReplaceTempView("AAPL")
```

---

## **1. Liste des dates de transaction, ouverture et fermeture**
```python
spark.sql("""
    SELECT Date, Open, Close
    FROM AAPL
""").show()
```
Cette requête sélectionne les colonnes **Date**, **Open** et **Close** pour afficher les valeurs d’ouverture et de fermeture des transactions.

---

## **2. Liste des dates de transaction avec la différence entre fermeture et ouverture**
```python
spark.sql("""
    SELECT Date, (Close - Open) AS Diff_Close_Open
    FROM AAPL
""").show()
```
Cette requête affiche les **dates de transaction** ainsi que la **différence entre la valeur de fermeture et d’ouverture**.

---

## **3. Max et Min des volumes**
```python
spark.sql("""
    SELECT MAX(Volume) AS Max_Volume, MIN(Volume) AS Min_Volume
    FROM AAPL
""").show()
```
Cette requête extrait le **volume maximal et minimal** des transactions.

---

## **4. Moyenne des valeurs d’ouverture par année**
```python
spark.sql("""
    SELECT SUBSTRING(Date, 1, 4) AS Year, AVG(Open) AS Avg_Open
    FROM AAPL
    GROUP BY Year
    ORDER BY Year
""").show()
```
Cette requête :
- Extrait l’**année** en utilisant `SUBSTRING(Date, 1, 4)`.
- Regroupe par **année**.
- Calcule la **moyenne des valeurs d’ouverture**.
- Trie les résultats par année.

---

## **5. Somme des volumes par mois**
```python
spark.sql("""
    SELECT SUBSTRING(Date, 1, 7) AS Year_Month, SUM(Volume) AS Total_Volume
    FROM AAPL
    GROUP BY Year_Month
    ORDER BY Year_Month
""").show()
```
Cette requête :
- Extrait le **mois et l’année** au format `YYYY-MM`.
- Regroupe par **mois**.
- Calcule la **somme des volumes échangés**.
- Trie les résultats.

---

## **Conclusion**
Avec ces requêtes **Spark SQL**, on peut facilement :
- Charger et analyser les données **sans transformer explicitement les types**.
- Manipuler et agréger les données avec **des requêtes SQL classiques**.
- Exploiter **les performances de Spark** pour traiter de grands volumes de données efficacement.

Assurez-vous que votre fichier **AAPL.csv** contient les bonnes colonnes :  
- `Date` (format `YYYY-MM-DD`)
- `Open`
- `Close`
- `Volume`

Ces requêtes permettent une analyse rapide et efficace des transactions boursières avec **Spark SQL**.
