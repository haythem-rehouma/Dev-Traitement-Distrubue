# **Correction de l’Examen Mi-Session (Partie 2) – Systèmes Distribués et Big Data**  


# **QUESTION 1 – Architecture de Systèmes Distribués**  
### **Théorème CAP et compromis dans les bases de données distribuées**  

Le **théorème CAP**, formulé par Eric Brewer, affirme qu’un système distribué ne peut garantir simultanément **trois propriétés** :  
1. **Cohérence (Consistency, C)** : Tous les nœuds du système voient les mêmes données à un instant donné.  
2. **Disponibilité (Availability, A)** : Chaque requête reçoit une réponse, même en cas de panne de certains nœuds.  
3. **Tolérance au Partage (Partition Tolerance, P)** : Le système continue à fonctionner même si la communication entre certains nœuds est interrompue.  

Un système distribué ne peut garantir **au maximum que deux** de ces propriétés en même temps. Cela entraîne des **compromis CAP**, qui influencent la conception des bases de données distribuées :  

| **Type de base de données** | **Compromis** | **Exemple** |
|-----------------------------|--------------|-------------|
| **CP (Cohérence + Tolérance au Partage)** | Sacrifie la disponibilité. | **HBase, MongoDB (mode strict), Redis (mode cluster)** |
| **AP (Disponibilité + Tolérance au Partage)** | Sacrifie la cohérence stricte. | **Cassandra, DynamoDB, CouchDB** |
| **CA (Cohérence + Disponibilité)** | Impossible dans un réseau partitionné. | **Systèmes centralisés (ex: PostgreSQL sur un seul serveur)** |

### **Exemple concret : Apache Cassandra (AP)**
Apache Cassandra est conçu pour des applications nécessitant **une disponibilité élevée et une tolérance aux pannes réseau**. Il privilégie la **disponibilité** en acceptant que les données puissent être temporairement inconsistantes entre les nœuds. La cohérence est ensuite assurée via un mécanisme de **réplication asynchrone et d'anti-entropy** (réconciliation des données).  



# **QUESTION 2 – Microservices et Communication**  
### **Avantages et défis de l’architecture microservices**  

L’architecture microservices décompose une application en **services indépendants** qui communiquent via des API.  

✅ **Avantages :**  
- **Scalabilité** : Chaque service peut être dimensionné indépendamment.  
- **Déploiement rapide** : Mise à jour d’un service sans impacter les autres.  
- **Flexibilité technologique** : Chaque service peut utiliser un langage ou un framework différent.  
- **Résilience** : Une panne d’un service n’entraîne pas l’arrêt total du système.  

❌ **Inconvénients :**  
- **Complexité** : Gestion accrue des interconnexions et des versions.  
- **Latence réseau** : Chaque appel inter-service ajoute un délai.  
- **Monitoring difficile** : Multiplication des logs et des erreurs à surveiller.  
- **Gestion des transactions** : Difficile d’assurer une cohérence ACID sur plusieurs services.  

### **Moyens de communication entre microservices**  

Les microservices peuvent communiquer via :  
1. **API REST (HTTP/HTTPS)** – Simple, mais forte latence.  
2. **gRPC (Remote Procedure Call)** – Rapide et optimisé pour la performance.  
3. **Message Brokers (Kafka, RabbitMQ, AWS SQS)** – Utilisé pour une architecture event-driven.  
4. **GraphQL** – Permet d’interroger plusieurs services via une seule requête.  

### **Stratégies pour gérer les échecs de communication**  

1. **Timeouts et Retries** : Limiter le temps d’attente et retenter en cas d’échec.  
2. **Circuit Breaker (ex: Resilience4j, Hystrix)** : Stopper les appels vers un service en panne.  
3. **Fallbacks (Plan B)** : Fournir une réponse de secours en cas d’échec.  
4. **Eventual Consistency** : Synchronisation asynchrone des services avec des événements.  



# **QUESTION 3 – Solutions de Stockage et de Traitement en Environnements Distribués**  
### **(A) Stockage distribué**  

Le **stockage par blocs** découpe les données en fragments distribués sur plusieurs serveurs, assurant **tolérance aux pannes et haute disponibilité**.  

**Exemples de systèmes de stockage distribués :**  

| **Système** | **Avantages** | **Inconvénients** |
|------------|-------------|-----------------|
| **HDFS (Hadoop Distributed File System)** | Réplication des données, tolérant aux pannes | Faible performance en accès aléatoire |
| **Ceph** | Accès objet, bloc et fichier, évolutivité automatique | Configuration complexe |

---

### **(B) Traitement des données distribuées**  

**Comparaison de frameworks :**  

| **Framework** | **Architecture** | **Avantages** | **Inconvénients** |
|--------------|----------------|--------------|----------------|
| **Hadoop MapReduce** | Basé sur fichiers (HDFS) | Fiable pour gros volumes | Latence élevée |
| **Apache Spark** | In-memory (RDD) | Très rapide, support du streaming | Gourmand en RAM |



# **QUESTION 4 – Écosystème Big Data**  
### **MapReduce vs DAG (Directed Acyclic Graph)**  

📌 **MapReduce**  
- Exécute les tâches de manière **séquentielle** (map → shuffle → reduce).  
- Stocke les données intermédiaires sur disque, ce qui ralentit le traitement.  
- **Exemple** : **Hadoop MapReduce** pour du traitement batch.  

📌 **DAG (Graphe Acyclique Dirigé)**  
- Définit un pipeline optimisé où **les tâches peuvent s’exécuter en parallèle**.  
- Les données intermédiaires sont **traitées en mémoire** (plus rapide).  
- **Exemple** : **Apache Spark**, qui optimise l’ordre d’exécution des tâches.  

| **Critère** | **MapReduce** | **DAG (Spark, Flink)** |
|------------|--------------|----------------|
| **Latence** | Élevée (stockage disque) | Faible (in-memory) |
| **Flexibilité** | Rigide | Adaptable |
| **Efficacité** | Bonne sur gros fichiers | Excellente sur flux rapides |

✅ **Conclusion** : DAG est plus rapide et efficace pour des **analyses interactives** et du **streaming**. MapReduce reste pertinent pour des **traitements batch massifs** sur disque.  



# **QUESTION 5 – Systèmes de Stockage Distribués**  
### **Caractéristiques d’un système de fichiers distribué fiable**  

1. **Réplication des données** : Chaque fichier est copié sur plusieurs nœuds.  
2. **Tolérance aux pannes** : Fonctionne malgré la perte de certains nœuds.  
3. **Consistance des métadonnées** : Empêche les incohérences.  
4. **Évolutivité** : Ajout dynamique de serveurs sans perturber le système.  

### **Impact de la réplication et du partitionnement**  

📌 **Réplication**  
- Sauvegarde plusieurs copies des données pour éviter la perte.  
- Exemples : HDFS stocke chaque bloc **en 3 copies**.  

📌 **Partitionnement**  
- Divise les données entre plusieurs serveurs pour améliorer la rapidité.  
- Exemples : Cassandra répartit les données avec une fonction de hash.  

**Exemple concret : Google File System (GFS)**  
- Gère des **fichiers massifs** via des "chunks" répliqués.  
- Les nœuds "Master" et "Chunkserver" assurent **scalabilité et tolérance aux pannes**.  



# **Conclusion**  

- Cette correction détaillée vous fournit des explications pédagogiques, des comparaisons précises et des exemples concrets pour chaque question. 
- Pour exceller, il est essentiel de structurer vos réponses avec **des définitions claires, des exemples illustratifs et des tableaux comparatifs**.  
