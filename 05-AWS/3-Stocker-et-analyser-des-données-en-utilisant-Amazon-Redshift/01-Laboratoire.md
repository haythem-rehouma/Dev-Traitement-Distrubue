# Aperçu et objectifs du lab

Parmi les membres de l'équipe de science des données, Mary travaille le plus souvent avec de grands ensembles de données et des entrepôts de données en colonnes. Vous avez décidé d'explorer l'utilisation d'Amazon Redshift pour votre prochaine preuve de concept (POC) afin de répondre à ces cas d'utilisation.

Vous demandez à Mary si elle dispose d'un échantillon de données que vous pouvez utiliser pour votre POC. Elle vous suggère d'utiliser un grand ensemble de données sur les ventes de billets de musique avec lequel elle a déjà travaillé. Cet ensemble de données est disponible dans un compartiment public Amazon Simple Storage Service (Amazon S3).

Les analystes de données travaillent souvent avec de grands ensembles de données. Par exemple, certains entrepôts de données peuvent contenir plusieurs pétaoctets de données, et les lacs de données peuvent être encore plus grands. Amazon Redshift est conçu pour gérer de grands ensembles de données. Contrairement aux systèmes traditionnels de gestion de bases de données relationnelles, Amazon Redshift utilise le stockage en colonnes. Le stockage en colonnes améliore les performances des requêtes et réduit les coûts de stockage.

Dans ce lab, vous apprendrez à implémenter un cluster Redshift dans le cadre de votre pipeline d'analyse. Vous allez extraire les données d'un ensemble de données stocké dans Amazon S3, les charger dans une base de données Redshift, puis interroger ces données pour les analyser.

Après avoir terminé ce lab, vous devriez être en mesure de faire ce qui suit :

- Examiner les autorisations d'un rôle AWS Identity and Access Management (IAM) pour accéder et configurer Amazon Redshift.
- Créer et configurer un cluster Redshift.
- Créer un groupe de sécurité pour un cluster Redshift.
- Créer une base de données, des schémas et des tables avec le cluster Redshift.
- Charger des données dans les tables d'un cluster Redshift.
- Interroger les données dans un cluster Redshift à l'aide de la console Amazon Redshift.
- Interroger les données dans un cluster Redshift à l'aide de l'API et de l'interface en ligne de commande AWS (AWS CLI) dans AWS Cloud9.
- Examiner une politique IAM avec des autorisations pour exécuter des requêtes sur un cluster Redshift.
- Confirmer qu'un membre de l'équipe de science des données peut exécuter des requêtes sur un cluster Redshift.

# Durée

Ce lab nécessite 90 minutes pour être complété. Le lab restera actif pendant 180 minutes pour vous permettre de terminer toutes les étapes.

# Restrictions de service AWS

Dans cet environnement de lab, l'accès aux services AWS et aux actions de service peut être limité à ceux nécessaires pour compléter les instructions du lab. Vous pourriez rencontrer des erreurs si vous tentez d'accéder à d'autres services ou d'effectuer des actions non décrites dans ce lab.

# Scénario

Dans ce lab, vous allez créer une preuve de concept (POC) pour utiliser Amazon Redshift afin de répondre au besoin de requêter de grands ensembles de données. Vous allez utiliser des requêtes SQL sur un ensemble de données de ventes de billets de musique. Ces fichiers de données sont déjà stockés dans un compartiment S3 appartenant à un autre compte, et vous allez les charger dans une base de données Redshift.

# Tâche 1 : Révision du rôle IAM pour accéder à Amazon Redshift

Vous devez disposer des autorisations adéquates pour accéder à la console Amazon Redshift. Ces accès sont contrôlés via une politique IAM qui vous est attribuée lors du lancement de l'environnement de lab.

Dans cette tâche, vous allez faire ce qui suit :

- Accéder à IAM et obtenir l'ARN pour le rôle IAM MyRedshiftRole.
- Examiner les permissions dans la politique IAM AmazonS3ReadOnlyAccess.
- Examiner les permissions dans la politique IAM RedshiftIAMLabPolicy.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 1 : Révision du rôle IAM pour accéder à Amazon Redshift (suite)

# Accéder au rôle IAM et obtenir l'ARN

