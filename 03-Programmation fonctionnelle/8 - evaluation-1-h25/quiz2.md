# **EXAMEN - PARTIE 2**  

**Big Data & Apache Spark**  

> **Instructions générales :**  
> - Cet examen est **exclusivement basé sur des études de cas réels**.  
> - **Chaque réponse doit être rigoureusement justifiée**.  
> - **Aucune réponse superficielle ne sera acceptée**.  
> - **L’objectif est d’évaluer votre capacité à prendre des décisions technologiques en conditions réelles**.  

---

# **PARTIE 1 - PYSPARK OU SCALA ?**  

---

## **ÉTUDE DE CAS 1 : STREAMING TEMPS RÉEL & CAPTEURS IOT**  
### **Contexte**  
Une entreprise industrielle doit surveiller en **temps réel** ses équipements via des **capteurs IoT** qui transmettent leurs données en continu via **Kafka** vers un cluster **Apache Spark**.  

L’objectif est de :  
1. **Analyser instantanément les anomalies.**  
2. **Déclencher des alertes en moins de 2 secondes.**  
3. **Stocker ces données pour des analyses prédictives ultérieures.**  

### **Contraintes**  
- **Flux de données volumineux et continu.**  
- **Toute latence excessive compromet la réactivité du système.**  
- **L’optimisation du streaming en temps réel est critique.**  

### **Questions**  
1. **Quel langage adoptez-vous entre PySpark et Scala ?**  
2. **Justifiez rigoureusement votre choix.**  
3. **Expliquez pourquoi l’autre option est inadaptée.**  

---

## **ÉTUDE DE CAS 2 : MIGRATION HADOOP/HIVE VERS APACHE SPARK**  
### **Contexte**  
Une entreprise migre son pipeline de traitement batch de **Hadoop et Hive** vers **Apache Spark** pour améliorer les performances et réduire les coûts.  

Les tâches concernées sont :  
- **Extraction et transformation de données SQL, NoSQL et JSON.**  
- **Nettoyage et agrégation avant stockage en Data Warehouse.**  
- **Optimisation des performances globales.**  

### **Contraintes**  
- **Les pipelines doivent être robustes et maintenables.**  
- **Requêtes SQL complexes impliquées.**  
- **Spark est déjà en production.**  

### **Questions**  
1. **Quel langage recommandez-vous entre PySpark et Scala ?**  
2. **Analysez l’impact sur la performance, la maintenance et l’intégration.**  

---

## **ÉTUDE DE CAS 3 : MOTEUR DE RECOMMANDATION & MACHINE LEARNING**  
### **Contexte**  
Une plateforme de streaming vidéo met en place un **moteur de recommandations** basé sur les habitudes de visionnage.  

Ce pipeline inclut :  
1. **Prétraitement de logs massifs.**  
2. **Entraînement d’un modèle via Spark MLlib.**  
3. **Déploiement en production pour recommandations en temps réel.**  

### **Contraintes**  
- **L’équipe Data Science utilise TensorFlow et Scikit-learn.**  
- **Le modèle doit être entraîné avec Spark MLlib.**  
- **L’architecture doit être scalable.**  

### **Questions**  
1. **Quel langage choisissez-vous et pourquoi ?**  
2. **Quels facteurs garantissent la performance du modèle ML en production ?**  

---

## **ÉTUDE DE CAS 4 : DÉTECTION DE FRAUDE EN TEMPS RÉEL**  
### **Contexte**  
Une entreprise financière analyse des **millions de transactions bancaires** pour détecter la fraude en **temps réel (< 1 seconde de latence)**.  

Le pipeline doit :  
- **Exécuter des requêtes analytiques complexes.**  
- **Générer des alertes immédiates en cas d’anomalie.**  
- **Stocker les données en Parquet sur Spark.**  

### **Questions**  
1. **PySpark ou Scala : quel choix technologique pour garantir la performance et la scalabilité ?**  
2. **Justifiez l’impact sur les requêtes SQL complexes et le stockage Parquet.**  

