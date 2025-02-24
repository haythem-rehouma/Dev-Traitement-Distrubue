# EVALUATION: Analyse de Données avec Apache Spark SQL

Ce document décrit les étapes et les exigences pour l'évaluation formative axée sur l'analyse de données financières et de revenus à l'aide d'Apache Spark SQL. L'évaluation est divisée en deux parties principales, traitant respectivement des données boursières et des données de revenu.

## Configuration Requise

- Apache Spark 2.x ou version ultérieure.
- Scala 2.11 ou version ultérieure.
- Un environnement de développement intégré (IDE) ou un interpréteur de commande pour exécuter des scripts Scala.

## Partie 1: Analyse des Données Boursières (`AAPL.csv`)

### Objectif

Effectuer une série de requêtes SQL sur un ensemble de données contenant les transactions boursières de l'entreprise Apple. Utilisez les données fournies dans le fichier `AAPL.csv`.

### Requêtes à Exécuter

1. **Dates de Transaction, Ouverture et Fermeture:**  
   Affichez la liste des dates de transaction avec les valeurs d'ouverture et de fermeture de l'action Apple.

2. **Différence Entre Fermeture et Ouverture:**  
   Pour chaque jour de transaction, calculez la différence entre le prix de fermeture et le prix d'ouverture.

3. **Max et Min des Volumes:**  
   Identifiez les volumes maximum et minimum échangés durant toute la période couverte par le dataset.

4. **Moyenne des Valeurs d'Ouverture par Année:**  
   Calculez la moyenne des valeurs d'ouverture des actions Apple, regroupées par année.

5. **Somme des Volumes par Mois:**  
   Déterminez la somme totale des volumes échangés, regroupée par mois.

### Étapes Suggérées

- Lire le fichier `AAPL.csv` comme un DataFrame Spark SQL.
- Utiliser les fonctions de Spark SQL pour effectuer les transformations et les agrégations nécessaires.
- Afficher les résultats de chaque requête avec la méthode `.show()`.

## Partie 2: Analyse des Données de Revenu (`income.csv`)

### Objectif

Choisissez un traitement analytique à effectuer sur les données de revenu, en utilisant le fichier `income.csv` comme exemple. Créez et testez votre code Spark SQL pour réaliser cette analyse.

### Suggestions de Traitement

- Calculez la distribution des revenus par région ou par catégorie d'emploi.
- Identifiez les tendances de revenu en fonction de l'éducation ou de l'âge.
- Analysez la corrélation entre les heures travaillées par semaine et les tranches de revenu.

### Étapes Suggérées

- Définir clairement l'objectif de votre analyse.
- Charger le fichier `income.csv` dans un DataFrame Spark SQL.
- Utiliser les opérations de filtrage, de regroupement et d'agrégation pour obtenir les résultats souhaités.
- Commenter chaque étape du code pour clarifier la logique et les opérations réalisées.

## Références

- **Données:** Accédez aux datasets via [GitHub - ScalaProject](https://github.com/brahmbhattspandan/ScalaProject/tree/master/data/stocks) pour télécharger les fichiers `AAPL.csv` et `income.csv`.
- **Tutoriel sur l'Analyse de Stock Market avec Spark:** Pour plus d'informations et d'inspiration, regardez [cette vidéo sur YouTube](https://www.youtube.com/watch?v=Mxw6QZk1CMY) à partir de la minute 35.

## Conseils pour la Réalisation

- Assurez-vous de bien comprendre les fonctions de Spark SQL avant de commencer l'évaluation.
- Testez votre code étape par étape pour vous assurer qu'il fonctionne correctement et produit les résultats attendus.
- Utilisez les ressources en ligne et la documentation officielle de Spark pour résoudre les problèmes ou pour rechercher des fonctions spécifiques.

Ce document vous guide à travers les étapes de réalisation de l'évaluation, en mettant l'accent sur la manipulation des données et l'analyse à l'aide de Spark SQL. Bonne chance!