1. Dans la console AWS Management Console, dans la barre de recherche à droite de **Services**, cherchez et sélectionnez **IAM** pour ouvrir la console IAM.
2. Dans le panneau de navigation, choisissez **Roles** (Rôles).
3. Dans la liste des rôles, recherchez **MyRedshiftRole** puis cliquez sur le lien associé lorsqu'il apparaît.
4. Dans la section **Résumé** en haut de la page, copiez l'ARN du rôle et enregistrez-le dans un éditeur de texte pour l'utiliser plus tard.
5. Dans la partie inférieure de la page, vous pouvez voir quelles politiques de permissions sont attachées au rôle. Vous remarquerez que deux politiques IAM sont attachées à ce rôle : **AmazonS3ReadOnlyAccess** (une politique gérée par AWS) et **RedshiftIAMLabPolicy**.

# Examiner les politiques associées au rôle

1. Développez et examinez la politique **AmazonS3ReadOnlyAccess**.
   - Cette politique inclut des permissions qui permettent à Amazon Redshift d'accéder aux compartiments S3 et de lire les objets qu'ils contiennent.
   
2. Développez ensuite et examinez la politique **RedshiftIAMLabPolicy**.
   - Cette politique permet à Amazon Redshift de décrire les ressources Amazon Elastic Compute Cloud (EC2) et Amazon Virtual Private Cloud (VPC). Par exemple, elle permet à Redshift d’obtenir des détails sur la configuration du VPC et des sous-réseaux associés, ainsi que de créer et de supprimer une interface réseau dans le VPC.

# Résumé de la tâche 1

Dans cette tâche, vous avez examiné un rôle qui permet à Amazon S3 d'avoir un accès en lecture seule à Amazon Redshift. Vous avez également examiné un rôle qui permet à Amazon Redshift d'accéder à Amazon S3, EC2, et aux ressources VPC. Gardez ces autorisations à l'esprit en poursuivant le lab.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 2 : Création et configuration d'un cluster Redshift

Les clusters sont les principaux composants de l'infrastructure d'un entrepôt de données Amazon Redshift. Un cluster est composé d'un ou plusieurs nœuds de calcul. Si un cluster a plus d'un nœud, l'un des nœuds est le **nœud leader**, et les autres sont des **nœuds de calcul**. Les applications clientes interagissent avec le nœud leader.

#### Accéder à la console Amazon Redshift et initier le processus de création du cluster

1. Dans la barre de recherche à droite de **Services**, recherchez et sélectionnez **Amazon Redshift** pour ouvrir la console.
2. Dans le panneau de navigation, choisissez **Clusters**.
3. Cliquez sur **Create cluster** (Créer un cluster).

#### Configurer le cluster Redshift

Configurez les paramètres suivants pour le cluster :

1. **Cluster identifier** : Saisissez `redshift-cluster-1`
2. **Node type** : Sélectionnez `dc2.large`.
3. **Nombre de nœuds** : Entrez `2`.
4. **Admin user name** : Entrez `awsuser`.
5. **Admin user password** : Entrez `Passw0rd1` (Attention : la lettre O est en fait le chiffre 0).
6. **Cluster permissions** : Choisissez **Manage IAM roles** > **Associate IAM roles**, puis sélectionnez **MyRedshiftRole** dans la fenêtre contextuelle et cliquez sur **Associate IAM roles**.
7. À la fin de la page, cliquez sur **Create cluster**.

Attendez quelques minutes que le cluster Redshift soit créé. Une fois cela fait, allez dans la section **Clusters** et sélectionnez le lien pour **redshift-cluster-1**.

#### Configurer le VPC pour permettre l'accès au cluster Redshift

1. Dans la barre de recherche, recherchez et sélectionnez **EC2** pour ouvrir la console EC2.
2. Dans le panneau de navigation sous **Network & Security**, choisissez **Security Groups** (Groupes de sécurité).
3. Cliquez sur **Create security group** (Créer un groupe de sécurité) et configurez les paramètres suivants :
   - **Security group name** : Entrez `Redshift security group`
   - **Description** : Saisissez `Security group for my Redshift cluster`
