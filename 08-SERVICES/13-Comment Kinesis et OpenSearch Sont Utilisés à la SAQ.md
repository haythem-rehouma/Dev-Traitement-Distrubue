# Exemple de l'Architecture AWS avec la SAQ (Société de l'Assurance Automobile du Québec)  
Comment Kinesis et OpenSearch Sont Utilisés à la SAQ (Analogie avec le Service Client)

## Question à laquelle le tutoriel répond :

**Comment fonctionne une architecture AWS avec Kinesis Data Streams, Firehose, Lambda et OpenSearch, expliquée avec l'exemple de la SAQ (Société de l'assurance automobile du Québec) ?**

## 1. Où se trouve le stockage ?  

Dans cette analogie, **OpenSearch** est comme une **grande base de données** à la **SAQ**, où l’on garde une trace des **résultats de chaque examen** ou **service** rendu. Chaque personne qui passe un examen de conduite ou obtient des plaques d’immatriculation a ses informations stockées dans ce système. Cela permet de retrouver facilement les données plus tard, par exemple, pour savoir combien de personnes ont passé un examen ou obtenu leurs plaques.

## 2. Pourquoi utiliser Firehose et Streams ensemble ?

### Kinesis Data Streams (La file d'attente)  
Pense à **Kinesis Data Streams** comme une **file d'attente** à la SAQ. Chaque personne qui arrive pour un service se met dans cette file en **temps réel**. Chaque nouvelle personne (ou donnée) est capturée instantanément. Les personnes (ou données) arrivent en continu, et Streams permet de **gérer ce flux** sans arrêt.

### Kinesis Data Firehose (Le coordinateur qui regroupe et guide les gens)  
**Kinesis Data Firehose** est comme un **coordinateur** à la SAQ. Son rôle est de **regrouper plusieurs personnes** qui attendent dans la file et de s'assurer qu'elles sont prêtes avant de les accompagner **ensemble** au bon guichet. Plutôt que d’envoyer chaque personne une par une, Firehose attend qu'un groupe soit formé et les **guide ensemble** vers leur service (par exemple, vers l’examen de conduite ou la délivrance de plaques). Cela permet d’envoyer les données de manière **organisée et efficace** vers **OpenSearch**.

## 3. Pourquoi utiliser Kinesis Data Streams avant Firehose ?

1. **Kinesis Data Streams (file d'attente)** : Streams capture les arrivées **en temps réel**, comme une file d’attente qui ne s’arrête jamais. Il permet de ne pas perdre de données, même si elles arrivent constamment.  
2. **Firehose (coordinateur pour regrouper)** : Après la file d’attente, **Firehose** regroupe les personnes (ou les données) et les envoie **en lot** au service concerné (ici, OpenSearch). Cela simplifie la gestion et optimise le traitement.

## 4. Pourquoi Lambda (L'assistant) ?  

**Lambda** est comme un **employé de la SAQ** qui vérifie les informations des personnes dans la file d’attente avant qu’elles ne passent leur examen. Par exemple, Lambda vérifie si les documents sont bien remplis, ou ajoute des informations comme l’adresse ou la ville avant d’envoyer les personnes vers le bon service. Cela permet de **nettoyer et enrichir** les données avant qu’elles ne soient stockées dans OpenSearch.

## 5. Pourquoi ne pas utiliser seulement Kinesis Data Streams ?  

Si tu n’utilises que **Kinesis Data Streams**, chaque personne (ou donnée) serait envoyée directement au service concerné **une par une**. Cela peut fonctionner, mais ce serait **moins efficace**. Envoyer les données une par une ralentit le traitement.

**Firehose** regroupe ces personnes (ou données) et les envoie **par lots**, ce qui rend le processus plus rapide et mieux organisé. De plus, Firehose gère les erreurs et s'assure que tout est bien en ordre avant l’envoi.

## 6. Conclusion simplifiée

- **Kinesis Data Streams** est comme une **file d'attente** continue où chaque personne (ou donnée) est capturée dès son arrivée  
- **Firehose** est un **coordinateur** qui regroupe plusieurs personnes avant de les envoyer pour traitement (vers OpenSearch)  
- **Lambda** est l'**assistant** qui vérifie les informations dans la file avant qu’elles ne soient envoyées pour être traitées

Ensemble, ces services garantissent que les données (ou les personnes) sont **capturées**, **vérifiées**, et **organisées** avant d’être envoyées dans le système de stockage ou de traitement (ici **OpenSearch**)
