### **Pourquoi utiliser les classes case en Scala au lieu du scriptage classique ou des RDD dans Spark ?**  

Dans un contexte **Big Data**, il est possible d’utiliser différentes approches pour manipuler les données dans **Apache Spark** :  
1. **Le scriptage classique (DataFrames sans types explicites)**  
2. **Les RDD (Resilient Distributed Datasets)**  
3. **Les Datasets typés avec des classes case**  

Voyons pourquoi **les classes case et les Datasets sont souvent préférés**.

---

## **1. Problèmes du scriptage classique (DataFrames sans types explicites)**  
Un **DataFrame Spark standard** est une collection de **lignes non typées** (de type `Row`). Cela signifie que **les noms des colonnes sont en texte** et que les types de données sont vérifiés **à l'exécution**.

**Exemple avec un DataFrame classique** :
```scala
val df = spark.read.option("header", "true").csv("data.csv")

val result = df.filter(df("age") > 18) // Vérification du type à l'exécution
```
### **Pourquoi c'est un problème ?**  
- **Pas de vérification à la compilation** : Si la colonne `"age"` est absente ou mal typée, l'erreur apparaîtra **à l'exécution**.  
- **Pas d'auto-complétion** : Scala ne sait pas quelles colonnes existent, donc pas d’aide du compilateur.  
- **Risque d'erreurs fréquentes** : Une faute de frappe sur un nom de colonne (`"Age"` au lieu de `"age"`) peut causer un bug silencieux.

---

## **2. Pourquoi ne pas utiliser les RDD en Big Data ?**  
Les **RDD (Resilient Distributed Datasets)** sont les structures de base de Spark, mais elles sont **moins efficaces** que les DataFrames et les Datasets.

**Exemple avec un RDD :**
```scala
val rdd = spark.sparkContext.textFile("data.txt")
val filteredRDD = rdd.filter(line => line.split(",")(1).toInt > 18)
```
### **Problèmes des RDD en Big Data** :
1. **Manque d'optimisation**  
   - Spark ne comprend pas la structure des RDD, donc il ne peut pas **optimiser automatiquement** les requêtes.  
   - Toutes les transformations sont exécutées **en mémoire**, ce qui peut être plus lent.  

2. **Pas de vérification de type**  
   - Le RDD contient des `String`, donc **chaque conversion (`toInt`) doit être faite manuellement**.  
   - Si une ligne est mal formée, le code **crash à l'exécution**.  

3. **Pas d'intégration avec le moteur SQL de Spark**  
   - Les **RDD ne peuvent pas utiliser les optimisations SQL** comme le moteur **Catalyst** ou le format **Parquet optimisé**.  

---

## **3. Pourquoi utiliser les Classes Case avec Dataset ?**  
Les **classes case** permettent d’exploiter les **Datasets**, qui combinent **la performance des DataFrames** et **la sécurité des RDD**.

### **Exemple avec une classe case et un Dataset Spark**
```scala
case class Person(name: String, age: Int)

// Charger les données et convertir en Dataset typé
val peopleDS = spark.read.option("header", "true").csv("data.csv")
  .as[Person]

// Appliquer un filtre en toute sécurité
val adults = peopleDS.filter(_.age > 18)

adults.show()
```
---

### **Avantages des Classes Case et Dataset**
| Critère | RDD | DataFrame | Dataset (avec classe case) |
|---------|-----|----------|---------------------------|
| **Performance** | Faible (pas d'optimisation) | Haute | Très haute |
| **Vérification à la compilation** | ❌ Non | ❌ Non | ✅ Oui |
| **Optimisation Spark (Catalyst, Tungsten)** | ❌ Non | ✅ Oui | ✅ Oui |
| **Manipulation avec des objets** | ❌ Non (tableaux) | ❌ Non (`Row`) | ✅ Oui (objets Scala) |
| **Interopérabilité avec SQL** | ❌ Non | ✅ Oui | ✅ Oui |
| **Lisibilité et maintenabilité** | ❌ Difficile | ⚠️ Moyen | ✅ Facile |

---

## **Conclusion : Pourquoi choisir les Classes Case avec Dataset ?**  

1. **Sécurité du typage**  
   - Contrairement aux DataFrames classiques, **le compilateur Scala détecte les erreurs avant l’exécution**.  

2. **Optimisation automatique par Spark**  
   - Spark peut **analyser et optimiser** les Datasets (contrairement aux RDD).  

3. **Lisibilité et facilité de manipulation**  
   - On peut manipuler les données avec **des objets Scala** et non des lignes brutes (`Row`).  

4. **Interopérabilité avec SQL et les DataFrames**  
   - Un Dataset peut être **converti en DataFrame et vice-versa**.  

**Verdict :**  
- **Les RDD sont trop lents et complexes** pour le Big Data.  
- **Les DataFrames classiques sont rapides mais risqués** car ils ne sont pas typés.  
- **Les Datasets avec Classes Case combinent les avantages des deux** et sont le meilleur choix pour travailler avec **Apache Spark en Scala**.
