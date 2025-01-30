# **Table des Matières**

## **Pratique 1 : Opérations Fondamentales avec Apache Spark**
**Objectif** : Comprendre les fonctionnalités de base d'Apache Spark, notamment la création et la manipulation des **RDD** (Resilient Distributed Datasets).

## **Pratique 2 : Manipulation Avancée des RDD**
**Objectif** : Explorer des opérations avancées sur les RDD, incluant :
- **Échantillonnage des données** (`sample`)
- **Opérations d’ensemble** (`union`, `intersection`)
- **Fusion et jointures avancées** (`cogroup`, `fullOuterJoin`)

## **Pratique 3 : De RDD à DataFrame et Spark SQL**
**Objectif** : Illustrer le processus de conversion des **RDD** en **DataFrames**, puis démontrer comment interroger et manipuler ces **DataFrames** à l’aide de **Spark SQL**.

## **Pratique 4 : Comparaison entre RDD, DataFrame et Dataset & Manipulation de Fichiers JSON/CSV**
**Objectif** :
- Analyser les différences fondamentales entre **RDD**, **DataFrame** et **Dataset**, en expliquant leurs cas d’utilisation respectifs.
- Charger, manipuler et interroger des fichiers **JSON** et **CSV** avec **Apache Spark**.

## **Pratique 5 : Transformation d'un RDD en DataFrame puis en Dataset**
**Objectif** : Décrire, étape par étape, la conversion d’un **RDD** en **DataFrame**, puis en **Dataset**, en fournissant des implémentations en **Scala** et **PySpark**.

## **Pratique 6 : Introduction à Apache Spark Streaming**
**Objectif** : Découvrir **Spark Streaming** pour le traitement des flux de données en temps réel. Cette pratique couvre :
- La configuration d’un **contexte Spark Streaming**.
- L’écoute des données sur un **port spécifique**.
- La transformation et l’analyse des données en temps réel (**compter les occurrences de mots**).
- L'utilisation de **Netcat** pour tester un flux de données interactif.

## **Pratique 7 : Apache Spark Streaming – Analyse de Sentiment en Temps Réel**
**Objectif** : Approfondir l'utilisation de **Spark Streaming** en intégrant des techniques d'**intelligence artificielle** pour analyser les sentiments en temps réel à partir de **tweets**.  
Ce tutoriel aborde :
- La configuration d’un **pipeline Spark Streaming**.
- L’implémentation d’un **modèle de régression logistique** pour l’analyse de sentiment.
- Le streaming de données en temps réel avec **PySpark**.
- La visualisation des résultats en temps réel.
