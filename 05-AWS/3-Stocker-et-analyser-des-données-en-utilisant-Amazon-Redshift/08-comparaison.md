# Résumé

<br/> 

# 1 - **Amazon Redshift – Prise en charge AWS**
| Élément | Détail |
|--------|--------|
| **Service AWS ?** | Oui, c’est un service natif AWS |
| **Type de service** | Data warehouse (entrepôt de données) |
| **Objectif** | Analyser de très grandes quantités de données en SQL |
| **Langage supporté** | SQL (avec extensions spécifiques Redshift) |
| **Stockage** | Colonnaire (optimisé pour les requêtes analytiques) |
| **Prise en charge IAM** | Oui, contrôle d'accès fin avec IAM |
| **Intégrations** | S3, Glue, Athena, Kinesis, Lambda, EMR, etc. |
| **Sécurité** | Chiffrement, VPC, Audit avec CloudTrail |
| **Support AWS ?** | Oui : support technique officiel + documentation complète |

---

### **Fonctionnalités clés prises en charge par AWS**
- Redshift Serverless (pas besoin de gérer des clusters)
- Sauvegarde automatique (Snapshots)
- Intégration avec **Amazon S3** via `COPY`, `UNLOAD`
- Connexion à **BI tools** (Tableau, QuickSight, Power BI)
- Redshift Spectrum pour requêter des fichiers S3 sans les importer
- Chargement massif avec `COPY` à partir de S3, DynamoDB, etc.




<br/>


# 2 -  **AWS Glue – Prise en charge AWS**
| Élément | Détail |
|--------|--------|
| **Service AWS ?** | Oui, 100 % géré par AWS |
| **Type de service** | ETL (Extract – Transform – Load) serverless |
| **Objectif** | Préparer, transformer et déplacer des données |
| **Langages supportés** | Python (PySpark), Scala |
| **Composants principaux** | Glue Job, Glue Crawler, Glue Catalog, Trigger |
| **Serverless ?** | Oui, pas besoin de provisionner d’infrastructure |
| **Support IAM** | Oui, gestion des rôles pour accès S3, Redshift, etc. |
| **Intégrations** | S3, Redshift, Athena, RDS, JDBC, Lake Formation… |
| **Support AWS ?** | Oui : documentation officielle, console AWS, API, CLI |

---

### **Fonctionnalités clés prises en charge par AWS Glue**
- **Glue Data Catalog** : base de métadonnées centrale (dictionnaire de schéma)
- **Glue Crawlers** : détectent automatiquement les schémas (auto discovery)
- **Glue Jobs** : scripts ETL en PySpark ou Scala, entièrement gérés
- **Glue Studio** : interface visuelle pour créer des workflows ETL
- **Glue Triggers** : automatisation des jobs (horaire ou événementielle)
- **Intégration avec Athena** : interroger directement les tables créées par Glue

---

###  Exemple de scénario typique (entièrement supporté par AWS)
1. Un fichier `CSV` est chargé sur **S3**
2. Un **Crawler Glue** détecte son schéma et crée une table dans **Glue Catalog**
3. Un **Job Glue** transforme les données (nettoyage, jointure, etc.)
4. Les données sont exportées vers **Redshift** ou une nouvelle partition S3
5. **Athena** peut maintenant les interroger via le Glue Catalog



<br/>

# 03 -  Comparaison entre Amazon Redshift et AWS Glue

| Critère | **AWS Glue** | **Amazon Redshift** |
|--------|--------------|---------------------|
| **Type de service** | ETL (Extract, Transform, Load) | Data Warehouse |
| **Objectif principal** | Préparer et transformer les données | Analyser les données via SQL |
| **Service Serverless ?** | Oui, totalement | Oui avec Redshift Serverless (optionnel) |
| **Langages supportés** | PySpark (Python), Scala | SQL (avec extensions Redshift) |
| **Transformation des données ?** | Oui, transformation flexible avec Spark | Non directement, nécessite ETL ou SQL transform |
| **Stockage intégré ?** | Non, Glue lit/écrit vers S3, Redshift, etc. | Oui, stockage colonnaire optimisé |
| **Accès aux métadonnées** | Glue Data Catalog (centralisé) | Tables internes Redshift |
| **Cas d’usage typique** | Nettoyage, filtrage, jointure de données sur S3 | Requêtes analytiques sur des millions/lignes |
| **Connexion avec S3** | Oui, en lecture/écriture via Job | Oui, via commandes `COPY`, `UNLOAD` |
| **Connexion avec Redshift** | Oui, Glue peut écrire vers Redshift | Peut recevoir des données via Glue ou S3 |
| **Requêtes SQL ?** | Indirectement via Athena (sur tables cataloguées) | Directement avec Redshift |
| **Interface visuelle ?** | Oui, Glue Studio (No-code/low-code ETL) | Oui, Query Editor V2 dans Redshift |
| **Mise à l’échelle automatique** | Oui (serverless Spark) | Oui (Redshift Serverless ou RA3 scalable) |
| **Tarification** | À la seconde, selon ressources utilisées par les jobs | À l’heure ou à la seconde (selon usage) |

---

# **Exemple concret d’usage combiné (Pipeline typique)**
> **Objectif : Analyser des données de taxi à New York.**

| Étape | Service |
|-------|--------|
| 1. Données `CSV` sur S3 | `Amazon S3` |
| 2. Découverte automatique du schéma | `AWS Glue Crawler` |
| 3. Création de la table dans Glue Catalog | `Glue Data Catalog` |
| 4. Nettoyage et conversion en Parquet | `Glue Job (PySpark)` |
| 5. Chargement dans Redshift | `Redshift COPY from S3` |
| 6. Analyse avec SQL | `Amazon Redshift` |
| 7. Visualisation | `QuickSight` ou autres BI |



