
**1. Quelles étapes font partie de chaque pipeline de données moderne ? (Sélectionnez TROIS réponses.)**

- [ ] Analyse et visualisation  
- [ ] Ajustement du schéma  
- [ ] Stockage et traitement  
- [ ] Étiquetage et balisage des ressources  
- [ ] Compression Parquet et GZIP  
- [ ] Ingestion  

---

**Réponse correcte :**

- ✅ Ingestion  
- ✅ Stockage et traitement  
- ✅ Analyse et visualisation  

---

**Explication :**  
Chaque pipeline de données moderne comprend typiquement les étapes suivantes :
1. **Ingestion** : collecter les données depuis des sources variées.
2. **Stockage et traitement** : transformer, nettoyer et stocker les données.
3. **Analyse et visualisation** : interpréter les données pour en tirer des insights.

Les autres éléments peuvent faire partie de certains pipelines spécifiques mais ne sont pas systématiques à **tous** les pipelines.



<br/>

**2. Quelles options font partie des cinq "V" du Big Data ? (Sélectionnez DEUX réponses.)**

- [ ] Vérifiabilité (Verifiability)  
- ✅ Valeur (Value)  
- ✅ Véracité (Veracity)  
- [ ] Vérité (Verity)  
- [ ] Vérisimilitude (Verisimilitude)  

---

**Réponse correcte :**  
- ✅ Value  
- ✅ Veracity  

---

**Explication :**  
Les **5 V** du Big Data sont :
1. **Volume** – Quantité de données  
2. **Vélocité** (Velocity) – Vitesse de génération et traitement des données  
3. **Variété** (Variety) – Différents types de données (structurées, non structurées)  
4. **Véracité** (Veracity) – Fiabilité et qualité des données  
5. **Valeur** (Value) – Utilité que l’on peut tirer des données

Les autres options (Verifiability, Verity, Verisimilitude) ne font pas partie des cinq V officiels du Big Data.


<br/>

**3. Quel scénario décrit un défi lié à la vélocité (velocity) ?**

- [ ] Les bureaux régionaux envoient le même type de données mais utilisent des formats CSV différents.  
- ✅ Des données de navigation (clickstream) sont collectées depuis un site d’e-commerce pour générer des recommandations en temps réel. Quand le site est surchargé, il y a un délai dans les réponses aux clients.  
- [ ] Le département des ventes veut utiliser une source de données, mais ne connaît pas sa provenance ni son historique.  
- [ ] Les données sont ingérées depuis des sites de vente régionaux, et le traitement par lots échoue la nuit faute d’espace disque.  

---

**Réponse correcte :**  
- ✅ _Clickstream data is collected... there is a delay in returning results to customers._

---

**Explication :**  
La **vélocité** (velocity) fait référence à la **vitesse à laquelle les données sont générées, transférées et traitées**.  
→ Dans ce scénario, la **latence** dans la génération de recommandations en temps réel montre bien un défi lié à la capacité du système à **gérer les flux de données rapides** sans retard.


<br/>

**4. Un data engineer doit indexer par lot de grandes quantités de données textuelles provenant d’un site d’articles, et permettre une recherche approfondie par mots-clés aux utilisateurs via une application. Quel service AWS peut l’aider à accomplir cela ?**

- ✅ Amazon OpenSearch Service  
- [ ] Amazon CloudWatch Logs  
- [ ] Amazon Redshift  
- [ ] Amazon Kinesis Data Analytics  

---

**Réponse correcte :**  
- ✅ **Amazon OpenSearch Service**

---

**Explication :**  
Amazon OpenSearch Service (anciennement Amazon Elasticsearch Service) est **conçu pour indexer, rechercher et analyser de grandes quantités de données textuelles**, notamment via des **recherches par mots-clés, des filtres, et de l’analyse sémantique**. C’est le service le plus adapté pour fournir des **capacités de recherche textuelle avancées** dans une application.


<br/>

**5. Quels types de traitement sont pris en charge par la couche de traitement d’une architecture de données moderne ? (Sélectionnez DEUX réponses.)**

- [ ] Scripting Bash  
- ✅ Traitement en quasi temps réel (Near real-time processing)  
- ✅ Traitement basé sur SQL (SQL-based processing)  
- [ ] Scripting Golang  
- [ ] Traitement de lac de données (Data lake processing)  

---

**Réponse correcte :**  
- ✅ **Near real-time processing**  
- ✅ **SQL-based processing**

---

**Explication :**  
Dans une architecture de données moderne, la couche de traitement est conçue pour :
- Gérer des **flux de données en continu ou en quasi temps réel**, souvent avec des technologies comme Apache Kafka, Kinesis, Flink ou Spark Streaming.
- Supporter **des requêtes analytiques basées sur SQL** via des moteurs comme Apache Spark SQL, Presto, Trino, Redshift, Athena, etc.

Les scripts Bash ou Golang ne représentent pas des types de traitement à l’échelle de la couche de traitement d'une architecture moderne ; ils relèvent plutôt de scripts d’automatisation ponctuels.


<br/>

**6. Un data engineer s'inquiète d'une augmentation du trafic web lors d'une promotion visant à attirer de nouveaux clients. Il souhaite ajouter des instances Amazon EC2 pour gérer cette hausse de la demande et mieux répartir la charge de travail. Quel service serait le PLUS rentable pour accomplir cette tâche ?**

