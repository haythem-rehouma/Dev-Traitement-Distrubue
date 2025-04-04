# **Question 1**  
Une entreprise collecte des données de capteurs IoT en continu depuis des milliers d'appareils dans une usine. Leur besoin principal est de stocker ces données dans Amazon S3 pour les analyser plus tard. Ils ne veulent pas gérer les flux en temps réel, mais cherchent une solution qui regroupe les données et les livre efficacement au stockage. Quel(s) service(s) AWS sont les plus adaptés à ce besoin ?

**Réponse choisie** : A. Kinesis Data Firehose  
**Justification** : Firehose est un service entièrement managé qui ingère les flux de données, les bufferise, les transforme (si nécessaire avec Lambda), puis les livre automatiquement vers des destinations telles que Amazon S3, Redshift, ou OpenSearch. Il ne nécessite pas de gestion de l’infrastructure, contrairement à Kinesis Data Streams. C’est donc la solution idéale pour envoyer efficacement des données vers S3 sans intervention manuelle.



# **Question 2**  
Une startup développe une application de trading en temps réel. Ils doivent analyser chaque transaction dès qu'elle est effectuée pour détecter des comportements frauduleux dans les secondes qui suivent l'événement. Ils ont besoin d'une latence extrêmement faible pour ingérer les données et réagir immédiatement.

**Réponse choisie** : B. Kinesis Data Streams  
**Justification** : Kinesis Data Streams permet une ingestion de données avec une **latence très faible**, un contrôle granulaire sur les consommateurs, et la possibilité de traiter les données immédiatement via Lambda ou d’autres applications analytiques. Firehose introduit un délai lié au buffering. S3 et OpenSearch ne conviennent pas pour de l’analyse immédiate.



# **Question 3**  
Une entreprise collecte des logs d'application pour des audits futurs. Leur priorité est de stocker ces logs dans Amazon S3 toutes les 5 minutes sans gérer l'infrastructure.

**Réponse choisie** : A. Kinesis Data Firehose  
**Justification** : Firehose permet de tamponner automatiquement les logs d’application et de les livrer par lot vers S3 à intervalles réguliers, sans configuration complexe. Kinesis Streams demanderait une gestion active. Lambda seul ne fait pas de buffering, et RDS n’est pas adapté au stockage de logs volumineux.



# **Question 4**  
Un service de surveillance de sécurité vidéo envoie des flux vidéo en continu. Les vidéos doivent être traitées immédiatement pour identifier des objets suspects en temps réel.

**Réponse choisie** : B. Kinesis Data Streams  
**Justification** : Data Streams est adapté pour le traitement immédiat de flux en temps réel, avec la possibilité de connecter des fonctions Lambda pour une analyse rapide. Firehose n’est pas optimal ici car il tamponne les données. OpenSearch est utile en aval, et S3 est un stockage non interactif.



# **Question 5**  
Une société souhaite analyser des données financières après les avoir regroupées et stockées dans Amazon Redshift pour des rapports hebdomadaires.

**Réponse choisie** : A. Kinesis Data Firehose  
**Justification** : Firehose permet la **livraison automatisée vers Redshift** avec très peu de configuration. Il peut aussi transformer les données en amont avec Lambda. Kinesis Streams est surdimensionné. S3 seul ne permet pas le chargement direct dans Redshift sans processus intermédiaire. Lambda ne gère pas la logique d’ingestion complète vers Redshift.



# **Question 6**  
Une société de e-commerce collecte des millions de transactions clients chaque jour. Ils souhaitent exécuter des requêtes SQL complexes pour générer des rapports hebdomadaires.

**Réponse choisie** : C. Amazon Redshift  
**Justification** : Redshift est un entrepôt de données (data warehouse) conçu pour traiter d’énormes volumes de données relationnelles avec des requêtes SQL complexes. Glue est un ETL. Firehose sert à livrer les données, mais ne les analyse pas. Lambda est limité pour des traitements massifs et longs.



# **Question 7**  
Une équipe de scientifiques de données souhaite exécuter des tâches de traitement de données à grande échelle sur des clusters de calcul distribués avec Apache Spark ou Hadoop.

**Réponse choisie** : B. Amazon EMR  
**Justification** : EMR (Elastic MapReduce) permet d'exécuter Spark, Hadoop, et d'autres frameworks big data sur des clusters AWS. C’est l'option idéale pour du traitement distribué à grande échelle. Glue est limité en flexibilité. Redshift est un data warehouse, et Streams n’est pas un environnement d’exécution distribué.



# **Question 8**  
Une entreprise veut automatiser l’ETL pour préparer ses données brutes à analyser dans Redshift ou S3, avec un service sans serveur.

**Réponse choisie** : A. AWS Glue  
**Justification** : AWS Glue est un service ETL serverless qui se connecte à différentes sources de données, applique des transformations et charge les résultats dans S3 ou Redshift. EMR est plus complexe. Firehose ne permet pas d’enchaîner plusieurs étapes complexes. Lambda est limité pour des workflows ETL complets.



