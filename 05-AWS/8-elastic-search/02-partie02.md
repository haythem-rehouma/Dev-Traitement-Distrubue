### Étape 10 : Tester des requêtes avec Cypher
Une fois que vous êtes connecté à Neo4j via le **Cypher Shell** ou l’interface web, vous pouvez commencer à créer des données et exécuter des requêtes. Voici quelques exemples pour tester Neo4j avec des requêtes de base :

#### Exemple 1 : Créer un nœud (node)
```cypher
CREATE (n:Person {name: 'John', age: 30})
```
Cela crée un nœud avec l'étiquette `Person`, qui possède les propriétés `name` et `age`.

#### Exemple 2 : Trouver un nœud
```cypher
MATCH (n:Person) WHERE n.name = 'John' RETURN n
```
Cette requête recherche tous les nœuds de type `Person` où le nom est `John` et retourne ces nœuds.

#### Exemple 3 : Créer une relation entre deux nœuds
```cypher
CREATE (a:Person {name: 'Alice'})-[:KNOWS]->(b:Person {name: 'Bob'})
```
Cette commande crée deux nœuds `Alice` et `Bob`, et crée une relation `KNOWS` entre eux.

#### Exemple 4 : Trouver des relations
```cypher
MATCH (a:Person)-[:KNOWS]->(b:Person) RETURN a, b
```
Cette requête retourne les personnes qui se connaissent, en récupérant les nœuds et leurs relations.

#### Exemple 5 : Mettre à jour un nœud
```cypher
MATCH (n:Person {name: 'John'}) SET n.age = 31 RETURN n
```
Cela met à jour l’âge de la personne nommée `John`.

#### Exemple 6 : Supprimer un nœud et une relation
```cypher
MATCH (n:Person {name: 'John'}) DETACH DELETE n
```
Cette requête supprime le nœud `John` ainsi que toutes les relations qui y sont associées.

### Étape 11 : Sauvegarde et Restauration de Neo4j
Pour des raisons de sécurité et de gestion des données, vous pourriez vouloir sauvegarder ou restaurer votre base de données.

#### Sauvegarder la base de données Neo4j :
Neo4j fournit des outils pour la sauvegarde. Voici une commande pour créer un snapshot (sauvegarde) :
```bash
neo4j-admin dump --database=neo4j --to=/path/to/backup.dump
```

#### Restaurer la base de données Neo4j :
Pour restaurer une base de données à partir d'une sauvegarde, vous pouvez utiliser la commande suivante :
```bash
neo4j-admin load --from=/path/to/backup.dump --database=neo4j --force
```

### Étape 12 : Configurer Neo4j pour l’accès distant
Par défaut, Neo4j n'est accessible que localement (localhost). Si vous souhaitez y accéder depuis une machine distante, vous devez modifier le fichier de configuration.

1. **Éditer le fichier de configuration Neo4j** :
   Ouvrez le fichier de configuration Neo4j pour autoriser les connexions externes.
   ```bash
   sudo nano /etc/neo4j/neo4j.conf
   ```

2. **Activer l'accès distant** :
   Recherchez la ligne suivante dans le fichier de configuration :
   ```bash
   #dbms.default_listen_address=0.0.0.0
   ```
   Remplacez-la par :
   ```bash
   dbms.default_listen_address=0.0.0.0
   ```
   Cela permettra à Neo4j d'écouter sur toutes les interfaces réseau.

3. **Redémarrer Neo4j** :
   Après avoir fait cette modification, redémarrez Neo4j pour que les changements prennent effet :
   ```bash
   sudo systemctl restart neo4j
   ```

### Étape 13 : Surveillance et gestion de Neo4j
Vous pouvez surveiller Neo4j et voir son utilisation de la mémoire, des requêtes en cours, etc., à partir de l'interface graphique en accédant à l'onglet **Monitoring**. Cela peut vous aider à optimiser les performances ou à détecter des problèmes potentiels.

#### Commande pour surveiller Neo4j via le système :
```bash
top | grep neo4j
```
Cela vous montrera l’utilisation de Neo4j en termes de CPU et de mémoire.

### Étape 14 : Documentation et Ressources supplémentaires
Neo4j dispose d'une documentation très complète et de nombreux guides pour débuter ou approfondir vos connaissances. Voici quelques ressources utiles :
- [Documentation officielle de Neo4j](https://neo4j.com/docs/)
- [Guide Cypher](https://neo4j.com/developer/cypher-query-language/)

### Conclusion
Vous avez maintenant installé Neo4j et vous savez comment démarrer, créer des nœuds, interroger des relations, et gérer votre base de données. Neo4j est un puissant outil pour gérer des données relationnelles et explorer les interactions complexes entre elles. 
