### **Explication de l'ordre du schéma : Processus d'ETL avec AWS Glue et Athena**


![image](https://github.com/user-attachments/assets/2d32f9ee-c9d8-469e-af8c-914644c74d76)

Le schéma illustre comment différentes composantes AWS interagissent pour permettre l'importation, la transformation et l'interrogation de données météorologiques (**GHCN-D Dataset**) via **AWS Glue, Athena, CloudFormation, et IAM**. Voici l'ordre chronologique des actions :

---

### **Étape 1 : Création du Crawler AWS Glue (Weather Crawler)**
- **Vous** créez un **Crawler AWS Glue** nommé `weather` pour explorer un dataset **GHCN-D** stocké dans un bucket S3 appartenant à un autre compte AWS.
- Ce crawler utilise un **rôle IAM nommé `gluelab`** qui lui donne les permissions nécessaires pour accéder aux données S3.
- Le crawler lit les fichiers CSV dans le bucket et enregistre les métadonnées (schéma) dans le **AWS Glue Data Catalog**.

---

### **Étape 2 : Interrogation des données avec Athena**
- Une fois les métadonnées disponibles dans le **Data Catalog**, vous utilisez **Amazon Athena** pour effectuer des requêtes SQL sur ces données.
- Les résultats des requêtes sont stockés dans deux buckets S3 : 
  - `data-science-bucket` pour les requêtes générales.
  - `glue-1950-bucket` pour les tables qui ne contiennent que les données postérieures à 1950.

---

### **Étape 3 : Création d'un Crawler via CloudFormation (cfn-weather-crawler)**
- **AWS Cloud9** est utilisé pour déployer un **modèle CloudFormation** qui crée un nouveau crawler appelé `cfn-weather-crawler`.
- Ce crawler est conçu pour automatiser l'exploration des données en fonction de configurations spécifiques.

---

### **Étape 4 : Configuration des permissions IAM (Policy-For-Data-Scientists)**
- Une **politique IAM nommée `Policy-For-Data-Scientists`** est examinée pour s'assurer que l'utilisateur **Mary** peut accéder aux données, exécuter des requêtes avec Athena, et utiliser les crawlers créés.
- Ce rôle (`gluelab`) est associé au crawler initial `weather`.

---

### **Étape 5 : Utilisation du Crawler par Mary**
- Mary, en tant que membre de l'équipe des Data Scientists, utilise les permissions qui lui sont accordées par `Policy-For-Data-Scientists` pour exécuter le crawler `cfn-weather-crawler`.
- Le crawler découvre et enregistre les métadonnées des données explorées dans le **AWS Glue Data Catalog**.

---

### **Points clés à retenir :**
- **AWS Glue Data Catalog** centralise les métadonnées permettant à Athena d'interroger efficacement les données.  
- **CloudFormation** est utilisé pour automatiser la création d'un crawler prêt à l'emploi.  
- **IAM Policies** sont essentielles pour restreindre ou accorder l'accès aux ressources AWS de manière sécurisée.  
- **Les données explorées** sont disponibles pour interrogation via Athena après leur indexation par AWS Glue.



# Annexe - **Pourquoi deux crawlers ?**  

L'utilisation de **deux crawlers distincts** (`weather` et `cfn-weather-crawler`) dans ce lab a une logique précise, basée sur la nécessité de **séparer les processus d'exploration de données** et de **rendre les opérations reproductibles pour d'autres utilisateurs (comme Mary)**.  

---

## **Crawler 1 : `weather` (Crawler principal créé manuellement)**
- **Créé manuellement par vous** dans la console AWS Glue.  
- **But principal :**  
  - Découvrir le schéma des données stockées dans le bucket S3 public contenant le dataset **GHCN-D**.  
  - **Indexer les métadonnées** dans un catalogue de données nommé `weatherdata` dans AWS Glue Data Catalog.  
- **Rôle IAM utilisé :** `gluelab` (Vous devez le configurer manuellement).  
- **Utilisation directe :** Vous l'utilisez pour faire une découverte initiale des données.  

---

## **Crawler 2 : `cfn-weather-crawler` (Crawler créé via CloudFormation)**
- **Créé automatiquement via un modèle AWS CloudFormation** exécuté depuis AWS Cloud9.  
- **But principal :**  
  - **Standardiser et automatiser la création du crawler**.  
  - Permettre à d'autres utilisateurs, comme **Mary**, d'utiliser ce crawler facilement.  
  - Faciliter la réutilisation du modèle pour d'autres projets sans avoir à recréer un crawler manuellement chaque fois.  
- **Rôle IAM utilisé :** `gluelab` (Assigne automatiquement les permissions nécessaires via CloudFormation).  
- **Utilisation principale :** Rendre le processus **reproductible, facile à déployer, et sécurisé** pour d'autres utilisateurs qui ont des permissions spécifiques.  

---

###  **Pourquoi avoir deux crawlers ?**
1. **Séparation des responsabilités :**  
   - Le premier crawler (`weather`) est utilisé pour votre découverte initiale.  
   - Le deuxième (`cfn-weather-crawler`) est conçu pour permettre à d'autres utilisateurs comme Mary d'explorer les données de manière autonome.  

2. **Reproductibilité et Automatisation :**  
   - Avec un modèle CloudFormation, d'autres équipes peuvent facilement déployer leurs propres crawlers.  
   - Cela évite d'avoir à configurer manuellement les permissions, les chemins de données, et les configurations chaque fois.  

3. **Sécurité et Contrôle d'accès :**  
   - Le modèle CloudFormation (`cfn-weather-crawler`) respecte les politiques IAM (`Policy-For-Data-Scientists`) permettant un accès restreint aux ressources AWS.  
   - Chaque utilisateur peut créer son propre crawler en utilisant un modèle prédéfini sans avoir accès aux configurations internes de celui-ci.  


