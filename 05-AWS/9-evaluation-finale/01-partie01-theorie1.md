# Partie 01 - Quiz avec *justification de la réponse* de 18h à 20h

### *Pour chaque scénario décrit dans les questions suivantes, choisissez le ou les services AWS les plus adaptés. Justifiez votre choix en expliquant votre (ou vos) choix et pourquoi vous avez éliminé les autres options.*

---

### **Question 1.**  
Une entreprise collecte des données de capteurs IoT en continu depuis des milliers d'appareils dans une usine. Leur besoin principal est de stocker ces données dans **Amazon S3** pour les analyser plus tard. Ils ne veulent pas gérer les flux en temps réel, mais cherchent une solution qui regroupe les données et les livre efficacement au stockage. Quel(s) service(s) AWS sont les plus adaptés à ce besoin ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. AWS Lambda  
- D. OpenSearch  

---

### **Question 2.**  
Une startup développe une application de trading en temps réel. Ils doivent analyser chaque transaction dès qu'elle est effectuée pour détecter des comportements frauduleux dans les secondes qui suivent l'événement. Ils ont besoin d'une latence extrêmement faible pour ingérer les données et réagir immédiatement. Quel(s) service(s) est/sont le(s) plus approprié(s) pour ce cas d'utilisation ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. Amazon S3  
- D. OpenSearch  

---

### **Question 3.**  
Une entreprise collecte des logs d'application pour des audits futurs. Leur priorité est de stocker ces logs dans **Amazon S3** toutes les 5 minutes sans gérer l'infrastructure. Ils n'ont pas besoin de traiter les logs en temps réel ni de contrôler les détails du flux. Quel(s) service(s) devriez-vous recommander ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. Amazon RDS  
- D. AWS Lambda  

---

### **Question 4.**  
Un service de surveillance de sécurité vidéo envoie des flux vidéo en continu. Les vidéos doivent être traitées immédiatement pour identifier des objets suspects en temps réel. Les vidéos ne seront pas archivées, mais la priorité est de pouvoir réagir instantanément à tout événement détecté. Quel(s) service(s) convient/conviendraient le mieux à ce besoin ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. Amazon S3  
- D. OpenSearch  

---

### **Question 5.**  
Une société souhaite analyser des données financières après les avoir regroupées et stockées dans **Amazon Redshift** pour des rapports hebdomadaires. Ils n'ont pas besoin de traiter les données en temps réel et préfèrent un service qui automatise l'envoi des données vers Redshift avec une gestion minimale. Quel(s) service(s) devriez-vous recommander ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. Amazon S3  
- D. AWS Lambda  

---

### **Question 6.**  
Une société de e-commerce collecte des millions de transactions clients chaque jour. Ils souhaitent exécuter des **requêtes SQL complexes** pour générer des rapports analytiques hebdomadaires sur les ventes. Leur besoin principal est d’optimiser l’analyse des **grands ensembles de données** en utilisant un **service de data warehouse** performant. Quel(s) service(s) AWS recommanderiez-vous ?  
- A. Kinesis Data Firehose  
- B. AWS Glue  
- C. Amazon Redshift  
- D. AWS Lambda  

---

### **Question 7.**  
Une équipe de scientifiques de données souhaite exécuter des **tâches de traitement de données à grande échelle** sur des **clusters de calcul distribués**. Ils veulent analyser des ensembles de données non structurés en utilisant **Apache Spark** et **Hadoop** et ont besoin d'un environnement hautement flexible pour ajuster la capacité de calcul en fonction de la charge de travail. Quel(s) service(s) AWS est/sont le(s) plus adapté(s) à ce besoin ?  
- A. Kinesis Data Streams  
- B. Amazon EMR  
- C. Amazon Redshift  
- D. AWS Glue  

---

### **Question 8.**  
Une entreprise veut automatiser l’**ETL (Extract, Transform, Load)** pour préparer ses données brutes avant de les analyser. Ils ont besoin d'un service **sans serveur**, capable d’**intégrer différentes sources de données** et de **préparer les données** pour des analyses ultérieures dans Redshift ou S3. Quel(s) service(s) AWS conviendrait/conviendraient le mieux ?  
- A. AWS Glue  
- B. Amazon EMR  
- C. Kinesis Data Firehose  
- D. AWS Lambda  

---

### **Question 9.**  
Une société souhaite diffuser des logs d'application vers **Amazon S3** pour stockage à long terme. Ils veulent une solution qui **tamponne** les données et les envoie automatiquement vers S3 sans nécessiter de configuration manuelle complexe ni traitement en temps réel. Quel(s) service(s) choisiriez-vous ?  
- A. Kinesis Data Streams  
- B. AWS Glue  
- C. Kinesis Data Firehose  
- D. Amazon EMR  

---

### **Question 10.**  
Une plateforme de streaming vidéo traite des millions de requêtes par seconde. Ils veulent réagir en **temps réel** aux données d'utilisation de leur application afin de recommander des vidéos personnalisées instantanément à leurs utilisateurs. Leur besoin est un service capable d'ingérer de gros volumes de données en **temps réel** avec une **latence extrêmement faible**. Quel(s) service(s) est/sont le(s) plus adapté(s) ?  
- A. Kinesis Data Firehose  
- B. Kinesis Data Streams  
- C. AWS Glue  
- D. Amazon EMR  

---

