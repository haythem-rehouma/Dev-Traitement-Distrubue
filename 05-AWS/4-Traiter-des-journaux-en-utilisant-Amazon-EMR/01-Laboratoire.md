### Traitement des journaux à l'aide d'Amazon EMR

#### Aperçu et objectifs du laboratoire

Apache Hadoop est un framework open-source qui prend en charge le traitement massif de données sur un cluster d'instances. Hadoop peut s'exécuter sur une seule instance Amazon Elastic Compute Cloud (Amazon EC2) ou sur des milliers d'instances. Hadoop utilise le système de fichiers distribué Hadoop (HDFS) pour stocker les données sur plusieurs instances, que Hadoop appelle des nœuds. Hadoop peut également lire et écrire des données vers et à partir d'Amazon Simple Storage Service (Amazon S3). Le service Amazon EMR regroupe le noyau de Hadoop et d'autres projets liés à Hadoop dans chaque version. Vous verrez une liste de ces services lors de la création d'un cluster EMR dans ce laboratoire.

Dans ce laboratoire, vous allez utiliser Amazon EMR pour traiter des journaux stockés dans un compartiment S3. Pour traiter les données dans Amazon EMR, vous utiliserez Apache Hive. Hive est un cadre d'infrastructure d'entrepôt de données open-source construit au-dessus de Hadoop, souvent utilisé pour traiter de grands ensembles de données. Avec Hive, vous pouvez interroger de grands ensembles de données avec des requêtes de type SQL. Lorsque vous exécutez une requête Hive, la demande est traduite en tâche YARN (Yet Another Resource Negotiator). YARN est la partie du logiciel Hadoop qui coordonne la planification des tâches.

Après avoir terminé ce laboratoire, vous serez en mesure de :
- Lancer un cluster EMR via la console de gestion AWS.
- Exécuter Hive de manière interactive.
- Utiliser des commandes Hive pour créer des tables à partir de données de journaux stockées dans Amazon S3.
- Utiliser des commandes Hive pour joindre des tables et stocker la table jointe dans Amazon S3.
- Utiliser Hive pour interroger des tables stockées dans Amazon S3.

#### Durée
Ce laboratoire prendra environ 90 minutes à compléter.

#### Restrictions de services AWS
Dans cet environnement de laboratoire, l'accès aux services et actions AWS pourrait être limité aux éléments nécessaires pour compléter les instructions du laboratoire. Vous pourriez rencontrer des erreurs si vous tentez d'accéder à d'autres services ou d'effectuer des actions non mentionnées dans ce laboratoire.

---

### Scénario

Un autre département universitaire a demandé à votre groupe de rechercher des tendances dans les données publicitaires en ligne contenues dans un ensemble de fichiers de journaux web. Les journaux sont répartis dans plusieurs fichiers en raison du processus de rotation des journaux utilisé pour les collecter et les stocker. Deux types de journaux vous ont été fournis :
- **Journaux d'impressions** : Chaque entrée indique qu'une publicité a été affichée à un utilisateur.
- **Journaux de clics** : Chaque entrée indique qu'un utilisateur a cliqué sur une publicité.

Votre défi est de développer une preuve de concept (POC) pour trouver des tendances montrant quelles impressions ont conduit à des clics publicitaires.

Après avoir discuté du défi avec vos coéquipiers, vous avez décidé qu'Amazon EMR serait un bon choix pour relever ce défi de données. Vous savez qu'Amazon EMR peut traiter de grands ensembles de données et qu'il peut lire à partir et écrire vers Amazon S3. De plus, vous avez appris que vous pouvez utiliser Hive (un outil inclus avec Amazon EMR) pour construire un entrepôt de données. Ensuite, vous pouvez exécuter des requêtes de type SQL sur les données.

Lorsque vous démarrez le laboratoire, l'environnement contiendra les ressources illustrées dans le diagramme suivant. Pour cet environnement de laboratoire, la source de données d'origine est un compartiment S3 situé dans un autre compte AWS.

---

### Tâche 1 : Lancer un cluster Amazon EMR

