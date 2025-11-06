# 1 - Question 

À partir de ce que vous avez vu en cours, proposez un exemple d'architecture d’une pipeline de données pour l’ingestion, le traitement, le stockage et la mise à disposition d’analyses. Vous **ne codez rien** : vous **dessinez** et **justifiez** vos choix.

# 2 - Contexte 

* **A. Streaming temps quasi réel** : événements (logs, clics) 10–50 k/s, latence cible < 2 min vers la recherche/BI.
* **B. Traitement par lots** : fichiers journaliers (~100 GB/jour) déposés sur un data lake, agrégations et indexation.

# 3 - Exemples de technologies

1. **Ingestion** : Kinesis Data Streams/Firehose, Kafka, **ou** dépôt par lots (S3).
2. **Traitement** : Flink/Spark/Kinesis Data Analytics/Lambda (map-filter-enrich).
3. **Stockage** : S3 avec zones **bronze/silver/gold** et **partitionnement**.
4. **Indexation/recherche** : Elasticsearch/OpenSearch pour requêtes et tableaux de bord.
5. **Fiabilité & observabilité** : métriques/alertes, **DLQ**, **replay**, **idempotence**.
6. **Gouvernance & sécurité** : contrat de schéma, chiffrement au repos/en transit, IAM.

> Vous pouvez penser à **Kinesis Firehose** , Kafka, les step functions de AWS, **Elasticsearch**,... mais tout équivalent cohérent est accepté.

# 4 - Livrables

* **L1. Description du problème à résoudre** 
* **L2. Diagramme** de la pipeline (Draw.io/diagrams.net **ou** Markdown).
* **L3. Justification** (≈250–700 mots) des choix techniques (latence, débit, coût, reprise, gouvernance).



> **Note – Originalité exigée.** Il est **interdit de copier-coller** les pipelines vus en cours. Vous devez proposer **une architecture nouvelle** ou **améliorer de façon substantielle** une variante (ex. nouvelles sources, autre moteur de traitement, schéma/partitions repensés, ...).
Tout diagramme manifestement recopié **sera pénalisé** et pourra être **rejeté**.



<br/>

# 5 - Grille d'évaluation

| Section                                                            |  Points |
| ------------------------------------------------------------------ | ------: |
| **Originalité** (idée nouvelle ou nette amélioration)              |  **35** |
| **Présentation du diagramme** (lisible, clair, complet)            |  **25** |
| **Cohérence technique** (ingestion → traitement → stockage → expo) |  **20** |
| **Fiabilité & sécurité** (DLQ/retry, replay, IAM/chiffrement)      |  **10** |
| **Justification** (250–700 mots, choix expliqués)                  |  **10** |
| **Total**                                                          | **100** |

**Pénalités**

* Copie d’un schéma vu en cours : **–60** (et 0 en Originalité).
* Diagramme illisible : **–20 à –30**.
* Justification trop courte/hors sujet : **–5**.

**Note 1 – Originalité exigée.** Pas de copier-coller ; proposez quelque chose de **nouveau** ou **amélioré**.

**Note 2  — Partie libre**

> Vous êtes **libres de choisir** la pipeline et les technologies que vous voulez. Toute architecture **cohérente** (streaming, batch ou **hybride**) est acceptée, y compris des solutions **on-prem**, **cloud** (AWS/Azure/GCP) ou **open-source**. Les **équivalences** sont permises (remplacez n’importe quel composant par un autre de rôle similaire). L’important : **clarté du schéma** et **raisonnement** derrière vos choix.