4. Dans la section **Inbound rules** (Règles entrantes), choisissez **Add rule** (Ajouter une règle) et configurez comme suit :
   - **Type** : Sélectionnez **Redshift** (le protocole et le port sont automatiquement configurés sur TCP et 5439).
   - **Source** : Sélectionnez **Anywhere-IPv4** (ce qui ajoute automatiquement `0.0.0.0/0`).
5. En bas de la page, cliquez sur **Create security group**.

#### Ajouter le cluster à votre groupe de sécurité

1. Retournez à la console **Amazon Redshift**.
2. Assurez-vous que le statut de **redshift-cluster-1** est **Available** (Disponible).
3. Sélectionnez **redshift-cluster-1**, puis allez dans l'onglet **Properties** (Propriétés).
4. Dans la section **Network and security settings** (Paramètres réseau et sécurité), cliquez sur **Edit**.
5. Pour **VPC security groups**, sélectionnez **Redshift security group** et décochez **default**.
6. Cliquez sur **Save changes** (Enregistrer les modifications).

Attendez que le statut du cluster passe de **Modifying** à **Available** avant de continuer.

#### Résumé de la tâche 2

Dans cette tâche, vous avez créé un cluster Redshift et associé le rôle IAM **MyRedshiftRole** au cluster. Ce rôle permet à Amazon Redshift d'accéder aux ressources Amazon S3 pour charger des jeux de données. Vous avez également créé un groupe de sécurité pour le cluster afin que d'autres ressources, comme les instances EC2, puissent y accéder via le port 5439.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 3 : Création de tables dans la base de données

Maintenant que vous avez créé un cluster Redshift pour votre POC, vous pouvez charger des données directement depuis Amazon S3.

#### Créer les tables dans la base de données `dev`

1. Dans le panneau de navigation, choisissez **Clusters**.
2. Sélectionnez **redshift-cluster-1**, puis choisissez **Query data > Query in query editor**.
3. Connectez-vous à la base de données en choisissant **Create a new connection**, et configurez comme suit :
   - **Authentication** : Choisissez **Temporary credentials**.
   - **Database name** : Saisissez `dev`.
   - **Database user** : Saisissez `awsuser`.

Dans l'éditeur de requêtes, créez les tables suivantes :

- Pour créer la table **users**, exécutez la requête suivante :

```sql
create table users(
    userid integer not null distkey sortkey,
    username char(8),
    city varchar(30),
    state char(2),
    likesports boolean,
    liketheatre boolean,
    likeconcerts boolean,
    likejazz boolean,
    likeclassical boolean,
    likeopera boolean,
    likerock boolean,
    likevegas boolean,
    likebroadway boolean,
    likemusicals boolean
);
```

- Pour créer la table **date**, exécutez la requête suivante :

```sql
create table date(
    dateid smallint not null distkey sortkey,
    caldate date not null,
    day character(3) not null,
    week smallint not null,
    month character(5) not null,
    qtr character(5) not null,
    year smallint not null,
    holiday boolean default('N')
);
```

- Pour créer la table **sales**, exécutez la requête suivante :

```sql
create table sales(
    salesid integer not null,
    listid integer not null distkey,
    sellerid integer not null,
    buyerid integer not null,
    eventid integer not null,
    dateid smallint not null sortkey,
    qtysold smallint not null,
    pricepaid decimal(8,2),
    commission decimal(8,2),
    saletime timestamp
);
```

### Résumé de la tâche 3

Dans cette tâche, vous avez accédé à la console Amazon Redshift et créé les tables **users**, **date**, et **sales** dans la base de données **dev**.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 4 : Chargement des données depuis Amazon S3

Pour charger les données dans les tables, vous allez utiliser la commande `COPY`. Voici comment procéder pour chacune des tables :

- Pour charger les données dans la table **users**, exécutez la commande suivante en remplaçant `<REDSHIFT-ROLE-ARN>` par l'ARN du rôle IAM que vous avez copié :

```sql
copy users from 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab4/allusers_tab.txt'
credentials 'aws_iam_role=<REDSHIFT-ROLE-ARN>'
delimiter '\t' region 'us-west-2';
```

- Pour charger les données dans la table **date** :

```sql
copy date from 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab4/date2008_pipe.txt'
credentials 'aws_iam_role=<REDSHIFT-role-arn>'
delimiter '|' region 'us-west-2';
```

