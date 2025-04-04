# **Description complète du Pipeline 3**

- Le pipeline 3 illustre une architecture typique de traitement de données structurée en plusieurs phases, allant de l’ingestion brute à la visualisation finale. 
- Il est conçu pour gérer des volumes importants de données issues de différentes bases de données, et appliquer une transformation rigoureuse avant l'analyse.



# **1. Ingestion des données brutes (Raw Ingestion)**

### **Sources**
Les données proviennent de **plusieurs bases de données relationnelles** ou opérationnelles. Ces sources peuvent inclure :
- Des bases internes d’un système d'information (ex : PostgreSQL, MySQL, Oracle)
- Des exports périodiques de données métiers
- Des fichiers générés par des applications (logs, fichiers plats, exports CSV, etc.)

### **Destination**
Ces données sont stockées dans **Amazon S3**, dans un bucket nommé **RAW**. À ce stade :
- Aucune transformation n’a encore été appliquée
- Les fichiers sont conservés dans leur format d’origine (CSV, JSON, Parquet, etc.)
- Ce dépôt constitue une **zone de rétention brute (raw zone)**, essentielle pour assurer la traçabilité et la reproductibilité des traitements futurs


# **2. Transformation des données (Data Transformation)**

La transformation des données s’effectue en plusieurs étapes successives, chacune stockée dans un emplacement distinct.

### **Étape 1 : Traitement initial**
- **Service utilisé : AWS Lambda**
- Utilisé pour effectuer des traitements simples, rapides et automatisés dès la détection de nouveaux fichiers (déclenchement par événements S3).
- Exemples d’opérations :
  - Normalisation de formats de dates
  - Validation de structure JSON
  - Élimination de fichiers corrompus

### **Étape 2 : Transformation avancée**
- **Service utilisé : AWS Glue**
- Glue est un service ETL serverless qui permet de transformer les données en profondeur :
  - Nettoyage de colonnes
  - Jointure entre jeux de données
  - Enrichissement avec des référentiels externes
  - Conversion en format analytique (ex. Parquet)
- Les résultats sont enregistrés dans un bucket S3 nommé **TRANSFORMED**

### **Étape 3 : Enrichissement métier**
- **Service utilisé : AWS Step Functions**
- Ce service orchestre les différentes étapes de transformation, permettant une **exécution séquentielle et conditionnelle** :
  - Vérification de la qualité des données
  - Croisement avec des données de référence (clients, produits, etc.)
  - Calculs métiers (marges, taux, scores, KPI, etc.)
- À l’issue de cette phase, les données sont écrites dans un troisième bucket S3 nommé **ENRICHED**, qui constitue la **zone de données prêtes à l’analyse**.



# **3. Stockage analytique (Data Warehouse)**

### **Service utilisé : Amazon Redshift**
- Les données enrichies, désormais de haute qualité et prêtes à l’emploi, sont chargées dans **Amazon Redshift**, un entrepôt de données cloud optimisé pour l’analyse à grande échelle.
- Cette étape permet de :
  - Structurer les données en tables relationnelles
  - Appliquer des index pour accélérer les requêtes
  - Préparer des vues matérialisées ou des tables agrégées
- Le chargement peut se faire via des scripts Glue ou des pipelines automatisés (par exemple via AWS Data Pipeline ou Step Functions)


# **4. Visualisation et analyse (Business Intelligence)**

### **Service (implicite) : Amazon QuickSight**
- Bien que le service ne soit pas nommé ici, la dernière étape du pipeline consiste à une visualisation analytique**.
- **Amazon QuickSight** est généralement utilisé ici pour :
  - Construire des tableaux de bord dynamiques
  - Mettre en place des filtres interactifs, des indicateurs clés de performance (KPI)
  - Fournir un accès sécurisé aux utilisateurs métiers via le web



## **Caractéristiques techniques du pipeline**

| Élément | Description |
|--------|-------------|
| **Nature du traitement** | Traitement batch par étapes successives |
| **Orchestration** | Gérée par **Step Functions** |
| **Niveau de gouvernance** | Élevé (chaque étape est tracée et isolée dans un bucket spécifique) |
| **Modularité** | Architecture modulaire permettant l’ajout ou modification d’étapes sans impacter le reste |
| **Scalabilité** | Entièrement scalable via les services serverless (Glue, Lambda, Redshift) |
| **Traçabilité** | Conservation des fichiers à chaque étape (RAW, TRANSFORMED, ENRICHED) permettant audit et reproductibilité |



# **Avantages de ce pipeline**

- Permet un **contrôle rigoureux de la qualité des données**
- Adapté aux **traitements complexes avec règles métier spécifiques**
- Favorise la **séparation des responsabilités** (stockage, transformation, enrichissement, entrepôt, visualisation)
- Architecture extensible pour intégrer des validations, logs, métriques, ou intégrations tierces
