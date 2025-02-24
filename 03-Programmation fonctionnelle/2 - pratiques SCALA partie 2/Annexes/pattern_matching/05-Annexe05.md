
### *Exemple : Nettoyage d'un fichier CSV* (Annexe 05)
```scala
case class Person(name: String, age: Option[Int])

def parseRow(row: Array[String]): Person = row match {
  case Array(name, age) if age.forall(_.isDigit) => Person(name, Some(age.toInt))
  case Array(name, _)                            => Person(name, None)
  case _                                         => Person("Inconnu", None)
}

val data = List(
  Array("Alice", "30"),
  Array("Bob", "not_a_number"),
  Array("Charlie")
)

val cleanedData = data.map(parseRow)
cleanedData.foreach(println)
```
**Pourquoi c'est utile en Big Data ?**  
- On peut **éviter les erreurs dues aux données mal formées**.  
- On **gère les valeurs manquantes proprement**.  

---


# Annexe 1 - *Distinction entre mots-clés et identifiants dans ce code Scala*

Ce code définit une **classe case `Person`**, une **fonction `parseRow`** qui utilise **Pattern Matching** sur un tableau (`Array[String]`), et applique cette fonction à une **liste `data`**.

---

## **1. Analyse ligne par ligne : Mots-clés vs Identifiants**

### **Définition de la classe `Person`**
```scala
case class Person(name: String, age: Option[Int])
```
| **Terme**   | **Type** | **Explication** |
|------------|----------|----------------|
| `case class` | **Mot-clé Scala** | Définit une **classe case**, qui génère automatiquement un constructeur, `toString`, `equals`, etc. |
| `Person`    | **Identifiant** | Nom de la classe, défini par l'utilisateur. |
| `name`      | **Identifiant** | Attribut de la classe, défini par l'utilisateur. |
| `String`    | **Mot-clé Scala** | Type de donnée de `name` (chaîne de caractères). |
| `age`       | **Identifiant** | Attribut de la classe, défini par l'utilisateur. |
| `Option[Int]` | **Type Scala (bibliothèque standard)** | Peut contenir `Some(Int)` ou `None` (permet de gérer l'absence de valeur). |
| `Int`       | **Mot-clé Scala** | Type entier natif. |

Les mots-clés Scala sont fixes et font partie du langage, tandis que les identifiants sont définis par le programmeur.

---

### **Définition de la fonction `parseRow`**
```scala
def parseRow(row: Array[String]): Person = row match {
```
| **Terme**   | **Type** | **Explication** |
|------------|----------|----------------|
| `def`      | **Mot-clé Scala** | Déclare une **fonction**. |
| `parseRow` | **Identifiant** | Nom de la fonction, défini par l'utilisateur. |
| `row`      | **Identifiant** | Paramètre d'entrée de la fonction. |
| `Array[String]` | **Type Scala (bibliothèque standard)** | Définit que `row` est un tableau de chaînes de caractères. |
| `Person`   | **Identifiant** | Type de retour de la fonction. |
| `match`    | **Mot-clé Scala** | Effectue un **Pattern Matching** sur `row`. |

---

### **Cas de Pattern Matching**
#### **Premier cas : Si `row` est un tableau de 2 éléments et que `age` est un nombre**
```scala
case Array(name, age) if age.forall(_.isDigit) => Person(name, Some(age.toInt))
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `case`   | **Mot-clé Scala** | Définit un **cas du Pattern Matching**. |
| `Array(name, age)` | **Identifiant + Type Scala** | Vérifie si `row` est un **tableau de 2 éléments**. |
| `if`     | **Mot-clé Scala** | Condition supplémentaire (`guard`). |
| `age.forall(_.isDigit)` | **Expression** | Vérifie si `age` ne contient que des chiffres. |
| `Person(name, Some(age.toInt))` | **Constructeur** | Crée un objet `Person` avec un `age` converti en entier. |
| `Some`   | **Type Scala (bibliothèque standard)** | Conteneur pour une valeur non `null` (ici, `age.toInt`). |
| `toInt`  | **Méthode Scala** | Convertit une chaîne (`String`) en entier (`Int`). |

Les méthodes comme `forall` et `toInt` font partie de la bibliothèque standard de Scala.

---

#### **Deuxième cas : Si `row` a 2 éléments mais `age` n'est pas un nombre**
```scala
case Array(name, _) => Person(name, None)
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `case`   | **Mot-clé Scala** | Définit un **cas du Pattern Matching**. |
| `Array(name, _)` | **Identifiant + Type Scala** | Vérifie si `row` est un tableau de **2 éléments**, mais ignore le deuxième (`_`). |
| `Person(name, None)` | **Constructeur** | Crée un objet `Person` avec `age = None`. |
| `None`   | **Type Scala (bibliothèque standard)** | Représente **l'absence de valeur**. |

---

#### **Troisième cas : `row` ne correspond à aucun autre pattern**
```scala
case _ => Person("Inconnu", None)
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `case`   | **Mot-clé Scala** | Définit un **cas du Pattern Matching**. |
| `_`      | **Mot-clé Scala (joker)** | Accepte **n'importe quelle valeur** (liste vide ou incorrecte). |
| `Person("Inconnu", None)` | **Constructeur** | Retourne `Person("Inconnu", None)` si la ligne est mal formattée. |

---

### **Création des données d'entrée**
```scala
val data = List(
  Array("Alice", "30"),
  Array("Bob", "not_a_number"),
  Array("Charlie")
)
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `val`    | **Mot-clé Scala** | Déclare une **valeur immuable**. |
| `data`   | **Identifiant** | Nom de la variable. |
| `List(...)` | **Type Scala (bibliothèque standard)** | Déclare une liste de **tableaux** (`Array[String]`). |
| `Array("Alice", "30")` | **Type Scala** | Tableau contenant `"Alice"` et `"30"`. |

---

### **Transformation des données**
```scala
val cleanedData = data.map(parseRow)
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `val`    | **Mot-clé Scala** | Définit une **valeur immuable**. |
| `cleanedData` | **Identifiant** | Nom de la variable. |
| `data.map(parseRow)` | **Méthode de List** | Applique `parseRow` à chaque élément de `data`. |

---

### **Affichage des résultats**
```scala
cleanedData.foreach(println)
```
| **Terme** | **Type** | **Explication** |
|----------|---------|----------------|
| `foreach` | **Méthode Scala** | Applique `println` à chaque élément de `cleanedData`. |

---

## **2. Tableau récapitulatif : Mots-clés vs Identifiants**
| **Type**        | **Exemples** |
|----------------|-------------|
| **Mots-clés Scala** | `case class`, `def`, `val`, `match`, `case`, `if`, `_` |
| **Identifiants (noms définis par l'utilisateur)** | `Person`, `parseRow`, `row`, `name`, `age`, `data`, `cleanedData` |
| **Types Scala (standard)** | `String`, `Int`, `Option`, `Some`, `None`, `Array`, `List` |
| **Méthodes Scala** | `forall`, `isDigit`, `toInt`, `map`, `foreach` |

---

## **3. Conclusion**
Les mots-clés Scala sont **réservés au langage** et ont une signification fixe (`case class`, `def`, `match`).  
Les identifiants sont **choisis par le développeur** (`Person`, `parseRow`, `data`).  
Les types et méthodes Scala standard facilitent la gestion des **listes, tableaux et options**.

