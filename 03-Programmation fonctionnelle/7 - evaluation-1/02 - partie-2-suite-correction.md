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
- `filter(_ % 2 == 0)` garde les nombres pairs `[2, 4]`.  
- `map(_ * 2)` multiplie chaque élément par 2, donnant `[4, 8]`.  

---

### **2. Quelle est la particularité des objets `case` en Scala ?**  
**Réponse :** `Ils supportent automatiquement le pattern matching.`  

**Explication :**  
- Les `case class` facilitent le pattern matching en générant des méthodes `apply`, `unapply`, `equals`, `hashCode` et `toString` automatiquement.  

---

### **3. Comment définir une méthode générique en Scala ?**  
**Réponse :** `def methodName[T](param: T): T = {...}`  

**Explication :**  
- `[T]` définit un type générique.  

---

### **4. Concernant les traits en Scala, lequel est vrai ?**  
**Réponse :** `Un trait peut contenir des méthodes concrètes et abstraites.`  

**Explication :**  
- Un `trait` peut définir des méthodes avec ou sans implémentation, contrairement aux interfaces Java classiques.  

---

### **5. Quelle est la sortie du code suivant ?**  
```scala
def multiply(x: Int, y: Int): Int = x * y  
println(multiply(2, 3))
```  
**Réponse :** `6`  

**Explication :**  
- L'appel `multiply(2, 3)` retourne `2 × 3 = 6`.  

---

### **6. Meilleure façon d’itérer sur une liste et appliquer une fonction ?**  
**Réponse :** `list.foreach(function)`  

**Explication :**  
- `foreach` applique une fonction à chaque élément **sans créer une nouvelle collection**, contrairement à `map`.  

---

### **7. Différence entre `val` et `var` en Scala ?**  
**Réponse :** `val déclare une variable immuable, tandis que var déclare une variable mutable.`  

**Explication :**  
- `val` crée une constante (**immuable**), tandis que `var` crée une variable modifiable (**mutable**).  

---

### **8. Qu'est-ce qu'un `for-comprehension` en Scala ?**  
**Réponse :** `Une boucle for spéciale qui peut retourner un résultat.`  

**Explication :**  
- Contrairement aux `for` classiques, il retourne une **collection de valeurs** au lieu d'exécuter simplement du code.  

---

### **9. Rôle du mot-clé `yield` dans un `for` ?**  
**Réponse :** `Retourne une valeur à partir d'une boucle, créant une collection des résultats.`  

**Explication :**  
- `yield` permet de construire une nouvelle collection en appliquant une transformation sur chaque élément d’une boucle `for`.  

---

### **10. Le pattern matching en Scala est similaire à ?**  
**Réponse :** `Les switch statements en C.`  

**Explication :**  
- Il fonctionne comme `switch`, mais avec des capacités avancées, comme la décomposition d’objets et les guards.  

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
- `Option` remplace `null` et force le développeur à gérer proprement les valeurs absentes pour éviter les erreurs de type `NullPointerException`.  

---

### **13. Quel est le rôle du mot-clé `implicit` en Scala ?**  
**Réponse :** `Il indique qu'une variable ou fonction peut être passée automatiquement comme paramètre à une fonction.`  

**Explication :**  
- `implicit` permet d’éviter d’écrire explicitement certains paramètres en les passant automatiquement lorsqu’ils sont requis.  

---

### **14. Qu'est-ce que `lazy val` en Scala ?**  
**Réponse :** `Une valeur qui est calculée et assignée lors de sa première utilisation.`  

**Explication :**  
- Une variable `lazy val` ne sera évaluée que lorsque son premier accès aura lieu, ce qui améliore les performances si elle n'est jamais utilisée.  

---

### **15. Comment créer un singleton en Scala ?**  
**Réponse :** `En déclarant une classe avec le mot-clé object.`  

**Explication :**  
- Scala gère les **singletons** via `object`, ce qui évite le besoin d'une instanciation explicite.  

---

### **Notation et conseils**  

| Score | Interprétation |
|--------|---------------|
| 30/30 | Maîtrise parfaite des concepts Scala. |
| 24-28/30 | Très bon niveau, quelques détails à approfondir. |
| 18-22/30 | Bon niveau, revoir certaines notions avancées. |
| < 18/30 | Besoin de réviser les bases (collections, `Option`, `flatMap`). |

