---
# 1 - Amazon Redshift (Cluster - Pas Serverless)
---

- **Type d'architecture :** Cluster (nœuds de calcul et un nœud leader).  
- **Objectif principal :** Entrepôt de données orienté analytique (Stocker & Interroger les données).  
- **Processus :** ELT (Extract, Load, Transform).  
- **Pourquoi ELT ?**  
  - Les données sont d'abord chargées (Load) dans Amazon Redshift, puis transformées (Transform) par des requêtes SQL directement au sein de Redshift.  
  - C'est efficace lorsque vous voulez charger massivement des données brutes et appliquer les transformations après coup.  
  - Typiquement utilisé pour des requêtes analytiques intensives sur des grands ensembles de données déjà chargées.  
- **Est-ce serverless ?** Non, vous devez configurer et gérer des clusters Redshift.  


---
# 2 - AWS Glue (Serverless)
---

- **Type d'architecture :** Serverless (sans infrastructure à gérer).  
- **Objectif principal :** Pipeline ETL (Extraction, Transformation, Chargement).  
- **Processus :** ETL (Extract, Transform, Load).  
- **Pourquoi ETL ?**  
  - Les données sont extraites (Extract) de différentes sources (Amazon S3, DynamoDB, RDS, Redshift, etc.).  
  - Puis elles sont transformées (Transform) (nettoyage, formatage, enrichissement) au sein d’AWS Glue (souvent avec Spark).  
  - Enfin, elles sont chargées (Load) dans une destination (comme Amazon Redshift, S3, DynamoDB, etc.).  
- **Est-ce serverless ?** Oui, AWS Glue est complètement serverless, vous payez seulement pour les tâches exécutées.  


---
### Résumé de la différence ELT vs ETL :
---

| Caractéristique        | Amazon Redshift (ELT)        | AWS Glue (ETL)             |
|------------------------|-----------------------------|----------------------------|
| Processus              | Extract -> Load -> Transform | Extract -> Transform -> Load |
| Transformation          | Se fait **après** le chargement dans Redshift (par des requêtes SQL). | Se fait **avant** de charger les données finales (avec Glue jobs utilisant Spark). |
| Cas d'utilisation typique| Analyses BI, Reporting, Big Data Analytics. | Préparation de données, Pipelines de données, Pré-traitement. |
| Architecture            | Cluster (pas serverless).    | Serverless.                |


### Exemple pratique d'utilisation combinée :

1. **AWS Glue (ETL)** : Préparer des données brutes provenant de Amazon S3 (ou autre source).  
2. **Amazon Redshift (ELT)** : Charger les données préparées dans un cluster Redshift et appliquer des transformations supplémentaires par requêtes SQL.  
3. **Reporting & Visualisation :** Connecter Redshift à un outil BI comme Tableau, Power BI, etc.  
