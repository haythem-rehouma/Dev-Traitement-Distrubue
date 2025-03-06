### **Examen – Partie 1 : Programmation Fonctionnelle avec Scala et PySpark**  

#### **Durée :** 1 heure  
#### **Instructions :**  
- Lisez attentivement chaque question.  
- Sélectionnez la **meilleure réponse** parmi les choix proposés.  
- Une seule réponse correcte par question.  

---

### **1. Quelle est la principale différence entre transformations et actions dans Spark ?**  
☐ Les transformations sont exécutées immédiatement, tandis que les actions sont paresseuses.  
☐ Les actions déclenchent l’exécution des transformations et retournent un résultat.  
☐ Les transformations sont utilisées uniquement pour créer des RDDs, alors que les actions servent à les manipuler.  
☐ Les actions créent un DAG (Directed Acyclic Graph), tandis que les transformations n'ont aucun impact sur l'exécution du code.  

---

### **2. Quelle est la différence entre flatMap et map dans Spark ?**  
☐ flatMap retourne une liste de listes de mots, tandis que map retourne une seule liste.  
☐ flatMap divise chaque élément en plusieurs éléments, tandis que map conserve la structure d’origine.  
☐ flatMap et map produisent le même résultat, mais flatMap est plus performant.  
☐ Aucune différence significative, flatMap est juste une alternative à map.  

---

### **3. Pourquoi reduceByKey est-il utilisé dans le calcul du WordCount au lieu de reduce ?**  
☐ Parce qu'il permet d’agréger les valeurs directement sur chaque partition avant de les combiner globalement.  
☐ Parce qu'il réduit uniquement les clés qui existent déjà dans le RDD sans les regrouper.  
☐ Parce qu'il est plus rapide que reduce, mais produit un résultat identique.  
☐ Parce qu'il ne nécessite pas de transformation préalable sur le RDD.  

---

### **4. Quelle est la principale différence entre groupByKey et reduceByKey ?**  
☐ groupByKey regroupe les valeurs sans les agréger, alors que reduceByKey applique une réduction sur chaque clé.  
☐ reduceByKey est plus lent que groupByKey, car il effectue des agrégations supplémentaires.  
☐ groupByKey est utilisé uniquement pour trier les valeurs dans un RDD.  
☐ Il n’y a pas de réelle différence, les deux peuvent être utilisés de manière interchangeable.  

---

### **5. Quelle sera la sortie de l’opération suivante ?**  
```scala
val rddf = sc.parallelize(List("Salim","Martha-Patricia","Abed","François"))  
val result = rddf.filter(x => x.contains("-")).collect()
```  
☐ List("Martha-Patricia")  
☐ List("Salim", "Martha-Patricia", "Abed", "François")  
☐ List("Abed", "François")  
☐ Une erreur se produit car filter ne fonctionne pas sur les RDDs.  

---

### **6. Quelle est la sortie probable de l’opération suivante ?**  
```scala
val rddsortByKey = rddreduceByKey.sortByKey()
rddsortByKey.collect()
```  
☐ Trie les éléments par valeur en ordre croissant.  
☐ Trie les éléments par clé en ordre croissant.  
☐ Trie les éléments par valeur en ordre décroissant.  
☐ Trie les éléments par clé en ordre décroissant.  

---

### **7. Quelle différence principale y a-t-il entre join et fullOuterJoin dans Spark ?**  
☐ join retourne uniquement les correspondances entre deux RDDs, tandis que fullOuterJoin garde toutes les valeurs, même sans correspondance.  
☐ fullOuterJoin est plus rapide que join car il inclut toutes les valeurs.  
☐ join fonctionne uniquement avec des RDDs de taille identique.  
☐ fullOuterJoin supprime automatiquement les valeurs nulles après la jointure.  

---

### **8. Quelle est la bonne manière de convertir un DataFrame en RDD ?**  
☐ df.toRDD()  
☐ df.rdd  
☐ df.convertToRDD()  
☐ df.map(x => x.toRDD())  

---

### **9. Quelle est la principale différence entre takeOrdered(3) et top(3) ?**  
☐ takeOrdered(3) retourne les 3 plus petits éléments, tandis que top(3) retourne les 3 plus grands.  
☐ top(3) est plus rapide car il ne trie pas les données.  
☐ takeOrdered(3) retourne les 3 derniers éléments du RDD.  
☐ Il n’y a aucune différence, les deux retournent les mêmes éléments.  

---

### **10. Pourquoi utiliser Spark SQL au lieu des RDDs bruts pour effectuer des requêtes sur de gros volumes de données ?**  
☐ Parce que Spark SQL optimise les requêtes avec le moteur Catalyst et le format colonne Parquet.  
☐ Parce que les RDDs ne peuvent pas être utilisés pour stocker des données volumineuses.  
☐ Parce que Spark SQL est le seul moyen d’exécuter des requêtes SQL sur Spark.  
☐ Parce que les RDDs ne supportent pas de transformations avancées comme map et reduceByKey.  

---

### **Consignes Finales**  
- Cochez **une seule réponse par question**.  
- Justifiez vos réponses si nécessaire.  
- Une réponse incorrecte sans justification peut entraîner une pénalité.  

Bonne chance !
