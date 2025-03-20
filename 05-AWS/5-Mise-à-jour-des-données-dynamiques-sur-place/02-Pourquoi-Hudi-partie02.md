-------------------------------------------------------
# 1 - **AWS Hudi et la mise à jour in place**
-------------------------------------------------------
- **AWS Hudi** n'est pas un simple outil de traitement de texte ou de lignes dans des fichiers. Il s'agit d'un **framework de gestion de données** conçu pour gérer des **données transactionnelles** dans des fichiers stockés dans un data lake (comme Amazon S3).
- Contrairement à **`sed`** ou **`awk`**, qui travaillent principalement avec des fichiers texte, **Hudi travaille avec des ensembles de données structurés** (comme des fichiers Parquet ou Avro) et fournit des fonctionnalités **avancées de mise à jour, d'insertion et de suppression de données**, tout en prenant en charge l'historique des versions et les transactions.
- Les approches **COW** (Copy on Write) et **MOR** (Merge on Read) dans Hudi permettent de gérer les modifications sur de grands ensembles de données **sans avoir à traiter ou réécrire entièrement les fichiers**. Hudi garde une trace des modifications sous forme de logs, fusionne les données à la lecture (MOR), ou réécrit les fichiers modifiés (COW).




----------------------------------------------------
# 2 - Mise à jour **in place** avec Hudi
----------------------------------------------------

Lorsqu'on parle de mise à jour **in place**, cela signifie que l'on peut modifier des données déjà présentes dans un fichier (ou ensemble de fichiers) sans avoir à recréer ou réécrire tous les fichiers. 

Dans un scénario classique (sans Hudi), lorsque vous travaillez avec des données sur un **data lake** (comme S3), les fichiers sont généralement immuables. Si vous devez mettre à jour un enregistrement ou en supprimer un, vous devez :
1. Télécharger le fichier entier.
2. Appliquer les modifications (ajout, mise à jour, suppression).
3. Réécrire le fichier mis à jour en remplaçant l'ancien fichier.

C'est ce qu'on appelle une **approche classique**. Elle peut devenir très coûteuse en temps et en ressources, surtout si vos fichiers sont très volumineux.

### Hudi : Approches **Copy on Write (COW)** et **Merge on Read (MOR)**

Hudi introduit deux façons d'optimiser ce processus en permettant des mises à jour **in place**, c'est-à-dire sans avoir besoin de recréer et réécrire toute la table ou tout le fichier. Voici comment fonctionnent les deux approches :

1. **Copy on Write (COW)**
   - **Comment ça marche ?**
     - Chaque fois que vous faites une mise à jour ou une insertion, Hudi réécrit uniquement les **fichiers qui sont affectés** par les changements. Par exemple, si un fichier contient 1000 lignes et que vous mettez à jour 50 lignes, Hudi va réécrire uniquement le fichier contenant ces 50 lignes modifiées et laisser les autres fichiers intacts.
   - **Avantage :** C'est plus efficace que la méthode classique, car vous ne réécrivez pas toute la table, juste les fichiers modifiés.
   - **Inconvénient :** Vous devez toujours réécrire les fichiers affectés, donc si beaucoup de fichiers sont touchés, cela peut encore être coûteux.

2. **Merge on Read (MOR)**
   - **Comment ça marche ?**
     - Dans ce cas, au lieu de réécrire immédiatement les fichiers, Hudi écrit les nouvelles données dans des **fichiers de log** séparés. Ces fichiers contiennent uniquement les changements (insertions, mises à jour). Ensuite, au moment de la lecture des données, Hudi fusionne (merge) les fichiers de log avec les données existantes.
   - **Avantage :** Vous n'avez pas besoin de réécrire les fichiers immédiatement, ce qui rend les mises à jour plus rapides. La réécriture complète n'a lieu que lors de la lecture, si nécessaire.
   - **Inconvénient :** Lors de la lecture, il peut y avoir un coût supplémentaire dû à la fusion des fichiers de log avec les fichiers de données existants.

### Différence avec l'approche classique
- **Approche classique :** Vous devez réécrire tout le fichier (ou parfois plusieurs fichiers) chaque fois que vous avez des modifications, ce qui prend beaucoup de temps et de ressources.
- **Hudi (COW et MOR) :** Seuls les fichiers affectés sont mis à jour (COW), ou les modifications sont enregistrées dans des logs et fusionnées à la lecture (MOR), ce qui est beaucoup plus efficace.

