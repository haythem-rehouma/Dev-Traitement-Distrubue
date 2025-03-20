### **Question 1**  
**Contexte** : Une équipe d'analystes de données dans une grande entreprise de vente au détail exécute des requêtes complexes sur Amazon Athena pour générer des rapports hebdomadaires de ventes. Les données pertinentes pour ces rapports ne changent pas durant la période de génération du rapport.

**Question** :  
Quelle approche l'équipe devrait-elle adopter pour atteindre ses objectifs d'optimisation des performances des requêtes et réduction des coûts tout en maintenant la précision et la rapidité des rapports ?

**Réponse correcte** :  
Activer la fonctionnalité « Réutiliser les résultats de requête » pour chaque requête et définir une durée maximale de 7 jours pour la réutilisation des résultats.

**Explication** :  
La fonctionnalité de réutilisation des résultats dans Amazon Athena permet de réutiliser les résultats des requêtes précédentes, ce qui améliore la performance et réduit les coûts. Puisque les données ne changent pas pendant la période de génération des rapports, il est plus efficace de réutiliser les résultats pendant une durée de 7 jours plutôt que d'exécuter à nouveau les requêtes.

---

### **Question 2**  
**Contexte** : Une entreprise possède un grand volume de données d'achats stockées dans une instance Amazon RDS. Ces données sont sensibles et nécessitent un contrôle d'accès strict tout en facilitant les requêtes complexes pour l'intelligence d'affaires.

**Question** :  
Quelle solution répond à ces exigences avec le moins de surcharge opérationnelle ?

**Réponse correcte** :  
Exploiter un lac de données via AWS Lake Formation. Configurer une connexion JDBC à Amazon RDS en utilisant AWS Glue.

**Explication** :  
AWS Lake Formation offre un cadre centralisé pour sécuriser et gérer les accès aux données tout en minimisant la surcharge opérationnelle. En intégrant AWS Glue pour la connexion JDBC, l'entreprise peut utiliser un ETL efficace pour analyser les données tout en respectant les contrôles d'accès granulaire nécessaires.

---

### **Question 3**  
**Contexte** : Une entreprise de logistique gère un lac de données Amazon S3 partitionné et compatible avec Hive. Les nouvelles partitions ne sont pas automatiquement reconnues par Athena.

**Question** :  
Comment l'équipe peut-elle s'assurer qu'Athena renvoie les données nouvellement insérées et les dernières partitions avec un effort de développement minimal ?

**Réponse correcte** :  
Exécuter la commande `MSCK REPAIR TABLE` dans Athena pour découvrir et ajouter automatiquement les nouvelles partitions.

**Explication** :  
La commande `MSCK REPAIR TABLE` dans Amazon Athena permet de synchroniser automatiquement les partitions Hive nouvellement ajoutées sans nécessiter de mise à jour manuelle des métadonnées, simplifiant ainsi la gestion des nouvelles partitions.

---

### **Question 4**  
**Contexte** : Une équipe d'ingénierie des données consolide des dossiers de patients, dont certains numérisés via OCR, dans un lac de données Amazon S3. L'équipe doit s'assurer de la qualité des données avant leur intégration au pipeline ETL.

**Question** :  
Quel service AWS fournit la solution avec le moins de surcharge de développement pour valider la qualité des données ?

**Réponse correcte** :  
Utiliser AWS Glue Data Quality pour surveiller et valider automatiquement la qualité des données dans le pipeline ETL.

**Explication** :  
AWS Glue Data Quality permet de surveiller automatiquement les anomalies, les erreurs de format et les duplications dans les données, réduisant ainsi la surcharge de développement tout en garantissant l'intégrité des données avant leur traitement.

---

### **Question 5**  
**Contexte** : Une équipe d'analyse travaille avec des enregistrements de tremblements de terre stockés dans Amazon S3. Ils ont besoin de calculer des zones géographiques en utilisant un système d'indexation géospatiale.

**Question** :  
Quelle est la manière la plus efficace d'intégrer ces calculs dans leurs requêtes Athena ?

**Réponse correcte** :  
Implémenter une fonction définie par l'utilisateur (UDF) dans Amazon Athena, alimentée par AWS Lambda, pour calculer les zones de grille en utilisant le système d'indexation géospatiale.

