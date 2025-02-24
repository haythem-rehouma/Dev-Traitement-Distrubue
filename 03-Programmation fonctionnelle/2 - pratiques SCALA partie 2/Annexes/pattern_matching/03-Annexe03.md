


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
# Annexe - *Explication détaillée*
---


Ce code met en place **un système de gestion d'événements** en Scala en utilisant **Pattern Matching** et **les classes case**. Il est conçu pour **classer et traiter des événements comme des connexions (`Login`), des achats (`Purchase`) et des erreurs système (`SystemError`)**.

---

## **1. Définition du `trait` `Event`**
```scala
sealed trait Event
```
- **`sealed`** → **Mot-clé Scala** qui signifie que **toutes les sous-classes de `Event` doivent être définies dans le même fichier**. Cela **garantit** que Scala peut vérifier **tous les cas possibles lors de la compilation**.
- **`trait`** → **Mot-clé Scala** utilisé pour définir **une interface ou une classe abstraite**. Un `trait` **ne peut pas être instancié** directement.
- **`Event`** → **Identifiant (nom défini par l'utilisateur)**, utilisé pour représenter **un type générique d'événement**.

📌 **Pourquoi `sealed trait` ?**  
- Scala **force à gérer tous les cas** dans un `match-case`.  
- Empêche **l'ajout de nouveaux événements ailleurs**, ce qui **évite les bugs**.

---

## **2. Définition des événements (`case class` et `case object`)**
Ces événements **héritent de `Event`** et représentent **des actions possibles**.

### **Classe `Login`**
```scala
case class Login(userId: String) extends Event
```
- **`case class`** → **Mot-clé Scala** pour créer **une classe case**, utilisée pour le **Pattern Matching**.
- **`Login`** → **Identifiant défini par l'utilisateur**, représentant **un événement de connexion**.
- **`userId: String`** → **Paramètre** contenant l'**identifiant de l'utilisateur**.
- **`extends Event`** → Indique que `Login` **hérite de `Event`**.

💡 **Où est l'implémentation de `Login` ?**
> **Elle est générée automatiquement par Scala !**  
> Les `case class` **génèrent** automatiquement :
> - Un **constructeur** (`new Login("Alice")` est implicite).
> - Une **méthode `toString`** (`Login(Alice)`).
> - Une **méthode `equals`** pour la comparaison.
> - Une **copie facile** (`.copy()`).

✅ **Exemple d'utilisation :**
```scala
val login = Login("Alice")
println(login.userId) // Alice
```

---

### **Classe `Purchase`**
```scala
case class Purchase(userId: String, amount: Double) extends Event
```
- **`case class`** → Mot-clé Scala pour une classe case.
- **`Purchase`** → Identifiant, représente **un événement d'achat**.
- **`userId: String, amount: Double`** → Paramètres stockant **l'identifiant utilisateur** et **le montant de l'achat**.
- **`extends Event`** → `Purchase` **hérite de `Event`**.

✅ **Exemple d'utilisation :**
```scala
val purchase = Purchase("Bob", 99.99)
println(purchase.amount) // 99.99
```

---

### **Objet `SystemError`**
```scala
case object SystemError extends Event
```
- **`case object`** → **Mot-clé Scala** pour créer un **singleton**.
- **`SystemError`** → **Identifiant** représentant **une erreur système**.
- **`extends Event`** → `SystemError` **hérite de `Event`**.

📌 **Pourquoi `case object` ?**
✅ **Pas de paramètres**, donc inutile de créer une classe.  
✅ **Gère un état unique**, ce qui évite **les doublons en mémoire**.

✅ **Exemple d'utilisation :**
```scala
println(SystemError) // SystemError
```

---

## **3. Fonction `processEvent` avec Pattern Matching**
```scala
def processEvent(event: Event): String = event match {
```
- **`def`** → Mot-clé Scala pour **déclarer une fonction**.
- **`processEvent`** → Nom défini par l'utilisateur.
- **`event: Event`** → Paramètre **de type `Event`**, qui peut être :
  - `Login`
  - `Purchase`
  - `SystemError`
- **`String`** → La fonction **retourne une chaîne de caractères**.
- **`event match { ... }`** → Applique le **Pattern Matching** sur `event`.

---

### **Traitement des événements avec `case`**
```scala
  case Login(user)       => s"Utilisateur $user connecté"
```
- **`case`** → **Mot-clé Scala** pour un **cas de Pattern Matching**.
- **`Login(user)`** → **Teste si `event` est `Login`** et **extrait `userId`**.
- **Retourne** `"Utilisateur Alice connecté"`.

✅ **Exemple d'exécution :**
```scala
println(processEvent(Login("Alice"))) // Utilisateur Alice connecté
```

---

```scala
  case Purchase(user, a) => s"Utilisateur $user a acheté pour $a €"
```
- **`case`** → Définit un autre **cas**.
- **`Purchase(user, a)`** → Vérifie si `event` est `Purchase`, **extrait `userId` et `amount`**.
- **Retourne** `"Utilisateur Bob a acheté pour 99.99 €"`.

✅ **Exemple :**
```scala
println(processEvent(Purchase("Bob", 99.99))) // Utilisateur Bob a acheté pour 99.99 €
```

---

```scala
  case SystemError       => "Erreur système détectée"
```
- **`case`** → Définit un dernier cas.
- **`SystemError`** → Vérifie si `event` est `SystemError`.
- **Retourne** `"Erreur système détectée"`.

✅ **Exemple :**
```scala
println(processEvent(SystemError)) // Erreur système détectée
```

---

## **4. Distinction entre Mots-clés et Identifiants**
| **Terme**       | **Type** | **Explication** |
|----------------|----------|----------------|
| `sealed`       | **Mot-clé Scala** | Restreint l'héritage aux classes définies dans le même fichier. |
| `trait`        | **Mot-clé Scala** | Définit un type abstrait qui peut être hérité. |
| `Event`        | **Identifiant** | Nom du `trait`, défini par l'utilisateur. |
| `case class`   | **Mot-clé Scala** | Déclare une classe case. |
| `case object`  | **Mot-clé Scala** | Déclare un objet unique (singleton). |
| `Login`        | **Identifiant** | Nom d'une classe case, défini par l'utilisateur. |
| `Purchase`     | **Identifiant** | Nom d'une autre classe case. |
| `SystemError`  | **Identifiant** | Nom d'un `case object`. |
| `extends`      | **Mot-clé Scala** | Indique l'héritage d'un `trait`. |
| `def`          | **Mot-clé Scala** | Définit une fonction. |
| `processEvent` | **Identifiant** | Nom d'une fonction. |
| `match`        | **Mot-clé Scala** | Utilisé pour exécuter un Pattern Matching. |
| `case`         | **Mot-clé Scala** | Définit un cas dans un Pattern Matching. |
| `userId`       | **Identifiant** | Paramètre de `Login` et `Purchase`. |
| `amount`       | **Identifiant** | Paramètre de `Purchase`. |
| `event`        | **Identifiant** | Paramètre de la fonction `processEvent`. |

---

## **5. Pourquoi ce modèle est utile en Big Data ?**
| **Problème** | **Solution avec Pattern Matching** |
|-------------|---------------------------------|
| Événements Big Data variés | Scala **gère plusieurs types d'événements** avec `trait`. |
| Analyse et classification | Chaque événement est **traité automatiquement**. |
| Prévention des erreurs | Scala **force à gérer tous les cas** avec `sealed trait`. |

---

## **Conclusion**
Ce modèle **simplifie le traitement des événements**, **assure la robustesse du code**, et **évite les erreurs**. Dans **Apache Spark**, ce modèle est très utilisé pour **classer et filtrer les données en temps réel**.
