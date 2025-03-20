# Question :

Vous travaillez en tant qu'architecte sur une application utilisant Amazon DynamoDB. Vous avez une table avec une partition key nommée `customer_id`, qui représente l'identifiant unique de chaque client. Votre application traite des millions d'enregistrements, et chaque `customer_id` est unique (haute cardinalité). Vous remarquez des problèmes de performance lors des requêtes, notamment une lenteur accrue pour accéder à certaines données.

Quel pourrait être le problème lié à l'utilisation de la clé de partition `customer_id` dans ce contexte de haute cardinalité, et comment pourriez-vous optimiser les performances des requêtes tout en maintenant une répartition équilibrée des données dans DynamoDB ?

### A. Diviser la table en plusieurs petites tables, chacune avec un sous-ensemble de `customer_id`.

### B. Utiliser une clé de tri (sort key) pour compléter la clé de partition `customer_id` et mieux structurer les requêtes.

### C. Modifier la clé de partition pour qu'elle combine `customer_id` avec un autre champ ayant une cardinalité plus faible, afin de réduire la dispersion des données sur les partitions.

### D. Configurer une politique de provisionnement de la capacité pour allouer plus de capacité de lecture/écriture aux partitions contenant les enregistrements les plus demandés.

---

### Réponse attendue :
**B. Utiliser une clé de tri (sort key) pour compléter la clé de partition `customer_id` et mieux structurer les requêtes.**

**Explication** :  
Dans DynamoDB, une partition key de haute cardinalité comme `customer_id` peut entraîner une mauvaise répartition des requêtes si les accès sont trop concentrés sur quelques `customer_id` spécifiques. 
En ajoutant une clé de tri, cela permet de structurer les requêtes de manière plus efficace et d'améliorer les performances en diversifiant les accès à différents enregistrements sous une même partition key. 
Cette approche réduit la pression sur les partitions les plus sollicitées.



---------------------------------------------------------------------------------
# Vulgarisation 
---------------------------------------------------------------------------------

### Problème de base (partition key avec haute cardinalité) :
Imagine que tu gères une énorme **bibliothèque** avec des **millions de livres**. Pour t'assurer que tout le monde puisse rapidement trouver et emprunter un livre, tu décides d'organiser les livres en fonction de l'**auteur**. Chaque auteur est unique (comme un `customer_id`), donc chaque livre est placé dans un **rayon dédié** à l'auteur. 

Ça a l'air génial, non ? Chaque auteur a son propre rayon, et si quelqu'un veut un livre de J.K. Rowling, il sait exactement où aller. Sauf que… J.K. Rowling est super populaire ! Presque **tout le monde** vient chercher des livres de J.K. Rowling, et il y a **toujours une longue file d'attente** dans son rayon. Pendant ce temps, dans les rayons des auteurs moins connus, personne ne vient et ils sont presque vides. Résultat : le rayon de J.K. Rowling est **surchargé**, et ça prend **beaucoup de temps** pour que chaque personne trouve et emprunte son livre.

### Le problème en termes de DynamoDB :
C'est un peu pareil avec DynamoDB quand tu utilises une partition key de **haute cardinalité**, comme `customer_id`, pour répartir tes données. Si trop de monde demande les données associées à un `customer_id` très populaire (comme J.K. Rowling dans notre exemple), cela surcharge une seule partition dans DynamoDB, et les performances diminuent.

---

### Solution (ajouter une clé de tri) avec une analogie :
Maintenant, revenons à notre bibliothèque. Comment rendre les choses plus efficaces ? 

Au lieu de tout mettre dans un seul rayon par auteur, tu décides d'ajouter une **deuxième organisation** : en plus du nom de l'auteur, tu vas organiser les livres par **année de publication**. Maintenant, non seulement les livres de J.K. Rowling sont dans un rayon, mais ils sont également répartis par année. Il y a un rayon pour les livres de J.K. Rowling de **1997**, un autre pour **1998**, etc. Ainsi, si beaucoup de gens viennent chercher les livres de J.K. Rowling, ils ne se bousculent plus tous dans le même rayon. Certains iront dans le rayon 1997, d'autres dans le 1998, ce qui rend la bibliothèque **plus rapide et efficace**.

### Solution en termes de DynamoDB :
En ajoutant une **clé de tri** (`sort key`) en plus du `customer_id` (la partition key), on fait exactement la même chose. Par exemple, si on ajoute une `order_date` (la date de commande) comme clé de tri, les données ne seront plus toutes stockées ensemble uniquement par `customer_id`. Elles seront mieux **réparties** par `order_date`, ce qui répartit la charge et rend les **requêtes plus rapides**.

---

### Conclusion :
En organisant mieux ta bibliothèque (ou tes données DynamoDB), tu évites que tout le monde se précipite dans un seul rayon. En ajoutant une deuxième organisation (la clé de tri), tu **divises la charge**, et tout le monde peut accéder à ses livres (ou données) **plus rapidement et sans attendre**.

