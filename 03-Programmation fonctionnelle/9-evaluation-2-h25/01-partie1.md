
# Partie #1

## EXERCICE #1
Décrivez les différences fondamentales entre les transformations et les actions dans le contexte de Spark. 

## EXERCICE #2
Comparez les opérations `flatMap` et `map` dans Spark en mettant en avant leurs usages respectifs dans le traitement des données. Quelles sont les principales différences en termes de résultat produit ?

## EXERCICE #3
Dans le contexte de l'exemple de WordCount, pourquoi est-il préférable d'utiliser `reduceByKey` plutôt que `reduce` ?  

### 3.1
Analysez la distinction entre les transformations et les actions spécifiquement dans l'exemple de WordCount, en vous appuyant sur le PDF fourni.

### 3.2
Expliquez les différences entre les opérations `flatMap` et `map` dans le contexte du calcul de la fréquence des mots (WordCount).

### 3.3
Quels sont les avantages d'utiliser `reduceByKey` par rapport à `reduce` dans l'exemple de WordCount ? Justifiez votre réponse en termes de performance et de traitement distribué.

## EXERCICE #4
Quelle est la terminologie utilisée pour décrire la pratique d'enchaîner plusieurs appels de fonctions ou de méthodes, où chaque appel prend en entrée le résultat de l'appel précédent, en particulier en programmation fonctionnelle ? Comment cette approche est-elle exploitée dans les frameworks tels qu'Apache Spark pour la manipulation des RDDs et des DataFrames ?

## EXERCICE #5
En Scala, que permet l'import de `scala.util.Sorting._` ? Donnez un exemple d'utilisation pour illustrer vos propos.

## EXERCICE #6
Comment Spark traite-t-il le calcul sur plusieurs nœuds ? Comparez les opérations `fold` et `reduce` en utilisant l'exemple de l'introduction à Spark. Confirmez ou réfutez l'assertion suivante :
- L'opération `reduce` regroupe toutes les données sur un seul nœud avant de calculer, ce qui peut causer des problèmes de performance pour des volumes importants.  
- L'opération `fold` traite les données de manière distribuée, avec un calcul parallèle sur chaque nœud, limitant ainsi les mouvements de données.

## EXERCICE #7
**Analyse de Fréquence des Mots avec Spark RDD**

**Objectif :** Mettre en pratique les opérations sur les RDD pour analyser la fréquence des mots dans différents ensembles de données.

**Données :**
- Ensemble 1 : `List("toto tata titi toto tutu tata")`
- Ensemble 2 : `List("toto tata", "titi toto", "tutu tata")`
- Ensemble 3 : `List("toto tata titi", "toto tutu tata")`

**Instructions :**
1. **Initialisation de RDD :** Créez un RDD pour chaque ensemble de données en utilisant `sc.parallelize`.
2. **Transformation et Action :**
   - **Étape A :** Appliquez `flatMap` pour extraire les mots de chaque ensemble.
   - **Étape B :** Utilisez `map` pour générer des paires clé-valeur (mot, 1).
   - **Étape C :** Agrégez les résultats avec `reduceByKey` pour calculer les fréquences.
3. **Résultats :** Présentez les paires clé-valeur résultantes pour chaque ensemble.

**Questions :**
- Quelle est l'influence de la structure des données initiales sur les résultats obtenus ?
- Comparez les résultats entre les trois ensembles et analysez les différences.

## EXERCICE #8

**Question 1 :** Quels sont les points communs et les différences entre les opérations `cogroup` et `join` sur des RDDs dans Spark ? 

**Question 2 :** En quoi l'utilisation de `fullOuterJoin` diffère-t-elle de `cogroup` en termes de résultat retourné ? 

---

**Remarque :** Assurez-vous que Spark est bien configuré et que SparkContext est initialisé avant de commencer l'exercice #7. Utilisez les commandes RDD pour manipuler les données comme indiqué. 

**Références :**
1. [CoGroup Partie 1](https://github.com/hrhouma/beginingSpark-part1/blob/main/CoGroupePartie1.md)  
2. [CoGroup Partie 2](https://github.com/hrhouma/beginingSpark-part1/blob/main/CoGroupePartie2.md)
