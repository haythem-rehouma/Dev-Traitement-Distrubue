# **EXAMEN MI-SESSION – PARTIE 3**  
## **Analyse des Données Boursières avec Spark SQL et Scala**  

### **Nom :** .............................................................................  
### **Matricule :** .............................................................................  
### **Date :** .............................................................................  

---

## **1. Contexte**  

Vous disposez d’un fichier nommé **AHPI.csv** contenant l’**Indice des prix de l'immobilier résidentiel (AHPI)**.

Ce fichier comprend les colonnes suivantes :  

- **Date** : Date de la transaction  
- **Open** : Prix d’ouverture  
- **High** : Prix le plus haut de la journée  
- **Low** : Prix le plus bas de la journée  
- **Close** : Prix de clôture  
- **Volume** : Volume des échanges  
- **Adj Close** : Prix de clôture ajusté  

Votre mission est d’**analyser ces données en utilisant Spark SQL ou Scala (DataFrame API)** pour répondre aux questions suivantes.

---

## **2. Instructions**  

- **Chaque question doit être accompagnée de la requête SQL correspondante ou du code Scala/DataFrame.**  
- **Un code bien structuré et commenté sera valorisé.**  
- **Les résultats doivent être justifiés et interprétés.**  
- **Répondez directement dans les espaces dédiés sous chaque question.**  

---

## **3. Questions**  

### **1. Transactions Quotidiennes**
📌 **Objectif :** Afficher la **date**, le **prix d’ouverture** et le **prix de clôture** de l’action pour chaque jour.  

✍ **Réponse :**  
_(Insérez ici votre code SQL ou Scala)_

```
..............................................................................................................
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
_(Expliquez brièvement ce que vos résultats indiquent)_

```
..............................................................................................................
..............................................................................................................
```

---

### **2. Volatilité Journalière**
📌 **Objectif :** Calculer la **différence entre le prix de clôture et d’ouverture** pour chaque jour afin d’identifier la **volatilité journalière**.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **3. Extrêmes de Volume**
📌 **Objectif :** Trouver les **valeurs maximales et minimales** des volumes échangés sur la période analysée.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **4. Moyenne Annuelle des Valeurs d’Ouverture**
📌 **Objectif :** Calculer la **moyenne des prix d’ouverture** de l’action par **année**.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **5. Total des Volumes par Mois**
📌 **Objectif :** Déterminer la **somme des volumes échangés** par **mois**, en incluant **l’année** dans le résultat.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **6. Plus Grand Écart Quotidien**
📌 **Objectif :** Identifier **la date où l’écart entre le prix le plus haut et le plus bas était le plus grand**.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **7. Moyenne Mobile sur 7 Jours**
📌 **Objectif :** Calculer la **moyenne mobile** du **prix de clôture** sur une fenêtre glissante de **7 jours**.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **8. Volume Moyen Quotidien**
📌 **Objectif :** Calculer le **volume moyen des échanges** par jour sur l’ensemble de la période.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

### **9. Nombre de Jours de Hausse**
📌 **Objectif :** Déterminer **le nombre de jours où le prix de clôture était supérieur au prix d’ouverture**, indiquant une **journée de hausse**.  

✍ **Réponse :**  
```
..............................................................................................................
..............................................................................................................
```

📊 **Interprétation :**  
```
..............................................................................................................
..............................................................................................................
```

---

## **4. Critères d’Évaluation**  

📌 **Flexibilité du Langage et de la Technologie**  
- Possibilité d’utiliser **Scala (Spark DataFrame API)** ou **PySpark**.  
- Utilisation encouragée de **Spark SQL** et des **DataFrames API**.  

📌 **Initialisation et Configuration**  
- **Scala :** Initialisation correcte de **SparkContext** et **SparkSession**.  
- **PySpark :** Utilisation correcte de **SparkSession**.  
- **Bonne gestion des configurations Spark.**  

📌 **Exécution des Tâches d’Analyse**  
- **Exactitude des requêtes SQL** : Syntaxe correcte et production des **résultats attendus**.  
- **Utilisation efficace des transformations DataFrame.**  

📌 **Présentation et Clarté**  
- **Lisibilité et documentation du code** : Code bien **structuré**, avec des **commentaires explicatifs**.  
- **Clarté du rapport d’analyse** : Explication des **choix effectués**, **interprétation des résultats**, **formulation de conclusions pertinentes**.  

