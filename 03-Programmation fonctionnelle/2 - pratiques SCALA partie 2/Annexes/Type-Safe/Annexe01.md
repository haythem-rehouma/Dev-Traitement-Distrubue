### **Explication Vulgarisée : Qu'est-ce que la Sûreté de Type (Type-Safe) ?**

La **sûreté de type** signifie qu'un langage de programmation **empêche** d'utiliser des données d'un certain type de manière incorrecte.  
En d'autres termes, **il bloque les erreurs avant même que le programme ne s'exécute**, ce qui évite les bugs et les failles de sécurité.

---

## **1. Pourquoi c'est important ?**
Si un langage n'est pas type-safe, un programmeur pourrait, par exemple, **additionner un nombre et du texte** par erreur :
```scala
val x: Int = "texte" // ERREUR en Scala
```
En Scala, **le compilateur empêche cette erreur** avant l'exécution. En revanche, dans des langages comme Python ou JavaScript, ce genre d'erreur peut passer inaperçue et créer des bugs.

---

## **2. Quels sont les avantages de la sûreté de type ?**

### **Moins d'erreurs au moment d'exécuter le programme**
Dans les langages type-safe comme Scala, **les erreurs sont détectées dès la compilation**. Cela évite des plantages ou des comportements imprévisibles.

### **Code plus fiable et plus sécurisé**
Les langages qui ne sont pas type-safe (comme C) permettent **des manipulations dangereuses de la mémoire**, qui peuvent être exploitées pour des attaques informatiques.

### **Meilleure compréhension du code**
Si chaque variable a **un type bien défini**, il est plus facile de comprendre ce que fait le programme. On évite aussi les conversions de type inutiles.

---

## **3. Exemples concrets**

### **Exemple 1 : Erreur détectée avant l'exécution**
```scala
val nombre: Int = "42" // ERREUR : "42" est une chaîne, pas un entier
```
En Scala, cette erreur est bloquée immédiatement.

Dans JavaScript, au contraire :
```javascript
let nombre = "42" + 1; // Pas d'erreur, mais résultat inattendu "421"
```
Cela peut causer des bugs imprévisibles.

---

### **Exemple 2 : Inférence de Type**
Scala **devine** les types automatiquement :
```scala
val x = 10  // Scala sait que c'est un Int
val y = "Bonjour" // Scala sait que c'est une String
```
Même si les types ne sont pas écrits, Scala **les détecte et empêche les erreurs**.

---

## **4. Quels sont les risques d'un langage non type-safe ?**
Un langage qui **ne contrôle pas bien les types** peut être vulnérable aux attaques informatiques.

### **Dépassement de Mémoire (Buffer Overflow)**
Dans des langages comme C, on peut écrire au-delà de la mémoire autorisée, ce qui permet à un hacker d'exécuter du code malveillant.

### **Confusion de Type (Type Confusion)**
Si un programme traite une donnée **comme un mauvais type**, il peut accéder à des informations qu'il ne devrait pas.

---

## **5. Pourquoi Scala est type-safe et adapté au Big Data ?**
Dans les applications **Big Data** (ex: Apache
