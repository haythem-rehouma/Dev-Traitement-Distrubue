
### Créer des nœuds
1. **Créer un nœud `cours` avec un sigle** :
   ```cypher
   CREATE (:cours {sigle: '420-J35-RO'})
   ```

2. **Lister tous les nœuds** :
   ```cypher
   MATCH (n) RETURN n;
   ```

3. **Filtrer les nœuds avec une étiquette `cours`** :
   ```cypher
   MATCH (n:cours) RETURN n;
   MATCH (n:cours) RETURN ID(n), n, n.sigle;
   ```

4. **Créer plusieurs cours avec diplômes** :
   ```cypher
   CREATE (:cours {sigle: "420-J44-RO", diplome:"AEC/DEC/MAITRISE/BAC/DOCTORAT"});
   CREATE (:cours {sigle: "420-J45-RO", diplome:"AEC/DEC/MAITRISE/BAC/DOCTORAT"});
   ```

5. **Lister les nœuds `cours` et leurs étiquettes** :
   ```cypher
   MATCH (n:cours) RETURN n, labels(n);
   ```

### Manipuler des chaînes de caractères
1. **Comparer des chaînes avec sensibilité à la casse** :
   ```cypher
   MATCH (n:cours) WHERE n.sigle="420-J44-RO" RETURN n;
   ```

2. **Utiliser `tolower()` ou `toupper()` pour ignorer la casse** :
   ```cypher
   MATCH (n:cours) WHERE tolower(n.sigle)="420-j44-ro" RETURN n;
   MATCH (n:cours) WHERE toupper(n.sigle)="420-J44-RO" RETURN n;
   ```

3. **Extraire une sous-chaîne de caractères** :
   ```cypher
   MATCH (n:cours) RETURN n, substring(n.sigle, 0, 3);
   MATCH (n:cours) RETURN DISTINCT substring(trim(n.sigle), 8, 2);
   ```

4. **Convertir des sous-chaînes en entier et les manipuler** :
   ```cypher
   MATCH (n:cours) RETURN toInteger(substring(trim(n.sigle), 0, 3)) + 1;
   ```

### Relations entre nœuds
1. **Créer un professeur et une relation d'enseignement** :
   ```cypher
   CREATE (:professeur {matricule: 123, prenom: "Nadine"}) -[:ENSEIGNER]-> (:cours {sigle: "420-J44-RO"});
   ```

2. **Lister les nœuds avec leurs relations** :
   ```cypher
   MATCH (a)-[r]->(b) RETURN a, r, b;
   ```

3. **Ajouter une relation `PREALABLE` entre cours** :
   ```cypher
   CREATE (:cours {sigle:"420-J04-RO"}) -[:PREALABLE]-> (:cours {sigle:"420-J44-RO"});
   ```

4. **Lister les relations avec leurs propriétés** :
   ```cypher
   MATCH (a)-[r:COLLEGUES]->(b) RETURN a.nom, r.programme, b.nom;
   ```

### Utilisation d'`UNWIND` et `ORDER BY`
1. **Unwind pour séparer les valeurs d’une propriété** :
   ```cypher
   MATCH (n:cours) WHERE n.sigle="420-J44-RO"
   UNWIND split(n.diplome, "/") AS element RETURN element;
   ```

2. **Classer les résultats** :
   ```cypher
   MATCH (n:cours) RETURN n ORDER BY n.sigle ASC;
   ```

### Travailler avec `WITH`
1. **Transformer des parties de nœuds avec `WITH`** :
   ```cypher
   MATCH (n:cours)
   WITH substring(n.sigle, 0, 3) AS prefixe, substring(n.sigle, 8, 2) AS college
   RETURN prefixe, college;
   ```

### Fusionner et gérer les relations avec `MERGE`
1. **Fusionner des relations entre professeurs** :
   ```cypher
   MATCH (a:professeur), (b:professeur)
   WHERE a.prenom="haythem" AND b.prenom="julie"
   MERGE (a)-[r:COLLEGUES {programme:"bigdata"}]->(b)
   RETURN a, r, b;
   ```



### Commandes pour manipuler les relations et les propriétés

1. **Créer un professeur et lier des cours en tant que préalable** :
   ```cypher
   CREATE (:cours {sigle: "420-J15-RO"}) -[:PREALABLE]-> (:cours {sigle: "420-J34-RO"}) -[:PREALABLE]-> (:cours {sigle: "420-J45-RO"});
   ```