1. Accédez à la console EMR via la recherche dans la barre des services AWS.
2. Cliquez sur **Créer un cluster**.
3. Configurez les options suivantes :
   - **Nom du cluster** : `Hive EMR cluster`
   - **Version EMR** : `emr-5.29.0`
   - Sélectionnez les applications : Hadoop 2.8.5, Hive 2.3.6.
   - **Type d'instance principale et de nœud** : `m4.large`
   - Sélectionnez le réseau **Lab VPC** et le sous-réseau **Lab subnet**.
4. Désactivez l'option de protection de terminaison du cluster et choisissez un compartiment S3 pour stocker les résultats Hive.
5. Sélectionnez une paire de clés SSH (vockey).
6. Créez le cluster et attendez qu'il soit en état **Running**.

---

### Tâche 2 : Connexion au nœud principal via SSH

1. Récupérez l'adresse DNS publique du nœud principal dans l'onglet **Résumé du cluster**.
2. Connectez-vous à AWS Cloud9 et ouvrez un terminal bash.
3. Téléchargez la clé privée SSH (`labsuser.pem`) sur Cloud9 et modifiez ses permissions avec la commande :
   ```bash
   chmod 400 labsuser.pem
   ```
4. Connectez-vous au nœud principal avec SSH :
   ```bash
   ssh -i labsuser.pem hadoop@<PUBLIC-DNS>
   ```
5. Vous êtes maintenant connecté au nœud principal du cluster.

---

### Tâche 3 : Exécution de Hive de manière interactive

1. Configurez un répertoire pour les logs Hive :
   ```bash
   sudo chown hadoop -R /var/log/hive
   mkdir /var/log/hive/user/hadoop
   ```
2. Exécutez Hive avec les paramètres appropriés pour lire et écrire des données dans S3 :
   ```bash
   hive -d SAMPLE=s3://aws-tc-largeobjects/... -d OUTPUT=s3://<HIVE-BUCKET-NAME>/output/
   ```

---

### Tâche 4 : Créer des tables à partir des données sources

1. Créez une table externe `impressions` :
   ```sql
   CREATE EXTERNAL TABLE impressions (
       requestBeginTime string,
       adId string,
       impressionId string,
       referrer string,
       userAgent string,
       userCookie string,
       ip string)
   PARTITIONED BY (dt string)
   ROW FORMAT  serde 'org.apache.hive.hcatalog.data.JsonSerDe'
   LOCATION '${SAMPLE}/tables/impressions';
   ```
2. Mettez à jour la partition avec la commande :
   ```bash
   MSCK REPAIR TABLE impressions;
   ```
3. Répétez le processus pour la table `clicks`.

---

### Tâche 5 : Jointure des tables avec Hive

1. Créez une table `joined_impressions` pour stocker les résultats de la jointure :
   ```sql
   CREATE EXTERNAL TABLE joined_impressions (
       requestBeginTime string,
       adId string,
       impressionId string,
       referrer string,
       userAgent string,
       userCookie string,
       ip string,
       clicked Boolean)
   PARTITIONED BY (day string, hour string)
   STORED AS SEQUENCEFILE
   LOCATION '${OUTPUT}/tables/joined_impressions';
   ```
2. Créez des tables temporaires `tmp_impressions` et `tmp_clicks` pour stocker des données intermédiaires.
3. Exécutez une jointure externe gauche entre `tmp_impressions` et `tmp_clicks` pour remplir la table `joined_impressions`.

---

### Tâche 6 : Interrogation des résultats

1. Exécutez des requêtes Hive pour analyser les données :
   ```sql
   SELECT adid, count(*) AS hits FROM joined_impressions WHERE clicked = true GROUP BY adid ORDER BY hits DESC LIMIT 10;
   ```
2. Exportez les résultats vers un fichier texte :
   ```bash
   hive -e "SELECT referrer, count(*) as hits FROM joined_impressions WHERE clicked = true GROUP BY referrer ORDER BY hits DESC LIMIT 10;" > result.txt
   ```

---

### Soumission du travail

Cliquez sur **Soumettre** pour enregistrer vos progrès et obtenir votre évaluation finale.

Félicitations, vous avez terminé ce laboratoire !

