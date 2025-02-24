


### **Exemple : Classification des événements dans Spark**
```scala
sealed trait Event
case class Login(userId: String) extends Event
case class Purchase(userId: String, amount: Double) extends Event
case object SystemError extends Event

def processEvent(event: Event): String = event match {
  case Login(user)       => s"Utilisateur $user connecté"
  case Purchase(user, a) => s"Utilisateur $user a acheté pour $a €"
  case SystemError       => "Erreur système détectée"
}

println(processEvent(Login("Alice")))           // Utilisateur Alice connecté
println(processEvent(Purchase("Bob", 99.99)))   // Utilisateur Bob a acheté pour 99.99 €
println(processEvent(SystemError))              // Erreur système détectée
```

---
# Annexe - *Explication détaillée mot à mot*
---
Ce code **définit un système de gestion d'événements** en Scala, en utilisant **Pattern Matching** et **les classes case**.

---

## **1. Définition de `sealed trait Event`**
```scala
sealed trait Event
```
- **`sealed`** → **Mot-clé Scala** qui signifie que **toutes les sous-classes de `Event` doivent être définies dans le même fichier**. Cela **empêche** d'ajouter des nouvelles sous-classes ailleurs, assurant que **tous les cas possibles sont connus à la compilation**.
- **`trait`** → **Mot-clé Scala** utilisé pour définir **une interface ou une classe abstraite** qui peut être héritée. Un `trait` ne peut pas être instancié directement.
- **`Event`** → **Nom du `trait`**, utilisé pour représenter **un événement générique** (exemple : une connexion, un achat, une erreur système).

📌 **Pourquoi `sealed trait` ?**  
✅ Scala oblige à **gérer tous les cas dans le pattern matching**.  
✅ Empêche **d'ajouter des événements imprévus** ailleurs dans le code.  

---

## **2. Définition des événements (`case class` et `case object`)**
Ces événements **héritent du `trait` `Event`**.

```scala
case class Login(userId: String) extends Event
```
- **`case class`** → **Mot-clé Scala** pour créer **une classe case** (optimisée pour le pattern matching).
- **`Login`** → **Nom de l'événement** (c'est une classe).
- **`userId: String`** → **Champ de la classe** qui contient l'identifiant de l'utilisateur.
- **`extends Event`** → Indique que `Login` est **un sous-type de `Event`**.

Exemple d'utilisation :
```scala
val login = Login("Alice")
println(login.userId) // Alice
```
---

```scala
case class Purchase(userId: String, amount: Double) extends Event
```
- **`case class`** → Mot-clé Scala pour **une classe case**.
- **`Purchase`** → Nom de l'événement.
- **`userId: String, amount: Double`** → Champs contenant **l'identifiant utilisateur** et **le montant de l'achat**.
- **`extends Event`** → `Purchase` est aussi un sous-type de `Event`.

Exemple :
```scala
val purchase = Purchase("Bob", 99.99)
println(purchase.amount) // 99.99
```
---

```scala
case object SystemError extends Event
```
- **`case object`** → Mot-clé Scala pour **un singleton immuable**.
- **`SystemError`** → Nom de l'événement.
- **`extends Event`** → `SystemError` est aussi un sous-type de `Event`.

📌 **Pourquoi `case object` ici ?**  
✅ Il n'a **pas de paramètres** (contrairement aux autres événements).  
✅ Il **représente un état unique** (exemple : une erreur système).  

---

## **3. Fonction `processEvent` avec Pattern Matching**
```scala
def processEvent(event: Event): String = event match {
```
- **`def`** → Mot-clé Scala pour **définir une fonction**.
- **`processEvent`** → Nom de la fonction.
- **`event: Event`** → Paramètre `event` de type `Event` (il peut être `Login`, `Purchase` ou `SystemError`).
- **`String`** → La fonction retourne **une chaîne de caractères**.
- **`event match { ... }`** → Utilise le **Pattern Matching** pour traiter l'événement.

---

### **4. Traitement des événements avec `case`**
```scala
  case Login(user)       => s"Utilisateur $user connecté"
```
- Si `event` est un **objet de type `Login`**, alors :  
  - **`user`** récupère la valeur de `userId`.  
  - On retourne **"Utilisateur Alice connecté"**.

Exemple :
```scala
println(processEvent(Login("Alice"))) // Utilisateur Alice connecté
```

---

```scala
  case Purchase(user, a) => s"Utilisateur $user a acheté pour $a €"
```
- Si `event` est un **objet de type `Purchase`**, alors :  
  - **`user`** récupère `userId`.  
  - **`a`** récupère `amount`.  
  - On retourne `"Utilisateur Bob a acheté pour 99.99 €"`.

Exemple :
```scala
println(processEvent(Purchase("Bob", 99.99))) // Utilisateur Bob a acheté pour 99.99 €
```

---

```scala
  case SystemError       => "Erreur système détectée"
```
- Si `event` est **SystemError**, on retourne `"Erreur système détectée"`.

Exemple :
```scala
println(processEvent(SystemError)) // Erreur système détectée
```

---

## **5. Résumé des concepts Scala utilisés**

| **Mot-clé** | **Explication** |
|------------|----------------|
| `sealed` | Indique que toutes les sous-classes doivent être définies dans le même fichier. |
| `trait` | Déclare un comportement ou une interface qui peut être héritée. |
| `case class` | Crée une classe optimisée pour le pattern matching et immuable par défaut. |
| `case object` | Crée un objet singleton (unique instance). |
| `extends` | Indique qu'une classe hérite d'un `trait`. |
| `match` | Permet d'écrire des conditions optimisées sous forme de Pattern Matching. |
| `case` | Décrit chaque scénario à tester dans le pattern matching. |

---

## **6. Pourquoi c'est utile en Big Data ?**

| **Problème** | **Solution avec Pattern Matching** |
|-------------|---------------------------------|
| Les événements ont des types différents (achat, connexion, erreur) | Scala permet de **gérer plusieurs types** avec un seul `trait` (`Event`). |
| Il faut classifier les données pour faire des analyses | Avec `match`, chaque événement est **traité automatiquement selon son type**. |
| Il faut éviter les erreurs (ex: un événement inconnu) | Avec `sealed trait`, Scala **force** à gérer **tous les cas possibles**. |
| Un achat et une connexion n'ont pas les mêmes informations | Avec `case class`, chaque type d'événement a **ses propres champs**. |

---

## **7. Conclusion**
📌 **Ce code permet de structurer et de traiter facilement des événements Big Data.**  
📌 **Le Pattern Matching permet d'écrire un code plus clair et sans erreurs.**  
📌 **Le `sealed trait` empêche d'oublier des cas lors du traitement des événements.**  

💡 **Dans un projet Big Data (ex: Apache Spark), ce modèle est utilisé pour classer et filtrer les données en temps réel.**
