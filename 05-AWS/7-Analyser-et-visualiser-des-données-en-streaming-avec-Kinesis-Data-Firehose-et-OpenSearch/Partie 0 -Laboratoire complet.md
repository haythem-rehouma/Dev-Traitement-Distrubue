-------------------------------------------------
# **Aperçu du laboratoire et objectifs**
-------------------------------------------------

Les problèmes liés aux mégadonnées nécessitent souvent des solutions en temps réel. Cela correspond à la partie « vélocité » des cinq V des mégadonnées (volume, variété, vélocité, véracité et valeur). Les sources de données courantes pour ces scénarios incluent les flux vidéo, les journaux d'application et les dispositifs d'infrastructure. Les données dans ces scénarios de vélocité sont appelées données de streaming. Amazon Kinesis est une suite de services que vous pouvez utiliser pour analyser des données de streaming.

Dans ce laboratoire, vous utiliserez **Amazon Kinesis Data Streams** pour collecter des données à partir d'un serveur web hébergé dans **Amazon Elastic Compute Cloud (Amazon EC2)**. Vous utiliserez ensuite **AWS Lambda** pour traiter et enrichir les données. Vous analyserez les données en utilisant **OpenSearch Dashboards**, où vous pourrez construire un index, puis visualiser les données pour créer des tableaux de bord. Les utilisateurs métiers pourront accéder aux tableaux de bord via **Amazon Cognito**.

Après avoir terminé ce laboratoire, vous devriez être en mesure de :

- Décrire l'infrastructure du laboratoire dans la **console de gestion AWS**.
- Ingérer des journaux de serveur web dans **Amazon Kinesis Data Firehose** et **Amazon OpenSearch Service**.
- Observer le processus d'ingestion et de transformation des données en utilisant les événements de journalisation dans **Amazon CloudWatch**.
- Construire un index pour ces journaux dans **OpenSearch Service**.
- Visualiser les données en utilisant **OpenSearch Dashboards**, notamment :

  - Créer un graphique circulaire (camembert) illustrant les systèmes d'exploitation et les navigateurs utilisés par les visiteurs pour consulter le site web.
  - Créer une carte thermique (heat map) illustrant comment les utilisateurs sont redirigés vers les pages produits (via la page de recherche ou la page de recommandations).

# **Durée**

Ce laboratoire nécessitera environ 90 minutes pour être complété.

# **Restrictions sur les services AWS**

Dans cet environnement de laboratoire, l'accès aux services et aux actions des services AWS peut être limité à ceux nécessaires pour accomplir les instructions du laboratoire. Vous pourriez rencontrer des erreurs si vous tentez d'accéder à d'autres services ou d'effectuer des actions en dehors de celles décrites dans ce laboratoire.

-------------------------------------------------
# **Scénario**
-------------------------------------------------

L'administrateur du site web de la librairie universitaire souhaite obtenir des informations sur la manière dont les visiteurs interagissent avec le site. Elle utilisait un système de suivi basé sur JavaScript pour afficher des graphiques contenant des informations sur l'activité des utilisateurs. Ces informations incluent l'emplacement des utilisateurs, les navigateurs qu'ils utilisent et s'ils accèdent au site via des appareils mobiles. Les données peuvent également indiquer si les visiteurs atteignent la page produit d'une librairie via une page de recherche ou une page de recommandations.

Le nombre de visiteurs sur le site a augmenté chaque année. Cependant, comme de nombreux visiteurs utilisent des navigateurs qui bloquent les scripts de suivi tiers, elle est maintenant préoccupée par l'exactitude des données concernant l'activité des utilisateurs.

En cherchant une solution pour obtenir des données plus précises, elle est tombée sur l'article **Analyser le comportement des utilisateurs avec Amazon Elasticsearch Service, Amazon Kinesis Data Firehose et Kibana** sur le blog **AWS Database**. Cet article détaille une solution utilisant des données de streaming pour analyser les journaux d'un serveur web et déterminer les modèles d'accès des utilisateurs. L'administratrice web a entendu dire que votre équipe utilise les services AWS pour créer des solutions d'analyse de données. Elle vous contacte pour obtenir des conseils sur la possibilité que l'approche présentée dans l'article puisse fournir les mêmes informations que son système de suivi actuel.

L'article de blog contient des fichiers pour un site web de base avec une page de recherche et une page de recommandations qui orientent les visiteurs vers quelques pages de produits. Vous décidez d'utiliser ce site web de base pour développer une **preuve de concept (POC)** utilisant des données de streaming pour analyser l'activité des utilisateurs du site de la librairie.

La solution utilise **Kinesis Data Firehose** pour ingérer les journaux d'accès au serveur web en continu, puis utilise des fonctions **Lambda** pour enrichir les données. Après avoir utilisé Kinesis et Lambda pour traiter les données, vous pouvez utiliser **OpenSearch Service** pour charger et indexer les données. En utilisant **OpenSearch Dashboards**, vous pouvez créer des visualisations des données afin de fournir des informations sur les visiteurs des sites web de l'université. Vous pouvez partager ces visualisations en utilisant **Amazon Cognito** comme outil d'authentification et **AWS Identity and Access Management (IAM)** pour autoriser l'accès au tableau de bord.

Lorsque vous commencez le laboratoire, l'environnement contiendra les ressources représentées dans le schéma suivant.