- Pour charger les données dans la table **sales** :

```sql
copy sales from 's3://aws-tc-largeobjects/CUR-TF-200-ACDSCI-1/Lab4/sales_tab.txt'
credentials 'aws_iam_role=<REDSHIFT-ROLE-ARN>'
delimiter '\t' timeformat 'MM/DD/YYYY HH:MI:SS' region 'us-west-2';
```

#### Résumé de la tâche 4

Dans cette tâche, vous avez chargé un jeu de données dans votre cluster Redshift. Vous avez utilisé la commande `COPY` pour copier les données des fichiers texte situés dans Amazon S3 vers les tables que vous avez créées dans la base de données. Vous avez également appris à configurer un délimiteur dans la commande `COPY` pour vous assurer que les données sont correctement chargées et que chaque enregistrement correspond au schéma de sa table.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 5 : Interroger les données

Maintenant que le jeu de données a été importé avec succès dans le cluster Redshift, vous pouvez écrire des requêtes SQL pour générer les rapports dont Mary a besoin.

#### Prévisualisation de la table **sales**

1. Dans le panneau **Resources** (Ressources), à droite de **sales**, cliquez sur l'icône des trois points (ellipsis) et choisissez **Preview data** (Prévisualiser les données).
2. Les données s'afficheront dans l'onglet **Table details** (Détails de la table).

#### Exécuter une requête SQL simple

Vous pouvez maintenant exécuter la requête suivante pour obtenir le nombre total d'articles vendus à une date particulière :

```sql
SELECT sum(qtysold)
FROM sales, date
WHERE sales.dateid = date.dateid
AND caldate = '2008-01-05';
```

Cette requête renvoie une ligne avec la valeur **210**, ce qui correspond au nombre total d'articles vendus à cette date précise.

#### Requête avancée : Top 10 des acheteurs par quantité

Essayez maintenant d'exécuter la requête suivante, qui permet de trouver les 10 meilleurs acheteurs en fonction de la quantité achetée :

```sql
SELECT username, total_quantity
FROM (
    SELECT buyerid, sum(qtysold) total_quantity
    FROM sales
    GROUP BY buyerid
    ORDER BY total_quantity DESC
    LIMIT 10
) Q, users
WHERE Q.buyerid = userid
ORDER BY Q.total_quantity DESC;
```

Cette requête renvoie les noms d'utilisateur des acheteurs ainsi que la quantité totale achetée, classés par ordre décroissant.

#### Résumé de la tâche 5

Dans cette tâche, vous avez utilisé SQL pour interroger la base de données Redshift. Vous avez exécuté des requêtes pour obtenir des informations sur les ventes et les acheteurs à partir du jeu de données chargé dans Redshift. L'utilisation de SQL facilite l'analyse des données dans Redshift, et vous pouvez l'intégrer à des flux de travail plus larges.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 6 : Exécution de requêtes depuis l'AWS CLI

Outre l'utilisation de la console pour exécuter des requêtes sur les données dans Amazon Redshift, vous pouvez également utiliser l'API Amazon Redshift, les bibliothèques AWS SDK, ainsi que l'interface AWS CLI.

Dans cette tâche, vous allez utiliser le terminal AWS Cloud9 pour exécuter des actions sur votre cluster Redshift.

#### Ouvrir l'environnement AWS Cloud9

1. Dans la console de gestion AWS, recherchez **Cloud9** dans la barre de recherche et ouvrez la console.
2. Sélectionnez l'environnement nommé **Cloud9 Instance** et cliquez sur **Open IDE**.

Une nouvelle fenêtre de navigateur s'ouvre pour afficher l'IDE AWS Cloud9.

#### Exécuter une requête à l'aide de l'interface CLI

Utilisez l'utilisateur de base de données **awsuser** que vous avez utilisé précédemment pour exécuter la commande suivante dans le terminal Cloud9 afin de sélectionner un enregistrement dans la table **users** :

```bash
aws redshift-data execute-statement --region us-east-1 --db-user awsuser --cluster-identifier redshift-cluster-1 --database dev --sql "select * from users limit 1"
```

L'interface CLI renvoie un résultat similaire à :