# **Question 9**  
Une société souhaite diffuser des logs d'application vers Amazon S3 pour stockage à long terme avec une solution simple et automatisée.

**Réponse choisie** : C. Kinesis Data Firehose  
**Justification** : Firehose bufferise les logs, les compresse, et les envoie automatiquement vers S3 sans effort opérationnel. Glue est un ETL, pas un pipeline de diffusion. EMR est surdimensionné, et Kinesis Streams nécessite de gérer les consommateurs.



# **Question 10**  
Une plateforme de streaming vidéo veut recommander des vidéos en temps réel selon les interactions utilisateur.

**Réponse choisie** : B. Kinesis Data Streams  
**Justification** : Streams permet une ingestion rapide et un traitement immédiat de millions d’événements, ce qui est essentiel pour faire des recommandations instantanées. Firehose introduit de la latence. Glue et EMR sont utilisés pour du traitement différé.



# **Question 11**  
Une entreprise souhaite interroger des données au format Parquet stockées sur S3 avec SQL, sans data warehouse.

**Réponse choisie** : A. Amazon Athena  
**Justification** : Athena permet d’interroger directement les données sur S3 avec SQL sans infrastructure. Hudi est un format pour versionner les données, mais il ne fournit pas l’interface SQL. Streams et Glue ne sont pas conçus pour l’interrogation directe en SQL.


# **Question 12**  
Une entreprise de logistique veut analyser en temps réel les données de capteurs embarqués sur ses véhicules.

**Réponse choisie** : B. Kinesis Data Streams  
**Justification** : Streams permet une ingestion avec très faible latence pour détecter immédiatement les anomalies. Athena est pour l’analyse différée. Hudi est un format. Redshift n'est pas conçu pour la détection instantanée.



# **Question 13**  
Une société souhaite gérer les versions de données dans S3 et faire des recherches incrémentales avec SQL.

**Réponse choisie** : C. AWS Hudi  
**Justification** : Hudi permet de versionner les données dans S3, de gérer les changements incrémentaux, et de les interroger avec Athena. Glue est un ETL. Streams n’a pas cette fonction. Athena seul ne gère pas la logique de version.



# **Question 14**  
Une société de streaming musical veut faire des recommandations personnalisées en temps réel.

**Réponse choisie** : B. Kinesis Data Streams  
**Justification** : Streams est adapté pour la gestion massive de flux de données avec faible latence, idéal pour des systèmes de recommandation en direct. Glue, Hudi et Athena sont inadaptés pour une telle exigence de latence.



# **Question 15**  
Un département marketing veut faire des analyses ponctuelles de données dans S3 via SQL sans infrastructure.

**Réponse choisie** : A. Amazon Athena  
**Justification** : Athena offre une solution serverless pour lancer des requêtes SQL directement sur S3. Glue est un ETL. Hudi est un format. Lambda n'est pas conçu pour exécuter des analyses SQL complexes.



# **Question 16**  
Une entreprise veut visualiser en temps réel les trajets GPS de ses véhicules.

**Réponse choisie** : C. Kinesis Data Streams  
**Justification** : Streams capture les flux GPS dès leur génération et permet une analyse ou visualisation immédiate. Glue est différé. OpenSearch est utile pour visualiser en aval, mais pas pour la collecte immédiate.



# **Question 17**  
Une institution veut centraliser les logs d’applications dans un moteur de recherche sans configuration manuelle.

**Réponse choisie** : C. Kinesis Data Firehose  
**Justification** : Firehose peut livrer automatiquement les logs vers OpenSearch, avec regroupement, transformation et livraison automatique. Glue et EMR sont pour des traitements lourds. Streams demande de gérer les consommateurs.


# **Question 18**  
Une entreprise veut enrichir les données RH avant de les stocker pour analyse.

**Réponse choisie** : B. AWS Lambda  
**Justification** : Lambda peut être déclenché pour enrichir les données à la volée avant qu’elles ne soient stockées. Streams ne fait que diffuser. OpenSearch est une destination. Redshift est un entrepôt, pas un outil de traitement.



# **Question 19**  
Une entreprise souhaite regrouper les résultats d’examens de centres multiples avant stockage dans OpenSearch.

**Réponse choisie** : B. Kinesis Data Firehose  
**Justification** : Firehose peut regrouper les données de manière automatique et les livrer en lots à OpenSearch. Glue et EMR sont trop complexes. Streams nécessiterait une gestion supplémentaire.



# **Question 20**  
Une équipe marketing veut capturer et analyser en direct les clics utilisateurs pour recommandations immédiates.

**Réponse choisie** : A. Kinesis Data Streams  
**Justification** : Streams est capable d’ingérer chaque interaction utilisateur et de les envoyer immédiatement à une application de traitement. Firehose introduit un buffering. Glue et Athena sont inadaptés pour du temps réel.
