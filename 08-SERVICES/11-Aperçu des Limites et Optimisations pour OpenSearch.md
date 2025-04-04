# Principales Limites d'Amazon OpenSearch Service : Stockage, Capacité et Utilisation  
Aperçu des Limites et Optimisations pour OpenSearch

## Question à laquelle le tutoriel répond :

*Quelles sont les principales limites d'Amazon OpenSearch Service concernant le stockage, la capacité et l'utilisation ?*

## 1. Tableau des limites d'Amazon OpenSearch Service

| **Catégorie**                               | **Limites**                         | **Utilisation**                                            |
|---------------------------------------------|-------------------------------------|------------------------------------------------------------|
| **Stockage par nœud (standard)**            | Jusqu'à **512 Go - 15 To** (EBS)    | Données chaudes, fréquemment accédées                      |
| **Stockage par nœud (haute capacité)**      | Jusqu'à **15,2 To** (SSD local)     | Données chaudes avec grande capacité de stockage           |
| **Capacité UltraWarm par nœud**             | Jusqu'à **200 To**                  | Données froides, moins fréquemment accédées                |
| **Taille maximale par index** (recommandé)  | **50 Go** par shard (recommandé)    | Optimisation des performances des recherches               |
| **Nombre de shards par index** (recommandé) | Illimité (optimisé selon usage)     | Plus de shards = plus de ressources utilisées              |
| **Capacité totale par cluster (standard)**  | Jusqu'à **3 Po**                    | Volume maximal pour un cluster en production               |
| **Capacité totale par cluster (UltraWarm)** | Jusqu'à **200 To**                  | Stockage de données moins critiques à moindre coût         |
| **Nombre maximal d'index par cluster**      | Illimité (dépend des ressources)    | Dépend des performances du cluster                         |
| **Limite de requêtes simultanées**          | Dépend de la configuration des nœuds | Lié aux performances des nœuds et du cluster               |

## 2. Explications

### Stockage par nœud (standard)  
Le stockage standard des nœuds avec des volumes **EBS** varie entre **512 Go et 15 To**, idéal pour les **données chaudes**, souvent accédées.

### Stockage par nœud (haute capacité)  
Les nœuds à haute capacité, utilisant du **stockage SSD local**, offrent jusqu’à **15,2 To** par nœud, parfait pour les **données chaudes** nécessitant un accès rapide et de grandes capacités de stockage.

### Capacité UltraWarm  
Pour les **données froides**, moins fréquemment accédées, **UltraWarm** offre jusqu’à **200 To** par nœud à un coût réduit, utilisant **Amazon S3** pour la persistance.

### Taille maximale par index (recommandé)  
Il est recommandé de limiter la taille d’un shard à **50 Go** pour maintenir des performances optimales lors des recherches dans OpenSearch.

### Nombre de shards par index  
Bien que le nombre de shards soit illimité, il est important d'optimiser selon les usages pour éviter une consommation excessive de ressources.

### Capacité totale par cluster (standard)  
Les clusters standard peuvent gérer jusqu’à **3 Po** de stockage pour les données fréquemment accédées, offrant une grande capacité pour les environnements de production.

### Capacité totale par cluster (UltraWarm)  
Pour les données moins critiques, **UltraWarm** permet de stocker jusqu’à **200 To** par cluster à moindre coût.

### Nombre maximal d'index par cluster  
Le nombre d'index par cluster est illimité, mais il dépend des ressources disponibles pour garantir des performances adéquates.

### Limite de requêtes simultanées  
Le nombre de requêtes simultanées dépend de la configuration du cluster et des nœuds. Plus il y a de ressources (CPU, mémoire), plus le cluster peut gérer de requêtes en parallèle.

## 3. Conclusion  
Cette table fournit un aperçu des principales **limites** d'Amazon OpenSearch Service, permettant de mieux comprendre les **capacités** de stockage et d'utilisation pour différents types de données (chaudes et froides). En respectant ces recommandations, vous pouvez optimiser les performances de votre cluster et ajuster les ressources en fonction de vos besoins.

