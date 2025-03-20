# QUIZ 1

### **Mise en Situation 1 : Analyse des Ventes**

Une entreprise de e-commerce a accumulé des années de données sur les ventes. Elle souhaite analyser ces données afin de comprendre les tendances d'achat, optimiser son inventaire, et prévoir les futures demandes. Les données sont stockées dans des fichiers CSV sur Amazon S3. L'entreprise souhaite une solution qui permette à son équipe d'analyser rapidement ces données et de créer des rapports interactifs à partager avec les managers.

**Besoins de l'entreprise** :
- Traiter des volumes importants de données.
- Automatiser le processus de chargement des données et leur transformation.
- Créer des tableaux de bord interactifs.
  
**Options de services** :
1. **AWS Glue** – Pour automatiser l'ETL (extraction, transformation, chargement).
2. **Amazon Redshift** – Pour les entrepôts de données permettant de générer des rapports et faire des analyses à grande échelle.
3. **AWS Athena** – Pour interroger directement les données sur S3 sans avoir besoin de les déplacer.
4. **AWS Crawler** – Pour détecter et cataloguer automatiquement les schémas des fichiers CSV stockés dans S3.

---

### **Mise en Situation 2 : Gestion des Logs**

Une entreprise de télécommunications reçoit des millions de logs chaque jour provenant de son infrastructure réseau. Ces logs sont essentiels pour détecter les anomalies, suivre les performances, et prévoir les pannes éventuelles. Ils sont stockés dans S3, et l'entreprise doit les analyser en temps quasi réel pour réagir rapidement aux incidents. Elle a besoin d'une solution flexible, qui puisse traiter les données en continu et à grande échelle.

**Besoins de l'entreprise** :
- Traiter des données massives en temps quasi réel.
- Analyser les logs et générer des alertes automatiques en cas d'anomalies.
- Minimiser la latence du traitement.

**Options de services** :
1. **Amazon EMR** – Pour traiter les logs à l'aide de frameworks comme Apache Spark ou Hadoop.
2. **AWS Lambda** – Pour exécuter du code sans serveur en réponse à des événements comme le stockage de nouveaux logs dans S3.
3. **Amazon Kinesis** – Pour traiter et analyser des flux de données en temps réel.
4. **AWS Glue** – Pour l'ETL des logs, mais avec des transformations régulières plutôt que temps réel.

---

### **Mise en Situation 3 : Optimisation du Marketing**

Une agence de marketing collecte des données provenant de plusieurs sources (réseaux sociaux, campagnes de publicités en ligne, données CRM) afin d'optimiser ses futures campagnes publicitaires. Cependant, ces données sont dans différents formats et proviennent de sources multiples. L'agence a besoin d'un pipeline qui puisse normaliser ces données et fournir des insights sur les tendances des clients, en particulier concernant les campagnes qui génèrent le plus de revenus.

**Besoins de l'agence** :
- Rassembler des données provenant de sources hétérogènes.
- Normaliser les formats de données pour permettre des analyses cohérentes.
- Stocker les données dans un entrepôt de données pour des analyses futures.

**Options de services** :
1. **AWS Glue** – Pour automatiser les transformations des données issues de différentes sources.
2. **Amazon Redshift** – Pour stocker et analyser les données normalisées.
3. **Amazon RDS** – Pour stocker des données structurées issues d'un système de gestion de base de données relationnelle.
4. **AWS Step Functions** – Pour orchestrer les différents processus et étapes de transformation et d'analyse des données.

---

### **Mise en Situation 4 : Prédiction de Maintenance**

Une société de transport ferroviaire souhaite améliorer la maintenance de ses trains en prédisant les pannes mécaniques avant qu'elles ne surviennent. Les trains sont équipés de capteurs qui collectent des données en temps réel sur l'état des pièces mécaniques. L'entreprise souhaite exploiter ces données pour entraîner des modèles de machine learning et effectuer des prédictions basées sur l'historique des pannes et les conditions actuelles des trains.

**Besoins de l'entreprise** :
- Collecter et traiter des données de capteurs en temps réel.
- Utiliser ces données pour entraîner des modèles de machine learning.
- Intégrer les résultats des prédictions dans un système pour alerter les techniciens de maintenance.

**Options de services** :
1. **Amazon SageMaker** – Pour entraîner et déployer des modèles de machine learning basés sur les données des capteurs.
2. **Amazon Kinesis** – Pour traiter en continu les données des capteurs en temps réel.
3. **AWS Glue** – Pour transformer et préparer les données pour l'entraînement des modèles.
4. **Amazon Redshift** – Pour stocker l'historique des pannes et effectuer des analyses à long terme.

---

### **Mise en Situation 5 : Catalogue de Données**

Une entreprise de recherche génère de grandes quantités de données chaque jour issues de leurs expérimentations scientifiques. Ces données sont stockées dans S3 sous des formats variés (CSV, Parquet, JSON). L'équipe technique souhaite créer un catalogue centralisé de ces données pour permettre aux scientifiques de découvrir facilement de nouvelles données pour leurs analyses.

**Besoins de l'entreprise** :
- Cataloguer automatiquement toutes les nouvelles données qui arrivent.
- Faciliter l’accès aux données pour les utilisateurs techniques et non techniques.
- Gérer les métadonnées pour chaque fichier stocké.

**Options de services** :
1. **AWS Glue Data Catalog** – Pour créer et gérer un catalogue centralisé des données.
2. **AWS Crawler** – Pour détecter automatiquement les nouveaux fichiers et mettre à jour le catalogue avec les schémas et métadonnées.
3. **Amazon Redshift Spectrum** – Pour interroger les données stockées directement dans S3 sans les déplacer dans un entrepôt.
4. **AWS Athena** – Pour exécuter des requêtes SQL sur les données stockées dans S3 à partir du catalogue.