- [ ] Amazon GuardDuty  
- [ ] AWS Application Auto Scaling  
- ✅ AWS Auto Scaling  
- [ ] Amazon DynamoDB  

---

**Réponse correcte :**  
- ✅ **AWS Auto Scaling**

---

**Explication :**  
**AWS Auto Scaling** est la solution la **plus rentable** pour ajuster automatiquement le nombre d’instances EC2 en fonction de la demande. Il permet :
- D’**augmenter ou de réduire automatiquement** les ressources selon des métriques comme l’utilisation du CPU ou le trafic réseau.
- D’**optimiser les coûts** en n’utilisant que les ressources nécessaires à chaque instant.

Les autres services ne sont pas directement liés à la gestion automatique de la capacité EC2 selon la demande :
- **GuardDuty** est un service de sécurité.
- **Application Auto Scaling** est utilisé pour des ressources comme DynamoDB ou ECS, mais pas spécifiquement pour les groupes EC2.
- **DynamoDB** est une base de données, pas un service de scaling EC2.

  
<br/>

**7. Un data engineer a constaté des problèmes de latence et souhaite recréer facilement et en toute sécurité son infrastructure dans une autre région AWS. Quel service AWS peut-il utiliser pour accomplir cette tâche ?**

- [ ] AWS CloudTrail  
- [ ] AWS Key Management Service (AWS KMS)  
- [ ] Amazon CloudWatch  
- ✅ AWS CloudFormation  

---

**Réponse correcte :**  
- ✅ **AWS CloudFormation**

---

**Explication :**  
**AWS CloudFormation** permet de **définir toute une infrastructure sous forme de code** (IaC - Infrastructure as Code). Grâce à cela :
- On peut **reproduire exactement la même infrastructure dans une autre région AWS**, ce qui est idéal pour la migration, la réplication ou le déploiement multi-région.
- C’est **sécurisé, automatisé et reproductible**, avec un seul modèle YAML ou JSON.

Les autres services n’ont pas cette capacité :
- **CloudTrail** : journalisation des appels API.
- **KMS** : gestion des clés de chiffrement.
- **CloudWatch** : surveillance et alertes, pas de déploiement d’infrastructure.
  

<br/>

**8. Quel est l’ordre correct des tâches dans la structuration des données ?**

- ✅ Analyser le fichier source, organiser le stockage, mapper les champs, puis gérer la taille des fichiers.  
- [ ] Organiser le stockage, analyser le fichier source, mapper les champs, puis gérer la taille des fichiers.  
- [ ] Organiser le stockage, analyser le fichier source, gérer la taille des fichiers, puis mapper les champs.  
- [ ] Analyser le fichier source, mapper les champs, gérer la taille des fichiers, puis organiser le stockage.  

---

**Réponse correcte :**  
- ✅ **Parse the source file, organize storage, map fields, and then manage file size.**

---

**Explication :**  
Dans un **processus de structuration des données**, l’ordre logique est :

1. **Parse the source file** – Comprendre le contenu et la structure initiale.
2. **Organize storage** – Préparer l’architecture de stockage (dossiers, partitions, formats).
3. **Map fields** – Associer les colonnes ou les champs aux structures cibles.
4. **Manage file size** – Optimiser pour la performance (split, compression, tailles optimales).

Cet ordre garantit que les données sont traitées, organisées, comprises, puis optimisées efficacement.


<br/>

**9. Que signifie l'acronyme ELT ?**

- [ ] Extract, label, and transform  
- [ ] Examine, label, and transform  
- ✅ Extract, load, and transform  
- [ ] Examine, load, and transform  

---

**Réponse correcte :**  
- ✅ **Extract, Load, and Transform**

---

**Explication :**  
**ELT** est une approche moderne de traitement des données, particulièrement utilisée dans les architectures cloud. Elle consiste à :

1. **Extract** – Extraire les données brutes depuis les sources (bases, APIs, fichiers, etc.).
2. **Load** – Charger ces données directement dans un data warehouse ou un data lake.
3. **Transform** – Effectuer la transformation **après** le chargement, souvent à l’aide de requêtes SQL ou de moteurs comme Spark, pour profiter de la puissance de calcul du système cible.

Cela diffère d'**ETL**, où les transformations sont faites **avant** le chargement.

<br/>

**10. Quel service AWS est conçu pour ingérer des données à partir de systèmes de fichiers ?**

- ✅ AWS DataSync  
- [ ] AWS Data Exchange  
- [ ] Amazon Kinesis Data Firehose  
- [ ] Amazon Cognito  

---

**Réponse correcte :**  
- ✅ **AWS DataSync**

---

**Explication :**  
**AWS DataSync** est un service spécialement conçu pour **transférer rapidement et de manière sécurisée** des données entre **systèmes de fichiers sur site** (comme NFS, SMB) et des services AWS (comme Amazon S3, EFS, FSx). Il est idéal pour l’ingestion de **données basées sur fichiers** vers le cloud.

Les autres services ont des objectifs différents :
- **Data Exchange** : partage de données entre fournisseurs tiers.
- **Kinesis Data Firehose** : ingestion de **flux** de données, pas de fichiers.
- **Cognito** : gestion des identités et authentification, pas d’ingestion de données.
