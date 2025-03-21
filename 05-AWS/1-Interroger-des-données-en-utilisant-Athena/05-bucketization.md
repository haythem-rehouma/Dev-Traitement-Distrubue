-------------------------------------------------------------------
# Bucketing
-------------------------------------------------------------------

- Le terme "bucketing" (ou **bucketization** en anglais) est une technique utilisée pour optimiser le stockage et l'exécution des requêtes dans des systèmes de données massives, comme dans **Amazon S3** lorsqu'on interroge les données via **Amazon Athena**.
- Cette technique consiste à regrouper des lignes de données ayant des valeurs similaires dans des blocs appelés "buckets" ou seaux en français.
- Voici une explication détaillée étape par étape, basée sur le workflow que tu as décrit :


-------------------------------------------------------------------
# 1. **Qu’est-ce que la buckétisation (bucketing) ?**
-------------------------------------------------------------------

La **buckétisation** est une méthode d'optimisation où les données sont réparties dans différents seaux ou "buckets" en fonction de la valeur de certaines colonnes. Cela permet de structurer les données de manière à ce que les requêtes puissent cibler des sous-ensembles de données, réduisant ainsi la quantité de données scannées lors de chaque requête. 

C’est similaire au partitionnement, mais avec quelques différences importantes :
- **Le partitionnement** divise les données en fonction d’une colonne spécifique (ex : une partition par année ou par type de paiement).
- **La buckétisation**, en revanche, divise les données en blocs égaux ou en seaux (buckets) basés sur la valeur de la colonne que tu choisis, en fonction d’un **hash** de cette colonne. Par exemple, si tu as une colonne avec des valeurs comme `paytype`, les lignes de la même catégorie de type de paiement pourraient être distribuées dans plusieurs buckets pour répartir uniformément la charge.


-------------------------------------------------------------------
# 2. **Comment la buckétisation fonctionne-t-elle avec Amazon Athena et S3 ?**
-------------------------------------------------------------------

Dans un scénario AWS, l’objectif principal est d’interroger des **datasets** volumineux (ex : fichiers CSV ou Parquet stockés dans **Amazon S3**) en optimisant le temps de réponse des requêtes tout en réduisant les coûts liés à la quantité de données scannées.

- **Amazon S3** est utilisé pour stocker les fichiers de données brutes.
- **AWS Glue** sert à cataloguer ces données, avec des informations comme les schémas (bases de données, tables, colonnes, types de données).
- **Amazon Athena** est le service qui te permet de lancer des requêtes SQL directement sur ces données.

La **buckétisation** dans ce contexte est un processus qui se déroule lors de la préparation des données dans **AWS Glue**. Tu définis la façon dont tu souhaites buckétiser les données lors de leur ingestion dans le catalogue Glue, de façon à ce que lorsque **Athena** exécute une requête, elle puisse cibler les bons buckets et éviter de scanner l'ensemble du dataset.

-------------------------------------------------------------------
# 3. **Pourquoi utiliser la buckétisation ?**
-------------------------------------------------------------------

- **Amélioration des performances des requêtes** : Au lieu de parcourir toutes les données, Athena peut directement accéder aux buckets concernés, ce qui réduit le temps de traitement.
- **Réduction des coûts** : Amazon Athena facture en fonction de la quantité de données scannées. En réduisant cette quantité grâce à la buckétisation, tu diminues les coûts d’exécution des requêtes.

-------------------------------------------------------------------
# 4. **Différence entre buckétisation et partitionnement**
-------------------------------------------------------------------

Bien que les deux approches visent à optimiser les requêtes, elles diffèrent dans leur implémentation et leur usage :

- **Partitionnement** : Il divise les fichiers de données en plusieurs répertoires sur la base d'une valeur de colonne précise (ex : une partition pour chaque mois ou année). Les requêtes peuvent alors ignorer complètement certaines partitions.
- **Buckétisation** : Il divise les fichiers de données en blocs logiques (buckets) sur la base d'un **hash** de la valeur d'une colonne. Athena utilise cette information pour scanner uniquement certains buckets, plutôt que l'intégralité des données.

