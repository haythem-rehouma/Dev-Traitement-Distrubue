

### **1. Configuration de Spark et Chargement des Données**
```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

val spark = SparkSession.builder()
  .appName("AAPL_Analysis")
  .master("local[*]")
  .getOrCreate()

// Charger le fichier CSV (assurez-vous que le fichier est dans le bon répertoire)
val df = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .csv("AAPL.csv")

// Créer une table temporaire pour utiliser Spark SQL
df.createOrReplaceTempView("AAPL")
```

---

### **2. Liste des dates de transaction, ouverture et fermeture**
```scala
val result1 = spark.sql("""
  SELECT Date, Open, Close
  FROM AAPL
""")
result1.show()
```
Affiche les **dates de transaction** avec les valeurs d’**ouverture** et de **fermeture**.

---

### **3. Liste des dates de transaction avec la différence entre fermeture et ouverture**
```scala
val result2 = spark.sql("""
  SELECT Date, (Close - Open) AS Diff_Close_Open
  FROM AAPL
""")
result2.show()
```
Calcule la **différence entre la fermeture et l’ouverture** de l’action.

---

### **4. Max et Min des volumes**
```scala
val result3 = spark.sql("""
  SELECT MAX(Volume) AS Max_Volume, MIN(Volume) AS Min_Volume
  FROM AAPL
""")
result3.show()
```
Retourne les **valeurs maximales et minimales des volumes** échangés.

---

### **5. Moyenne des valeurs d’ouverture par année**
```scala
val result4 = spark.sql("""
  SELECT SUBSTRING(Date, 1, 4) AS Year, AVG(Open) AS Avg_Open
  FROM AAPL
  GROUP BY Year
  ORDER BY Year
""")
result4.show()
```
Calcule la **moyenne des valeurs d’ouverture** pour chaque année.

---

### **6. Somme des volumes par mois**
```scala
val result5 = spark.sql("""
  SELECT SUBSTRING(Date, 1, 7) AS Year_Month, SUM(Volume) AS Total_Volume
  FROM AAPL
  GROUP BY Year_Month
  ORDER BY Year_Month
""")
result5.show()
```
Affiche la **somme des volumes échangés par mois**.

---

## **Explication**
- **SparkSession** : Création d'une session Spark.
- **DataFrame API** : Lecture du fichier CSV sans utiliser de **case classes**.
- **createOrReplaceTempView** : Permet d’utiliser **SQL directement** sur le DataFrame.
- **SUBSTRING(Date, 1, 4)** : Extrait **l'année** pour la moyenne.
- **SUBSTRING(Date, 1, 7)** : Extrait **l'année et le mois** pour la somme des volumes.

Ces requêtes sont efficaces et adaptées aux **grands volumes de données**.
