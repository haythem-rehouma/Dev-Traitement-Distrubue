# **Quiz – Distribue-1 (30 points)**  
**Matière : Scala et Programmation Fonctionnelle**  
**Instructions :**  
- **Type de questions :** Choix unique.  
- **Barème :** Chaque question vaut **2 points**.  
- **Notation :** Seule **une réponse** est correcte par question.  
- **Durée suggérée :** 30 minutes.  
- **Répondez avec précision** et **justifiez vos choix** lorsque possible.  

### Documentation : 
- https://github.com/haythem-rehouma/Dev-Traitement-Distrubue/tree/main/03-Programmation%20fonctionnelle/2%20-%20pratiques%20SCALA%20partie%202

---

## **1. Quel est le résultat de l'exécution du code Scala suivant ?**  
```scala
val list = List(1, 2, 3, 4, 5)  
list.filter(_ % 2 == 0).map(_ * 2)
```  
🔘 **List(2, 4)**  
🔘 **List(4, 8)**  
🔘 **List(1, 3, 5)**  
🔘 **List(2, 4, 6, 8, 10)**  

---

## **2. Quelle est la particularité des objets case en Scala ?**  
🔘 **Ils ne peuvent pas avoir de méthodes.**  
🔘 **Ils supportent automatiquement le pattern matching.**  
🔘 **Ils ne peuvent pas être instanciés.**  
🔘 **Ils sont principalement utilisés pour la programmation impérative.**  

---

## **3. Comment définir une méthode générique en Scala ?**  
🔘 **`def methodName[T](param: T): T = {...}`**  
🔘 **`def methodName(param: T): T = {...}`**  
🔘 **`def <T> methodName(param: T): T = {...}`**  
🔘 **`T def methodName(param: T): T = {...}`**  

---

## **4. Lequel des éléments suivants est vrai concernant les traits en Scala ?**  
🔘 **Un trait peut être instancié directement comme une classe.**  
🔘 **Un trait peut contenir des méthodes concrètes et abstraites.**  
🔘 **Un trait est l'équivalent d'une classe abstraite en Java.**  
🔘 **Un trait ne peut pas contenir de méthodes concrètes.**  

---

## **5. Quelle est la sortie du code Scala suivant ?**  
```scala
def multiply(x: Int, y: Int): Int = x * y  
println(multiply(2, 3))
```  
🔘 **`2 * 3`**  
🔘 **`6`**  
🔘 **`multiply(2,3)`**  
🔘 **Erreur de compilation**  

---

## **6. Quel est le moyen le plus efficace d'itérer sur tous les éléments d'une liste en Scala pour appliquer une fonction à chaque élément ?**  
🔘 **`for (i <- list) function(i)`**  
🔘 **`list.foreach(function)`**  
🔘 **`list.map(function)`**  
🔘 **`for (i = 0; i < list.length; i++) function(list(i))`**  

---

## **7. Quelle affirmation est vraie à propos de `val` et `var` en Scala ?**  
🔘 **`val` déclare une variable mutable, tandis que `var` déclare une variable immuable.**  
🔘 **`val` et `var` sont tous les deux mutables, mais `val` est thread-safe.**  
🔘 **`val` déclare une variable immuable, tandis que `var` déclare une variable mutable.**  
🔘 **`val` et `var` sont des mots-clés pour les méthodes et non pour les variables.**  

---

## **8. Qu'est-ce qu'une expression `for-comprehension` en Scala ?**  
🔘 **Une boucle `for` spéciale qui peut retourner un résultat.**  
🔘 **Un moyen de filtrer les éléments d'une collection.**  
🔘 **Une syntaxe pour définir des compréhensions de liste en Python.**  
🔘 **Un outil pour la programmation réactive asynchrone.**  

---

## **9. En Scala, que fait le mot-clé `yield` lorsqu'il est utilisé dans une boucle `for` ?**  
🔘 **Interrompt l'exécution de la boucle.**  
🔘 **Retourne une valeur à partir d'une boucle, créant une collection des résultats.**  
🔘 **Indique qu'une fonction est une coroutine.**  
🔘 **Ne fait rien de particulier, c'est juste une syntaxe pour améliorer la lisibilité.**  

---

## **10. Le pattern matching en Scala est similaire à quelles structures dans d'autres langages de programmation ?**  
🔘 **Les `switch` statements en C**  
🔘 **Les expressions lambda en Python**  
🔘 **Les tableaux associatifs en PHP**  
🔘 **Les fonctions fléchées en JavaScript**  

---

## **11. Quelle est la différence principale entre `flatMap` et `map` en Scala ?**  
🔘 **`flatMap` peut retourner plusieurs éléments pour chaque élément d'entrée, tandis que `map` retourne exactement un élément de sortie pour chaque élément d'entrée.**  
🔘 **`map` est plus rapide que `flatMap`.**  
🔘 **`flatMap` utilise une fonction qui retourne une collection, tandis que `map` utilise une fonction qui retourne un seul élément.**  
🔘 **Il n'y a pas de différence; `flatMap` et `map` sont interchangeables.**  

---

## **12. En Scala, quel est l'avantage principal d'utiliser une `Option` pour gérer les valeurs qui peuvent être `null` ?**  
🔘 **Elle permet une gestion plus sûre des nullités, en forçant l'utilisateur à vérifier explicitement la présence d'une valeur avant de l'utiliser.**  
🔘 **Elle améliore la performance du code en éliminant la nécessité de vérifications `null`.**  
🔘 **Elle permet d'utiliser des fonctionnalités avancées de programmation fonctionnelle sans se soucier des valeurs `null`.**  
🔘 **Elle est seulement utile pour la compatibilité avec les bibliothèques Java qui retournent `null`.**  

---

## **13. Quel est le rôle du mot-clé `implicit` en Scala ?**  
🔘 **Il indique qu'une variable ou fonction peut être passée automatiquement comme paramètre à une fonction.**  
🔘 **Il est utilisé pour déclarer des conversions de types automatiques.**  
🔘 **Il rend une méthode ou une variable disponible globalement sans avoir besoin de l'importer.**  
🔘 **Il déclare une variable ou une fonction qui ne peut pas être modifiée ou surchargée.**  

---

## **14. Qu'est-ce que le `lazy val` en Scala ?**  
🔘 **Une valeur qui est calculée et assignée lors de sa première utilisation.**  
🔘 **Une constante qui ne peut jamais être modifiée après son initialisation.**  
🔘 **Une variable qui peut être initialisée plusieurs fois.**  
🔘 **Une méthode qui est exécutée en arrière-plan, de manière asynchrone.**  

---

## **15. Comment pouvez-vous créer un singleton en Scala ?**  
🔘 **En utilisant le mot-clé `singleton` lors de la déclaration d'une classe.**  
🔘 **En déclarant une classe avec le mot-clé `object`.**  
🔘 **En créant une classe normale avec un constructeur privé et une méthode `getInstance`.**  
🔘 **Scala ne supporte pas directement les singletons; ils doivent être implémentés à l'aide de bibliothèques tierces.**  

---

# **Fin du Quiz – Distribue-1**  
💡 **Instructions finales :**  
- Assurez-vous de bien **relire vos réponses** avant de soumettre.  
- Un raisonnement logique et structuré sera valorisé dans l’évaluation.  
