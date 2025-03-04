# **Correction du Quiz – Distribue-1 (30 points)**  

### **Scala et Programmation Fonctionnelle**  

**Barème :** 2 points par question | **Total : 30 points**  
**Instructions :** Une seule réponse correcte par question.  

---

### **1. Quel est le résultat de l'exécution du code suivant ?**  
```scala
val list = List(1, 2, 3, 4, 5)  
list.filter(_ % 2 == 0).map(_ * 2)
```  
**Réponse :** `List(4, 8)`  

**Explication :**  
- `filter(_ % 2 == 0) → List(2, 4)` (on garde les nombres pairs).  
- `map(_ * 2) → List(4, 8)` (chaque élément est multiplié par 2).  

---

### **2. Quelle est la particularité des objets `case` en Scala ?**  
**Réponse :** `Ils supportent automatiquement le pattern matching.`  

**Explication :**  
- Les `case class` génèrent des méthodes automatiques (`apply`, `unapply`) et facilitent la déconstruction via pattern matching.  

---

### **3. Comment définir une méthode générique en Scala ?**  
**Réponse :** `def methodName[T](param: T): T = {...}`  

**Explication :**  
- `[T]` définit un paramètre de type générique.  

---

### **4. Concernant les traits en Scala, lequel est vrai ?**  
**Réponse :** `Un trait peut contenir des méthodes concrètes et abstraites.`  

**Explication :**  
- Contrairement aux interfaces Java, un `trait` Scala peut contenir des implémentations **et** des méthodes abstraites.  

---

### **5. Quelle est la sortie du code suivant ?**  
```scala
def multiply(x: Int, y: Int): Int = x * y  
println(multiply(2, 3))
```  
**Réponse :** `6`  

**Explication :**  
- La fonction multiplie `2 × 3 = 6`.  

---

### **6. Meilleure façon d’itérer sur une liste et appliquer une fonction ?**  
**Réponse :** `list.foreach(function)`  

**Explication :**  
- `foreach` applique une fonction **sans créer une nouvelle collection** (contrairement à `map`).  

---

### **7. Différence entre `val` et `var` en Scala ?**  
**Réponse :** `val` déclare une variable immuable, tandis que `var` déclare une variable mutable.`  

**Explication :**  
- `val` = constante **(immuable)**.  
- `var` = modifiable **(mutable)**.  

---

### **8. Qu'est-ce qu'un `for-comprehension` en Scala ?**  
**Réponse :** `Une boucle for spéciale qui peut retourner un résultat.`  

**Explication :**  
- Contrairement aux `for` classiques, il retourne une **collection de valeurs**.  

---

### **9. Rôle du mot-clé `yield` dans un `for` ?**  
**Réponse :** `Retourne une valeur à partir d'une boucle, créant une collection des résultats.`  

**Explication :**  
- `yield` génère un **nouveau tableau/liste** en appliquant une transformation.  

---

### **10. Le pattern matching en Scala est similaire à ?**  
**Réponse :** `Les switch statements en C.`  

**Explication :**  
- Il fonctionne comme `switch`, mais en **beaucoup plus puissant** (supporte la déconstruction d’objets).  

---

### **11. Différence entre `flatMap` et `map` ?**  
**Réponse :** `flatMap utilise une fonction qui retourne une collection, tandis que map utilise une fonction qui retourne un seul élément.`  

**Explication :**  
- `map(f)` applique `f` à chaque élément et retourne une nouvelle collection.  
- `flatMap(f)` **aplatit** les résultats si `f` retourne une collection.  

---

### **12. Pourquoi utiliser `Option` en Scala ?**  
**Réponse :** `Elle permet une gestion plus sûre des nullités, en forçant l'utilisateur à vérifier explicitement la présence d'une valeur avant de l'utiliser.`  

**Explication :**  
- Remplace `null` par `Some(value)` ou `None`, évitant ainsi les `NullPointerException`.  

---

### **13. Quel est le rôle du mot-clé `implicit` en Scala ?**  
**Réponse :** `Il indique qu'une variable ou fonction peut être passée automatiquement comme paramètre à une fonction.`  

**Explication :**  
- Utilisé pour injecter des **paramètres implicites** sans les passer explicitement.  

---

### **14. Qu'est-ce que `lazy val` en Scala ?**  
**Réponse :** `Une valeur qui est calculée et assignée lors de sa première utilisation.`  

**Explication :**  
- Évite l’évaluation **immédiate** et améliore les performances si la variable n’est jamais utilisée.  

---

### **15. Comment créer un singleton en Scala ?**  
**Réponse :** `En déclarant une classe avec le mot-clé object.`  

**Explication :**  
- Scala gère les **singletons** via `object`, sans besoin de `new`.  
