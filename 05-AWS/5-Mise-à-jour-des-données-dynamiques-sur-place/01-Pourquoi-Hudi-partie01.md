#### 1. Différents types de données dans un contexte *Big Data* 
#### 2. Ce que signifie **dynamique**
#### 3. Pourquoi **Hudi** est utile ?

----------------------
----------------------
----------------------

### 1. **Les types de données dans le Big Data**
Dans un environnement Big Data, il existe plusieurs types de données à traiter :

- **Données structurées** : Ce sont des données organisées dans des tableaux ou des bases de données avec des colonnes fixes (comme dans une feuille Excel ou une base de données relationnelle). Exemples : transactions bancaires, inventaires de produits.
  
- **Données semi-structurées** : Ces données ont une certaine structure, mais elle n'est pas rigide. Un bon exemple est **JSON** ou **XML**, où les données sont organisées en paires clé-valeur mais sans un format strict.
  
- **Données non structurées** : Ce sont des données qui n’ont aucune structure définie. Exemples : des images, des vidéos, des documents texte, des e-mails.

- **Données en flux (streaming)** : Ce sont des données qui arrivent en continu, souvent en temps réel. Exemples : données de capteurs IoT, logs de serveurs web.

### 2. **Qu'est-ce qu'un système dynamique ?**
Un système **dynamique** fait référence à un système où les données changent fréquemment. Ces données ne sont pas statiques ; elles évoluent constamment (insertion, mise à jour, suppression). Dans un contexte **Big Data**, cela signifie que les informations changent régulièrement et qu'il est important de pouvoir les modifier **sans perturber l’ensemble du système**.

Exemples :
- Dans une entreprise e-commerce, les **données de commandes** changent tout le temps : nouvelles commandes, annulations, mises à jour des adresses.
- Dans un réseau social, chaque nouvelle publication ou mise à jour de profil modifie les données stockées.

Dans un environnement traditionnel, chaque mise à jour de données peut nécessiter la **réécriture complète** des fichiers. Cela peut devenir très coûteux en termes de **temps et de ressources** lorsque vous avez des téraoctets de données. 

### 3. **Pourquoi Hudi est utile dans un contexte dynamique ?**
**Hudi** est un framework qui permet de gérer ces changements fréquents sans devoir réécrire toutes les données. Il permet d'ajouter, mettre à jour ou supprimer des données **sans avoir à réécrire complètement les fichiers**, ce qui est **très efficace pour les systèmes où les données évoluent souvent**.

#### Table comparative des approches :

| Caractéristiques               | Approche Traditionnelle                 | Avec Hudi (COW)                     | Avec Hudi (MOR)                      |
|---------------------------------|-----------------------------------------|-------------------------------------|--------------------------------------|
| **Type de données**             | Principalement statiques                | Données dynamiques                  | Données dynamiques                   |
| **Traitement des mises à jour** | Réécriture complète des fichiers        | Réécriture des fichiers modifiés    | Stockage dans des logs, fusion à la lecture |
| **Performance**                 | Lente, surtout pour des gros fichiers   | Plus rapide, seules les parties modifiées sont réécrites | Très rapide, car les fichiers ne sont pas réécrits immédiatement |
| **Usage**                       | Données peu changeantes (historique)    | Données en constante évolution (transactions) | Données avec mise à jour fréquente et lecture rapide |
| **Exemple d'utilisation**       | Archives de logs, données historiques   | Suivi des transactions de clients   | Données d'événements en temps réel (IoT, logs de serveur) |

### 4. **Exemple simple :**
Imaginez que vous avez un **journal de bord** (comme un cahier de notes) dans lequel vous enregistrez des informations de vente. 

- Avec l’approche traditionnelle : Chaque fois que vous devez modifier ou ajouter une vente, vous **réécrivez tout le cahier** avec les nouvelles informations.
  
- Avec Hudi (COW) : Vous réécrivez **seulement la page** où la vente a changé.
  
- Avec Hudi (MOR) : Vous **ajoutez un post-it** avec la modification, et lorsque vous voulez lire le journal, vous lisez à la fois la page originale et le post-it pour voir l’information mise à jour.

### Conclusion :
- Hudi est utile dans un environnement dynamique car il permet de gérer les mises à jour fréquentes de manière efficace.
- C’est un outil particulièrement intéressant dans le cadre du Big Data, où la gestion de grandes quantités de données doit être optimisée.