---

## **ÉTUDE DE CAS 5 : INDUSTRIALISATION D’UN DATA LAKE**  
### **Contexte**  
Une entreprise mondiale centralise ses données RH, ventes et finance dans un **Data Lake**.  

### **Contraintes**  
- **Pétaoctets de données à traiter.**  
- **Pipelines de transformation distribués.**  
- **Déploiement hybride (AWS + On-Premise).**  

### **Questions**  
1. **Quel langage adoptez-vous pour concevoir le Data Lake et pourquoi ?**  
2. **Impact sur la scalabilité, la maintenabilité et l’industrialisation des pipelines.**  

---

# **PARTIE 2 - RDD, DATAFRAME OU DATASET ?**  

## **Consignes**  
- **Cochez une seule ou plusieurs options par étude de cas.**  
- **Justifiez clairement votre choix.**  
- **Expliquez pourquoi vous éliminez les autres options.**  
- **Une réponse sans justification sera considérée comme incomplète.**  

---

## **ÉTUDE DE CAS 1 : ANALYSE DE LOGS & DÉTECTION D’ANOMALIES**  
### **Contexte**  
Une entreprise de cybersécurité traite **des téraoctets de logs** contenant :  
- **Timestamp, Adresse IP, URL, Code HTTP**  

Objectif : **détecter des IP suspectes et analyser les erreurs HTTP 500.**  

### **Contraintes**  
- **Données en CSV sur HDFS.**  
- **Transformations lourdes (filtrage, comptage, regroupement).**  

✅ **Quel choix recommandez-vous ?**  
☐ **RDD**  
☐ **DataFrame**  
☐ **Dataset**  

---

## **ÉTUDE DE CAS 2 : DÉTECTION DE FRAUDE BANCAIRE**  
### **Contexte**  
Une banque analyse **des millions de transactions** pour détecter des fraudes.  

### **Contraintes**  
- **Données stockées en Parquet.**  
- **Requêtes analytiques complexes et jointures massives.**  

✅ **Quel choix recommandez-vous ?**  
☐ **RDD**  
☐ **DataFrame**  
☐ **Dataset**  

---

## **ÉTUDE DE CAS 3 : SYSTÈME DE RECOMMANDATION E-COMMERCE**  
### **Contexte**  
Un site e-commerce analyse les **co-occurrences d’achats** pour recommander des produits.  

### **Contraintes**  
- **Données stockées en JSON sur HDFS.**  
- **Modèle de clustering et apprentissage.**  

✅ **Quel choix recommandez-vous ?**  
☐ **RDD**  
☐ **DataFrame**  
☐ **Dataset**  

---

## **ÉTUDE DE CAS 4 : MONITORING EN TEMPS RÉEL D’UNE INFRASTRUCTURE CLOUD**  
### **Contexte**  
Une entreprise surveille **des milliers de machines virtuelles** en temps réel.  

### **Contraintes**  
- **Données en streaming via Kafka.**  
- **Calculs en temps réel.**  

✅ **Quel choix recommandez-vous ?**  
☐ **RDD**  
☐ **DataFrame**  
☐ **Dataset**  

---

## **ÉTUDE DE CAS 5 : ANALYSE DES TRANSACTIONS D’UNE BLOCKCHAIN**  
### **Contexte**  
Une entreprise surveille **+100 millions de transactions blockchain** par jour.  

### **Contraintes**  
- **Données JSON sur un Data Lake distribué.**  
- **Jointures complexes avec bases d’adresses suspectes.**  

✅ **Quel choix recommandez-vous ?**  
☐ **RDD**  
☐ **DataFrame**  
☐ **Dataset**  

---

## **FIN DE L’EXAMEN**  
- **Toute réponse non argumentée sera considérée comme incorrecte.**  
- **Un raisonnement structuré et précis est exigé.**  