**Explication** :  
Les UDF d'Athena alimentées par AWS Lambda permettent de réaliser des calculs géospatiaux personnalisés et complexes directement au sein des requêtes SQL, améliorant ainsi l'efficacité des analyses.

---

### **Question 6**  
**Contexte** : Une entreprise de vente au détail utilise AWS pour traiter et analyser ses données. Elle souhaite interroger et combiner des données provenant de plusieurs sources (Aurora MySQL, HBase, DynamoDB) sans les déplacer dans S3.

**Question** :  
Quel service AWS peut être utilisé pour effectuer efficacement ces requêtes sur des sources de données multiples ?

**Réponse correcte** :  
Utiliser Amazon Athena Federated Query pour exécuter des requêtes SQL sur les données stockées dans S3, Aurora MySQL, HBase sur EMR et DynamoDB sans déplacer les données.

**Explication** :  
Athena Federated Query permet de joindre des données de plusieurs sources sans les déplacer, ce qui répond directement à l'exigence de ne pas dupliquer les données et de pouvoir les interroger en place.

---

### **Question 7**  
**Contexte** : Une organisation de santé agrège des données de patients dans Amazon S3, mais les dossiers n'ont pas d'identifiant unique commun.

**Question** :  
Quelle solution permettrait à l'organisation de faire correspondre les dossiers des patients avec le moins de surcharge de développement ?

**Réponse correcte** :  
Configurer la transformation de machine learning FindMatches d'AWS Glue pour identifier et regrouper des dossiers similaires de patients.

**Explication** :  
La transformation FindMatches dans AWS Glue utilise le machine learning pour identifier automatiquement les correspondances entre les dossiers sans nécessiter d'identifiants uniques, ce qui réduit considérablement la surcharge de développement.

---

### **Question 8**  
**Contexte** : Une équipe d'ingénierie des données dans une entreprise de commerce électronique cherche à améliorer les performances de leurs requêtes Athena à mesure que la taille des données augmente.

**Question** :  
Quelle méthode serait la plus efficace pour optimiser les performances des requêtes ?

**Réponse correcte** :  
Exécuter une requête `CREATE TABLE AS SELECT` (CTAS) dans Amazon Athena pour convertir les résultats en formats de stockage efficaces tels que Parquet ou ORC.

**Explication** :  
Le formatage des résultats en Parquet ou ORC via une requête CTAS optimise les performances des requêtes en réduisant la quantité de données scannées et améliore ainsi l'efficacité des requêtes sur des ensembles de données volumineux.

---

### **Question 9**  
**Contexte** : Une entreprise de marketing digital gère un lac de données sur Amazon S3 et utilise AWS Glue Data Catalog pour la gestion des métadonnées. L'administrateur souhaite gérer les permissions de manière synchronisée entre S3 et le Data Catalog.

**Question** :  
Quelle solution permettrait de gérer les permissions avec le moins de surcharge de développement ?

**Réponse correcte** :  
Utiliser les permissions AWS Lake Formation pour gérer les politiques d'accès à la fois pour Amazon S3 et AWS Glue Data Catalog.

**Explication** :  
AWS Lake Formation simplifie la gestion des permissions en offrant un cadre centralisé et granulaire pour les politiques d'accès, permettant une synchronisation facile des permissions entre Amazon S3 et AWS Glue Data Catalog.

---

### **Question 10**  
**Contexte** : Une entreprise d'analytique dans le domaine de la santé souhaite améliorer ses pratiques de gestion des données, en offrant à la fois une personnalisation par scripts et une interface visuelle facile à utiliser.

**Question** :  
Quel service AWS répondrait à ces exigences ?

**Réponse correcte** :  
Utiliser AWS Glue Studio pour concevoir, exécuter et surveiller les tâches ETL via une interface graphique.

**Explication** :  
AWS Glue Studio permet à des utilisateurs avec différents niveaux de compétences techniques de créer, exécuter et surveiller des tâches ETL de manière visuelle tout en offrant la possibilité de personnaliser les scripts, répondant ainsi aux besoins de l'entreprise.

