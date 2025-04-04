# **Pipeline 2 – Architecture et Étapes Clés**

![image](https://github.com/user-attachments/assets/1d390e9e-2c18-463f-9a23-709205c89a30)


Ce pipeline suit un chemin plus direct, composé de quatre étapes principales : **Data sources**, **Streaming**, **Data warehouse**, et **Analytics**. Il est optimisé pour le traitement continu des données.

# **1. Data Sources – Sources de données**
La source de données est constituée des événements générés en temps réel par un serveur back-end.

- Ces données peuvent provenir d'applications web, mobiles ou de microservices.
- Le format typique des événements est JSON ou CSV, contenant des informations sur les actions utilisateur, les erreurs applicatives, les transactions, etc.
- Les événements sont produits de manière continue et doivent être collectés et transmis sans délai.

# **2. Streaming – Ingestion des données en flux**
Les données sont envoyées vers **Amazon Kinesis Data Firehose**, un service managé permettant de collecter, traiter et charger des flux de données en continu.

- Kinesis Firehose agit comme une passerelle entre les producteurs de données et les systèmes d’analyse ou de stockage.
- Il peut effectuer des transformations légères (compression, conversion de format) avant de livrer les données.
- Il garantit une ingestion rapide, sans nécessiter de gestion d’infrastructure.

# **3. Data Warehouse – Entrepôt de données**
Les données collectées par Kinesis Firehose sont automatiquement livrées dans **Amazon Redshift**, un entrepôt de données massivement parallèle.

- Redshift permet de stocker de grandes quantités de données structurées.
- Il est optimisé pour les requêtes analytiques complexes et les traitements en batch ou quasi-temps réel.
- Les données sont disponibles presque immédiatement pour l’analyse après leur ingestion.

# **4. Analytics – Visualisation et analyse**
Une fois les données intégrées dans Redshift, elles peuvent être analysées à l’aide de **Amazon QuickSight**, un service de business intelligence.

- QuickSight se connecte directement à Redshift pour créer des rapports interactifs, des dashboards et des visualisations personnalisées.
- Il permet aux utilisateurs métiers d’explorer les données sans avoir besoin de maîtriser le SQL.
- Cette étape offre un support décisionnel basé sur les données actualisées en continu.


# **Résumé du pipeline**

| Étape          | Service AWS             | Rôle principal                                                   |
|----------------|--------------------------|------------------------------------------------------------------|
| Data sources   | Événements serveur       | Génération continue des données à surveiller ou analyser         |
| Streaming      | AWS Kinesis Firehose     | Ingestion en temps réel, transformation légère, transfert vers Redshift |
| Data warehouse | Amazon Redshift          | Stockage structuré et performant des données                     |
| Analytics      | Amazon QuickSight        | Visualisation, analyse et reporting à partir des données Redshift|



### **Particularités de ce pipeline**
- Orienté **temps réel** ou **quasi temps réel**
- Optimisé pour des cas d’usage à haute fréquence (ex. logs applicatifs, événements système)
- Utilise peu de composants intermédiaires, ce qui favorise la rapidité et la simplicité
- Repose sur un entrepôt de données (Redshift) plutôt que sur un lac de données (S3)

