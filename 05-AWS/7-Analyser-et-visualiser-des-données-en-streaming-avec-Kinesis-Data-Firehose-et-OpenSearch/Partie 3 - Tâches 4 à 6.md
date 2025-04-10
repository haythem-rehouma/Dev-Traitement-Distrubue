#  **Partie 3 : Tâches 4 à 6**

<br/>
<br/>

#  **Tâche 4 : Configuration d’un index OpenSearch**

###  Objectif :
Créer un index dans **OpenSearch Dashboards** pour recevoir les journaux du serveur web transmis par **Kinesis Data Firehose**.

>  **Rappel** : Un index permet aux moteurs de recherche comme OpenSearch d'organiser les données pour des recherches rapides.

###  Étapes :

1. **Connexion à OpenSearch Dashboards** :
   - Ouvrez un nouvel onglet et accédez à l’**URL Dashboards** (copiée précédemment).
   - Utilisez ces identifiants :
     - **Nom d’utilisateur** : `LabUser`
     - **Mot de passe** : `Passw0rd1!`
   - Modifiez le mot de passe lorsque cela vous est demandé : utilisez `Passw0rd1!2`.

2. **Suppression d’un index existant** (si présent) :
   - Ouvrez le menu (☰), puis cliquez sur **Dev Tools**.
   - Supprimez l’ancien index en exécutant :
     ```json
     DELETE /apache_logs
     ```
   - Une réponse comme celle-ci doit apparaître :
     ```json
     {
       "acknowledged": true
     }
     ```

3. **Création de l’index** :
   - Copiez/collez cette commande dans la console :

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
           "agent": { "type": "text" },
           "browser": { "type": "keyword" },
           "bytes": { "type": "text" },
           "city": { "type": "keyword" },
           "country": { "type": "keyword" },
           "datetime": { "type": "date", "format": "dd/MMM/yyyy:HH:mm:ss Z" },
           "host": { "type": "text" },
           "location": { "type": "geo_point" },
           "referer": { "type": "text" },
           "os": { "type": "keyword" },
           "request": { "type": "text" },
           "response": { "type": "text" },
           "webpage": { "type": "keyword" },
           "refering_page": { "type": "keyword" }
         }
       }
     }
     ```

   - Résultat attendu :
     ```json
     {
       "acknowledged": true,
       "shards_acknowledged": true,
       "index": "apache_logs"
     }
     ```

###  Analyse :
Cet index `apache_logs` définira le schéma pour les journaux d’accès. Lorsque vous naviguerez sur le site, les logs générés seront indexés selon cette structure.

 **Félicitations !** Vous avez créé un nouvel index OpenSearch.

<br/>
<br/>

# **Tâche 5 : Génération des journaux d’accès du serveur web**

### Objectif :
Tester l’infrastructure en générant du trafic vers le site web pour produire des logs.

### Étapes :

1. Dans un nouvel onglet, accédez à :
   ```
   http://<ADRESSE-IP-PUBLIQUE>/main.php
   ```

2. **Naviguez** sur le site :
   - Visitez plusieurs pages.
   - Utilisez **plusieurs navigateurs** (ou appareils) pour générer des logs variés.
   - Exemple de pages à visiter :
     - `/search.php`
     - `/recommendation.php`
     - `/kindle.php`, `/firetvstick.php`, etc.

**Félicitations !** Vous avez généré des journaux à partir de vos actions de navigation. Ces logs sont transmis en streaming via Kinesis.

<br/>
<br/>

##  **Tâche 6 : Observation des événements CloudWatch**

### Objectif :
Comprendre **le flux d’ingestion et de transformation des données** grâce à **CloudWatch Logs**.

> Les logs du serveur sont capturés par Kinesis, transformés par Lambda, puis stockés dans OpenSearch. Ce processus prend quelques millisecondes.

### Étapes :

1. Accédez à **CloudWatch** depuis la console AWS.
2. Dans le menu de gauche, allez à **Logs > Log groups**.
3. Cliquez sur le groupe de logs :
   ```
   /aws/lambda/aes-demo-lambda-function
   ```

### 🔍 Examinez un **flux de logs (log stream)** :
- Cliquez sur le log stream le plus récent.
- Vous y verrez plusieurs événements. Déroulez-en certains.

#### Exemple 1 : Log d’entrée depuis Firehose :
```json
Incoming Record from Kinesis Firehose:
{
  "host": "68.206.xxx.xxx",
  "datetime": "09/Jul/2022:02:43:51 +0000",
  "request": "GET /firetvstick.php HTTP/1.1",
  "response": "200",
  "bytes": "305",
  "referer": "http://18.207.217.39/recommendation.php",
  "agent": "Mozilla/5.0 ..."
}
```

#### Exemple 2 : Log transformé retourné à Firehose :
```json
Transformed Record going back to Kinesis Firehose:
{
  ...,
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

#### Exemple 3 : Statistiques d'exécution Lambda :
```
REPORT RequestId: ...  Duration: 474 ms  Memory Size: 128 MB Max Memory Used: 51 MB
```

### Analyse :
- Le premier log montre les données brutes reçues.
- Le second montre les **données enrichies** par Lambda (géolocalisation, navigateur, OS...).
- Le troisième donne un aperçu **des performances de Lambda**.

**Félicitations !** Vous avez observé les événements de transformation dans CloudWatch.