-------------------------------------------------------------------
# 5. **Mise en œuvre dans le workflow :**
-------------------------------------------------------------------

Regardons comment cela s'intègre dans le workflow que tu as mentionné :

1. **IAM User (Utilisateur Mary)** : Mary a des permissions limitées mais peut interroger des données via **Amazon Athena**. Elle interroge des fichiers CSV stockés dans **Amazon S3**.
   
2. **Athena** : Mary exécute une requête SQL sur ces fichiers. Athena s’appuie sur le **catalogue AWS Glue** pour comprendre la structure des données (schémas).

3. **Amazon S3** : Les données sont stockées sous forme de fichiers CSV dans des buckets S3. C’est ici que les données brutes se trouvent.

4. **Buckétisation et partitionnement** : Pour optimiser les performances, les données peuvent être **partitionnées** et **buckétisées**. 
   - Le partitionnement pourrait être basé sur une colonne comme `paytype` (ex : données par type de paiement), ce qui divise les données en différentes partitions.
   - La **buckétisation**, elle, va plus loin en divisant les partitions elles-mêmes en buckets. Par exemple, si tu partitionnes par année (`year`), les données de 2022 peuvent être encore subdivisées en buckets pour améliorer davantage les performances.

5. **Athena Views** : Des vues sont créées pour simplifier les requêtes et cacher la complexité de la partition et de la buckétisation à l'utilisateur final, comme Mary.

-------------------------------------------------------------------
# 6. **Exemple :**
-------------------------------------------------------------------


Imaginons un fichier CSV qui contient des informations sur les trajets en taxi. Tu as une colonne `paytype` qui indique si le paiement a été fait en liquide, par carte, ou autre.

- Tu peux **partitionner** tes données par `year`, de sorte que chaque année soit stockée dans une partition différente.
- Ensuite, tu peux **buckétiser** la colonne `paytype` à l'intérieur de chaque partition, ce qui signifie que les données de paiements en liquide, par carte, etc., seront réparties entre différents buckets à l'intérieur de la partition de l'année 2022.

Quand Mary exécute une requête sur les trajets payés en liquide en 2022, Athena peut sauter toutes les partitions sauf celle de 2022, et dans cette partition, elle peut directement accéder aux buckets qui contiennent les données de `paytype=liquide`, réduisant ainsi la quantité de données scannées et accélérant la requête.



-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------
# Vulgarisation: 
-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------

Imagine que tu as une grosse boîte pleine de jouets. Mais à chaque fois que tu veux un jouet, tu dois fouiller dans toute la boîte, ce qui prend beaucoup de temps, car il y a des milliers de jouets dedans.

Alors, pour rendre ça plus facile, tu décides de faire **deux choses** :

1. **Tu classes les jouets par catégories** : Les peluches dans une boîte, les voitures dans une autre boîte, et les puzzles dans une troisième boîte. C'est un peu comme si tu rangeais tes jouets par thème. Cela s’appelle le **partitionnement**. Maintenant, si tu veux une peluche, tu sais dans quelle boîte chercher, donc tu ne perds plus de temps à chercher dans les autres.

2. **Tu divises encore plus les jouets dans chaque catégorie** : Par exemple, dans la boîte des peluches, tu mets les petits nounours dans un coin, les lapins dans un autre, et les pandas ailleurs. C’est ce qu’on appelle la **buckétisation** (ou "mettre dans des seaux"). Cela te permet de trouver encore plus rapidement ce que tu cherches !

Donc, au lieu de fouiller dans toute ta chambre (qui serait très long), tu sais dans quelle boîte aller (grâce au **partitionnement**), et même dans quelle partie de la boîte chercher (grâce à la **buckétisation**). Ça rend tout beaucoup plus rapide !