![image](https://github.com/user-attachments/assets/0a45b8a8-be38-4577-a997-73e2cb6bf580)

-------------------------------------------------
### **L'infrastructure est conçue pour analyser les données de streaming et se compose des éléments suivants :**
-------------------------------------------------

- **Une instance EC2** qui exécute un serveur web. L'instance se trouve dans un sous-réseau public.

- **Le serveur web** qui fonctionne sur l'instance inclut un site web avec les fichiers suivants. Si vous souhaitez consulter le package du site web, vous pouvez télécharger et ouvrir ce fichier .zip :

    - **httpd.conf** : Fichier de configuration pour le serveur web Apache.
    
    - **agent.json** : Fichier de configuration pour connecter le serveur web à un flux de livraison Kinesis Data Firehose.
    
    - **search.php** : Page où un utilisateur peut rechercher un produit.
    
    - **recommendation.php** : Page qui recommande un produit particulier en fonction de l'historique de recherche de l'utilisateur.
    
    - **echo.php**, **kindle.php**, et **firetvstick.php** : Pages pour trois produits sur le site web.

- **Un flux de livraison Kinesis Data Firehose** qui capture les données de streaming à partir des journaux du serveur web. Vous utiliserez ce flux de livraison pour écrire des données dans un cluster **OpenSearch Service**.

- **Une fonction Lambda** pour transformer les données. Si vous souhaitez consulter la fonction, vous pouvez télécharger et ouvrir ce fichier .zip.

- **Un cluster OpenSearch Service** pour indexer et stocker les données.

- **Une instance OpenSearch Dashboards** pour créer des visualisations de données et obtenir des informations à partir des données.

---

### **À la fin du laboratoire, vous aurez utilisé cette architecture pour effectuer plusieurs tâches. Le tableau après le schéma fournit une explication détaillée de ces tâches en relation avec l'architecture du laboratoire.**

![image](https://github.com/user-attachments/assets/344c5510-8de4-4ff3-ae46-2425257793d7)


-------------------------------------------------
### **Tâches numérotées**
-------------------------------------------------

| **Tâche Numérotée** | **Détail** |
| -------------------- | ---------- |
| **1** | Vous allez examiner la configuration de l'instance EC2 pour le serveur web. Vous examinerez également le rôle **IAM OsDemoWebserverIAMRole** et les politiques **IAM OsDemoWebserverIAMPolicy** pour comprendre les autorisations IAM appliquées au rôle. |
| **2** | Vous allez examiner le flux de données Kinesis qui est configuré pour capturer les journaux d'accès au web des utilisateurs accédant au site web. |
| **3** | Vous allez également examiner la configuration du cluster **OpenSearch Service** utilisé dans le laboratoire. |
| **4** | Ensuite, vous allez configurer l'index **OpenSearch Service**. |
| **5** | Après avoir tout configuré, vous allez parcourir le site web pour générer des journaux d'accès. |
| **6** | Après avoir généré des journaux d'accès, des événements de journaux **CloudWatch** seront également générés dans le compte AWS. Vous examinerez ces journaux pour mieux comprendre comment **Kinesis Data Firehose** ingère ces données et les transmet à une fonction **Lambda** pour un enrichissement supplémentaire. |
| **7** | Vous allez créer un modèle d'index **OpenSearch Service**, nécessaire pour créer des visualisations dans **OpenSearch Dashboards**. |
| **8** | Vous allez créer une visualisation en graphique circulaire (camembert) dans **OpenSearch Dashboards**. |
| **9** | Vous terminerez le laboratoire en créant une visualisation en carte thermique (heat map). |

---

### **Accès à la console de gestion AWS**

1. **En haut de ces instructions, choisissez** « **Démarrer le laboratoire** ».

    - La session de laboratoire démarre.
    - Un chronomètre s'affiche en haut de la page et indique le temps restant dans la session.
    - **Conseil** : Pour actualiser la durée de la session à tout moment, choisissez à nouveau « **Démarrer le laboratoire** » avant que le chronomètre n'atteigne 0:00.
    - Avant de continuer, attendez que l'icône circulaire à droite du lien **AWS** dans le coin supérieur gauche devienne verte.

2. **Pour vous connecter à la console de gestion AWS, choisissez le lien** « **AWS** » **dans le coin supérieur gauche**.

    - Un nouvel onglet du navigateur s'ouvre et vous connecte à la console.
    - **Conseil** : Si un nouvel onglet du navigateur ne s'ouvre pas, une bannière ou une icône se trouve généralement en haut de votre navigateur avec le message indiquant que votre navigateur empêche le site d'ouvrir des fenêtres contextuelles. Choisissez la bannière ou l'icône, puis choisissez « **Autoriser les fenêtres contextuelles** ».


-------------------------------------------------
# **Tâche 1 : Révision de l'instance EC2 et de sa configuration de sécurité**
-------------------------------------------------

Commencez par examiner la configuration de l'instance EC2 pour le serveur web.

1. **Trouvez l'adresse IP publique de l'instance.**

    - Pour accéder à la console **Amazon EC2**, dans la barre de recherche à droite de **Services**, recherchez et sélectionnez **EC2**.
    
    - Dans la section **Resources** (Ressources), choisissez le lien **Instances (running)** (Instances en cours d'exécution).
    
    - Une liste des instances EC2 s'affiche.
    
    - Choisissez le lien **Instance ID** pour l'instance nommée **OpenSearch Demo**.
    
      - **Remarque** : Si cette instance n'est pas encore répertoriée, attendez qu'elle soit disponible.
    
    - Copiez la valeur de l'adresse **Public IPv4** dans un éditeur de texte de votre choix.
    
      - **Conseil** : Pour copier rapidement l'adresse IP, cliquez sur l'icône à gauche de l'adresse IP.
    
    - Vous utiliserez cette adresse IP plus tard dans le laboratoire pour accéder au serveur web en tant que visiteur et générer des journaux d'accès au web.

2. **Examinez les paramètres de sécurité associés à l'instance du serveur web.**

    - Dans la partie inférieure de la page récapitulative de l'instance **OpenSearch Demo**, choisissez l'onglet **Security** (Sécurité).
    
    - Sous **IAM Role** (Rôle IAM), choisissez le lien **OsDemoWebserverIAMRole**.
    
    - La console **IAM** s'ouvre. Plusieurs politiques IAM ont été créées pour contrôler l'accès aux ressources dans ce laboratoire. Pour afficher les détails de chaque politique, vous pouvez cliquer sur l'icône plus à gauche du nom de la politique.
    
    - Choisissez le lien pour **OsDemoWebserverIAMPolicy1**.
    
    - Choisissez l'onglet **JSON**.
    
    - La politique IAM s'affiche au format JSON.
    
    - Examinez la politique.
    
    - Cette politique IAM permet à l'instance EC2 du serveur web de lire et d'écrire dans le **bucket S3 TempS3Bucket**.
    
      - **Remarque** : Il existe d'autres politiques référencées par le rôle **OsDemoWebserverIAMRole**, qui accomplissent d'autres fonctions. Si cela vous intéresse, examinez-les et identifiez ce qu'elles font.

---

**Excellent !** Dans cette tâche, vous avez révisé l'instance EC2 et examiné comment elle est sécurisée.



-------------------------------------------------
### **Tâche 2 : Révision du flux de livraison Kinesis Data Firehose**
-------------------------------------------------

Vous allez maintenant examiner la configuration du flux de livraison Kinesis Data Firehose.

1. **Pour accéder à la console Kinesis**, dans la barre de recherche à droite de **Services**, recherchez et sélectionnez **Kinesis**.

    - **Remarque** : Plusieurs services commencent par « Kinesis », y compris **Amazon Kinesis Data Analytics** et **Amazon Kinesis Video Streams**. Choisissez le service qui s'appelle uniquement « **Kinesis** ».

2. **Examinez la configuration du flux de livraison Kinesis Data Firehose**.

    - Dans le volet de navigation de gauche, choisissez **Delivery streams** (Flux de livraison).
    
    - Choisissez le lien **Name** (Nom) du flux de livraison **os-demo-firehose-stream**.
    
    - Remarquez que dans la section **Delivery stream details** (Détails du flux de livraison) de la page, la **Source** est spécifiée comme **Direct PUT**. **Direct PUT** a été sélectionné car l'instance EC2 exécute **Linux** et utilise **Apache** comme serveur web. Étant donné que l'instance EC2 fonctionne sous Linux, vous pouvez utiliser **Kinesis Agent (Linux)** pour collecter les journaux du serveur web. Pour plus d'informations, consultez les paramètres **Source, Destination and Name settings** dans les flux de livraison Kinesis Data Firehose.
    
    - Remarquez également que la **Destination** est spécifiée comme **Amazon OpenSearch Service**. Le flux de livraison enverra la charge utile à **Amazon OpenSearch Service** afin que vous puissiez analyser les journaux ultérieurement.
    
    - Faites défiler vers le bas dans votre navigateur. Remarquez que **Monitoring** (Surveillance) est déjà activé et génère des métriques pour le flux de livraison.
    
    - Dans la partie inférieure de la page des détails du flux de livraison, choisissez l'onglet **Configuration**, en particulier la section **Transform records** (Transformer les enregistrements).
    
    - La fonction **Lambda os-demo-lambda-function** est associée au flux de livraison et est utilisée pour transformer les journaux d'accès avant de les envoyer au service OpenSearch pour analyse.
    
      - **Remarque** : D'une manière générale, la fonction **Lambda** enrichit les journaux du serveur web avec des informations supplémentaires. Par exemple, la fonction détermine la localisation géographique du visiteur du site en convertissant l'adresse IP du visiteur en emplacement. Vous pouvez examiner les détails de cette fonction Lambda en cliquant sur le lien si vous êtes intéressé.

---

**Félicitations !** Dans cette tâche, vous avez examiné la configuration du flux de livraison Kinesis Data Firehose.



-------------------------------------------------

### **Tâche 3 : Révision du cluster OpenSearch Service**
-------------------------------------------------


Vous allez maintenant examiner la configuration du cluster OpenSearch Service qui a été créé pour vous.

1. **Examinez la configuration du cluster OpenSearch Service.**

    - Dans l'onglet **Configuration** du flux de livraison, dans la section **Destination OpenSearch Service**, sous **Domain** (Domaine), choisissez le lien **os-demo**.

    - La console **OpenSearch Service** s'ouvre.

    - Examinez la configuration du cluster.

    - Dans la section **General information** (Informations générales), copiez l'URL d'**OpenSearch Dashboards** dans un éditeur de texte pour l'utiliser plus tard dans le laboratoire.

    - Vous pourriez remarquer que le domaine OpenSearch est en train de supprimer des ressources. Pour voir la progression, choisissez **View details** (Voir les détails). Attendez la fin avant de continuer.

      - **Remarque** : Vous pourriez remarquer que l'état du cluster est **Yellow** (Jaune). Si vous cliquez sur le lien **Info** à droite de ce statut, un panneau s'ouvre et indique que "L'état du cluster jaune signifie que tous les fragments primaires sont alloués, mais au moins une réplique ne l'est pas. Tous les clusters à nœud unique commencent avec un état jaune." Le cluster utilisé dans ce laboratoire est un cluster à nœud unique ; par conséquent, il aura un état jaune tout au long du laboratoire. Dans un environnement de production, vous configureriez le cluster avec plusieurs nœuds. L'augmentation du nombre de nœuds à 2 ou plus changerait l'état du cluster en vert.

2. **Examinez la configuration de sécurité du cluster OpenSearch Service.**

    - Choisissez l'onglet **Security configuration** (Configuration de la sécurité).

    - Examinez les informations dans la section **Access policy** (Politique d'accès).

    - Cette politique d'accès utilise deux rôles : **os_demo_firehose_delivery_role** et **osdemocognitoauthuserrole**.

    - Vous allez maintenant examiner les détails concernant ces rôles.

3. **Pour accéder à la console IAM**, dans la barre de recherche à droite de **Services**, recherchez et sélectionnez **IAM**.

    - Dans le volet de navigation de gauche, choisissez **Roles** (Rôles).

    - Choisissez le lien **Role name** (Nom du rôle) pour **osdemocognitoauthuserrole**.

    - Examinez la politique d'autorisations associée à ce rôle.

    - Ce rôle permet au domaine OpenSearch d'utiliser **Amazon Cognito** pour authentifier les utilisateurs.

4. **Retournez à la page des rôles**, et choisissez le lien **Role name** pour **os_demo_firehose_delivery_role**.

    - Examinez la politique d'autorisations associée à ce rôle, **OsdemoFirehoseIAMPolicy**.

    - Ce rôle est plus complexe que l'autre, mais dans les grandes lignes, il permet au domaine OpenSearch de :

        - Accéder à **Amazon S3** et lire et écrire des objets.
        
        - Obtenir des fonctions **Lambda** et les utiliser.
        
        - Créer des journaux **CloudWatch**.
        
        - Utiliser le domaine OpenSearch Service pour des actions comme configurer le domaine.

---

**Félicitations !** Dans cette tâche, vous avez examiné la configuration du cluster OpenSearch Service.



-------------------------------------------------
### **Tâche 4 : Configurer un index OpenSearch**
-------------------------------------------------

Dans cette tâche, vous allez vous connecter à OpenSearch Dashboards et configurer un index pour les journaux du serveur web qui sont diffusés à partir de Kinesis Data Firehose.

- **Remarque** : L'indexation est la méthode utilisée par les moteurs de recherche pour organiser les données afin de les récupérer rapidement. La structure résultante est appelée un **index**. Pour plus d'informations sur les index OpenSearch, consultez **Index Data**.

Dans OpenSearch, l'unité de base de données est un document JSON. Au sein d'un index, OpenSearch identifie chaque document à l'aide d'un identifiant unique.




1. **Connexion et modification du mot de passe LabUser.**

    - Ouvrez un nouvel onglet ou une nouvelle fenêtre de votre navigateur et accédez à l'URL **OpenSearch Dashboards** que vous avez copiée précédemment.
    
    - Utilisez les identifiants suivants pour vous connecter :
    
        - **Nom d'utilisateur** : LabUser
        - **Mot de passe** : Passw0rd1!
    
      - **Remarque** : Amazon Cognito héberge cet écran de connexion. OpenSearch Service dépend d'Amazon Cognito pour l'authentification et d'IAM pour l'autorisation.
    
      - Vous serez invité à changer le mot de passe.
    
    - Entrez le nouveau mot de passe suivant : **Passw0rd1!2**
    
    - Choisissez **Envoyer**.
    
    - Si une fenêtre contextuelle vous demande si vous souhaitez **Ajouter des données** ou **Explorer par vous-même**, choisissez **Explorer par vous-même**.
    
    - La page d'accueil d'OpenSearch Dashboards s'affiche.
  

![image](https://github.com/user-attachments/assets/6d736aec-1f8a-44ed-bc76-a96aea2b80a4)


2. **Suppression des journaux du serveur web existants.**

    - Choisissez l'icône de menu (≡), puis choisissez **Dev Tools**.

    - La console **Dev Tools** s'affiche.

    - Dans la zone de console, remplacez le texte JSON existant par la commande SQL suivante :

![image](https://github.com/user-attachments/assets/6eb7fed2-d8b4-40ec-bc6d-5e83f907d577)



```sql
DELETE /apache_logs

```

- Cette commande supprime l'index **apache_logs**, s'il existe déjà, qui est stocké sur l'instance EC2 OpenSearch Service.
- Pour exécuter la commande, choisissez l'icône de flèche bleue.
- La réponse suivante s'affiche :

```json
      {
        "acknowledged": true
      }
```

3. **Création d'un index OpenSearch à l'aide de l'API REST.**

    - **Remarque** : La méthode **PUT** est utilisée pour créer l'index. Pour plus d'informations, consultez **Create Index**.

    - Copiez et collez le texte suivant dans la console :

```json
      PUT apache_logs
      {
          "settings" : {
              "index" : {
                  "number_of_shards" : 10,
                  "number_of_replicas" : 0
              }
          },
          "mappings": {
              "properties": {
                  "agent": { 
                    "type": "text"  
                  },
                  "browser": {
                    "type": "keyword"
                  },
                  "bytes": {
                    "type": "text"
                  },
                  "city": {
                    "type": "keyword"
                  },
                  "country": {
                    "type": "keyword"
                  },
                  "datetime": {
                    "type": "date","format":"dd/MMM/yyyy:HH:mm:ss Z"
                  },
                  "host": {
                    "type": "text"
                  },
                  "location": {
                    "type": "geo_point"
                  },
                  "referer": {
                    "type": "text"
                  },
                  "os": {
                    "type": "keyword"
                  },
                  "request": {
                    "type": "text"
                  },
                  "response": {
                    "type": "text"
                  },
                  "webpage": {
                    "type": "keyword"
                  },
                  "refering_page": {
                    "type": "keyword"
                  }
              }
          }
      }
```

- **Remarque** : Copiez la commande exactement comme elle est écrite, sinon vous risquez de recevoir des erreurs lors de son exécution. Vous devriez voir le texte exact montré dans l'image suivante.

4. **Exécution de la commande.**

    - Pour exécuter la commande, choisissez l'icône de flèche bleue.

![image](https://github.com/user-attachments/assets/24a41d6b-6e16-40ab-9fac-c47ab6ac2d9f)


- La réponse suivante s'affiche :

```json
      {
        "acknowledged": true,
        "shards_acknowledged": true,
        "index": "apache_logs"
      }
```

### **Analyse :**
Cette commande a créé un nouvel index appelé **apache_logs**. Lorsque les journaux d'accès du serveur web seront mis à jour en raison du trafic sur le site, le flux de livraison **Kinesis Data Firehose** remplira le cluster **OpenSearch Service** avec des données en fonction des mappages dans la commande. La commande définit les types de données pour les champs dans les fichiers journaux du serveur au sein de la base de données OpenSearch Service. Maintenant que vous avez un index, vous pouvez générer des journaux d'accès web et créer des visualisations basées sur les données de cet index.

---

**Félicitations !** Dans cette tâche, vous avez réussi à créer un nouvel index dans OpenSearch.

---

### **Tâche 5 : Générer des journaux d'accès au serveur web**

Maintenant que vous avez créé l'index, il est temps de tester la capacité de votre infrastructure à diffuser les journaux du serveur web via un flux de livraison Kinesis Data Firehose vers OpenSearch Service.

Pour commencer le processus de test, vous devez d'abord générer des journaux sur le serveur web.

1. **Ouvrez un nouvel onglet ou une nouvelle fenêtre de votre navigateur** et accédez à l'URL suivante. Remplacez `<PUBLIC-IP>` par l'adresse IP publique que vous avez copiée précédemment :

```json
    http://<PUBLIC-IP>/main.php
```

2. **Générez des journaux du serveur web en utilisant plusieurs navigateurs web.**

    - Naviguez sur le site web et accédez à ses différentes pages.
    
    - Utilisez un navigateur web différent pour accéder au site et le parcourir.

      - **Remarque** : Pour cette preuve de concept (POC), vous avez besoin de données provenant d'au moins deux navigateurs web. Pour diversifier les résultats, vous pouvez utiliser plusieurs appareils pour accéder et naviguer sur le site web.

---

**Excellent !** Dans cette tâche, vous avez alimenté les journaux du serveur web avec des données afin de pouvoir les analyser plus tard avec OpenSearch Dashboards.

---


### **Tâche 6 : Observation des événements de journaux dans CloudWatch**

Maintenant que vous avez généré les journaux d'accès du serveur web, il est important de comprendre comment fonctionnent les phases d'ingestion et de traitement des données avec votre POC et l'infrastructure qui le prend en charge. Cette partie de l'infrastructure est automatisée de telle manière que les journaux d'accès des visiteurs sont extraits du serveur web dans un flux de données Kinesis. Les journaux sont envoyés à **Kinesis Data Firehose**, puis une fonction **Lambda** traite et enrichit les données. Après l'enrichissement, les journaux sont renvoyés à **Kinesis Data Firehose**, puis à **OpenSearch Service** pour une analyse ultérieure. Ce processus se déroule en millisecondes et est transparent pour les parties prenantes qui utiliseront **OpenSearch Dashboards** pour analyser les journaux.

En tant qu'ingénieur des données, comment pouvez-vous observer l'ingestion, le traitement et la transformation des données dans votre POC ? **CloudWatch Logs** peut vous aider à voir ce qui se passe.

Dans cette tâche, vous allez examiner les informations des journaux CloudWatch qui ont été générées lorsque les journaux d'accès du serveur web ont été ingérés dans Kinesis Data Firehose, puis transformés et enrichis par Lambda.

- **Remarque** : Dans ce laboratoire, les événements du flux de travail sont combinés dans un groupe de journaux nommé **/aws/lambda/aes-demo-lambda-function**. Les deux événements clés que vous allez examiner dans ce groupe de journaux sont **Incoming Record from Kinesis Firehose** et **Transformed Record going back to Kinesis Firehose**.

1. **Accédez au groupe de journaux.**

    - Revenez à l'onglet ou à la fenêtre où la console de gestion AWS est ouverte.

    - Pour accéder à la console **CloudWatch**, dans la barre de recherche à droite de **Services**, recherchez et sélectionnez **CloudWatch**.

    - Dans le volet de navigation de gauche, choisissez **Logs** > **Log groups** (Groupes de journaux).

    - Choisissez le lien **Log group** (Groupe de journaux) pour le groupe **/aws/lambda/aes-demo-lambda-function**.

2. **Examinez un flux de journaux.**

    - La section **Log streams** (Flux de journaux) répertorie plusieurs flux de journaux.

    - Choisissez le lien **Log stream** pour le flux de journaux le plus récent.

    - Les événements dans le flux de journaux sont répertoriés comme montré dans l'image suivante. Chaque événement inclut un horodatage (timestamp) au moment où il a été créé, ainsi qu'un message avec les détails de l'événement.

![image](https://github.com/user-attachments/assets/ee21949a-1c13-466f-ae26-17fdf18d1382)


    - Développez l'un des journaux contenant un message qui commence par **Incoming Record from Kinesis Firehose**.

    - Les détails de l'événement sont similaires aux suivants :

```json
      Incoming Record from Kinesis Firehose : 
      {
          "host": "68.206.xxx.xxx",
          "ident": null,
          "authuser": null,
          "datetime": "09/Jul/2022:02:43:51 +0000",
          "request": "GET /firetvstick.php HTTP/1.1",
          "response": "200",
          "bytes": "305",
          "referer": "http://18.207.217.39/recommendation.php",
          "agent": "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1"
      }
```


- **Analyse** : Cet événement dans **CloudWatch Logs** a été généré lorsque le journal d'accès du serveur web a été envoyé depuis l'instance EC2 et ingéré dans Kinesis Data Firehose. Après ingestion, les données des journaux d'accès sont transmises à Lambda, où une fonction transforme et enrichit les données.

3. **Développez l'un des journaux contenant un message qui commence par** **Transformed Record going back to Kinesis Firehose**.

    - Les détails de l'événement sont similaires aux suivants :

```json
      Transformed Record going back to Kinesis Firehose : 
      {
          "host": "68.206.xxx.xxx",
          "ident": null,
          "authuser": null,
          "datetime": "09/Jul/2022:02:43:51 +0000",
          "request": "GET /firetvstick.php HTTP/1.1",
          "response": "200",
          "bytes": "305",
          "referer": "http://18.207.217.39/recommendation.php",
          "agent": "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1",
          "webpage": "firetvstick",
          "refering_page": "recommendation",
          "city": "Corpus Christi",
          "country": "United States",
          "browser": "Mobile Safari",
          "os": "iOS",
          "location": {
              "lat": 27.7693,
              "lon": -97.444
          }
      }
```


- **Analyse** : Remarquez comment les données sont transformées avec des champs supplémentaires et enrichies avec la localisation du visiteur du site (en utilisant l'adresse IP du visiteur).
  

4. **Développez l'un des journaux contenant un message qui commence par** **REPORT RequestId**.

    - Les détails de l'événement sont similaires aux suivants :


```text
      REPORT RequestId: d0ad7487-bcce-4881-a030-6dea03c100bd  Duration: 473.96 ms Billed Duration: 474 ms Memory Size: 128 MB Max Memory Used: 51 MB  Init Duration: 454.88 ms
```


- **Analyse** : Cet événement apparaît périodiquement dans le flux de journaux et inclut des informations d'utilisation pour Kinesis Data Firehose et Lambda. Par exemple, la **Billed Duration** (Durée facturée) est le temps qu'il faut à Lambda pour traiter un groupe de journaux d'accès web et les enrichir. Avec Kinesis Data Firehose et Lambda, les clients AWS sont facturés pour ce qu'ils utilisent.

![image](https://github.com/user-attachments/assets/749002f4-4705-4188-b310-f9a0d94ef147)


**Super !** Vous avez examiné les événements des journaux CloudWatch associés au flux de livraison Kinesis Data Firehose et à la fonction Lambda de votre POC. Le flux de livraison et la fonction Lambda ont automatiquement ingéré et transformé les journaux d'accès web que vous avez créés en naviguant sur le site. Vous pouvez consulter les événements des journaux dans CloudWatch pour voir le processus en cours et obtenir des informations sur l'utilisation des ressources.

---

### **Tâche 7 : Création du modèle d'index OpenSearch Service**

Maintenant que vous avez collecté des journaux d'accès et observé leur traitement, vous allez créer un modèle d'index dans OpenSearch Service pour les données. OpenSearch utilise des modèles d'index pour identifier les index OpenSearch que vous souhaitez utiliser pour créer des visualisations. Dans ce cas, vous utiliserez le champ **datetime** pour filtrer les données qui vous intéressent.

1. **Créer le modèle d'index.**

    - Revenez à la fenêtre du navigateur où **OpenSearch Dashboards** est ouvert.
    
    - Dans le menu de navigation, choisissez **Discover** (Découvrir).
    
    - Choisissez **Create index pattern** (Créer un modèle d'index).
    
    - Sur la page **Étape 1 sur 2**, pour **Index pattern name** (Nom du modèle d'index), entrez **apache_logs**.
    
      - **Remarque** : Cela correspond à une source.
    
    - Choisissez **Next step** (Étape suivante).
    
    - Sur la page **Étape 2 sur 2**, pour **Time field** (Champ de temps), choisissez **datetime**.
    
    - Choisissez **Create index pattern** (Créer un modèle d'index).
    
    - OpenSearch Dashboards affiche une liste de champs et de types de données qui ont été enregistrés dans OpenSearch Service et montre quels champs sont recherchables, comme illustré dans l'image suivante.

---

**Parfait !** Dans cette tâche, vous avez créé un modèle d'index OpenSearch en utilisant le champ **datetime** inclus dans les données de journaux d'accès.




### **Tâche 8 : Création d'une visualisation en graphique circulaire**

Maintenant que l'index est configuré, vous pouvez utiliser **OpenSearch Dashboards** pour analyser et visualiser les résultats. La première visualisation portera sur la question de savoir s'il existe une relation entre les produits que les clients sélectionnent et le système d'exploitation ou le navigateur web qu'ils utilisent. Dans cette tâche, vous allez créer un graphique circulaire empilé pour obtenir des informations et répondre à cette question.

1. **Créer la visualisation et sélectionner une source de données.**

    - Dans le menu de navigation, choisissez **Visualize** (Visualiser).
    
    - Choisissez **Create new visualization** (Créer une nouvelle visualisation).
    
    - Choisissez le type de visualisation **Pie** (Camembert).
    
    - Sélectionnez **apache_logs***.
    
    - Dans le coin supérieur droit, modifiez l'intervalle de temps en sélectionnant les **30 dernières minutes**.
    
    - Choisissez **Update** (Mettre à jour) dans le coin inférieur droit.

2. **Créer le premier bucket pour la page web.**

    - **Remarque** : Lors de la création d'un bucket pour la première fois, il est important de comprendre ce qu'est une agrégation de bucket. Les agrégations de bucket catégorisent des ensembles de documents en tant que buckets. Le type d'agrégation de bucket détermine si un document donné est inclus ou non dans un bucket.

    - Vous pouvez utiliser les agrégations de bucket pour implémenter une navigation par facettes (généralement placée en tant que barre latérale sur une page de résultats de recherche) afin d'aider vos utilisateurs à affiner les résultats. Pour créer le bucket :
    
        - Dans la section **Buckets**, choisissez **Add** > **Split slices**.
        
        - Pour **Aggregation** (Agrégation), choisissez **Terms** (Termes).
        
        - **Important** : Ne choisissez pas **Significant Terms**.
        
        - Pour **Field** (Champ), choisissez **webpage**.
        
        - Conservez les valeurs par défaut pour **Order by**, **Order**, et **Size**.
        
        - Activez l'option **Group other values in separate bucket** (Grouper les autres valeurs dans un bucket séparé).
        
        - Conservez les valeurs par défaut pour les autres options.

3. **Créer un bucket pour le système d'exploitation de l'appareil du visiteur.**

    - Choisissez **Add** > **Split slices**.
    
    - Pour **Sub aggregation** (Sous-agrégation), choisissez **Terms**.
    
    - Pour **Field** (Champ), choisissez **os**.
    
    - Conservez les valeurs par défaut pour **Order by**, **Order**, et **Size**.
    
    - Activez l'option **Group other values in separate bucket**.
    
    - Conservez les valeurs par défaut pour les autres options.

4. **Répétez les étapes précédentes** pour ajouter un autre bucket pour le type de navigateur.

5. **Choisissez** **Update** (Mettre à jour) dans le coin inférieur droit.

6. **Configurer des filtres pour le graphique circulaire.**

    - Dans le coin supérieur gauche, choisissez **Add filter** (Ajouter un filtre).
    
    - Pour **Field** (Champ), choisissez **webpage**.
    
    - Pour **Operator** (Opérateur), choisissez **is** (est).
    
    - Pour **Value** (Valeur), choisissez **kindle**.
    
    - Choisissez **Save** (Enregistrer).
    
    - Vos filtres sont appliqués au graphique, et des cercles supplémentaires sont ajoutés à la visualisation.





7. **Appliquer le style donut au graphique circulaire et configurer d'autres paramètres du graphique.**

    - Dans le volet à droite de la page, choisissez **Options**.
    
    - Désactivez les options **Donut** et **Show top level only** comme illustré dans l'image suivante.
    
    - **Remarque** : L'interface utilisateur a peut-être été mise à jour. Ces options sont désormais des bascules au lieu de cases à cocher.
    
    - Activez **Show labels** (Afficher les étiquettes).
    
    - Pour appliquer les modifications, choisissez **Update** (Mettre à jour) dans le coin inférieur droit.
    
    - Les étiquettes de données sont ajoutées à la visualisation. Votre visualisation devrait ressembler à l'image suivante.
    
    - **Remarque** : Les couleurs de votre visualisation peuvent ne pas correspondre aux couleurs de l'image.


![image](https://github.com/user-attachments/assets/80d43cc1-9701-4850-9daa-f2890287e322)
  
![image](https://github.com/user-attachments/assets/d7246955-3a12-4cf3-93fd-4545400f5034)

8. **Interagir avec le graphique pour obtenir des informations.**

    - Passez la souris sur chaque tranche du graphique pour voir les détails des données incluses dans chaque tranche.
    
    - Ajoutez un filtre différent pour afficher uniquement les pages où le navigateur est égal à l'un des navigateurs que vous avez utilisés lors de la génération des journaux web précédemment.
    
    - Modifiez également votre filtre produit afin d'inclure les autres produits. Pour supprimer le filtre précédent, survolez-le et choisissez l'icône **X**.
    
    - Le graphique circulaire résultant ressemble à ce qui suit :


![image](https://github.com/user-attachments/assets/d1be515c-abb7-4545-856b-e7428d73574d)

**Analyse** : Ce graphique circulaire empilé est similaire au précédent mais inclut le filtre pour un navigateur spécifique.

---

**Félicitations !** Dans cette tâche, vous avez réussi à créer un graphique circulaire empilé pour illustrer les systèmes d'exploitation et les navigateurs utilisés par les visiteurs du site web.

---


### **Tâche 9 : Création d'une visualisation en carte thermique (heat map)**

Vous allez maintenant créer une visualisation en **carte thermique** pour répondre à la question de savoir si plus de clients sont redirigés vers les pages de produits depuis la page de recherche ou la page de recommandations.

1. **Créer la visualisation et sélectionner une source de données.**

    - Dans le menu de navigation, choisissez **Visualize** (Visualiser).
    
    - Choisissez **Create new visualization** (Créer une nouvelle visualisation).
    
    - Choisissez le type de visualisation **Heat Map** (Carte thermique).
    
    - Sélectionnez **apache_logs***.

2. **Créer un bucket pour la page de référence.**

    - Dans la section **Buckets**, choisissez **Add** > **X-axis** (Axe X).
    
    - Pour **Aggregation** (Agrégation), choisissez **Terms** (Termes).
    
    - Pour **Field** (Champ), choisissez **refering_page** (Page de référence).
    
    - Conservez les valeurs par défaut pour **Order by**, **Order**, et **Size**.
    
    - Activez l'option **Group other values in separate bucket** (Grouper les autres valeurs dans un bucket séparé).
    
    - Conservez les valeurs par défaut pour les autres options.
    
    - **Conseil** : Pour simplifier les étapes suivantes, vous pouvez réduire le premier bucket que vous avez créé, comme illustré dans l'image suivante.

![image](https://github.com/user-attachments/assets/893a5038-0cc6-4019-9af7-6c0a87f984f7)


3. **Créer un bucket pour la page web.**

    - Choisissez **Add** > **Y-axis** (Axe Y).
    
    - Pour **Sub aggregation** (Sous-agrégation), choisissez **Terms** (Termes).
    
    - Pour **Field** (Champ), choisissez **webpage** (Page web).
    
    - Conservez les valeurs par défaut pour **Order by**, **Order**, et **Size**.
    
    - Activez l'option **Group other values in separate bucket**.
    
    - Choisissez l'onglet **Options**.
    
    - Dans la section **Labels**, activez **Show labels** (Afficher les étiquettes).
    
    - Pour appliquer les modifications, choisissez **Update** (Mettre à jour) dans le coin inférieur droit.

4. **Dans le coin supérieur droit**, modifiez la durée en sélectionnant **la dernière heure** et choisissez **Refresh** (Actualiser).

    - La carte thermique montre le nombre de vues des pages web ainsi que si les références proviennent de la page de recherche ou de la page de recommandations.

    - Votre carte thermique devrait ressembler à l'image suivante, mais elle variera en fonction des pages que vous avez visitées lorsque vous avez généré les journaux d'accès web et des navigateurs que vous avez utilisés.

---

![image](https://github.com/user-attachments/assets/e4180cea-28bb-4b38-9d40-65dd244bc00e)

### **Analyse :**
D'après cette image de la visualisation, les visiteurs ont accédé plus fréquemment aux pages de produits **Echo** et **FireStick** depuis la page de recherche que depuis la page de recommandations. L'équipe pourrait en déduire que la page de recherche est plus efficace que la page de recommandations pour diriger les utilisateurs vers les pages de produits.

---

**Félicitations !** Dans cette tâche, vous avez créé une carte thermique pour obtenir des informations sur la question de savoir si plus de clients sont redirigés vers les pages de produits depuis la page de recherche ou la page de recommandations. Votre preuve de concept (POC) pour démontrer comment utiliser **Kinesis Data Firehose** et **OpenSearch Service** pour analyser des données de streaming à partir d'un site web a été un succès.

---

### **Mise à jour de l'équipe**

Vous partagez les résultats de la POC avec l'administratrice du site web de la librairie universitaire. Elle est enthousiaste à l'idée de mettre en place l'infrastructure pour analyser les données de streaming des journaux d'accès du serveur web.

Dans ce laboratoire, vous avez configuré **OpenSearch Service** pour utiliser un index pour les données des journaux d'accès web. Vous avez observé comment **Kinesis Data Firehose** a ingéré ces données, puis **Lambda** les a rapidement transformées. Vous avez également créé des visualisations dans **OpenSearch Dashboards** pour analyser vos données et générer des insights.

---

### **Soumission de votre travail**

1. Pour enregistrer votre progression, choisissez **Submit** (Soumettre) en haut de ces instructions.

    - Lorsque vous y êtes invité, choisissez **Yes** (Oui).
    
    - Après quelques minutes, le panneau de notes s'affiche et vous indique combien de points vous avez obtenus pour chaque tâche. Si les résultats ne s'affichent pas après quelques minutes, choisissez **Grades** en haut de ces instructions.
    
    - **Important** : Certaines vérifications effectuées par le processus de soumission dans ce laboratoire ne vous attribueront des points que s'il s'est écoulé au moins 5 minutes depuis que vous avez effectué l'action. Si vous ne recevez pas de crédit la première fois que vous soumettez, vous devrez peut-être attendre quelques minutes, puis soumettre à nouveau pour recevoir le crédit pour ces éléments.
    
    - **Conseil** : Vous pouvez soumettre votre travail plusieurs fois. Après avoir modifié votre travail, choisissez **Submit** à nouveau. Votre dernière soumission est enregistrée pour ce laboratoire.

2. Pour trouver des commentaires détaillés sur votre travail, choisissez **Submission Report** (Rapport de soumission).

---

### **Fin du laboratoire**

**Félicitations !** Vous avez terminé le laboratoire.

- En haut de cette page, choisissez **End Lab** (Terminer le laboratoire), puis choisissez **Yes** pour confirmer que vous souhaitez terminer le laboratoire.

    - Un panneau de message indique que le laboratoire est en cours de terminaison.
    
    - Pour fermer le panneau, choisissez **Close** (Fermer) dans le coin supérieur droit.

---

### **Ressources supplémentaires**

Pour plus d'informations sur les services et concepts couverts dans ce laboratoire, consultez les ressources suivantes :

- **Fine-grained access control in Amazon OpenSearch Service**
- **Bucket aggregations**

