# **Correction de l’Examen Mi-Session – Partie 3**  
### **Analyse des Données Boursières avec Spark SQL et Scala**  

---

## **Initialisation de l’Environnement Spark**  

Avant de commencer l’analyse, nous devons **initialiser Spark** et charger les données à partir du fichier CSV.  

### **Configuration de SparkSession et Chargement des Données**  

```python
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, year, month, avg, sum, max, min, expr, round, window

# Initialiser SparkSession
spark = SparkSession.builder \
    .appName("AMRB Analysis") \
    .getOrCreate()

# Charger le fichier CSV dans un DataFrame
amrb_data = spark.read \
    .option("header", "true") \
    .option("inferSchema", "true") \
    .csv("AMRB.csv")

# Créer une vue temporaire pour utiliser Spark SQL
amrb_data.createOrReplaceTempView("amrb")
```

---

## **1. Transactions Quotidiennes (Facile – 5 points)**  
**Objectif :** Afficher la **date**, le **prix d’ouverture**, et le **prix de clôture** pour chaque jour.  

### **Solution Spark SQL**
```python
query1 = """
  SELECT Date, Open, Close 
  FROM amrb
"""
result1 = spark.sql(query1)
result1.show()
```

---

## **2. Volatilité Journalière (Facile – 5 points)**  
**Objectif :** Calculer la **différence entre le prix de clôture et d’ouverture** pour chaque jour.  

### **Solution Spark SQL**
```python
query2 = """
  SELECT Date, (Close - Open) AS Volatility 
  FROM amrb
"""
result2 = spark.sql(query2)
result2.show()
```

---

## **3. Extrêmes de Volume (Intermédiaire – 7 points)**  
**Objectif :** Trouver **les valeurs maximale et minimale des volumes échangés**.  

### **Solution Spark SQL**
```python
query3 = """
  SELECT MAX(Volume) AS MaxVolume, MIN(Volume) AS MinVolume
  FROM amrb
"""
result3 = spark.sql(query3)
result3.show()
```

---

## **4. Moyenne Annuelle des Valeurs d’Ouverture (Intermédiaire – 7 points)**  
**Objectif :** Calculer la **moyenne des prix d’ouverture** de l’action **par année**.  

### **Solution Spark SQL**
```python
query4 = """
  SELECT YEAR(Date) AS Year, AVG(Open) AS AvgOpen 
  FROM amrb
  GROUP BY YEAR(Date)
"""
result4 = spark.sql(query4)
result4.show()
```

---

## **5. Total des Volumes par Mois (Intermédiaire – 7 points)**  
**Objectif :** Déterminer **la somme des volumes échangés** par **mois**, en incluant l’année.  

### **Solution Spark SQL**
```python
query5 = """
  SELECT YEAR(Date) AS Year, MONTH(Date) AS Month, SUM(Volume) AS TotalVolume
  FROM amrb
  GROUP BY YEAR(Date), MONTH(Date)
"""
result5 = spark.sql(query5)
result5.show()
```

---

## **6. Plus Grand Écart Quotidien (Avancé – 10 points)**  
**Objectif :** Identifier la **date où l’écart entre le prix le plus haut et le plus bas était le plus grand**.  

### **Solution Spark SQL**
```python
query6 = """
  SELECT Date, (High - Low) AS Range
  FROM amrb
  ORDER BY Range DESC
  LIMIT 1
"""
result6 = spark.sql(query6)
result6.show()
```

---

## **7. Moyenne Mobile sur 7 Jours (Avancé – 10 points)**  
**Objectif :** Calculer la **moyenne mobile du prix de clôture sur une fenêtre glissante de 7 jours**.  

### **Solution avec DataFrame API**  
```python
from pyspark.sql.window import Window
from pyspark.sql.functions import avg

# Créer une fenêtre pour la moyenne mobile sur 7 jours
windowSpec = Window.orderBy("Date").rowsBetween(-6, 0)

# Ajouter une colonne pour la moyenne mobile
amrb_data = amrb_data.withColumn("MovingAvg", avg("Close").over(windowSpec))

amrb_data.select("Date", "Close", "MovingAvg").show()
```

---

## **Critères d’Évaluation**  

| Critère | Détails | Points |
|---------|---------|--------|
| **Exactitude des requêtes SQL** | Syntaxe correcte et résultats attendus | 40 |
| **Utilisation de Spark SQL et DataFrame API** | Capacité à utiliser efficacement Spark | 15 |
| **Initialisation et Configuration** | Bonne mise en place de SparkSession et chargement des données | 10 |
| **Clarté et Documentation du Code** | Code bien structuré et expliqué | 10 |
| **Interprétation des résultats** | Explication des analyses et conclusions | 10 |
| **Exploration au-delà des exigences** | Optimisation et créativité dans l’approche | 5 |
| **Total** | **Note maximale possible** | **100** |

---

## **Instructions Supplémentaires**  

- **Environnement recommandé** :  
  - **Google Colab** pour exécuter PySpark.  
  - **Exécution locale** avec Scala et Spark si nécessaire.  
- **Vérifications préliminaires** :  
  - Assurez-vous que **Spark est bien initialisé** et que les données sont correctement chargées.  
  - **Analysez les résultats** après chaque requête pour valider la cohérence des données.  
- **Approfondissement possible** :  
  - Ajout de visualisations avec **Matplotlib** ou **Seaborn** pour mieux comprendre les tendances.  
  - Optimisation des calculs avec **cache()** et **persist()** pour améliorer les performances.  

**Fin de la correction.**
