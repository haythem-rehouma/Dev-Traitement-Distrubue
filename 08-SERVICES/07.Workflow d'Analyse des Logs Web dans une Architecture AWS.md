# Gestion des Logs Web en Temps Réel avec AWS : EC2, Kinesis, Lambda, OpenSearch, Cognito et CloudWatch  
Workflow d'Analyse des Logs Web dans une Architecture AWS

## Question à laquelle le tutoriel répond :

**Comment fonctionne le workflow d'une architecture AWS intégrant EC2, Kinesis, Lambda, OpenSearch, Cognito, et CloudWatch pour le traitement et l'analyse des logs web ?**

## 1. Workflow du Laboratoire  

Voici le **workflow** détaillé de l'architecture, intégrant les services AWS tels que **EC2**, **Kinesis Data Streams**, **Kinesis Data Firehose**, **Lambda**, **OpenSearch**, et **Cognito**. Chaque étape du processus de génération, ingestion, transformation, et visualisation des logs est décrite ci-dessous :

## 2. Génération des logs par le serveur EC2  

- Le serveur **EC2** héberge un **serveur web** (comme Apache) qui enregistre les **logs d’accès** chaque fois qu'un utilisateur visite le site.  
- Ces logs contiennent des informations comme les **navigateurs utilisés**, les **systèmes d'exploitation**, les **pages visitées**, et les **horaires d'accès**.

## 3. Ingestion des logs dans Kinesis Data Streams  

- Les logs générés par EC2 sont envoyés en **temps réel** à **Kinesis Data Streams** via l'agent Kinesis ou un script configuré sur l'instance.  
- **Kinesis Data Streams** permet d’absorber un grand volume de données et de les préparer pour des traitements ultérieurs.

## 4. Transformation des données avec Lambda (facultatif)  

- **AWS Lambda** peut être déclenché par les données entrantes dans **Kinesis Data Streams**.  
- Lambda enrichit les logs avec des **informations supplémentaires** (comme la géolocalisation), les **nettoie**, ou modifie leur structure.  
- Les données transformées sont ensuite envoyées à **Kinesis Data Firehose** pour la suite du traitement.

## 5. Buffering et livraison avec Kinesis Data Firehose  

- **Kinesis Data Firehose** bufferise et livre les logs (transformés ou non) vers **Amazon OpenSearch Service**.  
- Il garantit une **livraison fiable** et peut appliquer des transformations supplémentaires avec Lambda si nécessaire.

## 6. Indexation et stockage dans Amazon OpenSearch Service  

- **OpenSearch** reçoit les logs livrés par **Kinesis Data Firehose**.  
- Les logs sont **indexés** sous forme de documents JSON pour permettre une recherche rapide et une analyse des données.

## 7. Visualisation avec OpenSearch Dashboards  

- **OpenSearch Dashboards** permet de créer des **visualisations** comme des **diagrammes circulaires** ou des **cartes thermiques** à partir des logs indexés.  
- Ces visualisations aident à comprendre les **comportements des utilisateurs**, les **pages visitées**, etc.

## 8. Accès sécurisé avec Amazon Cognito  

- **Amazon Cognito** gère l'**authentification des utilisateurs** qui souhaitent accéder aux **OpenSearch Dashboards**.  
- Les utilisateurs se connectent via un **formulaire Cognito**, et une fois authentifiés, ils peuvent visualiser les tableaux de bord.  
- **IAM** contrôle les autorisations spécifiques, définissant ce que chaque utilisateur peut voir ou faire.

## 9. Surveillance avec Amazon CloudWatch  

- Les événements et performances tout au long du pipeline (de Kinesis à OpenSearch) sont **surveillés** via **Amazon CloudWatch**.  
- CloudWatch permet de **visualiser les logs** des transformations et surveille les services AWS impliqués, comme Lambda et Firehose.

## 10. Diagramme Simplifié du Workflow  

1. **Utilisateur** accède au site web  
2. **Serveur EC2** génère des logs d’accès  
3. **Kinesis Data Streams** ingère les logs en temps réel  
4. **AWS Lambda** (optionnel) transforme et enrichit les logs  
5. **Kinesis Data Firehose** bufferise et livre les logs vers OpenSearch  
6. **Amazon OpenSearch Service** indexe et stocke les logs  
7. **OpenSearch Dashboards** crée des visualisations  
8. **Amazon Cognito** gère l’authentification des utilisateurs  
9. **Amazon CloudWatch** surveille les événements et performances du pipeline

## 11. Pourquoi cette architecture est-elle avantageuse ?

- **Temps réel** : Kinesis permet de traiter les données en continu sans délai  
- **Flexibilité** : Lambda peut enrichir et transformer les logs à la volée  
- **Sécurité** : Cognito garantit que seuls les utilisateurs authentifiés accèdent aux visualisations  
- **Simplicité** : Firehose gère automatiquement la livraison des données  
- **Analyse puissante** : OpenSearch et Dashboards permettent de visualiser rapidement les données pour obtenir des insights

Cette architecture offre un flux de traitement des données en temps réel, sécurisé et flexible, tout en garantissant des analyses efficaces grâce à **OpenSearch** et **Cognito**.