### Avantages de la mise à jour **in place** avec Hudi
- **Performance** : Les approches COW et MOR permettent d'éviter les réécritures inutiles. Vous gagnez du temps et économisez des ressources lorsque vous travaillez sur de gros ensembles de données.
- **Coût** : Vous économisez sur les coûts de stockage et de traitement, surtout dans un environnement comme S3 où réécrire de grands fichiers peut être coûteux en termes de bande passante et d'opérations.
- **Gestion des données transactionnelles** : Avec Hudi, vous pouvez gérer les mises à jour incrémentales et les transactions, ce qui est idéal pour des systèmes où les données changent fréquemment (par exemple, des logs d'événements).

### Exemples pratiques
1. **Sans Hudi** : Vous avez un fichier de 1 Go avec 100 000 enregistrements. Si vous devez mettre à jour 1000 enregistrements, vous devrez réécrire tout le fichier de 1 Go.
   
2. **Avec Hudi COW** : Vous avez le même fichier, mais cette fois-ci, vous ne réécrivez que la portion du fichier où les 1000 enregistrements sont modifiés, disons un fichier de 10 Mo. Vous économisez donc beaucoup de ressources.

3. **Avec Hudi MOR** : Au lieu de réécrire immédiatement, Hudi écrit simplement les 1000 mises à jour dans un fichier de log. Ce n'est qu'au moment de la lecture que les modifications sont appliquées.

### En résumé
- **COW** est utile si vous voulez des fichiers directement à jour, mais nécessite une réécriture des fichiers affectés.
- **MOR** est plus rapide pour les mises à jour, car vous n'avez pas besoin de réécrire immédiatement, mais la fusion se fait à la lecture.




-----------------------------------
# 3 - Contexte dynamique ?
-----------------------------------

Le **contexte dynamique** dans le cadre de **AWS Hudi** fait référence à la façon dont les données sont modifiées, mises à jour ou insérées **en temps réel** ou de manière **incrémentale** dans un **data lake** comme Amazon S3, tout en évitant de réécrire l'intégralité des fichiers ou tables. Voici ce que cela signifie plus concrètement.

### 1. **Données incrémentales et temps réel**
Dans un **contexte dynamique**, les données changent fréquemment. Par exemple :
   - Vous recevez de nouvelles données à chaque heure, jour ou semaine.
   - Vous devez mettre à jour des enregistrements déjà existants dans votre système (par exemple, une mise à jour des informations clients ou des logs d'événements).
   - Vous devez supprimer certaines lignes (par exemple, suppression des données obsolètes ou erreurs).

Dans un système classique, pour effectuer ces changements, il faut souvent réécrire tout le fichier ou la table, ce qui peut être coûteux en temps et en ressources, surtout si les fichiers sont volumineux.

Avec **Hudi**, ce processus devient **dynamique** dans le sens où vous pouvez effectuer des **mises à jour, insertions ou suppressions en temps réel** **sans réécrire tout le fichier**. Les approches COW et MOR que j'ai mentionnées sont précisément là pour rendre cela possible.

### 2. **Pourquoi c’est dynamique ?**
   - **Données en constante évolution :** Dans un environnement dynamique, les données ne sont pas statiques. Elles évoluent constamment et de nouveaux événements ou données sont ajoutés en permanence.
   - **Optimisation des mises à jour :** Le fait de ne pas réécrire les données existantes à chaque modification rend le système beaucoup plus réactif et efficace pour gérer ces changements en temps réel. Cela est particulièrement utile pour les systèmes où les données sont constamment modifiées ou enrichies, comme les systèmes de log, d'analytique, ou les bases de données transactionnelles.
   - **Traitement en temps réel :** Dans des architectures de traitement de données modernes (comme les pipelines de données sur AWS Glue ou Apache Spark), le traitement en temps réel ou quasi-réel est essentiel pour s'assurer que les modifications sont immédiatement prises en compte dans les analyses ou les rapports.

### 3. **Exemple pratique de contexte dynamique :**
Prenons l'exemple d'un **site de commerce en ligne** qui enregistre les transactions des clients dans un système de data lake comme Amazon S3 :

   - **Nouvelles transactions** : Chaque fois qu’un client effectue une nouvelle commande, cette information doit être ajoutée aux données de transaction. 
     - Avec Hudi, vous pouvez **insérer** ces nouvelles transactions sans devoir toucher aux anciennes données. C'est une opération **dynamique** : elle se produit en temps réel, avec des données ajoutées au fur et à mesure.
   
   - **Mises à jour des commandes** : Si un client met à jour sa commande (par exemple, changement d'adresse de livraison), il est important de **mettre à jour ces informations** dans les données existantes.
     - Avec Hudi, cela se fait de manière incrémentale : seules les lignes affectées sont modifiées, sans réécrire toute la table.
   
   - **Suppression des données** : Si un client demande la suppression de ses données pour des raisons de confidentialité (par exemple, GDPR), Hudi peut supprimer ces enregistrements **dynamique**ment, c’est-à-dire sans avoir à recréer tout le dataset.
   
   - **Analytique en temps réel** : En plus, des outils comme **AWS Glue** ou **Apache Spark** peuvent lire ces données **en temps réel**, avec les modifications appliquées dynamiquement, et fournir des rapports ou des tableaux de bord immédiatement à jour.

### 4. **Différence avec un système statique**
Dans un système **statique**, les données sont chargées une fois, et les mises à jour nécessitent des **réécritures complètes** de fichiers ou de tables. Ce type de système n'est pas optimisé pour gérer les changements fréquents ou en temps réel.

Dans un système **dynamique** comme Hudi :
   - Les modifications sont appliquées de manière **incrémentale**.
   - Les données peuvent être continuellement **mises à jour** ou **supprimées** sans impact significatif sur les performances globales.
   - Vous pouvez gérer des changements fréquents sans avoir à tout reconstruire à chaque fois.

### 5. **Les avantages de ce contexte dynamique avec Hudi**
   - **Gagner du temps et des ressources** : Vous ne réécrivez pas tout le dataset, ce qui économise des ressources (CPU, stockage, bande passante).
   - **Meilleures performances** : Le système reste performant même avec des données qui changent constamment.
   - **Facilité de gestion** : Vous pouvez effectuer des mises à jour fréquentes sans risque de perturber ou de perdre des données déjà existantes.
   - **Réactivité en temps réel** : Vous avez la possibilité de gérer des flux de données en temps réel, ce qui est essentiel dans de nombreux secteurs (finance, logistique, e-commerce).

### En résumé :
Le **contexte dynamique** signifie que les données sont constamment en mouvement (ajout, mise à jour, suppression), et **AWS Hudi** permet de traiter ces modifications **de manière efficace et incrémentale** sans réécrire toutes les données, ce qui est très utile pour des environnements où les changements se produisent fréquemment et où les performances sont critiques.

----------------------------------
# 4 - Annexe 1 : **Étude de Cas : Pourquoi utiliser AWS Hudi au lieu de `awk` et `sed` ?**
----------------------------------

#### Scénario :
Un collègue vient vous voir et dit : "Je comprends qu'on peut utiliser `awk` et `sed` pour modifier des fichiers dans Linux. Alors pourquoi devons-nous utiliser AWS Hudi pour ce projet de big data ? Est-ce que Hudi fait vraiment quelque chose de différent ?"

---

### Réponse à votre collègue :

"Bonne question ! `awk` et `sed` sont des outils puissants, mais ils sont conçus pour modifier des fichiers texte de manière **locale et temporaire**, généralement pour des fichiers de petite à moyenne taille. Voici pourquoi AWS Hudi est mieux adapté à notre cas d'usage big data :

1. **Échelle des Données :**  
   - `awk` et `sed` modifient des fichiers ligne par ligne ou selon des patterns, mais cela devient **impraticable** quand on parle de **millions** ou **milliards de lignes** dans un data lake. Hudi est conçu pour gérer ces énormes volumes de données de manière **efficace et scalable**.

2. **Gestion des Versions et Transactions :**  
   - Contrairement à `awk` et `sed` qui modifient simplement un fichier, Hudi permet de **garder l'historique des modifications** et de **gérer les transactions**. Cela signifie que tu peux revenir à une version précédente de ton dataset, ou bien suivre les changements dans le temps. 

3. **Mises à Jour Dynamiques :**  
   - Hudi permet de **mettre à jour des datasets sans réécrire l'intégralité des fichiers**. Avec les modes **Copy on Write (COW)** et **Merge on Read (MOR)**, les modifications sont soit appliquées lors de la lecture (MOR) ou lors de l'écriture (COW). Avec `awk` ou `sed`, tu devrais réécrire le fichier entier chaque fois que tu veux modifier des lignes.

4. **Gestion Optimisée des Données Stockées sur S3 :**  
   - Dans notre cas, nos données sont stockées dans un data lake comme **Amazon S3**, et Hudi est optimisé pour interagir avec ces systèmes de fichiers distribués. Il est conçu pour **minimiser les lectures et écritures** sur ces systèmes, ce qui n’est pas possible avec des outils de ligne de commande comme `awk` et `sed`."

---

### Analogie :

Pense à `awk` et `sed` comme des outils **ciseaux** ou **correcteurs**, parfaits pour des petites modifications dans des documents simples. AWS Hudi, en revanche, est comme une **imprimerie automatique** capable de gérer des **millions de livres**, tout en te permettant de **remplacer des pages spécifiques** sans réimprimer l'ensemble du livre à chaque correction.

---

### Conclusion :

Bien que `awk` et `sed` puissent convenir à des fichiers texte simples ou des modifications locales, **Hudi est conçu pour la gestion de gros volumes de données transactionnelles**, avec des fonctionnalités comme la mise à jour en place, la gestion des versions, et une performance optimisée pour les data lakes comme S3.



----------------------------------
# 5 - Annexe 2 - exemple de commandes et comparaison entre `sed`, `awk`, et AWS Hudi**
----------------------------------

## **Introduction**

Dans le domaine de la gestion et de la transformation des données, plusieurs outils sont disponibles en fonction des besoins et du contexte. Ce README se concentre sur trois outils couramment utilisés : **`sed`**, **`awk`**, et **AWS Hudi**. Bien qu'ils partagent un objectif commun — la manipulation des données —, ils diffèrent en termes d'échelle, de capacité et d'application.

### Outils comparés :
1. **`sed`** : Stream Editor pour la modification de fichiers texte.
2. **`awk`** : Outil de manipulation et d'extraction de colonnes dans des fichiers structurés.
3. **AWS Hudi** : Framework de gestion de données transactionnelles à grande échelle dans un data lake.

---

## **1. `sed` : Stream Editor pour Modifier du Texte**

### Description :
**`sed`** est un éditeur de flux utilisé pour effectuer des modifications automatisées sur des fichiers texte. Il permet d'éditer des lignes dans un fichier en fonction de patterns spécifiques et est particulièrement utile pour des tâches de transformation de texte.

### Exemple d'Utilisation :
Si vous avez un fichier appelé `exemple.txt` contenant du texte, et que vous voulez remplacer toutes les occurrences du mot "chat" par "chien", vous pouvez utiliser la commande suivante :

```bash
sed 's/chat/chien/g' exemple.txt > exemple_modifie.txt
```

Cette commande :
- **Cherche** toutes les occurrences du mot "chat".
- **Remplace** chaque occurrence par "chien".
- **Écrit** le résultat dans un nouveau fichier `exemple_modifie.txt`.

### Cas d'Usage :
- **Modifications locales** sur des fichiers texte.
- **Automatisation simple** des tâches répétitives sur des fichiers.
- **Inadapté pour des grandes quantités de données** ou des formats complexes comme Parquet.

---

## **2. `awk` : Outil pour Extraire et Manipuler des Colonnes de Fichiers**

### Description :
**`awk`** est un langage de programmation puissant pour la manipulation et l'extraction de données structurées dans des fichiers. Il est idéal pour travailler avec des fichiers contenant des colonnes (comme des fichiers CSV, TSV ou des fichiers avec des délimiteurs).

### Exemple d'Utilisation :
Imaginons un fichier `données.txt` contenant des informations structurées en colonnes :

```
nom age ville
Alice 30 Paris
Bob 25 Lyon
Charlie 22 Marseille
```

Vous pouvez utiliser `awk` pour afficher uniquement la première et la troisième colonne (nom et ville) :

```bash
awk '{print $1, $3}' données.txt
```

Sortie :
```
nom ville
Alice Paris
Bob Lyon
Charlie Marseille
```

### Cas d'Usage :
- Extraction de **colonnes spécifiques** dans des fichiers structurés.
- Manipulation de fichiers avec des **données tabulaires**.
- **Inadapté pour des datasets massifs** ou des data lakes.

---

## **3. AWS Hudi : Gestion Transactionnelle des Données à Grande Échelle**

### Description :
**AWS Hudi** (Hadoop Upsert Delete Incremental) est un framework conçu pour gérer de grands ensembles de données dans un data lake (tel qu'Amazon S3). Hudi permet de **mettre à jour**, **insérer** ou **supprimer** des données de manière efficace, sans réécrire l'ensemble du dataset. Il prend en charge des formats comme **Parquet** et **Avro** et est optimisé pour des opérations transactionnelles à grande échelle.

### Exemple d'Utilisation :
Supposons que vous travaillez avec des données Parquet stockées dans Amazon S3 et que vous souhaitez mettre à jour une colonne spécifique dans votre dataset avec Hudi. Utilisez un environnement Spark avec Hudi pour effectuer la mise à jour.

Voici un exemple de code Python utilisant Spark pour charger, modifier et sauvegarder des données avec Hudi en mode **Copy on Write (COW)** :

```python
from pyspark.sql import SparkSession
from org.apache.hudi.config import HoodieWriteConfig

spark = SparkSession.builder.appName("Hudi Update").getOrCreate()

# Charger les données Hudi depuis S3
df = spark.read.format("hudi").load("s3://mon-bucket-donnees/hudi-table/")

# Appliquer des modifications sur une colonne
df_updated = df.withColumn("colonne_a_mettre_a_jour", df["colonne_a_mettre_a_jour"] + 100)

# Sauvegarder les modifications avec Hudi en mode COW (Copy on Write)
df_updated.write.format("hudi").option(HoodieWriteConfig.TABLE_NAME, "hudi_table") \
    .mode("append").save("s3://mon-bucket-donnees/hudi-table/")
```

### Cas d'Usage :
- **Mises à jour transactionnelles** dans des datasets à grande échelle.
- **Gestion de versions** et de l'historique des modifications dans un data lake.
- **Optimisé pour les environnements distribués** (Amazon S3, HDFS).
- **Inadapté pour des petites tâches locales** sur des fichiers texte.

---

## **Comparaison des Outils**

| Outil       | Type de Données       | Cas d'Usage                             | Limites                                      |
|-------------|-----------------------|-----------------------------------------|----------------------------------------------|
| `sed`       | Fichiers texte simples | Remplacement ou modification locale     | Pas conçu pour de grandes quantités de données |
| `awk`       | Fichiers structurés (CSV, TSV) | Extraction et manipulation de colonnes  | Inadapté pour des datasets volumineux         |
| AWS Hudi    | Datasets massifs (Parquet, Avro) | Mises à jour transactionnelles dans des data lakes | Nécessite un environnement distribué (ex: S3) |

---

## **Conclusion**

- **`sed`** et **`awk`** sont parfaits pour des **modifications locales et simples** sur des fichiers texte ou tabulaires.
- **AWS Hudi** est nécessaire lorsque vous travaillez avec des **datasets massifs dans des data lakes**, avec des besoins de gestion transactionnelle et de versions. 

Si votre objectif est de gérer des **grandes quantités de données**, de **mettre à jour des datasets** tout en maintenant l'historique des modifications, alors AWS Hudi est la solution idéale. Pour des tâches plus simples et locales, `sed` et `awk` restent des outils puissants et pratiques.

---

### **Note :**  
N'oubliez pas de choisir l'outil en fonction de la **taille des données** et des **besoins transactionnels**. Pour des manipulations de texte simples, `sed` et `awk` feront l'affaire, tandis que pour des mises à jour dynamiques à grande échelle, **AWS Hudi** s'impose comme le choix logique.


### Conclusion :
Bien que l’idée de modification soit présente dans les deux cas, **`sed` et `awk`** travaillent sur des fichiers de texte de façon plus simple et à petite échelle, tandis que **Hudi** offre des fonctionnalités beaucoup plus avancées pour gérer des modifications **dynamiques et transactionnelles** dans des datasets volumineux.



