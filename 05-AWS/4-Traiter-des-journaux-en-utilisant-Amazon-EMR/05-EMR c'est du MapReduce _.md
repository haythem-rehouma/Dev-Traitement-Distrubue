# EMR c'est du MapReduce ?
Amazon **EMR** s'appelle **Elastic MapReduce** en raison de ses origines basées sur le paradigme **MapReduce** de traitement de données distribué, qui était très populaire lorsque le service a été lancé.

Voici pourquoi ce nom a été choisi et pourquoi il est encore utilisé :

### 1. **Historique basé sur Hadoop MapReduce** :
Lorsque **Amazon EMR** a été lancé pour la première fois, l'une de ses principales fonctionnalités était de fournir une infrastructure facile à utiliser pour exécuter des tâches **Hadoop**. Hadoop est le système de traitement de données distribué qui repose principalement sur le modèle **MapReduce** pour traiter de grandes quantités de données de manière parallèle sur un cluster de machines. 

- **MapReduce** dans Hadoop permet de diviser les tâches en petits morceaux (étape "Map") et de rassembler les résultats (étape "Reduce") pour obtenir un résultat final. À l'époque, cette approche était l'une des meilleures pour traiter des **volumes massifs de données** de manière efficace.

### 2. **"Elastic"** pour la scalabilité** :
Le terme **Elastic** fait référence à la capacité d'Amazon EMR à **s'adapter dynamiquement** aux besoins en ressources. Vous pouvez augmenter ou diminuer la taille de votre cluster en fonction des volumes de données à traiter ou de la charge de travail. Cela est très utile dans le contexte du traitement de données massives, où les besoins peuvent varier.

### 3. **Le rôle évolutif d'EMR** :
Bien que le modèle **MapReduce** soit à l'origine du nom, Amazon EMR a évolué pour inclure de nombreux autres frameworks de traitement de données distribuées en plus de Hadoop, comme **Apache Spark**, **Hive**, **Presto**, et d'autres. Ces technologies permettent des approches plus rapides et plus flexibles que le modèle original MapReduce.

### Pourquoi le nom **MapReduce** est-il encore utilisé ?
Même si Amazon EMR supporte désormais de nombreuses autres technologies en plus de MapReduce, le nom **Elastic MapReduce** est resté pour des raisons historiques et parce qu'il évoque l'idée de traitement de données massives en parallèle, qui est toujours un concept fondamental dans les big data.

En résumé, **EMR** s'appelle **Elastic MapReduce** car à l'origine, il était conçu pour exécuter des tâches Hadoop basées sur le modèle MapReduce, et le terme **"Elastic"** souligne sa capacité à s'adapter aux besoins de l'utilisateur. Aujourd'hui, bien que d'autres technologies comme Spark soient supportées, le nom est resté en raison de son lien historique avec Hadoop et MapReduce.
