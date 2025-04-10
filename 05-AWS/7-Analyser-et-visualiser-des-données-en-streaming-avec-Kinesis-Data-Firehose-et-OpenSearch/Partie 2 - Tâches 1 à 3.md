---
# Partie 2 : Infrastructure du labo et Tâches 1 à 3
---

## **Infrastructure du laboratoire**

L’architecture est conçue pour **analyser les données en streaming** et comprend les composants suivants :

- Une **instance EC2** exécutant un serveur web, située dans un **sous-réseau public**.
- Le site web contient les fichiers suivants (disponibles via un fichier .zip) :
  - `httpd.conf` : fichier de configuration d’Apache.
  - `agent.json` : fichier de configuration pour connecter le serveur web à un flux de livraison Kinesis.
  - `search.php` : page de recherche produit.
  - `recommendation.php` : page de recommandation produit.
  - `echo.php`, `kindle.php`, `firetvstick.php` : pages produits.

- Un **flux Kinesis Data Firehose** qui capture les logs du serveur web pour les écrire dans un cluster OpenSearch.
- Une **fonction Lambda** associée au flux, chargée de transformer/enrichir les données.
- Un **cluster OpenSearch Service** pour indexer et stocker les données.
- Un **OpenSearch Dashboards** pour construire des visualisations.
- **Amazon Cognito** pour l’authentification, et **IAM** pour l’autorisation des accès aux tableaux de bord.

<br/>
<br/>


# 01 - **Tâche 1 : Vérification de l’instance EC2 et de sa configuration de sécurité**

###  Étapes :

1. **Obtenir l’adresse IP publique** de l’instance EC2 :
   - Accédez à la **console EC2** depuis AWS Management Console.
   - Dans la section **Instances**, sélectionnez l’instance nommée **OpenSearch Demo**.
   - Copiez l’adresse IPv4 publique (bouton de copie disponible).

2. **Examiner la configuration de sécurité** :
   - Dans la page de l’instance, cliquez sur l’onglet **Sécurité**.
   - Cliquez sur le rôle **IAM** `OsDemoWebserverIAMRole`.
   - Examinez la **politique IAM** `OsDemoWebserverIAMPolicy1` :
     - Elle permet à l’instance EC2 de lire/écrire dans le **bucket S3 TempS3Bucket**.
   - Vous pouvez aussi examiner les autres politiques attachées à ce rôle si cela vous intéresse.

**Félicitations !** Vous avez examiné la configuration sécurisée de l’instance EC2 utilisée pour ce laboratoire.

<br/>
<br/>


# 02 - **Tâche 2 : Vérification du flux de livraison Kinesis Data Firehose**

### Étapes :

1. Ouvrir la **console Kinesis** (attention : choisir **Kinesis**, pas Kinesis Video ou Data Analytics).
2. Dans le menu de gauche, sélectionnez **Amazon Data Firehose**.
3. Cliquez sur le lien **os-demo-firehose-stream**.

### Points à observer :

- **Source** : `Direct PUT` → l’agent Kinesis sur Linux pousse directement les logs.
- **Destination** : `Amazon OpenSearch Service`.
- **Surveillance** (Monitoring) activée.
- **Transformation** via **Lambda** : fonction `os-demo-lambda-function`.

**Cette fonction Lambda enrichit les logs** :
- Exemple : détermine la **localisation géographique** du visiteur à partir de son adresse IP.

**Félicitations !** Vous avez examiné la configuration complète du flux Kinesis Data Firehose.

<br/>
<br/>


## 03 - **Tâche 3 : Vérification du cluster OpenSearch Service**

###  Étapes :

1. Dans l’onglet **Configuration** du flux Firehose, cliquez sur le lien de domaine `os-demo`.
2. La console OpenSearch s’ouvre.
3. Copiez l’**URL d’OpenSearch Dashboards** pour un usage ultérieur.

### Remarques :

- Si l’état du **cluster est jaune**, ce n’est **pas une erreur** :
  - Cela signifie que tous les fragments primaires sont alloués, mais **au moins une réplique ne l’est pas**.
  - Le cluster du labo est **mononode**, donc ce comportement est attendu.

### Sécurité :

- Onglet **Configuration de sécurité** → Politiques d’accès :
  - Rôle `os_demo_firehose_delivery_role`
  - Rôle `osdemocognitoauthuserrole`

#### Examiner ces rôles dans IAM :
- Le rôle `osdemocognitoauthuserrole` permet à OpenSearch d’utiliser Cognito pour l’authentification.
- Le rôle `os_demo_firehose_delivery_role` permet à OpenSearch :
  - D’accéder à S3,
  - D’utiliser Lambda,
  - De créer des logs CloudWatch,
  - Et de configurer le domaine OpenSearch.

**Félicitations !** Vous avez compris la configuration du cluster OpenSearch et ses permissions.