C'est exactement ce que font les ordinateurs avec les données : ils organisent tout pour que ce soit plus facile à trouver rapidement, comme toi avec tes jouets !


-------------------------------------------------------------------
# 7. **Conclusion :**
-------------------------------------------------------------------

Le **bucketing** est une technique d'optimisation puissante pour les requêtes sur de grands volumes de données. En l’utilisant conjointement avec le **partitionnement**, tu peux améliorer considérablement les performances de tes requêtes et réduire les coûts associés aux services comme **Amazon Athena**.


------------------
# Annexe :
------------------

- Prenons le **dataset de trajets en taxi**, stocké dans **Amazon S3** au format **Parquet**. 
- Chaque ligne du dataset contient des informations comme :

| ride_id | year | month | day | paytype   | amount | passenger_count |
|---------|------|-------|-----|-----------|--------|------------------|
| 1       | 2022 | 1     | 5   | Cash      | 15.50   | 2                |
| 2       | 2022 | 1     | 6   | Card      | 23.00   | 1                |
| 3       | 2022 | 2     | 15  | Cash      | 12.75   | 3                |
| 4       | 2022 | 2     | 20  | Card      | 45.00   | 2                |
| 5       | 2023 | 1     | 3   | Card      | 18.50   | 1                |

---

## **Exemple de Partitionnement :**
Nous allons **partitionner les données par `year` et `month`**.

### Organisation sur S3 :
```
s3://taxi-data/year=2022/month=1/
s3://taxi-data/year=2022/month=2/
s3://taxi-data/year=2023/month=1/
```
Chaque dossier contient des fichiers Parquet contenant uniquement les trajets de l'année et du mois concernés.

### Pourquoi c'est utile ?
Si Mary souhaite interroger uniquement les données de **Janvier 2022**, Athena peut directement cibler :  
```
s3://taxi-data/year=2022/month=1/
```
Ainsi, cela réduit significativement le volume de données à scanner.

---

## **Exemple de Bucketing :**
Maintenant, nous allons **appliquer la buckétisation sur la colonne `paytype`** au sein de chaque partition.

### Configuration du Bucketing :
Pour la **partition** `year=2022/month=1`, nous voulons **3 buckets** (ce nombre est défini par l'utilisateur) :

```
Bucket 1 (hash de `paytype` mod 3 = 0) : "Cash"
Bucket 2 (hash de `paytype` mod 3 = 1) : "Card"
Bucket 3 (hash de `paytype` mod 3 = 2) : (Autres types de paiement, si présents)
```

### Organisation sur S3 :
```
s3://taxi-data/year=2022/month=1/bucket_1/
s3://taxi-data/year=2022/month=1/bucket_2/
s3://taxi-data/year=2022/month=1/bucket_3/
```
Les trajets payés en **cash** sont stockés dans un bucket, ceux payés par **carte** dans un autre, etc.

---

## **Interrogation Athena :**
Disons que Mary souhaite obtenir tous les trajets payés en `Cash` pour **Janvier 2022**.

### Requête SQL :
```sql
SELECT * 
FROM taxi_data
WHERE year = 2022 AND month = 1 AND paytype = 'Cash';
```
---

## **Ce qui se passe en interne :**
1. **Partitionnement :** Athena identifie rapidement le dossier concerné : `s3://taxi-data/year=2022/month=1/`.
2. **Buckétisation :** Athena sait que les données `paytype = 'Cash'` sont probablement dans un **bucket spécifique** (`bucket_1`).
3. **Optimisation :** Athena ne scanne que les fichiers nécessaires. Cela réduit les coûts d'analyse et améliore les performances.

---

### 🔍 **En résumé :**
- **Partitionnement** permet de diviser physiquement les données par dossier sur S3.  
- **Bucketing** permet de diviser ces partitions en sous-blocs logiques sur la base d'une colonne (par hash).

