### **Explication de l'ordre du schéma : Processus d'ETL avec AWS Glue et Athena**



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
