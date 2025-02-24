
# *Exemple : Vérifier le type d'une donnée*
```scala
def identifyType(x: Any): String = x match {
  case _: Int    => "C'est un entier"
  case _: String => "C'est une chaîne de caractères"
  case _: Boolean => "C'est un booléen"
  case null      => "Valeur manquante"
  case _         => "Type inconnu"
}

println(identifyType(42))         // C'est un entier
println(identifyType("Scala"))    // C'est une chaîne de caractères
println(identifyType(true))       // C'est un booléen
println(identifyType(null))       // Valeur manquante
```
**Pourquoi c'est utile en Big Data ?**  
- On peut **filtrer les données invalides**.  
- On peut **automatiser la conversion des types**.  


----
# *Annexe - Explication détaillée du code*
----

Ce code définit une fonction `identifyType` qui **analyse le type** de la valeur passée en paramètre et **retourne une chaîne de caractères** décrivant ce type.

---

## **1. Définition de la fonction**
```scala
def identifyType(x: Any): String = x match {
```
- **`def identifyType(x: Any): String`** → Déclare une fonction nommée `identifyType` qui prend un **paramètre `x` de type `Any`**.  
  - `Any` signifie que **n'importe quel type d'objet** peut être passé (entier, chaîne, booléen, etc.).  
  - La fonction **retourne un `String`**, c'est-à-dire une phrase expliquant le type de `x`.

- **`x match { ... }`** → Utilise **le pattern matching** pour comparer `x` à plusieurs **cas (`case`)**.

---

## **2. Explication des cas (`case`)**

```scala
  case _: Int    => "C'est un entier"
```
- **`case _: Int`** → Vérifie si `x` est un **entier (`Int`)**.  
- **Si oui**, retourne la chaîne `"C'est un entier"`.

---

```scala
  case _: String => "C'est une chaîne de caractères"
```
- **`case _: String`** → Vérifie si `x` est une **chaîne de caractères (`String`)**.  
- **Si oui**, retourne `"C'est une chaîne de caractères"`.

---

```scala
  case _: Boolean => "C'est un booléen"
```
- **`case _: Boolean`** → Vérifie si `x` est un **booléen (`true` ou `false`)**.  
- **Si oui**, retourne `"C'est un booléen"`.

---

```scala
  case null      => "Valeur manquante"
```
- **`case null`** → Vérifie si `x` est **`null`** (valeur vide).  
- **Si oui**, retourne `"Valeur manquante"`.

---

```scala
  case _         => "Type inconnu"
```
- **`case _`** → **Cas par défaut**, utilisé si `x` **ne correspond à aucun des cas précédents**.  
- **Si aucun des types vérifiés ne correspond**, retourne `"Type inconnu"`.

---

## **3. Appel de la fonction avec différents types de valeurs**

```scala
println(identifyType(42))         // C'est un entier
```
- `42` est un entier (`Int`), donc **le premier `case` s'applique**.  
- Résultat : `"C'est un entier"`.

---

```scala
println(identifyType("Scala"))    // C'est une chaîne de caractères
```
- `"Scala"` est une chaîne (`String`), donc **le deuxième `case` s'applique**.  
- Résultat : `"C'est une chaîne de caractères"`.

---

```scala
println(identifyType(true))       // C'est un booléen
```
- `true` est un **booléen (`Boolean`)**, donc **le troisième `case` s'applique**.  
- Résultat : `"C'est un booléen"`.

---

```scala
println(identifyType(null))       // Valeur manquante
```
- `null` est **une valeur vide**, donc **le `case null` s'applique**.  
- Résultat : `"Valeur manquante"`.

---

```scala
println(identifyType(3.14))       // Type inconnu
```
- `3.14` est un `Double`, qui **n'est pas prévu dans les `case`**, donc le `case _` s'applique.  
- Résultat : `"Type inconnu"`.

---

## **Résumé du fonctionnement**
1. La fonction **prend une valeur `x`** de **n'importe quel type** (`Any`).
2. Elle **compare `x`** à **plusieurs types** (`Int`, `String`, `Boolean`, `null`).
3. **Si `x` correspond à un des types testés**, elle **retourne une phrase descriptive**.
4. **Si `x` ne correspond à aucun type testé, elle retourne "Type inconnu"**.

---

## **Pourquoi c'est utile en Big Data ?**
| **Problème** | **Solution avec Pattern Matching** |
|-------------|---------------------------------|
| Les données Big Data contiennent souvent des valeurs de différents types | On peut tester et identifier les types facilement |
| Certaines données sont manquantes (`null`) | On peut détecter `null` et l'afficher clairement |
| On veut nettoyer et organiser les données | On peut filtrer et classer les types efficacement |

---

## **Conclusion**
Le **Pattern Matching** permet d’écrire **un code clair et efficace** pour **tester des types** et **éviter les erreurs** dans les traitements de données.  

En **Big Data**, ce genre de fonction est utile pour **analyser des jeux de données hétérogènes**, **filtrer des erreurs**, et **organiser les valeurs avant leur stockage**.
