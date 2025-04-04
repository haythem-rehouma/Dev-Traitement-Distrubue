# **Analyse complète des trois pipelines de traitement de données AWS**

*Comparez les 3 pipelines* 

###  Pipeline 1
![pipeline1](https://github.com/user-attachments/assets/84ed9186-a77d-40a4-b97f-13e939596ad4)

### Pipeline 2
![pipeline2](https://github.com/user-attachments/assets/6aeb023f-50a7-44c3-b028-05b98817ab02)

### Pipeline 3
![pipeline3](https://github.com/user-attachments/assets/f82ef59e-a43a-4e2f-849a-f2c0eda01016)

## **I. Description détaillée de chaque pipeline**



# **Pipeline 1 – Architecture centrée sur le data lake avec traitement serverless**

#### **Étapes du pipeline :**

1. **Ingestion (Acquire)**  
   - Les données d’un client sont envoyées et stockées dans **Amazon S3** sous forme brute.
   - S3 agit comme une couche d’acquisition durable et faiblement coûteuse.

2. **Découverte et catalogage (Stage)**  
   - Un **AWS Glue Crawler** est utilisé pour parcourir les fichiers bruts et découvrir automatiquement le schéma (colonnes, types, structure).
   - Le résultat est enregistré dans le **Glue Data Catalog**, une base de métadonnées centrale.

3. **Transformation (Process)**  
   - Le service **AWS Glue** effectue les opérations ETL (Extract, Transform, Load).
   - Les données transformées sont réécrites dans un second emplacement dans Amazon S3.

4. **Deuxième découverte (Store)**  
   - Un second Glue Crawler est utilisé pour cataloguer les données transformées.
   - Ceci permet de préparer les données pour l’analyse.

5. **Analyse (Analyze)**  
   - **Amazon Athena** est utilisé pour interroger directement les données dans S3 en SQL.
   - Athena utilise le schéma stocké dans le Glue Data Catalog.

6. **Visualisation (Serve)**  
   - Les résultats d’Athena sont connectés à **Amazon QuickSight** pour créer des tableaux de bord.

7. **Consommation (Consume)**  
   - Les utilisateurs métier accèdent aux tableaux de bord pour la prise de décision.



# **Pipeline 2 – Architecture orientée streaming avec ingestion temps réel**

#### **Étapes du pipeline :**

1. **Ingestion (Data sources)**  
   - Des événements générés par des systèmes serveur (logs, événements utilisateur) sont capturés en temps réel.

2. **Streaming (Kinesis Firehose)**  
   - **AWS Kinesis Data Firehose** assure une ingestion continue des événements.
   - Il peut également effectuer des transformations légères (compression, formatage) avant de livrer les données.

3. **Stockage analytique (Data warehouse)**  
   - Les données sont automatiquement chargées dans **Amazon Redshift**, sans passer par une couche intermédiaire.

4. **Analyse et visualisation (Analytics)**  
   - **Amazon QuickSight** est connecté à Redshift pour offrir une interface d’exploration et de visualisation des données.



# **Pipeline 3 – Architecture multi-étapes avec enrichissement structuré**

#### **Étapes du pipeline :**

1. **Ingestion des données brutes**  
   - Données provenant de bases relationnelles ou systèmes métiers.
   - Stockées dans un bucket S3 nommé **RAW**.

2. **Transformation et enrichissement**  
   - Plusieurs services AWS sont utilisés :
     - **AWS Lambda** : pour des opérations simples, rapides, sans infrastructure.
     - **AWS Glue** : pour des transformations ETL complexes.
     - **AWS Step Functions** : pour orchestrer et automatiser les séquences de traitement.
   - Les données passent successivement par les buckets **TRANSFORMED** puis **ENRICHED**.

3. **Chargement dans un entrepôt de données**  
   - Les données enrichies sont chargées dans **Amazon Redshift** pour des analyses complexes.

4. **Visualisation**  
   - Un service BI (probablement **Amazon QuickSight**) exploite les données stockées dans Redshift pour produire des tableaux de bord.



## **II. Comparaison entre les trois pipelines**

| **Critère**                 | **Pipeline 1**                                      | **Pipeline 2**                                     | **Pipeline 3**                                                  |
|-----------------------------|-----------------------------------------------------|----------------------------------------------------|------------------------------------------------------------------|
| **Source des données**      | Fichiers fournis par le client                      | Événements serveur en temps réel                   | Bases de données relationnelles ou systèmes métiers              |
| **Mode d’ingestion**        | Dépôt manuel ou automatique dans S3                | Streaming en continu                               | Extraction par processus batch                                 |
| **Stockage intermédiaire**  | Amazon S3 (raw, transformed)                        | Aucun, ingestion directe dans Redshift             | Amazon S3 (raw → transformed → enriched)                        |
| **Service de transformation** | AWS Glue                                           | Kinesis Firehose (simple)                          | Lambda, Glue, orchestré par Step Functions                      |
| **Entrepôt de données**     | Analyse via Athena (pas de data warehouse classique) | Amazon Redshift                                   | Amazon Redshift                                                 |
| **Service d’analyse**       | Athena (requêtes SQL sur S3)                        | Redshift                                           | Redshift                                                        |
| **Visualisation**           | Amazon QuickSight                                   | Amazon QuickSight                                  | Amazon QuickSight (ou outil similaire)                          |
| **Approche technique**      | Data lake et architecture serverless               | Architecture temps réel simplifiée                | ETL orchestré, pipeline structuré multi-étapes                  |
| **Gestion des métadonnées** | Glue Data Catalog                                   | Non utilisée                                       | Non précisée, possible usage implicite                         |



## **III. Rôle et importance des services AWS dans chaque pipeline**

### **Amazon S3**
- Support de stockage évolutif utilisé dans les pipelines 1 et 3 pour stocker les données brutes, transformées, et enrichies.
- Permet un découplage entre ingestion, transformation et analyse.

### **AWS Glue & Glue Crawler**
- Moteur ETL serverless essentiel dans les pipelines 1 et 3.
- Le Glue Crawler permet de construire automatiquement le schéma des données, indispensable pour l’analyse par Athena (pipeline 1).

### **Amazon Athena**
- Moteur de requête SQL serverless utilisé dans le pipeline 1 pour interroger des données stockées dans S3 sans nécessiter d’entrepôt de données.

### **AWS Kinesis Firehose**
- Utilisé dans le pipeline 2 pour une ingestion rapide et continue des flux de données.
- Idéal pour des besoins en temps réel sans gestion d’infrastructure complexe.

### **AWS Lambda**
- Utilisé dans le pipeline 3 pour les étapes de transformation légères ou déclenchement d’événements.
- Permet un traitement sans infrastructure, élastique, et réactif.

### **AWS Step Functions**
- Orchestration utilisée dans le pipeline 3.
- Assure la coordination automatique des étapes de traitement (exécuter Lambda, lancer Glue, transférer des fichiers, etc.).

### **Amazon Redshift**
- Entrepôt de données analytique à haute performance, présent dans les pipelines 2 et 3.
- Permet d’exécuter des requêtes analytiques sur de grandes quantités de données structurées.

### **Amazon QuickSight**
- Outil de visualisation utilisé dans les trois pipelines.
- Fournit des tableaux de bord interactifs accessibles aux utilisateurs métier.



## **IV. Cas d’usage recommandé pour chaque pipeline**

### **Pipeline 1 – Analyse exploratoire de données structurées ou semi-structurées**
- **Exemple** : Analyse des fichiers journaux, données client, données IoT.
- **Justification** : La flexibilité de S3 combinée à Athena permet une exploration ad hoc sans charger dans un entrepôt.

### **Pipeline 2 – Supervision et reporting en temps réel**
- **Exemple** : Monitoring des erreurs serveur, analyse de trafic web ou mobile en temps réel.
- **Justification** : L’ingestion en streaming via Firehose vers Redshift permet une analyse rapide des événements.

### **Pipeline 3 – Traitement structuré avec enrichissement métier**
- **Exemple** : Traitement de données financières, pipeline de scoring client, conformité réglementaire.
- **Justification** : La combinaison de plusieurs étapes de transformation orchestrées permet un contrôle précis et une traçabilité des traitements appliqués.