2. **Lister toutes les relations entre les nœuds `cours`** :
   ```cypher
   MATCH (a)-[r]->(b) RETURN a.sigle, r, b.sigle;
   ```

3. **Afficher une relation avec ses propriétés spécifiques** :
   ```cypher
   MATCH (a:cours)-[r:PREALABLE]->(b:cours) RETURN a.sigle, r, b.sigle;
   ```

### Requêtes combinées pour explorer plusieurs nœuds ou relations

1. **Lister toutes les combinaisons de `cours` et de `professeurs`** :
   ```cypher
   MATCH (a:cours), (b:professeur) RETURN a, b;
   ```

2. **Filtrer sur des attributs spécifiques des professeurs** :
   ```cypher
   MATCH (a:professeur), (b:professeur) WHERE a.prenom="haythem" OR b.prenom="julie" RETURN a, b;
   ```

3. **Créer une relation `COLLEGUES` entre deux professeurs avec des propriétés** :
   ```cypher
   MATCH (a:professeur), (b:professeur)
   WHERE a.prenom="haythem" AND b.prenom="julie"
   CREATE (a)-[r:COLLEGUES {programme: "bigdata"}]->(b)
   RETURN a, r, b;
   ```

4. **Fusionner (MERGE) une relation entre professeurs pour éviter les doublons** :
   ```cypher
   MATCH (a:professeur), (b:professeur)
   WHERE a.prenom="haythem" AND b.prenom="julie"
   MERGE (a)-[r:COLLEGUES {programme: "bigdata"}]->(b)
   RETURN a, r, b;
   ```

### Manipuler et retourner des informations avec des transformations

1. **Retourner uniquement des noms ou des propriétés spécifiques** :
   ```cypher
   MATCH (a)-[r:COLLEGUES]->(b)
   RETURN a.nom, r.programme, b.nom;
   ```

2. **Fusionner des relations `PREALABLE` entre des cours** :
   ```cypher
   MATCH (a:cours), (b:cours)
   WHERE a.sigle="420-J04-RO" AND b.sigle="420-J44-RO"
   MERGE (a)-[r:PREALABLE]->(b);
   ```

3. **Lister toutes les relations avec un certain type** :
   ```cypher
   MATCH (a)-[r:ENSEIGNER]->(b) RETURN a, r, b;
   ```

### Commandes avancées pour la gestion des relations et des propriétés

1. **Ajouter des heures d’enseignement à une relation `ENSEIGNER` entre un professeur et un cours** :
   ```cypher
   CREATE (:professeur {matricule: 789, prenom: "julie", nom: "bousquet"}) -[:ENSEIGNER {nbrhrs: 60}]-> (:cours {sigle: "420-J04-RO"});
   ```

2. **Rechercher une relation avec ses propriétés et lister des informations spécifiques** :
   ```cypher
   MATCH (a)-[r]->(b) RETURN a.sigle, r.nbrhrs, b.sigle;
   ```

3. **Créer des relations hiérarchiques avec des préalables entre les cours** :
   ```cypher
   CREATE (:cours {sigle: "420-J04-RO"}) -[:PREALABLE]-> (:cours {sigle: "420-J44-RO"});
   ```

4. **Lister toutes les relations `PREALABLE` entre les cours** :
   ```cypher
   MATCH (a)-[r:PREALABLE]->(b) RETURN a.sigle, r, b.sigle;
   ```

### Filtrage et manipulation avec des combinaisons et des jointures

1. **Retourner des combinaisons de professeurs avec des filtres sur les prénoms** :
   ```cypher
   MATCH (a:professeur), (b:professeur)
   WHERE a.prenom="haythem" OR b.prenom="julie"
   RETURN a, b;
   ```

2. **Créer une relation `COLLEGUES` entre deux professeurs avec une propriété spécifique** :
   ```cypher
   MATCH (a:professeur), (b:professeur)
   WHERE a.prenom="haythem" AND b.prenom="julie"
   CREATE (a)-[r:COLLEGUES {programme: "bigdata"}]->(b)
   RETURN a, r, b;
   ```

3. **Créer des relations entre les cours et les professeurs, puis lister toutes les combinaisons** :
   ```cypher
   MATCH (a:cours), (b:professeur) RETURN a.sigle, b.prenom;
   ```