```json
{
    "ClusterIdentifier": "redshift-cluster-1",
    "CreatedAt": 1649864498.714,
    "Database": "dev",
    "DbUser": "awsuser",
    "Id": "ce0ec95c-596e-4cd9-86d4-5d9847dacd94"
}
```

#### Obtenir les résultats de la requête

Pour obtenir les résultats de la requête, exécutez la commande suivante en remplaçant `<QUERY-ID>` par l'ID retourné précédemment :

```bash
aws redshift-data get-statement-result --id <QUERY-ID> --region us-east-1
```

La sortie sera similaire à ceci :

```json
{
    "Records": [
        [
            {
                "longValue": 3
            },
            {
                "stringValue": "IFT66TXU"
            },
            {
                "stringValue": "Lars"
            },
            {
                "stringValue": "Ratliff"
            },
            {
                "stringValue": "High Point"
            },
            {
                "stringValue": "ME"
            }
        ]
    ]
}
```

#### Résumé de la tâche 6

Dans cette tâche, vous avez appris à utiliser l'interface AWS CLI pour interroger une base de données Redshift. L'utilisation de l'API et de l'interface CLI vous permet d'automatiser des tâches et d'intégrer des requêtes Redshift dans des applications.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 7 : Révision de la politique IAM pour accéder à Amazon Redshift

Maintenant que vous avez testé votre capacité à interroger la base de données Redshift, vous allez examiner une politique IAM qui permet à d'autres utilisateurs d'exécuter des actions Amazon Redshift en production.

1. Accédez à la console IAM et choisissez **Users** dans le panneau de navigation.
2. Recherchez l'utilisateur **Mary**, qui fait partie du groupe IAM **DataScienceGroup**.
3. Cliquez sur le lien pour le groupe **DataScienceGroup**, puis sur l'onglet **Permissions**.
4. Recherchez et examinez la politique **Policy-For-Data-Scientists** qui est attachée au groupe.

La politique permet à l'utilisateur Mary d'exécuter des actions limitées sur l'API Redshift Data.

#### Résumé de la tâche 7

Dans cette tâche, vous avez examiné une politique IAM qui accorde un accès limité à l'API Redshift Data. Ce type de politique permet de définir des autorisations spécifiques pour des utilisateurs qui doivent interagir avec Redshift via l'API.

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

# Tâche 8 : Vérification que les utilisateurs peuvent exécuter des requêtes sur la base de données Redshift

Vous allez maintenant confirmer que Mary peut exécuter des requêtes sur la base de données Redshift en utilisant l'API AWS CLI.

1. Accédez à la console **CloudFormation** et choisissez la pile (stack) qui a créé l'environnement du lab.
2. Dans l'onglet **Outputs**, copiez les valeurs de **MarysAccessKey** et **MarysSecretAccessKey**.
3. Retournez dans le terminal Cloud9 et définissez les variables suivantes :

```bash
AK=<ACCESS-KEY>
SAK=<SECRET-ACCESS-KEY>
```

4. Exécutez la commande suivante pour exécuter une requête en tant que Mary :

```bash
AWS_ACCESS_KEY_ID=$AK AWS_SECRET_ACCESS_KEY=$SAK aws redshift-data execute-statement --region us-east-1 --db-user awsuser --cluster-identifier redshift-cluster-1 --database dev --sql "select * from users limit 1"
```

5. Pour obtenir les résultats de la requête, exécutez la commande suivante en remplaçant `<QUERY-ID>` par l'ID de la requête :

```bash
AWS_ACCESS_KEY_ID=$AK AWS_SECRET_ACCESS_KEY=$SAK aws redshift-data get-statement-result --id <QUERY-ID> --region us-east-1
```

#### Résumé de la tâche 8

Dans cette tâche, vous avez confirmé que Mary a la capacité d'exécuter des requêtes SQL sur une base de données Redshift à l'aide de l'interface AWS CLI. Cela permet à l'équipe de science des données de simplifier ses flux de travail et d'automatiser des tâches dans Amazon Redshift.

---

# Conclusion du lab

Félicitations ! Vous avez appris à créer un cluster Redshift, charger des données à partir d'Amazon S3, et interroger les données via l'interface AWS CLI et la console Redshift. Vous avez également examiné les autorisations IAM pour permettre à des utilisateurs comme Mary de travailler avec Redshift.