### **Question 11.**  
Une entreprise dispose d'un large ensemble de données stockées dans **Amazon S3** au format parquet. Elle souhaite exécuter des **requêtes SQL ad-hoc** pour analyser ces données sans avoir à déplacer ou transformer les fichiers. Ils veulent éviter de créer un data warehouse dédié et souhaitent une solution **sans serveur** pour exécuter ces requêtes directement sur S3. Quel(s) service(s) est/sont le(s) plus approprié(s) ?  
- A. Amazon Athena  
- B. Amazon Kinesis Data Streams  
- C. AWS Hudi  
- D. AWS Glue  

---

### **Question 12.**  
Une entreprise de logistique collecte en temps réel des informations depuis des capteurs embarqués sur leurs camions. Ils veulent analyser ces flux de données en temps réel afin de surveiller l’état des véhicules et de détecter des anomalies instantanément. Le besoin est un service capable d’**ingérer** et de **traiter les données en temps réel** avec une latence minimale. Quel(s) service(s) recommanderiez-vous ?  
- A. Amazon Athena  
- B. Kinesis Data Streams  
- C. AWS Hudi  
- D. Amazon Redshift  

---

### **Question 13.**  
Une société veut stocker des données historiques dans **Amazon S3**, tout en assurant un suivi des versions et des changements sur ces données, afin de faciliter des **recherches incrémentales** et des **mises à jour**. Ils veulent une solution qui gère les données transactionnelles et permette des requêtes SQL pour obtenir des vues cohérentes de leurs données au fil du temps. Quel(s) service(s) conviendrait/conviendraient le mieux ?  
- A. Amazon Athena  
- B. Kinesis Data Streams  
- C. AWS Hudi  
- D. AWS Glue  

---

### **Question 14.**  
Une société de streaming musical souhaite fournir des recommandations personnalisées en **temps réel** en fonction des comportements d'écoute de ses utilisateurs. Le besoin est un service capable de traiter des **volumes massifs de données** tout en maintenant une **latence ultra-faible** pour réagir instantanément aux nouvelles données. Quel(s) service(s) est/sont le(s) plus adapté(s) ?  
- A. Amazon Athena  
- B. Kinesis Data Streams  
- C. AWS Hudi  
- D. AWS Glue  

---

### **Question 15.**  
Un département marketing souhaite analyser des **données historiques stockées dans S3** en utilisant des **requêtes SQL standard** pour identifier les tendances d’achat. Ils veulent exécuter ces analyses ponctuelles de manière flexible et **sans gestion d'infrastructure** supplémentaire. Quel(s) service(s) recommanderiez-vous ?  
- A. Amazon Athena  
- B. Kinesis Data Streams  
- C. AWS Hudi  
- D. AWS Lambda  

---

### **Question 16.**  
Une entreprise de transport souhaite surveiller en temps réel les **flux de données GPS** de ses véhicules pour suivre leur position sur une carte en continu. Ils ont besoin de capturer les données GPS dès qu’elles sont générées, afin de ne jamais perdre une mise à jour et de pouvoir visualiser les trajets des véhicules en temps réel. Quel(s) service(s) est/sont le(s) plus adapté(s) à cette tâche ?  
- A. Amazon Athena  
- B. AWS Glue  
- C. Kinesis Data Streams  
- D. OpenSearch  

---

### **Question 17.**  
Une institution gouvernementale veut analyser les **logs des applications** de manière centralisée pour améliorer la gestion des incidents. Ces logs sont collectés en temps quasi réel et doivent être **stockés et indexés** dans une base de données pour faciliter la recherche par mots-clés en cas de problème. Ils recherchent une solution qui regroupe ces logs en lot et les envoie vers un moteur de recherche sans gestion manuelle. Quel(s) service(s) recommanderiez-vous ?  
- A. AWS Glue  
- B. Kinesis Data Streams  
- C. Kinesis Data Firehose  
- D. Amazon EMR  

---

### **Question 18.**  
Une entreprise de gestion des ressources humaines souhaite capturer des **données sur la performance des employés** à des intervalles réguliers, les enrichir avec des informations supplémentaires comme les commentaires des managers, puis envoyer ces données enrichies vers un système de stockage pour des analyses ultérieures. Ils recherchent un service pour enrichir automatiquement les données avant de les envoyer au stockage. Quel(s) service(s) conviendrait/conviendraient le mieux ?  
- A. Amazon Redshift  
- B. AWS Lambda  
- C. Kinesis Data Streams  
- D. OpenSearch  

---

### **Question 19.**  
Une entreprise souhaite consolider les données d'examen de conduite des différents centres d'examen à travers le pays. Chaque centre envoie ses données de manière continue, mais l'entreprise souhaite regrouper les informations en blocs et les envoyer à un **système de stockage centralisé** comme OpenSearch pour permettre des analyses ultérieures. Ils ne veulent pas envoyer chaque donnée une par une mais veulent regrouper et bufferiser les informations. Quel(s) service(s) est/sont le(s) plus approprié(s) ?  
- A. Kinesis Data Streams  
- B. Kinesis Data Firehose  
- C. AWS Glue  
- D. Amazon EMR  

---

### **Question 20.**  
Une équipe marketing souhaite analyser les **interactions des clients** sur le site web de l'entreprise pour voir en temps réel comment les utilisateurs naviguent. Ils veulent capturer chaque interaction des utilisateurs sur la page et utiliser ces données pour créer des recommandations immédiates basées sur les clics et les comportements des clients. Quel(s) service(s) AWS est/sont le(s) plus adapté(s) à cette analyse en temps réel ?  
- A. Kinesis Data Streams  
- B. AWS Glue  
- C. Kinesis Data Firehose  
- D. Amazon Athena  



