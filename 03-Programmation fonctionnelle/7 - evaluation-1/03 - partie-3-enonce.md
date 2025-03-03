# **Examen Mi-Session – Partie 3**  
### **Analyse des Données Boursières avec Spark SQL et Scala**  

## **1. Contexte**  
Vous disposez d’un fichier nommé **AMRB.csv** contenant l’historique des prix de l’action d’**American River Bankshares (AMRB)**. Ce fichier inclut les colonnes suivantes :  

- **Date** : La date de la transaction  
- **Open** : Prix d’ouverture  
- **High** : Prix le plus haut de la journée  
- **Low** : Prix le plus bas de la journée  
- **Close** : Prix de clôture  
- **Volume** : Volume des échanges  
- **Adj Close** : Prix de clôture ajusté  

Votre mission est d’analyser ces données en utilisant **Spark SQL ou Scala (DataFrame API)** pour répondre aux questions suivantes.  

---

## **2. Instructions**  
- Chaque question doit être accompagnée de la requête SQL correspondante ou du code Scala/DataFrame.  
- Le niveau de difficulté est indiqué pour chaque question.  
- Une bonne **compréhension de Spark SQL et des DataFrames** sera évaluée.  

---

## **3. Questions**  

### **1. Transactions Quotidiennes (Facile – 5 points)**  
Affichez la **date**, le **prix d’ouverture** et le **prix de clôture** de l’action pour chaque jour.  

---

### **2. Volatilité Journalière (Facile – 5 points)**  
Calculez la **différence entre le prix de clôture et d’ouverture** pour chaque jour afin d’identifier la volatilité journalière.  

---

### **3. Extrêmes de Volume (Intermédiaire – 7 points)**  
Trouvez les **valeurs maximale et minimale** des volumes échangés.  

---

### **4. Moyenne Annuelle des Valeurs d’Ouverture (Intermédiaire – 7 points)**  
Calculez la **moyenne des prix d’ouverture** de l’action par **année**.  

---

### **5. Total des Volumes par Mois (Intermédiaire – 7 points)**  
Déterminez la **somme des volumes échangés** par **mois**, en incluant l’année dans votre résultat.  

---

### **6. Plus Grand Écart Quotidien (Avancé – 10 points)**  
Identifiez la **date où l’écart entre le prix le plus haut et le plus bas était le plus grand**.  

---

### **7. Moyenne Mobile sur 7 Jours (Avancé – 10 points)**  
Calculez la **moyenne mobile du prix de clôture** sur une fenêtre glissante de **7 jours**.  

---

## **4. Critères d’Évaluation**  

### **Flexibilité du Langage et de la Technologie**  
- Les étudiants peuvent utiliser **Scala (Spark DataFrame API)** ou **PySpark** pour leurs analyses.  
- L'utilisation de **Spark SQL et des DataFrame API** est encouragée.  

### **Initialisation et Configuration**  
- **Scala** : Initialisation correcte de `SparkContext` et `SparkSession`.  
- **PySpark** : Utilisation correcte de `SparkSession`.  
- Une bonne gestion des **configurations Spark** est requise.  

### **Exécution des Tâches d’Analyse**  
- **Exactitude des Requêtes SQL** : Bonne syntaxe et production des résultats attendus.  

### **Présentation et Clarté**  
- **Lisibilité et documentation du code** : Code bien structuré, avec des commentaires explicatifs.  
- **Clarté du rapport d’analyse** : Explication des choix effectués, interprétation des résultats et formulation de conclusions pertinentes.  

### **Instructions Supplémentaires**  
- **Environnement recommandé** :  
  - Utilisation de **Google Colab** pour exécuter PySpark.  
  - Exécution locale avec **Scala et Spark** sur un environnement de développement configuré.  
- **Mise en place de Spark** :  
  - Spark doit être correctement initialisé avec **SparkContext** ou **SparkSession**.  
  - Vérification de la bonne **lecture du fichier CSV** avant toute analyse.  

### **Remarque**  
- **Créativité et innovation** dans l’approche des problèmes seront valorisées.  
- Une exploration **au-delà des exigences minimales** peut conduire à des **points bonus**.  
