### **`as[]` en Spark Scala : Transformer un DataFrame en Dataset**  
En **Spark avec Scala**, la méthode **`as[T]`** est utilisée pour **convertir un `DataFrame` en `Dataset[T]`**, où `T` est une **case class** représentant la structure des données.

---

## **Syntaxe et Exemple**
### **1. Définir une `case class` pour représenter les données**
```scala
case class Stock(Date: String, Open: Double, Close: Double)
```
La `case class Stock` définit la structure de chaque ligne du Dataset.

---

### **2. Charger les données en `DataFrame`**
```scala
val df = spark.read
  .option("header", "true") // La première ligne contient les noms des colonnes
  .option("inferSchema", "true") // Infère automatiquement les types
  .csv("AAPL.csv") // Charger un fichier CSV
```
À ce stade, **`df` est un DataFrame**, ce qui signifie qu'il **n'a pas de typage fort** (les colonnes sont stockées comme des `Row`).

---

### **3. Transformer le DataFrame en Dataset avec `as[Stock]`**
```scala
import spark.implicits._

val dataset: Dataset[Stock] = df.as[Stock]
```
- `df.as[Stock]` convertit **le DataFrame en Dataset[Stock]**.
- `Stock` est la case class qui donne un **typage fort** à chaque ligne.

Après la conversion, chaque ligne est maintenant un objet de type **`Stock`**, ce qui permet d'utiliser des **opérations fonctionnelles** comme `map()`, `filter()` en toute sécurité.

---

## **Différences entre DataFrame et Dataset avec `as[]`**
| **Méthode**          | **Avant (`DataFrame`)** | **Après (`Dataset[Stock]`)** |
|----------------------|----------------------|----------------------------|
| **Structure des données** | `Row` (non typé) | `Stock` (typé) |
| **Accès aux valeurs** | `df("Open")` | `ds.Open` |
| **Support des transformations** | `.select()`, `.groupBy()` | `.map()`, `.filter()`, `.flatMap()` |
| **Sécurité de type** | Non sécurisé (erreurs possibles) | Fortement typé (compilation plus sûre) |
| **Performance** | Optimisé (Catalyst) | Optimisé (Catalyst) |

---

## **Pourquoi utiliser `as[]` pour convertir en Dataset ?**
1. **Sécurité de type**  
Avec un Dataset, les erreurs sont détectées **à la compilation** au lieu de l'exécution.

2. **Facilité d'accès aux données**  
Dans un `DataFrame`, il est nécessaire de référencer les colonnes par leur nom (`df("Open")`), alors qu'avec un `Dataset`, il est possible d'utiliser `.Open` directement.

3. **Meilleure lisibilité du code**  
Le typage fort facilite la compréhension des données et des transformations.

4. **Performances optimisées**  
Les **Datasets bénéficient des optimisations Catalyst**, donc aussi rapides que les DataFrames.

---

## **Exemple d'utilisation de Dataset après conversion**
```scala
// Filtrer les actions dont le prix d'ouverture est supérieur à 100
val highOpenStocks = dataset.filter(_.Open > 100)
highOpenStocks.show()
```
- **Avec un Dataset**, il est possible d'utiliser `_.Open` directement car **chaque ligne est un objet `Stock`**.
- **Avec un DataFrame**, il faudrait écrire :  
  ```scala
  df.filter($"Open" > 100).show()
  ```

---

## **Conclusion**
- **`as[T]` permet de convertir un `DataFrame` en `Dataset[T]` en appliquant un typage fort avec une case class.**  
- **Les Datasets sont plus sûrs (typage fort) et aussi performants que les DataFrames grâce à Catalyst.**  
- **Les DataFrames restent préférés pour les manipulations simples, mais les Datasets sont utiles quand vous travaillez en Scala et avez besoin d’un typage strict.**
