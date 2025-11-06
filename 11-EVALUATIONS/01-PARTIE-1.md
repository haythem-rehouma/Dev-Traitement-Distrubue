# Quiz — Kinesis, Firehose, AWS, OpenSearch/Elasticsearch (20)


**Consignes.** 

> Cochez **toutes** les réponses exactes.

> Une question peut avoir **0, 1 ou plusieurs** bonnes réponses.

> Les choix « **Aucune réponse n’est correcte** » et « **Toutes les réponses sont correctes** » peuvent aussi être valides.



1. Kinesis **Data Streams** est le mieux adapté pour :

* [ ] A) Chargements par lots vers S3
* [ ] B) Streaming avec consommateurs multiples et latence faible
* [ ] C) Migrations de bases relationnelles
* [ ] D) Envoi d’e-mails en masse
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

2. Kinesis **Firehose** :

* [ ] A) Stocke les données durablement comme un stream consultable
* [ ] B) Pousse automatiquement vers cibles (S3/Redshift/OpenSearch/HTTP) avec buffering
* [ ] C) Offre la sémantique exactly-once native
* [ ] D) Nécessite toujours un VPC
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

3. **Data Streams** garantit l’ordre pour une même partition key.

* [ ] A) Vrai
* [ ] B) Faux


4. Combinaison correcte :

* [ ] A) Firehose → consumers concurrents à la carte
* [ ] B) Data Streams → fan-out, consumers indépendants
* [ ] C) Firehose → rétention configurable en jours
* [ ] D) Data Streams → pas de Lambda possible en lecture
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

5. Firehose peut **transformer** les records via :

* [ ] A) AWS Lambda
* [ ] B) Glue DataBrew
* [ ] C) Systems Manager
* [ ] D) CloudFormation
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

6. Buffering Firehose :

* [ ] A) Par taille et/ou temps avant livraison
* [ ] B) Uniquement par taille
* [ ] C) Uniquement par temps
* [ ] D) Pas de buffering
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

7. **Data Streams** peut être une **source** de Firehose.

* [ ] A) Vrai
* [ ] B) Faux


8. Objectif principal d’**OpenSearch/Elasticsearch** :

* [ ] A) Transactions ACID strictes
* [ ] B) Recherche plein texte + analytics quasi temps réel
* [ ] C) Orchestration de conteneurs
* [ ] D) Cache clé-valeur
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

9. Changer le **nombre de shards primaires** d’un index existant se fait par :

* [ ] A) Édition d’un runtime field
* [ ] B) Update direct des settings
* [ ] C) **Reindex** vers un nouvel index
* [ ] D) API de merge des shards primaires
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

10. Les **replicas** servent surtout à :

* [ ] A) Tolérance de panne et débit de lecture
* [ ] B) Réduire la taille disque
* [ ] C) Accélérer l’indexation
* [ ] D) Gérer le mapping automatique
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

11. Amazon **OpenSearch Service** est le nouveau nom d’“Amazon Elasticsearch Service”.

* [ ] A) Vrai
* [ ] B) Faux


12. Différence clé **OpenSearch vs Elasticsearch** (projet) :

* [ ] A) OpenSearch est un fork sous licence permissive; Elasticsearch a changé de licence
* [ ] B) OpenSearch ne supporte pas la recherche plein texte
* [ ] C) Elasticsearch est géré uniquement par AWS
* [ ] D) OpenSearch n’a pas d’API REST
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

13. Gestion du cycle de vie des index :

* [ ] A) OpenSearch : **ISM** (Index State Management)
* [ ] B) Elasticsearch 7.10 OSS : **ILM**
* [ ] C) Les deux partagent exactement la même API ILM
* [ ] D) Aucun des deux


14. Kinesis **Data Streams** : scaler le débit d’écriture surtout en :

* [ ] A) Ajoutant des shards
* [ ] B) Changeant la classe de stockage
* [ ] C) Modifiant la taille des buffers
* [ ] D) Ajoutant des réplicas
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

15. Firehose peut convertir en **Parquet/ORC** avec Glue Schema Registry.

* [ ] A) Vrai
* [ ] B) Faux

16. Dashboards quasi temps réel à partir d’événements :

* [ ] A) Data Streams → Firehose → OpenSearch → Dashboards
* [ ] B) Data Streams → S3 → Glacier
* [ ] C) Firehose → RDS MySQL → QuickSight uniquement
* [ ] D) Data Streams → Redshift Spectrum sans stockage
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

17. Sécurité côté OpenSearch/Elasticsearch managé AWS (choix qui s’applique) :

* [ ] A) IAM, KMS (repos), TLS (transit), VPC endpoints
* [ ] B) Pas de chiffrement supporté
* [ ] C) Uniquement des ACL IP
* [ ] D) Uniquement des users locaux
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

18. **Enhanced Fan-Out** dans Data Streams fournit un débit de lecture dédié par consommateur.

* [ ] A) Vrai
* [ ] B) Faux

19. Firehose **ne convient pas** si vous avez besoin de :

* [ ] A) Multi-consommateurs indépendants
* [ ] B) Livraison gérée vers S3
* [ ] C) Buffering puis compression
* [ ] D) Transformation Lambda
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes

20. Reprocess/replay historique : meilleure approche :

* [ ] A) Data Streams avec rétention + copie vers S3
* [ ] B) Firehose sans S3
* [ ] C) OpenSearch uniquement
* [ ] D) CloudWatch uniquement
* [ ] E) Aucune réponse n’est correcte
* [ ] F) Toutes les réponses sont correctes


