# Pipeline AWS : Combiner Kinesis Data Streams, Firehose et Lambda pour Stocker des Données dans OpenSearch  

*Pipeline de Données avec Kinesis, Firehose, Lambda et OpenSearch*

## Question à laquelle le tutoriel répond :

**Comment et pourquoi utiliser une combinaison de Kinesis Data Streams, Kinesis Data Firehose, et AWS Lambda pour ingérer, transformer, et stocker des données dans Amazon OpenSearch Service dans une architecture AWS ?**

## 1. Où se trouve le stockage ?

Le stockage principal se fait dans **Amazon OpenSearch Service**. OpenSearch permet de stocker, indexer et analyser les données. Les journaux d’accès web sont envoyés via **Firehose**, transformés par **Lambda**, puis indexés et stockés de manière persistante dans OpenSearch. Firehose est un service de livraison, pas un système de stockage, mais il peut envoyer les données vers des solutions de stockage comme **OpenSearch**, **Amazon S3**, ou **Amazon Redshift**.

## 2. Pourquoi utiliser Kinesis Data Streams et Kinesis Data Firehose ensemble ?

### Kinesis Data Streams  
**Kinesis Data Streams** est utilisé pour l’ingestion de données en **temps réel**, comme les logs d’accès web. Il gère des volumes de données importants à faible latence et permet de traiter les données au fur et à mesure qu’elles arrivent en continu.

### Kinesis Data Firehose  
**Firehose** simplifie la livraison des données vers des destinations comme OpenSearch. Il gère automatiquement la **bufferisation** et la transformation des données avant de les envoyer en lot. Il permet également de configurer des intégrations avec **Lambda** pour transformer les données en cours de livraison.

## 3. Pourquoi utiliser Kinesis Data Streams avant Firehose ?

1. **Souplesse et traitement personnalisé** : Avec **Kinesis Data Streams**, vous avez un contrôle plus flexible pour traiter les données en temps réel. Vous pouvez configurer plusieurs consommateurs, tels que **Lambda**, qui peuvent lire et transformer les données en temps réel.  
2. **Firehose pour simplifier la livraison** : Une fois les données traitées par Streams, Firehose prend le relais pour **bufferiser** et livrer les données vers OpenSearch de manière plus simple, tout en gérant automatiquement les erreurs et la transformation via **Lambda**.

## 4. Pourquoi Lambda ?

**Lambda** est utilisé pour **transformer** les logs d'accès avant qu’ils ne soient envoyés à OpenSearch. Par exemple, Lambda peut enrichir les données en ajoutant des informations comme la localisation des utilisateurs à partir de leur adresse IP. Lambda permet également de nettoyer ou d’analyser les données en temps réel.

## 5. Pourquoi ne pas utiliser seulement Kinesis Data Streams ?

Bien que **Kinesis Data Streams** soit puissant pour l’ingestion en temps réel, il ne gère pas aussi bien la livraison des données vers des services comme OpenSearch. **Firehose** simplifie la gestion des **batches** et des transformations automatiques, ce qui rend le processus de livraison plus fluide et plus fiable.

## 6. Conclusion

- **Kinesis Data Streams** : utilisé pour l’ingestion et le traitement en temps réel des données  
- **Kinesis Data Firehose** : utilisé pour **bufferiser** et livrer les données dans **OpenSearch**  
- **Lambda** : utilisé pour enrichir ou transformer les données avant leur stockage

La combinaison de ces services permet de traiter des **flux de données en temps réel** et de garantir une livraison fiable vers une plateforme d'analyse comme **OpenSearch**

